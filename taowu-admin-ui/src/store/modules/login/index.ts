import { defineStore } from 'pinia';
import {
    adminLogout,
    getAdminDetail,
    login as userLogin,
    LoginReq,
} from '@/api/login/login';
import { getImageCaptcha as getCaptcha } from '@/api/login/captcha';
import { setToken, clearToken } from '@/utils/auth';
import { removeRouteListener } from '@/utils/route-listener';
import { MemberState } from './types';
import useAppStore from '../app';

const useLoginStore = defineStore('login', {
    state: (): MemberState => ({
        id: 0,
        username: '',
        avatar: '',
        realName: '',
        gender: 0,
        mobile: '',
        email: '',
        googleStatus: 0,
        lastLoginTime: '',
        ip: '',
        location: '',
        browser: '',
        permissions: [],
        roles: []
    }),

    actions: {
        // 获取图片验证码
        getImgCaptcha() {
            return getCaptcha();
        },

        // 用户登录
        async login(req: LoginReq) {
            try {
                const res = await userLogin(req);
                setToken(res.data);
            } catch (err) {
                clearToken();
                throw err;
            }
        },

        async getAdminInfo() {
            const res = await getAdminDetail();
            this.$patch(res.data);
        },

        async logout() {
            try {
                await adminLogout();
            }finally {
                this.logoutCallBack();
            }
        },

        logoutCallBack() {
            const appStore = useAppStore();
            this.resetInfo();
            clearToken();
            removeRouteListener();
            appStore.clearServerMenu();
        },

        // 重置用户信息
        resetInfo() {
            this.$reset();
        },
    },
});

export default useLoginStore;
