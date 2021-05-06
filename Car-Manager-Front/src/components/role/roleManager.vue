<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'homePage' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>角色管理</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入内容" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="findEmployee"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="add">添加用户</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <template>
      <el-table :data="tableData" border style="width: 100%" height="400"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column fixed prop="name" label="姓名" width="100%"></el-table-column>
        <el-table-column fixed prop="age" label="年龄" width="100%"></el-table-column>
        <el-table-column fixed prop="gender" label="性别" width="100%">
          <template slot-scope="scope">{{ scope.row.gender == '0' ? '男' : '女' }}</template>
        </el-table-column>
        <el-table-column fixed prop="address" label="地址" width="100%"></el-table-column>
        <el-table-column fixed prop="telephone" label="电话" width="150%"></el-table-column>
        <el-table-column fixed prop="birthday" label="生日" width="150%">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.birthday | dateConvert() }}
          </template>
        </el-table-column>
        <el-table-column fixed prop="idCard" label="身份证" width="150%"></el-table-column>
        <el-table-column fixed prop="entryDate" label="入职日期" width="150%">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.entryDate | dateConvert() }}
          </template>
        </el-table-column>
        <el-table-column fixed prop="status" label="账号状态" width="100%">
          <template slot-scope="scope">{{ scope.row.status == '0' ? '正常' : '锁定' }}</template>
        </el-table-column>

        <el-table-column fixed label="操作" width="200%">
          <template slot-scope="scope">
            <el-tooltip content="编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="bottom" effect="light">
              <el-button type="danger" icon="el-icon-delete" circle @click="del(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="分配权限" placement="bottom" effect="light">
              <el-button type="warning" icon="el-icon-share" circle></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </template>

    <br/>
    <br/>
    <br/>

    <el-dialog title="添加用户" :visible.sync="addDialogFormVisible" center width="80%">
      <el-form :model="Employee" label-width="70px">
        <el-row :gutter="20">

          <el-col :span="6">
            <el-form-item label="账号:">
              <el-input v-model="Employee.account"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="密码:">
              <el-input v-model="Employee.password"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="姓名:">
              <el-input v-model="Employee.name"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="年龄">
              <el-input v-model="Employee.age"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="手机号">
              <el-input v-model="Employee.telephone"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="性别">
              <template>
                <el-radio v-model="Employee.gender" label="0">男</el-radio>
                <el-radio v-model="Employee.gender" label="1">女</el-radio>
              </template>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="地址">
              <el-input v-model="Employee.address"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="生日">
              <el-date-picker
                v-model="Employee.birthday"
                align="right"
                type="date"
                placeholder="选择日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="身份证">
              <el-input v-model="Employee.idCard"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="入职日期">
              <el-date-picker
                v-model="Employee.entryDate"
                align="right"
                type="date"
                placeholder="选择日期"
                :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="部门">
              <el-select v-model="value" clearable placeholder="请选择">
                <el-option v-for="item in options" :key="item.id"
                           :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="职位">
              <el-select v-model="value" clearable placeholder="请选择">
                <el-option
                  v-for="item in options" :key="item.id"
                  :label="item.label" :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="状态">
              <template>
                <el-radio v-model="Employee.status" label="0">男</el-radio>
                <el-radio v-model="Employee.status" label="1">女</el-radio>
              </template>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRole()">确 定</el-button>
      </span>
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
  name: "roleManager",
  data() {
    return {
      searchText: '',
      p: 1,
      tableData: [],
      addDialogFormVisible: false,
      Employee: {},
      total: '',
      size: 5
    }
  },
  methods: {
    add() {
      this.addDialogFormVisible = true;
    },
    findEmployee() {
      this.$axios.get("employee/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.tableData = r.data.data.list
        this.total = r.data.data.total
        console.log(r)
      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.findEmployee();
    },
    handleSizeChange(val) {
      this.size = val;
      this.findEmployee();
    },
    del(row) {
      this.$confirm("此操作将删除该账户,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("employee/del?id=" + row.id).then(r => {
          this.p=1,
          this.findEmployee();
          this.$message.success("删除成功");
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    }
  }
  ,
  created() {
    this.findEmployee();
  }
}
</script>

<style scoped>

</style>
