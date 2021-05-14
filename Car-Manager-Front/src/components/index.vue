<template>
  <div>
    <br />
    <br />
    <div align="center" style="width:100%">
      <el-carousel height="500px">
        <el-carousel-item v-for="item in pics" :key="item.url">
          <img :src="item.url" />
        </el-carousel-item>
      </el-carousel>
    </div>
    <br />
    <br />
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      pics: []
    };
  },

  mounted() {
    this.pics = [
      { url: require("../assets/image/01.jpg") },
      { url: require("../assets/image//02.jpg") },
      { url: require("../assets/image/03.jpg") },
      { url: require("../assets/image/04.jpg") }
    ];
  }
};
</script>

<style></style>
