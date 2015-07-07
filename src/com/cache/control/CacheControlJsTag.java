package com.cache.control;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CacheControlJsTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    private String type = "text/javascript";
	private String charset = "UTF-8";
	private String src;

    public void setType(String type) {
        this.type = type;
    }
    
	public void setSrc(String src) {
	    this.src = src;
	}

	public void setCharset(String charset) {
	    this.charset = charset;
	}

	public int doStartTag() throws JspException {
        try {
        	String encodePath = CacheControlUtils.getEncodePath(pageContext, src);

        	pageContext.getOut().println("<script type='" + type + "' src='" + encodePath + "'"
        			+ " charset='" + charset + "'></script>");

        } catch (Exception ex) {
            CacheControlUtils.error("Exception in CacheControlJsTag.doStartTag:"
                    + " src [" + src + "] type [" + type + "] charset [" + charset + "]: " + ex.toString());
        }

        return SKIP_BODY;
    }
   
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}