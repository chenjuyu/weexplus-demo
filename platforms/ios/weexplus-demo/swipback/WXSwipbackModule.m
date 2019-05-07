//
//  WXSwipbackModule.m
//  AFNetworking
//
//  Created by 郑江荣 on 2019/5/7.
//

#import "WXSwipbackModule.h"

#import <WeexPluginLoader/WeexPluginLoader.h>
WX_PlUGIN_EXPORT_MODULE(swipeBack, WXSwipbackModule)
@implementation WXSwipbackModule
WX_EXPORT_METHOD_SYNC(@selector(forbidSwipeBack:callback:))

-(void)forbidSwipeBack:(NSMutableDictionary*)param callback: (WXModuleKeepAliveCallback)callback{
    
}
@end
