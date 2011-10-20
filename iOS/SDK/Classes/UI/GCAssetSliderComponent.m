//
//  GCAssetSliderComponent.m
//  realtyChute
//
//  Created by Brandon Coston on 9/03/11.
//  Copyright 2011 Chute. All rights reserved.
//

#import "GCAssetSliderComponent.h"
#import "GCAsset.h"

@implementation GCAssetSliderComponent
@synthesize objects, sliderObjects, currentPage, objectSlider;

- (CGRect)rectForPage:(NSInteger)page{
    CGRect imageRect = CGRectMake((objectSlider.frame.size.width*(page-1)), 0, objectSlider.frame.size.width, objectSlider.frame.size.height);
    return imageRect;
}

- (void) resizeScrollView
{
    NSUInteger size = [[self objects] count];
    [objectSlider setContentSize:CGSizeMake(objectSlider.frame.size.width * size, objectSlider.frame.size.height)];
}

- (void)scrollViewDidEndDecelerating:(UIScrollView *)sv
{ 
    if(sv == objectSlider)
    [self loadObjectsForCurrentPosition];
}

- (void)loadObjectsForCurrentPosition
{
    if(![self objects])
        return;
    if([[self objects] count] == 0)
        return;
    currentPage = ((objectSlider.contentOffset.x - objectSlider.frame.size.width / 2) / objectSlider.frame.size.width) + 2; 
    BOOL foundCurrent = NO;
    BOOL foundPrevious = NO;
    BOOL foundNext = NO;
    
    
    // Unload views too far away
    for (UIView *v in [objectSlider subviews])
    {
        if (v.tag == currentPage)
        {
            foundCurrent = YES;
            continue;
        }
        
        if (v.tag == (currentPage + 1))
        {
            foundNext = YES;
            continue;
        }
        
        if (v.tag == (currentPage - 1))
        {
            foundPrevious = YES;
            continue;
        }
        
        // Remove images
        if (v.tag != 0)
        {
            [v removeFromSuperview];
        }
    }
    
    NSMutableArray *array = [NSMutableArray array];
    for(UIScrollView *image in [self sliderObjects]){
        if(image.superview)
            [array addObject:image];
    }
    [self setSliderObjects:array];
    
    // Load Images    
    if (foundCurrent == NO)
    {
        [self queueObjectRetrievalForPage:(currentPage)];
    }
    
    if (foundNext == NO)
    {
        [self queueObjectRetrievalForPage:(currentPage + 1)];
    }
    
    if (foundPrevious == NO)
    {
        [self queueObjectRetrievalForPage:(currentPage - 1)];
    }
}

-(UIView*) viewForPage:(NSInteger)page{
    GCAsset *asset = [objects objectAtIndex:page];
    CGRect rect = CGRectMake(0, 0, objectSlider.frame.size.width, objectSlider.frame.size.height);
    UIScrollView *view = [[[UIScrollView alloc] initWithFrame:rect] autorelease];
    UIImageView *image = [[[UIImageView alloc] initWithFrame:rect] autorelease];
    [image setContentMode:UIViewContentModeScaleAspectFit];
    [view addSubview:image];
    
    [asset imageForWidth:320 andHeight:480 inBackgroundWithCompletion:^(UIImage *_image){
        [image setImage:_image];
    }];
    
    image.autoresizingMask = ( UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight);
    [view setMinimumZoomScale:1];
    [view setMaximumZoomScale:2.5];
    [view setClipsToBounds:YES];
    [view setBackgroundColor:[UIColor blackColor]];
    [view setShowsVerticalScrollIndicator:NO];
    [view setShowsHorizontalScrollIndicator:NO];
    [image setClipsToBounds:YES];
    [view setContentSize: CGSizeMake(image.bounds.size.width, image.bounds.size.height)];
    [view setDelegate:self];
    NSMutableArray *array = [NSMutableArray arrayWithArray:[self sliderObjects]];
    [array addObject:view];
    [self setSliderObjects:array];
    return view;
}

- (UIView *)viewForZoomingInScrollView:(UIScrollView *)scrollView {
    if(scrollView != objectSlider){
        if(scrollView.subviews.count > 0)
        return [scrollView.subviews objectAtIndex:0];
    }
    return NULL;
}

//varies depending on objects.  Implement in child class
- (void) queueObjectRetrievalForPage:(NSInteger)page
{
    if (page < 1 || page > ([[self objects] count]))
    {
        return;
    }
    UIView* v = [self viewForPage:(page-1)];
    [v setFrame:[self rectForPage:page]];
    v.tag = page;
    [objectSlider addSubview:v];
}

- (void)switchToObjectAtIndex:(NSNumber*)index{
    [self switchToObjectAtIndex:[index unsignedIntValue] animated:NO];
}
- (void)switchToObjectAtIndex:(NSUInteger)index animated:(BOOL)_animated{
    if(index > [[self objects] count])
        index = [[self objects] count];
    [objectSlider scrollRectToVisible:[self rectForPage:index] animated:_animated];
    [self performSelector:@selector(loadObjectsForCurrentPosition) withObject:nil afterDelay:.4];
}


- (void)nextObject{
    if(currentPage >= ([[self objects] count]))
        return;
    [self switchToObjectAtIndex:(currentPage+1) animated:YES];
}
- (void)previousObject{
    if(currentPage <= 1)
        return;
    [self switchToObjectAtIndex:(currentPage-1) animated:YES];
}




- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)dealloc
{
    [sliderObjects release];
    [objectSlider release];
    [objects release];
    [super dealloc];
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
    [self resizeScrollView];
    [self loadObjectsForCurrentPosition];
    [objectSlider setPagingEnabled:YES];
    [objectSlider setClipsToBounds:YES];
    [objectSlider setDelegate:self];
    // Do any additional setup after loading the view from its nib.
    //[table setAllowsSelection:NO];
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


@end
