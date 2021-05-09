<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>日常处理</el-breadcrumb-item>
      <el-breadcrumb-item>保养记录</el-breadcrumb-item>
    </el-breadcrumb>

    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="按车牌号查询" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="keepfindlist()"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showEditkeep()">新增保养记录</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <template>
      <el-table :data="keeplist" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">

        <el-table-column prop="carNum" label="车牌号" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="typename" label="保养类别" min-width :show-overflow-tooltip="true">


        </el-table-column>

        <el-table-column prop="keepDate" label="保养时间" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="nextKeepTime" label="下次保养时间" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="fee" label="保养金额" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="oppositename" label="保养单位" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="thisKeepMileage" label="本次保养里程" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="nextKeepMileage" label="下次保养里程" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="drivername" label="操作人员" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="remarks" label="备注" min-width :show-overflow-tooltip="true">

        </el-table-column>

        <el-table-column prop="" label="操作" width="120">
          <template slot-scope="scope">
            <!-- element -ui  button   plain属性，鼠标放上去变蓝色  size="mini" -->
            <el-button type="primary" icon="el-icon-edit" circle plain
                       @click="goupdate(scope.row.id)"></el-button>
            <el-button type="danger" icon="el-icon-delete" circle plain
                       @click="del(scope.row.id)"></el-button>
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
    <el-dialog title="新增加油记录" :visible.sync="keepVisible"
               ref="keepadds" center width="80%">
      <el-form :model="keepadds" label-width="150px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="车牌号" prop="carId">
              <el-select v-model="keepadds.carId" placeholder="请选择">
                <el-option label="请选择" value="0"></el-option>
                <el-option :label="cars.carNum" :value="cars.id"
                           v-for="cars in cars" :key="cars.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保养类别" prop="type">
              <el-select v-model="keepadds.type" placeholder="请选择">
                <el-option label="请选择" value="0"></el-option>
                <el-option :label="keepType.text" :value="keepType.id"
                           v-for="keepType in keepTypes" :key="keepType.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保养金额：" prop="fee">
              <el-input v-model="keepadds.fee"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="保养单位" prop="oppositeCompanyId">
              <el-select v-model="keepadds.oppositeCompanyId" placeholder="请选择">
                <el-option label="请选择" value="0"></el-option>
                <el-option :label="opposites.name" :value="opposites.id"
                           v-for="opposites in opposites" :key="opposites.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="本次保养里程：" prop="thisKeepMileage">
              <el-input v-model="keepadds.thisKeepMileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="下次保养里程：" prop="nextKeepMileage">
              <el-input v-model="keepadds.nextKeepMileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="操作人员" prop="driverId">
              <el-select v-model="keepadds.driverId" placeholder="请选择">
                <el-option label="请选择" value="0"></el-option>
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              <el-input v-model="keepadds.remarks"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="10">
            <el-form-item label="保养时间：" prop="keepDate">

              <el-date-picker
                v-model="keepadds.keepDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>

            </el-form-item>
          </el-col>

          <el-col :span="10">
            <el-form-item label="下次保养时间：" prop="nextKeepTime">

              <el-date-picker
                v-model="keepadds.nextKeepTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="keepVisible = false">取 消</el-button>
        <el-button type="primary" @click="addkeep()">确 定</el-button>
      </div>
    </el-dialog>


    <!-- 修改加油记录信息 -->
    <el-dialog title="修改加油记录" :visible.sync="keepupdate"
               ref="addCar" center width="80%">
      <el-form :model="updates" label-width="150px">
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
            <el-form-item label="保养类别" prop="type">
              <el-select v-model="updates.type" placeholder="请选择">
                <el-option label="请选择" value="0"></el-option>
                <el-option :label="keepType.text" :value="keepType.id"
                           v-for="keepType in keepTypes" :key="keepType.index">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保养金额" prop="fee">
              <el-input
                placeholder="请输入内容"
                v-model="updates.fee"
                :disabled="true">
              </el-input>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="保养单位" prop="oppositeCompanyId">
              <el-select v-model="updates.oppositeCompanyId" placeholder="请选择">
                <el-option label="请选择" value="0"></el-option>
                <el-option :label="opposites.name" :value="opposites.id"
                           v-for="opposites in opposites" :key="opposites.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="本次保养里程：" prop="thisKeepMileage">
              <el-input v-model="updates.thisKeepMileage"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="下次保养里程：" prop="nextKeepMileage">
              <el-input v-model="updates.nextKeepMileage"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="6">
            <el-form-item label="操作人员" prop="driverId">
              <el-select v-model="updates.driverId" placeholder="请选择">
                <el-option label="请选择" value="0"></el-option>
                <el-option :label="driver.employeeName" :value="driver.id"
                           v-for="driver in drivers" :key="driver.index">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              <el-input v-model="updates.remarks"></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="10">
            <el-form-item label="保养时间：" prop="keepDate">

              <el-date-picker
                v-model="updates.keepDate"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="10">
            <el-form-item label="下次保养时间：" prop="nextKeepTime">

              <el-date-picker
                v-model="updates.nextKeepTime"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期">
              </el-date-picker>

            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="keepupdate = false">取 消</el-button>
        <el-button type="primary" @click="update()">确 定</el-button>
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
      //列表
      keeplist: [
        {
          id: "",
          carId: "",//车牌
          carNum: "",//车牌号外键

          type: "",//保养类型
          typename: "",//保养类型名

          keepDate: "",//保养时间
          fee: "",//保养金额
          oppositeCompanyId: "",//保养单位
          oppositename: "",//往来单位名

          thisKeepMileage: "",//本次保养里程
          keepContext: "",//保养内容
          nextKeepTime: "",//下次保养时间
          nextKeepMileage: "",//下次保养里程
          driverId: "",//经办人
          drivername: "",//经办人员名

          remarks: ""//保养备注
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
      keepadds:
        {
          id: "",
          carId: "",//车牌
          carNum: "",//车牌号外键

          type: "",//保养类型
          typename: "",//保养类型名

          keepDate: "",//保养时间
          fee: "",//保养金额
          oppositeCompanyId: "",//保养单位
          oppositename: "",//往来单位名

          thisKeepMileage: "",//本次保养里程
          keepContext: "",//保养内容
          nextKeepTime: "",//下次保养时间
          nextKeepMileage: "",//下次保养里程
          driverId: "",//经办人
          drivername: "",//经办人员名

          remarks: ""//保养备注

        },

      cars: [],//查询的车牌号下拉框
      opposites: [],//查询的往来单位下拉框
      drivers: [],//查询的操作人员下拉框
      keepTypes: [],//保养类别下拉框

      //修改
      updates: {
        id: "",
        carId: "",//车牌
        carNum: "",//车牌号外键

        type: "",//保养类型
        typename: "",//保养类型名

        keepDate: "",//保养时间
        fee: "",//保养金额
        oppositeCompanyId: "",//保养单位
        oppositename: "",//往来单位名

        thisKeepMileage: "",//本次保养里程
        keepContext: "",//保养内容
        nextKeepTime: "",//下次保养时间
        nextKeepMileage: "",//下次保养里程
        driverId: "",//经办人
        drivername: "",//经办人员名

        remarks: ""//保养备注
      },
      //控制添加对话框是否显示
      keepVisible: false,
      //修改对话框
      keepupdate: false,
    }

  },
  methods: {
    //列表
    keepfindlist() {
      this.$axios.get("keeprecord/list", {
        params: {
          p: this.p,
          searchText: this.searchText,
          size: this.size
        }
      }).then(r => {

        this.keeplist = r.data.data.list
        this.total = r.data.data.total
        console.log("列表的数据")
        console.log(r)
      })

    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.keepfindlist();
    },
    handleSizeChange(val) {
      this.size = val;
      this.keepfindlist();
    },

    //新增保养记录
    showEditkeep() {
      this.keepVisible = true;
      //查询车牌号下拉框
      this.$axios.post("oilrecord/carlist").then(r => {
        this.cars = r.data
        //console.log(r)
      })

      //查询往来单位下拉框
      this.$axios.post("opposite/getoppolist?type=32").then(r => {
        this.opposites = r.data
        //console.log("往来单位对话框")
        // console.log(r)
      })

      //查询加油人员下拉框
      this.$axios.post("feerecord/getDriverName").then(r => {
        this.drivers = r.data.data;
      })
      //保养类别
      this.$axios.post("dictionary/menu").then(r => {
        this.keepTypes = r.data.data[6].children
      })


    },
    //添加
    addkeep() {
      this.$axios.post("keeprecord/add", this.keepadds).then(r => {
        if (r.data.code == 200) {
          this.$message({type: 'success', message: "添加成功", duration: 800});
          this.oppoVisible = false;

          //循环清空editoppo集合中的值
          for (var i in this.keepadds) {
            this.keepadds[i] = "";
          }

          //重新加载页面
          this.keepfindlist();
          this.keepVisible = false;

        } else {
          this.$message({type: 'success', message: "添加失败", duration: 800});
        }
      })

    },
    //打开修改框
    goupdate(id) {
      this.keepupdate = true;
      //查询车牌号下拉框
      this.$axios.post("oilrecord/carlist").then(r => {
        this.cars = r.data
      })

      //查询往来单位下拉框
      this.$axios.post("opposite/getoppolist?type=32").then(r => {
        this.opposites = r.data
      })

      //查询加油人员下拉框
      this.$axios.post("feerecord/getDriverName").then(r => {
        this.drivers = r.data.data;
      })
      //保养类别
      this.$axios.post("dictionary/menu").then(r => {
        this.keepTypes = r.data.data[6].children
      })


      this.$axios.get("keeprecord/findbyid?id=" + id).then(r => {
        this.updates = r.data.data

      })

    },
    //修改
    update() {
      this.$axios.post("keeprecord/update", this.updates).then(r => {
        if (r.data.code == 200) {
          this.$message({type: 'success', message: "修改成功", duration: 800});
          this.keepupdate = false;
          //循环清空editoppo集合中的值
          for (var i in this.updates) {
            this.updates[i] = "";
          }

          //查询车牌号下拉框
          this.$axios.post("oilrecord/carlist").then(r => {
            this.cars = r.data
          })

          //查询往来单位下拉框
          this.$axios.post("opposite/getoppolist?type=32").then(r => {
            this.opposites = r.data
          })

          //查询加油人员下拉框
          this.$axios.post("oilrecord/driverlist").then(r => {
            this.driverIds = r.data
          })

          //重新加载页面
          this.keepfindlist();
        } else {
          this.$message({type: 'success', message: "修改失败", duration: 800});
        }
      })

      this.keepupdate = false;


    },
    //删除
    del(id) {
      this.$confirm("此操作将删除该条记录,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.post("keeprecord/delete?id=" + id).then(r => {
          console.log(r)
          if (r.data.code == 200) {
            this.$message({type: 'success', message: "删除成功", duration: 800});
            //重新加载页面
            this.keepfindlist();
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
    this.keepfindlist();
  }


}
</script>

<style>

</style>


