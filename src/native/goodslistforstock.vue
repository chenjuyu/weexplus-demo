<template>
    <div class="wrapper">
        <head :rightText="rightText" title="货品列表"  @rightClick="rightClick"></head>
        <wxc-searchbar ref="wxc-searchbar"
                       @wxcSearchbarCancelClicked="wxcSearchbarCancelClicked"
                       @wxcSearchbarInputReturned="wxcSearchbarInputReturned"
                       @wxcSearchbarInputOnInput="wxcSearchbarInputOnInput"
                       @wxcSearchbarCloseClicked="wxcSearchbarCloseClicked"
                       @wxcSearchbarInputOnFocus="wxcSearchbarInputOnFocus"
                       @wxcSearchbarInputOnBlur="wxcSearchbarInputOnBlur"></wxc-searchbar>
        <list style="flex: 1;margin-bottom: 100px"> <!-- background-color: red height: 910px; 用于测试高度  refresh用于给列表添加下拉刷新的功能。 -->

            <refresh class="refresh" @refresh="onrefresh" @pullingdown="onpullingdown" :display="refreshing ? 'show' : 'hide'">
                <text class="indicator-text">Refreshing...</text>
                <loading-indicator class="indicator"></loading-indicator>
            </refresh>



            <cell v-for="(item,i) in templist " @click="onNodeClick(item, i)" style="flex-direction: row;height:170px;border-bottom-width: 1px;border-color: #dddddd">
                <div style="width: 150px;height: 150px;margin-left: 20px;justify-content: center;align-items: center">

                    <image   :src="item.img?item.img:'root:img/nopic.jpg'" style="width: 100px;height: 100px;"></image>
                    <!-- v-if="item.img"  <image v-else  src="root:img/nopic.jpg" style="width: 100px;height: 100px;"></image> -->

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
            <div @click="selectedgoods" style="width:375px;height: 100px;border-width: 5px;border-color: #0085ee;align-items: center;justify-content: center;border-radius:20px">
                <text style="font-size: 35px;">已选货品({{selectedQuanity}})</text></div>
            <div @click="goSalesAdd" style="width:375px;height: 100px;border-width: 5px;border-color: #0085ee;align-items: center;justify-content: center;border-radius:20px">
                <text style="font-size: 35px;">下一步></text>
            </div>
        </div>
    </div>
</template>

