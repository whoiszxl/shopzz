import { StyleSheet, Text, View, Dimensions, Image } from 'react-native'
import React, { useEffect, useState } from 'react'
import { useLocalStore, observer } from 'mobx-react';
import Feather from 'react-native-vector-icons/Feather';
import AntDesign from 'react-native-vector-icons/AntDesign';
import FlowList from '../../../components/flowlist/FlowList.js';
import { TouchableOpacity } from 'react-native-gesture-handler';
import TitleBar from './components/TitleBar';
import {useSafeAreaInsets} from 'react-native-safe-area-context';
import { GestureResponderEvent } from 'react-native';
import { CommonColor } from '../../../common/CommonColor';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import MessageStore from '../../../stores/MessageStore';
import WebSocketUtil from '../../../utils/WebSocketUtil';
import ChatWebSocket from '../../../stores/ChatWebSocket';
import DatabaseHelper from '../../../utils/DatabaseHelper';
import command from '../../../common/Command';
import { CommonConstant } from '../../../common/CommonConstant';
import StorageUtil from '../../../utils/StorageUtil';


const {width:SCREEN_WIDTH} = Dimensions.get('window');


export default observer(() => {
  const insets = useSafeAreaInsets();

  const navigation = useNavigation<StackNavigationProp<any>>();

  const store = useLocalStore(() => new MessageStore());

  const [index, setIndex] = useState<number>(0);

  useEffect(() => {
    // WebSocketUtil.connect();
    // WebSocketUtil.addListener('message', handleMessage);
    // WebSocketUtil.addListener('open', handleOpen);
    
    // store.requestTalkList();

    DatabaseHelper.initializeDatabase(CommonConstant.IM_DB_NAME)
    .then(() => {
      console.log('Database initialized');

      DatabaseHelper.executeQuery("CREATE TABLE IF NOT EXISTS " + CommonConstant.IM_PRIVATE_CHAT_TABLE + " (id INTEGER PRIMARY KEY AUTOINCREMENT, content_id INTEGER UNIQUE, owner_member_id INTEGER, from_member_id INTEGER, to_member_id INTEGER, body TEXT);")
      .then(() => {
        console.log('Table initialized');
      })
      .catch((error) => {
        console.error('Error initializing Table:', error);
      });

    })
    .catch((error) => {
      console.error('Error initializing database:', error);
    });

    //获取离线消息

    StorageUtil.getItem(CommonConstant.OFFLINE_MESSAGE_SEQ).then(res => {
      var finalRes:number = 0;
      if(res !== null) {
        finalRes = parseInt(res); 
      }

      store.requestOfflineMessageList(finalRes + 1, (setList: PrivateChatMessage[]) => {
        console.log("获取到离线消息，需要将离线消息持久化到本地数据库:", setList);
  
        if(setList.length !== 0) {
        //遍历所有离线消息，并持久化到APP本地
        setList.forEach(element => {
  
          try {
            //将消息持久化到本地数据库SQLite中
            DatabaseHelper.executeQuery("INSERT INTO " + CommonConstant.IM_PRIVATE_CHAT_TABLE + " (content_id, owner_member_id, from_member_id, to_member_id, body) VALUES (?, ?, ?, ?, ?)", [
              element.data.contentId,
              element.data.fromMemberId,
              element.data.fromMemberId,
              element.data.toMemberId,
              JSON.stringify(element),
            ])
              .then((res) => {
                console.log('record add success', res);
              })
              .catch((error) => {
                console.error('record add fail:', error);
              });
          } catch (error) {
            console.log("error输出:", error);
          }
        });
        
        //记录最后的序列号
        const lastElement = setList[setList.length - 1];
        console.log("这波离线消息最新的序列号", lastElement.data.sequence);
  
        StorageUtil.setItem(CommonConstant.OFFLINE_MESSAGE_SEQ, lastElement.data.sequence.toString());
        }
      });
    });
    

  }, []);

  const handleOpen = () => {
    ChatWebSocket.login();
  }

  const handleMessage = (message: any) => {
    const chat: PrivateChatMessage = JSON.parse(message);
    console.log("接收到的消息:", chat);

    if (chat.command === command.MessageCommand.PRIVATE_CHAT) {
      //将消息持久化到本地数据库SQLite中
      DatabaseHelper.executeQuery("INSERT INTO " + CommonConstant.IM_PRIVATE_CHAT_TABLE + " (content_id, owner_member_id, from_member_id, to_member_id, body) VALUES (?, ?, ?, ?, ?)", [
        chat.data.contentId,
        chat.data.fromMemberId,
        chat.data.fromMemberId,
        chat.data.toMemberId,
        message,
      ])
        .then(() => {
          console.log('record add success');
        })
        .catch((error) => {
          console.error('record add fail:', error);
        });
      return;
    }

    //接收到自己发送消息的ack，将ack消息存到数据库中
    if(chat.command === command.MessageCommand.PRIVATE_CHAT_ACK) {
      //将消息持久化到本地数据库SQLite中
      DatabaseHelper.executeQuery("INSERT INTO " + CommonConstant.IM_PRIVATE_CHAT_TABLE + " (content_id, owner_member_id, from_member_id, to_member_id, body) VALUES (?, ?, ?, ?, ?)", [
        chat.data.contentId,
        chat.data.toMemberId,
        chat.data.fromMemberId,
        chat.data.toMemberId,
        message,
      ])
        .then(() => {
          console.log('record add success');
        })
        .catch((error) => {
          console.error('record add fail:', error);
        });
      return;
    }


  }

  const onJobRefresh = () => {
    store.resetPage();
    store.requestTalkList();
  };

  const loadData = () => {
    store.requestTalkList();
  };

  const MyFooter = () => {
    return (
      <Text style={{
        textAlign: 'center',
        color: '#999',
        width: '100%',
        padding: 10,
        paddingBottom: 20,
        fontSize: 12
      }}>以上是30天内的联系人</Text>
    );
  };



    //首页职位item UI
    const renderItem = ({item, index}: {item:TalkEntity, index:number}) => {
      const memberInfo = JSON.parse(item.fromMemberInfo);
      const styles = StyleSheet.create({
        root: {
          backgroundColor: 'white',
          width: '100%',
          flexDirection: 'column',
          paddingVertical: 5
        },

        item: {
          width: SCREEN_WIDTH,
          backgroundColor: 'white',
          overflow: 'hidden'
        },

        fourLine: {
          flexDirection: 'row', // 将子组件排列在一行
          alignItems: 'center', // 垂直居中对齐
          justifyContent: 'space-between', // 在容器中水平分散对齐
          paddingHorizontal: 12,
          paddingTop: 7,
          paddingBottom: 6
        },

        fourLineHR: {
          flexDirection: 'row',
          alignItems: 'center',
        },

        fourLineHRAvatar: {
          width: 40,
          height: 40,
          resizeMode: 'cover',
          borderRadius: 100
        },

        fourLineHRText:{
          paddingLeft: 5,
          flexDirection: 'row',
          alignItems: 'center',
        },

        fourLineName:{
          fontSize: 13,
          color: CommonColor.fontColor
        },

        fourLineCompanyAbbrName:{
          fontSize: 11,
          paddingLeft: 4
        },

        fourLineHRReplyText:{
          color: CommonColor.normalGrey,
          fontSize: 11,
          paddingLeft: 5,
          paddingTop: 8
        },

        fourLineHRReplyText2: {
          color: CommonColor.fontColor,
          fontSize: 11,
          paddingLeft: 5,
          paddingTop: 8
        },


        messageTip: {
          flexDirection: 'row'
        },
      

        fourLineAddress: {
          flexDirection: 'row',
          paddingBottom: 20
        },

        fourLineAddressInfo: {
          fontSize: 10,
          color: CommonColor.normalGrey
        },

        fourLineAddressDistance: {
          fontSize: 10,
          color: CommonColor.normalGrey,
          paddingRight: 4
        }


      });

      return (
        <>
          <TouchableOpacity onPress={() => {
            //跳转到职位详情页
            navigation.push('ChatPage', {
              memberId: item.fromMemberId,
              avatar: memberInfo.avatar, 
              name: memberInfo.name, 
              jobTitle: memberInfo.jobTitle
            });

          }} activeOpacity={1} style={styles.item} key={index}>
            <View style={styles.root}>

              {/* HR信息与地址信息 */}
              <View style={styles.fourLine}>
                
                <View style={styles.fourLineHR}>
                  {/* 头像 */}
                  <Image style={styles.fourLineHRAvatar} source={{uri: memberInfo.avatar}}/>

                  <View style={{flexDirection: 'column'}}>
                    {/* HR信息 */}
                    <View style={styles.fourLineHRText}>
                      <Text style={styles.fourLineName}>{memberInfo.name}</Text>
                      <Text style={styles.fourLineCompanyAbbrName}>{memberInfo.companyAbbrName  + "·" +  memberInfo.jobTitle}</Text>
                    </View>

                    <View style={styles.messageTip}>
                      <Text style={styles.fourLineHRReplyText}>[新招呼]</Text>
                      <Text style={styles.fourLineHRReplyText2}>您好，我是负责TT公司招聘的X老板...</Text>
                    </View>
                  </View>

                </View>

                <View style={styles.fourLineAddress}>
                  <Text style={styles.fourLineAddressInfo}>{item.createdAt}</Text>
                </View>
              </View>

            </View>
          </TouchableOpacity>

        </>
  
        
  
      );
    }

  const renderRecommend = () => {
    return (
      <>
        {/** 主页视频列表 */}
        <FlowList 
          keyExtractor={(item: TalkEntity) => `${item.id}`}
          contentContainerStyle={styles.container} 
          style={styles.flatList} 
          data={store.talkList} 
          extraData={[store.refreshing]}
          renderItem={renderItem} 
          numColumns={1}
          refreshing={store.refreshing}
          onRefresh={onJobRefresh} 
          onEndReachedThreshold={0.2}
          onEndReached={loadData}
          ListFooterComponent={MyFooter}
        />
      </>
    );
  }

  const renderNearBy = () => {
    return (
      <View style={{alignContent: 'center', alignItems: 'center', flex: 1, flexDirection: 'row'}}>
        <Text>附近列表</Text>
      </View>
    );
  }

  const renderLatest = () => {
    return (
      <View style={{alignContent: 'center', alignItems: 'center', flex: 1, flexDirection: 'row'}}>
        <Text>最新列表</Text>
      </View>
    );
  }



  return (
    
    <View style={styles.root}>
      <View style={[{paddingTop: insets.top, height: insets.top + 75}]}>
      
        {/** 顶部标题栏 */}
        <View style={styles.topTitle}>

          {/** 职位类型 */}
          <Text style={styles.leftText}>聊天</Text>

          {/** 添加按钮和搜索按钮 */}
          <View style={styles.rightContainer}>
            <TouchableOpacity activeOpacity={1}>
              <Feather style={styles.rightIcon} name="bell" />
            </TouchableOpacity>

            <TouchableOpacity activeOpacity={1}>
              <AntDesign style={styles.rightIcon} name="setting" />
            </TouchableOpacity>
          </View>
        </View>


      <TitleBar tab={0} onAddButtonPress={(event: GestureResponderEvent) => {
        }} onTabChanged={(tab: number) => { setIndex(tab); }}/>
      </View>

      {index === 0 ? renderRecommend() : renderNearBy()}
    </View>
    
  );

})

const styles = StyleSheet.create({
  root: {
    width: '100%',
    height: '100%',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'white',
  },

  flatList: {
    width: '100%',
    height: '100%',
    backgroundColor: CommonColor.normalBg
  },

  topTitle: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    backgroundColor: 'white'
  },

  leftText: {
    flex: 1,
    textAlign: 'left',
    fontSize: 25,
    fontWeight: '500',
    color: 'black'
  },

  rightContainer: {
    flex: 1,
    flexDirection: 'row',
    justifyContent: 'flex-end',
  },

  rightIcon: {
    fontSize: 18,
    color: 'black',
    paddingLeft: 10
  },

  container: {
    paddingTop: 6
  },
  
});