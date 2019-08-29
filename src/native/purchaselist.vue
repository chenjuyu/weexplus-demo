<template>
    <div class="container">
        <head :rightText="rightText" title="采购收货单"  @rightClick="rightClick"></head>
        <div class="search">
            <input type="text" style="width: 500px;height: 80px;border-width: 5px;border-color: #00B4FF;margin-left: 10px" return-key-type="search" v-model="No" @input="search" placeholder="输入单号查询"/>
            <div style="border-width: 5px;border-color: #00B4FF;height: 80px;width: 200px;align-items:center;justify-content: center"><text @click="add" style="font-size: 35px;">增加单据</text> </div>
        </div>
        <list style="flex: 1; margin-top: 20px;margin-bottom: 80px;">
            <cell ref="skid" v-for="(item, i) of data" @click="onNodeClick(item, i)" :key="'skid-' + i" class="wxc-skid" :style="{width: (750 + item.right.length * 100) + 'px', height: height + 'px'}" @touchstart="(e) => !isAndroid && onPanStart(e, item, i)" @horizontalpan="(e) => isAndroid && onPanStart(e, item, i)" @touchend="(e) => onPanEnd(e, item, i)">
                <div :style='styles' class="swipe-action-center border">

                    <!--   <slot :val='{item: item, index: i}'/> -->
                    <div style="justify-content: center;align-items: center;height: 300px"><text style="font-size: 35px;color:red">{{Number(i)+1}}</text></div>
                    <div class="left">
                        <text style="font-size: 35px;height: 60px;font-weight:bold">{{item.No}}</text>
                        <text style="font-size: 35px;height: 60px">{{item.Supplier}}</text>
                        <text style="font-size: 35px;height: 60px">类型:{{item.Type}}</text>
                        <text style="font-size: 35px;height: 60px">经手人:{{item.Name}}</text>
                        <text style="font-size: 35px;height: 60px">数量:{{item.QuantitySum}}</text>
                    </div>

                    <image src="root:img/Audit.png" class="input_bg" v-if="item.AuditFlag"></image>

                    <div class="right" style="position: absolute;right: 0">
                        <text style="font-size: 35px;height: 60px"></text>
                        <text style="font-size: 35px;height: 60px">{{item.Date}}</text>
                        <text style="font-size: 35px;height: 60px">部门:{{item.Department}}</text>
                        <text style="font-size: 35px;height: 60px">制单人:{{item.MadeBy}}</text>
                        <!--    <text style="font-size: 35px;height: 60px">应收余额:{{item.LastNeedRAmount}}</text> -->
                        <text style="font-size: 35px;height: 60px">金额:{{item.AmountSum}}</text>
                    </div>

                </div>
                <!-- <text :style="{lineHeight: height + 'px'}" :class="['box-center', 'border', 'text', i + 1 === data.length && 'box-center-last']">{{item.title}}</text> -->
                <div class="swipe-action-right">
                    <text class="swipe-action-child swipe-action-text" @click="onRightNode(item, child, i)" v-for="(child, childIdx) of item.right" :style="Object.assign({lineHeight: height + 'px'}, child.style || {})" :key="'child-' + childIdx">{{child.text}}</text>
                </div>
            </cell>

        </list>
        <div class="footer">
            <div style="height: 80px;justify-content: center;align-items: center"><text style="font-size: 40px;color: #FFFFFF";>合计：{{totalQty}}</text></div>
            <div style="height: 80px;justify-content: center;align-items: center;position: absolute;right: 0;bottom: 0"> <text style="font-size: 40px;color: #FFFFFF;">￥{{totalAmt}}</text></div>
        </div>
    </div>
</template>

