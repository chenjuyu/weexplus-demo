<template>
    <div>
        <head :rightText="rightText" title="图片放大测试"  @rightClick="rightClick"></head>
        <image src="root:img/home_logo.png" @click="clickImg($event)" style="width: 80px;height: 80px"/>
        <!-- 放大图片 -->
        <big-img v-if="showImg" @clickit="viewImg" :imgSrc="imgSrc"></big-img>
    </div>
</template>

<script>
    import BigImg from './component/BigImg.vue';
    export default {
        data() {
            return {
                showImg:false,
                imgSrc: ''
            }
        },
        components: {
            'big-img':BigImg
        },
        methods: {
            clickImg(e) {
            //    this.alert(JSON.stringify(e))
                this.showImg = true;
                // 获取当前图片地址
                this.imgSrc = e.currentTarget.attr.src;
           //     this.alert(e.currentTarget.attr.src)
            },
            viewImg(){
                this.showImg = false;
            },
        }
    }
</script>