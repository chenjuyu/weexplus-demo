<template>
    <div class="demo"  ref="cjy" >
      <!-- :style="cityExtendStyle"  <head :title="tit" @titleClick="clickT" :height="120" @rightClick="clickR">
            <text  slot="right" style="flex-direction: row;margin-top: 13px;">右边</text>
        </head> -->
        <head :autoback="false" @backClick="back" rightButton=""></head>
        <div>
            <wxc-searchbar ref="wxc-searchbar"
                           @wxcSearchbarCancelClicked="wxcSearchbarCancelClicked"
                           @wxcSearchbarInputReturned="wxcSearchbarInputReturned"
                           @wxcSearchbarInputOnInput="wxcSearchbarInputOnInput"
                           @wxcSearchbarCloseClicked="wxcSearchbarCloseClicked"
                           @wxcSearchbarInputOnFocus="wxcSearchbarInputOnFocus"
                           @wxcSearchbarInputOnBlur="wxcSearchbarInputOnBlur"></wxc-searchbar>
        </div>
        <scroller>
            <div class="margin">
                <!--               v-for="(num,key) in list"
                <text>{{num.Customer}}</text>  -->
                        <wxc-checkbox-list :list="list"
                                             @wxcCheckBoxListChecked="wxcCheckBoxListChecked"></wxc-checkbox-list>
                          <text class="checked-text">选中项 {{checkedList.toString()}}</text>

            </div>

         <wxc-button text="确定"
                     @wxcButtonClicked="wxcButtonClicked"></wxc-button>
            </scroller>
     </div>
 </template>

 <script>
     import { WxcCheckbox,WxcCheckboxList,WxcButton,WxcSearchbar,Utils  } from 'weex-ui'
     const modal = weex.requireModule('modal');

     export default {
         components:{ WxcCheckbox, WxcCheckboxList,WxcButton,WxcSearchbar },
        data () {
         return {
             name: "basecheck",
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
             var self=this
             //self.alert(self.list.length)
             //self.list.splice(0,self.list.length);
             const net = weex.requireModule('net');
             net.post('http://192.168.1.102:8080/FPOS/select.do?getCustomer',{currPage:"1"},{},function(){
                 //start
             },function(e){
                 //success
                 //  self.back=e.res;
                 //  self.list.splice(0,self.list);
               var array= e.res.obj;

                 for (var i=0;i<array.length;i++) {
                     var map = {};
                     map.title = array[i].Customer;
                     map.value=array[i].CustomerID;
                     map.checked=false;
                     self.list.push(map);
                 }

             },function(e){
                 //compelete

             },function(){
                 //exception
             });
           //   this.alert('测试')


         },

         computed: {
            /* cityExtendStyle () {
                 return Utils.uiStyle.pageTransitionAnimationStyle(this.animationType)
             } *///这个其实就是隐藏

         },mounted(){

         },

         methods: {
             onLoad(p){
              this.alert(p)

             },
             wxcCheckBoxListChecked (e){
                 this.checkedList = e.checkedList;
             }, wxcButtonClicked (e) {
                 //this.alert(this.checkedList)
                 let nav = weex.requireModule('navigator') ;
                 nav.backFull({"userid":"123","CustomerID":"00A","Customer":"小陈",item: this.checkedList.toString()},true)
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
                 this.value = e.value;
             },
             wxcSearchbarCancelClicked () {
                 modal.toast({ 'message': 'cancel.click', 'duration': 1 });
             },
             wxcSearchbarInputDisabledClicked () {
                 modal.toast({ 'message': 'input.onclick', 'duration': 1 });
             },
             wxcSearchbarDepChooseClicked () {
                 modal.toast({ 'message': 'dep.choose.click', 'duration': 1 });
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