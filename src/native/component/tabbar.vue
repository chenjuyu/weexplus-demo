<template>
    <div class="wrapper">
        <embed v-for="(item,i) in tabItems"
               :src="item.src"
               :key="i"
               type="weex"
               :style="{ visibility:item.visibility}"
               class="content">
        </embed>

        <div class="tabbar" append="tree">
            <tabitem
                    v-for="item in tabItems"
                    :key="item.index"
                    :index="item.index"
                    :icon="item.icon"
                    :title="item.title"
                    :titleColor="item.titleColor"
                    @tabItemOnClick="tabItemOnClick"
            ></tabItem>
        </div>
         </div>
</template>

      <style scoped>
         .wrapper {
             width: 750px;
             background-color:#ffffff;
             display: block;
             position: absolute;
             top: 0;
             left: 0;
             right: 0;
             bottom: 0;
         /*   justify-content: center;align-items: center  position: absolute; */
         }
         .content {
             position: absolute;
             top: 0;
             left: 0;
             right: 0;
             bottom: 0;
             margin-top: 0;
            margin-bottom: 120px; /*88*/
         }
         .tabbar {
             flex-direction: row;
             position: fixed;
             bottom: 0;
             left: 0;
             right: 0;
             height: 120px; /*80px*/
             color: #ffffff;
             background-color:red;
         }
      </style>

      <script>
       import  tabitem from './tabitem.vue'
         export default {
             components: {
                tabitem
             },
             props:{
                 tabItems:{default:[]},
                 msg:{default:'ccc'},
                 selectedColor: { default: '#1EA5FC' },//#ff0000
                 unselectedColor: { default: '#000000' }

             },
             data: function () {
                 return {
                     selectedIndex: 0
                 }
             },

             created() {

                 this.select(this.selectedIndex);
             },
             methods: {
                 tabItemOnClick: function (e) {
                     this.selectedIndex = e.index;
                     this.select(e.index);
                     this.$emit('tabBarOnClick', e);
                 },
                 select: function(index) {
                     //this.tabItems.splice(0,this.tabItems.length)
                   //  var map={};
                    // map.title='fffffffffffffffffffff'
                    // this.tabItems.push(map)
                     for(var i = 0; i < this.tabItems.length; i++) {
                         var tabItem = this.tabItems[i];
                         if(i == index){
                             tabItem.icon = tabItem.selectedImage;
                             tabItem.titleColor = this.selectedColor;
                             tabItem.visibility = 'visible';
                         }
                         else {
                             tabItem.icon = tabItem.image;
                             tabItem.titleColor = this.unselectedColor;
                             tabItem.visibility = 'hidden';
                         }
                     }
                 },
             }
         }
      </script>