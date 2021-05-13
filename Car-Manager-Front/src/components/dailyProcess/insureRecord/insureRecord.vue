<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日常处理</el-breadcrumb-item>
      <el-breadcrumb-item>保险记录</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>


    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="输入车牌号查询" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="insurefindlist()"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showEditinsure()">新增保险记录</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="insurelist" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">

        <el-table-column fixed prop="carNum" label="车牌号码" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="insureNum" label="保险编号" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="beginDate" label="保险开始时间" min-width
                         :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="typename" label="保险种类" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="cost" label="保险金额" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="oppositename" label="保险公司" min-width
                         :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="outDate" label="保险到期时间" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="drivername" label="操作人员" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column fixed prop="remarks" label="备注" min-width :show-overflow-tooltip="true"></el-table-column>


        <el-table-column fixed label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip content="编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="goupdate(scope.row.id)"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="bottom" effect="light">
              <el-button type="danger" icon="el-icon-delete" circle @click="del(scope.row.id)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </template>
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


    <!-- 添加保养记录对话框
   :visible 是否显示对话框，如果 dialogFormVisible：true 就显示  否则就不显示
   rules 就是表单每项的数据校验  ref  就是将来可以通过 this.$ref.addCar
   el-form-item :就是el-form表单里面的每项-->
    <el-dialog title="新增保险记录" :visible.sync="insureVisible"
               ref="insureadds" center width="80%">
      <el-form :model="insureadds" label-width="150px" :rules="addrules" ref="addForm">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="车牌号" prop="cardId">
              <el-select v-model="insureadds.cardId" placeholder="请选择">
                <el-option label="请选择" value="0"></el-option>
                <el-option :label="cars.carNum" :value="cars.id"
                           v-for="cars in cars" :key="cars.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="保险编号：" prop="insureNum">
              <el-input v-model="insureadds.insureNum"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="经办人" prop="driverId">
              <el-select v-model="insureadds.driverId" placeholder="请选择">
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保险公司" prop="oppositeCompanyId">
              <el-select v-model="insureadds.oppositeCompanyId" placeholder="请选择">
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="opposites.name" :value="opposites.id"
                           v-for="opposites in opposites" :key="opposites.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="保险种类" prop="type">
              <el-select v-model="insureadds.type" placeholder="请选择">
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="insureType.text" :value="insureType.id"
                           v-for="insureType in insureTypes" :key="insureType.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="保险金额：" prop="cost">
              <el-input v-model="insureadds.cost"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保险备注：" prop="remarks">
              <el-input v-model="insureadds.remarks"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="10">
            <el-form-item label="保险开始日期：" prop="beginDate">

              <el-date-picker
                v-model="insureadds.beginDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>

            </el-form-item>
          </el-col>


          <el-col :span="10">
            <el-form-item label="保险到期日期：" prop="outDate">

              <el-date-picker
                v-model="insureadds.outDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>


        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="outinsure('addForm')">取 消</el-button>
        <el-button type="primary" @click="addinsure('addForm')">确 定</el-button>
      </div>
    </el-dialog>


    <!-- 修改加油记录信息 -->
    <el-dialog title="修改保险记录" :visible.sync="insureupdate"
               ref="addCar" center width="80%">
      <el-form :model="updates" label-width="150px" :rules="updaterules" ref="updateForm">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="车牌号" prop="carId">
              <el-input
                placeholder="请输入内容"
                v-model="updates.carNum"
                :disabled="true">
              </el-input>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保险编号：" prop="insureNum">
              <el-input v-model="updates.insureNum"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="经办人" prop="driverId">
              <el-select v-model="updates.driverId" placeholder="请选择">
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保险公司" prop="oppositeCompanyId">
              <el-select v-model="updates.oppositeCompanyId" placeholder="请选择">
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="opposites.name" :value="opposites.id"
                           v-for="opposites in opposites" :key="opposites.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保险种类" prop="type">
              <el-select v-model="updates.type" placeholder="请选择">
                <!-- <el-option label="请选择" value="0" ></el-option> -->
                <el-option :label="insureType.text" :value="insureType.id"
                           v-for="insureType in insureTypes" :key="insureType.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保险金额：" prop="cost">
              <el-input v-model="updates.cost" :disabled="true"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保险备注：" prop="remarks">
              <el-input v-model="updates.remarks"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="10">
            <el-form-item label="保险开始日期：" prop="beginDate">

              <el-date-picker
                v-model="updates.beginDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>

            </el-form-item>
          </el-col>


          <el-col :span="10">
            <el-form-item label="保险到期日期：" prop="outDate">

              <el-date-picker
                v-model="updates.outDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="outupdate('updateForm')">取 消</el-button>
        <el-button type="primary" @click="update('updateForm')">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      dialogStatus: "",

      addrules:{
          cardId: [
            { required: true, message: '请选择车牌', trigger: 'change' }
          ],
          insureNum: [
            { required: true, message: '请输入保险编号', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          beginDate:[
            { required: true, message: '请输入保险时间', trigger: 'blur' },          
          ], 
          type: [
            { required: true, message: '请选择保险种类', trigger: 'change' }
          ],
          cost: [
            { required: true, message: '请输入保险金额', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请输入正确的金额',
                  trigger: 'blur'
              }
          ],
          oppositeCompanyId: [
            { required: true, message: '请选择保险公司', trigger: 'change' }
          ],
          outDate:[
            { required: true, message: '请输入保险到期时间', trigger: 'blur' },          
          ], 
          driverId: [
            { required: true, message: '请选经办人', trigger: 'change' }
          ],

      },
      updaterules:{

          insureNum: [
            { required: true, message: '请输入保险编号', trigger: 'blur' },
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          beginDate:[
            { required: true, message: '请输入保险时间', trigger: 'blur' },          
          ], 
          type: [
            { required: true, message: '请选择保险种类', trigger: 'change' }
          ],

          oppositeCompanyId: [
            { required: true, message: '请选择保险公司', trigger: 'change' }
          ],
          outDate:[
            { required: true, message: '请输入保险到期时间', trigger: 'blur' },          
          ], 
          driverId: [
            { required: true, message: '请选经办人', trigger: 'change' }
          ],

      },

      //列表
      insurelist: [
        {
          id: "",
          cardId: "",
          carNum: "",//车牌号外键

          insureNum: "",//保险编号
          beginDate: "",//保险开始日期
          type: "",//保险种类
          typename: "",//保险种类名

          cost: "",//保险金额
          oppositeCompanyId: "",//保险公司
          oppositename: "",//保险公司名

          outDate: "",//保险到期日期
          driverId: "",//经办人
          drivername: "",//经办人名

          remarks: "",//备注
        }
      ],
      //分页
      searchText: '',
      p: 1,
      addDialogFormVisible: false,
      Employee: {},
      total: 0,
      size: 5,
      //添加
      insureadds:
        {
          id: "",
          cardId: "",
          carNum: "",//车牌号外键

          insureNum: "",//保险编号
          beginDate: "",//保险开始日期
          type: "",//保险种类
          typename: "",//保险种类名

          cost: "",//保险金额
          oppositeCompanyId: "",//保险公司
          oppositename: "",//保险公司名

          outDate: "",//保险到期日期
          driverId: "",//经办人
          drivername: "",//经办人名

          remarks: "",//备注

        },

      cars: [],//查询的车牌号下拉框
      opposites: [],//查询的保险单位下拉框
      drivers: [],//查询的经办人下拉框
      insureTypes: [],//保险类别下拉框

      //修改
      updates: {
        id: "",
        cardId: "",
        carNum: "",//车牌号外键

        insureNum: "",//保险编号
        beginDate: "",//保险开始日期
        type: "",//保险种类
        typename: "",//保险种类名

        cost: "",//保险金额
        oppositeCompanyId: "",//保险公司
        oppositename: "",//保险公司名

        outDate: "",//保险到期日期
        driverId: "",//经办人
        drivername: "",//经办人名

        remarks: "",//备注
      },
      //控制添加对话框是否显示
      insureVisible: false,
      //修改对话框
      insureupdate: false,
    }

  },
  methods: {
    //列表
    insurefindlist() {
      this.$axios.get("insureRecord/list", {
        params: {
          p: this.p,
          searchText: this.searchText,
          size: this.size
        }
      }).then(r => {
        this.insurelist = r.data.data.list
        this.total = r.data.data.total
      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.insurefindlist();
    },
    handleSizeChange(val) {
      this.size = val;
      this.insurefindlist();
    },

    //新增保险记录
    showEditinsure() {
      this.insureVisible = true;
      //查询车牌号下拉框
      this.$axios.post("oilrecord/carlist").then(r => {
        this.cars = r.data
      })

      //保险下拉框
      this.$axios.post("opposite/getoppolist?type=33").then(r => {
        this.opposites = r.data
      })

      //查询经办下拉框
      this.$axios.post("feerecord/getDriverName").then(r => {
        this.drivers = r.data.data;
      })
      //保养类别
      this.$axios.post("dictionary/menu").then(r => {
        this.insureTypes = r.data.data[3].children[2].children
      })


    },
    //添加
    addinsure(addForm) {

      this.$refs[addForm].validate((valid) => {
          if (valid) {
            this.$axios.post("insureRecord/add", this.insureadds).then(r => {
        if (r.data == 1) {
          this.$message({type: 'success', message: "添加成功", duration: 800});
          this.insureVisible = false;
          //循环清空editoppo集合中的值
          for (var i in this.insureadds) {
            this.insureadds[i] = "";
          }

          //重新加载页面
          this.insurefindlist();
          this.insureVisible = false;

        } else {
          this.$message({type: 'success', message: "添加失败", duration: 800});
        }
      })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      

    },
    //打开修改框
    goupdate(id) {
      this.insureupdate = true;
      //查询车牌号下拉框
      this.$axios.post("oilrecord/carlist").then(r => {
        this.cars = r.data
      })

      //保险下拉框
      this.$axios.post("opposite/getoppolist?type=33").then(r => {
        this.opposites = r.data
      })

      //查询经办下拉框
      this.$axios.post("feerecord/getDriverName").then(r => {
        this.drivers = r.data.data;
      })
      //保养类别
      this.$axios.post("dictionary/menu").then(r => {
        this.insureTypes = r.data.data[3].children[2].children
      })

      this.$axios.get("insureRecord/findbyid?id=" + id).then(r => {
        this.updates = r.data.data
      })

    },
    //取消添加框
    outinsure(addForm){
        this.insureVisible = false
        this.$refs[addForm].resetFields();
    },
    //修改
    update(updateForm) {

      this.$refs[updateForm].validate((valid) => {
          if (valid) {
            this.$axios.post("insureRecord/update", this.updates).then(r => {
        if (r.data == 1) {
          this.$message({type: 'success', message: "修改成功", duration: 800});
          this.keepupdate = false;
          //循环清空editoppo集合中的值
          for (var i in this.updates) {
            this.updates[i] = "";
          }

          //重新加载页面
          this.insurefindlist();
        } else {
          this.$message({type: 'success', message: "修改失败", duration: 800});
        }
      })

      this.insureupdate = false;
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      


    },
    //取消修改框
    outupdate(updateForm){
        this.insureupdate = false
        this.$refs[updateForm].resetFields();
    },
    //删除
    del(id) {
      this.$confirm("此操作将删除该条记录,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.post("insureRecord/delete?id=" + id).then(r => {
          
          if (r.data == 1) {
            this.$message({type: 'success', message: "删除成功", duration: 800});
            //重新加载页面
            this.insurefindlist();
          } else {
            this.$message({type: 'success', message: "删除失败", duration: 800});
          }
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },


  },
  created() {
    this.insurefindlist();
  }


}
</script>

<style>

</style>
