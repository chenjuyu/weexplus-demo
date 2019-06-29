<template>
    <div class="wxc-demo">
        <scroller class="scroller">
            <wxc-radio :list="list" @wxcRadioListChecked="wxcRadioListChecked"></wxc-radio>
            <text class="radio-text">checkedItem: {{checkedInfo}}</text>
            <category title="Radio不可选"></category>
            <wxc-radio :list="list2"></wxc-radio>
        </scroller>
    </div>
</template>

<script>
    import { WxcRadio } from 'weex-ui'
    export default {
        components: { WxcRadio },
        data: () => ({
            list: [
                { title: '选项1', value: 1 },
                { title: '选项2', value: 2, checked: true },
                { title: '选项3', value: 3 },
                { title: '选项4', value: 4 },
            ],
            list2: [
                { title: '未选不可修改', value: 5, disabled: true },
                { title: '已选不可修改', value: 6, disabled: true, checked: true },
            ],
            checkedInfo: { title: '选项2', value: 2 }
        }),
        methods: {
            wxcRadioListChecked (e) {
                this.checkedInfo = e;
                var pop=weex.requireModule("centerpop")
                pop.dismiss();
            }
        }
    }
</script>
