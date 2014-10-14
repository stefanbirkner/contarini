package com.github.stefanbirkner.contarini;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

/**
 * Presentation model for web crawler tags. A {@code WebCrawlerInfo} is
 * a value object with a fluent interface. Start with an empty
 * {@code WebCrawlerInfo} and consecutively set its values.
 * <pre>
 * WebCrawlerInfo webCrawlerInfo = new WebCrawlerInfo()
 *   .withAdvices({@link com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice#NO_FOLLOW NO_FOLLOW}, {@link com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice#NO_INDEX NO_INDEX})
 *   .withAlternates(
 *     new {@link com.github.stefanbirkner.contarini.Alternate Alternate}("it", "http://dummy.domain.it/aiuto"),
 *     new Alternate("fi", "http://dummy.domain.fi/ohje"))
 *   .withCanonical("http://dummy.domain.com/help")
 *   .withDescription("This is the help page of dummy domain.")
 *   .withKeywords("help, dummy domain");
 * </pre>
 * You don't have to set properties that you don't want to use. The tags
 * for the {@code WebCrawlerInfo} object are rendered by a
 * {@link com.github.stefanbirkner.contarini.WebCrawlerInfoRenderer}.
 */
public class WebCrawlerInfo {
    private static final List<WebCrawlerAdvice> NO_ADVICES = emptyList();
    private static final List<Alternate> NO_ALTERNATES = emptyList();
    private final String canonical;
    private final List<WebCrawlerAdvice> advices;
    private final List<Alternate> alternates;
    private final String description;
    private final String keywords;

    private WebCrawlerInfo(String canonical, List<WebCrawlerAdvice> advices, List<Alternate> alternates,
                           String description, String keywords) {
        this.canonical = canonical;
        this.advices = advices;
        this.alternates = alternates;
        this.description = description;
        this.keywords = keywords;
    }

    /**
     * Creates an empty {@code WebCrawlerInfo}. No tags should be
     * rendered for such an object. You always start by creating such
     * an empty {@code WebCrawlerInfo} and create a full-fledged
     * {@code WebCrawlerInfo} by subsequently consecutively calling the
     * {@code with} methods.
     */
    public WebCrawlerInfo() {
        this.canonical = null;
        this.advices = NO_ADVICES;
        this.alternates = NO_ALTERNATES;
        this.description = null;
        this.keywords = null;
    }

    /**
     * Returns the canonical URL (aka the {@code href} attribute of
     * {@code <link rel="canonical" href="http://dummy.domain.com/help"/>}).
     * @return the canonical URL or {@code null} if it is not set.
     * @see #withCanonical(String)
     */
    public String getCanonical() {
        return canonical;
    }

    /**
     * Creates a new {@code WebCrawlerInfo} with a different canonical
     * URL. The other properties are taken from the current object.
     * @param canonical the new canonical URL.
     * @return a new {@code WebCrawlerInfo} object.
     * @see #getCanonical()
     */
    public WebCrawlerInfo withCanonical(String canonical) {
        return new WebCrawlerInfo(canonical, advices, alternates, description, keywords);
    }

    /**
     * Returns a list of advices for the robots meta tag (aka the
     * single parts of the {@code content} attribute of
     * {@code <meta name="robots" content="index,follow"/>}).
     * @return a list of advices for the robots meta tag. Never
     * returns {@code null}.
     * @see #withAdvices(WebCrawlerAdvice...)
     */
    public List<WebCrawlerAdvice> getAdvices() {
        return advices;
    }

    /**
     * Creates a new {@code WebCrawlerInfo} with a different list of
     * advices for the robots meta tag. The other properties are taken
     * from the current object.
     * @param advices a list of advices for the robots meta tag.
     * @return a new {@code WebCrawlerInfo} object.
     * @see #getAdvices()
     */
    public WebCrawlerInfo withAdvices(WebCrawlerAdvice... advices) {
        return new WebCrawlerInfo(canonical, asList(advices), alternates, description, keywords);
    }

    /**
     * Returns a list of alternates. They are presentation models for
     * the {@code alternate} link tags (aka
     * {@code <link rel="alternate" href="http://dummy.domain.fi/ohje" hreflang="fi"/>}).
     * @return a list of advices for the alternate link tag. Never
     * returns {@code null}.
     * @see #withAlternates(Alternate...)
     */
    public List<Alternate> getAlternates() {
        return alternates;
    }


    /**
     * Creates a new {@code WebCrawlerInfo} with a different list of
     * alternates. The other properties are taken from the current
     * object.
     * @param alternates a list of alternates for the corresponding
     *                   link tag.
     * @return a new {@code WebCrawlerInfo} object.
     * @see #getAlternates()
     */
    public WebCrawlerInfo withAlternates(Alternate... alternates) {
        return new WebCrawlerInfo(canonical, advices, asList(alternates), description, keywords);
    }

    /**
     * Returns the page description (aka the {@code content} attribute of
     * {@code <meta rel="description" content="This is the help page of dummy domain."/>}).
     * @return the page description or {@code null} if it is not set.
     * @see #withDescription(String)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Creates a new {@code WebCrawlerInfo} with a different page
     * description. The other properties are taken from the current
     * object.
     * @param description the new page description.
     * @return a new {@code WebCrawlerInfo} object.
     * @see #getDescription()
     */
    public WebCrawlerInfo withDescription(String description) {
        return new WebCrawlerInfo(canonical, advices, alternates, description, keywords);
    }

    /**
     * Returns the keywords (aka the {@code content} attribute of
     * {@code <meta rel="keywords" content="help, dummy domain"/>}).
     * @return the keywords or {@code null} if they are not set.
     * @see #withKeywords(String)
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * Creates a new {@code WebCrawlerInfo} with different keywords.
     * The other properties are taken from the current object.
     * @param keywords the new keywords.
     * @return a new {@code WebCrawlerInfo} object.
     * @see #getKeywords()
     */
    public WebCrawlerInfo withKeywords(String keywords) {
        return new WebCrawlerInfo(canonical, advices, alternates, description, keywords);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((advices == null) ? 0 : advices.hashCode());
        result = prime * result + ((alternates == null) ? 0 : alternates.hashCode());
        result = prime * result + ((canonical == null) ? 0 : canonical.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        WebCrawlerInfo other = (WebCrawlerInfo) obj;
        if (advices == null) {
            if (other.advices != null)
                return false;
        } else if (!advices.equals(other.advices))
            return false;
        if (alternates == null) {
            if (other.alternates != null)
                return false;
        } else if (!alternates.equals(other.alternates))
            return false;
        if (canonical == null) {
            if (other.canonical != null)
                return false;
        } else if (!canonical.equals(other.canonical))
            return false;
        if (description == null) {
            if (other.description != null)
                return false;
        } else if (!description.equals(other.description))
            return false;
        if (keywords == null) {
            if (other.keywords != null)
                return false;
        } else if (!keywords.equals(other.keywords))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "WebCrawlerInfo [canonical=" + canonical + ", advices=" + advices + ", alternates=" + alternates
                + ", description=" + description + ", keywords=" + keywords + "]";
    }
}
