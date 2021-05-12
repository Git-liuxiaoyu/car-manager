<template>
  <div>
    <ol class="breadcrumb">
      <li><a href="javascript:void(0)" @click.prevent="getByAccount">Home</a></li>
      <li class="active">Personal Data</li>
    </ol>

    <div>
      <el-row :gutter="20" style="padding: 2cm 0cm 0cm 0cm">
        <el-col :span="6" :push="3">
          <a href="#" class="thumbnail" style="background-color: #F5F5F5">
            <img src="@/assets/image/profile-pic.jpg" alt=""
                 class="img-thumbnail" title="头像">
          </a>
          <el-col :span="5"><span>体重:</span></el-col>
          <el-col :span="19">
            <div class="progress progress-striped active">
              <div class="progress-bar progress-bar-info" role="progressbar"
                   aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                   style="width: 90%;">
                <span class="sr-only">40% 完成</span>
              </div>
            </div>
          </el-col>
          <el-col :span="5"><span>身高:</span></el-col>
          <el-col :span="19">
            <div class="progress progress-striped active">
              <div class="progress-bar progress-bar-success" role="progressbar"
                   aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
                   style="width: 40%;">
                <span class="sr-only">40% 完成</span>
              </div>
            </div>
          </el-col>
          <el-col :span="5"><span>爱好:</span></el-col>

          <el-col :span="19">
            <span class="label label-danger" style="width: 200px">唱</span>
            <span class="label label-primary">跳</span>
            <span class="label label-success">rap</span>
            <span class="label label-warning">篮球</span>

          </el-col>
        </el-col>

        <el-col :span="10" :push="4">
          <div class="panel panel-default">
            <div class="panel-heading" style="font-family:微软雅黑">个人资料</div>
            <table class="table table-bordered">
              <tbody>
              <tr class="danger">
                <td style="width: 20%">用户名</td>
                <td>{{ employee.name }}</td>
              </tr>
              <tr>
                <td>年龄</td>
                <td>{{ employee.age }}</td>
              </tr>
              <tr class="success">
                <td>性别</td>
                <td v-if="employee.gender==0">男</td>
                <td v-if="employee.gender==1">女</td>
              </tr>
              <tr>
                <th>手机号</th>
                <td>{{ employee.telephone }}</td>
              </tr>
              <tr class="warning">
                <th>部门</th>
                <td>销售部</td>
              </tr>
              <tr>
                <th>职位</th>
                <td>销售员</td>
              </tr>
              <tr class="active">
                <th>生日</th>
                <td>{{ employee.birthday|dateConvert() }}</td>
              </tr>
              <tr>
                <th>地址</th>
                <td>{{ employee.address }}</td>
              </tr>
              </tbody>
            </table>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
export default {
  name: "userData",
  data() {
    return {
      employee: {},
    }
  },
  methods: {
    getByAccount() {
      let account = localStorage.getItem("account");
      this.$axios.get("employee/getByAccount?account=" + account).then(r => {
        this.employee = r.data.data;
      });
    },
  },
  created() {
    this.getByAccount();
  }
}
</script>

<style scoped>

</style>
