declare module '*.jpg';
declare module '*.png';
declare module '*.webp';
declare module '*.jpeg';
declare module '*.gif';
declare module '*.mp4';
declare module '*.json';
declare module '*.js';

declare module 'react-native-refreshable-listview';

type ArticleSimple = {
  id: number;
  title: string;
  userName: string;
  avatarUrl: string;
  favoriteCount: number;
  isFavorite: boolean;
  image: string;
}

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
}

type BossMemberEntity = {
  id: string;
  phone: string;
  email: string;
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
  identityStatus: number;
  workStatus: number;
  highestQualification: number;
  highestQualificationType: number;
  isToutou: number;
};


type JobEntity = {
    id: string;
    memberInfo: string;
    memberId: string;
    companyId: string;
    jobName: string;
    salaryRangeStart: number;
    salaryRangeEnd: number;
    salaryOptional: {
        payDay: string;
        subsidy: string[];
        basicSalary: number;
        socialSecurity: string;
    };
    workYearRangeStart: number;
    workYearRangeEnd: number;
    ageRangeStart: number;
    ageRangeEnd: number;
    educationAttainment: string;
    jobTags: string;
    jobDescription: string;
    replyCount: number;
    longitude: number;
    latitude: number;
    locationImg: string;
    country: string;
    province: string;
    city: string;
    district: string;
    addressDetail: string;
    createdAt: string;
    updatedAt: string;

    companyResponse: {
        id: string;
        applyMemberId: string;
        companyFullName: string;
        companyAbbrName: string;
        companyLogo: string;
        companyDescription: string;
        companyScale: string;
        financingStage: string;
        industry: string;
        workDateStart: string;
        workDateEnd: string;
        restWay: number;
        overtime: number;
        photo: string;
        employeeWelfare: string;
        mainBusiness: string;
        longitude: number;
        latitude: number;
        country: string;
        province: string;
        city: string;
        district: string;
        addressDetail: string;
    }
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
    gender: string;
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
    status: string;
    token: string;
    location: string;
    browser: string;
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



type ResumeData = {
    memberInfoResponse: MemberInfoResponse;
    workExpectDtoList: WorkExpectDto[];
    workExperienceDtoList: WorkExperienceDto[];
    projectExperienceDtoList: ProjectExperienceDto[];
    eduExperienceDtoList: EduExperienceDto[];
    qualificationList: string[];
    advantage: string;
}

type MemberInfoResponse = {
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
    identityStatus: number;
    workStatus: number;
    highestQualification: number;
    highestQualificationType: number;
    isToutou: number;
    status: number;
    token: string;
    location: string;
    browser: string;
  }
  
  type WorkExpectDto = {
    type: number;
    city: string;
    job: string;
    salaryRangeStart: number;
    salaryRangeEnd: number;
    industryArr: string[];
  }
  
  type WorkExperienceDto = {
    companyFullName: string;
    industry: string;
    workDateStart: string;
    workDateEnd: string;
    jobName: string;
    workDetail: string;
  }
  
  type ProjectExperienceDto = {
    projectName: string;
    projectRole: string;
    projectDateStart: string;
    projectDateEnd: string;
    projectResult: string;
    projectLink: string;
  }
  
  type EduExperienceDto = {
    schoolName: string;
    educationAttainment: string;
    major: string;
    yearStart: number;
    yearEnd: number;
    schoolExp: string;
    paper: string;
  }
  

  type AttachmentResume = {
    id: string;
    memberId: string;
    filename: string;
    url: string;
    status: number;
    version: string;
    isDeleted: number;
    createdAt: string;
    updatedAt: string;
  }
  