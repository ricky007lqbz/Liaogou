package com.common.bean;

import java.io.Serializable;

/**
 * @author Administrator
 * 
 *         积分+经验
 */
public class C_Exp implements Serializable {

	private int exp; // 经验值
	private int gold; // 积分值
	private int id;

	private int changeGold;//
	private int changeExp;

	public int getChangeGold() {
		return changeGold;
	}

	public void setChangeGold(int changeGold) {
		this.changeGold = changeGold;
	}

	public int getChangeExp() {
		return changeExp;
	}

	public void setChangeExp(int changeExp) {
		this.changeExp = changeExp;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "C_Exp [exp=" + exp + ", gold=" + gold + ", id=" + id
				+ ", changeGold=" + changeGold + ", changeExp=" + changeExp
				+ "]";
	}
}
