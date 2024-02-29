import axios from 'axios';
import qs from 'query-string';
import { Notification } from '@arco-design/web-vue';
import dayjs from 'dayjs';

/**
 * 下载excel
 * @param url excel文件地址
 * @param params 参数
 * @param fileName 文件名
 */
export default function download(url: string, params: any, fileName: string | undefined) {
    return axios.get(url, {params, paramsSerializer: (obj) => {return qs.stringify(obj)}, responseType: 'blob',})
        .then(async (res) => {
            // 获取文件名
            if (!fileName) {
                const contentDisposition = res.headers['content-disposition'];
                const pattern = new RegExp('filename=([^;]+\\.[^\\.;]+);*');
                const result = pattern.exec(contentDisposition) || '';
                // 对名字进行解码
                fileName = window.decodeURI(result[1]);
            } else {
                fileName = `${fileName}_${dayjs().format('YYYYMMDDHHmmss')}`;
            }

            // 创建下载的链接
            const blob = new Blob([res.data], {type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8'});
            const downloadElement = document.createElement('a');
            const href = window.URL.createObjectURL(blob);
            downloadElement.style.display = 'none';
            downloadElement.href = href;
            downloadElement.download = fileName;
            document.body.appendChild(downloadElement);
            downloadElement.click();
            document.body.removeChild(downloadElement);
            window.URL.revokeObjectURL(href);
        })
        .catch((error) => {
            // 注释掉main.js下的[import './mock';]便可在mock环境下导出
            Notification.warning({
                title: '警告',
                content:"MOCK环境无法导出",
                duration: 10000,
                closable: true,
            });
        });
}

