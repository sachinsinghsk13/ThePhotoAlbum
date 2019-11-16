package com.techjs.thephotoalbum.models;

import java.util.Date;

public class Album {
	private Long id;
	private Long userId;
	private String title;
	private String description;
	private Date createTime;
	private Date lastPhotoUploadTime;
	transient private byte[] albumCover;
	
	public Album() {
		super();
		this.albumCover = "NO ALBUM COVER".getBytes();
	}
	
	
	public Album(Long id, Long userId, String title, String description, Date createTime) {
		super();
		this.id = id;
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.createTime = createTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastPhotoUploadTime() {
		return lastPhotoUploadTime;
	}
	public void setLastPhotoUploadTime(Date lastPhotoUploadTime) {
		this.lastPhotoUploadTime = lastPhotoUploadTime;
	}
	public byte[] getAlbumCover() {
		return albumCover;
	}
	public void setAlbumCover(byte[] albumCover) {
		this.albumCover = albumCover;
	}


	@Override
	public String toString() {
		return "Album [id=" + id + ", userId=" + userId + ", title=" + title + ", description=" + description
				+ ", createTime=" + createTime + ", lastPhotoUploadTime=" + lastPhotoUploadTime + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Album) {
			Album album = (Album) obj;
			return (album.getId() == this.getId()) ? true : false;
		}
		return false;
	}
		
}
