package com.n.patterncall.model;

/**
 * Created by N on 2016-03-08.
 */
public class PatternDirectory {
    private String pattern;
    private String relatedValue;

    public PatternDirectory(String pattern, String relatedValue) {
        this.pattern = pattern;
        this.relatedValue = relatedValue;
    }

    public String getPattern() {
        return pattern;
    }

    public String getRelatedValue() {
        return relatedValue;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setRelatedValue(String relatedValue) {
        this.relatedValue = relatedValue;
    }
}
