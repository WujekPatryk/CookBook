package com.example.patryk.cookbook.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.patryk.cookbook.data.Comments;
import com.example.patryk.cookbook.data.CommentsList;
import com.example.patryk.cookbook.itemView.CommentItemView;
import com.example.patryk.cookbook.itemView.CommentItemView_;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.List;

@EBean
public class CommentListAdapter extends BaseAdapter {

    @RootContext
    Context context;

    List<Comments> comments = new ArrayList<Comments>();

    public CommentListAdapter() {
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comments getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CommentItemView commentItemView;
        if (convertView == null) {
            commentItemView = CommentItemView_.build(context);
        } else {
            commentItemView = (CommentItemView) convertView;
        }

        commentItemView.bind(getItem(position));

        return commentItemView;
    }

    public void update(CommentsList commentsList) {
        comments.clear();
        comments.addAll(commentsList.records);
        notifyDataSetChanged();
    }
}

