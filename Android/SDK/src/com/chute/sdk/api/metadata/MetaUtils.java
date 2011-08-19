package com.chute.sdk.api.metadata;

public class MetaUtils {
    @SuppressWarnings("unused")
    private static final String TAG = MetaUtils.class.getSimpleName();

    public static String resolveMetaType(MetaType metaType) {
	switch (metaType) {
	case ASSETS:
	    return "assets";
	case PARCELS:
	    return "parcels";
	case ME:
	    return "me";
	case CHUTES:
	    return "chutes";
	default:
	    throw new RuntimeException("Meta type not mapped");
	}

    }
}
