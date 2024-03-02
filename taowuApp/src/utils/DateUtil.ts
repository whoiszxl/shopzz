
class DateUtil {
    static calculateAge(dateOfBirth: string): number {
        // Using regex to check if the dateOfBirth is in the correct format
        const dateRegex: RegExp = /^\d{4}-\d{2}-\d{2} \d{2}:\d{2}:\d{2}$/;
      
        if (!dateRegex.test(dateOfBirth)) {
          throw new Error('Invalid date format. Please use the format: 2002-08-26 10:03:12');
        }
      
        // Parse the input date string into a Date object
        const birthDate: Date = new Date(dateOfBirth);
      
        // Get the current date
        const currentDate: Date = new Date();
      
        // Calculate the age
        const ageInMillis: number = currentDate.getTime() - birthDate.getTime();
        const ageInYears: number = Math.floor(ageInMillis / (365.25 * 24 * 60 * 60 * 1000));
      
        return ageInYears;
      }

      static formatWorkDate(inputDate: string): string {
        const dateObject = new Date(inputDate);
        const year = dateObject.getFullYear();
        const month = (dateObject.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1
        return `${year}.${month}`;
      }
}


export default DateUtil;