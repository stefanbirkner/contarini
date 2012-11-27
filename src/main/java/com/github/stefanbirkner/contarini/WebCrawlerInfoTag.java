package com.github.stefanbirkner.contarini;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class WebCrawlerInfoTag extends SimpleTagSupport {
  private static final WebCrawlerInfoRenderer RENDERER = new WebCrawlerInfoRenderer();
  private WebCrawlerInfo info;

  public void setInfo(WebCrawlerInfo info) {
    this.info = info;
  }

  @Override
  public void doTag() throws JspException, IOException {
    JspWriter out = getJspContext().getOut();
    RENDERER.writeTagsForInfoToWriter(info, out);
  }
}
