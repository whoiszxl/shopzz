import { METHODS, PARAM_TYPE } from "./methods";

const apis = {

    videoTestList: {url: '/video/video/latest/test', method: METHODS.POST}, //获取测试的视频最新列表
    jobDetail: {url: '/job/api/job/', method: METHODS.GET, paramType: PARAM_TYPE.PATH}, //获取职位详情信息，jobId拼接在url之后


    /** 会员相关接口 */
    sendSmsCaptcha: {url: '/member/api/login/sms-captcha', method: METHODS.POST}, //发送登录短信
    smsLogin: {url: '/member/api/login/sms', method: METHODS.POST}, //短信登录接口

    memberInfo: {url: '/member/api/member/info', method: METHODS.GET}, //获取用户信息





    
    

    logout: {url: '/member/api/member/logout', method: METHODS.POST}, //登出接口

    /** 文件上传相关接口 */
    fileUpload: {url: '/file/api/file/upload', method: METHODS.POST}, //文件上传

    /** IM相关接口 */
    talkList: {url: '/im/talk/list', method: METHODS.GET}, //获取IM对话列表
    offlineMessageList: {url: '/im/message/offline/list', method: METHODS.POST}, //获取离线消息列表




    /** 购物相关接口 */
    oneCategoryList: {url: '/product/api/category/one/level', method: METHODS.GET}, // 获取所有第一级分类的列表
    childCategoryList: {url: '/product/api/category/child/', method: METHODS.GET, paramType: PARAM_TYPE.PATH}, // 获取第一级分类下的二三级分类列表


    spuIndexList: {url: '/product/api/spu/index/list/', method: METHODS.GET, paramType: PARAM_TYPE.PATH}, // 获取首页最新商品SPU的列表

    productDetail: {url: '/product/api/spu/detail/', method: METHODS.POST, paramType: PARAM_TYPE.PATH}, // 获取商品详情

    
}

export default apis;