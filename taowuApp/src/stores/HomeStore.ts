import { action, flow, observable } from "mobx";
import ApiService from "../apis/ApiService";

const SIZE = 10;
export default class HomeStore {

    page: number = 1;

    //@ts-ignore
    @observable index: number = 0;

    //@ts-ignore
    @observable jobList: VideoEntity[] = [];

    //@ts-ignore
    @observable refreshing: boolean = false;

    //@ts-ignore
    @observable categoryList: Category[]  = [];


    //@ts-ignore
    @action
    resetPage = () => {
        this.page = 1;
    }

    requestVideotTest = async () => {
        if(this.refreshing) {
            return;
        }

        try{
            this.refreshing = true;

            const params = {
                page: this.page,
                size: SIZE,
            };

            const { data } = await ApiService.request('videoTestList', params);
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

    getCategoryList = async () => {
        this.categoryList = DEFAULT_CATEGORY_LIST;
    }


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

const DEFAULT_CATEGORY_LIST: Category[] = [
    // 默认添加频道
    { name: '关注',  isDefault: false},
    { name: '推荐',  isDefault: true},
    { name: '视频',  isDefault: false},
    { name: '直播',  isDefault: false},

    { name: '车品',  isDefault: false},
    { name: '潮鞋',  isDefault: false},
    { name: '穿搭',  isDefault: false},
    { name: '手表',  isDefault: false},

    { name: '潮玩',  isDefault: false},
    { name: '理容',  isDefault: false},
    { name: '美妆',  isDefault: false},
    { name: '健身',  isDefault: false},

    { name: '文化',  isDefault: false},
    { name: '数码',  isDefault: false}
];