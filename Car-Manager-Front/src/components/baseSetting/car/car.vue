<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础设置</el-breadcrumb-item>
      <el-breadcrumb-item>车辆档案</el-breadcrumb-item>
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
        <el-button type="primary" @click="showAddDialog">添加车辆</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column type="index"></el-table-column>
        <el-table-column  prop="carNum" label="车牌号码" min-width> </el-table-column>
        <el-table-column  prop="brandName" label="车牌品牌" min-width></el-table-column>
        <el-table-column  prop="type" label="车辆型号" min-width></el-table-column>
        <el-table-column  prop="colorName" label="车辆颜色" min-width></el-table-column>
        <el-table-column  prop="seatNum" label="座位数" min-width></el-table-column>
        <el-table-column  prop="deptName" label="部门" min-width></el-table-column>
        <el-table-column  prop="carStatus" label="车辆状态" min-width> </el-table-column>
        <el-table-column  prop="status" label="是否启用" min-width></el-table-column>
        <el-table-column  label="操作" width="230">
          <template slot-scope="scope">
            <el-tooltip content="修改" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="showUpdateDialog(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="图片管理" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-share" circle @click="showImgManageDialog(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="详情" placement="bottom" effect="light">
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

    <!--添加-->
    <el-dialog title="维修信息登记" :visible.sync="addDialogFormVisible" center width="80%">
      <el-form :model="addData" label-width="150px">
        <el-row :gutter="20">

          <el-col :span="6">
            <el-form-item label="车牌号码：">
              <el-input v-model="addData.carNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="品牌:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="型号:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="颜色:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="载重（吨）：" prop="carrying">
              <el-input v-model="addData.carrying"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="座位数：" prop="seatNum">
              <el-input v-model="addData.seatNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="油耗（百公里）：" prop="oilConsume">
              <el-input v-model="addData.oilConsume"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="初始里程（公里）：" prop="initialMileage">
              <el-input v-model="addData.initialMileage"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="保养里程（公里）：" prop="curingMileage">
              <el-input v-model="addData.curingMileage"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="保养周期（公里）：" prop="curingCycle">
              <el-input v-model="addData.curingCycle"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">

            <el-form-item label="发动机号码：" prop="engineNum">
              <el-input v-model="addData.engineNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车架号：" prop="serialNumber">
              <el-input v-model="addData.serialNumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="供应商:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="购买价格：" prop="price">
              <el-input v-model="addData.price"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="购买日期:">
              <el-date-picker
                v-model="addData.boughtDate"
                type="date"
                placeholder="选择日期时间"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="部   门:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车辆状态:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              <el-input v-model="addData.remarks"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态:" prop="remarks">
              <el-input v-model="addData.status"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doAdd">确 定</el-button>
      </span>
    </el-dialog>
    <!--修改-->
    <el-dialog title="编辑车辆" :visible.sync="updateDialogFormVisible" center width="80%">
      <el-form :model="updateData" label-width="150px">
        <el-row :gutter="20">

          <el-col :span="6">
            <el-form-item label="车牌号码:" prop="carId">
              <el-input v-model="updateData.carNum"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="品牌:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="型号:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="颜色:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="载重（吨）：" prop="carrying">
              <el-input v-model="updateData.carrying"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="座位数：" prop="seatNum">
              <el-input v-model="updateData.seatNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="油耗（百公里）：" prop="oilConsume">
              <el-input v-model="updateData.oilConsume"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="初始里程（公里）：" prop="initialMileage">
              <el-input v-model="updateData.initialMileage"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="保养里程（公里）：" prop="curingMileage">
              <el-input v-model="updateData.curingMileage"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="保养周期（公里）：" prop="curingCycle">
              <el-input v-model="updateData.curingCycle"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">

            <el-form-item label="发动机号码：" prop="engineNum">
              <el-input v-model="updateData.engineNum"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车架号：" prop="serialNumber">
              <el-input v-model="updateData.serialNumber"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="供应商:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="购买价格：" prop="price">
              <el-input v-model="updateData.price"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="购买日期:">
              <el-date-picker
                v-model="updateData.boughtDate"
                type="date"
                placeholder="选择日期时间"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="部   门:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车辆状态:">
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              <el-input v-model="updateData.remarks"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态:" prop="remarks">
              <el-input v-model="updateData.status"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="doUpdate">确 定</el-button>
      </span>
    </el-dialog>
    <!--图片管理-->
    <el-dialog title="车辆图片管理" :visible.sync="manageCarImgVisible" center width="80%">
    </el-dialog>
    <!--详细-->
    <el-dialog title="维修信息详细" :visible.sync="ViewDialogFormVisible" center width="80%">
      <el-form :model="updateData" label-width="150px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="车牌号码：" >
              {{ updateData.carNum }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="品牌：" >
              {{ updateData.brandName }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="型号：" >
              {{ updateData.type }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="颜色：" >
              {{ updateData.colorName }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="载重（吨）：">
              {{ updateData.carrying }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="座位数：" >
              {{ updateData.seatNum }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="油耗（百公里）：" >
              {{ updateData.oilConsume }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="初始里程（公里）：" >
              {{ updateData.initialMileage }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="保养里程（公里）：" >
              {{ updateData.curingMileage }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="保养周期（公里）：" >
              {{ updateData.curingCycle }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="发动机号码：" >
              {{ updateData.engineNum }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车架号：" >
              {{ updateData.serialNumber }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="购买价格：" >
              {{ updateData.price }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="购买日期：" >
              {{ updateData.boughtDate }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="部   门：">
              {{ updateData.deptName }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车辆状态：" >
              {{ updateData.carStatus }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="备注：" >
              {{ updateData.remarks }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态：" >
              {{ updateData.status }}
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
      manageCarImgVisible: false,
      // 其他

      brandType:[], // 品牌集合
      colorType:[], // 颜色集合
      deptType:[], // 部门集合
    }
  },
  methods: {
    // 查
    loadList() {
      this.$axios.get("car/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.tableData = r.data.data.list
        this.tableData.forEach(e1 => {
          this.brandType.forEach(e2=>{
            if(e1.brandId===e2.id){
              e1.brandName = e2.text
            }
          })
          this.colorType.forEach(e3=>{
            if(e1.color===e3.id){
              e1.colorName = e3.text
            }
          })
          this.deptType.forEach(e4=>{
            if(e1.deptId===e4.id){
              e1.deptName = e4.text
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
      this.$axios.post("car/add",this.addData).then(r=>{
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
    showImgManageDialog(row){
      this.manageCarImgVisible = true;
    },
    doUpdate(){
      this.$axios.post("car/update",this.updateData).then(r=>{
        if (r.data.code = 200) {
          this.$message.success("修改成功");
          this.updateDialogFormVisible = false;
          this.p = 1;
          this.loadList();
        }
      })
    },
    getCarRigister(){
      this.$axios.post("car/update",this.updateData).then(r=>{
        if (r.data.code = 200) {
          this.$message.success("取车成功");
          this.manageCarImgVisible = false;
          this.p = 1;
          this.loadList();
        }
      })
    },
    // 删
    del(row){
      this.$confirm("此操作将删除该车辆,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("car/delete?id=" + row.id).then(r => {
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
          if (totalTree[i].id == 21) {
            this.brandType = totalTree[i].children;
          }
          if (totalTree[i].id == 27) {
            console.log(totalTree[i].children)
            this.colorType = totalTree[i].children;
          }
          if (totalTree[i].id == 1) {
            console.log(totalTree[i].children)
            this.deptType = totalTree[i].children;
          }
        }
      })
    },
  }
  ,
  created() {
    Promise.all([
      this.getMenu()
    ]).then(res => {
      this.loadList()
    })

  },
}
</script>

<style scoped>

</style>
