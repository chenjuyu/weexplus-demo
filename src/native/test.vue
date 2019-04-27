<template>
    <div class="layout">

        <text class="text">服务器地址:</text>
        <input class="input" type="text" placeholder="请输入服务器地址" v-model="ip" return-key-type="default" />
        <div class="submit"> <text  @click="save" class="text1">保存</text> </div>
    </div>
</template>

<script>
    const  pref=weex.requireModule('pref')
    export default {
        data(){
            return {
                name: "test",
                ip:''
            }
        },
        methods:{
            onLoad(p){
                // debugger
                // this.alert(p);
                //this.name=p.name;
                this.ip=pref.getString('ip');
            },
            save(){
                pref.setString('ip',this.ip);
                this.toast("保存成功");
                //  this.alert(pref.getString('ip'));
                this.push("root:index.js");
            }
        },
        created(e){
            //alert(e)
        }
    }
</script>

<style scoped>
    .layout{
        justify-content: center;
        background-color:#eeeeee;
        text-align: center;
    }
    .text{
        color: black;
        margin-top: 30px;
        font-size: 30px;
    }
    .text1{
        width: 110px;
        height: 60px;
        font-size: 50px;
        justify-content: center;
        align-items: center;
        background-color: #00B4FF;
        text-align-all: center;
    }
    .submit{
        /* background-color: black; */
        display: block;
        margin-top:50px;
        direction:rtl
    }
    .input {
        border-width: 1px;
        margin-bottom: 10px;
        height: 100px;
    }
</style>