//
//  RootViewController.m
//  KitchenSink
//
//  Created by Achal Aggarwal on 08/08/11.
//  Copyright 2011 NA. All rights reserved.
//

#import "RootViewController.h"
#import "ChuteBaseViewController.h"
#import "PhotosBaseViewController.h"
#import "BundlesBaseViewController.h"

@implementation RootViewController

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)didReceiveMemoryWarning
{
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

- (void)viewDidLoad
{
    [super viewDidLoad];
    // Do any additional setup after loading the view from its nib.
    [GCLoginViewController presentInController:self];
}

- (void)viewDidUnload
{
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
    // Return YES for supported orientations
    return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (IBAction)chute:(id)sender {
    ChuteBaseViewController *chuteBaseViewController = [[ChuteBaseViewController alloc] init];
    [chuteBaseViewController setTitle:@"Chutes"];
    [self.navigationController pushViewController:chuteBaseViewController animated:YES];
    [chuteBaseViewController release];
}

- (IBAction)photos:(id)sender {
    PhotosBaseViewController *photosBaseViewController = [[PhotosBaseViewController alloc] init];
    [self.navigationController pushViewController:photosBaseViewController animated:YES];
    [photosBaseViewController setTitle:@"Photos"];
    [photosBaseViewController release];
}

- (IBAction)bundles:(id)sender {
    BundlesBaseViewController *bundlesBaseViewController = [[BundlesBaseViewController alloc] init];
    [self.navigationController pushViewController:bundlesBaseViewController animated:YES];
    [bundlesBaseViewController setTitle:@"Bundles"];
    [bundlesBaseViewController release];
}

- (void) progress:(NSNotification *) notification {
    DLog(@"%f", [[notification object] progress]);
}


- (void) status:(NSNotification *) notification {
    DLog(@"%d", [[notification object] status]);
}


- (void) complete {
    DLog(@"=================================== parcel completed");
}

- (IBAction)sync:(id)sender {
    
    [[GCAccount sharedManager] getInboxParcels];
    
//    [GCChute allInBackgroundWithCompletion:^(GCResponse *response) {
//        GCChute *first = [[response object] objectAtIndex:0];
//        [first assetsInBackgroundWithCompletion:^(GCResponse *response) {
//            GCAsset *_asset = [[response object] objectAtIndex:0];
//            [_asset addComment:@"test comment 111"];
//        }];
//    }];
    
//    [GCChute allInBackgroundWithCompletion:^(GCResponse *response) {
//        GCChute *first = [[response object] objectAtIndex:0];
//        [first assetsInBackgroundWithCompletion:^(GCResponse *response) {
//            DLog(@"%@", response);
//            GCAsset *_asset = [[response object] objectAtIndex:0];
//            [_asset heart];
//            //[_asset comments];
//        }];
//    }];
    

//    [GCChute allPublicInBackgroundWithCompletion:^(GCResponse *response) {
//        DLog(@"%@", response);
//    }];
    
    
//    GCParcel *parcel = [GCParcel objectWithAssets:[[GCAccount sharedManager] assetsArray] andChutes:[response object]];
//    [[GCUploader sharedUploader] addParcel:parcel];
//    
    
//
//    GCResponse *response   = [GCChute searchMetaDataForKey:@"AnyKey" andValue:@"AnyValue"];
//    for (GCChute *chute in [response object]) {
//        //Individual Chutes
//        NSLog(@"%@", chute);
//    }
//    
//    [GCChute searchMetaDataForKey:@"AnyKey" andValue:@"AnyValue" inBackgroundWithCompletion:^(GCResponse *response) {
//        for (GCChute *chute in [response object]) {
//            //Individual Chutes
//            NSLog(@"%@", chute);
//        }
//    }];
    
//    GCResponse *response = [GCChute findById:29];
//    GCChute *chute       = [response object];
//    if ([chute destroy]) {
//        //Destroyed
//    }
//
//    [chute destroyInBackgroundWithCompletion:^(BOOL value) {
//        if (value) {
//            //destroyed
//        }
//    }];
//    
//    [chute setName:@"NewName"];
//    
//    if ([chute update]) {
//        //Updated
//    }
//    
//    [chute updateInBackgroundWithCompletion:^(BOOL value) {
//        if (value) {
//            //Updated
//        }
//    }];
//    
//    
//    GCResponse *_response = [GCChute all];
//    for (GCChute *chute in [_response object]) {
//        //Individual Chutes
//        NSLog(@"%@", chute);
//    }
//    
//    
//    [GCChute allInBackgroundWithCompletion:^(GCResponse *response) {
//        for (GCChute *chute in [response object]) {
//            //Individual Chutes
//            NSLog(@"%@", chute);
//        }
//    }];
    
    //[GCChute searchMetaDataForKey:@"world" andValue:nil];
//   [GCChute searchMetaDataForKey:@"world" andValue:nil inBackgroundWithCompletion:^(GCResponseObject *response) {
//        DLog(@"%@", response);  
//    }];

//    [GCAsset findById:55 inBackgroundWithCompletion:^(GCResponse *response) {
//        DLog(@"%@", response);
//    }];
    
//    [GCAsset allInBackgroundWithCompletion:^(GCResponse *response) {
//        DLog(@"%@", response);
//    }];
    
//    [GCChute findById:29 inBackgroundWithCompletion:^(GCResponse *response) {
//        [[response object] assets];
//    }];
//
//    GCChute *_new = [GCChute new];
//    [_new setName:@"new chute"];
//    [_new save];
//    
//    [_new saveInBackgroundWithCompletion:^(BOOL value) {
//        if (value) {
//            //Saved
//        }
//    }];
//    DLog(@"%@", _new);
//    [_new destroy];
    
//    GCChute *_new = [GCChute new];
//    [_new setName:@"test name 1"];
//    [_new save];
//    
//    [_new setName:@"test name 2"];
//    [_new update];

//    
    
    
//    [GCChute allInBackgroundWithCompletion:^(GCResponseObject *response) {
//        DLog(@"%@", response);  
//        [[[response data] objectAtIndex:0] setMetaData:@"hello" forKey:@"world"];
//    }];
//    
//    
//    
//    
//    [GCChute searchMetaDataForKey:@"world" andValue:nil];
//    
    //[GCChute searchMetaDataForKey:@"key1" andValue:nil];
//    [GCChute allInBackgroundWithCompletion:^(id response) {
//        DLog(@"%@", response); 
//    } andError:^(NSError *error) {
//        
//    }];

//    [GCChute findById:32 inBackgroundWithCompletion:^(id response) {
//        [response destroy];
//    } andError:^(NSError *error) {        
//        DLog(@"%@", [error localizedDescription]);
//    }];
//    
//    [GCChute allInBackgroundWithCompletion:^(id response) {
//        DLog(@"%@", response); 
//    } andError:^(NSError *error) {
//        
//    }];
        
//    [GCChute findById:32 inBackgroundWithCompletion:^(id response) {
//        [response setMetaData:[NSDictionary dictionaryWithObjectsAndKeys:@"obj1", @"key1", @"obj2", @"key2", nil]];
//        DLog(@"%@", [response getMetaDataForKey:@"key1"]);
//        [response deleteMetaDataForKey:@"key1"];
//        DLog(@"%@", [response getMetaDataForKey:@"key1"]);
//    } andError:^(NSError *error) {
//        
//    }];
//    
//    [GCChute findById:32 inBackgroundWithCompletion:^(id response) {
//        [response getMetaData];
//    } andError:^(NSError *error) {
//        
//    }];
    
    //[[ChuteNetwork new] getRequestInBackgroundWithPath:@"http://google.com" andParams:nil withResponse:nil andError:nil];
//    [[ChuteAPI shared] test];
//    [[ChuteAPI shared] syncWithResponse:^(void) {
//        DLog(@"synced");
//    } andError:^(NSError *error) {
//        DLog(@"%@", [error localizedDescription]);
//    }];
}
@end
