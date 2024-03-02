import { action, observable, flow } from "mobx";
import ApiService from "../apis/ApiService";

const SIZE = 10;
export default class MineStore {


    //@ts-ignore
    @observable refreshing: boolean = false;

    //@ts-ignore
    @observable memberInfo: any  = {};

    requestAll = async () => {
        this.refreshing = true;

        var data = null;
        Promise.all([
            data = this.requestMemberDetail(),
        ]).then(() => {
            this.refreshing = false;
            
        });
        return data;
    }

    //获取用户详细信息
    requestMemberDetail = async () => {
        try {
            const { data } = await ApiService.request('memberInfo', {});
            this.memberInfo = data.data || {};
            return data;
        } catch (error) {
            console.log(error);
        }
    }

}





