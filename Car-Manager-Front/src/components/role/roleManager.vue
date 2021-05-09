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
          <el-button slot="append" icon="el-icon-search" @click="findRole"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="doAddRole">添加角色</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>

    <template>
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">

        <el-table-column prop="id" label="编号" min-width></el-table-column>

        <el-table-column prop="name" label="角色名称" min-width></el-table-column>

        <el-table-column label="操作" width="170">
          <template slot-scope="scope">
            <el-tooltip content="编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="doUpdate(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="删除" placement="bottom" effect="light">
              <el-button type="danger" icon="el-icon-delete" circle @click="del(scope.row)"></el-button>
            </el-tooltip>
            <el-tooltip content="分配权限" placement="bottom" effect="light">
              <el-button type="warning" icon="el-icon-share" circle @click="doMenuRole(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
    </template>

    <el-dialog title="添加角色" :visible.sync="addRoleFormVisible" center width="30%">
      <el-form :model="Role" label-width="70px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="角色名:">
              <el-input v-model="Role.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addRoleFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRole()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改角色" :visible.sync="updateRoleFormVisible" center width="30%">
      <el-form :model="update" label-width="70px">
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item label="角色名:">
              <el-input v-model="update.name"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updateRoleFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRole()">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog title="分配权限" :visible.sync="menuRoleFormVisible" center width="20%">
      <el-tree :data="data" show-checkbox
               default-expand-all node-key="id" ref="tree"
               highlight-current :props="defaultProps">
      </el-tree>
      <span slot="footer" class="dialog-footer">
        <el-button @click="menuRoleFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="menuRole()">确 定</el-button>
      </span>
    </el-dialog>

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
  </div>
</template>

<script>
export default {
  name: "roleManager",
  data() {
    return {
      //分页要的东西
      p: 1,
      tableData: [],
      total: 5,
      size: 5,
      //模糊查询的文本框内容
      searchText: '',
      //模态框
      addRoleFormVisible: false,//添加模态框
      updateRoleFormVisible: false,//修改模态框
      menuRoleFormVisible: false,//分配权限模态框
      //添加的对象
      Role: {},
      //修改的对象
      update: {
        id: 0,
        name: '',
      },
      //树状图的数据
      data: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
    }
  },
  methods: {
    //添加角色
    doAddRole() {
      this.addRoleFormVisible = true;
    },
    //添加角色确定
    addRole() {
      this.addRoleFormVisible = false;
      this.$axios.post("role/add", this.Role).then(r => {
        if (r.data.code = 200) {
          this.$message.success("添加成功");
          this.addRoleFormVisible = false;
          this.p = 1;
          this.findRole();
        }
      })
    },
    //编辑角色
    doUpdate(row) {
      this.updateRoleFormVisible = true;
      this.update.id = row.id;
      this.update.name = row.name;
    },
    //编辑角色确定
    updateRole() {
      this.updateRoleFormVisible = false;
      this.$axios.post("role/update", this.update).then(r => {
        if (r.data.code = 200) {
          this.$message.success("修改成功");
          this.addRoleFormVisible = false;
          this.p = 1;
          this.findRole();
        }
      })
    },
    //删除角色
    del(row) {
      this.$confirm("此操作将删除该角色,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.post("role/delete?id=" + row.id).then(r => {
          console.log(r)
          if (r.data.code != 200) {
            this.$message.error(r.data.msg);
          } else {
            this.$message.success("删除成功");
            this.addRoleFormVisible = false;
            this.p = 1;
            this.findRole();
          }
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },
    //分配权限
    doMenuRole(row) {
      this.menuRoleFormVisible = true;
      this.$axios.get("menu/list").then(r => {
        this.data = r.data.data;
        console.log(r)
      })
    },
    //分配权限确定方法
    menuRole() {

    },
    //分页的角色列表
    findRole() {
      this.$axios.get("role/list",
        {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r => {
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
  },
  created() {
    this.findRole();
  }
}
</script>

<style scoped>

</style>
