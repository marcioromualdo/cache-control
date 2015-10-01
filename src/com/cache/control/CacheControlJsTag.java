package com.cache.control;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class CacheControlJsTag extends TagSupport {
    private static final long serialVersionUID = 1L;

    private String type = "text/javascript";
	private String charset;
	private String src;
	private String defer;
	private String async;
	private String crossorigin;

    public void setType(String type) {
        this.type = type;
    }
    
	public void setSrc(String src) {
	    this.src = src;
	}

	public void setCharset(String charset) {
	    this.charset = charset;
	}
	
	public void setDefer(String defer) {
	    this.defer = defer;
	}

    public void setAsync(String async) {
        this.async = async;
    }

    public void setCrossorigin(String crossorigin) {
        this.crossorigin = crossorigin;
    }

	public int doStartTag() throws JspException {
        try {
        	String encodePath = CacheControlUtils.getEncodePath(pageContext, src);

        	JspWriter out = pageContext.getOut(); 

        	out.print("<script type='" + type + "' src='" + encodePath + "'");

        	if (charset != null) {
        	    out.print(" charset='" + charset + "'");
        	}

            if (crossorigin != null) {
                out.print(" crossorigin='" + crossorigin + "'");
            }

        	if ("true".equals(defer)) {
        	    out.print(" defer"); // HTML 4.01 and HTML5
        	} else if ("defer".equals(defer)) {
        	    out.print(" defer='defer'"); // XHTML
        	}

            if ("true".equals(async)) {
                out.print(" async"); // HTML 4.01 and HTML5
            } else if ("async".equals(async)) {
                out.print(" async='async'"); // XHTML
            }

        	out.println("></script>");

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