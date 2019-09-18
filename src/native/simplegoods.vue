<template>
    <div class="wrapper">
        <head :rightText="rightText" title="货品资料详情"  @rightClick="rightClick"></head>
       <list class="scroller">
        <cell>
        <div class="lightbox">
            <slider class="slider" interval="3000" auto-play="true" offset-x-accuracy="0.9" @scroll="scrollHandler" @change="changeHandler" infinite="true">
                <div class="slider-pages" v-for="item in itemList">
                    <image class="img" :src="item.pictureUrl?item.pictureUrl:'root:img/nopic.jpg'" @click="open"></image>
                    <text class="title">{{item.title}}</text>
                </div>
                <indicator class="indicator"></indicator>
            </slider>
        </div>
        <div class="demo">

                <gridselect ref="glist"
                            :single="false"
                            :cols="4"
                            :list="colorData"
                            @del ="params=>del(params)"
                            @select="params => onSelect('res3', params)">
                </gridselect>
                <!-- :customStyles="customStyles" 自定义
                     <text class="res">{{res3}}</text>  :style="{'height':getScreenHeight()}"   -->
        </div>
        <div class="detail">
            <wxccell title="货品编码"
                      :has-arrow="false"
                      :titlestyle="{color:'red','font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                      :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Code"  placeholder="输入货号"/>
            </wxccell>
            <wxccell title="货品名称"
                     :has-arrow="false"
                     :titlestyle="{color:'red','font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Name"  placeholder="输入名称"/>
            </wxccell>
            <wxccell title="货品类别"
                     :has-arrow="true"
                     :cellStyle="{height:'80px'}"
                     :titlestyle="{color:'red','font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked(3)"
                     :has-top-border="false">
                <text style="font-size:35px">{{goods.GoodsType}}</text>
            </wxccell>
            <wxccell title="货品子类别"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.SubType"  placeholder="输入货品子类别"/>
            </wxccell>
            <wxccell title="厂商"
                     :has-arrow="true"
                     :cellStyle="{height:'80px'}"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked(11)"
                     :has-top-border="false">
                <text style="font-size:30px">{{goods.Supplier}}</text>
            </wxccell>
            <wxccell title="收货部门"
                     :has-arrow="true"
                     :cellStyle="{height:'80px'}"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked(15)"
                     :has-top-border="false">
                <text style="font-size:35px">{{goods.Department}}</text>
            </wxccell>
            <wxccell title="型号规格"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Model"  placeholder="型号规格"/>
            </wxccell>
            <wxccell title="材料成分1"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Model1"  placeholder=""/>
            </wxccell>
            <wxccell title="材料成分2"
                         :has-arrow="false"
                         :titlestyle="{'font-size':'35px'}"
                         :cellStyle="{height:'80px'}"
                         :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Model2"  placeholder=""/>
            </wxccell>
            <wxccell title="厂商货品编码"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.SupplierCode"  placeholder=""/>
            </wxccell>
            <wxccell title="年份"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Age"  placeholder=""/>
            </wxccell>
            <wxccell title="季节"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Season"  placeholder=""/>
            </wxccell>

            <wxccell title="品牌"
                     :has-arrow="true"
                     :cellStyle="{height:'80px'}"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked(44)"
                     :has-top-border="false">
                <text style="font-size:35px">{{goods.Brand}}</text>
            </wxccell>
            <wxccell title="参考进价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="goods.PurchasePrice"  placeholder=""/>
            </wxccell>
            <wxccell title="零售价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="goods.RetailSales"  placeholder=""/>
            </wxccell>

            <wxccell title="零售价2"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="goods.RetailSales1"  placeholder=""/>
            </wxccell>

            <wxccell title="批发价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     :cellStyle="{height:'80px'}"
                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" auto-appear="false" v-model="goods.TradePrice"  placeholder=""/>
            </wxccell>
        </div>

        </cell>
        <!-- 录入颜色尺码的数据-->

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
                        <image v-if="ls.img" @click="piczoom(ls.img)" :src="ls.img" style="width: 150px;height: 150px;"></image>
                        <image v-else  src="root:img/home_logo.png" @click="piczoom('root:img/home_logo.png')" style="width: 150px;height: 150px;"></image>

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
        </list>

        <div class="footer">
            <div class="btn" @click="save"><text style="font-size: 40px;color: white">保存</text></div>
            <div class="btn" @click="addcolor"><text style="font-size: 40px;color: white">新建颜色</text></div>
        </div>

        <wxcpopover ref="wxc-popover"
                    :buttons="btns"
                    :position="popoverPosition"
                    :arrowPosition="popoverArrowPosition"
                    @wxcPopoverButtonClicked="popoverButtonClicked"></wxcpopover>

        <wxc-dialog title="增加颜色"
                    :show="coloraddFlag"
                    :single="false"
                    :is-checked="false"
                    :show-no-prompt="false"
                    @wxcDialogCancelBtnClicked="wxcDialogCancelBtnClicked"
                    @wxcDialogConfirmBtnClicked="wxcDialogConfirmBtnClicked"
                    @wxcDialogNoPromptClicked="wxcDialogNoPromptClicked">

            <div slot="content" style="align-items: flex-start;line-height: 150px" >
                <div style="flex-direction: row;height: 80px;align-items: center"><text style="font-size: 35px">颜色编码:</text> <input type="text" class="input" style="width:300px;" v-model="Color.No" /></div>
                <div style="flex-direction: row;height: 80px;align-items: center"><text style="font-size: 35px">颜色名称:</text><input type="text" class="input" style="width:300px;" v-model="Color.Color" /></div>
            </div>
        </wxc-dialog>

    </div>
