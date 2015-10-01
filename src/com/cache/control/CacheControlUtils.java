package com.cache.control;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

public final class CacheControlUtils {
	public static final String GET_VAR_KEY = "cache-id";
	public static final int FILTER_KEY_SIZE = 39;
	private static final int JS_EXTENSION_SIZE = 3;
	private static final int CCS_EXTENSION_SIZE = 4;

	private static Map encodedFiles = new HashMap();
    private static boolean debugCode = false;

	private CacheControlUtils() {
	}

	public static Map getEncodedFiles() {
        return encodedFiles;
    }

    public static void setEncodedFiles(Map encodedFiles) {
        CacheControlUtils.encodedFiles = encodedFiles;
    }

    public static boolean isDebugCode() {
        return debugCode;
    }

    public static void setDebugCode(boolean debugCode) {
        CacheControlUtils.debugCode = debugCode;
    }

    public static String getEncodePath(PageContext pageContext, String path) {
		ServletContext servletContext = pageContext.getServletContext();
		
    	// get encodeFile from cache
		String encodeFile = (String) encodedFiles.get(path);

		if (encodeFile == null) {
		    encodeFile = getEncodeFile(servletContext, path);

		    // put in cache
		    if (encodeFile != null) {
		        CacheControlUtils.encodedFiles.put(path, encodeFile);    
		    }
		}

        try {
            encodeFile = URLEncoder.encode(encodeFile, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            error("getEncodePath: URLEncoder.encode() encoding exception", e);
        }
        
        // create encodePath
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String encodePath = request.getContextPath() + path + "?" + GET_VAR_KEY + "=" + encodeFile;
        debug("getEncodePath: encodePath [" + encodePath + "]");

    	return encodePath;
    }

    public static String getEncodeFile(ServletContext servletContext, String path) {
        String encodeFile = null;

        if (path == null || (!path.substring(path.length() - JS_EXTENSION_SIZE).equalsIgnoreCase(".js") 
                && !path.substring(path.length() - CCS_EXTENSION_SIZE).equalsIgnoreCase(".css"))) {
            return null;
        }

        try {
            // create encodeFile
            MessageDigest md = MessageDigest.getInstance("MD5");
            InputStream io = servletContext.getResourceAsStream(path);
            BufferedInputStream in = new BufferedInputStream(io);
            int theByte = 0;
            while ((theByte = in.read()) != -1) {
                md.update((byte) theByte);
            }
            in.close();
            byte[] theDigest = md.digest();

            encodeFile = new sun.misc.BASE64Encoder().encode(theDigest);
            debug("getEncodeFile: encodeFile [" + encodeFile + "]");
        } catch (Exception e) {
            error("Exception in CacheControlUtils.getEncodeFile: path [" + path + "] " + e.getMessage());
        }

        return encodeFile;
    }
    
	public static void debug(String msg) {
		if (debugCode) {
			logSystem("CacheControlUtils.debug: " + msg);
		}
	}

    public static void warn(String msg) {
        logSystem("CacheControlUtils.warn: " + msg);
    }
	
    public static void error(String msg) {
        logSystem("CacheControlUtils.error: " + msg);
    }
    public static void error(String msg, Throwable e) {
        logSystem("CacheControlUtils.error: " + msg, e);
    }

    private static void logSystem(String msg) {
        System.out.println(msg);
    }

    private static void logSystem(String msg, Throwable e) {
        System.out.println(msg);
        e.printStackTrace();
    }
}