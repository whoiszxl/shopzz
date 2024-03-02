import AsyncStorage from "@react-native-async-storage/async-storage";

/**
 * 本地缓存工具，持久化数据到客户端本地，比如说用户Token与用户基本信息
 */
class StorageUtil {
  static async getJsonItem(key: string): Promise<any> {
    try {
      const value = await AsyncStorage.getItem(key);
      if (value !== null) {
        return JSON.parse(value);
      }
    } catch (error) {
      console.error('Error retrieving data from AsyncStorage:', error);
    }
    return null;
  }

  static async setJsonItem(key: string, value: any): Promise<void> {
    try {
      await AsyncStorage.setItem(key, JSON.stringify(value));
    } catch (error) {
      console.error('Error saving data to AsyncStorage:', error);
    }
  }


  static async getItem(key: string) {
    try {
      const value = await AsyncStorage.getItem(key);
      if (value !== null) {
        return value;
      }
    } catch (error) {
      console.error('Error retrieving data from AsyncStorage:', error);
    }
    return null;
  }

  static async setItem(key: string, value: any): Promise<void> {
    try {
      await AsyncStorage.setItem(key, value);
    } catch (error) {
      console.error('Error saving data to AsyncStorage:', error);
    }
  }

  static async removeItem(key: string): Promise<void> {
    try {
      await AsyncStorage.removeItem(key);
    } catch (error) {
      console.error('Error removing data from AsyncStorage:', error);
    }
  }
}

export default StorageUtil;
