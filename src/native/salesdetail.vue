<template>
    <div class="wrapper">
        <head :rightText="rightText" title="发货单"  @rightClick="rightClick"></head>
        <div class="form">
            <div class="form1">
            <div  class="divline">
                <text class="text">客户:</text>
                <div class="btn" @click="handleClick(6)">
                    <text class="btn-text">{{customer.customer}}</text>
                    <image src="root:img/input_bg.png" class="input_bg"></image>
                </div>
            </div>

            <div  class="divline">
                <text class="text">仓库:</text>
                <div class="btn" @click="handleClick(7)">
                    <text class="btn-text">{{Department.Department}}</text>
                    <image src="root:img/input_bg.png" class="input_bg"></image>
                </div>
            </div>

            <div  class="divline">
                <text class="text">类型:</text>
                <div class="btn" @click="handleClick(8)">
                    <text class="btn-text">{{billType.Name}}</text>
                    <image src="root:img/input_bg.png" class="input_bg"></image>
                </div>
            </div>
            <div  class="divline">
            <text class="text">经手:</text>

            <div class="btn" @click="handleClick(1)">
                <text class="btn-text" :style="{'color':colorName}">{{emp.Name}}</text>
                <image src="root:img/input_bg.png" class="input_bg"></image>
            </div>

        </div>

            <div class="divline">
                <text class="text">品牌:</text>

                <div class="btn" @click="handleClick(9)">
                    <text class="btn-text" :style="{'color':colorName}">{{Brand.Brand}}</text>
                    <image src="root:img/input_bg.png" class="input_bg"></image>
                </div>

            </div>
            </div>
           <image style="width: 270px;height: 350px; border-style: dashed;border-width: 1px"  :src="imgurl" ></image>
        </div>

        <div style="flex-direction: row;justify-content:flex-start;align-items: center">
            <text class="text">数量:</text>
            <input class="input1" type="text" v-model="iqty"/>
        </div>



            <div style="flex-direction: row;justify-content:flex-start;align-items: center">
                <text class="text">{{barlable}}</text>
                <input class="input2" type="number" v-model="barcode" v-if="!cs" @return="search" return-key-type="search" />
                <div class="btn" @click="handleClick(5)" v-else>
                    <text class="btn-text" :style="{'color':colorName}">{{goods.Name}}</text>
                    <image src="root:img/input_bg.png" class="input_bg"></image>
                </div>
                <text class="iconfont bar-ic" @click="qrclick" v-if="!cs">&#xe69a;</text>
                <text class="iconfont bar-ic" @click="qrclick" v-if="cs && !mult">&#xe603;</text>
            </div>



            <div style="flex-direction: row;justify-content:flex-start;align-items: center" v-if="cs && !mult">
                <text class="text">颜色:</text>
                <div class="input"  @click="handleClick(3)">
                    <text class="btn-text" :style="{'color':colorName}">{{color.color}}</text>
                    <image src="root:img/input_bg.png" class="input_bg"></image>
                </div>



                    <text class="text">尺码:</text>
                <div class="input" @click="handleClick(4)">
                    <text class="btn-text" :style="{'color':colorName}">{{size.size}}</text>
                    <image src="root:img/input_bg.png" style="width:10;height:40;position: absolute;right: 0px;bottom: 0px"></image>
                </div>
            </div>



        <wxc-dialog title="详情"
                    :show="show1"
                    :single="false"
                    :is-checked="isChecked"
                    :show-no-prompt="false"
                    @wxcDialogCancelBtnClicked="wxcDialogCancelBtnClicked"
                    @wxcDialogConfirmBtnClicked="wxcDialogConfirmBtnClicked"
                    @wxcDialogNoPromptClicked="wxcDialogNoPromptClicked">

           <div slot="content" style="align-items: flex-start;line-height: 150px" >
               <div style="flex-direction: row;height: 80px;align-items: center"><text class="size">货号:{{lmap.GoodsCode}}</text></div>
               <div style="flex-direction: row ;height: 80px;align-items: center"><text class="size">颜色:{{lmap.ColorName}}</text></div>
               <div style="flex-direction: row;height: 80px;align-items: center"><text class="size">尺码:{{lmap.SizeName}}</text></div>
               <div style="flex-direction: row;height: 80px;align-items: center"><text class="size">数量:</text><input type="number" class="dialoginput" v-model="lmap.Quantity" /></div>
           </div>
        </wxc-dialog>

        <wxc-popover ref="wxc-popover"
                     :buttons="btns"
                     :position="popoverPosition"
                     :arrowPosition="popoverArrowPosition"
                     @wxcPopoverButtonClicked="popoverButtonClicked"></wxc-popover>


     <scroller  style="width: 1100px" scroll-direction='horizontal'>
        <div  style="flex-direction: row">
            <text class="cellitem">条码</text>
            <text class="cellitem">货品</text>
            <text class="cellitem">颜色</text>
            <text class="cellitem">尺码</text>
            <text class="cellitem">数量</text>
            <text class="cellitem">折扣</text>
            <text class="cellitem">单价</text>
            <text class="cellitem">金额</text>
        </div>
         <scroller>
        <div class="cell" v-for="(ls,index) in list" @longpress="handleLongPress(ls,index)">
            <text class="cellitem">{{ls.Barcode}}</text>
            <text class="cellitem">{{ls.GoodsCode}}</text>
            <text class="cellitem">{{ls.ColorName}}</text>
            <text class="cellitem">{{ls.SizeName}}</text>
            <text class="cellitem">{{ls.Quantity}}</text>
            <text class="cellitem">{{ls.Discount|numFilter}}</text>
            <text class="cellitem">{{ls.UnitPrice|numFilter}}</text>
            <text class="cellitem">{{ls.Amount}}</text>
        </div>
         </scroller>
     </scroller>
        <div  class="tabbar">
          <!--  <div style="flex-direction: row;justify-content:flex-start;align-items: center;">
                <text class="size">折让</text><input type="number"  v-model="discountMoney" style="width: 300px;height: 70px;border-bottom-width:1px;"/>
                <text class="size">实付</text><input type="number"  v-model="AmountSum" style="width: 300px;height: 70px;border-bottom-width:1px;">
            </div>
            <div style="flex-direction: row;justify-content:flex-start;align-items: center;">
                <text class="size">积分</text><input type="number"  v-model="Point" style="width: 300px;height: 70px;border-bottom-width:1px;"/>
                <text class="size">抵扣</text><input type="number" :disabled="true" v-model="exchange_amount" style="width: 300px;height: 70px;border-bottom-width:1px;">
            </div> -->
            <div style="flex-direction: row; background-color: #1EA5FC; position: fixed;bottom: 0;left: 0;right: 0;height: 80px;align-items: center;justify-content:flex-start;">
                <text class="size">合计：</text><text class="size">{{QuantitySum}}</text><text style="margin-left: 100px" class="size">￥{{AmountSum}}</text>
                <div  v-if="show" style="background-color: orange;position: fixed;right: 0;bottom: 0;width: 250px;height: 80px;align-items: center;justify-content:center"><text @click="save" class="size">{{savetitle}}</text></div>
            </div>


        </div>
    </div>
