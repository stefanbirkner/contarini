package com.github.stefanbirkner.contarini;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class WebCrawlerInfoRenderer {
  public void writeTagsForInfoToWriter(WebCrawlerInfo info, Writer w) throws IOException {
    if (info.canonical != null)
      writeCanonicalToWriter(info.canonical, w);
    writeAlternatesToWriter(info.alternates, w);
    writeMetaTagToWriterIfContentExists("description", info.description, w);
    writeMetaTagToWriterIfContentExists("keywords", info.keywords, w);
  }

  private void writeCanonicalToWriter(String canonical, Writer w) throws IOException {
    w.write("<link rel=\"canonical\" href=\"");
    w.write(canonical);
    w.write("\"/>");
  }

  private void writeAlternatesToWriter(List<Alternate> alternates, Writer w) throws IOException {
    for (Alternate alternate : alternates)
      writeAlternateToWriter(alternate, w);
  }

  private void writeAlternateToWriter(Alternate alternate, Writer w) throws IOException {
    w.write("<link rel=\"alternate\" hreflang=\"");
    w.write(alternate.language);
    w.write("\" href=\"");
    w.write(alternate.href);
    w.write("\"/>");
  }

  private void writeMetaTagToWriterIfContentExists(String name, String content, Writer w) throws IOException {
    if (content != null)
      writeMetaTagToWriter(name, content, w);
  }

  private void writeMetaTagToWriter(String name, String content, Writer w) throws IOException {
    w.write("<meta name=\"");
    w.write(name);
    w.write("\" content=\"");
    w.write(content);
    w.write("\"/>");
  }
}
