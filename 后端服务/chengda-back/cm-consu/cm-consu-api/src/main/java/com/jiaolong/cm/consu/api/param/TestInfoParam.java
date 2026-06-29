package com.jiaolong.cm.consu.api.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * desc: 质检传参
 * user: pan
 * date: 2024-08-21 15:03
 */
@Data
public class TestInfoParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 文件标题
	 */
	@Schema(description = "文件标题")
	private String fileTitle;

	/**
	 * 查询开始时间
	 */
	@Schema(description = "查询开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime startTime;

	/**
	 * 查询结束时间
	 */
	@Schema(description = "查询结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime endTime;
}
