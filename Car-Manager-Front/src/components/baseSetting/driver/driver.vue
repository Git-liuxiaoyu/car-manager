<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础设置</el-breadcrumb-item>
      <el-breadcrumb-item>驾驶员档案</el-breadcrumb-item>
    </el-breadcrumb>

    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入驾驶员姓名" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="loadDriver"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <!-- <el-button type="danger">批量删除</el-button> -->
        <el-button type="primary" @click="addDriver">新增</el-button>
      </el-col>
    </el-row>

    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border :key="tableData.id" style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column prop="employee.name" label="姓名" min-width></el-table-column>

        <el-table-column prop="deptName" label="部门" min-width></el-table-column>

        <el-table-column prop="employee.gender" label="性别" min-width>
          <template slot-scope="scope">{{ scope.row.employee.gender == '0' ? '男' : '女' }}</template>
        </el-table-column>

        <el-table-column prop="employee.telephone" label="电话" min-width></el-table-column>

        <el-table-column prop="employee.entryDate" label="入职时间" min-width>
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.employee.entryDate | dateConvert() }}
          </template>
        </el-table-column>

        <el-table-column prop="driverNum" label="驾照号码" min-width></el-table-column>
        <el-table-column prop="driverType" label="驾照类型" min-width></el-table-column>

        <el-table-column prop="status" label="是否启用" min-width>
          <template slot-scope="scope">{{ scope.row.status == '1' ? '启用' : '禁用' }}</template>
        </el-table-column>

        <el-table-column label="操作" width="170">
          <template slot-scope="scoped">
            <el-tooltip content="查看详细" placement="bottom" effect="light">
              <el-button icon="el-icon-search" circle @click="showDriverDialog(scoped.row)"></el-button>
            </el-tooltip>

            <el-tooltip content="编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="updateDriver(scoped.row)"></el-button>
            </el-tooltip>

            <el-tooltip content="删除" placement="bottom" effect="light">
              <el-button type="danger" icon="el-icon-delete" circle @click="deleteDriver(scoped.row)"></el-button>
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
    <el-dialog title="添加驾驶员" :visible.sync="addDialogFormVisible"
                ref="toAddDriver"  center width="80%">
      <el-form :model="driver"  :rules="rules" ref="addForm" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="姓名" prop="employee.name">
              <el-input v-model="driver.employee.name" @blur="dname($event)"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="部门" prop="deptId">
              <el-input v-model="driver.deptName" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <!-- <el-form-item label="生日" prop="birthday">
              <el-input v-model="driver.employee.birthday" readonly="readonly"></el-input>
            </el-form-item> -->
            <el-form-item label="生日:">
              <el-date-picker
                v-model="driver.employee.birthday"
                type="date"
                placeholder="选择日期时间"
                value-format="yyyy-MM-dd " 
              >
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="身份证" prop="idCard">
              <el-input v-model="driver.employee.idCard" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="电话" prop="telephone">
              <el-input v-model="driver.employee.telephone" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <!-- <el-form-item label="入职时间" prop="entryDate">
              <el-input v-model="driver.employee.entryDate" readonly="readonly"></el-input>
            </el-form-item> -->
            <el-form-item label="入职时间">
              <el-date-picker
                v-model="driver.employee.entryDate"
                align="right"
                type="date"
                placeholder="选择日期" readonly="readonly">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="地址" prop="address">
              <el-input v-model="driver.employee.address" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="驾照号码" prop="driverNum">
              <el-input  v-model="driver.driverNum"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="驾照类型" prop="type">
              <el-select v-model="driver.type" placeholder="请选择" >
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="drivertype.text" :value="drivertype.id"
                           v-for="drivertype in driverTypes" :key="drivertype.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="driver.remarks"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="状态" prop="status">
              <!-- <el-switch v-model="driver.status"></el-switch> -->
               <template>
                <el-radio v-model="driver.status" label="0">禁用</el-radio>
                <el-radio v-model="driver.status" label="1">启用</el-radio>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addCancel('addForm')">取 消</el-button>
        <el-button type="primary" @click="toAddDriver('addForm')">确 定</el-button>
      </div>


    </el-dialog>


    <!-- 查看详细 -->
    <el-dialog title="驾驶员详细信息" :visible.sync="dialogshowDriverVisible" center width="80%">
      <el-form :model="showDriver" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="showDriver.employee.name" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="部门" prop="deptId">
              <el-input  v-model="showDriver.deptName" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="生日" >
              <el-date-picker
                v-model="showDriver.employee.birthday"
                align="right"
                type="date"
                placeholder="选择日期" readonly="readonly">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="身份证" prop="idCard">
              <el-input v-model="showDriver.employee.idCard" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="电话" prop="telephone">
              <el-input v-model="showDriver.employee.telephone" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="入职时间">
              <el-date-picker
                v-model="showDriver.employee.entryDate"
                align="right"
                type="date"
                placeholder="选择日期" readonly="readonly">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="地址" prop="address">
              <el-input v-model="showDriver.employee.address" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="驾照号码" prop="driverNum">
              <el-input v-model="showDriver.driverNum" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="驾照类型" prop="type">
              <el-input v-model="showDriver.driverType" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="showDriver.remarks" readonly="readonly"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="状态" prop="status">
              <el-input v-model="showDriver.status" readonly="readonly">
              </el-input>

            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogshowDriverVisible = false">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 修改 -->
    <el-dialog title="编辑驾驶员信息" :visible.sync="dialogEdiDriverVisible" center width="80%">
      <el-form :model="showDriver" label-width="80px" :rules="eitdRules" ref="eitdForm">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="姓名" prop="employee.name">
              <el-input v-model="showDriver.employee.name"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="部门" prop="employee.deptId">
              <el-select v-model="showDriver.employee.deptId" placeholder="请选择" >
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="dept.text" :value="dept.id"
                           v-for="dept in depts" :key="dept.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="生日">
              <el-date-picker
                v-model="showDriver.employee.birthday"
                align="right"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="身份证" prop="employee.idCard">
              <el-input v-model="showDriver.employee.idCard"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="电话" prop="employee.telephone">
              <el-input type="number" v-model="showDriver.employee.telephone"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="入职时间">
              <el-date-picker
                v-model="showDriver.employee.entryDate"
                align="right"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>

          </el-col>

          <el-col :span="8">
            <el-form-item label="地址" prop="employee.address">
              <el-input v-model="showDriver.employee.address"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="驾照号码" prop="driverNum">
              <el-input v-model="showDriver.driverNum"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="驾照类型" prop="type">
              <el-select v-model="showDriver.type" placeholder="请选择" >
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="drivertype.text" :value="drivertype.id"
                           v-for="drivertype in driverTypes" :key="drivertype.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="showDriver.remarks"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="状态">
              <el-radio-group v-model="showDriver.status">
                <el-radio  :label="0">禁用</el-radio>
                <el-radio :label="1">启用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="eitdDriverForm('eitdForm')">取 消</el-button>
        <el-button type="primary" @click="toUpdateDriver('eitdForm')">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>


