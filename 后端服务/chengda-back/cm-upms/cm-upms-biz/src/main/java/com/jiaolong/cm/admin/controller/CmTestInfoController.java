package com.jiaolong.cm.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiaolong.cm.admin.api.dto.ExamGoodsDto;
import com.jiaolong.cm.admin.api.vo.TestInfoVo;
import com.jiaolong.cm.admin.service.CmTestInfoService;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.common.security.annotation.Inner;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.param.TestInfoParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * desc: 质检管理
 * user: pan
 * date: 2024-09-24 09:43
 */
@RestController
@RequestMapping("/examine")
@Tag(description = "examine", name = "质检管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmTestInfoController {

	@Autowired
	private CmTestInfoService testInfoService;

	/**
	 * 获取质检分页列表
	 *
	 * @param page  分页参数
	 * @param vo 查询参数
	 * @return
	 */
	@GetMapping("/page")
	@HasPermission("admin_exam_page")
	@Operation(summary = "分页列表", description = "获取质检分页列表")
	public R<IPage<TestInfoDto>> getPage(@ParameterObject Page page, @ParameterObject TestInfoParam vo){
		return testInfoService.getTestInfoPage(page, vo);
	}

	/**
	 * 根据主键获取质检详情
	 *
	 * @param id 主键
	 * @return
	 */
	@GetMapping("/{id}")
	@HasPermission("admin_exam_detail")
	@Operation(summary = "详情", description = "根据主键获取质检详情")
	public R<TestInfoDto> getDetails(@PathVariable("id") Long id) {
		return testInfoService.getDetails(id);
	}

	/**
	 * 新增质检信息
	 * @param vo
	 * @return
	 */
	@SysLog("新增质检信息")
	@PostMapping
	@HasPermission("admin_exam_add")
	@Operation(summary = "新增", description = "新增质检信息")
	public R add(@RequestBody TestInfoVo vo){
		return testInfoService.addTestInfo(vo);
	}

	/**
	 * 新增质检信息
	 * @param vo
	 * @return
	 */
	@SysLog("编辑质检信息")
	@PutMapping
	@HasPermission("admin_exam_add")
	@Operation(summary = "编辑", description = "编辑质检信息")
	public R edit(@RequestBody TestInfoVo vo){
		return testInfoService.editTestInfo(vo);
	}

	/**
	 * 删除质检信息
	 * @param ids
	 * @return
	 */
	@SysLog("删除质检信息")
	@DeleteMapping
	@HasPermission("admin_exam_del")
	@Operation(summary = "删除", description = "删除质检信息")
	public R delete(@RequestBody Long[] ids){
		return testInfoService.deleteTestInfo(ids);
	}

	/**
	 * 查询质检商品列表
	 *
	 * @return
	 */
	@GetMapping("/goodsList")
	@Operation(summary = "查询质检商品列表", description = "查询质检商品列表")
	public R<List<ExamGoodsDto>> getExamGoods() {
		return testInfoService.getExamGoods();
	}

	/**
	 * 上传质检相关文件
	 * @param file 资源
	 * @return R(/ admin / bucketName / filename)
	 */
	@PostMapping(value = "/upload")
	@Operation(summary = "上传质检文件", description = "上传质检相关文件")
	public R upload(@RequestPart("file") MultipartFile file) {
		return testInfoService.uploadFile(file);
	}

	/**
	 * 获取文件
	 * @param bucket 桶名称
	 * @param fileName 文件空间/名称
	 * @param response
	 * @return
	 */
	@Inner(false)
	@GetMapping("/{bucket}/{fileName}")
	@Operation(summary = "获取上传的文件", description = "根据桶名称和文件名称获取上传的文件")
	public void file(@PathVariable(value = "bucket") String bucket, @PathVariable(value = "fileName") String fileName, HttpServletResponse response) {
		testInfoService.getFile(bucket, fileName, response);
	}
}
