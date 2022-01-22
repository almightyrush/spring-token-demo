import DashboardLayout from '../layout/DashboardLayout.vue'
// GeneralViews
import NotFound from '../pages/NotFoundPage.vue'

// Admin pages
import Overview from 'src/pages/Overview.vue'
import BlockLicense from 'src/pages/BlockLicense.vue'
import UserProfile from 'src/pages/UserProfile.vue'
import TableList from 'src/pages/TableList.vue'
import Restaurant from 'src/pages/RestaurantProfile.vue'
import Login from 'src/pages/Login.vue'
import PinChange from 'src/pages/PinChange.vue'
import RestaurantList from 'src/pages/RestaurantList.vue'
import UserList from 'src/pages/UserList.vue'

const routes = [
  // {
  //   path: '/',
  //   component: DashboardLayout,
  //   redirect: '/scanner/overview'
  // },
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/scanner',
    component: DashboardLayout,
    redirect: '/scanner/overview',
    children: [
      {
        path: 'overview',
        name: 'Overview',
        component: Overview
      },
      {
        path: 'user',
        name: 'User',
        component: UserProfile
      },
      {
        path: 'hotel',
        name: 'Restaurant',
        component: Restaurant
      },
      {
        path: 'block-license',
        name: 'BlockLicense',
        component: BlockLicense
      },
      {
        path: 'table-list',
        name: 'Table List',
        component: TableList
      },
      {
        path: 'pin-change',
        name: 'Pin Change',
        component: PinChange
      },
      {
        path: 'restaurants',
        name: 'Restaurants',
        component: RestaurantList
      },
      {
        path: 'users',
        name: 'Users',
        component: UserList
      },
    ]
  },
  { path: '*', component: NotFound }
]

/**
 * Asynchronously load view (Webpack Lazy loading compatible)
 * The specified component must be inside the Views folder
 * @param  {string} name  the filename (basename) of the view to load.
function view(name) {
   var res= require('../components/Dashboard/Views/' + name + '.vue');
   return res;
};**/

export default routes
