package com.github.stefanbirkner.contarini;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.NO_ARCHIVE;
import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.NO_INDEX;
import static com.github.stefanbirkner.contarini.GoogleFeature.SITELINKS_SEARCH_BOX;
import static com.github.stefanbirkner.contarini.GoogleFeature.TRANSLATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.rules.ExpectedException.none;

public class WebCrawlerInfoTest {
    private static final WebCrawlerAdvice DUMMY_ADVICE = NO_ARCHIVE;
    private static final WebCrawlerAdvice OTHER_DUMMY_ADVICE = NO_INDEX;
    private static final Alternate DUMMY_ALTERNATE = new Alternate("language", "href");
    private static final Alternate OTHER_DUMMY_ALTERNATE = new Alternate("another language", "another href");

    @Rule
    public final ExpectedException thrown = none();

    @Test
    public void makesListOfAdvicesUnmodifiable() {
        thrown.expect(UnsupportedOperationException.class);
        WebCrawlerInfo info = new WebCrawlerInfo().withAdvices(DUMMY_ADVICE);
        info.getAdvices().add(OTHER_DUMMY_ADVICE);
    }

    @Test
    public void makesListOfAlternatesUnmodifiable() {
        thrown.expect(UnsupportedOperationException.class);
        WebCrawlerInfo info = new WebCrawlerInfo().withAlternates(DUMMY_ALTERNATE);
        info.getAlternates().add(OTHER_DUMMY_ALTERNATE);
    }

    @Test
    public void makesListOfDisabledGoogleFeaturesUnmodifiable() {
        thrown.expect(UnsupportedOperationException.class);
        WebCrawlerInfo info = new WebCrawlerInfo().disableGoogleFeatures(SITELINKS_SEARCH_BOX);
        info.getDisabledGoogleFeatures().add(TRANSLATION);
    }

    @Test
    public void createsEmptyInfoWithUnmodfiableListOfNoAdvices() {
        thrown.expect(UnsupportedOperationException.class);
        WebCrawlerInfo info = new WebCrawlerInfo();
        info.getAdvices().add(DUMMY_ADVICE);
    }

    @Test
    public void createsEmptyInfoWithUnmodfiableListOfNoAlternates() {
        thrown.expect(UnsupportedOperationException.class);
        WebCrawlerInfo info = new WebCrawlerInfo();
        info.getAlternates().add(DUMMY_ALTERNATE);
    }

    @Test
    public void createsEmptyInfoWithUnmodfiableListOfDisabledGoogleFeatures() {
        thrown.expect(UnsupportedOperationException.class);
        WebCrawlerInfo info = new WebCrawlerInfo();
        info.getDisabledGoogleFeatures().add(TRANSLATION);
    }

    @Test
    public void isDifferentFromInfoWithOtherAdvices() {
        WebCrawlerInfo firstInfo = new WebCrawlerInfo().withAdvices(DUMMY_ADVICE);
        WebCrawlerInfo secondInfo = new WebCrawlerInfo().withAdvices(OTHER_DUMMY_ADVICE);
        assertThat(firstInfo).isNotEqualTo(secondInfo);
    }

    @Test
    public void isDifferentFromInfoWithOtherAlternates() {
        WebCrawlerInfo firstInfo = new WebCrawlerInfo().withAlternates(DUMMY_ALTERNATE);
        WebCrawlerInfo secondInfo = new WebCrawlerInfo().withAlternates(OTHER_DUMMY_ALTERNATE);
        assertThat(firstInfo).isNotEqualTo(secondInfo);
    }

    @Test
    public void isDifferentFromInfoWithOtherCanonical() {
        WebCrawlerInfo firstInfo = new WebCrawlerInfo().withCanonical("first canonical");
        WebCrawlerInfo secondInfo = new WebCrawlerInfo().withCanonical("second canonical");
        assertThat(firstInfo).isNotEqualTo(secondInfo);
    }

    @Test
    public void isDifferentFromInfoWithOtherDescription() {
        WebCrawlerInfo firstInfo = new WebCrawlerInfo().withDescription("first description");
        WebCrawlerInfo secondInfo = new WebCrawlerInfo().withDescription("second description");
        assertThat(firstInfo).isNotEqualTo(secondInfo);
    }

    @Test
    public void isDifferentFromInfoWithOtherDisabledGoogleFeatures() {
        WebCrawlerInfo firstInfo = new WebCrawlerInfo().disableGoogleFeatures(SITELINKS_SEARCH_BOX);
        WebCrawlerInfo secondInfo = new WebCrawlerInfo().disableGoogleFeatures(TRANSLATION);
        assertThat(firstInfo).isNotEqualTo(secondInfo);
    }

    @Test
    public void isDifferentFromInfoWithOtherKeywords() {
        WebCrawlerInfo firstInfo = new WebCrawlerInfo().withKeywords("first keywords");
        WebCrawlerInfo secondInfo = new WebCrawlerInfo().withKeywords("second keywords");
        assertThat(firstInfo).isNotEqualTo(secondInfo);
    }
}
