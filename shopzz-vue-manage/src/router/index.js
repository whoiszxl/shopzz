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

import AttributeList from '@/views/product/AttributeList.vue'
import SpuList from '@/views/product/SpuList.vue'
import SkuList from '@/views/product/SkuList.vue'
import SpuAdd from '@/views/product/SpuAdd.vue'
import AttributeDetail from '@/views/product/AttributeDetail.vue'

import CategoryList from '@/views/product/CategoryList.vue'

import FileList from '@/views/system/FileList.vue'


import BannerList from '@/views/promotion/BannerList.vue'
import BannerAdd from '@/views/promotion/BannerAdd.vue'

import ColumnList from '@/views/promotion/ColumnList.vue'
import ColumnAdd from '@/views/promotion/ColumnAdd.vue'


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




    {path: '/attributeList', name: 'AttributeList', component: AttributeList},
    {path: '/attributeDetail', name: 'AttributeDetail', component: AttributeDetail},

    {path: '/categoryList', name: 'CategoryList', component: CategoryList, children: [
      {path: '/categoryList2', name: 'CategoryList2', component: CategoryList},
      {path: '/categoryList3', name: 'CategoryList3', component: CategoryList}
    ]},

    {path: '/spuList', name: 'SpuList', component: SpuList},
    {path: '/spu/add', name: 'SpuAdd', component: SpuAdd},
    {path: '/skuList', name: 'SkuList', component: SkuList},


    {path: '/fileList', name: 'FileList', component: FileList},

    {path: '/bannerList', name: 'BannerList', component: BannerList},
    {path: '/banner/add', name: 'BannerAdd', component: BannerAdd},

    {path: '/columnList', name: 'ColumnList', component: ColumnList},
    {path: '/column/add', name: 'ColumnAdd', component: ColumnAdd},


  ]
})

export default router