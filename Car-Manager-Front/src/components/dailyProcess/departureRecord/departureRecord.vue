<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>出车记录</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="5">
        <el-input placeholder="请输入要查询的车牌" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="findOutCar"></el-button>
        </el-input>
      </el-col>
      <el-col :span="19">
        <el-button type="primary" @click="outCar">出车记录登记</el-button>
        <el-button type="success" @click="inCar">回车记录登记</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                highlight-current-row
                @current-change="sub" :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="carName" label="车牌号码" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="deptName" label="部门" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="outTime" label="出车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="preInTime" label="预计回车时间" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="inTime" label="回车时间" min-width :show-overflow-tooltip="true">
          <template slot-scope="scope">{{ scope.row.inTime == null ? '未回车' : scope.row.inTime }}</template>
        </el-table-column>
        <el-table-column prop="followPerson" label="随行人员" min-width></el-table-column>
        <el-table-column prop="driverName" label="驾驶员" min-width :show-overflow-tooltip="true">
        </el-table-column>

        <el-table-column prop="destination" label="目的地" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="mileage" label="本次行程" min-width :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="employeeName" label="创建人员" min-width :show-overflow-tooltip="true"></el-table-column>

        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip content="出车信息修改" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="updateOutCar(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="查看详细" placement="bottom" effect="light" v-if="scope.row.inTime!=null">
              <el-button type="primary" icon="el-icon-view" circle @click="showCar(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="查看详细" placement="bottom" effect="light" v-if="scope.row.inTime==null">
              <el-button type="primary" icon="el-icon-view" circle @click="showCarEqNull"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>

      </el-table>
    </template>


    <el-dialog title="出车记录登记" :visible.sync="addDialogFormVisible" center width="50%">
      <el-form :model="addData" label-width="120px" :rules="addrules" ref="addForm">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="车牌号码:"  prop="carId">
              <el-select v-model="addData.carId" placeholder="请选择" @focus="addressdow()">
                <el-option v-for="item in availableCar" :key="item.id"
                           :label="item.carNum" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="部门:"  prop="deptId">
              <el-select v-model="addData.deptId" placeholder="请选择" @focus="addressdow()">
                <el-option v-for="item in dept" :key="item.id"
                           :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:"  prop="outTime">
              <el-date-picker v-model="addData.outTime" type="datetime"
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS" @focus="addressdow()">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="预计回车时间:"  prop="preInTime">
              <el-date-picker v-model="addData.preInTime" type="datetime"
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS" @focus="addressdow()">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="随行人员:"  prop="followPerson">
              <el-input v-model="addData.followPerson" @focus="addressdow()"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="驾驶员:"  prop="driverId">
              <el-select v-model="addData.driverId" placeholder="请选择" @focus="addressdow()">
                <el-option v-for="item in driverName" :key="item.id"
                           :label="item.employeeName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="目的地:"  prop="destination">
              <el-input v-model="addData.destination"  @focus="addressshow()"  id='address'></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="5" style="display:none;"  id="addbutton">
           <el-button plain @click="luxian()">规划驾车路线</el-button>
          </el-col>
          <el-col :span="5" style="display:none;"  id="addmapbutton">
           <el-button plain @click="addressdow()">关闭地图</el-button>
          </el-col>
          <div id="container" style="display:none;"></div>
        <div class="input-item" style="display:none;">
          <div class="input-item-prepend"><span class="input-item-text">经纬度</span></div>
          <input id='lnglat' type="text" >
        </div>

          <el-col :span="12">
            <el-form-item label="开始里程(公里):"  prop="mileage">
              <el-input v-model="addData.mileage"  @focus="addressdow()"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="出车原因:"  prop="outCarReason">
              <el-input v-model="addData.outCarReason" type="textarea"  @focus="addressdow()"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="outcardow('addForm')">取 消</el-button>
        <el-button type="primary" @click="doOutCar('addForm')">确 定</el-button>
      </span>
    </el-dialog>

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


    <!--查看详细-->
    <el-drawer
      title="出车记录信息"
      :visible.sync="showOutCarFormVisible"
      direction="rtl"
      size="50%">
      <hr/>
      <el-form :model="showData" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="车牌号码:">
              <el-input v-model="showData.carName" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="部门:">
              <el-input v-model="showData.deptName" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:">
              <el-date-picker v-model="showData.outTime" type="datetime" disabled
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="预计回车时间:">
              <el-date-picker v-model="showData.preInTime" type="datetime" disabled
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>


          <el-col :span="12">
            <el-form-item label="回车时间:">
              <el-date-picker v-model="showData.outTime" type="datetime" disabled
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="驾驶员:">
              <el-select v-model="showData.driverId" placeholder="请选择" disabled>
                <el-option v-for="item in driverName" :key="item.id"
                           :label="item.employeeName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="11">
            <el-form-item label="随行人员:">
              <el-input v-model="showData.followPerson" disabled></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="11" :push="1">
            <el-form-item label="目的地:">
              <el-input v-model="showData.destination" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="23">
            <el-form-item label="出车原因:">
              <el-input v-model="showData.outCarReason" type="textarea" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11">
            <el-form-item label="开始里程(公里):">
              <el-input v-model="showData.mileage" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11" :push="1">
            <el-form-item label="回车里程(公里):">
              <el-input v-model="showData.returnMileage" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11">
            <el-form-item label="本次里程(公里):">
              <el-input v-model="showData.zongMileage" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11" :push="1">
            <el-form-item label="停放位置:">
              <el-input v-model="showData.stopPosition" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11">
            <el-form-item label="备注:">
              <el-input v-model="showData.remarks" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="11" :push="1">
            <el-form-item label="创建人:">
              <el-input v-model="showData.employeeName" disabled></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
    </el-drawer>





    <el-dialog title="回车记录登记" :visible.sync="addInCarFormVisible" center width="50%">
      <el-form :model="subInCar" label-width="120px" :rules="returnrules" ref="returnForm">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="车牌号码:"  prop="carId">
              <el-input v-model="subInCar.carName" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="驾驶员:"  prop="driverName">
              <el-input v-model="subInCar.driverName" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:"  prop="outTime">
              <el-date-picker v-model="subInCar.outTime" type="datetime" disabled
                              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" align="right">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="回车时间:"  prop="inCarTime">
              <el-date-picker v-model="subInCar.inCarTime" type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间" align="right"  @focus="stopdow()">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="开始里程(公里):"  prop="mileage">
              <el-input v-model="subInCar.mileage" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="回车里程(公里):"  prop="returnMileage">
              <el-input v-model="subInCar.returnMileage"  @focus="stopdow()"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="停放位置:"  prop="stopPosition">
              <el-input v-model="subInCar.stopPosition"  @focus="stopshow()"  id='address2'></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="5" style="display:none;"  id="stopbutton">
           <el-button plain @click="stopluxian()">规划停车路线</el-button>
          </el-col>
          <el-col :span="5" style="display:none;"  id="stopmapbutton">
           <el-button plain @click="stopdow()">关闭地图</el-button>
          </el-col>

          <div id="container2" style="display:none;"></div>
        <div class="input-item" style="display:none;">
          <div class="input-item-prepend"><span class="input-item-text">经纬度</span></div>
          <input id='lnglat2' type="text" >
        </div>



          <el-col :span="12">
            <el-form-item label="备注:"  prop="remarks">
              <el-input v-model="subInCar.remarks" @focus="stopdow()"></el-input>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="incardow('returnForm')">取 消</el-button>
        <el-button type="primary" @click="doInCar('returnForm')">确 定</el-button>
      </span>
    </el-dialog>





    <!--出车信息修改-->
    <el-drawer
      title="出车信息修改"
      :visible.sync="outCarFormVisible"
      direction="ltr"
      size="50%">
      <hr/>
      <el-form :model="outCarData" label-width="120px"  :rules="updaterules" ref="updateForm">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="车牌号码:" prop="carId">
              <el-select v-model="outCarData.carId" placeholder="请选择" disabled>
                <el-option v-for="item in carNum" :key="item.id"
                           :label="item.carNum" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="部门:" prop="deptId">
              <el-select v-model="outCarData.deptId" placeholder="请选择" @focus="updateressdow()">
                <el-option v-for="item in dept" :key="item.id"
                           :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="出车时间:" prop="outTime">
              <el-date-picker v-model="outCarData.outTime" type="datetime"
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS" @focus="updateressdow()">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="预计回车时间:" prop="preInTime">
              <el-date-picker v-model="outCarData.preInTime" type="datetime"
                              placeholder="选择日期时间" align="right" value-format="yyyy-MM-dd HH:mm:SS" @focus="updateressdow()">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="随行人员:" prop="followPerson">
              <el-input v-model="outCarData.followPerson" @focus="updateressdow()"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="驾驶员:" prop="driverId">
              <el-select v-model="outCarData.driverId" placeholder="请选择" disabled>
                <el-option v-for="item in driverName" :key="item.id"
                           :label="item.employeeName" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="目的地:" prop="destination">
              <el-input v-model="outCarData.destination"  @focus="updateressshow()"  id='address1'></el-input>
              
            </el-form-item>
          </el-col>
          <el-col :span="5" style="display:none;" id="updatebutton">
           <el-button plain @click="updateluxian()">规划驾车路线</el-button>
          </el-col>

          <el-col :span="5" style="display:none;"  id="updatemapbutton">
           <el-button plain @click="updateressdow()">关闭地图</el-button>
          </el-col>

        <div id="container1" style="display:none;"></div>

        <div class="input-item" style="display:none;">
        <div class="input-item-prepend"><span class="input-item-text">经纬度</span></div>
        <input id='lnglat1' type="text" >
        </div>


          <el-col :span="12">
            <el-form-item label="开始里程(公里):" prop="mileage">
              <el-input v-model="outCarData.mileage" @focus="updateressdow()"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="24">
            <el-form-item label="出车原因:" prop="outCarReason">
              <el-input v-model="outCarData.outCarReason" type="textarea" @focus="updateressdow()"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="demo-drawer">
        <el-button @click="updateoutcardow('updateForm')">取 消</el-button>
        <el-button type="primary" @click="doUpdateOutCar('updateForm')">确 定</el-button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
