<template>
    <div class="layout">
        <head leftButton="" title="销售管理" rightButton=""></head>
        <scroller style="flex: 1;">
            <!--九宫格显示  <text class="iconfont bar-ic">&#xe614;</text> 动态显示要 即：字体编码后前四位是Unicode编码，想使用字符串来传递的话,只要将 “&#xe64b;” 改为 “\ue64b” 即可。-->
            <div style="margin-top: 10px;padding-left:30px;border-bottom-width: 2px;border-color: #dddddd;background-color: white;height: 100px;justify-content: space-between;align-items: center;flex-direction: row;">
                <text style="font-size:40px;font-weight:bold">分销管理</text>
            </div>
            <div class="sudoku_row">
                <div class="sudoku_item " :class="curSelect===sudoku.id?'opacity':''"    v-for="(sudoku,index) in sales" :key="index" @touchstart="touchstart(index)" @touchend="touchend(sudoku)" >
                    <div v-if="sudoku.name =='订单' ||  sudoku.name =='退货单'  "  style="height: 100px;width: 100px;border-radius:20px;border-width: 1px;border-color:#FFFFFF;background-color: orange;justify-content: center;align-items: center;"><text class="iconfont bar-ic">{{sudoku.img_src}}</text></div>
                    <text v-if="sudoku.name =='订单' ||  sudoku.name =='退货单'  " style="font-size: 30px;text-align: center;margin-top: 15px;width: 100px;">{{sudoku.name}} </text>
                    <div v-if="sudoku.name =='发货单' ||  sudoku.name =='收款单' || sudoku.name =='费用单' "  style="height: 100px;width: 100px;border-radius:20px;border-width: 1px;border-color:#FFFFFF;background-color:#0085ee;justify-content: center;align-items: center;"><text class="iconfont bar-ic">{{sudoku.img_src}}</text></div>
                    <text v-if="sudoku.name =='发货单' ||  sudoku.name =='收款单' || sudoku.name =='费用单' " style="font-size: 30px;text-align: center;margin-top: 15px;width: 100px;">{{sudoku.name}} </text>
                </div>
            </div>
           <!-- 应收汇总表显示-->
     <div style="justify-content: center;align-items: center; margin-top: 100px;"><text style="font-size: 30px">客户应收汇总表</text></div>
     <UChartsView ref="myWeb" @newEvent="newEvent" :path="path" @finish="htmlFinish" style="flex: 1;margin-top: 20px;align-items: center;justify-content:center">
     </UChartsView>

        </scroller>

    </div>
</template>

