package com.chute.examples.kitchensink;


public interface IChuteKitchenSinkActivity {
    public void init();

    public void onChuteGroupChildClicked(int childPosition);

    public void onImageUploadGroupChildClicked(int childPosition);

    public void onBundleGroupChildClicked(int childPosition);

    public void onAuthenticationChildClicked(int childPosition);
}