export default {
  name: "departureRecord",
  data() {
    return {
      //添加出车记录表单验证
      addrules:{
        carId: [
            { required: true, message: '请选择车牌', trigger: 'change' }
          ],
        deptId: [
            { required: true, message: '请选择部门', trigger: 'change' }
          ],
        outTime:[
            { required: true, message: '请输入出车时间', trigger: 'blur' },          
          ], 
        preInTime:[
          { required: true, message: '请输入预计回车时间', trigger: 'blur' },          
        ], 
        followPerson: [
            { required: true, message: '请输入随行人员', trigger: 'blur' },
          ],
        driverId : [
          { required: true, message: '请选择驾驶员', trigger: 'change' }
        ],
        mileage: [
            { required: true, message: '请输入开始里程', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请输入正确的格式',
                  trigger: 'blur'
              }
          ],
        outCarReason: [
            { required: true, message: '请输入出车原因', trigger: 'blur' },
          ],

      },
      updaterules:{
        deptId: [
            { required: true, message: '请选择部门', trigger: 'change' }
          ],
        outTime:[
            { required: true, message: '请输入出车时间', trigger: 'blur' },          
          ], 
        preInTime:[
          { required: true, message: '请输入预计回车时间', trigger: 'blur' },          
        ], 
        followPerson: [
            { required: true, message: '请输入随行人员', trigger: 'blur' },
          ],
        mileage: [
            { required: true, message: '请输入开始里程', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请输入正确的格式',
                  trigger: 'blur'
              }
          ],
        outCarReason: [
            { required: true, message: '请输入出车原因', trigger: 'blur' },
          ],
      },
      //回车
      returnrules:{
        inCarTime:[
          { required: true, message: '请输入回车时间', trigger: 'blur' },          
        ], 
        returnMileage: [
            { required: true, message: '请输入回车里程', trigger: 'blur' },
            {
                  required: true,
                  pattern: /^(0|[1-9][0-9]*)(\.\d+)?$/,
                  message: '请输入正确的格式',
                  trigger: 'blur'
              }
          ],
        remarks:[{required: true, message: '备注为空请填‘无’', trigger: 'blur' }
        ],
      },

      //地图要的东西
      map:null,//添加用的
      updatemap:null,//修改用的
      stopmap:null,//回车用的

      //分页要的东西
      p: 1,
      tableData: [],
      total: 5,
      size: 5,
      //模糊查询的文本框内容
      searchText: '',
      //驾驶员的名字的数据
      driverName: [],
      //车牌名字的数据
      carNum: [],
      //所有的部门
      dept: [],
      //字典的数据
      children: [],
      //所有员工
      Employee: [],
      //修改出车数据对象
      outCarData: {
        carId: '',
        deptId: '',
        outTime: '',
        preInTime: '',
        followPerson: '',
        driverId: '',
        destination: '',
        mileage: '',
        outCarReason: '',
      },
      //模态框
      addDialogFormVisible: false,//添加模态框
      updateDialogFormVisible: false,//修改模态框
      showOutCarFormVisible: false,//查看详细模态框
      addInCarFormVisible: false,//添加回车记录
      outCarFormVisible: false,//出车记录修改
      //出车文本框对象
      addData: {},
      //创建人Id
      employeeId: '',
      //所有可以用的车
      availableCar: [],
      //提取的数据对象回车对象
      subInCar: {},
      //显示详细的数据
      showData: {},
    }
  },
  methods: {
    //修改出车信息确定
    doUpdateOutCar(updateForm) {
      this.$refs[updateForm].validate((valid) => {
          if (valid) {
            if (this.outCarData.preInTime < this.outCarData.outTime) {
                this.$message.error("预计回车时间不能小于出车时间");
                return;
              }
              //获得焦点关闭地图div
                document.getElementById('container1').style.display="none";
                //关闭规划路线按钮
                document.getElementById('updatebutton').style.display="none";

                document.getElementById('updatemapbutton').style.display="none";
              this.outCarData.destination = document.getElementById('address1').value
              this.$axios.post("departureRecord/update", this.outCarData).then(r => {
                if (r.data.code = 200) {
                  this.outCarFormVisible = false;
                  this.$message.success("修改成功");
                  this.p = 1;
                  this.findOutCar();
                  this.$router.go(0);
                }
              })
          } else {
            // console.log('error submit!!');
            return false;
          }
        });
      
    },
    //修改出车的信息
    updateOutCar(row) {
      if (row.inTime != null) {
        this.$message.error("以回车不能修改出车信息");
        return;
      }
      
      

      this.outCarFormVisible = true;
      this.outCarData.id = row.id;
      this.outCarData.carId = row.carId;
      this.outCarData.deptId = row.deptId;
      this.outCarData.outTime = row.outTime;
      this.outCarData.preInTime = row.preInTime;
      this.outCarData.followPerson = row.followPerson;
      this.outCarData.driverId = row.driverId;
      this.outCarData.destination = row.destination;
      this.outCarData.mileage = row.mileage;
      this.outCarData.outCarReason = row.outCarReason;
      this.outCarData.employeeId = this.employeeId;

           //地图
      window.onLoad  = function(){
              var map = new AMap.Map('container1', {
                  zoom:11,//级别
                  //center: [114.31, 30.52],//武汉坐标
                  viewMode:'3D'//使用3D视图
              });
              this.mapupdate = map;

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              map.addControl(toolbar);


              //var map = new AMap.Map('container');
              map.plugin('AMap.Geolocation', function () {
                var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              map.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container1');//返回定位信息
             // AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
            })


  }

                var key = "ba9419462964ab1cb3aea92b4d1d12e6"
                var url = `https://webapi.amap.com/maps?v=1.4.15&key=${key}&callback=onLoad&plugin=AMap.ToolBar,AMap.Driving,AMap.Geolocation`;
                var jsapi = document.createElement('script');
                jsapi.charset = 'utf-8';
                jsapi.src = url;
                document.head.appendChild(jsapi);


           

      // console.log(this.outCarData)
    },

     //修改路线失去焦点触发
    updateluxian(){
         this.updatemap = new AMap.Map('container1', {
                  zoom:11,//级别
                  viewMode:'3D'//使用3D视图
              });
          //this.updates.address = document.getElementById('address1').value


            var thismap = this.updatemap;
             thismap.plugin('AMap.Geolocation', function () {
                 var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: false,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              thismap.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container1');//返回定位信息
              //AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
              })

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              thismap.addControl(toolbar);


       //点击地图回显地址
      var geocoder = new AMap.Geocoder({
        //city: "010", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
    });

      var marker = new AMap.Marker();

    function regeoCode() {

        var lnglat  = document.getElementById('lnglat1').value.split(',');
        //alert(lnglat)
        thismap.add(marker);
        marker.setPosition(lnglat);

       geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                var address = result.regeocode.formattedAddress;
                document.getElementById('address1').value = address;

            }

        });
    }

                  //驾车路线规划
                  var driving = new AMap.Driving({
                  // 驾车路线规划策略，AMap.DrivingPolicy.LEAST_TIME是最快捷模式
                  policy: AMap.DrivingPolicy.LEAST_TIME,
                  map:this.updatemap,
                })
                
                var thisaddress = document.getElementById('address1').value
                var points = [//路线规划起点终点
                   {keyword: '蜗牛学院',city:'武汉'},
                    {keyword: thisaddress,city:'武汉'}
                ]
                driving.search(points, function (status, result) {
                // 未出错时，result即是对应的路线规划方案

                })

      thismap.on('click',function(e){
        document.getElementById('lnglat1').value = e.lnglat;
        regeoCode();

 })

 document.getElementById('lnglat1').onkeydown = function(e) {
     if (e.keyCode === 13) {
         regeoCode();
         return false;
     }
     return true;
 }

    },
    //关闭修改出车记录框
    updateoutcardow(updateForm){
        this.outCarFormVisible = false
        this.$refs[updateForm].resetFields();

        //获得焦点关闭地图div
        document.getElementById('container1').style.display="none";
         //关闭规划路线按钮
        document.getElementById('updatebutton').style.display="none";

        document.getElementById('updatemapbutton').style.display="none";
    },
    

    //回车记录
    inCar() {
      if (this.subInCar.id == null) {
        this.$message.error("请选中一条数据");
        return;
      }
      if (this.subInCar.inTime != null) {
        this.$message.error("选中车辆已回车");
        return;
      }
      this.addInCarFormVisible = true;



      //地图
      window.onLoad  = function(){
              var map = new AMap.Map('container2', {
                  zoom:11,//级别
                  //center: [114.31, 30.52],//武汉坐标
                  viewMode:'3D'//使用3D视图
              });
              this.stopmap = map;

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              map.addControl(toolbar);


              //var map = new AMap.Map('container');
              map.plugin('AMap.Geolocation', function () {
                var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              map.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container2');//返回定位信息
             // AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
            })
            //this.mapObj = mapObj;

    var marker = new AMap.Marker();

    function regeoCode() {

        var lnglat  = document.getElementById('lnglat2').value.split(',');
        //alert(lnglat)
        map.add(marker);
        marker.setPosition(lnglat);

       geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                var address = result.regeocode.formattedAddress;
                document.getElementById('address2').value = address;

            }

        });
    }

 map.on('click',function(e){
     document.getElementById('lnglat2').value = e.lnglat;
     regeoCode();
    
 })

 document.getElementById('lnglat2').onkeydown = function(e) {
     if (e.keyCode === 13) {
         regeoCode();
         return false;
     }
     return true;
 }


  }

                var key = "ba9419462964ab1cb3aea92b4d1d12e6"
                var url = `https://webapi.amap.com/maps?v=1.4.15&key=${key}&callback=onLoad&plugin=AMap.ToolBar,AMap.Driving,AMap.Geolocation`;
                var jsapi = document.createElement('script');
                jsapi.charset = 'utf-8';
                jsapi.src = url;
                document.head.appendChild(jsapi);

                //点击地图回显地址
      var geocoder = new AMap.Geocoder({
        //city: "010", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
    });






    },
  //路线规划
    stopluxian(){

            this.map = new AMap.Map('container2', {
                  zoom:11,//级别
                  //center: [114.31, 30.52],//武汉坐标
                  viewMode:'3D'//使用3D视图
              });
              //this.map = new AMap.Map('container');
            var thismap = this.map;
             thismap.plugin('AMap.Geolocation', function () {
                 var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: false,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              thismap.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container2');//返回定位信息
              //AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
              })

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              thismap.addControl(toolbar);




       //点击地图回显地址
      var geocoder = new AMap.Geocoder({
        //city: "010", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
    });

      var marker = new AMap.Marker();

    function regeoCode() {

        var lnglat  = document.getElementById('lnglat2').value.split(',');
        //alert(lnglat)
        thismap.add(marker);
        marker.setPosition(lnglat);

       geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                var address = result.regeocode.formattedAddress;
                document.getElementById('address2').value = address;

            }

        });
    }

                  //驾车路线规划
                  var driving = new AMap.Driving({
                  // 驾车路线规划策略，AMap.DrivingPolicy.LEAST_TIME是最快捷模式
                  policy: AMap.DrivingPolicy.LEAST_TIME,
                  map:this.map,
                })
                
                var thisaddress = document.getElementById('address2').value
                var lastaddress = this.subInCar.destination
                // alert(lastaddress)
                var points = [//路线规划起点终点
                   {keyword: lastaddress,city:'武汉'},
                    {keyword: thisaddress,city:'武汉'}
                ]
                driving.search(points, function (status, result) {
                // 未出错时，result即是对应的路线规划方案

                })

 thismap.on('click',function(e){
     document.getElementById('lnglat2').value = e.lnglat;
     regeoCode();
    
 })

 document.getElementById('lnglat2').onkeydown = function(e) {
     if (e.keyCode === 13) {
         regeoCode();
         return false;
     }
     return true;
 }
    },






    //查看回车记录
    showCar(row) {
      this.showData = row;
      this.$axios.post("returnRecord/getByDictionaryId?id=" + row.id).then(r => {
        // console.log("回车记录")
        // console.log(r)
        this.showData.stopPosition = r.data.data.stopPosition;
        this.showData.returnMileage = r.data.data.returnMileage;
        this.showData.remarks = r.data.data.remarks;
        this.showData.zongMileage = r.data.data.zongMileage;
      })
      setTimeout(() => {
        this.showOutCarFormVisible = true;
      }, 100);
    },
    //添加回车记录确定
    doInCar(returnForm) {
      this.$refs[returnForm].validate((valid) => {
          if (valid) {
             if (this.subInCar.inCarTime < this.subInCar.outTime) {
                  this.$message.error("回车不能小于出车时间");
                  return;
                }

                //获得焦点关闭地图div
                  document.getElementById('container2').style.display="none";
                  //关闭规划路线按钮
                  document.getElementById('stopbutton').style.display="none";

                  document.getElementById('stopmapbutton').style.display="none";

                this.subInCar.stopPosition = document.getElementById('address2').value
                

                this.$axios.post("returnRecord/add", this.subInCar).then(r => {
                  if (r.data.code = 200) {
                    this.addInCarFormVisible = false;
                    this.$message.success("回车成功");
                    setTimeout(() => {
                      this.$router.go(0);
                    }, 1000);
                  }
                })
          } else {
            // console.log('error submit!!');
            return false;
          }
        });
     
    },
