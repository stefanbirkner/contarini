package com.github.stefanbirkner.contarini;

/**
 * Alternate URLs for the current page. There are factory methods for known
 * types of alternate URLs:
 * <ul>
 *   <li>Alternate language or region: {@link #alternateLanguage(String, String)}</li>
 *   <li>Alternate media: {@link #alternateMedia(String, String)}</li>
 * </ul>
 */
public class Alternate {
    /**
     * Creates an {@code Alternate} for an alternate language or region.
     * See <a href="https://support.google.com/webmasters/answer/189077">Google
     * Search Console Help</a>.
     * @param language the alternate page's language and optionally the region.
     * @param href the URL of the alternate page.
     * @return an {@code Alternate} object for the specified language.
     * @since 1.1.0
     */
    public static Alternate alternateLanguage(String language, String href) {
        return new Alternate(href, language, null);
    }

    /**
     * Creates an {@code Alternate} for an alternate media (e.g. mobile pages).
     * See
     * <a href="https://developers.google.com/webmasters/mobile-sites/mobile-seo/configurations/separate-urls">Separate
     * URLs</a>.
     * @param media a CSS media query string that specifies the media features
     *              describing when Google should use the alternative URL.
     * @param href the URL of the alternate page.
     * @return an {@code Alternate} object for the specified media query.
     * @since 1.1.0
     */
    public static Alternate alternateMedia(String media, String href) {
        return new Alternate(href, null, media);
    }

    public final String href;
    public final String language;
    public final String media;

    /**
     * @since 1.1.0
     */
    public Alternate(String href, String language, String media) {
        this.href = href;
        this.language = language;
        this.media = media;
    }

    public Alternate(String href) {
        this(href, null, null);
    }

    /**
     * Creates an {@code Alternate} for an alternate language or region.
     * See <a href="https://support.google.com/webmasters/answer/189077">Google
     * Search Console Help</a>.
     * @param language the alternate page's language and optionally the region.
     * @param href the URL of the alternate page.
     * @deprecated Please use {@link #alternateLanguage(String, String)}
     */
    @Deprecated
    public Alternate(String language, String href) {
        this(href, language, null);
    }

    @Override
    public int hashCode() {
        int prime = 2543;
        int result = prime + ((href == null) ? 0 : href.hashCode());
        result = prime * result + ((language == null) ? 0 : language.hashCode());
        return prime * result + ((media == null) ? 0 : media.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Alternate other = (Alternate) obj;
        if (href == null) {
            if (other.href != null)
                return false;
        } else if (!href.equals(other.href))
            return false;
        if (language == null) {
            if (other.language != null)
                return false;
        } else if (!language.equals(other.language))
            return false;
        if (media == null) {
            if (other.media != null)
                return false;
        } else if (!media.equals(other.media))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Alternate [language=" + language + ", href=" + href
            + ", media=" + media + "]";
    }
}
