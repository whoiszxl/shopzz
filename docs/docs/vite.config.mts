import { defineConfig } from 'vite'
//import { SearchPlugin } from 'vitepress-plugin-search'
import Components from 'unplugin-vue-components/vite'
import { ArcoResolver } from 'unplugin-vue-components/resolvers'

export default defineConfig({
  plugins: [
    //SearchPlugin({
      //encode: false,
      //tokenize: 'full'
    //}),
    Components({
      dirs: ['.vitepress/theme/components'],
      include: [/\.vue$/, /\.vue\?vue/, /\.md$/],
      resolvers: [ArcoResolver({ sideEffect: true, resolveIcons: true })]
    })
  ],
  ssr: { noExternal: ['@arco-design/web-vue'] },
  resolve: {
    alias: {
      'mermaid': 'mermaid/dist/mermaid.esm.mjs',
    },
  },
});
