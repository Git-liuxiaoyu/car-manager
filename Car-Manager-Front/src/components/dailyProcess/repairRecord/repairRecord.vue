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
          <el-button slot="append" icon="el-icon-search" @click="loadList"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showAddDialog">添加维修记录</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column  prop="carNum" label="车牌号码" min-width></el-table-column>
        <el-table-column  prop="repairName" label="修理厂" min-width></el-table-column>
        <el-table-column  prop="sendTime" label="送修时间" min-width :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.sendTime | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column  prop="preGetTime" label="预计取时间" min-width :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.preGetTime | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column  prop="getTime" label="取车时间" min-width :show-overflow-tooltip="true">
                      <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.getTime | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column  prop="driverName" label="经办人" min-width></el-table-column>
        <el-table-column  prop="repairFee" label="维修金额" min-width></el-table-column>
        <el-table-column  label="操作" width="230">
          <template slot-scope="scope">
            <el-tooltip content="送修信息更改" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="showUpdateDialog(scope.row)"></el-button>
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
      <el-form :model="addData" label-width="100px">
        <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="车牌号码:" prop="carId">
                <el-select v-model="addData.carId" placeholder="请选择">
                  <el-option v-for="car in carList" :key="car.id"
                            :label="car.carNum" :value="car.id">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="修理厂:">
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修时间:">
               <el-date-picker
                    v-model="addData.sendTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:SS">
                </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="预计取车时间">
               <el-date-picker
                    v-model="addData.preGetTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:SS">
                </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修原因">
              <el-input v-model="addData.reason"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修备注">
              <el-input v-model="addData.remarks"></el-input>
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
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doAdd">确 定</el-button>
      </span>
    </el-dialog>
    <!--维修信息修改-->
    <el-dialog title="维修信息修改" :visible.sync="updateDialogFormVisible" center width="80%">
      <el-form :model="updateData" label-width="100px">
        <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="车牌号码:" prop="carId">
                <el-select v-model="updateData.carId" disabled placeholder="请选择">
                    <el-option v-for="car in carList" :key="car.id"
                              :label="car.carNum" :value="car.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="修理厂:">
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修时间:">
               <el-date-picker
                    v-model="updateData.sendTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:SS">
                </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="预计取车时间">
               <el-date-picker
                    v-model="updateData.preGetTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:SS">
                </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修原因">
              <el-input v-model="updateData.reason"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修备注">
              <el-input v-model="updateData.remarks"></el-input>
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
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doUpdate">确 定</el-button>
      </span>
    </el-dialog>
    <!--取车信息登记-->
    <el-dialog title="取车信息登记" :visible.sync="getCarDialogFormVisible" center width="80%">
      <el-form :model="updateData" label-width="100px">
         <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="车牌号码:" prop="carId">
                <el-select v-model="updateData.carId" disabled placeholder="请选择">
                  <el-option v-for="car in carList" :key="car.id"
                            :label="car.carNum" :value="car.id">
                  </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="修理厂:">
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="维修类别:">
              <el-select v-model="updateData.repairType" placeholder="请选择">
                <el-option v-for="item in repairItemType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="送修时间:">
               <el-date-picker
                    disabled
                    v-model="updateData.sendTime"
                    type="datetime"
                    placeholder="选择日期时间"
                    value-format="yyyy-MM-dd HH:mm:SS">
                </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="花费金额">
              <el-input v-model="updateData.repairFee"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="维修项目">
              <el-input v-model="updateData.repairItem"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="使用材料">
              <el-input v-model="updateData.useGoods"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="取车备注">
              <el-input v-model="updateData.remarks"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="getCarDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="getCarRigister">确 定</el-button>
      </span>
    </el-dialog>
    <!--维修信息详细-->
    <el-dialog title="维修信息详细" :visible.sync="ViewDialogFormVisible" center width="80%">
      <el-form :model="updateData" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="车牌号码:">
                {{updateData.carNum}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="修理厂:">
                {{updateData.oppositeCompanyId}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="送修时间:">
                {{updateData.sendTime | timeConvert()}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="预计取车时间">
                {{updateData.preGetTime | timeConvert()}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="送修原因">
              {{updateData.reason}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="送修备注">
              {{updateData.remarks}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="经办人">
              {{updateData.driverName}}
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="维修类别">
              {{updateData.repairItemName}}
            </el-form-item>
          </el-col>
         <el-col :span="8">
            <el-form-item label="取车时间">
              {{updateData.getTime | timeConvert()}}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="花费金额">
              {{updateData.repairFee}}
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="维修项目">
              {{updateData.repairItem}}
            </el-form-item>
          </el-col>
                    <el-col :span="8">
            <el-form-item label="使用材料">
              {{updateData.useGoods}}
            </el-form-item>
          </el-col>
           <el-col :span="8">
            <el-form-item label="取车备注">
              {{updateData.remarks}}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
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
      ViewDialogFormVisible:false,
      // 增
      addData:{},
      addDialogFormVisible:false,
      // 改
      updateData:{},
      updateDialogFormVisible: false,
      getCarDialogFormVisible:false,

      // 其他

      carList:[],// 车辆集合
      driverList:[], // 驾驶员集合
      violationType: [],// 违章类别
      repairItemType:[] // 维修类别集合
    }
  },
  methods: {
    // 查
    loadList() {
      this.$axios.get("repairRecord/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.tableData = r.data.data.list
        this.tableData.forEach(e1 => {
          this.violationType.forEach(e2=>{
            if(e1.type===e2.id){
              e1.violationName = e2.text
            }
          })
          this.repairItemType.forEach(e3=>{
            if(e1.repairType===e3.id){
              e1.repairItemName = e3.text
            }
          })
          this.driverList.forEach(e4=>{
            if(e1.driverId===e4.id){
              e1.driverName = e4.employeeName
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
      this.$axios.post("repairRecord/add",this.addData).then(r=>{
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
    showGetCarRepairDialog(row){
      this.getCarDialogFormVisible = true;
      this.updateData = row;
    },
    doUpdate(){
      this.$axios.post("repairRecord/update",this.updateData).then(r=>{
        if (r.data.code = 200) {
          this.$message.success("修改成功");
          this.updateDialogFormVisible = false;
          this.p = 1;
          this.loadList();
        }
      })
    },
    getCarRigister(){
        this.$axios.post("repairRecord/update",this.updateData).then(r=>{
        if (r.data.code = 200) {
          this.$message.success("取车成功");
          this.getCarDialogFormVisible = false;
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
        this.$axios.get("repairRecord/delete?id=" + row.id).then(r => {
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
          if (totalTree[i].id == 39) {
            this.repairItemType = totalTree[i].children;
            console.log(this.repairItemType)
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
    this.getMenu()
    this.getCarList()
    this.getDriverList()
    this.loadList()
  }
}
</script>

<style scoped>

</style>
