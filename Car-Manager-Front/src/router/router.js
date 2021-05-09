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

        {path: '/index', component: () => import('@/components/index')},

        {path: '/roleManager', component: () => import('@/components/role/roleManager.vue')},
        {path: '/userManager', component: () => import('@/components/role/userManager.vue')},
        {path: '/changePassword', component: () => import('@/components/role/changePassword.vue')},

        {path: '/car', component: () => import('@/components/baseSetting/car/car')},
        {path: '/opposite', component: () => import('@/components/baseSetting/opposite/oppositeCompany')},
        {path: '/driver', component: () => import('@/components/baseSetting/driver/driver')},
        {path: '/dictionary', component: () => import('@/components/baseSetting/dictionary/dictionary.vue')},

        {path: '/repairRecord', component: () => import('@/components/dailyProcess/repairRecord/repairRecord')},
        {path: '/oilrecord', component: () => import('@/components/dailyProcess/oilRecord/oilRecord')},
        {path: '/fee', component: () => import('@/components/dailyProcess/feeRecord/feeRecord')},
        {path: '/keepRecord', component: () => import('@/components/dailyProcess/keepRecord/keepRecord')},
        {path: '/violationRecord', component: () => import('@/components/dailyProcess/violationRecord/violationRecord')},
        {path: '/accidentRecord', component: () => import('@/components/dailyProcess/accidentRecord/accidentRecord')},
        {path: '/departureRecord', component: () => import('@/components/dailyProcess/departureRecord/departureRecord')},
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
