<template>
    <div>
    <head :rightText="rightText" title="扫描条码/二维码" @rightClick="rightClick"></head>

        <div style="margin-top: 300px;background-color: red;justify-content: center;align-items: center;height: 80px" @click="scan">
            <text style="font-size: 35px">自定义扫描模块</text>
        </div>

    </div>
</template>

<script>
    export default {
       data(){
           return{

           }
       },methods:{
           onLoad(p){

           },scan(){
               var qrnew =weex.requireModule('qrnew')
               // this.alert("aaa")
                qrnew.open((res)=>{
                    this.alert("res的返回："+res)
                })

            }

        }
    }
</script>

<style scoped>

</style>