</template>

<script>
    import gridselect from './component/wxc-grid-select.vue'
    import wxccell from './component/wxc-cell.vue'
    import wxcpopover from './component/wxc-popover.vue';
    import { WxcDialog,Utils } from 'weex-ui';
    import Binding from "weex-bindingx/lib/index.weex.js";
    const animation = weex.requireModule("animation");
    var nav = weex.requireModule('navigator') ;
    const net = weex.requireModule('net');
    const  pref=weex.requireModule('pref');
    const progress = weex.requireModule('progress');

   const staticData = weex.requireModule("static")

    var url='/goodsInfo.do?goodsDetail'
    export default {
        components:{gridselect,wxccell,wxcpopover,WxcDialog},
        props: {
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
        },
        data(){
         return{
             popoverPosition:{x:-14,y:110}
             ,popoverArrowPosition:{pos:'top',x:-14}
             ,btns:[{
                 icon: '\ue61f',
                 text:'录入单据',
                 key:'add'
             }],
             rightText:'\ue604',
             detaillist:[],
             mobileX: 0,
             webStarX: 0,
             saveIdx: null,
             isAndroid: Utils.env.isAndroid(),
             editflag:false,//为真时，这个是修改货品，其他为新增
             Color:{ColorID:'',No:'',Color:''},//新增颜色暂存字段
             coloraddFlag:false,
             goods:{
                 GoodsID:'',
                 Code:'',
                 Name:'',
                 GoodsTypeID:'',
                 GoodsType:'',
                 SubType:'',
                 Age:'',
                 Season:'',
                 BrandID:'',
                 Brand:'',
                 SupplierCode:'',
                 Model:'',
                 Model1:'',
                 Model2:'',
                 PurchasePrice:'',
                 RetailSales:'',
                 RetailSales1:'',
                 RetailSales2:'',
                 TradePrice:'',
                 DepartmentID:'',
                 Department:'',
                 Supplier :'',
                 SupplierID:''
             },
             scrollHnadlerCallCount:0,
             offsetXRatio: 0,
             currIdx: 0,
             itemList: [
                 {itemId: '', title: '', pictureUrl:'' //'https://gd2.alicdn.com/bao/uploaded/i2/T14H1LFwBcXXXXXXXX_!!0-item_pic.jpg'
                 }
               //  {itemId: '522076777462', title: 'item2', pictureUrl: 'https://gd1.alicdn.com/bao/uploaded/i1/TB1PXJCJFXXXXciXFXXXXXXXXXX_!!0-item_pic.jpg'},
               //  {itemId: '522076777462', title: 'item3', pictureUrl: 'https://gd3.alicdn.com/bao/uploaded/i3/TB1x6hYLXXXXXazXVXXXXXXXXXX_!!0-item_pic.jpg'},
               //  {itemId: '522076777467', title: 'item4', pictureUrl: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491837948&di=3dcecd1b1d709196873a91f9fd585962&imgtype=jpg&er=1&src=http%3A%2F%2Fphotocdn.sohu.com%2F20160304%2Fmp61863731_1457078539188_3.gif'}
             ],
             colorData:[
                /* {   'GoodsID':'00AQ',
                     'ColorID':'0BA',
                     'title': '红色',
                     'checked': true,
                     'tipqty':''
                 },
                 {   'GoodsID':'00AQ',
                     'ColorID':'0BB',
                     'title': '蓝色',
                     'checked': true,
                     'tipqty':''
                 },
                 {  'GoodsID':'00AQ',
                     'ColorID':'0BC',
                     'title': '白色',
                     'checked': true,
                     'tipqty':''
                 },
                 {  'GoodsID':'00AQ',
                     'ColorID':'0BC',
                     'title': '黑色',
                     'checked': true,
                     'tipqty':''
                 },
                 {  'GoodsID':'00AQ',
                     'ColorID':'0BC',
                     'title': '澄色',
                     'checked': true,
                     'tipqty':''
                 },
                 {  'GoodsID':'00AQ',
                     'ColorID':'0BC',
                     'title': '之色',
                     'checked': true,
                     'tipqty':''
                 }, */
                 {GoodsID:'',ColorID:'',title:'增加',tipqty:''}
             ]
         }
        } , methods : {
            onLoad(p) {
                var that = this
               // this.alert(JSON.stringify(p))
                if (p != undefined && p != null && JSON.stringify(p) != '{}') {
                    this.GoodsID = p.GoodsID
                    this.editflag = p.editflag || false

                    net.post(pref.getString('ip') + url, {GoodsID: this.GoodsID}, {}, function () {
                        //start
                    }, function (e) {
                        //success
                        if (e != undefined && e != null && JSON.stringify(e) != '{}') {
                          //  that.alert(JSON.stringify(e))
                            that.colorData = e.res.attributes.datalist || []
                            that.goods = e.res.attributes.goods || {}
                            that.detaillist = p.displaylist || []
                            // that.goods.DepartmentID =''
                            //  that.goods.Department=''
                        }


                    }, function (e) {
                        //compelete

                    }, function () {
                        //exception
                    });


                }
            }, wxcCellClicked(id) {
                var that = this
                var submitmap = {}
                if (id == 3) {//货品类别  只有新增才可以修改
                    if (this.editflag) {
                        that.toast('修改状态下不能修改货品类别')
                        return
                    }
                    submitmap.send = 'getGoodsType'
                } else if (id == 11) { //厂商
                    submitmap.send = 'getGoodsSupplier'

                } else if (id == 15) {//收货部门
                    submitmap.send = 'getWarehouse'

                } else if (id == 44) {//品牌
                    submitmap.send = 'getBrand'

                }
                submitmap.condition = ''
                submitmap.Type = ''
                submitmap.customerid = ''
                submitmap.mult = false
                nav.pushFull({url: 'root:base.js', param: submitmap, animate: true, isPortrait: true},
                    (res) => {
                      //  this.alert(JSON.stringify(res))
                        if (res == undefined || res == null || JSON.stringify(res) == '{}') {
                            return
                        }
                        if (id == 3) {
                            that.goods.GoodsTypeID = res.id
                            that.goods.GoodsType = res.Name
                        } else if (id == 11) {
                            //  this.goods.SupplierID=res.id  这种的，返回固定列是可以的，值有，页面不刷新 坑，但如果有其他的就不行，就要用如下方法更新
                            // this.goods.Supplier=res.Name
                            Vue.set(this.goods, 'SupplierID', res.id)
                            Vue.set(this.goods, 'Supplier', res.Name)
                            //this.alert('goods.Supplier的值:'+this.goods.Supplier)
                        } else if (id == 15) {
                            //      this.goods.DepartmentID=res.id
                            //  this.goods.Department=res.Name
                            Vue.set(this.goods, 'DepartmentID', res.id)
                            Vue.set(this.goods, 'Department', res.Name)

                            that.alert('departmentID的值：' + that.goods.DepartmentID)
                        } else if (id == 44) {
                            // that.goods.BrandID=res.id
                            // that.goods.Brand=res.Name
                            Vue.set(this.goods, 'BrandID', res.id)
                            Vue.set(this.goods, 'Brand', res.Name)
                        }

                    });
            },
            open() {
                var self = this;
                if (self.goods.Code == '') {
                    self.toast('货品编码为空不能使用拍照功能')
                    return
                }

                const photo = weex.requireModule('photoplus');//photo 这个模块是有裁剪功能的
                //type:   video,photo
                //action:  camera 相机 ；choose:相册
                photo.open({action:'camera'}, function (res) {
                    self.src = res.res[0].path;
                    var param = {};
                    var header = {};
                    var path = {};

                    var SalesID = self.goods.Code //以货品编码命名图片名字

                    path.file = res.res[0].path;
                    var net = weex.requireModule("net"); //&SalesID='+SalesID
                    net.postFile(pref.getString('ip') + '/common.do?uploadImages&SalesID=' + SalesID, param, header, path, () => {
                        //start
                        progress.showFull('正在上传',false)
                    }, (e) => {
                        //succcess

                        var modal = weex.requireModule("modal")

                       // modal.toast({message: '上传成功！url:' + e.res.obj})
                        if(e.res.success) {
                            self.itemList[0].pictureUrl = e.res.obj
                        }else{
                            modal.toast({message: e.res.msg})
                        }
                    }, () => {
                        //compelete
                        progress.dismiss()
                    }, () => {
                        //exception
                        progress.dismiss()
                        var modal = weex.requireModule("modal")
                        modal.toast({message: '上传异常！'})
                    })
                });
            },
            scrollHandler: function (e) {
                this.scrollHnadlerCallCount = this.scrollHnadlerCallCount + 1;
                this.offsetXRatio = e.offsetXRatio;
            },
            changeHandler: function (e) {
                this.currIdx = e.index;
            }, onSelect(res, {selectIndex, checked, checkedList}) {
                let _this = this
                Vue.set(this, res, `本次选择的index：${selectIndex}\n是否选中：${checked
                    ? '是'
                    : '否'} \n 选中的id: ${checkedList.map(item => item.ColorID)} \n选中列表：${checkedList.map(item => item.title).join(',')}`);
                if (this.colorData[selectIndex].title == '增加') {
                    //this.alert('这是一个跳转页面的按扭')
                    nav.pushFull({url: 'root:basecheck.js', param: {send: 'getGoodsColor', editflag: true}}, (res) => {
                        this.log('res的返回值：' + JSON.stringify(res))
                        if (res == undefined || res == null || JSON.stringify(res) == '{}') {
                            return
                        }
                        for (var k = 0; k < res.item.length; k++) {
                            var map = _this.checkcolor(res.item[k])
                            if (map == undefined) { //代表无
                                _this.colorData.unshift(res.item[k])
                            }

                        }
                    })
                }
            }, checkcolor(map) { //检查颜色列表，是否已有colorid
                for (var i = 0; i < this.colorData.length; i++) {
                    if (map.ColorID == this.colorData[i].ColorID) {
                        return this.colorData[i]
                    }
                }
                return undefined
            },
            del(index) {
               // this.alert(index)
            }, rightClick() {
                this.$refs['wxc-popover'].wxcPopoverShow();
            }, save() { //保存货品资料
                var param = {}
                var saveurl = ''
                if (this.goods.Code == '' || this.goods.Name == '' || this.goods.GoodsTypeID == '') {
                    this.toast('请填写必填属性后，再提交')
                    return
                }
                if (this.colorData.length > 8) {
                    this.alert('颜色最多选8个，多的不做保存')
                    // return
                }

                if (this.goods.GoodsID == '') {//为新增
                    param.goodsCode = this.goods.Code
                    param.goodsName = this.goods.Name
                    param.goodsTypeId = this.goods.GoodsTypeID
                    saveurl = '/goodsInfo.do?saveGoodsInfo'
                } else {//修改
                    param.goodsId = this.goods.GoodsID
                    saveurl = '/goodsInfo.do?updateGoodsInfo'
                }
                param.goodsSubType = this.goods.SubType
                param.brandId = this.goods.BrandID
                param.brandSerialId = this.goods.BrandSerialID
                param.kind = this.goods.Kind
                param.age = this.goods.Age
                param.season = this.goods.Season
                param.supplierId = this.goods.SupplierID
                param.supplierCode = this.goods.SupplierCode
                param.purchasePrice = this.goods.PurchasePrice
                param.tradePrice = this.goods.TradePrice
                param.retailSales = this.goods.RetailSales
                param.retailSales1 = this.goods.RetailSales1
                param.retailSales2 = this.goods.RetailSales2
                param.retailSales3 = this.goods.RetailSales3
                param.salesPrice1 = this.goods.SalesPrice1
                param.salesPrice2 = this.goods.SalesPrice2
                param.salesPrice3 = this.goods.SalesPrice3
                //货品颜色
                var p = {}, tmp = {},p1={}
                var colorarray = this.colorData.filter(map => map.ColorID)
                this.log('colorarray:'+JSON.stringify(colorarray))
                if (colorarray.length > 0) {
                    param.colorId1 = colorarray[0].ColorID
                }else{
                    param.colorId1=''
                }
                    if (colorarray.length > 1) {
                        param.colorId2 = colorarray[1].ColorID
                    }else {
                        param.colorId2=''
                    }
                    if (colorarray.length > 2) {
                        param.colorId3 = colorarray[2].ColorID
                    }else{
                        param.colorId3=''
                    }
                    if (colorarray.length > 3) {
                        param.colorId4 = colorarray[3].ColorID
                    }else{
                        param.colorId4=''
                    }
                    if (colorarray.length > 4) {
                        param.colorId5 = colorarray[4].ColorID
                    }else{
                        param.colorId5=''
                    }
                    if (colorarray.length > 5) {
                        param.colorId6 = colorarray[5].ColorID
                    }else{
                        param.colorId6=''
                    }
                    if (colorarray.length > 6) {
                        param.colorId7 = colorarray[6].ColorID
                    }else {
                        param.colorId7=''
                    }
                    if (colorarray.length > 7) {
                        param.colorId8 = colorarray[7].ColorID
                    }else{
                        param.colorId8=''
                    }

                var that = this

                net.post(pref.getString('ip') + saveurl, param, {}, function () {
                    //start

                    progress.showFull('正在保存', false)
                }, function (e) {
                    //success
                    that.toast('保存成功')
                    progress.dismiss()
                    for (let k of Object.keys(param)) { //成功返回后清空 提交对象
                        Vue.delete(param, k);
                    }
                    if (that.goods.GoodsID == '') { //新增
                        that.goods.GoodsID = e.res.obj
                        tmp.GoodsID = e.res.obj || ''
                        tmp.Code = that.goods.Code
                        tmp.Name = that.goods.Name
                        tmp.GoodsType = that.goods.GoodsType
                        tmp.SubType = that.goods.SubType
                        tmp.Season = that.goods.Season
                        tmp.Age = that.goods.Age
                        tmp.Supplier = that.goods.Supplier
                        tmp.Brand = that.goods.Brand
                        tmp.RetailSales = that.goods.RetailSales
                        tmp.Quantity = ''
                        tmp.Amount = ''
                        tmp.right=[
                            {text: "删除"},{text: "审核",style: { backgroundColor: "#F4333C", color: "white" }},{text: "反审",style: { backgroundColor: "orange", color: "white" }}
                        ]
                        param.goods = tmp
                        if (that.detaillist.length > 0) {
                            //    that.log('detaillist原始值:'+JSON.stringify(that.detaillist))

                            p.detaillist = that.detaillist ||[]
                            staticData.set('detaillist', p)

                            //  that.log('详情页的detaillist:'+SON.stringify(staticData.get('detaillist')))
                            // that.alert('详情页的detaillist:'+JSON.stringify(staticData.get('detaillist')))
                            param.detaillist = that.detaillist || []
                        }
                        p1.GoodsID =e.res.obj
                        p1.editflag =false
                        p1.displaylist=[]
                        that.onLoad(p1) //重新拉取数据
                        that.log('第一次aa')
                        //nav.backFull(param, true)  新增后可以增加单据数据 ，先不返回货品列表
                    } else {
                        that.log('第二次bb')
                        if (that.detaillist.length > 0) {
                            //  that.log('detaillist原始值:'+JSON.stringify(that.detaillist))
                            p.detaillist = that.detaillist || []
                            staticData.set('detaillist', p)
                            //   that.log('详情页的detaillist:'+JSON.stringify(staticData.get('detaillist')))
                            //   that.alert('详情页的detaillist:'+JSON.stringify(staticData.get('detaillist')))
                            param.detaillist = that.detaillist || []
                        }
                        param.goods = that.goods
                        nav.backFull(param, true)
                    }

                }, function (e) {
                    //compelete

                }, function () {
                    //exception
                    that.toast('保存失败')
                    progress.dismiss()
                })


            },colorchecked(arr){

                for(var i=0;i<arr.length;i++){
                    if(arr[i].hasOwnProperty('checked')){
                        return true
                    }
                }
                return false
            }
            ,popoverButtonClicked(obj) {
                //  modal.toast({ 'message': `key:${obj.key}, index:${obj.index}`, 'duration': 1 });
                this.log('进入点击了:' + JSON.stringify(obj))
                var that = this
                if (obj.key == 'add') {
                    if (this.goods.GoodsID == '') {
                        this.toast('请先保存货品后，再录入单据')
                        return;
                    }
                    if (this.goods.SupplierID == '' || this.goods.DepartmentID == '') {
                        this.toast('请选择货品资料的厂商或者收货部门，再录入')
                        return
                    }
                    if (this.colorData.length <= 2) { //因为增加空的占了一个
                        this.toast('请选择颜色后，再录入')
                        return
                    }
                    //组装数据
                    var colorlist = []
                    var sizelist = []

                    net.post(pref.getString('ip') + url, {GoodsID: this.goods.GoodsID}, {}, function () {
                        //start
                    }, function (e) {
                        //success
                        if (e != undefined && e != null && JSON.stringify(e) != '{}') {
                            //that.alert(JSON.stringify(e))
                            colorlist = e.res.attributes.datalist || []

                            for (let n = 0; n < colorlist.length; n++) { //先删除掉一个没用的
                                if (colorlist[n].title == '增加') {
                                    colorlist.splice(n, 1)
                                }
                            }
                            if(colorlist.length ==0){
                                that.toast('请先保存颜色后，再增加单据')
                                return
                            }
                            //判断是否有默认选择
                        /*   if(!that.colorchecked(colorlist)){
                               colorlist[0].checked =true
                           } */
                            //再重新加上已填的数据
                            for (let j = 0; j < colorlist.length; j++) {
                                for (let i = 0; i < that.detaillist.length; i++) {

                                    if (colorlist[j].GoodsID == that.detaillist[i].GoodsID && colorlist[j].ColorID == that.detaillist[i].ColorID) {
                                        colorlist[j] = that.detaillist[i]
                                        colorlist[j].title = colorlist[j].Color
                                        colorlist[j].tipqty = that.detaillist[i].Quantity
                                    }
                                }
                            }
                            that.log('colorlist最后的结果：' + JSON.stringify(colorlist))


                            //这里只是单个货品的 尺码sizedata

                            for (let n = 0; n < colorlist.length; n++) { //sizeData

                                for (let m = 0; m < colorlist[n].sizeData.length; m++) {
                                    sizelist.push(colorlist[n].sizeData[m])
                                }

                            }


                            that.log('sizelist最后的结果：' + JSON.stringify(sizelist))

                            nav.pushFull({
                                url: 'root:goodsDetail.js',
                                param: {colorlist: colorlist, sizelist: sizelist}
                            }, (res) => {
                                if (JSON.stringify(res) != '{}') {
                                    if (res != null) {
                                        that.detaillist = res.detaillist
                                        //把厂商与收货部门加进去，后台要 根据这些生成单据的
                                        for (let i = 0; i < that.detaillist.length; i++) {
                                            that.detaillist[i].SupplierID = that.goods.SupplierID
                                            that.detaillist[i].DepartmentID = that.goods.DepartmentID
                                            that.detaillist[i].right = [
                                                {text: "删除", style: {backgroundColor: "#F4333C", color: "white"}}
                                            ]
                                        }
                                        that.log('输出返回的detaillist' + JSON.stringify(that.detaillist));
                                    }

                                }
                            })


                        }


                    }, function (e) {
                        //compelete

                    }, function () {
                        //exception
                        that.alert('异常')
                    });
                }


            }, onRightNode(pNode, node, i) {//向左滑动相关
                // node.onPress();
                //this.alert(node)
                if (pNode.autoClose)
                    this.special(this.$refs.skid[i], {
                        transform: `translate(0, 0)`
                    });

                if(node.text=='删除'){
                  this.detaillist.splice(i,1)
                }


            }, onNodeClick(node, i) {
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
                }else{
                    var obj={}
                    obj.key = 'add'
                    this.popoverButtonClicked(obj)
                }
            },
            onPanEnd(e, node, i) {
                if (Utils.env.isWeb()) {
                    const webEndX = e.changedTouches[0].pageX;
                    this.movingDistance(webEndX - this.webStarX, node, this.$refs.skid[i]);
                }
            },
            onPanStart: function (e, node, i) {
                const {saveIdx} = this;
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
                        const {state, deltaX} = e;
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
            }, special(dom, styles) {
                animation.transition(dom, {
                    styles,
                    duration: 200, //ms
                    timingFunction: "ease",
                    delay: 100 //ms
                });
            },wxcDialogCancelBtnClicked(){
                this.coloraddFlag=false
            },wxcDialogConfirmBtnClicked(){

                if(this.Color.No.trim() ==''){
                    this.toast('颜色编码不能为空')
                    return
                }
                if(this.Color.Color.trim() ==''){
                    this.toast('颜色q名称不能为空')
                    return
                }
               var that =this
                var k={}
                var map={}

                net.post(pref.getString('ip')+'/color.do?coloradd',{No:this.Color.No,Color:this.Color.Color,GoodsID:that.goods.GoodsID},{},function(){
                    //start
                    progress.showFull('提交中',false)
                },function(e){
                    //success self.back=e.res;

                    if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {

                        if(e.res.success){
                            that.Color.ColorID =e.res.obj

                            k={  'GoodsID':that.goods.GoodsID?that.goods.GoodsID:'',
                                'ColorID':that.Color.ColorID ,
                                'title': that.Color.Color ,
                                'checked': true,
                                'tipqty':''
                            }
                            //再添加到货品颜色中，是否保存由客户决定
                             map = that.checkcolor(k)
                            if (map == undefined) { //代表无
                                that.colorData.unshift(k)
                            }

                        }


                      that.toast(e.res.msg)
                    }
                    progress.dismiss()
                },function(e){
                    //compelete

                },function(){
                    //exception
                    progress.dismiss()
                    that.toast('地址或者参数错误')
                });


                this.coloraddFlag=false
            },addcolor(){
                this.coloraddFlag=true
            }


        }//methods 结束


    }
