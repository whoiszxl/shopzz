<template>
  <div class="main-container-tag">
    <!-- 头部 -->
    <div class="tag-header-wrapper">
      <span class="tag-breadcrumb-icon">
        <svg role="img" viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="1em" height="1em" class="larkui-icon icon-svg index-module_size_wVASz" style="width: 16px; min-width: 16px; height: 16px;"><defs></defs><path d="M527.744 32c20.8 0.192 40.32 8.32 55.04 23.04l386.112 386.24c14.912 14.848 23.104 34.56 23.104 55.68 0 20.992-8.192 40.704-23.04 55.552l-416.512 416.512c-14.784 14.784-34.624 22.976-55.68 22.976a78.08 78.08 0 0 1-55.616-23.04L55.104 582.784A78.272 78.272 0 0 1 32 527.552V110.72C32 67.2 67.2 32 110.72 32h417.024zM267.136 267.136a128.064 128.064 0 1 0 181.184 181.12 128.064 128.064 0 0 0-181.184-181.12z"></path></svg>
      </span>
      <span class="tag-breadcrumb-item">我的标签</span>
    </div>

    <!-- 内容 -->
    <div>
      <!-- 标签云 -->
      <WordCloud :dataList="dataList" :style="{ width: '100%', height: '130px' }" />
      <a-row :gutter="24">
        <!-- 标签列表区域 -->
        <a-col :span="24">
          <a-card :style="{ width: '100%', marginBottom: '20px' }">
            <a-tag
              @click="toggleTag(tagTitle)"
              v-for="(tag, tagTitle) in tags"
              :class="selectTag === tagTitle ? 'tag-checkable-checked tag-item' : 'tag-item'"
            >
              <span class="tag-title">{{ tagTitle }}</span>
              <span>{{ tag.length }}</span>
            </a-tag>
          </a-card>
        </a-col>

        <!-- 文章列表区域 -->
        <a-col :span="24">
          <a-list v-if="selectTag" :style="{ width: '100%' }">
            <template #header>
              共 {{ tags[selectTag].length }} 篇文章
            </template>
            <a-list-item v-for="(article, index) in tags[selectTag]">
              <div class="result-item">
                <h3 class="result-item-title">
                  <a :href="article.path" class="title" target="_blank">{{ article.title }}</a>
                </h3>
                <p class="result-item-description"></p>
                <!-- 文章元数据信息 -->
                <ArticleMetadata :article="article" :key="md5(article.date)" />
              </div>
            </a-list-item>
          </a-list>
          <a-card
            :style="{ width: '100%' }"
            class="no-result"
            v-if="!selectTag">
            <svg role="img" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" viewBox="0 0 480 480"><defs><linearGradient id="a" x1="1.128" y1="0.988" x2="0.364" y2="1" gradientUnits="objectBoundingBox"><stop offset="0" stop-color="#e0e5ef" stop-opacity="0"/><stop offset="1" stop-color="#e0e5ef"/></linearGradient><linearGradient id="c" x1="1" y1="0.5" x2="0.112" y2="1.125" gradientUnits="objectBoundingBox"><stop offset="0" stop-color="#fff" stop-opacity="0"/><stop offset="1" stop-color="#747f95"/></linearGradient><linearGradient id="d" x1="-0.392" y1="1.114" x2="0.5" y2="0.396" gradientUnits="objectBoundingBox"><stop offset="0" stop-color="#fff" stop-opacity="0"/><stop offset="1" stop-color="#ebedf5"/></linearGradient><linearGradient id="e" x1="-0.906" y1="1.646" x2="0.636" y2="0.061" xlink:href="#d"/><linearGradient id="f" x1="-0.109" y1="1.931" x2="0.736" y2="0.141" xlink:href="#d"/></defs><g transform="translate(-135 -375)"><circle cx="184" cy="184" r="184" transform="translate(191 443)" fill="#f3f3fa"/><path d="M2925,350h0c-8.837,0-16-32.235-16-72s7.163-72,16-72c.038,0,11.813.471,18.75-7.529s9-14.486,9-24.469c0-34.257,14.681-58.6,28.25-63.313,3.909-.688,10,.818,16-4.354s8-9.372,8-16.333c0-37.555,12.536-68,28-68s28,30.445,28,68c0,6.961-.667,10.328,5.333,15.5s14.76,4.5,18.667,5.187c13.569,4.714,24,33.055,24,67.312a101.212,101.212,0,0,0,2.333,20s4.485,11.842,11,5.5,9.13-14.885,10.25-22.871C3135.767,157.923,3142.61,142,3149,142c6.519,0,12.127,16.566,14.645,40.566.741,7.066,2.2,11.743,6.521,17.6A14.3,14.3,0,0,0,3180.92,206H3181c6.488,0,12.073,16.409,14.617,40.308.5,4.725.982,7.6,5.3,11.527S3212.884,262,3212.884,262l.116,0c2.16,0,4.255,1.8,6.228,5.344a58.6,58.6,0,0,1,5.086,14.573C3227.336,294.758,3229,311.835,3229,330c0,6.817-.237,13.546-.7,20H2925Zm303.3,0h0Z" transform="translate(-2718 397)" fill="url(#a)"/><path d="M117,208H.7c-.466-6.453-.7-13.181-.7-20,0-18.163,1.664-35.24,4.686-48.083a58.6,58.6,0,0,1,5.086-14.573C11.745,121.8,13.84,120,16,120l.116,0s7.651-.242,11.967-4.166,4.8-6.8,5.3-11.527C35.927,80.408,41.513,64,48,64a16.6,16.6,0,0,0,3.3-1.014A6.153,6.153,0,0,0,53.365,61.5c6.515-6.342,9.13-14.884,10.25-22.871C66.8,15.924,73.642,0,80.032,0,86.55,0,92.158,16.566,94.676,40.567c.742,7.065,2.2,11.742,6.521,17.6A14.3,14.3,0,0,0,111.951,64h.081c6.487,0,12.073,16.409,14.617,40.307.5,4.725.983,7.6,5.3,11.527S143.915,120,143.915,120l.116,0c2.16,0,4.255,1.8,6.228,5.344a58.6,58.6,0,0,1,5.086,14.573c3.022,12.844,4.686,29.921,4.686,48.083,0,6.818-.237,13.546-.7,20H117Zm42.328,0h0ZM.7,208h0Z" transform="translate(350.969 539)" fill="url(#a)"/><path d="M2989,62c-10.838-4.087-16.3,0-32,0-26.51,0-48-8.954-48-20s21.49-20,48-20h256a16,16,0,1,1,0,32s-15.5,0-27.5,3S3165,68.714,3165,68.714,3127.392,110,3081,110c-38.041,0-70.176-13.246-80.647-31.653C2998.219,74.6,2999.838,66.087,2989,62Z" transform="translate(-2702 701)" fill="#d1d6e2"/><path d="M-2493,98s-56.355,45.651-64,16,74.25-17.75-16,72" transform="translate(3044 409)" fill="none" stroke="#909aa9" stroke-linecap="round" stroke-width="2" stroke-dasharray="10"/><path d="M4,2.2C7.15-.75,16,0,16,0s-1.5,4-2.6,8-.232,5.942-1.8,8C7.6,21.25,0,21,0,21s.75-3.4,2-8S.85,5.15,4,2.2Z" transform="translate(447 603.085)" fill="#909aa9"/><ellipse cx="10" cy="4" rx="10" ry="4" transform="translate(294 787)" fill="url(#c)"/><path d="M8.44,24s8.115-6,6.94-10S11.51,9.625,9.775,6.125A11.222,11.222,0,0,1,8.44,0S1.767,2.625,1.5,9.375C1.38,12.419,4.436,14.344,6.171,18A32.451,32.451,0,0,1,8.44,24Z" transform="translate(287 794.497) rotate(-90)" fill="#909aa9"/><path d="M0,0,57,4.5,136,0l31.5,12,17,10-37,8.5-24.5-5-58,5L4,23Z" transform="translate(191 699)" fill="#fff"/><path d="M-1.4,1.2,60,9l58.75-5.25L143,9l36-9V24.5L144.4,29l-16.2-7.25L95.6,23l-5.1,1.5L67.2,21.75,5,23.25S2.8,16.713,1.2,11.2-1.4,1.2-1.4,1.2Z" transform="translate(196 720)" fill="#eceff5"/><ellipse cx="43" cy="9.5" rx="43" ry="9.5" transform="translate(253 701)" fill="#ebedf5"/><g transform="translate(63 354)"><g transform="translate(258.49 305.55)"><path d="M525.021,66.584a31.23,31.23,0,0,1,7.085,10.425c2.772,6.6,5.877,13.459,8.386,14.78s3.695,10.033-8.053,8.185S525.021,66.584,525.021,66.584Z" transform="translate(-524.241 -66.584)" fill="#fff"/><path d="M525.494,68.3a32.341,32.341,0,0,1,6.953,16.851c.847,8.628,2.933,13.332,5.151,13.016a12.659,12.659,0,0,1-5.991-.025C528.092,97.37,524.074,68.412,525.494,68.3Z" transform="translate(-523.763 -65.64)" fill="url(#d)"/></g><path d="M537.949,131.675a34.415,34.415,0,0,0,14.137,1.09c6.9-.975,8.727-13.747-.647-15.059-7.267-1.02-6.026-12.167-7.366-22.433s-6.56-18.848-7.364-23.026,4.251-9.233,3.614-18.062c-.652-9.065-6.3-10.479-8.307-10.074s-3.609,2.392-6.154,3.47-6.292-.673-11.112,1.619-9.377,7.547-9.377,7.547c-2.009,2.561.4,10.648-.938,14.691s-6.694,39.223-6.56,49.062,6.426,16.715,19.952,18.467,19.419-.606,19.856-4.448c.279-2.443,1.905-11.053-7.8-12.535-4.83-.74-7.363-1.347-7.363-1.347" transform="translate(-279.872 225.445)" fill="#fff"/><path d="M519.206,44.961s.961-1.578,1.726-1.594c1.313-.026,2.7,1.631,2.7,1.631S519.249,46.731,519.206,44.961Z" transform="translate(-268.363 226.187)" fill="#757f95"/><path d="M522.077,37.922c-2.054-.536-2.278,2.085-2.278,2.085s-2.89-.313-2.6,1.743c.357,2.566,5.831,2.443,5.831,2.443S524.583,38.578,522.077,37.922Z" transform="translate(-269.464 223.151)" fill="#757f95"/><path d="M505.743,52.715s-6.088-1.338-6.755,3.318,4.181,7.509,7.656.6" transform="translate(-279.292 231.235)" fill="#fff"/><path d="M503.084,74.624s-1.45,17.9,1.1,22.385c2.3,4.044,10.662,5.138,16.755,4.63a25.038,25.038,0,0,0,6.013-1.246c6.068-2.157,2.831-6.2,0-8.893s-3.738-10.346-8.593-14.5" transform="translate(-276.501 243.626)" fill="url(#e)"/><path d="M514.078,48.635a.6.6,0,0,0-.522.31v0l-.009.014a4.814,4.814,0,0,1-3.228,2.322l-.019,0,0,0a.6.6,0,0,0-.509.5l-.011,0-.406,1.078s.341-.014.842-.088l.057-.307.11-.584v.865c.188-.031.389-.073.6-.121v-.747l.064.454.037.268a5.609,5.609,0,0,0,2.386-1.138,4.083,4.083,0,0,0,1.152-1.977c.04-.155.054-.248.054-.248A.6.6,0,0,0,514.078,48.635Z" transform="translate(-273.668 229.087)" fill="#757f95"/><path d="M531.516,76.393c-3.6-3.507-6.766.555-6.766.555s-6.2-4.888-8.5.26C513.373,83.63,528.051,94,528.051,94S535.2,79.982,531.516,76.393Z" transform="translate(-270.216 243.516)" fill="url(#f)"/><path d="M504.118,75.051s5.02,15.274,7.571,19.76c3.236,5.688,9.468,8.51,15.533,6.355s2.831-5.527,0-8.223S523.148,81.155,518.293,77" transform="translate(-277.496 242.564)" fill="#fff"/></g><path d="M0,9.833l18-9.5,2.667,4v8.2L13,18,8.167,12.532,0,13.671Z" transform="translate(377 777)" fill="#eceff5"/><path d="M4,3.167,18,0V10l-5,3.167-4.833-4L0,10Z" transform="translate(377 777)" fill="#fff"/><path d="M-.211,18.893,16,12l.246,14.107-2.084,4.646L0,31Z" transform="matrix(1, 0.017, -0.017, 1, 400.376, 734.864)" fill="#eceff5"/><path d="M9.75,12H16l-3.75,7H0Z" transform="translate(400 735)" fill="#fff"/><g transform="translate(447 690)"><path d="M97,0,63.923,4.5,24.316,0,8.523,12,0,22l18.55,8.5,12.283-5,29.079,5,23.488-5,6.467-12.126Z" transform="translate(-1 12)" fill="#fff"/><path d="M81.149.607l-28.1,3.945L26.17,1.9l-11.1,2.655L-2.651-1.333V12.391l17.083,2.276L21.846,11l14.917.632,2.334.759L49.759,11l28.991,1.391s-1.4-1.778,0-4.724A43.992,43.992,0,0,0,81.149.607Z" transform="translate(1.651 35.333)" fill="#eceff5"/></g></g></svg>
            <p>点击上方标签，查看标签下的所有文章</p>
          </a-card>
        </a-col>
      </a-row>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { computed, ref, onMounted } from 'vue'