<script>
    var modal=weex.requireModule('modal');
    var nav = weex.requireModule('navigator') ;
    const progress = weex.requireModule('progress');
    var page=weex.requireModule("page")
    var pstatic=weex.requireModule("static")
    const net = weex.requireModule('net');
    const  pref=weex.requireModule('pref')
    import module1 from './jstools/mytool'// 引用方式
    let timestr=module1.formatDate((new Date()),"yyyy-MM-dd")

    export default {
        components:{
        },
        data() {
            return { //
                path:{path :'uCharts/pie.html',
                    poststr:{"series":[/*{"name":"鞍山-王鑫","data":270.00},{"name":"白城-王森","data":1560.00},{"name":"北行-李冬梅","data":5315.00},{"name":"鲅鱼圈-关德亮","data":10270.00},{"name":"鲅鱼圈-刘少美","data":1320.00},{"name":"北镇-李兰","data":4455.00},{"name":"长春-张强","data":25.00},{"name":"昌图-马军","data":35336.00},{"name":"茨榆陀-李辉","data":15430.00},{"name":"朝阳镇-李志国","data":9615.00},{"name":"大连开发区-阿南新玛特","data":4440.00},{"name":"大连-孟静","data":357400.00},{"name":"大石桥-张利新","data":20674.00},{"name":"阜新-陈永良","data":400.00},{"name":"阜新-赵长林","data":3000.00},{"name":"晖春-孙业宏","data":16435.00},{"name":"葫芦岛-李宝良","data":18895.00},{"name":"黑山-李兰","data":17780.00},{"name":"九台-赵红凯","data":5025.00},{"name":"金州-吴业波","data":6159.00},{"name":"开原-陈云飞","data":28745.00},{"name":"凌海-王春风","data":7955.00},{"name":"辽源百乐-杨晓志","data":80.00},{"name":"孟静新店","data":64447.00},{"name":"盘山-老三","data":7880.00},{"name":"绥化阳光地下-姜海洋","data":14725.00},{"name":"绥化正大鞋城-姜海洋","data":121370.00},{"name":"沈阳北行-朴野","data":20034.00},{"name":"沈阳时尚-吕广","data":14520.00},{"name":"沈阳皇寺-老三","data":113160.00},{"name":"沈阳-孟庆峰","data":17750.00},{"name":"沈阳-小明","data":2000.00},{"name":"铁岭-张宏涛","data":7825.00},{"name":"瓦房店-阿南","data":6080.00},{"name":"万和汇-阿南","data":22590.00},{"name":"海城【飞驼】店-王志海","data":7830.00},{"name":"亨达-王志海","data":3480.00},{"name":"新民-范东升","data":1060.00},{"name":"营口-马军","data":29282.00} */]
                    }},
                salesMenuRight:pstatic.get('objkey').salesMenuRight ||[],//发货单菜单权限
                salesReturnMenuRight:pstatic.get('objkey').salesReturnMenuRight||[],
                salesOrderMenuRight:pstatic.get('objkey').salesOrderMenuRight||[],
                receivalMenuRight:pstatic.get('objkey').receivalMenuRight||[],
                sales:[
                   // {id:5,name:'费用单',img_src:'\ue609',url:''}
                ],

            };
        },props:{
            curSelect:{
                default:100
            }
        },created(){
              debugger
            this.sales.splice(0,this.sales.length) //清空掉 embed 方法引入 ，所以这个要放在 created中才能生效


            if(this.salesOrderMenuRight[0].BrowseRight){
                this.sales.push({id:1,name:'订单',img_src:'\ue606',url:'salesorderlist.js'})
            }
            if(this.salesMenuRight[0].BrowseRight){
                this.sales.push({id:2,name:'发货单',img_src:'\ue6bc',url:'saleslist.js'})
            }
            if(this.salesReturnMenuRight[0].BrowseRight){
                this.sales.push({id:3,name:'退货单',img_src:'\ue82a',url:'saleslist.js'})
            }
            if(this.receivalMenuRight[0].BrowseRight){
                this.sales.push({id:4,name:'收款单',img_src:'\ue71d',url:'reclist.js'})
            }



        },
        methods:{
            onLoad(p){

            },
            touchstart:function(e){
                /* this.alert(e)
                 this.curSelect=Number(e)
                  var that = this;
                   var list = that.sudokus;
                   for (var i = 0, len = list.length; i < len; ++i) {
                       if (list[i].id == e) {
                           that.curSelect = i;
                       }
                   } */
            },touchend:function(ls){
                //  this.alert(JSON.stringify(ls))
                var that = this;
                var direction =1
                // var url=''
                if(ls.name=='发货单' || ls.name=='采购收货单'){
                    direction =1
                }else if(ls.name=='退货单' || ls.name=='采购退货单'){
                    direction =-1
                }
                if(ls.url !==''){
                    nav.pushFull({url: 'root:'+ls.url,param:{direction:direction,title:ls.name},animate:true},
                        (e)=>{
                        })
                }
                that.curSelect = null;
            },   newEvent(e){
                //e.data即为HTML传来的值
                //this.msg=e
                modal.alert({message:e.data});
            },htmlFinish(){//pref.getString('ip')+'
                var that=this
                //请求数据

                                   net.post(pref.getString('ip') + '/sales.do?salesreport',{BeginDate:timestr,EndDate:timestr},{},function(){
                                       //start
                                       progress.showFull('加载中',false)
                                   },function(e){
                                       //success
                                       if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {
                                           if(e.res.attributes.hasOwnProperty('series')) { //饼形图要求data:222 为数字不能带有引号
                                               for(var i=0;i<e.res.attributes.series.length;i++){
                                                   var m=e.res.attributes.series[i]
                                                   m.data =Number(m.data)//这里去掉引号的操作
                                                   that.path.poststr.series.push(m)
                                               }
                                              //that.log('path:'+JSON.stringify(that.path)) //这里原型输出有引号就有引号
                                               that.$refs.myWeb.submit(that.path,(res)=>{
                                                   //that.alert(res)
                                              })
                                           }
                                       }
                                       progress.dismiss()
                                   },function(e){
                                       //compelete

                                   },function(){
                                       //exception
                                       progress.dismiss()
                                   });


            },excuJS(e){

            }
        }
    }
</script>

<style scoped>
    .iconfont {
        font-family:iconfont;  /*必须写哦！ ！ width: 100px; */
    }
    .bar-ic{
        font-size: 80px;
        color: #FFFFFF;
        text-align: center;
    }
    .layout{
        /*  display: flex;
          align-items: center;
          width:100%;
          flex-wrap: wrap; background-color: #5ac3ff; */
        background-color:#FFF;
    }
    .sudoku_row{
        padding-top: 10px;
        background-color: #FFFFFF;
        flex-direction:row;
        flex-wrap: wrap;
    }
    .sudoku_item{
        /*   flex:1; display: flex;  justify-content: center; 250px;  border-width:1px;
        border-color: #eeeeee;*/
        justify-content: center;
        align-items:center;
        text-align: center;
        width: 180px;
        height: 180px;
    }
    .opacity{
        opacity: 0.4;
        background: #e5e5e5;
    }
    .sudoku_item img{
        margin-bottom: 3px;
        display: block;
    }
</style>