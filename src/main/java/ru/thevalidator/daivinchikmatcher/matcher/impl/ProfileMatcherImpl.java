/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.matcher.impl;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.thevalidator.daivinchikmatcher.dto.Profile;
import static ru.thevalidator.daivinchikmatcher.handler.Identifier.REGEXP;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.matcher.ProfileMatcher;

public class ProfileMatcherImpl implements ProfileMatcher {

    private final Pattern pattern;
    private Set<Filter> filters;
    private Matcher matcher;

    public ProfileMatcherImpl(Set<Filter> filters) {
        this.filters = filters;
        pattern = Pattern.compile(REGEXP);
    }

    @Override
    public boolean matches(String profileText) {
        String name, age, city, text;
        matcher = pattern.matcher(profileText);
        
        if (matcher.find()) {
            name = getName(matcher);
            age = getAge(matcher);
            city = getCity(matcher);
            text = getText(matcher);
            
            Profile profile = new Profile(name, age, city, text);
            for (Filter filter : filters) {
                if (!filter.isFiltered(profile)) {
                    return false;
                }
            }
            
            return true;
        }
        throw new IllegalArgumentException("No match pattern found in: " + profileText);
    }

    public String getName(Matcher matcher) {
        String text = matcher.group("name");
        return text.substring(0, text.length() - 1).trim();
    }

    public String getAge(Matcher matcher) {
        String text = matcher.group("age");
        return text.substring(0, text.length() - 1).trim();
    }

    public String getCity(Matcher matcher) {
        String text = matcher.group("city");
        return text.trim().toLowerCase();
    }

    public String getText(Matcher matcher) {
        String text = matcher.group("text");
        return text.trim();
    }

}
