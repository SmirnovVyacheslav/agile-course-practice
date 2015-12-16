package ru.unn.agile.MergeSort.ViewModel;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class MergeSortRegexMatcher extends TypeSafeMatcher<String> {
    public MergeSortRegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("Matches regular expression = " + regex);
    }

    @Override
    protected boolean matchesSafely(final String regex) {
        return regex.matches(this.regex);
    }

    public static MergeSortRegexMatcher matches(final String regex) {
        return new MergeSortRegexMatcher(regex);
    }

    private final String regex;
}

