<template>


    <div style="border-radius: 10;border-width: 3;flex-direction: row" :style="{'border-color':theme}">
        <div  :class="[selectIndex==index? 'item_sel':'item']" :style="{'border-right-width':index==items.length-1?0:3,'background-color':getBg(index),'border-color':getBorderColor(index)}" @click="onitemclick(index)" v-for="(item,index) in items" >
            <text :style="{'color':selectIndex==index? '#FFF':'#000','font-size':'30'}">{{item}}</text>
        </div>
    </div>


</template>
<style src="../css/style.css"></style>
<style scoped>


    .item
    {
        flex: 1;
        align-items: center;
        justify-content: center;
        height: 60;
        border-right-width: 3;
        border-color: #1296db;
        font-size: 30;
    }
/*
background-color: #1296db;
*/

    .item_sel
    {
        flex: 1;
        align-items: center;
        justify-content: center;
        height: 60;
        background-color: orange;
        font-size: 30;
    }



</style>
<script>

    export default {
        props:{
            theme:{
                default:'red'
            },
            items:{
                default:['今天','昨天','一周']

            },
            height:50,
            selectIndex:{
                default:0
            }


        },
        data () {
            return {

                visiable:true,


            }
        },
        methods: {

            onitemclick(index)
            {
                this.selectIndex=index
                this.$emit('change',index);
            },
            getBorderColor(index)
            {
//                return this.theme
                if(this.items.length-1!=index)
                {
                    return this.theme
                }
                else
                {
                    return 'transparent';
                }
            },
            getBg(index)
            {
                if(this.selectIndex==index)
                {
                    return this.theme
                }
                else
                {
                    return 'transparent';
                }
            }



        },

        created:function(){

            this.visiable=!this.value=='';


        },
        ready:function()
        {



        },
//        watch: {
//
//
//            disabled:{
//                immediate: true,
//                handler (val) {
//
//                }
//            }
//        }
    }
</script>
