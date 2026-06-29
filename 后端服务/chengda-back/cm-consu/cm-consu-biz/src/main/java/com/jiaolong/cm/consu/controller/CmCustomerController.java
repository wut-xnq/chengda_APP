package com.jiaolong.cm.consu.controller;

import com.jiaolong.cm.common.core.util.R;
import com.jiaolong.cm.common.log.annotation.SysLog;
import com.jiaolong.cm.common.security.annotation.HasPermission;
import com.jiaolong.cm.consu.api.dto.UserPersonalDto;
import com.jiaolong.cm.consu.api.dto.UserScoreDto;
import com.jiaolong.cm.consu.api.dto.UserVerifiedDto;
import com.jiaolong.cm.consu.api.param.CustomerPasswordVo;
import com.jiaolong.cm.consu.api.param.CustomerVo;
import com.jiaolong.cm.consu.api.param.TransScoreParam;
import com.jiaolong.cm.consu.service.CmCustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * desc: 用户管理
 * user: pan
 * date: 2024-08-23 16:46
 */
@RestController
@RequestMapping("/user")
@Tag(description = "user", name = "用户管理")
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class CmCustomerController {

	@Autowired
	private CmCustomerService customerService;

	/**
	 * 根据主键获取用户信息详情
	 *
	 * @param id 用户主键
	 * @return
	 */
	@GetMapping("/details/{id}")
	@HasPermission("consu_user_detail")
	@Operation(summary = "个人详情", description = "根据主键获取用户信息详情")
	public R<UserPersonalDto> getDetailsById(@PathVariable("id") Long id) {
		return customerService.getDetails(id);
	}

	/**
	 * 根据手机号获取好友信息详情
	 *
	 * @param phone 好友手机号
	 * @return
	 */
	@GetMapping("/details")
	@HasPermission("consu_user_detail")
	@Operation(summary = "好友详情", description = "根据手机号获取好友信息详情")
	public R<UserPersonalDto> getDetailsByPhone(@RequestParam("phone") String phone) {
		return customerService.getDetailsByPhone(phone);
	}

	/**
	 * 获取积分转换页面用户列表
	 *
	 * @param id 用户主键
	 * @return
	 */
	@GetMapping("/listForTranScore/{id}")
	@HasPermission("consu_trans_list")
	@Operation(summary = "积分转换用户列表", description = "获取积分转换页面用户列表")
	public R<List<UserScoreDto>> transScoreUserList(@PathVariable("id") Long id) {
		return customerService.getListForTranScore(id);
	}

	/**
	 * 发起积分转化
	 *
	 * @param ids 用户主键
	 * @return
	 */
	@SysLog("发起积分转换")
	@PutMapping("/tranScore")
	@HasPermission("consu_trans_req")
	@Operation(summary = "发起积分转化", description = "发起积分转化")
	public R reqTransScore(@RequestBody Long[] ids) {
		return customerService.reqTransScore(ids);
	}

	/**
	 * 处理积分转化
	 *
	 * @param param 传参
	 * @return
	 */
	@SysLog("处理积分转换")
	@PutMapping("/tranScore/handle")
	@HasPermission("consu_trans_res")
	@Operation(summary = "处理积分转化", description = "响应企业/邀请人积分转化")
	public R resTransScore(@Valid @RequestBody TransScoreParam param) {
		return customerService.resTransScore(param);
	}

	/**
	 * 查看用户账号认证结果
	 *
	 * @param userId 用户主键
	 * @return
	 */
	@GetMapping("/identResult/{userId}")
	@HasPermission("consu_ident_result")
	@Operation(summary = "账号认证结果", description = "查看用户账号认证结果")
	public R<UserVerifiedDto> getIdentResult(@PathVariable("userId") Long userId) {
		return customerService.getIdentResult(userId);
	}

	/**
	 * 员工申请企业认证
	 *
	 * @param merchantId 申请认证的企业主键
	 * @return
	 */
	@SysLog("用户申请企业认证")
	@PutMapping("/identApply")
	@HasPermission("consu_ident_apply")
	@Operation(summary = "申请企业认证", description = "员工申请企业认证")
	public R identApply(@RequestParam("nameLastChar") String nameLastChar, @RequestParam("merchantId") Long merchantId) {
		return customerService.identApply(nameLastChar, merchantId);
	}

	/**
	 * 修改个人信息
	 *
	 * @param vo 用户信息
	 * @return
	 */
	@SysLog("用户修改个人信息")
	@PutMapping
	@Operation(summary = "修改个人信息", description = "修改个人信息")
	public R editInfo(@RequestBody CustomerVo vo) {
		return customerService.editCustomerInfo(vo);
	}

	/**
	 * 修改个人密码
	 *
	 * @param vo 用户信息
	 * @return
	 */
	@SysLog("用户修改个人密码")
	@PutMapping("/changePwd")
	@Operation(summary = "修改个人密码", description = "修改个人密码")
	public R changePassword(@RequestBody CustomerPasswordVo vo) {
		return customerService.changePassword(vo);
	}
}
