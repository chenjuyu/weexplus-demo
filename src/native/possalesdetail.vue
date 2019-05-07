<template>
    <div class="wrapper">
        <head :rightText="rightText" title="小票"  @rightClick="rightClick"></head>
        <div class="form">
            <div style="flex-direction: row;justify-content:flex-start;align-items: center;margin-top: 10px;">
                <text class="text">售货员</text>

                <div class="btn" @click="handleClick(1)">
                    <text class="btn-text" :style="{'color':colorName}">{{emp.Name}}</text>
                </div>

            </div>
            <div style="flex-direction: row;justify-content:flex-start;align-items: center">
                <text class="text">VIP</text>
                <div class="btn" @click="handleClick(2)">
                    <text class="btn-text">{{vip.vip}}</text>
                </div>
            </div>
            <div style="flex-direction: row;justify-content:flex-start;align-items: center">
                <text class="text">数量</text>
                <input class="input1" type="text" v-model="iqty"/>
            </div>
            <div style="flex-direction: row;justify-content:flex-start;align-items: center">
                <text class="text">{{barlable}}</text>
                <input class="input2" type="number" v-model="barcode" v-if="!cs" @return="search" return-key-type="search" />
                <div class="btn" @click="handleClick(5)" v-else>
                    <text class="btn-text" :style="{'color':colorName}">{{goods.Name}}</text>
                </div>
                <text class="iconfont bar-ic" @click="qrclick" v-if="!cs">&#xe69a;</text>

            </div>
            <div style="flex-direction: row;justify-content:flex-start;align-items: center" v-if="cs && !mult">
                <text class="text">颜色</text>
                <div class="btn" @click="handleClick(3)">
                    <text class="btn-text" :style="{'color':colorName}">{{color.color}}</text>
                </div>

            </div>
            <div style="flex-direction: row;justify-content:flex-start;align-items: center" v-if="cs && !mult">
                <text class="text">尺码</text>
                <div class="input" @click="handleClick(4)">
                    <text class="btn-text" :style="{'color':colorName}">{{size.size}}</text>
                </div>
                <text class="iconfont bar-ic" @click="qrclick" v-if="cs && !mult">&#xe603;</text>
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
         <list>
        <cell class="cell" v-for="(ls,index) in list" @longpress="handleLongPress(ls,index)">
            <text class="cellitem">{{ls.Barcode}}</text>
            <text class="cellitem">{{ls.GoodsCode}}</text>
            <text class="cellitem">{{ls.ColorName}}</text>
            <text class="cellitem">{{ls.SizeName}}</text>
            <text class="cellitem">{{ls.Quantity}}</text>
            <text class="cellitem">{{ls.Discount|numFilter}}</text>
            <text class="cellitem">{{ls.UnitPrice|numFilter}}</text>
            <text class="cellitem">{{ls.Amount}}</text>
        </cell>
         </list>
     </scroller>
        <div  v-bind:class="[isIPhoneX ?'tabbarxr':'tabbar']">
            <div style="flex-direction: row;justify-content:flex-start;align-items: center;">
                <text class="size">折让</text><input type="number"  v-model="discountMoney" style="width: 300px;height: 70px;border-bottom-width:1px;"/>
                <text class="size">实付</text><input type="number"  v-model="AmountSum" style="width: 300px;height: 70px;border-bottom-width:1px;">
            </div>
            <div style="flex-direction: row;justify-content:flex-start;align-items: center;">
                <text class="size">积分</text><input type="number"  v-model="Point" style="width: 300px;height: 70px;border-bottom-width:1px;"/>
                <text class="size">抵扣</text><input type="number" :disabled="true" v-model="exchange_amount" style="width: 300px;height: 70px;border-bottom-width:1px;">
            </div>
            <div style="flex-direction: row; background-color: #1EA5FC; position: fixed;bottom: 0;left: 0;right: 0;height: 80px;align-items: center;justify-content:flex-start;">
                <text class="size">合计：</text><text class="size">{{QuantitySum}}</text><text style="margin-left: 100px" class="size">￥{{AmountSum}}</text>
                <div  v-if="show" style="background-color: orange;position: fixed;right: 0;bottom: 0;width: 250px;height: 80px;align-items: center;justify-content:center"><text @click="save" class="size">{{savetitle}}</text></div>
            </div>


        </div>
    </div>
</template>

