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
  WarehouseSku: '仓库SKU管理',

  AttributeList: '属性管理',
  AttributeDetail: '属性详情',
  SpuList: 'SPU管理',
  SkuList: 'SKU管理',
  SpuAdd: 'SPU新增',
  CategoryList: '分类管理',
  CategoryList2: '二级分类管理',
  CategoryList3: '三级分类管理',


  FileList: '文件管理',

  BannerList: '轮播图管理',
  BannerAdd: '轮播图添加',
  ColumnList: '专栏管理',
  ColumnAdd: '专栏添加',
  ColumnSpuList: '专栏SPU添加',

  CouponList: '优惠券管理',
  CouponAdd: '优惠券添加',

  
}

// 单张图片上传
export const uploadImgServer = '/api/file/file/upload'
// 多张图片上传
export const uploadImgsServer = '/api/file/file/upload'