// src/router.js
import { createRouter, createWebHashHistory } from 'vue-router';
import HomeView from './views/HomeView.vue';
import DashboardView from './views/DashboardView.vue'


const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    component: DashboardView,
  },
  
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

export default router;
