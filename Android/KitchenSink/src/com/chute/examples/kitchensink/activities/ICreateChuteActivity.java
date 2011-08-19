package com.chute.examples.kitchensink.activities;

import android.widget.RadioGroup;

public interface ICreateChuteActivity {

    public void createNewChute();

    public void gatherDataFromScreen();

    public int getSelectedRadio(RadioGroup gr);
}