<script>
    const  pref=weex.requireModule('pref');
    const saveMethod="/salesTicket.do?saveSalesTicket"
    import { WxcDialog ,WxcPopover,Utils} from 'weex-ui';
    export default {
        components: { WxcDialog ,WxcPopover},
        data() {
            return {
                isIPhoneX:false,
                cs:false,
                mult:false,
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
                    Name :'请选择售货员'
                },
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
                this.isIPhoneX =Utils.env.isIPhoneX()

                this.alert(this.isIPhoneX)
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
                let nav=weex.requireModule('navigator');
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
                }

                nav.pushFull({url:'root:base.js',param: {"send": wherestr,"condition":condition,"id":id},
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
                            this.alert(res)
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
                        }else if(id===3){
                            this.color.colorid =res.id
                            this.color.color=res.Name
                        }else if(id===4){
                            this.size.sizeid=res.id
                            this.size.size=res.Name
                            this.size.x=res.x
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
              }else if(obj.key==='key-qrcode'){
                  self.cs=true
                  self.mult =true
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
                if(self.cs ){
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

                    map.Discount = self.vip.DiscountRate==0?self.goods.Discount:Number(self.vip.DiscountRate);
                    map.DiscountRate=self.vip.DiscountRate==0?self.goods.Discount:Number(self.vip.DiscountRate);

                    map.Quantity=self.iqty==0?1:self.iqty

                    if (map.DiscountRate !=null) {
                        map.DiscountPrice =Number(map.UnitPrice) * Number(map.DiscountRate)/10.0
                    }else{
                        map.DiscountPrice=null
                    }


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
                               map.Discount = self.vip.DiscountRate==0?array.Discount:Number(self.vip.DiscountRate);
                               map.DiscountRate=self.vip.DiscountRate==0?array.Discount:Number(self.vip.DiscountRate);
                               map.GoodsID = array.GoodsID;
                               map.ColorID = array.ColorID;
                               map.SizeID = array.SizeID;
                               map.UnitPrice = array.UnitPrice;
                               map.RetailSales = array.RetailSales;
                               map.Quantity=self.iqty==0?1:self.iqty

                               map.x=array.x
                               map.sizIndex=array.sizIndex



                              if (map.DiscountRate !=null) {
                                  map.DiscountPrice =Number(map.UnitPrice) * Number(map.DiscountRate)/10.0
                              }else{
                                  map.DiscountPrice=null
                              }
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



            },countTotal(){
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

                if(self.savetitle==='新增'){
                    var page=weex.requireModule("page")
                    page.reload();
                    return
                }


                const net = weex.requireModule('net');
                const progress =weex.requireModule('progress')
                var map={}
                map.data=self.list;
                map.vipId=self.vip.vipId;
                map.vipCode= self.vip.vipCode;
                map.employeeId= self.emp.employeeId;
                map.amount= self.AmountSum;
                map.retailAmount =Number(self.AmountSum)-Number(self.discountMoney) -Number(self.exchange_amount) //exchange_amount 积分兑换的金额
                map.discountMoney=self.discountMoney
                map.vipPointRate=1
              //  map.put("retailAmount", retailAmount);
               // map.put("discountMoney", String.valueOf(exchangeMoney + Double.parseDouble(discountMoney)));

              //  map.put("vipPointRate", String.valueOf(vipPointRate));
              //  map.put("vipDiscount", String.valueOf(vipDiscount));
              //  map.put("posBackAudit", String.valueOf(posBackAudit));
                map.qty= self.QuantitySum;
                map.memo='demo生成====';
                map.type=self.type
                map.exchangedPoint='0'
                map.posBackAudit=false


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
  }
    .size{
        font-size: 30px;
    }
  .wrapper{

      background-color:#eeeeee;
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
      font-size: 30px
  }
    .cell:active{ /*按下的样式*/
        background-color: #0085ee;
    }
  .input {
      padding-top: 20px;
      padding-bottom: 20px;
      padding-left: 20px;
      padding-right: 20px;
      border-width: 1px;
      margin-bottom: 10px;
      height: 70px;
      width: 500px;
      background-color: #FFF;
  }
    .input2 {
        border-width: 1px;
        margin-bottom: 10px;
        height: 70px;
        width: 500px;
        background-color: #FFF;
    }

  .input1 {
      border-width: 1px;
      margin-bottom: 10px;
      height: 70px;
      width: 750px;
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
      margin-bottom: 20px;
      width: 750px;
      height: 70px;
      border-width: 1px;
      align-items: flex-start;
  }
  .btn-text {
      font-size: 30px;
  }
  .tabbar{
      display: block;
      height: 230px;width: 750px; position: fixed;bottom: 0;left: 0;right: 0;
      background-color: #1EA5FC;
  }
    .tabbarxr{
        display: block;
        height: 230px;width: 750px; position: fixed;bottom: 44px;left: 0;right: 0;
        background-color: #1EA5FC;
    }
  .text{
      width:100px;
     direction: ltr;
      font-size: 30px
  }
</style>