import md5 from 'blueimp-md5'
import articleData from '../../../../article-data.json'
import { formatDate, getQueryParam } from '../utils.ts'

const tags = computed(() => initTags(articleData))
/**
 * 初始化标签数据
 * {tagTitle1: [article1, article2, ...}
 */
function initTags(articleData) {
  const tags: any = {}
  for (let i = 0; i < articleData.length; i++) {
    const article = articleData[i]
    const articleTags = article.tags
    if (Array.isArray(articleTags)) {
      articleTags.forEach((articleTag) => {
        if (!tags[articleTag]) {
          tags[articleTag] = []
        }
        tags[articleTag].push(article)
        // 文章按发布时间降序排序
        tags[articleTag].sort((a, b) => b.date.localeCompare(a.date))
      })
    }
  }
  return tags
}

// 点击指定Tag后进行选中
let selectTag = ref('')
const toggleTag = (tagTitle: string) => {
  if (selectTag.value && selectTag.value == tagTitle) {
    selectTag.value = null
  } else {
    selectTag.value = tagTitle
  }
}

// 如果URL路径有tag参数, 默认选中指定Tag, 例如: /tags?tag=Git
let tag = getQueryParam('tag')
if (tag && tag.trim() != '') {
  toggleTag(tag)
}

const dataList = computed(() => initWordCloud(tags))
/**
 * 初始化词云数据
 * [{"name": xx, "value": xx}]
 */
