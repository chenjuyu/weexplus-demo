<template>
    <div class="wxc-demo">
        <scroller class="scroller">
            <div class="demo">
                <wxc-minibar background-color="#1EA5FC"

                             @wxcMinibarLeftButtonClicked="backTo"
                             @wxcMinibarRightButtonClicked="rightclick">
                    <image :src="leftButton"
                           slot="left"
                           style="height: 70px;width: 70px;" ></image>
                    <text :style="{'color':titleColor,'font-size':40+'px'}" slot="middle" >{{title}}</text>

                    <text slot="right" v-if="rightText" class="iconfont">{{rightText}}</text>
                    <image  slot="right" v-if="rightButton && !rightText"
                           :src="rightButton"
                           style="height: 32px;width: 40px"></image>

                </wxc-minibar>
            </div>


        </scroller>
    </div>
</template>
<script>//https://img.alicdn.com/tfs/TB1j39Uc0fJ8KJjy0FeXXXKEXXa-160-128.png
    import { WxcMinibar } from 'weex-ui';
    export default {
        components: { WxcMinibar },
        props: {
            leftButton:{default:'root:img/back.png'},
            rightButton:{default:''},
            rightText:'',
            title: {
                default: ''

            },
            back: {
                default: true
            },
            titleColor:{
                default:'#ffffff'
            },
            hideBottomLine: {
                default: false
            },

            bgcolor: {
                default: '#1EA5FC'

            },
            autoback: {
                default: true
            },
            isloading: {
                default: false
            },
            disabled: {

                default: false
            },

            type: {
                type: String,
                default: 'text'
            },
            font_size: {
                default: 20
            },
            height: {
                default: 135
            },
            top: {
                default: 40
            },
            titletop: {
                default: 10
            }


        },
        data () {
            return {}
        },
        methods: {
            titleClick()
            {
                this.$emit('titleClick');
            },
            rightclick() //https://img.alicdn.com/tfs/TB1j39Uc0fJ8KJjy0FeXXXKEXXa-160-128.png
            {
                this.$emit('rightClick');
            },
            backTo()
            {
                if(this.autoback)
                {
                    var nav = weex.requireModule("navigator");
                    nav.back();
                    return;
                }
                this.$emit('backClick');


            },

            onclick()
            {
                if (!this.disabled)
                    this.$emit('onclick');
            },


            adjust()
            {
                if (weex.config.env.platform == 'android') {
//                    if(weex.config.env.osVersion=)
                    var p = weex.config.env.osVersion
                    p = p.replace(/\./g, '')
                    if (p.length < 3)
                        p = p + "0";
                    if (p <= '440') {
                        this.height = 108
                        this.top = 16;
                        this.titletop = 4;
                    }
                }
            }


        },

        created: function () {
          //  var font=weex.requireModule("font");
           // font.addFont('iconfont','root:font/iconfont.ttf')//阿里的 iconfont不用本地的，直接用外网的

            let domModule = weex.requireModule('dom');
            domModule.addRule('fontFace',{
                'fontFamily': "iconfont",
               'src':"url('http://at.alicdn.com/t/font_1074303_qm5uzmgorba.ttf')" // "url('root:font/iconfont.ttf')"//../ http://at.alicdn.com/t/font_1074303_e64s32fcc65.ttf
            })

            this.adjust();

        },
        ready: function () {


        },

    }
</script>
<style scoped>

    .wxc-demo {
      /*  position: absolute; */
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        background-color:#1EA5FC;
        width: 750px;
        height: 130px;
        display: block;
    }
    .scroller {
        flex: 1;
    }
    .demo {

        width: 750px;
        height: 130px;
        align-items: flex-start;
        padding-top: 40px;
    }
    .iconfont {
        font-family:iconfont;  /*必须写哦！！ */
        font-size: 50px;
        color: #0085ee;
    }
</style>
