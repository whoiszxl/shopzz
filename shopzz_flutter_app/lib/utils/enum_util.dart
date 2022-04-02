class EnumUtil {

  ///判断类型设置值到sp
  static getGender(int enumValue) {
    switch(enumValue) {
      case 0:
        return "未知";
      case 1:
        return "男";
      case 2:
        return "女";
      default:
        return "未知";
    }
  }

}