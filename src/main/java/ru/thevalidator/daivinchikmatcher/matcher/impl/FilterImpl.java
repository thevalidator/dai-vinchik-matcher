/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.matcher.impl;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.util.Reader;

public class FilterImpl implements Filter {

    private static final String regexp = "^(?<name>.+,) (?<age>\\d{1,3},) (?<city>[a-zA-Zа-яА-я0-9 -]+)(?<text>((<br>)|\\n).+)?";
    private final Set<String> cities;
    private final Set<String> words;
    private final Set<String> dictionary;
    private final Pattern pattern;

    public FilterImpl() {
        this.cities = Reader.readDict("cities.dict");
        this.words = Reader.readDict("words.dict");
        this.dictionary = Reader.readDict("match.dict");
        pattern = Pattern.compile(regexp);
    }

    @Override
    public boolean isMatched(String text) {
        String city = getCity(text).toLowerCase();
        String description = getText(text);
        if (cities.contains(city)) {
            for (String s : dictionary) {
                if (description.contains(s)) {
                    return true;
                }
            }
        }

        return false;
    }

    public String getName(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = matcher.group("name");
            return match.substring(0, match.length() - 1).trim();
        }
        throw new IllegalArgumentException("Name not found");
    }

    public String getAge(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = matcher.group("age");
            return match.substring(0, match.length() - 1).trim();
        }
        throw new IllegalArgumentException("Age not found");
    }

    public String getCity(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = matcher.group("city");
            return match.trim();
        }
        throw new IllegalArgumentException("City not found");
    }

    public String getText(String text) {
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            String match = matcher.group("text");
            return match;
        }
        return null;
    }

}
