package com.example.patryk.cookbook;


import com.example.patryk.cookbook.data.CookBook;
import com.example.patryk.cookbook.data.Picture;
import com.example.patryk.cookbook.data.PictureList;
import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

import java.util.Collections;

@EBean
public class RestBackgroundTask {

    @RootContext
    MainActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    void getCookBook() {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            CookBook cookBook = restClient.getCookBook();
            Collections.reverse(cookBook.records);
            for(Recipe recipe : cookBook.records){
                PictureList pictureList = restClient.getPictureListById("recipeId="+recipe.id);
                if(pictureList.pictureList.size() > 0){
                    recipe.pictureBytes = pictureList.pictureList.get(0).base64bytes;
                }
                publishResult(cookBook);
            }
        }
        catch (Exception e) {
            publishError(e);
        }
    }


    @UiThread
        void publishResult(CookBook cookBook) {
       activity.update(cookBook);
        }


    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }

}
