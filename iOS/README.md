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
Many methods return a response object.  This tells you whether the API call succeeded and includes an error if it didn't.  The response object also has pre-created objects if there were any returned as well as the raw JSON string and the native objective-C decoding of that string.  This allows you to easily get the response in whatever format is most convenient for your project.


Basic Tasks
=========

## Uploading Assets
You upload assets using a parcel.  To perform an upload you first need to create an array of assets you want to upload and an array of chutes you want to upload the assets to.  Then you initialize a parcel with those arrays and either add it to the GCUploader to queue in the background or tell the parcel to begin uploading.  If you have the parcel handle the uploading you must retain it until uploading completes.  If you use the uploader it handles the memory management for the parcel.

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

You may also set the target and selector to nil if you don't wish to have any completion behavior.

## Displaying Assets

Each asset has a method to retrieve a thumbnail or a custom size image.  You can access the thumbnail by simply calling `[_asset thumbnail]`, which returns a UIImage that is formatted for 75x75 pixels.  Custom sized images can be accessed in the foreground or background

###In the Foreground

You can retrieve a UIImage formatted to a custom size in the foreground.  Keep in mind that doing so will block the UI until the image is downloaded.  This will however be quick if you are loading an asset from the device's camera roll.  You can get an image this way with the following code

``` Objective-C
    UIImageView *v = [[[UIImageView alloc] init] autorelease];
    UIImage *temp = [_asset imageForWidth:320 andHeight:480];
    [v setImage:temp];
```

###In the Background

Retrieving an image formatted to a custom size is easy to do in the background as well.  When downloading an image from chute this is recommended so that you don't block the main thread.  The code to do this is

``` Objective-C
    UIImageView *v = [[[UIImageView alloc] init] autorelease];
    [_asset imageForWidth:320 andHeight:480 inBackgroundWithCompletion:^(UIImage *temp){
        [v setImage:temp];
    }];
```

###Other UI Components

It's also possible to retrieve the most recent image that was uploaded to a chute.  This is retrieved through the recentThumbnailUrl.  This returns the url to the full size image.  You can request a customized size by adding the dimensions to the end of the URL in the form of "/widthxheight".  If there is no thumbnail this returns null.  For example to retrieve the image formatted to 50 by 50 px you could use the following code

``` Objective-C
    UIImageView *v = [[[UIImageView alloc] init] autorelease];
    NSString *thumbString = [_chute recentThumbnailUrl];
    if(thumbString && thumbString.length > 0){
        thumbString = [thumbString stringByAppendingString:@"/50x50"];
        UIImage *image = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:thumbString]]];
        [v setImage:image];
    }
```

You can also load an icon for a user.  This is done using the avatarURL

``` Objective-C
    UIImageView *v = [[[UIImageView alloc] init] autorelease];
    NSString *thumbString = [_user avatarURL];
    if(thumbString && thumbString.length > 0){
        UIImage *image = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:thumbString]]];
        [v setImage:image];
    }
```

## Organizing Assets

Assets are organized into a few different objects; chutes, parcels, and bundles.

###Chutes

Chutes are the main containers for organizing assets.  When you create a chute you can set a variety of privacy options.  The privacy settings are GCPermissionTypePrivate, GCPermissionTypeMembers, GCPermissionTypePublic, GCPermissionTypeFriends, and GCPermissionTypePassword.  Use the following code to create a new chute

```Objective-C
    GCChute *_newChute = [GCChute new];
    
    [_newChute setName:_name];
    
    [_newChute setPermissionView:GCPermissionTypePublic];
    [_newChute setPermissionAddMembers:GCPermissionTypePublic];
    [_newChute setPermissionAddPhotos:GCPermissionTypePublic];
    [_newChute setPermissionAddComments:GCPermissionTypePublic];
    [_newChute setModeratePhotos:GCPermissionTypePublic];
    [_newChute setModerateMembers:GCPermissionTypePublic];
    [_newChute setModerateComments:GCPermissionTypePublic];
    
    [_newChute saveInBackgroundWithCompletion:^(BOOL value, NSError *error) {
        
    }];
```

There are a couple methods for retrieving assets.  One runs in the foreground and the other in the background.  These methods are `- (GCResponse *) assets` and `- (void) assetsInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock`.  The following is an example of how to use the second method to initialize an array of assets from a chute.

```Objective-C
    [_chute assetsInBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *assets = [response object];
        }
    }];
```

###Parcels

