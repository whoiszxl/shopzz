import { Dimensions, Image, LayoutChangeEvent, RefreshControl, ScrollView, StatusBar, StyleSheet, Text, TouchableWithoutFeedback, View } from 'react-native'
import React, { Component, useEffect, useState, useRef } from 'react'
import { TouchableOpacity } from 'react-native-gesture-handler';
import Ionicons from 'react-native-vector-icons/Ionicons';
import Feather from 'react-native-vector-icons/Feather';
import AntDesign from 'react-native-vector-icons/AntDesign';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import { observer, useLocalStore } from 'mobx-react';

import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';

import { Modal } from 'react-native';

import mine_bg from '../../../assets/images/mine_bg2.jpg';
import star_bg from '../../../assets/images/star_bg.png';
import { ImageBackground } from 'react-native';
import StorageUtil from '../../../utils/StorageUtil';
import { CommonConstant } from '../../../common/CommonConstant';
import MineStore from '../../../stores/MineStore';
import MineSideMenu, { MineSideMenuRef } from './components/MineSideMenu';
import { CommonColor } from '../../../common/CommonColor';
import MenuBar from '../../../components/MenuBar';
import LinearGradient from 'react-native-linear-gradient';

 

const { width: SCREEN_WIDTH } = Dimensions.get('window');