</template>

<script>
    const  pref=weex.requireModule('pref');
    const saveMethod="/sales.do?saveSales"
    const url ='root:img/input_bg.png'
    let nav=weex.requireModule('navigator');
    import { WxcDialog ,WxcPopover} from 'weex-ui';
    export default {
        components: { WxcDialog ,WxcPopover},
        data() {
            return {
                bgImg: {
                   // backgroundImage: "url(" + url + ")"

                    width:'380px',
                    height:'70px',
                    backgroundcolor:'red'
                },
                direction:'',
                SalesID:'',
                discountRateSum:'', //发货单 整单折扣
                lastARAmount:'',
                orderAmount:'',
                privilegeAmount:'',
                imgurl:'root:img/logo.png',  //先做新增
                cs:false,
                mult:false,
                billType:{  //单据类别
                    id:0.00,
                    Name:''
                },
                color:{
                    colorid:'',
                    color:''
                },
                size:{
                  sizeid:'',
                  size:'',
                    x:0
                },
                barlable:'条码',
                rightText:'\ue602',
                lmap:{},
                popoverPosition:{x:-14,y:120},
                popoverArrowPosition:{pos:'top',x:-15},
                btns:[
                    {
                       // icon: 'https://gw.alicdn.com/tfs/TB1Vm3abuuSBuNjy1XcXXcYjFXa-64-64.png',
                        text:'货品录入',
                        key:'key-scan'
                    },
                    {
                        //icon: 'https://gw.alicdn.com/tfs/TB1U93abuuSBuNjy1XcXXcYjFXa-64-64.png',
                        text:'多颜色录入',
                        key:'key-qrcode'
                    },
                    {
                     //   icon: 'https://gw.alicdn.com/tfs/TB1MWDTbwmTBuNjy1XbXXaMrVXa-64-64.png',
                        text:'打印',
                        key:'key-help'
                    },
                ],
                oldqty:0,
                show1: false,
                isChecked: false,
                name: "possalesdetail",
                savetitle:'保存',
                show:false,
                AmountSum:0.00,
                discountMoney:0.00,
                QuantitySum:0,
                DiscountSum:0.0,
                amount:0.0,
                type:'销售单',
                Point:0,
                exchange_amount:0.0,
                barcode:'',
                list:[],
                iqty:1,
                qty:0,
                emp:{
                    employeeId:'',
                    Name :'请选择经手人',
                    BusinessDeptID:''
                },customer:{
                    customerid:'',
                    customer:'',
                    DiscountRate:0.00,
                    DistrictID:'',
                    OrderDiscount:0.00,
                    AllotDiscount:0.00,
                    ReplenishDiscount:0.00
                },Department:{
                    DepartmentID:'',
                    Department:''
                },Brand:{
                    BrandID:'',
                    Brand:''
                } ,
                vip:{
                    vipId:'',
                    vipCode:'',
                    vip:'',
                    VIPTypeID:'',
                    DiscountRate:0.0,
                    vipPointRate:0,
                    UsablePoint:0
                },
                goods:{
                    goodsid:'',
                    code:'',
                    Name:'',
                    RetailSales:0.0,
                    UnitPrice:0.0,
                    Discount:0.0,
                    DiscountFlag:false,
                    sizIndex:0

                }
            }
        },
        props:{
            colorName:{
                default:"#eeeeee"
            }
        },methods:{
            onLoad(p){
                // this.alert("接收参数"+p);
                // this.alert("No的值："+p.No+",madeby的值："+p.madeby);
                //console.log(p)
                nav.setRoot('sd')
                nav.setPageId('sd')
            },
            focus() {
                this.$refs.widget.focus();
            },
            blur() {
                this.$refs.widget.blur();
            },
            handleFocus() {
                // toast('获得焦点');
                //
            },
            handleBlur() {
                //toast('失去焦点');
            },
            handleReturn() {
                //toast('点击了回车按钮');
            },
            change(){
            },
            input(){
            },handleClick(id){
                let self=this

                let wherestr="";
                let condition=""
                if (id===1){
                    wherestr= "getEmployee"
                }else if(id===2){
                    wherestr= "getVip"
                }else if(id===3) //颜色
                {
                    if(self.goods.goodsid===''){
                        self.alert('请先输入货号')
                        return
                    }
                    //手动选择
                    wherestr="getColorByGoodsCode"
                    condition ='@'+self.goods.goodsid+'@'
                }else  if(id===4) //尺码
                {
                    if(self.goods.goodsid===''){
                        self.alert('请先输入货号')
                        return
                    }
                    //手动选择
                    wherestr="getSizeByGoodsCode"
                    condition ='@'+self.goods.goodsid+'@'
                } else if(id===5){
                    wherestr= "getPosSalesGoods"
                } else if (id===6){
                      // 客户
                    wherestr ='getCustomer'
                } else if (id===7){
                    wherestr ='getWarehouse'
                } else if(id===8){
                    //单据类别
                    wherestr="Type"
                } else if(id===9){
                    wherestr ='getBrand'
                }
                //self.mult 多颜色录入标志


                nav.pushFull({url:'root:base.js',param: {"send": wherestr,"condition":condition,"id":id,'mult':self.mult},
                    animate:true,
                    isPortrait:true},(res)=>{
                    this.colorName = '#000'

                    if (res !=undefined) {
                        if (id===1){ //员工

                        this.emp.employeeId = res.id;
                          //  this.alert("employeeid:"+this.emp.employeeId)
                        this.emp.Name = res.Name;

                        }else if(id===2){ //vip
                            this.vip.vipId=res.id
                            this.vip.vip =res.Name;
                            this.vip.vipCode=res.Code
                            this.vip.DiscountRate=res.DiscountRate
                            this.vip.UsablePoint =res.UsablePoint
                            this.vip.vipPointRate =res.vipPointRate
                            this.vip.VIPTypeID =res.VIPTypeID

                        } else if(id===5){


                            const chen = new BroadcastChannel('sizelist')
                            debugger
                            chen.onmessage=(event)=> {
                               // this.alert('event:'+JSON.stringify(event.data))
                                console.log(event.data)
                                modal.alert({message:JSON.stringify(event.data)})
                            }
                            this.alert('list:'+JSON.stringify(res))
                            this.goods.goodsid=res.id
                            this.goods.Name=res.Name
                            this.goods.code=res.Code
                            this.goods.RetailSales=res.RetailSales

                            this.goods.UnitPrice=res.UnitPrice
                            this.goods.Discount=res.Discount
                            this.goods.DiscountFlag=res.DiscountFlag
                            this.goods.sizIndex=res.sizIndex

                            //重新选择货号，要重新选择颜色与尺码
                            this.color.colorid =''
                            this.color.color=''
                            this.size.sizeid=''
                            this.size.size=''
                            this.size.x=''
                           if(this.cs && !this.mult)
                            self.handleClick(3)

                        }else if(id===3){
                            this.color.colorid =res.id
                            this.color.color=res.Name
                            self.handleClick(4)
                        }else if(id===4){
                            this.size.sizeid=res.id
                            this.size.size=res.Name
                            this.size.x=res.x
                        } else if(id===6){
                            this.customer.customerid=res.id
                            this.customer.customer =res.Name
                            this.customer.DiscountRate=res.DiscountRate
                            this.customer.DistrictID=res.DistrictID
                            this.OrderDiscount=res.OrderDiscount
                            this.AllotDiscount=res.AllotDiscount
                            this.ReplenishDiscount=res.ReplenishDiscount

                        } else if(id===7){
                            this.Department.DepartmentID=res.id
                            this.Department.Department =res.Name

                        }else if(id===8){ //单据类别
                            this.billType.id=res.id
                            this.billType.Name =res.Name

                        }else if(id===9){
                            //表头品牌
                            this.Brand.BrandID=res.id
                            this.Brand.Brand =res.Name
                        }

                    }

                });
            },rightClick(){
                this.$refs['wxc-popover'].wxcPopoverShow();
            }, popoverButtonClicked (obj) {
               //modal.toast({ 'message': `key:${obj.key}, index:${obj.index}`, 'duration': 1 });
                let self=this
                let map=self.btns[obj.index]
              if(obj.key==='key-scan') {
                  if(self.cs){
                      self.cs=false
                      self.mult =false
                      self.barlable='条码'
                      map.text='货品录入'
                  }else{
                      self.cs=true
                      self.mult =false
                      self.barlable='货号'
                      map.text='条码录入'
                  }
              }else if(obj.key==='key-qrcode'){ //多颜色录入
                  self.cs=true
                  self.mult =true
                  self.barlable='货号'
              }
            },
            handleLongPress(ls,index){
                //this.alert("ls"+ls.GoodsCode +",index:"+index)
                this.lmap=ls
                this.oldqty=ls.Quantity
                this.show1 = true;
            },   wxcDialogCancelBtnClicked () {
                //此处必须设置，组件为无状态组件，自己管理  取消取回原来的值
                if(this.oldqty !=this.lmap.Quantity){
                    this.lmap.Quantity=  this.oldqty
                }
                this.show1 = false;
            },
            wxcDialogConfirmBtnClicked () {
                //此处必须设置，组件为无状态组件，自己管理
                if(!this.isIntNum(this.lmap.Quantity))
                {  this.alert('请输入数字')
                    return
                }
                if(this.oldqty !=this.lmap.Quantity){
                   // this.lmap.Amount= Number(this.lmap.Quantity) * Number(this.lmap.Discount)/10 *Number(this.lmap.UnitPrice)
                    this.countTotal()
                }
                this.show1 = false;
            },
            qrclick(){
                const self=this
                if(self.cs ){ //手输货号
                 if (self.goods.goodsid ==='' && self.color.colorid ==='' && self.size.sizeid ==='')
                     return;

                    let map={}
                    map.GoodsName =self.goods.Name;
                    map.ColorName =self.color.color
                    map.SizeName = self.size.size
                    map.GoodsCode = self.goods.code;
                 //   map.Discount = self.vip.DiscountRate==0?array.Discount:Number(self.vip.DiscountRate);
                  //  map.DiscountRate=self.vip.DiscountRate==0?array.Discount:Number(self.vip.DiscountRate);
                    map.GoodsID =self.goods.goodsid
                    map.ColorID =self.color.colorid
                    map.SizeID = self.size.sizeid
                    map.UnitPrice =self.goods.UnitPrice;
                    map.RetailSales = self.goods.RetailSales;

                    map.Discount = self.getPriceByType(self.goods.UnitPrice).DiscountRate
                    map.DiscountRate=self.getPriceByType(self.goods.UnitPrice).DiscountRate

                    map.Quantity=self.iqty==0?1:self.iqty


                    map.DiscountPrice=self.getPriceByType(self.goods.UnitPrice).price



                    let  mapData =self.getList(map)  //返回是否存在
                    if(mapData===null){
                        self.list.unshift(map);
                    }else{
                        mapData.Quantity=Number(mapData.Quantity)+Number(map.Quantity)
                    }


                    self.clear()
                    self.countTotal()

                    if (self.list.length>0){
                        self.show=true
                    }

                    return
                }

                var qr=weex.requireModule('qr')
                var p={};
                p.color='#ffffff'//'#000000'
                p.bgcolor='#00000000'//'#ffffff'
                qr.open(p,(res)=>{
                   // var url=res.url
                    //this.alert(res)
                    if(res !=undefined){
                        self.barcode=res
                        self.search()
                    }
                })
            },clear(){
                let self=this
                self.barcode='' //重置扫描区
                self.goods.goodsid=''
                self.goods.code=''
                self.goods.Name=''
                self.goods.RetailSales=0.0
                self.goods.UnitPrice=0.0
                self.goods.Discount=0.0
                self.goods.DiscountFlag=false
                self.color.colorid=''
                self.color.color=''
                self.size.sizeid=''
                self.size.size=''
            },
            search(){
                let self=this

                if(self.emp.employeeId ==='')
                {
                    self.alert('请先选择售货员')
                    return
                }
                if (self.customer.customerid ===''){
                    self.alert('请先选择客户')
                    return;
                }
                if (self.billType.id ===''){
                    self.alert('请先选择客户的单据类型')
                    return;
                }
                if(self.barlable==='货号' && self.barcode !=''){
                    self.handleClick(5)
                    return;//不用再查询条码
                }
               if(this.barcode !='')
               {

                   if (!self.isIntNum(self.iqty)){
                       self.alert('数量：请输入数字')
                       return
                   }


                   const net = weex.requireModule('net');
                   net.post(pref.getString('ip')+'/select.do?analyticalBarcode',{"Type":"possales","BarCode":this.barcode},{},function(){
                       //start
                   },function(e){
                       //success
                       //  self.back=e.res;
                       //  self.list.splice(0,self.list);
                     // if(e.res.obj !=undefined)

                       if(e.res.obj===undefined){
                           self.alert('没有找到此条码')
                          return
                       }

                       var array= e.res.obj;

                      self.alert(e.res.obj)

                     //  debugger

                           var map = {};
                              map.Barcode=array.BarCode;
                               map.GoodsName = array.GoodsName;
                               map.ColorName = array.ColorName;
                               map.SizeName = array.SizeName;
                               map.GoodsCode = array.GoodsCode;

                               map.Discount = self.getPriceByType(self.goods.UnitPrice).DiscountRate
                               map.DiscountRate=self.getPriceByType(self.goods.UnitPrice).DiscountRate

                               map.GoodsID = array.GoodsID;
                               map.ColorID = array.ColorID;
                               map.SizeID = array.SizeID;
                               map.UnitPrice = array.UnitPrice;
                               map.RetailSales = array.RetailSales;
                               map.Quantity=self.iqty==0?1:self.iqty

                               map.x=array.x
                               map.sizIndex=array.sizIndex




                               map.DiscountPrice=self.getPriceByType(self.goods.UnitPrice).price

                             //  map.Amount=self.iqty==0?1:parseFloat(self.iqty * array.UnitPrice).toFixed(2) //先单价* 数量，后台促销后面再算
                        //  self.list.push(map); //最后面
                        //   self.alert(map.Amount)
                       // self.list.unshift(map);
                       let  mapData =self.getList(map)  //返回是否存在
                       if(mapData===null){
                           self.list.unshift(map);
                       }else{
                           mapData.Quantity=Number(mapData.Quantity)+Number(map.Quantity)
                       }
                       self.barcode='' //重置扫描区
                       self.countTotal()

                     if (self.list.length>0){
                         self.show=true
                     }

                      // self.alert("employeeId的变化"+self.emp.employeeId)
                      // self.alert("vipid的变化"+self.vip.vipId)

                   },function(e){
                       //compelete

                   },function(){
                       //exception
                   });

               }



            },getPriceByType(unitprice){ //得到客户价格类型的折扣，即折后价, 参数为单价
               var map={}
               var price =0.00
                if(this.billType.Name==='批发'){
                    price=Number(unitprice) *Number(this.customer.DiscountRate)/10
                    map.DiscountRate=this.customer.DiscountRate
               }else if(this.billType.Name==='订货'){
                    price=Number(unitprice) *Number(this.customer.OrderDiscount)/10
                    map.DiscountRate=this.customer.OrderDiscount
                }else if(this.billType.Name==='配货'){
                    price=Number(unitprice) *Number(this.customer.AllotDiscount)/10
                    map.DiscountRate=this.customer.AllotDiscount
                }else if(this.billType.Name==='补货'){
                    price=Number(unitprice) *Number(this.customer.ReplenishDiscount)/10
                    map.DiscountRate=this.customer.ReplenishDiscount
                }
                map.price=parseFloat(price).toFixed(2)
                return map

            },
            countTotal(){
                //统计合计与金额 this.vip.DiscountRate !=0.0 &&
                let sumAmt=0.00
                let sumQty=0
                if( this.list.length >0){

                    for(let i=0;i< this.list.length;i++){
                       let map=  this.list[i]
                       // this.alert("map的值:"+map.UnitPrice+";Quantity的值："+map.UnitPrice*0.8025)
                        map.Amount=parseFloat(this.vip.DiscountRate==0?map.UnitPrice *map.Quantity:map.UnitPrice *map.Quantity*this.vip.DiscountRate/10.0).toFixed(2)
                        sumAmt=Number(sumAmt)+Number(parseFloat(this.vip.DiscountRate==0?map.UnitPrice *map.Quantity:map.UnitPrice *map.Quantity*this.vip.DiscountRate/10.0).toFixed(2))
                        sumQty=Number(sumQty)+Number(map.Quantity)
                    }
                 this.QuantitySum = sumQty
                 this.AmountSum   = parseFloat(sumAmt).toFixed(2)




                }

            },isIntNum(val){
                var regPos = /^[0-9]+.?[0-9]*$/;; // 非负整数
                var regNeg = /^\-[1-9][0-9]*$/; // 负整数
                if(regPos.test(val) || regNeg.test(val)){
                    return true;
                }else{
                    return false;
                }
            },getList(barcode) {  //检查ListView中的重复记录  参数也为map
                for(let i=0;i<this.list.length;i++){
                 let tempMap=this.list[i]
                  if(barcode.GoodsID===tempMap.GoodsID && barcode.ColorID===tempMap.ColorID &&barcode.SizeID===tempMap.SizeID){
                     let Quantity =tempMap.Quantity

                      if(barcode.Quantity>0 && Quantity>0){
                          return tempMap
                      }else if(barcode.Quantity<0 && Quantity<0){
                          return tempMap
                      }

                  }


                }
                return null;
            },save(){
                const self=this
                self.direction=1
                if(self.savetitle==='新增'){
                    var page=weex.requireModule("page")
                    page.reload();
                    return
                }


                const net = weex.requireModule('net');
                const progress =weex.requireModule('progress')
                var map={}
                map.SalesID=''

                map.customerid=self.customer.customerid;
                map.discountRateSum= self.discountRateSum;
                map.lastARAmount= self.lastARAmount;
                map.orderAmount= self.orderAmount;
                map.privilegeAmount =self.privilegeAmount
                map.discountMoney=self.discountMoney
                map.departmentid=self.Department.DepartmentID
                map.employeeId=self.emp.employeeId
                map.brandId=self.Brand.BrandID
                map.businessDeptId=self.emp.BusinessDeptID
                map.paymentTypeId=''
                map.memo='wx生成'
                map.type=self.billType.Name
                map.direction=self.direction
                map.typeEName=self.billType.id
                map.notUseNegativeInventoryCheck=false
                map.data=self.list;

                map.wxflag=true



              /*
                map.qty= self.QuantitySum;
                map.memo='demo生成====';
                map.type=self.type
                map.exchangedPoint='0'
                map.posBackAudit=false

               */


                net.post(pref.getString('ip')+saveMethod,map,{},function(){
                    //start
                    progress.showFull('保存中...',true)
                },function(e){
                    //success

                    progress.dismiss();
                    self.savetitle ='新增'
                },function(e){
                    //exception

                },function(){
                    //compelete

                });





            }

        },
        created() {

        },
        filters: {

            numFilter(value) {

                // 截取当前数据到小数点后两位

                let realVal = parseFloat(value).toFixed(2)

                return realVal

            }

        }
    }
