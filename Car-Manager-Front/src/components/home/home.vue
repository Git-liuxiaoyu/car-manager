<template>
  <div class="wrapper">
    <!-- 头部 -->
    <el-row class="l-head">
      <el-col :span="10" class="l-head-zj">
        <i class="glyphicon glyphicon-leaf"></i>
        <span>小蜗牛车辆管理系统</span>
      </el-col>

      <el-col :span="13" class="l-head-youbian">
        <el-dropdown @command="handleCommand">
          <el-button style="background-color:  #438EB9;color: white">
            <i class="glyphicon glyphicon-th-list"></i>
            <i class="el-icon-arrow-down el-icon--right"></i>
          </el-button>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item icon="el-icon-user" disabled>{{ userNamer }}</el-dropdown-item>
            <el-dropdown-item icon="el-icon-user-solid" command="a">个人信息</el-dropdown-item>
            <el-dropdown-item icon="glyphicon glyphicon-cog" command="b">修改密码</el-dropdown-item>
            <el-dropdown-item icon="el-icon-loading" command="d">刷新界面</el-dropdown-item>
            <el-dropdown-item icon="glyphicon glyphicon-share" command="c">退出系统</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
    </el-row>

    <el-container>
      <!-- 中左 -->
      <!-- default-active默认显示高亮 -->
      <el-aside width="200px" class="main">
        <el-menu
          class="el-menu-vertical-demo"
          :router="true"
          background-color="#F5F5F5"
          :unique-opened="true"
          default-active="index"
        >
          <el-menu-item index="index">
            <i class="glyphicon glyphicon-home"></i>
            <span slot="title">系统首页</span>
          </el-menu-item>

          <el-submenu :index="item.id + ''" v-for="item in menu" :key="item.id">
            <template slot="title">
              <i class="glyphicon glyphicon-list"></i>
              <span>{{ item.name }}</span>
            </template>

            <el-menu-item-group>
              <el-menu-item :index="son.href"
                            v-for="son in item.children" :key="son.id">
                <i class="glyphicon glyphicon-align-left"></i>
                <span>{{ son.name }}</span>
              </el-menu-item>
            </el-menu-item-group>
          </el-submenu>
        </el-menu>
      </el-aside>

      <!-- 中右 -->
      <el-main class="main1">
        <router-view/>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      menu: [],
      userNamer: "",
    };
  },
  methods: {
    handleCommand(item) {
      if (item == "a") {
        this.$router.push("/userData");
      }
      if (item == "b") {
        this.$router.push("/changePassword");
      }
      if (item == "c") {
        this.logout();
      }
      if (item == "d") {
        this.$router.go(0);
      }
    },
    findPerms() {
      let account=localStorage.getItem("account");
      this.$axios.get("employee/menu?account=" + account).then(r => {
          this.menu = r.data.data;
          this.userNamer = this.menu[0].userName;
        });
    },
    logout() {
      this.$confirm("此操作将退出该账户,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(res => {
          localStorage.removeItem("token");
          this.$router.push("/");
          this.$message({
            message: "退出成功",
            type: "success"
          });
        })
        .catch(res => {
          this.$message({
            message: "退出取消",
            type: "info"
          });
        });
    }
  },
  created() {
    this.findPerms();
  }
};
</script>

<style scoped>
.main {
  background-color: #F5F5F5;
  height: 90vh;
  overflow-y: auto;
}

.main1 {
  background-color: white;
  height: 90vh;
  overflow-y: auto;
}

.wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100%;
}

.l-head {
  background-color: #438EB9;
}

.logo {
  width: 180px;
  padding: 10px;
}

.l-head-zj {
  font-size: 30px;
  margin-left: 20px;
  color: white;
  line-height: 65px;
  height: 10vh;
}

.span1 {
  font-size: 30px;
  color: #ffffff;
  text-shadow: #ffffff 3px 3px 4px;
}

.l-head-youbian {
  text-align: center;
  height: 65px;
  line-height: 65px;
  text-align: right;
}

.span2 {
  font-size: 15px;
  color: #ffffff;
  text-shadow: #ffffff 3px 3px 4px;
  padding: 50px;
}

.el-footer {
  line-height: 60px;
  text-align: center;
  background-color: black;
  color: black;
}

#h2 {
  color: white;
  text-shadow: #ffffff 3px 3px 4px;
}
</style>
