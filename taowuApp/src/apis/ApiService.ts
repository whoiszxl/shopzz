import axios, { AxiosInstance, AxiosResponse } from 'axios';
import apis from "./apis";
import { METHODS, PARAM_TYPE } from './methods';
import StorageUtil from '../utils/StorageUtil';
import { CommonConstant } from '../common/CommonConstant';
import FormData from 'form-data';
import { Platform } from 'react-native';


// Api http请求服务
class ApiService {

  private axiosInstance: AxiosInstance;

  // 接口base路径
  private baseURL = 'http://q3zdir.natappfree.cc/';

  constructor() {
    // 创建axios实例，指定接口base路径以及超时时间
    this.axiosInstance = axios.create({
        baseURL: this.baseURL,
        timeout: 5000
    });

    // 请求拦截器，在请求接口前带上本地token
    this.axiosInstance.interceptors.request.use(async (config) => {
        // 在发起请求时，从本地缓存中获取并设置 token
        const token = await StorageUtil.getItem(CommonConstant.TOKEN);
        if (token) {
          config.headers['Authorization'] = 'Bearer ' + token;
        }
        return config;
      },

      (error: any) => {
        return Promise.reject(error);
      }
    );

    // 响应拦截器，对接口返回的数据做初步处理，如数据转换等。
    this.axiosInstance.interceptors.response.use((response: AxiosResponse) => {
        return response;
      },
      (error: any) => {
        const { response } = error;
        if(response) {
            const { status } = response;
            if(status === 401) {
                //TODO 鉴权失败
            }
        }

        return Promise.reject(error);
      }
    );
  }

  request(key: string, params?: any): Promise<AxiosResponse<any, any>> {
    const api = (apis as any)[key];
    const {url, method, paramType} = api;

    var finalUrl = url;
    if(paramType === PARAM_TYPE.PATH) {
      finalUrl = url + params;
    }

    if(method === METHODS.GET) {
        if(paramType === PARAM_TYPE.PATH) {
          return this.get(finalUrl, null);
        }
        return this.get(finalUrl, params);
    }

    if(method === METHODS.POST) {
        return this.post(finalUrl, params);
    }

    if(method === METHODS.DELETE) {
        return this.delete(finalUrl, params);
    }

    return this.post(finalUrl, params);
  }


  // 发起 GET 请求
  get(url: string, params?: any): Promise<AxiosResponse<any, any>> { 
    return this.axiosInstance.get(url, {params: params});
  }

  // 发起 POST 请求
  post(url: string, params?: any): Promise<AxiosResponse<any, any>> {
    return this.axiosInstance.post(url, params);
  }

    // 发起 DELETE 请求
  delete(url: string, params?: any): Promise<AxiosResponse<any, any>> {
    return this.axiosInstance.delete(url, params);
  }

  // 发起其他类型的请求（PUT 等）
  // ...


  //发起文件上传的请求
  upload(url: string, uri: string, fileName: string, fileType: string): Promise<AxiosResponse<any, any>> {
    const formData = new FormData();
    formData.append('file', {
      uri: Platform.OS === 'android' ? uri : uri.replace('file://', ''),
      name: encodeURI(fileName),
      type: fileType
    });

    return this.axiosInstance.post(url, formData, {
      headers: {
        'Content-Type': 'multipart/form-data;charset=utf-8',
      }
    });
  } 

}

export default new ApiService();
