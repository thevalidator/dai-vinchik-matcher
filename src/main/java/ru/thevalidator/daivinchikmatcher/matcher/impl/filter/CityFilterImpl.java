/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.matcher.impl.filter;

import java.util.Set;
import ru.thevalidator.daivinchikmatcher.dto.Profile;
import ru.thevalidator.daivinchikmatcher.matcher.Filter;
import ru.thevalidator.daivinchikmatcher.util.FileUtil;


public class CityFilterImpl implements Filter {
    
    private final Set<String> cities;

    public CityFilterImpl() {
        this.cities = FileUtil.readDict("cities.dict");
    }

    @Override
    public boolean isFiltered(Profile profile) {
        return cities.contains(profile.getCity());
    }

}
