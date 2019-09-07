<template>
    <div class="wrapper" >
        <head  @backClick="back" title="查询条件" :rightText="rightText" @rightClick="rightClick"></head>
        <div style="flex-direction: row;height: 100px; border-bottom-width: 1px;border-color: #eeeeee; justify-content: flex-start;align-items: center">
            <div style="height: 100px; width:200px; justify-content: center;align-items: center">
                <text style="font-size: 40px;">开始日期: </text>
            </div>
            <div style="height:100px;width: 550px;justify-content: center;align-items: center; " @click="choose(1)">
                <text style="font-size: 40px;">{{DateStr1}}</text>
            </div>
        </div>
        <div style="flex-direction: row;height: 100px; border-bottom-width: 1px;border-color: #eeeeee; justify-content: flex-start;align-items: center">
            <div style="height: 100px; width:200px; justify-content: center;align-items: center">
                <text style="font-size: 40px;">结束日期:</text> </div>
            <div style="height:100px;width: 550px;justify-content: center;align-items: center; " @click="choose(2)">
                <text style="font-size: 40px;">{{DateStr2}}</text>
            </div>
        </div>


        <wxccell title="客户"
                 v-if="tag==28 || tag==30 || tag==97 || tag==32 || tag==34"
                 :has-arrow="true"
                 @wxcCellClicked="wxcCellClicked(1)"
                 :has-margin="true">
            <text style="font-size: 35px">{{Customer.Customer}}</text>
        </wxccell>

        <wxccell title="厂商"
                 v-if="tag==22 || tag==20 || tag==95 || tag==24"
                 :has-arrow="true"
                 @wxcCellClicked="wxcCellClicked(2)"
                 :has-margin="true">
            <text style="font-size: 35px"> {{Supplier.Supplier}} </text>
        </wxccell>
        <!-- 付 ，收款单查询条件-->
        <wxccell title="结算方式"
                 v-if="tag==26 || tag==34"
                 :has-arrow="true"
                 @wxcCellClicked="wxcCellClicked(3)"
                 :has-margin="true">
            <text style="font-size: 35px"> {{PaymentType.PaymentType}}</text>
        </wxccell>

        <wxccell :title="deptlabel"
                 v-if="tag !=0"
                 :has-arrow="true"
                 @wxcCellClicked="wxcCellClicked(4)"
                 :has-margin="true">
            <text style="font-size: 35px">{{Department.Department}}</text>
        </wxccell>


        <wxccell title="类型"
                 v-if="tag !=0"
                 :has-arrow="true"
                 @wxcCellClicked="selectType(1)"
                 :has-margin="true">
            <text style="font-size: 35px"> {{Type}}</text>
        </wxccell>

        <wxccell title="经手人"
                 v-if="tag !=0"
                 :has-arrow="true"
                 @wxcCellClicked="wxcCellClicked(5)"
                 :has-margin="true">
            <text style="font-size: 35px">{{Employee.Name}}</text>
        </wxccell>

        <wxccell title="是否审核"
                 v-if="tag !=0"
                 :has-arrow="true"
                 @wxcCellClicked="selectType(2)"
                 :has-margin="true">
            <text style="font-size: 35px">{{AuditType}}</text>
        </wxccell>



        <div style="margin-top: 20px;justify-content: center;align-items: center">
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
                  :show="auditshow"
                  @wxcMaskSetHidden="wxcMaskSetHidden">

            <wxc-radio :list="list" @wxcRadioListChecked="wxcRadioListChecked"></wxc-radio>
        </wxc-mask>

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
    import module1 from './jstools/mytool'
    var date=module1.formatDate(new Date(),'yyyy-MM-dd')
    export default {
        components:{WxcButton,WxcRadio,WxcMask,wxccell}
        ,data(){
            return{
                rightText:'\ue667',
                DateStr1:date,
                DateStr2:date,
                deptlabel:'部门名称',
                tag:0, //单据标识
                hasAnimation:false,
                auditshow:false,//显示弹窗
                typeshow:false,
                Department:{DepartmentID:'',Department:''},
                Customer:{CustomerID:'',Customer:''},
                Supplier:{SupplierID:'',Supplier:''},
                PaymentType:{PaymentTypeID:'',PaymentType:''},
                Employee:{EmployeeID:'',Name:''},
                AuditType:'全部',
                AuditFlag:'',
                Type:'',
                list: [
                    { title: '全部', value: 0,checked: true },
                    { title: '审核', value: 1  },
                    { title: '未审核', value: 2 },

                ],
                checkedInfo: { title: '全部', value: 0 }
            }


        },methods:{//查询条件
            onLoad(p){
                if(p==null || p==undefined || JSON.stringify(p)=='{}'){
                    return
                }
                this.tag =p.hasOwnProperty('tag')?p.tag:0
                //this.log('Depatment:'+this.Department.Department)
                if(this.tag ==28 || this.tag==20){
                    this.deptlabel='订货部门'
                }else if(this.tag ==30){
                    this.deptlabel='发货部门'
                }else if(this.tag ==97 || this.tag==22){
                    this.deptlabel='收货部门'
                }else if(this.tag ==32 || this.tag==24 || this.tag==45){
                    this.deptlabel='仓库名称'
                }else if(this.tag==34 || this.tag==26){
                    this.deptlabel='部门名称'
                }else if(this.tag==95){
                    this.deptlabel='退货部门'
                }

            },
            choose(id){

                let dateTimePicker=weex.requireModule('timepicker')
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

            },wxcRadioListChecked (e) {
                this.log('e:'+JSON.stringify(e))
                this.checkedInfo = e;
                if(this.typeshow){
                    this.Type=e.title
                }else if(this.auditshow){
                    this.AuditType=e.title
                    this.AuditFlag =e.value
                }
                this.auditshow=false
                this.typeshow =false

            } ,wxcMaskSetHidden(e){
                this.auditshow=false
                this.typeshow =false
            }
            ,wxcButtonClicked(e){
                var p={}
                p.BeginDate =this.DateStr1
                p.EndDate =this.DateStr2
                if(this.tag==28 || this.tag==30 || this.tag==97 || this.tag==32 || this.tag==34){
                    p.CustomerID =this.Customer.CustomerID
                    p.Customer =this.Customer.Customer
                    //  p.PaymentTypeID=this.PaymentType.PaymentTypeID
                    //    p.PaymentType=this.PaymentType.PaymentType
                    p.AuditType =this.AuditType
                    p.Type =this.Type
                    p.DepartmentID =this.Department.DepartmentID
                    p.Department =this.Department.Department
                    p.EmployeeID =this.Employee.EmployeeID
                    p.Name =this.Employee.Name
                }else if(this.tag==22 || this.tag==20 || this.tag==95 || this.tag==24){
                    p.SupplierID =this.Supplier.SupplierID
                    p.Supplier =this.Supplier.Supplier
                    p.AuditType =this.AuditType
                    p.Type =this.Type
                    p.DepartmentID =this.Department.DepartmentID
                    p.Department =this.Department.Department
                    p.EmployeeID =this.Employee.EmployeeID
                    p.Name =this.Employee.Name
                }else if(this.tag==26 || this.tag==34){
                    if(this.tag==26){//付款单
                        p.SupplierID =this.Supplier.SupplierID
                        p.Supplier =this.Supplier.Supplier
                        p.AuditType =this.AuditType
                        p.Type =this.Type
                        p.PaymentTypeID=this.PaymentType.PaymentTypeID
                        p.PaymentType=this.PaymentType.PaymentType
                        p.EmployeeID =this.Employee.EmployeeID
                        p.Name =this.Employee.Name
                    }else{ //收款
                        p.CustomerID =this.Customer.CustomerID
                        p.Customer =this.Customer.Customer
                        p.AuditType =this.AuditType
                        p.Type =this.Type
                        p.PaymentTypeID=this.PaymentType.PaymentTypeID
                        p.PaymentType=this.PaymentType.PaymentType
                        p.EmployeeID =this.Employee.EmployeeID
                        p.Name =this.Employee.Name
                    }
                }
                nav.backFull(p,true)
            },rightClick(e){
                this.log('右点击')
                this.wxcButtonClicked(e)
            }
            ,selectType(e){
                // var pop=weex.requireModule("centerpop")
                //pop.show('root:Typeradio.js',{width:500,height:700},{},true);
                /* for(var i=0 ;i< this.list.length ;i++){
                     var map=this.list[i]
                     if(this.Type == map.value){
                         map.checked=true
                     }
                 } */
                if(e==1) {
                    this.typeshow = true
                    this.list=[
                        { title: '全部', value: '',checked: true },
                        { title: '批发', value: 0},
                        { title: '订货', value: 1  },
                        { title: '配货', value: 2 },
                        { title: '补货', value: 2 }

                    ]

                }else if(e==2){
                    this.auditshow =true

                    this.list=[
                        { title: '全部', value: 0,checked: true },
                        { title: '审核', value: 1  },
                        { title: '未审核', value: 2 }

                    ]

                }
            },wxcCellClicked(id){
                var p={}
                var that=this

                if(id==1){//客户
                    p.send='getCustomer'
                }else if(id==2){//厂商
                    p.send ='getGoodsSupplier'
                }else if(id==3){ //getPaymentType 结算方式
                    p.send ='getPaymentType'
                }else if(id==4){//部门
                    p.send ='getWarehouse'
                }else if(id==5){//经手人
                    p.send ='getEmployee'
                }
                p.mult=false
                p.Type=''
                p.customerid=''

                nav.pushFull({url: 'root:base.js',param:p,animate:true},(e)=>{
                    if (e !== undefined) {
                        if (e == null || JSON.stringify(e) == '{}') {//无结果返回，指的是点左上角返回菜单的返回
                            return
                        }
                        //   this.BeginDate =e.BeginDate
                        //  this.EndDate=e.EndDate
                        if(id==1){
                            that.Customer.CustomerID =e.id
                            that.Customer.Customer =e.Name
                        }else if(id==2){
                            that.Supplier.SupplierID =e.id
                            that.Supplier.Supplier =e.Name
                        }else if(id==3){
                            that.PaymentType.PaymentTypeID=e.id
                            that.PaymentType.PaymentType =e.Name
                        }else if(id==4){
                            that.Department.DepartmentID=e.id
                            that.Department.Department =e.Name
                        }else if(id==5){
                            that.Employee.EmployeeID =e.id
                            that.Employee.Name =e.Name
                        }
                    }

                })
            }

        }
    }
</script>

<style scoped>
    .wrapper{
        position: absolute;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
    }
    .sales{
        height: 500px;
    }
</style>
