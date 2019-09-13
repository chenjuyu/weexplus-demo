<template>
    <div class="container">
        <head :rightText="rightText" :title="title"  @rightClick="rightClick"></head>
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
                        <text style="font-size: 35px;height: 60px">{{item.Department}}</text>
                        <text style="font-size: 35px;height: 60px">经手人:{{item.Employee}}</text>
                        <text style="font-size: 35px;height: 60px">数量:{{item.QuantitySum}}</text>
                        <text style="font-size: 35px;height: 60px">备注:{{item.Memo}}</text>
                    </div>
                    <!--
                                        <image src="root:img/Audit.png" class="input_bg" v-if="item.AuditFlag"></image>
                    -->
                                        <div class="right" style="position: absolute;right: 0">
                                            <text style="font-size: 35px;height: 60px;background-color: orange;color: #FFFFFF;padding-top: 10px;" v-if="item.DaySumFlag">已日结</text>
                                            <text style="font-size: 35px;height: 60px;background-color: orange;color: #FFFFFF;padding-top: 10px;" v-else>未日结</text>

                                            <text style="font-size: 35px;height: 60px">{{item.Date}}</text>
                                            <text style="font-size: 35px;height: 60px">实收现金:{{item.RealCash}}</text>
                                            <text style="font-size: 35px;height: 60px">实收金额:{{item.FactAmountSum}}</text>
                                            <!--    <text style="font-size: 35px;height: 60px">应收余额:{{item.LastNeedRAmount}}</text> -->
                        <text style="font-size: 35px;height: 60px">金额:{{item.AmountSum}}</text>
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
    const  pstatic=weex.requireModule("static")
    const progress = weex.requireModule('progress');
    var date = new Date();//获取当前时间
    date.setDate(date.getDate()-8);//设置天数 -1 天
    let beginTime=module1.formatDate((date),"yyyy-MM-dd")
    let endTime=module1.formatDate((new Date()),"yyyy-MM-dd")
    var url='/salesTicket.do?getPossales'
    var auditurl ='/salesTicket.do?copy'
    export default {
        components: {
        },
        props: {
            data: {
                type: Array,
                default: [
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
                rightText:'\ue621',
                title:'销售小票',
                No:'',
                totalQty:'',
                totalAmt:'',
                currPage:1,
                mobileX: 0,
                webStarX: 0,
                saveIdx: null,
                isAndroid: Utils.env.isAndroid(),
                loadinging:false
            };
        },
        methods: {
            onLoad(p){
                //   this.alert(this.data)
                var page=weex.requireModule("page")
                page.enableBackKey(true);
                page.setBackKeyCallback(()=>{
                    nav.push('root:GridViewList.js')
                    return
                })
                if(p ==null || p==undefined || JSON.stringify(p)=='{}')
                {
                    return
                }
                var that=this
                var param={}
                param.currPage=this.currPage
                param.audit=p.hasOwnProperty('audit')?p.audit:''

                param.no=''
                param.beginDate=p.hasOwnProperty('beginDate')?p.beginDate:beginTime
                param.endDate=p.hasOwnProperty('endDate')?p.endDate:endTime
                param.departmentId=p.hasOwnProperty('departmentId')?p.departmentId:''
                param.employeeId=p.hasOwnProperty('employeeId')?p.employeeId:''

                //that.title=p.hasOwnProperty('title')?p.title:''
                this.log("执行到了，列表方法")
                pstatic.set('posmaster',param)//用于加载更多使用
                net.post(pref.getString('ip') + url,param,{},function(){
                    //start
                    progress.showFull('加载中',false)
                },function(e){
                    //success
                    that.log("net方法中了")
                    if(e !=null && e !=undefined ){
                        if(e.res.msg=='暂无数据'){
                            that.toast(e.res.msg)
                        }else {
                            that.data = e.res.obj
                            that.log('obj:'+e.res.obj)
                            that.total()
                        }
                    }
                    progress.dismiss()
                },function(e){
                    //compelete
                },function(){
                    progress.dismiss()
                    //exception
                });
            },total(){
                var q=Number(0)
                var a=Number(0)
                for(var i=0;i<this.data.length;i++){
                    q=q+Number(this.data[i].QuantitySum)
                    a=a+Number(this.data[i].FactAmountSum)
                }
                this.totalQty =q
                this.totalAmt =a
                if(this.totalAmt){ //这里表示 不等于0 与空
                    this.totalAmt =parseFloat(this.totalAmt).toFixed(2)
                }
            }
            ,add(e){
                nav.pushParam('root:PosAdd.js',{title:this.title})
            },search(){
                var that=this
                var param={}
                param.currPage=this.currPage
                param.audit=''
                param.no=that.No
                param.beginDate=''
                param.endDate=''
                param.departmentId=''
                param.employeeId=''

                setTimeout(() => {
                    net.post(pref.getString('ip') + url,param,{},function(){
                        //start
                    },function(e){
                        //success
                        //  self.back=e.res;
                        if(e !=null && e !=undefined ){
                            var arr =e.res.obj || []
                            if(arr.length >0) {
                                that.data = e.res.obj
                                that.total()
                            }else{
                                that.toast('暂无数据返回')
                            }
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
               if(node.text =='冲销') {
                   if (pNode.DelFlag) {
                       that.toast('单据已冲销')
                       return
                   } else {
                       p.POSSalesID = pNode.POSSalesID
                       p.deptNo = pNode.deptNo

                       this.log('POSSalesID:'+p.POSSalesID)
                       this.log('deptNo:'+p.deptNo)

                       net.post(pref.getString('ip') + '/salesTicket.do?POSSalesUnDo', p, {}, function () {
                           //start
                       }, function (e) {
                           //success
                           if (e != null && e != undefined) {
                               if (e.res.success) {
                                   if (!p.DelFlag) {
                                       that.data[i].DelFlag = true
                                   }
                               }
                               that.toast(e.res.msg)
                               // var page=weex.requireModule("page")
                               // page.reload();
                           }
                       }, function (e) {
                           //compelete
                       }, function () {
                           //exception
                           that.alert('异常')
                       })

                   }
               } //冲销判断


                if(pNode.TallyFlag){
                    that.toast('单据已记账，无法修改')
                    return
                }
                if(node.text =='审核'){
                    if(pNode.AuditFlag){
                        that.toast('单据已审核')
                        return
                    }
                    p.direction =that.direction
                    p.PurchaseID =pNode.PurchaseID
                    p.departmentid =pNode.DepartmentID
                    p.AuditFlag =1
                }else if(node.text =='反审'){
                    if(!pNode.AuditFlag){
                        that.toast('单据未审核不能反审')
                        return
                    }
                    p.AuditFlag =0
                    p.direction =that.direction
                    p.PurchaseID =pNode.PurchaseID
                    p.departmentid =''
                }
                if(node.text =='审核' || node.text =='反审') {
                    net.post(pref.getString('ip') + auditurl, p, {}, function () {
                        //start
                    }, function (e) {
                        //success
                        if(e !=null && e !=undefined){
                            if (p.AuditFlag == 0){
                                that.data[i].AuditFlag=false
                            }else if(p.AuditFlag ==1){
                                that.data[i].AuditFlag=true
                            }
                            that.toast(e.res.msg)
                            // var page=weex.requireModule("page")
                            // page.reload();
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
                    p.POSSalesID=node.POSSalesID
                    p.DepartmentID =node.DepartmentID
                    p.Department =node.Department
                    p.Memo=node.Memo
                    p.Vip=node.Vip
                    p.VipID=node.VipID
                    p.VipCode =node.VipCode
                    p.Name=node.Employee
                    p.No=node.No
                    p.EmployeeID=node.EmployeeID
                    p.Date=node.Date


                    //this.alert("direction:"+this.direction)
                    p.title =this.title
                    //    p.LastNeedRAmount=node.LastNeedRAmount
                    p.AuditFlag =node.AuditFlag
                    this.push('root:PosAdd.js',p)
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
            },rightClick(){
                this.log('右击')
                var that=this
                var  p={}
                p.tag =408
                nav.pushFull({url: 'root:selectdate.js',param:p,animate:true},(e)=> {
                    if (e !== undefined) {
                        if (e == null || JSON.stringify(e) == '{}') {//无结果返回，指的是点左上角返回菜单的返回
                            return
                        }
                        p.supplierId=e.SupplierID
                        p.supplier=e.Supplier
                        p.audit =''//  e.AuditType 固定查询 销售单
                        p.beginDate=e.BeginDate
                        p.endDate=e.EndDate
                        p.departmentId=e.DepartmentID
                        p.employeeId=e.EmployeeID
                        that.currPage =1
                        p.direction =that.direction
                        p.no=''
                        that.onLoad(p)
                    }
                })
            },onloading(event) { //上拉加载更多
                var that=this
                this.loadinging = true;
                modal.toast({
                    message: "loading",
                    duration: 1
                });
                setTimeout(()=>{
                    this.currPage=Number(this.currPage) +Number(1)
                    var p=pstatic.get('posmaster') ||{}
                    p.currPage=this.currPage
                    net.post(pref.getString('ip')+url,p,{},function(){
                        //start
                    },function(e){
                        //success
                        if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {
                            if(e.res.msg=='暂无数据'){
                                that.toast('数据已加载完')
                            }else {
                                var array = e.res.obj || []
                                for (var i = 0; i < array.length; i++) {
                                    that.data.push(array[i])
                                }
                                that.total()
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