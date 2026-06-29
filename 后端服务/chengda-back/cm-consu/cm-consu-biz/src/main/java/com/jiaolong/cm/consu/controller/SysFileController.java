package com.jiaolong.cm.consu.controller;

import cn.hutool.core.io.IoUtil;
import com.jiaolong.cm.consu.service.SysFileService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.security.annotation.Inner;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/file")
@Tag(description = "file", name = "文件图片上传管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class SysFileController {

	private final SysFileService sysFileService;

	/**
	 * 上传文件 文件名采用uuid,避免原始文件名中带"-"符号导致下载的时候解析出现异常
	 *
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/upload")
	@Operation(summary = "上传文件", description = "上传文件")
	public R upload(@RequestPart("file") MultipartFile file) {
		return sysFileService.uploadFile(file);
	}

	/**
	 * 获取文件
	 *
	 * @param bucket   桶名称
	 * @param fileName 文件空间/名称
	 * @param response
	 * @return
	 */
	@Inner(false)
	@GetMapping("/{bucket}/{fileName}")
	@Operation(summary = "获取文件", description = "获取文件")
	public void file(@PathVariable(value = "bucket", name = "bucket") String bucket,
					 @PathVariable(value = "fileName", name = "fileName") String fileName,
					 HttpServletResponse response) {
		sysFileService.getFile(bucket, fileName, response);
	}

	/**
	 * 获取本地（resources）文件
	 *
	 * @param fileName 文件名称
	 * @param response 本地文件
	 */
	@SneakyThrows
	@GetMapping("/local/file/{fileName}")
	@Operation(summary = "获取本地（resources）文件", description = "获取本地（resources）文件")
	public void localFile(@PathVariable String fileName, HttpServletResponse response) {
		ClassPathResource resource = new ClassPathResource("file/" + fileName);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IoUtil.copy(resource.getInputStream(), response.getOutputStream());
	}

}
