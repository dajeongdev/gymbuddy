package com.gymbuddy.backgymbuddy.admin.frequencyQuestion.controller;

import com.gymbuddy.backgymbuddy.admin.frequencyQuestion.service.FQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FQController {

    private final FQService fqService;
}
