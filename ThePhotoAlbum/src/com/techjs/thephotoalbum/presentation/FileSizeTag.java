package com.techjs.thephotoalbum.presentation;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FileSizeTag extends SimpleTagSupport {
	private Double size;
	
	public void setSize(Double size) {
		this.size = size;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if (size != null) {
			JspWriter out = getJspContext().getOut();
			
			if (size > 1024.00) {
				out.println(String.format("%.1f MB", size/1024 ));
			}
			else {
				out.println(String.format("%.1f KB", size ));
			}
		}
	}
	
	
}
