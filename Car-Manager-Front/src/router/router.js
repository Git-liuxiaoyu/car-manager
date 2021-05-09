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

        {path: '/roleManager', component: () => import('@/components/role/roleManager')},
        {path: '/userManager', component: () => import('@/components/role/userManager')},
        {path: '/changePassword', component: () => import('@/components/role/changePassword')},

        {path: '/car', component: () => import('@/components/baseSetting/car/car')},
        {path: '/opposite', component: () => import('@/components/baseSetting/opposite/oppositeCompany')},
        {path: '/driver', component: () => import('@/components/baseSetting/driver/driver')},
        {path: '/dictionary', component: () => import('@/components/baseSetting/dictionary/dictionary')},

        {path: '/repairRecord', component: () => import('@/components/dailyProcess/repairRecord/repairRecord')},
        {path: '/oilRecord', component: () => import('@/components/dailyProcess/oilRecord/oilRecord')},
        {path: '/feeRecord', component: () => import('@/components/dailyProcess/feeRecord/feeRecord')},
        {path: '/keepRecord', component: () => import('@/components/dailyProcess/keepRecord/keepRecord')},
        {path: '/violationRecord', component: () => import('@/components/dailyProcess/violationRecord/violationRecord')},
        {path: '/accidentRecord', component: () => import('@/components/dailyProcess/accidentRecord/accidentRecord')},
        {path: '/departureRecord', component: () => import('@/components/dailyProcess/departureRecord/departureRecord')},
        {path: '/yearCheckRecord', component: () => import('@/components/dailyProcess/yearCheckRecord/yearCheckRecord')},
        {path: '/violationRecord', component: () => import('@/components/dailyProcess/violationRecord/violationRecord')},
        {path: '/accidentRecord', component: () => import('@/components/dailyProcess/accidentRecord/accidentRecord')},
        {path: '/insureRecord', component: () => import('@/components/dailyProcess/insureRecord/insureRecord')},
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
