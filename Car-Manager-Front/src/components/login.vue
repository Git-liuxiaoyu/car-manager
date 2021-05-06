<template>
  <div class="login">
    <el-row>
      <el-col :span="8" :push="8">
        <div class="subject">
          <p>欢迎登入后台管理系统</p>
          <br/><br/><br/>
          <div class="acc">
            <el-col :span="18" :push="3">
              <el-input placeholder="请输入内容" v-model="employee.account">
                <el-button slot="prepend" icon="el-icon-user-solid"></el-button>
              </el-input>
            </el-col>
          </div>

          <div class="pwd">
            <el-col :span="18" :push="3">
              <el-input placeholder="请输入内容" v-model="employee.password" type="password">
                <el-button slot="prepend" icon="el-icon-lock"></el-button>
              </el-input>
            </el-col>
          </div>
          <br/><br/><br/><br/>
          <el-col :span="6" :push="5">
            <div>
              <el-button type="primary" @click="login">登入</el-button>
            </div>
          </el-col>
          <el-col :span="6" :push="6">
            <div>
              <el-button type="info" @click="reset">重置</el-button>
            </div>
          </el-col>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: 'login',
  data() {
    return {
      employee: {
        account: '',
        password: '',
        captcha: ''
      },
    }
  },
  methods: {
    reset() {
      this.employee.account = '';
      this.employee.password = '';
      this.employee.captcha = ''
    },
    login() {
      this.$axios.post("employee/login", this.employee).then(r => {
        console.log(r)
        if (r.data.code == 200) {
          //存进token
          localStorage.setItem("token", r.data.msg);
          //跳转路由
          this.$router.push("/home");
          this.$message.success("登入成功");
        }else if(r.data.code == 101){
          this.$message.error("用户不存在");
        }else if (r.data.code == 102) {
          this.$message.error("密码错误");
        }
      })
    }
  },
  // created() {
  //   this.$axios.get("employee/captcha").then(r => {
  //     .src =r.data;
  //     console.log(r)
  //   })
  // },
}
</script>

<style scoped>
.login {
  width: 100%;
  height: 100%;
  background-image: linear-gradient(to right, aquamarine, blanchedalmond);
  /*background-image: url("../assets/image/login.jpg");*/
  background-color: #48c0c0;
  -webkit-animation: hue 5s infinite linear;
}

@keyframes hue {
  from {
    -webkit-filter: hue-rotate(0deg);
  }
  to {
    -webkit-filter: hue-rotate(360deg);
  }
}

.subject {
  border-radius: 35px;
  width: 500px;
  height: 350px;
  border: 1px solid black;
  margin-top: 30%;
  text-align: center;
  background-image: linear-gradient(to right, blanchedalmond, aquamarine);
}

p {
  margin-top: 80px;
  font-size: 35px;
  color: #497B9A;
}

.acc {
  margin-bottom: 60px;
}

.img {
  border: 1px solid red;
  width: 86px;
  height: 40px;
}
</style>
