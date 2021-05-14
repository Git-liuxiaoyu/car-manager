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
            <el-form ref="form" :model="employee" :rules="rules">
              <div>
                <div style="margin-top: 70px">
                  <el-col :span="14" :push="5">
                    <el-form-item prop="account">
                      <el-input placeholder="请输入账号" v-model="employee.account"
                                max clearable prefix-icon="el-icon-user-solid" clearable
                                @keyup.enter.native="submitForm('form')">
                      </el-input>
                    </el-form-item>
                  </el-col>
                </div>
                <br/><br/><br/><br/>
                <div>
                  <el-col :span="14" :push="5">
                    <el-form-item prop="password">
                      <el-input placeholder="请输入密码" v-model="employee.password" prefix-icon="el-icon-lock"
                                type="password" clearable @keyup.enter.native="submitForm('form')">
                      </el-input>
                    </el-form-item>
                  </el-col>
                </div>
                <br/><br/><br/><br/>
                <el-col :span="9" :push="5">
                  <el-form-item prop="seccode">
                    <el-input placeholder="请输入验证码" v-model="employee.seccode" prefix-icon="el-icon-lock"
                              clearable @keyup.enter.native="submitForm('form')">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :span="4" :push="6">
                  <div class="yzm" @click="createCode" title="看不清点我">
                    <p class="checkCode">{{ checkCode }}</p>
                  </div>
                </el-col>
                <br/><br/><br/><br/>
                <el-col :span="6" :push="7">
                  <div>
                    <el-button type="primary" slot="reference" @click="submitForm('form')">登入</el-button>
                  </div>
                </el-col>
                <el-col :span="6" :push="7">
                  <div>
                    <el-button type="info" @click="reset">重置</el-button>
                  </div>
                </el-col>
              </div>
            </el-form>
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
        account: "",//账号
        password: "",//密码
        seccode: "",//验证码
      },
      disabled:true,
      checkCode: '',
      rules: {
        account: [{required: true, message: "请输入用户名", trigger: "blur"}],
        password: [{required: true, message: "请输入密码", trigger: "blur"}],
        seccode: [{required: true, message: "请输验证码", trigger: "blur"}]
      },
    }
  },
  methods: {
    doYz(){
      this.disabled=false;
    },
    reset() {
      this.employee.account = "";
      this.employee.password = "";
      this.employee.seccode = "";
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.login()
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    login() {
      if (this.employee.seccode != this.checkCode.toLowerCase()) {
        this.$message.error("输入的验证码有误");
        this.createCode();
        return;
      };
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
    //验证码
    createCode() {
      let code = "";
      const codeLength = 4; //验证码的长度
      const random = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
        'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'); //随机数
      for (let i = 0; i < codeLength; i++) { //循环操作
        let index = Math.floor(Math.random() * 36); //取得随机数的索引（0~35）
        code += random[index]; //根据索引取得随机数加到code上
      }
      this.checkCode = code; //把code值赋给验证码
    },
  },
  created() {
    this.createCode();
  },
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

.yzm {
  height: 40px;
  text-align: center;
  line-height: 40px;
  background-color: #909399;
}

</style>
