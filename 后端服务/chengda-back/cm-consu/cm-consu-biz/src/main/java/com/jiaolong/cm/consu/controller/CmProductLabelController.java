package com.jiaolong.cm.consu.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * desc: 商品标签管理
 * user: pan
 * date: 2024-08-21 10:55
 */
@RestController
@RequestMapping("/label")
@Tag(description = "label", name = "商品标签管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmProductLabelController {
}
