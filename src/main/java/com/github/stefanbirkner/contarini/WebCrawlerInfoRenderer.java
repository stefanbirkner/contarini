package com.github.stefanbirkner.contarini;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static java.util.Arrays.asList;

public class WebCrawlerInfoRenderer {
    private static final List<Replacement> REPLACEMENTS = asList(new Replacement("&", "&amp;"), new Replacement("<",
            "&lt;"), new Replacement(">", "&gt;"), new Replacement("\"", "&quot;"), new Replacement("'", "&apos;"));

    public void writeTagsForInfoToWriter(WebCrawlerInfo info, Writer w) throws IOException {
        if (info.getCanonical() != null)
            writeCanonicalToWriter(info.getCanonical(), w);
        if (!info.getAdvices().isEmpty())
            writeAdvicesToWriter(info.getAdvices(), w);
        writeAlternatesToWriter(info.getAlternates(), w);
        writeMetaTagToWriterIfContentExists("description", info.getDescription(), w);
        writeMetaTagToWriterIfContentExists("keywords", info.getKeywords(), w);
    }

    private void writeCanonicalToWriter(String canonical, Writer w) throws IOException {
        w.write("<link");
        writeAttribute("rel", "canonical", w);
        writeAttribute("href", canonical, w);
        w.write("/>");
    }

    private void writeAdvicesToWriter(List<WebCrawlerAdvice> advices, Writer w) throws IOException {
        String content = join(advices);
        writeMetaTagToWriter("robots", content, w);
    }

    private String join(List<WebCrawlerAdvice> advices) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (WebCrawlerAdvice advice : advices) {
            if (!first)
                sb.append(", ");
            sb.append(advice.getLabel());
            first = false;
        }
        return sb.toString();
    }

    private void writeAlternatesToWriter(List<Alternate> alternates, Writer w) throws IOException {
        for (Alternate alternate : alternates)
            writeAlternateToWriter(alternate, w);
    }

    private void writeAlternateToWriter(Alternate alternate, Writer w) throws IOException {
        w.write("<link");
        writeAttribute("rel", "alternate", w);
        writeAttributeIfValueExists("hreflang", alternate.language, w);
        writeAttribute("href", alternate.href, w);
        w.write("/>");
    }

    private void writeMetaTagToWriterIfContentExists(String name, String content, Writer w) throws IOException {
        if (content != null)
            writeMetaTagToWriter(name, content, w);
    }

    private void writeMetaTagToWriter(String name, String content, Writer w) throws IOException {
        w.write("<meta");
        writeAttribute("name", name, w);
        writeAttribute("content", escape(content), w);
        w.write("/>");
    }

    private String escape(String content) {
        for (Replacement replacement : REPLACEMENTS)
            content = content.replace(replacement.character, replacement.escapeSequence);
        return content;
    }

    private void writeAttributeIfValueExists(String name, String value, Writer w) throws IOException {
        if (value != null)
            writeAttribute(name, value, w);
    }

    private void writeAttribute(String name, String value, Writer w) throws IOException {
        w.write(" ");
        w.write(name);
        w.write("=\"");
        w.write(value);
        w.write("\"");
    }

    private static class Replacement {
        final String character;
        final String escapeSequence;

        public Replacement(String character, String escapeSequence) {
            this.character = character;
            this.escapeSequence = escapeSequence;
        }
    }
}
