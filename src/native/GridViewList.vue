<template>
    <div class="layout">

        <head leftButton="" title="主页" rightButton=""></head>
        <scroller style="flex: 1;">
            <!--九宫格显示  <text class="iconfont bar-ic">&#xe614;</text> 动态显示要 即：字体编码后前四位是Unicode编码，想使用字符串来传递的话,只要将 “&#xe64b;” 改为 “\ue64b” 即可。-->

            <div v-if="baseFlag"  style="margin-top: 10px;padding-left:30px;border-bottom-width: 2px;border-color: #dddddd;background-color: white;height: 100px;justify-content: space-between;align-items: center;flex-direction: row;">
                <text style="font-size:40px;font-weight:bold">基本资料</text>
            </div>
            <div class="sudoku_row">
                <div class="sudoku_item " :class="curSelect===sudoku.id?'opacity':''"   v-for="(sudoku,index) in base" :key="index" @touchstart="touchstart(index)" @touchend="touchend(sudoku)" >
                    <div v-if="sudoku.id ==2"  style="height: 100px;width: 100px;border-radius:20px;border-width: 1px;border-color:#FFFFFF;background-color: #0085ee;justify-content: center;align-items: center;"><text class="iconfont bar-ic">{{sudoku.img_src}}</text></div>
                    <text v-if="sudoku.id ==2"  style="font-size: 30px;text-align: center;margin-top: 15px;width: 120px;">{{sudoku.name}} </text>
                    <div v-if="sudoku.id ==1"  style="height: 100px;width: 100px;border-radius:20px;border-width: 1px;border-color:#FFFFFF;background-color: orange;justify-content: center;align-items: center;"><text class="iconfont bar-ic">{{sudoku.img_src}}</text></div>
                    <text v-if="sudoku.id ==1" style="font-size: 30px;text-align: center;margin-top: 15px;width: 120px;">{{sudoku.name}} </text>

                </div>
            </div>
            <!--九宫格显示 -->
            <div v-if="purchaseFlag" style="margin-top: 10px;padding-left:30px;border-bottom-width: 2px;border-color: #dddddd;background-color: white;height: 100px;justify-content: space-between;align-items: center;flex-direction: row;">
                <text style="font-size:40px;font-weight:bold">采购管理</text>
            </div>
            <div class="sudoku_row">
                <div class="sudoku_item " :class="curSelect===sudoku.id?'opacity':''"   v-for="(sudoku,index) in purchase" :key="index" @touchstart="touchstart(index)" @touchend="touchend(sudoku)" >
                    <div v-if="sudoku.id ==2"  style="height: 100px;width: 100px;border-radius: 50px;border-width: 1px;border-color:#FFFFFF;background-color:mediumspringgreen;justify-content: center;align-items: center;">
                        <text class="iconfont bar-ic">{{sudoku.img_src}}</text>
                    </div>
                    <text v-if="sudoku.id ==2"  style="font-size: 30px;text-align: center;margin-top: 15px;width: 150px;">{{sudoku.name}} </text>
                    <div v-if="sudoku.id ==1"  style="height: 100px;width: 100px;border-radius:50px;border-width: 1px;border-color:#FFFFFF;background-color: orange;justify-content: center;align-items: center;"><text class="iconfont bar-ic">{{sudoku.img_src}}</text></div>
                    <text v-if="sudoku.id ==1" style="font-size: 30px;text-align: center;margin-top: 15px;width: 150px;">{{sudoku.name}} </text>

                    <div v-if="sudoku.id ==3"  style="height: 100px;width: 100px;border-radius: 50px;border-width: 1px;border-color:#FFFFFF;background-color:mediumorchid;justify-content: center;align-items: center;">
                        <text class="iconfont bar-ic">{{sudoku.img_src}}</text>
                    </div>
                    <text v-if="sudoku.id ==3"  style="font-size: 30px;text-align: center;margin-top: 15px;width: 150px;">{{sudoku.name}} </text>

                    <div v-if="sudoku.id ==4"  style="height: 100px;width: 100px;border-radius: 50px;border-width: 1px;border-color:#FFFFFF;background-color:orange;justify-content: center;align-items: center;">
                        <text class="iconfont bar-ic">{{sudoku.img_src}}</text>
                    </div>
                    <text v-if="sudoku.id ==4"  style="font-size: 30px;text-align: center;margin-top: 15px;width: 150px;">{{sudoku.name}} </text>

                </div>
            </div>

            <!--九宫格显示 sudoku_item  代替每一个 -->
            <div v-if="salesFlag" style="margin-top: 10px;padding-left:30px;border-bottom-width: 2px;border-color: #dddddd;background-color: white;height: 100px;justify-content: space-between;align-items: center;flex-direction: row;">
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

            <!--九宫格显示 -->
            <div v-if="stockFlag" style="margin-top: 10px;padding-left:30px;border-bottom-width: 2px;border-color: #dddddd;background-color: white;height: 100px;justify-content: space-between;align-items: center;flex-direction: row;">
                <text style="font-size:40px;font-weight:bold">库存管理</text>
            </div>
            <div class="sudoku_row">
                <div class="sudoku_item " :class="curSelect===sudoku.id?'opacity':''"   v-for="(sudoku,index) in stock" :key="index" @touchstart="touchstart(index)" @touchend="touchend(sudoku)" >

                    <div v-if="sudoku.id ==1"  style="height: 100px;width: 100px;border-radius: 50px;border-width: 1px;border-color:#FFFFFF;background-color:
#99FF33;justify-content: center;align-items: center;">
                        <text class="iconfont bar-ic">{{sudoku.img_src}}</text>
                    </div>
                    <text v-if="sudoku.id ==1"  style="font-size: 30px;text-align: center;margin-top: 15px;width: 150px;">{{sudoku.name}} </text>

                    <div v-if="sudoku.id ==2"  style="height: 100px;width: 100px;border-radius: 50px;border-width: 1px;border-color:#FFFFFF;background-color:
#FF8800;justify-content: center;align-items: center;">
                        <text class="iconfont bar-ic">{{sudoku.img_src}}</text>
                    </div>
                    <text v-if="sudoku.id ==2"  style="font-size: 30px;text-align: center;margin-top: 15px;width: 150px;">{{sudoku.name}} </text>

                    <div v-if="sudoku.id ==3"  style="height: 100px;width: 100px;border-radius: 50px;border-width: 1px;border-color:#FFFFFF;background-color:

#B94FFF;justify-content: center;align-items: center;">
                        <text class="iconfont bar-ic">{{sudoku.img_src}}</text>
                    </div>
                    <text v-if="sudoku.id ==3"  style="font-size: 30px;text-align: center;margin-top: 15px;width: 150px;">{{sudoku.name}} </text>

                </div>
            </div>

            <!-- 零售管理-->
            <div v-if="possalesFlag" style="margin-top: 10px;padding-left:30px;border-bottom-width: 2px;border-color: #dddddd;background-color: white;height: 100px;justify-content: space-between;align-items: center;flex-direction: row;">
                <text style="font-size:40px;font-weight:bold">零售管理</text>
            </div>
            <div class="sudoku_row">
                <div class="sudoku_item " :class="curSelect===sudoku.id?'opacity':''"   v-for="(sudoku,index) in possales" :key="index" @touchstart="touchstart(index)" @touchend="touchend(sudoku)" >

                <div v-if="sudoku.id ==1"  style="height: 100px;width: 100px;border-radius: 20px;border-width: 1px;border-color:#FFFFFF;background-color:
#FF8800;justify-content: center;align-items: center;">
                    <text class="iconfont bar-ic">{{sudoku.img_src}}</text>
                </div>
                    <text v-if="sudoku.id ==1"  style="font-size: 30px;text-align: center;margin-top: 15px;width: 150px;">{{sudoku.name}} </text>

                </div>

            </div>

        </scroller>
    </div>
</template>

