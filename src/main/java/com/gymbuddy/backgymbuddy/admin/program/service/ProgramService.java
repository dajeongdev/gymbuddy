package com.gymbuddy.backgymbuddy.admin.program.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.gymbuddy.backgymbuddy.admin.enums.status.ProgramStatus;
import com.gymbuddy.backgymbuddy.admin.exception.DMException;
import com.gymbuddy.backgymbuddy.admin.program.domain.Program;
import com.gymbuddy.backgymbuddy.admin.program.domain.ProgramDto;
import com.gymbuddy.backgymbuddy.admin.program.domain.ProgramOption;
import com.gymbuddy.backgymbuddy.admin.program.domain.ProgramOptionDto;
import com.gymbuddy.backgymbuddy.admin.program.repository.ProgramOptionRepository;
import com.gymbuddy.backgymbuddy.admin.program.repository.ProgramRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ProgramOptionRepository optionRepository;

    public int selectTotalCount() {
        return programRepository.findAll().size();
    }

    public List<ProgramDto> findAllForMain() {
        List<Program> list = programRepository.findAllByMainYn("Y");
        List<ProgramDto> dtoList = new ArrayList<>();
        list.stream().forEach(program -> {
            ProgramDto dto = new ProgramDto();
            dto.setId(program.getId());
            dto.setTitle(program.getTitle());
            dto.setCoach(program.getCoach());
            dto.setClassAddress(program.getClassAddress());
            dto.setClassDate(program.getClassDate());
            dto.setClassTime(program.getClassTime());
            dto.setPrice(program.getPrice());
            dto.setMainYn(program.getMainYn());
            dto.setStatus(program.getStatus());
            if (program.getThumbnailImgPath() != null) {
                dto.setThumbnailImgPath(program.getThumbnailImgPath());
            }
            if (program.getThumbnailImgName() != null) {
                dto.setThumbnailImgName(program.getThumbnailImgName());
            }
            if (program.getDetailImgPath() != null) {
                dto.setDetailImgPath(program.getDetailImgPath());
            }
            if (program.getDetailImgName() != null) {
                dto.setDetailImgName(program.getDetailImgName());
            }
            dtoList.add(dto);
        });
        return dtoList;
    }

    public List<Program> findAll(int page) {
        return programRepository.findAllByMainYnAndCreateDate(PageRequest.of(page, 10, Sort.by("id").descending())).getContent();
    }

    public Map<String, Object> findAllDto(int page) {
        List<Program> list = findAll(page);
        List<ProgramDto> dtoList = new ArrayList<>();
        for (Program one : list) {
            ProgramDto dto = entityToDto(one);
            dtoList.add(dto);
        }

        int count = programRepository.mainYnCount();

        Map<String, Object> result = new HashMap<>();
        result.put("programList", dtoList);
        result.put("mainCounts", count);
        return result;
    }

    public Program findOne(Long id) {
        Optional<Program> byId = programRepository.findById(id);
        if (!byId.isPresent()) {
            throw new DMException("존재하지 않는 프로그램입니다.");
        }
        Program program = byId.get();

        List<ProgramOption> options = optionRepository.findAllByProgramId(id);
        program.setProgramOptions(options);
        return program;
    }

    public ProgramDto findOneDto(Long id) {
        Program one = findOne(id);
        return entityToDto(one);
    }

    private ProgramDto entityToDto(Program one) {
        ProgramDto dto = new ProgramDto();
        if (one.getId() != null) {
            dto.setId(one.getId());
        }
        if (one.getTitle() != null) {
            dto.setTitle(one.getTitle());
        }
        if (one.getCoach() != null) {
            dto.setCoach(one.getCoach());
        }
        if (one.getClassAddress() != null) {
            dto.setClassAddress(one.getClassAddress());
        }
        if (one.getClassDate() != null) {
            dto.setClassDate(one.getClassDate());
        }
        if (one.getClassTime() != null) {
            dto.setClassTime(one.getClassTime());
        }
        if (one.getPrice() != null) {
            dto.setPrice(one.getPrice());
        }
        if (one.getMainYn() != null) {
            dto.setMainYn(one.getMainYn());
        }
        if (one.getStatus() != null) {
            dto.setStatus(one.getStatus());
        }
        if (one.getThumbnailImgPath() != null) {
            dto.setThumbnailImgPath(one.getThumbnailImgPath());
        }
        if (one.getThumbnailImgName() != null) {
            dto.setThumbnailImgName(one.getThumbnailImgName());
        }
        if (one.getDetailImgPath() != null) {
            dto.setDetailImgPath(one.getDetailImgPath());
        }
        if (one.getDetailImgName() != null) {
            dto.setDetailImgName(one.getDetailImgName());
        }
        if (!one.getProgramOptions().isEmpty()) {
            List<ProgramOptionDto> optionList = new ArrayList<>();
            for (ProgramOption opt : one.getProgramOptions()) {
                ProgramOptionDto optDto = new ProgramOptionDto();
                if (opt.getId() != null) {
                    optDto.setId(opt.getId());
                }
                if (opt.getClassDateTime() != null) {
                    optDto.setClassDateTime(opt.getClassDateTime());
                }
                if (opt.getUserCount() != 0) {
                    optDto.setUserCount(opt.getUserCount());
                }
                if (opt.getAddPrice() != null) {
                    optDto.setAddPrice(opt.getAddPrice());
                }
                optionList.add(optDto);
            }
            dto.setOptions(optionList);
        }
        return dto;
    }

    public ProgramOption findOneOption(Long id) {
        Optional<ProgramOption> byId = optionRepository.findById(id);
        if (!byId.isPresent()) {
            throw new DMException("존재하지 않는 프로그램 옵션입니다.");
        }
        return byId.get();
    }

    @Transactional
    public Long save(ProgramDto program) {
        // 현재 로그인한 아이디 정보 조회
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String loginId = userDetails.getUsername();

        Program entity = new Program();
        if (program.getTitle() != null) {
            entity.setTitle(program.getTitle());
        } else {
            throw new DMException("제목을 입력해주세요.");
        }
        if (program.getCoach() != null) {
            entity.setCoach(program.getCoach());
        } else {
            throw new DMException("진행자를 입력해주세요.");
        }
        if (program.getClassAddress() != null) {
            entity.setClassAddress(program.getClassAddress());
        } else {
            throw new DMException("장소를 입력해주세요.");
        }
        if (program.getClassDate() != null) {
            entity.setClassDate(program.getClassDate());
        } else {
            throw new DMException("일자를 입력해주세요.");
        }
        if (program.getClassTime() != null) {
            entity.setClassTime(program.getClassTime());
        } else {
            throw new DMException("시간을 입력해주세요.");
        }
        if (program.getPrice() != null) {
            entity.setPrice(program.getPrice());
        } else {
            throw new DMException("가격을 입력해주세요.");
        }
        if (program.getThumbnailImgName() != null) {
        entity.setThumbnailImgName(program.getThumbnailImgName());
        } else {
            throw new DMException("대표 이미지를 입력해주세요.");
        }
        if (program.getThumbnailImgPath() != null) {
        entity.setThumbnailImgPath(program.getThumbnailImgPath());
        } else {
            throw new DMException("대표 이미지를 입력해주세요.");
        }
        if (program.getDetailImgName() != null) {
        entity.setDetailImgName(program.getDetailImgName());
        } else {
            throw new DMException("상세 이미지를 입력해주세요.");
        }
        if (program.getDetailImgPath() != null) {
        entity.setDetailImgPath(program.getDetailImgPath());
        } else {
            throw new DMException("상세 이미지를 입력해주세요.");
        }
        entity.setMainYn("N");
        entity.setStatus(ProgramStatus.INPROGRESS);
        entity.setCreateId(loginId);
        entity.setUpdateId(loginId);

        programRepository.save(entity);

        // 옵션 저장(String -> List<ProgramOptionDto>로 변환 후 처리)
        String list = program.getOptionList();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ProgramOptionDto>>(){}.getType();
        ArrayList<ProgramOptionDto> optionList = gson.fromJson(list, listType);

        // 옵션 저장
        saveOptions(loginId, entity, optionList);

        return entity.getId();
    }

    @Transactional
    public void updateStatus(Long id, ProgramStatus status) {
        Program program = findOne(id);
        if (status != null) {
            program.setStatus(status);
        }
    }

    @Transactional
    public void setMainYn(Long id, String mainYn) {
        Program program = findOne(id);
        if (mainYn != null) {
            program.setMainYn(mainYn);
        }
    }

    @Transactional
    public void update(Long id, ProgramDto program) {
        // 현재 로그인한 아이디 정보 조회
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String loginId = userDetails.getUsername();

        Program origin = findOne(id);
        if (program.getTitle() != null) {
            origin.setTitle(program.getTitle());
        }
        if (program.getCoach() != null) {
            origin.setCoach(program.getCoach());
        }
        if (program.getClassAddress() != null) {
            origin.setClassAddress(program.getClassAddress());
        }
        if (program.getClassDate() != null) {
            origin.setClassDate(program.getClassDate());
        }
        if (program.getClassTime() != null) {
            origin.setClassTime(program.getClassTime());
        }
        if (program.getPrice() != null) {
            origin.setPrice(program.getPrice());
        }
        if (program.getMainYn() != null) {
            origin.setMainYn(program.getMainYn());
        }
        if (program.getStatus() != null) {
            origin.setStatus(program.getStatus());
        }
        if (origin.getThumbnailImgPath() != null) {
            origin.setThumbnailImgPath(program.getThumbnailImgPath());
        }
        if (origin.getThumbnailImgName() != null) {
            origin.setThumbnailImgName(program.getThumbnailImgName());
        }
        if (origin.getDetailImgPath() != null) {
            origin.setDetailImgPath(program.getDetailImgPath());
        }
        if (origin.getDetailImgName() != null) {
            origin.setDetailImgName(program.getDetailImgName());
        }
        origin.setUpdateId(loginId);

        // 옵션 수정(String -> List<ProgramOptionDto>로 변환 후 처리)
        String list = program.getOptionList();
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<ProgramOptionDto>>(){}.getType();
        ArrayList<ProgramOptionDto> optionList = gson.fromJson(list, listType);

        // 옵션 수정..
        if (!optionList.isEmpty()) {
            // 기존 옵션 삭제
            optionRepository.deleteByProgramId(id);

            // 옵션 저장
            saveOptions(loginId, origin, optionList);
        }
    }

    /**
     * 옵션 저장
     * @param loginId 로그인된 아이디
     * @param origin 프로그램 정보
     * @param optionList 새로운 옵션 목록
     */
    private void saveOptions(String loginId, Program origin, ArrayList<ProgramOptionDto> optionList) {
        for (ProgramOptionDto dto : optionList) {
            ProgramOption option = new ProgramOption();
            if (dto.getClassDateTime() != null) {
                option.setClassDateTime(dto.getClassDateTime());
            } else {
                throw new DMException("옵션명을 입력해주세요.");
            }
            if (dto.getUserCount() != 0) {
                option.setUserCount(dto.getUserCount());
            } else {
                throw new DMException("참여 인원수를 입력해주세요.");
            }
            if (dto.getAddPrice() != null) {
                option.setAddPrice(dto.getAddPrice());
            }
            option.setProgram(origin);
            option.setCreateId(loginId);
            option.setUpdateId(loginId);
            optionRepository.save(option);
        }
    }

    @Transactional
    public void delete(long id) {
        programRepository.deleteById(id);
        // 옵션 목록 삭제..
        optionRepository.deleteByProgramId(id);
    }
}
