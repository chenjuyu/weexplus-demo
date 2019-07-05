<template>
    <div class="wrapper">
        <head :rightText="rightText" title="已选列表"  @rightClick="rightClick"></head>
        <list style="height:970px">
                <cell ref="skid" v-for="(ls,i) in detaillist" @click="onNodeClick(ls, i)" style="flex-direction: row;height:290px;border-bottom-width: 1px;border-color: #dddddd">
                    <!-- 整体包1-->
                    <div :style='styles' class="swipe-action-center border">

                        <div style="flex-direction: row"><text style="font-size: 30px;color: red;">{{ls.Code}}</text> <text style="font-size: 30px;color:#000000; margin-left: 20px">{{ls.Name}}</text></div>
                        <!-- 图片与显示-->
                        <div style="flex-direction: row">
                            <text style="font-size: 30px;color:#000000;">{{Number(i)+1}}</text>
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

                    </div>  <!-- 整体包1结束-->
                    <!-- 菜单
                    <div class="swipe-action-right">
                        <text class="swipe-action-child swipe-action-text" @click="onRightNode(ls, child, i)" v-for="(child, childIdx) of ls.right" :style="Object.assign({lineHeight: height + 'px'}, child.style || {})" :key="'child-' + childIdx">{{child.text}}</text>
                    </div>
                     -->
                </cell>



        </list>
        <div @click="submit" style="position: absolute;left: 50px;right: 50px;bottom: 0;justify-content: center;align-items: center;height:100px;border-radius:20px;background-color: orange">
            <text style="font-size: 35px;color: #FFFFFF">保存返回列表</text>
        </div>
    </div>
</template>

<script>
    var nav = weex.requireModule('navigator') ;
    export default {
        components: {},
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

                detaillist:[]

            }
        },methods:{
            onLoad(p){
              this.detaillist=p.detaillist
            },
            submit(){

            }

        }
    }
</script>

<style scoped>

</style>