import axios from 'axios';
import type { AxiosRequestConfig, AxiosResponse } from 'axios';
import { Message, Modal } from '@arco-design/web-vue';
import { useUserStore } from '@/store';
import { getToken } from '@/utils/auth';

export interface HttpResponse<T = unknown> {
  message: string;
  code: number;
  data: T;
}

if (import.meta.env.VITE_API_BASE_URL) {
  axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;
}

axios.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    const token = getToken();
    if (token) {
      if (!config.headers) {
        config.headers = {};
      }
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // do something
    return Promise.reject(error);
  }
);
// add response interceptors
axios.interceptors.response.use(
  (response: AxiosResponse<HttpResponse>) => {
      if (response.request.responseType === 'blob' || response.request.responseType === 'arraybuffer') {
          return response;
      }


      const res = response.data;
    if(res.code === 0) {
      return res;
    }

    Message.error({
      content: res.message,
      duration: 5000
    });

    return Promise.reject(new Error(res.message));
  },
  (error) => {
      console.log(error.response);
        Message.error({
          content: error.response.data.message || '请求异常',
          duration: 5 * 1000,
        });
    return Promise.reject(error);
  }
);
