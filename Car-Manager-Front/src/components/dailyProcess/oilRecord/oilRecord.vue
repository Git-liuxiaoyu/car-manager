<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日常处理</el-breadcrumb-item>
      <el-breadcrumb-item>加油记录</el-breadcrumb-item>
    </el-breadcrumb>

    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="按车牌号查询" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="oilrelist()"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showEditoilre()">新增加油记录</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
        <el-table :data="oilrecords" border style="width: 100%" max-height="377"
                  :header-cell-style="{background:'#eef1f6',color:'#606266'}">

        <el-table-column prop="carNum" label="车牌号" min-width>

        </el-table-column>

        <el-table-column prop="oppositename" label="往来单位" min-width>


        </el-table-column>

        <el-table-column prop="addTime" label="加油时间" min-width :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.addTime | timeConvert() }}
          </template>
        </el-table-column>

        <el-table-column prop="oilTypename" label="油料标号" min-width>

        </el-table-column>

        <el-table-column prop="prePrice" label="油料单价" min-width>

        </el-table-column>

        <el-table-column prop="thisOilVolume" label="本次加油" min-width>

        </el-table-column>

        <el-table-column prop="lastOilVolume" label="上次加油" min-width>

        </el-table-column>

        <el-table-column prop="price" label="加油总价" min-width>

        </el-table-column>

        <el-table-column prop="thisMileage" label="本次里程" min-width>

        </el-table-column>

        <el-table-column prop="lastMileage" label="上次里程" min-width>

        </el-table-column>

        <el-table-column prop="remarks" label="备注" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="drivername" label="加油人员" min-width>

        </el-table-column>

        <el-table-column prop="" label="操作" width="120">
          <template slot-scope="scope">
            <el-button type="primary" icon="el-icon-edit" circle plain
                       @click="goupdate(scope.row.id)"
            ></el-button>
            <el-button type="danger" icon="el-icon-delete" circle plain
                       @click="del(scope.row.id)"
            ></el-button>
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




    <!-- 添加加油记录对话框
      :visible 是否显示对话框，如果 dialogFormVisible：true 就显示  否则就不显示
      rules 就是表单每项的数据校验  ref  就是将来可以通过 this.$ref.addCar
      el-form-item :就是el-form表单里面的每项-->
    <el-dialog title="新增加油记录" :visible.sync="oilreVisible"
               ref = "addCar" center width="80%">
      <el-form :model="editoilreadd" label-width="150px" :rules="addrules" ref="addForm">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="车牌号" prop="carId">
              <el-select v-model="editoilreadd.carId" placeholder="请选择">
                <el-option label="请选择" value="0" ></el-option>
                <el-option :label="cars.carNum" :value="cars.id"
                           v-for="cars in cars" :key="cars.id" :disabled="cars.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="加油站" prop="oppositeCompanyId">
              <el-select v-model="editoilreadd.oppositeCompanyId" placeholder="请选择">
                <el-option :label="opposites.name" :value="opposites.id"
                           v-for="opposites in opposites" :key="opposites.id" :disabled="opposites.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="10">
            <el-form-item label="加油时间：" prop="addTime">
              <el-date-picker
                v-model="editoilreadd.addTime"
                type="datetime"
                value-format="yyyy-MM-DD HH:mm:ss"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="油料标号" prop="oilType">
              <el-select v-model="editoilreadd.oilType" placeholder="请选择" :trigger-click="dnamead(editoilreadd.oilType)" >            
                <el-option :label="oilTypes.text" :value="oilTypes.id"
                           v-for="oilTypes in oilTypes" :key="oilTypes.id" >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="油料单价（每升）：" prop="prePrice">
              <el-input v-model="editoilreadd.prePrice" @blur="dnameadd()"></el-input>
            </el-form-item>
          </el-col>



          <el-col :span="6">
            <el-form-item label="本次加油（升）：" prop="thisOilVolume">
              <el-input v-model="editoilreadd.thisOilVolume" @blur="dnameadd()"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="加油总价：" prop="price">
              <el-input v-model="editoilreadd.price"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="上次加油（升）：" prop="lastOilVolume">
              <el-input v-model="editoilreadd.lastOilVolume"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="本次里程：" prop="thisMileage">
              <el-input v-model="editoilreadd.thisMileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="上次里程：" prop="lastMileage">
              <el-input v-model="editoilreadd.lastMileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              <el-input v-model="editoilreadd.remarks"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="加油人员" prop="driverId">
              <el-select v-model="editoilreadd.driverId" placeholder="请选择">
                <el-option label="请选择" value="0" ></el-option>
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id" :disabled="driver.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="outoiler('addForm')">取 消</el-button>
        <el-button type="primary" @click="addoilre('addForm')">确 定</el-button>
      </div>
    </el-dialog>








    <!-- 修改加油记录信息 -->
    <el-dialog title="修改加油记录" :visible.sync="oilreupdate"
               ref = "updates" center width="80%">
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
            <el-form-item label="加油站" prop="oppositeCompanyId">
              <el-select v-model="updates.oppositeCompanyId" placeholder="请选择">
                
                <el-option :label="opposites.name" :value="opposites.id"
                           v-for="opposites in opposites" :key="opposites.id" :disabled="opposites.disabled">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="10">
            <el-form-item label="加油时间" prop="addTime">
              <el-input
                placeholder="加油时间"
                v-model="updates.addTime"
                :disabled="true">
              </el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="油料标号" prop="oilType">
              <el-select v-model="updates.oilType" placeholder="请选择" :trigger-click="dnameup(updates.oilType)">
                <el-option :label="oilType.text" :value="oilType.id"
                           v-for="oilType in oilTypes" :key="oilType.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="油料单价（每升）：" prop="prePrice">
              <el-input v-model="updates.prePrice" @blur="dnameupdate()"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="本次加油（升）：" prop="thisOilVolume">
              <el-input v-model="updates.thisOilVolume" @blur="dnameupdate()"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="加油总价：" prop="price">
              <el-input v-model="updates.price"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="上次加油（升）：" prop="lastOilVolume">
              <el-input v-model="updates.lastOilVolume"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="本次里程：" prop="thisMileage">
              <el-input v-model="updates.thisMileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="上次里程：" prop="lastMileage">
              <el-input v-model="updates.lastMileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              <el-input v-model="updates.remarks"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="加油人员" prop="driverId">
              <el-select v-model="updates.driverId" placeholder="请选择">
                <el-option label="请选择" value="0" ></el-option>
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id" :disabled="driver.disabled">
                </el-option>
              </el-select>
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
export default {
  data() {
    return {
      dialogStatus:"",

      //添加表单验证
      addrules:{
        carId: [
            { required: true, message: '请选择车牌', trigger: 'change' }
          ],
        oppositeCompanyId: [
            { required: true, message: '请选择加油站', trigger: 'change' }
          ],
        addTime: [
            { required: true, message: '请选择加油时间', trigger: 'change' }
          ],
        oilType: [
            { required: true, message: '请选择油料标号', trigger: 'change' }    
          ],
        thisOilVolume: [
            { required: true, message: '请输入本次加油量', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请正确输入油量',
                  trigger: 'blur'
              }
          ],
        lastOilVolume: [
            { required: true, message: '请输入上次加油量', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请正确输入油量',
                  trigger: 'blur'
              }
          ],
        thisMileage : [
            { required: true, message: '请输入本次里程', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请正确输入里程',
                  trigger: 'blur'
              }
          ],
        lastMileage : [
            { required: true, message: '请输入上次里程', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请正确输入里程',
                  trigger: 'blur'
              }
          ],
        driverId: [
            { required: true, message: '请选择加油人员', trigger: 'change' }
          ],


      },
       //修改表单验证
      updaterules:{
        carId: [
            { required: true, message: '请选择车牌', trigger: 'change' }
          ],
        oppositeCompanyId: [
            { required: true, message: '请选择加油站', trigger: 'change' }
          ],
        addTime: [
            { required: true, message: '请选择加油时间', trigger: 'change' }
          ],
        oilType: [
            { required: true, message: '请选择油料标号', trigger: 'change' }    
          ],
        thisOilVolume: [
            { required: true, message: '请输入本次加油量', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请正确输入油量',
                  trigger: 'blur'
              }
          ],
        lastOilVolume: [
            { required: true, message: '请输入上次加油量', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请正确输入油量',
                  trigger: 'blur'
              }
          ],
        thisMileage : [
            { required: true, message: '请输入本次里程', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请正确输入里程',
                  trigger: 'blur'
              }
          ],
        lastMileage : [
            { required: true, message: '请输入上次里程', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请正确输入里程',
                  trigger: 'blur'
              }
          ],
        driverId: [
            { required: true, message: '请选择加油人员', trigger: 'change' }
          ],


      },



      //列表
      oilrecords:[
        {
          id:"",//id
          carId:"",//车牌号id
          carNum:"",//车牌号，查出-----
          oppositeCompanyId:"",//往来单位id
          oppositename:"",//往来单位名，查出-----------
          addTime:"",//加油时间
          oilType:"",//油料标号
          oilTypename:"",//油料标号名，查出--------
          prePrice:"",//油料单价
          thisOilVolume:"",//本次加油
          lastOilVolume:"",//上次加油
          price:"",//加油总价
          thisMileage:"",//本次里程
          lastMileage:"",//上次里程
          remarks:"",//备注
          driverId:"",//加油人员
          drivername:"",//加油人员名，查出---------
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
      editoilreadd:
        {
          id:"",//id
          carId:"",//车牌号id
          carNum:"",//车牌号，查出-----
          oppositeCompanyId:"",//往来单位id
          oppositename:"",//往来单位名，查出-----------
          addTime:"",//加油时间
          oilType:"",//油料标号
          oilTypename:"",//油料标号名，查出--------
          prePrice:"",//油料单价
          thisOilVolume:"",//本次加油
          lastOilVolume:"",//上次加油
          price:"",//加油总价
          thisMileage:"",//本次里程
          lastMileage:"",//上次里程
          remarks:"",//备注
          driverId:"",//加油人员
          drivername:"",//加油人员名，查出---------

        } ,

      cars:[],//查询的车牌号下拉框
      opposites:[],//查询的往来单位下拉框
      drivers:[],//查询的加油人员下拉框
      oilTypes:[],//油料标号下拉框

      //修改
      updates:{
        id:"",//id
        carId:"",//车牌号id
        carNum:"",//车牌号，查出-----
        oppositeCompanyId:"",//往来单位id
        oppositename:"",//往来单位名，查出-----------
        addTime:"",//加油时间
        oilType:"",//油料标号
        oilTypename:"",//油料标号名，查出--------
        prePrice:"",//油料单价
        thisOilVolume:"",//本次加油
        lastOilVolume:"",//上次加油
        price:"",//加油总价
        thisMileage:"",//本次里程
        lastMileage:"",//上次里程
        remarks:"",//备注
        driverId:"",//加油人员
        drivername:"",//加油人员名，查出---------
      },


      //控制添加对话框是否显示
      oilreVisible:false,

      //修改对话框
      oilreupdate:false,




    }

  },

  methods:{
    //列表
    oilrelist(){
      var oitype =[];
      this.$axios.get("oilrecord/list",{params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {


        this.oilrecords = r.data.data.list
        this.total = r.data.data.total
      })


    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.oilrelist();
    },
    handleSizeChange(val) {
      this.size = val;
      this.oilrelist();
    },


    //显示新增对话框
    showEditoilre() {
      this.oilreVisible  = true;
      //查询车牌号下拉框
      this.$axios.post("car/getAll").then(r => {
        this.cars = r.data.data
        this.cars.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
      })

      //查询往来单位下拉框
      this.$axios.post("oilrecord/oppolist").then(r => {
        this.opposites = r.data
        this.opposites.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
      })

      //查询加油人员下拉框
      this.$axios.post("driver/getAll").then(r => {
        this.drivers=r.data.data;
        this.drivers.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
      })
      this.$axios.post("dictionary/menu").then(r => {
        this.oilTypes = r.data.data[9].children
      })


    },
    //添加加油记录
    addoilre(addForm){
      this.$refs[addForm].validate((valid) => {
          if (valid) {
              this.$axios.post("oilrecord/add", this.editoilreadd).then(r => {
                if(r.data.code == 200){
                  this.$message({type: 'success', message:"添加成功",  duration:800});
                  this.oppoVisible = false;

                  //循环清空editoppo集合中的值
                  for (var i in this.editoilreadd) {
                    this.editoilreadd[i] = "";
                  }

                  //重新加载页面
                  this.oilrelist();
                  this.oilreVisible  = false;

                }else{
                  this.$message({type: 'success', message:"添加失败",  duration:800});
                }
              })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      
    },
    //取消添加框
    outoiler(addForm){
      this.oilreVisible = false
      this.$refs[addForm].resetFields();

    },
    //打开修改对话框
    goupdate(id){
      this.oilreupdate=true;
      //查询车牌号下拉框
      this.$axios.post("oilrecord/carlist").then(r => {
        this.cars = r.data
      })

      //查询往来单位下拉框
      this.$axios.post("oilrecord/oppolist").then(r => {
        this.opposites = r.data
        this.opposites.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
      })

      //查询加油人员下拉框
      this.$axios.post("driver/getAll").then(r => {
        this.drivers=r.data.data;
        this.drivers.forEach(e=>{
          if (e.status===0){
            e.disabled = true
          }else{
            e.disabled = false
          }
        })
      })

      this.$axios.post("dictionary/menu").then(r => {
        this.oilTypes = r.data.data[9].children
      })


      this.$axios.get("oilrecord/findbyid?id="+id).then(r => {
        this.updates = r.data

      })
    },
    //取消修改框
    outupdate(updateform){
        this.oilreupdate = false

        this.$refs[updateform].resetFields();

    },

    update(updateform){
        this.$refs[updateform].validate((valid) => {
          if (valid) {
            this.$axios.post("oilrecord/update",this.updates).then(r => {
              if(r.data.code == 200){
                this.$message({type: 'success', message:"修改成功",  duration:800});
                this.oppoupdate = false;
                //循环清空editoppo集合中的值
                for (var i in this.updates) {
                  this.updates[i] = "";
                }

                //查询车牌号下拉框
                this.$axios.post("oilrecord/carlist").then(r => {
                  this.cars = r.data
                })

                //查询往来单位下拉框
                this.$axios.post("oilrecord/oppolist").then(r => {
                  this.opposites = r.data
                })

                //查询加油人员下拉框
                this.$axios.post("oilrecord/driverlist").then(r => {
                  this.driverIds = r.data
                })

                //重新加载页面
                this.oilrelist();
              }else{
                this.$message({type: 'success', message:"修改失败",  duration:800});
              }
            })

            this.oilreupdate = false;
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      
    },

    del(id){
      this.$confirm("此操作将删除该条记录,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.post("oilrecord/delete?id="+id).then(r => {
          if(r.data.code == 200){
            this.$message({type: 'success', message:"删除成功",  duration:800});
            //重新加载页面
            this.oilrelist();
          }else{
            this.$message({type: 'success', message:"删除失败",  duration:800});
          }
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },
    //计算添加栏油料总价
    dnameadd(){
      var prePrice = this.editoilreadd.prePrice;
      var thisOilVolume = this.editoilreadd.thisOilVolume;

      this.editoilreadd.price = (prePrice*thisOilVolume).toFixed(2);

    },//计算各类油料的单价添加栏
    dnamead(event){
      if(event == 59){//92#
          this.editoilreadd.prePrice = 6.68      
      }else if(event == 60){//柴油
          this.editoilreadd.prePrice = 6.29        
      }else if(event == 61){//98#
          this.editoilreadd.prePrice = 8.06
      }else if(event == 109){//95#
          this.editoilreadd.prePrice = 7.16
      }
      this.dnameadd()
      
    },
    //计算修改栏油料总价
    dnameupdate(){
      var prePrice = this.updates.prePrice;
      var thisOilVolume = this.updates.thisOilVolume;

      this.updates.price = (prePrice*thisOilVolume).toFixed(2);

    },
    //设置各类油料的单价 修改栏
    dnameup(event){
      
      if(event == 59){//92#
          this.updates.prePrice = 6.68      
      }else if(event == 60){//柴油
          this.updates.prePrice = 6.29        
      }else if(event == 61){//98#
          this.updates.prePrice = 8.06
      }else if(event == 109){//95#
          this.updates.prePrice = 7.16
      }
      this.dnameupdate()
    }


  },
  created(){
    this.oilrelist()
  }

}
</script>

<style scoped>

</style>
