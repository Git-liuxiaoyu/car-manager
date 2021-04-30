import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router/router'


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
