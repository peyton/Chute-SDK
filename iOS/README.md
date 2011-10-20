Setup
====

First copy the SDK files into your project.  Find the GCConstants.h file located at SDK/Classes/Core and enter your OAuth information.

``` Objective-C
    #define kOAuthRedirectURL               @"http://getchute.com/oauth/callback"
    #define kOAuthRedirectRelativeURL       @"/oauth/callback"
    #define kOAuthClientID                  @"xxxxxxxxxxxxxxxxxxxxxxxx"
    #define kOAuthClientSecret              @"xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"

    #define kOAuthPermissions               @"all_resources manage_resources profile resources"

    #define kOAuthTokenURL                  @"http://getchute.com/oauth/access_token"
```

  You can also adjust which service your users will use to sign in.
  
``` Objective-C
    // Set which service is to be used
    // 0 - Facebook
    // 1 - Evernote
    // 2 - Chute
    // 3 - Twitter
    // 4 - Foursquare

    #define kSERVICE 0
```
  
At this point your application will be ready to use the Chute SDK.  Simply `#import "GetChute.h"` in any classes that will be accessing the SDK.

You can also find prebuilt customizable drop in components [here](https://github.com/chute/chute-ios-components).  You can pick and choose which components you want to use and it's a simple way to get working with chute quickly and see the SDK in action.

Key Concepts
========

## Client
All Chute applications use OAuth and are referred to as 'Clients'

## Asset
Any photo or video managed by Chute

## Chute
A container for assets.  Chutes can be nested inside of each other.

## Parcel
A named collection of assets.  Whenever you upload assets, they are grouped into parcels.

## Response
Many methods return a response object.  This tells you whether the API call succeeded and includes an error if it didn't.  The response object also has precreated objects if there were any returned as well as the raw JSON string and the native objective-C decoding of that string.  This allows you to easily get the response in whatever format is most convenient for your project.


Basic Tasks
=========

## Uploading Assets
You upload assets using a parcel.  You can use it in conjunction with the GCUploader class to queue uploads in the background, or you can upload directly from the parcel.

The following code will queue an array of assets to upload to an array of chutes in the background.

``` Objective-C
    GCParcel *parcel = [GCParcel objectWithAssets:_assets andChutes:_chutes];
    [[GCUploader sharedUploader] addParcel:parcel];
```

If you want to perform the upload now or with a custom completion block you can use the following code.

``` Objective-C
    GCParcel *parcel = [GCParcel objectWithAssets:_assets andChutes:_chutes];
    [parcel startUploadWithTarget:self andSelector:@selector(parcelCompleted)];
```

You may also set these to to nil if you don't wish have any completion behavior.

## Displaying Assets

Each asset has a method to retrieve a thumbnail.  You can access the thumbnail by simply calling `[_asset thumbnail]`, which returns a UIImage that is formatted for 75x75 pixels.  

To retrieve a custom sized image you can call `[_asset imageForWidth:(NSUInteger)width andHeight:(NSUInteger)height]` which returns an image formatted to the given dimensions.  This method runs in the foreground so it may be somewhat slow.  You can also call `[_asset imageForWidth:(NSUInteger)width andHeight:(NSUInteger)height inBackgroundWithCompletion:(void (^)(UIImage *))aResponseBlock]` to get the image in the background and run a response block on completion.  For example

``` Objective-C
    UIImageView *v = [[[UIImageView alloc] init] autorelease];
    [_asset imageForWidth:320 andHeight:480 inBackgroundWithCompletion:^(UIImage *temp){
        [v setImage:temp];
    }];
```

## Organizing Assets

Chutes are used for organizing assets.  Retriving the assets for a chute is simple with the following methods

```Objective-C
    - (GCResponse *) assets;
    - (void) assetsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;
```

Assets can also be organized into parcels.  Retrieving a parcel's assets is even easier.  Each parcel has an array of assets so to get a parcel's assets you simply call `[_parcel assets]`.

## Associating Your Data with Assets

Many chute components can have metadata associated with them.  This metadata is specific to your application.  There are several methods for setting the metadata.  They are

``` Objective-C
    - (BOOL) setMetaData:(NSDictionary *) metaData;
    - (void) setMetaData:(NSDictionary *) metaData inBackgroundWithCompletion:(GCBoolBlock) aBoolBlock;

    - (BOOL) setMetaData:(NSString *) data forKey:(NSString *) key;
    - (void) setMetaData:(NSString *) data forKey:(NSString *) key inBackgroundWithCompletion:(GCBoolBlock) aBoolBlock;
```

The first two methods can set multiple values by using a dictionary.  The second two are for setting a single value for a key.

There are also several methods for retrieving metadata.  There are two methods for retrieving all your metadata and two for simply getting a value for a single key.  These methods are

``` Objective-C
    - (GCResponse *) getMetaData;
    - (void) getMetaDataInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;

    - (id) getMetaDataForKey:(NSString *) key;
    - (void) getMetaDataForKey:(NSString *) key inBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;
```

There are also methods for retrieving all objects that have a specific key/value pair.  These are

``` Objective-C
    + (GCResponse *) searchMetaDataForKey:(NSString *) key andValue:(NSString *) value;
    + (void) searchMetaDataForKey:(NSString *) key andValue:(NSString *) value inBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;
```

Finally you can also delete your metadata for an object.  You do this using one of the following methods

``` Objective-C
    - (BOOL) deleteMetaData;
    - (void) deleteMetaDataInBackgroundWithCompletion:(GCBoolBlock) aBoolBlock;

    - (BOOL) deleteMetaDataForKey:(NSString *) key;
    - (void) deleteMetaDataForKey:(NSString *) key inBackgroundWithCompletion:(GCBoolBlock) aBoolBlock;
```

The first set delete all metadata and the second set deletes meta data for a specific key.


Social Tasks
==========

## Hearting Assets

Hearting and unhearting an asset is simple.  You just call one of the following methods

``` Objective-C
    - (GCResponse *) heart;
    - (void) heartInBackgroundWithCompletion:(GCBoolErrorBlock) aBoolErrorBlock;

    - (GCResponse *) unheart;
    - (void) unheartInBackgroundWithCompletion:(GCBoolErrorBlock) aBoolErrorBlock;
```

You can also check if an asset is hearted by calling `[_asset isHearted]`.  This method checks against your hearted assets which must be loaded by calling `[[GCAccount sharedManager] loadHeartedAssets]`.  To get a correct response when checking if an asset is hearted it is recommended that you call this function once when your app loads and any time you heart or unheart an asset.

## Commenting on Assets

You can retrieve all comments for an asset by calling one of two methods

``` Objective-C
    - (GCResponse *) comments;
    - (void) commentsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;
```

There are also two methods for posting a comment to an asset

``` Objective-C
    - (GCResponse *) addComment:(NSString *) _comment;
    - (void) addComment:(NSString *) _comment inBackgroundWithCompletion:(GCResponseBlock) aResponseBlock;
```

Both of these rely on the asset's parentID to be set.  If the asset is retrieved from a chute then this as already set, however if you are retrieving the asset from a parcel you need to set it manually since there could be multiple chutes that the asset was uploaded to.  You can set it by calling `[_asset setParentID:[_chute objectID]]`.
