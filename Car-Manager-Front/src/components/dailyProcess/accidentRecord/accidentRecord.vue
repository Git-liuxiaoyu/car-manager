<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'homePage' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日常处理</el-breadcrumb-item>
      <el-breadcrumb-item>事故记录</el-breadcrumb-item>
    </el-breadcrumb>

    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入车牌号码" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="loadAccident"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="addAccidentDialog()">事故信息登记</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column prop="carNum" label="车牌号码" min-width></el-table-column>
        <el-table-column prop="driverName" label="驾驶员" min-width></el-table-column>
        <el-table-column prop="time" label="事故时间" min-width :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.time | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column prop="place" label="事故地点" min-width></el-table-column>
        <el-table-column prop="explain" label="事故说明" min-width></el-table-column>
        <el-table-column prop="selfCost" label="我方承担金额" min-width></el-table-column>
        <el-table-column prop="oppoCost" label="对方承担金额" min-width></el-table-column>
        <el-table-column prop="insureCost" label="保险承担金额" min-width></el-table-column>
        <el-table-column label="操作" width="180">
          <template slot-scope="scoped">
            <el-tooltip content="事故信息详情" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-view" circle @click="showViewAccidentDialog(scoped.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="updateAccident(scoped.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="bottom" effect="light">
              <el-button type="danger" icon="el-icon-delete" circle @click="deleteAccident(scoped.row.id)"></el-button>
            </el-tooltip>
          </template>

        </el-table-column>

      </el-table>
    </template>

    <br/>
    <br/>
    <br/>

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
    <!-- 新增 -->
    <el-dialog title="事故信息登记" :visible.sync="addDialogFormVisible" center width="80%">
      <el-form :model="addAccident" label-width="120px" :rules="rules" ref="addForm">
        <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="车牌号" prop="carId">
              <el-select v-model="addAccident.carId" placeholder="请选择">
                <el-option :label="car.carNum" :value="car.id"
                           v-for="car in cars" :key="car.id" :disabled="car.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>



          <el-col :span="8">

            <el-form-item label="驾驶员" prop="driverId">
              <el-select v-model="addAccident.driverId" placeholder="请选择">
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id" :disabled="driver.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="事故时间:" prop="time">
              <el-date-picker
                v-model="addAccident.time"
                type="datetime"
                placeholder="选择日期时间"
                value-format="yyyy-MM-dd HH-mm-ss"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="事故地点" prop="place">
              <el-input v-model="addAccident.place"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="事故说明"  prop="explain">
              <el-input v-model="addAccident.explain"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="我方情况" prop="selfCondition">
              <el-input v-model="addAccident.selfCondition"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="对方情况" prop="oppoCondition">
              <el-input v-model="addAccident.oppoCondition"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="处理结果" prop="solveResult">
              <el-input v-model="addAccident.solveResult"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="我方承担金额" prop="selfCost">
              <el-input v-model="addAccident.selfCost"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="对方承但金额" prop="oppoCost">
              <el-input v-model="addAccident.oppoCost"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="保险承但金额" prop="insureCost" >
              <el-input v-model="addAccident.insureCost"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="addAccident.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addAccidentForm('addForm')">取 消</el-button>
        <el-button type="primary" @click="toAddAccident('addForm')">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 修改 -->
    <el-dialog title="事故信息修改" :visible.sync="dialogEditVisible" center width="80%">
      <el-form :model="editAccident" label-width="100px" :rules="rules" ref="editForm">
        <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="车牌号" prop="carId">
              <el-select v-model="editAccident.carId" placeholder="请选择" :disabled="true">
                <el-option :label="car.carNum" :value="car.id"
                           v-for="car in cars" :key="car.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>



          <el-col :span="8">

            <el-form-item label="驾驶员" prop="driverId">
              <el-select v-model="editAccident.driverId" placeholder="请选择">
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="事故时间" >
              <el-date-picker
                v-model="editAccident.time"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:ss"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="事故地点" prop="place">
              <el-input v-model="editAccident.place"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="事故说明"  prop="explain">
              <el-input v-model="editAccident.explain"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="我方情况" prop="selfCondition">
              <el-input v-model="editAccident.selfCondition"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="对方情况" prop="oppoCondition">
              <el-input v-model="editAccident.oppoCondition"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="处理结果" prop="solveResult">
              <el-input v-model="editAccident.solveResult"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="我方承担金额" prop="selfCost">
              <el-input v-model="editAccident.selfCost"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="对方承但金额" prop="oppoCost">
              <el-input v-model="editAccident.oppoCost"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="保险承但金额" prop="insureCost" >
              <el-input v-model="editAccident.insureCost"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="editAccident.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editAccidentForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="toUpdate('editForm')">确 定</el-button>
      </div>
    </el-dialog>



    <!-- 查看详细 -->
    <el-dialog title="事故信息详细" :visible.sync="dialogshowAccidentVisible" center width="80%">
      <el-form :model="editAccident" label-width="100px">
             <el-row :gutter="20">

          <el-col :span="8">
            <el-form-item label="车牌号:">
              <el-input v-model="editAccident.carNum" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="驾驶员" >
              <el-input v-model="editAccident.driverName" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="事故时间" prop="time" >
              <el-date-picker
                v-model="editAccident.time"
                type="datetime"
                value-format="yyyy-MM-dd HH:mm:SS"
                placeholder="选择日期" readonly="readonly">
              </el-date-picker>
            </el-form-item>


          </el-col>

          <el-col :span="12">
            <el-form-item label="事故地点" prop="place" >
              <el-input v-model="editAccident.place" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="事故说明"  prop="explain" >
              <el-input v-model="editAccident.explain" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="我方情况" prop="selfCondition" >
              <el-input v-model="editAccident.selfCondition" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="对方情况" prop="oppoCondition" >
              <el-input v-model="editAccident.oppoCondition" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="处理结果" prop="solveResult" >
              <el-input v-model="editAccident.solveResult" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="我方承担金额" prop="selfCost" >
              <el-input v-model="editAccident.selfCost" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="对方承但金额" prop="oppoCost" >
              <el-input v-model="editAccident.oppoCost" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="保险承但金额" prop="insureCost" >
              <el-input v-model="editAccident.insureCost" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="备注" prop="remarks" >
              <el-input v-model="editAccident.remarks" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" >
        <el-button @click="dialogshowAccidentVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
export default {
  data(){
    return{
      searchText:'',
      p: 1,
      total: 0,
      size: 5,
      tableData:[],
      //新增
      addDialogFormVisible:false,
      addAccident:{},
      cars:[],
      drivers:[],
      // 编辑
      dialogEditVisible:false,
      editAccident:{},
      //详细
      dialogshowAccidentVisible:false,

      rules:{
         driverId:[
            {required: true, //是否必填       
            message: '驾驶员不能为空', //规则
            trigger: 'change'  //何事件触发
          }
         ],
         carId:[
            {required: true, //是否必填       
            message: '车牌号不能为空', //规则
            trigger: 'change'  //何事件触发
          }
         ],
         time:[
            {required: true, //是否必填       
            message: '事故时间不能为空', //规则
            trigger: 'change'  //何事件触发
          }
         ],
         place:[
            {required: true, //是否必填       
            message: '事故地点不能为空', //规则
            trigger: 'blur'  //何事件触发
          }
         ],
         explain:[
             {required: true, //是否必填       
            message: '事故说明不能为空', //规则
            trigger: 'blur'  //何事件触发
          }
         ],
                  selfCondition:[
             {required: true, //是否必填       
            message: '我方情况不能为空', //规则
            trigger: 'blur'  //何事件触发
          }
         ],
                  oppoCondition:[
             {required: true, //是否必填       
            message: '我方情况不能为空', //规则
            trigger: 'blur'  //何事件触发
          }
         ],
                  solveResult:[
             {required: true, //是否必填       
            message: '处理结果不能为空', //规则
            trigger: 'blur'  //何事件触发
          }
         ],
                  selfCost:[
             {required: true, //是否必填       
            message: '我方承担金额不能为空', //规则
            trigger: 'blur'  //何事件触发
          },
           { pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, message: '请输入正确额格式,可保留两位小数' },
         ],
                           oppoCost:[
             {required: true, //是否必填       
            message: '对方承担金额不能为空', //规则
            trigger: 'blur'  //何事件触发
          },
           { pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, message: '请输入正确额格式,可保留两位小数' },
         ],
                           insureCost:[
             {required: true, //是否必填       
            message: '保险承担金额不能为空', //规则
            trigger: 'blur'  //何事件触发
          },
           { pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, message: '请输入正确额格式,可保留两位小数' },
         ],
                           remarks:[
             {required: true, //是否必填       
            message: '备注为空请填‘无’', //规则
            trigger: 'blur'  //何事件触发
          }
         ],
      }

    }
  },
  methods:{
    loadAccident(){
      this.$axios.get("accidentRecord/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r=>{
        console.log(r);
        this.tableData = r.data.data.list
        this.total = r.data.data.total
      });

    },

    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.loadAccident();
    },
    handleSizeChange(val) {
      this.size = val;
      this.loadAccident();
    },

    addAccidentDialog(){
      this.addDialogFormVisible=true;
      this.loadDriverName();
      this.getCarList()
    },

    // 获取驾驶员信息
    loadDriverName(){
      this.$axios.get("driver/getAll" ).then(r=>{
        this.drivers=r.data.data;
        this.drivers.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
      })
    },
    // 获取车牌信息
    getCarList(){
      this.$axios.get("car/getAll").then(r => {
        this.cars = r.data.data
         this.cars.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
      })
    },

    toAddAccident(formName){
     this.$refs[formName].validate((valid) => {
          if (valid) {
      this.$axios.post("accidentRecord/add",this.addAccident ).then(r=>{
        console.log(r);
        if(r.data.code==200){
          this.$message({type: 'success', message:"添加成功",  duration:800});
          this.addDialogFormVisible=false;
           this.$refs[formName].resetFields();
          this.loadAccident();
        }else{
          this.$message({type: 'error', message:"添加失败",  duration:800});
        }
        // this.drivers=r.data.data;
      })
      } else {
            return false;
          }
        });
    },

    addAccidentForm(formName){
      this.addDialogFormVisible=false;
        this.$refs[formName].resetFields();
    },
    showViewAccidentDialog(row){
       this.dialogshowAccidentVisible=true;
      //  this.editAccident=row;

      //  this.loadDriverName();
      //  console.log(this.drivers);console.log(this.tableData);
      //  for(let accident in this.tableData){
      //    if(accident.driverId==this.drivers.id){
      //      this.editAccident.driverName=this.drivers.employeeName;
      //    }
      //  }
       this.editAccident=row;
    },
    updateAccident(row){
      this.dialogEditVisible=true;
      
      this.loadAccident();
      // for(let accident in this.tableData){
      //   if(accident.id==row.id){
      //      this.editAccident=accident;
      //   }
      // }
      this.editAccident=row;
      this.loadDriverName();
      this.getCarList()
    },
    toUpdate(formName){
     this.$refs[formName].validate((valid) => {
          if (valid) {
      this.$axios.post("accidentRecord/update",this.editAccident).then(r=>{
        console.log(r);
        if(r.data.code==200){
          this.$message({type: 'success', message:"修改成功",  duration:800});
          this.dialogEditVisible=false;
          this.loadAccident();
        }else{
          this.$message({type: 'error', message:"修改失败",  duration:800});
        }
      })
      } else {
            return false;
          }
        });
    },

    editAccidentForm(formName){
      this.dialogEditVisible=false;
       this.$refs[formName].resetFields();
    },
    deleteAccident(id){

      this.$confirm("是否删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("accidentRecord/delete?id="+id).then(r=>{
          console.log(r);
          if(r.data.code==200){
            this.$message({type: 'success', message:"删除成功",  duration:800});
            this.loadAccident();
          }else{
            this.$message({type: 'error', message:"删除失败",  duration:800});
          }
        });
      }).catch(res => {
        this.$message.info("删除取消");
      })

    }
  },
  created(){
    this.loadAccident();
  }

}
</script>


<style>

</style>
