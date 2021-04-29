<template>
  <div class="wrapper">
    <!-- 头部 -->
    <el-row class="l-head">
      <el-col :span="8" class="l-img">
                <img src="../../assets/image/logo.png" class="logo"/>
      </el-col>
      <el-col :span="8" class="l-head-zj">
        <span class="span1">
          车辆管理系统
        </span>
      </el-col>
      <el-col :span="8" class="l-head-youbian">
        <span class="span2">
          欢迎您&nbsp;&nbsp;&nbsp;
          <el-tooltip placement="top">
                  <div slot="content">退出</div>
          <el-button type="danger" icon="el-icon-switch-button" circle @click.prevent="logout"></el-button>
            </el-tooltip>
        </span>
      </el-col>
    </el-row>
    <!-- 中左 -->
    <el-container>
      <el-container>
        <el-aside width="200px" class="aside">
          <!-- default-active默认显示高亮 -->
          <el-menu class="el-menu-vertical-demo" :router="true">

            <el-submenu :index="item.id+''" v-for="item in menu" :key="item.id">
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>{{ item.name }}</span>
              </template>
              <el-menu-item-group>
                <el-menu-item :index="son.link" v-for="son in item.children" :key="son.id">
                  {{ son.name }}</el-menu-item>
              </el-menu-item-group>
            </el-submenu>

          </el-menu>
        </el-aside>
        <!-- 中右 -->
        <el-main class="main">
          <router-view></router-view>
        </el-main>
      </el-container>
      <!-- 底部 -->
      <el-footer class="el-footer" height="60px">
        <h3 id="h2">2021 &copy;版权所有&nbsp;蜗牛学院</h3>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
export default {
  data() {
    return {
      menu: [],
    }
  },
  methods: {
    findPerms() {
      var token = localStorage.getItem("token");
      this.$axios.get("http://localhost/perms/menu", {
        params: {},
        headers: {Authorization: token}
      }).then(r => {
        this.menu = r.data.data;
      })
    },
    logout() {
      this.$confirm("此操作将退出该账户,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        localStorage.removeItem("token");
        this.$router.push("/");
        this.$message({
          message: "退出成功",
          type: "success"
        });
      }).catch(res => {
        this.$message({
          message: "退出取消",
          type: "info"
        });
      })
    }
  },
  created() {
    this.findPerms();
  }
}
</script>

<style scoped>
.aside {
  background-color: #fff9e6;
}

.main {
  background-color: #edfff3;
}

.wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100%;
}

.l-head {
  background-color: black;
}

.logo {
  width: 150px;
  padding: 10px;
}

.l-head-zj {
  text-align: center;
  height: 65px;
  line-height: 65px;
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
