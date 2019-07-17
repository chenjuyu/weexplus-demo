<template>
    <div class="container">
        <head :rightText="rightText" title="收款"  @rightClick="rightClick"></head>
        <div class="master">
            <wxccell label="应收余额:"
                     :has-arrow="false"
                     :cell-style="{'height':'100px'}"
                     :has-bottom-border="true"	>
                <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{LastNeedRAmount}}</text>
            </wxccell>

            <wxccell label="收款金额:"
                     :has-arrow="false"
                     :cell-style="{'height':'100px'}"
                     :has-bottom-border="true">
                <input type="number" v-model="ReceivalAmount" style="width: 500px;height: 90px;"/>
            </wxccell>

            <wxccell label="结算方式:"
                     :has-arrow="true"
                     :cell-style="{'height':'100px'}"
                     @wxcCellClicked="wxcCellClicked(1)"
                     :has-bottom-border="true"	>
                <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{PaymentType}}</text>
            </wxccell>
            <wxccell label="收款金额2:"
                     :has-arrow="false"
                     :cell-style="{'height':'100px'}"
                     :has-bottom-border="true"	>
                <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{ReceivalAmount2}}</text>
            </wxccell>

            <wxccell label="结算方式2:"
                     :has-arrow="true"
                     :cell-style="{'height':'100px'}"
                     @wxcCellClicked="wxcCellClicked(2)"
                     :has-bottom-border="true"	>
                <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{PaymentType2}}</text>
            </wxccell>

            <wxccell label="收款金额3:"
                     :has-arrow="false"
                     :cell-style="{'height':'100px'}"
                     :has-bottom-border="true"	>
                <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{ReceivalAmount3}}</text>
            </wxccell>
            <wxccell label="结算方式3:"
                     :has-arrow="true"
                     :cell-style="{'height':'100px'}"
                     @wxcCellClicked="wxcCellClicked(3)"
                     :has-bottom-border="true"	>
                <text style="direction: rtl;margin-right: 10px;font-size: 30px;color: #666666;text-align: right">{{PaymentType3}}</text>
            </wxccell>

        </div>



        <div class="footer">
            <div style="height: 80px;justify-content: center;align-items: center;flex-direction: row">
                <text style="font-size: 40px;color: #FFFFFF";>合计：{{totalQty}}</text>
                <text style="font-size: 40px;color: red";>￥{{totalAmt}}</text>
            </div>
            <div @click="submit" style="height: 80px;justify-content: center;align-items: center;position: absolute;right: 0;bottom: 0;width: 200px;background-color: orange"> <text style="font-size: 40px;color: #FFFFFF;">确认</text></div>
        </div>
    </div>
</template>

<script>
    import wxccell from './component/wxc-cell.vue';
    var nav = weex.requireModule('navigator') ;
    export default {
        components: { wxccell },
        data(){
            return{
                LastNeedRAmount:0.00,
                ReceivalAmount:0.00,
                PaymentTypeID:'',
                PaymentType:'',
                ReceivalAmount2:0.00,
                PaymentType2ID:'',
                PaymentType2:'',
                ReceivalAmount3:0.00,
                PaymentType3ID:'',
                PaymentType3:'',
                totalQty:0,
                totalAmt:0.00


            }

        }
        ,methods:{
            onLoad(p){
                if(p==null){
                    p={}
                }
                this.ReceivalAmount=p.hasOwnProperty("lastARAmount")?p.lastARAmount:''
                this.totalQty=p.hasOwnProperty("totalQty")?p.totalQty:''
                this.totalAmt=p.hasOwnProperty("totalAmt")?p.totalAmt:''
            },wxcCellClicked(id){
                var that=this
                var condition=''
                if(that.PaymentType !=='' && id==1){
                    condition =that.PaymentType
                }else if(that.PaymentType2 !=='' && id==2){
                    condition =that.PaymentType2
                }else if(that.PaymentType3 !=='' && id==3){
                    condition =that.PaymentType3
                }


                nav.pushFull({
                    url: 'root:base.js',
                    param: {send:'getPaymentType',condition:condition},
                    animate:true,
                    isPortrait:true
                }, (e) => {
                    if (e != undefined){
                        if(e==null || JSON.stringify(e)=="{}"){
                            return
                        }
                        if(id==1){
                            that.PaymentTypeID =e.id
                            that.PaymentType =e.Name
                        }else if(id==2){
                            that.PaymentType2ID =e.id
                            that.PaymentType2 =e.Name
                        }else if(id==3){
                            that.PaymentType3ID =e.id
                            that.PaymentType3 =e.Name
                        }



                    }

                })
            },submit(e){

                param.ReceivalAmount =this.ReceivalAmount
                param.PaymentTypeID =this.PaymentTypeID
                param.PaymentType =this.PaymentType
                param.ReceivalAmount2 =this.ReceivalAmount2
                param.PaymentType2ID =this.PaymentType2ID
                param.PaymentType2 =this.PaymentType2
                param.ReceivalAmount3 = this.ReceivalAmount3
                param.PaymentType3ID =this.PaymentType3ID
                param.PaymentType3  =this.PaymentType3
                nav.backFull(param,false)
            }
        }
    }
</script>

<style scoped>
    .container{
        flex: 1;
    }
    .footer{
        position: absolute;
        left: 0;
        right: 0;
        bottom: 0;
        width: 750px;
        flex-direction: row;
        height: 80px;
        background-color: #1EA5FC;
    }
</style>
