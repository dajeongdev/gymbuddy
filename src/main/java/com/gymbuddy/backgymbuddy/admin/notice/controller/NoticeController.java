package com.gymbuddy.backgymbuddy.admin.notice.controller;

import com.gymbuddy.backgymbuddy.admin.base.BaseController;
import com.gymbuddy.backgymbuddy.admin.notice.domain.Notice;
import com.gymbuddy.backgymbuddy.admin.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gymbuddy.backgymbuddy.admin.base.Constants.NOTICE_PREFIX;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NoticeController extends BaseController {

    private final String URI_PREFIX = NOTICE_PREFIX;

    private final NoticeService noticeService;

    /**
     * 전체 공지사항 조회(관리자)
     */
    @GetMapping(URI_PREFIX + "/all")
    public ResponseEntity<List<Notice>> selectNoticeList() {
        return createResponseEntity(true, noticeService.findAll());
    }

    /**
     * 메인 노출 공지사항 조회(최근 5개만)
     */
    @GetMapping(URI_PREFIX + "/mainAll")
    public ResponseEntity<List<Notice>> selectMainNoticeList() {
        return createResponseEntity(true, noticeService.findAllForMain());
    }

    /**
     * 공지사항 상세
     */
    @GetMapping(URI_PREFIX + "/detail/{id}")
    public ResponseEntity<Notice> selectNoticeDetail(@PathVariable("id") Long id) {
        log.info("공지사항 아이디로 조회: {}", id);
        return createResponseEntity(true, noticeService.findOne(id));
    }

    /**
     * 공지사항 등록
     */
    @PostMapping(URI_PREFIX + "/new")
    public ResponseEntity<Map<String, Object>> insertNotice(@RequestBody Notice notice) {
        log.info("공지사항 등록: {}", notice);
        Long id = noticeService.save(notice);

        Map<String, Object> result = new HashMap<>();
        result.put("id", id);
        return createResponseEntity(true, result);
    }

    /**
     * 공지사항의 제목, 내용 수정
     */
    @PutMapping(URI_PREFIX + "/update/{id}")
    public ResponseEntity<Map<String, Object>> updateNotice(@PathVariable("id") Long id, @RequestBody Map<String, Object> param) {
        log.info("공지사항 수정 - id: {}, param: {}", id, param);
        String title = Objects.toString(param.get("title"));
        String contents = Objects.toString(param.get("contents"));
        noticeService.update(id, title, contents);
        Notice findNotice = noticeService.findOne(id);

        Map<String, Object> result = new HashMap<>();
        result.put("id", findNotice.getId());
        result.put("title", findNotice.getTitle());
        result.put("contents", findNotice.getContents());
        return createResponseEntity(true, result);
    }

    // TODO 이미지 추가 및 삭제 로직 필요

    /**
     * 공지사항 삭제
     */
    @DeleteMapping(URI_PREFIX + "/delete")
    public ResponseEntity<Map<String, Object>> deleteNotice(@RequestParam List<Long> ids) {
        log.info("공지사항 삭제: {}", ids.toString());
        int deleteResult = noticeService.delete(ids);

        Map<String, Object> result = new HashMap<>();
        result.put("result", deleteResult);
        return createResponseEntity(true, result);
    }

}
