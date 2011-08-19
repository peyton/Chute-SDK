package com.chute.sdk.api.assets;

import java.util.List;

import com.chute.examples.kitchensink.models.SingleChuteAsset;

public interface OnLoadAssetsComplete {
    public void onSuccess(List<SingleChuteAsset> assets);
}
