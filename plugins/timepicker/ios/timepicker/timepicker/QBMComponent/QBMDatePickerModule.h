//
//  QBMDatePickerModule.h
//  WeexEros
//
//  Created by qbm_ios on 2018/5/24.
//  Copyright © 2018年 qbm Company. All rights reserved.

#import <Foundation/Foundation.h>
#import  "WXModuleProtocol.h"
#import "QBMDefine.h"
#import "WXComponentManager.h"
#import "QBMDatePickerView.h"
#import "WXConvert.h"
#import <WeexPluginLoader/WeexPluginLoader.h>
@interface QBMDatePickerModule : NSObject<WXModuleProtocol,QBMDatePickerViewDelegate>
@property(nonatomic,copy)NSString *title;
@property(nonatomic,strong)UIColor *titleColor;
@property(nonatomic,copy)NSString *cancelTitle;
@property(nonatomic,copy)NSString *confirmTitle;
@property(nonatomic,strong)UIColor *cancelTitleColor;
@property(nonatomic,strong)UIColor *confirmTitleColor;

@property(nonatomic,strong)QBMDatePickerView  *pickerView;
@property (nonatomic, strong) NSDate *selectedDate;
@property(nonatomic,copy)WXModuleKeepAliveCallback callback;
@end

