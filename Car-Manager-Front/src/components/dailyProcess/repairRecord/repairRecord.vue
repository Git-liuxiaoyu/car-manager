<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日常处理</el-breadcrumb-item>
      <el-breadcrumb-item>维修记录</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入车牌号码" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="findRepairRecord"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showAddRepairDialog">添加维修记录</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <template>
      <el-table :data="tableData" border style="width: 100%" height="400"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column fixed prop="carNum" label="车牌号码" width="100"></el-table-column>
        <el-table-column fixed prop="oppositeCompanyId" label="修理厂" width="100"></el-table-column>
        <el-table-column fixed prop="sendTime" label="送修时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.sendTime | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column fixed prop="preGetTime" label="预计取时间" width="200">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.preGetTime | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column fixed prop="getTime" label="取车时间" width="200">
                      <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.getTime | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column fixed prop="driverId" label="经办人" width="100"></el-table-column>
        <el-table-column fixed prop="repairFee" label="维修金额" width="100"></el-table-column>
        <el-table-column fixed label="操作" width="300">
          <template slot-scope="scope">
            <el-tooltip content="送修信息更改" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="showUpdateRepairDialog(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="取车信息登记" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-check" circle @click="showGetCarRepairDialog(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="维修信息详情" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-view" circle @click="showViewRepairDialog(scope.row)"></el-button>
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
    <!--添加维修信息-->
    <el-dialog title="维修信息登记" :visible.sync="addDialogFormVisible" center width="80%">
      <el-form :model="RepairRecord" label-width="100px">
        <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="车牌号码:" prop="carId">
              <el-select v-model="RepairRecord.carId" clearable placeholder="请选择">
                <e-option label="请选择" value="0"></e-option>
                <el-option v-for="car in carList" :key="car.id"
                           :label="car.carNum" :value="car.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="修理厂:">
              <el-select v-model="value" clearable placeholder="请选择">
                <el-option v-for="item in options" :key="item.id"
                           :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修时间:">
                <el-date-picker
                v-model="value2"
                type="datetime"
                placeholder="选择日期时间"
                align="right"
                :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="预计取车时间">
               <el-date-picker
                    v-model="value2"
                    type="datetime"
                    placeholder="选择日期时间"
                    align="right"
                    :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修原因">
              <el-input v-model="RepairRecord.reason"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修备注">
              <el-input v-model="RepairRecord.remarks"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经办人">
              <el-select v-model="value" clearable placeholder="请选择">
                <el-option
                  v-for="item in options" :key="item.id"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRepairRecord()">确 定</el-button>
      </span>
    </el-dialog>
    <!--维修信息修改-->
    <el-dialog title="维修信息修改" :visible.sync="updateDialogFormVisible" center width="80%">
      <el-form :model="editRepair" label-width="100px">
        <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="车牌号码:" prop="carId">
              <el-select v-model="editRepair.carId" clearable placeholder="请选择">
                <e-option label="请选择" value="0"></e-option>
                <el-option v-for="car in carList" :key="car.id"
                           :label="car.carNum" :value="car.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="修理厂:">
              <el-select v-model="value" clearable placeholder="请选择">
                <el-option v-for="item in options" :key="item.id"
                           :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修时间:">
                <el-date-picker
                v-model="value2"
                type="datetime"
                placeholder="选择日期时间"
                align="right"
                :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="预计取车时间">
               <el-date-picker
                    v-model="value2"
                    type="datetime"
                    placeholder="选择日期时间"
                    align="right"
                    :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修原因">
              <el-input v-model="editRepair.reason"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修备注">
              <el-input v-model="editRepair.remarks"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="经办人">
              <el-select v-model="value" clearable placeholder="请选择">
                <el-option
                  v-for="item in options" :key="item.id"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRepairRecord()">确 定</el-button>
      </span>
    </el-dialog>
    <!--取车信息登记-->
    <el-dialog title="取车信息登记" :visible.sync="getCarDialogFormVisible" center width="80%">
      <el-form :model="editRepair" label-width="100px">
         <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="车牌号码:" prop="carId">
              <el-select v-model="editRepair.carId" clearable placeholder="请选择">
                <e-option label="请选择" value="0"></e-option>
                <el-option v-for="car in carList" :key="car.id"
                           :label="car.carNum" :value="car.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="修理厂:">
              <el-select v-model="value" clearable placeholder="请选择">
                <el-option v-for="item in options" :key="item.id"
                           :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="维修类别:">
              <el-select v-model="value" clearable placeholder="请选择">
                <el-option v-for="item in options" :key="item.id"
                           :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修时间:">
                <el-date-picker
                v-model="value2"
                type="datetime"
                placeholder="取车时间"
                align="right"
                :picker-options="pickerOptions">
                </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="花费金额">
              <el-input v-model="editRepair.repairFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="维修项目">
              <el-input v-model="editRepair.repairItem"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="使用材料">
              <el-input v-model="editRepair.useGoods"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="取车备注">
              <el-input v-model="editRepair.remarks"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="getCarDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="getCarRigister()">确 定</el-button>
      </span>
    </el-dialog>
    <!--维修信息详细-->
    <el-dialog title="维修信息详细" :visible.sync="ViewDialogFormVisible" center width="80%">
      <el-form :model="editRepair" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="车牌号码:">
                {{editRepair.carNum}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="修理厂:">
                {{editRepair.oppositeCompanyId}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="送修时间:">
                {{editRepair.sendTime | timeConvert()}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计取车时间">
                {{editRepair.preGetTime | timeConvert()}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="送修原因">
              {{editRepair.reason}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="送修备注">
              {{editRepair.remarks}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经办人">
              {{editRepair.driverId}}
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="维修类别">
              {{editRepair.repairType}}
            </el-form-item>
          </el-col>
         <el-col :span="8">
            <el-form-item label="取车时间">
              {{editRepair.getTime}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="花费金额">
              {{editRepair.driverId}}
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="维修项目">
              {{editRepair.repairFee}}
            </el-form-item>
          </el-col>
                    <el-col :span="8">
            <el-form-item label="使用材料">
              {{editRepair.useGoods}}
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="取车备注">
              {{editRepair.remarks}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
    <el-row>
      <el-col :span="10" :push="8">
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
      carList:[],
      searchText: '',
      p: 1,
      tableData: [],
      addDialogFormVisible: false,
      updateDialogFormVisible:false,
      getCarDialogFormVisible:false,
      ViewDialogFormVisible:false,
      RepairRecord: {},
      editRepair:{},
      total: '',
      size: 5
    }
  },
  methods: {
    findRepairRecord() {
      this.$axios.get("repairRecord/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.tableData = r.data.data.list
        this.total = r.data.data.total
      })
    },
    addRepairRecord(){
      this.$axios.post("repairRecord/add",this.RepairRecord).then(res=>{
        if (res.data.code == 200) {
          this.$message({
            type: "success",
            message: "添加成功",
            duration: 1600
          });
          this.addDialogFormVisible = false;
          //重新加载页面
          this.findRepairRecord();
        }
      })
    },
    updateRepairRecord(){
      this.$axios.post("repairRecord/update",this.editRepair).then(res=>{
        if (res.data.code == 200) {
          this.$message({
            type: "success",
            message: "修改成功",
            duration: 1600
          });
          this.updateDialogFormVisible = false;
          //重新加载页面
          this.findRepairRecord();
        }
      })
    },
    getCarRigister(){
        updateRepairRecord()
        this.ViewDialogFormVisible = false;
    },
    del(row) {
      this.$confirm("此操作将删除该账户,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("repairRecord/delete?id=" + row.id).then(r => {
          this.p=1,
          this.findRepairRecord();
          this.$message.success("删除成功");
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.findRepairRecord();
    },
    handleSizeChange(val) {
      this.size = val;
      this.findRepairRecord();
    },

    showAddRepairDialog() {
      this.addDialogFormVisible = true;
    },
    showUpdateRepairDialog(row){
      this.updateDialogFormVisible = true;
      this.editRepair = row;
    },
    showGetCarRepairDialog(row){
      this.getCarDialogFormVisible = true;
      this.editRepair = row;
    },
    showViewRepairDialog(row){
      this.ViewDialogFormVisible = true;
      this.editRepair = row;
    },
    getCarList(){
        this.$axios.get("car/getAll").then(r => {
        this.carList = r.data.data
        console.log(this.carList)
      })
    }
  }
  ,
  created() {
    this.getCarList();
    this.findRepairRecord();
  }
}
</script>

<style scoped>

</style>
