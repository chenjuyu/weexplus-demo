<template>
    <div class="wrapper">
        <head :rightText="rightText" title="货品资料详情"  @rightClick="rightClick"></head>

        <scroller class="scroller" >

        <div class="lightbox">
            <slider class="slider" interval="3000" auto-play="true" offset-x-accuracy="0.9" @scroll="scrollHandler" @change="changeHandler" infinite="true">
                <div class="slider-pages" v-for="item in itemList">
                    <image class="img" :src="item.pictureUrl"></image>
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
                      @wxcCellClicked="wxcCellClicked"
                      :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="Code"  placeholder="输入货号"/>
            </wxccell>
            <wxccell title="货品名称"
                     :has-arrow="false"
                     :titlestyle="{color:'red','font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="Name"  placeholder="输入名称"/>
            </wxccell>
            <wxccell title="货品类别"
                     :has-arrow="true"
                     :titlestyle="{color:'red','font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <text style="font-size:35px">{{GoodsType}}</text>
            </wxccell>
            <wxccell title="货品子类别"
                     :has-arrow="true"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <text style="font-size:35px">{{subType}}</text>
            </wxccell>
            <wxccell title="厂商"
                     :has-arrow="true"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <text style="font-size:30px">{{Supplier}}</text>
            </wxccell>
            <wxccell title="收货部门"
                     :has-arrow="true"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <text style="font-size:35px">{{Department}}</text>
            </wxccell>
            <wxccell title="型号规格"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="Model"  placeholder="型号规格"/>
            </wxccell>
            <wxccell title="材料成分1"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="Model1"  placeholder=""/>
            </wxccell>
            <wxccell title="材料成分2"
                         :has-arrow="false"
                         :titlestyle="{'font-size':'35px'}"
                         @wxcCellClicked="wxcCellClicked"
                         :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="Model2"  placeholder=""/>
            </wxccell>
            <wxccell title="厂商货品编码"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="SupplierCode"  placeholder=""/>
            </wxccell>
            <wxccell title="年份"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="Age"  placeholder=""/>
            </wxccell>
            <wxccell title="季节"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="text" class="input" return-key-type="search" v-model="Season"  placeholder=""/>
            </wxccell>

            <wxccell title="品牌"
                     :has-arrow="true"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <text style="font-size:35px">{{Brand}}</text>
            </wxccell>
            <wxccell title="参考进价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="PurchasePrice"  placeholder=""/>
            </wxccell>
            <wxccell title="零售价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="RetailSales"  placeholder=""/>
            </wxccell>

            <wxccell title="零售价2"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" v-model="RetailSales1"  placeholder=""/>
            </wxccell>

            <wxccell title="批发价"
                     :has-arrow="false"
                     :titlestyle="{'font-size':'35px'}"
                     @wxcCellClicked="wxcCellClicked"
                     :has-top-border="false">
                <input type="number" class="input" return-key-type="search" auto-appear="false" v-model="TradePrice"  placeholder=""/>
            </wxccell>
        </div>

        </scroller>

        <div class="footer">
           <div class="btn"><text style="font-size: 40px;color: white">保存</text></div>
        </div>
    </div>
</template>

<script>
    import gridselect from './component/wxc-grid-select.vue'
    import wxccell from './component/wxc-cell.vue'
    var nav = weex.requireModule('navigator') ;
    const net = weex.requireModule('net');
    const  pref=weex.requireModule('pref');
    export default {
        components:{gridselect,wxccell},
        data(){
         return{
             GoodsID:'',
             Code:'11111888',
             Name:'长袖裙子',
             GoodsType:'裙子',
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
                 {   'GoodsID':'00AQ',
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
                 },
                 {GoodsID:'',ColorID:'',title:'增加',tipqty:''}
             ]
         }
        } , methods : {
            onLoad(p){
                if(p !=undefined && p!=null) {
                    this.GoodsID = p.GoodsID
                }
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
                    this.alert('这是一个跳转页面的按扭')
                }
            },del(index){
                this.alert(index)
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