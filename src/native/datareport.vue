<template>
    <div class="layout">

        <head leftButton="" title="数据分析" rightButton=""></head>
        <scroller style="flex: 1;">
            <!--九宫格显示  <text class="iconfont bar-ic">&#xe614;</text> 动态显示要 即：字体编码后前四位是Unicode编码，想使用字符串来传递的话,只要将 “&#xe64b;” 改为 “\ue64b” 即可。-->


            <!--九宫格显示 -->


            <!--九宫格显示 sudoku_item  代替每一个 -->
            <div style="margin-top: 10px;padding-left:30px;border-bottom-width: 2px;border-color: #dddddd;background-color: white;height: 100px;justify-content: space-between;align-items: center;flex-direction: row;">
                <text style="font-size:40px;font-weight:bold">零售类</text>
            </div>
            <div class="sudoku_row">
                <div class="sudoku_item " :class="curSelect===sudoku.id?'opacity':''"    v-for="(sudoku,index) in sales" :key="index" @touchstart="touchstart(index)" @touchend="touchend(sudoku)" >
                    <div v-if="sudoku.name =='零售分析' ||  sudoku.name =='收款单' || sudoku.name =='费用单' "  style="height: 100px;width: 100px;border-radius:20px;border-width: 1px;border-color:#FFFFFF;background-color:#0085ee;justify-content: center;align-items: center;"><text class="iconfont bar-ic">{{sudoku.img_src}}</text></div>
                    <text v-if="sudoku.name =='零售分析' ||  sudoku.name =='收款单' || sudoku.name =='费用单' " style="font-size: 30px;text-align: center;margin-top: 15px;width: 100px;">{{sudoku.name}} </text>
                </div>
            </div>

            <!--九宫格显示
            <div style="margin-top: 10px;"><text>库存管理</text></div>
            <div class="sudoku_row">
                <div class="sudoku_item " :class="curSelect===sudoku.id?'opacity':''"   v-for="(sudoku,index) in sudokus" :key="index" @touchstart="touchstart(index)" @touchend="touchend" >
                    <div> <text class="iconfont bar-ic" style="width: 72px;height: 72px">{{sudoku.img_src}}</text>
                        <text>{{sudoku.name}} </text>
                    </div>
                </div>
            </div> -->

            <!--九宫格显示
            <div style="margin-top: 10px;"><text>零售管理</text> <text class="iconfont bar-ic">&#xeb4c;</text> </div>
            <div class="sudoku_row">
                <div class="sudoku_item " :class="curSelect===sudoku.id?'opacity':''"   v-for="(sudoku,index) in possales" :key="index" @touchstart="touchstart(index)" @touchend="touchend" >
                    <div> <text class="iconfont bar-ic" style="width: 72px;height: 72px">{{sudoku.img_src}}</text>
                        <text>{{sudoku.name}} </text>
                    </div>
                </div>
            </div>
              -->

        </scroller>
    </div>
</template>

<script>
    var nav = weex.requireModule('navigator') ;
    var page=weex.requireModule("page")
    export default {
        name: "GridViveList",
        components:{
        },
        data() {
            return {
                sudokus:[
                    /*  {
                      id:0,
                      name:'飞机',
                      img_src:'\ue614' //https://gw.alicdn.com/tfs/TB1kCk2SXXXXXXFXFXXXXXXXXXX-72-72.png
                  },{
                      id:1,
                      name:'游戏',
                      img_src:'\ue614'//require('../static/img/game.png')
                  },{
                      id:2,
                      name:'音乐',
                      img_src:'\ue614'//require('../static/img/music.png')
                  },{
                      id:3,
                      name:'物流',
                      img_src:'\ue614'//require('../static/img/logistics.png')
                  },{
                      id:4,
                      name:'医院',
                      img_src:'\ue614'//require('../static/img/hospital.png')
                  } */
                ]
                ,base:[
                    {id:1,name:'货品资料',img_src:'\ue60b',url:'goodsmanager.js'},
                    {id:2,name:'货品拍照',img_src:'\ue7bc',url:'goodsphoto.js'},
                ]
                ,purchase:[
                    {id:1,name:'采购收货单',img_src:'\ue629',url:'purchaselist.js'},
                    {id:2,name:'采购退货单',img_src:'\ue628',url:'purchaselist.js'},
                ]
                ,sales:[
                    {id:1,name:'零售分析',img_src:'\ue606',url:'selecttab.js'},

                ],
                possales:[
                    {id:1,name:'销售',img_src:'\ueb4c'},
                    {id:2,name:'日结',img_src:'\ue6b7'},
                    {id:3,name:'缴费',img_src:'\ue601'},
                    {id:4,name:'报表',img_src:'\ue6af'}
                ]
            };
        },props:{
            curSelect:{
                default:100
            }
        },created(){
            /*   let domModule = weex.requireModule('dom');
               domModule.addRule('fontFace',{
                   'fontFamily': "iconfont",
                   'src': "url('http://at.alicdn.com/t/font_1074303_e64s32fcc65.ttf')"
               }) */
        },
        methods:{
            onLoad(p){
                /* page.doubleBack();
                     page.enableBackKey(true);
                     page.setBackKeyCallback(()=>{
                         page.exit();

                     }) */
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
        background-color:#dddddd;
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