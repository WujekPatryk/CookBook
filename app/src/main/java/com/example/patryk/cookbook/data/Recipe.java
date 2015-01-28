package com.example.patryk.cookbook.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipe implements Serializable, Comparable<Recipe>{

    public String title;
    public String introduction;
    public String ingredients;
    public String steps;
    public String created;
    public String preparationMinutes;
    public String cookingMinutes;
    public String servings;
    public Integer ownerId;
    public Integer id;

    @JsonProperty("session_id")
    public String sessionId;

    @JsonIgnore
    public String pictureBytes;

    @Override
    public int compareTo(Recipe recipe) {

        if(id > recipe.id){
            return -1;
        }
        else if(id == recipe.id){
            return 0;

        }
        else
        {
            return 1;
        }
    }


}