</script>

<style scoped>
    .iconfont {
        font-family:iconfont;  /*必须写哦！！ */
    }
    .bar-ic{
    /*  padding-top: 14px; */
      font-size: 70px;
      color: #0085ee;
      margin-left: 20px;
  }
    .size{
        font-size: 35px;
    }
  .wrapper{

  /*    background-color:#eeeeee; */
      display: block;
      position: absolute;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;

  }
  .cell{
      top: 0;
      left: 0;
      right: 0;
      margin-bottom: 0px;
      margin-top: 0;
      flex-direction: row;
  }
  .cellitem{
      width: 120px;
      line-height: 70px;
      font-size: 35px
  }
    .cell:active{ /*按下的样式*/
        background-color: #0085ee;
    }
    .input_bg{
        width:10;height:40;position: absolute;right: 0px;bottom: 0px
    }
  .input {
      padding-top: 20px;
      padding-bottom: 20px;
      padding-left: 20px;
      padding-right: 20px;
      border-bottom-width: 1px;
      margin-bottom: 10px;
      height: 70px;
      width: 260px;
      background-color: #FFF;
     /* background: url(img/input_bg.png) no-repeat right bottom; */
  }
    .input2 {
        border-bottom-width: 1px;
        margin-bottom: 10px;
        height: 70px;
        width: 380px;
        background-color: #FFF;
    }
    .form{
        width: 750px;
        flex-direction: row;
    }
    .form1{
        width: 480px;

    }
    .divline{
        flex-direction: row;justify-content:flex-start;
        align-items: center;
        width:480px
    }
  .input1 {
      border-bottom-width: 1px;
      margin-bottom: 10px;
      height: 70px;
      width: 380px;
      background-color: #FFF;
  }
    .dialoginput{
        border-bottom-width: 1px;
        height: 50px;
        width: 300px;


    }
  .btn {
      padding-top: 20px;
      padding-bottom: 20px;
      padding-left: 20px;
      padding-right: 20px;
      background-color: #FFF;

      width: 380px;
      height: 70px;
      border-bottom-width: 1px;
      align-items: flex-start;
  }
  .btn-text {
      font-size: 35px;
  }
  .tabbar{
      display: block;
     /* height: 230px; */
      width: 750px;
      position: fixed;bottom: 0;left: 0;right: 0;
      background-color: #1EA5FC;
  }
  .text{
      width:100px;
     direction: ltr;
      font-size: 35px;


  }
</style>