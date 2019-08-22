<template>
    <div class="wrapper">
        <head :rightText="rightText" title="货品管理列表"  @rightClick="rightClick"></head>
        <div class="search"> <!-- style="width: 500px;height: 80px;border-width: 5px;border-color: #00B4FF;margin-left: 10px"-->
            <input type="text" class="input" return-key-type="search" v-model="Code" @input="search" placeholder="输入货号或者名称"/>
            <div style="border-width: 1px;border-color: #ccc;height: 70px;border-radius: 3px;width: 200px;align-items:center;justify-content: center"><text @click="add" style="font-size: 35px;">增加货品</text> </div>
        </div>
        <list style="flex: 1;margin-bottom: 80px;">
            <refresh class="refresh" @refresh="onrefresh" @pullingdown="onpullingdown" :display="refreshing ? 'show' : 'hide'">
                <text class="indicator-text">Refreshing...</text>
                <loading-indicator class="indicator"></loading-indicator>
            </refresh>

            <cell ref="skid" v-for="(item, i) of data" @click="onNodeClick(item, i)" :key="'skid-' + i" class="wxc-skid" :style="{width: (750 + item.right.length * 100) + 'px', height: height + 'px'}" @touchstart="(e) => !isAndroid && onPanStart(e, item, i)" @horizontalpan="(e) => isAndroid && onPanStart(e, item, i)" @touchend="(e) => onPanEnd(e, item, i)">
                <div :style='styles' class="swipe-action-center border">

                    <!--   <slot :val='{item: item, index: i}'/> -->
                    <div style="justify-content: center;align-items: center;height: 300px"><text style="font-size: 35px;color:red">{{Number(i)+1}}</text></div>
                    <div class="left" >
                        <text style="font-size: 35px;height: 60px;font-weight:bold">{{item.Code}}</text>
                        <text style="font-size: 35px;height: 60px">{{item.Name}}</text>
                        <text style="font-size: 35px;height: 60px">货品子类别:{{item.subType}}</text>
                        <text style="font-size: 35px;height: 60px">季节:{{item.Season}}</text>
                        <text style="font-size: 35px;height: 60px">厂商:{{item.Supplier}}</text>
                        <text style="font-size: 35px;height: 60px">期初数据:{{item.Quantity}}</text>
                    </div>


                    <div class="right" style="position: absolute;right: 0;">
                        <text style="font-size: 35px;height: 60px">{{item.GoodsType}}</text>
                        <text style="font-size: 35px;height: 60px">年份:{{item.Age}}</text>
                        <text style="font-size: 35px;height: 60px">品牌:{{item.Brand}}</text>
                        <text style="font-size: 35px;height: 60px">零售价:{{item.RetailSales}}</text>
                        <text style="font-size: 35px;height: 60px">期初金额:{{item.Amount}}</text>
                    </div>

                </div>
                <!-- <text :style="{lineHeight: height + 'px'}" :class="['box-center', 'border', 'text', i + 1 === data.length && 'box-center-last']">{{item.title}}</text> -->
                <div class="swipe-action-right">
                    <text class="swipe-action-child swipe-action-text" @click="onRightNode(item, child, i)" v-for="(child, childIdx) of item.right" :style="Object.assign({lineHeight: height + 'px'}, child.style || {})" :key="'child-' + childIdx">{{child.text}}</text>
                </div>
            </cell>

            <!--  用于给列表添加上拉加载更多的功能-->
            <loading class="loading" @loading="onloading" :display="loadinging ? 'show' : 'hide'">
                <loading-indicator class="indicator"></loading-indicator>
                <text class="indicator-text">Loading...</text>
            </loading>
        </list>
        <div class="footer">
            <text style="font-size: 35px;color: white">新品生成采购订单</text>
        </div>
    </div>
</template>

