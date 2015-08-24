package com.tju;

public class WeiboItem {
	private String uid;
	private String cid;
	private String pDate;
	private Integer forwardTimes;
	private Integer reviewTimes;
	private Integer niceTimes;
	private String content;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getpDate() {
		return pDate;
	}

	public void setpDate(String pDate) {
		this.pDate = pDate;
	}

	public Integer getForwardTimes() {
		return forwardTimes;
	}

	public void setForwardTimes(Integer forwardTimes) {
		this.forwardTimes = forwardTimes;
	}

	public Integer getReviewTimes() {
		return reviewTimes;
	}

	public void setReviewTimes(Integer reviewTimes) {
		this.reviewTimes = reviewTimes;
	}

	public Integer getNiceTimes() {
		return niceTimes;
	}

	public void setNiceTimes(Integer niceTimes) {
		this.niceTimes = niceTimes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public WeiboItem() {
	}

	public WeiboItem(String line) {
		String s[] = line.split("\t");
		if (s.length == 7) {
			this.uid = s[0];
			this.cid = s[1];
			this.pDate = s[2];
			this.forwardTimes = Integer.valueOf(s[3]);
			this.reviewTimes = Integer.valueOf(s[4]);
			this.niceTimes = Integer.valueOf(s[5]);
			this.content = s[6];
		}
		else if(s.length==4){
			this.uid = s[0];
			this.cid = s[1];
			this.pDate = s[2];
			this.forwardTimes = 0;
			this.reviewTimes = 0;
			this.niceTimes = 0;
			this.content = s[3];
		}
	}
}
