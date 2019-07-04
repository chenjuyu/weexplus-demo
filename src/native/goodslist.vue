<template>
    <div class="wrapper">
        <head :rightText="rightText"  @rightClick="rightClick"></head>
        <wxc-searchbar ref="wxc-searchbar"
                       @wxcSearchbarCancelClicked="wxcSearchbarCancelClicked"
                       @wxcSearchbarInputReturned="wxcSearchbarInputReturned"
                       @wxcSearchbarInputOnInput="wxcSearchbarInputOnInput"
                       @wxcSearchbarCloseClicked="wxcSearchbarCloseClicked"
                       @wxcSearchbarInputOnFocus="wxcSearchbarInputOnFocus"
                       @wxcSearchbarInputOnBlur="wxcSearchbarInputOnBlur"></wxc-searchbar>
        <list style="height: 910px;"> <!-- background-color: red 用于测试高度  refresh用于给列表添加下拉刷新的功能。 -->

            <refresh class="refresh" @refresh="onrefresh" @pullingdown="onpullingdown" :display="refreshing ? 'show' : 'hide'">
                <text class="indicator-text">Refreshing...</text>
                <loading-indicator class="indicator"></loading-indicator>
            </refresh>



           <cell v-for="(item,i) in templist " onclick="onNodeClick(ls, i)" style="flex-direction: row;height:170px;border-bottom-width: 1px;border-color: #dddddd">
            <div style="width: 150px;height: 150px;margin-left: 20px;justify-content: center;align-items: center">
            <image :src="item.img" style="width: 100px;height: 100px;"></image>
            <text v-if="item.Quantity" style="font-size: 35px;color:#FFFFFF;position:absolute;bottom: 0;left:0;right: 0;height: 40px;background-color: orange;text-align: center">{{item.Quantity}}</text>
            </div>
            <div style="margin-left: 30px">
            <text style="font-size: 35px;">货品编码:{{item.Code}}</text>
            <text style="font-size: 35px;margin-top: 15px">货品名称:{{item.Name}}</text>
            <text style="font-size: 35px;margin-top: 15px">零售价:{{item.RetailSales}}</text>
           </div>
           </cell>
            <!--  用于给列表添加上拉加载更多的功能-->
            <loading class="loading" @loading="onloading" :display="loadinging ? 'show' : 'hide'">
                <loading-indicator class="indicator"></loading-indicator>
                <text class="indicator-text">Loading...</text>
            </loading>



        </list>
        <div style="position: absolute;bottom: 0;left:0;right: 0;flex-direction: row">
           <div style="width:375px;height: 100px;border-width: 5px;border-color: #0085ee;align-items: center;justify-content: center;border-radius:20px">
               <text style="font-size: 35px;">已选货品</text></div>
            <div style="width:375px;height: 100px;border-width: 5px;border-color: #0085ee;align-items: center;justify-content: center;border-radius:20px">
                <text style="font-size: 35px;">下一步></text>
            </div>
        </div>
    </div>
</template>

