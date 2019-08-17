<template>
    <div>
        <div @click="open" style="justify-content: center;align-items: center;margin-top: 300px;">
            <text style="font-size: 35px;">上传图片</text>
        </div>

    </div>
</template>

<script>
    export default {
        data(){
            return{

            }
        },methods:{
            open(){


                var self=this;
                const photo = weex.requireModule('photo');
                photo.openCamera(500,800,'#000000',function(e){
                    self.src=e.path;
                    var param={};
                    var header={};
                    var path={};

                   var SalesID ='00AB'

                    path.file=e.path;
                    var net=weex.requireModule("net"); //&SalesID='+SalesID
                    net.postFile('http://192.168.43.53:8080/FPOS/common.do?uploadImages&SalesID='+SalesID,param,header,path,()=>{
                        //start
                    },(e)=>{
                        //succcess
                        var modal=weex.requireModule("modal")

                        modal.toast({message:'上传成功！url:'+e.res.obj})
                    },()=>{
                        //compelete

                    },()=>{
                        //exception
                        var modal=weex.requireModule("modal")
                        modal.toast({message:'上传异常！'})
                    })
                });

            }

        }
    }
</script>

<style scoped>

</style>