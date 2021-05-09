<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>出车记录</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="5">
        <el-input placeholder="请输入要查询的车牌" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="findOutCar"></el-button>
        </el-input>
      </el-col>
      <el-col :span="8">
        <el-button type="primary" @click="outCar">出车记录登记</el-button>
        <el-button type="success" @click="inCar">回车记录登记</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column prop="carNum" label="车牌号码" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="text" label="部门" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="outTime" label="出车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="preInTime" label="预计回车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="inTime" label="回车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="followPerson" label="随行人员" min-width></el-table-column>

        <el-table-column prop="driverId" label="驾驶员" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="destination" label="目的地" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="mileage" label="本次行程" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="name" label="创建人员" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column label="操作" min-width="60">
          <template slot-scope="scope">
            <el-tooltip content="查看详细" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="showCar(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>

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
export default {
  name: "departureRecord",
  data() {
    return {
      //分页要的东西
      p: 1,
      tableData: [],
      total: 5,
      size: 5,
      //模糊查询的文本框内容
      searchText: '',
      //驾驶员的名字的数据
      driverName:{},
    }
  },
  methods: {
//分页的出车记录列表
    findOutCar() {
      this.$axios.get("departureRecord/list", {
        params: {
          p: this.p,
          searchText: this.searchText,
          size: this.size
        }
      }).then(r => {
        console.log(r)
        this.tableData = r.data.data.list
        this.total = r.data.data.total
      })
    },
    //查询所有的车牌号
    getFindDriver(){
      this.$axios.get("driver/getAll").then(r => {
        this.driverName=r.data.data
      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.findOutCar();
    },
    handleSizeChange(val) {
      this.size = val;
      this.findOutCar();
    },
    //出车记录
    outCar() {

    },
    //回车记录
    inCar() {

    },
    //查看回车记录
    showCar() {

    }
  },
  created() {
    this.getFindDriver();
    this.findOutCar();
  }
}
</script>

<style scoped>

</style>
