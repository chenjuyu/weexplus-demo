<template>
    <div class="wrapper">
        <head rightText="" title="录入界面" rightButton=""></head>
        <div class="goodsimg">
            <image style="width: 250px;height: 250px; border-style: dashed;border-width: 1px"  :src="imgurl"></image>
            <div class="dec">
                <text class="size" style="height: 60px;margin-top: 10px">货品名称:{{goods.productName}}</text>
                <text class="size" style="height: 60px;">货品编码:{{goods.productNo}}</text>
                <text class="size" style="height: 60px;">零售价￥:{{retailsales}}</text>
                <div class="unitprice">
                    <text class="size" style="height:60px;">单价:</text><input type="number"  @input="change(1)"  style="width: 150px;height: 50px;border-bottom-width: 2px;border-color: #eeeeee;font-size: 35px;" v-model.number="unitprice">
                    <text class="size" style="height: 60px;">折扣:</text><input type="number" @input="change(2)" style="width: 100px;height: 50px;border-bottom-width: 2px;border-color: #eeeeee;font-size: 35px;" v-model.number="discountrate">
                </div>
            </div>
        </div>
     <!--disabled="true"   -->

        <!--   <title title="wxc-grid-select"></title>
           <category title="单选"></category>
           <div class="demo">
               <wxc-grid-select
                       :list="testData1"
                       :single="true"
                       @select="params => onSelect('res1', params)">
               </wxc-grid-select>
               <text class="res">{{res1}}</text>
           </div>
           <text class="res"></text>
           <category title="多选"></category>
           <div class="demo">
               <wxc-grid-select
                       :list="testData2"
                       :limit="5"
                       @overLimit="onOverLimit"
                       @select="params => onSelect('res2', params)">
               </wxc-grid-select>
               <text class="res">{{res2}}</text>
           </div>
         -->

        <div class="demo">
            <scroller style="height: 200px;">
                <gridselect ref="glist"
                            :single="true"
                            :cols="5"
                            :list="testData3"
                            @select="params => onSelect('res3', params)">
                </gridselect>
                <!-- :customStyles="customStyles" 自定义
                     <text class="res">{{res3}}</text>  :style="{'height':getScreenHeight()}" -->
            </scroller>
        </div>




        <div class="cellTitle">
            <text class="text">尺码</text><text class="text2">库存数</text><text class="text3">输入数</text>
        </div>

        <list style="height: 490px;margin-bottom: 5px">
            <cell class="cell"  v-for="(ls,index) in sizefilter(sizelist,colorid)">
                <text class="cellitem">{{ls.sizesName}}</text>
                <text class="cellitem">{{ls.stock}}</text><!-- v-focus="true"  blur(ls)-->
                <!-- v-focus="true"  blur(ls) <text class="cellitem">{{ls.sizesName}}/{{ls.Size}}</text> -->
                <div class="inputdiv">
                    <text class="iconfont bar-ic" @click="jian(ls)">&#xe605;</text>
                    <input type="text" v-model="ls.Quantity" disabled="true" class="input" return-key-type="next"  @input="input(ls)"/>
                    <text class="iconfont bar-ic2" @click="add(ls)">&#xe604;</text>
                </div>
            </cell>
        </list>


        <div class="bottom">
            <div class="heji">
                <text class="size" style="color: #FFFFFF">合计:</text>
                <text class="size" style="margin-left: 20px; color: #FFFFFF">{{qtytotal}}</text>
                <text class="size" style="margin-left: 20px; color: #FFFFFF">￥{{Amttotal}}</text>
                <div class="submit" @click="submit"><text class="size" style="color: #FFFFFF">确认</text> </div>
            </div>

        </div>






    </div>
</template>

