<template>
    
<div>
    <el-breadcrumb separator="/">
        <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>车辆状态</el-breadcrumb-item>
        <el-breadcrumb-item>车辆状态</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>
<div>
    <div class="demo-image">
        <div class="block" v-for="fit in outtimelist" :key="fit.id">
            <img src="@/assets/image/chuche.jpg" alt="123" style="width:210px;height:130px"  v-if="fit.carStatus==36">
            <img src="@/assets/image/weixiu.jpg" alt="123" style="width:210px;height:130px" v-if="fit.carStatus==37"> 
            <img src="@/assets/image/keyong.png" alt="123" style="width:210px;height:130px" v-if="fit.carStatus==38">
            <br/>
            <br/>
            <br/>
            <span class="demonstration" style="center">{{fit.carNum}}&nbsp;&nbsp;【{{fit.carStatusname}}】</span>
        </div>
    </div>
</div>
    <br/>
    <br/>
    <br/>
    <!--分页组件-->
    <el-row>
      <el-col :span="10" :push="6">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="p"
          :page-sizes="[4, 10, 15, 20]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-col>
    </el-row>
   </div>
</template>


<script>
import axios from 'axios';
export default {
     data() {
    return {
        dialogStatus: "",

        outtimelist:[
            {
                id: "",
                carNum: "",//车牌号外键  
                carStatus:"",//车辆状态
                carStatusname:"",
            }
        ],
         //分页
      p: 1,
      addDialogFormVisible: false,
      Employee: {},
      total: 0,
      size: 4,

        }
    },
     methods: {
          //列表
    outtimefindlist() {
      this.$axios.get("car/list", {
        params: {
          p: this.p,
          size: this.size
        }
      }).then(r => {
        this.outtimelist = r.data.data.list
        this.total = r.data.data.total
        for(var i = 0;i<this.outtimelist.size;i++){
            if(this.outtimelist[i].carStatus == 36){
                this.outtimelist[i].carStatus = '出车';
            }else if(this.outtimelist[i].carStatus == 37){
               this.outtimelist[i].carStatus = '维修';
            }else if(this.outtimelist[i].carStatus == 38){
                this.outtimelist[i].carStatus = '可用';
            }    
        }

      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.outtimefindlist();
    },
    handleSizeChange(val) {
      this.size = val;
      this.outtimefindlist();
    },
    },
    created() {
    this.outtimefindlist();
  }
    
}
</script>


<style>
    .block{
        margin-left: 7%;
        float: left;
        
    }
    .demo-image{
        margin-top: 50px;

        width: 1200px;
        height: 300px;
        
    }
    .demonstration{
        margin-left: 35px;
    }

</style>