<script>
    import module1 from './jstools/mytool'// 引用方式
    var nav = weex.requireModule('navigator') ;
    import { Utils } from 'weex-ui';
    import Binding from "weex-bindingx/lib/index.weex.js";
    const animation = weex.requireModule("animation");
    const modal = weex.requireModule("modal");
    var nav = weex.requireModule('navigator') ;
    const  pref=weex.requireModule('pref')
    const net = weex.requireModule('net');

    var date = new Date();//获取当前时间
    date.setDate(date.getDate()-8);//设置天数 -1 天
    let beginTime=module1.formatDate((date),"yyyy-MM-dd")
    let endTime=module1.formatDate((new Date()),"yyyy-MM-dd")

    var url='/purchase.do?purchaselist'
    var auditurl ='/purchase.do?auditOrder'
    export default {
        components: {

        },
        props: {
            data: {
                type: Array,
                default: [
                    {
                        No:'DN0002507',Date:'2019-07-08',AuditFlag:true,AuditDate:'2019-07-08',MadeBy:'张一发',CustomerID:'008',Customer:'李二狗',SupplierID:'OAD',Supplier:'厂商1',DepartmentID:'00A',Department:'广州总公司',WarehouseID:'00B',Warehouse:'云南分店'
                        ,Type:'批发',EmployeeID:'0GA',Name:'张三',QuantitySum:80,AmountSum:1600.00
                        ,right:[
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "orange", color: "white" }
                            },
                            {
                                text: "审核",
                                onPress: function() {
                                    modal.toast({
                                        message: "审核",
                                        duration: 0.3
                                    });
                                }
                            },
                            {
                                text: "反审",
                                onPress: () => {
                                    modal.toast({
                                        message: "反审",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white" }
                            }

                        ]
                    },
                    {
                        No:'DN0002506',Date:'2019-07-08',AuditFlag:false,AuditDate:'2019-07-08',MadeByDate:'2019-07-08 08:30:35',CustomerID:'008',Customer:'张三八',DepartmentID:'00A',Department:'广州总公司',WarehouseID:'00B',Warehouse:'云南分店'
                        ,Type:'批发',EmployeeID:'0GA',Name:'李四',LastNeedRAmount:2100,QuantitySum:70,AmountSum:1600.00
                        ,right:[
                            {
                                text: "删除",
                                onPress: () => {
                                    modal.toast({
                                        message: "删除",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "orange", color: "white" }
                            },
                            {
                                text: "审核",
                                onPress: function() {
                                    modal.toast({
                                        message: "审核",
                                        duration: 0.3
                                    });
                                }
                            },
                            {
                                text: "反审",
                                onPress: () => {
                                    modal.toast({
                                        message: "反审",
                                        duration: 0.3
                                    });
                                },
                                style: { backgroundColor: "#F4333C", color: "white"  }
                            }


                        ]
                    }


                ]
            },
            height: {
                type: Number,
                default: 300
            },
            styles: {
                type: Object,
                default: {}
            }
        },
        data() {
            return {
                No:'',
                totalQty:'',
                totalAmt:'',
                currPage:1,
                mobileX: 0,
                webStarX: 0,
                saveIdx: null,
                isAndroid: Utils.env.isAndroid(),
                data: [
                    {
                        title: "标题1",
                        right: [
                            {
                                text: "置顶",
                                onPress: function() {
                                    modal.toast({
                                        message: "置顶",
                                        duration: 0.3
                                    });
                                }
                            },
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
                    },
                    {
                        title: "标题2",
                        right: [
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
                    },
                    {
                        title: "标题3",
                        right: [
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
            };
        },
        methods: {
            onLoad(p){
                //   this.alert(this.data)
                var that=this
                var param={}
                param.currPage=this.currPage
                param.audit=''
                param.no=''
                param.beginDate=beginTime
                param.endDate=endTime
                param.departmentId=''
                param.customerId=''
                param.employeeId=''
                param.direction=1

                net.post(pref.getString('ip') + url,param,{},function(){
                    //start
                },function(e){
                    //success
                    //  self.back=e.res;
                    if(e !=null && e !=undefined ){
                        that.data =e.res.obj
                        that.total()
                    }

                },function(e){
                    //compelete

                },function(){
                    //exception
                });


            },total(){
                var q=Number(0)
                var a=Number(0)
                for(var i=0;i<this.data.length;i++){
                    q=q+Number(this.data[i].QuantitySum)
                    a=a+Number(this.data[i].AmountSum)
                }
                this.totalQty =q
                this.totalAmt =a
                if(this.totalAmt){ //这里表示 不等于0 与空
                    this.totalAmt =parseFloat(this.totalAmt).toFixed(2)
                }
            }
            ,add(e){
                nav.push('root:PurchaseAdd.js')
            },search(){
                var that=this
                var param={}
                param.currPage=this.currPage
                param.audit=''
                param.no=that.No
                param.beginDate=''
                param.endDate=''
                param.departmentId=''
                param.supplierId=''
                param.employeeId=''
                param.direction=this.direction
                setTimeout(() => {
                    net.post(pref.getString('ip') + url,param,{},function(){
                        //start
                    },function(e){
                        //success
                        //  self.back=e.res;
                        if(e !=null && e !=undefined ){
                            that.data =e.res.obj
                            that.total()
                        }

                    },function(e){
                        //compelete

                    },function(){
                        //exception
                    });
                }, 2000);//setTimeout 结束
            },
            special(dom, styles) {
                animation.transition(dom, {
                    styles,
                    duration: 200, //ms
                    timingFunction: "ease",
                    delay: 100 //ms
                });
            },
            onRightNode(pNode, node, i) {
                var that =this
                //node.onPress();

                var p={}
                if(node.text =='审核'){
                    if(pNode.AuditFlag){
                        that.toast('单据已审核')
                        return
                    }
                    p.direction ='1'
                    p.PurchaseID =pNode.PurchaseID
                    p.departmentid =pNode.DepartmentID
                    p.AuditFlag =1
                }else if(node.text =='反审'){
                    if(!pNode.AuditFlag){
                        that.toast('单据未审核不能反审')
                        return
                    }
                    p.AuditFlag =0
                    p.direction ='1'
                    p.SalesID =pNode.SalesID
                    p.departmentid =''
                }
                if(node.text =='审核' || node.text =='反审') {
                    net.post(pref.getString('ip') + auditurl, p, {}, function () {
                        //start
                    }, function (e) {
                        //success
                        if(e !=null && e !=undefined){
                            that.toast(e.res.msg)
                            var page=weex.requireModule("page")
                            page.reload();
                        }

                    }, function (e) {
                        //compelete

                    }, function () {
                        //exception
                        that.alert('异常')
                    });
                }
                if (pNode.autoClose)
                    this.special(this.$refs.skid[i], {
                        transform: `translate(0, 0)`
                    });

            },
            onNodeClick(node, i) {
                if (this.mobileX < 0) {
                    this.mobileX = 0;
                    this.special(this.$refs.skid[this.saveIdx], {
                        transform: `translate(0, 0)`
                    });
                    this.isAndroid &&
                    this.special(this.$refs.skid[i], {
                        transform: `translate(0, 0)`
                    });
                } else {
                    //this.$emit("onNodeClick", node, i);
                    var p={}
                    p.PurchaseID=node.PurchaseID
                    p.DepartmentID =node.DepartmentID
                    p.Department =node.Department
                    p.Memo=node.Memo
                    p.Supplier=node.Supplier
                    p.SupplierID=node.SupplierID
                    p.Name=node.Name
                    p.EmployeeID=node.EmployeeID
                    p.Date=node.Date
                    p.direction =this.direction
                    //    p.LastNeedRAmount=node.LastNeedRAmount
                    p.AuditFlag =node.AuditFlag
                    p.PaymentTypeID =node.PaymentTypeID
                    p.PaymentType =node.PaymentType
                    p.PaymentAmount =node.PaymentAmount
                    this.push('root:PurchaseAdd.js',p)

                }
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
            }
        }
    }
</script>

<style scoped>
    .container {
        /* background-color: #dddddd; */
        border-top-width: 1px;
        border-top-color: #dddddd;
        flex:1;
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

    /* .box-center {
      width: 735px;
      line-height: 90px;
      background-color: #FFFFFF;
      margin-left: 15px !important;
      margin-left: 15px;
    }
    .box-center-last {
      width: 750px;
      margin-left: 0 !important;
      padding-left: 15px !important;
      margin-left: 0;
      padding-left: 15px;
    } */

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
    .search{
        flex-direction: row;
        width: 750px;
        margin-top: 5px;
        justify-content: center;
        align-self: center;
    }
    .footer{
        position: absolute;
        left: 0;
        right: 0;
        bottom: 0;
        width: 750px;
        flex-direction: row;
        height: 80px;
        background-color: #1EA5FC;
    }
    .input_bg{ /*position: absolute; background-color: #0085ee; top: 60px;bottom: 60px; 170*/
        top:50;
        width:170px;height:125px;
    }
</style>
