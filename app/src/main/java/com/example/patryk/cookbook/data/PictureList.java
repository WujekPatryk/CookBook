package com.example.patryk.cookbook.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PictureList {

    @JsonProperty("record")
    public List<Picture> pictureList = new ArrayList<Picture>();


}
