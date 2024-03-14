import DefaultTheme from 'vitepress/theme'
import { nav } from './nav'
import { sidebar } from './sidebar'

export const themeConfig: DefaultTheme.Config = {
  nav, // 导航栏配置
  sidebar, // 侧边栏配置

  logo: '/logo2.png',
  outline: 'deep', // 右侧大纲标题层级
  outlineTitle: '目录', // 右侧大纲标题文本配置
  outlineBadges: false, // 是否在大纲中显示 Badge 文本
  lastUpdatedText: '最后更新', // 最后更新时间文本配置, 需先配置lastUpdated为true
  // 文档页脚文本配置
  docFooter: {
    prev: '上一篇',
    next: '下一篇'
  },
  // 编辑链接配置
  editLink: {
    pattern: '',
    text: ''
  },
  // 搜索配置
  algolia: {
    appId: '',
    apiKey: '',
    indexName: 'whoiszxl',
    placeholder: '请输入关键词',
    buttonText: '搜索',
  },

  socialLinks: [
    { icon: 'github', link: 'https://github.com/whoiszxl/shopzz' }
  ],

  // 元数据配置
  articleMetadataConfig: {
    author: 'whoiszxl',
    authorLink: '/about/me',
    showViewCount: true
  },
  // 版权配置
  copyrightConfig: {
    license: '',
    licenseLink: ''
  },
  // 评论配置
  commentConfig: {
    type: 'gitalk',
    showComment: false
  },
  // 页脚配置
  footerConfig: {
    showFooter: true,
    icpRecordCode: '',
    publicSecurityRecordCode: '',
    copyright: `Copyright © 2018-${new Date().getFullYear()} whoiszxl`
  }
}