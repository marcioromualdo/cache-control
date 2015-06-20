package com.cache.control;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class CacheControlCssTag extends TagSupport {
    private static final long serialVersionUID = 1L;
	private String rel = "stylesheet";
	private String type = "text/css";
	private String media = "all";
	private String href = null;

	public void setRel(String rel) {
	    this.rel = rel;
	}

	public void setType(String type) {
	    this.type = type;
	}

	public void setHref(String href) {
	    this.href = href;
	}
	 
	public void setMedia(String media) {
	    this.media = media;
	}
	
    public int doStartTag() throws JspException {
        try {
        	String encodePath = CacheControlUtils.getEncodePath(pageContext, href);

        	pageContext.getOut().println("<link rel='" + rel + "' type='" + type + "' href='" + encodePath + "'"
        			+ " media='" + media + "'>");

        } catch (Exception ex) {
            CacheControlUtils.error("Exception in CacheControlCssTag.doStartTag:"
                    + " href [" + href + "] rel [" + rel + "] type [" + type + "] media [" + media + "]: " 
                    + ex.toString());
        }

        return SKIP_BODY;
    }
   
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}