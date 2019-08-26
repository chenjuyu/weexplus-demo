

Pod::Spec.new do |s|

 

  s.name         = "lottie"
  s.version      = "0.0.1"
  s.summary      = "Handle some data."
  s.description  = <<-DESC
                    Handle the data.
                   DESC

  s.homepage     = "http://csdn.net/veryitman"
  s.license      = "MIT"
  s.author             = { "veryitman" => "veryitman@126.com" }
  s.source =  { :path => '.' }
  s.source_files  = "Source", "**/**/*.{h,m,mm,c}"
  s.exclude_files = "Source/Exclude"
  s.resources = 'lottie/resources/storyboard/**','lottie/resources/image/**','lottie/resources/xib/**'
  s.platform     = :ios, "8.0"
  s.requires_arc = true
  
 
  s.dependency 'farwolf.weex'
  s.dependency 'lottie-ios','1.5.1'



end
