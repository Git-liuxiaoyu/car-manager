<template>
  <div>
      <!-- 柱状图 -->
    <div
      class="HelloWorld echart-box"
      id="myChart1"
      :style="{width: '1000px', height: '500px'}"
    ></div>
   <div>

    <el-form :inline="true" class="demo-form-inline">
    <el-form-item label="" prop="carId">
              <el-select v-model="reportCarDate.carId" placeholder="鄂A88888" @change="handleCommand(reportCarDate.carId)">
                <!-- <el-option label="请选择" value="0"></el-option> -->
                <el-option :label="reportCar.carNum" :value="reportCar.carId"
                           v-for="reportCar in reportCarDate" :key="reportCar.id" >
                </el-option>
              </el-select>
    </el-form-item>
     <!-- <el-button type="primary" @click="handleCommand(reportCarDate.carId)">确 定</el-button>  -->
    </el-form>

  </div>
    <div
      class="HelloWorld echart-box"
      id="myChart2"
      :style="{width: '1000px', height: '500px',background:'#ffffff'}"
    ></div>

  </div>
</template>

<script>
var that = this;
export default {
  data() {
    return {
      // 定义图表，各种参数
    //   msg: "柱状图",
    reportCarDate:[],
      opinion: [],
      opinionData: [],
      datas: [
        // { value: 64, name: "男" },
        // { value: 32, name: "女" },
        // { value: 12, name: "未知" },
      ],

      names:[],
    };
  },
  mounted: function () {
    this.drawLine(); //按照默认值绘制图表
    this.drawLine1();
  },
  created() {
    this.$axios.get("reportCar/all").then(r=>{
        // console.log(r);
        this.reportCarDate=r.data.data;
        console.log(this.reportCarDate);
        this.opinion.length = 0; //清空数组
        this.opinionData.length = 0; //清空数组

        for (let i = 0; i < r.data.data.length; i++) {
            this.opinion.push(r.data.data[i].carNum);
            this.opinionData.push(r.data.data[i].totalMoney);
        }

         console.log(this.opinion);
         console.log(this.opinionData);
         
        });
        this.loadPie(1);
    //     this.$axios.get("reportCar/one?carNum=鄂A88888").then(r=>{
    //     console.log(r);
    //     console.log(this.datas);
    //     this.datas.length = 0; //清空数组

    //      for (let key in r.data.data) {
    //       var item = {
    //         value: r.data.data[key],
    //         name: key,
    //       };
          
    //       this.datas.push(item);
    //       this.names.push(key);
    //     }
    // //    console.log(this.datas);
    //    console.log(item.value);
    //     console.log(item.name);

         
    //     });

  },
  watch: {
    opinion: {
      //对于深层对象的属性，watch不可达，因此对数组监控需要将数组先清空，再添加数据
      handler: function () {
        this.drawLine();
      },
      deep: true,
    },
    datas: {
      handler: function () {
        this.drawLine1();
      },
      deep: true,
    },

  },


  methods: {
    loadPie(id){
        this.$axios.get("reportCar/one?carId="+id).then(r=>{
        // console.log(r);
        // console.log(this.datas);
        this.datas.length = 0; //清空数组

         for (let key in r.data.data) {
          var item = {
            value: r.data.data[key],
            name: key,
          };
          
          this.datas.push(item);
          this.names.push(key);
        }
    //    console.log(this.datas);
      //  console.log(item.value);
      //   console.log(item.name);     
        });
    },
    drawLine() {
      // 1、基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById("myChart1"));
      //2、构造图表数据
      let options = {
        title: {
          text: "车辆费用对比",
          left: "center",
          tooltip: {},
        },
        xAxis: {
        //   data: [
        //     "0~10",
        //     "10~20",
        //     "21~30",
        //     "31~40",
        //     "41~50",
        //     "51~60",
        //     "61~70",
        //     "71~80",
        //     "80以上",
        //   ],
        data:this.opinion,
        },
        yAxis: {},
        barWidth:"30%",
        label:{
          show:true,
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a}: {b} <br/> 总费用：{c}元'
        },
        series: [
          {
            name: "车牌号",
            data: this.opinionData,
            type: "bar",
          },
        ],
      };
      // 3、绘制图表
      myChart.setOption(options);
    },


        drawLine1() {
      // 1、基于准备好的dom，初始化echarts实例
      let myChart = this.$echarts.init(document.getElementById("myChart2"));
      //2、构造图表数据
      let options = {
        title: {
          text: "车辆费用分布",
          left: "center",
        },
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} :  ({d}%)",
        },
        legend: {
          orient: "vertical",
          left: "right",
        //   data: ["男", "女", "未知"],
        data: this.names,
        },
        series: [
          {
            name: "费用占比",
            type: "pie",
            radius: "55%",
            center: ["50%", "60%"],
            data: this.datas,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      // 3、绘制图表
      myChart.setOption(options);
    },
    handleCommand(id){
      //  alert(id);
       this.loadPie(id);
    }

  },
};
</script>



<style>

</style>
