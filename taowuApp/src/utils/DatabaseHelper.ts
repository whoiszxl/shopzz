import SQLite, { ResultSet } from 'react-native-sqlite-storage';
import { CommonConstant } from '../common/CommonConstant';
import { Alert } from 'react-native';

class DatabaseHelper {
  private static database: SQLite.SQLiteDatabase | null = null;

  static initializeDatabase(databaseName: string): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      if (this.database) {
        resolve();
        return;
      }

      this.database = SQLite.openDatabase(
        {
          name: databaseName,
          location: 'default',
        },
        () => {
          resolve();
        },
        (error) => {
          reject(error);
        }
      );
    });
  }

  static closeDatabase(): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      if (!this.database) {
        resolve();
        return;
      }

      this.database.close(
        () => {
          this.database = null;
          resolve();
        },
        (error) => {
          reject(error);
        }
      );
    });
  }

  static executeQuery(query: string, params: any[] = []): Promise<ResultSet> {
    return new Promise<ResultSet>((resolve, reject) => {
      if (!this.database) {
        reject(new Error('Database is not initialized'));
        return;
      }

      this.database.executeSql(
        query,
        params,
        (_, resultSet) => {
          resolve(resultSet);
        },
        (error) => {
          reject(error);
        }
      );
    });
  }

  static insertData(table: string, data: Record<string, any>): Promise<number> {
    const columns = Object.keys(data).join(', ');
    const placeholders = Object.keys(data)
      .map(() => '?')
      .join(', ');

    const query = `INSERT INTO ${table} (${columns}) VALUES (${placeholders})`;

    return this.executeQuery(query, Object.values(data)).then((resultSet) => {
      return resultSet.insertId;
    });
  }

  static updateData(table: string, data: Record<string, any>, where: string, whereArgs: any[] = []): Promise<number> {
    const setValues = Object.keys(data)
      .map((column) => `${column} = ?`)
      .join(', ');

    const query = `UPDATE ${table} SET ${setValues} WHERE ${where}`;

    return this.executeQuery(query, [...Object.values(data), ...whereArgs]).then((resultSet) => {
      return resultSet.rowsAffected;
    });
  }

  static deleteData(table: string, where: string, whereArgs: any[] = []): Promise<number> {
    const query = `DELETE FROM ${table} WHERE ${where}`;

    return this.executeQuery(query, whereArgs).then((resultSet) => {
      return resultSet.rowsAffected;
    });
  }

  static fetchAllData(query: string, params: any[] = []): Promise<any[]> {
    return this.executeQuery(query, params).then((resultSet) => {
      const rows = resultSet.rows;
      const data: any[] = [];

      for (let i = 0; i < rows.length; i++) {
        data.push(rows.item(i));
      }

      return data;
    });
  }


  static getPrivateChatByMemberId(memberId: number):any {
    this.database!.transaction(tx => {
      tx.executeSql(
        'SELECT * FROM messages where from_member_id = ' + memberId, 
        [],
        (_, resultSet) => {
          const rows = resultSet.rows;
          const messages = [];
  
          for (let i = 0; i < rows.length; i++) {
            const row = rows.item(i);
            messages.push(row);
          }
  
          return messages;
        },
        (_, error) => {
          console.error('Error fetching data: ', error);
        }
      );
    });
  }


  static executeSQL(query: string, params: any[] = []): Promise<any[]> {
    return new Promise<any[]>((resolve, reject) => {
      if (!this.database) {
        reject(new Error('Database is not initialized'));
        return;
      }

      this.database!.transaction(tx => {
        tx.executeSql(
          //'SELECT * FROM messages where from_member_id = ' + memberId, 
          query,
          params,
          (_, resultSet) => {
            const rows = resultSet.rows;
            const messages = [];
    
            for (let i = 0; i < rows.length; i++) {
              const row = rows.item(i);
              messages.push(row);
            }            
            resolve(messages);
          },
          (_, error) => {
            reject(error);
          }
        );
      });
    });
  }


  static deleteMessage(tableName:string): void {
    this.database!.transaction((tx) => {
      tx.executeSql('DELETE FROM ' + tableName + ";", [], (tx, results) => {
        if (results.rowsAffected > 0) {
          Alert.prompt('成功删除了 ' + results.rowsAffected + ' 行数据');
        } else {
          console.log('没有删除任何数据');
        }
      },
      (error) => {
        console.log('删除数据时发生错误: ' + JSON.stringify(error));
      });
    });
  }

  
}

export default DatabaseHelper;
