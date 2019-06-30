<template>
    <div class="wrapper">
        <head :rightText="rightText"  @rightClick="rightClick"></head>
    <div class="search">
    <input type="text" style="width: 500px;height: 60px;border-width: 5px;border-color: #00B4FF;margin-left: 10px"/>
        <text style="font-size: 35px;border-width: 5px;border-color: #00B4FF;height: 60px;width: 220px;margin-right: 5px;text-align: center">查询</text>
    </div>
    <list style="height: 1000px;">
    <cell>
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
    </div> </cell><!--master 结束 border-radius:25px   v-for="num in lists" <list style="height: 200px"> </list>-->


             <cell v-for="(ls,index) in detaillist" style="height: 290px;border-bottom-width: 1px;justify-content: center;border-color: #dddddd">
               <div style="flex-direction: row"><text style="font-size: 30px;color: red;">{{ls.Code}}</text> <text style="font-size: 30px;color:#000000; margin-left: 20px">{{ls.Name}}</text></div>
                 <!-- 图片与显示-->
               <div style="flex-direction: row">
                   <text style="font-size: 30px;color:#000000;">{{Number(index)+1}}</text>
                   <image src="https://www.baidu.com/img/bd_logo1.png" style="width: 150px;height: 150px"></image>
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
                 <text style="font-size: 30px;color:red;margin-left: 10px;width: 60px;text-align: center" v-for="(lst,index1) in ls.sizetitle">{{lst.title}}</text>
                </div>
                 <div style="flex-direction: row">
                 <text style="font-size: 30px;color:#000000;margin-left: 10px;width: 60px;text-align: center" v-for="(lst2,index2) in ls.sizeData">{{lst2.Quantity }}</text>
                 </div>


             </cell>

            <!-- ===================================分隔线 -->
            <cell style="height: 80px;border-bottom-width: 1px;justify-content: center;border-color: #dddddd">
                <text style="font-size: 30px;color: #666666;">发货数量合计:{{totalQty}}</text>
            </cell>
            <cell style="height: 80px;border-bottom-width: 1px;justify-content: center;border-color: #dddddd">
                <text style="font-size: 30px;color: #666666;">折扣合计:{{totalDiscount}}</text>
            </cell>
            <cell style="height: 80px;border-bottom-width: 1px;justify-content: center;border-color: #dddddd">
                <text style="font-size: 30px;color: #666666;">发货金额合计:{{totalAmt}}</text>
            </cell>





    </list> <!-- 包整体-->
        <div class="footer">
            <div style="background-color: orange;justify-content:center;align-items:center;width: 200px; border-radius:20px ">  <text style="font-size: 40px;color: #FFFFFF;">保存</text></div>
            <div style="background-color: orange;justify-content:center;align-items:center;width: 200px; margin-right: 20px;border-radius:20px"><text style="font-size: 40px;color: #FFFFFF;">收款</text></div>
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
               display: block;
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
                     ,totalQty:0
                     ,totalAmt:0.0
                     ,totalDiscount:0.00
                     ,Type:'批发'
                     ,Department:{Department:'',DepartmentID:''}
                     ,Customer:{Customer:'',CustomerID:'',lastAmt:''}
                     ,Emp:{Name:'',EmpID:''}
                     ,detaillist:[
                         {GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BA',Color:'黑色',
                               Discount:0.0,DiscountRate:8.0,Quantity:2,Amount:34.5,
                               sizetitle:[
                                   {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                   ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                   {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                   ,{filed:'x_10',title:'5L'}
                               ],
                               sizeData:[{GoodsID:'00AG',ColorID:'0BA',x_1:1,Quantity:1,SizeID:'00A'},{GoodsID:'00AG',ColorID:'0BA',x_2:2,Quantity:1,SizeID:'00A'}]
                           }
                           ,{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BB',Color:'白色',
                               Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,
                               sizetitle:[
                                   {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                   ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                   {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                   ,{filed:'x_10',title:'5L'}
                               ],
                               sizeData:[{GoodsID:'00AG',ColorID:'0BB',x_1:1,Quantity:1,SizeID:'00A'},{GoodsID:'00AG',ColorID:'0BB',x_2:2,Quantity:2,SizeID:'00B'}]
                           }
                           ,{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BC',Color:'黄色',
                               Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,
                               sizetitle:[
                                   {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                   ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                   {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                   ,{filed:'x_10',title:'5L'}
                               ],
                               sizeData:[{GoodsID:'00AG',ColorID:'0BC',x_1:1,SizeID:'00B',Quantity:1},{GoodsID:'00AG',ColorID:'0BC',x_2:2,SizeID:'00C',Quantity:2}]
                           }
                           ,{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BD',Color:'绿色',
                               Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,
                               sizetitle:[
                                   {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                   ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                   {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                   ,{filed:'x_10',title:'5L'}
                               ],
                               sizeData:[{GoodsID:'00AG',ColorID:'0BD',x_1:1,SizeID:'00A',Quantity:1},{GoodsID:'00AG',ColorID:'0BD',x_2:2,SizeID:'00D',Quantity:2}]
                           }
                      ]
                     ,list: [
                           { title: '批发', value: '批发' },
                           { title: '订货', value: '订货' },//checked: true
                           { title: '配货', value:'配货' },
                           { title: '补货', value: '补货' },
                       ]
                       ,checkedInfo: { title: '批发', value: '批发' } //已选择
                   }
               }
               ,methods:{
                   onLoad(p){
                     this.total()
                   }
                   ,total(){

                      for(var i=0 ;i<this.detaillist.length;i++){
                          this.totalQty =Number(this.totalQty)+Number(this.detaillist[i].Quantity)
                          this.totalAmt =Number(this.totalAmt)+Number(this.detaillist[i].Amount)
                      }
                   }
                   ,wxcCellClicked(e){
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


