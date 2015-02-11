package com.github.stefanbirkner.contarini.render;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import static java.util.Arrays.asList;

import com.github.stefanbirkner.contarini.Alternate;
import com.github.stefanbirkner.contarini.GoogleFeature;
import com.github.stefanbirkner.contarini.WebCrawlerAdvice;
import com.github.stefanbirkner.contarini.WebCrawlerInfo;

/**
 * Renders the HTML tags for a {@link com.github.stefanbirkner.contarini.WebCrawlerInfo} object.
 * <table>
 *     <caption>Tags rendered by WebCrawlerInfoRenderer</caption>
 *     <tr>
 *         <th>WebCrawlerInfo property</th>
 *         <th>Output</th>
 *     </tr>
 *     <tr>
 *         <td>getCanonical()</td>
 *         <td><code>&lt;link rel="canonical" href="..."&gt;</code></td>
 *     </tr>
 *     <tr>
 *         <td>getAdvices()</td>
 *         <td><code>&lt;meta name="robots" href="..."&gt;</code></td>
 *     </tr>
 *     <tr>
 *         <td>getAlternates()</td>
 *         <td>
 *             For each alternate:
 *             <code>&lt;link rel="alternate" hreflang="..." href="..."&gt;</code>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>getDescription()</td>
 *         <td><code>&lt;meta name="description" href="..."&gt;</code></td>
 *     </tr>
 *     <tr>
 *         <td>getKeywords()</td>
 *         <td><code>&lt;meta name="keywords" href="..."&gt;</code></td>
 *     </tr>
 *     <tr>
 *         <td>getDisabledGoogleFeatures()</td>
 *         <td>
 *             For each disabled feature:
 *             <code>&lt;meta name="google" content="..."&gt;</code>
 *         </td>
 *     </tr>
 * </table>
 *
 * <h2>HTML format</h2>
 * <p>The renderer escapes the provided texts.
 * <p>An {@link Style} can be used to control the style of the generated
 * HTML.
 */
public class WebCrawlerInfoRenderer {
    private static final Style DEFAULT_STYLE = new Style();
    private final Style style;

    /**
     * Creates a {@code WebCrawlerInfoRenderer} that renders HTML tags
     * with the default {@link Style}.
     */
    public WebCrawlerInfoRenderer() {
        this(DEFAULT_STYLE);
    }

    /**
     * Creates a {@code WebCrawlerInfoRenderer} that renders HTML tags
     * with the specified {@link Style}.
     * @param style the {@link Style} that specifies the format of the
     *              generated HTML.
     */
    public WebCrawlerInfoRenderer(Style style) {
        this.style = style;
    }

    /**
     * Writes HTML tags to the writer according to the provided {@link WebCrawlerInfo}.
     * @param info the {@link com.github.stefanbirkner.contarini.WebCrawlerInfo} that defines the tags.
     * @param w the {@link Writer}.
     * @throws IOException If an I/O error occurs.
     */
    public void writeTagsForInfoToWriter(WebCrawlerInfo info, Writer w) throws IOException {
        TagWriter tagWriter = new TagWriter(w, style);
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
        writeMetaTagsForDisabledGoogleFeatures(info.getDisabledGoogleFeatures(), w);
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

    private void writeMetaTagsForDisabledGoogleFeatures(List<GoogleFeature> disabledFeatures, TagWriter w)
            throws IOException {
        for (GoogleFeature feature : disabledFeatures)
            w.writeMetaTag("google", feature.getLabelForDisabling());
    }

    private static class TagWriter {
        static final List<Replacement> REPLACEMENTS = asList(new Replacement("&", "&amp;"), new Replacement("<",
            "&lt;"), new Replacement(">", "&gt;"), new Replacement("\"", "&quot;"), new Replacement("'", "&apos;"));
        final Writer w;
        final Style style;

        TagWriter(Writer w, Style style) {
            this.w = w;
            this.style = style;
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
            w.write(style.getVoidElementStyle().closingSuffix);
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
