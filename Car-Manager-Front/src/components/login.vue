<template>
  <div class="login">
    <div class="subject">
      <el-row>
        <el-col :span="12">
          <div style="margin-top: 30px">
            <span style="font-weight: 900;font-size: 40px;padding: 30px;">LOGO</span>
          </div>

          <div>
            <el-row>
              <el-col :span="12" :push="7">
                <div style="margin-top: 30%">
                  <span style="font-weight: 900;font-size: 20px;padding: 40px;">小蜗牛车辆管理系统</span>
                </div>
              </el-col>
            </el-row>
            <div>
              <div style="margin-top: 70px">
                <el-col :span="14" :push="5">
                  <el-input placeholder="请输入账号" v-model="employee.account" max clearable
                            prefix-icon="el-icon-user-solid">
                  </el-input>
                </el-col>
              </div>
<br/><br/><br/><br/><br/>
              <div>
                <el-col :span="14" :push="5">
                  <el-input placeholder="请输入密码" v-model="employee.password" prefix-icon="el-icon-lock"
                            type="password" clearable @keyup.enter.native="login">
                  </el-input>
                </el-col>
              </div>
              <br/><br/><br/><br/><br/>
              <el-col :span="6" :push="7">
                <div>

                  <el-popover placement="bottom" trigger="click">
                    <slide-verify :l="42" :r="10" :w="310" :h="155"
                                  @success="onSuccess" @fail="onFail" @refresh="onRefresh"
                                  :slider-text="text">
                    </slide-verify>
                    <el-button type="primary" slot="reference">登入</el-button>
                  </el-popover>
                </div>
              </el-col>
              <el-col :span="6" :push="7">
                <div>
                  <el-button type="info" @click="reset">重置</el-button>
                </div>
              </el-col>

            </div>
          </div>
        </el-col>
        <div style="padding: 2cm 0cm 4cm 0px">
          <img src="@/assets/image/1.png" alt="" style="width: 400px">
        </div>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      employee: {
        account: "",
        password: "",
      },
      drawer: false,
      text: '向右滑',
    };
  },
  methods: {
    reset() {
      this.employee.account = "";
      this.employee.password = "";
    },
    login() {
      this.drawer = true;
    },
    //验证成功
    onSuccess() {
      this.$axios.post("employee/login", this.employee).then(r => {
        if (r.data.code == 200) {
          //存进token
          localStorage.setItem("token", r.data.msg);
          //将账号和用户名存进浏览器中
          localStorage.setItem("account", this.employee.account);
          //跳转路由
          this.$router.push("/index");
          this.$message.success("登入成功");
        } else if (r.data.code == 101) {
          this.$message.error("用户不存在");
        } else if (r.data.code == 102) {
          this.$message.error("密码错误");
        }
      });
    },
    // //验证失败
    // onFail() {
    //   this.msg = ''
    // },
    // //刷新
    // onRefresh() {
    //   this.msg = ''
    // }
  }
};
</script>

<style scoped>
.login {
  width: 100%;
  height: 100%;
  background-color: #8FC3FC;
  justify-content: center;
  align-items: center;
  display: -webkit-flex;
}

.subject {
  width: 80%;
  height: 80%;
  background-color: white;
  border-radius: 35px;
}

</style>
