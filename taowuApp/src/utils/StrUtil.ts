export function formatPhone(phone: string):string {
    let trimPhone:string = phone.replace(/\s+/g, '');

    const result = [
        trimPhone.slice(0,3),
        trimPhone.slice(3,7),
        trimPhone.slice(7,11)
    ].filter(item => !!item).join(' ');

    return result;
}


export function replaceBlank(phone: string):string {
    return phone ? phone.replace(/\s+/g, '') : '';
}

export function generateString(length: number): string {
    const fillCharacter = '*';
    let result = '';
  
    for (let i = 0; i < length; i++) {
      result += fillCharacter;
    }
  
    return result;
}
  
export function formatNumber(num: number): string {
    const units = ["", "万", "亿", "万亿"]; // 单位
    const digits = Math.floor(Math.log10(num)); // 整数的位数
  
    if (digits < 4) {
      return num.toString(); // 位数小于4，直接返回原始数字
    }
  
    const unitIndex = Math.floor(digits / 4); // 计算应该使用的单位索引
    const divisor = Math.pow(10, unitIndex * 4); // 根据单位索引计算除数
    const result = num / divisor; // 计算缩写形式的数值
    return result.toFixed(1) + units[unitIndex]; // 返回带有单位的缩写形式
}



export function dateFormat(originalDateTime: Date): string {
  // 将原始时间字符串转换为 Date 对象

  
  // 获取年、月、日、小时、分钟、秒
  const year = originalDateTime.getUTCFullYear();
  const month = String(originalDateTime.getUTCMonth() + 1).padStart(2, '0');
  const day = String(originalDateTime.getUTCDate()).padStart(2, '0');
  const hours = String(originalDateTime.getUTCHours()).padStart(2, '0');
  const minutes = String(originalDateTime.getUTCMinutes()).padStart(2, '0');
  const seconds = String(originalDateTime.getUTCSeconds()).padStart(2, '0');

  // 格式化为 "YYYY-MM-DD HH:mm:ss" 格式
  const formattedDateTime = `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
  return formattedDateTime;
}

