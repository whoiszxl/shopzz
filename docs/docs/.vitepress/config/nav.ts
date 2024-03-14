import DefaultTheme from 'vitepress/theme'

export const nav: DefaultTheme.Config['nav'] = [
  {
    text: '项目文档',
    link: '/taowu/01-快速开始/01-简介',
  },
  {
    text: '我的归档',
    link: '/archives',
    activeMatch: '/archives'
  }
]