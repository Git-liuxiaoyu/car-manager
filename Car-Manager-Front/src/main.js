import Vue from 'vue'
import App from './App'
import router from './router/router'

Vue.config.productionTip = false

import axios from "axios";
axios.defaults.baseURL = 'localhost:8888'
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
