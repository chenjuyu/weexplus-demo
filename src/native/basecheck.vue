<template>
    <div class="demo"  ref="cjy" >
      <!-- :style="cityExtendStyle"  <head :title="tit" @titleClick="clickT" :height="120" @rightClick="clickR">
            <text  slot="right" style="flex-direction: row;margin-top: 13px;">右边</text>
        </head> -->
        <head  @backClick="back" title="资料选择" :rightText="rightText" @rightClick="rightClick"></head>
        <div>
            <wxc-searchbar ref="wxc-searchbar"
                           @wxcSearchbarCancelClicked="wxcSearchbarCancelClicked"
                           @wxcSearchbarInputReturned="wxcSearchbarInputReturned"
                           @wxcSearchbarInputOnInput="wxcSearchbarInputOnInput"
                           @wxcSearchbarCloseClicked="wxcSearchbarCloseClicked"
                           @wxcSearchbarInputOnFocus="wxcSearchbarInputOnFocus"
                           @wxcSearchbarInputOnBlur="wxcSearchbarInputOnBlur"></wxc-searchbar>
        </div>
        <scroller style="flex:1;">
            <div class="margin">
                <!--      <text class="checked-text">选中项 {{checkedList.toString()}}</text>         v-for="(num,key) in list"
                <text>{{num.Customer}}</text>  -->
                        <wxc-checkbox-list :list="list"
                                             @wxcCheckBoxListChecked="wxcCheckBoxListChecked"></wxc-checkbox-list>


            </div>
        </scroller>
        <div style="left: 0;right: 0;bottom: 0;justify-content: center;align-items: center">
          <wxc-button text="确定"
                     @wxcButtonClicked="wxcButtonClicked"></wxc-button>
        </div>

     </div>
 </template>

 <script>
     import { WxcCheckbox,WxcCheckboxList,WxcButton,WxcSearchbar,Utils  } from 'weex-ui'
     const modal = weex.requireModule('modal');
     const net = weex.requireModule('net');
     const nav =weex.requireModule('navigator')
     const  pref=weex.requireModule('pref');
     export default {
         components:{ WxcCheckbox, WxcCheckboxList,WxcButton,WxcSearchbar },
        data () {
         return {
             rightText:'\ue667',
             currPage:1,
             sendtype:'',
             list:[],
             checkedList: []
         }
        }, props: {

             show:{
                 default:true
             },
             animationType: {
                 type: String,
                 default: 'push'
             }

         },
         created(){



         },

         computed: {
            /* cityExtendStyle () {
                 return Utils.uiStyle.pageTransitionAnimationStyle(this.animationType)
             } *///这个其实就是隐藏

         },mounted(){

         },

         methods: {
             onLoad(p){
                 var self=this
                 //self.alert(self.list.length)
                 //self.list.splice(0,self.list.length);
                 self.sendtype=p.send
                 net.post(pref.getString('ip')+'/select.do?'+self.sendtype,{currPage:self.currPage,param:''},{},function(){
                     //start
                 },function(e){
                     //success
                     //  self.back=e.res;
                     //  self.list.splice(0,self.list);
                     var array= e.res.obj;
                     for (var i=0;i<array.length;i++) {
                         var map = {};
                         if(self.sendtype=='getGoodsColor') {
                             map.title = array[i].Name;
                             map.value = array[i].ColorID;
                             map.checked = false;
                         }else if(self.sendtype=='getWarehouse'){
                             map.title = array[i].Name;
                             map.value = array[i].DepartmentID;
                             map.checked = false;
                         }else if(self.sendtype=='getGoodsType'){
                             map.title = array[i].Name;
                             map.value = array[i].GoodsTypeID;
                             map.checked = false;
                         }else if(self.sendtype=='getBrand'){
                             map.title = array[i].Name;
                             map.value = array[i].BrandID;
                             map.checked = false;
                         }else if(self.sendtype=='getGoodsSupplier'){
                             map.title = array[i].Name;
                             map.value = array[i].SupplierID;
                             map.checked = false;
                         }else if(self.sendtype=='getEmployee'){
                             map.title = array[i].Name;
                             map.value = array[i].EmployeeID;
                             map.checked = false;
                         }else if(self.sendtype=='getPosSalesGoods'){
                             map.title = array[i].Name;
                             map.value = array[i].GoodsID;
                             map.checked = false;
                         }
                         self.list.push(map);
                     }

                 },function(e){
                     //compelete

                 },function(){
                     //exception
                 });
                 //   this.alert('测试')

             },
             wxcCheckBoxListChecked (e){//为了不动weexui的组件，这里要再次 组装数据 已选中的
                 var self=this
                 this.checkedList.splice(0,this.checkedList.length)//清空后，再加，不然会重复添加 checkedList 每次点都会返回 全部已选的
                 for(var j=0;j<e.checkedList.length;j++) {
                     var map={}
                     for (var i = 0; i < this.list.length; i++) {
                         if(e.checkedList[j]==this.list[i].value){
                             if(self.sendtype=='getGoodsColor') {
                                 map.GoodsID = ''
                                 map.ColorID = e.checkedList[j]
                                 map.title = this.list[i].title
                                 map.checked = true
                                 map.tipqty = ''
                             }else {
                                 map.Name =this.list[i].title
                                 map.id=e.checkedList[j]
                             }
                             this.checkedList.push(map)
                         }
                     }
                 }
                // this.alert(JSON.stringify(this.checkedList))
                // this.checkedList = e.checkedList;
             }, wxcButtonClicked (e) {
                 //this.alert(this.checkedList)
                 let nav = weex.requireModule('navigator') ;

                 nav.backFull({item: this.checkedList},true)
               /*  this.show(false);
                 this.$emit('choice', { item: this.checkedList.toString() }); */
                // this.checkedList.splice(0,checkedList.length)

                 console.log(e)
             }, show(status = true, callback = null) {
                 const ref = this.$refs.cjy
                 if (this.animationType === 'push') {
                     Utils.animation.pageTransitionAnimation(ref, `translateX(${status ? -750 : 750}px)`, status, callback)
                 } else if (this.animationType === 'model') {
                     Utils.animation.pageTransitionAnimation(ref, `translateY(${status ? -Utils.env.getScreenHeight() : Utils.env.getScreenHeight()}px)`, status, callback)
                 }

             },
             back(){
                 this.show(false);
             },
             setValue () {
                 this.$refs['wxc-searchbar'].setValue('点击了手动设置输入框内容的开关');
             },
             wxcSearchbarInputOnFocus () {
                 modal.toast({ 'message': 'onfocus', 'duration': 1 });
             },
             wxcSearchbarInputOnBlur () {
                 modal.toast({ 'message': 'onbulr', 'duration': 1 });
             },
             wxcSearchbarCloseClicked () {
                 modal.toast({ 'message': 'close.click', 'duration': 1 });
             },
             wxcSearchbarInputOnInput (e) {

                 if(e.value.length <3)
                     return

                 this.value = e.value;
                 var self=this
                 setTimeout(()=>{
                     net.post(pref.getString('ip')+'/select.do?'+self.sendtype,{currPage:self.currPage,param:self.value},{},function(){
                         //start
                     },function(e){
                         //success
                         //  self.back=e.res;
                          self.list.splice(0,self.list.length);
                         var array= e.res.obj;
                         for (var i=0;i<array.length;i++) {
                             var map = {};
                             if(self.sendtype=='getGoodsColor') {
                                 map.title = array[i].Name;
                                 map.value = array[i].ColorID;
                                 map.checked = false;
                             }else if(self.sendtype=='getWarehouse'){
                                 map.title = array[i].Name;
                                 map.value = array[i].DepartmentID;
                                 map.checked = false;
                             }else if(self.sendtype=='getGoodsType'){
                                 map.title = array[i].Name;
                                 map.value = array[i].GoodsTypeID;
                                 map.checked = false;
                             }else if(self.sendtype=='getBrand'){
                                 map.title = array[i].Name;
                                 map.value = array[i].BrandID;
                                 map.checked = false;
                             }else if(self.sendtype=='getGoodsSupplier'){
                                 map.title = array[i].Name;
                                 map.value = array[i].SupplierID;
                                 map.checked = false;
                             }else if(self.sendtype=='getEmployee'){
                                 map.title = array[i].Name;
                                 map.value = array[i].EmployeeID;
                                 map.checked = false;
                             }else if(self.sendtype=='getPosSalesGoods'){
                                 map.title = array[i].Name;
                                 map.value = array[i].GoodsID;
                                 map.checked = false;
                             }
                             self.list.push(map);
                         }

                     },function(e){
                         //compelete

                     },function(){
                         //exception
                     });
                 },2000)
             },
             wxcSearchbarCancelClicked () {
                 modal.toast({ 'message': 'cancel.click', 'duration': 1 });
             },
             wxcSearchbarInputDisabledClicked () {
                 modal.toast({ 'message': 'input.onclick', 'duration': 1 });
             },
             wxcSearchbarDepChooseClicked () {
                 modal.toast({ 'message': 'dep.choose.click', 'duration': 1 });
             },rightClick(e){
                 this.wxcButtonClicked(e)
             }

         }
     }
 </script>

 <style scoped>

     .demo {
         position: fixed;
         width: 750px;
         background-color: #F2F3F4;
     }

 </style>