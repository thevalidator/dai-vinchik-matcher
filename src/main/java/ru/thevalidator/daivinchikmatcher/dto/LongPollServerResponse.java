/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.dto;

import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class LongPollServerResponse {
    
    private Integer ts;
    private List<List<Object>> updates;

    public LongPollServerResponse() {
    }

    public Integer getTs() {
        return ts;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public List<List<Object>> getUpdates() {
        return updates;
    }

    public void setUpdates(List<List<Object>> updates) {
        this.updates = updates;
    }

}
