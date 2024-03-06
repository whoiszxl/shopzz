declare module '*.jpg';
declare module '*.png';
declare module '*.webp';
declare module '*.jpeg';
declare module '*.gif';
declare module '*.mp4';
declare module '*.json';
declare module '*.js';

declare module 'react-native-refreshable-listview';


type VideoEntity = {
  id: string;
  memberId: string;
  descs: string;
  cover: string;
  videoUrl: string;
  imageUrl: string;
  seconds: number;
  width: number;
  height: number;
  resourceType: number;
  watchType: number;
  canWatchMember: string;
  cannotWatchMember: string;
  channel: string;
  address: string;
  longitude: number;
  latitude: number;
  ip: string;
  status: number;
  version: string;
  isDeleted: number;
  createdAt: string;
  updatedAt: string;
}


type Category = {
  name: string;
  isDefault: boolean;
}

type TalkEntity = {
    id: number;
    talkType: number;
    fromMemberId: number;
    fromMemberInfo: string;
    toMemberId: number;
    muteStatus: number;
    topStatus: number;
    readSequence: number;
    sequence: number;
    createdAt: string;
}


type MessageData = {
    contentId: string;
    messageId: string;
    fromMemberId: number;
    toMemberId: number;
    body: string;
    sequence: number;
  }
  
type PrivateChatMessage = {
    toMemberId: number;
    clientType: number;
    command: number;
    imei: string;
    data: MessageData;
    ackStatus: number;
    sendAt: string;
}


type PrivateChatPack = {
    messageId: string;
    contentId: string;
    fromMemberId: number;
    toMemberId: number;
    body: string;
}

type OneLevelCategory = {
  id: string;
  name: string;
}

type TwoLevelCategory = {
  id: string;
  name: string;
  childList: ThreeLevelCategory[];
}

type ThreeLevelCategory = {
  id: string;
  name: string;
  icon: string;
}


type IndexSpuEntity = {
  id: string;
  name: string;
  defaultPrice: string;
  defaultPic: string;
}
