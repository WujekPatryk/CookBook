package com.example.patryk.cookbook.data;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comments implements Serializable{
    public Integer id;
    public Integer recipeId;
    public Integer ownerId;
    public String text;
    public String created;
}