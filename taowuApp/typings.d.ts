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


type SPUAttribute = {
  keyId: string;
  keyName: string;
  selected: number;
  spuAttrList: SpuAttr[];
}

type SpuAttr = {
  keyId: string;
  key: string;
  valueId: string;
  value: string;
}

type SKU = {
  id: string;
  spuId: string;
  categoryId: string;
  parentCategoryId: string;
  skuName: string;
  skuImg: string;
  salePrice: number;
  promotionPrice: number;
  saleAttr: string;
  skuCode: string;
}

type SKUStock = {
  id: string;
  skuId: string;
  saleStockQuantity: number;
  stockStatus: number;
}

type SPU = {
  id: string;
  name: string;
  subName: string;
  defaultPrice: number;
  defaultPic: string;
  categoryId: string;
  parentCategoryId: string;
  brandId: string;
  brandName: string;
  packageList: string;
  defaultSkuId: string;
}

type Image = {
  id: string;
  spuId: string;
  imgUrl: string;
  sort: number;
  isDefault: number;
}

type SPUVO = {
  spuVO: SPU;
  skus: SKU[];
  skuStocks: SKUStock[];
  images: Image[];
  spuDetailVO: string;
  spuAttributeGroupVOList: SPUAttribute[];
}

type MemberInfoEntity = {
  id: string;
  phone: string;
  email: string;
  password: string;
  fullName: string;
  workDate: string;
  wxCode: string;
  birthday: string;
  country: string;
  province: string;
  city: string;
  district: string;
  gender: number;
  avatar: string;
  ip: string;
  loginCount: string;
  loginErrorCount: string;
  lastLogin: string;
  identityStatus: string;
  workStatus: string;
  highestQualification: string;
  highestQualificationType: string;
  isToutou: string;
  status: number;
  token: string;
  location: string;
  browser: string;
}

type AddressEntity = {
  id: string;
  memberId: string;
  receiverName: string;
  receiverPhone: string;
  province: string;
  city: string;
  district: string;
  detailAddress: string;
  isDefault: number;
}