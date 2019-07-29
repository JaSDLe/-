import Vue from 'vue'
import Vuex from 'vuex'
// import '../lib/sockjs'
// import '../lib/stomp'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: {
      id: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).id,
      name: window.sessionStorage.getItem('user' || '[]') == null ? '未登录' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).name,
      username: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).username,
      roles: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).roles
    },
    routes: []
  },
  mutations: {
    initMenu(state, menus){
      // alert('user:'+JSON.stringify(state));
      state.routes = menus;
      
    },
    login(state, user){
      state.user = user;
      window.sessionStorage.setItem('user', JSON.stringify(user));
    },
    logout(state){
      window.sessionStorage.removeItem('user');
      state.routes = [];
    }
  }
});
