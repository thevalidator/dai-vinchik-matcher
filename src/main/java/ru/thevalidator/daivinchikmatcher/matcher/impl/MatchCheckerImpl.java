/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.matcher.impl;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.thevalidator.daivinchikmatcher.dto.Profile;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.matcher.MatchChecker;

public class MatchCheckerImpl implements MatchChecker {

    public static final String REGEXP = "(?<name>.+,) "
            + "(?<age>\\d{1,3},) "
            + "(?<city>[a-zA-Zа-яА-я0-9 \\-,\\.ёЁ]+)"
            + "(?<text>(((<br>)|\\n){1,}.+){0,})";
    private final Pattern pattern; 
    private Set<Filter> filters;

    public MatchCheckerImpl(Set<Filter> filters) {
        this.filters = filters;
        pattern = Pattern.compile(REGEXP);
    }
    
    @Override
    public boolean matches(String text) {
        Profile profile = new Profile(getName(text), getAge(text), getCity(text).toLowerCase(), getText(text));
        for (Filter filter : filters) {
            if (!filter.isFiltered(profile)) {
                return false;
            }
        }
        return true;
    }

    public String getName(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = matcher.group("name");
            return match.substring(0, match.length() - 1).trim();
        }
        throw new IllegalArgumentException("Name not found:" + text);
    }

    public String getAge(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = matcher.group("age");
            return match.substring(0, match.length() - 1).trim();
        }
        throw new IllegalArgumentException("Age not found: " + text);
    }

    public String getCity(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = matcher.group("city");
            return match.trim();
        }
        throw new IllegalArgumentException("City not found: " + text);
    }

    public String getText(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = matcher.group("text");
            return match;
        }
        return null;
        //TODO: check if need return null or throw ex (now it finds empty text in case of no text) 
    }

}
