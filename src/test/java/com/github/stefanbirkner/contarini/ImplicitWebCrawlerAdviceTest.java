package com.github.stefanbirkner.contarini;

import org.junit.Test;

import java.util.List;

import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.NO_FOLLOW;
import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.NO_SNIPPET;
import static com.github.stefanbirkner.contarini.ImplicitWebCrawlerAdvice.*;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class ImplicitWebCrawlerAdviceTest {
    private static final WebCrawlerAdvice NEUTRAL_ADVICE = NO_SNIPPET;

    @Test
    public void addsAllImplicitAdvicesToNeutralAdvice() {
        List<WebCrawlerAdvice> advices = implicitAdvicesAnd(singleAdvice(NEUTRAL_ADVICE));
        assertThat(advices)
            .contains(ImplicitWebCrawlerAdvice.values())
            .contains(NEUTRAL_ADVICE);
    }

    @Test
    public void doesNotAddImplicitAdviceIfCancellingAdviceIsPresent() {
        List<WebCrawlerAdvice> advices = implicitAdvicesAnd(singleAdvice(NO_FOLLOW));
        assertThat(advices).doesNotContain(FOLLOW);
    }

    @Test
    public void doesNotAddAdviceThatIsAlreadyPresent() {
        List<WebCrawlerAdvice> advices = implicitAdvicesAnd(singleAdvice(FOLLOW));
        assertThat(advices).contains(FOLLOW, INDEX);
    }

    private List<WebCrawlerAdvice> singleAdvice(WebCrawlerAdvice advice) {
        return asList(advice);
    }
}
