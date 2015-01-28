package com.example.patryk.cookbook;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.patryk.cookbook.adapter.RecipeListAdapter;
import com.example.patryk.cookbook.data.CookBook;
import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.NonConfigurationInstance;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

@EActivity (R.layout.activity_main)
public class MainActivity extends ActionBarActivity {

    @Extra
    Recipe recipe;

    @Extra
    User user;

    @ViewById
    Button login;

    @ViewById
    ListView list;

    @ViewById
    Button showList;

    @Bean
    RecipeListAdapter adapter;

    @Bean
    @NonConfigurationInstance
    RestBackgroundTask restBackgroundTask;

    ProgressDialog ringProgressDialog;

    @AfterViews
    void init() {
        ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage("Ładowanie listy przepisów...");
        ringProgressDialog.setIndeterminate(true);
    }

    @Click(R.id.showList)
    void showListClicked() {
        ringProgressDialog.show();
        list.setAdapter(adapter);
        restBackgroundTask.getCookBook();
    }

    @ItemClick
    void listItemClicked(Recipe recipe) {
        DetailActivity_.intent(this).recipe(recipe).user(user).start();
    }

    public void update(CookBook cookBook) {
        ringProgressDialog.dismiss();
        adapter.update(cookBook);
    }

    public void showError(Exception e) {
        ringProgressDialog.dismiss();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        e.printStackTrace();
    }

    @Click(R.id.add)
    void addClicked() {
        if (user == null) {
            Intent intent = new Intent(this, LoginActivity_.class);
            startActivityForResult(intent, 1);
            Toast.makeText(this, "Musisz się najpierw zalogować!", Toast.LENGTH_LONG).show();
        } else {
            AddActivity_.intent(this).user(user).start();
        }
    }
    @OnActivityResult(1)
    protected void onActivityResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            user = (User)data.getSerializableExtra("user");
        }
    }
}

