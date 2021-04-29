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
              <el-input placeholder="请输入内容" v-model="employee.password">
                <el-button slot="prepend" icon="el-icon-lock"></el-button>
              </el-input>
            </el-col>
          </div>
          <br/><br/><br/><br/>

          <el-row :gutter="20">
            <el-col :span="12" :push="3">
              <el-input placeholder="请输入验证码" v-model="employee.captcha"></el-input>
            </el-col>
            <el-col :span="6" :push="3">
              <div class="img">
                <img src="" alt="">
              </div>
            </el-col>
          </el-row>
          <br/><br/>
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
        if (r.data.code == 200) {

        }
      })
    }
  }
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
background-size: 100 %;
}

.subject {
  border-radius: 35px;
  width: 500px;
  height: 450px;
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
