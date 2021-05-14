<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
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
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column  prop="name" label="姓名" width="100"></el-table-column>
        <el-table-column  prop="age" label="年龄" width="80"></el-table-column>

        <el-table-column  prop="gender" label="性别" width="80">
          <template slot-scope="scope">{{ scope.row.gender == '0' ? '男' : '女' }}</template>
        </el-table-column>

        <el-table-column  prop="address" label="地址" min-width></el-table-column>
        <el-table-column  prop="telephone" label="电话" width="120"></el-table-column>
<!--        :show-overflow-tooltip="true" -->
        <el-table-column  prop="birthday" label="生日" width="120">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.birthday | dateConvert() }}
          </template>
        </el-table-column>
        <el-table-column  prop="idCard" label="身份证" min-width></el-table-column>
        <el-table-column  prop="entryDate" label="入职日期" width="120">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            {{ scope.row.entryDate | dateConvert() }}
          </template>
        </el-table-column>
        <el-table-column  prop="status" label="账号状态" width="80">
          <template slot-scope="scope">{{ scope.row.status == '0' ? '锁定' : '正常' }}</template>
        </el-table-column>

        <el-table-column  label="操作" width="170">
          <template slot-scope="scope">
            <el-tooltip content="编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="doUpdate(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="bottom" effect="light">
              <el-button type="danger" icon="el-icon-delete" circle @click="del(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="分配角色" placement="bottom" effect="light">
              <el-button type="warning" icon="el-icon-share" circle @click="addRole(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </template>

    <br/>
    <br/>
    <br/>

    <el-dialog title="添加用户" :visible.sync="addDialogFormVisible" center width="80%">
      <el-form :model="Employee" label-width="80px" :rules="rules" ref="addForm">
        <el-row :gutter="20">

          <el-col :span="6">
            <el-form-item label="账号:" prop="account">
              <el-input v-model="Employee.account"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="密码:" prop="password">
              <el-input v-model="Employee.password"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="姓名:" prop="name" >
              <el-input v-model="Employee.name"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="Employee.age"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="手机号" prop="telephone">
              <el-input v-model="Employee.telephone"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="性别" prop="gender">
              <template>
                <el-radio v-model="Employee.gender" label="0">男</el-radio>
                <el-radio v-model="Employee.gender" label="1">女</el-radio>
              </template>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="地址" prop="address">
              <el-input v-model="Employee.address"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="生日" prop="birthday">
              <el-date-picker
                v-model="Employee.birthday"
                align="right"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="身份证" prop="idCard">
              <el-input v-model="Employee.idCard"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="入职日期" prop="entryDate">
              <el-date-picker
                v-model="Employee.entryDate"
                align="right"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="部门" prop="oneId">
              <el-select v-model="oneId" placeholder="请选择" @change="oneMenu">
                <el-option v-for="item in children" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="职位" prop="twoId">
              <el-select v-model="twoId" placeholder="请选择">
                <el-option v-for="item in childrenTow" :label="item.text" :key="item.id" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="状态" prop="status">
              <template>
                <el-radio v-model="Employee.status" label="0">锁定</el-radio>
                <el-radio v-model="Employee.status" label="1">正常</el-radio>
              </template>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addCancel('addForm')">取 消</el-button>
        <el-button type="primary" @click="addUser('addForm')">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改用户" :visible.sync="updateDialogFormVisible" center width="80%">
      <el-form :model="updateEmployee" label-width="80px" :rules="rules" ref="editForm">
        <el-row :gutter="20">

          <el-col :span="6">
            <el-form-item label="账号:" prop="account">
              <el-input v-model="updateEmployee.account"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="密码:">
              <el-input v-model="updateEmployee.password" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="姓名:" prop="name">
              <el-input v-model="updateEmployee.name"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="年龄" prop="age">
              <el-input v-model="updateEmployee.age"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="手机号" prop="telephone">
              <el-input v-model="updateEmployee.telephone"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="updateEmployee.gender">
                <el-radio :label="0">男</el-radio>
                <el-radio :label="1">女</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12">
            <el-form-item label="地址" prop="address">
              <el-input v-model="updateEmployee.address"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="生日" prop="birthday">
              <el-date-picker
                v-model="updateEmployee.birthday"
                align="right"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="身份证" prop="idCard">
              <el-input v-model="updateEmployee.idCard"></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="入职日期" prop="entryDate">
              <el-date-picker
                v-model="updateEmployee.entryDate"
                align="right"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="部门" prop="oneId">
              <el-select v-model="oneId" placeholder="请选择" @change="oneMenu">
                <el-option v-for="item in children" :key="item.id" :label="item.text" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="职位" prop="twoId">
              <el-select v-model="twoId" placeholder="请选择">
                <el-option v-for="item in childrenTow" :label="item.text" :key="item.id" :value="item.id">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="状态"  prop="status">
              <el-radio-group v-model="updateEmployee.status">
                <el-radio :label="0">锁定</el-radio>
                <el-radio :label="1">正常</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="editCancel('editForm')">取 消</el-button>
        <el-button type="primary" @click="updateUser('editForm')">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="分配角色" :visible.sync="addRoleFormVisible" center width="30%">
      <template>
        <el-checkbox-group v-model="roleName">
          <el-row :gutter="20">
            <el-col :span="8" v-for="item in checkList" :key="item.id">
              <el-checkbox :label="item.name"></el-checkbox>
            </el-col>
          </el-row>
        </el-checkbox-group>
      </template>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addRoleFormVisible = false">取 消</el-button>
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
  name: "userManager",
  data() {
    return {
      id: '',
      checkList: [],
      roleId: [],
      roleName: [],
      searchText: '',
      p: 1,
      tableData: [],
      addDialogFormVisible: false,
      updateDialogFormVisible: false,
      addRoleFormVisible: false,
      Employee: {},
      total: 5,
      size: 5,
      oneId: '',
      twoId: '',
      children: [],//字典
      childrenTow: [],
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
      //表单验证
      rules:{
        account:[
           {required: true, //是否必填       
          message: '账号不能为空', //规则
          trigger: 'blur'  //何事件触发
          }
        ],
         password:[
           {required: true, //是否必填       
          message: '密码不能为空', //规则
          trigger: 'blur'  //何事件触发
          }
        ],
        name:[
           {required: true, //是否必填       
          message: '姓名不能为空', //规则
          trigger: 'blur'  //何事件触发
          }
        ],
          age:[
           {required: true, //是否必填       
          message: '年龄不能为空', //规则
          trigger: 'blur'  //何事件触发
          },
           { pattern:/^(?:[1-9][0-9]?|1[01][0-9]|120)$/, message: '年龄1-120之间' },
        ],

        telephone:[
           {required: true, //是否必填       
          message: '年龄不能为空', //规则
          trigger: 'blur'  //何事件触发
          },
           {pattern: '^1[3|4|5|7|8][0-9]{9}$', message: '请输入正确的11位手机号', trigger: 'blur' }
        ],

          gender:[
           {required: true, //是否必填       
            message: '请选择性别', //规则
            trigger: 'change'  //何事件触发
            }
        ],
        address:[
           {required: true, //是否必填       
            message: '地址不能为空', //规则
            trigger: 'blur'  //何事件触发
            }
        ],
                idCard:[
           {required: true, //是否必填       
            message: '身份证不能为空', //规则
            trigger: 'blur'  //何事件触发
            },
            // {pattern: /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$|^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}([0-9]|X)$/,
            //  message: '请输入正确的身份证号', trigger: 'blur' }
        ],
        birthday:[
           {required: true, //是否必填       
            message: '生日不能为空', //规则
            trigger: 'chage'  //何事件触发
            },
        ],
        entryDate:[
           {required: true, //是否必填       
            message: '入职时间不能为空', //规则
            trigger: 'chage'  //何事件触发
            },
        ],           
        // oneId:[
        //    {required: true, //是否必填       
        //     message: '部门不能为空', //规则
        //     trigger: 'chage'  //何事件触发
        //     },
        // ],
        // twoId:[
        //    {required: true, //是否必填       
        //     message: '职位不能为空', //规则
        //     trigger: 'chage'  //何事件触发
        //     },
        // ],
       status:[
           {required: true, //是否必填       
            message: '请选择一种状态', //规则
            trigger: 'chage'  //何事件触发
            },
        ],


      }
    }
  },
  methods: {
    updateUser(formName) {
       this.$refs[formName].validate((valid) => {
        if (valid) {
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
           } else {
          return false;
        }
      });
    },
    updateRole() {
      this.roleId=[];
      for (let i = 0; i < this.checkList.length; i++) {
        for (let j = 0; j < this.roleName.length; j++) {
          if (this.checkList[i].name == this.roleName[j]) {
            this.roleId.push(this.checkList[i].id)
          }
        }
      }
      this.$axios.get("role/addRole?roleId=" + this.roleId + "&id=" + this.id).then(r => {
        if (r.data.code = 200) {
          this.$message.success("修改成功");
          this.addRoleFormVisible = false;
          this.p = 1;
          this.findEmployee();
        }
      })
    },

    editCancel(formName){
        this.updateDialogFormVisible=false;
         this.$refs[formName].resetFields();
    },
    addRole(row) {
      this.addRoleFormVisible = true;
      this.roleName = [];
      this.id = row.id;
      this.$axios.get("role/lists").then(r => {
        this.checkList = r.data;
      })
      this.$axios.post("role/doRole?id=" + row.id).then(r => {
        for (let i = 0; i < r.data.data.length; i++) {
          this.roleName.push(r.data.data[i].name)
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
    addUser(formName) {
       this.$refs[formName].validate((valid) => {
        if (valid) {
            this.Employee.deptId = this.oneId;
            this.Employee.positionId = this.twoId;
            console.log(this.Employee)
            this.$axios.post("employee/add", this.Employee).then(r => {
              if (r.data.code = 200) {
                this.$message.success("添加成功");
                this.addDialogFormVisible = false;
                this.p = 1;
                this.findEmployee();
                this.$refs[formName].resetFields();
              }
            })
        } else {
          return false;
        }
      });
    },
    addCancel(formName){
        this.addDialogFormVisible=false;
         this.$refs[formName].resetFields();
    },
    oneMenu(id) {
      for (let i = 0; i < this.children.length; i++)
        if (this.children[i].id == id) {
          this.childrenTow = this.children[i].children;
        }
      this.twoId = this.childrenTow[0].id
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
