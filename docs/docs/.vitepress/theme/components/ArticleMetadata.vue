<template>
  <div class="meta-wrapper">
    <div class="meta-item original">
      <a-tag v-if="isOriginal" color="orangered" title="原创文章">
        <template #icon>
          <icon-trophy />
        </template>
        原创
      </a-tag>
      <a-tag v-else color="arcoblue" title="转载文章">
        <template #icon>
          <icon-share-alt />
        </template>
        转载
      </a-tag>
    </div>
    <div class="meta-item">
      <span class="meta-icon author">
        <svg role="img" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
          <title>原创作者</title>
          <path d="M858.5 763.6c-18.9-44.8-46.1-85-80.6-119.5-34.5-34.5-74.7-61.6-119.5-80.6-0.4-0.2-0.8-0.3-1.2-0.5C719.5 518 760 444.7 760 362c0-137-111-248-248-248S264 225 264 362c0 82.7 40.5 156 102.8 201.1-0.4 0.2-0.8 0.3-1.2 0.5-44.8 18.9-85 46-119.5 80.6-34.5 34.5-61.6 74.7-80.6 119.5C146.9 807.5 137 854 136 901.8c-0.1 4.5 3.5 8.2 8 8.2h60c4.4 0 7.9-3.5 8-7.8 2-77.2 33-149.5 87.8-204.3 56.7-56.7 132-87.9 212.2-87.9s155.5 31.2 212.2 87.9C779 752.7 810 825 812 902.2c0.1 4.4 3.6 7.8 8 7.8h60c4.5 0 8.1-3.7 8-8.2-1-47.8-10.9-94.3-29.5-138.2zM512 534c-45.9 0-89.1-17.9-121.6-50.4S340 407.9 340 362c0-45.9 17.9-89.1 50.4-121.6S466.1 190 512 190s89.1 17.9 121.6 50.4S684 316.1 684 362c0 45.9-17.9 89.1-50.4 121.6S557.9 534 512 534z"></path>
        </svg>
      </span>
      <span class="meta-content">
        <a v-if="isOriginal" :href="authorLink" title="进入作者主页">{{ author }}</a>
        <span v-else :title="author">{{ author }}</span>
      </span>
    </div>
    <div class="meta-item">
      <span class="meta-icon date">
        <svg role="img" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg">
          <title v-if="isOriginal">发布时间</title>
          <title v-else>转载时间</title>
          <path d="M512 64C264.6 64 64 264.6 64 512s200.6 448 448 448 448-200.6 448-448S759.4 64 512 64z m0 820c-205.4 0-372-166.6-372-372s166.6-372 372-372 372 166.6 372 372-166.6 372-372 372z"></path><path d="M686.7 638.6L544.1 535.5V288c0-4.4-3.6-8-8-8H488c-4.4 0-8 3.6-8 8v275.4c0 2.6 1.2 5 3.3 6.5l165.4 120.6c3.6 2.6 8.6 1.8 11.2-1.7l28.6-39c2.6-3.7 1.8-8.7-1.8-11.2z"></path>
        </svg>
      </span>
      <time class="meta-content" :datetime="date.toISOString()" :title="dayjs().to(dayjs(date))">{{ date.toLocaleString('zh', {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'}) }}</time>
    </div>
    <div class="meta-item" v-if="showViewCount">
      <span class="meta-icon pv">
        <svg role="img" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"><title>阅读数</title><path d="M942.2 486.2C847.4 286.5 704.1 186 512 186c-192.2 0-335.4 100.5-430.2 300.3-7.7 16.2-7.7 35.2 0 51.5C176.6 737.5 319.9 838 512 838c192.2 0 335.4-100.5 430.2-300.3 7.7-16.2 7.7-35 0-51.5zM512 766c-161.3 0-279.4-81.8-362.7-254C232.6 339.8 350.7 258 512 258c161.3 0 279.4 81.8 362.7 254C791.5 684.2 673.4 766 512 766z"></path><path d="M508 336c-97.2 0-176 78.8-176 176s78.8 176 176 176 176-78.8 176-176-78.8-176-176-176z m0 288c-61.9 0-112-50.1-112-112s50.1-112 112-112 112 50.1 112 112-50.1 112-112 112z"></path></svg>
      </span>
      <span class="meta-content" v-text="viewCount" :title="viewCount"></span>
    </div>
    <div class="meta-item" v-if="showCategory">
      <span class="meta-icon category">
        <svg role="img" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"><title>所属分类</title><path d="M928 444H820V330.4c0-17.7-14.3-32-32-32H473L355.7 186.2a8.15 8.15 0 0 0-5.5-2.2H96c-17.7 0-32 14.3-32 32v592c0 17.7 14.3 32 32 32h698c13 0 24.8-7.9 29.7-20l134-332c1.5-3.8 2.3-7.9 2.3-12 0-17.7-14.3-32-32-32zM136 256h188.5l119.6 114.4H748V444H238c-13 0-24.8 7.9-29.7 20L136 643.2V256z m635.3 512H159l103.3-256h612.4L771.3 768z"></path></svg>
      </span>
      <span class="meta-content">
        <span v-for="(category, index) in categories" :key="index">
          <a href="javascript:void(0);" @click="goToLink('/archives', 'category', category)" target="_self" :title="category">{{ category }}</a>
          <span v-if="index != categories.length - 1">, </span>
        </span>
      </span>
    </div>
    <div class="meta-item tag">
      <span class="meta-icon tag">
        <svg role="img" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg"><title>标签列表</title><path d="M483.2 790.3L861.4 412c1.7-1.7 2.5-4 2.3-6.3l-25.5-301.4c-0.7-7.8-6.8-13.9-14.6-14.6L522.2 64.3c-2.3-0.2-4.7 0.6-6.3 2.3L137.7 444.8a8.03 8.03 0 0 0 0 11.3l334.2 334.2c3.1 3.2 8.2 3.2 11.3 0z m62.6-651.7l224.6 19 19 224.6L477.5 694 233.9 450.5l311.9-311.9z m60.16 186.23a48 48 0 1 0 67.88-67.89 48 48 0 1 0-67.88 67.89zM889.7 539.8l-39.6-39.5a8.03 8.03 0 0 0-11.3 0l-362 361.3-237.6-237a8.03 8.03 0 0 0-11.3 0l-39.6 39.5a8.03 8.03 0 0 0 0 11.3l243.2 242.8 39.6 39.5c3.1 3.1 8.2 3.1 11.3 0l407.3-406.6c3.1-3.1 3.1-8.2 0-11.3z"></path></svg>
      </span>
      <span class="meta-content">
        <span v-for="(tag, index) in tags" :key="index">
          <a href="javascript:void(0);" @click="goToLink('/archives', 'tag', tag)" target="_self" :title="tag">{{ tag }}</a>
          <span v-if="index != tags.length - 1">, </span>
        </span>
      </span>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { reactive, toRefs, onMounted } from 'vue'
import { useData } from 'vitepress'
import md5 from 'blueimp-md5'
import dayjs from 'dayjs'
import 'dayjs/locale/zh-cn'
import relativeTime from 'dayjs/plugin/relativeTime'
import { goToLink } from '../utils.ts'

dayjs.extend(relativeTime)
dayjs.locale('zh-cn')

// 定义文章属性
const props = defineProps({
  article: Object,
  showCategory: {
    type: Boolean,
    default: true
  }
})

// 初始化文章元数据信息
const { theme, page } = useData()
const data = reactive({
  isOriginal: props.article?.isOriginal ?? true,
  author: props.article?.author ?? theme.value.articleMetadataConfig.author,
  authorLink: props.article?.authorLink ?? theme.value.articleMetadataConfig.authorLink,
  showViewCount: theme.value.articleMetadataConfig?.showViewCount ?? false,
  viewCount: 0,
  date: new Date(props.article.date),
  categories: props.article?.categories ?? [],
  tags: props.article?.tags ?? [],
  showCategory: props.showCategory
})
const { isOriginal, author, authorLink, showViewCount, viewCount, date, toDate, categories, tags, showCategory } = toRefs(data)

if (data.showViewCount) {
  // 记录并获取文章阅读数（使用文章标题 + 发布时间生成 MD5 值，作为文章的唯一标识）
  onMounted(() => {
    $api.getArticleViewCount(md5(props.article.title + props.article.date), location.href, function(viewCountData) {
      data.viewCount = viewCountData
    })
  })
}
</script>

<style scoped>
.meta-wrapper {
  margin-top: 10px;
}

.meta-item {
  display: inline-block;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  vertical-align: middle;
  max-width: 240px;
  color: var(--vp-c-text-2);
  cursor: default;
  font-size: 14px;
}
.meta-item:not(.tag) {
  margin-right: 1rem;
}
.meta-item.original {
  margin-right: 0.5rem;
  margin-top: -0.5px;
}
.meta-icon, meta-content {
  display: inline-block;
  margin-right: .375rem;
  vertical-align: middle;
}
.meta-icon {
  position: relative;
  bottom: 1.5px;
}
.meta-icon.author {
  bottom: 1.3px;
}
.meta-icon.date {
  bottom: 1.3px;
}
.meta-icon svg {
  fill: var(--vp-c-text-2);
  height: 16px;
  width: 16px;
}
.meta-content a {
  font-weight: 400;
  color: var(--vp-c-text-2);
}
.meta-content a:hover {
  color: var(--vp-c-brand);
}
</style>