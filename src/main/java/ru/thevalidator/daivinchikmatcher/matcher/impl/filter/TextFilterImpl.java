/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.matcher.impl.filter;

import java.util.Set;
import ru.thevalidator.daivinchikmatcher.dto.Profile;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.property.Data;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;


public class TextFilterImpl implements Filter {
    
    private final Set<String> words;

    public TextFilterImpl() {
        this.words = FileUtil.readDict(Data.WORDS);
    }

    @Override
    public boolean isFiltered(Profile profile) {
        String text = profile.getText();
        for (String word : words) {
            if (text.contains(word)) {
                return true;
            }
        }
        
        return false;
    }

}
