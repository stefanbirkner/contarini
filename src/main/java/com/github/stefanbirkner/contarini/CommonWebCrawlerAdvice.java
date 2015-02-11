package com.github.stefanbirkner.contarini;

/**
 * Advices that are supported by most web crawlers. See
 * <a href="https://support.google.com/webmasters/answer/79812">Meta
 * tags that Google understands</a> and
 * <a href="http://www.bing.com/webmaster/help/which-robots-metatags-does-bing-support-5198d240">bing
 * webmaster help &amp; how-to</a>
 */
public enum CommonWebCrawlerAdvice implements WebCrawlerAdvice {
    NO_INDEX("noindex"), NO_FOLLOW("nofollow"), NONE("none"),
    NO_ARCHIVE("noarchive"), NO_SNIPPET("nosnippet"),
    DONT_USE_DESCRIPTION_FROM_OPEN_DIRECTORY_PROJECT("noodp"),
    NO_IMAGE_INDEX("noimageindex");

    private final String label;

    private CommonWebCrawlerAdvice(String label) {
        this.label = label;
    }

    /**
     * Returns the label that has to be used by the meta robots tag.
     */
    @Override
    public String getLabel() {
        return label;
    }
}
