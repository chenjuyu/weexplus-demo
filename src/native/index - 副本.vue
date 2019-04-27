<template>
    <div class="layout">
        <head leftButton="" rightButton=""></head>
        <!--
          <head :title="tit" @titleClick="clickT" :height="120" :back="false" @rightClick="clickR">
              <text  slot="right" style="flex-direction: row;margin-top: 13px;"></text>
          </head> -->

        <!-- weex-ui  :sourceData="sourceData" class="scroller"  :sourceData="sourceData"城市录入方式 showListCity showyuangong-->
        <scroller class="scroller" >
            <div><input type="text" class="input" return-key-type="default" placeholder="default" v-model="no"></div>
            <div class="btn" > <text class="btn-txt" @click="login">登录</text> </div>
            <div class="btn" >
                <text class="btn-txt" @click="upload">拍照上传</text> </div>
            <div class="btn" @click="showyuangong">
                <text class="btn-txt">城市选择</text>
            </div>
            <div class="panel">
                <text v-if="currentCity" class="text">当前城市: {{currentCity}}</text>
                <text>B页面返回的值：{{value}}</text>
            </div>

        </scroller>

        <!--   <basecheck ref="cjy" @choice="submit" ></basecheck> -->



        <wxc-city ref="wxcCity"
                  :animationType="animationType"
                  :currentLocation="location"
                  :cityStyleType="cityStyleType"

                  :showNavHeader="true"
                  @wxcCityItemSelected="citySelect"
                  @wxcCityOnInput="onInput"></wxc-city>


        <scroller :style="{'width':width+'px'}" scroll-direction='horizontal'>
            <div class="label">
                <text class="item">单号</text>
                <text class="item">日期</text>
                <text class="item">制单人</text>
                <text class="item">审核人</text>
                <text class="item">审核时间</text>
                <text class="item">订单2</text>
                <text class="item">tt</text>
            </div>
            <scroller :style="{'width':width+'px'}">
                <div class="cell" v-for="(num,key) in lists" @click="onSelected(num,key)">
                    <text class="item">{{num.Customer}}</text>
                </div>

                <!-- <div class="cell" v-for="(num,key) in lists" @click="onSelected(num,key)" >
                   <text class="item">{{num.No}}</text>
                  <text class="item">{{num.Date}}</text>
                   <text class="item">{{num.madeby}}</text>
                   <text class="item">{{num.audit}}</text>
                    <text class="item">{{num.Date}}</text>
                   <text class="item">{{num.order}}</text>
                   <text class="item">{{num.tt}}</text>
                 </div> -->
            </scroller>
        </scroller>







    </div>
</template>

