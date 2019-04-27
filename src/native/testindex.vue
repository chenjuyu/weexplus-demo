<template>
    <div class="wxc-demo">
        <scroller class="scroller">
            <wxc-checkbox title="默认"></wxc-checkbox>
            <wxc-checkbox title="默认选中"
                          :checked="true"></wxc-checkbox>
            <wxc-checkbox title="未选中不可更改"
                          :disabled="true"></wxc-checkbox>
            <wxc-checkbox title="选中不可更改"
                          :disabled="true"
                          :checked="true"></wxc-checkbox>
            <div class="margin">
                <wxc-checkbox-list :list="list"
                                   @wxcCheckBoxListChecked="wxcCheckBoxListChecked"></wxc-checkbox-list>
                <text class="checked-text">选中项 {{checkedList.toString()}}</text>
            </div>
        </scroller>

        <wxc-button text="确定"
                    @wxcButtonClicked="wxcButtonClicked"></wxc-button>


    </div>
</template>
<style scoped>
    .wxc-demo {
        position: absolute;
        top: 0;
        right: 0;
        left: 0;
        bottom: 0;
        background-color: #ffffff;
    }
    .scroller {
        flex: 1;
    }
    .margin {
        margin-top: 60px;
    }
    .checked-text {
        font-size: 30px;
        color: #333333;
        margin-top: 30px;
        margin-bottom: 30px;
        margin-left: 24px;
    }
</style>

<script>
    import { WxcButton,WxcCheckbox,WxcCheckboxList } from 'weex-ui'
    let nav = weex.requireModule('navigator');
    let modal = weex.requireModule('modal');
    const test=weex.requireModule('test');



    export default {
        components: { WxcButton,WxcCheckbox, WxcCheckboxList },
        data: () => ({
            list: [
                { title: '选项1', value: 1 },
                { title: '选项2', value: 2, checked: true },
                { title: '选项3', value: 3 },
                { title: '选项4', value: 4 }
            ],
            checkedList: [2]
        }),
        methods: {
            wxcCheckBoxListChecked (e) {
                this.checkedList = e.checkedList;
            },
            wxcButtonClicked(e){
                console.log(e);
                //{index:1,userid:'中文内容'}
                debugger
                var param={};
                param.index='1';
                param.userid='中文内容';
                nav.pushParam('root:login.js',param);
                //   nav.pushParam('login.js',param);


            }
        },created() {
            /*
               test.testpint((e)=>{
                 console.log("返回的内容:"+e);
               },"给android的一个请求条件");
          */

        }
    }
</script>