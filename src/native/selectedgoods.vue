<template>
    <div class="wrapper">
        <head :rightText="rightText" title="已选列表"  @rightClick="rightClick"></head>
        <list style="height:970px">
                <cell ref="skid" v-for="(ls,i) in detaillist" @click="onNodeClick(ls, i)" style="flex-direction: row;height:290px;border-bottom-width: 1px;border-color: #dddddd">
                    <!-- 整体包1-->
                    <div :style='styles' class="swipe-action-center border">

                        <div style="flex-direction: row"><text style="font-size: 30px;color: red;">{{ls.Code}}</text> <text style="font-size: 30px;color:#000000; margin-left: 20px">{{ls.Name}}</text></div>
                        <!-- 图片与显示-->
                        <div style="flex-direction: row;width: 750px">
                            <text style="font-size: 30px;color:#000000;">{{Number(i)+1}}</text>
                            <image src="https://www.baidu.com/img/bd_logo1.png" style="width: 150px;height: 150px;border-width: 3px"></image>
                            <div>
                                <text style="font-size: 30px;color:#000000;height: 50px">颜色:{{ls.Color}}</text>
                                <text style="font-size: 30px;color:#000000;height: 50px">折扣率:{{ls.Discount}}</text>
                                <text style="font-size: 30px;color:#000000;height: 50px">数量:{{ls.Quantity}}</text>
                            </div>
                            <div style="position: absolute;right: 10px;">
                                <text style="font-size: 30px;color:#000000;height: 50px;">单价:29.50</text>
                                <text style="font-size: 30px;color:#000000;height: 50px;">折扣:9.0</text>
                                <text style="font-size: 30px;color:#000000;height: 50px;">金额:29.50</text>
                            </div>
                        </div>
                        <!-- 图片与显示-->
                        <div style="flex-direction: row">
                            <text style="font-size: 30px;color:red;margin-left: 5px;width: 40px;text-align: center" v-for="(lst,index1) in ls.sizetitle">{{lst.title}}</text>
                        </div>
                        <div style="flex-direction: row">
                            <text style="font-size: 30px;color:#000000;margin-left: 5px;width: 40px;text-align: center" v-for="(lst2,index2) in ls.sizeData">{{lst2.Quantity }}</text>
                        </div>

                    </div>  <!-- 整体包1结束-->
                    <!-- 菜单
                    <div class="swipe-action-right">
                        <text class="swipe-action-child swipe-action-text" @click="onRightNode(ls, child, i)" v-for="(child, childIdx) of ls.right" :style="Object.assign({lineHeight: height + 'px'}, child.style || {})" :key="'child-' + childIdx">{{child.text}}</text>
                    </div>
                     -->
                </cell>



        </list>
        <div @click="submit" style="position: absolute;left: 50px;right: 50px;bottom: 0;justify-content: center;align-items: center;height:100px;border-radius:20px;background-color: orange">
            <text style="font-size: 35px;color: #FFFFFF">保存返回列表</text>
        </div>
    </div>
</template>