<script>
    var nav = weex.requireModule('navigator') ;
    var page=weex.requireModule("page")
    var pstatic=weex.requireModule("static")
    export default {
        components:{
        },
        data() {
            return {
                baseFlag:false,
                salesFlag:false,
                purchaseFlag:false,
                stockFlag:false,
                possalesFlag:false,
                salesMenuRight:pstatic.get('objkey').salesMenuRight ||[],//发货单菜单权限
                salesReturnMenuRight:pstatic.get('objkey').salesReturnMenuRight||[],
                salesOrderMenuRight:pstatic.get('objkey').salesOrderMenuRight||[],
                receivalMenuRight:pstatic.get('objkey').receivalMenuRight||[],

                purchaseMenuRight:pstatic.get('objkey').purchaseMenuRight,
                purchaseReturnMenuRight:pstatic.get('objkey').purchaseReturnMenuRight,
                purchaseOrderMenuRight:pstatic.get('objkey').purchaseOrderMenuRight,
                paymentMenuRight:pstatic.get('objkey').paymentMenuRight,

                stockMoveMenuRight:pstatic.get('objkey').stockMoveMenuRight,
                stockInMenuRight:pstatic.get('objkey').stockInMenuRight,
                stockOutMenuRight:pstatic.get('objkey').stockOutMenuRight,

                posSalesMenuRight :pstatic.get('objkey').posSalesMenuRight,

                goodsUserMenuRight:pstatic.get('objkey').goodsUserMenuRight,

                stock:[]
                ,base:[

                ]
                ,purchase:[
                ]
                ,sales:[

                  //  {id:5,name:'费用单',img_src:'\ue609',url:''}
                ],
                possales:[
                    {id:1,name:'销售小票',img_src:'\ueb4c',url:'poslist.js'}
                  /*  {id:2,name:'日结',img_src:'\ue6b7'},
                    {id:3,name:'缴费',img_src:'\ue601'},
                    {id:4,name:'报表',img_src:'\ue6af'} */
                ]
            };
        },props:{
            curSelect:{
                default:100
            }
        },created(){
            this.sales.splice(0,this.sales.length) //清空掉 embed 方法引入 ，所以这个要放在 created中才能生效

            if(this.salesOrderMenuRight[0].BrowseRight){
                this.sales.push({id:1,name:'订单',img_src:'\ue606',url:'salesorderlist.js'})
                this.salesFlag =true //总标题显示
            }
            if(this.salesMenuRight[0].BrowseRight){
                this.sales.push({id:2,name:'发货单',img_src:'\ue6bc',url:'saleslist.js'})
                this.salesFlag =true //总标题显示
            }
            if(this.salesReturnMenuRight[0].BrowseRight){
                this.sales.push({id:3,name:'退货单',img_src:'\ue82a',url:'saleslist.js'})
                this.salesFlag =true //总标题显示
            }
            if(this.receivalMenuRight[0].BrowseRight){
                this.sales.push({id:4,name:'收款单',img_src:'\ue71d',url:'reclist.js'})
                this.salesFlag =true //总标题显示
            }

            this.purchase.splice(0,this.purchase.length) //清空掉 embed 方法引入 ，所以这个要放在 created中才能生效

            if(this.purchaseOrderMenuRight[0].BrowseRight){
                this.purchase.push({id:1,name:'采购订单',img_src:'\ue62d',url:'purchaseorderlist.js'})
                this.purchaseFlag=true
            }
            if(this.purchaseMenuRight[0].BrowseRight){
                this.purchase.push({id:2,name:'采购收货单',img_src:'\ue629',url:'purchaselist.js'})
                this.purchaseFlag=true
            }
            if(this.purchaseReturnMenuRight[0].BrowseRight){
                this.purchase.push({id:3,name:'采购退货单',img_src:'\ue628',url:'purchaselist.js'})
            }
            if(this.paymentMenuRight[0].BrowseRight){
                this.purchase.push({id:4,name:'付款单',img_src:'\ue636',url:'paylist.js'})
                this.purchaseFlag=true
            }

            this.stock.splice(0,this.stock.length)
           if(this.stockMoveMenuRight[0].BrowseRight){
               this.stock.push({id:1,name:'转仓单',img_src:'\ue732',url:'stockmovelist.js'})
               this.stockFlag=true
           }
            if(this.stockInMenuRight[0].BrowseRight){
                this.stock.push({id:2,name:'进仓单',img_src:'\ue635',url:'stocklist.js'})
                this.stockFlag=true
            }

            if(this.stockOutMenuRight[0].BrowseRight){
                this.stock.push({id:3,name:'出仓单',img_src:'\ue60e',url:'stocklist.js'})
                this.stockFlag=true
            }

            this.possales.splice(0,this.possales.length)
            if(this.posSalesMenuRight[0].BrowseRight){
                this.possales.push({id:1,name:'销售小票',img_src:'\ueb4c',url:'poslist.js'})
                this.possalesFlag=true
            }

             this.base.splice(0,this.base.length)
            if(this.goodsUserMenuRight[0].BrowseRight){ //货品资料操作权限
              this.base.push({id:1,name:'货品资料',img_src:'\ue60b',url:'goodsmanager.js'})
              this.base.push({id:2,name:'货品拍照',img_src:'\ue7bc',url:'goodsphoto.js'})
                this.baseFlag =true//总标题显示
            }

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
                if(ls.name=='发货单' || ls.name=='采购收货单' || ls.name=='进仓单'){
                    direction =1
                }else if(ls.name=='退货单' || ls.name=='采购退货单' || ls.name=='出仓单'){
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