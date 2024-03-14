import glob  from 'fast-glob'
import matter from 'gray-matter'
import fs from 'node:fs/promises'

const articleData = await Promise.all(
  glob.sync('./docs/**/*.md', {
    onlyFiles: true,
    objectMode: true,
    ignore: ['./docs/**/index.md', './docs/**/tags.md', './docs/**/archives.md', './docs/**/me.md'], // without !
  }).map(async (article) => {
    const file = matter.read(`${article.path}`)
    const { data, path } = file
    return {
      ...data,
      path: path.replace(/\.md$/, '').replace('./docs/', '')
    }
  })
)

await fs.writeFile('./article-data.json', JSON.stringify(articleData), 'utf-8')