package ru.unn.agile.LeftistHeap.viewmodel;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class LeftistHeapRegexMatcher extends TypeSafeMatcher<String> {
    private final String regex;

    public LeftistHeapRegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    protected boolean matchesSafely(final String item) {
        return item.matches(regex);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("Matches regular expression=`" + regex + "`");
    }

    public static LeftistHeapRegexMatcher matches(final String regex) {
        return new LeftistHeapRegexMatcher(regex);
    }
}
