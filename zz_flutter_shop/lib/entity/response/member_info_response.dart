class MemberInfoResponse {
  Member member;
  MemberInfo memberInfo;

  MemberInfoResponse({this.member, this.memberInfo});

  MemberInfoResponse.fromJson(Map<String, dynamic> json) {
    member =
    json['member'] != null ? Member.fromJson(json['member']) : null;
    memberInfo = json['memberInfo'] != null
        ? MemberInfo.fromJson(json['memberInfo'])
        : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    if (member != null) {
      data['member'] = member.toJson();
    }
    if (memberInfo != null) {
      data['memberInfo'] = memberInfo.toJson();
    }
    return data;
  }
}

class Member {
  int id;
  String username;
  int googleStatus;
  String realName;
  String email;
  String phone;
  int status;

  Member(
      {this.id,
        this.username,
        this.googleStatus,
        this.realName,
        this.email,
        this.phone,
        this.status});

  Member.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    username = json['username'];
    googleStatus = json['googleStatus'];
    realName = json['realName'];
    email = json['email'];
    phone = json['phone'];
    status = json['status'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['username'] = username;
    data['googleStatus'] = googleStatus;
    data['realName'] = realName;
    data['email'] = email;
    data['phone'] = phone;
    data['status'] = status;
    return data;
  }
}

class MemberInfo {
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

  MemberInfo(
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

  MemberInfo.fromJson(Map<String, dynamic> json) {
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
    final Map<String, dynamic> data = <String, dynamic>{};
    data['memberId'] = memberId;
    data['profilePhoto'] = profilePhoto;
    data['gender'] = gender;
    data['birthday'] = birthday;
    data['countryCode'] = countryCode;
    data['country'] = country;
    data['province'] = province;
    data['city'] = city;
    data['district'] = district;
    data['gradeLevel'] = gradeLevel;
    data['loginCount'] = loginCount;
    data['loginErrorCount'] = loginErrorCount;
    data['lastLogin'] = lastLogin;
    return data;
  }
}