Assets can also be organized into parcels.  See the section on uploading assets to see how to create a parcel.

You can retrieve the latests parcels for chutes you have subscribed to or created.  This is known as the inbox.  You can retrieve the inbox with the following code

```Objective-C
    [[GCAccount sharedManager] getInboxParcelsInBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *parcels = [response object];
        }
    }];
```

Retrieving a parcel's assets is even easier.  Each parcel has an array of assets so to get a parcel's assets you simply call `[_parcel assets]`.

###Bundles

Bundles will be added soon.

##Make Chute Smarter

You can associate metadata with most types of objects.  Chutes, Assets, Parcels, Users, and Bundles all support metadata.  You can set a string for any key and then retrieve it later or find objects based on a key/value pair.

###Storing Metadata

There are several methods for setting metadata.  You can set a single string for a key or you can set a dictionary of several key/value pairs.

####Setting a Single Property

You can set a single value for a key either in the foreground or background.  You do this by either calling `- (BOOL) setMetaData:(NSString *) data forKey:(NSString *) key` or `- (void) setMetaData:(NSString *) data forKey:(NSString *) key inBackgroundWithCompletion:(GCBoolBlock) aBoolBlock`.  Here is an example of this

``` Objective-C
    [_chute setMetaData:@"San Francisco" forKey:@"location" inBackgroundWithCompletion:^(BOOL *success){
        
    }];
```

####Setting Multiple Properties

If you need to set multiple values you can use a dictionary to set them all at once.  This can also be done in the foreground or background.  The methods for this are `- (BOOL) setMetaData:(NSDictionary *) metaData` and `- (void) setMetaData:(NSDictionary *) metaData inBackgroundWithCompletion:(GCBoolBlock) aBoolBlock`.

``` Objective-C
    NSDictionary *meta = [NSDictionary dictionaryWithObjectsAndKeys:@"value1", @"key1", @"value2", @"key2", nil];
    [_chute setMetaData:meta];
```

###Retrieving Metadata

Just as you can set a single metadata value or a dictionary of values, you can also retrieve a single value or a dictionary of all values.

####Retrieving a Single Value

You can use the methods `- (id) getMetaDataForKey:(NSString *) key` and `- (void) getMetaDataForKey:(NSString *) key inBackgroundWithCompletion:(GCResponseBlock) aResponseBlock` to retrieve a single value.

``` Objective-C
    [_chute getMetaDataForKey:@"location" inBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSString *location = [response object];
        }
    }];
```

####Retrieving All Values

Getting all the values for an object is just as easy and flexible.  You can use `- (GCResponse *) getMetaData` or `- (void) getMetaDataInBackgroundWithCompletion:(GCResponseBlock) aResponseBlock` to retrieve a dictionary of values.

``` Objective-C
    NSDictionary *meta = [[_asset getMetaData] object];
```

###Searching With Metadata

You can do a search on a specific key/value pair to get all objects of a specific type that have that metadata saved.  You can use the methods `+ (GCResponse *) searchMetaDataForKey:(NSString *) key andValue:(NSString *) value` and `+ (void) searchMetaDataForKey:(NSString *) key andValue:(NSString *) value inBackgroundWithCompletion:(GCResponseBlock) aResponseBlock` to accomplish this.  You will get back an array of objects matching the data.

``` Objective-C
    [GCParcel searchMetaDataForKey:@"location" andValue:@"Portland" inBackgroundWithCompletion:^(GCResponse *response){
        if([response isSuccessful]){
            NSArray *parcels = [response object];
        }
    }];
```

###Removing Metadata

Finally you can also delete your metadata for an object.  This can also be done for a single value or for all metadata associated with an object.

####Deleting a Single Value

If you just want to remove a single value for a key there a couple methods that can do that.  You can call `- (BOOL) deleteMetaDataForKey:(NSString *) key` or `- (void) deleteMetaDataForKey:(NSString *) key inBackgroundWithCompletion:(GCBoolBlock) aBoolBlock`.

```Objective-C
    [_chute deleteMetaDataForKey:@"location"];
```

####Deleting All Metadata

You can also delete all metadata that you have set for an object.  This is done with either `- (BOOL) deleteMetaData` or `- (void) deleteMetaDataInBackgroundWithCompletion:(GCBoolBlock) aBoolBlock`.

``` Objective-C
    [_chute deleteMetaDataInBackgroundWithCompletion:^(BOOL *success){
        
    }];
```

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
