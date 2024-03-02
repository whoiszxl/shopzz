import command from "../common/Command";
import { CommonConstant } from "../common/CommonConstant";
import StorageUtil from "../utils/StorageUtil";
import WebSocketUtil from "../utils/WebSocketUtil";
import uuid from 'react-native-uuid';

export default class ChatWebSocket {


    private static imei:string = "imei-100";
    private static clientType:number = 1;

    
    static login = async () => {
        // 在发起请求时，从本地缓存中获取并设置 token
        const token = await StorageUtil.getItem(CommonConstant.TOKEN);
        WebSocketUtil.send(this.imei, token!, command.LOGIN, this.clientType, "{}");
    }

    static sendPrivateChatMessage = async (messageBody:string, toMemberId: number) => {
        const token = await StorageUtil.getItem(CommonConstant.TOKEN);

        const privateChatPack: PrivateChatPack = {
            contentId: "-1",
            messageId: uuid.v4().toString(),
            fromMemberId: -1,
            toMemberId: toMemberId,
            body: messageBody
        };

        WebSocketUtil.send(this.imei, token!, command.MessageCommand.PRIVATE_CHAT, this.clientType, JSON.stringify(privateChatPack));

    }
}





