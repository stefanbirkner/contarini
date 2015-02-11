package com.github.stefanbirkner.contarini;

/**
 * Features of the Google search that can be disabled by the site owner.
 */
public enum GoogleFeature {
    /**
     * Displays a search box for the site in the Sitelinks section.
     * See <a href="https://developers.google.com/webmasters/structured-data/slsb-overview">Sitelinks
     * Search Box</a> at Google Developers.
     */
    SITELINKS_SEARCH_BOX("nositelinkssearchbox"),
    /**
     * Provide a link to translation. See
     * <a href="https://support.google.com/webmasters/answer/79812">Meta
     * tags that Google understands</a>
     */
    TRANSLATION("notranslate");

    private final String labelForDisabling;

    private GoogleFeature(String labelForDisabling) {
        this.labelForDisabling = labelForDisabling;
    }

    /**
     * Returns the label that has to be used in a meta tag for
     * disabling the feature.
     */
    public String getLabelForDisabling() {
        return labelForDisabling;
    }
}
