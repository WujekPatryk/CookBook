package com.example.patryk.cookbook.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Picture implements Serializable {

    public Integer id;
    public Integer ownerId;
    public String base64bytes;
    public Integer recipeId;

}