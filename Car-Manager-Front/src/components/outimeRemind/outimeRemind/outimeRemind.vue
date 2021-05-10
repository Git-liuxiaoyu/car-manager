<template>
    
   <div>
        <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>到期提醒</el-breadcrumb-item>
      <el-breadcrumb-item>到期提醒</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>

    <template>
      <el-table :data="outtimelist" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">

        <el-table-column fixed prop="carNum" label="车牌号码" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="outDate" label="到期时间" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="typename" label="提醒类别" min-width :show-overflow-tooltip="true"></el-table-column>

      </el-table>
    </template>
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
          :page-sizes="[5, 10, 15, 20]"
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
                cardId: "",
                carNum: "",//车牌号外键  
                outDate:"",//到期时间
                type:"",//到期类型
                typename:"",//到期类型名
            }
        ],
         //分页
      p: 1,
      addDialogFormVisible: false,
      Employee: {},
      total: 0,
      size: 5,

        }
    },
     methods: {
          //列表
    outtimefindlist() {
      this.$axios.get("outimeRemind/list", {
        params: {
          p: this.p,
          size: this.size
        }
      }).then(r => {
          console.log("分页数据")
          console.log(r)
        this.outtimelist = r.data.data.list
        this.total = r.data.data.total
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

</style>
