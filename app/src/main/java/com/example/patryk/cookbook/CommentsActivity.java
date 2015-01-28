package com.example.patryk.cookbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.cookbook.adapter.CommentListAdapter;
import com.example.patryk.cookbook.data.Comments;
import com.example.patryk.cookbook.data.CommentsList;
import com.example.patryk.cookbook.data.LikeList;
import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

@EActivity(R.layout.activity_comments)
public class CommentsActivity extends ActionBarActivity {

    @Extra
    User user;

    @Extra
    Recipe recipe;

    @Bean
    @NonConfigurationInstance
    RestLikeBackgroundTask restLikeBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestCommentsBackgroundTask restCommentsBackgroundTask;

    @Bean
    @NonConfigurationInstance
    RestCommentsAddBackgroundTask restCommentsAddBackgroundTask;

    @Bean
    CommentListAdapter adapter;

    ProgressDialog ringProgressDialog;

    @ViewById
    ListView commentsList;

    @ViewById
    Button addComment;

    @ViewById
    Button like;

    @ViewById
    Button refreshComments;

    @ViewById
    EditText text;

    @ViewById
    TextView likes;

    @ViewById
    Button menu;

    @AfterViews
    void init() {
        commentsList.setAdapter(adapter);

        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Ładowanie...");
        ringProgressDialog.setIndeterminate(true);

        restCommentsBackgroundTask.getComment("recipeId=" + recipe.id.toString());
        restLikeBackgroundTask.getLike("recipeId=" + recipe.id.toString());

    }

    @Click(R.id.refreshComments)
    void refreshCommentsClicked(){
        restCommentsBackgroundTask.getComment("recipeId=" + recipe.id.toString());
    }

    @Click (R.id.addComment)
    void addCommentClicked(){
        if(user == null) {
            Intent intent2 = new Intent(this, LoginActivity_.class);
            startActivityForResult(intent2, 1);
            Toast.makeText(this, "Musisz się najpierw zalogować!", Toast.LENGTH_LONG).show();}

        else{
            if(!StringUtils.hasText(text.getText().toString())) {
                Toast.makeText(this,"Napisz coś!",Toast.LENGTH_LONG).show();
            } else {
                restCommentsAddBackgroundTask.addComment(user, recipe, text.getText().toString());
                Toast.makeText(this,"Dodano komentarz!",Toast.LENGTH_LONG).show();
                restCommentsBackgroundTask.getComment("recipeId=" + recipe.id.toString());
            }
            }
        }

    public void updateComments(CommentsList commentsList){
        if(commentsList != null) {
            adapter.update(commentsList);
            ringProgressDialog.dismiss();
        }
    }

    @Click (R.id.like)
    void likeClicked(){
        if(user == null) {
            Intent intent = new Intent(this, LoginActivity_.class);
            startActivityForResult(intent, 1);
            Toast.makeText(this, "Musisz się najpierw zalogować!", Toast.LENGTH_LONG).show();
        } else {
            restLikeBackgroundTask.giveLike(user, recipe);
            Toast.makeText(this, "Lubisz to :)", Toast.LENGTH_LONG).show();
            restLikeBackgroundTask.getLike("recipeId=" + Integer.toString(recipe.id));
        }
    }

    @Click(R.id.menu)
    void menuClicked(){
        MainActivity_.intent(this).user(user).start();
        this.finish();
    }

    public void updateLikes(LikeList likeList){
        if(likeList != null){
            likes.setText(Integer.toString(likeList.records.size()) + " osób polubiło ten przepis.");
        }
        ringProgressDialog.dismiss();

    }


    public void showError(Exception e){
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

    @OnActivityResult(1)
    protected void onActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            user = (User)data.getSerializableExtra("user");
        }
    }
}
