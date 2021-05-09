<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日常处理</el-breadcrumb-item>
      <el-breadcrumb-item>年检记录</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入车牌号码" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="loadList"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showAddDialog">年检信息登记</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border style="width: 100%" height="400"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column fixed prop="carNum" label="车牌号码" width="100"></el-table-column>
        <el-table-column fixed prop="checkNum" label="年检标号" width="100"></el-table-column>
        <el-table-column fixed prop="checkDate" label="年检时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.checkDate | dateConvert() }}
          </template>
        </el-table-column>
        <el-table-column fixed prop="cost" label="年检金额" width="100"></el-table-column>
        <el-table-column fixed prop="oppositeName" label="年检单位" width="100"></el-table-column>
        <el-table-column fixed prop="nextCheckDate" label="下次年检时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.nextCheckDate | dateConvert() }}
          </template>
        </el-table-column>
        <el-table-column fixed prop="driverName" label="操作人员" width="100"></el-table-column>
        <el-table-column fixed label="操作" width="300">
          <template slot-scope="scope">
            <el-tooltip content="年检信息编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="showUpdateDialog(scope.row)"></el-button>
            </el-tooltip>

            <el-tooltip content="删除" placement="bottom" effect="light">
              <el-button type="danger" icon="el-icon-delete" circle @click="del(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </template>

    <br/>
    <br/>
    <br/>
    <!--年检信息登记-->
    <el-dialog title="年检信息登记" :visible.sync="addDialogFormVisible" center width="80%">
      <el-form :model="addData" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="车牌号码:">
                <el-select v-model="addData.carId" placeholder="请选择">
                  <el-option v-for="car in carList" :key="car.id"
                            :label="car.carNum" :value="car.id">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年检标号">
              <el-input v-model="addData.checkNum"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="年检日期:">
               <el-date-picker
                    v-model="addData.checkDate"
                    type="date"
                    placeholder="选择日期"
                    value-format="yyyy-MM-dd">
                </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年检金额">
              <el-input v-model="addData.cost"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年检单位">
                <el-select v-model="addData.oppositeCompanyId" placeholder="请选择">
                    <el-option v-for="opposite in oppositeList" :key="opposite.type"
                                :label="opposite.name" :value="opposite.type">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="下次年检日期">
               <el-date-picker
                    v-model="addData.nextCheckDate"
                    type="date"
                    placeholder="选择日期"
                    value-format="yyyy-MM-dd">
                </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经办人">
                <el-select v-model="addData.driverId" placeholder="请选择">
                    <el-option v-for="driver in driverList" :key="driver.id"
                                :label="driver.employeeName" :value="driver.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="8">
            <el-form-item label="年检备注">
              <el-input v-model="addData.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doAdd">确 定</el-button>
      </span>
    </el-dialog>
    <!--年检信息编辑-->
    <el-dialog title="年检信息编辑" :visible.sync="updateDialogFormVisible" center width="80%">
      <el-form :model="updateData" label-width="100px">
        <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="车牌号码:" >
                <el-select v-model="updateData.carId" disabled placeholder="请选择">
                    <el-option v-for="car in carList" :key="car.id"
                              :label="car.carNum" :value="car.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年检标号">
              <el-input v-model="updateData.checkNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年检日期:">
               <el-date-picker
                    v-model="updateData.checkDate"
                    type="date"
                    placeholder="选择日期"
                    value-format="yyyy-MM-dd">
                </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年检金额:">
                <el-input v-model="updateData.cost"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年检单位:">
                 <el-select v-model="updateData.oppositeCompanyId" placeholder="请选择">
                    <el-option v-for="opposite in oppositeList" :key="opposite.type"
                                :label="opposite.name" :value="opposite.type">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="下次年检日期">
               <el-date-picker
                    v-model="updateData.nextCheckDate"
                    type="date"
                    placeholder="选择日期"
                    value-format="yyyy-MM-dd">
                </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经办人">
                <el-select v-model="updateData.driverId" placeholder="请选择">
                  <el-option v-for="driver in driverList" :key="driver.id"
                            :label="driver.employeeName" :value="driver.id">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年检备注">
              <el-input v-model="updateData.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doUpdate">确 定</el-button>
      </span>
    </el-dialog>

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
  name: "roleManager",
  data() {
    return {
      // 查
      tableData:[],
      searchText: '',
      p: 1,
      size: 5,
      total:5,
      // 增
      addData:{},
      addDialogFormVisible:false,
      // 改
      updateData:{},
      updateDialogFormVisible: false,
      // 其他

      carList:[],// 车辆集合
      driverList:[], // 驾驶员集合
      oppositeList:[], // 往来单位集合
    }
  },
  methods: {
    // 查
    loadList() {
      this.$axios.get("yearCheckRecord/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.tableData = r.data.data.list
        this.tableData.forEach(e1 => {
          this.driverList.forEach(e2=>{
            if(e1.driverId===e2.id){
              e1.driverName = e2.employeeName
            }
          })
        this.oppositeList.forEach(e3=>{
            if(e1.oppositeCompanyId===e3.type){
              e1.oppositeName = e3.name
            }
          })
        });
        this.total = r.data.data.total
      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.loadList();
    },
    handleSizeChange(val) {
      this.size = val;
      this.loadList();
    },
    // 增
    showAddDialog(){
        this.addDialogFormVisible = true;
    },
    showViewRepairDialog(row){
      this.ViewDialogFormVisible = true;
      this.updateData = row;
    },
    doAdd(){
      this.$axios.post("yearCheckRecord/add",this.addData).then(r=>{
        if (r.data.code = 200) {
          this.$message.success("添加成功");
          this.addDialogFormVisible = false;
          this.p = 1;
          this.loadList();
        }
      })
    },
    // 改
    showUpdateDialog(row){
        this.updateDialogFormVisible = true;
        this.updateData = row;
    },
    doUpdate(){
      this.$axios.post("yearCheckRecord/update",this.updateData).then(r=>{
        if (r.data.code = 200) {
          this.$message.success("修改成功");
          this.updateDialogFormVisible = false;
          this.p = 1;
          this.loadList();
        }
      })
    },
    // 删
    del(row){
        this.$confirm("此操作将删除该记录,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("yearCheckRecord/delete?id=" + row.id).then(r => {
          this.p = 1,
            this.loadList();
          this.$message.success("删除成功");
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },
    // 其他
    getMenu() {
      this.$axios.get("dictionary/menu").then(r => {
        let totalTree = r.data.data;
        for (let i = 0; i < totalTree.length; i++) {
          if (totalTree[i].id == 39) {
            this.repairItemType = totalTree[i].children;
          }
        }
      })
    },
    getCarList(){
        this.$axios.get("car/getAll").then(r => {
        this.carList = r.data.data
      })
    },
    getDriverList(){
        this.$axios.get("driver/getAll").then(r => {
        this.driverList = r.data.data
      })
    },
    getOppositeList(){
        this.$axios.get("opposite/getoppolist?type=92").then(r => {
        this.oppositeList = r.data
        console.log(this.oppositeList)
      })
    }
  }
  ,
  created() {
    this.getMenu()
    this.getCarList()
    this.getDriverList()
    this.getOppositeList()
    this.loadList()
  }
}
</script>

<style scoped>

</style>
