import axios from 'axios';
import query from 'query-string';
import {DictResponseState} from "@/store/modules/dict/types";

const BASE_URL = '/admin/system/role';


export interface RoleParam {
    name?: string;
    status?: number;
    page?: number;
    size?: number;
    sort?: Array<string>;
}

export interface RoleResponse {
    id?: number;
    name: string;
    code?: string;
    description?: string;
    menuIds?: Array<string>;
    status?: number;
    createdBy?: string;
    updatedBy?: string;
    createdAt?: string;
    updatedAt?: string;
}

export interface RoleSaveCommand {
    id?: number;
    name: string;
    code: string;
    description?: string;
    menuIds: Array<number>;
}

export interface RoleResponseList {
    list: RoleResponse[];
    total: number;
}


// 通过RoleParam参数获取分页列表
export function pageRole(params: RoleParam) {
    return axios.get<RoleResponseList>(`${BASE_URL}/page`, {
        params, paramsSerializer: (obj) => {
            return query.stringify(obj);
        }
    })
}

// 通过id获取角色信息
export function getRole(id: number) {
    return axios.get<RoleResponse>(`${BASE_URL}/detail/${id}`);
}

// 添加角色
export function addRole(params: RoleSaveCommand) {
    return axios.post(`${BASE_URL}/add`, params);
}

// 更新角色
export function updateRole(params: RoleSaveCommand) {
    return axios.put(`${BASE_URL}/update`, params);
}

// 批量删除角色
export function deleteRole(ids: number | Array<number>) {
    return axios.delete(`${BASE_URL}/delete/${ids}`);
}

// 切换角色状态
export function switchRoleStatus(id: number) {
    return axios.patch(`${BASE_URL}/switch/status/${id}`);
}


export function listRoleDict(params: RoleParam) {
    return axios.get<DictResponseState[]>(`${BASE_URL}/dict`, {
        params,
        paramsSerializer: (obj) => {
            return query.stringify(obj);
        },
    });
}