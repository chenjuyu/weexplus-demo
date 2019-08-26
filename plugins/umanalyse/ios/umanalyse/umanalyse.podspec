# coding: utf-8
Pod::Spec.new do |s|
  s.name         = "umanalyse"
  s.version      = "1.0.1"
  s.summary      = "友盟统计"
  s.homepage     = 'https://github.com/bmfe/eros-plugin-ios-UMAnalytics'
  s.license      = "MIT"
  s.authors      = { "zjr" => "362675035@qq.com" }
  s.platform     = :ios
  s.ios.deployment_target = "8.0"
  s.source = { :path => '.' }

  s.source_files = "Source/*.{h,m,mm}"
  s.requires_arc = true
  s.dependency 'farwolf.weex'
  s.dependency 'UMCAnalytics', '5.5.0'

end
