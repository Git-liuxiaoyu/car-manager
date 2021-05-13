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
        <el-input placeholder="请输入车牌号码,支持模糊查询" v-model="searchText" class="input-with-select">
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
        <el-table-column  prop="typeName" label="车辆型号" min-width></el-table-column>
        <el-table-column  prop="colorName" label="车辆颜色" min-width></el-table-column>
        <el-table-column  prop="seatNum" label="座位数" min-width></el-table-column>
        <el-table-column  prop="deptName" label="部门" min-width></el-table-column>
        <el-table-column  prop="carStatusName" label="车辆状态" min-width> </el-table-column>
        <el-table-column  prop="status" label="是否启用" width="80">
          <template slot-scope="scope">{{ scope.row.status == '0' ? '不启用' : '启用' }}</template>
        </el-table-column>
        <el-table-column  label="操作" width="230">
          <template slot-scope="scope">
            <el-tooltip content="修改" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="showUpdateDialog(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="图片管理" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-share" circle @click="showImgManageDialog(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="详情" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-view" circle @click="showViewDialog(scope.row)"></el-button>
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
    <el-dialog title="新增车辆" :visible.sync="addDialogFormVisible" center width="80%" >
      <el-form :model="addData" label-width="150px" :rules='checkRules' ref="addForm">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="车牌号码：" prop="carNum">
              <el-input v-model="addData.carNum" ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="品牌:">
                <el-select v-model="oneId" placeholder="请选择"  @change="oneMenu">
                <el-option v-for="item in brandType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="型号:">
                <el-select v-model="twoId" placeholder="请选择">
                <el-option v-for="item in typeType" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="颜色:">
                <el-select v-model="addData.color" placeholder="请选择">
                    <el-option v-for="color in colorType" :key="color.id"
                                :label="color.text" :value="color.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="载重（吨）：" prop="carrying">
              <el-input v-model="addData.carrying" ></el-input>
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
            <el-form-item label="保养周期（月）：" prop="curingCycle">
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
                <el-select v-model="addData.oppositeCompanyId" placeholder="请选择">
                    <el-option v-for="opposite in oppositeList" :key="opposite.id"
                                :label="opposite.name" :value="opposite.id">
                    </el-option>
                </el-select>
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
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="部   门:">
                <el-select v-model="addData.deptId" placeholder="请选择">
                    <el-option v-for="dept in deptType" :key="dept.id"
                                :label="dept.text" :value="dept.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车辆状态:">
                <el-select v-model="addData.carStatus" placeholder="请选择">
                    <el-option v-for="item in carStatusType" :key="item.id"
                                :label="item.text" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              <el-input v-model="addData.remarks"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="状态:" >
              <template>
                <el-radio v-model="addData.status" label="0">不启用</el-radio>
                <el-radio v-model="addData.status" label="1">启用</el-radio>
              </template>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeAddDialog">取 消</el-button>
        <el-button type="primary" @click="doAdd">确 定</el-button>
      </span>
    </el-dialog>
    <!--修改-->
    <el-dialog title="编辑车辆" :visible.sync="updateDialogFormVisible" center width="80%">
      <el-form :model="updateData" label-width="150px" :rules='checkRules' ref="updateForm">
        <el-row :gutter="20">

          <el-col :span="6">
            <el-form-item label="车牌号码:" prop="carId" >
              <el-input v-model="updateData.carNum" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="品牌:">
                <el-select v-model="oneId" placeholder="请选择"  @change="oneMenu">
                  <el-option v-for="item in brandType" :key="item.id" :label="item.text" :value="item.id">
                  </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="型号:">
                <el-select v-model="twoId" placeholder="请选择">
                    <el-option v-for="item in typeType" :key="item.id" :label="item.text" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="颜色:">
                <el-select v-model="updateData.color" placeholder="请选择">
                    <el-option v-for="color in colorType" :key="color.id"
                                :label="color.text" :value="color.id">
                    </el-option>
                </el-select>
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
                <el-select v-model="updateData.oppositeCompanyId" placeholder="请选择">
                    <el-option v-for="opposite in oppositeList" :key="opposite.id"
                                :label="opposite.name" :value="opposite.id">
                    </el-option>
                </el-select>
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
                placeholder="选择日期"
                value-format="yyyy-MM-dd">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="部   门:">
                <el-select v-model="updateData.deptId" placeholder="请选择">
                    <el-option v-for="dept in deptType" :key="dept.id"
                                :label="dept.text" :value="dept.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="车辆状态:">
                <el-select v-model="updateData.carStatus" placeholder="请选择">
                    <el-option v-for="item in carStatusType" :key="item.id"
                                :label="item.text" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              <el-input v-model="updateData.remarks"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态:" prop="remarks">
              <el-radio-group v-model="updateData.status">
                <el-radio :label="0">不启用</el-radio>
                <el-radio :label="1">启用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeUpdateDialog">取 消</el-button>
        <el-button type="primary" @click="doUpdate">确 定</el-button>
      </span>
    </el-dialog>
    <!--图片管理-->
    <el-dialog :title="updateData.carNum+'图片管理'" :visible.sync="manageCarImgVisible" center width="80%">
      <!-- 图片展示 -->
      <div>
          <div class="demo-image">
            <div v-for="img in imgList" :key="img.id" style="text-align:center">
              <div class="block" style="width:200px;height:200px;display: inline-block;float:left;text-align:center" >
                    <el-image
                      :preview-src-list=[img.imgName]
                      :z-index = 20000
                      style="width:100px;height:100px;"
                      class="table-td-thumb"
                      :src="img.imgName">
                    </el-image>
                    <br/>
                    <br/>
                    {{img.description}}
                    <br/>
                    <br/>
                    <el-button type="danger" icon="el-icon-delete" circle @click="delImg(img)"></el-button>
              </div>
            </div>
          </div>
      </div>
      <!--图片上传-->
      <!-- action="http://localhost:8888/carImg/upload" -->
      <el-upload
          id="sbtn"
          style="clear:both"
          class="upload-demo"
          ref="upload"
          action="http://localhost:8888/carImg/upload"
          accept="image/jpeg,image/gif,image/png,image/jpg"
          :file-list="fileList"
          :headers= {Authorization:this.token}
          :data=uploadData
          :before-upload=handleBefore
          :auto-upload="false">
          <el-button slot="trigger" size="large" type="primary">选取图片</el-button>
          <el-button style="margin-left: 10px;" size="large" type="success" @click="submitUpload">上传图片</el-button>
          <div slot="tip" class="el-upload__tip">只能上传小于500kb的jpeg/gif/png/jpg文件</div>
      </el-upload>
      <br/>
      <el-input placeholder="请输入上传图片描述" v-model="uploadData.description" width="40px" ></el-input>
    </el-dialog>
    <!--详细-->
    <el-dialog title="车辆信息详细" :visible.sync="ViewDialogFormVisible" center width="80%">
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
              {{ updateData.typeName }}
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
              
              {{ updateData.carStatusName  }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="备注：" >
              {{ updateData.remarks }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            
            <el-form-item label="状态：" >
              {{ updateData.status == '0' ? '不启用' : '启用'}}
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
      tableData: [],
      searchText: "",
      p: 1,
      size: 5,
      total: 5,
      ViewDialogFormVisible: false,
      // 增
      addData: {status:"1"},
      addDialogFormVisible: false,
      // 改
      updateData: {},
      updateDialogFormVisible: false,
      manageCarImgVisible: false,
      // 其他
      oneId: "",
      twoId: "",
      brandType: [], // 品牌集合
      typeType: [], // 信号集合
      colorType: [], // 颜色集合
      deptType: [], // 部门集合
      carStatusType: [], // 车辆状态集合
      oppositeList: [], // 往来单位集合
      imgList:[], // 图片集合
      fileList:[],
      token: "",
      uploadData: {
        carId: 0,
        imgName: "",
        description: ""
      },
      // 验证规则
      checkRules: {
      // 要以数组形式展示
        carNum: [
          { required: true, message: "车牌号不能为空", trigger: "blur" },
          { 
            pattern:/(^[\u4E00-\u9FA5]{1}[A-Z0-9]{6}$)|(^[A-Z]{2}[A-Z0-9]{2}[A-Z0-9\u4E00-\u9FA5]{1}[A-Z0-9]{4}$)|(^[\u4E00-\u9FA5]{1}[A-Z0-9]{5}[挂学警军港澳]{1}$)|(^[A-Z]{2}[0-9]{5}$)|(^(08|38){1}[A-Z0-9]{4}[A-Z0-9挂学警军港澳]{1}$)/,
            message: '常规格式：晋B12345'
          },
        ],
        carrying:[
          { required: true, message: "载重不能为空", trigger: "blur"},
          { 
            pattern: /^(([1-9]{1}\d*)|(0{1}))(\.\d{1,2})?$/,
            message: "请输入合法的数字",
            trigger: "change"
          }
        ],
        seatNum:[
          { required: true, message: "座位数不能为空", trigger: "blur"},
          { 
            type: 'number',
            min: 1,
            message: '座位数为正整数',
            trigger: 'change'
          }
        ],
        oilConsume:[
          { required: true, message: "油耗不能为空", trigger: "blur"},
          { 
            type: 'number',
            min: 0,
            message: '请输入大于0的数字',
            trigger: 'change'
          }
        ],
        initialMileage:[
          { required: true, message: "初始里程不能为空", trigger: "blur"},
          { 
            type: 'number',
            min: 0,
            message: '请输入大于0的数字',
            trigger: 'change'
          }
        ],
        curingMileage:[
          { required: true, message: "保养里程不能为空", trigger: "blur"},
          { 
            type: 'number',
            min: 0,
            message: '请输入大于0的数字',
            trigger: 'change'
          }
        ],
        curingCycle:[
          { required: true, message: "保养周期不能为空", trigger: "blur"},
          { 
            type: 'number',
            min: 0,
            message: '请输入大于0的数字',
            trigger: 'change'
          }
        ],
        engineNum:[
          { required: true, message: "发动机号码不能为空", trigger: "blur"},
        ],
        serialNumber:[
          { required: true, message: "车架号不能为空", trigger: "blur"},
        ],
        remarks:[
          { required: true, message: "备注为空请填'无'", trigger: "blur"},
        ],
        price:[
          { required: true, message: "金额不能为空", trigger: "blur"},
          { pattern: /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/, message: '请输入正确额格式,可保留两位小数' },
        ]
      }

    };
  },
  methods: {
    // 查
    loadList() {
      this.$axios
        .get("car/list", {
          params: { p: this.p, searchText: this.searchText, size: this.size }
        })
        .then(r => {
          this.tableData = r.data.data.list;
          this.tableData.forEach(e1 => {
            this.brandType.forEach(e2 => {
              if (e1.brandId === e2.id) {
                e1.brandName = e2.text;
                e2.children.forEach(child => {
                  if (e1.type === child.id) {
                    e1.typeName = child.text;
                  }
                });
              }
            }),
              this.colorType.forEach(e3 => {
                if (e1.color === e3.id) {
                  e1.colorName = e3.text;
                }
              }),
              this.deptType.forEach(e4 => {
                if (e1.deptId === e4.id) {
                  e1.deptName = e4.text;
                }
              }),
              this.carStatusType.forEach(e5 => {
                if (e1.carStatus === e5.id) {
                  e1.carStatusName = e5.text;
                }
              });
            this.oppositeList.forEach(e6 => {
              if (e1.oppositeCompanyId === e6.id) {
                e1.oppositeName = e6.name;
              }
            });
          });
          this.total = r.data.data.total;
        });
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
    showAddDialog() {
      this.addDialogFormVisible = true;
      this.oneId = "";
      this.twoId = "";
    },
    closeAddDialog(){
      this.$refs['addForm'].resetFields();
      this.addDialogFormVisible = false;
    },
    showViewDialog(row) {
      this.ViewDialogFormVisible = true;
      this.updateData = row;
    },
    doAdd() {
      this.addData.brandId = this.oneId;
      this.addData.type = this.twoId;
      this.$axios.post("car/add", this.addData).then(r => {
        if ((r.data.code === 200)) {
          this.$message.success("添加成功");
          this.$refs['addForm'].resetFields();
          this.addDialogFormVisible = false;
          this.p = 1;
          this.loadList();
        }else{
          this.$message.warning("该车牌号已被占用无法添加");
        }
      });
    },
    // 改
    showUpdateDialog(row) {
      this.updateDialogFormVisible = true;
      this.loadList();
      for(let element in this.tableData){
        if(element.id==row.id){
          this.updateData=element
        }
      }
      this.updateData = row;
      this.oneId = row.brandId;
      this.twoId = row.type;
      this.oneMenu(row.brandId);
    },
    closeUpdateDialog(){
      this.$refs['updateForm'].resetFields();
      this.updateDialogFormVisible = false;
    },

    doUpdate() {
      this.updateData.brandId = this.oneId;
      this.updateData.type = this.twoId;
      this.$axios.post("car/update", this.updateData).then(r => {
        if ((r.data.code === 200)) {
          this.$message.success("修改成功");
          this.updateDialogFormVisible = false;
          this.p = 1;
          this.loadList();
        }
      });
    },
    // 图片管理
    showImgManageDialog(row) {
      this.manageCarImgVisible = true;
      this.updateData = row;
      this.getCarImgList(this.updateData.id);
    },
    submitUpload() {
        this.uploadData.carId=this.updateData.id
        this.uploadData.imgName=this.updateData.carNum+this.uploadData.description;
        this.$refs.upload.submit();
    },
    handleBefore(file) {
        this.fileList = []
        var testmsg = file.name.substring(file.name.lastIndexOf(".") + 1);
        const extension = testmsg === "jpg"|testmsg ==="gif"|testmsg==="png"|testmsg==="jpeg";
        if (!extension ) {
          this.$message({
            message: "上传文件只能图片格式!",
            type: "warning",
          });
        }
        return extension;
    },
    delImg(img) {
      this.$confirm("此操作将删除该图片,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(res => {
          this.$axios.get("carImg/delete?id=" + img.id).then(r => {
            (this.p = 1), this.loadList();
            this.$message.success("删除成功");
          });
        })
        .catch(res => {
          this.$message.info("取消删除");
        });
    },
    // 删
    del(row) {
      this.$confirm("此操作将删除该车辆,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(res => {
          this.$axios.get("car/delete?id=" + row.id).then(r => {
            (this.p = 1), this.loadList();
            this.$message.success("删除成功");
          });
        })
        .catch(res => {
          this.$message.info("取消删除");
        });
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
            this.colorType = totalTree[i].children;
          }
          if (totalTree[i].id == 1) {
            this.deptType = totalTree[i].children;
          }
          if (totalTree[i].id == 35) {
            this.carStatusType = totalTree[i].children;
          }
        }
      });
    },
    oneMenu(id) {
      for (let i = 0; i < this.brandType.length; i++)
        if (this.brandType[i].id == id) {
          this.typeType = this.brandType[i].children;
        }
      this.twoId = this.typeType[0].id;
    },
    getOppositeList() {
      this.$axios.get("opposite/getoppolist?type=32").then(r => {
        this.oppositeList = r.data;
      });
    },
    getCarImgList(carId){
        this.$axios.get("carImg/getByCarId?carId=" +carId).then(r => {
        this.imgList = r.data.data
        return this.imgList;
      })
    },
  },
  watch: {
    // 如果 `question` 发生改变，这个函数就会运行
    imgList: function (newQuestion, oldQuestion) {
      this.getCarImgList(this.updateData.id);
    }
  },
  created() {
    Promise.all([
      this.getMenu(),
      this.getOppositeList(),
      (this.token = localStorage.getItem("token"))
    ]).then(res => {
      setTimeout(() => {
        this.loadList(); //延时结束loading
      }, 500);
    });
  },  
};
</script>

<style>
  #sbtn>div>input{
    display: none!important;
  }
</style>
