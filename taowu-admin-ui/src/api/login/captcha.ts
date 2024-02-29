import axios from 'axios';

export interface ImageCaptchaRes {
  uuid: string;
  captcha: string;
}
export function getImageCaptcha() {
  return axios.get<ImageCaptchaRes>('/admin/captcha/image');
}
