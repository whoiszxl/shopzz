import axios from 'axios';
import { MemberState } from '@/store/modules/login/types';

export interface LoginReq {
    username: string;
    password: string;
    captcha: string;
    uuid: string;
}

export interface LoginRes {
    data: string;
}

export function login(req: LoginReq) {
    return axios.post(`/admin/member/login`, req);
}

export function adminLogout() {
    return axios.post(`/admin/member/logout`);
}

export function getAdminDetail() {
    return axios.get<MemberState>("/admin/member/detail");
}