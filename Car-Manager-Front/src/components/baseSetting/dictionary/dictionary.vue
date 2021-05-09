<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础设置</el-breadcrumb-item>
      <el-breadcrumb-item>系统字典</el-breadcrumb-item>
    </el-breadcrumb>
    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="请输入内容" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="findDictionary"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="add">添加字典</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <template>
      <el-table :data="tableData" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column prop="level" label="级别" min-width></el-table-column>
        <el-table-column prop="text" label="字段" min-width></el-table-column>
        <el-table-column prop="isDisable" label="字段状态" min-width>
          <template slot-scope="scope">{{ scope.row.isDisable == '1' ? '启动' : '禁用' }}</template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template slot-scope="scope">
            <el-tooltip content="编辑" placement="bottom" effect="light">
              <el-button type="primary" icon="el-icon-edit" circle @click="doUpdate(scope.row)"></el-button>
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

    <el-dialog title="添加字典" :visible.sync="addDialogFormVisible" center width="50%">
      <el-form :model="Dictionary" label-width="100px">

        <span style="font-size: 20px;font-style: oblique">请选择字典的目录级别</span>
        <span style="display: inline-block;width: 70%"><hr/></span>
        <br/><br/><br/>

        <el-form-item label="字典级别:">
          <el-radio-group v-model="level" size="medium" @change="agreeChange">
            <el-radio-button label="一级"></el-radio-button>
            <el-radio-button label="二级"></el-radio-button>
            <el-radio-button label="三级"></el-radio-button>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="一级菜单" v-if="one">
          <el-select v-model="oneId" clearable placeholder="请选择" @change="onemenu">
            <el-option v-for="item in children" :key="item.id" :label="item.text" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="二级菜单" v-if="two">
          <el-select v-model="twoId" clearable placeholder="请选择">
            <el-option v-for="item in childrentow" :label="item.text" :key="item.id" :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="是否启用:">
          <el-switch v-model="isDisable" active-color="#13ce66" inactive-color="#ff4949">
          </el-switch>
        </el-form-item>

        <el-form-item label="文本内容">
          <el-input v-model="Dictionary.text"></el-input>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addRole()">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改字典" :visible.sync="updateDialogFormVisible" center width="30%">
      <el-form>
        <el-form-item label="文本内容">
          <el-input v-model="updateDictionary.text"></el-input>
        </el-form-item>

        <el-form-item label="是否启用:">
          <el-switch v-model="updateisDisable" active-color="#13ce66" inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="updateDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="update()">修改</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "dictionary",
  data() {
    return {
      searchText: '',
      p: 1,
      tableData: [],
      addDialogFormVisible: false,
      updateDialogFormVisible: false,
      Dictionary: {},
      total: 0,
      size: 5,
      level: '一级',
      one: false,//二级菜单
      two: false,//三级菜单
      isDisable: true,//是否启用
      children: [],
      oneId: '',
      twoId: '',
      childrentow: [],
      parentId: '',
      updateDictionary: {
        id: '',
        text: '',
        parentId: '',
        level: '',
        isDisable: ''
      },
      updateisDisable: true,
    }
  },
  methods: {
    add() {
      this.addDialogFormVisible = true;
      this.$axios.get("dictionary/menu").then(r => {
        this.children = r.data.data
      })
    },
    onemenu(id) {
      for (let i = 0; i < this.children.length; i++)
        if (this.children[i].id == id) {
          this.childrentow = this.children[i].children;
        }
      this.twoId = this.childrentow[0].id
    },
    addRole() {
      if (this.two) {
        this.parentId = this.twoId;
      } else if (this.one) {
        this.parentId = this.oneId;
      } else {
        this.parentId = 0;
      }
      if (this.level == '一级') {
        this.Dictionary.level = 1;
      } else if (this.level == '二级') {
        this.Dictionary.level = 2;
      } else {
        this.Dictionary.level = 3;
      }
      if (this.isDisable) {
        this.Dictionary.isDisable = 1;
      } else {
        this.Dictionary.isDisable = 0;
      }
      this.Dictionary.parentId = this.parentId;
      this.$axios.get("dictionary/add", {params: this.Dictionary}).then(r => {
        console.log(r)
        if (r.data.code = 200) {
          this.$message.success("添加成功");
          this.addDialogFormVisible = false;
          this.findDictionary();
        }
      })
    },
    doUpdate(row) {
      this.updateDialogFormVisible = true;
      this.updateDictionary.id = row.id;
      this.updateDictionary.text = row.text;
      this.updateDictionary.parentId = row.parentId;
      this.updateDictionary.level = row.level;
      this.updateDictionary.isDisable = row.isDisable;
      if (this.updateDictionary.isDisable == 1) {
        this.updateisDisable = true;
      } else {
        this.updateisDisable = false;
      }
    },
    update() {
      if (this.updateisDisable == true) {
        this.updateDictionary.isDisable = 1;
      } else {
        this.updateDictionary.isDisable = 0;
      }
      this.$axios.get("dictionary/update", {params: this.updateDictionary}).then(r => {
        if (r.data.code == 200) {
          this.updateDialogFormVisible = false;
          this.$message.success("修改成功");
          this.findDictionary();
        }
      })
    },
    del(row) {
      this.$confirm("此操作将删除该字典,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("dictionary/delete", {params: {id: row.id}}).then(r => {
          if (r.data.code != 200) {
            this.$message.error(r.data.msg);
          } else {
            this.$message.success("删除成功");
            this.findDictionary();
          }
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },
    agreeChange(label) {
      if (label == '一级') {
        this.one = false;
        this.two = false;
      } else if (label == '二级') {
        this.one = true;
        this.two = false;
      } else if (label == '三级') {
        this.one = true;
        this.two = true;
      }
    },
    findDictionary() {
      this.$axios.get("dictionary/list", {
        params: {
          p: this.p,
          searchText: this.searchText,
          size: this.size
        }
      }).then(r => {
        this.tableData = r.data.data.list
        this.total = r.data.data.total
      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.findDictionary();
    },
    handleSizeChange(val) {
      this.size = val;
      this.findDictionary();
    },
  },
  created() {
    this.findDictionary();
  }
}

</script>

<style scoped>

</style>
