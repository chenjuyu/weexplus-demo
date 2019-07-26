<template>
    <div style="width:750px">
        <head :rightText="rightText" title="多颜色"  @rightClick="rightClick"></head>
        <UChartsView ref="myWeb" @newEvent="newEvent" :path="path" @finish="htmlFinish" style="width: 720px; height:600px;align-items: center;justify-content:center">
        </UChartsView>

     <div @click="excuJS" style="background-color: orange;position: fixed;right: 0;bottom: 0;width: 750px;height: 80px;align-items: center;justify-content:center">
         <text style="font-size: 30px" >提交</text>
     </div>

    </div>
</template>

<script>
    const  pref=weex.requireModule('pref');
    var modal=weex.requireModule('modal');
    const webview = weex.requireModule('webview');
    export default {
        data() {
         return {//demo.html在 放在 assets/app 中的表 静太文件
             name: "webView",
             path:{path :'demo.html',poststr:{"categories":["2012","2013","2014","2015","2016","2017"],"series":[{"name":"成交量A","data":[35,8,25,37,4,20]},{"name":"成交量B","data":[70,40,65,100,44,68]},{"name":"成交量C","data":[100,80,95,150,112,132]}]}},
             url:'',
             msg:'rrrr',
             list:[{name:'陈中国',id:1},
                 {name:'陈中国2',id:2},
                 {name:'陈中国3',id:3}]

         }
        },methods: {
            onLoad(p){
          /*  this.path.url ='http://192.168.1.25:8080/FPOS/demo.jsp' //list=+JSON.stringify(this.list)
            this.path.poststr='list='+JSON.stringify(this.list)
            this.log('url：'+this.path.url)
            this.log('poststr：'+this.path.poststr)
            */

            },
            htmlFinish(){//pref.getString('ip')+'

                modal.alert({message:'界面加载完成'});
                //this.html=pref.getString('ip')+'/common.do?testwebview'+'&test=asssssss'
                //this.newEvent()
            },
            newEvent(e){
                //e.data即为HTML传来的值
                //this.msg=e
                modal.alert({message:e.data});
            },excuJS(e){//第二个参数 传到HTML的数据
             /*   this.$refs.myWeb.JavatoHtml('save','',
              (res)=>{
                    //执行完成的回调
                  //this.alert(res)
                }) */

               // submit
                this.path.path ='demo.html' //list=+JSON.stringify(this.list)
              //  this.path.poststr='list='+JSON.stringify(this.list)
                this.log('path：'+this.path.path)
                this.log('poststr：'+this.path.poststr)

                this.path.poststr ={"categories":["2012","2013","2014"],"series":[{"name":"成交量A","data":[35,8,25,37,4,20]},{"name":"成交量B","data":[70,40,65,100,44,68]}]}


              /*  this.$refs.myWeb.htmlTOJava('submit',this.path.url,
                    (res)=>{
                    //执行完成的回调
                    this.alert(res)
                }
                ) */
                this.$refs.myWeb.submit(this.path,(res)=>{
                    this.alert(res)
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
