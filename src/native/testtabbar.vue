<template>

    <div style="background-color: #5ac3ff" >

        <tabbar1 :tabItems="tabItems" @tabBarOnClick="tabBarOnClick"  :msg="msg"></tabbar1>


    </div>
</template>

<script>
    var page=weex.requireModule("page")
    import tabbar1 from './component/tabbar.vue'
    export default {
        components :{
            tabbar1
        },
        data () {
            return {
                dir: 'examples',
                msg:'kbkbkbfffffff',
                tabItems: [
                    {
                        index: 0,
                        title: '销售管理',
                        titleColor: '#000000',
                        icon: '',
                        image: '\ue631',//http://gtms01.alicdn.com/tps/i1/TB1qw.hMpXXXXagXXXX9t7RGVXX-46-46.png',
                        selectedImage:'\ue631', //'http://gtms04.alicdn.com/tps/i4/TB16jjPMpXXXXazXVXX9t7RGVXX-46-46.png',
                        src:'salesmanager.js',
                        //   src: 'component/tabbar/tabbar-item.js?itemId=tab1',
                        visibility: 'visible'//'visible',
                    },
                    {
                        index: 1,
                        title: '采购管理',
                        titleColor: '#000000',
                        icon: '',
                        image:'\ue62a', //'http://gtms03.alicdn.com/tps/i3/TB1LEn9MpXXXXaUXpXX9t7RGVXX-46-46.png',
                        selectedImage: '\ue62a',//'http://gtms02.alicdn.com/tps/i2/TB1qysbMpXXXXcnXXXX9t7RGVXX-46-46.png',
                        src: 'purchasemanager.js',// ?No=123&madeby=ddddd'component/tabbar/tabbar-item.js?itemId=tab2',
                        visibility: 'hidden',
                    },
                    {
                        index: 2,
                        title: '主页',
                        titleColor: '#000000',
                        icon: '',
                        image: '\ue608',//'http://gtms01.alicdn.com/tps/i1/TB1B0v5MpXXXXcvXpXX9t7RGVXX-46-46.png',
                        selectedImage:'\ue608', //'http://gtms04.alicdn.com/tps/i4/TB1NxY5MpXXXXcrXpXX9t7RGVXX-46-46.png',
                        src: 'GridViewList.js',// ?index=1&userid=ddddd'component/tabbar/tabbar-item.js?itemId=tab3',
                        visibility: 'hidden',
                    },  {
                        index: 3,
                        title: '数据统计',
                        titleColor: '#000000',
                        icon: '',
                        image: '\ue60c',//'http://gtms03.alicdn.com/tps/i3/TB1LEn9MpXXXXaUXpXX9t7RGVXX-46-46.png',
                        selectedImage:'\ue60c', //'http://gtms02.alicdn.com/tps/i2/TB1qysbMpXXXXcnXXXX9t7RGVXX-46-46.png',
                        src: 'datareport.js',// ?No=123&madeby=ddddd'component/tabbar/tabbar-item.js?itemId=tab2',
                        visibility: 'hidden',
                    },
                    {
                        index: 4,
                        title: '基本资料',
                        titleColor: '#000000',
                        icon: '',
                        image: '\ue634',//'http://gtms01.alicdn.com/tps/i1/TB1B0v5MpXXXXcvXpXX9t7RGVXX-46-46.png',
                        selectedImage:'\ue634',// 'http://gtms04.alicdn.com/tps/i4/TB1NxY5MpXXXXcrXpXX9t7RGVXX-46-46.png',
                        src: 'basemanager.js',// ?index=1&userid=ddddd'component/tabbar/tabbar-item.js?itemId=tab3',
                        visibility: 'hidden',
                    }
                ],
            }
        },

        created() {

            //  var baseURL ='root:' //getBaseURL(this) baseURL +
           // this.alert('aaaa');
            this.diplay();

        },
        methods: {
            onLoad(p){
                page.doubleBack()//拦截返回键触发的回调
                page.enableBackKey(true)
                page.setBackKeyCallback(()=>{
                  //  page.enableBackKey(true);
                    page.exit();
                })
            }
            ,tabBarOnClick: function (e) {
                this.log('tabBarOnClick:'+e.index)
                if(!e.src){
                    this.toast('功能开发中')
                }
            },diplay(){
                var baseURL ='root:'
                for(var i = 0; i < this.tabItems.length; i++) {
                    var tabItem = this.tabItems[i];
                    if(tabItem.src) {
                        tabItem.src = baseURL + tabItem.src;
                    }
                }
            }

        }
    }
</script>
<style>


</style>