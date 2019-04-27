<template>
<div class="layout">
    <div style="height: 130px">
        <head></head>
    </div>
  <text class="message">这是userid的值：{{userid}}</text>
   <text>这是index的值：{{index}}</text>
    <text>这是param的值：{{param}}</text>

</div>
</template>
<script>

export default {
  data(){
    return{
    userid:'',
    index:'0',
    param:''
    }
  },
  props: {
      title:{
          default:'登录日志'
      }
  },
  methods: {
  onLoad(param) {
   // debugger;
      console.log(param);
   this.param=param;

  }
  }   


}
</script>
<style>
 .layout{
    width: 750px;
 }

</style>

