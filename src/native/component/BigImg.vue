<template>
    <!-- 过渡动画 -->
    <transition name="fade">
        <div class="img-view" @click="bigImg">
            <!-- 遮罩层 -->
            <div class="img-layer"></div>
            <div class="img">
                <image :src="imgSrc" style="width: 500px;height: 500px;"/>
            </div>
        </div>
    </transition>
</template>
<script>

    export default {
        components:{},
        props: {'imgSrc':''},
        data(){
            return{
                show:true
            }
        },
        methods: {
            bigImg() {
                // 发送事件
                this.$emit('clickit')
            },
            wxcOverlayBodyClicked(){
                this.show =false
            }
        }
    }
</script>
<style scoped>
    /*动画*/
    .fade-enter-active,
    .fade-leave-active {
        transition: all .2s linear;
        transform: translate3D(0, 0, 0);
    }

    .fade-enter,
    .fade-leave-active {
        transform: translate3D(100%, 0, 0);
    }


    /* bigimg */

    .img-view {
        position: relative;
        width: 500px;
        height: 500px;

    }

    /*遮罩层样式*/
    .img-view .img-layer {
        background-color: #000000;
        width: 750px;
        position: fixed;
        bottom: 0;
        right: 0
    }

    /*不限制图片大小，实现居中*/
    .img-view .img img {
      justify-content: center;
        align-items: center;

    }
</style>