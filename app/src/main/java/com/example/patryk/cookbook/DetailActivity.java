package com.example.patryk.cookbook;

import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;


@EActivity (R.layout.activity_detail)
public class DetailActivity extends ActionBarActivity {

    @Extra
    Recipe recipe;

    @Extra
    User user;

    @ViewById
    TextView title;

    @ViewById
    TextView introduction;

    @ViewById
    TextView ingredients;

    @ViewById
    TextView steps;

    @ViewById
    TextView created;

    @ViewById
    TextView preparationMinutes;

    @ViewById
    TextView cookingMinutes;

    @ViewById
    TextView servings;

    @ViewById
    Button menu;

    @AfterViews
    void init (){

        title.setText(recipe.title);
        introduction.setText(recipe.introduction);
        ingredients.setText(recipe.ingredients);
        steps.setText(recipe.steps);
        created.setText(recipe.created);
        preparationMinutes.setText(recipe.preparationMinutes);
        cookingMinutes.setText(recipe.cookingMinutes);
        servings.setText(recipe.servings);
    }

   @Click(R.id.comment)
    void commentClicked(){
       CommentsActivity_.intent(this).recipe(recipe).user(user).start();
   }

   @Click(R.id.menu)
    void menuClicked(){
       MainActivity_.intent(this).user(user).start();
   }
}
