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
        {path: '/userManager', component: () => import('@/components/role/userManager.vue')},
        {path: '/changePassword', component: () => import('@/components/role/changePassword.vue')},
        {path: '/index', component: () => import('@/components/index')},
        {path: '/car', component: () => import('@/components/baseSetting/car/car')},
        {path: '/opposite', component: () => import('@/components/baseSetting/opposite/oppositeCompany')},
        {path: '/driver', component: () => import('@/components/baseSetting/driver/driver')},
        {path: '/dictionary', component: () => import('@/components/baseSetting/dictionary/dictionary.vue')},
        {path: '/repairRecord', component: () => import('@/components/dailyProcess/repairRecord/repairRecord')},
        {path: '/oilrecord', component: () => import('@/components/dailyProcess/oilrecord/oilrecord')},
        {path: '/fee', component: () => import('@/components/dailyProcess/fee/fee')},
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
