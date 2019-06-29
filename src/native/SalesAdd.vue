<template>
    <div class="wrapper">
        <head :rightText="rightText"  @rightClick="rightClick"></head>
    <div class="search">
    <input type="text" style="width: 500px;height: 60px;border-width: 5px;border-color: #00B4FF;margin-left: 10px"/>
        <text style="font-size: 35px;border-width: 5px;border-color: #00B4FF;height: 60px;width: 220px;margin-right: 5px;text-align: center">查询</text>
    </div>
    <div class="master">
        <wxc-cell label="备注:"
                  :has-arrow="false"
                  :cell-style="{'height':'70px'}"
                  @wxcCellClicked="wxcCellClicked"
                   :has-bottom-border="false"	>
            <input type="text" style="width: 500px;height: 70px"/>
        </wxc-cell>
        <wxc-cell label="日期:"
                  :has-arrow="true"
                  :cell-style="{'height':'70px'}"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{DateStr}}</text>
        </wxc-cell>

        <wxc-cell label="类别:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  @wxcCellClicked="selectType"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{Type}}</text>
        </wxc-cell>
        <wxc-cell label="发货部门:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  @wxcCellClicked="wxcCellClicked"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{Department.Department}}</text>
        </wxc-cell>

        <wxc-cell label="客户名称:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  @wxcCellClicked="wxcCellClicked"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{Customer.Customer}}</text>
        </wxc-cell>
        <wxc-cell label="应收余额:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  @wxcCellClicked="wxcCellClicked"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{Customer.lastAmt}}</text>
        </wxc-cell>

        <wxc-cell label="经手人:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  @wxcCellClicked="wxcCellClicked"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{Emp.Name}}</text>
        </wxc-cell>
     <div class="listbutton">
         <text style="font-size: 35px;border-width: 5px;border-color: #00B4FF;height: 60px;width: 220px;margin-left: 5px;text-align: center">蓝牙增加</text>
         <text style="font-size: 35px;border-width: 5px;border-color: #00B4FF;height: 60px;width: 220px;text-align: center">快速增加</text>
         <text style="font-size: 35px;border-width: 5px;border-color: #00B4FF;height: 60px;width: 220px;margin-right: 5px;text-align: center">扫码增加</text>
     </div>
    </div> <!--master 结束 border-radius:25px   v-for="num in lists"-->

        <list class="detail">
            <cell style="height: 80px;border-bottom-width: 1px;justify-content: center;border-color: #dddddd">
                <text style="font-size: 30px;color: #666666;">发货数量合计:{{Department.Department}}</text>
            </cell>
            <cell style="height: 80px;border-bottom-width: 1px;justify-content: center;border-color: #dddddd">
                <text style="font-size: 30px;color: #666666;">折扣合计:{{Department.Department}}</text>
            </cell>
            <cell style="height: 80px;border-bottom-width: 1px;justify-content: center;border-color: #dddddd">
                <text style="font-size: 30px;color: #666666;">发货金额合计:{{Department.Department}}</text>
            </cell>
        </list>


       <div class="footer">
           <div style="background-color: orange;justify-content:center;align-items:center;width: 200px; ">  <text style="font-size: 40px;color: #FFFFFF;">保存</text></div>
        <div style="background-color: orange;justify-content:center;align-items:center;width: 200px; margin-right: 20px"><text style="font-size: 40px;color: #FFFFFF;">收款</text></div>

      </div>

        <wxc-mask height="500"
                  width="500"
                  border-radius="0"
                  duration="200"
                  mask-bg-color="#FFFFFF"
                  :has-animation="hasAnimation"
                  :has-overlay="true"
                  :show-close="true"
                  :show="show"
                  @wxcMaskSetHidden="wxcMaskSetHidden">
            <wxc-radio :list="list" @wxcRadioListChecked="wxcRadioListChecked"></wxc-radio>


        </wxc-mask>



         </div>
       </template>
       <style scoped>
           .search{
               flex-direction: row;
               width: 750px;
               margin-top: 5px;
           }
           .master {
               width: 750px;
               background-color: #f2f3f4;
           }
           .listbutton{
               justify-content: center;
               flex-direction: row;
               width: 750px;
               margin-top: 5px;
           }
           .detail{
               height: 380px;

           }
           .footer{
               position: absolute;
               left: 0;
               right: 0;
               bottom: 0;
              flex-direction: row;
               height: 80px;
               direction: rtl;
               background-color: #00B4FF;

           }

       </style>
       <script>
           import { WxcCell ,WxcMask,WxcRadio  ,Utils } from 'weex-ui';
           import module1 from './jstools/mytool'//引用方式
           let timestr=module1.formatDate((new Date()),"yyyy-MM-dd")
           export default {
               components: { WxcCell,WxcMask,WxcRadio   }
               ,data() {
                   return {
                     DateStr:timestr
                     ,show:false
                     ,hasAnimation:false
                     ,Type:'批发'
                     ,Department:{Department:'',DepartmentID:''}
                     ,Customer:{Customer:'',CustomerID:'',lastAmt:''}
                     ,Emp:{Name:'',EmpID:''}
                     ,list: [
                           { title: '批发', value: '批发' },
                           { title: '订货', value: '订货' },//checked: true
                           { title: '选项3', value: 3 },
                           { title: '选项4', value: 4 },
                       ]
                       ,checkedInfo: { title: '选项2', value: 2 } //已选择
                   }
               }
               ,methods:{
                   wxcCellClicked(e){
                       this.alert('点击了')
                   }
                  ,selectType(e){
                      // var pop=weex.requireModule("centerpop")
                       //pop.show('root:Typeradio.js',{width:500,height:700},{},true);
                      for(var i=0 ;i< this.list.length ;i++){
                          var map=this.list[i]
                           if(this.Type == map.value){
                               map.checked=true
                           }
                       }
                       this.show=true
                   }
                  ,wxcMaskSetHidden(e){
                       this.show=false
                   }
                  , wxcRadioListChecked (e) {
                       this.alert("e的值："+JSON.stringify(e))

                       this.checkedInfo = e;
                      // var pop=weex.requireModule("centerpop")
                      // pop.dismiss();

                       this.Type =e.value
                       this.show=false
                   }
               }
           }
       </script>


