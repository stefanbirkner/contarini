package com.github.stefanbirkner.contarini;

import static com.github.stefanbirkner.contarini.CommonWebCrawlerAdvice.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link com.github.stefanbirkner.contarini.WebCrawlerAdvice} that
 * doesn't have to be explicitly stated. Nevertheless you can
 * automatically add the applicable advices to an existing list of
 * advices with {@link #implicitAdvicesAnd(java.util.List)}.
 */
public enum ImplicitWebCrawlerAdvice implements WebCrawlerAdvice {
    INDEX("index", NO_INDEX, NONE), FOLLOW("follow", NO_FOLLOW, NONE);

    private final String label;
    //The implicit advice doesn't apply if on of the cancelling advices is present.
    private WebCrawlerAdvice[] cancellingAdvices;

    private ImplicitWebCrawlerAdvice(String label, WebCrawlerAdvice... cancellingAdvices) {
        this.label = label;
        this.cancellingAdvices = cancellingAdvices;
    }

    /**
     * Returns the text that appears in the content attribute of the
     * robots meta tag.
     * @return the label of this advice.
     */
    @Override
    public String getLabel() {
        return label;
    }

    /**
     * Adds the applicable {@code ImplicitWebCrawlerAdvice} to a list
     * of advices. An advice is only added if an advice with the same
     * label is already present. This means that this function is
     * idempotent.
     *
     * @param advices a list of advices.
     * @return the original advices together with the applicable
     * {@code ImplicitWebCrawlerAdvice}s.
     */
    public static List<WebCrawlerAdvice> implicitAdvicesAnd(List<WebCrawlerAdvice> advices) {
        List<WebCrawlerAdvice> advicesIncludingImplicit = new ArrayList<WebCrawlerAdvice>(advices);
        for (ImplicitWebCrawlerAdvice advice : values())
            if (canAddAdviceToAdvices(advice, advicesIncludingImplicit))
                advicesIncludingImplicit.add(advice);
        return advicesIncludingImplicit;
    }

    private static boolean canAddAdviceToAdvices(ImplicitWebCrawlerAdvice adviceToAdd,
            List<WebCrawlerAdvice> advices) {
        for (WebCrawlerAdvice advice : advices)
            if (isCancellingAdviceForImplicitAdvice(advice, adviceToAdd)
                    || hasSameLabel(advice, adviceToAdd))
                return false;
        return true;
    }

    private static boolean isCancellingAdviceForImplicitAdvice(WebCrawlerAdvice adviceToTest,
            ImplicitWebCrawlerAdvice adviceToAdd) {
        for (WebCrawlerAdvice cancellingAdvice : adviceToAdd.cancellingAdvices)
            if (hasSameLabel(cancellingAdvice, adviceToTest))
                return true;
        return false;
    }

    private static boolean hasSameLabel(WebCrawlerAdvice firstAdvice, WebCrawlerAdvice secondAdvice) {
        return firstAdvice.getLabel().equals(secondAdvice.getLabel());
    }
}
