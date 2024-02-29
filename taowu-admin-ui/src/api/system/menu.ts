import axios from 'axios';
import query from 'query-string';
import {RoleResponse} from "@/api/system/role";
import { TreeNodeData } from '@arco-design/web-vue';

const BASE_URL = '/admin/system/menu';


export interface MenuParam {
    name?: string;
    status?: number;
}


export interface MenuResponse {
    id?: number;
    name: string;
    parentId?: string;
    type: number;
    path?: string;
    component?: string;
    query?: string;
    isFrame: number;
    isCache: number;
    visible: number;
    permission?: string;
    icon?: string;
    sort: number;
    status?: number;
    createdBy?: string;
    updatedBy?: string;
    createdAt?: string;
    updatedAt?: string;
    children?: Array<MenuResponse>;
    parentName?: string;
}

export interface MenuSaveCommand {
    id?: number;
    name: string;
    parentId?: string;
    type: number;
    path?: string;
    component?: string;
    query?: string;
    isFrame: number;
    isCache: number;
    visible: number;
    permission?: string;
    icon?: string;
    sort: number;
    status?: number;
    createdBy?: string;
    updatedBy?: string;
    createdAt?: string;
    updatedAt?: string;
    children?: Array<MenuResponse>;
    parentName?: string;
}



// 获取菜单树
export function treeMenu(params: MenuParam) {
    return axios.get<TreeNodeData[]>(`${BASE_URL}/common/tree`, {
        params, paramsSerializer: (obj) => {return query.stringify(obj);}
    });
}

export function treeList(params: MenuParam) {
    return axios.get<MenuResponse[]>(`${BASE_URL}/tree/list`, {
        params, paramsSerializer: (obj) => {return query.stringify(obj);}
    });
}

// 通过id获取菜单详情
export function getMenu(id: number) {
    return axios.get<MenuResponse>(`${BASE_URL}/${id}`);
}

// 添加菜单
export function addMenu(command: MenuSaveCommand) {
    return axios.post(`${BASE_URL}/add`, command);
}

// 更新菜单
export function updateMenu(req: MenuSaveCommand) {
    return axios.put(BASE_URL, req);
}

// 批量删除菜单
export function deleteMenu(ids: number | Array<number>) {
    return axios.delete(`${BASE_URL}/delete/${ids}`);
}