<script>
    import gridselect from './component/wxc-grid-select.vue'
    import module1 from './jstools/mytool'
    const nav=weex.requireModule('navigator')
    const net = weex.requireModule('net')
    const  pref=weex.requireModule('pref')
    const modal = weex.requireModule('modal')
        //imgurl:'root:img/logo.png',
    export default {
        components: {gridselect},
        data(){
            return{
                goods:{},
                retailsales:0,
                unitprice:0,
                discountrate:'',
                imgurl:'',
                res1: '',
                res2: '',
                res3: '',
                qtytotal:'',
                Amttotal:'',
                customStyles: {
                    lineSpacing: '14px',
                    width: '120px',
                    height: '50px',
                    icon: 'https://gw.alicdn.com/tfs/TB1IAByhgMPMeJjy1XdXXasrXXa-38-34.png',
                    color: '#333333',
                    checkedColor: '#ffffff',
                    disabledColor: '#eeeeee',
                    borderColor: '#666666',
                    checkedBorderColor: '#ffb200',
                    backgroundColor: '#ffffff',
                    checkedBackgroundColor: '#ffb200'
                },
                inputlist:[],//用于记录总的录入数量
                colorid:'',//当前选中的颜色ID
                selectIndex:'',//数组选中项
                checkedList:[],
                qtysum :0,//单个颜色尺码数量和
                sizelist:[
                 /*
                    {"productID":37541,"colorID":5594,"sizesID":52,"Quantity":"","Amount":"","stock":16,"productNo":"CD711201","productName":"小坐狗","colorNo":"5000","colorName":"蓝色","sizesNo":"00","sizesName":"均码","retailPrice":48.00,"price":23.00,"unitPrice":23.00,"discount":1.00},{"productID":37541,"colorID":4979,"sizesID":52,"Quantity":"","Amount":"","stock":0,"productNo":"CD711201","productName":"小坐狗","colorNo":"1204","colorName":"粉色","sizesNo":"00","sizesName":"均码","retailPrice":48.00,"price":23.00,"unitPrice":23.00,"discount":1.00}
                */
                ],
                testData3: [
                  /*  {   'productID':'00AQ',
                        'colorID':'0BA',
                        'title': '红色',
                        'checked': true,
                        'tipqty':''
                    },
                    {   'productID':'00AQ',
                        'colorID':'0BB',
                        'title': '蓝色',
                        'tipqty':''
                    },
                    {  'productID':'00AQ',
                        'colorID':'0BC',
                        'title': '白色',
                        'tipqty':''
                    }

                    {"productID":37541,"colorID":5594,"colorNo":"5000","colorName":"蓝色","title":"蓝色","tipqty":"","checked":true},{"productID":37541,"colorID":4979,"colorNo":"1204","colorName":"粉色","title":"粉色","tipqty":""}

                     */
                ]

            }
        },directives: {
            trigger: {
                inserted(el, binging) {
                    //el.blur()
                    //$(el).trigger('click')
                    if (binging) {
                        el.blur()
                    }
                    //  $(el).trigger('blur')
                }
            }, focus: {
                inserted: function (el, {value}) {
                    console.log(el, {value})
                    if (value) {
                        el.focus();
                    }
                }
            },blur:{
                inserted: function (el, {value}) {
                    console.log(el, {value})
                    if (value) {
                        el.blur();
                    }
                }
            }
        },
        methods: {
            onLoad(p){
                var param={}
                param.GoodsID='A001'//p.GoodsID
                param.DeptID='007'
                param.onLineId='0000-0000'
                param.userId=1
               // param.GoodsID='A001'
                let  _this=this

                _this.sizelist =p.sizeslist

               _this.testData3 =p.colorlist


               let colorcheck=_this.testData3.filter(item => item.checked === true)

                _this.colorid=colorcheck[0].colorID

           //     modal.alert({message:JSON.stringify(_this.testData3)})
            //    modal.alert({message:JSON.stringify(_this.sizelist)})
            //    modal.alert({message:'选中的colorlist'+JSON.stringify(colorcheck)})
             //   modal.alert({message:_this.colorid})

             //  _this.colorid='37'
              _this.retailsales= parseFloat(Number(_this.sizelist[0].retailPrice)).toFixed(2)
              _this.unitprice= parseFloat(Number( _this.sizelist[0].unitPrice)).toFixed(2)

              _this.discountrate=parseFloat(Number(_this.sizelist[0].discount)).toFixed(2)

              _this.goods.productName=  _this.sizelist[0].productName

                _this.goods.productNo=  _this.sizelist[0].productNo
            //    modal.alert({message:JSON.stringify(_this.sizelist)})
           //    modal.alert({message:"折扣："+ _this.discountrate});

             /*   modal.toast({
                    message: 'testData3的值：'+_this.testData3.toString(),
                    duration: 0.3
                })

                modal.toast({
                    message: 'colorid的值：'+_this.colorid,
                    duration: 0.3
                }) */

              //  modal.toast({
               //     message: 'GoodsID的值：'+ param.GoodsID,
                //    duration: 0.3
               // })
                // _this.colorid='0BA'
           /*     net.post(pref.getString('ip')+'/common.do?getColorAndSize', param,{},function () {
                    //start
                },function (res) {
                    //success
                    // _this.alert(res.res.obj.[colors])
                    if(res.res.success){
                        _this.testData3=res.res.attributes.colors
                        // _this.alert('testData3:'+ _this.testData3)
                        _this.goods=res.res.attributes.goods
                        _this.sizelist=res.res.obj
                        // _this.alert( 'sizelist:'+JSON.stringify(_this.sizelist))
                        _this.colorid=(_this.testData3.filter(item=>item.checked))[0].colorID
                        //_this.alert('filter:'+JSON.stringify(_this.testData3.filter(item=>item.checked)))
                        // _this.alert( 'colorid:'+_this.colorid)
                    }
                },function (res){//compelete
                },function (res){//exception
                })    */
            },jian(ls){
                let _this=this
                if(Number(ls.Quantity)>0) {
                    ls.Quantity = Number(ls.Quantity) - 1

                    let dList = _this.$refs.glist.dList.filter(item => item.checked === true)
                    for (let i = 0; i < dList.length; i++) {
                        let map = dList[i]
                        map.tipqty = _this.tipqtytotal(map.productID, map.colorID)
                        if (map.tipqty > 99) {
                            map.tipqty = '99+'
                        }
                    }

                    for(let i=0;i < _this.sizelist.length;i++){
                        let map = _this.sizelist[i]
                        map.unitprice = _this.unitprice
                        if(Number(map.price)==0){
                            map.price=Number(_this.unitprice)
                        }
                        map.discount =parseFloat(_this.discountrate).toFixed(2)
                        map.Amount=parseFloat(Number(map.price) * Number(map.Quantity) * Number(map.discount)).toFixed(2)
                    }

                    this.total()


                }
            },add(ls){
                let _this=this
              //  debugger
                if(Number(ls.stock)>0 && Number(ls.Quantity)< Number(ls.stock) ) {
                    ls.Quantity = Number(ls.Quantity) + 1
                    ls.Amount=Number(ls.Quantity) * Number(ls.unitPrice)

                    let dList = _this.$refs.glist.dList.filter(item => item.checked === true)
                    for (let i = 0; i < dList.length; i++) {
                        let map = dList[i]
                        map.tipqty = _this.tipqtytotal(map.productID, map.colorID)

                        if (map.tipqty > 99) {
                            map.tipqty = '99+'
                        }
                    }

                    for(let i=0;i < _this.sizelist.length;i++){
                        let map = _this.sizelist[i]
                        map.unitprice = _this.unitprice
                        if(Number(map.price)==0){
                            map.price=Number(_this.unitprice)
                        }
                        map.discount =parseFloat(_this.discountrate).toFixed(2)
                        map.Amount=parseFloat(Number(map.price) * Number(map.Quantity) * Number(map.discount)).toFixed(2)
                    }

                    this.total()




                }else {
                    modal.alert({message:'输入数不能大于库存数'})

                }
            //    modal.alert({message:JSON.stringify(dList)})





            },change(id){
                let _this=this
                    //  debugger
                if(id===1){//单价
                    if(Number(_this.sizelist[0].price)===0){
                        _this.discountrate =Number(1)

                    }else if(Number(_this.sizelist[0].price) !==0) {
                       // _this.alert(_this.unitprice)
                      //  _this.alert( _this.sizelist[0].price)
                        _this.discountrate =parseFloat(Number(_this.unitprice)/Number(_this.sizelist[0].price)).toFixed(2)

                    }


                //    _this.alert(_this.discountrate)


                }else if (id===2){//折扣
                if(Number(_this.discountrate) !==0){
                    _this.unitprice = parseFloat(Number(_this.sizelist[0].price) * Number(_this.discountrate)).toFixed(2)

                    _this.discountrate =parseFloat(_this.discountrate).toFixed(2)

                }


                }

            },submit(){
                var p={}
                p.sizelist=this.sizelist.filter(item=>Number(item.Quantity)!==0)
                pref._SendN(this.sizelist.filter(item=>Number(item.Quantity)!==0))
                //  nav.backFull(p,false)
                //this.alert(this.sizelist.filter(item=>Number(item.Quantity)>0))
                //return https://weex.apache.org/zh/docs/api/broadcast-channel.html#%E9%80%9A%E4%BF%A1%E8%BF%87%E7%A8%8B
                //  const cjy = new BroadcastChannel('sizelist')
                // let list=this.sizelist.filter(item=>Number(item.Quantity)>0)
                // cjy.postMessage({list})
                //  var p={};
                /*  p.sizelist=this.sizelist.filter(item=>Number(item.Quantity)>0)
                  var notify=weex.requireModule("notify")
                  notify.send('key',p)
                */
                //  nav.backTo('sd')
                //   nav.backFull(this.sizelist.filter(item=>Number(item.Quantity)>0),false)
            },
            onSelect (res,{selectIndex, checked, checkedList }) {
                let _this=this
                Vue.set(this, res, `本次选择的index：${selectIndex}\n是否选中：${checked
                    ? '是'
                    : '否'} \n 选中的id: ${checkedList.map(item=>item.colorID)} \n选中列表：${checkedList.map(item => item.title).join(',')}`);
                // _this.alert('inputlist:'+JSON.stringify(_this.inputlist))
                _this.colorid=checkedList.map(item=>item.colorID).toString()
                /*
                             var array= _this.sizefilter(_this.sizelist,_this.colorid)

                                modal.toast({
                                    message: ' 选中的id:：'+ _this.colorid,
                                    duration: 0.3
                                })

                                modal.toast({
                                    message: ' 选中的array:'+ array.toString(),
                                    duration: 0.3
                                })
                                */

                _this.selectIndex=selectIndex
                _this.checkedList=checkedList
                // checkedList.map(item => item.tipqty=8) //这种方式可以更新
                //  _this.alert('checkedList:'+JSON.stringify(_this.$refs.glist.dList))
                //这种的没有选择的
             /*   let dList=_this.$refs.glist.dList.filter(item => item.checked===false)
                for(let i=0;i<dList.length;i++){
                    let map=dList[i]
                    map.tipqty=_this.tipqtytotal(map.productID,map.colorID)
                    if(map.tipqty>99){
                        map.tipqty='99+'
                    }
                } */
                // _this.alert( JSON.stringify(_this.checkedList.filter(item =>item.checked)))
                //  _this.checkedList.map(item => item.tipqty=_this.qtysum)
            },
            onOverLimit (limit) {
                modal.toast({
                    message: `最多选择${limit}个`,
                    duration: 0.8
                });
            },sizefilter(sizelist,colorid){
                return sizelist.filter(function (map) {
                    return map.colorID== colorid
                })
            },
            tipqtytotal(productID,colorID){
                let _this=this
                _this.qtysum=Number(0)
                for(let i=0;i<_this.sizelist.length;i++){
                 /*   modal.toast({
                        message: '列表产品ID:'+_this.sizelist[i].productID,
                        duration: 0.3
                    })
                    modal.toast({
                        message: '参数ID:'+productID,
                        duration: 0.3
                    })

                    modal.toast({
                        message: '列表颜色ID:'+_this.sizelist[i].colorID,
                        duration: 0.3
                    })
                    modal.toast({
                        message: '参数颜色ID:'+colorID,
                        duration: 0.3
                    }) */
               //  debugger
                    if(_this.sizelist[i].productID===productID &&_this.sizelist[i].colorID===colorID)
                    {
                        _this.qtysum=_this.qtysum+Number(_this.sizelist[i].Quantity)
                    }
                }
                return _this.qtysum
            },total(){
                let q=Number(0)
                let a=Number(0)
                for(let i=0;i<this.sizelist.length;i++)
                {
                    q=q+Number(this.sizelist[i].Quantity)
                    a=a+ Number(this.sizelist[i].Amount)
                }
                this.qtytotal=q
                this.Amttotal=a
            },input(ls){ //输入时触发
                if(!module1.isIntNum(ls.Quantity)){
                    this.alert('请输入数字')
                    return
                }
                this.total()
            },
            blur(ls){
                var _this=this
                if(ls.qty !='' || ls.qty !=null) { //尺码的每一行记录
                    var map={}
                    map.GoodsID='01AQ'
                    map.ColorID=ls.colorid
                    map.SizeID=ls.sizeid
                    map.Quantity=ls.qty
                    //   _this.alert(_this.inputlist)
                    let mapData=_this.getList(map)
                    if(mapData ===null){//这个为不存在
                        _this.inputlist.unshift(map)
                    }else{
                        mapData.Quantity=map.Quantity
                    }
                    //   _this.qtysum=Number(_this.qtysum)+Number(map.Quantity)
                    //   _this.alert(qtysum)
                }
            },getList(barcode) {  //检查ListView中的重复记录  参数也为map
                for (let i = 0; i < this.inputlist.length; i++) {
                    let tempMap = this.inputlist[i]
                    if (barcode.GoodsID === tempMap.GoodsID && barcode.ColorID === tempMap.ColorID && barcode.SizeID === tempMap.SizeID) {
                        let Quantity = tempMap.Quantity
                        if (barcode.Quantity > 0 && Quantity > 0) {
                            return tempMap
                        } else if (barcode.Quantity < 0 && Quantity < 0) {
                            return tempMap
                        }
                    }
                }
                return null;
            }
        }
    }
