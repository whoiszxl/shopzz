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
        console.log("requestVideotTest start  " + this.refreshing);
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

                console.log(this.jobList);
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

const DEFAULT_CATEGORY_LIST: Category[] = [
    // 默认添加频道
    { name: '关注' },
    { name: '推荐' },
    { name: '视频', },
    { name: '直播',  },

    { name: '车品',  },
    { name: '潮鞋',  },
    { name: '穿搭',  },
    { name: '手表',  },

    { name: '潮玩',  },
    { name: '理容',  },
    { name: '美妆',  },
    { name: '健身',  },

    { name: '文化',  },
    { name: '数码',  }
];