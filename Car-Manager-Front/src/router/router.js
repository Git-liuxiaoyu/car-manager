import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

let router = new Router({
  mode: 'history',
  routes: [
    {
      path: '/', redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/components/login'),
    }
  ]
})
export default router;
router.beforeEach((to, from, next) => {
  if (to.path == '/login') {
    next()
  } else {
    var token = localStorage.getItem("token");
    token ? next() : next('/login')
  }
})