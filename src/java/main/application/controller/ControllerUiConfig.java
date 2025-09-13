package com.gmail.bishoybasily.licensing.application.controller;

import com.gmail.bishoybasily.licensing.application.model.dto.UiConfigDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/ui-config")
public class ControllerUiConfig {

    @GetMapping
    public @ResponseBody Mono<UiConfigDto> getUiConfig() {
        return Mono.fromCallable(() -> UiConfigDto.builder().name("test").build());
    }

}
