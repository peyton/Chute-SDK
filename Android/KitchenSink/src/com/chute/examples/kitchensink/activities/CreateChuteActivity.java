package com.chute.examples.kitchensink.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.chute.examples.kitchensink.R;
import com.chute.examples.kitchensink.models.DataCreateChuteRequest.CreateChute;
import com.chute.examples.kitchensink.models.FormMembersContactEmailInfo;
import com.chute.sdk.api.chutes.ChutesCreateOrUpdate;
import com.chute.sdk.api.chutes.ChutesCreateOrUpdate.OnChuteCreated;

public class CreateChuteActivity extends Activity implements OnClickListener, ICreateChuteActivity,
	OnChuteCreated {
    @SuppressWarnings("unused")
    private static final String TAG = CreateChuteActivity.class.getSimpleName();
    public CreateChute chute;
    private ChutesCreateOrUpdate taskCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.create_chute_activity);
	init();
    }

    public void init() {
	Button save = (Button) findViewById(R.id.ButtonSave);
	save.setOnClickListener(this);
	Button cancel = (Button) findViewById(R.id.ButtonCancel);
	cancel.setOnClickListener(this);
    }

    public CreateChute getCreateChute() {
	if (chute == null) {
	    chute = new CreateChute();
	}
	return chute;
    }

    @Override
    public void onClick(View v) {
	switch (v.getId()) {
	case R.id.ButtonSave:
	    createNewChute();
	    break;
	case R.id.ButtonCancel:
	    CreateChuteActivity.this.finish();
	    break;
	}
    }

    @Override
    public void createNewChute() {
	gatherDataFromScreen();
	if (getCreateChute().getName().contentEquals("")) {
	    Toast.makeText(getApplicationContext(), R.string.create_chute_toast_chute_name,
		    Toast.LENGTH_LONG).show();
	} else {
	    try {
		// By adding an ArrayList<FormMembersContactEmailInfo>() you can
		// add email addresses of
		// users that you want to invite to chute and grant them
		// permission to view the chute.
		if (taskCreate == null || taskCreate.getStatus() == Status.FINISHED) {
		    taskCreate = (ChutesCreateOrUpdate) new ChutesCreateOrUpdate(
			    CreateChuteActivity.this, getCreateChute(),
			    new ArrayList<FormMembersContactEmailInfo>(), CreateChuteActivity.this)
			    .execute();
		}
	    } catch (NullPointerException e) {
		Log.d(TAG, "", e);
	    }
	}
    }

    @Override
    public void gatherDataFromScreen() {

	/**
	 * if you add an existing chute id then that one will be updated with
	 * the chosen data instead of creating a new chute.
	 * 
	 * ex: getCreateChute().setId(7);
	 */

	EditText chuteName = (EditText) findViewById(R.id.EditTextChuteName);
	getCreateChute().setName(chuteName.getText().toString());

	// Init the radio Groups
	RadioGroup view = (RadioGroup) findViewById(R.id.radioGroup1);
	getCreateChute().setPermission_view(getSelectedRadio(view));
	getCreateChute().setPermission_add_comments(getSelectedRadio(view));

	CheckBox cb = (CheckBox) findViewById(R.id.checkBox1);
	getCreateChute().setPermission_add_photos(cb.isChecked() ? 1 : 0);
	cb = (CheckBox) findViewById(R.id.checkBox2);
	getCreateChute().setPermission_add_members(cb.isChecked() ? 1 : 0);
    }

    @Override
    public int getSelectedRadio(RadioGroup gr) {
	switch (gr.getCheckedRadioButtonId()) {
	case R.id.radio0:
	    return 0;
	case R.id.radio1:
	    return 1;
	case R.id.radio2:
	    return 2;
	}
	return 0;
    }

    @Override
    public void onSuccess(int id) {
	Toast.makeText(getApplicationContext(), R.string.success, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFail() {
	Toast.makeText(getApplicationContext(), R.string.fail, Toast.LENGTH_SHORT).show();
    }

}
