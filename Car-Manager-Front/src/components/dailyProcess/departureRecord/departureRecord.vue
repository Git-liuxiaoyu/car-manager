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
                highlight-current-row
                @current-change="sub" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="carName" label="车牌号码" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="deptName" label="部门" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="outTime" label="出车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="preInTime" label="预计回车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="inTime" label="回车时间" min-width :show-overflow-tooltip="true">
          <template slot-scope="scope">{{ scope.row.inTime == null ? '未回车' : scope.row.inTime }}</template>
        </el-table-column>
        <el-table-column prop="followPerson" label="随行人员" min-width></el-table-column>
        <el-table-column prop="driverName" label="驾驶员" min-width :show-overflow-tooltip="true">
        </el-table-column>

        <el-table-column prop="destination" label="目的地" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="mileage" label="本次行程" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="employeeName" label="创建人员" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip content="出车信息修改" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="updateOutCar(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="查看详细" placement="bottom" effect="light" v-if="scope.row.inTime!=null">
              <el-button type="primary" icon="el-icon-view" circle @click="showCar(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="查看详细" placement="bottom" effect="light" v-if="scope.row.inTime==null">
              <el-button type="primary" icon="el-icon-view" circle @click="showCarEqNull"></el-button>
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
              <el-select v-model="addData.carId" placeholder="请选择">
                <el-option v-for="item in availableCar" :key="item.id"
                           :label="item.carNum" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="部门:">
              <el-select v-model="addData.deptId" placeholder="请选择">
                <el-option v-for="item in dept" :key="item.id"
                           :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:">
              <el-date-picker v-model="addData.outTime" type="datetime"
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="预计回车时间:">
              <el-date-picker v-model="addData.preInTime" type="datetime"
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
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
              <el-select v-model="addData.driverId" placeholder="请选择">
                <el-option v-for="item in driverName" :key="item.id"
                           :label="item.employeeName" :value="item.id">
                </el-option>
              </el-select>
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


    <!--查看详细-->
    <el-drawer
      title="出车记录信息"
      :visible.sync="showOutCarFormVisible"
      direction="rtl"
      size="50%">
      <hr/>
      <el-form :model="showData" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="车牌号码:">
              <el-input v-model="showData.carName" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="部门:">
              <el-input v-model="showData.deptName" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:">
              <el-date-picker v-model="showData.outTime" type="datetime" disabled
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="预计回车时间:">
              <el-date-picker v-model="showData.preInTime" type="datetime" disabled
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="回车时间:">
              <el-date-picker v-model="showData.outTime" type="datetime" disabled
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="驾驶员:">
              <el-select v-model="showData.driverId" placeholder="请选择" disabled>
                <el-option v-for="item in driverName" :key="item.id"
                           :label="item.employeeName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="11">
            <el-form-item label="随行人员:">
              <el-input v-model="showData.followPerson" disabled></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="11" :push="1">
            <el-form-item label="目的地:">
              <el-input v-model="showData.destination" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="23">
            <el-form-item label="出车原因:">
              <el-input v-model="showData.outCarReason" type="textarea" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11">
            <el-form-item label="开始里程(公里):">
              <el-input v-model="showData.mileage" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11" :push="1">
            <el-form-item label="回车里程(公里):">
              <el-input v-model="showData.returnMileage" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11">
            <el-form-item label="本次里程(公里):">
              <el-input v-model="showData.zongMileage" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11" :push="1">
            <el-form-item label="停放位置:">
              <el-input v-model="showData.stopPosition" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11">
            <el-form-item label="备注:">
              <el-input v-model="showData.remarks" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11" :push="1">
            <el-form-item label="创建人:">
              <el-input v-model="showData.employeeName" disabled></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
    </el-drawer>

    <el-dialog title="回车记录登记" :visible.sync="addInCarFormVisible" center width="50%">
      <el-form :model="subInCar" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="车牌号码:">
              <el-input v-model="subInCar.carName" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="驾驶员:">
              <el-input v-model="subInCar.driverName" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:">
              <el-date-picker v-model="subInCar.outTime" type="datetime" disabled
                              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" align="right">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="回车时间:">
              <el-date-picker v-model="subInCar.inCarTime" type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" align="right">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="开始里程(公里):">
              <el-input v-model="subInCar.mileage" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="回车里程(公里):">
              <el-input v-model="subInCar.returnMileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="停放位置:">
              <el-input v-model="subInCar.stopPosition"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="备注:">
              <el-input v-model="subInCar.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addInCarFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doInCar()">确 定</el-button>
      </span>
    </el-dialog>

    <!--查看详细-->
    <el-drawer
      title="出车信息修改"
      :visible.sync="outCarFormVisible"
      direction="ltr"
      size="50%">
      <hr/>
      <el-form :model="outCarData" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="车牌号码:">
              <el-select v-model="outCarData.carId" placeholder="请选择" disabled>
                <el-option v-for="item in carNum" :key="item.id"
                           :label="item.carNum" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="部门:">
              <el-select v-model="outCarData.deptId" placeholder="请选择">
                <el-option v-for="item in dept" :key="item.id"
                           :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:">
              <el-date-picker v-model="outCarData.outTime" type="datetime"
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="预计回车时间:">
              <el-date-picker v-model="outCarData.preInTime" type="datetime"
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="随行人员:">
              <el-input v-model="outCarData.followPerson"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="驾驶员:">
              <el-select v-model="outCarData.driverId" placeholder="请选择" disabled>
                <el-option v-for="item in driverName" :key="item.id"
                           :label="item.employeeName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="目的地:">
              <el-input v-model="outCarData.destination"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="开始里程(公里):">
              <el-input v-model="outCarData.mileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="出车原因:">
              <el-input v-model="outCarData.outCarReason" type="textarea"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="demo-drawer">
        <el-button @click="outCarFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doUpdateOutCar()">确 定</el-button>
      </div>
    </el-drawer>
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
      Employee: [],
      //修改出车数据对象
      outCarData: {
        carId: '',
        deptId: '',
        outTime: '',
        preInTime: '',
        followPerson: '',
        driverId: '',
        destination: '',
        mileage: '',
        outCarReason: '',
      },
      //模态框
      addDialogFormVisible: false,//添加模态框
      updateDialogFormVisible: false,//修改模态框
      showOutCarFormVisible: false,//查看详细模态框
      addInCarFormVisible: false,//添加回车记录
      outCarFormVisible: false,//出车记录修改
      //出车文本框对象
      addData: {},
      //创建人Id
      employeeId: '',
      //所有可以用的车
      availableCar: [],
      //提取的数据对象回车对象
      subInCar: {},
      //显示详细的数据
      showData: {},
    }
  },
  methods: {
    //修改出车信息确定
    doUpdateOutCar() {
      if (this.outCarData.preInTime < this.outCarData.outTime) {
        this.$message.error("预计回车时间不能小于出车时间");
        return;
      }
      this.$axios.post("departureRecord/update", this.outCarData).then(r => {
        if (r.data.code = 200) {
          this.outCarFormVisible = false;
          this.$message.success("修改成功");
          this.p = 1;
          this.findOutCar();
        }
      })
    },
    //修改出车的信息
    updateOutCar(row) {
      if (row.inTime != null) {
        this.$message.error("以回车不能修改出车信息");
        return;
      }
      this.outCarFormVisible = true;
      this.outCarData.id = row.id;
      this.outCarData.carId = row.carId;
      this.outCarData.deptId = row.deptId;
      this.outCarData.outTime = row.outTime;
      this.outCarData.preInTime = row.preInTime;
      this.outCarData.followPerson = row.followPerson;
      this.outCarData.driverId = row.driverId;
      this.outCarData.destination = row.destination;
      this.outCarData.mileage = row.mileage;
      this.outCarData.outCarReason = row.outCarReason;
      this.outCarData.employeeId = this.employeeId;
      console.log(this.outCarData)
    },
    //回车记录
    inCar() {
      if (this.subInCar.id == null) {
        this.$message.error("请选中一条数据");
        return;
      }
      if (this.subInCar.inTime != null) {
        this.$message.error("选中车辆已回车");
        return;
      }
      this.addInCarFormVisible = true;
      console.log(this.subInCar)
    },
    //查看回车记录
    showCar(row) {
      this.showData = row;
      this.$axios.post("returnRecord/getByDictionaryId?id=" + row.id).then(r => {
        this.showData.stopPosition = r.data.data.stopPosition;
        this.showData.returnMileage = r.data.data.returnMileage;
        this.showData.remarks = r.data.data.remarks;
        this.showData.zongMileage = r.data.data.zongMileage;
      })
      setTimeout(() => {
        this.showOutCarFormVisible = true;
      }, 100);
    },
    //添加回车记录确定
    doInCar() {
      if (this.subInCar.inCarTime < this.subInCar.outTime) {
        this.$message.error("回车不能小于出车时间");
        return;
      }
      console.log(this.subInCar)
      this.$axios.post("returnRecord/add", this.subInCar).then(r => {
        if (r.data.code = 200) {
          this.addInCarFormVisible = false;
          this.$message.success("回车成功");
          this.p = 1;
          this.findOutCar();
        }
      })
    },
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
        this.carNum.forEach(e => {
          if (e.carStatus == 38) {
            this.availableCar.push(e);
          }
        })
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
        this.Employee = r.data.data;
      })
    },
    //查询登入人的id
    getEmployeeId() {
      let account = localStorage.getItem("account");
      this.$axios.get("employee/getByAccount?account=" + account).then(r => {
        this.employeeId = r.data.data.id
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
    //提取表格一行元素的内容
    sub(row) {
      this.subInCar = row;
    },
    //出车记录填写确定
    doOutCar() {
      if (this.addData.preInTime < this.addData.outTime) {
        this.$message.error("预计回车时间不能小于出车时间");
        return;
      }
      this.addData.employeeId = this.employeeId;
      this.$axios.post("departureRecord/add", this.addData).then(r => {
        if (r.data.code = 200) {
          this.addDialogFormVisible = false;
          this.$message.success("添加成功");
          this.p = 1;
          this.findOutCar();
        }
      })
    },
    showCarEqNull() {
      this.$message.error("未回车无法查看");
    },
  },
  created() {
    Promise.all([
      this.getFindDriver(),
      this.getFindCar(),
      this.getFindDictionary(),
      this.getFindEmployee(),
      this.getEmployeeId()
    ]).then(res => {
      setTimeout(() => {
        this.findOutCar();//延时结束loading
      }, 100);
    })
  }
}
</script>

<style scoped>
.demo-drawer {
  text-align: center;
}
</style>
