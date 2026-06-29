package com.jiaolong.cm.admin.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiaolong.cm.admin.api.dto.ExamGoodsDto;
import com.jiaolong.cm.admin.api.entity.SysFile;
import com.jiaolong.cm.admin.api.vo.TestInfoVo;
import com.jiaolong.cm.admin.mapper.CmMerchantInfoMapper;
import com.jiaolong.cm.admin.mapper.CmProductMapper;
import com.jiaolong.cm.admin.mapper.CmTestInfoMapper;
import com.jiaolong.cm.admin.mapper.SysFileMapper;
import com.jiaolong.cm.admin.service.CmTestInfoService;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.exception.BusinessErrorMessage;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.file.core.FileProperties;
import com.jiaolong.cm.common.file.core.FileTemplate;
import com.jiaolong.cm.common.security.service.CmUser;
import com.jiaolong.cm.common.security.util.SecurityUtils;
import com.jiaolong.cm.consu.api.dto.ProductVerifiedDto;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.entity.CmTestInfo;
import com.jiaolong.cm.consu.api.param.TestInfoParam;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * desc: 质检服务实现
 * user: pan
 * date: 2024-09-24 09:44
 */
@Slf4j
@Service
public class CmTestInfoServiceImpl extends ServiceImpl<CmTestInfoMapper, CmTestInfo> implements CmTestInfoService {

	@Autowired
	private CmTestInfoMapper testInfoMapper;
	@Autowired
	private CmMerchantInfoMapper merchantInfoMapper;
	@Autowired
	private CmProductMapper productMapper;
	@Autowired
	private SysFileMapper fileMapper;

	@Autowired
	private FileTemplate fileTemplate;
	@Autowired
	private FileProperties properties;

	@Override
	public R<IPage<TestInfoDto>> getTestInfoPage(Page page, TestInfoParam vo) {
		IPage<TestInfoDto> pageList = testInfoMapper.selectListPage(page, vo);
		List<TestInfoDto> records = pageList.getRecords();
		for (TestInfoDto dto : records) {
			fillingRelationFiles(dto);
		}
		return R.ok(pageList);
	}


	@Override
	public R<TestInfoDto> getDetails(Long id) {
		TestInfoDto dto = testInfoMapper.getDtoById(id);
		fillingVerifiedProducts(dto);
		fillingMerchantNames(dto);
		fillingRelationFiles(dto);
		return R.ok(dto);
	}

	@Override
	public R addTestInfo(TestInfoVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		String productIds = vo.getTestProductIds();
		/* 组装数据 */
		CmTestInfo testInfo = new CmTestInfo();
		testInfo.setTestTime(vo.getTestTime());
		testInfo.setCreateBy(cmUser.getName());
		testInfo.setCreateTime(LocalDateTime.now());
		testInfo.setFileType(vo.getFileType());
		testInfo.setFileTitle(vo.getFileTitle());
		testInfo.setContent(vo.getContent());
		testInfo.setAgencyName(vo.getAgencyName());
		testInfo.setUpdateBy(cmUser.getName());
		testInfo.setUpdateTime(LocalDateTime.now());
		testInfo.setTestProductIds(productIds);
		testInfo.setRelationFileIds(vo.getRelationFileIds());
		testInfo.setRemarks(vo.getRemarks());
		testInfo.setDeleted(BusinessConstants.DELETED_NO);

		if (StringUtils.isNotBlank(productIds)) {
			String[] ids = productIds.split(StrUtil.COMMA);
			Set<Long> merchantIds = productMapper.getMerchantIdByProductIds(ids);
			StringBuffer sb = new StringBuffer();
			for (Long id : merchantIds) {
				sb.append(id).append(StrUtil.COMMA);
			}
			String testMerchantIds = sb.toString().substring(0, sb.toString().length() - 1);
			testInfo.setTestMerchantIds(testMerchantIds);
		}

		int result = testInfoMapper.insert(testInfo);
		if (result > 0) {
			return R.ok(testInfo);
		}
		return R.failed();
	}

