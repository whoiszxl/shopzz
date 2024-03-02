import { action, flow, observable } from "mobx";
import ApiService from "../apis/ApiService";

const SIZE = 10;
export default class HomeStore {

    page: number = 1;

    //@ts-ignore
    @observable index: number = 0;

    //@ts-ignore
    @observable jobList: BossMemberEntity[] = [];

    //@ts-ignore
    @observable refreshing: boolean = false;

    //@ts-ignore
    @action
    resetPage = () => {
        this.page = 1;
    }

    requestLatestTest = async () => {
        if(this.refreshing) {
            return;
        }

        try{
            this.refreshing = true;

            const params = {
                page: this.page,
                size: SIZE,
            };

            const { data } = await ApiService.request('toutouWorkerList', params);
            
            if(data?.data?.total > 0) {
                if(this.page === 1) {
                    this.jobList = data.data.list;
                }else {
                    this.jobList = [...this.jobList, ...(data.data.list)];
                }
                this.page = this.page + 1;
                this.refreshing = false;
            }else {
                if(this.page === 1) {
                    this.jobList = [];
                    this.refreshing = false;
                }else {
                    //没有更多数据了
                }
            }
        }catch(error) {
            
        }finally{
            this.refreshing = false;
        }

        
    }

    //@ts-ignore
    @observable jobDetail: JobEntity = {};

    //@ts-ignore
    @observable memberInfo: any = {};

    //@ts-ignore
    @observable detailRefreshing: boolean = false;


    requestDetail = async (id:string) => {
        
        if(this.detailRefreshing) {
            return;
        }

        try{
            this.detailRefreshing = true;

            const { data } = await ApiService.request('jobDetail', id);
            
            this.jobDetail = data.data;
            this.memberInfo = JSON.parse(this.jobDetail.memberInfo);
            this.detailRefreshing = false;
        }catch(error) {
            
        }finally{
            this.detailRefreshing = false;
        }

        
    }



    requestDetail2 = flow(function* (this: HomeStore, id: string, callback: (data?: JobEntity) => void) {
        try {

            const { data } = yield ApiService.request('jobDetail', id);
            if (data) {
                if(data.code === 0) {
                    callback?.(data.data);
                    this.memberInfo = data.data;
                }
            }
        } catch (error) {
            callback?.(undefined);
        }
    });
}