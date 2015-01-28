package com.example.patryk.cookbook;

import com.example.patryk.cookbook.data.CommentsList;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.rest.RestService;

@EBean
public class RestCommentsBackgroundTask {
    @RootContext
    CommentsActivity activity;

    @RestService
    CookbookRestClient restClient;

    @Background
    void getComment(String path){
        try {
            restClient.setHeader("X-Dreamfactory-Application-Name", "cookbook");
            CommentsList commentsList = restClient.getComment(path);
            publishResult(commentsList);
        } catch (Exception e){
            publishError(e);
        }
    }

    @UiThread
    void publishResult(CommentsList commentsList){
        activity.updateComments(commentsList);
    }

    @UiThread
    void publishError(Exception e){
        activity.showError(e);
    }



}