	@Override
	public R editTestInfo(TestInfoVo vo) {
		CmUser cmUser = SecurityUtils.getUser();
		if (cmUser == null) {
			return R.failed(BusinessErrorMessage.USER_NOT_LOGIN);
		}

		Long id = vo.getId();
		if (id == null) {
			return R.failed(BusinessErrorMessage.PARAMETER_MISSING);
		}

		CmTestInfo testInfo = testInfoMapper.selectById(id);
		if (testInfo == null) {
			return R.failed(BusinessErrorMessage.MISSING_USERDATA);
		}

		/* 组装数据 */
		LocalDateTime testTime = vo.getTestTime();
		Integer fileType = vo.getFileType();
		String fileTitle = vo.getFileTitle();
		String content = vo.getContent();
		String agencyName = vo.getAgencyName();
		String productIds = vo.getTestProductIds();
		String relationFileIds = vo.getRelationFileIds();
		String remarks = vo.getRemarks();
		if (testTime != null) {
			testInfo.setTestTime(testTime);
		}
		if (fileType != null) {
			testInfo.setFileType(fileType);
		}
		if (StringUtils.isNotBlank(fileTitle)) {
			testInfo.setFileTitle(fileTitle);
		}
		if (StringUtils.isNotBlank(content)) {
			testInfo.setContent(content);
		}
		if (StringUtils.isNotBlank(agencyName)) {
			testInfo.setAgencyName(agencyName);
		}
		if (StringUtils.isNotBlank(relationFileIds)) {
			testInfo.setRelationFileIds(relationFileIds);
		}
		if (StringUtils.isNotBlank(productIds)) {
			testInfo.setTestProductIds(productIds);
		}
		if (StringUtils.isNotBlank(remarks)) {
			testInfo.setRemarks(remarks);
		}
		testInfo.setUpdateBy(cmUser.getName());
		testInfo.setUpdateTime(LocalDateTime.now());
		testInfo.setDeleted(BusinessConstants.DELETED_NO);
		// 处理商品所属商户
		if (StringUtils.isNotBlank(productIds)) {
			String[] ids = productIds.split(StrUtil.COMMA);
			Set<Long> merchantIds = productMapper.getMerchantIdByProductIds(ids);
			StringBuffer sb = new StringBuffer();
			for (Long merchantId : merchantIds) {
				sb.append(merchantId).append(StrUtil.COMMA);
			}
			String testMerchantIds = sb.toString().substring(0, sb.toString().length() - 1);
			testInfo.setTestMerchantIds(testMerchantIds);
		}
		/* 更新质检信息 */
		int result = testInfoMapper.updateById(testInfo);
		if (result > 0) {
			return R.ok(testInfo);
		}
		return R.failed();
	}

	@Override
	public R deleteTestInfo(Long[] ids) {
		int result = testInfoMapper.deleteTestInfoByIds(ids);
		if (result > 0) {
			return R.ok();
		}
		return R.failed();
	}

	@Override
	public R<List<ExamGoodsDto>> getExamGoods() {
		List<ExamGoodsDto> dtoList = productMapper.getExamGoodList();
		return R.ok(dtoList);
	}

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
		} catch (Exception e) {
			log.error("上传失败", e);
			return R.failed(e.getLocalizedMessage());
		}
		return R.ok(resultMap);
	}

	@Override
	public void getFile(String bucket, String fileName, HttpServletResponse response) {
		try (S3Object s3Object = fileTemplate.getObject(bucket, fileName)) {
			response.setContentType("application/octet-stream; charset=UTF-8");
			IoUtil.copy(s3Object.getObjectContent(), response.getOutputStream());
		} catch (Exception e) {
			log.error("文件读取异常: {}", e.getLocalizedMessage());
		}
	}

	/**
	 * 文件管理数据记录,收集管理追踪文件
	 *
	 * @param file     上传文件格式
	 * @param fileName 文件名
	 * @param userId   所属用户主键
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
		fileMapper.insert(sysFile);
		return sysFile;
	}

	/**
	 * 填充质检文件的关联文件列表
	 *
	 * @param dto
	 */
	private void fillingRelationFiles(TestInfoDto dto) {
		String relationFileIds = dto.getRelationFileIds();
		if (StringUtils.isNotBlank(relationFileIds)) {
			String[] fileListIds = relationFileIds.split(StrUtil.COMMA);
			List<SysFile> fileList = fileMapper.selectFileListByIds(fileListIds);
			dto.setFileList(fileList);
		}
	}

	/**
	 * 填充质检文件的关联商户名称列表
	 *
	 * @param dto
	 */
	private void fillingMerchantNames(TestInfoDto dto) {
		String merchantIds = dto.getTestMerchantIds();
		if (StringUtils.isNotBlank(merchantIds)) {
			String[] merchantIdList = merchantIds.split(StrUtil.COMMA);
			List<String> merchantNames = merchantInfoMapper.selectNameList(merchantIdList);
			dto.setMerchantNames(merchantNames);
		}
	}

	/**
	 * 填充质检文件的关联商品名称列表
	 *
	 * @param dto
	 */
	private void fillingVerifiedProducts(TestInfoDto dto) {
		String productIds = dto.getTestProductIds();
		if (StringUtils.isNotBlank(productIds)) {
			String[] productIdList = productIds.split(StrUtil.COMMA);
			List<ProductVerifiedDto> verifiedProducts = productMapper.selectVerifiedProductList(productIdList);
			dto.setVerifiedProduct(verifiedProducts);
		}
	}

}
