package com.example.esr14.signupplay.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by ESR14 on 26/05/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTemplate{

//    private String averageDistance;
    @JsonProperty("content")
    private List<String> content;

    public ResponseTemplate(){

    }
/*
    public String getAverageDistance() {
        return averageDistance;
    }

    public void setAverageDistance(String averageDistance) {
        this.averageDistance = averageDistance;
    }
*/
    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public ResponseTemplate(List<String> content) {

  //      this.averageDistance = averageDistance;
        this.content = content;
    }
}
