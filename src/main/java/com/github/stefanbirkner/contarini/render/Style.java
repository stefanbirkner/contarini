package com.github.stefanbirkner.contarini.render;

/**
 * Specifies the style of the output.
 */
public class Style {
    private final VoidElementStyle voidElementStyle;

    /**
     * Creates a {@code Style} with {@link VoidElementStyle#HTML_VOID}.
     */
    public Style() {
        this(VoidElementStyle.HTML_VOID);
    }

    private Style(VoidElementStyle voidElementStyle) {
        this.voidElementStyle = voidElementStyle;
    }

    /**
     * Returns the style for void elements.
     */
    public VoidElementStyle getVoidElementStyle() {
        return voidElementStyle;
    }

    /**
     * Creates a new style with a different {@link VoidElementStyle}.
     */
    public Style withVoidElementStyle(VoidElementStyle style) {
        return new Style(style);
    }

    @Override
    public int hashCode() {
        return voidElementStyle != null ? voidElementStyle.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Style that = (Style) o;
        return voidElementStyle == that.voidElementStyle;

    }

    @Override
    public String toString() {
        return "HtmlFormat [voidElementStyle=" + voidElementStyle + "]";
    }
}
