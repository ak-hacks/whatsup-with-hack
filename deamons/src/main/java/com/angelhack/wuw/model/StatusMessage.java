package com.angelhack.wuw.model;

import com.restfb.Facebook;

public class StatusMessage {

	@Facebook("status_id")
	private int statusId;

	@Facebook("message")
	private String message;

	@Facebook("time")
	private long time;

	public int getStatusId() {
		return this.statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "StatusMessage [statusId=" + this.statusId + ", message="
				+ this.message + ", time=" + this.time + "]";
	}

}
