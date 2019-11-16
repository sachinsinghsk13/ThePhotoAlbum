package com.techjs.thephotoalbum.models;

public class JsPhotoModel {
	private String filename;
	private String title;
	private String description;
	private Double size;
	private Integer width;
	private Integer height;
	private String quality;
	private String orientation;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getSize() {
		return size;
	}
	public void setSize(Double size) {
		this.size = size;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public String getQuality() {
		return quality;
	}
	public void setQuality(String quality) {
		this.quality = quality;
	}
	public String getOrientation() {
		return orientation;
	}
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	@Override
	public String toString() {
		return "JsPhotoModel [filename=" + filename + ", title=" + title + ", description=" + description + ", size="
				+ size + ", width=" + width + ", height=" + height + ", quality=" + quality + ", orientation="
				+ orientation + "]";
	}
	
}
