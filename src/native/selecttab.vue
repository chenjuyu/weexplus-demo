<template>
    <div>
        <head rightText="" backClick="back" title="零售分析" rightButton=""></head>
        <div style="align-items: center;margin-top: 10px" append="tree">
            <selecttab style="width: 750px;" :items="['今天','昨天','近7天','近30天','自定义']" theme="#1296db"  @change="change2"></selecttab>
        </div>
        <!-- 显示当前日期-->
        <div style="align-items: center;justify-content: center;height: 50px"><text style="font-size: 35px;color: red">{{BeginDate}}至{{EndDate}}</text></div>

        <!-- <scroller style="flex:0;width: 780px;"  scroll-direction="horizontal">  </scroller>添加滚动-->
        <div style="align-items: center;margin-top: 10px" append="tree">
            <selecttab style="width: 750px;" :items="['店铺','品类','品牌','厂商','导购','销售排行']" theme="#1296db"  @change="change"></selecttab>
        </div>


        <div style="flex: 1;">
            <!-- 店铺-->
            <div  :style="{'visibility':selectIndex==0?'visible':'hidden'}" style="position: absolute;left: 20;top: 10;right: 20;bottom: 0">
                <list style="flex: 1" >
                    <header>
                        <div class="banner">
                            <text class="txttitle" :style="{width:'187px'}">名次</text>
                            <text class="txttitle" :style="{width:'187px'}">店铺</text>
                            <text class="txttitle" :style="{width:'187px'}">数量</text>
                            <text class="txttitle" :style="{width:'187px'}">金额</text>
                        </div>
                    </header>
                    <cell v-for="(ls,index) in lists" :key="index" >
                        <div class="panel">
                            <text class="text" :style="{width:'187px'}">{{ls.No}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Amount}}</text>
                        </div>
                    </cell>
                </list>

                <div v-if="lists.length ==0" style="justify-content: center;align-items: center;height: 100">
                    <text style="color: #ffffff;font-size: 30">未找到下载列表！</text>
                </div>
            </div>
            <!-- 品类-->
            <div  :style="{'visibility':selectIndex==1?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" >
                    <header>
                        <div class="banner">
                            <text class="txttitle" :style="{width:'150px'}">名次</text>
                            <text class="txttitle" :style="{width:'150px'}">品类</text>
                            <text class="txttitle" :style="{width:'150px'}">数量</text>
                            <text class="txttitle" :style="{width:'150px'}">金额</text>
                            <text class="txttitle" :style="{width:'150px'}">占比%</text>
                        </div>
                    </header>
                    <cell v-for="(ls,index) in lists" :key="index" >
                        <div class="panel">
                            <text class="text" :style="{width:'150px'}">{{ls.No}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Amount}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.LvStr}}</text>
                        </div>
                    </cell>
                </list>
            </div>

           <!-- 品牌-->
            <div  :style="{'visibility':selectIndex==2?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" >
                    <header>
                        <div class="banner">
                            <text class="txttitle" :style="{width:'150px'}">名次</text>
                            <text class="txttitle" :style="{width:'150px'}">品牌</text>
                            <text class="txttitle" :style="{width:'150px'}">数量</text>
                            <text class="txttitle" :style="{width:'150px'}">金额</text>
                            <text class="txttitle" :style="{width:'150px'}">占比%</text>
                        </div>
                    </header>
                    <cell v-for="(ls,index) in lists" :key="index" >
                        <div class="panel">
                            <text class="text" :style="{width:'150px'}">{{ls.No}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Amount}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.LvStr}}</text>
                        </div>
                    </cell>
                </list>
            </div>
            <!-- 厂商-->
            <div  :style="{'visibility':selectIndex==3?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" >
                    <header>
                        <div class="banner">
                            <text class="txttitle" :style="{width:'150px'}">名次</text>
                            <text class="txttitle" :style="{width:'150px'}">厂商</text>
                            <text class="txttitle" :style="{width:'150px'}">数量</text>
                            <text class="txttitle" :style="{width:'150px'}">金额</text>
                            <text class="txttitle" :style="{width:'150px'}">占比%</text>
                        </div>
                    </header>
                    <cell v-for="(ls,index) in lists" :key="index" >
                        <div class="panel">
                            <text class="text" :style="{width:'150px'}">{{ls.No}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Amount}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.LvStr}}</text>
                        </div>
                    </cell>
                </list>
            </div>
            <!-- 导购-->
            <div  :style="{'visibility':selectIndex==4?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" >
                    <header>
                        <div class="banner">
                            <text class="txttitle" :style="{width:'187px'}">名次</text>
                            <text class="txttitle" :style="{width:'187px'}">导购</text>
                            <text class="txttitle" :style="{width:'187px'}">数量</text>
                            <text class="txttitle" :style="{width:'187px'}">金额</text>
                        </div>
                    </header>
                    <cell v-for="(ls,index) in lists" :key="index" >
                        <div class="panel">
                            <text class="text" :style="{width:'187px'}">{{ls.No}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Amount}}</text>

                        </div>
                    </cell>
                </list>
            </div>
            <!-- 货品 销售排行-->
            <div  :style="{'visibility':selectIndex==5?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" >
                    <header>
                        <div class="banner">
                            <text class="txttitle" :style="{width:'150px'}">名次</text>
                            <text class="txttitle" :style="{width:'150px'}">货号</text>
                            <text class="txttitle" :style="{width:'150px'}">数量</text>
                            <text class="txttitle" :style="{width:'150px'}">零售价</text>
                            <text class="txttitle" :style="{width:'150px'}">平均售价</text>
                        </div>
                    </header>
                    <cell v-for="(ls,index) in lists" :key="index" >
                        <div class="panel">
                            <text class="text" :style="{width:'150px'}">{{ls.No}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Amount}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.AvgPrice}}</text>
                        </div>
                    </cell>
                </list>
            </div>


        </div>
        <!-- 底部-->
        <div class="footer">
            <div class="total">
                <text style="font-size: 35px;font-weight: bold;color: red">合计</text>
                <text style="font-size: 35px;font-weight: bold;color: red;margin-left: 100px">{{totalQty}}</text>
                <text style="font-size: 35px;font-weight: bold;color: red;margin-left: 100px">{{totalAmt}}</text>
            </div>

            <div class="total">
                <text style="font-size: 35px;font-weight: bold;border-color:#eeeeee;width: 375px;border-right-width:1px; text-align: center " @click="choose1">排序^</text>
                <text style="font-size: 35px;font-weight: bold;width: 375px;text-align: center" @click="choose2">筛选</text>
            </div>
        </div>

        <wxcpopover ref="wxc-popover"
                    :buttons="btns"
                    :position="popoverPosition"
                    :arrowPosition="popoverArrowPosition"
                    @wxcPopoverButtonClicked="popoverButtonClicked"></wxcpopover>


    </div>
