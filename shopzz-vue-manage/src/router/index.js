import {createRouter, createWebHashHistory} from 'vue-router'
import Index from '@/views/Index.vue'
import AddGood from '@/views/AddGood.vue'
import Login from '@/views/Login.vue'
import SysUser from '@/views/SysUser.vue'
import SysUserAdd from '@/views/SysUserAdd.vue'
import PurchaseOrder from '@/views/PurchaseOrder.vue'
import Supplier from '@/views/Supplier.vue'
import SupplierAdd from '@/views/SupplierAdd.vue'
import Warehouse from '@/views/Warehouse.vue'
import WarehouseAdd from '@/views/WarehouseAdd.vue'
import WarehouseShelf from '@/views/WarehouseShelf.vue'
import WarehouseShelfAdd from '@/views/WarehouseShelfAdd.vue'
import InboundReturnOrder from '@/views/InboundReturnOrder.vue'
import OutboundSellOrder from '@/views/OutboundSellOrder.vue'
import WarehouseSku from '@/views/WarehouseSku.vue'

const router = createRouter({
  history: createWebHashHistory(), // hash 模式
  routes: [
    {
      path: '/',
      name: 'index',
      component: Index
    },
    {
      path: '/add',
      name: 'add',
      component: AddGood
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },

    {
      path: '/sysUser',
      name: 'SysUser',
      component: SysUser
    },

    {
      path: '/sysuser/add',
      name: 'SysUserAdd',
      component: SysUserAdd
    },



    {path: '/supplier', name: 'Supplier', component: Supplier},
    {path: '/supplier/add', name: 'SupplierAdd', component: SupplierAdd},

    {path: '/purchaseOrder', name: 'PurchaseOrder', component: PurchaseOrder},

    {path: '/warehouse', name: 'Warehouse', component: Warehouse},
    {path: '/warehouse/add', name: 'WarehouseAdd', component: WarehouseAdd},

    {path: '/warehouseShelf', name: 'WarehouseShelf', component: WarehouseShelf},
    {path: '/warehouseShelf/add', name: 'WarehouseShelfAdd', component: WarehouseShelfAdd},

    {path: '/inboundReturnOrder', name: 'InboundReturnOrder', component: InboundReturnOrder},
    {path: '/outboundSellOrder', name: 'OutboundSellOrder', component: OutboundSellOrder},

    {path: '/warehouseSku', name: 'WarehouseSku', component: WarehouseSku},

  ]
})

export default router