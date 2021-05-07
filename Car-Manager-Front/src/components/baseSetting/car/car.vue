<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'homePage' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础设置</el-breadcrumb-item>
      <el-breadcrumb-item>车辆档案</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入车牌号码" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="loadCars"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showAddCarDialog()">添加车辆</el-button>
      </el-col>
    </el-row>
    <br/>

    <template>
      <el-table :data="carData" style="width: 100%" height="470">
        <el-table-column fixed prop="id" label="id" width="100">

        </el-table-column>
         <el-table-column fixed prop="carNum" label="车牌号码" width="100">

        </el-table-column>
        <el-table-column fixed prop="brandId" label="车牌品牌" width="100">

        </el-table-column>
        <el-table-column fixed prop="type" label="车辆型号" width="100">

        </el-table-column>
        <el-table-column fixed prop="color" label="车辆颜色" width="100">

        </el-table-column>
         <el-table-column fixed prop="seatNum" label="座位数" width="100">

        </el-table-column>
        <el-table-column fixed prop="deptId" label="部门" width="100">

        </el-table-column>    
        <el-table-column fixed prop="carStatus" label="车辆状态" width="100">

        </el-table-column>  
        <el-table-column fixed prop="status" label="是否启用" width="100">

        </el-table-column>  
        <el-table-column  label="操作" >
            <!-- element ui  table表格的自定义列 -->
            <template slot-scope="scope">
                <!-- element -ui  button   plain属性，鼠标放上去变蓝色  size="mini" -->
                <el-button type="primary" icon="el-icon-edit" circle plain
                    @click="showEditCarDialog(scope.row)"
                ></el-button>
                <el-button type="primary" icon="el-icon-share" circle plain
                    @click="ManageCarImgDialog(scope.row)"
                ></el-button>   
                <el-button type="primary" icon="el-icon-view" circle plain
                    @click="showCarView(scope.row)"
                ></el-button>            
                <el-button type="danger" icon="el-icon-delete" circle plain
                    @click="delCar(scope.row.id)"
                ></el-button>
            </template>
        </el-table-column>                   
      </el-table>
    </template>
    <br/>

    <el-row>
      <el-col :span="10" :push="8">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="changeCurrentPage"
          :current-page="p"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-col>
    </el-row>
     <!-- 添加车辆 对话框 
    :visible 是否显示对话框，如果 dialogFormVisible：true 就显示  否则就不显示
    rules 就是表单每项的数据校验  ref  就是将来可以通过 this.$ref.addCar
    el-form-item :就是el-form表单里面的每项-->
    <el-dialog title="添加车辆" :visible.sync="addDialogFormVisible" 
        ref = "addCar" center width="80%">
        <el-form :model="insertCar" label-width="150px">
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="车牌号码：" prop="carNum">
                        <el-input v-model="insertCar.carNum"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="品牌：" prop="brandId">
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="insertCar.brandId">
                            </el-option>
                       </el-select>
                     </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="型号：" prop="type">  
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="insertCar.type">
                            </el-option>
                       </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
            <el-form-item label="颜色：" prop="color">
                <el-select v-model="value" placeholder="请选择">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="insertCar.color">
                        </el-option>
                </el-select>
            </el-form-item>
                </el-col>
                <el-col :span="6">
                 <el-form-item label="载重（吨）：" prop="carrying">
                <el-input v-model="insertCar.carrying"></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="6">
                                <el-form-item label="座位数：" prop="seatNum">
                <el-input v-model="insertCar.seatNum"></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="6">
            <el-form-item label="油耗（百公里）：" prop="oilConsume">
                <el-input v-model="insertCar.oilConsume"></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="6">
            <el-form-item label="初始里程（公里）：" prop="initialMileage">
                <el-input v-model="insertCar.initialMileage"></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="6">
            <el-form-item label="保养里程（公里）：" prop="curingMileage">
                <el-input v-model="insertCar.curingMileage"></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="6">
            <el-form-item label="保养周期（公里）：" prop="curingCycle">
                <el-input v-model="insertCar.curingCycle"></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="6">

            <el-form-item label="发动机号码：" prop="engineNum">
                <el-input v-model="insertCar.engineNum"></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="6">
            <el-form-item label="车架号：" prop="serialNumber">
                <el-input v-model="insertCar.serialNumber"></el-input>
            </el-form-item>
                </el-col>
                <el-col :span="6">
            <el-form-item label="供应商：" prop="oppositeCompanyId">
                <el-select v-model="value" placeholder="请选择">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="insertCar.oppositeCompanyId">
                    </el-option>
                </el-select>
            </el-form-item>
            </el-col>
            <el-col :span="6">
                <el-form-item label="购买价格：" prop="price">
                    <el-input v-model="insertCar.price"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="6">
                    <el-form-item label="购买价格：" prop="price">
                        <el-input v-model="insertCar.price"></el-input>
                    </el-form-item>
            </el-col>
            <el-col :span="6">
            <el-form-item label="购买日期：" prop="boughtDate">
                <el-date-picker v-model="insertCar.boughtDate"  format="yyyy-MM-dd" value-format="yyyy-MM-dd" clearable style="width: 100%"
                :picker-options="startDatePicker" :disabled="dialogStatus=='view'" type="date"  :placeholder="dialogStatus=='view'?'':'请输入购买时间'"></el-date-picker>
            </el-form-item>
            </el-col>
            <el-col :span="6">

            <el-form-item label="部   门：" prop="deptId">
                <el-select v-model="value" placeholder="请选择">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="insertCar.deptId">
                    </el-option>
                </el-select>
            </el-form-item>
                </el-col>
            <el-col :span="6">
                <el-form-item label="车辆状态：" prop="carStatus">
                    <el-select v-model="value" placeholder="请选择">
                        <el-option
                        v-for="item in options"
                        :key="item.value"
                        :label="item.label"
                        :value="insertCar.carStatus">
                        </el-option>
                    </el-select>
                </el-form-item>
            </el-col>
             <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
                <el-input v-model="insertCar.remarks"></el-input>
            </el-form-item>
            </el-col>
            <el-col :span="6">
                <el-form-item label="状态：" prop="status">
                    <el-radio v-model="radio" label="1">启用</el-radio>
                    <el-radio v-model="radio" label="2">禁用</el-radio>
                 </el-form-item>
            </el-col>
            </el-row>
        </el-form> 
        <div slot="footer" class="dialog-footer">
            <el-button @click="addDialogFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="addCar()">确 定</el-button>
        </div>
     </el-dialog>
    <!-- 修改车辆对话框 -->
     <el-dialog title="编辑车辆" :visible.sync="dialogEditCarVisible" center width="80%">
        <el-form :model="editCar"  label-width="150px">
            <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="车牌号码：" prop="carNum">
                        <el-input v-model="editCar.carNum"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="品牌：" prop="brandId">
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="editCar.brandId">
                            </el-option>
                       </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="型号：" prop="type">  
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="editCar.type">
                            </el-option>
                       </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="颜色：" prop="color">
                       <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="editCar.color">
                            </el-option>
                       </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="载重（吨）：" prop="carrying">
                        <el-input v-model="editCar.carrying"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="座位数：" prop="seatNum">
                         <el-input v-model="editCar.seatNum"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="油耗（百公里）：" prop="oilConsume">
                        <el-input v-model="editCar.oilConsume"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="初始里程（公里）：" prop="initialMileage">
                        <el-input v-model="editCar.initialMileage"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="保养里程（公里）：" prop="curingMileage">
                        <el-input v-model="editCar.curingMileage"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="保养周期（公里）：" prop="curingCycle">
                        <el-input v-model="editCar.curingCycle"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="发动机号码：" prop="engineNum">
                        <el-input v-model="editCar.engineNum"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="车架号：" prop="serialNumber">
                        <el-input v-model="editCar.serialNumber"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="供应商：" prop="deptId">
                         <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="editCar.oppositeCompanyId">
                            </el-option>
                       </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="购买价格：" prop="price">
                        <el-input v-model="editCar.price"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="购买日期：" prop="boughtDate">
                        <el-input v-model="editCar.boughtDate"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="部   门：" prop="deptId">
                         <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="editCar.deptId">
                            </el-option>
                       </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="车辆状态：" prop="carStatus">
                        <el-select v-model="value" placeholder="请选择">
                            <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="editCar.carStatus">
                            </el-option>
                       </el-select>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="备注：" prop="remarks">
                        <el-input v-model="editCar.remarks"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="状态：" prop="status">
                        <el-radio v-model="radio" label="1">启用</el-radio>
                        <el-radio v-model="radio" label="2">禁用</el-radio>
                    </el-form-item>
                </el-col>       
            </el-row>
        </el-form> 
        <div slot="footer" class="dialog-footer">
        <el-button @click="dialogEditCarVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateCar">确 定</el-button>
        </div>
    </el-dialog>
    <!--图片对话框-->
    <el-dialog title="车辆图片管理" :visible.sync="manageCarImgVisible" center width="80%">
        <el-form :model="manageCar" label-width="150px">
            <el-upload
            class="upload-demo"
            ref="upload"
            action="https://jsonplaceholder.typicode.com/posts/"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            :file-list="fileList"
            :auto-upload="false">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
            <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
            <el-form-item label="描述：" prop="status">
                <el-input v-model="editCar.remarks"></el-input>
            </el-form-item>
        </el-form> 
        <div slot="footer" class="dialog-footer">
        <el-button @click="manageCarImgVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateCar">确 定</el-button>
        </div>
    </el-dialog>
    <!--车辆详情对话框-->
    <el-dialog title="车辆详情" :visible.sync="showCarViewVisible" center width="80%">
        <el-form :model="viewCar" label-width="150px">
             <el-row :gutter="20">
                <el-col :span="6">
                    <el-form-item label="车牌号码：" prop="carNum">
                        {{viewCar.carNum}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="品牌：" prop="brandId">
                        {{viewCar.brandId}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="型号：" prop="type">  
                       {{viewCar.type}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="颜色：" prop="color">
                        {{viewCar.color}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="载重（吨）：" prop="carrying">
                        {{viewCar.carrying}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="座位数：" prop="seatNum">
                         {{viewCar.seatNum}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="油耗（百公里）：" prop="oilConsume">
                        {{viewCar.oilConsume}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="初始里程（公里）：" prop="initialMileage">
                        {{viewCar.initialMileage}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="保养里程（公里）：" prop="curingMileage">
                        {{viewCar.curingMileage}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="保养周期（公里）：" prop="curingCycle">
                        {{viewCar.curingCycle}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="发动机号码：" prop="engineNum">
                        {{viewCar.engineNum}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="车架号：" prop="serialNumber">
                        {{viewCar.serialNumber}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="购买价格：" prop="price">
                        {{viewCar.price}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="购买日期：" prop="boughtDate">
                        {{viewCar.boughtDate}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="部   门：" prop="deptId">
                        {{viewCar.deptId}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="车辆状态：" prop="carStatus">
                        {{viewCar.carStatus}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="备注：" prop="remarks">
                        {{viewCar.remarks}}
                    </el-form-item>
                </el-col>
                <el-col :span="6">
                    <el-form-item label="状态：" prop="status">
                        {{viewCar.status}}
					</el-form-item>
                </el-col>       
            </el-row>
        </el-form> 
    </el-dialog>
  </div>
</template>
<script >
export default {
  data() {
    return {
      totalCount: 0,
      p: 1,
      searchText: "",
      size:5,
      carData: [
        {
          id: "",
          carNum: "",
          brandId: "",
          type: "",
          color: "",
          seatNum: "",
          deptId: "",
          carStatus: "",
          status: "",
        }
      ],
      car: {},
      //控制添加对话框是否显示
      addDialogFormVisible: false,
      //修改车辆对话框 判断
      dialogEditCarVisible: false,
      //车辆图片管理对话框 判断
      manageCarImgVisible: false,
      //车辆详情对话框 判断
      showCarViewVisible: false,
      insertCar: {},
      editCar: {
        id: "",
        carNum: "",
        brandId: "",
        type: "",
        color: "",
        carrying: "",
        seatNum: "",
        oilConsume: "",
        initialMileage: "",
        curingMileage: "",
        curingCycle: "",
        engineNum: "",
        serialNumber: "",
        oppositeCompanyId: "",
        price: "",
        boughtDate: "",
        deptId: "",
        carStatus: "",
        remarks: "",
        deptId: "",
        status: "",
        images: ""
      },
      manageCar: {
        id: "",
        images: ""
      },
      images:[],//车辆图片集合
      viewCar: {
        id: "",
        carNum: "",
        brandId: "",
        type: "",
        color: "",
        carrying: "",
        seatNum: "",
        oilConsume: "",
        initialMileage: "",
        curingMileage: "",
        curingCycle: "",
        engineNum: "",
        serialNumber: "",
        oppositeCompanyId: "",
        price: "",
        boughtDate: "",
        deptId: "",
        carStatus: "",
        remarks: "",
        deptId: "",
        status: "",
        images: ""
      }
    };
  },
  methods: {
    //携带token 自定义请求头
    loadCars() {
      this.$axios.get("car/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.carData = r.data.data.list
        this.total = r.data.data.total
      })
    },
    //修改当前页码 触发的事件方法
    changeCurrentPage(curpage) {
      this.p = curpage;
      this.loadCars();
    },
    handleSizeChange(val) {
      this.size = val;
      this.loadCars();
    },

    //根据车牌号码去查询  而且要分页
    startQuery() {
      this.p = 1;
      this.loadCars();
    },

    //打开车辆对话框 添加车辆的
    showAddCarDialog() {
      this.addDialogFormVisible = true;
    },
    //添加车辆 确定方法
    addCar() {
      let car = this.insertCar;
      this.$axios.post("car/add", car).then(res => {
        if (res.data.code == 200) {
          this.$message({
            type: "success",
            message: "添加成功",
            duration: 800
          });
          this.addDialogFormVisible = false;
          //重新加载页面
          this.loadCars();
        }
      });
    },
    //显示修改对话框
    showEditCarDialog(row) {
      this.dialogEditCarVisible = true;
      this.editCar.id = row.id;
      this.editCar.carNum = row.carNum;
      this.editCar.brandId = row.brandId;
      this.editCar.type = row.type;
      this.editCar.color = row.color;
      this.editCar.carrying = row.carrying;
      this.editCar.seatNum = row.seatNum;
      this.editCar.oilConsume = row.oilConsume;
      this.editCar.initialMileage = row.initialMileage;
      this.editCar.curingMileage = row.curingMileage;
      this.editCar.curingCycle = row.curingCycle;
      this.editCar.engineNum = row.engineNum;
      this.editCar.serialNumber = row.serialNumber;
      this.editCar.oppositeCompanyId = row.oppositeCompanyId;
      this.editCar.price = row.price;
      this.editCar.boughtDate = row.boughtDate;
      this.editCar.deptId = row.deptId;
      this.editCar.carStatus = row.carStatus;
      this.editCar.remarks = row.remarks;
      this.editCar.status = row.status;
      this.editCar.images = row.images;
    },
    //显示图片管理对话框
    ManageCarImgDialog(row) {
      this.manageCarImgVisible = true;
      this.manageCar.id = row.id;
      this.manageCar.images = row.images;
      this.images = this.manageCar.images.split(",");

    },
    //显示详情对话框
    showCarView(row) {
      this.showCarViewVisible = true;
      this.viewCar.id = row.id;
      this.viewCar.carNum = row.carNum;
      this.viewCar.brandId = row.brandId;
      this.viewCar.type = row.type;
      this.viewCar.color = row.color;
      this.viewCar.carrying = row.carrying;
      this.viewCar.seatNum = row.seatNum;
      this.viewCar.oilConsume = row.oilConsume;
      this.viewCar.initialMileage = row.initialMileage;
      this.viewCar.curingMileage = row.curingMileage;
      this.viewCar.curingCycle = row.curingCycle;
      this.viewCar.engineNum = row.engineNum;
      this.viewCar.serialNumber = row.serialNumber;
      this.viewCar.oppositeCompanyId = row.oppositeCompanyId;
      this.viewCar.price = row.price;
      this.viewCar.boughtDate = row.boughtDate;
      this.viewCar.deptId = row.deptId;
      this.viewCar.carStatus = row.carStatus;
      this.viewCar.remarks = row.remarks;
      this.viewCar.status = row.status;
      this.viewCar.images = row.images;
    },
    //提交修改
    updateCar() {
      this.$axios.post("car/update", this.editCar).then(res => {
        if (res.data.code == 200) {
          this.$message({
            type: "success",
            message: "修改成功",
            duration: 800
          });
          this.dialogEditCarVisible = false;
          //重新加载页面
          this.loadCars();
        }
      });
    },
    //删除车辆
    delCar(id) {
     this.$confirm("此操作将删除该车辆,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.post("car/delete?id=" + id).then(r => {
          this.p=1,
          //重新加载页面
          this.loadCars();
          this.$message.success("删除成功");
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },
  },
  created() {
    this.loadCars();
  }
};
</script>
<style >
</style>