</template>

<script>
    var selecttab =require('./component/selecttab.vue')
    //  import selecttab from './component/selecttab.vue'
    import {Utils} from 'weex-ui'
    import module1 from './jstools/mytool'
    var nav = weex.requireModule('navigator')
    const  pref=weex.requireModule('pref')
    const net = weex.requireModule('net');
    import wxcpopover from './component/wxc-popover.vue'
    export default {
        components:{selecttab,wxcpopover},
        data(){
            return{
                btns:[],
                popoverPosition:{x: 450, y: Utils.env.getPageHeight()-70 },
                popoverArrowPosition:{pos: 'bottom', x: 0},//160
                item:{},
                BeginDate:module1.formatDate(new Date(),'yyyy-MM-dd'),
                EndDate:module1.formatDate(new Date(),'yyyy-MM-dd'),
                condition:[],//用于保存查询 条件转换
                selectIndex:0,
                selectIndex2:0,
                totalQty:7336,
                totalAmt:22222222,
                appendFlag:false,//用于标志是否上拉分页加载，默认为假
                lists:[// 店铺列表
                    {No:1,id:'ooa',Name:'文山',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山1',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山2',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山2',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山1',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山2',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山2',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山1',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山2',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山2',Quantity:78,Amount:2540.00},
                    {No:1,id:'ooa',Name:'文山',Quantity:78,Amount:2540.00},

                ],
                des:'xxxxx'
            }
        },methods:{
            onLoad(p){
                this.modifymenu()
            },modifymenu(){
                if(this.selectIndex ==0){
                    this.btns=[
                        {
                            icon: '\ue61f',
                            text:'部门类型',
                            key:'key-print'
                        }
                        ,{
                            icon: '\ue61f',
                            text:'店铺名称',
                            key:'key-print'
                        }
                    ]
                    this.popoverPosition={x: 450, y: Utils.env.getPageHeight()-140 }

                }else if(this.selectIndex ==1){
                    this.btns=[
                        {
                            icon: '\ue61f',
                            text:'品类',
                            key:'key-print'
                        }]
                    this.popoverPosition={x: 450, y: Utils.env.getPageHeight()-70 }
                }else if(this.selectIndex ==2){
                    this.btns=[
                        {
                            icon: '\ue61f',
                            text:'品牌',
                            key:'key-print'
                        }]
                    this.popoverPosition={x: 450, y: Utils.env.getPageHeight()-70 }
                }else if(this.selectIndex ==3){
                    this.btns=[
                        {
                            icon: '\ue61f',
                            text:'厂商',
                            key:'key-print'
                        }]
                    this.popoverPosition={x: 450, y: Utils.env.getPageHeight()-70 }
                }else if(this.selectIndex ==4){
                    this.btns=[
                        {
                            icon: '\ue61f',
                            text:'导购',
                            key:'key-print'
                        }]
                    this.popoverPosition={x: 450, y: Utils.env.getPageHeight()-70 }
                }else if(this.selectIndex ==5){
                    this.btns=[
                        {
                            icon: '\ue61f',
                            text:'货品',
                            key:'key-print'
                        }]
                    this.popoverPosition={x: 450, y: Utils.env.getPageHeight()-70 }
                }
            },
            change(index)
            {
                this.selectIndex=index;
                this.modifymenu()
                //切换后要清空，再查询  上拉加载更多除外
                this.lists.splice(0,this.lists.length)

            },change2(index){
                this.selectIndex2 =index;
                if(this.selectIndex2 ==0){
                    this.BeginDate=module1.formatDate(new Date(),'yyyy-MM-dd')
                    this.EndDate=module1.formatDate(new Date(),'yyyy-MM-dd')
                }else if(this.selectIndex2 ==1){
                    var date =new Date()
                    module1.AddDays(date,-1) //使用了set 方法不会返回值
                    //this.log('昨天的数据：'+date)
                    this.BeginDate=module1.formatDate(date,'yyyy-MM-dd')
                    this.EndDate=module1.formatDate(date,'yyyy-MM-dd')
                }else if(this.selectIndex2 ==2){
                    var date2 =new Date()
                    module1.AddDays(date2,-7) //使用了set 方法不会返回值
                    this.BeginDate=module1.formatDate(date2,'yyyy-MM-dd')
                    this.EndDate=module1.formatDate(new Date(),'yyyy-MM-dd')
                }else if(this.selectIndex2 ==3){
                    var date2 =new Date()
                    module1.AddDays(date2,-30) //使用了set 方法不会返回值
                    this.BeginDate=module1.formatDate(date2,'yyyy-MM-dd')
                    this.EndDate=module1.formatDate(new Date(),'yyyy-MM-dd')
                }else if(this.selectIndex2 ==4){
                    var p={}
                    nav.pushFull({url: 'root:selectdate.js',param:p,animate:true},(e)=>{
                        if (e !== undefined) { //返回结果是尺码集体的，要拆分
                            if (e == null || JSON.stringify(e) == '{}') {//无结果返回，指的是点左上角返回菜单的返回
                                return
                            }
                            this.BeginDate =e.BeginDate
                            this.EndDate=e.EndDate
                        }

                    })
                }
                //切换后要清空，再查询  上拉加载更多除外
                this.lists.splice(0,this.lists.length)

            },getVisable(it)
            {
                if(it.serial==undefined)
                    return false;
                return   it.serial.length==0;
            },total(){
                if(this.selectIndex==0){ //店铺报表 合计




                }
            },choose2(e){
                //this.alert(this.$refs)
                this.$refs['wxc-popover'].wxcPopoverShow();
            }, popoverButtonClicked (obj) {
                // modal.toast({ 'message': `key:${obj.key}, index:${obj.index}`, 'duration': 1 });
                //  this.alert(JSON.stringify(obj))
                //  this.alert(this.btns[obj.index].text)
                var p={}

                if(this.btns[obj.index].text =='店铺名称'){
                    p.send ='getWarehouse'
                }else if(this.btns[obj.index].text =='部门类型'){
                    p.send ='getWarehouse'
                }else if(this.btns[obj.index].text =='品类'){
                    p.send ='getGoodsType'
                }else if(this.btns[obj.index].text =='品牌'){
                    p.send ='getBrand'
                }else if(this.btns[obj.index].text =='厂商'){
                    p.send ='getGoodsSupplier'
                }else if(this.btns[obj.index].text =='导购'){
                    p.send ='getEmployee'
                }else if(this.btns[obj.index].text =='货品'){
                    p.send ='getPosSalesGoods'
                }


                nav.pushFull({url: 'root:basecheck.js',param:p,animate:true},(e)=>{
                    if (e !== undefined) {
                        if (e == null || JSON.stringify(e) == '{}') {//无结果返回，指的是点左上角返回菜单的返回
                            return
                        }
                        //   this.BeginDate =e.BeginDate
                        //  this.EndDate=e.EndDate
                        //使用前先清空
                        if(this.condition.length >0) {
                            this.condition.splice(0, this.condition.length)
                        }
                         var arr = e.item || []
                         for(var i=0;i<arr.length;i++){
                             this.condition.push(arr[i].id)
                         }
                         this.log('condition列表字符串:'+this.condition.join())
                    }

                })
            },searchdata(){ //切换 查询数据 加载数据
               var that =this

                net.post(pref.getString('ip') + '/report.do?',param,{},function(){
                    //start
                },function(e){
                    //success
                    //  self.back=e.res;
                    if(e !=null && e !=undefined ){
                        if(!that.appendFlag) {
                            that.lists = e.res.obj
                            that.total()
                        }
                    }

                },function(e){
                    //compelete

                },function(){
                    //exception
                });

            }

        }
    }
</script>

<style scoped>
    .banner {
        width: 750px;
        flex-direction: row;
        border-bottom-width: 1px;
        border-color: #eeeeee;
        /*  padding: 25px;
          font-size: 60px;
          text-align: center;
          font-weight: bold;
          color: #41B883;
          background-color: rgb(162, 217, 192);
          */

    }
    .txttitle{
        font-size: 35px;
        font-weight: bold;

    }
    .text{
        font-size: 35px;


    }
    .panel{
        flex-direction: row;
        border-bottom-width: 1px;
        height: 80px;
        padding-top: 20px; /* 为了让文字居中*/
        border-color: #eeeeee;
    }
    .total{
        flex-direction: row;
        border-bottom-width: 1px;
        height: 80px;
        padding-top: 20px; /* 为了让文字居中*/
        border-color: #eeeeee;
    }
    .footer{
        height: 160px;
        bottom: 0;
        left: 0;
        right: 0;
        /*    background-color: #0080FF; */
    }
</style>
