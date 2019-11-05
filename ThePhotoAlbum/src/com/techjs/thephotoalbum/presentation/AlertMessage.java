package com.techjs.thephotoalbum.presentation;

public class AlertMessage {
	private String message;
	private String type;
	private boolean closable;
	
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isClosable() {
		return closable;
	}
	public void setClosable(boolean closable) {
		this.closable = closable;
	}
	
	
}
