package com.common.bean;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         抽象公共相应实体
 */
public abstract class C_Wrapper implements Serializable {
	protected int pageNo; // 当前页
	protected int pageSize;// 单页数据量
	protected int totals;// 数据总数
	protected int totalNo; // 页面总数
	protected long timestamp; //时间戳

	protected int code; // 返回码 0 (正常)
	protected String message; // 返回信息
	protected C_Exp exp; // 操作经验

	protected String round_id; // 赛事轮次ID;特殊字段。

	public String getRound_id() {
		return round_id;
	}

	public void setRound_id(String round_id) {
		this.round_id = round_id;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotals() {
		return totals;
	}

	public void setTotals(int totals) {
		this.totals = totals;
	}

	public int getTotalNo() {
		return totalNo;
	}

	public void setTotalNo(int totalNo) {
		this.totalNo = totalNo;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public C_Exp getExp() {
		return exp;
	}

	public void setExp(C_Exp exp) {
		this.exp = exp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}



	/* 用于标识网络请求是否缓存的flag */
}
