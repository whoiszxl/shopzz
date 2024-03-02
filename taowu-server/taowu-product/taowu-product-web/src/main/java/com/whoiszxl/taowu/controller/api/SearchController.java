package com.whoiszxl.taowu.controller.api;

import com.whoiszxl.taowu.service.SpuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@Tag(name = "C端:搜索相关接口")
@RequiredArgsConstructor
public class SearchController {

    private final SpuService spuService;

}