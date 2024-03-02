import ApiService from "../apis/ApiService";
import { flow, observable } from "mobx";



export default class OnlineResumeStore {

    //@ts-ignore
    @observable onlineResumeInfo: ResumeData  = {};
    
    //获取用户详细信息
    requestOnlineResumeInfo = async () => {
        try {
            const { data } = await ApiService.request('onlineResumeInfo', {});
            this.onlineResumeInfo = data.data || {};
            return data;
        } catch (error) {
            console.log(error);
        }
    }
    

}