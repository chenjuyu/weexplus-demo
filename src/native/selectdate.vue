<template>
    <div>
        <head rightText="" backClick="back" title="查询条件" rightButton=""></head>
        <div style="flex-direction: row;height: 100px; border-bottom-width: 1px;border-color: #eeeeee; justify-content: flex-start;align-items: center">
           <div style="height: 100px; width:200px; justify-content: center;align-items: center"><text style="font-size: 35px;">开始日期:</text> </div>
            <div style="height:100px;width: 550px;justify-content: center;align-items: center; " @click="choose(1)">
                <text style="font-size: 35px">{{DateStr1}}</text>
            </div>
        </div>
        <div style="flex-direction: row;height: 100px; border-bottom-width: 1px;border-color: #eeeeee; justify-content: flex-start;align-items: center">
            <div style="height: 100px; width:200px; justify-content: center;align-items: center">
                <text style="font-size: 35px;">结束日期:</text> </div>
            <div style="height:100px;width: 550px;justify-content: center;align-items: center; " @click="choose(2)">
                <text style="font-size: 35px">{{DateStr2}}</text>
            </div>
        </div>

        <div style="margin-top: 20px;justify-content: center;align-items: center">
        <wxc-button text="确定"
                    @wxcButtonClicked="wxcButtonClicked"></wxc-button>
        </div>

    </div>
</template>

<script>
    import module1 from './jstools/mytool'
    import { WxcButton } from 'weex-ui'
    var nav = weex.requireModule('navigator') ;
    var date=module1.formatDate(new Date(),'yyyy-MM-dd')
    export default {
        components:{WxcButton},
       data(){
           return{
               DateStr1:date,
               DateStr2:date
           }


       },methods:{
            choose(id){
                let dateTimePicker=weex.requireModule('timepicker')
                dateTimePicker.open({
                    value: '',//必选,选中的值，格式为yyyy-MM-dd HH:mm;当value为空,默认选中当前时间;当value不为空时为返回值
                    max: '',//可选，日期最大值,默认2099-12-31 23:59
                    min: '',//可选，日期最小值,默认1900-12-31 00:00
                    title: '',//可选，标题的文案，默认为空
                    titleColor: '',//可选，默认为空,title不为空时有效，颜色值（#313131）
                    confirmTitle: '', //确认按钮的文案,默认值（完成）
                    confirmTitleColor: '', //确认按钮的文字颜色，默认值(#00b4ff)
                    cancelTitle: '', //取消按钮的文案,默认值（取消）
                    cancelTitleColor: '', //取消按钮的文字颜色,默认值(#313131)
                },(res) =>{//回调
                    //返回字段
                    //result{string}：success,cancel
                    //data {string}：格式为yyyy-MM-dd HH:mm
                    if(res.result === "success"){
                        //业务逻辑
                        if(id==1) {
                            this.DateStr1 = res.data.substring(0, 10)
                        }else if(id==2){
                            this.DateStr2 = res.data.substring(0, 10)
                        }
                    }else{
                        //业务逻辑
                    }
                });

            },wxcButtonClicked(e){
                var p={}
                p.BeginDate =this.DateStr1
                p.EndDate =this.DateStr2
                nav.backFull(p,true)
            }

        }
    }
</script>

<style scoped>

</style>
