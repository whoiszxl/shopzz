import { observable } from "mobx";
import Loading from "../../components/Loading";
import ApiService from "../../apis/ApiService";

export default class ProductDetailStore {

    //@ts-ignore
    @observable productDetail: SPUVO = {} as SPUVO;

    requestProductDetail = async (id: number) => {

        Loading.show();
        try {
            const { data } = await ApiService.request('productDetail', id);
            if(data?.data) {
                this.productDetail = data.data;
            }
        } catch (error) {
            console.log(error);
        } finally {
            Loading.hide();
        }
    }
}
