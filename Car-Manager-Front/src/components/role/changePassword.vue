<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>系统设置</el-breadcrumb-item>
      <el-breadcrumb-item>修改密码</el-breadcrumb-item>
    </el-breadcrumb>

    <br />
    <br />
    <div class="lox">
      <el-form ref="form" label-width="100px">
        <el-row>
          <el-col :span="6" :push="8">
            <el-form-item label="原密码:" size="medium">
              <el-input
                v-model="password"
                type="password"
                @input="isNo"
                @blur="compare"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :push="8">
            <el-form-item label="新密码:" size="medium" >
              <el-input v-model="newOnePassword" type="password"  @input="isNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :push="8">
            <el-form-item label="确定密码:" size="medium" >
              <el-input v-model="newTwoPassword" type="password"  @input="isNo"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :push="10">
            <el-button type="primary " size="mini" @click="updatePassWord" :disabled="disabled"
              >立即修改</el-button
            >
            <el-button size="mini" @click="cancel">取消</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "changePassword",
  data() {
    return {
      //当前用户信息
      employee: {},
      password: "", //输入的原密码
      newOnePassword: "", //输入的新密码
      newTwoPassword: "", //输入的确定的新密码
      disabled:true,
    };
  },
  methods: {
    //判断按钮是否禁止
    isNo(){
if(this.password=="" || this.newOnePassword=="" || this.newTwoPassword==""){
this.disabled=true;
}else{
  this.disabled=false;
}
    },
    //根据用户名查询密码
    getByAccount() {
      let account = localStorage.getItem("account");
      this.$axios.get("employee/getByAccount?account=" + account).then(r => {
        this.employee = r.data.data;
      });
    },
    compare() {
      if (this.password != this.employee.password) {
        this.$message.error("原密码不正确");
      }
    },
    updatePassWord() {
      if (this.password != this.employee.password) {
        this.$message.error("原密码不正确");
        return;
      }
      if (this.newOnePassword != this.newTwoPassword) {
        this.$message.error("两次输入的密码不一致");
        return;
      }
      this.$confirm("修改密码后会退出本系统,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(res => {
          this.employee.password = this.newOnePassword;
          this.$axios.post("employee/updatePassWord", this.employee).then(r => {
            if ((r.data.code = 200)) {
              this.$message.success("修改成功");
              localStorage.removeItem("token");
              this.$router.push("/");
            }
          });
        })
        .catch(res => {
          this.$message({
            message: "退出取消",
            type: "info"
          });
        });
    },
    //取消修改
    cancel() {
      this.password = "";
      this.newOnePassword = "";
      this.newTwoPassword = "";
      this.$router.push("/userManager");
    }
  },
  created() {
    this.getByAccount();
  }
};
</script>

<style scoped>
.lox {
  margin-top: 100px;
}
</style>
