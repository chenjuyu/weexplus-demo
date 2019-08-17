<template>
    <div class="wrapper">
        <head :rightText="rightText" title="货品资料详情"  @rightClick="rightClick"></head>
        <div class="lightbox">
            <slider class="slider" interval="3000" auto-play="true" offset-x-accuracy="0.9" @scroll="scrollHandler" @change="changeHandler" infinite="false">
                <div class="slider-pages" v-for="item in itemList">
                    <image class="img" :src="item.pictureUrl"></image>
                    <text class="title">{{item.title}}</text>
                </div>
                <indicator class="indicator"></indicator>
            </slider>
        </div>
        <div class="demo">
            <scroller style="height: 200px;">
                <gridselect ref="glist"
                            :single="false"
                            :cols="5"
                            :list="colorData"
                            @del ="params=>del(params)"
                            @select="params => onSelect('res3', params)">
                </gridselect>
                <!-- :customStyles="customStyles" 自定义
                     <text class="res">{{res3}}</text>  :style="{'height':getScreenHeight()}" -->
            </scroller>
        </div>

    </div>
</template>

<script>
    import gridselect from './component/wxc-grid-select.vue'
    export default {
        components:{gridselect},
        data(){
         return{
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
                 }
             ]
         }
        } , methods : {
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
            },del(index){
                this.alert(index)
            }
            }
    }
</script>

<style scoped>
.wrapper{
    flex-direction: column;
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
    height: 200px;
}
</style>