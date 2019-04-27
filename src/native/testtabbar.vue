<template>

    <div style="background-color: #5ac3ff" >

        <tabbar1 :tabItems="tabItems" @tabBarOnClick="tabBarOnClick"  :msg="msg"></tabbar1>


    </div>
</template>

<script>
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
                        title: 'tab1',
                        titleColor: '#000000',
                        icon: '',
                        image: 'http://gtms01.alicdn.com/tps/i1/TB1qw.hMpXXXXagXXXX9t7RGVXX-46-46.png',
                        selectedImage: 'http://gtms04.alicdn.com/tps/i4/TB16jjPMpXXXXazXVXX9t7RGVXX-46-46.png',
                        src:'index.js?No=123&madeby=ddddd',
                        //   src: 'component/tabbar/tabbar-item.js?itemId=tab1',
                        visibility: 'visible'//'visible',
                    },
                    {
                        index: 1,
                        title: 'tab2',
                        titleColor: '#000000',
                        icon: '',
                        image: 'http://gtms03.alicdn.com/tps/i3/TB1LEn9MpXXXXaUXpXX9t7RGVXX-46-46.png',
                        selectedImage: 'http://gtms02.alicdn.com/tps/i2/TB1qysbMpXXXXcnXXXX9t7RGVXX-46-46.png',
                        src: 'GridViewList.js',// ?No=123&madeby=ddddd'component/tabbar/tabbar-item.js?itemId=tab2',
                        visibility: 'hidden',
                    },
                    {
                        index: 2,
                        title: 'tab3',
                        titleColor: '#000000',
                        icon: '',
                        image: 'http://gtms01.alicdn.com/tps/i1/TB1B0v5MpXXXXcvXpXX9t7RGVXX-46-46.png',
                        selectedImage: 'http://gtms04.alicdn.com/tps/i4/TB1NxY5MpXXXXcrXpXX9t7RGVXX-46-46.png',
                        src: 'login.js',// ?index=1&userid=ddddd'component/tabbar/tabbar-item.js?itemId=tab3',
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
            tabBarOnClick: function (e) {
                console.log('tabBarOnClick', e.index)
            },diplay(){
                var baseURL ='root:'
                for(var i = 0; i < this.tabItems.length; i++) {
                    var tabItem = this.tabItems[i];
                    tabItem.src =baseURL+tabItem.src;
                }
            }

        }
    }
</script>
<style>


</style>