package com.github.stefanbirkner.contarini;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static java.util.Arrays.asList;

public class WebCrawlerInfoRenderer {

    public void writeTagsForInfoToWriter(WebCrawlerInfo info, Writer w) throws IOException {
        TagWriter tagWriter = new TagWriter(w);
        writeTagsForInfoToTagWriter(info, tagWriter);
    }

    private void writeTagsForInfoToTagWriter(WebCrawlerInfo info, TagWriter w) throws IOException {
        if (info.getCanonical() != null)
            writeCanonicalToWriter(info.getCanonical(), w);
        if (!info.getAdvices().isEmpty())
            writeAdvicesToWriter(info.getAdvices(), w);
        writeAlternatesToWriter(info.getAlternates(), w);
        writeMetaTagToWriterIfContentExists("description", info.getDescription(), w);
        writeMetaTagToWriterIfContentExists("keywords", info.getKeywords(), w);
    }

    private void writeCanonicalToWriter(String canonical, TagWriter w) throws IOException {
        w.startTag("link");
        w.writeAttribute("rel", "canonical");
        w.writeAttribute("href", canonical);
        w.closeTag();
    }

    private void writeAdvicesToWriter(List<WebCrawlerAdvice> advices, TagWriter w) throws IOException {
        w.writeMetaTag("robots", join(advices));
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

    private void writeAlternatesToWriter(List<Alternate> alternates, TagWriter w) throws IOException {
        for (Alternate alternate : alternates)
            writeAlternateToWriter(alternate, w);
    }

    private void writeAlternateToWriter(Alternate alternate, TagWriter w) throws IOException {
        w.startTag("link");
        w.writeAttribute("rel", "alternate");
        w.writeAttributeIfValueExists("hreflang", alternate.language);
        w.writeAttribute("href", alternate.href);
        w.closeTag();
    }

    private void writeMetaTagToWriterIfContentExists(String name, String content, TagWriter w) throws IOException {
        if (content != null)
            w.writeMetaTag(name, content);
    }

    private static class TagWriter {
        static final List<Replacement> REPLACEMENTS = asList(new Replacement("&", "&amp;"), new Replacement("<",
            "&lt;"), new Replacement(">", "&gt;"), new Replacement("\"", "&quot;"), new Replacement("'", "&apos;"));
        final Writer w;

        TagWriter(Writer w) {
            this.w = w;
        }

        void startTag(String name) throws IOException {
            w.write("<");
            w.write(name);
        }

        void writeAttribute(String name, String value) throws IOException {
            w.write(" ");
            w.write(name);
            w.write("=\"");
            w.write(value);
            w.write("\"");
        }

        void writeAttributeIfValueExists(String name, String value) throws IOException {
            if (value != null)
                writeAttribute(name, value);
        }

        void writeMetaTag(String name, String content) throws IOException {
            startTag("meta");
            writeAttribute("name", name);
            writeAttribute("content", escape(content));
            closeTag();
        }

        void closeTag() throws IOException {
            w.write("/>");
        }

        String escape(String content) {
            for (Replacement replacement : REPLACEMENTS)
                content = content.replace(replacement.character, replacement.escapeSequence);
            return content;
        }
    }

    private static class Replacement {
        final String character;
        final String escapeSequence;

        Replacement(String character, String escapeSequence) {
            this.character = character;
            this.escapeSequence = escapeSequence;
        }
    }
}
