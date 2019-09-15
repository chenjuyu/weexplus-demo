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
                <list style="flex: 1" v-if="!noDataFlag">
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
                            <text class="text" :style="{width:'187px'}">{{index+1}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Amount}}</text>
                        </div>
                    </cell>

                    <!--  用于给列表添加上拉加载更多的功能-->
                    <loading class="loading" @loading="onloading" :display="loadinging ? 'show' : 'hide'">
                        <loading-indicator class="indicator"></loading-indicator>
                        <text class="indicator-text">Loading...</text>
                    </loading>

                </list>

                <div v-if="noDataFlag" style="justify-content: center;align-items: center;height: 100">
                    <text style="color: #000000;font-size: 30">暂无数据！</text>
                </div>
            </div>
            <!-- 品类-->
            <div  :style="{'visibility':selectIndex==1?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" v-if="!noDataFlag" >
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
                            <text class="text" :style="{width:'150px'}">{{index+1}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Amount}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.AmtLVStr}}</text>
                        </div>
                    </cell>

                    <!--  用于给列表添加上拉加载更多的功能-->
                    <loading class="loading" @loading="onloading" :display="loadinging ? 'show' : 'hide'">
                        <loading-indicator class="indicator"></loading-indicator>
                        <text class="indicator-text">Loading...</text>
                    </loading>
                </list>

                <div v-if="noDataFlag" style="justify-content: center;align-items: center;height: 100">
                    <text style="color: #000000;font-size: 30">暂无数据！</text>
                </div>

            </div>

           <!-- 品牌-->
            <div  :style="{'visibility':selectIndex==2?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" v-if="!noDataFlag">
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
                            <text class="text" :style="{width:'150px'}">{{index+1}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Amount}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.AmtLVStr}}</text>
                        </div>
                    </cell>

                    <!--  用于给列表添加上拉加载更多的功能-->
                    <loading class="loading" @loading="onloading" :display="loadinging ? 'show' : 'hide'">
                        <loading-indicator class="indicator"></loading-indicator>
                        <text class="indicator-text">Loading...</text>
                    </loading>

                </list>

                <div v-if="noDataFlag" style="justify-content: center;align-items: center;height: 100">
                    <text style="color: #000000;font-size: 30">暂无数据！</text>
                </div>

            </div>
            <!-- 厂商-->
            <div  :style="{'visibility':selectIndex==3?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" v-if="!noDataFlag">
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
                            <text class="text" :style="{width:'150px'}">{{index+1}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Amount}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.AmtLVStr}}</text>
                        </div>
                    </cell>

                    <!--  用于给列表添加上拉加载更多的功能-->
                    <loading class="loading" @loading="onloading" :display="loadinging ? 'show' : 'hide'">
                        <loading-indicator class="indicator"></loading-indicator>
                        <text class="indicator-text">Loading...</text>
                    </loading>


                </list>

                <div v-if="noDataFlag" style="justify-content: center;align-items: center;height: 100">
                    <text style="color: #000000;font-size: 30">暂无数据！</text>
                </div>

            </div>
            <!-- 导购-->
            <div  :style="{'visibility':selectIndex==4?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" v-if="!noDataFlag">
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
                            <text class="text" :style="{width:'187px'}">{{index+1}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'187px'}">{{ls.Amount}}</text>

                        </div>
                    </cell>

                    <!--  用于给列表添加上拉加载更多的功能-->
                    <loading class="loading" @loading="onloading" :display="loadinging ? 'show' : 'hide'">
                        <loading-indicator class="indicator"></loading-indicator>
                        <text class="indicator-text">Loading...</text>
                    </loading>

                </list>

                <div v-if="noDataFlag" style="justify-content: center;align-items: center;height: 100">
                    <text style="color: #000000;font-size: 30">暂无数据！</text>
                </div>

            </div>
            <!-- 货品 销售排行-->
            <div  :style="{'visibility':selectIndex==5?'visible':'hidden'}"  style="position: absolute;left: 20;top: 10;right: 10;bottom: 0">
                <list style="flex: 1" v-if="!noDataFlag">
                    <header>
                        <div class="banner">
                            <text class="txttitle" :style="{width:'150px'}">名次</text>
                            <text class="txttitle" :style="{width:'150px'}">货号</text>
                            <text class="txttitle" :style="{width:'150px'}">数量</text>
                            <text class="txttitle" :style="{width:'150px'}">金额</text>
                            <text class="txttitle" :style="{width:'150px'}">平均售价</text>
                        </div>
                    </header>
                    <cell v-for="(ls,index) in lists" :key="index" >
                        <div class="panel">
                            <text class="text" :style="{width:'150px'}">{{index+1}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Name}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Quantity}}</text>
                            <text class="text" :style="{width:'150px'}">{{ls.Amount}}</text>
                            <text class="text" :style="{width:'150px'}" >{{ls.AvgPrice}}</text>
                        </div>
                    </cell>

                    <!--  用于给列表添加上拉加载更多的功能-->
                    <loading class="loading" @loading="onloading" :display="loadinging ? 'show' : 'hide'">
                        <loading-indicator class="indicator"></loading-indicator>
                        <text class="indicator-text">Loading...</text>
                    </loading>

                </list>

                <div v-if="noDataFlag" style="justify-content: center;align-items: center;height: 100">
                    <text style="color: #000000;font-size: 30">暂无数据！</text>
                </div>


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
    const progress = weex.requireModule('progress');
    const  pref=weex.requireModule('pref')
    const net = weex.requireModule('net');
    var pstatic=weex.requireModule("static")
    var modal =weex.requireModule('modal')
    import wxcpopover from './component/wxc-popover.vue'
    export default {
        components:{selecttab,wxcpopover},
        data(){
            return{
                btns:[],
                popoverPosition:{x: 450, y: Utils.env.getPageHeight()-70 },
                popoverArrowPosition:{pos: 'bottom', x: 0},//160
                searchType:'Department',//这里代替 是店铺的，汇总，还是其他的品类
                item:{},
                currPage:1, //当前页码
                BeginDate:module1.formatDate(new Date(),'yyyy-MM-dd'),
                EndDate:module1.formatDate(new Date(),'yyyy-MM-dd'),
                condition:'',//[],//用于保存查询 条件转换
                noDataFlag:false,//无数据返回标志
                loadinging:false,//下拉加载的
                selectIndex:0,
                selectIndex2:0,
                totalQty:0,
                totalAmt:0,
                appendFlag:false,//用于标志是否上拉分页加载，默认为假
                lists:[// 店铺列表  当前显示

                ],
                totallist:[],//所有的数据不分页前都放在这里
                des:'xxxxx'
            }
        },methods:{
            onLoad(p){
                this.modifymenu() //不管错误都要改菜单
                var param={}
                var that=this

                this.searchType =p.hasOwnProperty('searchType')?p.searchType:'Department'
                this.log('searchType:'+this.searchType)

                    param.searchType =this.searchType
                    param.BeginDate = this.BeginDate
                    param.EndDate = this.EndDate
                    param.Condition = p.hasOwnProperty('Condition')?p.Condition:''//这里之前是，年，月，日，但这里都是给beginddate
                    param.DisType = '明细'//汇总
                    param.DepartmentID = p.hasOwnProperty('DepartmentID')?p.DepartmentID:''
                    param.DistrictID = p.hasOwnProperty('DistrictID')?p.DistrictID:''
                    param.Orderby = '金额'

                pstatic.set('report',param)
                net.post(pref.getString('ip')+'/salesTicket.do?report',param,{},function(){
                    //start
                    progress.showFull('正在载入',false)
                },function(e){
                    //success self.back=e.res;
                    that.lists.splice(0,that.lists.length)//因为共用这个
                    that.totallist.splice(0,that.totallist.length)//因为共用这个
                    that.currPage =1
                    that.totalQty =0
                    that.totalAmt =0
                    if(e.res.success){
                        that.totallist=e.res.obj || []
                        if(that.totallist.length==0){
                           that.noDataFlag =true
                            that.total()
                        }else{
                            that.noDataFlag= false
                            that.log('that.currPage：'+that.currPage)
                            that.lists =that.pagination(that.currPage,15,that.totallist)//that.totallist 原始数组不会改变，这里是第一页
                            that.total()
                        }


                    }else {
                        that.toast(e.res.msg)
                    }
                    progress.dismiss()

                },function(e){
                    //compelete

                },function(){
                    //exception
                    progress.dismiss()
                });

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
            {  var p=pstatic.get('report')
                this.selectIndex=index;
                this.modifymenu()
                //切换后要清空，再查询  上拉加载更多除外
                this.lists.splice(0,this.lists.length)
                if(this.selectIndex ==0){
                    this.searchType ='Department'
                }else if(this.selectIndex ==1){
                    this.searchType='GoodsType'
                    this.log('searchType=1'+this.searchType)
                }else if(this.selectIndex ==2){
                    this.searchType='Brand'
                }else if(this.selectIndex ==3){
                    this.searchType='Supplier'
                }else if(this.selectIndex ==4){
                    this.searchType='Employee'
                }else if(this.selectIndex ==5){
                    this.searchType='Goods'
                }
                p.searchType =this.searchType
                p.DepartmentID ='' //切换就要马上清空
                this.log('汇总项目的触发report:'+JSON.stringify(p))
                pstatic.set('report',p)
                this.onLoad(p)
            },change2(index){
                var p={}
                p=pstatic.get('report')
                this.selectIndex2 =index;
                if(this.selectIndex2 ==0){
                    this.BeginDate=module1.formatDate(new Date(),'yyyy-MM-dd')
                    this.EndDate=module1.formatDate(new Date(),'yyyy-MM-dd')
                    p.BeginDate =this.BeginDate
                    p.EndDate =this.EndDate
                    //切换后要清空，再查询  上拉加载更多除外
                    this.lists.splice(0,this.lists.length)
                    this.log('reportp:'+JSON.stringify(p))
                    pstatic.set('report',p)
                    this.onLoad(p)
                }else if(this.selectIndex2 ==1){
                    var date =new Date()
                    module1.AddDays(date,-1) //使用了set 方法不会返回值
                    //this.log('昨天的数据：'+date)
                    this.BeginDate=module1.formatDate(date,'yyyy-MM-dd')
                    this.EndDate=module1.formatDate(date,'yyyy-MM-dd')
                    p.BeginDate =this.BeginDate
                    p.EndDate =this.EndDate
                    //切换后要清空，再查询  上拉加载更多除外
                    this.lists.splice(0,this.lists.length)
                    this.log('report:'+JSON.stringify(p))
                    pstatic.set('report',p)
                    this.onLoad(p)
                }else if(this.selectIndex2 ==2){
                    var date2 =new Date()
                    module1.AddDays(date2,-7) //使用了set 方法不会返回值
                    this.BeginDate=module1.formatDate(date2,'yyyy-MM-dd')
                    this.EndDate=module1.formatDate(new Date(),'yyyy-MM-dd')
                    p.BeginDate =this.BeginDate
                    p.EndDate =this.EndDate
                    //切换后要清空，再查询  上拉加载更多除外
                    this.lists.splice(0,this.lists.length)
                    this.log('report:'+JSON.stringify(p))
                    pstatic.set('report',p)
                    this.onLoad(p)
                }else if(this.selectIndex2 ==3){
                    var date2 =new Date()
                    module1.AddDays(date2,-30) //使用了set 方法不会返回值
                    this.BeginDate=module1.formatDate(date2,'yyyy-MM-dd')
                    this.EndDate=module1.formatDate(new Date(),'yyyy-MM-dd')
                    p.BeginDate =this.BeginDate
                    p.EndDate =this.EndDate
                    //切换后要清空，再查询  上拉加载更多除外
                    this.lists.splice(0,this.lists.length)
                    this.log('report:'+JSON.stringify(p))
                    pstatic.set('report',p)
                    this.onLoad(p)
                }else if(this.selectIndex2 ==4){

                    nav.pushFull({url: 'root:selectdate.js',param:p,animate:true},(e)=>{
                        if (e !== undefined) { //返回结果是尺码集体的，要拆分
                            if (e == null || JSON.stringify(e) == '{}') {//无结果返回，指的是点左上角返回菜单的返回
                                return
                            }
                            this.BeginDate =e.BeginDate
                            this.EndDate=e.EndDate
                            p.BeginDate =this.BeginDate
                            p.EndDate =this.EndDate
                            //切换后要清空，再查询  上拉加载更多除外
                            this.lists.splice(0,this.lists.length)
                            this.log('p:'+JSON.stringify(p))
                            pstatic.set('report',p)
                            this.onLoad(p)
                        }

                    })
                }

            },getVisable(it)
            {
                if(it.serial==undefined)
                    return false;
                return   it.serial.length==0;
            },total(){
               //数量与金额统一用相同的字段
                var qtysum=0,amtsum=0
                for(var i=0;i<this.totallist.length;i++){
                    qtysum =Number(qtysum)+Number(this.totallist[i].Quantity)
                    amtsum =Number(amtsum)+Number(this.totallist[i].Amount)
                }
                this.totalQty =qtysum
                if(amtsum) {
                    this.totalAmt = parseFloat(amtsum).toFixed(2)
                }
            },choose2(e){
                //this.alert(this.$refs)
                this.$refs['wxc-popover'].wxcPopoverShow();
            }, popoverButtonClicked (obj) {
                // modal.toast({ 'message': `key:${obj.key}, index:${obj.index}`, 'duration': 1 });
                //  this.alert(JSON.stringify(obj))
                //  this.alert(this.btns[obj.index].text)
                var p={}
                var p1=pstatic.get('report')
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
                        this.condition =''
                         var arr = e.item || []
                        if(arr.length ==1){
                            this.condition="'"+arr[0].id+"'" //不用，号
                        }else if(arr.length >1) {
                            for (var i = 0; i < arr.length; i++) {
                                if(i==arr.length-1) {
                                    this.condition = this.condition + "'" + (arr[i].id) + ("'")

                                }else{
                                    this.condition = this.condition + "'" + (arr[i].id) + ("',")
                                }
                            }
                        }

                         this.log('condition列表字符串:'+this.condition)//this.condition.join() 如果是列表可以这样用
                        p1.DepartmentID =this.condition
                        pstatic.set('report',p1)
                        this.log('p1:'+JSON.stringify(p1))
                        this.onLoad(p1)
                    }

                })
            }, pagination(pageNo, pageSize, array) { //分页
                var offset = (pageNo - 1) * pageSize;
                return (offset + pageSize >= array.length) ? array.slice(offset, array.length) : array.slice(offset, offset + pageSize);
            },onloading(event) { //上拉加载更多
                var that=this
                this.loadinging = true;
                modal.toast({
                    message: "loading",
                    duration: 1
                });
                setTimeout(()=>{
                    this.currPage=Number(this.currPage) +Number(1)
                    if(that.totallist.length == that.lists.length){
                        that.toast('数据已加载完')
                    }else {
                        var array = that.pagination(this.currPage,15,that.totallist)|| []
                        for (var i = 0; i < array.length; i++) {
                            that.lists.push(array[i])
                        }
                        //that.total()
                    }


                    this.loadinging = false;
                },2000)
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
    .indicator-text {
        font-size: 42px;
        text-align: center;
        width: 750px;
    }
    .indicator {
        margin-top: 16px;
        height: 60px;
        width: 60px;
        margin-left: 345px;
        color: blue;
    }
</style>
