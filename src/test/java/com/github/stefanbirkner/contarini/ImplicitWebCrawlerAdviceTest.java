package com.github.stefanbirkner.contarini;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.NO_FOLLOW;
import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.NO_SNIPPET;
import static com.github.stefanbirkner.contarini.ImplicitWebCrawlerAdvice.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ImplicitWebCrawlerAdviceTest {
    private static final WebCrawlerAdvice NEUTRAL_ADVICE = NO_SNIPPET;
    public static final Matcher<Iterable<WebCrawlerAdvice>> HAS_ALL_IMPLICIT_ADVICES = Matchers.<WebCrawlerAdvice>hasItems(ImplicitWebCrawlerAdvice.values());

    @Test
    public void addsAllImplicitAdvicesToNeutralAdvice() {
        List<WebCrawlerAdvice> advices = implicitAdvicesAnd(singleAdvice(NEUTRAL_ADVICE));
        //The OpenJDK 6 needs the explicit type argument.
        assertThat(advices, Matchers.<Iterable<WebCrawlerAdvice>>allOf(HAS_ALL_IMPLICIT_ADVICES, hasItem(NEUTRAL_ADVICE)));
    }

    @Test
    public void doesNotAddImplicitAdviceIfCancellingAdviceIsPresent() {
        List<WebCrawlerAdvice> advices = implicitAdvicesAnd(singleAdvice(NO_FOLLOW));
        assertThat(advices, not(Matchers.<WebCrawlerAdvice>hasItem(FOLLOW)));
    }

    @Test
    public void doesNotAddAdviceThatIsAlreadyPresent() {
        List<WebCrawlerAdvice> advices = implicitAdvicesAnd(singleAdvice(FOLLOW));
        assertThat(advices, Matchers.<WebCrawlerAdvice>containsInAnyOrder(FOLLOW, INDEX));
    }

    private List<WebCrawlerAdvice> singleAdvice(WebCrawlerAdvice advice) {
        return asList(advice);
    }
}