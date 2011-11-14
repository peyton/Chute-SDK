Setup
====

1. Create a new Android project or open an existing one.
2. Add the SDK library jar file to the build path.
3. Open the manifest and register the Authentication activity and the HTTP Service

    ```
     <activity
      android:name="com.chute.sdk.api.authentication.GCAuthenticationActivity"
      android:theme="@android:style/Theme.Light.NoTitleBar"></activity>    
     <service android:name="com.chute.sdk.api.GCHttpService"></service>
    ```
4. You need to add the required permissions to the maniifest:

    ```
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	<uses-permission android:name="android.permission.READ_PHONE_STATE" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
	<uses-permission android:name="android.permission.WAKE_LOCK" />
    ```
	
5. In your application use the following code to launch the Authentication screen

    <pre><code>
 GCAccount.getInstance(getApplicationContext()).startAuthenticationActivity(
	this, // Activity context
	AccountType.FACEBOOK, // TYPE of Account to authenticate
	"all_resources manage_resources profile resources", // Chute Permissions
	"http://getchute.com/oauth/callback", // Callback URL
	"ReplaceWithClientID", // Client id aquired from Chute
	"ReplaceWithClientSecret" // Client Secret aquired from chute
	);
    </code></pre>

6. This will return a result in onActivityResult according to the status of the sign in.
7. The Authentication token will be persisted and used from inside GCAccount in every request towards the Chute API.

Key Concepts
========


## Client
All Chute applications use OAuth and are referred to as 'Clients'


## Asset
Any photo or video managed by Chute


## Chute
A container for assets.  Chutes can be nested inside of each other.


## Parcel
A named collection of assets.  Whenever you upload assets, they are grouped into parcels.


## Bundle
An unnamed collection of assets.

## Request execution and callback

1. Every request can be either:
-synchronous (it executes in the same thread as the <code>execute()</code> method was called
-asynchronous (it executes in the Background and it is started by calling <code>executeAsync()</code>);

2. Every request can accept a custom response parser or use the default parser for each request type and a suitable callback which will return an object or a collection depending on the response type
3. The callback has 4 possible outcomes

	<pre>
	// returns the parsed response according to the parsers return type.
	
	<code>public void onSuccess(T responseData); </code>
    
	// it returns an object that will contain the request parameters, the URL, the headers and the Request Type (GET, POST, PUT, DELETE)
	// this happens if there was a timeout and the request didn't reach the server (usually due to connectivity issues)
    
	<code>public void onHttpException(GCHttpRequestParameters params, Throwable exception); </code>
	
	// this happens when the server didn't process the result correctly, it returns a HTTP Status code and an error message
    
	<code>public void onHttpError(int responseCode, String statusMessage);</code>
	
	// This happens when the parser didn't successfuly parse the response string, usually this requires adjustments on the client side and it is not recoverable by retries
	
	<code>public void onParserException(int responseCode, Throwable exception);</code>
	</pre>
Basic Tasks
=========

## Uploading Assets

You can use the folowing code to execute an upload request. This method needs an asset collection that will contain the paths to the photos that you want to upload

<pre><code>
GCAssets.upload(context, progressListener,
			parser,callback, localAssetCollection)
			.executeAsync();
			
</code></pre>

for progress updates you need to include a progressListener which is a class implementing the GCUploadProgressListener Interface. 
Important note: the Callback runs in the thread that is executing the request

it has 3 callbacks:

<pre><code>
public interface GCUploadProgressListener {
    /**
     * This is triggered when the 
     * 
     * @param assetId the id of the asset you are currently uploading
     * @param filepath the filepath of the asset
     * @param thumbnail a small thumbnail that will be created from the asset before the upload starts
     */
    public void onUploadStarted(String assetId, String filepath, final Bitmap thumbnail);

    /**
     * @param total the total size of the asset
     * @param uploaded the ammount of data uploaded
     */
    public void onProgress(int total, int uploaded);

    /**
     * This triggers when the upload has finished, it doesnt carry the information about the status of the upload request
     * @param assetId the id of the asset
     * @param filepath the filepath of the asset
     */
    public void onUploadFinished(String assetId, String filepath);
}
</code></pre>

To place a photo in a chute you need to create a parcel with that photo or a collection of photos <code>GCLocalAsset</code>.
It takes as an input a collection of local assets you want to create a parcel with and a collection of chutes you want the assets to be linked to.

<pre><code>

GCParcel.create(context, assets, chutes, new GCCreateParcelsUploadsListParser(),
		new GCParcelCreateCallback()).executeAsync();

</code></pre>

Chute doesn't require you to upload the same photo multiple times, so in the parcel creation callback you will get a response with just the assets that you still haven't uploaded.
		

## Displaying Assets

in the sdk we have included a way to asynchronously bind photos to imageViews.
To use this you need to folow a couple of steps to include and configure the Image Loader for your project

1. Create a Class Extending Application, define this class in the project manifest inside the application tag

    ```
	<application
		android:theme="@android:style/Theme.Black.NoTitleBar"
		android:icon="@drawable/icon"
		android:screenOrientation="portrait"
		android:label="@string/app_name"
		android:name=".app.MyExtendedApplicationClassName" >	
    ```
	
2. Use and modify the folowing code inside that class to create an instance of the ImageLoader, The Loader uses an sd card cache with a combination of an in-memory implementation

<pre><code>
 private static ImageLoader createImageLoader(Context context) {
	ImageLoader imageLoader = new ImageLoader(context, R.drawable.placeholder_image_small);
	imageLoader.setRequiredSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
		75, context.getResources().getDisplayMetrics()));
	return imageLoader;
    }

    private ImageLoader mImageLoader;

    @Override
    public void onCreate() {
	super.onCreate();
	mImageLoader = createImageLoader(this);
    }

    @Override
    public Object getSystemService(String name) {
	if (ImageLoader.IMAGE_LOADER_SERVICE.equals(name)) {
	    return mImageLoader;
	} else {
	    return super.getSystemService(name);
	}
    }
	</code></pre>
	
