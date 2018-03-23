package org.rg.drip.utils;

/**
 * Author : Tank
 * Time : 23/03/2018
 */
public class TipMessage {

	private boolean state;      // 结果
	private String message;     // 信息

	public TipMessage(boolean state) {
		this.state = state;
		this.message = null;
	}

	/**
	 * false 的情况下输入错误信息
	 */
	public TipMessage(String message) {
		this.state = false;
		this.message = message;
	}

	public TipMessage(boolean state, String message) {
		this.state = state;
		this.message = message;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