//分页的出车记录列表
    findOutCar() {
      this.$axios.get("departureRecord/list", {
        params: {
          p: this.p,
          searchText: this.searchText,
          size: this.size
        }
      }).then(r => {
        this.tableData = r.data.data.list;
        this.total = r.data.data.total;
        this.tableData.forEach(e1 => {
          //   //查询驾驶员
          this.driverName.forEach(e2 => {
            if (e1.driverId == e2.id) {
              e1.driverName = e2.employeeName
            }
          });
          //查询车牌号码
          this.carNum.forEach(e3 => {
            if (e1.carId == e3.id) {
              e1.carName = e3.carNum
            }
          });
          //查询部门
          this.dept.forEach(e4 => {
            if (e1.deptId == e4.id) {
              e1.deptName = e4.text
            }
          });
          //查询员工
          this.Employee.forEach(e5 => {
            if (e1.employeeId == e5.id) {
              e1.employeeName = e5.name
            }
          });
        })
      })
    },
    //查询所有的驾驶员
    getFindDriver() {
      this.$axios.get("driver/getAll").then(r => {
        this.driverName = r.data.data
      })
    },
    //查询所有的车牌号
    getFindCar() {
      this.$axios.get("car/getAll").then(r => {
        this.carNum = r.data.data
        this.carNum.forEach(e => {
          if (e.carStatus == 38) {
            this.availableCar.push(e);
          }
        })
      })
    },
    //查询所有的部门
    getFindDictionary() {
      this.$axios.get("dictionary/menu").then(r => {
        this.children = r.data.data;
        this.children.forEach(r2 => {
          if (r2.text == "部门") {
            this.dept = r2.children;
          }
        })
      })
    },
    //查询所有员工
    getFindEmployee() {
      this.$axios.get("employee/getAll").then(r => {
        this.Employee = r.data.data;
      })
    },
    //查询登入人的id
    getEmployeeId() {
      let account = localStorage.getItem("account");
      this.$axios.get("employee/getByAccount?account=" + account).then(r => {
        this.employeeId = r.data.data.id
      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.findOutCar();
    },
    handleSizeChange(val) {
      this.size = val;
      this.findOutCar();
    },
    //出车记录
    outCar() {
      this.addDialogFormVisible = true;

      //地图
      window.onLoad  = function(){
              var map = new AMap.Map('container', {
                  zoom:11,//级别
                  //center: [114.31, 30.52],//武汉坐标
                  viewMode:'3D'//使用3D视图
              });
              this.map = map;

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              map.addControl(toolbar);


              //var map = new AMap.Map('container');
              map.plugin('AMap.Geolocation', function () {
                var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              map.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container');//返回定位信息
             // AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
            })
            //this.mapObj = mapObj;

    var marker = new AMap.Marker();

    function regeoCode() {

        var lnglat  = document.getElementById('lnglat').value.split(',');
        //alert(lnglat)
        map.add(marker);
        marker.setPosition(lnglat);

       geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                var address = result.regeocode.formattedAddress;
                document.getElementById('address').value = address;

            }

        });
    }

 map.on('click',function(e){
     document.getElementById('lnglat').value = e.lnglat;
     regeoCode();
    
 })

 document.getElementById('lnglat').onkeydown = function(e) {
     if (e.keyCode === 13) {
         regeoCode();
         return false;
     }
     return true;
 }


  }

                var key = "ba9419462964ab1cb3aea92b4d1d12e6"
                var url = `https://webapi.amap.com/maps?v=1.4.15&key=${key}&callback=onLoad&plugin=AMap.ToolBar,AMap.Driving,AMap.Geolocation`;
                var jsapi = document.createElement('script');
                jsapi.charset = 'utf-8';
                jsapi.src = url;
                document.head.appendChild(jsapi);

                //点击地图回显地址
      var geocoder = new AMap.Geocoder({
        //city: "010", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
    });





    },
      //地址
    luxian(){

            this.map = new AMap.Map('container', {
                  zoom:11,//级别
                  //center: [114.31, 30.52],//武汉坐标
                  viewMode:'3D'//使用3D视图
              });
              //this.map = new AMap.Map('container');
            var thismap = this.map;
             thismap.plugin('AMap.Geolocation', function () {
                 var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: false,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              thismap.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container');//返回定位信息
              //AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
              })

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              thismap.addControl(toolbar);




       //点击地图回显地址
      var geocoder = new AMap.Geocoder({
        //city: "010", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
    });

      var marker = new AMap.Marker();

    function regeoCode() {

        var lnglat  = document.getElementById('lnglat').value.split(',');
        //alert(lnglat)
        thismap.add(marker);
        marker.setPosition(lnglat);

       geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                var address = result.regeocode.formattedAddress;
                document.getElementById('address').value = address;

            }

        });
    }

                  //驾车路线规划
                  var driving = new AMap.Driving({
                  // 驾车路线规划策略，AMap.DrivingPolicy.LEAST_TIME是最快捷模式
                  policy: AMap.DrivingPolicy.LEAST_TIME,
                  map:this.map,
                })
                //alert(this.editoppo.address)
                var thisaddress = document.getElementById('address').value
                var points = [//路线规划起点终点
                   {keyword: '蜗牛学院',city:'武汉'},
                    {keyword: thisaddress,city:'武汉'}
                ]
                driving.search(points, function (status, result) {
                // 未出错时，result即是对应的路线规划方案

                })

 thismap.on('click',function(e){
     document.getElementById('lnglat').value = e.lnglat;
     regeoCode();
    
 })

 document.getElementById('lnglat').onkeydown = function(e) {
     if (e.keyCode === 13) {
         regeoCode();
         return false;
     }
     return true;
 }




    },


    //提取表格一行元素的内容
    sub(row) {
      this.subInCar = row;
    },
    //出车记录填写确定
    doOutCar(addForm) {

      this.$refs[addForm].validate((valid) => {
          if (valid) {
            if (this.addData.preInTime < this.addData.outTime) {
                this.$message.error("预计回车时间不能小于出车时间");
                return;
              }
              this.addData.employeeId = this.employeeId;
              this.addData.destination = document.getElementById('address').value
              //获得焦点关闭地图div
                document.getElementById('container').style.display="none";
                //关闭规划路线按钮
                document.getElementById('addbutton').style.display="none";
                //关闭关闭按钮
                document.getElementById('addmapbutton').style.display="none";

              this.$axios.post("departureRecord/add", this.addData).then(r => {
                if (r.data.code = 200) {
                  this.addDialogFormVisible = false;
                  this.$message.success("添加成功");
                  this.p = 1;
                  this.findOutCar();
                }
              })
          } else {
            // console.log('error submit!!');
            return false;
          }
        });
      
    },
    //添加出车记录取消
    outcardow(addForm){
      this.$refs[addForm].resetFields();
        this.addDialogFormVisible = false
       //获得焦点关闭地图div
        document.getElementById('container').style.display="none";
         //关闭规划路线按钮
        document.getElementById('addbutton').style.display="none";
        //关闭关闭按钮
        document.getElementById('addmapbutton').style.display="none";
    },
    //添加回车记录取消
    incardow(returnForm){
        this.$refs[returnForm].resetFields();
        this.addInCarFormVisible = false
        //获得焦点关闭地图div
        document.getElementById('container2').style.display="none";
         //关闭规划路线按钮
        document.getElementById('stopbutton').style.display="none";

        document.getElementById('stopmapbutton').style.display="none";
    },

    showCarEqNull() {
      this.$message.error("未回车无法查看");
    },
    //控制地图显示
    addressshow(){
        //获得焦点显示地图div
        document.getElementById('container').style.display="block";
        //显示规划路线按钮
        document.getElementById('addbutton').style.display="block";
        document.getElementById('addmapbutton').style.display="block";
    },
    //控制地图关闭
    addressdow(){
        //获得焦点关闭地图div
        document.getElementById('container').style.display="none";
         //关闭规划路线按钮
        document.getElementById('addbutton').style.display="none";
        //关闭关闭按钮
        document.getElementById('addmapbutton').style.display="none";
    },
    updateressshow(){
         //获得焦点显示地图div
        document.getElementById('container1').style.display="block";
        //显示规划路线按钮
        document.getElementById('updatebutton').style.display="block";

        document.getElementById('updatemapbutton').style.display="block";
    },
    updateressdow(){
      //获得焦点关闭地图div
        document.getElementById('container1').style.display="none";
         //关闭规划路线按钮
        document.getElementById('updatebutton').style.display="none";

        document.getElementById('updatemapbutton').style.display="none";
    },
    stopshow(){
         //获得焦点显示地图div
        document.getElementById('container2').style.display="block";
        //显示规划路线按钮
        document.getElementById('stopbutton').style.display="block";

        document.getElementById('stopmapbutton').style.display="block";
    },
    stopdow(){
      //获得焦点关闭地图div
        document.getElementById('container2').style.display="none";
         //关闭规划路线按钮
        document.getElementById('stopbutton').style.display="none";

        document.getElementById('stopmapbutton').style.display="none";
    }

  },
  created() {
    Promise.all([
      this.getFindDriver(),
      this.getFindCar(),
      this.getFindDictionary(),
      this.getFindEmployee(),
      this.getEmployeeId()
    ]).then(res => {
      setTimeout(() => {
        this.findOutCar();//延时结束loading
      }, 100);
    })
  }
}
</script>

<style scoped>
.demo-drawer {
  text-align: center;
}

#container{
    width: 600px;
    height: 270px;
    margin-left:78px;
    margin-bottom: 20px;
}
#container1{
    width: 600px;
    height: 300px;
    margin-left:78px;
    margin-bottom: 20px;
}
#container2{
    width: 600px;
    height: 300px;
    margin-left:78px;
    margin-bottom: 20px;
}
</style>
