package com.example.patryk.cookbook.itemView;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.patryk.cookbook.R;
import com.example.patryk.cookbook.data.Comments;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

@EViewGroup(R.layout.list_comment)
public class CommentItemView extends RelativeLayout {
    @ViewById
    TextView ownerId;
    @ViewById
    TextView created;
    @ViewById
    TextView text;


    public CommentItemView(Context context){
        super(context);
    }

    public void bind(Comments comments){

        ownerId.setText(String.valueOf(comments.ownerId));
        created.setText(comments.created);
        text.setText(comments.text);
    }
//        created.setText(recipe.created.toString());
}
