package com.example.patryk.cookbook;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.cookbook.data.Comments;
import com.example.patryk.cookbook.data.CookBook;
import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

@EActivity (R.layout.activity_add)
public class AddActivity extends ActionBarActivity {

    @Bean
    @NonConfigurationInstance
    RestRecipeBackgroundTask restRecipeBackgroundTask;

    @Extra
    User user;

    @ViewById
    EditText title;

    @ViewById
    EditText introduction;

    @ViewById
    EditText ingredients;

    @ViewById
    EditText steps;

    @ViewById
    EditText preparationMinutes;

    @ViewById
    EditText cookingMinutes;

    @ViewById
    EditText servings;

    @ViewById
    Button confirm;

    @ViewById
    Button menu;

    ProgressDialog ringProgressDialog;

    @AfterViews
    void init() {
        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Dodawanie przepisu...");
        ringProgressDialog.setIndeterminate(true);
    }

    @Click(R.id.menu)
    void menuClicked(){
        MainActivity_.intent(this).user(user).start();
        this.finish();
    }

    @Click
    void confirmClicked() {
        ringProgressDialog.show();

        if (user == null) {
            Intent addrecipe = new Intent(this, LoginActivity_.class);
            startActivityForResult(addrecipe, 1);
            Toast.makeText(this, "Musisz się najpierw zalogować!", Toast.LENGTH_LONG).show();
        }
        else {
            Recipe recipe = new Recipe();
            recipe.ownerId = user.id;
            recipe.title = title.getText().toString();
            recipe.introduction = introduction.getText().toString();
            recipe.ingredients = ingredients.getText().toString();
            recipe.steps = steps.getText().toString();
            recipe.preparationMinutes = preparationMinutes.getText().toString();
            recipe.cookingMinutes = cookingMinutes.getText().toString();
            recipe.servings = servings.getText().toString();


            if ((title.length() == 0) || (ingredients.length() == 0) || (steps.length() == 0) || (servings.length() == 0)) {
                ringProgressDialog.dismiss();
                Toast.makeText(this, "Wypełnij wszystkie pola!", Toast.LENGTH_LONG).show();
            } else {
                restRecipeBackgroundTask.addCookBookEntry(user, recipe);
            }
        }
    }
    public void confirmSuccess(Recipe recipe) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, "Dodano przepis!", Toast.LENGTH_LONG).show();
        MainActivity_.intent(this).recipe(recipe).user(user).start();
        this.finish();
    }

    public void showError(Exception e) {
        ringProgressDialog.dismiss();
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
