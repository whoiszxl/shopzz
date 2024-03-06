import { action, flow, observable } from "mobx";
import ApiService from "../../../apis/ApiService";

const SIZE = 10;
export default class BuyStore {

    page: number = 1;

    //@ts-ignore
    @observable index: number = 0;

    //@ts-ignore
    @observable jobList: VideoEntity[] = [];

    //@ts-ignore
    @observable refreshing: boolean = false;




    //@ts-ignore
    @observable oneCategoryList: OneLevelCategory[] = [];

    //@ts-ignore
    @observable childCategoryList: TwoLevelCategory[] = [];

    //@ts-ignore
    @observable childCategoryOtherTabList: TwoLevelCategory[] = [];

    //@ts-ignore
    @observable indexSpuEntityList: IndexSpuEntity[] = [];

    


    //@ts-ignore
    @action
    resetPage = () => {
        this.page = 1;
    }


    requestOneCategoryList = async () => {
        console.log("请求一级分类");
        const { data } = await ApiService.request('oneCategoryList');
        this.oneCategoryList = data.data;
    }

    requestChildCategoryList = async (id:string) => {
        console.log("请求二级分类 ");
        const { data } = await ApiService.request('childCategoryList', id);
        this.childCategoryList = data.data;
    }

    requestChildCategoryOtherTabList = async (id:string) => {
        console.log("请求二级分类");
        const { data } = await ApiService.request('childCategoryList', id);
        this.childCategoryOtherTabList = data.data;
    }


    requestIndexSpuList = async () => {
        console.log("requestIndexSpuList start  " + this.refreshing);
        if(this.refreshing) {
            return;
        }

        try{
            this.refreshing = true;

            const { data } = await ApiService.request('spuIndexList', this.page);
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

}