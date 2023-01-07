/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.matcher.impl.filter;

import ru.thevalidator.daivinchikmatcher.dto.Profile;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;


public class AgeFilterImpl implements Filter {
    
    private final int[] ages;

    public AgeFilterImpl() {
        this.ages = FileUtil.readAgeDict("ages.dict");
    }

    @Override
    public boolean isFiltered(Profile profile) {
        Integer age = Integer.valueOf(profile.getAge());
        return ages[0] <= age && age <= ages[1];
    }

}
