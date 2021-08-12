package com.whoiszxl.constants;

/**
 * 采购单状态
 */
public class PurchaseOrderStatusConstants {

	/**
	 * 编辑中
	 */
	public static final Integer EDITING = 1;
	/**
	 * 待审核
	 */
	public static final Integer WAIT_FOR_APPROVE = 2;
	/**
	 * 已审核
	 */
	public static final Integer APPROVED = 3;
	/**
	 * 待入库
	 */
	public static final Integer WAIT_FOR_INBOUND = 4;
	/**
	 * 审核待入库
	 */
	public static final Integer WAIT_FOR_APPROVE_BY_INBOUND = 5;
	/**
	 * 已入库
	 */
	public static final Integer FINISHED_INBOUND = 6;
	/**
	 * 待结算
	 */
	public static final Integer WAIT_FOR_SETTLEMENT = 7;
	/**
	 * 已完成
	 */
	public static final Integer FINISHED = 8;
	
	private PurchaseOrderStatusConstants() {}
	
}
