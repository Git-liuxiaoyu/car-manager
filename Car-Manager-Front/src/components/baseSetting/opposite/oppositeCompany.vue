<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础设置</el-breadcrumb-item>
      <el-breadcrumb-item>往来单位</el-breadcrumb-item>
    </el-breadcrumb>

    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="输入车牌号查询" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="oppolist()"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showEditoppo()">新增</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <template>

      <el-table :data="oppos" border style="width: 100%" height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column fixed prop="id" label="id" min-width>

        </el-table-column>

        <el-table-column prop="name" label="单位名称" min-width>

        </el-table-column>

        <el-table-column prop="typename" label="单位类型" min-width>


        </el-table-column>

        <el-table-column prop="phone" label="单位电话" min-width>

        </el-table-column>

        <el-table-column prop="linkName" label="联系人" min-width>

        </el-table-column>

        <el-table-column prop="status" label="是否启用" min-width>
          <template slot-scope="scope">{{ scope.row.status == '1' ? '启用' : '禁用' }}</template>

        </el-table-column>

        <el-table-column prop="" label="操作" width="170">
          <template slot-scope="scope">
            <!-- element -ui  button   plain属性，鼠标放上去变蓝色  size="mini" -->
            <el-button icon="el-icon-search" circle plain @click="detail(scope.row.id,scope.row.type)">


            </el-button>

            <el-button type="primary" icon="el-icon-edit" circle plain
                       @click="goupdate(scope.row.id,scope.row.type)"
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


    <!-- 添加往来单位信息 -->
    <el-dialog title="新增往来单位信息" :visible.sync="oppoVisible">
      <el-form :model="editoppo" label-width="80px">
        <el-form-item label="单位名称" prop="name">

          <el-input v-model="editoppo.name"></el-input>

        </el-form-item>

        <el-form-item label="单位类型" prop="type">
          <el-select v-model="editoppo.type" placeholder="请选择">
            <el-option label="请选择" value="0"></el-option>
            <el-option :label="types.text" :value="types.id"
                       v-for="types in types" :key="types.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="地址" prop="address">
          <el-input v-model="editoppo.address"></el-input>
        </el-form-item>

        <el-form-item label="电话" prop="phone">
          <el-input v-model="editoppo.phone"></el-input>
        </el-form-item>

        <el-form-item label="联系人" prop="linkName">
          <el-input v-model="editoppo.linkName"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remarks">
          <el-input v-model="editoppo.remarks"></el-input>
        </el-form-item>

        <el-form-item label="是否锁定" prop="status">
          <!-- element-ui 的 开关 switch -->
          <el-switch v-model="editoppo.status"></el-switch>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="oppoVisible = false">取 消</el-button>
        <el-button type="primary" @click="addoppo">确 定</el-button>
      </div>
    </el-dialog>


    <!--往来单位详情-->
    <el-dialog title="往来单位详细信息" :visible.sync="oppodetail" center width="70%">
      <el-form :model="details" label-width="150px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="单位名称：" prop="name">
              {{ details.name }}
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="单位类型：" prop="typename">
              {{ typee }}
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="单位地址：" prop="address">
              {{ details.address }}
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="联系电话：" prop="phone">
              {{ details.phone }}
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="联系人：" prop="linkName">
              {{ details.linkName }}
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="备注：" prop="remarks">
              {{ details.remarks }}
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item label="状态：" prop="status">
              {{ details.status }}
            </el-form-item>
          </el-col>
          <div slot="footer" class="dialog-footer">
            <el-button @click="oppodetail = false">确定</el-button>

          </div>
        </el-row>
      </el-form>
    </el-dialog>


    <!-- 修改往来单位信息 -->
    <el-dialog title="修改往来单位信息" :visible.sync="oppoupdate">
      <el-form :model="updates" label-width="80px">
        <el-form-item label="单位名称" prop="name">

          <el-input v-model="updates.name"></el-input>

        </el-form-item>

        <el-form-item label="单位类型" prop="type">
          <el-select v-model="updates.type" placeholder="请选择" :disabled="true">
            <el-option label="请选择" value="0"></el-option>
            <el-option :label="types.text" :value="types.id"
                       v-for="types in types" :key="types.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="地址" prop="address">
          <el-input v-model="updates.address"></el-input>
        </el-form-item>

        <el-form-item label="电话" prop="phone">
          <el-input v-model="updates.phone"></el-input>
        </el-form-item>

        <el-form-item label="联系人" prop="linkName">
          <el-input v-model="updates.linkName"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remarks">
          <el-input v-model="updates.remarks"></el-input>
        </el-form-item>

        <el-form-item label="是否锁定" prop="status">
          <!-- element-ui 的 开关 switch -->
          <el-switch v-model="updates.status"></el-switch>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="oppoupdate = false">取 消</el-button>
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
      oppos: [
        {
          id: "",
          name: "",
          type: "",
          address: "",
          phone: "",
          linkName: "",
          remarks: "",
          status: "",
        }
      ],
       //分页
      searchText: '',
      p: 1,
      addDialogFormVisible: false,
      Employee: {},
      total: 0,
      size: 5,
      //控制添加对话框是否显示
      oppoVisible: false,
      //详细信息对话框
      oppodetail: false,
      //修改对话框
      oppoupdate: false,

      //新增往来单位
      editoppo: {
        name: "",
        type: "",
        address: "",
        phone: "",
        linkName: "",
        remarks: "",
        status: "",
      },

      types: [],
      typee:"",

      //详细信息
      details: {
        name: "",
        typename: "",
        address: "",
        phone: "",
        linkName: "",
        remarks: "",
        status: "",
      },
      //修改数据
      updates: {
        id: "",
        name: "",
        typename: "",
        address: "",
        phone: "",
        linkName: "",
        remarks: "",
        status: "",
      }


    }
  },
  methods: {
    oppolist() {
      this.$axios.get("opposite/list", {
        params: {
          p: this.p,
          searchText: this.searchText,
          size: this.size
        }
      }).then(r => {

        this.oppos = r.data.data.list
        this.total = r.data.data.total
        console.log("列表的数据")
        console.log(r)
      })

    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.oppolist();
    },
    handleSizeChange(val) {
      this.size = val;
      this.oppolist();
    },



    //显示新增对话框
    showEditoppo() {
      this.oppoVisible = true;
      this.$axios.post("dictionary/menu").then(r => {
        console.log("新增对话框")
        console.log(r)
        this.types = r.data.data[3].children
      })
    },
    //添加往来单位
    addoppo() {

      if (this.editoppo.status) {
        this.editoppo.status = '0'
      } else {
        this.editoppo.status = '1'
      }
      this.$axios.post("opposite/add", this.editoppo).then(r => {
        //console.log(r)
        if (r.data.code == 200) {
          this.$message({type: 'success', message: "添加成功", duration: 800});
          this.oppoVisible = false;

          //循环清空editoppo集合中的值
          for (var i in this.editoppo) {
            this.editoppo[i] = "";
          }

          //重新加载页面
          this.oppolist();
        } else {
          this.$message({type: 'success', message: "添加失败", duration: 800});
        }
      })
    },
    //往来单位详情
    detail(id, type) {

      this.oppodetail = true;
      this.$axios.post("opposite/findbyid?id=" + id).then(r => {
        //console.log(r)
        this.details = r.data.data
        if (this.details.status == 1) {
          this.details.status = "启用";
        } else {
          this.details.status = "禁用";
        }
      })
       this.$axios.post("dictionary/menu").then(r => {
        console.log("详情对话框")
        console.log(r)
        this.types = r.data.data[3].children
            for (var i in this.types) {
            if(this.types[i].id==type){
              this.typee = this.types[i].text
            }
          }

      })
    },
    //打开修改对话框
    goupdate(id, type) {
      this.oppoupdate = true;
      this.$axios.post("opposite/findbyid?id=" + id).then(r => {
        console.log("查询的修改数据")
        console.log(r)
        this.updates = r.data.data
        if (this.updates.status == 0) {
          this.updates.status = true;
        } else {
          this.updates.status = false
        }
      })
       this.$axios.post("dictionary/menu").then(r => {
        console.log("修改对话框")
        console.log(r)
        this.types = r.data.data[3].children

      })
    },
    //修改
    update() {

      if (this.updates.status) {
        this.updates.status = '0'
      } else {
        this.updates.status = '1'
      }
      this.$axios.post("opposite/update", this.updates).then(r => {
        console.log(r)
        if (r.data.code == 200) {
          this.$message({type: 'success', message: "修改成功", duration: 800});
          this.oppoupdate = false;
          //循环清空editoppo集合中的值
          for (var i in this.updates) {
            this.updates[i] = "";
          }
          //重新加载页面
          this.oppolist();
        } else {
          this.$message({type: 'success', message: "修改失败", duration: 800});
        }
      })

    },

    //删除往来单位信息
    del(id) {
      this.$confirm("此操作将删除该账户,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.post("opposite/delete?id=" + id).then(r => {
          console.log(r)
          if (r.data.code == 200) {
            this.$message({type: 'success', message: "删除成功", duration: 800});
            //重新加载页面
            this.oppolist();
          } else {
            this.$message({type: 'success', message: "删除失败", duration: 800});
          }
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    }
  },
  created() {
    this.oppolist()
  }


}
</script>

<style>

</style>
