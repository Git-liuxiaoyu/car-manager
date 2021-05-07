<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
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
          <template slot-scope="scope">{{ scope.row.status == '0' ? '锁定' : '正常' }}</template>
        </el-table-column>

        <el-table-column fixed label="操作" width="200%">
          <template slot-scope="scope">
            <el-tooltip content="编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="doUpdate(scope.row)"></el-button>
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
                placeholder="选择日期">
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
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="部门">
              <el-select v-model="oneId" placeholder="请选择" @change="oneMenu">
                <el-option v-for="item in children" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="职位">
              <el-select v-model="twoId" placeholder="请选择">
                <el-option v-for="item in childrentow" :label="item.text" :key="item.id" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="状态">
              <template>
                <el-radio v-model="Employee.status" label="0">锁定</el-radio>
                <el-radio v-model="Employee.status" label="1">正常</el-radio>
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

    <el-dialog title="修改用户" :visible.sync="updateDialogFormVisible" center width="80%">
      <el-form :model="updateEmployee" label-width="70px">
        <el-row :gutter="20">

          <el-col :span="6">
            <el-form-item label="账号:">
              <el-input v-model="updateEmployee.account"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="密码:">
              <el-input v-model="updateEmployee.password"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="姓名:">
              <el-input v-model="updateEmployee.name"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="年龄">
              <el-input v-model="updateEmployee.age"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="手机号">
              <el-input v-model="updateEmployee.telephone"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="性别">
              <el-radio-group v-model="updateEmployee.gender">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="地址">
              <el-input v-model="updateEmployee.address"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="生日">
              <el-date-picker
                v-model="updateEmployee.birthday"
                align="right"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="身份证">
              <el-input v-model="updateEmployee.idCard"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="入职日期">
              <el-date-picker
                v-model="updateEmployee.entryDate"
                align="right"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="部门">
              <el-select v-model="oneId" placeholder="请选择" @change="oneMenu">
                <el-option v-for="item in children" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="职位">
              <el-select v-model="twoId" placeholder="请选择">
                <el-option v-for="item in childrentow" :label="item.text" :key="item.id" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="状态">
              <el-radio-group v-model="updateEmployee.status">
                <el-radio :label="0">锁定</el-radio>
                <el-radio :label="1">正常</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRole">确 定</el-button>
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
      updateDialogFormVisible: false,
      Employee: {},
      total: 5,
      size: 5,
      oneId: '',
      twoId: '',
      children: [],//字典
      childrentow: [],
      updateEmployee: {
        id: '',
        account: '',
        password: '',
        name: '',
        age: '',
        gender: '',
        telephone: '',
        deptId: '',
        positionId: '',
        birthday: '',
        idCard: '',
        entryDate: '',
        address: '',
        status: '',
      },//被修改的对象
    }
  },
  methods: {
    updateRole() {
      this.updateEmployee.deptId = this.oneId;
      this.updateEmployee.positionId = this.twoId;
      this.$axios.post("employee/update", this.updateEmployee).then(r => {
        if (r.data.code = 200) {
          this.$message.success("修改成功");
          this.updateDialogFormVisible = false;
          this.p = 1;
          this.findEmployee();
        }
      })
    },
    doUpdate(row) {
      this.updateDialogFormVisible = true;
      this.updateEmployee.id = row.id;
      this.updateEmployee.account = row.account;
      this.updateEmployee.password = row.password;
      this.updateEmployee.name = row.name;
      this.updateEmployee.age = row.age;
      this.updateEmployee.gender = row.gender;
      this.updateEmployee.telephone = row.telephone;
      this.oneId = row.deptId;
      this.twoId = row.positionId;
      this.updateEmployee.birthday = row.birthday;
      this.updateEmployee.idCard = row.idCard;
      this.updateEmployee.entryDate = row.entryDate;
      this.updateEmployee.address = row.address;
      this.updateEmployee.status = row.status;
      this.oneMenu(row.deptId)
    },
    addRole() {
      this.Employee.deptId = this.oneId;
      this.Employee.positionId = this.twoId;
      console.log(this.Employee)
      this.$axios.post("employee/add", this.Employee).then(r => {
        if (r.data.code = 200) {
          this.$message.success("添加成功");
          this.addDialogFormVisible = false;
          this.p = 1;
          this.findEmployee();
        }
      })
    },
    oneMenu(id) {
      for (let i = 0; i < this.children.length; i++)
        if (this.children[i].id == id) {
          this.childrentow = this.children[i].children;
        }
      this.twoId = this.childrentow[0].id
    },
    add() {
      this.addDialogFormVisible = true;
      this.oneId = '';
      this.twoId = '';
    },
    getMenu() {
      this.$axios.get("dictionary/menu").then(r => {
        this.children = r.data.data;
        for (let i = 0; i < this.children.length; i++) {
          if (this.children[i].id == 1) {
            this.children = this.children[i].children;
          }
        }
      })
    },

    findEmployee() {
      this.$axios.get("employee/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
        this.tableData = r.data.data.list
        this.total = r.data.data.total
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
          this.p = 1,
            this.findEmployee();
          this.$message.success("删除成功");
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    }
  },
  created() {
    this.findEmployee();
    this.getMenu();
  }
}
</script>

<style scoped>

</style>
