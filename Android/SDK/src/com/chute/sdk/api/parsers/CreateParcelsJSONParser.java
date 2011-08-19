package com.chute.sdk.api.parsers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;

import com.chute.examples.kitchensink.db.DB;
import com.chute.examples.kitchensink.models.PhotoDataModel;

public class CreateParcelsJSONParser extends AbstractJSONParser {

    public CreateParcelsJSONParser(Context context) {
	super(context);
    }

    @Override
    public void parse(final String response) throws JSONException {
	JSONObject obj = new JSONObject(response);
	JSONArray array = obj.getJSONArray("uploads");
	ArrayList<PhotoDataModel> list = new ArrayList<PhotoDataModel>();
	for (int i = 0; i < array.length(); i++) {
	    obj = array.getJSONObject(i);
	    ContentValues values = new ContentValues();
	    values.put(DB.ImagesColumns.STATUS, obj.getString("status"));
	    values.put(DB.ImagesColumns.ASSET_ID, obj.getString("asset_id"));
	    getContext().getContentResolver().update(DB.ImagesColumns.CONTENT_URI, values,
		    DB.ImagesColumns.FILEPATH + "=?", new String[] { obj.getString("file_path") });
	    list.add(new PhotoDataModel(obj.getString("asset_id"), obj.getString("file_path"), obj
		    .getString("status")));
	}
	/*if (list.size() > 0) {
	    Intent intent = new Intent(getContext(), UploadImagesService.class);
	    intent.putParcelableArrayListExtra(UploadImagesService.EXTRA_IMAGES_LIST, list);
	    getContext().startService(intent);
	}*/
    }

}
