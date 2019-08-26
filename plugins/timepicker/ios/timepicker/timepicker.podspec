#
#  Be sure to run `pod spec lint QBMDatePickerPlugin.podspec' to ensure this is a

Pod::Spec.new do |s|



  s.name         = "timepicker"
  s.version      = "1.0.2"
  s.summary      = "timepicker File"

  s.description  = <<-DESC
                  Testing private podspec,to verify availability
                   DESC

  s.homepage     = 'https://github.com/qbmFE/eros-plugin-IOS-dateTimePicker'

  s.license      = "MIT"

  s.author       = { "yolanderJing" => "zhangj@qbm360.com" }

  s.platform     = :ios

  s.ios.deployment_target = "8.0"

   s.source =  { :path => '.' }
   
   s.source_files  = "Source", "**/**/*.{h,m,mm,c}"

  s.requires_arc = true
   s.dependency 'farwolf.weex'

  
end
