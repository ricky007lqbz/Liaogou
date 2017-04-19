package com.common.bean;

import java.io.Serializable;

/**
 * @author Administrator
 *
 * @param <T>
 * 
 *            data 泛型
 */
public class C_DataWrapper<T> extends C_Wrapper implements Serializable {
	private T data;
	
	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "C_DataWrapper [data=" + data + ", pageNo=" + pageNo
				+ ", pageSize=" + pageSize + ", totals=" + totals
				+ ", totalNo=" + totalNo + ", code=" + code + ", message="
				+ message + ", exp=" + exp + ", round_id=" + round_id + "]";
	}
}
