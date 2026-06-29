/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the cm4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.jiaolong.cm.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.api.entity.SysFile;
import com.jiaolong.cm.admin.mapper.SysFileMapper;
import com.jiaolong.cm.admin.service.SysFileService;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.file.core.FileProperties;
import com.jiaolong.cm.common.file.core.FileTemplate;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysFileServiceImpl extends ServiceImpl<SysFileMapper, SysFile> implements SysFileService {

	private final FileTemplate fileTemplate;

	private final FileProperties properties;

	/**
	 * 上传文件
	 * @param file
	 * @return
	 */
	@Override
	public R uploadFile(MultipartFile file) {
		CmUser user = SecurityUtils.getUser();
		if (user == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		String fileName = IdUtil.simpleUUID() + StrUtil.DOT + FileUtil.extName(file.getOriginalFilename());
		String bucketName = properties.getBucketName();
		String url = String.format(properties.getOss().getEndpoint() + "/%s/%s", bucketName, fileName);

		Map<String, String> resultMap = new HashMap<>(4);
		resultMap.put("bucketName", bucketName);
		resultMap.put("fileName", fileName);
		resultMap.put("url", url);

		try (InputStream inputStream = file.getInputStream()) {
			fileTemplate.putObject(bucketName, fileName, inputStream, file.getContentType());
			// 文件管理数据记录,收集管理追踪文件
			SysFile sysFile = fileLog(file, fileName, user.getId(), url);
			resultMap.put("fileId", String.valueOf(sysFile.getId()));
		}
		catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(resultMap);
	}

	/**
	 * 读取文件
	 * @param bucket
	 * @param fileName
	 * @param response
	 */
	@Override
	public void getFile(String bucket, String fileName, HttpServletResponse response) {
		try (S3Object s3Object = fileTemplate.getObject(bucket, fileName)) {
			response.setContentType("application/octet-stream; charset=UTF-8");
			IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
		}
		catch (Exception e) {
			log.error("文件读取异常: {}", e.getLocalizedMessage());
		}
	}

	/**
	 * 删除文件
	 * @param id
	 * @return
	 */
	@Override
	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteFile(Long id) {
		SysFile file = this.getById(id);
		if (Objects.isNull(file)) {
			return Boolean.FALSE;
		}
		fileTemplate.removeObject(properties.getBucketName(), file.getFileName());
		return this.removeById(id);
	}

	/**
	 * 文件管理数据记录,收集管理追踪文件
	 * @param file 上传文件格式
	 * @param fileName 文件名
	 */
	private SysFile fileLog(MultipartFile file, String fileName, Long userId, String url) {
		SysFile sysFile = new SysFile();
		sysFile.setFileName(fileName);
		sysFile.setOriginal(file.getOriginalFilename());
		sysFile.setFileSize(file.getSize());
		sysFile.setType(FileUtil.extName(file.getOriginalFilename()));
		sysFile.setBucketName(properties.getBucketName());
		sysFile.setUserId(userId);
		sysFile.setUrl(url);
		this.save(sysFile);
		return sysFile;
	}

}