</script>

<style scoped>
    .wrapper {
        display: block;
        position: absolute;
        top: 0;
        bottom: 0;
        left: 0;
        right: 0;
        /* padding-bottom: 24px; */
        background-color: #ffffff;
    }
    .goodsimg{
        flex-direction: row;
        width: 750px;
    }
    .dec{
        height: 250px;
    }
    .unitprice{
        flex-direction: row;
        height: 100px;
    }
    .scroller {
        flex: 1;
        height: 490px;
    }
    .demo {
        padding-top: 24px;
        padding-right: 24px;
        padding-left: 24px;
        border-bottom-width:1px;
        border-color: #eeeeee;
    }
    .res {
        margin-top: 24px;
        color: #666666;
        font-size: 26px;
        line-height: 36px;
    }.cell{
         flex-direction: row;
         top: 0;
         left: 0;
         right: 0;
     }
    .cellTitle{
        flex-direction: row;
        justify-content: center;
        align-items: center;
        text-align: center;
        height: 70px;
    }
    .text{
        justify-content: center;
        align-items: center;
        text-align: center;
        font-size: 35px;
        width:230px;
        flex-direction: row;
    }
    .text2{
        justify-content: center;
        align-items: center;
        text-align: center;
        font-size: 35px;
        width:230px;
        flex-direction: row;
    }
    .text3{
        justify-content: center;
        align-items: center;
        text-align: center;
        font-size: 35px;
        width:320px;
    }
    .inputdiv{
        flex-direction: row;
        width:320px;
        justify-content: center;
        align-items: center;
        text-align: center;
        font-size: 35px;
    }
    .input{
        width: 80px;
        height: 70px;
        /* border-bottom-width: 1px; */
        border-width: 1px;
        border-color:#eeeeee;
        justify-content: center;
        align-items: center;
        text-align: center;
    }
    .iconfont {
        font-family:iconfont;  /*必须写哦！！ */
    }
    .bar-ic{
        /*  padding-top: 14px; */
        font-size: 60px;
        color:black;
        /*  margin-left: 50px; */
        justify-content: center;
        align-items: center;
        background-color: #eeeeee;
        width: 80px;
        height: 70px;
        text-align: center;
    } .bar-ic2{
          /*  padding-top: 14px; */
          font-size: 60px;
          color: black;
          background-color: #eeeeee;
          justify-content: center;
          align-items: center;
          width: 80px;
          height: 70px;
          text-align: center;
      }
    .bottom{
        display: block;
        position: fixed;
        bottom:0;
        left:0;
        right: 0;
        width: 750px;
        background-color: #0085ee;
    }
    .heji{
        flex-direction: row; background-color: #1EA5FC; position: fixed;bottom: 0;left: 0;right: 0;height: 80px;align-items: center;justify-content:flex-start;
    }
    .submit{
        position: absolute;
        bottom:0;
        right: 0;
        height: 80px;
        width: 200px;
        justify-content: center;
        align-items: center;
        text-align: center;
        /*  -webkit-border-radius: 24px;
          border-radius: 24px; */
        background-color: orange;
    }
    .size{
        font-size: 35px;
    }
    .cellitem{
        width: 230px;
        justify-content: center;
        align-items: center;
        text-align: center;
        line-height: 70px;
        font-size: 35px
    }
</style>
