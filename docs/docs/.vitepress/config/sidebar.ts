import DefaultTheme from 'vitepress/theme'
import fg from 'fast-glob';

import matter from 'gray-matter'

const sync = fg.sync;

export const sidebar: DefaultTheme.Config['sidebar'] = {

  '/taowu': getItems("taowu"),
}


function getItems (path: string) {
  let groups: DefaultTheme.SidebarGroup[] = []
  let items: DefaultTheme.SidebarItem[] = []
  let total = 0
  const groupCollapsedSize = 2
  const titleCollapsedSize = 20

  sync(`docs/${path}/*`, {
    onlyDirectories: true,
    objectMode: true
  }).forEach(({ name }) => {
    let groupName = name
    sync(`docs/${path}/${groupName}/*`, {
      onlyFiles: true,
      objectMode: true
    }).forEach((article) => {
      const articleFile = matter.read(`${article.path}`)
      const { data } = articleFile
      items.push({
        text: data.title,
        link: `/${path}/${groupName}/${article.name.replace('.md', '')}`
      })
      total ++
    })

    groups.push({
      text: `${groupName.substring(groupName.indexOf('-') + 1)} (${items.length}篇)`,
      collapsed: items.length < groupCollapsedSize || total > titleCollapsedSize,
      items: items
    })

    items = []
  })

  addOrderNumber(groups)
  return groups
}

function addOrderNumber(groups) {
  for (var i = 0; i < groups.length; i++) {
    for (var j = 0; j < groups[i].items.length; j++) {
      var items = groups[i].items
      items[j].text = `[${j + 1}] ${items[j].text}`
    }
  }
}