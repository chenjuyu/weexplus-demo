<template>
    <div class="wrapper">
        <head :rightText="rightText" title="货品资料详情"  @rightClick="rightClick"></head>

        <scroller class="scroller" >

        <div class="lightbox">
            <slider class="slider" interval="3000" auto-play="true" offset-x-accuracy="0.9" @scroll="scrollHandler" @change="changeHandler" infinite="true">
                <div class="slider-pages" v-for="item in itemList">
                    <image class="img" :src="item.pictureUrl" @click="open"></image>
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
                     <text class="res">{{res3}}</text>  :style="{'height':getScreenHeight()}" -->
        </div>
        <div class="detail">
            <wxccell title="货品编码"
                      :has-arrow="false"
                      :titlestyle="{color:'red','font-size':'35px'}"

                      :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Code"  placeholder="输入货号"/>
            </wxccell>
            <wxccell title="货品名称"
                     :has-arrow="false"
                     :titlestyle="{color:'red','font-size':'35px'}"

                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Name"  placeholder="输入名称"/>
            </wxccell>
            <wxccell title="货品类别"
                     :has-arrow="true"
                     :titlestyle="{color:'red','font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked(3)"
                     :has-top-border="false">
                <text style="font-size:35px">{{goods.GoodsType}}</text>
            </wxccell>
            <wxccell title="货品子类别"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.SubType"  placeholder="输入货品子类别"/>
            </wxccell>
            <wxccell title="厂商"
                     :has-arrow="true"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked(11)"
                     :has-top-border="false">
                <text style="font-size:30px">{{goods.Supplier}}</text>
            </wxccell>
            <wxccell title="收货部门"
                     :has-arrow="true"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked(15)"
                     :has-top-border="false">
                <text style="font-size:35px">{{goods.Department}}</text>
            </wxccell>
            <wxccell title="型号规格"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Model"  placeholder="型号规格"/>
            </wxccell>
            <wxccell title="材料成分1"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Model1"  placeholder=""/>
            </wxccell>
            <wxccell title="材料成分2"
                         :has-arrow="false"
                         :titlestyle="{'font-size':'35px'}"

                         :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Model2"  placeholder=""/>
            </wxccell>
            <wxccell title="厂商货品编码"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.SupplierCode"  placeholder=""/>
            </wxccell>
            <wxccell title="年份"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Age"  placeholder=""/>
            </wxccell>
            <wxccell title="季节"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="goods.Season"  placeholder=""/>
            </wxccell>

            <wxccell title="品牌"
                     :has-arrow="true"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked(44)"
                     :has-top-border="false">
                <text style="font-size:35px">{{goods.Brand}}</text>
            </wxccell>
            <wxccell title="参考进价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="goods.PurchasePrice"  placeholder=""/>
            </wxccell>
            <wxccell title="零售价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="goods.RetailSales"  placeholder=""/>
            </wxccell>

            <wxccell title="零售价2"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="goods.RetailSales1"  placeholder=""/>
            </wxccell>

            <wxccell title="批发价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"

                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" auto-appear="false" v-model="goods.TradePrice"  placeholder=""/>
            </wxccell>
        </div>

        </scroller>

        <div class="footer">
           <div class="btn" @click="save"><text style="font-size: 40px;color: white">保存</text></div>
        </div>
    </div>
</template>

