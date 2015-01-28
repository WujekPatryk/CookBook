package com.example.patryk.cookbook;

import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestRecipeBackgroundTask {

    @RootContext
    AddActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    void addCookBookEntry(User user, Recipe recipe) {
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            restClient.setHeader("X-Dreamfactory-Session-Token", user.sessionId);
            restClient.addCookBookEntry(recipe);
            publishResult(recipe);
        } catch (Exception e) {
            publishError(e);
        }
    }

    @UiThread
    void publishResult(Recipe recipe) {
        activity.confirmSuccess(recipe);
    }

    @UiThread
    void publishError(Exception e) {
        activity.showError(e);
    }

}
