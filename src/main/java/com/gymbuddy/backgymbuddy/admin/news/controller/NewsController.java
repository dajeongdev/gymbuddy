package com.gymbuddy.backgymbuddy.admin.news.controller;

import com.gymbuddy.backgymbuddy.admin.base.BaseController;
import com.gymbuddy.backgymbuddy.admin.news.domain.News;
import com.gymbuddy.backgymbuddy.admin.news.domain.NewsDto;
import com.gymbuddy.backgymbuddy.admin.news.service.NewsService;
import com.gymbuddy.backgymbuddy.admin.notice.domain.Notice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gymbuddy.backgymbuddy.admin.base.Constants.NEWS_PREFIX;

@Slf4j
@RestController
@RequiredArgsConstructor
public class NewsController extends BaseController {

    private final String URI_PREFIX = NEWS_PREFIX;
    private String newsPath = "/resources/static/img/news";
    private String rootPath = System.getProperty("user.dir") + "/src/main" + newsPath;
    private File newfile = new File(rootPath);

    private final NewsService newsService;

    /**
     * 전체 대외뉴스 조회(관리자)
     */
    @GetMapping(URI_PREFIX + "/all/{page}")
    public ResponseEntity<List<News>> selectNewsList(@PathVariable("page") int page) {
        return createResponseEntity(true, newsService.findAll(page));
    }

    /**
     * 메인 노출 대외뉴스 조회(최근 5개만)
     */
    @GetMapping(URI_PREFIX + "/mainAll")
    public ResponseEntity<List<News>> selectMainNewsList() {
        return createResponseEntity(true, newsService.findAllForMain());
    }

    /**
     * 대외뉴스 상세
     */
    @GetMapping(URI_PREFIX + "/detail/{id}")
    public ResponseEntity<News> selectNewsDetail(@PathVariable("id") Long id) {
        log.info("대외뉴스 아이디로 조회: {}", id);
        return createResponseEntity(true, newsService.findOne(id));
    }

    /**
     * 대외뉴스 등록
     */
    @PostMapping(URI_PREFIX + "/new")
    public ResponseEntity<Map<String, Object>> insertNews(@RequestBody NewsDto news) {
        log.info("대외뉴스 등록: {}", news);

        // 이미지 업로드
        String filename = news.getFile().getOriginalFilename();
        try {
            if (!newfile.exists()) {
                newfile.mkdir();
            }
            File realFile = new File(newfile + "/" + System.currentTimeMillis() + "_" + filename);
            news.getFile().transferTo(realFile);
            news.setImgName(filename);
            news.setImgPath(newsPath + realFile.getName());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("id", newsService.save(news));
        return createResponseEntity(true, result);
    }

    /**
     * 대외뉴스 수정
     */
    @PutMapping(URI_PREFIX + "/update/{id}")
    public ResponseEntity<Map<String, Object>> updateNews(@PathVariable("id") Long id, @RequestBody NewsDto news) {
        log.info("대외뉴스 수정 - id: {}, param: {}", id, news);

        News origin = newsService.findOne(id);
        String filename = news.getFile().getOriginalFilename();
        if (!origin.getImgName().equals(filename)) {
            // 이미지 업로드
            try {
                File realFile = new File(newfile + "/" + System.currentTimeMillis() + "_" + filename);
                news.getFile().transferTo(realFile);
                news.setImgName(filename);
                news.setImgPath(newsPath + realFile.getName());

                // 기존 이미지 파일 서버에서 삭제
                File originFile = new File(newfile + "/" + origin.getImgPath());
                if (originFile.exists()) {
                    originFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }

        newsService.update(id, news);
        News findNews = newsService.findOne(id);


        boolean flag = true;
        if (news.getTitle() != null) {
            flag = news.getTitle().equals(findNews.getTitle()) ? true : false;
        }
        if (news.getContents() != null) {
            flag = news.getContents().equals(findNews.getContents()) ? true : false;
        }
        if (news.getImgPath() != null) {
            flag = news.getImgPath().equals(findNews.getImgPath());
        }
        if (news.getImgName() != null) {
            flag = news.getImgName().equals(findNews.getImgName());
        }
        if (news.getMainYn() != null) {
            flag = news.getMainYn().equals(findNews.getMainYn());
        }

        Map<String, Object> result = new HashMap<>();
        result.put("id", flag);
        return createResponseEntity(true, result);
    }

    /**
     * 대외뉴스 삭제
     */
    @DeleteMapping(URI_PREFIX + "/delete")
    public ResponseEntity<Map<String, Object>> deleteNews(@RequestBody List<Integer> ids) {
        log.info("대외뉴스 삭제: {}", ids);

        for (int id : ids) {
            long idL = new Long(id);
            News origin = newsService.findOne(idL);
            // 이미지 삭제
            File originFile = new File(newfile + "/" + origin.getImgPath());
            if (originFile.exists()) {
                originFile.delete();
            }
            newsService.delete(idL);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        return createResponseEntity(true, result);
    }
}
