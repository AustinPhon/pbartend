#import <UIKit/UIKit.h>
#import <CoreLocation/CoreLocation.h>
#import <UIKit/UINavigationController.h>
#import "JSON.h"

#import "Location.h"
#import "Device.h"
//#import "Sound.h"
//#import "Contacts.h"
#import "DebugConsole.h"
//#import "UIControls.h"


@class InvokedUrlCommand;
@class PhoneGapViewController;
@class Sound;
@class Contacts;
@class Console;
//@class Image;

@interface PhoneGapDelegate : NSObject <
    UIApplicationDelegate, 
    UIWebViewDelegate, 
    UIAccelerometerDelegate,
    UINavigationControllerDelegate
  >
{
	IBOutlet UIButton *aboutbutton;
	IBOutlet UIWindow *window;
	IBOutlet UIWebView *webView;
	IBOutlet PhoneGapViewController *viewController;
	
	IBOutlet UIImageView *imageView;
	IBOutlet UIActivityIndicatorView *activityView;

	NSURLConnection *conn;				// added by urbian
	NSMutableData *receivedData;		// added by urbian	

    UIInterfaceOrientation orientationType;
    NSDictionary *settings;
    NSMutableDictionary *commandObjects;
    NSURL *invokedURL;
}

@property (nonatomic,retain) UIButton *aboutbutton;
@property (nonatomic, retain) UIWindow *window;
@property (nonatomic, retain) PhoneGapViewController *viewController;
@property (nonatomic, retain) UIActivityIndicatorView *activityView;
@property (nonatomic, retain) NSMutableDictionary *commandObjects;
@property (nonatomic, retain) NSDictionary *settings;
@property (nonatomic, retain) NSURL *invokedURL;

-(IBAction)showInfo:(id)sender;

-(NSString *)getIPAddress;
- (id) getCommandInstance:(NSString*)className;
- (BOOL) execute:(InvokedUrlCommand*)command;

+ (NSDictionary*)getBundlePlist:(NSString *)plistName;

@end
