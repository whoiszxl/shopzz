import { createApp } from 'vue'
import {
  ElButton,
  ElContainer,
  ElAside,
  ElMenu,
  ElSubmenu,
  ElMenuItemGroup,
  ElMenuItem,
  ElForm,
  ElFormItem,
  ElInput,
  ElCheckbox
} from 'element-plus'
import App from './App.vue'
import router from '@/router'

import '~/theme/index.css'

const app = createApp(App) // 生成 Vue 实例 app

app.use(router) // 引用路由实例

app.use(ElButton)
  .use(ElContainer)
  .use(ElAside)
  .use(ElMenu)
  .use(ElSubmenu)
  .use(ElMenuItemGroup)
  .use(ElMenuItem)
  .use(ElForm)
  .use(ElFormItem)
  .use(ElCheckbox)
  .use(ElInput)

app.mount('#app') // 挂载到 #app
