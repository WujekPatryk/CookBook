package com.example.patryk.cookbook;

import com.example.patryk.cookbook.data.Comments;
import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestCommentsAddBackgroundTask {
    @RootContext
    CommentsActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    void addComment(User user, Recipe recipe, String text){
        try{
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);

            Comments comments = new Comments();
            comments.recipeId = recipe.id;
            comments.ownerId = user.id;
            comments.text = text;

            restClient.addComment(comments);
        } catch(Exception e){
            publishError(e);
        }

    }

    @UiThread
    void publishError(Exception e){
        activity.showError(e);
    }
}