function initWordCloud(tags) {
  const dataList = []
  for (let tag in tags.value) {
    dataList.push({"name": tag, "value": tags.value[tag].length})
  }
  return dataList
}
</script>

<style scoped>
/** ---------------Arco样式--------------- */
/** 卡片样式 */
:deep(.arco-card) {
  background: var(--vp-c-bg);
}
:deep(.arco-card-bordered) {
  border: 1px solid var(--vp-c-divider-light);
}
:deep(.arco-card-body) {
  color: var(--vp-c-text-1);
}

/** 列表样式 */
:deep(.arco-list) {
  color: var(--vp-c-text-1);
}
:deep(.arco-list-bordered) {
  border: 1px solid var(--vp-c-divider-light);
}
:deep(.arco-list-split .arco-list-header) {
  color: var(--vp-c-text-1);
  border-bottom: 1px solid var(--vp-c-divider-light);
}
:deep(.arco-list-split .arco-list-item:not(:last-child)) {
  border-bottom: 1px solid var(--vp-c-divider-light);
}

/** 标签样式 */
:deep(.arco-tag) {
  background-color: var(--vp-c-bg);
}

/** ---------------自定义样式--------------- */
/** 头部样式 */
.main-container-tag .tag-header-wrapper {
  padding: 24px 0;
  margin-bottom: 24px;
  box-shadow: 0 1px 0 0 var(--vp-c-divider-light);
  -webkit-box-shadow: 0 1px 0 0 var(--vp-c-divider-light);
}
.main-container-tag .tag-header-wrapper .tag-breadcrumb-icon {
  position: relative;
  display: inline-block;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: 1px solid var(--vp-c-divider);
  vertical-align: middle;
}
.main-container-tag .tag-header-wrapper .tag-breadcrumb-icon .icon-svg {
  position: absolute;
  left: 50%;
  top: 50%;
  margin-left: -8px;
  margin-top: -8px;
  fill: #bec0bf;
}
svg:not(:root) {
  overflow: hidden;
}
.main-container-tag .tag-header-wrapper .tag-breadcrumb-item {
  vertical-align: middle;
  display: inline-block;
  font-size: 16px;
  margin-left: 16px;
}

