package com.github.stefanbirkner.contarini.render;

import com.github.stefanbirkner.contarini.Alternate;
import com.github.stefanbirkner.contarini.WebCrawlerInfo;
import org.junit.Test;

import java.io.IOException;
import java.io.StringWriter;

import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.NO_ARCHIVE;
import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.NO_FOLLOW;
import static com.github.stefanbirkner.contarini.GoogleFeature.SITELINKS_SEARCH_BOX;
import static com.github.stefanbirkner.contarini.GoogleFeature.TRANSLATION;
import static com.github.stefanbirkner.contarini.render.VoidElementStyle.XML_SELF_CLOSING_WITHOUT_SPACE;
import static org.assertj.core.api.Assertions.assertThat;

public class WebCrawlerInfoRendererTest extends WebCrawlerInfoRenderer {
    private static final String CHARACTERS_TO_ESCAPE = "<>\"&'";
    private static final String ESCAPED_CHARACTERS_TO_ESCAPE = "&lt;&gt;&quot;&amp;&apos;";
    private static final String DUMMY_CANONICAL = "http://dummy.canonical";
    private static final String DUMMY_TEXT = "dummy text";
    private static final String FIRST_DUMMY_LANGUAGE = "de";
    private static final String FIRST_DUMMY_HREF = DUMMY_CANONICAL;
    private static final String SECOND_DUMMY_LANGUAGE = "en";
    private static final String SECOND_DUMMY_HREF = DUMMY_CANONICAL + ".en";

    @Test
    public void writesCanonical() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().withCanonical(DUMMY_CANONICAL);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<link rel=\"canonical\" href=\"" + DUMMY_CANONICAL + "\">");
    }

    @Test
    public void writesDescription() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().withDescription(DUMMY_TEXT);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<meta name=\"description\" content=\"" + DUMMY_TEXT + "\">");
    }

    @Test
    public void escapesCharactersInDescription() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().withDescription(CHARACTERS_TO_ESCAPE);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<meta name=\"description\" content=\"" + ESCAPED_CHARACTERS_TO_ESCAPE + "\">");
    }

    @Test
    public void writesKeywords() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().withKeywords(DUMMY_TEXT);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<meta name=\"keywords\" content=\"" + DUMMY_TEXT + "\">");
    }

    @Test
    public void escapesCharactersInKeywords() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().withKeywords(CHARACTERS_TO_ESCAPE);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<meta name=\"keywords\" content=\"" + ESCAPED_CHARACTERS_TO_ESCAPE + "\">");
    }

    @Test
    public void writesSingleAdvice() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().withAdvices(NO_ARCHIVE);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<meta name=\"robots\" content=\"noarchive\">");
    }

    @Test
    public void writesTwoAdvicesSeparatedByComma() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().withAdvices(NO_ARCHIVE, NO_FOLLOW);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<meta name=\"robots\" content=\"noarchive, nofollow\">");
    }

    @Test
    public void writesTwoAlternates() throws Exception {
        Alternate firstAlternate = new Alternate(FIRST_DUMMY_LANGUAGE, FIRST_DUMMY_HREF);
        Alternate secondAlternate = new Alternate(SECOND_DUMMY_LANGUAGE, SECOND_DUMMY_HREF);
        WebCrawlerInfo info = new WebCrawlerInfo().withAlternates(firstAlternate, secondAlternate);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<link rel=\"alternate\" hreflang=\"" + FIRST_DUMMY_LANGUAGE + "\" href=\""
                + FIRST_DUMMY_HREF + "\"><link rel=\"alternate\" hreflang=\"" + SECOND_DUMMY_LANGUAGE + "\" href=\""
                + SECOND_DUMMY_HREF + "\">");
    }

    @Test
    public void writesAlternateWithoutLanguage() throws Exception {
        Alternate alternate = new Alternate(FIRST_DUMMY_HREF);
        WebCrawlerInfo info = new WebCrawlerInfo().withAlternates(alternate);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<link rel=\"alternate\" href=\"" + FIRST_DUMMY_HREF + "\">");
    }

    @Test
    public void writesMetaTagsForDisabledGoogleFeatures() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().disableGoogleFeatures(SITELINKS_SEARCH_BOX, TRANSLATION);
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("<meta name=\"google\" content=\"nositelinkssearchbox\">"
                + "<meta name=\"google\" content=\"notranslate\">");
    }

    @Test
    public void writesNothingForEmptyInfo() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo();
        String tags = renderTagsForInfo(info);
        assertThat(tags).isEqualTo("");
    }

    @Test
    public void writesTagWithXmlStyleIfSpecified() throws Exception {
        WebCrawlerInfo info = new WebCrawlerInfo().withCanonical(DUMMY_CANONICAL);
        Style format = new Style().withVoidElementStyle(XML_SELF_CLOSING_WITHOUT_SPACE);
        String tags = renderTagsForInfo(info, format);
        assertThat(tags).isEqualTo("<link rel=\"canonical\" href=\"" + DUMMY_CANONICAL + "\"/>");
    }

    private String renderTagsForInfo(WebCrawlerInfo info) throws IOException {
        WebCrawlerInfoRenderer renderer = new WebCrawlerInfoRenderer();
        return renderTagsForInfo(info, renderer);
    }

    private String renderTagsForInfo(WebCrawlerInfo info, Style style) throws IOException {
        WebCrawlerInfoRenderer renderer = new WebCrawlerInfoRenderer(style);
        return renderTagsForInfo(info, renderer);
    }

    private String renderTagsForInfo(WebCrawlerInfo info, WebCrawlerInfoRenderer renderer) throws IOException {
        StringWriter w = new StringWriter();
        renderer.writeTagsForInfoToWriter(info, w);
        return w.toString();
    }
}
