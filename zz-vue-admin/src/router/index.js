import { createRouter, createWebHashHistory } from 'vue-router'
import Index from '@/views/Index.vue'
import Login from '@/views/Login.vue'
import SysAdmin from '@/views/SysAdmin.vue'
import SysAdminAdd from '@/views/SysAdminAdd.vue'

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {path: '/', name: 'index', component: Index},
        {path: '/login', name: 'login', component: Login},
        {path: '/sysAdmin', name: 'SysAdmin', component: SysAdmin},
        {path: '/sysAdmin/add', name: 'SysAdminAdd', component: SysAdminAdd},
    ]
})

export default router