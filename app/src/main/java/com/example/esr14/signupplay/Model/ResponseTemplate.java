package com.example.esr14.signupplay.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by ESR14 on 26/05/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseTemplate{

//    private String averageDistance;

    private List<BusStop> content;

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
    public List<BusStop> getContent() {
        return content;
    }

    public void setContent(List<BusStop> content) {
        this.content = content;
    }

    public ResponseTemplate(List<BusStop> content) {

  //      this.averageDistance = averageDistance;
        this.content = content;
    }
}
