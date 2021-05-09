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
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="carName" label="车牌号码" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="deptName" label="部门" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="outTime" label="出车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="preInTime" label="预计回车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="inTime" label="回车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="followPerson" label="随行人员" min-width></el-table-column>
        <el-table-column prop="driverName" label="驾驶员" min-width :show-overflow-tooltip="true">
        </el-table-column>

        <el-table-column prop="destination" label="目的地" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="mileage" label="本次行程" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="employeeName" label="创建人员" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip content="出车信息修改" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="showCar(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="查看详细" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-view" circle @click="showCar(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>

      </el-table>
    </template>

    <el-dialog title="出车记录登记" :visible.sync="addDialogFormVisible" center width="50%">
      <el-form :model="addData" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="车牌号码:">
              <el-input v-model="addData.carId"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="部门:">
              <el-input v-model="addData.deptId"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:">
              <el-date-picker v-model="addData.outTime" type="datetime"
                              placeholder="选择日期时间" align="right">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="预计回车时间:">
              <el-date-picker v-model="addData.preInTime" type="datetime"
                              placeholder="选择日期时间" align="right">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="随行人员:">
              <el-input v-model="addData.followPerson"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="驾驶员:">
              <el-input v-model="addData.driverId"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="目的地:">
              <el-input v-model="addData.destination"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="开始里程(公里):">
              <el-input v-model="addData.mileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="出车原因:">
              <el-input v-model="addData.outCarReason" type="textarea"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doOutCar()">确 定</el-button>
      </span>
    </el-dialog>

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
      driverName: [],
      //车牌名字的数据
      carNum: [],
      //所有的部门
      dept: [],
      //字典的数据
      children: [],
      //所有员工
      Employee:[],
      //模态框
      addDialogFormVisible: false,//添加模态框
      updateDialogFormVisible: false,//修改模态框
      menuRoleFormVisible: false,//分配权限模态框
      //出车文本框对象
      addData: {},
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
        this.tableData = r.data.data.list;
        this.total = r.data.data.total;
        this.tableData.forEach(e1 => {
        //   //查询驾驶员
          this.driverName.forEach(e2 => {
            if (e1.driverId == e2.id) {
              e1.driverName = e2.employeeName
            }
          });
          //查询车牌号码
          this.carNum.forEach(e3 => {
            if (e1.carId == e3.id) {
              e1.carName = e3.carNum
            }
          });
          //查询部门
          this.dept.forEach(e4 => {
            if (e1.deptId == e4.id) {
              e1.deptName = e4.text
            }
          });
          //查询员工
          this.Employee.forEach(e5 => {
            if (e1.employeeId == e5.id) {
              e1.employeeName = e5.name
            }
          });
        })
      })
    },
    //查询所有的驾驶员
    getFindDriver() {
      this.$axios.get("driver/getAll").then(r => {
        this.driverName = r.data.data
      })
    },
    //查询所有的车牌号
    getFindCar() {
      this.$axios.get("car/getAll").then(r => {
        this.carNum = r.data.data
      })
    },
    //查询所有的部门
    getFindDictionary() {
      this.$axios.get("dictionary/menu").then(r => {
        this.children = r.data.data;
        this.children.forEach(r2 => {
          if (r2.text == "部门") {
            this.dept = r2.children;
          }
        })
      })
    },
    //查询所有员工
    getFindEmployee() {
      this.$axios.get("employee/getAll").then(r => {
        this.Employee=r.data.data;
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
      this.addDialogFormVisible = true;
    },
    //回车记录
    inCar() {

    },
    //查看回车记录
    showCar() {

    },
    //出车记录填写确定
    doOutCar() {

    }
  },
  created() {
    Promise.all([
      this.getFindDriver(),
      this.getFindCar(),
      this.getFindDictionary(),
      this.getFindEmployee(),
    ]).then(res => {
      setTimeout(() => {
        this.findOutCar();//延时结束loading
      }, 100);
    })
  }
}
</script>

<style scoped>

</style>
