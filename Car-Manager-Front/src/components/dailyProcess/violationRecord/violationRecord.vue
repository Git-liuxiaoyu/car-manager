<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日常处理</el-breadcrumb-item>
      <el-breadcrumb-item>违章记录</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入车牌号码,支持模糊查询" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="loadList"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary"  @click="showAddDialog">违章信息登记</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column  prop="carNum" label="车牌号码" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column  prop="violationName" label="违章项目" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column  prop="time" label="违章时间" min-width :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.time | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column  prop="fee" label="违章罚款" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column  prop="reduceScore" label="违章扣分" min-width :show-overflow-tooltip="true"> </el-table-column>
        <el-table-column  prop="driverName" label="驾驶员" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column  prop="place" label="违章地点" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column  prop="remarks" label="备注" min-width :show-overflow-tooltip="true"> </el-table-column>

        <el-table-column  label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip content="编辑" placement="bottom" effect="light">
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
<!--违章信息登记-->
<el-dialog title="违章信息登记" :visible.sync="addDialogFormVisible" center width="80%">
      <el-form :model="addData" label-width="120px" :rules='checkRules' ref="addForm">
        <el-row :gutter="20">

          <el-col :span="6">
            <el-form-item label="车牌号码:" prop="carId">
                <el-select v-model="addData.carId" placeholder="请选择">
                  <el-option v-for="car in carList" :key="car.id"
                            :label="car.carNum" :value="car.id" :disabled="car.disabled">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章项目:" prop="type">
              <el-select v-model="addData.type" placeholder="请选择">
                <el-option v-for="item in violationType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章时间:" prop="time">
                  <el-date-picker
                    v-model="addData.time"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:ss">
                  </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章罚款" prop="fee">
              <el-input v-model="addData.fee"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章扣分" prop="reduceScore">
              <el-input v-model="addData.reduceScore"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="驾驶员" prop="driverId">
                <el-select v-model="addData.driverId" placeholder="请选择">
                  <el-option v-for="driver in driverList" :key="driver.id"
                            :label="driver.employeeName" :value="driver.id" :disabled="driver.disabled">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章地点" prop="place">
              <el-input v-model="addData.place"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章备注" prop="remarks">
                <el-input v-model="addData.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="closeAddDialog">取 消</el-button>
        <el-button type="primary" @click="doAdd()">确 定</el-button>
      </span>
    </el-dialog>

<!--违章信息修改-->
    <el-dialog title="违章信息修改" :visible.sync="updateDialogFormVisible" center width="80%">
      <el-form :model="updateData" label-width="100px" :rules='checkRules' ref="updateForm">
         <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="车牌号码:" prop="carId">
                <el-select v-model="updateData.carId" disabled placeholder="请选择">
                    <el-option v-for="car in carList" :key="car.id" :label="car.carNum" :value="car.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章项目:" prop="type">
              <el-select v-model="updateData.type" placeholder="请选择">
                <el-option v-for="item in violationType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章时间:" prop="time">
                  <el-date-picker
                    v-model="updateData.time"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:ss"
                  >
                  </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章罚款" prop="fee">
              <el-input v-model="updateData.fee"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章扣分" prop="reduceScore">
              <el-input v-model="updateData.reduceScore"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="驾驶员" prop="driverId">
                <el-select v-model="updateData.driverId" placeholder="请选择">
                  <el-option v-for="driver in driverList" :key="driver.id"
                            :label="driver.employeeName" :value="driver.id">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章地点" prop="place">
              <el-input v-model="updateData.place"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="违章备注" prop="remarks">
                <el-input v-model="updateData.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="closeUpdateDialog">取 消</el-button>
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
  name: "violationRecord",
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
      violationType: [],// 违章类别
      carList:[],// 车辆集合
      driverList:[], // 驾驶员集合
             // 验证规则
      checkRules: {
      // 要以数组形式展示
        place:[
          { required: true, message: "地点不能为空", trigger: "blur"},
        ],
        reduceScore:[
          { required: true, message: "扣分不能为空", trigger: "blur"},
          { 
            pattern: /^[1-9][0-9]{0,1}$/,
            message: "请输入合法数字",
          }
        ],
        remarks:[
          { required: true, message: "备注为空请填'无'", trigger: "blur"},
        ],
        fee:[
          { required: true, message: "金额不能为空", trigger: "blur"},
          { pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, message: '请输入正确额格式,可保留两位小数' },
        ],
        driverId:[
          { required: true, message: "请选择", trigger: "change"},
        ],
        carId:[
          { required: true, message: "请选择", trigger: "change"},
        ],
        type:[
          { required: true, message: "请选择", trigger: "change"},
        ],
        time:[
          { required: true, message: "请选择", trigger: "change"},
        ],
      }
    }
  },
  methods: {
    // 查
    loadList(){
      this.$axios.get("driver/getAll").then(r => {
        this.driverList = r.data.data
        this.driverList.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
        this.$axios.get("dictionary/menu").then(r => {
        let totalTree = r.data.data;
        for (let i = 0; i < totalTree.length; i++) {
          if (totalTree[i].id == 43) {
            this.violationType = totalTree[i].children;
          }
        }
        this.$axios.get("car/getAll").then(r => {
        this.carList = r.data.data
        this.carList.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
        this.$axios.get("violationRecord/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.tableData = r.data.data.list
        this.tableData.forEach(e1 => {
          this.violationType.forEach(e2=>{
            if(e1.type===e2.id){
              e1.violationName = e2.text
            }
          })
          this.driverList.forEach(e3=>{
            if(e1.driverId===e3.id){
              e1.driverName = e3.employeeName
            }
          })
        });
        this.total = r.data.data.total
      })

      })
      })
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
    closeAddDialog(){
      this.$refs['addForm'].resetFields();
      this.addDialogFormVisible = false;
    },
    doAdd(){
      this.$refs["addForm"].validate((valid) => {
        if (valid) {
      this.$axios.post("violationRecord/add", this.addData).then(r => {
        if (r.data.code === 200) {
          this.$message.success("添加成功");
          this.$refs['addForm'].resetFields();
          this.addDialogFormVisible = false;
          this.p = 1;
          this.loadList();
        }
      })
      } else {
        console.log('error submit!!');
        return false;
      }
    });
    },
    // 改
    showUpdateDialog(row){
        this.updateDialogFormVisible = true;
        this.loadList();
        for(let element in this.tableData){
          if(element.id==row.id){
            this.updateData=element
          }
        }
        this.updateData = row;
    },
    closeUpdateDialog(){
      this.$refs['updateForm'].resetFields();
      this.updateDialogFormVisible = false;
    },
    doUpdate(row){
      this.$refs["updateForm"].validate((valid) => {
      if (valid) {
      this.$axios.post("violationRecord/update", this.updateData).then(r => {
        if (r.data.code === 200) {
          this.$message.success("修改成功");
          this.updateDialogFormVisible = false;
          this.p = 1;
          this.loadList();
        }
      })
      } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 删
    del(row){
        this.$confirm("此操作将删除该记录,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("violationRecord/delete?id=" + row.id).then(r => {
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
          if (totalTree[i].id == 43) {
            this.violationType = totalTree[i].children;
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
    }
  }
  ,
  created() {
    this.loadList()
  }
}
</script>

<style scoped>

</style>
