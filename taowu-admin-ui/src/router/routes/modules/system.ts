import { DEFAULT_LAYOUT } from '../base';
import { AppRouteRecordRaw } from '../types';

const LIST: AppRouteRecordRaw = {
  path: '/system',
  name: 'system',
  component: DEFAULT_LAYOUT,
  meta: {
    locale: 'menu.system',
    requiresAuth: true,
    icon: 'icon-list',
    order: 0,
  },
  children: [
    {
      path: 'workplace',
      name: 'Workplace',
      component: () => import('@/views/dashboard/workplace/index.vue'),
      meta: {
        locale: 'menu.dashboard.workplace',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    
    {
      path: 'system-admin', // The midline path complies with SEO specifications
      name: 'Admin',
      component: () => import('@/views/system/admin/index.vue'),
      meta: {
        locale: 'menu.system.admin.list',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: 'system-role',
      name: 'Role',
      component: () => import('@/views/system/role/index.vue'),
      meta: {
        locale: 'menu.system.role.list',
        requiresAuth: true,
        roles: ['*'],
      },
    },
    {
      path: 'system-menu',
      name: 'Menu',
      component: () => import('@/views/system/menu/index.vue'),
      meta: {
        locale: 'menu.system.menu.list',
        requiresAuth: true,
        roles: ['*'],
      },
    },
  ],
};

export default LIST;
