import { createRouter as createRouter, createWebHistory } from 'vue-router'
import { useStore } from 'vuex'

import HomeView from '../views/HomeView.vue'
import PrivacyPolicyView from '../views/PrivacyPolicyView.vue'
import TermsOfServiceView from '../views/TermsOfServiceView.vue'
import ComplianceView from '../views/ComplianceView.vue'
import LogoutView from '../views/LogoutView.vue'
import AdminView from '@/views/AdminView.vue'
const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
    meta: {
      requiresAuth: false,
    },
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminView,
    meta: {
      requiresAuth: true,
    },
  },
   {
      path: "/logout",
      name: "logout",
      component: LogoutView,
      meta: {
        requiresAuth: false
      }
    },
  { path: '/privacy-policy', component: PrivacyPolicyView },
  { path: '/terms-of-service', component: TermsOfServiceView },
  { path: '/compliance', component: ComplianceView },
]

// Create the router
const router = createRouter({
  history: createWebHistory(),
  routes: routes,
})

router.beforeEach((to) => {
  // Get the Vuex store
  const store = useStore()

  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some((x) => x.meta.requiresAuth)

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    return { name: 'login' }
  }
  // Otherwise, do nothing and they'll go to their next destination
})

export default router
