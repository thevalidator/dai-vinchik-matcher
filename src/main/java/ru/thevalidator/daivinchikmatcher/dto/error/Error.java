/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.daivinchikmatcher.dto.error;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.google.gson.annotations.SerializedName;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "error_code",
    "error_msg",
    "request_params",
    "captcha_sid",
    "captcha_img"
})
@Generated("jsonschema2pojo")
public class Error {

    @JsonProperty("error_code")
    private Integer errorCode;
    @JsonProperty("error_msg")
    private String errorMsg;
    @JsonProperty("request_params")
    private List<RequestParam> requestParams = null;
    @JsonProperty("captcha_sid")
    private String captchaSid;
    @JsonProperty("captcha_img")
    private String captchaImg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("error_code")
    public Integer getErrorCode() {
        return errorCode;
    }

    @JsonProperty("error_code")
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @JsonProperty("error_msg")
    public String getErrorMsg() {
        return errorMsg;
    }

    @JsonProperty("error_msg")
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @JsonProperty("request_params")
    public List<RequestParam> getRequestParams() {
        return requestParams;
    }

    @JsonProperty("request_params")
    public void setRequestParams(List<RequestParam> requestParams) {
        this.requestParams = requestParams;
    }

    @JsonProperty("captcha_sid")
    public String getCaptchaSid() {
        return captchaSid;
    }

    @JsonProperty("captcha_sid")
    public void setCaptchaSid(String captchaSid) {
        this.captchaSid = captchaSid;
    }

    @JsonProperty("captcha_img")
    public String getCaptchaImg() {
        return captchaImg;
    }

    @JsonProperty("captcha_img")
    public void setCaptchaImg(String captchaImg) {
        this.captchaImg = captchaImg;
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