export default observer(() => {

  const [bgImgHeight, setBgImgHeight] = useState<number>(500);

  const [tabIndex, setTabIndex] = useState<number>(0);

  const store = useLocalStore(() => new MineStore());

  const navigation = useNavigation<StackNavigationProp<any>>();

  const mineSideMenuRef = useRef<MineSideMenuRef>(null);

  const [modalVisible, setModalVisible] = useState(false);

  const openModal = () => {
    setModalVisible(true);
  };

  const closeModal = () => {
    setModalVisible(false);
  };


  // 检查本地缓存是否存在 token
  const checkToken = async () => {
    try {
      const token = await StorageUtil.getItem(CommonConstant.TOKEN);
      return token;
    } catch (error) {
      console.log('Error retrieving token from AsyncStorage:', error);
      return null;
    }
  };

  useEffect(() => {
    //初始化检查本地token是否存在，不存在则跳转到登录页面
    // checkToken().then((token) => {
    //   if(token) {
    //     store.requestAll().then(res => {
    //       if(res.code !== 0) {
    //         navigation.navigate('LoginPage');
    //       }
    //     });
    //   }else {
    //     navigation.navigate('LoginPage');
    //   }
    // });    
  }, []);



  const renderInfo = () => {
    const styles = StyleSheet.create({
        avatarLayout: {
            width: '100%',
            flexDirection: 'row',
            alignItems: 'center',
            paddingLeft: 16,
            paddingBottom: 10
        },
        avatarImg: {
            width: 55,
            height: 55,
            resizeMode: 'cover',
            borderRadius: 48,
            borderWidth: 1,
            borderColor: 'white'
        },
        nameLayout: {
            marginLeft: 11,
        },
        nicknameText: {
            fontSize: 15,
            color: CommonColor.fontColor,
            fontWeight: 'bold',
        },
        idLayout: {
            flexDirection: 'row',
            alignItems: 'center',
            marginTop: 4,
        },
        idText: {
            fontSize: 11,
            color: CommonColor.deepGrey,
            paddingRight: 2
        },

        countLabelContainer: {
          flexDirection: 'row',
          justifyContent: 'space-between',
        },
        countLabelItem: {
          flex: 1,
          marginHorizontal: 5,
          alignItems: 'center',
        },
        countLabelNumberText: {
          fontWeight: 'bold',
          fontSize: 14,
          color: CommonColor.fontColor,
          textAlign: 'center',
        },

        countLabelTitleText: {
          fontWeight: 'normal',
          fontSize: 10,
          color: CommonColor.normalGrey,
          textAlign: 'center',
        },
    });


    return (
      <View style={{width: "100%", backgroundColor: 'white', paddingBottom: 11}}>

        <View style={styles.avatarLayout}>

          {<Image style={styles.avatarImg} 
            source={{uri: store.memberInfo.avatar ?? 'http://qiniu.whoiszxl.com/default-avatar.png' }} />}
          

          <View style={styles.nameLayout}>
              <Text style={styles.nicknameText}>{store.memberInfo.fullName ?? '未登录'}</Text>
              <View style={styles.idLayout}>
                  <Text style={styles.idText}>填写你的个性签名吧</Text>
              </View>
          </View>
        </View>

        <View style={styles.countLabelContainer}>
            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>0</Text>
              <Text style={styles.countLabelTitleText}>获赞与收藏</Text>
            </View>
            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>11</Text>
              <Text style={styles.countLabelTitleText}>粉丝</Text>
            </View>
            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>10</Text>
              <Text style={styles.countLabelTitleText}>关注</Text>
            </View>
            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>8</Text>
              <Text style={styles.countLabelTitleText}>动态</Text>
            </View>
          </View>
                
      </View>


    );
  }

  const renderCounterLabel = () => {
    const styles = StyleSheet.create({
      root: {
        marginTop: 4,
        width: '98%',
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: 'white',
        borderRadius: 5,
        paddingHorizontal: 10,
        paddingVertical: 8,
        borderBottomColor: '#eee',
      },
      subRoot: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: 'white',
        borderRadius: 5,
        borderBottomColor: '#eee',
      },
      countLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 10,
        paddingLeft: 10,
        alignItems: 'center',

      },

      countLabel: {
        flexDirection: 'row',
        alignContent: 'center',
        alignItems: 'center'
      },

      countNumberText: {
        fontSize: 12,
        fontWeight: '500',
        color: 'black',
        paddingLeft: 3
      },

      addressCommonText: {
        fontSize: 8,
        color: '#eb6d6d'
      },

      tagLayout: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: CommonColor.tagBg,
        paddingHorizontal: 4,
        paddingVertical: 1,
        borderRadius: 3,
        marginRight: 3
      },

      customMenuLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 10,
        paddingLeft: 10,
        alignItems: 'center',
        paddingBottom: 10
      },

      menuItemLayout: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
      },

      menuItemTitleLayout: {
        paddingLeft: 4
      },

      menuItemTitleMain: {
        fontSize: 11,
        fontWeight: '500',
        color: 'black'
      },
      
      menuItemTitleSub: {
        fontSize: 10,
        fontWeight: 'normal',
        color: 'grey',
        paddingTop: 3
      },

      fontText: {
        color: CommonColor.fontColor, 
        fontSize: 10,
        paddingTop: 2
      },

      fontCount: {
        fontWeight: 'bold',
        fontSize: 16,
        color: CommonColor.fontColor,
      },

      titleFontText: {
        fontWeight: '900',
        fontSize: 13,
        color: CommonColor.fontColor,
        fontStyle: 'italic',
      }
    });

    return (
      <View style={styles.root}>

          <View>
            <Text style={styles.titleFontText}>创作</Text>
            <Text style={styles.titleFontText}>中心</Text>
          </View>

          <View style={{marginLeft:11 ,width: 1, height: 27, backgroundColor: CommonColor.transparentGreyBg}} />
            
        <View style={styles.subRoot}>



          <View style={{flexDirection: 'column', flex:1, alignItems: 'center', marginLeft: 10}}>
            <Ionicons name='tv-outline' size={17} color={CommonColor.fontColor}/>
            <Text style={styles.fontText}>数据</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Ionicons name='ribbon-outline' size={17} color={CommonColor.fontColor}/>
            <Text style={styles.fontText}>活动</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <AntDesign name='pay-circle-o1' size={17} color={CommonColor.fontColor}/>
            <Text style={styles.fontText}>收益</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center', marginRight: 40}}>
            <AntDesign name='staro' size={17} color={CommonColor.fontColor}/>
            <Text style={styles.fontText}>淘物评价官</Text>
          </View>
        </View>
      </View>
      
    );
  }

  const renderVIPLabel = () => {
    const styles = StyleSheet.create({
      root: {
        width: '98%',
        flexDirection: 'row',
        alignItems: 'center',
        borderRadius: 5,
        paddingVertical: 8,
        justifyContent: 'space-between',
        height: 50
      },
      menuItem: {
        paddingHorizontal: 5,
        paddingVertical: 8,
        marginRight: 10,
        borderRadius: 4,
        width: '100%',
      },
      tabbar: {
        height: '100%',
        borderBottomColor: CommonColor.transparentGreyBg,
        borderBottomWidth: 0.4,
      },
      tab: {
        paddingHorizontal: 5,
      },
    
      labelStyle: {
        fontSize: 12,
        color: 'black',
      },
      pager: {
        flex: 1,
      },
    
      titleAdLayout: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
      },
    
      leftContainer: {
        flexDirection: 'row',
        alignItems: 'center',
      },
      imageIcon: {
        width: 24,
        height: 24,
        marginRight: 5,
        borderRadius: 2
      },
      leftText: {
        fontSize: 12,
        fontWeight: 'bold',
        color: CommonColor.fontColor
      },
      rightContainer: {
        alignItems: 'center',
      },
      rightText: {
        marginTop: 0,
        fontSize: 10,
        fontWeight: 'bold',
        color: CommonColor.fontColor
      },

      
    });

    return (
  
        <View style={styles.root}>
          <LinearGradient
            style={styles.menuItem} 
            colors={['rgba(1, 194, 195, 0.1)', 'rgba(1, 194, 195, 0.04)']}
            start={{x: 0, y: 0.5}}
            end={{x: 1, y: 0.5}}  
          >
            <View style={styles.titleAdLayout}>
              <View style={styles.leftContainer}>
                <Image source={require('../../../assets/images/ic_launcher_small.png')} style={styles.imageIcon} />
                <Text style={styles.leftText}>借钱 | 最高20万借钱额度</Text>
                <Ionicons name="chevron-forward-outline" size={18} color={CommonColor.fontColor} />
              </View>

              <TouchableOpacity style={styles.rightContainer}>
                <Text style={styles.rightText}>{'立即领取  >'}</Text>
              </TouchableOpacity>
            </View>
          </LinearGradient>
        </View>

    );
  }


  const renderNormalFunction = () => {
    const styles = StyleSheet.create({
      root: {
        width: '98%',
        flexDirection: 'column',
        alignItems: 'center',
        backgroundColor: 'white',
        borderRadius: 3,
        borderBottomColor: '#eee',
        marginTop: 0
      },
      countLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 10,
        paddingLeft: 10,
        alignItems: 'center',

      },

      countLabel: {
        flexDirection: 'row',
        alignContent: 'center',
        alignItems: 'center'
      },

      countNumberText: {
        fontSize: 12,
        fontWeight: '500',
        color: 'black',
        paddingLeft: 3
      },

      addressCommonText: {
        fontSize: 8,
        color: '#eb6d6d'
      },

      tagLayout: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: CommonColor.tagBg,
        paddingHorizontal: 4,
        paddingVertical: 1,
        borderRadius: 3,
        marginRight: 3
      },

      provinceCity: {
        marginLeft: 6
      },

      customMenuLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 10,
        paddingLeft: 10,
        alignItems: 'center',
        paddingBottom: 10
      },

      menuItemLayout: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
      },

      menuItemTitleLayout: {
        paddingLeft: 4
      },

      menuItemTitleMain: {
        fontSize: 11,
        fontWeight: '500',
        color: 'black'
      },
      
      menuItemTitleSub: {
        fontSize: 10,
        fontWeight: 'normal',
        color: 'grey',
        paddingTop: 3
      },
      mainTitle: {
        fontSize: 10,
        color: CommonColor.deepGrey,
        paddingTop: 2
      },

      subTitle: {
        fontSize: 10,
        color: CommonColor.normalGrey
      },

      functionIcon: {
        color: CommonColor.fontColor,
        fontSize: 22
      }
    });


    return (
      <View style={styles.root}>

        <View style={styles.countLayout}>
          <TouchableOpacity style={styles.countLabel} onPress={openModal} >
            <Text style={styles.countNumberText}>订单</Text>
          </TouchableOpacity>
        </View>


        <View style={styles.customMenuLayout}>
          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <TouchableOpacity onPress={() => { navigation.push("OnlineResumePage") }} style={{alignItems: 'center'}}>
              <Ionicons style={styles.functionIcon} name='card-outline'/>
              <Text style={styles.mainTitle}>待付款</Text>
            </TouchableOpacity>
            
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <TouchableOpacity onPress={() => { navigation.push("AttachmentResumePage") }} style={{alignItems: 'center'}}>
              <Feather style={styles.functionIcon} name='send'/>
              <Text style={styles.mainTitle}>待发货</Text>
            </TouchableOpacity>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Feather style={styles.functionIcon} name='truck'/>
            <Text style={styles.mainTitle}>待收货</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <MaterialCommunityIcons style={styles.functionIcon} name='comment-processing-outline'/>
            <Text style={styles.mainTitle}>待评价</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <MaterialCommunityIcons style={styles.functionIcon} name='swap-horizontal-circle-outline'/>
            <Text style={styles.mainTitle}>退款/售后</Text>
          </View>
        </View>


      </View>
    );
  }


  const renderWallet = () => {
    const styles = StyleSheet.create({
      root: {
        width: '98%',
        flexDirection: 'column',
        alignItems: 'center',
        backgroundColor: 'white',
        borderRadius: 3,
        borderBottomColor: '#eee',
        marginTop: 6
      },
      countLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 10,
        paddingHorizontal: 10,
        alignItems: 'center',
        justifyContent: 'space-between'

      },

      countLabel: {
        flexDirection: 'row',
        alignContent: 'center',
        alignItems: 'center'
      },

      walletTitleText: {
        fontSize: 12,
        fontWeight: 'bold',
        color: CommonColor.fontColor,
        paddingLeft: 3
      },

      walletTitleLeftText: {
        fontSize: 10,
        color: CommonColor.normalGrey,
        paddingLeft: 3
      },

      addressCommonText: {
        fontSize: 8,
        color: '#eb6d6d'
      },

      tagLayout: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: CommonColor.tagBg,
        paddingHorizontal: 4,
        paddingVertical: 1,
        borderRadius: 3,
        marginRight: 3
      },

      provinceCity: {
        marginLeft: 6
      },

      customMenuLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 10,
        paddingLeft: 10,
        alignItems: 'center',
        paddingBottom: 10,
      },

      menuItemLayout: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
      },

      menuItemTitleLayout: {
        paddingLeft: 4
      },

      menuItemTitleMain: {
        fontSize: 11,
        fontWeight: '500',
        color: 'black'
      },
      
      menuItemTitleSub: {
        fontSize: 10,
        fontWeight: 'normal',
        color: 'grey',
        paddingTop: 3
      },
      mainTitle: {
        fontSize: 11,
        color: CommonColor.fontColor
      },

      subTitle: {
        fontSize: 10,
        color: CommonColor.normalGrey
      },

      otherTitle: {
        fontSize: 10,
        color: CommonColor.deepGrey
      },

      otherFunctionIcon: {
        fontSize: 22,
        color: '#5e5e5e'
      },

      countLabelContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        paddingHorizontal: 10,
        paddingTop: 7,
        paddingBottom: 11
      },
      countLabelItem: {
        flex: 1,
        marginHorizontal: 5,
        alignItems: 'center',
      },
      countLabelNumberText: {
        fontWeight: 'bold',
        fontSize: 14,
        color: CommonColor.fontColor,
        textAlign: 'center',
      },

      countLabelTitleText: {
        fontWeight: 'normal',
        fontSize: 10,
        color: CommonColor.normalGrey,
        textAlign: 'center',
      },
    });


    return (
      <View style={styles.root}>

        <View style={styles.countLayout}>
          <TouchableOpacity style={styles.countLabel} onPress={openModal} >
            <Text style={styles.walletTitleText}>钱包</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.countLabel} onPress={openModal} >
            <Text style={styles.walletTitleLeftText}>{'心意礼品卡  >'}</Text>
          </TouchableOpacity>
        </View>


        <View style={styles.countLabelContainer}>
            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>0</Text>
              <Text style={styles.countLabelTitleText}>优惠券</Text>
            </View>
            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>11</Text>
              <Text style={styles.countLabelTitleText}>借钱</Text>
            </View>
            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>10</Text>
              <Text style={styles.countLabelTitleText}>可分12期</Text>
            </View>
            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>8</Text>
              <Text style={styles.countLabelTitleText}>津贴</Text>
            </View>

            <View style={styles.countLabelItem}>
              <Text style={styles.countLabelNumberText}>8</Text>
              <Text style={styles.countLabelTitleText}>礼品卡</Text>
            </View>
        </View>

      


      </View>
    );
  }



  const renderOtherFunction = () => {
    const styles = StyleSheet.create({
      root: {
        width: '98%',
        flexDirection: 'column',
        alignItems: 'center',
        backgroundColor: 'white',
        borderRadius: 3,
        borderBottomColor: '#eee',
        marginTop: 6
      },
      countLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 10,
        paddingHorizontal: 10,
        alignItems: 'center',
        justifyContent: 'space-between'

      },

      countLabel: {
        flexDirection: 'row',
        alignContent: 'center',
        alignItems: 'center'
      },

      walletTitleText: {
        fontSize: 13,
        fontWeight: '500',
        color: 'black',
        paddingLeft: 3
      },

      walletTitleLeftText: {
        fontSize: 10,
        color: CommonColor.normalGrey,
        paddingLeft: 3
      },

      addressCommonText: {
        fontSize: 8,
        color: '#eb6d6d'
      },

      tagLayout: {
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: CommonColor.tagBg,
        paddingHorizontal: 4,
        paddingVertical: 1,
        borderRadius: 3,
        marginRight: 3
      },

      provinceCity: {
        marginLeft: 6
      },

      customMenuLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 10,
        alignItems: 'center',
        paddingBottom: 10,
      },

      menuItemLayout: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
      },

      menuItemTitleLayout: {
        paddingLeft: 4
      },

      menuItemTitleMain: {
        fontSize: 11,
        fontWeight: '500',
        color: 'black'
      },
      
      menuItemTitleSub: {
        fontSize: 10,
        fontWeight: 'normal',
        color: 'grey',
        paddingTop: 3
      },
      mainTitle: {
        fontSize: 11,
        color: CommonColor.fontColor
      },

      subTitle: {
        fontSize: 10,
        color: CommonColor.normalGrey
      },

      otherTitle: {
        fontSize: 10,
        color: CommonColor.deepGrey
      },

      otherFunctionIcon: {
        fontSize: 22,
        color: '#5e5e5e'
      }
    });


    return (
      <View style={styles.root}>

        <View style={styles.countLayout}>
          <TouchableOpacity style={styles.countLabel} onPress={openModal} >
            <Text style={styles.walletTitleText}>其他功能</Text>
          </TouchableOpacity>

          <TouchableOpacity style={styles.countLabel} onPress={openModal} >
            <Text style={styles.walletTitleLeftText}>{'查看更多  >'}</Text>
          </TouchableOpacity>
        </View>


        <View style={styles.customMenuLayout}>
          <MenuItem iconName="customerservice" title="客服中心" onPress={() => {console.log("客服中心");}}/>
          <MenuItem iconName="inbox" title="高价回收" onPress={() => {console.log("高价回收");}}/>
          <MenuItem iconName="bulb1" title="鉴别服务" onPress={() => {console.log("鉴别服务");}}/>
          <MenuItem iconName="tagso" title="天天领券" onPress={() => {console.log("天天领券");}}/>
          <MenuItem iconName="skin" title="鞋服洗护" onPress={() => {console.log("鞋服洗护");}}/>
        </View>

        <View style={styles.customMenuLayout}>
          <MenuItem iconName="isv" title="尺码管理" onPress={() => {console.log("尺码管理");}}/>
          <MenuItem iconName="staro" title="星星森林" onPress={() => {console.log("星星森林");}}/>
          <MenuItem iconName="mail" title="上上签" onPress={() => {console.log("上上签");}}/>
          <MenuItem iconName="calculator" title="0元抽奖" onPress={() => {console.log("0元抽奖");}}/>
          <MenuItem iconName="codepen" title="搭配日记" onPress={() => {console.log("搭配日记");}}/>
        </View>

        <View style={styles.customMenuLayout}>
          <MenuItem iconName="solution1" title="开票中心" onPress={() => {console.log("开票中心");}}/>
          <MenuItem iconName="smileo" title="学生专区" onPress={() => {console.log("学生专区");}}/>
          <MenuItem iconName="home" title="门店预约" onPress={() => {console.log("门店预约");}}/>
          <MenuItem iconName="notification" title="发售提醒" onPress={() => {console.log("发售提醒");}}/>
          <MenuItem iconName="bank" title="我的竞拍" onPress={() => {console.log("我的竞拍");}}/>
        </View>

      </View>
    );
  }

  const MenuItem = ({ iconName, title, onPress }: { iconName: string, title: string, onPress: any }) => {
    const styles = StyleSheet.create({
      otherTitle: {
        fontSize: 10,
        color: CommonColor.deepGrey,
        paddingTop: 2
      },

      otherFunctionIcon: {
        fontSize: 22,
        color: CommonColor.dark
      }
    });
    return (
      <View style={{ flexDirection: 'column', flex: 1, alignItems: 'center' }}>
        <TouchableOpacity style={{ flexDirection: 'column', alignItems: 'center' }} onPress={onPress}>
          <AntDesign style={styles.otherFunctionIcon} name={iconName} />
          <Text style={styles.otherTitle}>{title}</Text>
        </TouchableOpacity>
      </View>
    );
  };

  const renderTitleBar = () => {

    const styles = StyleSheet.create({
      menuItem: {
        paddingHorizontal: 5,
        paddingVertical: 8,
        marginRight: 10,
        borderRadius: 4,
        backgroundColor: 'rgba(255, 165, 0, 0.05)',
        width: '100%',
        height: (StatusBar.currentHeight || 0) + 50
      },
      tabbar: {
        height: '100%',
        borderBottomColor: CommonColor.transparentGreyBg,
        borderBottomWidth: 0.4,
      },
      tab: {
        paddingHorizontal: 5,
      },
    
      labelStyle: {
        fontSize: 12,
        color: 'black',
      },
      pager: {
        flex: 1,
      },

      titleAdLayout: {
        paddingTop: (StatusBar.currentHeight || 0) + 11,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        padding: 10,
      },
    
      leftContainer: {
        flexDirection: 'row',
        alignItems: 'center',
      },
      imageIcon: {
        width: 24,
        height: 24,
        marginRight: 5,
        borderRadius: 2
      },
      leftText: {
        fontSize: 15,
        fontWeight: 'bold',
        color: CommonColor.fontColor
      },
      rightContainer: {
        alignItems: 'center',
        flexDirection: 'row'
      },
      rightText: {
        marginTop: 0,
        fontSize: 10,
        fontWeight: 'bold',
        color: CommonColor.mainColorTwoDeep
      },
    });

    return (
      <View style={{height: (StatusBar.currentHeight || 0) + 40, width: '100%', backgroundColor: 'white'}}>

        <View style={styles.titleAdLayout}>
          <View style={styles.leftContainer}>
            <Ionicons name="scan-sharp" size={18} color={CommonColor.fontColor} />
          </View>

          <View style={styles.rightContainer}>
            <TouchableOpacity>
              <Ionicons size={18} name='gift-sharp' color={CommonColor.fontColor}/>
            </TouchableOpacity>

            <TouchableOpacity style={{paddingLeft: 8}}>
              <Ionicons size={18} name='settings-outline' color={CommonColor.fontColor}/>
            </TouchableOpacity>
          </View>


        </View>
    </View>
    );
  }


  return (
    <View style={styles.root}>
      {renderTitleBar()}

      <ScrollView contentContainerStyle={styles.scrollViewRoot}>
      

      {/** 用户基本信息：头像，昵称，账号登 */}
      {renderInfo()}

      {/** 在线简历诊断栏目 */}
      {renderCounterLabel()}

      {renderVIPLabel()}


      {/** 常用功能 */}
      {renderNormalFunction()}

      {renderWallet()}

      {/** 其他功能 */}
      {renderOtherFunction()}

      <MineSideMenu ref={mineSideMenuRef} />

      <Modal animationType='fade' transparent={true} visible={modalVisible} onRequestClose={closeModal}>
        <View style={styles.modalContainer}>

          
          <View style={styles.modalContent}>
            <Image style={styles.modalContentBg} source={star_bg}/>
            
            <Text style={styles.modalContentText}>{`"${store.memberInfo.nickName}"共获得 666 个赞`}</Text>

            <View style={styles.line} />

            <TouchableWithoutFeedback style={styles.modalContentConfirmTextLayout} onPress={closeModal}>
              <Text style={styles.modalContentConfirmText}>确认</Text>
            </TouchableWithoutFeedback>

          </View>
        </View>
      </Modal>
    </ScrollView>
    </View>
    
  );
});

const styles = StyleSheet.create({
  root: {
    height: '100%',
    width: '100%',
  },

  scrollViewRoot: {
    justifyContent: 'center', // 在垂直方向上居中内容  
    alignItems: 'center', // 在水平方向上居中内容  
  },
  bgImg: {
    position: 'absolute',
    top: 0,
    width: '100%',
    height: 600,
  },
  scrollView: {
    width: '100%',
  },

  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.3)',
  },
  modalContent: {
    backgroundColor: '#fff',
    alignItems: 'center',
  },

  modalContentBg: {
    width: 240,
    height: 170
  },

  modalContentText: {
    fontSize: 12,
    color: '#333333',
    paddingVertical: 20,

  },

  modalContentConfirmTextLayout: {
    width: 240,
    alignContent: 'center',
    alignItems: 'center'
  },

  modalContentConfirmText: {
    fontSize: 14,
    color: '#333333',
    fontWeight: 'bold',
    paddingVertical: 8,
    paddingHorizontal: 100
  },

  line: {
    width: 220,
    height: 0.5,
    backgroundColor: '#ddd',
    borderRadius: 1,
  },
});