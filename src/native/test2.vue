<template>
<div class="wrapper">
    <head :rightText="rightText"  @rightClick="rightClick"></head>
   <list> <!-- detaillist -->
       <cell  ref="skid" v-for="(item, i) of detaillist" @click="onNodeClick(item, i)" :key="'skid-' + i" class="wxc-skid" :style="{width: (750 + item.right.length * 100) + 'px', height: height + 'px'}" @touchstart="(e) => !isAndroid && onPanStart(e, item, i)" @horizontalpan="(e) => isAndroid && onPanStart(e, item, i)" @touchend="(e) => onPanEnd(e, item, i)">


           <div :style='styles' class="swipe-action-center border">
               <text style="font-size: 30px;color:#000000;margin-left: 10px;width: 200px;text-align: center">{{item.Code}}</text>
               <text style="font-size: 30px;color:#000000;margin-left: 10px;width: 100px;text-align: center">{{item.Color}}</text>
               <text style="font-size: 30px;color:#000000;margin-left: 10px;width: 60px;text-align: center" v-for="(lst2,index2) in item.sizeData">{{lst2.Quantity }}</text>

           </div>

           <div class="swipe-action-right">
               <text class="swipe-action-child swipe-action-text" @click="onRightNode(item, child, i)" v-for="(child, childIdx) of item.right" :style="Object.assign({lineHeight: height + 'px'}, child.style || {})" :key="'child-' + childIdx">{{child.text}}</text>
           </div>

       </cell>
   </list>



</div>
</template>

<script>
    //[{name:'k1',value:1},{name:'k2',value:2}]
    import Binding from "weex-bindingx/lib/index.weex.js";
    const animation = weex.requireModule("animation");
    const modal =weex.requireModule('modal')
    import {Utils} from 'weex-ui';
    export default {
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
                mobileX: 0,
                webStarX: 0,
                saveIdx: null,
                isAndroid: Utils.env.isAndroid()
                ,detaillist:[{
                    GoodsID: '00AG', Code: '192B1210017', Name: '外披', ColorTitle: '颜色', ColorID: '0BB', Color: '白色',
                    sizeData: [{GoodsID: '00AG', ColorID: '0BA', x_1: 1, Quantity: 1, SizeID: '00A'}, {
                        GoodsID: '00AG',
                        ColorID: '0BA',
                        x_2: 2,
                        name:'x_2', //列名
                        Quantity: 1,
                        SizeID: '00A'
                    }],right: [
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

                },{
                    GoodsID: '00AG', Code: '192B1210017', Name: '外披', ColorTitle: '颜色', ColorID: '0BB', Color: '白色',
                    sizeData: [{GoodsID: '00AG', ColorID: '0BA', x_1: 1, Quantity: 1, SizeID: '00A'}, {
                        GoodsID: '00AG',
                        ColorID: '0BA',
                        x_2: 2,
                        name:'x_2', //列名
                        Quantity: 1,
                        SizeID: '00A'
                    }],right: [
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

                },{
                    GoodsID: '00AG', Code: '192B1210017', Name: '外披', ColorTitle: '颜色', ColorID: '0BB', Color: '白色',
                    sizeData: [{GoodsID: '00AG', ColorID: '0BA', x_1: 1, Quantity: 1, SizeID: '00A'}, {
                        GoodsID: '00AG',
                        ColorID: '0BA',
                        x_2: 2,
                        name:'x_2', //列名
                        Quantity: 1,
                        SizeID: '00A'
                    }],right: [
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
                ]

            }
        },  methods: {
            special(dom, styles) {
                animation.transition(dom, {
                    styles,
                    duration: 200, //ms
                    timingFunction: "ease",
                    delay: 100 //ms
                });
            },
            onRightNode(pNode, node, i) {
                node.onPress();
                modal.toast({
                    message: "点击事件",
                    duration: 0.3
                });

                if (pNode.autoClose)
                    this.special(this.$refs.skid[i], {
                        transform: `translate(0, 0)`
                    });
            },
            onNodeClick(node, i) {
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
                    this.$emit("onNodeClick", node, i);
                }
            },

            onPanEnd(e, node, i) {
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
        }
    }
</script>

<style scoped>
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
    .border {
        border-bottom-width: 1px;
        border-bottom-color: #dddddd;
    }
    .wxc-skid {
        flex-direction: row;
        background-color: #FFFFFF;
    }
    .swipe-action-right {
        flex-direction: row;
        align-items: center;
    }

    .swipe-action-center {
        width: 750px;
        flex-direction: row;
        background-color: orange;
    }
</style>
