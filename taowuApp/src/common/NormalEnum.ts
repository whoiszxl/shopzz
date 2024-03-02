const educationMap: Record<number, string> = {
    1: '初中及以下',
    2: '中专/中技',
    3: '高中',
    4: '大专',
    5: '本科',
    6: '硕士',
    7: '博士',
};

export function getChineseEducation(level: number): string {
    const chineseEducation = educationMap[level];
    if (chineseEducation) {
      return chineseEducation;
    } else {
      throw new Error('Invalid education level');
    }
}