<script>
    import gridselect from './component/wxc-grid-select.vue'
    import wxccell from './component/wxc-cell.vue'
    var nav = weex.requireModule('navigator') ;
    const net = weex.requireModule('net');
    const  pref=weex.requireModule('pref');
    const progress = weex.requireModule('progress');
    var url='/goodsInfo.do?goodsDetail'
    export default {
        components:{gridselect,wxccell},
        data(){
         return{
             rightText:'\ue604',
             editflag:false,//为真时，这个是修改货品，其他为新增
             GoodsID:'',
             Code:'',
             Name:'',
             GoodsType:'',
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
                 {itemId: '520421163634', title: 'item1', pictureUrl: 'https://gd2.alicdn.com/bao/uploaded/i2/T14H1LFwBcXXXXXXXX_!!0-item_pic.jpg'},
                 {itemId: '522076777462', title: 'item2', pictureUrl: 'https://gd1.alicdn.com/bao/uploaded/i1/TB1PXJCJFXXXXciXFXXXXXXXXXX_!!0-item_pic.jpg'},
                 {itemId: '522076777462', title: 'item3', pictureUrl: 'https://gd3.alicdn.com/bao/uploaded/i3/TB1x6hYLXXXXXazXVXXXXXXXXXX_!!0-item_pic.jpg'},
                 {itemId: '522076777467', title: 'item4', pictureUrl: 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491837948&di=3dcecd1b1d709196873a91f9fd585962&imgtype=jpg&er=1&src=http%3A%2F%2Fphotocdn.sohu.com%2F20160304%2Fmp61863731_1457078539188_3.gif'}
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
            onLoad(p){
                var that=this
                this.alert(JSON.stringify(p))
                if(p !=undefined && p!=null && JSON.stringify(p)!='{}') {
                    this.GoodsID = p.GoodsID
                    this.editflag=p.editflag || false

                    net.post(pref.getString('ip')+url,{GoodsID:this.GoodsID},{},function(){
                        //start
                    },function(e){
                        //success
                        if(e !=undefined && e !=null && JSON.stringify(e) !='{}' ) {
                            //that.alert(JSON.stringify(e))
                            that.colorData =  e.res.attributes.datalist || []
                            that.goods =e.res.attributes.goods || {}
                           // that.goods.DepartmentID =''
                          //  that.goods.Department=''
                        }


                    },function(e){
                        //compelete

                    },function(){
                        //exception
                    });


                }
            },wxcCellClicked(id){
                var that=this
                var submitmap={}
                if(id==3){//货品类别  只有新增才可以修改
                    if(this.editflag){
                        that.toast('修改状态下不能修改货品类别')
                        return
                    }
                    submitmap.send ='getGoodsType'
                }else if(id==11){ //厂商
                    submitmap.send= 'getGoodsSupplier'

                }
                else if(id==15){//收货部门
                    submitmap.send= 'getWarehouse'

                }else if(id==44){//品牌
                    submitmap.send= 'getBrand'

                }
                submitmap.condition=''
                submitmap.Type=''
                submitmap.customerid=''
                submitmap.mult=false
                nav.pushFull({url: 'root:base.js',param:submitmap,animate:true,isPortrait:true},
                (res)=>{
                    this.alert(JSON.stringify(res))
                    if(res == undefined || res==null || JSON.stringify(res)=='{}'){
                        return
                    }
                  if(id==3){
                      that.goods.GoodsTypeID=res.id
                      that.goods.GoodsType=res.Name
                  }else if(id==11){
                    //  this.goods.SupplierID=res.id  这种的，返回固定列是可以的，值有，页面不刷新 坑，但如果有其他的就不行，就要用如下方法更新
                     // this.goods.Supplier=res.Name
                      Vue.set(this.goods, 'SupplierID', res.id)
                      Vue.set(this.goods, 'Supplier', res.Name)
                      //this.alert('goods.Supplier的值:'+this.goods.Supplier)
                  }else if(id==15){
                //      this.goods.DepartmentID=res.id
                    //  this.goods.Department=res.Name
                     Vue.set(this.goods, 'DepartmentID', res.id)
                     Vue.set(this.goods, 'Department', res.Name)

                      that.alert('departmentID的值：'+that.goods.DepartmentID)
                  }else if(id==44){
                     // that.goods.BrandID=res.id
                     // that.goods.Brand=res.Name
                      Vue.set(this.goods, 'BrandID', res.id)
                      Vue.set(this.goods, 'Brand', res.Name)
                  }

                });
            },
            open(){
                var self=this;
                if(self.goods.Code ==''){
                    self.toast('货品编码为空不能使用拍照功能')
                    return
                }

                const photo = weex.requireModule('photo');
                photo.openCamera(500,800,'#000000',function(e){
                    self.src=e.path;
                    var param={};
                    var header={};
                    var path={};

                    var SalesID =self.goods.Code //以货品编码命名图片名字

                    path.file=e.path;
                    var net=weex.requireModule("net"); //&SalesID='+SalesID
                    net.postFile(pref.getString('ip')+'/common.do?uploadImages&SalesID='+SalesID,param,header,path,()=>{
                        //start
                    },(e)=>{
                        //succcess
                        var modal=weex.requireModule("modal")

                        modal.toast({message:'上传成功！url:'+e.res.obj})
                    },()=>{
                        //compelete

                    },()=>{
                        //exception
                        var modal=weex.requireModule("modal")
                        modal.toast({message:'上传异常！'})
                    })
                });
            },
            scrollHandler: function(e) {
                this.scrollHnadlerCallCount = this.scrollHnadlerCallCount + 1;
                this.offsetXRatio = e.offsetXRatio;
            },
            changeHandler: function(e) {
                this.currIdx = e.index;
            }, onSelect (res,{selectIndex, checked, checkedList }) {
                let _this = this
                Vue.set(this, res, `本次选择的index：${selectIndex}\n是否选中：${checked
                    ? '是'
                    : '否'} \n 选中的id: ${checkedList.map(item => item.ColorID)} \n选中列表：${checkedList.map(item => item.title).join(',')}`);
                if(this.colorData[selectIndex].title =='增加'){
                    //this.alert('这是一个跳转页面的按扭')
                    nav.pushFull({url:'root:basecheck.js',param:{send:'getGoodsColor',editflag:true}},(res)=>{
                        this.log('res的返回值：'+JSON.stringify(res))
                        if(res == undefined || res==null || JSON.stringify(res)=='{}'){
                            return
                        }
                        for(var k=0;k<res.item.length;k++){
                            var map=_this.checkcolor(res.item[k])
                            if(map==undefined){ //代表无
                                _this.colorData.unshift(res.item[k])
                            }

                        }
                    })
                }
            },checkcolor(map){ //检查颜色列表，是否已有colorid
                for(var i=0;i<this.colorData.length;i++){
                    if(map.ColorID==this.colorData[i].ColorID){
                        return this.colorData[i]
                    }
                }
                return undefined
            },
            del(index){
                this.alert(index)
            },rightClick(){

            },save(){ //保存货品资料
                var param={}
                var saveurl=''
                if(this.goods.Code=='' || this.goods.Name =='' || this.goods.GoodsTypeID==''){
                    this.toast('请填写必填属性后，再提交')
                    return
                }
                if(this.colorData.length >8){
                    this.alert('颜色最多选8个，多的不做保存')
                   // return
                }

                if(this.goods.GoodsID ==''){//为新增
                    param.goodsCode=this.goods.Code
                    param.goodsName=this.goods.Name
                    param.goodsTypeId=this.goods.GoodsTypeID
                    saveurl='/goodsInfo.do?saveGoodsInfo'
                }else{//修改
                    param.goodsId=this.goods.GoodsID
                    saveurl='/goodsInfo.do?updateGoodsInfo'
                }
                param.goodsSubType =this.goods.SubType
                param.brandId =this.goods.BrandID
                param.brandSerialId=this.goods.BrandSerialID
                param.kind=this.goods.Kind
                param.age=this.goods.Age
                param.season=this.goods.Season
                param.supplierId=this.goods.SupplierID
                param.supplierCode=this.goods.SupplierCode
                param.purchasePrice=this.goods.PurchasePrice
                param.tradePrice=this.goods.TradePrice
                param.retailSales=this.goods.RetailSales
                param.retailSales1=this.goods.RetailSales1
                param.retailSales2=this.goods.RetailSales2
                param.retailSales3=this.goods.RetailSales3
                param.salesPrice1 =this.goods.SalesPrice1
                param.salesPrice2 =this.goods.SalesPrice2
                param.salesPrice3 =this.goods.SalesPrice3
                 //货品颜色
                if(this.colorData.length>0) {
                    param.colorId1 = this.colorData[0].ColorID
                   if(this.colorData.length>1)
                    param.colorId2 = this.colorData[1].ColorID
                    if(this.colorData.length>2)
                    param.colorId3 = this.colorData[2].ColorID
                    if(this.colorData.length>3)
                    param.colorId4 = this.colorData[3].ColorID
                    if(this.colorData.length>4)
                    param.colorId5 = this.colorData[4].ColorID
                    if(this.colorData.length>5)
                    param.colorId6 = this.colorData[5].ColorID
                    if(this.colorData.length>6)
                     param.colorId7 = this.colorData[6].ColorID
                    if(this.colorData.length>7)
                    param.colorId8 = this.colorData[7].ColorID
                }
                var that=this
                net.post(pref.getString('ip')+saveurl,param,{},function(){
                    //start

                    progress.showFull('正在保存',false)
                },function(e){
                    //success
                    that.toast('保存成功')
                    progress.dismiss()
                    for(let k of Object.keys(param)) { //成功返回后清空 提交对象
                        Vue.delete(param, k);
                    }
                        if(that.goods.GoodsID=='') { //新增
                        param.GoodsID = e.res.obj || ''
                        param.Code =that.goods.Code
                        param.Name =that.goods.Name
                        param.GoodsType=that.goods.GoodsType
                        param.SubType =that.goods.SubType
                        param.Season =that.goods.Season
                        param.Age=that.goods.Age
                        param.Supplier=that.goods.Supplier
                        param.Brand=that.goods.Brand
                        param.RetailSales=that.goods.RetailSales
                        param.Quantity=''
                        param.Amount=''
                            nav.backFull(param,true)
                    }else{
                        nav.backFull(that.goods,true)
                    }

                },function(e){
                    //compelete

                },function(){
                    //exception
                    that.toast('保存失败')
                    progress.dismiss()
                })


            }
            }
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
}
.btn{
    background-color: orange;
    width: 500px;
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
</style>