<script>
export default {

  data() {

    return {
      searchText: '',
      p: 1,
      total: 5,
      size: 5,
      tableData: [
        // {
        // driverNum:'',
        // type:'',
        // status:'',
        // remarks:'',
        // deptName:'',
        // employee:{name:'',deptId: '' ,gender:'',telephone:"",entryDate:"",address:'',birthday:'',idCard:''}
        // }
      ],
      // 字典信息
      dictionary: [],
      depts: [],
      driverTypes: [],
      driver: {
        driverNum: '',
        type: '',
        status: '',
        remarks: '',
        deptName:'',
        employee: {
          name: '',
          deptId: '',
          gender: '',
          telephone: "",
          entryDate: "",
          address: '',
          birthday: '',
          idCard: ''
        }
      },
      employees: [],
      rules: {
      "employee.name":[
        {
          required: true, //是否必填       
          message: '姓名不能为空', //规则
          trigger: 'blur'  //何事件触发
        }
       ],
        driverNum:[
          {
            required: true, //是否必填
          
          message: '请输入正确驾照号码', //规则
          trigger: 'blur'  //何事件触发
          },
          {  pattern: '^[0-9]*$',min: 18, max: 18, message: '请输入正确驾照号码', trigger: 'blur' }
          ],
          type:[
          {required: true, //是否必填
          message: '请选择驾照类型’', //规则
          trigger: 'blur'  //何事件触发
          },
          ],
          status:[
          {required: true, //是否必填
          message: '请选择状态', //规则
          trigger: 'change'  //何事件触发
          },
          ],
          remarks:[
          {required: true, //是否必填
          message: '备注为空请填‘无’', //规则
          trigger: 'blur'  //何事件触发
          },
          ]
      },
      //添加框
      addDialogFormVisible: false,
      // 详细框
      dialogshowDriverVisible: false,
      showDriver: {
        driverNum: '',
        type: '',
        status: '',
        remarks: '',
        deptName:"",
        driverType:"",
        employee: {
          name: '',
          deptId: '',
          gender: '',
          telephone: "",
          entryDate: "",
          address: '',
          birthday: '',
          idCard: ''
        }
      },
      // 修改框
      dialogEdiDriverVisible: false,

      eitdRules:{
        "employee.name":[
           {
             required: true, //是否必填
          //  
            message: '姓名不能为空', //规则
            trigger: 'blur'  //何事件触发
            },
             { pattern: '^[\u4E00-\u9FA5]{2,4}$', message: '请输入2到4个汉字', trigger: 'blur' }
          ],
          "employee.idCard":[
            { required: true, 
          //  pattern: '/^\s+|\s+$/g',
            message: '身份证不能为空', 
            trigger: 'blur'  },
             { min:18,max:18, message: '请输入18个数字', trigger: 'blur' }
          ],
          "employee.telephone":[
            { required: true, 
            message: '手机号不能为空', 
            trigger: 'blur'  },
            {pattern: '^1[3|4|5|7|8][0-9]{9}$', message: '请输入11位手机号', trigger: 'blur' }
          ],
          "employee.address":[
           { required: true, 
            message: '地址不能为空', 
            trigger: 'blur'  },
           ],
          driverNum:[
           { required: true, 
            
            message: '驾照号码不能为空', 
            trigger: 'blur'  },
             { pattern: '^[0-9]*$', min: 18, max: 18, message: '请输入18位驾照号码', trigger: 'blur' }
          ],
           remarks:[
          {required: true, //是否必填
          message: '备注为空请填‘无’', //规则
          trigger: 'blur'  //何事件触发
          },
          ]
      }

    }
  },
  methods: {

    loadDriver() {
      this.$axios.get("driver/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.tableData = r.data.data.list
        this.total = r.data.data.total
        this.tableData.forEach(e1 => {
          this.depts.forEach(e2 => {
            if (e1.employee.deptId === e2.id) {
              e1.deptName = e2.text;
              this.showDriver.deptName= e2.text;
            }
            
          })
          this.driverTypes.forEach(e3=>{
            if(e1.type===e3.id){
              e1.driverType = e3.text;
              this.showDriver.driverType = e3.text;
            }
          })
          
        });
        




      });
    },

    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.loadDriver();
    },
    handleSizeChange(val) {
      this.size = val;
      this.loadDriver();
    },
    // 字典
    loadDictionary() {

      this.$axios.get("dictionary/menu").then(r => {
        this.dictionary = r.data.data;
        //获取部门信息
        for (let i = 0; i < this.dictionary.length; i++) {
          if (this.dictionary[i].id === 1) {
            this.depts = this.dictionary[i].children;

          }
        }
        //获取驾照类型
        for (let i = 0; i < this.dictionary.length; i++) {
          if (this.dictionary[i].id === 51) {
            this.driverTypes = this.dictionary[i].children;

          }
        }

      })
    },
    addDriver() {
      this.addDialogFormVisible = true;
    },
    //获取驾驶员的员工信息
    dname(event) {
      let name = event.target.value;
      // alert(name);
     this.$axios.get("driver/getOne?name=" + name).then(r => {

           if(r.data.code == 200){
              this.$axios.get("driver/getDriver?name=" + name).then(r => {
                   if(r.data.code == 200){

                     this.$axios.get("driver/dname?name=" + name).then(r => {

                    if (r.data.code == 200) {
                      this.driver.employee = r.data.data;

                      this.depts.forEach(e2 => {
                        if (this.driver.employee.deptId == e2.id) {
                          this.driver.deptName = e2.text;

                        }
                        
                      })

                    }
                   });

                   }else{
                     this.$message({type: 'error', message: "此员工已经是驾驶员，请重新输入", duration: 2000});
                   }
              });

           }else{
             this.$message({type: 'error', message: "没有此员工", duration: 2000});
           }
     })

    },
    toAddDriver(formName) {
      this.$refs[formName].validate((valid) => {
      if (valid) {
            this.$axios.post("driver/addDriver", this.driver).then(r => {
              if (r.data.code == 200) {
                this.$message({type: 'success', message: "添加成功", duration: 800});
                 this.$refs[formName].resetFields();
                this.addDialogFormVisible = false;
                    for (var i in this.driver.employee) {
                    this.driver.employee[i] = "";
                  }
                  this.driver.deptName="",
                  this.p=1;

              this.loadDriver();
              } else {
                this.$message({type: 'error', message: "添加失败", duration: 800});
              }
            })
            } else {

            return false;
          }
        });
    },

    addCancel(formName){
       this.addDialogFormVisible = false;
       this.$refs[formName].resetFields();
    },

    showDriverDialog(row) {

      this.dialogshowDriverVisible = true;
      //   this.showDriver.employee.name=row.employee.name;
      //   this.showDriver.employee.deptId=row.employee.deptId;
      //   this.showDriver.employee.gender=row.employee.gender;
      //   this.showDriver.employee.telephone=row.employee.telephone;
      //   this.showDriver.employee.entryDate=row.employee.entryDate;
      //  this.showDriver.employee.address=row.employee.address;
      //  this.showDriver.employee.birthday=row.employee.birthday;
      //  this.showDriver.employee.idCard=row.employee.idCard;
      //  this.showDriver.driverNum=row.driverNum;
      //  this.showDriver.type=row.type;
      //  this.showDriver.remarks=row.remarks;
      //  this.showDriver.status=row.status;
      this.showDriver = row
      if (this.showDriver.status == 1) {
        this.showDriver.status = "启动";
      } else {
        this.showDriver.status = "禁止";
      }

    },

    updateDriver(row) {
       if (this.showDriver.status == "启动") {
        this.showDriver.status = 1;
      } else if (this.showDriver.status == "禁止"){
        this.showDriver.status = 0;
      }
      this.dialogEdiDriverVisible = true;
      this.loadDriver();
      for(let driver in this.tableData){
        if(driver.id==row.id){
          this.showDriver=driver
        }
      }
      this.showDriver = row
    },

    toUpdateDriver(formName) {
      this.$refs[formName].validate((valid) => {
      if (valid) {
          this.$axios.post("driver/updated", this.showDriver).then(r => {
            if (r.data.code == 200) {
              this.$message({type: 'success', message: "修改成功", duration: 800});
            this.dialogEdiDriverVisible = false;
              this.loadDriver();
            } else {
              this.$message({type: 'error', message: "修改失败", duration: 800});
            }
          })
          } else {

            return false;
          }
        });
    },
    eitdDriverForm(formName){
      this.dialogEdiDriverVisible=false;
      this.$refs[formName].resetFields();
    },
    deleteDriver(row) {
      this.$confirm("是否删除?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.post("driver/del",row).then(r=>{
          if(r.data.code==200){
            this.$message({type: 'success', message:"删除成功",  duration:800});
            this.p=1;
            this.loadDriver();
          }else{
            this.$message({type: 'error', message:"删除失败",  duration:800});
          }
        });
      }).catch(res => {
        this.$message.info("删除取消");
      })
    },
  },
  created() {

    this.loadDictionary();

  },
  mounted(){
    this.loadDriver();
  }

}
</script>


<style>

</style>
