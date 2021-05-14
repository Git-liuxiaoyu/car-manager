<template>
<div>
  <!-- 我是首页 -->
    <!-- 柱状图 -->



    <div align="center" style="width:100%">
    <el-carousel height="500px" >
      <el-carousel-item v-for="item in pics" :key="item.url">
        <img :src="item.url"/>
      </el-carousel-item>
    </el-carousel>
  </div>
     <br/>
     <br/>
    <div align="center">
        欢迎您，使用车辆管理系统！！！
    </div>
     <br/>
    <!-- <div
      class="HelloWorld echart-box"
      id="myChart1"
      :style="{width: '1000px', height: '500px'}"
    ></div> -->
</div>
</template>

<script>
export default {
  name: "index",
  data() {
      return {
        cars:[],
        names:[],
        nums:[],   
        num:0,
        pics: [
        
      ]
        }
    },

    mounted(){
            // this.drawLine();
            this.pics=[{ url: require("../assets/image/01.jpg") },
        { url: require("../assets/image//02.jpg") },
        { url: require("../assets/image/03.jpg") },
        { url: require("../assets/image/04.jpg") }]

    },
    created(){
        // this.getCarList();



      
    },
    //  watch: {
    // names: {
    //   //对于深层对象的属性，watch不可达，因此对数组监控需要将数组先清空，再添加数据
    //   handler: function () {
    //     this.drawLine();
    //   },
    //   deep: true,
    // },
    //     nums: {
    //   //对于深层对象的属性，watch不可达，因此对数组监控需要将数组先清空，再添加数据
    //   handler: function () {
    //     this.drawLine();
    //   },
    //   deep: true,
    // },
    // },

    // methods:{



    //     getCarList(){
    //     this.$axios.get("car/getAll").then(r => {
    //         this.cars = r.data.data
    //         console.log(this.cars)
    //         for(let i=0 ;i<this.cars.length;i++){
    //         this.names.push(this.cars[i].carNum);
    //          this.departureNum(this.cars[i].id);
    //         //  console.log(this.num)
    //         //  this.nums.push(this.num);
    //        }

    //     })

    //     },
    //     departureNum(carId){
    //      this.$axios.get("reportCar/departureNum?carId="+carId).then(r=>{
    //          console.log(r);
    //          this.num=r.data;
    //           this.nums.push(this.num); 
    //      });
      
    //     },

    //     drawLine() {
    //   // 1、基于准备好的dom，初始化echarts实例
    //   let myChart = this.$echarts.init(document.getElementById("myChart1"));
    //   //2、构造图表数据
    //   let options = {
    //     title: {
    //       text: "出车统计表",
    //       left: "center",
    //       tooltip: {},
    //     },
    //     xAxis: {
    //       //   data: [
    //       //     "0~10",
    //       //     "10~20",
    //       //     "21~30",
    //       //     "31~40",
    //       //     "41~50",
    //       //     "51~60",
    //       //     "61~70",
    //       //     "71~80",
    //       //     "80以上",
    //       //   ],
    //       data:this.names,
    //     },
    //     yAxis: {},
    //     barWidth:"30%",
    //     label:{
    //       show:true,
    //     },
    //     tooltip: {
    //       trigger: 'item',
    //       formatter: '{a}: {b} <br/> 出车次数：{c}次'
    //     },
    //     series: [
    //       {
    //         name: "车牌号",
    //         data: this.nums,
    //         type: "bar",
    //       },
    //     ],
    //   };
    //   // 3、绘制图表
    //   myChart.setOption(options);
    // },
    // }



}
</script>

<style>
/* #container{
    width: 500px;
    height: 300px;
} */

</style>
