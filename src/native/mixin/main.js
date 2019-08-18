const Mixins = {}
let wraper = require('../component/wraper.vue')
let head = require('../component/head.vue')
let button = require('../component/button.vue')
let page = require('../component/page.vue')
Mixins.install = (Vue, options) => {
  Vue.mixin({
    components: {wraper, head, button, page},
    data(){
      return {
        modal: {},
        navigator: {},
      }
    },
    methods: {
      toast(msg)
      {
        if (this.modal && this.modal.toast)
          this.modal.toast({message: msg})
      },
      alert(msg)
      {
        this.modal.alert({message: msg})
      },
      push(url, param)
      {
        if (!param)
          this.navigator.push(url)
        else
          this.navigator.pushParam(url, param)
      },
      getScreenHeight() {
        return 750 / weex.config.env.deviceWidth * weex.config.env.deviceHeight
      },
        log(msg,level){
          if(!level){
              level='info'
          }
            let type = typeof msg
            let log = weex.requireModule('log')
            if (type == 'object') {
                msg = JSON.stringify(msg)
            }
            log.log({msg:this.timestamp() + '    ' + msg + '',level:level})
        },

        timestamp() {
            var date = new Date();//时间戳为10位需*1000，时间戳为13位的话不需乘1000
            var Y = date.getFullYear() + '-';
            var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
            var D = date.getDate() < 10 ? '0' + date.getDate() + ' ' : date.getDate() + ' ';
            var h = date.getHours() < 10 ? '0' + date.getHours() + ':' : date.getHours() + ':';
            var m = date.getMinutes() < 10 ? '0' + date.getMinutes() + ':' : date.getMinutes() + ':';
            var s = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds();
            return Y + M + D + h + m + s;
        }

    },
    created()
    {
      this.navigator = weex.requireModule('navigator')
      this.modal = weex.requireModule('modal')
      var globalEvent = weex.requireModule('globalEvent');
      globalEvent.addEventListener("onPageInit", (param) => {
        let p = param
        if (param && p.param && p.bubbles)
          p = p.param
        if (this.onLoad != undefined)
          this.onLoad(p)

        if (WXEnvironment.platform === 'android') {
          let keyboard = weex.requireModule('keyboard')
          keyboard.setKeyboardMode('adjust_pan')
        }
         //阿里字库
        let domModule = weex.requireModule('dom');
        domModule.addRule('fontFace',{
          'fontFamily': "iconfont",
          'src':"url('http://at.alicdn.com/t/font_1074303_0xf03ihmlpyq.ttf')" // "url('root:font/iconfont.ttf')"//../ http://at.alicdn.com/t/font_1074303_e64s32fcc65.ttf
        })

      });

    }

  })
}
const install = Mixins.install
export {install}
export  default Mixins