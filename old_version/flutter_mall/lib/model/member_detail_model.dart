class MemberDetailModel {
  MemberVO memberVO;
  MemberInfoVO memberInfoVO;

  MemberDetailModel({this.memberVO, this.memberInfoVO});

  MemberDetailModel.fromJson(Map<String, dynamic> json) {
    memberVO = json['memberVO'] != null
        ? new MemberVO.fromJson(json['memberVO'])
        : null;
    memberInfoVO = json['memberInfoVO'] != null
        ? new MemberInfoVO.fromJson(json['memberInfoVO'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.memberVO != null) {
      data['memberVO'] = this.memberVO.toJson();
    }
    if (this.memberInfoVO != null) {
      data['memberInfoVO'] = this.memberInfoVO.toJson();
    }
    return data;
  }
}

class MemberVO {
  int id;
  String username;
  int googleStatus;
  String email;
  String phone;
  int status;
  String createdAt;

  MemberVO(
      {this.id,
        this.username,
        this.googleStatus,
        this.email,
        this.phone,
        this.status,
        this.createdAt});

  MemberVO.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
    googleStatus = json['googleStatus'];
    email = json['email'];
    phone = json['phone'];
    status = json['status'];
    createdAt = json['createdAt'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['username'] = this.username;
    data['googleStatus'] = this.googleStatus;
    data['email'] = this.email;
    data['phone'] = this.phone;
    data['status'] = this.status;
    data['createdAt'] = this.createdAt;
    return data;
  }
}

class MemberInfoVO {
  int memberId;
  String profilePhoto;
  int gender;
  String birthday;
  String countryCode;
  String country;
  String province;
  String city;
  String district;
  String gradeLevel;
  int loginCount;
  int loginErrorCount;
  String lastLogin;

  MemberInfoVO(
      {this.memberId,
        this.profilePhoto,
        this.gender,
        this.birthday,
        this.countryCode,
        this.country,
        this.province,
        this.city,
        this.district,
        this.gradeLevel,
        this.loginCount,
        this.loginErrorCount,
        this.lastLogin});

  MemberInfoVO.fromJson(Map<String, dynamic> json) {
    memberId = json['memberId'];
    profilePhoto = json['profilePhoto'];
    gender = json['gender'];
    birthday = json['birthday'];
    countryCode = json['countryCode'];
    country = json['country'];
    province = json['province'];
    city = json['city'];
    district = json['district'];
    gradeLevel = json['gradeLevel'];
    loginCount = json['loginCount'];
    loginErrorCount = json['loginErrorCount'];
    lastLogin = json['lastLogin'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['memberId'] = this.memberId;
    data['profilePhoto'] = this.profilePhoto;
    data['gender'] = this.gender;
    data['birthday'] = this.birthday;
    data['countryCode'] = this.countryCode;
    data['country'] = this.country;
    data['province'] = this.province;
    data['city'] = this.city;
    data['district'] = this.district;
    data['gradeLevel'] = this.gradeLevel;
    data['loginCount'] = this.loginCount;
    data['loginErrorCount'] = this.loginErrorCount;
    data['lastLogin'] = this.lastLogin;
    return data;
  }
}
