package com.example.patryk.cookbook.itemView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.patryk.cookbook.R;
import com.example.patryk.cookbook.data.Recipe;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.list_item)
public class RecipeItemView extends RelativeLayout {

    @ViewById
    TextView title;

    @ViewById
    TextView introduction;

    @ViewById
    ImageView photo;

    public RecipeItemView(Context context) {
        super(context);
    }

    public void bind(Recipe recipe) {
        title.setText(recipe.title);
        introduction.setText(recipe.introduction);
        if (recipe.pictureBytes != null) {
            decodeAndSetImage(recipe.pictureBytes, photo);
        } else {
            photo.setImageResource(R.drawable.food);
        }
    }

    private void decodeAndSetImage(String base64bytes, ImageView image) {
        // zamieĹ„ ciÄ…g tekstowy Base-64 na tablicÄ™ bajtĂłw
        byte[] decodedString = Base64.decode(base64bytes, Base64.DEFAULT);
        // utwĂłrz bitmapÄ™ na podstawie ciÄ…gu bajtĂłw z obrazem JPEG
        Bitmap decodedBytes = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        // wstaw bitmapÄ™ do komponentu ImageView awatara
        image.setImageBitmap(decodedBytes);
    }

}

