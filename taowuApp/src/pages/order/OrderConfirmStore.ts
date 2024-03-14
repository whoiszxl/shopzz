import { observable } from "mobx";
import Loading from "../../components/Loading";
import ApiService from "../../apis/ApiService";

export default class OrderConfirmStore {


    //@ts-ignore
    @observable addressList: AddressEntity[]  = [];

    requestProductDetail = async () => {

        Loading.show();
        try {
            const { data } = await ApiService.request('addressList');
            console.log(data);
            if(data?.data) {
                this.addressList = data.data;
            }
        } catch (error) {
            console.log(error);
        } finally {
            Loading.hide();
        }
    }
}
