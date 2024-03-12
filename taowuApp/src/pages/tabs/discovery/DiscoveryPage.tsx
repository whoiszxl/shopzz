import { StyleSheet, Text, View, Dimensions, Image, StatusBar } from 'react-native'
import React, { useEffect, useState } from 'react'
import { useLocalStore, observer } from 'mobx-react';
import Feather from 'react-native-vector-icons/Feather';
import AntDesign from 'react-native-vector-icons/AntDesign';
import Ionicons from 'react-native-vector-icons/Ionicons';
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
import LinearGradient from 'react-native-linear-gradient';
import { CommonLogo } from '../../../common/CommonLogo';


const {width:SCREEN_WIDTH} = Dimensions.get('window');


interface Props {  
  leftTopText: string;
  leftBottomText: string;
  iconImage: any;
}  

export default observer(() => {
  const insets = useSafeAreaInsets();

  const navigation = useNavigation<StackNavigationProp<any>>();

  const [index, setIndex] = useState<number>(0);

  useEffect(() => {

  }, []);

  const MyComponent: React.FC<Props> = ({
    leftTopText,
    leftBottomText,
    iconImage
  }) => {

    const styles = StyleSheet.create({
      container: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
        paddingHorizontal: 10,
        paddingVertical: 15,
        backgroundColor: CommonColor.whiteBg,
        marginHorizontal: 7,
        borderRadius: 2
      },
      leftColumn: {
        flex: 1,
      },
      rightColumn: {
        flexDirection: 'row',
        alignItems: 'center',
      },
      titleText: {
        fontSize: 13,
        fontWeight: 'bold',
        color: CommonColor.fontColor
      },
      subText: {
        paddingTop: 4,
        fontSize: 10,
        color: CommonColor.deepGrey
      },

      iconImage: {
        width: 35,
        height: 35
      },
      iconEnter: {
        paddingLeft: 10
      }
    });
    return (
      <View style={styles.container}>
        <View style={styles.leftColumn}>
          <Text style={styles.titleText}>{leftTopText}</Text>
          <Text style={styles.subText}>{leftBottomText}</Text>
        </View>
        <View style={styles.rightColumn}>
          <Image source={iconImage} style={styles.iconImage} />

          <Ionicons style={styles.iconEnter} name={CommonLogo.Ionicons.chevron_forward_outline} size={16} color={CommonColor.normalGrey} />
        </View>
      </View>
    );
  }; 

  return (
    
    <View style={styles.container}>
      <View style={{height: (StatusBar.currentHeight || 0) + 50, width: '100%', backgroundColor: 'white'}}>

          <View style={styles.titleAdLayout}>
            <View style={styles.leftContainer}>
              <Text style={styles.leftText}>探索</Text>
            </View>
          </View>
      </View>


      <View style={styles.bodyContainer}>
        <MyComponent
          leftTopText="消息中心"
          leftBottomText="收发消息 | 查看物流 | 平台通知 | 与淘物APP客服交流"
          iconImage={require('../../../assets/icons/discovery-1.png')}
        />

        <View style={{height: 5}}/>

        <MyComponent
          leftTopText="鉴别服务"
          leftBottomText="专业鉴别 | 帮你分辨真与假 淘物有保障"
          iconImage={require('../../../assets/icons/discovery-2.png')}
        />

        <View style={{height: 5}}/>

        <MyComponent
          leftTopText="玩一玩"
          leftBottomText="签到兑礼 | 幸运抽奖 超多礼品 等你来拿"
          iconImage={require('../../../assets/icons/discovery-3.png')}
        />
        
        <View style={{height: 5}}/>

        <MyComponent
          leftTopText="买卖闲置"
          leftBottomText="逐渐查验 | iPhone 15 Pro Max 低至8600元"
          iconImage={require('../../../assets/icons/discovery-4.png')}
        />

        <View style={{height: 5}}/>

        <MyComponent
          leftTopText="焕新洗护"
          leftBottomText="春季焕新 | 鞋服洗护单件29.9元起"
          iconImage={require('../../../assets/icons/discovery-5.png')}
        />

        <View style={{height: 5}}/>

        <MyComponent
          leftTopText="借钱备用金"
          leftBottomText="最高20万 | 升舱的钱我来出！"
          iconImage={require('../../../assets/icons/discovery-6.png')}
        />
      </View>
    </View>
    
  );

})

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  

  labelStyle: {
    fontSize: 12,
    color: 'black',
  },
  pager: {
    flex: 1,
  },

  titleAdLayout: {
    paddingTop: StatusBar.currentHeight || 0,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 10,
  },

  leftContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  leftText: {
    fontSize: 20,
    fontWeight: 'bold',
    color: CommonColor.fontColor,
    paddingTop: 15
  },
  rightContainer: {
    alignItems: 'center',
  },
  rightText: {
    marginTop: 0,
    fontSize: 10,
    fontWeight: 'bold',
    color: CommonColor.mainColorTwoDeep
  },

  bodyContainer: {
    paddingTop: 5
  },

  messageContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: 16, 
  },


  leftColumn: {
    flex: 1,
  },  
  rightColumn: {
    flex: 1,
    alignItems: 'flex-end', // 图标靠右对齐
  },
  text: {
    marginBottom: 4, // 根据需要调整文本之间的间距
  },
})