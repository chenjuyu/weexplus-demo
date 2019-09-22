<template>
    <div class="wrapper" >
        <head  @backClick="back" :title="title" :rightText="rightText" @rightClick="rightClick"></head>
        <list style="flex: 1">
        <cell>
        <wxccell title="单号"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 :has-arrow="false"
                 :has-margin="true">
            <text style="font-size: 35px">{{No}}</text>
        </wxccell>
        <wxccell title="应收余额"
                 :titlestyle="{'font-size':'35px'}"
                 v-if="tag==34"
                 :cellStyle="{height:'80px'}"
                 :has-arrow="false"
                 :has-margin="true">
            <text style="font-size: 35px">{{LastNeedRAmount}}</text>
        </wxccell>
        <wxccell title="应付余额"
                 v-if="tag==26"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 :has-arrow="false"
                 :has-margin="true">
            <text style="font-size: 35px">{{LastMustPayAmount}}</text>
        </wxccell>


        <!--  收付款单放在一起了 34 为收 ，26 为付-->
        <wxccell title="客户名称"
                 v-if="tag==34"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 :has-arrow="true"
                 @wxcCellClicked="wxcCellClicked(1)"
                 :has-margin="true">
            <text style="font-size: 35px">{{Customer.Customer}}</text>
        </wxccell>
        <wxccell title="厂商"
                 :cellStyle="{height:'80px'}"
                 :titlestyle="{'font-size':'35px'}"
                 v-if="tag==26"
                 :has-arrow="true"
                 @wxcCellClicked="wxcCellClicked(2)"
                 :has-margin="true">
            <text style="font-size: 35px"> {{Supplier.Supplier}} </text>
        </wxccell>

        <wxccell title="收款金额"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 v-if="tag==34"
                 :has-arrow="false"
                 :has-margin="true">
            <input type="number" class="input" return-key-type="search" v-model="ReceivalAmount"  placeholder=""/>
        </wxccell>

        <wxccell title="付款金额"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 v-if="tag==26"
                 :has-arrow="false"
                 :has-margin="true">
            <input type="number" class="input" return-key-type="search" v-model="PaymentAmount"  placeholder=""/>
        </wxccell>

        <wxccell title="类别"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 v-if="tag ==34"
                 :has-arrow="true"
                 @wxcCellClicked="selectType(1)"
                 :has-margin="true">
            <text style="font-size: 35px"> {{Type}}</text>
        </wxccell>

        <wxccell title="日期"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 :has-arrow="false"
                 @wxcCellClicked="choosedate(1)"
                 :has-margin="true">
            <text style="font-size: 35px"> {{DateStr1}}</text>
        </wxccell>

        <wxccell title="生效日期"
                 v-if="tag==34"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 :has-arrow="false"
                 @wxcCellClicked="choosedate(2)"
                 :has-margin="true">
            <text style="font-size: 35px"> {{DateStr2}}</text>
        </wxccell>

        <wxccell title="结算方式"
                 :has-arrow="true"
                 :titlestyle="{'font-size':'35px'}"
                 :cellStyle="{height:'80px'}"
                 @wxcCellClicked="wxcCellClicked(3)"
                 :has-margin="true">
            <text style="font-size: 35px"> {{PaymentType.PaymentType}}</text>
        </wxccell>

        <wxccell title="经手人"
                 :cellStyle="{height:'80px'}"
                 :titlestyle="{'font-size':'35px'}"
                 :has-arrow="true"
                 @wxcCellClicked="wxcCellClicked(4)"
                 :has-margin="true">
            <text style="font-size: 35px">{{Employee.Name}}</text>
        </wxccell>

        <wxccell title="备注"
                 :cellStyle="{height:'80px'}"
                 :titlestyle="{'font-size':'35px'}"
                 :has-arrow="false"
                 :has-margin="true">
            <input type="text" class="input" return-key-type="default" v-model="Memo"  placeholder=""/>
        </wxccell>

        </cell>

        </list>

        <div v-if="!AuditFlag" style="margin-top: 20px;justify-content: center;align-items: center;bottom: 0;left: 0">
            <wxc-button text="确定"
                        @wxcButtonClicked="wxcButtonClicked"></wxc-button>
        </div>

        <wxc-mask height="500"
                  width="500"
                  border-radius="0"
                  duration="200"
                  mask-bg-color="#FFFFFF"
                  :has-animation="hasAnimation"
                  :has-overlay="true"
                  :show-close="true"
                  :show="typeshow"
                  @wxcMaskSetHidden="wxcMaskSetHidden">

            <wxc-radio :list="list" @wxcRadioListChecked="wxcRadioListChecked"></wxc-radio>
        </wxc-mask>

    </div>