3.To use the loader to bind images you need to first get an instance of the loader:

<pre><code>
imageLoader = ImageLoader.get(context);
</code></pre>

4. Then call the displayImage method with the url modified for a custom sized photo

<pre><code>
imageLoader.displayImage(GCUtils.getCustomSizePhotoURL(url,100,100), imageView);	
</code></pre>

## Organizing Assets

Assets are organized in Chutes

To get all the assets for a specific chute you would use:

<pre><code>
GCChutes.Resources.assets(context, chuteId, new GCAssetListObjectParser(),
		new GCHttpCallback<GCAssetCollection>(){...});
</code></pre>

### Building Chutes

To create a chute execute 

<pre><code> GCChutes.createChute(Context context,
	    GCChuteModel chuteModel, GCHttpResponseParser<T> parser,
	    GCHttpCallback<T> callback);
		</code></pre>
		
In this request only the chute name is a mandatory parameter.


Social Tasks
==========


## Hearting Assets

To heart/unheart an asset
<pre><code>
GCHearts.set(Context context, String assetId,
	    boolean isHeart, GCHttpResponseParser<T> parser, GCHttpCallback<T> callback)
</code></pre>
To get a list of all asset ids that are hearted:
<pre><code>
GCHearts.get(Context context, String userId,
	    GCHttpResponseParser<T> parser, GCHttpCallback<T> callback)
</code></pre>		
## Commenting on Assets

To create a comment

<pre><code>
 GCComments.add(final Context context, final String chuteId,
	    final String assetId, final String comment, final GCHttpResponseParser<T> parser,
	    final GCHttpCallback<T> callback);
		</code></pre>
To get all comments for a specific asset:
<pre><code>
GCComments.get(final Context context, final String chuteId,
	    final String assetId, final GCHttpResponseParser<T> parser,
	    final GCHttpCallback<T> callback)
</code></pre>
		
		