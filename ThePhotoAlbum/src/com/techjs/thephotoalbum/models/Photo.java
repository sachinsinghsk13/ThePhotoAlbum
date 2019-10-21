package com.techjs.thephotoalbum.models;

import java.util.Date;

import com.techjs.thephotoalbum.utils.ImageQuality;
import com.techjs.thephotoalbum.utils.Orientation;

public class Photo {
	private Long id;
	private Long albumId;
	private Long userId;
	private String title;
	private String description;
	private Date uploadDate;
	private byte[] binaryData;
	private byte[] thumbBinaryData;
	private Integer height;
	private Integer width;
	private Orientation orientation;
	private ImageQuality quality;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Date getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	public byte[] getBinaryData() {
		return binaryData;
	}
	public void setBinaryData(byte[] binaryData) {
		this.binaryData = binaryData;
	}
	public byte[] getThumbBinaryData() {
		return thumbBinaryData;
	}
	public void setThumbBinaryData(byte[] thumbBinaryData) {
		this.thumbBinaryData = thumbBinaryData;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Orientation getOrientation() {
		return orientation;
	}
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	public ImageQuality getQuality() {
		return quality;
	}
	public void setQuality(ImageQuality quality) {
		this.quality = quality;
	}
	
	@Override
	public String toString() {
		return "Photo [id=" + id + ", albumId=" + albumId + ", userId=" + userId + ", title=" + title + ", description="
				+ description + ", uploadDate=" + uploadDate + ", height=" + height + ", width=" + width
				+ ", orientation=" + orientation + "]";
	}	
}
