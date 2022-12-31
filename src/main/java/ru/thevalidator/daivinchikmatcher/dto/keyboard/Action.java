/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.daivinchikmatcher.dto.keyboard;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.annotation.processing.Generated;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"type",
"payload",
"label"
})
@Generated("jsonschema2pojo")
public class Action {

@JsonProperty("type")
private String type;
@JsonProperty("payload")
private String payload;
@JsonProperty("label")
private String label;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("type")
public String getType() {
return type;
}

@JsonProperty("type")
public void setType(String type) {
this.type = type;
}

@JsonProperty("payload")
public String getPayload() {
return payload;
}

@JsonProperty("payload")
public void setPayload(String payload) {
this.payload = payload;
}

@JsonProperty("label")
public String getLabel() {
return label;
}

@JsonProperty("label")
public void setLabel(String label) {
this.label = label;
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
