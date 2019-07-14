<template>
    <div class="wxc-demo">
        <head :rightText="rightText" title="资料选择"  @rightClick="rightClick"></head>
        <div> <input class="input" type="text" placeholder="search" return-key-type="search" @return="search" v-model="text" /></div>
        <list>
        <cell class="cell" v-for="(lst,index) in list" @click="onSelected(lst,index)">
            <text class="item">{{lst.Name}}</text>
        </cell>
        </list>
    </div>
</template>

<script>
    import { WxcCity } from 'weex-ui';
    const nav=weex.requireModule('navigator');
    const net = weex.requireModule('net');
    const  pref=weex.requireModule('pref');
    let send=''
    export default {
        components: { WxcCity },
      data(){
          return {  name: "base",
              list:[],
              text:'',
              cmap:{},//查询条件
              mult:false, //多颜色录入标志

          }
      },   methods: {
            onLoad(p){

                var self=this

                self.list.splice(0,self.list.length)
                self.mult=p.mult
              //  self.send =p.send
                self.cmap.send=p.send  //存起来
                if(p.send==='Type') {
                    var ls=["批发", "订货", "配货", "补货"]
                    var lskey=["PriceType", "OrderPriceType", "AllotPriceType", "ReplenishType"]
                  for(var i=0;i<ls.length;++i) {
                      var map={}
                      map.Name=ls[i]
                      map.id=lskey[i]
                      self.list.push(map)
                  }
                    return
                }





                //self.alert(self.list.length)
                //self.list.splice(0,self.list.length);

                var Type=p.Type
                var customerid=p.customerid


                net.post(pref.getString('ip')+'/select.do?'+p.send,{"currPage":"1","param":p.condition,"Type":Type,"customerid":customerid},{},function(){
                    //start
                },function(e){
                    //success
                    //  self.back=e.res;
                    //  self.list.splice(0,self.list);
                   /* self.alert(e)
                    if (e){
                        return
                    } */
                    var array= e.res.obj;
                    for (var i=0;i<array.length;i++) {
                        var map = {};
                       if(p.send==='getEmployee'){
                        map.id   = array[i].EmployeeID;
                        map.Name = array[i].Name;
                        self.list.push(map);
                       }else if(p.send==='getVip'){
                           map.id=array[i].VIPID;
                           map.Name = array[i].Name;
                           map.Code=array[i].Code
                           map.DiscountRate=array[i].DiscountRate
                           map.PointRate=array[i].PointRate
                           map.UsablePoint=array[i].UsablePoint
                           self.list.push(map);
                       } else if(p.send==='getPosSalesGoods' || p.send==='getSalesGoods'){
                           map.id=array[i].GoodsID;
                           map.Name = array[i].Name;
                           map.Code=array[i].Code
                           map.RetailSales=array[i].RetailSales
                           map.UnitPrice=array[i].UnitPrice
                           map.Discount=array[i].Discount
                           map.DiscountFlag=array[i].DiscountFlag
                           map.sizIndex=array[i].sizIndex
                           self.list.push(map);
                       }else if (p.send==='getColorByGoodsCode'){
                           map.id=array[i].ColorID;
                           map.Name = array[i].Name;
                           self.list.push(map);

                       }
                       else if (p.send==='getSizeByGoodsCode'){
                           map.id=array[i].SizeID;
                           map.Name = array[i].Name;
                           map.Code=array[i].SizeCode
                           map.x=array[i].x
                           self.list.push(map);

                       } else if (p.send==='getCustomer'){
                           map.id=array[i].CustomerID
                           map.Name =array[i].Name
                           map.DiscountRate=array[i].DiscountRate
                           map.DistrictID=array[i].DistrictID
                           map.OrderDiscount=array[i].OrderDiscount
                           map.AllotDiscount=array[i].AllotDiscount
                           map.ReplenishDiscount=array[i].ReplenishDiscount
                           self.list.push(map);
                       } else if(p.send==='getWarehouse'){
                           map.id=array[i].DepartmentID
                           map.Name=array[i].Name
                           map.MustExistsGoodsFlag=array[i].MustExistsGoodsFlag
                           self.list.push(map);
                       } else if (p.send==='getBrand'){
                           map.id=array[i].BrandID
                           map.Name=array[i].Name
                           self.list.push(map);
                       }
                    }

                },function(e){
                    //compelete

                },function(){
                    //exception
                });



            },
            onSelected(lst,index){

                    nav.backFull(lst, false);
            },search(){
                if(this.text!=''){
                   let self=this  //简化写法
                    self.cmap.condition=self.text
                    self.cmap.mult=self.mult
                    self.onLoad(self.cmap)
                /*    const net = weex.requireModule('net');
                    net.post(pref.getString('ip')+'/select.do?'+send,{"currPage":"1","param":this.text},{},function(){
                        //start
                    },function(e){
                        //success
                        //  self.back=e.res;
                        //  self.list.splice(0,self.list);
                        var array= e.res.obj;
                        if(array.length>0)
                        { self.list.splice(0,self.list.length)}

                        for (var i=0;i<array.length;i++) {
                            var map = {};
                            if(send==='getEmployee'){
                                map.id=array[i].EmployeeID;
                                map.Name = array[i].Name;
                                self.list.push(map);
                            }else if(send==='getVip'){
                                map.id=array[i].vipId;
                                map.Name = array[i].Name;
                                map.DiscountRate=array[i].DiscountRate
                                map.PointRate=array[i].PointRate
                                map.UsablePoint=array[i].UsablePoint
                                self.list.push(map);
                            }else if(p.send==='getPosSalesGoods'){
                                map.id=array[i].GoodsID;
                                map.Name = array[i].Name;
                                map.Code=array[i].Code
                                map.RetailSales=array[i].RetailSales
                                map.UnitPrice=array[i].UnitPrice
                                map.Discount=array[i].Discount
                                map.DiscountFlag=array[i].DiscountFlag
                                self.list.push(map);
                            }else if (p.send==='getGoodsColor'){
                                map.id=array[i].ColorID;
                                map.Name = array[i].Name;
                                self.list.push(map);

                            }
                            else if (p.send==='getSizeByGoodsCode'){
                                map.id=array[i].SizeID;
                                map.Name = array[i].Name;
                                map.Code=array[i].SizeCode
                                self.list.push(map);

                            }else if (p.send==='getCustomer'){
                                map.id=array[i].CustomerID
                                map.Name =array[i].Name
                                map.DiscountRate=array[i].DiscountRate
                                map.DistrictID=array[i].DistrictID
                                map.OrderDiscount=array[i].OrderDiscount
                                map.AllotDiscount=array[i].AllotDiscount
                                map.ReplenishDiscount=array[i].ReplenishDiscount
                                self.list.push(map);
                            } else if(p.send==='getWarehouse'){
                                map.DepartmentID=array[i].DepartmentID
                                map.Name=array[i].Name
                                map.MustExistsGoodsFlag=array[i].MustExistsGoodsFlag
                                self.list.push(map);
                            } else if (p.send==='getBrand'){
                                map.BrandID=array[i].BrandID
                                map.Name=array[i].Name
                                self.list.push(map);
                            }
                        }

                    },function(e){
                        //compelete

                    },function(){
                        //exception
                    });  */
                }
            },
            showGroupCity () {
                this.cityStyleType = 'group'
                this.$refs['wxcCity'].show();
            },
            citySelect (e) {
                this.currentCity = e.item;
            },
            onInput (e) {
            }
        }





    }
</script>

<style scoped>
    .input {
        border-width: 1px;

        height: 100px;
    }
    .cell{

        align-items: center;
        justify-content: center;
    }
.item{
   line-height: 80px;
    border-bottom-width: 1px;
    border-bottom-color: #eeeeee;
    width: 750px;
    align-items: center;
    justify-content: center;
    font-size: 30px;

}
</style>