<script>
    import { WxcSearchbar } from 'weex-ui'
    var modal = weex.requireModule("modal");
    var nav = weex.requireModule('navigator') ;
    const net = weex.requireModule('net');
    const  pref=weex.requireModule('pref');
    const url='/goodsInfo.do?goodslist'
    export default {
        components: { WxcSearchbar },
        data(){
            return{
                addflag:false,//单据新增标志
                loadinging: false,
                refreshing: false,
                Code:'',//搜索条件&page=1
                page:1,//当前页数
                CustomerID:'',
                SupplierID:'',
                Type:'',
                submitmap:{},
                templist:[],
                selectedlist:[],//已选列表，修改界面，点增加按扭进来，要保存单据已有的数据
                selectedQuanity:0,//已选列表，总数，用于显示
                goodslist:[]


            }
        },methods: {
            onLoad(p){
                //...this.goodslist
                var that=this
                // this.goodslist =p.detaillist || []
                if(p !=null && p !=undefined && JSON.stringify(p) !="{}"){
                    this.addflag =p.hasOwnProperty("addflag")?p.addflag:false
                    this.CustomerID =p.hasOwnProperty("CustomerID")?p.CustomerID:''
                    this.SupplierID =p.hasOwnProperty("SupplierID")?p.SupplierID:''
                    this.Type =p.hasOwnProperty("Type")?p.Type:''
                }
                //有数据时单据
                this.selectedlist =p.detaillist || []//JSON.parse(JSON.stringify(this.goodslist.filter(map =>(map.Quantity !==0 && map.Quantity !=='')))) || []

                this.totalSelectedQuantity()

                net.post(pref.getString('ip') + url,{Code:this.Code,page:this.page,CustomerID:p.CustomerID,SupplierID:that.SupplierID,Type:p.Type},{},function(){
                    //start
                },function(e){
                    //success
                    //self.back=e.res;
                    //that.alert('e的返回：'+JSON.stringify(e))
                    if(e.res.attributes !==undefined) {
                        that.goodslist = e.res.attributes.goodslist || []

                        for(var i=0;i<that.goodslist.length;i++) {
                            //进出仓单要新增字段 RelationUnitPrice，RelationAmount
                            Vue.set(that.goodslist[i], 'RelationUnitPrice', '')
                            Vue.set(that.goodslist[i], 'RelationAmount', '')
                        }


                        //   if(that.goodslist.length>0 && that.selectedlist.length >0){
                        for(var i=0;i<that.selectedlist.length ;i++){
                            var m1=that.selectedlist[i]
                            //    this.alert('执行次数：'+i)
                            for(var j=0;j<that.goodslist.length ;j++){
                                var m2=that.goodslist[j]
                                if(m1.GoodsID==m2.GoodsID && m1.ColorID==m2.ColorID){
                                    m2.Quantity=m1.Quantity
                                    m2.sizeData =m1.sizeData
                                }
                            }
                        }

                        //   }
                        that.templist =that.goodsfilter(JSON.parse(JSON.stringify(that.goodslist)))


                    }

                },function(e){
                    //compelete

                },function(){
                    //exception
                    that.alert('请求异常')
                });



                //  this.alert('经过方法过滤后的templist：'+JSON.stringify(this.templist))
                // this.alert('goodslist：'+JSON.stringify(this.goodslist))

            },totalSelectedQuantity(){
                var sumqty=0
                if(this.selectedlist.length>0) {
                    for(var i =0 ;i<this.selectedlist.length ;i++) {
                        sumqty =Number(sumqty)+Number(this.selectedlist[i].Quantity)
                    }
                    this.selectedQuanity =sumqty
                }
            }
            ,onloading(event) {
                modal.toast({
                    message: "loading",
                    duration: 1
                });
                this.loadinging = true;
                var that=this
                that.page =Number(that.page)+Number(1)
                setTimeout(() => {
                    const length = that.goodslist.length;
                    net.post(pref.getString('ip') + url,{Code:that.Code,page:that.page,CustomerID:that.CustomerID,SupplierID:that.SupplierID,Type:that.Type},{},function(){

                    },function(e){
                        //let i = length; i < length + 5; i++
                        for (let i = 0; i <e.res.attributes.goodslist.length; i++) {
                            var map=e.res.attributes.goodslist[i] || {}

                               if(!map.hasOwnProperty('RelationUnitPrice')) //进出新加的字段
                               {
                                   Vue.set(map,'RelationUnitPrice','')
                                   Vue.set(map,'RelationAmount','')
                               }

                            var cm=that.hasmap(that.goodslist,map,0)//这里是每一项
                            if(cm==undefined){
                                that.goodslist.push(map);//i + 1
                            } else if(cm !=undefined){
                                for(var j=0;j<map.sizeData.length;j++){
                                    var m=map.sizeData[j]
                                    var zm=that.hasSize(cm.sizeData,m,1)
                                    if( zm==undefined){
                                        cm.sizeData.push(m)
                                    }
                                }

                            }
                        }
                        that.templist =that.goodsfilter(JSON.parse(JSON.stringify(that.goodslist))) //刷新列表
                        that.loadinging = false;


                    },function(e){
                        //compelete

                    },function(){
                        //exception
                    });




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
            },onNodeClick(item2,i){
                //this.goodslist.filter(item=>item.GoodsID ==item2.GoodsID))
               //  this.log('全部的：'+JSON.stringify(this.goodslist))
                var obj =(this.goodslist.filter(item=>item.GoodsID ==item2.GoodsID)).map(function (obj) {  // node.sizeData.map(function (obj) {
                    return {
                        GoodsID: obj.GoodsID,
                        Code:obj.Code,
                        img:obj.img,
                        Name:obj.Name,
                        RetailSales:obj.RetailSales,
                        ColorID: obj.ColorID,
                        Quantity: obj.Quantity,
                        UnitPrice:obj.UnitPrice,
                        RelationUnitPrice:obj.RelationUnitPrice,
                        RelationAmount:obj.RelationAmount,
                        DiscountRate:obj.DiscountRate,
                        tipqty: obj.Quantity,
                        title: obj.Color,
                        Amount:obj.Amount,
                        sizeData :obj.sizeData,
                        sizetitle:obj.sizetitle


                    }
                })
                 // this.log('颜色列表obj：'+JSON.stringify(obj))
                var arr = [] ,   sizearr=[]
                for (var i = 0; i < obj.length; i++) {
                    debugger
                    var map = {}
                    map.GoodsID = obj[i].GoodsID
                    map.Code = obj[i].Code
                    map.img=obj[i].img
                    map.Name=obj[i].Name
                    map.RetailSales=obj[i].RetailSales
                    map.ColorID = obj[i].ColorID
                    map.Quantity = obj[i].Quantity
                    map.UnitPrice =obj[i].UnitPrice
                    map.RelationUnitPrice =obj[i].RelationUnitPrice
                    map.RelationAmount=obj[i].RelationAmount
                    map.Amount =obj[i].Amount
                    map.DiscountRate=obj[i].DiscountRate
                    map.tipqty = obj[i].tipqty
                    map.title = obj[i].title
                    map.sizetitle=obj[i].sizetitle
                    if(arr.length>0) {

                        var  m=this.hasmap(arr,map,0)
                        if(m==undefined){

                            arr.push(map)
                        }

                    }else {
                        arr.push(map)
                    }

                    //尺码集体
                    var sizeMap= obj[i]

                    for(var k=0;k <sizeMap.sizeData.length; k++){
                        //这里不存在重复尺码与颜色的
                        //新增货品，尺码集合是没有RelationAmount RelationUnitPrice字段的，详情中就会有返回
                        if(!sizeMap.sizeData[k].hasOwnProperty('RelationUnitPrice')){ //不存在
                           Vue.set(sizeMap.sizeData[k],'RelationUnitPrice','')//增加一个
                            Vue.set(sizeMap.sizeData[k],'RelationAmount','')
                        }
                        sizearr.push(sizeMap.sizeData[k])


                    }

                }//颜色结果 for 结束
                arr[0].checked = true

                // this.alert('颜色列表：'+JSON.stringify(arr.filter(map=>map.checked)))

                this.submitmap.colorlist = arr
                this.submitmap.sizelist = sizearr
                this.submitmap.addflag=this.addflag

             //   this.log('submitmap的值:'+JSON.stringify(this.submitmap))

                nav.pushFull({url: 'root:goodsDetailforstock.js',param:this.submitmap,animate:true}
                    , (e) => {
                        //   this.alert('e:'+e)
                        if (e !== undefined){ //返回结果  这里返回 的一个货号的
                            // this.alert('返回的数据:'+JSON.stringify(e)+',记录数： '+e.detaillist.length)
                            if(e ==null){ //无结果返回，指的是点左上角返回菜单的返回
                                return
                            }
                            var templist =e.detaillist ||[]
                            for(var i=0;i<templist.length ;i++){
                                var backdata=templist[i]
                                var m=this.hasmap(this.goodslist,backdata,1) //1 替换到货品颜色的值 整个货品列表
                                if(m != undefined) {//更新尺码
                                    for(var j=0;j<backdata.sizeData.length;j++) {
                                        var sizemap=backdata.sizeData[j]
                                        var n = this.hasSize(m.sizeData, sizemap,0)
                                    }
                                }else {
                                    this.goodslist.unshift(backdata)
                                }

                                var tempselect= this.hasmap(this.selectedlist,backdata,1) //已选货品列表
                                if(tempselect ==undefined){
                                    this.selectedlist.unshift(backdata)
                                }else {
                                    for(var j=0;j<backdata.sizeData.length;j++) {
                                        var smap=backdata.sizeData[j]
                                        this.hasSize(tempselect.sizeData, smap,0)   //替换原来 的值 0
                                    }
                                }

                            }
                            //goodslist 代表所有货品 ，不管有没数量 这里只有有数量 的都要 但不确定是否有问题
                            //this.selectedlist  这里是单据详情过来的，可能 有数据 ，也可能没有

                            //  this.alert('selectedlist:'+JSON.stringify(this.selectedlist))
                            this.totalSelectedQuantity()
                            this.templist =this.goodsfilter(JSON.parse(JSON.stringify(this.goodslist)))
                            //  this.alert(JSON.stringify(this.goodslist))

                        }


                    })
            },goodsfilter(arr){

                var returnlist=[]
                for(var i=0; i<arr.length;i++) { //每次都重新刷新
                    var map = arr[i]
                    var m=this.hasGoodsID(returnlist,map)
                    if(m==undefined){
                        returnlist.push(map)
                    }
                }
                return  returnlist;

            },selectedgoods(){ //只作显示 从已选列表返回
                //   this.selectedlist=JSON.parse(JSON.stringify(this.goodslist.filter(map =>(map.Quantity !==0 && map.Quantity !==''))))
                 this.log("selectedlist的值:"+JSON.stringify(this.selectedlist))
                this.submitmap.detaillist =this.selectedlist ||[] //this.goodslist.filter(map =>(map.Quantity !==0 && map.Quantity !=='') )
                this.submitmap.addflag=this.addflag
                //    this.alert("selectedlist发到已选列表的："+JSON.stringify(this.submitmap.detaillist))
                nav.pushFull({url: 'root:selectgoodsforstock.js',param:this.submitmap,animate:true}
                    , (e) => {
                        if(e !=undefined) { //返回的一定是已选列表里面有的货品
                            var tempselect =e.detaillist || []

                            //this.alert("tempselect:"+JSON.stringify(tempselect))

                            for (var i = 0; i < tempselect.length; i++) {
                                var tmap=tempselect[i]
                                if (Number(tmap.Quantity) > 0) {
                                    var m= this.hasmap(this.selectedlist,tmap,1)
                                    if(m !=undefined){
                                        m.sizeData=tmap.sizeData
                                    }


                                }
                            }//tempselect for 结束
                         //   this.alert("selectedlist:"+JSON.stringify(this.selectedlist))
                            //     if(this.goodslist.length>0 && this.selectedlist.length>0) {
                            for(var i2 =0 ; i2<this.selectedlist.length ;i2++){
                                var map=this.selectedlist[i2]
                                var m=this.hasmap(this.goodslist,map,1) //返回的是替换
                            }

                            //    }
                            this.totalSelectedQuantity()
                            this.templist = this.goodsfilter(JSON.parse(JSON.stringify(this.goodslist)))


                        }
                    })

            }
            ,hasGoodsID(ls,map){

                for(var i=0;i<ls.length ;i++){
                    if(ls[i].GoodsID == map.GoodsID ){
                        ls[i].Quantity =Number(ls[i].Quantity)+Number(map.Quantity)
                        var  m= ls[i]

                        return  m
                    }
                }
                return undefined;
            },hasmap(arr,map,isback){ //有一个就返回，最后判断，没有返回undefined,isback代表从其他页面返回的结果
                for (var j = 0; j < arr.length; j++) {
                    if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && isback==0 ) {
                        arr[j].Quantity = Number(arr[j].Quantity) + Number(map.Quantity)
                        arr[j].Amount = Number(arr[j].Amount) + Number(map.Amount)
                        arr[j].RelationAmount = Number(arr[j].RelationAmount) + Number(map.RelationAmount)
                        arr[j].tipqty = Number(arr[j].tipqty) + Number(map.tipqty)
                        var m=arr[j]
                        return m
                    }else  if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && isback==1 ) {
                        arr[j].Quantity = Number(map.Quantity)
                        arr[j].UnitPrice = Number(map.UnitPrice)
                        arr[j].DiscountRate = Number(map.DiscountRate)
                        arr[j].RelationUnitPrice=Number(map.RelationUnitPrice)
                        if(map.DiscountRate !=0 && map.DiscountRate !='' && map.DiscountRate !=undefined){
                            arr[j].Amount =Number(map.Quantity) * Number(map.UnitPrice) //* Number(map.DiscountRate) /10.0
                            arr[j].RelationAmount =Number(map.Quantity) * Number(map.RelationUnitPrice) * Number(map.DiscountRate) /10.0
                        }else{
                            arr[j].Amount =Number(map.Quantity) * Number(map.UnitPrice)
                            arr[j].RelationAmount=Number(map.Quantity) * Number(map.RelationUnitPrice)
                        }

                        arr[j].tipqty =  Number(map.tipqty)
                        var m=arr[j]
                        return m
                    }
                }
                return undefined


            },hasSize(arr,map,append){//用于过滤 sizeData

                for (var j = 0; j < arr.length; j++) {

                    if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && arr[j].SizeID ==map.SizeID && append==0) {
                        arr[j].Quantity = map.Quantity
                        arr[j].UnitPrice = map.UnitPrice
                        arr[j].DiscountRate = map.DiscountRate
                        arr[j].Amount =map.Amount
                        arr[j].RelationAmount =map.RelationAmount
                        arr[j].RelationUnitPrice=Number(map.RelationUnitPrice)
                        var m  = arr[j]
                        return m
                    }else if(arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && arr[j].SizeID ==map.SizeID && append==1){
                        arr[j].Quantity = Number(arr[j].Quantity)+Number(map.Quantity)
                        arr[j].Amount =Number(arr[j].Amount)+Number(map.Amount)
                        arr[j].RelationAmount =Number(arr[j].RelationAmount)+Number(map.RelationAmount)
                        var  m  = arr[j]
                        return m
                    }
                }
                return undefined
            },goSalesAdd(){ //返回单据详情
                var p={}
                p.detaillist= this.selectedlist || [] //已选列表
                nav.backFull(p,false)
            }
            //搜索框
            , wxcSearchbarInputOnFocus () {
            },
            wxcSearchbarInputOnBlur (e) {
                // this.alert("e的值:"+JSON.stringify(e))
                var that=this
                // this.value = e.value;
                // this.alert("e的值:"+e.value)
                // if(e.value.length >3) {
                net.post(pref.getString('ip') + url, {Code:e.value, page:1,CustomerID:that.CustomerID,SupplierID:that.SupplierID,Type:that.Type}, {}, function () {
                    //start
                }, function (e) {
                    //success
                    // that.alert("e的值："+JSON.stringify(e))
                    if(e.res.toLogin){
                        that.alert('登录超时')
                        that.push("root:index.js")
                    }
                    if(e.res.success && e.res.msg=='暂无数据'){
                        that.toast(e.res.msg)
                        return
                    }
                    if(!e.res.attributes.hasOwnProperty("goodslist")){
                        that.toast('暂无数据')
                        return
                    }
                    if(e.res.attributes.goodslist.length>0) {
                        that.goodslist = e.res.attributes.goodslist || []
                        that.templist = that.goodsfilter(JSON.parse(JSON.stringify(that.goodslist)))
                    }
                }, function (e) {
                    //compelete

                }, function () {
                    //exception
                });


            },
            wxcSearchbarInputReturned (e){


            },
            wxcSearchbarCloseClicked () {
            },
            wxcSearchbarInputOnInput (e) {


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
