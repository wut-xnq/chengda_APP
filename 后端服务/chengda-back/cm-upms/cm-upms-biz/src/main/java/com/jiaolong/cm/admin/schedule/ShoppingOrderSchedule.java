package com.jiaolong.cm.admin.schedule;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.jiaolong.cm.admin.mapper.CmOrderShoppingMapper;
import com.jiaolong.cm.admin.mapper.SysConfigMapper;
import com.jiaolong.cm.common.core.constant.BusinessConstants;
import com.jiaolong.cm.common.core.constant.CacheConstants;
import com.jiaolong.cm.merch.api.dto.SettlementOrderDto;
import com.jiaolong.cm.merch.api.entity.SysConfiguration;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

/**
 * desc: 购物订单定时任务
 * user: pan
 * date: 2024-11-13 11:19
 */
@Slf4j
@Component
public class ShoppingOrderSchedule {
//	@Value("${cm.alipay.rate}")
//	private String alipayRate;
//	@Value("${cm.wechatpay.rate}")
//	private String wechatpayRate;

	@Autowired
	private CmOrderShoppingMapper orderShoppingMapper;
	@Autowired
	private SysConfigMapper configMapper;

	private static BigDecimal hundred = new BigDecimal(BusinessConstants.ONE_HUNDRED);
	private static BigDecimal ten_thousand = new BigDecimal(BusinessConstants.TEN_THOUSAND);

	/**
	 * 购物订单的发货时间超过15天，但用户没有操作”确认收货“，由系统自动确认到货，并更新相关订单的结算金额
	 * <p>
	 * 1、查询商家发货超过15天，但未被客户确认收货的订单；
	 * 2、由系统自动确认，当前订单已经到货；
	 * 3、当前自动确认收货的订单，更新结算金额
	 * 4、结算金额 = 客户实付金额 * （1 - 第三方支付平台费率） * （1 - 本平台展区商品抽成）
	 * 5、本平台展区商品抽成，在国标区、正品区、优惠区的抽成各不相同，相关数据存放在配置表中
	 */
	@Async("taskExecutor")
	@Scheduled(cron = "0 0 * * * *")
	public void updateOrderStateAuto() {
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime offsetTime = LocalDateTimeUtil.offset(time, -15L, ChronoUnit.DAYS);
		List<SettlementOrderDto> dtoList = orderShoppingMapper.getUnreceivedOrders(offsetTime);
		log.info("=============" + time + "待处理数据共有" + dtoList.size() + "条=============");

		List<SysConfiguration> configList = configMapper.getList();
		SysConfiguration config = configList.get(0);
		for (SettlementOrderDto dto : dtoList) {
			/* 一、计算订单结算金额 */
			BigDecimal settlementAmount = calculation(dto, config);
			/* 二、更新购物订单数据（订单状态、结算金额） */
			Long orderId = dto.getOrderId();
			int update = orderShoppingMapper.updateOrderSettlementAmount(orderId, settlementAmount);

			/* 打印执行结果 */
			if (update <= 0) {
				String msg = String.format("更新订单数据失败。操作时间：%s。参数：orderId=%s，settlementAmount=%s", time, orderId, settlementAmount);
				log.error(msg);
			} else {
				String msg = String.format("更新订单数据成功。操作时间：%s。参数：orderId=%s，settlementAmount=%s", time, orderId, settlementAmount);
				log.info(msg);
			}
		}
	}

	/**
	 * 购物订单的发货并经过用户的”确认收货“操作后，时长超过7天，由系统自动更新相关订单的结算金额
	 * 1、查询被客户确认收货，而且更新时间超过7天的订单；
	 * 2、因为用户已经确认到货，由系统更新结算金额；
	 * 3、结算金额 = 客户实付金额 * （1 - 第三方支付平台费率） * （1 - 本平台展区商品抽成）
	 * 4、本平台展区商品抽成，在国标区、正品区、优惠区的抽成各不相同，相关数据存放在配置表中
	 */
	@Async("taskExecutor")
	@Scheduled(cron = "0 30 * * * *")
	public void updateOrderState() {
		LocalDateTime time = LocalDateTime.now();
		LocalDateTime offsetTime = LocalDateTimeUtil.offset(time, -7L, ChronoUnit.DAYS);
		List<SettlementOrderDto> dtoList = orderShoppingMapper.getCustomerReceivedOrders(offsetTime);
		log.info("=============" + time + "待处理数据共有" + dtoList.size() + "条=============");

		List<SysConfiguration> configList = configMapper.getList();
		SysConfiguration config = configList.get(0);
		for (SettlementOrderDto dto : dtoList) {
			/* 一、计算订单结算金额 */
			BigDecimal settlementAmount = calculation(dto, config);
			/* 二、更新购物订单数据（订单状态、结算金额） */
			Long orderId = dto.getOrderId();
			int update = orderShoppingMapper.updateOrderSettlementAmount(orderId, settlementAmount);

			/* 打印执行结果 */
			if (update <= 0) {
				String msg = String.format("更新订单数据失败。操作时间：%s。参数：orderId=%s，settlementAmount=%s", time, orderId, settlementAmount);
				log.error(msg);
			} else {
				String msg = String.format("更新订单数据成功。操作时间：%s。参数：orderId=%s，settlementAmount=%s", time, orderId, settlementAmount);
				log.info(msg);
			}
		}
	}

	/**
	 * 计算订单结算金额
	 *
	 * @param dto    订单基础数据
	 * @param config 平台配置
	 * @return
	 */
	private BigDecimal calculation(SettlementOrderDto dto, SysConfiguration config) {
		// 1、设定变量
		BigDecimal payPlatRateMulti100 = BigDecimal.ZERO;
		BigDecimal platRateMulti100 = BigDecimal.ZERO;

		// 2、获取第三方支付平台的费率 * 100
		int paymentType = dto.getPaymentType();
		if (paymentType == BusinessConstants.PAYMENT_ALIPAY) {
			payPlatRateMulti100 = new BigDecimal(0.6);
		} else if (paymentType == BusinessConstants.PAYMENT_WECHAT) {
			payPlatRateMulti100 = new BigDecimal(0.6);
		} else {
			payPlatRateMulti100 = BigDecimal.ZERO;
		}

		// 3、获取本平台的费率 * 100
		int shelveArea = dto.getShelveArea();
		if (shelveArea == BusinessConstants.GB_AREA) {
			Integer gbRate = config.getGbRate();
			platRateMulti100 = new BigDecimal(gbRate);
		} else if (shelveArea == BusinessConstants.ZP_AREA) {
			Integer zpRate = config.getZpRate();
			platRateMulti100 = new BigDecimal(zpRate);
		} else if (shelveArea == BusinessConstants.YH_AREA) {
			Integer yhRate = config.getYhRate();
			platRateMulti100 = new BigDecimal(yhRate);
		} else {
			platRateMulti100 = BigDecimal.ZERO;
		}

		// 4、计算最终的结算金额
		BigDecimal realPrice = dto.getRealPrice();
		BigDecimal rate1 = hundred.subtract(payPlatRateMulti100);
		BigDecimal rate2 = hundred.subtract(platRateMulti100);
		BigDecimal settlementAmount = realPrice.multiply(rate1).multiply(rate2).divide(ten_thousand, 2, RoundingMode.DOWN);
		return settlementAmount;
	}

}
