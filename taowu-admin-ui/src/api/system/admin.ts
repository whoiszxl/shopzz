import axios from 'axios';
import query from 'query-string';
import { getCurrentInstance } from 'vue';

const BASE_URL = '/admin/system/admin';

export interface AdminResponse {
    id?: number;
    username: string;
    avatar?: string;
    realName: string;
    gender?: number;
    mobile?: string;
    email?: string;
    googleStatus?: number;
    lastLoginTime?: string;
    ip?: string;
    status?: number;
    location?: string;
    browser?: string;
    permissions?: Array<string>;
    roles?: Array<string>;
    roleIds?: Array<number>;

    createdBy?: string;
    updatedBy?: string;
    createdAt?: string;
    updatedAt?: string;
}

// 管理员添加命令
export interface AdminSaveCommand {
    id?: number;
    username: string;
    password: string;
    avatar?: string;
    realName: string;
    gender?: number;
    mobile: string;
    email: string;
    roles: Array<string>;
    roleIds: Array<number>;
}

export interface AdminParam {
    username?: string;
    status?: number;
    startCreatedAt?: string;
    endCreatedAt?: string;
    page?: number,
    size?:number,
    sort?:Array<string>;
}

export interface UpdateAdminRoleCommand {
    id: number,
    roleIds?: Array<number>;
}

export interface AdminResponseList {
    list: AdminResponse[];
    total: number;
}

// 通过id获取管理员详情信息
export function getAdmin(id: number) {
    return axios.get<AdminResponse>(`${BASE_URL}/detail/${id}`);
}

// 通过AdminParam参数获取分页列表
export function pageAdmin(params: AdminParam) {
    return axios.get<AdminResponseList>(`${BASE_URL}/page`, {
        params, paramsSerializer: (obj) => {
            return query.stringify(obj);
        }
    })
}

// 添加管理员
export function addAdmin(params: AdminSaveCommand) {
    return axios.post(`${BASE_URL}/add`, params);
}

// 更新管理员
export function updateAdmin(params: AdminSaveCommand) {
    return axios.put(`${BASE_URL}/update`, params);
}

// 切换管理员状态
export function switchAdminStatus(id: number) {
    return axios.patch(`${BASE_URL}/switch/status/${id}`);
}

// 批量删除管理员
export function deleteAdmin(ids: number | Array<number>) {
    return axios.delete(`${BASE_URL}/delete/${ids}`);
}

// 更新管理员的角色
export function updateAdminRole(params: UpdateAdminRoleCommand) {
    return axios.patch(`${BASE_URL}/update/role`, params);
}

// 重置管理员密码
export function resetPassword(id: number) {
    return axios.patch(`${BASE_URL}/reset/password/${id}`);
}