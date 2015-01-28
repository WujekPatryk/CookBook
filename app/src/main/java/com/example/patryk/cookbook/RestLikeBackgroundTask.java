package com.example.patryk.cookbook;

import com.example.patryk.cookbook.data.Like;
import com.example.patryk.cookbook.data.LikeList;
import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestLikeBackgroundTask {
    @RootContext
    CommentsActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    public void getLike(String path){
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            LikeList likeList = restClient.getLike(path);
            publishResult(likeList);
        } catch (Exception e){
            publishError(e);
        }
    }

    @Background
    public void giveLike(User user, Recipe recipe){
        try{
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);


            Like like = new Like();
            like.recipeId = recipe.id;
            like.ownerId = user.id;

            restClient.giveLike(like);

            getLike("recipeId=" + Integer.toString(recipe.id));
        } catch(Exception e){
            publishError(e);
        }
    }

    @UiThread
    void publishResult(LikeList likeList){
        activity.updateLikes(likeList);
    }

    @UiThread
    void publishError(Exception e){
        activity.showError(e);
    }
}

