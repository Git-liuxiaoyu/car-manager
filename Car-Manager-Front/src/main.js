import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/router'
import * as echarts from 'echarts';
Vue.prototype.$echarts = echarts;
import SlideVerify from 'vue-monoplasty-slide-verify';

Vue.use(SlideVerify);
Vue.use(ElementUI);
Vue.config.productionTip = false

import axios from "axios";
axios.defaults.baseURL = 'http://localhost:8888'
Vue.prototype.$axios=axios


axios.interceptors.request.use(
  config => {
    config.headers.Authorization = localStorage.getItem("token");
    return config
  },
  error => {
    return Promise.reject(error);
  });

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})

// 全局过滤器
Vue.filter("dateConvert", function (str) {
  let date = new Date(str);
  let year = date.getFullYear().toString();
  let month = (date.getMonth() + 1).toString().padStart(2, 0);
  let day = date.getDate().toString().padStart(2, 0);
  let hour = date.getHours().toString().padStart(2, 0);
  let minute = date.getMinutes().toString().padStart(2, 0);
  let second = date.getSeconds().toString().padStart(2, 0);
  let time = year+'-'+month+'-'+day;
  return time
});

//时间全局过滤器
Vue.filter("timeConvert", function (str) {
  let date = new Date(str);
  let year = date.getFullYear().toString();
  let month = (date.getMonth() + 1).toString().padStart(2, 0);
  let day = date.getDate().toString().padStart(2, 0);
  let hour = date.getHours().toString().padStart(2, 0);
  let minute = date.getMinutes().toString().padStart(2, 0);
  let second = date.getSeconds().toString().padStart(2, 0);
  let time = `${year}-${month}-${day} ${hour}:${minute}:${second}`;
  return time
});