</script>

<style scoped>
.wrapper{
    flex-direction: column;
    position: relative;
}
    .lightbox{
        width: 750px;
        flex-direction: row;
        height: 350px;
    }
.img {
    width: 714px;
    height: 300px;
}
.title {
    position: absolute;
    top: 20px;
    left: 20px;
    color: #ff0000;
    font-size: 48px;
    font-weight: bold;
    background-color: #eeeeee;
}
.slider {
    flex-direction: row;
    margin: 18px;
    width: 714px;
    height: 300px;
}
.slider-pages {
    flex-direction: row;
    width: 714px;
    height: 300px;
}
.indicator {
    width:714px;
    height:30px;
    position:absolute;
    bottom: 1px;
    left:1px;
    item-color: red;
    item-selected-color: blue;
    item-size: 20px;
}
.demo{
    /*height: 200px; */
    background-color: #eeeeee;
}
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
.footer{
   position: absolute;
    bottom:0;
    left:0;
    right:0;
    justify-content: center;
    align-items: center;
    height: 80px;
    flex-direction: row;
}
.btn{
    background-color: orange;
    width: 350px;
    height: 80px;
    justify-content: center;
    align-items: center;
    border-width: 5px;
    border-color: white;
    border-radius: 15px;
}
.scroller{
flex: 1;
margin-bottom: 80px;

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
</style>