/** 标签样式 */
.main-container-tag .tag-item {
  color: var(--vp-c-text-1);
  border-radius: 50px;
  line-height: 24px;
  padding: 12px 12px;
  margin: 8px 8px 0 0;
  cursor: pointer;
  border: 1px solid var(--vp-c-divider-light);
}
.main-container-tag .tag-title {
  margin-right: 6px;
  word-break: normal;
  white-space: pre-wrap;
}
.main-container-tag .tag-checkable-checked {
  border-color: #3384f5;
  color: #1672f3;
}
.main-container-tag .card-header {
  color: var(--vp-c-text-1);
  font-size: 16px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}
@media (min-width: 1440px) {
  :deep(.VPDoc:not(.has-sidebar) .content) {
    max-width: 1104px;
  }
}
@media (min-width: 960px) {
  :deep(.VPDoc:not(.has-sidebar) .content) {
    max-width: 1104px;
  }
}
:deep(.content-container) {
  max-width: 1204px;
}

/** 文章列表样式 */
.main-container-tag .no-result {
  text-align: center;
}
.main-container-tag .no-result svg {
  height: 300px;
  width: 300px;
  margin: 40px auto 20px;
}
.result-item-title {
  margin: 0;
  font-size: 16px;
  font-weight: 400;
  line-height: 22px;
}
.result-item-description {
  word-wrap: break-word;
  line-height: 22px;
  font-size: 14px;
  margin: 8px 0;
}

.vp-doc a {
  font-weight: 400;
  font-size: 14px;
  color: var(--vp-c-text-1);
}
.vp-doc a:hover {
  color: var(--vp-c-brand);
}
.meta-content a {
  font-size: 14px;
  color: var(--vp-c-text-2);
}
</style>