<script>
    var nav = weex.requireModule('navigator') ;
    export default {
        components: {},
        props: {
            data: {
                type: Array,
                default: []
            },
            height: {
                type: Number,
                default: 290
            },
            styles: {
                type: Object,
                default: {}
            }
        },
        data(){
            return{
                submitmap:{},
                detaillist:[]

            }
        },methods:{
            onLoad(p){
              this.detaillist=p.detaillist
            },
            submit(e){
              var p={}
              p.detaillist=this.detaillist
              nav.backFull(p,false)
            },onNodeClick(node,i){

                var obj =(this.detaillist.filter(item=>item.GoodsID ==node.GoodsID)).map(function (obj) {  // node.sizeData.map(function (obj) {
                    return {
                        GoodsID: obj.GoodsID,
                        Code:obj.Code,
                        Name:obj.Name,
                        RetailSales:obj.RetailSales,
                        ColorID: obj.ColorID,
                        Quantity: obj.Quantity,
                        UnitPrice:obj.UnitPrice,
                        DiscountRate:obj.DiscountRate,
                        tipqty: obj.Quantity,
                        title: obj.Color,
                        Amount:obj.Amount,
                        sizeData :obj.sizeData


                    }
                })

                //   this.alert('obj：' + JSON.stringify(obj)+',记录数：'+obj.length)
                var arr = [] ,   sizearr=[]
                for (var i = 0; i < obj.length; i++) {
                    debugger
                    var map = {}
                    map.GoodsID = obj[i].GoodsID
                    map.Code = obj[i].Code
                    map.Name = obj[i].Name
                    map.RetailSales=obj[i].RetailSales
                    map.ColorID = obj[i].ColorID
                    map.Quantity = obj[i].Quantity
                    map.UnitPrice=obj[i].UnitPrice
                    map.DiscountRate=obj[i].DiscountRate
                    map.tipqty = obj[i].tipqty
                    map.title = obj[i].title
                    if(arr.length>0) {

                        var  m=this.hasmap(arr,map,0)
                        if(JSON.stringify(m)=='{}' || m==undefined){

                            arr.push(map)
                        }

                    }else {
                        arr.push(map)
                    }

                    //尺码集体
                    var sizeMap= obj[i]

                    for(var k=0;k <sizeMap.sizeData.length; k++){
                        //这里不存在重复尺码与颜色的
                        sizearr.push(sizeMap.sizeData[k])


                    }

                }










            arr[0].checked = true
            this.submitmap.colorlist = arr
            this.submitmap.sizelist = sizearr//node.sizeData//this.testlist
            //this.alert('挑选颜色列表对象：' + JSON.stringify(arr)+',颜色记录数：'+arr.length)
            //  this.alert('挑选尺码列表对象：' + JSON.stringify(sizearr)+',尺码记录数:'+sizearr.length)



        nav.pushFull({url: 'root:goodsDetail.js',param:this.submitmap,animate:true}
            , (e) => {
                if (e !== undefined){ //返回结果
                    // this.alert('返回的数据:'+JSON.stringify(e)+',记录数： '+e.detaillist.length)
                    if(e.detaillist.length >0) {

                        for(var i=0;i<e.detaillist.length ;i++){

                            var backdata=e.detaillist[i]
                            if(this.detaillist.length>0){
                                var m=this.hasmap(this.detaillist,backdata,1) //已经累加到货品颜色的值
                                if(JSON.stringify(m) !=='{}') {
                                    for(var j=0;j<backdata.sizeData.length;j++) {
                                        var sizemap=backdata.sizeData[j]
                                        var n = this.hasSize(m.sizeData, sizemap)
                                    }
                                }else if(JSON.stringify(m) =='{}'){
                                    this.detaillist.unshift(backdata)
                                }
                            }else{
                                this.detaillist.unshift(backdata)
                            }



                        }

                        this.alert(JSON.stringify(this.detaillist))

                    }
                }

            })


            }, hasmap(arr,map,isback){ //有一个就返回，最后判断，没有返回undefined,isback代表从其他页面返回的结果
                var m={}
                for (var j = 0; j < arr.length; j++) {
                    if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && isback==0 ) {
                        arr[j].Quantity = Number(arr[j].Quantity) + Number(map.Quantity)
                        arr[j].tipqty = Number(arr[j].tipqty) + Number(map.tipqty)
                        m=arr[j]
                    }else  if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && isback==1 ) {
                        arr[j].Quantity = Number(map.Quantity)
                        arr[j].tipqty =  Number(map.tipqty)
                        m=arr[j]
                    }
                }
                return m


            },hasSize(arr,map){
            var m={}
            for (var j = 0; j < arr.length; j++) {

                if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && arr[j].SizeID ==map.SizeID) {
                    arr[j].Quantity = map.Quantity
                    arr[j].Amount =map.Amount
                    m  = arr[j]

                }
            }
            return m
        }

        }
    }
</script>

<style scoped>

</style>