<script>
    import { Utils } from 'weex-ui';
    import Binding from "weex-bindingx/lib/index.weex.js";
    const animation = weex.requireModule("animation");
    var modal = weex.requireModule("modal");
    var nav = weex.requireModule('navigator') ;
    const net = weex.requireModule('net');
    const  pref=weex.requireModule('pref');
    const url='/goodsInfo.do?glist'
    export default {
       components: {  },
        props: {
            data: {//列表
                type: Array,
                default:[
                    {Code:'193C03003',Name:'长袖连衣裙',subType:'',Season:'秋',Supplier:'广州十三行',Quantity:10,GoodsType:'裙子',Age:'2019',
                        Brand:'爱衣服',RetailSales:299.00,Amount:29900.00,
                        right:[
                            {text: "删除"},{text: "审核",style: { backgroundColor: "#F4333C", color: "white" }},{text: "反审",style: { backgroundColor: "orange", color: "white" }}
                        ]
                    },
                    {Code:'193C03004',Name:'长袖连衣裙2',subType:'',Season:'秋',Supplier:'广州十三行',Quantity:10,GoodsType:'裙子',Age:'2018',
                        Brand:'爱衣服',RetailSales:299.00,Amount:29900.00,
                        right:[
                            {text: "删除"},{text: "审核",style: { backgroundColor: "#F4333C", color: "white" }},{text: "反审",style: { backgroundColor: "orange", color: "white" }}
                        ]
                    },{Code:'193C03005',Name:'长袖连衣裙3',subType:'',Season:'秋',Supplier:'广州十三行',Quantity:10,GoodsType:'裙子',Age:'2017',
                        Brand:'爱衣服',RetailSales:299.00,Amount:29900.00,
                        right:[
                            {text: "删除"},{text: "审核",style: { backgroundColor: "#F4333C", color: "white" }},{text: "反审",style: { backgroundColor: "orange", color: "white" }}
                        ]
                    },
                    {Code:'193C03006',Name:'长袖连衣裙3',subType:'',Season:'秋',Supplier:'广州十三行',Quantity:10,GoodsType:'裙子',Age:'2017',
                        Brand:'爱衣服',RetailSales:299.00,Amount:29900.00,
                        right:[
                            {text: "删除"},{text: "审核",style: { backgroundColor: "#F4333C", color: "white" }},{text: "反审",style: { backgroundColor: "orange", color: "white" }}
                        ]
                    },
                    {Code:'193C03007',Name:'长袖连衣裙3',subType:'',Season:'秋',Supplier:'广州十三行',Quantity:10,GoodsType:'裙子',Age:'2017',
                        Brand:'爱衣服',RetailSales:299.00,Amount:29900.00,
                        right:[
                            {text: "删除"},{text: "审核",style: { backgroundColor: "#F4333C", color: "white" }},{text: "反审",style: { backgroundColor: "orange", color: "white" }}
                        ]
                    }

                ]
            }, height: {
                type: Number,
                default: 300
            },
        },
        data(){
            return{
                rightText:'',
                Code:'',
                page:1,
                loadinging:false,
                refreshing:false,
                goodslist:[],
                mobileX: 0,
                webStarX: 0,
                saveIdx: null,
                isAndroid: Utils.env.isAndroid()

            }

        },methods:{
           onLoad(p){
               var that=this
               net.post(pref.getString('ip')+url,{Code:"",page:that.page},{},function(){
                   //start
               },function(e){
                   //success
                   if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {
                      that.data =  e.res.obj || []
                   }


               },function(e){
                   //compelete

               },function(){
                   //exception
               });

           },add(){
                    nav.pushFull({url:'root:simplegoods.js',param:{}},(res)=>{
                        this.log('res的返回值：'+JSON.stringify(res))
                        if(JSON.stringify(res)!='{}'){
                            var map=this.checkdata(res)
                            if(map==undefined){
                                res.right= [
                                    {text: "删除"},{text: "审核",style: { backgroundColor: "#F4333C", color: "white" }},{text: "反审",style: { backgroundColor: "orange", color: "white" }}
                                ]
                                this.data.unshift(res)
                            }
                        }



                    })

           },search(){ //输入查询 要加个延时2秒
               let that=this
               setTimeout(()=>{
                   net.post(pref.getString('ip') + url,{Code:that.Code,page:that.page},{},function(){
                   },function(e){
                       if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {
                           that.data =  e.res.obj || []
                       }


                   },function(e){
                       //compelete

                   },function(){
                       //exception
                   });

               },2000)

            },  special(dom, styles) {
                animation.transition(dom, {
                    styles,
                    duration: 200, //ms
                    timingFunction: "ease",
                    delay: 100 //ms
                });
            }, onRightNode(pNode, node, i) {
                if (pNode.autoClose)
                    this.special(this.$refs.skid[i], {
                        transform: `translate(0, 0)`
                    });
            },onNodeClick(node, i) {
                if (this.mobileX < 0) {
                    this.mobileX = 0;
                    this.special(this.$refs.skid[this.saveIdx], {
                        transform: `translate(0, 0)`
                    });
                    this.isAndroid &&
                    this.special(this.$refs.skid[i], {
                        transform: `translate(0, 0)`
                    });
                }else{

                    nav.pushFull({url:'root:simplegoods.js',param:{GoodsID:node.GoodsID,editflag:true}},(res)=>{
                        this.log('res的返回值：'+JSON.stringify(res))




                    })
                }


            },checkdata(map){ //查看 data中是否有这条数据
               for(var i=0;i<this.data.length;i++){
                   if(map.GoodsID==this.data[i].GoodsID){
                       return this.data[i]
                   }
               }
               return undefined
            },

            onPanEnd(e, node, i) {
                if (Utils.env.isWeb()) {
                    const webEndX = e.changedTouches[0].pageX;
                    this.movingDistance(webEndX - this.webStarX, node, this.$refs.skid[i]);
                }
            },
            onPanStart: function(e, node, i) {
                const { saveIdx } = this;
                if (saveIdx !== i && saveIdx !== null && this.$refs.skid[saveIdx]) {
                    this.special(this.$refs.skid[saveIdx], {
                        transform: `translate(0, 0)`
                    });
                    this.mobileX = 0;
                }
                this.saveIdx = i;
                !Utils.env.isWeb() ? this.mobile(e, node, i) : this.web(e, node, i);
                e.stopPropagation();
            },
            web(e, node, i) {
                this.webStarX = e.changedTouches[0].pageX;
            },
            mobile(e, node, i) {
                var el = this.$refs["skid"][i];
                Binding.bind(
                    {
                        anchor: el.ref,
                        eventType: "pan",
                        props: [
                            {
                                element: el.ref,
                                property: "transform.translateX",
                                expression: `x+${this.isAndroid ? 0 : this.mobileX}`
                            }
                        ]
                    },
                    e => {
                        const { state, deltaX } = e;
                        if (state === "end") {
                            this.mobileX += deltaX;
                            this.movingDistance(this.mobileX, node, el);
                        }
                    }
                );
            },
            movingDistance(scope, node, el) {
                const len = node.right ? node.right.length : 0;
                const distance = len * -100;
                if (scope < -30) {
                    this.special(el, {
                        transform: `translate(${distance}px, 0)`
                    });
                    this.mobileX = distance;
                } else {
                    this.special(el, {
                        transform: `translate(0, 0)`
                    });
                    this.mobileX = 0;
                }
            },onrefresh(event) {
                modal.toast({
                    message: "refresh",
                    duration: 1
                });
                var that=this
                this.refreshing = true;
                setTimeout(() => {
                    //  this.goodslist = [1, 2, 3, 4, 5]; 上拉先不管
                    net.post(pref.getString('ip')+url,{Code:that.Code,page:1},{},function(){
                        //start
                    },function(e){
                        //success
                        if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {
                            that.data =  e.res.obj || []
                        }


                    },function(e){
                        //compelete

                    },function(){
                        //exception
                    });

                    this.refreshing = false;
                }, 2000);
            },onpullingdown(event) {
                modal.toast({
                    message: "刚开始下拉....",
                    duration: 1
                });
            },onloading(event) { //上拉加载更多
                 var that=this
                this.loadinging = true;
                modal.toast({
                    message: "loading",
                    duration: 1
                });
                setTimeout(()=>{
                    this.page=Number(this.page) +Number(1)
                    net.post(pref.getString('ip')+url,{Code:that.Code,page:that.page},{},function(){
                        //start
                    },function(e){
                        //success
                        if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {
                            var array=e.res.obj || []
                            for(var i=0;i<array.length;i++){
                                that.data.push(array[i])
                            }
                        }


                    },function(e){
                        //compelete

                    },function(){
                        //exception
                    });

                    this.loadinging = false;
                },2000)

           }
        }
    }
