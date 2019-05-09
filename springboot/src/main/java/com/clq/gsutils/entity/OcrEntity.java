package com.clq.gsutils.entity;

public class OcrEntity {

	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 正反面类型 0:正面  1：反面
	 */
	private String backType;
	
	/**
	 * 图片路径
	 */
	private String file;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBackType() {
		return backType;
	}

	public void setBackType(String backType) {
		this.backType = backType;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
}
