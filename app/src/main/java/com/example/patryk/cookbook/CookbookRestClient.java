package com.example.patryk.cookbook;

import com.example.patryk.cookbook.data.Comments;
import com.example.patryk.cookbook.data.CommentsList;
import com.example.patryk.cookbook.data.CookBook;
import com.example.patryk.cookbook.data.EmailAndPassword;
import com.example.patryk.cookbook.data.Like;
import com.example.patryk.cookbook.data.LikeList;
import com.example.patryk.cookbook.data.Picture;
import com.example.patryk.cookbook.data.PictureList;
import com.example.patryk.cookbook.data.Recipe;
import com.example.patryk.cookbook.data.ResultWithId;
import com.example.patryk.cookbook.data.User;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.RequiresHeader;
import org.androidannotations.annotations.rest.Rest;
import org.androidannotations.api.rest.RestClientHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


@Rest(rootUrl = "http://pegaz.wzr.ug.edu.pl:8080/rest",
        converters = { MappingJackson2HttpMessageConverter.class })
@RequiresHeader({"X-Dreamfactory-Application-Name"})
public interface CookbookRestClient extends RestClientHeaders {

    @Get("/db/recipes")
    CookBook getCookBook();

    @Post("/user/session")
    User login(EmailAndPassword emailAndPassword);

    @Post("/user/register/?login=true")
    User register(EmailAndPassword emailAndPassword);

    @RequiresHeader({"X-Dreamfactory-Session-Token","X-Dreamfactory-Application-Name"})
    @Post("/db/recipes")
    void addCookBookEntry(Recipe recipe);

    @Get("/db/comments?filter={path}")
    CommentsList getComment(String path);

    @RequiresHeader({"X-Dreamfactory-Application-Name", "X-Dreamfactory-Session-Token"})
    @Post("/db/comments")
    void addComment(Comments comments);

    @Get("/db/likes?filter={path}")
    LikeList getLike(String path);

    @RequiresHeader({"X-Dreamfactory-Application-Name","X-Dreamfactory-Session-Token"})
    @Post("/db/likes")
    void giveLike(Like like);

    @Get("/db/pictures?filter={path}")
    PictureList getPictureListById(String path);

    @RequiresHeader({"X-Dreamfactory-Application-Name", "X-Dreamfactory-Session-Token"})
    @Post("/db/pictures")
    ResultWithId addPicture(Picture picture);

}