/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.matcher;

import ru.thevalidator.daivinchikmatcher.dto.Profile;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Filter {
    
    boolean isFiltered(Profile profile);

}
