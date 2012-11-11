package com.angelhack.wuw.model;

import com.restfb.Facebook;

public class StatusMessage {

	@Facebook("status_id")
	private String statusId;

	@Facebook("message")
	private String message;

	@Facebook("time")
	private long time;

	@Facebook("uid")
	private String uid;

	@Facebook("parentUid")
	private String parentUid;

	public String getParentUid() {
		return parentUid;
	}

	public void setParentUid(String parentUid) {
		this.parentUid = parentUid;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getStatusId() {
		return this.statusId;
	}

	public void setStatusId(String statusId) {
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
		return "StatusMessage [statusId=" + statusId + ", message=" + message
				+ ", time=" + time + ", uid=" + uid + ", parentUid="
				+ parentUid + "]";
	}

}
