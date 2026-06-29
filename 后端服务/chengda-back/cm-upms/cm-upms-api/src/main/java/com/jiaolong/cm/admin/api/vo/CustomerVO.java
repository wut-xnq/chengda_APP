package com.jiaolong.cm.admin.api.vo;

import com.jiaolong.cm.admin.api.entity.SysPost;
import com.jiaolong.cm.admin.api.entity.SysRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * desc: 前端商户客户展示对象
 * user: pan
 * date: 2024-09-14 16:57
 */
@Data
@Schema(description = "前端商户客户展示对象")
public class CustomerVO implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@Schema(description = "主键")
	private Long userId;

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;

	/**
	 * 创建时间
	 */
	@Schema(description = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 修改时间
	 */
	@Schema(description = "修改时间")
	private LocalDateTime updateTime;

	/**
	 * 0-正常，1-删除
	 */
	@Schema(description = "删除标记,1:已删除,0:正常")
	private String delFlag;

	/**
	 * 锁定标记
	 */
	@Schema(description = "锁定标记,0:正常,9:已锁定")
	private String lockFlag;

	/**
	 * 手机号
	 */
	@Schema(description = "手机号")
	private String phone;

	/**
	 * 头像
	 */
	@Schema(description = "头像")
	private String avatar;

	/**
	 * 部门ID
	 */
	@Schema(description = "所属部门")
	private Long deptId;

	/**
	 * 部门名称
	 */
	@Schema(description = "所属部门名称")
	private String deptName;

	/**
	 * 角色列表
	 */
	@Schema(description = "拥有的角色列表")
	private List<SysRole> roleList;

	/**
	 * 岗位列表
	 */
	private List<SysPost> postList;

	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickname;

	/**
	 * 真实姓名
	 */
	@Schema(description = "真实姓名")
	private String name;

	/**
	 * 邮箱
	 */
	@Schema(description = "邮箱")
	private String email;

	/**
	 * 性别;（F-女，M-男）
	 */
	@Schema(description = "性别（F-女，M-男）")
	private String gender;

	/**
	 * 生日
	 */
	@Schema(description = "生日")
	private LocalDateTime birthday;

	/**
	 * 爱好
	 */
	@Schema(description = "爱好")
	private String hobby;

	/**
	 * 专业
	 */
	@Schema(description = "专业")
	private String major;

	/**
	 * 用户角色主键
	 */
	@Schema(description = "用户角色主键")
	private Long roleId;

	/**
	 * 所属商户单位主键
	 */
	@Schema(description = "所属商户单位主键")
	private Long merchantId;

	/**
	 * 商户名称
	 */
	@Schema(description = "商户名称")
	private String merchantName;

}
