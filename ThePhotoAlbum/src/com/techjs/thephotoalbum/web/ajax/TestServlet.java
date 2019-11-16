package com.techjs.thephotoalbum.web.ajax;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/App/TestServlet")
@MultipartConfig
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part part = request.getPart("macos.jpg");
		InputStream inputStream = part.getInputStream();
		FileOutputStream fileOutputStream = new FileOutputStream("/home/sachinsingh/Workspace/input.jpg");
		fileOutputStream.write(inputStream.readAllBytes());
		
	}

}
