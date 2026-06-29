package com.jiaolong.cm.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiaolong.cm.admin.api.dto.ExamGoodsDto;
import com.jiaolong.cm.admin.api.vo.TestInfoVo;
import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.consu.api.dto.TestInfoDto;
import com.jiaolong.cm.consu.api.entity.CmTestInfo;
import com.jiaolong.cm.consu.api.param.TestInfoParam;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * desc: 质检服务接口
 * user: pan
 * date: 2024-09-24 09:44
 */
public interface CmTestInfoService extends IService<CmTestInfo> {
	R<IPage<TestInfoDto>> getTestInfoPage(Page page, TestInfoParam vo);

	R<TestInfoDto> getDetails(Long id);

	R addTestInfo(TestInfoVo vo);

	R deleteTestInfo(Long[] ids);

    R uploadFile(MultipartFile file);

	void getFile(String bucket, String fileName, HttpServletResponse response);

	R<List<ExamGoodsDto>> getExamGoods();

	R editTestInfo(TestInfoVo vo);
}
