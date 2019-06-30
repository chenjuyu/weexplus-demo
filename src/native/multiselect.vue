<template>
    <div class="wrapper">
        <head rightText="" title="多颜色多尺码" rightButton=""></head>
        <div class="goodsimg">
        <image style="width: 250px;height: 250px; border-style: dashed;border-width: 1px"  :src="imgurl"></image>
        <div class="dec">
            <text class="size" style="height: 60px;margin-top: 10px">货品名称</text>
            <text class="size" style="height: 60px;">货品编码</text>
            <text class="size" style="height: 60px;">零售价￥:{{retailsales}}</text>
         <div class="unitprice">
             <text class="size" style="height:60px;">单价:</text><input type="number" style="width: 100px;height: 50px;border-bottom-width: 2px;border-color: #eeeeee" v-model="unitprice">
             <text class="size" style="height: 60px;">折扣:</text><input type="number" style="width: 100px;height: 50px;border-bottom-width: 2px;border-color: #eeeeee" v-model="discountrate">
         </div>
        </div>
        </div>


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
                 <text class="text">尺码</text><text class="text2">采购数/可发数</text><text class="text3">输入数</text>
             </div>

             <list style="height: 490px;margin-bottom: 5px">
             <cell class="cell"  v-for="(ls,index) in sizefilter(sizelist,colorid)">
               <text class="cellitem">{{ls.Size}}</text><!-- v-focus="true"  blur(ls)-->
               <text class="cellitem">{{ls.Size}}/{{ls.Size}}</text><!-- v-focus="true"  blur(ls)-->
               <div class="inputdiv">
                   <text class="iconfont bar-ic" @click="jian(ls)">&#xe605;</text>
                   <input type="text" v-model="ls.Quantity" class="input" return-key-type="next"  @input="input(ls)"/>
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
    export default {
        components: {gridselect},
        data(){
            return{
                isPDA:false,
                goods:{},
                retailsales:99.00,
                unitprice:80.00,
                discountrate:8.0,
                imgurl:'root:img/logo.png',
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
                    {'GoodsID':'00AQ','ColorID':'0BA','SizeID':'0A1','Size':'35','x':'x_1','Quantity':'','Amount':'100'},
                    {'GoodsID':'00AQ','ColorID':'0BA','SizeID':'0A2','Size':'36','x':'x_2','Quantity':'','Amount':'100'},
                    {'GoodsID':'00AQ','ColorID':'0BA','SizeID':'0A3','Size':'37','x':'x_3','Quantity':'','Amount':'100'},
                    {'GoodsID':'00AQ','ColorID':'0BB','SizeID':'0A1','Size':'38','x':'x_1','Quantity':'','Amount':'100'},
                    {'GoodsID':'00AQ','ColorID':'0BB','SizeID':'0A2','Size':'39','x':'x_2','Quantity':'','Amount':'100'},
                    {'GoodsID':'00AQ','ColorID':'0BB','SizeID':'0A3','Size':'40','x':'x_3','Quantity':'','Amount':'100'},
                    {'GoodsID':'00AQ','ColorID':'0BC','SizeID':'0A1','Size':'41','x':'x_1','Quantity':'','Amount':'100'},
                    {'GoodsID':'00AQ','ColorID':'0BC','SizeID':'0A2','Size':'42','x':'x_2','Quantity':'','Amount':'100'},
                    {'GoodsID':'00AQ','ColorID':'0BC','SizeID':'0A3','Size':'43','x':'x_3','Quantity':'','Amount':'100'}
                 ],
                testData3: [
                    {   'GoodsID':'00AQ',
                        'ColorID':'0BA',
                        'title': '红色',
                        'checked': true,
                        'tipqty':''
                    },
                    {   'GoodsID':'00AQ',
                        'ColorID':'0BB',
                        'title': '蓝色',
                        'tipqty':''
                    },
                    {  'GoodsID':'00AQ',
                        'ColorID':'0BC',
                        'title': '白色',
                        'tipqty':''
                    },
                    {
                        'title': '合肥'
                    },
                    {
                        'title': '武汉'
                    },
                    {
                        'title': '长沙'
                    },
                    {
                        'title': '南昌'
                    },
                    {
                        'title': '太原'
                    },
                    {
                        'title': '西安'
                    },
                    {
                        'title': '郑州'
                    },
                    {
                        'title': '福州'
                    }



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
             param.GoodsID=p.GoodsID
             param.DeptID='007'
             param.onLineId='0000-0000'
             param.userId=1
             let  _this=this

                modal.toast({
                    message: 'GoodsID的值：'+ param.GoodsID,
                    duration: 0.3
                })

               // _this.colorid='0BA'
               net.post(pref.getString('ip')+'/common.do?getColorAndSize', param,{},function () {

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
                  _this.colorid=(_this.testData3.filter(item=>item.checked))[0].ColorID
                  //_this.alert('filter:'+JSON.stringify(_this.testData3.filter(item=>item.checked)))
                 // _this.alert( 'colorid:'+_this.colorid)
                }

                },function (res){//compelete

                    },function (res){//exception
                })



            },jian(ls){
                ls.Quantity =Number(ls.Quantity)-1
                this.total()
            },add(ls){
                ls.Quantity =Number(ls.Quantity)+1
                this.total()
            },submit(){
                var p={}
                p.sizelist=this.sizelist.filter(item=>Number(item.Quantity)!==0)
              if(this.isPDA) {
                  pref._SendN(this.sizelist.filter(item => Number(item.Quantity) !== 0))
              }else {
                  nav.backFull(p, false)
              }
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
                    : '否'} \n 选中的id: ${checkedList.map(item=>item.ColorID)} \n选中列表：${checkedList.map(item => item.title).join(',')}`);


               // _this.alert('inputlist:'+JSON.stringify(_this.inputlist))
                _this.colorid=checkedList.map(item=>item.ColorID).toString()
                _this.selectIndex=selectIndex
                _this.checkedList=checkedList
               // checkedList.map(item => item.tipqty=8) //这种方式可以更新
              //  _this.alert('checkedList:'+JSON.stringify(_this.$refs.glist.dList))

                let dList=_this.$refs.glist.dList.filter(item => item.checked===false)
                for(let i=0;i<dList.length;i++){
                    let map=dList[i]
                     map.tipqty=_this.tipqtytotal(map.GoodsID,map.ColorID)
                    if(map.tipqty>99){
                        map.tipqty='99+'
                    }
                }



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
                    return map.ColorID=== colorid
                })
            },
            tipqtytotal(GoodsID,ColorID){
               let _this=this
                _this.qtysum=Number(0)
               for(let i=0;i<_this.sizelist.length;i++){
                   if(_this.sizelist[i].GoodsID===GoodsID &&_this.sizelist[i].ColorID ===ColorID)
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
        color:#FFFFFF;
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
          color: #FFF;
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