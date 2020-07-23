package cn.sm.util;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class util {
	private util() {}

	
	public static String getFileContent(String filePath){
		String readContent = "no file";
		try{
		File file = new File(filePath);
		FileReader in = new FileReader(file);
		BufferedReader inTwo=new BufferedReader(in);
		StringBuffer stringbuff = new StringBuffer();
		String s = null;
		while((s=inTwo.readLine())!=null){
			byte b[] = s.getBytes();
			s = new String(b);
			stringbuff.append("\n"+s);
		}
		readContent = new String(stringbuff);
		in.close();
		}catch(Exception e){
			
		}
		return readContent;
	}

	public int UploadFile(MultipartFile uploadFile, String filePath) {
		if(uploadFile.isEmpty()) {
			return 0;
		}
		String result = "";
		InputStream fis = null;
		OutputStream outputStream = null;
		try {
			fis = uploadFile.getInputStream();
			outputStream = new FileOutputStream(filePath + uploadFile.getOriginalFilename());
			IOUtils.copy(fis,outputStream);
			//File dest = new File(filePath + uploadFile.getOriginalFilename());
			//file.transferTo(dest); //与上述相同功能
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return -1;
	}

	public void downloadFileAction(HttpServletRequest request, HttpServletResponse response) {

		response.setCharacterEncoding(request.getCharacterEncoding());
		response.setContentType("application/octet-stream");
		FileInputStream fis = null;
		try {
			File file = new File("G:\\config.ini");
			fis = new FileInputStream(file);
			response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
			IOUtils.copy(fis,response.getOutputStream());
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static String stringToMD5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("not found md5 algorithm");
		}
		String md5code = new BigInteger(1, secretBytes).toString(16);
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}
	public static boolean IsStrEmpty(String str){
		str = str.trim();
		if(str.length()<1 || str==null){
			return true;
		}
		return false;
	}

	public static String StringToHash(String str) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.update(str.getBytes());
			byte[] src = digest.digest();
			StringBuilder stringBuilder = new StringBuilder();
			// 字节数组转16进制字符串
			for (byte aSrc : src) {
				String s = Integer.toHexString(aSrc & 0xFF);
				if (s.length() < 2) {
					stringBuilder.append('0');
				}
				stringBuilder.append(s);
			}
			return stringBuilder.toString();
		} catch (NoSuchAlgorithmException ignore) {
		}
		return null;
	}

	public static boolean CheckSignState(HttpServletRequest request) {

		 HttpSession session = request.getSession();
		boolean isLogin = false;
		if(session.getAttribute("uid") != null && !"".equals(session.getAttribute("uid"))) {
			if(session.getAttribute("username") != null && !"".equals(session.getAttribute("username"))) {
				if(session.getAttribute("groups") != null && !"".equals(session.getAttribute("groups"))) {
					isLogin = true;				
				}
			}
		}
		return isLogin;
	}

	public static boolean isNumeric(final String str) {
		// null or empty
		if (str == null || str.length() == 0) {
			return false;
		}

		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			try {
				Double.parseDouble(str);
				return true;
			} catch (NumberFormatException ex) {
				try {
					Float.parseFloat(str);
					return true;
				} catch (NumberFormatException exx) {
					return false;
				}
			}
		}
	}
	
}
