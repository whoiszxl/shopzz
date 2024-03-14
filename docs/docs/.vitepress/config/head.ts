import type { HeadConfig } from 'vitepress'
import { metaData } from './constants'

export const head: HeadConfig[] = [
  ['link', { rel: 'icon', href: '/favicon.ico' }],
  ['meta', { name: 'author', content: 'whoiszxl' }],
  ['meta', { name: 'keywords', content: 'taowu' }],

  ['meta', { name: 'HandheldFriendly', content: 'True' }],
  ['meta', { name: 'MobileOptimized', content: '320' }],
  ['meta', { name: 'theme-color', content: '#3c8772' }],

  ['meta', { property: 'og:type', content: 'website' }],
  ['meta', { property: 'og:locale', content: metaData.locale }],
  ['meta', { property: 'og:title', content: metaData.title }],
  ['meta', { property: 'og:description', content: metaData.description }],
  ['meta', { property: 'og:site', content: metaData.site }],
  ['meta', { property: 'og:site_name', content: metaData.title }],
  ['meta', { property: 'og:image', content: metaData.image }],

  // 百度统计代码：https://tongji.baidu.com
  ['script', {}, `var _hmt = _hmt || [];
  (function() {
    var hm = document.createElement("script");
    hm.src = "https://hm.baidu.com/hm.js?xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    var s = document.getElementsByTagName("script")[0]; 
    s.parentNode.insertBefore(hm, s);
  })();`],
  // 页面访问量统计
  ['script', {}, `
  window.addEventListener('load', function() {
    let oldHref = document.location.href, bodyDOM = document.querySelector('body');
    const observer = new MutationObserver(function(mutations) {
      if (oldHref != document.location.href) {
        oldHref = document.location.href;
        getPv()
        window.requestAnimationFrame(function() {
          let tmp = document.querySelector('body');
          if(tmp != bodyDOM) {
            bodyDOM = tmp;
            observer.observe(bodyDOM, config);
          }
        })
      }
    });
    const config = {
      childList: true,
      subtree: true
    };
    observer.observe(bodyDOM, config);
    getPv()
  }, true);

  function getPv() {
    xhr = new XMLHttpRequest();
    xhr.open('GET', 'https://api.whoiszxl.top/blog/pv?pageUrl=' + location.href);
    xhr.send();
  }`]
]