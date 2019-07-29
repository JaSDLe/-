import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/components/Home'
import Login from '@/components/Login'
import Welcome from '@/components/Welcome'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },{
      path:'/home',
      name:'home',
      component:Home,
      meta: {
        requireAuth: true
      }
    },{
      path:'/welcome',
      name:'welcome',
      component:Welcome,
      meta: {
        requireAuth: true
      }
    }
  ]
})
