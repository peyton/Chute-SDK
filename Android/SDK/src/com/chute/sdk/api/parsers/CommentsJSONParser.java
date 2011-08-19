package com.chute.sdk.api.parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.chute.examples.kitchensink.models.CommentsResponse;
import com.chute.examples.kitchensink.models.User;

public class CommentsJSONParser extends AbstractJSONParser {
    final ArrayList<CommentsResponse> comments = new ArrayList<CommentsResponse>();

    public CommentsJSONParser(Context context) {
	super(context);
    }

    @Override
    public void parse(String response) throws JSONException {
	try {
	    JSONArray obj = new JSONArray(response);
	    CommentsResponse comment;
	    for (int i = 0; i < obj.length(); i++) {
		comment = new CommentsResponse();
		comment.setComment(obj.getJSONObject(i).getString("comment"));
		comment.setId(obj.getJSONObject(i).getString("id"));
		comment.setUser(new User(
			obj.getJSONObject(i).getJSONObject("user").getString("id"), obj
				.getJSONObject(i).getJSONObject("user").getString("name"), obj
				.getJSONObject(i).getJSONObject("user").getString("avatar")));
		comments.add(comment);
	    }
	} catch (JSONException e) {
	    JSONObject obj = new JSONObject(response);
	    CommentsResponse comment;
	    comment = new CommentsResponse();
	    comment.setComment(obj.getString("comment"));
	    comment.setId(obj.getString("id"));
	    comment.setUser(new User(obj.getJSONObject("user").getString("id"), obj.getJSONObject(
		    "user").getString("name"), obj.getJSONObject("user").getString("avatar")));
	    comments.add(comment);
	}
    }

    public ArrayList<CommentsResponse> getComments() {
	return comments;
    }

}
