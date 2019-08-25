package com.nfc.electronicseal.node;

public class VersionNode {
	private int id;
	private String instId;
	private String version;
	private String upgradeDetail;
	private int mobileType;
	private String name;
	private int status;
	private String downloadPath;
	private String created;
	private String updated;
	private int upgradeType;	//0:普通更新 1：强制更新
	private String instName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInstId() {
		return instId;
	}

	public void setInstId(String instId) {
		this.instId = instId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpgradeDetail() {
		return upgradeDetail;
	}

	public void setUpgradeDetail(String upgradeDetail) {
		this.upgradeDetail = upgradeDetail;
	}

	public int getMobileType() {
		return mobileType;
	}

	public void setMobileType(int mobileType) {
		this.mobileType = mobileType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public int getUpgradeType() {
		return upgradeType;
	}

	public void setUpgradeType(int upgradeType) {
		this.upgradeType = upgradeType;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}
}
