<template>
    <div>
    <head :rightText="rightText" title="扫描条码/二维码" @rightClick="rightClick"></head>

        <div style="flex-direction: row">
            <input type="text" style="width: 400px;height: 100px;border-width: 2px;border-color: #4d4d4d" ref="barcode" @change="change" v-model="barcode">
        </div>

        <div style="margin-top: 300px;background-color: red;justify-content: center;align-items: center;height: 80px" @click="scan">
            <text style="font-size: 35px">自定义扫描模块</text>
        </div>




    </div>
</template>

<script>
    export default {
       data(){
           return{
               barcode:''
           }
       },methods:{
           onLoad(p){

           },scan(){
               var qrnew =weex.requireModule('qrnew')
               // this.alert("aaa")
                qrnew.open((res)=>{
                  //  this.alert("res的返回："+res)
                    this.barcode =res
                    this.change()
                })

            },change(){
               this.log('barcode的值：'+this.barcode)
               this.$refs.barcode.blur()
                this.barcode =''
                this.$refs.barcode.focus();
                this.scan() //连续扫描
            }


        }
    }
</script>

<style scoped>

</style>
