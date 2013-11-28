package edu.ssn.sase.artiflow.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


/**
 * Servlet implementation class UploadServlet
 */

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public UploadServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		// Create path components to save the file
		final Part filePart = request.getPart("File");
		final String fileName = getFileName(filePart);

		InputStream input = filePart.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(input, 4096);
		File dir = new File("UploadedFiles");
		if(!dir.exists())
			dir.mkdir();
		File targetFile = new File(dir.getAbsolutePath()+"\\"+ System.currentTimeMillis()
				+ "_" + fileName);
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(targetFile), 4096);
		int theChar;
		while ((theChar = bis.read()) != -1) {
			bos.write(theChar);
		}
		bos.close();
		bis.close();
		HttpSession session = request.getSession();
		String filePath = (String) session.getAttribute("Upload-File");
		if(filePath == null || filePath.equals("")) {
			filePath = targetFile.getAbsolutePath();
		} else {
			filePath = filePath +";;;" + targetFile.getAbsolutePath();
		}
		session.setAttribute("Upload-File", filePath);
	}

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}

}
