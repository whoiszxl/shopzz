import ApiService from "../apis/ApiService";
import { flow } from "mobx";
import StorageUtil from "../utils/StorageUtil";
import { CommonConstant } from "../common/CommonConstant";
import { View, Button, Alert, StyleSheet } from 'react-native';


class MemberStore {

    token : any;

    loginToken: any;
    
    requestSmsLogin = flow(function* (this: MemberStore, phone: string, smsCode: string, uuid: string, callback: (success: boolean) => void) {
        try {
            const params = {
                phone: phone,
                smsCode: smsCode,
                uuid: uuid
            };

            const { data } = yield ApiService.request('smsLogin', params);
            if (data) {
                if(data.code !== 0) {
                    this.token = null;
                    callback?.(false);
                    Alert.alert(data.message);
                    return;
                }

                this.token = data.data;
                StorageUtil.setItem(CommonConstant.TOKEN, data.data);
                callback?.(true);
            } else {
                this.token = null;
                callback?.(false);
            }
        } catch (error) {
            this.token = null;
            callback?.(false);
        }
    });


    requestSendSmsCaptcha = flow(function* (this: MemberStore, phone: string, callback: (success: boolean) => void) {
        try {
            const params = { phone: phone };
            
            const { data } = yield ApiService.request('sendSmsCaptcha', params);
            if (data) {
                this.loginToken = data.data;
                StorageUtil.setItem(CommonConstant.LOGIN_TOKEN, data.data);
                callback?.(true);
            } else {
                this.loginToken = null;
                callback?.(false);
            }
        } catch (error) {
            console.log(error);
            this.loginToken = null;
            callback?.(false);
        }
    });


    requestMemberInfo = flow(function* (this: MemberStore, callback: (data?: MemberInfoEntity) => void) {
        try {
            
            const { data } = yield ApiService.request('memberInfo');
            if (data) {
                if(data.code === 0) {
                    callback?.(data.data);
                }else {
                    callback?.(undefined);
                }
            }
        } catch (error) {
            console.log("error", error);
            this.loginToken = null;
            callback?.(undefined);
        }
    });

    initBaseInfo = flow(function* (this: MemberStore, name: string, gender: number, birthday: string, callback: (success: boolean) => void) {
        try {
            const params = {
                fullName: name,
                gender: gender,
                birthday: birthday
            };
            const { data } = yield ApiService.request('initBaseInfo', params);
            if (data) {
                if(data.code === 0) {
                    callback?.(true);
                }
            }
        } catch (error) {
            console.log(error);
            this.loginToken = null;
            callback?.(false);
        }
    });

    initIdentityInfo = flow(function* (this: MemberStore, selectedButtonIdentityStatus: number, workStatus: number, callback: (success: boolean) => void) {
        try {
            const params = {
                identityStatus: selectedButtonIdentityStatus,
                workStatus: workStatus
            };
            

            const { data } = yield ApiService.request('initBaseInfo', params);
            if (data) {
                if(data.code === 0) {
                    callback?.(true);
                }
            }
        } catch (error) {
            console.log(error);
            this.loginToken = null;
            callback?.(false);
        }
    });


    initQualificationInfo = flow(function* (this: MemberStore, highestQualification: number, highestQualificationType: number, callback: (success: boolean) => void) {
        try {
            const params = {
                highestQualification: highestQualification,
                highestQualificationType: highestQualificationType
            };
            const { data } = yield ApiService.request('initBaseInfo', params);
            if (data) {
                if(data.code === 0) {
                    callback?.(true);
                }
            }
        } catch (error) {
            console.log(error);
            this.loginToken = null;
            callback?.(false);
        }
    });


    initAvatar = flow(function* (this: MemberStore, avatar: string, callback: (success: boolean) => void) {
        try {
            const params = {
                avatar: avatar
            };
            const { data } = yield ApiService.request('initBaseInfo', params);
            if (data) {
                if(data.code === 0) {
                    callback?.(true);
                }
            }
        } catch (error) {
            console.log(error);
            this.loginToken = null;
            callback?.(false);
        }
    });


    uploadAvatar = flow(function* (this: MemberStore, url: string, uri: string, fileName: string, fileType: string, callback: (url: string) => void) {
        try {
            const { data } = yield ApiService.upload(url, uri, fileName, fileType);
            if (data) {
                if(data.code === 0) {
                    callback?.(data.data);
                }
            }
        } catch (error) {
            console.log(error);
            this.loginToken = null;
            callback?.('');
        }
    });
}

export default new MemberStore();