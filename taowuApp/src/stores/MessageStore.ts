import { action, observable, flow } from "mobx";
import ApiService from "../apis/ApiService";
const SIZE = 10;
export default class MessageStore {

    page: number = 1;

    //@ts-ignore
    @observable index: number = 0;

    //@ts-ignore
    @observable talkList: TalkEntity[] = [];

    //@ts-ignore
    @observable refreshing: boolean = false;

    //@ts-ignore
    @action
    resetPage = () => {
        this.page = 1;
    }

    requestTalkList = async () => {
        if(this.refreshing) {
            return;
        }

        try{
            this.refreshing = true;

            const params = {
                page: this.page,
                size: SIZE,
            };

            const { data } = await ApiService.request('talkList', params);
            
            if(data?.data?.total > 0) {
                if(this.page === 1) {
                    this.talkList = data.data.list;
                }else {
                    this.talkList = [...this.talkList, ...(data.data.list)];
                }
                this.page = this.page + 1;
                this.refreshing = false;
            }else {
                if(this.page === 1) {
                    this.talkList = [];
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



    requestOfflineMessageList = flow(function* (this: MessageStore, clientSequence: number, callback: (success: PrivateChatMessage[]) => void) {
        try {
            const params = {
                clientSequence: clientSequence
            };

            const { data } = yield ApiService.request('offlineMessageList', params);
            if (data) {
                if(data.code !== 0) {
                    callback?.([]);
                    return;
                }
                callback?.(data.data);
            } else {
                callback?.([]);
            }
        } catch (error) {
            callback?.([]);
        }
    });




}





