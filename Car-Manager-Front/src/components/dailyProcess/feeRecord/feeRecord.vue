<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日常处理</el-breadcrumb-item>
      <el-breadcrumb-item>规费记录</el-breadcrumb-item>
    </el-breadcrumb>

    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入车牌号码" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="findFee"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="addFees()">规费信息登记</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <template>
      <el-table :data="feeData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <!-- <el-table-column prop="id" label="编号" min-width></el-table-column> -->
        <el-table-column prop="car.carNum" label="车牌号码" min-width></el-table-column>
        <el-table-column prop="feeName" label="规费名称" min-width></el-table-column>
        <el-table-column prop="payTime" label="缴费时间" min-width :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.payTime | timeConvert() }}
          </template>
        </el-table-column>
        <el-table-column prop="fee" label="缴费金额" min-width></el-table-column>
        <el-table-column prop="oppositeName" label="收费单位" min-width></el-table-column>
        <el-table-column prop="driverName" label="经办人" min-width></el-table-column>
        <el-table-column prop="remarks" label="备注" min-width></el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scoped">
            <el-tooltip content="规费信息编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="updateFee(scoped.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="规费信息删除" placement="bottom" effect="light">
              <el-button type="danger" icon="el-icon-delete" circle @click="deleteFee(scoped.row.id)"></el-button>
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
    <el-dialog title="新增规费信息" :visible.sync="addDialogFormVisible" center width="80%">
      <el-form :model="fees" label-width="100px" :rules="rules" ref="addForm">
        <el-row :gutter="20">

          <el-col :span="8">

            <el-form-item label="车牌号" prop="carId">
              <el-select v-model="fees.carId" placeholder="请选择">
                <!-- <el-option label="请选择" value="0"></el-option> -->
                <el-option :label="car.carNum" :value="car.id"
                           v-for="car in cars" :key="car.id" :disabled="car.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="8">
            <el-form-item label="规费名称" prop="feeName">
              <el-input v-model="fees.feeName"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="缴费时间:" prop="payTime">
              <el-date-picker
                v-model="fees.payTime"
                type="datetime"
                placeholder="选择日期时间"
                value-format="yyyy-MM-dd HH-mm-SS"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="缴费金额" prop="fee">
              <el-input v-model="fees.fee"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="收费单位" prop="oppositeCompanyId">
              <el-select v-model="fees.oppositeCompanyId" placeholder="请选择">
                <!-- <el-option label="请选择" value="0"></el-option> -->
                <el-option :label="opposite.name" :value="opposite.id"
                           v-for="opposite in opposites" :key="opposite.id">
                </el-option>
              </el-select>
            </el-form-item>

          </el-col>

          <el-col :span="8">
            <el-form-item label="经办人" prop="driverId">
              <el-select v-model="fees.driverId" placeholder="请选择">
                <!-- <el-option label="请选择" value="0"></el-option> -->
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id" :disabled="driver.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="fees.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addFee('addForm')">取 消</el-button>
        <el-button type="primary" @click="toAddFees('addForm')">确 定</el-button>
      </span>
    </el-dialog>


    <!-- 修改 -->
    <el-dialog title="编辑规费记录" :visible.sync="dialogEditFeeVisible" center width="80%">
      <el-form :model="editFee" label-width="100px" :rules="rules" ref="editForm">
        <el-row :gutter="20">

          <el-col :span="8">

            <el-form-item label="车牌号" prop="carId">
              <el-select v-model="editFee.carId" placeholder="请选择" :disabled="true">
                <el-option :label="car.carNum" :value="car.id"
                           v-for="car in cars" :key="car.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="8">
            <el-form-item label="规费名称" prop="feeName">
              <el-input v-model="editFee.feeName"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="缴费时间:" >
              <el-date-picker
                v-model="editFee.payTime"
                type="datetime"
                placeholder="选择日期时间"
                value-format="yyyy-MM-dd HH:mm:ss"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="缴费金额" prop="fee" > 
              <el-input v-model="editFee.fee"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="收费单位" prop="oppositeCompanyId">
              <el-select v-model="editFee.oppositeCompanyId" placeholder="请选择">
                <el-option :label="opposite.name" :value="opposite.id"
                           v-for="opposite in opposites" :key="opposite.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="经办人" prop="driverId">
              <el-select v-model="editFee.driverId" placeholder="请选择">
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="editFee.remarks"></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editFeeForm('editForm')">取 消</el-button>
        <el-button type="primary" @click="toUpdateFee('editForm')">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>


<script>
export default {


  data() {
       var checkAge = (rule, value, callback) => {
        if (value=="") {
          return callback(new Error('车牌不能为空'));
        }

      }
    return {
      searchText: '',
      p: 1,
      total: 0,
      size: 5,
      feeData: [
        // {
        // id: '',
        // carId: 0,
        // feeName: '',
        // payTime: '',
        // fee: '',
        // oppositeCompanyId: '',
        // driverId: '',
        // remarks: '',
        // car: {carNum: ''},
        // oppositeName: '',
        // driverName: ''
      // }
      ],
      fees: {},
      //控制新增弹框
      addDialogFormVisible: false,
      // 车牌号
      cars: [],
      //经办人
      drivers: [],
      //单位名称
      opposites: [],
      //控制修改弹框
      dialogEditFeeVisible: false,
      editFee: {},

      rules:{
        carId:[
          {required: true, //是否必填       
          message: '车牌不能为空', //规则
          trigger: 'change'  //何事件触发
          }
        ],
        feeName:[
           {required: true,       
          message: '规费名称不能为空', 
          trigger: 'blur'  
          }
        ],
        payTime:[
          { required: true, message: '请选择日期', trigger: 'change'}
        ],
        fee:[
          { required: true, message: "金额不能为空", trigger: "blur"},
          { pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, message: '请输入正确额格式,可保留两位小数' },
        ],
        oppositeCompanyId:[
           {required: true,       
          message: '规费单位不能为空', 
          trigger: 'change'  
          }
        ],
        driverId:[
          {required: true,       
          message: '经办人不能为空', 
          trigger: 'change'  
          }
        ],
        remarks:[
          {required: true,       
          message: '备注为空请填‘无’', 
          trigger: 'blur'  
          }
        ]
      }

    }
  },
  methods: {
    findFee() {
      this.$axios.get("feerecord/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        // console.log(r);
        //   this.feeData=r.data;
        this.feeData = r.data.data.list
        this.total = r.data.data.total
        
      })
    },
    // 获取车牌信息
    getCarList() {
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
    // 获取驾驶员信息
    loadDriverName() {
      this.$axios.get("feerecord/getDriverName").then(r => {
        this.drivers = r.data.data;
         this.drivers.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
      })
    },
    getOpposite() {
      //查询往来单位下拉框
      this.$axios.post("opposite/getoppolist?type=" + 104).then(r => {
        this.opposites = r.data

        for (let i; i < this.feeData.length; i++) {
          if (this.feeData.oppositeCompanyId === r.data.id) {
            this.feeData.oppositeName = r.data.name;
          }
        }

      })

    },


    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.findFee();
    },
    handleSizeChange(val) {
      this.size = val;
      this.findFee();
    },
    // 打开添加框
    addFees() {
      this.addDialogFormVisible = true;

    },
    //调用添加方法
    toAddFees(formName) {
       this.$refs[formName].validate((valid) => {
         if (valid) {
            this.$axios.post("feerecord/add", this.fees).then(r => {
              console.log(r);
              if (r.data.code == 200) {
                this.$message({type: 'success', message: "添加成功", duration: 800});
                this.addDialogFormVisible = false;
                this.findFee();
              } else {
                this.$message({type: 'error', message: "添加失败", duration: 800});
              }

            })
        } else {
              return false;
            }
        });
    },
    addFee(formName){
      this.addDialogFormVisible=false;
      this.$refs[formName].resetFields();
    },
    // 进入修改框
    updateFee(row) {
      this.dialogEditFeeVisible = true;
      this.findFee();


      this.editFee = row;
    },
    // 去修改
    toUpdateFee(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
              this.getCarList()
            this.$axios.post("feerecord/update", this.editFee).then(r => {

              if (r.data.code == 200) {
                this.$message({type: 'success', message: "修改成功", duration: 800});
                this.dialogEditFeeVisible = false;
                this.findFee();
              } else {
                this.$message({type: 'error', message: "修改失败", duration: 800});
              }

            })
      } else {
            return false;
          }
        });
    },

    editFeeForm(formName){
      this.dialogEditFeeVisible=false;
      this.$refs[formName].resetFields();
    },
    // 删除
    deleteFee(id) {

      this.$confirm("是否删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("feerecord/delete?id=" + id).then(r => {
          console.log(r);
          if (r.data.code == 200) {
            this.$message({type: 'success', message: "删除成功", duration: 800});
            this.findFee();
          } else {
            this.$message({type: 'error', message: "删除失败", duration: 800});
          }
        });
      }).catch(res => {
        this.$message.info("删除取消");
      })
    }
  },
  created() {
    this.findFee();
    this.getOpposite();

  },mounted(){
    this.getCarList();
    this.loadDriverName();
  }
}

</script>


<style>

</style>
