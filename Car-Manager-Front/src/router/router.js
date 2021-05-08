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
    },
    {
      path: '/home',
      component: () => import('@/components/home/home'),
      children: [
        {path: '/roleManager', component: () => import('@/components/role/roleManager.vue')},
        {path: '/index', component: () => import('@/components/index')},
        {path: '/car', component: () => import('@/components/baseSetting/car/car')},
        {path: '/opposite', component: () => import('@/components/baseSetting/opposite/oppositeCompany')},
        {path: '/driver', component: () => import('@/components/baseSetting/driver/driver')},
        {path: '/dictionary', component: () => import('@/components/baseSetting/dictionary/dictionary.vue')},
        {path: '/repairRecord', component: () => import('@/components/dailyProcess/repairRecord/repairRecord')},
        {path: '/oilrecord', component: () => import('@/components/baseSetting/oilrecord/oilrecord')},
        {path: '/fee', component: () => import('@/components/baseSetting/fee/fee')},
        {path: '/keeprecord', component: () => import('@/components/baseSetting/keeprecord/keeprecord')},
      ]
    },

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
