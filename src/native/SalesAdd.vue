<template>
    <div class="wrapper">
        <head :rightText="rightText" title="销售发货单单据" @rightClick="rightClick"></head>
    <div class="search" >
    <input type="text" style="width: 500px;height: 80px;border-width: 5px;border-color: #00B4FF;margin-left: 10px" placeholder="请输入货号查找" @input="input"  v-model="keyword"/>
     <div style="height: 80px;width: 220px;margin-right: 5px;border-width: 5px;justify-content: center;align-items: center;border-color: #00B4FF;"  @click="search">
         <text style="font-size: 35px;">查询</text>
     </div>
    </div>
        <!-- style="flex: 1;margin-top: 5px;margin-bottom: 80px" 'margin-top': '5px',,'background-color': 'orange'}" -->
        <list :style="{height: pageheight+'px'}">
    <cell >
        <wxc-cell label="备注:"
                  :has-arrow="false"
                  :cell-style="{'height':'70px'}"
                  @wxcCellClicked="wxcCellClicked"
                   :has-bottom-border="false"	>
            <input type="text" style="width: 500px;height: 70px" v-model="memo"/>
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
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{billType.Name}}</text>
        </wxc-cell>

      <div class="input_bg" v-if="AuditFlag">  <image src="root:img/Audit.png" style="width: 170px;height: 125px;"  ></image> </div>

        <wxc-cell label="发货部门:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  @wxcCellClicked="wxcCellClicked(1)"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{Department.Department}}</text>
        </wxc-cell>

        <wxc-cell label="客户名称:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  @wxcCellClicked="wxcCellClicked(2)"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{customer.customer}}</text>
        </wxc-cell>
        <!--  @wxcCellClicked="wxcCellClicked"-->
        <wxc-cell label="应收余额:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{customer.lastAmt}}</text>
        </wxc-cell>

        <wxc-cell label="经手人:"
                  :has-arrow="true"
                  :cell-style="{'height':'80px'}"
                  @wxcCellClicked="wxcCellClicked(3)"
                  :has-bottom-border="true"	>
            <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{emp.Name}}</text>
        </wxc-cell>
     <div class="listbutton">
         <text style="font-size: 35px;border-width: 5px;border-color: #00B4FF;height: 60px;width: 220px;margin-left: 5px;text-align: center">蓝牙增加</text>
         <text @click="addgoods"  style="font-size: 35px;border-width: 5px;border-color: #00B4FF;height: 60px;width: 220px;text-align: center">快速增加</text>
         <text @click="qrcodeclick"  style="font-size: 35px;border-width: 5px;border-color: #00B4FF;height: 60px;width: 220px;margin-right: 5px;text-align: center">扫码增加</text>
     </div>
    </cell><!--master 结束 border-radius:25px   v-for="num in lists" <list style="height: 200px"> </list>-->


             <cell ref="skid" v-for="(ls,i) in detaillist" @click="onNodeClick(ls, i)" :key="'skid-' + i" class="wxc-skid" :style="{width: (750 + ls.right.length * 100) + 'px', height: height + 'px'}" @touchstart="(e) => !isAndroid && onPanStart(e, ls, i)" @horizontalpan="(e) => isAndroid && onPanStart(e, ls, i)" @touchend="(e) => onPanEnd(e, ls, i)">
                 <!-- 整体包1-->
                 <div :style='styles' class="swipe-action-center border">

                     <div style="flex-direction: row">
                         <text style="font-size: 30px;color: red;">{{ls.Code}}</text>
                         <text style="font-size: 30px;color:#000000; margin-left: 20px">{{ls.Name}}</text>
                     </div>
                 <!-- 图片与显示-->
               <div style="flex-direction: row;width: 750px">
                  <div style="justify-content: center;align-items: center;height: 150px"> <text style="font-size: 30px;color:#000000;">{{Number(i)+1}}</text> </div>
                   <image v-if="ls.img" :src="ls.img" style="width: 150px;height: 150px;"></image>
                   <image v-else  src="root:img/home_logo.png" style="width: 150px;height: 150px;"></image>

                   <div style="margin-left: 20px">
                   <text style="font-size: 30px;color:#000000;height: 50px">颜色:{{ls.Color}}</text>
                   <text style="font-size: 30px;color:#000000;height: 50px">折扣率:{{ls.Discount}}</text>
                   <text style="font-size: 30px;color:#000000;height: 50px">数量:{{ls.Quantity}}</text>
                   </div>
                   <div style="position: absolute;right: 10px;">
                       <text style="font-size: 30px;color:#000000;height: 50px;">单价:{{ls.UnitPrice}}</text>
                       <text style="font-size: 30px;color:#000000;height: 50px;">折扣:{{ls.DiscountRate}}</text>
                       <text style="font-size: 30px;color:#000000;height: 50px;">金额:{{ls.Amount}}</text>
                   </div>
               </div>
                 <!-- 图片与显示-->
                <div style="flex-direction: row">
                 <text style="font-size: 35px;color:red;margin-left: 5px;width: 45px;text-align: center" v-for="(lst,index1) in ls.sizetitle">{{lst.title}}</text>
                </div>
                 <div style="flex-direction: row">
                 <text style="font-size: 35px;color:#000000;margin-left: 5px;width: 45px;text-align: center" v-for="(lst2,index2) in ls.sizeData">{{lst2.Quantity }}</text>
                 </div>

                 </div>  <!-- 整体包1结束-->
                 <!-- 菜单-->
                 <div class="swipe-action-right">
                     <text class="swipe-action-child swipe-action-text" @click="onRightNode(ls, child, i)" v-for="(child, childIdx) of ls.right" :style="Object.assign({lineHeight: height + 'px'}, child.style || {})" :key="'child-' + childIdx">{{child.text}}</text>
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
        <div class="footer" v-if="AuditFlag == false">
            <div style="background-color: orange;justify-content:center;align-items:center;width: 200px; border-radius:20px " @click="save">  <text style="font-size: 40px;color: #FFFFFF;">保存</text></div>
            <div style="background-color: orange;justify-content:center;align-items:center;width: 200px; margin-right: 20px;border-radius:20px" @click="receival"><text style="font-size: 40px;color: #FFFFFF;">收款</text></div>
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


        <wxcpopover ref="wxc-popover"
                     :buttons="btns"
                     :position="popoverPosition"
                     :arrowPosition="popoverArrowPosition"
                     @wxcPopoverButtonClicked="popoverButtonClicked"></wxcpopover>


         </div>
       </template>
       <style scoped>
           .wrapper{
               position: absolute;
               top: 0;
               right: 0;
               bottom: 0;
               left: 0;
           }
           .wxc-skid{
               flex-direction: row;
               border-bottom-width: 1px;
               justify-content: center;
               border-color: #dddddd
           }
           .swipe-action-center {
               width: 750px;
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
           .input_bg{ /*position: absolute; background-color: #0085ee; top: 60px;bottom: 60px; 170  background-color: red;*/
               position: absolute;
               left: 300;
               top: 90;

               width:170px;
               height:135px;
               justify-content: center;
               align-items: center;


           }
       </style>
       <script>
           import Binding from "weex-bindingx/lib/index.weex.js";
           const animation = weex.requireModule("animation");
           import { WxcCell ,WxcMask,WxcRadio  ,Utils } from 'weex-ui';
           import wxcpopover from './component/wxc-popover.vue';
           const modal =weex.requireModule('modal')
           var lodash = require('lodash');
           var nav = weex.requireModule('navigator') ;
           const  pref=weex.requireModule('pref')
           const net = weex.requireModule('net');
           const progress = weex.requireModule('progress');

           const dom = weex.requireModule('dom')
           import module1 from './jstools/mytool'// 引用方式
           let timestr=module1.formatDate((new Date()),"yyyy-MM-dd")
           var url='/sales.do?salesEditX'
           var saveurl='/sales.do?saveSales'
           var qrcodeurl='/select.do?analyticalBarcodeX'
           export default {
               components: { WxcCell,WxcMask,WxcRadio,wxcpopover   }
               ,props: {
                   pageheight: {
                       type: [Number, String],
                       default:weex.config.env.deviceHeight //Utils.env.getPageHeight()   //Utils.env.getPageHeight() 这个会已减去导航条的高度了
                   },
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
               }
               ,data() {
                   return {
                       popoverPosition:{x:-14,y:110}
                       ,popoverArrowPosition:{pos:'top',x:-14}
                       ,btns:[{
                           icon: '\ue61f',
                           text:'打印',
                           key:'key-print'
                          }]
                       ,rightText:'\ue604'
                       ,submitmap:{}
                       ,keyword:''
                       ,SalesID:''//销售发货单 主表ID
                       ,AuditFlag:false //审核标志
                       ,lastARAmount:'' //单据的收款金额
                       ,billType:{Name:'批发',id:'PriceType'}
                       ,memo:''
                      // ,testlist: [{GoodsID:'00AG',ColorID:'0BD',Color:'黑色',x:'x_1',SizeID:'00A',Size:'均码',Quantity:1},
                     //  {GoodsID:'00AG',ColorID:'0BD',Color:'黑色',x:'x_2',SizeID:'00D',Size:'XS',Quantity:2}]
                       ,mobileX: 0,
                       webStarX: 0,
                       saveIdx: null,
                       isAndroid: Utils.env.isAndroid()
                     ,DateStr:timestr
                     ,show:false
                     ,hasAnimation:false
                     ,totalQty:0
                     ,totalAmt:0.0
                     ,totalDiscount:0.00
                     ,Department:{Department:'',DepartmentID:''}
                     ,customer:{customer:'',customerid:'',lastAmt:''}
                     ,emp:{Name:'',EmpID:''}
                     ,detaillist:[ /*
                         {GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorID:'0BA',Color:'黑色',
                               Discount:0.0,DiscountRate:8.0,Quantity:2,Amount:34.5,
                               sizetitle:[
                                   {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                   ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                   {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                   ,{filed:'x_10',title:'5L'}
                               ],
                               sizeData:[{GoodsID:'00AG',ColorID:'0BA',Color:'黑色',x:'x_1',Quantity:1,SizeID:'00A',Size:'均码',Amount:''},
                                   {GoodsID:'00AG',ColorID:'0BA',Color:'黑色',x:'x_2',Quantity:1,SizeID:'00A',Size:'XS',Amount:''}]
                             ,right: [
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
                         }
                           ,{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BB',Color:'白色',
                               Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,
                               sizetitle:[
                                   {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                   ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                   {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                   ,{filed:'x_10',title:'5L'}
                               ],
                               sizeData:[{GoodsID:'00AG',ColorID:'0BB',x:'x_1',Quantity:1,SizeID:'00A',Color:'白色',Size:'37',Amount:''},
                                   {GoodsID:'00AG',ColorID:'0BB',Color:'白色',x:'x_2',Quantity:2,SizeID:'00B',Size:'38',Amount:''}]
                               ,right: [
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
                           }
                           ,{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BC',Color:'黄色',
                               Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,
                               sizetitle:[
                                   {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                   ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                   {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                   ,{filed:'x_10',title:'5L'}
                               ],
                               sizeData:[{GoodsID:'00AG',ColorID:'0BC',x:'x_1',Size:'均码',Color:'黄色',SizeID:'00B',Quantity:1,Amount:''},
                                   {GoodsID:'00AG',ColorID:'0BC',Color:'黄色',x:'x_2',SizeID:'00C',Size:'XS',Quantity:2,Amount:''}]
                               ,right: [
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
                           }
                           ,{GoodsID:'00AG',Code:'192B1210017',Name:'外披',ColorTitle:'颜色',ColorID:'0BD',Color:'绿色',
                               Discount:0.0,DiscountRate:8.0,Quantity:3,Amount:34.5,
                               sizetitle:[
                                   {field:'x_1',title:'均码'},{field:'x_2',title:'XS'},{filed:'x_3',title:'S'}
                                   ,{filed:'x_4',title:'M'},{filed:'x_5',title:'L'},{filed:'x_6',title:'1L'},
                                   {filed:'x_7',title:'2L'},{filed:'x_8',title:'3L'},{filed:'x_9',title:'4L'}
                                   ,{filed:'x_10',title:'5L'}
                               ],
                               sizeData:[{GoodsID:'00AG',ColorID:'0BD',x:'x_1',SizeID:'00A',Size:'均码',Quantity:1,Amount:''},
                                   {GoodsID:'00AG',ColorID:'0BD',x:'x_2',SizeID:'00D',Size:'XS',Quantity:2,Amount:''}]
                               ,right: [
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
                       } */
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

                       //   this.alert("页面高度:"+this.pageheight) //dom.scrollToElement(el, {offset:0}) 不用到定位功能可以用list 列表flex:1


                       var that=this
                       that.detaillist.slice(0,that.detaillist.length) //重新进入都清一次
                       this.alert(JSON.stringify(p))
                       if(p==null){
                           p={}
                       }
                      this.SalesID=p.hasOwnProperty("SalesID")?p.SalesID:''   //|| 'DN000XW'//''

                       if(this.SalesID ==''){ //可能是新增单据，也有可能是从其他页面点的返回键
                           return
                       }


                      this.memo =p.hasOwnProperty("Memo")?p.Memo:''
                      this.DateStr =p.hasOwnProperty("Date")?p.Date:timestr
                      this.Department.DepartmentID =p.hasOwnProperty("DepartmentID")?p.DepartmentID:''
                      this.Department.Department =p.hasOwnProperty("Department")?p.Department:''
                      this.customer.customerid =p.hasOwnProperty("CustomerID")?p.CustomerID:''
                      this.customer.customer =p.hasOwnProperty("Customer")?p.Customer:''
                      this.customer.lastAmt = p.hasOwnProperty("LastNeedRAmount")?p.LastNeedRAmount:''
                      this.emp.EmpID = p.hasOwnProperty("EmployeeID")?p.EmployeeID:''
                      this.emp.Name =p.hasOwnProperty("Name")?p.Name:''
                      this.AuditFlag =p.hasOwnProperty("AuditFlag")?p.AuditFlag:false


                       if(this.AuditFlag) {
                           //this.alert("页面高度a:"+this.pageheight)
                          // this.alert("页面高度:"+this.pageheight)
                           this.pageheight = weex.config.env.deviceHeight
                       }else{
                           this.pageheight = Utils.env.getPageHeight()- Number(160)//Number(this.pageheight) - Number(200) //动态获取页面的高度不用flex:1 这里用到定位功能

                       }



                       net.post(pref.getString('ip') + url,{SalesID:this.SalesID},{},function(){
                           //start
                           progress.showFull('加载中',false)
                       },function(e){
                           //success
                           if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {
                               that.detaillist = e.res.obj;
                             //  that.alert("网络返回的："+JSON.stringify(that.detaillist[3])+",长度："+that.detaillist[3].sizeData.length)



                            /*   for(var i=0;i<that.detaillist[0].right.length;i++) {
                                  // that.alert("style:" +that.detaillist[0].right[i].style)
                                   //that.detaillist[0].right[i].style =JSON.parse(JSON.stringify(that.detaillist[0].right[i].style))
                                   var style ={ backgroundColor: '#F4333C', color: 'white' }
                                   that.detaillist[0].right[i].style =style
                               } */
                               that.total()
                           }
                           progress.dismiss()
                       },function(e){
                           //compelete

                       },function(){
                           //exception
                           progress.dismiss()
                       });




                   }
                   ,total(){
                     var  sumqty =0,sumamt=0,sumdiscount=0
                      for(var i=0 ;i<this.detaillist.length;i++){
                          sumqty =Number(sumqty)+Number(this.detaillist[i].Quantity)
                          sumamt =Number(sumamt)+Number(this.detaillist[i].Amount)
                          sumdiscount =Number(sumdiscount)+Number(this.detaillist[i].Discount)
                      }
                       this.totalQty=sumqty
                       this.totalDiscount =sumdiscount
                       this.totalAmt=sumamt
                       if(this.totalDiscount){
                           this.totalDiscount =parseFloat(this.totalDiscount).toFixed(2)
                       }
                       if(this.totalAmt){
                           this.totalAmt =parseFloat(this.totalAmt).toFixed(2)
                       }

                   }
                   ,wxcCellClicked(id){
                   // var obj=   lodash.pick(this.testlist,['GoodsID','ColorID','Quantity'])
                  // this.alert(JSON.stringify(e))
                      var p={}
                      if(id==1){//发货部门
                         p.send='getWarehouse'
                          p.condition=''
                      }else if(id==2){//客户
                         p.send ='getCustomer'
                          p.condition=''

                      }else if(id==3){ //经手人
                         p.send ='getEmployee'
                         p.condition=''
                      }
                      //{"send": p.send,"condition":p.condition,"id":id}
                       nav.pushFull({url:'root:base.js',param: p,
                           animate:true,
                           isPortrait:true},(res)=>{

                           if (res !=undefined) {
                               if (id==1){ //发货部门

                                   this.Department.DepartmentID=res.id
                                   this.Department.Department =res.Name


                               }else if(id===2){ //v客户
                                   this.customer.customerid=res.id
                                   this.customer.customer =res.Name
                                   this.customer.DiscountRate=res.DiscountRate
                                   this.customer.DistrictID=res.DistrictID
                                   this.customer.OrderDiscount=res.OrderDiscount
                                   this.customer.AllotDiscount=res.AllotDiscount
                                   this.customer.ReplenishDiscount=res.ReplenishDiscount
                                   this.customer.lastAmt=''

                               }else if(id==3){

                                   this.emp.EmpID = res.id;
                                   //  this.alert("employeeid:"+this.emp.employeeId)
                                   this.emp.Name = res.Name;
                               }

                           }

                       });



                   }
                  ,selectType(e){
                      // var pop=weex.requireModule("centerpop")
                       //pop.show('root:Typeradio.js',{width:500,height:700},{},true);
                      for(var i=0 ;i< this.list.length ;i++){
                          var map=this.list[i]
                           if(this.billType.Name == map.value){
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

                       this.billType.Name =e.value
                       if(e.value=='批发'){
                        this.billType.id='PriceType'
                       }else if(e.value=='订货'){
                        this.billType.id='OrderPriceType'
                       }else if(e.value =='配货'){
                        this.billType.id='AllotPriceType'
                       }else if(e.value =='补货'){
                         this.billType.id='ReplenishType'
                       }


                       this.show=false
                   }
                   //左滑方法开始
                   ,special(dom, styles) {
                       animation.transition(dom, {
                           styles,
                           duration: 200, //ms
                           timingFunction: "ease",
                           delay: 100 //ms
                       });
                   },
                   onRightNode(pNode, node, i) {
                      // node.onPress();
                       //this.alert(node)

                       if (pNode.autoClose)
                           this.special(this.$refs.skid[i], {
                               transform: `translate(0, 0)`
                           });

                       if(this.AuditFlag){
                           this.toast('单据已审核不可删除')
                           return
                       }

                       if(node.text=='删除'){
                           this.detaillist.splice(i,1);//删除元素，以i 为参数
                           this.total()
                       }


                   },
                   onNodeClick(node, i) {
                    //   this.alert("mobileX:"+this.mobileX)
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
                          // this.$emit("onNodeClick", node, i);
                         //  this.alert(JSON.stringify(node))
                          // this.alert("占南后的："+JSON.stringify(this.detaillist.filter(item=>item.GoodsID ==node.GoodsID))+",记录数："+(this.detaillist.filter(item=>item.GoodsID ==node.GoodsID)).length)
                           if(this.AuditFlag) {
                             this.toast('单据已审核不能再修改')
                               return
                           }
                           if(node.sizeData.length>0) {
                               var obj =(this.detaillist.filter(item=>item.GoodsID ==node.GoodsID)).map(function (obj) {  // node.sizeData.map(function (obj) {
                                   return {
                                       SalesDetailID:obj.hasOwnProperty("SalesDetailID")?obj.SalesDetailID:'',
                                       SalesID:obj.hasOwnProperty("SalesID")?obj.SalesID:'',
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

                              this.alert('obj：' + JSON.stringify(obj)+',记录数：'+obj.length)
                               var arr = [] ,   sizearr=[]
                               for (var i = 0; i < obj.length; i++) {
                                   debugger
                                   var map = {}
                                   map.SalesDetailID=obj[i].SalesDetailID
                                   map.SalesID=obj[i].SalesID
                                   map.GoodsID = obj[i].GoodsID
                                   map.Code=obj[i].Code
                                   map.Name=obj[i].Name
                                   map.RetailSales=obj[i].RetailSales
                                   map.ColorID = obj[i].ColorID
                                   map.Quantity = obj[i].Quantity
                                   map.UnitPrice =obj[i].UnitPrice
                                   map.DiscountRate=obj[i].DiscountRate
                                   map.tipqty = obj[i].tipqty
                                   map.title = obj[i].title
                                   if(arr.length>0) {

                                    var  m=this.hasmap(arr,map,0) //合并相同颜色的 数量金额
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





                               }




                               arr[0].checked = true
                               this.submitmap.colorlist = arr
                               this.submitmap.sizelist = sizearr//node.sizeData//this.testlist
                               this.alert('挑选颜色列表对象：' + JSON.stringify(arr)+',颜色记录数：'+arr.length)
                              this.alert('挑选尺码列表对象：' + JSON.stringify(sizearr)+',尺码记录数:'+sizearr.length)
                        //  return
                           nav.pushFull({url: 'root:goodsDetail.js',param:this.submitmap,animate:true}
                               , (e) => {
                                   this.alert('e'+JSON.stringify(e))
                                   if (e !== undefined){ //返回结果是尺码集体的，要拆分
                                       // this.alert('返回的数据:'+JSON.stringify(e)+',记录数： '+e.detaillist.length)
                                       if(e==null || JSON.stringify(e)=='{}'){//无结果返回，指的是点左上角返回菜单的返回
                                           return
                                       }
                                       if(e.detaillist.length >0) {

                                           for(var i=0;i<e.detaillist.length ;i++){

                                               var backdata=e.detaillist[i]
                                               if(this.detaillist.length>0){
                                                   var m=this.hasmap(this.detaillist,backdata,1) //1 替换
                                                   if(JSON.stringify(m) !=='{}') {
                                                       for(var j=0;j<backdata.sizeData.length;j++) {
                                                           var sizemap=backdata.sizeData[j]
                                                           var n = this.hasSize(m.sizeData, sizemap,0)
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
                                       this.total()
                                   }

                               })

                           }



                   },
                   save(){ //保存单据
                       //提交前，先算好，零售金额
                       var that=this

                       if(this.Department.DepartmentID ==''){
                           that.alert('请选择发货部门')
                           return
                       }
                       if(this.customer.customerid ==''){
                           that.alert('请选择客户')
                           return;
                       }
                      if(this.detaillist.length <=0){
                          that.alert('当前无数据可提交')
                          return;
                      }


                       for(var i=0;i<this.detaillist.length;i++){
                           var map=this.detaillist[i] //只算到颜色那一层，尺码暂不管
                           map.RetailAmount =Number(map.Quantity) *  Number(map.RetailSales)
                       }
                       var p={}
                       p.SalesID =this.SalesID
                       p.customerid=this.customer.customerid
                       p.discountRateSum ='' //整单折扣 字段
                       p.lastARAmount =this.lastARAmount //单据收款金额
                       p.orderAmount ='' //使用订金
                       p.privilegeAmount=''//优惠金额
                       p.departmentid=this.Department.DepartmentID //发货部门
                       p.employeeId =this.emp.EmpID //经手人
                       p.brandId ='' //品牌
                       p.businessDeptId ='' //业务部门
                       p.paymentTypeId=''//结算方式
                       p.memo ='手机APP生成' //备注
                       p.type =this.billType.Name //单据类别
                       p.direction =1 //发货单标志
                       p.typeEName ='' //暂无使用些字段，有参数要地求要有
                       p.notUseNegativeInventoryCheck="true" //没有负库存开单 猜的
                       p.wxflag =true  //标志来源
                       p.data=this.detaillist //单据子表数据 关键

                       net.post(pref.getString('ip') + saveurl,p,{},function(){
                           //start
                           progress.showFull('正在保存单据',false)
                       },function(e){
                           //success
                           progress.dismiss()
                           that.SalesID =e.res.attributes.SalesID
                           that.toast('保存成功')
                           that.push('root:saleslist.js')
                           //var p={}
                          // p.SalesID=e.res.attributes.SalesID
                           //that.onLoad(p)

                       },function(e){
                           //compelete

                       },function(){
                           //exception
                           that.alert('异常')
                           progress.dismiss()
                       });


                   },receival(){//收款
                       var that=this
                       var p={}
                         p.lastARAmount =this.lastARAmount
                         p.totalQty =this.totalQty
                         p.totalAmt =this.totalAmt
                       nav.pushFull({url: 'root:salesreceival.js',param:p,animate:true}
                           , (e) => {
                               this.alert('e' + JSON.stringify(e))
                               if (e !== undefined) { //返回结果是尺码集体的，要拆分
                                   if(e==null || JSON.stringify(e)=='{}'){//无结果返回，指的是点左上角返回菜单的返回
                                       return
                                   }
                                   that.lastARAmount= e.ReceivalAmount


                               }
                           })

                   }
                   ,hasmap(arr,map,isback){ //有一个就返回，最后判断，没有返回undefined,isback代表从其他页面返回的结果
                       var m={}
                  for (var j = 0; j < arr.length; j++) {
                     if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && isback==0 ) {
                         arr[j].Quantity = Number(arr[j].Quantity) + Number(map.Quantity)
                         arr[j].Amount = Number(arr[j].Amount) + Number(map.Amount)
                         arr[j].tipqty = Number(arr[j].tipqty) + Number(map.tipqty)
                         m=arr[j]
                   }else  if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && isback==1 ) {
                         arr[j].Quantity = Number(map.Quantity)
                         arr[j].UnitPrice = Number(map.UnitPrice)
                         arr[j].DiscountRate = Number(map.DiscountRate)
                         if(Number(map.DiscountRate)!=0 && map.DiscountRate !=="" && map.DiscountRate !=undefined) {
                             arr[j].Discount = Number(map.UnitPrice) * Number(map.Quantity) * (Number(10)-Number(map.DiscountRate))/ 10.0 //增加一基折扣额
                         }else{
                             arr[j].Discount =''
                         }
                         arr[j].Amount = Number(map.Amount)
                         arr[j].tipqty =  Number(map.tipqty)
                         m=arr[j]
                     }else  if(arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && isback==2){ //扫码追加数据
                         arr[j].Quantity = Number(arr[j].Quantity) + Number(map.Quantity)
                         arr[j].Amount = Number(arr[j].Amount) + Number(map.Amount)
                         arr[j].DiscountRate = Number(map.DiscountRate)
                         if(Number(map.DiscountRate)!=0 && map.DiscountRate !=="" && map.DiscountRate !=undefined) {
                             arr[j].Discount =Number(arr[j].Discount)+ Number(map.UnitPrice) * Number(map.Quantity) * (Number(10)-Number(map.DiscountRate))/ 10.0 //增加一基折扣额
                         }else if(arr[j].Discount ==''){
                             arr[j].Discount ==''
                         }
                         arr[j].tipqty = Number(arr[j].tipqty) + Number(map.tipqty)
                         m=arr[j]
                     }
                 }
                      return m


                 },hasSize(arr,map,flag){  //flag 0  从详情页返回 替换 1 扫码追加
                       var m={}
                       for (var j = 0; j < arr.length; j++) {

                           if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && arr[j].SizeID ==map.SizeID && flag==0) {
                               arr[j].Quantity = map.Quantity
                               arr[j].Amount =map.Amount
                                 m  = arr[j]

                           }else if (arr[j].GoodsID == map.GoodsID && arr[j].ColorID == map.ColorID && arr[j].SizeID ==map.SizeID && flag==1){
                               arr[j].Quantity =Number(arr[j].Quantity)+Number(map.Quantity)
                               arr[j].Amount =Number(arr[j].Amount)+ Number(map.Amount)
                               m  = arr[j]
                           }
                       }
                       return m
                   }
                  , onPanEnd(e, node, i) {
                       if (Utils.env.isWeb()) {
                           const webEndX = e.changedTouches[0].pageX;
                           this.movingDistance(webEndX - this.webStarX, node, this.$refs.skid[i]);
                       }
                   },
                   onPanStart: function(e, node, i) {
                       const { saveIdx } = this;
                       if (saveIdx !== i && saveIdx !== null) {
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
                   //左滑方法结束
                   ,addgoods(e){ //如果有就是修改

                       if(this.customer.customerid == '') {
                           this.toast('请先选择客户')
                           return
                       }
                       if(this.billType.Name== '') {
                           this.toast('请先选择单据类别')
                           return
                       }

                      if(this.AuditFlag){
                          this.toast('单据已审核无法再修改')
                          return;
                      }

                       this.submitmap.detaillist=this.detaillist || []
                       this.submitmap.CustomerID=this.customer.customerid
                       this.submitmap.Type =this.billType.Name
                       this.submitmap.addflag=true
                       nav.pushFull({url: 'root:goodslist.js',param:this.submitmap,animate:true}
                                    ,(e)=>{
                               this.alert('e的的的'+JSON.stringify(e))
                               if(e==null || JSON.stringify(e)=='{}'){//无结果返回，指的是点左上角返回菜单的返回
                                   return
                               }
                                if(e.detaillist.length >0){

                                  this.detaillist =e.detaillist
                                  this.total()

                                }
                           })

                   },input(){
                       this.search()
                   }
                   ,search(){


                       for(var i=0;i<this.detaillist.length;i++) {
                         var map  =  this.detaillist[i]

                           //this.log("外层的："+i)
                        //   this.log(map) indexOf(this.keyword) !=-1
                             if(map.Code.indexOf(this.keyword) !=-1){
                                // this.log(i)
                               //  this.log("输出："+this.$refs["skid"][i])
                                   const el = this.$refs["skid"][i] //this.$refs.item10[0]
                                   dom.scrollToElement(el, {})

                               }
                       }
                   },qrcodeclick(){

                       if(this.Department.DepartmentID ==''){
                           that.alert('请选择发货部门')
                           return
                       }
                       if(this.customer.customerid ==''){
                           that.alert('请选择客户')
                           return;
                       }

                       if(this.AuditFlag){
                           this.toast('单据已审核无法再修改')
                           return;
                       }
                       var that=this
                       var qr=weex.requireModule('qr')
                       var p={};
                       p.color='#000000'
                       p.bgcolor='#ffffff'
                       qr.open(p,(res)=>{
                           //var url=res.url
                             if(res ==undefined){
                                 that.alert('没有扫到内容')
                                 return
                             }
                           that.alert('扫到内容'+res)
                           net.post(pref.getString('ip')+qrcodeurl,{BarCode:res,CustomerId:that.customer.customerid,Type:that.billType.id},{},function(){
                               //start
                           },function(e){
                               //success  e.res;
                               if(e==null || JSON.stringify(e)=='{}' || e==undefined){
                                   return
                               }
                               if(JSON.stringify(e.res.obj)=="{}" || e.res.obj==null){
                                   return;
                               }
                               that.alert("扫码返回的数据："+JSON.stringify(e))
                               that.alert("detaillist.length数据："+that.detaillist.length)
                               if(that.detaillist.length >0) {
                                   var m=that.hasmap(that.detaillist,e.res.obj,2) //1 替换
                                   that.alert("m的数据："+JSON.stringify(m))
                                   if(JSON.stringify(m) !=='{}') {
                                       for(var j=0;j<e.res.obj.sizeData.length;j++) {
                                           var sizemap=e.res.obj.sizeData[j]
                                           var n = that.hasSize(m.sizeData, sizemap,1)
                                       }
                                   }else if(JSON.stringify(m) =='{}'){
                                       that.detaillist.unshift(e.res.obj)
                                   }
                               }else{
                                   that.detaillist.unshift(e.res.obj)
                               }
                               that.toast('成功添加货号：'+e.res.obj.Code)
                               that.total()

                           },function(e){
                               //compelete

                           },function(){
                               //exception
                               that.alert('异常')
                           });


                       })

                   },rightClick(){
                      // this.toast('更多功能开发中，敬请期待')
                       this.$refs['wxc-popover'].wxcPopoverShow();
                   },popoverButtonClicked (obj) {
                       modal.toast({ 'message': `key:${obj.key}, index:${obj.index}`, 'duration': 1 });
                   }

               }
           }
       </script>