</script>

<style scoped>
    .wrapper{
        flex-direction: column;

    }
    .search{
        flex-direction: row;
        width: 750px;
        margin-top: 5px;
        justify-content: center;
        align-self: center;
    }
    .border {
        border-bottom-width: 1px;
        border-bottom-color: #dddddd;
    }
    .wxc-skid {
        flex-direction: row;
        background-color: #FFFFFF;
    }
    .swipe-action-right {
        flex-direction: row;
        align-items: center;
    }

    .swipe-action-center {
        width: 750px;
        flex-direction: row;

    }
    .swipe-action-child {
            width: 100px;
            text-align: center;
            color: #FFFFFF;
            background-color: #dddddd;
            line-height: 90px;

        }
    .swipe-action-text {
        font-size: 30px;
    }
    .footer{
        position: absolute;
        bottom: 0;
        right: 0;
        left: 0;
        justify-content: center;
        align-items: center;
        height: 80px;
        background-color: orange;
        width:750px;

    }
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
    .input{
        width: 500px;
        height: 70px;
        border-width: 1px;
        border-color:#ccc ;
        padding: 7px 0px;
        border-radius: 3px; /*css3属性IE不支持 border: 5px solid #ccc;*/
        padding-left:5px;
    }
    .input:focus{
        border-color: #66afe9;
        outline: 0;
        -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6);
        box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgba(102,175,233,.6)
    }
</style>