<script>
    import { WxcCity,Utils  } from 'weex-ui';
    //import basecheck  from './basecheck.vue';
    const dom = weex.requireModule('dom')
    const test=weex.requireModule('test');

    export default {
        components: { WxcCity //basecheck,

        },
        data () {
            return {
                animationType: 'push',
                currentCity: '',
                no:'',
                cityStyleType:'list',
                location: '定位中',
                lists: [{'No':123,'Date':'2019-03-03','madeby':'系统管理员','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':456,'Date':'2019-03-04','madeby':'系统管理员1','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':789,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':789,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':111,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':222,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':333,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':444,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':123,'Date':'2019-03-03','madeby':'系统管理员','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':456,'Date':'2019-03-04','madeby':'系统管理员1','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':789,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':789,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':111,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':222,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':333,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':444,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':123,'Date':'2019-03-03','madeby':'系统管理员','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':456,'Date':'2019-03-04','madeby':'系统管理员1','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':789,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':789,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':111,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':222,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':333,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':444,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':123,'Date':'2019-03-03','madeby':'系统管理员','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':456,'Date':'2019-03-04','madeby':'系统管理员1','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':789,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':789,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':111,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':222,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':333,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'},
                    {'No':444,'Date':'2019-03-05','madeby':'系统管理员2','audit':'陈陈陈','auditDate':'2019-03-03','order':'adafdsfsdfa','tt':'sdsdfsdfsdf'}

                ]
            }
        },props: {

            title:{
                default: '列表页aaaaa'
            },
            width:{
                default:750
            },
            sourceData:{

                default:''
            },value:{
                default:''
            }
        },methods:{
            clickT(){
                this.toast('点击标题栏')
            },clickR(){
                this.toast('点击右边')
            },
            onSelected(num,key){
                this.alert(num.No);
                this.push("root:possalesdetail.js",{"No":num.No,"madeby":num.madeby});

            }, showListCity () {

                this.sourceData= {hotCity:[{ cityName: '北京', pinYin: 'beijing', py: 'bj' },
                        { cityName: '包头', pinYin: 'baotou', py: 'bt' },
                        { cityName: '北海', pinYin: 'beihai', py: 'bh' },
                        { cityName: '宝鸡', pinYin: 'baoji', py: 'bj' }]};



                //  this.$refs['wxcCity'].saveDefaultSourceData =this.sourceData;
                //this.$refs['wxcCity'].saveDefaultSourceData =this.sourceData
                this.$refs['wxcCity'].sourceData=Utils.paramsToObj({hotCity:[{ cityName: '北京', pinYin: 'beijing', py: 'bj' },
                        { cityName: '包头', pinYin: 'baotou', py: 'bt' },
                        { cityName: '北海', pinYin: 'beihai', py: 'bh' },
                        { cityName: '宝鸡', pinYin: 'baoji', py: 'bj' }],
                    cities:[
                        { cityName: '北京', pinYin: 'beijing', py: 'bj'},
                        { cityName: '包头', pinYin: 'baotou', py: 'bt' }]  });
                this.$refs['wxcCity'].saveDefaultSourceData =this.sourceData
                this.cityStyleType = 'list';
                //   this.alert(this)
                // this.alert(this.$refs['wxcCity']);

                this.$refs['wxcCity'].show();
            },
            showGroupCity () {
                this.cityStyleType = 'group'
                this.$refs['wxcCity'].show();
            },
            citySelect (e) {
                this.currentCity = e.item;
            },
            onInput (e) {
            },
            onLoad(){
                let nav = weex.requireModule('navigator') ;
                nav.setRoot('A'),
                    nav.setPageId('A')
            },
            showyuangong(){
                let nav = weex.requireModule('navigator') ;
                nav.pushFull({
                    url: 'root:basecheck.js',
                    param: {a: '这是传过来的值'},
                    animate:true,
                    isPortrait:true
                }, (res) => {
                    if (res != undefined)
                        this.alert(JSON.stringify(res))
                    // self.callbackdata = e.ok;

                })

                // this.$refs.cjy.show() 不用显示与隐藏的方式 用页面跳转的方式请求返回数据
            },
            submit(e){
                //this.toast(e.value)
                // this.alert(e)
                this.value=e.item
            },
            login(e){
                var self=this;
                self.back="";
                const net = weex.requireModule('net');
                net.post('http://192.168.1.102:8080/FPOS/login.do?login',{username:"1",password:"1"},{},function(){
                    //start
                },function(e){
                    //success
                    self.back=e.res;

                },function(e){
                    //compelete

                },function(){
                    //exception
                });
            },
            upload(e){



                var self=this;
                self.back="";
                const net = weex.requireModule('net');
                net.post('http://192.168.1.102:8080/FPOS/select.do?getCustomer',{currPage:"1"},{},function(){
                    //start
                },function(e){
                    //success
                    self.back=e.res;
                    self.lists=e.res.obj

                },function(e){
                    //compelete

                },function(){
                    //exception
                });

                /*   var self=this;
                   const photo = weex.requireModule('photo');
                   photo.openCamera(500,800,'#000000',function(e){

                       self.src=e.path;
                       var param={};
                       var header={};
                       var path={};
                       path.file=e.path;
                       var net=weex.requireModule("net");
                      //http://192.168.1.103:8080/FPOS//common.do?uploadImages 服务端口的上传方法
                       net.postFile('http://192.168.1.103:8080/FPOS/common.do?uploadImages',param,header,path,()=>{
                           //start
                       },(e)=>{
                           //succcess
                           var modal=weex.requireModule("modal")
                           //modal.toast({message:'上传成功！'})
                           modal.toast({message:e.res.msg})
                       },(e)=>{
                           //compelete


                       },()=>{
                           //exception
                           var modal=weex.requireModule("modal")
                          // modal.toast({message:path})
                           modal.toast({message:'上传异常！'})
                       })
                   }) */
            }
        },
        created() {
            // this.alert('aaacccc')

            var page=weex.requireModule("page")
            page.enableBackKey(true);
            page.setBackKeyCallback(()=>{

                this.$refs.cjy.show(false);

            })


            let pa={}
            pa.str="给android的一个请求条件";
            pa.text="aaaa";

            test.testpint(pa,(e)=>{
                debugger
                console.log("返回的内容:"+e);
            });
        },mounted () {
            // 模拟定位
            setTimeout(() => {
                this.location = '杭州';
            }, 500);
        },

    }
</script>
<style>

    .input {
        border-width: 1px;
        margin-bottom: 5px;
        margin-top: 5px;
        height: 100px;
    }
    .row {
        width: auto;
        flex: 1;
        position: relative;
    }
    .cell {
        background-color: red;
        flex-direction: row;
    }
    .item{
        width:200px;
        line-height: 80px;
    }
    .label{
        width: auto;
        flex-direction: row;
        background-color:orangered;
    }

    .scroller {
        /*  flex: 1;  这个等于1会点用半屏*/
        margin-top: 5px;
        display: block;
        height: 400px;
    }
    .btn {
        width: 600px;
        height: 80px;
        margin-left: 75px;
        margin-top:100px;
        flex-direction: row;
        align-items: center;
        justify-content: center;
        border-radius: 6px;
        background-color: rgb(92, 184, 92);
        border-color: rgb(76, 174, 76);
    }
    .btn-txt {
        font-size: 32px;
        color: #ffffff;
    }
    .btn-margin {
        margin-top: 40px;
    }
    .panel {
        height: 100px;
        align-items: center;
        margin-top: 40px;

    }

</style>