<script>
    import { WxcSearchbar } from 'weex-ui'
    var modal = weex.requireModule("modal");
    export default {
        components: { WxcSearchbar },
        data(){
            return{
                loadinging: false,
                refreshing: false,
                templist:[],
                goodslist:[
                    {GoodsID:'00AG',Code:'192B1210088',Name:'外披',ColorID:'0BA',Color:'黑色',img:'root:img/logo.png',
                        Discount:0.0,DiscountRate:8.0,Quantity:0,Amount:34.5,RetailSales:59.00,
                        sizetitle:[
                            {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                            ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                            {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                            ,{filed:'x_10',title:'5L'}
                        ],
                        sizeData:[{GoodsID:'00AG',ColorID:'0BA',Color:'黑色',x:'x_1',Quantity:1,SizeID:'00A',Size:'均码',Amount:''},
                            {GoodsID:'00AG',ColorID:'0BA',Color:'黑色',x:'x_2',Quantity:1,SizeID:'00A',Size:'XS',Amount:''}]
                        ,right: [
                            /*  {
                                  text: "置顶",
                                  onPress: function() {
                                      modal.toast({
                                          message: "置顶",
                                          duration: 0.3
                                      });
                                  }
                              }, */
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white" }
                            }
                        ]
                    }
                    ,{GoodsID:'00AG',Code:'192B1210017',Name:'外披aa',ColorTitle:'颜色',ColorID:'0BB',Color:'白色',img:'root:img/logo.png',
                        Discount:0.0,DiscountRate:8.0,Quantity:0,Amount:34.5,RetailSales:59.00,
                        sizetitle:[
                            {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                            ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                            {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                            ,{filed:'x_10',title:'5L'}
                        ],
                        sizeData:[{GoodsID:'00AG',ColorID:'0BB',x:'x_1',Quantity:1,SizeID:'00A',Color:'白色',Size:'37',Amount:''},
                            {GoodsID:'00AG',ColorID:'0BB',Color:'白色',x:'x_2',Quantity:2,SizeID:'00B',Size:'38',Amount:''}]
                        ,right: [
                            /*  {
                                  text: "置顶",
                                  onPress: function() {
                                      modal.toast({
                                          message: "置顶",
                                          duration: 0.3
                                      });
                                  }
                              }, */
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white" }
                            }
                        ]
                    },{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BC',Color:'黄色',img:'root:img/logo.png',
                        Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,RetailSales:59.00,
                        sizetitle:[
                            {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                            ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                            {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                            ,{filed:'x_10',title:'5L'}
                        ],
                        sizeData:[{GoodsID:'00AG',ColorID:'0BC',x:'x_1',Size:'均码',Color:'黄色',SizeID:'00B',Quantity:1,Amount:''},
                            {GoodsID:'00AG',ColorID:'0BC',Color:'黄色',x:'x_2',SizeID:'00C',Size:'XS',Quantity:2,Amount:''}]
                        ,right: [
                            /*  {
                                   text: "置顶",
                                   onPress: function() {
                                       modal.toast({
                                           message: "置顶",
                                           duration: 0.3
                                       });
                                   }
                               }, */
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white" }
                            }
                        ]
                    }
                    ,{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BD',Color:'绿色',img:'root:img/logo.png',
                        Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,RetailSales:59.00,
                        sizetitle:[
                            {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                            ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                            {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                            ,{filed:'x_10',title:'5L'}
                        ],
                        sizeData:[{GoodsID:'00AG',ColorID:'0BD',x:'x_1',SizeID:'00A',Size:'均码',Quantity:1,Amount:''},
                            {GoodsID:'00AG',ColorID:'0BD',x:'x_2',SizeID:'00D',Size:'XS',Quantity:2,Amount:''}]
                        ,right: [
                            /*  {
                                  text: "置顶",
                                  onPress: function() {
                                      modal.toast({
                                          message: "置顶",
                                          duration: 0.3
                                      });
                                  }
                              }, */
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white" }
                            }
                        ]
                    },{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BD',Color:'绿色',img:'root:img/logo.png',
                        Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,RetailSales:59.00,
                        sizetitle:[
                            {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                            ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                            {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                            ,{filed:'x_10',title:'5L'}
                        ],
                        sizeData:[{GoodsID:'00AG',ColorID:'0BD',x:'x_1',SizeID:'00A',Size:'均码',Quantity:1,Amount:''},
                            {GoodsID:'00AG',ColorID:'0BD',x:'x_2',SizeID:'00D',Size:'XS',Quantity:2,Amount:''}]
                        ,right: [
                            /*  {
                                  text: "置顶",
                                  onPress: function() {
                                      modal.toast({
                                          message: "置顶",
                                          duration: 0.3
                                      });
                                  }
                              }, */
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white" }
                            }
                        ]
                    },{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BD',Color:'绿色',img:'root:img/logo.png',
                        Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,RetailSales:59.00,
                        sizetitle:[
                            {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                            ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                            {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                            ,{filed:'x_10',title:'5L'}
                        ],
                        sizeData:[{GoodsID:'00AG',ColorID:'0BD',x:'x_1',SizeID:'00A',Size:'均码',Quantity:1,Amount:''},
                            {GoodsID:'00AG',ColorID:'0BD',x:'x_2',SizeID:'00D',Size:'XS',Quantity:2,Amount:''}]
                        ,right: [
                            /*  {
                                  text: "置顶",
                                  onPress: function() {
                                      modal.toast({
                                          message: "置顶",
                                          duration: 0.3
                                      });
                                  }
                              }, */
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white" }
                            }
                        ]
                    },{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BD',Color:'绿色',img:'root:img/logo.png',
                        Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,RetailSales:59.00,
                        sizetitle:[
                            {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                            ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                            {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                            ,{filed:'x_10',title:'5L'}
                        ],
                        sizeData:[{GoodsID:'00AG',ColorID:'0BD',x:'x_1',SizeID:'00A',Size:'均码',Quantity:1,Amount:''},
                            {GoodsID:'00AG',ColorID:'0BD',x:'x_2',SizeID:'00D',Size:'XS',Quantity:2,Amount:''}]
                        ,right: [
                            /*  {
                                  text: "置顶",
                                  onPress: function() {
                                      modal.toast({
                                          message: "置顶",
                                          duration: 0.3
                                      });
                                  }
                              }, */
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white" }
                            }
                        ]
                    }

                ]
            }
        },methods: {
            onLoad(p){
                this.templist =this.goodsfilter(this.goodslist)
            },onloading(event) {
                modal.toast({
                    message: "loading",
                    duration: 1
                });
                this.loadinging = true;
                setTimeout(() => {
                    const length = this.goodslist.length;
                    for (let i = length; i < length + 5; i++) {
                        var map={GoodsID:'00AG',Code:'192B1210017',Name:'下拉加载的',ColorTitle:'颜色',ColorID:'0BD',Color:'绿色',img:'root:img/logo.png',
                            Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,RetailSales:59.00,
                            sizetitle:[
                                {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                ,{filed:'x_10',title:'5L'}
                            ],
                            sizeData:[{GoodsID:'00AG',ColorID:'0BD',x:'x_1',SizeID:'00A',Size:'均码',Quantity:1,Amount:''},
                                {GoodsID:'00AG',ColorID:'0BD',x:'x_2',SizeID:'00D',Size:'XS',Quantity:2,Amount:''}]
                            ,right: [
                                /*  {
                                      text: "置顶",
                                      onPress: function() {
                                          modal.toast({
                                              message: "置顶",
                                              duration: 0.3
                                          });
                                      }
                                  }, */
                                {
                                    text: "删除",
                                    onPress: () => {
                                        modal.toast({
                                            message: "删除",
                                            duration: 0.3
                                        });
                                    },
                                    style: { backgroundColor: "#F4333C", color: "white" }
                                }
                            ]
                        }
                        this.goodslist.push(map);//i + 1
                    }
                    this.loadinging = false;
                }, 2000);
            }, onrefresh(event) {
                modal.toast({
                    message: "refresh",
                    duration: 1
                });
                this.refreshing = true;
                setTimeout(() => {
                  //  this.goodslist = [1, 2, 3, 4, 5]; 上拉先不管
                    this.refreshing = false;
                }, 2000);
            },onpullingdown(event) {
                modal.toast({
                    message: "刚开始下拉....",
                    duration: 1
                });
            },onNodeClick(item,i){


            },goodsfilter(arr){ //此数组是精确到颜色的汇总  数量
                //this.alert("arr过滤后的数组："+arr.length)
              if(arr.length >0){ //要返回一个与货品汇总 数量的
               var returnlist=[]
               for(var i=0; i<arr.length;i++){
                   var map=arr[i]
                 if(returnlist.length>0){
                     var m=this.hasGoodsID(returnlist,map)
                     if(JSON.stringify(m) =='{}'){
                         returnlist.push(map)
                     }
                 }else{ //第一条
                 returnlist.push(map)
                 }
               }
            //   this.alert("过滤后的数组kkkk："+JSON.stringify(returnlist))
              return  returnlist;
              }
            },hasGoodsID(ls,map){
               var m={}
               for(var i=0;i<ls.length ;i++){
                   if(ls[i].GoodsID ==map.GoodsID){
                       ls[i].Quantity =Number(ls[i].Quantity)+Number(map.Quantity)
                       m= ls[i]
                       return  m
                   }
               }
               return m;
            }
            //搜索框
           , wxcSearchbarInputOnFocus () {
            },
            wxcSearchbarInputOnBlur () {
            },
            wxcSearchbarInputReturned (){
            },
            wxcSearchbarCloseClicked () {
            },
            wxcSearchbarInputOnInput (e) {
                this.value = e.value;
            },
            wxcSearchbarCancelClicked () {
            },
            wxcSearchbarInputDisabledClicked () {
            },
            wxcSearchbarDepChooseClicked () {
            } //搜索框结束
        }
    }
</script>

<style scoped>
    .indicator-text {
        font-size: 42px;
        text-align: center;
        width: 750px;
    }
    .indicator {
        margin-top: 16px;
        height: 60px;
        width: 60px;
        margin-left: 345px;
        color: blue;
    }

</style>