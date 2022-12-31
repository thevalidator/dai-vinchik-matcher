/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.dto.keyboard;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "one_time",
    "buttons"
})
@Generated("jsonschema2pojo")
public class Keyboard {

    @JsonProperty("one_time")
    private Boolean oneTime;
    @JsonProperty("buttons")
    private List<List<Button>> buttons = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("one_time")
    public Boolean getOneTime() {
        return oneTime;
    }

    @JsonProperty("one_time")
    public void setOneTime(Boolean oneTime) {
        this.oneTime = oneTime;
    }

    @JsonProperty("buttons")
    public List<List<Button>> getButtons() {
        return buttons;
    }

    @JsonProperty("buttons")
    public void setButtons(List<List<Button>> buttons) {
        this.buttons = buttons;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
