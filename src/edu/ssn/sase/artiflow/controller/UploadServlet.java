package edu.ssn.sase.artiflow.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import edu.ssn.sase.artiflow.functions.ReviewManager;

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
		HttpSession session = request.getSession();
		response.setContentType("text/html;charset=UTF-8");
		// Create path components to save the file
		final Part filePart = request.getPart("File");
		final String fileName = getFileName(filePart);

		InputStream input = filePart.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(input, 4096);
		String resource = getServletContext().getRealPath("UploadedFiles");
		System.out.println(getServletContext().getContextPath());
		File dir = new File(resource);
		if (!dir.exists())
			dir.mkdir();
		String reviewFolderName = "";

		ReviewManager revMgr = new ReviewManager("localhost", "artiflow");
		try {
			int nextReviewId = revMgr.getReviewId();
			reviewFolderName = "ReviewFolder" + nextReviewId;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		File reviewFolder = new File(resource + "\\" + reviewFolderName);
		if (!reviewFolder.exists()) {
			reviewFolder.mkdir();
		}
		File targetFile = new File(resource + "\\" + reviewFolderName + "\\"
				+ System.currentTimeMillis() + "_" + fileName);

		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(targetFile), 4096);
		int theChar;
		while ((theChar = bis.read()) != -1) {
			bos.write(theChar);
		}
		bos.close();
		bis.close();
		String filePath = (String) session.getAttribute("Upload-File");
		if (filePath == null || filePath.equals("")) {
			filePath = "UploadedFiles\\" + reviewFolderName + "\\"
					+ targetFile.getName();
		} else {
			filePath = filePath + ";;;" + "UploadedFiles\\" + reviewFolderName
					+ "\\" + targetFile.getName();
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
