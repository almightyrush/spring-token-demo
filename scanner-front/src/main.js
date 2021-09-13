
import Vue from 'vue'
import VueRouter from 'vue-router'
import App from './App.vue'
import API from './api';
import Notifications from 'vue-notification'
import VModal from 'vue-js-modal'
import {ClientTable} from 'vue-tables-2';

// LightBootstrap plugin
import LightBootstrap from './light-bootstrap-main'

// router setup
import routes from './routes/routes'

import './registerServiceWorker'
// plugin setup
Vue.use(VueRouter)
Vue.use(LightBootstrap)
Vue.use(ClientTable);
Vue.prototype.$http = API;
Vue.use(Notifications)
Vue.use(VModal, { componentName: 'MainModal' },)
// configure router
const router = new VueRouter({
  routes, // short for routes: routes
  linkActiveClass: 'nav-item active',
  scrollBehavior: (to) => {
    if (to.hash) {
      return {selector: to.hash}
    } else {
      return { x: 0, y: 0 }
    }
  }
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  router
})
