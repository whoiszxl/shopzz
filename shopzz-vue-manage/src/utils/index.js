export function localGet (key) {
  const value = window.localStorage.getItem(key)
  try {
    return JSON.parse(window.localStorage.getItem(key))
  } catch (error) {
    return value
  }
}

export function localSet (key, value) {
  window.localStorage.setItem(key, JSON.stringify(value))
}

export function localRemove (key) {
  window.localStorage.removeItem(key)
}

export const pathMap = {
  index: '首页',
  login: '登录',
  SysUser: '管理员管理',
  SysUserAdd: '添加管理员',

  Warehouse: '仓库管理',
  WarehouseAdd: '添加仓库',
  Supplier: '供应商管理',
  SupplierAdd: '添加供应商',
  PurchaseOrder: '采购单管理',
  OutboundSellOrder: '销售出库单管理',
  InboundReturnOrder: '退货入库单管理',
  WarehouseShelf: '货架管理',
  WarehouseSku: '仓库SKU管理'
}