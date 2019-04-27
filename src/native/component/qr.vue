<template>
    <div>
        <text>{{text}}</text>

        <qr ref="qr" style="position: absolute;top: 0;left: 0;right: 0;bottom: 0">

        </qr>
        <div  style="position: absolute;top: 0;left: 0;right: 0;bottom: 0;background-color: transparent;">
            <!--<button @click="start"></button>-->
            <!--<button @click="stop"></button>-->
            <div style="width: 750px;flex: 1;background-color: rgba(0,0,0,0.3)"></div>
            <div style="height: 450px;flex-direction: row">
                <div style="width: 150px;background-color: rgba(0,0,0,0.3)">
                    <text>返回</text>
                </div>
                <div style="flex: 1"></div>
                <div style="width: 150px;background-color: rgba(0,0,0,0.3)">
                    <text>相册</text>
                </div>
            </div>
            <div style="width: 750px;flex:1;background-color: rgba(0,0,0,0.3)"></div>

        </div>

    </div>
</template>
<script>

    export default{
        components: { },
        data(){
            return {
                text:''
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
