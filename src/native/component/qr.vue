<template>
    <div style="flex: 1">
        <head :rightText="rightText" title="扫描条码/二维码" @rightClick="rightClick"></head>

        <qr ref="qr" style="position: absolute;top: 130;left: 0;right: 0;bottom: 0">

        </qr>
        <div  style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;background-color: transparent;">

          <!--  <button @click="start"></button> <image src="root:tab_bg9.png" style="width: 32px;height: 32px"></image> -->
            <!-- <button @click="stop"></button> -->
             <div style="width: 750px;flex: 1;background-color: rgba(0,0,0,0.3)"></div>
             <div style="height: 450px;flex-direction: row">
                 <div style="width: 150px;background-color: rgba(0,0,0,0.3)">

                 </div>
                 <!--  background-color: red  <image src="root:img/scan_light.png" style="width: 500px;height: 32px"></image> -->
                 <div ref="scan" :style="{transform:`translateY(50)`}" @panmove="onPanMove" style="flex: 1;justify-content: center;align-items: center;">
                     <image src="root:img/scan_light.png" style="width: 500px;height: 32px"></image>

                 </div>
                 <div style="width: 150px;background-color: rgba(0,0,0,0.3)">

                 </div>
             </div>
             <div style="width: 750px;flex:1;background-color: rgba(0,0,0,0.3);flex-direction: row;justify-content: center;align-items: center">
                 <text style="font-size: 35px;color: #FFFFFF">扫描条码/二维码</text>

             </div>

         </div>

     </div>
 </template>
 <script>
     import { WxcLightbox } from 'weex-ui';
     const animation = weex.requireModule('animation');
     import Binding from 'weex-bindingx/lib/index.weex.js';
     export default{
         components: {WxcLightbox },
         data(){
             return {
                 text:'条码/二维码扫描',
                 show:true,
                 imageList:[{src: 'root:img/scan_light.png'}]
             }
         },
         props: {},
         methods: {
             start(){

             },
             stop(){

             },
             onLoad(p){

             }
         },
         mounted(){
             this.$refs.qr.scan((res)=>{
                 this.text=res.res
                 this.toast(this.text)

             })
         },
         created(){

         }
     }
 </script>
 <style scoped>

 </style>