</template>

<script>
    import wxccell from './component/wxc-cell.vue'
    import { WxcButton,WxcRadio,WxcMask } from 'weex-ui'
    var nav = weex.requireModule('navigator') ;
    let dateTimePicker=weex.requireModule('timepicker')
    const  pref=weex.requireModule('pref')
    const  pstatic=weex.requireModule("static")
    const progress = weex.requireModule('progress');
    const net = weex.requireModule('net');
    import module1 from './jstools/mytool'
    var date=module1.formatDate(new Date(),'yyyy-MM-dd')
    export default {
        components:{WxcButton,WxcRadio,WxcMask,wxccell},
        data(){
            return{
                DateStr1:date,
                DateStr2:date,
                title:'收款单单据',
                tag:34,
                No:'',
                Memo:'',
                Type:'',
                ReceivalID:'',//收款单主键
                PaymentID:'',
                hasAnimation:false,
                LastMustPayAmount:'',//应付余额
                LastNeedRAmount:'',//应收余额
                auditshow:false,//显示弹窗
                AuditFlag:false,
                ReceivalAmount:'',//收款金额
                PaymentAmount:'',
                typeshow:false,
                Department:{DepartmentID:'',Department:''},
                Customer:{CustomerID:'',Customer:''},
                Supplier:{SupplierID:'',Supplier:''},
                PaymentType:{PaymentTypeID:'',PaymentType:''},
                Employee:{EmployeeID:'',Name:''},
                list:[
                    { title: '货款', value: 0,checked: true },
                    { title: '订金', value: 1  },
                    { title: '保证金', value: 2 },
                    { title: '货到付款', value: 2 },
                    { title: '返利', value: 2 },
                    { title: '代收', value: 2 },
                    { title: '优惠', value: 2 }
                ]
            }

        },methods:{
            onLoad(p){
                if(p==null || p==undefined || JSON.stringify(p)=='{}'){
                    return
                }

                this.tag=p.hasOwnProperty('tag')?p.tag:34
                this.No=p.hasOwnProperty('No')?p.No:''
                this.LastNeedRAmount=p.hasOwnProperty('LastNeedRAmount')?p.LastNeedRAmount:''
                this.LastMustPayAmount=p.hasOwnProperty('LastMustPayAmount')?p.LastMustPayAmount:''
                this.Customer.CustomerID =p.hasOwnProperty('CustomerID')?p.CustomerID:''
                this.Customer.Customer =p.hasOwnProperty('Customer')?p.Customer:''
                this.ReceivalAmount =p.hasOwnProperty('ReceivalAmount')?p.ReceivalAmount:''
                this.PaymentAmount =p.hasOwnProperty('PaymentAmount')?p.PaymentAmount:''
                this.Type =p.hasOwnProperty('Type')?p.Type:''
                this.DateStr1 =p.hasOwnProperty('Date')?p.Date:date
                this.DateStr2 =p.hasOwnProperty('ValidBeginDate')?p.ValidBeginDate:date
                this.PaymentType.PaymentTypeID =p.hasOwnProperty('PaymentTypeID')?p.PaymentTypeID:''
                this.PaymentType.PaymentType =p.hasOwnProperty('PaymentType')?p.PaymentType:''
                this.Employee.EmployeeID =p.hasOwnProperty('EmployeeID')?p.EmployeeID:''
                this.Employee.Name =p.hasOwnProperty('Name')?p.Name:''
                this.ReceivalID=p.hasOwnProperty('ReceivalID')?p.ReceivalID:''
                this.PaymentID =p.hasOwnProperty('PaymentID')?p.PaymentID:''
                this.Supplier.SupplierID =p.hasOwnProperty('SupplierID')?p.SupplierID:''
                this.Supplier.Supplier =p.hasOwnProperty('Supplier')?p.Supplier:''
                this.Memo =p.hasOwnProperty('Memo')?p.Memo:''
                this.AuditFlag =p.hasOwnProperty('AuditFlag')?p.AuditFlag:false



                if(this.Type ==''){
                    this.Type='货款'
                }

                if(this.tag ==26){
                    this.title='付款单单据'
                }

            },
            choosedate(id){
                dateTimePicker.open({
                    value: '',//必选,选中的值，格式为yyyy-MM-dd HH:mm;当value为空,默认选中当前时间;当value不为空时为返回值
                    max: '',//可选，日期最大值,默认2099-12-31 23:59
                    min: '',//可选，日期最小值,默认1900-12-31 00:00
                    title: '',//可选，标题的文案，默认为空
                    titleColor: '',//可选，默认为空,title不为空时有效，颜色值（#313131）
                    confirmTitle: '', //确认按钮的文案,默认值（完成）
                    confirmTitleColor: '', //确认按钮的文字颜色，默认值(#00b4ff)
                    cancelTitle: '', //取消按钮的文案,默认值（取消）
                    cancelTitleColor: '', //取消按钮的文字颜色,默认值(#313131)
                },(res) =>{//回调
                    //返回字段
                    //result{string}：success,cancel
                    //data {string}：格式为yyyy-MM-dd HH:mm
                    if(res.result === "success"){
                        //业务逻辑
                        if(id==1) {
                            this.DateStr1 = res.data.substring(0, 10)
                        }else if(id==2){
                            this.DateStr2 = res.data.substring(0, 10)
                        }
                    }else{
                        //业务逻辑
                    }
                });
            },selectType(id){
               this.typeshow =true

            },wxcRadioListChecked (e) {
                this.log('e:'+JSON.stringify(e))
                if(this.typeshow){
                    this.Type=e.title
                }

                this.typeshow =false

            } ,wxcMaskSetHidden(e){
                this.typeshow =false
            },wxcCellClicked(id){ //跳转请求
                var p={}
                var that=this
                if(id==1){ //客户
                p.send='getCustomer'
                }else if(id==2){
                p.send='getGoodsSupplier'
                }else if(id==3){ //结算方式
                 if(this.tag==34) { // 查询 收款类别
                     p.send = 'getPaymentType'
                     p.Type ='收款'
                 }else if(this.tag==26){
                     p.send = 'getPaymentType'
                     p.Type ='付款'
                 }
                }else if(id==4){ //员工
                    p.send ='getEmployee'
                }
                p.Type =p.Type?p.Type:''
                p.mult =false
                p.customerid=''

                nav.pushFull({url: 'root:base.js',param:p,animate:true},(e)=> {
                    if (e !== undefined) {
                        if (e == null || JSON.stringify(e) == '{}') {//无结果返回，指的是点左上角返回菜单的返回
                            return
                        }
                        if(id==1){
                          that.Customer.CustomerID=e.id
                          that.Customer.Customer =e.Name
                        }else if(id==2){
                            that.Supplier.SupplierID=e.id
                            that.Supplier.Supplier =e.Name
                        }else if(id==3){
                            that.PaymentType.PaymentTypeID=e.id
                            that.PaymentType.PaymentType =e.Name
                        }else if(id==4){
                            that.Employee.EmployeeID=e.id
                            that.Employee.Name=e.Name
                        }

                    }
                })



            },wxcButtonClicked(e){ //保存单据 saverec
                var p={}
                var url=''
                var that=this
                if(this.tag==34){ //收款单
                 p.ReceivalID=that.ReceivalID
                 p.CustomerID =that.Customer.CustomerID
                 p.Customer =that.Customer.Customer
                 p.PaymentTypeID =that.PaymentType.PaymentTypeID
                 p.PaymentType =that.PaymentType.PaymentType
                 p.Type =that.Type
                 p.Date =that.DateStr1
                 p.ValidBeginDate =that.DateStr2
                 p.EmployeeID =that.Employee.EmployeeID
                 p.Name =that.Employee.Name
                 p.ReceivalAmount =that.ReceivalAmount
                 p.LastNeedRAmount =that.LastNeedRAmount
                 p.Memo =that.Memo
                 p.No=that.No

                 url='/receival.do?saverec'

                    if(that.Customer.CustomerID=='' || that.Customer.CustomerID==null || that.Customer.CustomerID ==undefined){
                        that.toast('客户不能为空')
                        return
                    }
                    if(that.Type=='' || that.Type==null || that.Type ==undefined){
                        that.toast('类别不能为空')
                        return
                       }
                    if(that.ReceivalAmount=='' || that.ReceivalAmount==null || that.ReceivalAmount ==undefined){
                     that.toast('收款金额不能为空')
                     return
                 }


                }if(this.tag==26){
                    p.No=that.No
                    p.PaymentID=that.PaymentID
                    p.SupplierID =that.Supplier.SupplierID
                    p.Supplier =that.Supplier.Supplier
                    p.Date=that.DateStr1
                    p.EmployeeID =that.Employee.EmployeeID
                    p.Name =that.Employee.Name
                    p.PaymentTypeID =that.PaymentType.PaymentTypeID
                    p.PaymentAmount =that.PaymentAmount
                    p.LastMustPayAmount=that.LastMustPayAmount
                    p.Memo =that.Memo

                    url='/payment.do?savepay' //待定

                    if(that.Supplier.SupplierID=='' || that.Supplier.SupplierID==null || that.Supplier.SupplierID ==undefined){
                        that.toast('厂商不能为空')
                        return
                    }

                    if(that.PaymentAmount=='' || that.PaymentAmount==null || that.PaymentAmount ==undefined){
                        that.toast('付款金额不能为空')
                        return
                    }


                }

                net.post(pref.getString('ip') + url, p, {}, function () {
                    //start
                    progress.showFull('正在保存', false)
                }, function (e) {
                    //success

                    progress.dismiss()
                    if(e.res.success){
                        that.toast('保存成功')
                    if(that.tag==34){
                        that.ReceivalID =e.res.obj
                        if(p.ReceivalID=='' || p.ReceivalID==null || p.ReceivalID==undefined){
                            p.ReceivalID = e.res.obj
                            p.AuditFlag=false
                            p.DepartmentID =''
                            p.Department =''
                            p.right=[{text:'审核',style:{backgroundColor:'mediumspringgreen',color:'white'}},
                                {text:'反审',style:{backgroundColor:'#F4333C',color:'white'}}] //右键菜单
                            nav.pushParam('root:reclist.js',{title:that.title}) //新增要重新请求
                           // nav.backFull(p,true)
                        }else{
                            p.right=[{text:'审核',style:{backgroundColor:'mediumspringgreen',color:'white'}},
                                {text:'反审',style:{backgroundColor:'#F4333C',color:'white'}}] //右键菜单
                            nav.backFull(p,true) //返回参数，不重新网络请求
                        }

                    }else if(that.tag==26) {

                        that.toast('保存成功')
                        that.PaymentID = e.res.obj
                        if (p.PaymentID == '' || p.PaymentID == null || p.PaymentID == undefined) {
                            p.PaymentID = e.res.obj
                            p.AuditFlag = false
                            p.DepartmentID = ''
                            p.Department = ''
                            p.right = [{text: '审核', style: {backgroundColor: 'mediumspringgreen', color: 'white'}},
                                {text: '反审', style: {backgroundColor: '#F4333C', color: 'white'}}] //右键菜单
                            nav.pushParam('root:paylist.js',{title:that.title}) //新增要重新请求
                            // nav.backFull(p,true) //返回参数，不重新网络请求
                        }else{
                            p.right=[{text:'审核',style:{backgroundColor:'mediumspringgreen',color:'white'}},
                                {text:'反审',style:{backgroundColor:'#F4333C',color:'white'}}] //右键菜单
                            nav.backFull(p,true) //返回参数，不重新网络请求

                        }

                    }

                    }else{
                      that.toast(e.res.msg)
                    }

                },function (e) {
                    //compelete

                }, function () {
                    //exception
                    progress.dismiss()
                    that.alert('异常')
                });


            }

        }
    }
</script>

<style scoped>
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