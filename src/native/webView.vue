<template>
    <div style="width:750px">
        <head :rightText="rightText" title="多颜色"  @rightClick="rightClick"></head>
        <webView ref="myWeb" @newEvent="newEvent" path="http://192.168.1.104:8080/FPOS/common.do?testwebview&test=asssssss" @finish="htmlFinish" style="width: 720px; height:600px;align-items: center;justify-content:center">
        </webView>
        <div>
            <richText tel="12305" style="width:200;height:100">12305</richText>
        </div>
     <div style="background-color: orange;position: fixed;right: 0;bottom: 0;width: 750px;height: 80px;align-items: center;justify-content:center">
         <text style="font-size: 30px" @click="excuJS">提交</text>
     </div>

    </div>
</template>

<script>
    const  pref=weex.requireModule('pref');
    var modal=weex.requireModule('modal');
    const webview = weex.requireModule('webview');
    export default {
        data() {
         return {
             name: "webView",
             url:'http://192.168.1.104:8080/FPOS/common.do?testwebview&test=asssssss',
             msg:'rrrr'

         }
        },methods: {
            onLoad(p){

            },
            htmlFinish(){//pref.getString('ip')+'

                modal.alert({message:'界面加载完成'});
                //this.html=pref.getString('ip')+'/common.do?testwebview'+'&test=asssssss'
                //this.newEvent()
            },
            newEvent(e){
                //e.data即为HTML传来的值
                this.msg=e
                modal.alert({message:e});
            },excuJS(e){//第二个参数 传到HTML的数据
                this.$refs.myWeb.executeJSFunction('save','',
              (res)=>{
                    //执行完成的回调
                  //this.alert(res)
                })

            }
        },created(){
           // this.url='http://192.168.1.104:8080/FPOS/common.do?testwebview&test=asssssss'
           // this.excuJS()
           // this.$refs.myWeb.executeJSFunction("save",'',(res)=>{
            //    this.alert(res)
            //});
        }
    }
</script>

<style scoped>
    .webview {
        width: 750px;
        position: absolute;
        top: 100px;
        bottom: 0px;
        right: 0px;
        left: 0px;
    }
</style>