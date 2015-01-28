package com.example.patryk.cookbook.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CommentsList {
    @JsonProperty("record")
    public List<Comments> records = new ArrayList<Comments>();
}