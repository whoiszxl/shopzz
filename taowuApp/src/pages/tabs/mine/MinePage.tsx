import { Dimensions, Image, LayoutChangeEvent, RefreshControl, ScrollView, StatusBar, StyleSheet, Text, TouchableWithoutFeedback, View } from 'react-native'
import React, { Component, useEffect, useState, useRef } from 'react'
import { TouchableOpacity } from 'react-native-gesture-handler';
import Ionicons from 'react-native-vector-icons/Ionicons';
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
import Icon from 'react-native-vector-icons/Ionicons';

 

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

  const renderHeadButton = () => {
    const buttonStyles = StyleSheet.create({
      titleLayout: {
          width: '100%',
          height: 48,
          flexDirection: 'row',
          alignItems: 'center',
          marginTop: 30,
          marginRight: 10
      }
    });
    return (
      <View style={buttonStyles.titleLayout}>
               
        <View style={{ flex: 1 }} />
        <Ionicons name="swap-horizontal-outline" size={18} color={'white'} onPress={() => {
          navigation.navigate('BecomeBossPage', {'is_toutou': store.memberInfo.isToutou, 'page':'MinePage' })
        }} />
        <Ionicons style={{paddingLeft: 10}} name="scan-outline" size={18} color={'white'} onPress={() => {

        }} />

        
        <Ionicons style={{paddingLeft: 10}} name="menu-outline" size={18} color={'white'} onPress={() => {
              mineSideMenuRef.current?.show();  
        }} />
      </View>
    );
  }

  const renderInfo = () => {
    const styles = StyleSheet.create({
        avatarLayout: {
            width: '100%',
            flexDirection: 'row',
            alignItems: 'flex-end',
            paddingLeft: 16,
            paddingBottom: 10
        },
        avatarImg: {
            width: 70,
            height: 70,
            resizeMode: 'cover',
            borderRadius: 48,
            borderWidth: 1,
            borderColor: 'white'
        },
        nameLayout: {
            marginLeft: 18,
        },
        nicknameText: {
            fontSize: 18,
            color: 'white',
            fontWeight: 'bold',
        },
        idLayout: {
            flexDirection: 'row',
            alignItems: 'center',
            marginTop: 8,
            marginBottom: 18,
        },
        idText: {
            fontSize: 12,
            color: 'white',
            paddingRight: 2
        }
    });


    return (
      <View style={{width: "100%"}} onLayout={(e: LayoutChangeEvent) => {
        const { height } = e.nativeEvent.layout;
        setBgImgHeight(height);
      }}>

        <View style={styles.avatarLayout}>

          {store.memberInfo.avatar ? <Image style={styles.avatarImg} source={{uri: store.memberInfo.avatar }} />: <></>}
          

          <View style={styles.nameLayout}>
              <Text style={styles.nicknameText}>{store.memberInfo.fullName}</Text>
              <View style={styles.idLayout}>
                  <Text style={styles.idText}>在线简历</Text>
                                            
                  {/** 二维码logo */}
                  <Ionicons name="create-outline" size={12} color="white"/>
              </View>
          </View>
        </View>
                
      </View>


    );
  }

  const renderOnlineResumeReport = () => {
    const styles = StyleSheet.create({
      root: {
        width: '98%',
        flexDirection: 'column',
        alignItems: 'center',
        backgroundColor: 'white',
        borderRadius: 12,
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
      }
    });


    const menus = [
      <View style={styles.menuItemLayout}>
        <View style={styles.menuItemTitleLayout}>
          <View style={{flexDirection: 'row'}}>
            <View style={[styles.tagLayout]}>
              <Text style={styles.addressCommonText}>待优化</Text>
            </View>
            <Text style={styles.menuItemTitleMain}>{'个人优势 >'}</Text>
          </View>
          <Text style={styles.menuItemTitleSub}>请补充亮点优势</Text>
        </View>
      </View>,

      <View style={styles.menuItemLayout}>
        <View style={styles.menuItemTitleLayout}>
          <View style={{flexDirection: 'row'}}>
            <View style={[styles.tagLayout]}>
              <Text style={styles.addressCommonText}>待优化</Text>
            </View>
            <Text style={styles.menuItemTitleMain}>{'个人优势 >'}</Text>
          </View>
          <Text style={styles.menuItemTitleSub}>个人优势亮点不突出，建议修改</Text>
        </View>
      </View>,

      <View style={styles.menuItemLayout}>
        <View style={styles.menuItemTitleLayout}>
          <View style={{flexDirection: 'row'}}>
            <Text style={styles.menuItemTitleMain}>{'完整简历报告'}</Text>
          </View>
          <Text style={styles.menuItemTitleSub}>立即查看</Text>
        </View>
      </View>,
      
    ];

    return (
      <View style={styles.root}>

        {/** 计数信息 */}
        <View style={styles.countLayout}>
          <TouchableOpacity style={styles.countLabel} onPress={openModal} >
            <Icon name='rocket' color={CommonColor.mainColor} size={14}/>
            <Text style={styles.countNumberText}>在线简历诊断86分</Text>
          </TouchableOpacity>
        </View>


        <View style={styles.customMenuLayout}>
          <MenuBar menus={menus} />
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
        color: CommonColor.deepGrey, 
        fontSize: 10,
        paddingTop: 2
      },

      fontCount: {
        fontWeight: 'bold',
        fontSize: 16,
        color: CommonColor.fontColor
      }
    });

    return (
      <View style={styles.root}>
        <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
          <Text style={styles.fontCount}>11</Text>
          <Text style={styles.fontText}>沟通过</Text>
        </View>

        <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
          <Text style={styles.fontCount}>21</Text>
          <Text style={styles.fontText}>已投简历</Text>
        </View>

        <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
          <Text style={styles.fontCount}>21</Text>
          <Text style={styles.fontText}>待面试</Text>
        </View>

        <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
          <Text style={styles.fontCount}>81</Text>
          <Text style={styles.fontText}>收藏</Text>
        </View>
      </View>
    );
  }

  const renderVIPLabel = () => {
    const styles = StyleSheet.create({
      root: {
        marginTop: 4,
        width: '98%',
        flexDirection: 'row',
        alignItems: 'center',
        borderRadius: 5,
        paddingVertical: 8,
        justifyContent: 'space-between',
        height: 70,
        zIndex: 10
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
        color: CommonColor.deepGrey, 
        fontSize: 10,
        paddingTop: 2
      },

      fontCount: {
        fontWeight: 'bold',
        fontSize: 16,
        color: CommonColor.fontColor
      },
      backgroundImage: {
        width: '100%', // 图片宽度占满父容器
        height: '100%', // 图片高度占满父容器
        borderRadius: 5, // 设置圆角
      },

      mainTitle: {
        color: 'white',
        fontSize: 13,
        alignItems: 'center'
      },

      subTitle: {
        color: 'white',
        fontSize: 10,
        alignItems: 'center'
      },

      levelupButton: {
        backgroundColor: '#ffbc66', 
        borderRadius: 120,
        paddingHorizontal: 10,
        paddingVertical: 2
      },

      levelupButtonText: {
        color: 'black',
        fontWeight: 'bold',
        fontSize: 10
      }
    });

    return (
  
        <ImageBackground style={styles.root} imageStyle={styles.backgroundImage} source={require('../../../assets/images/mine_bg3.jpg')} resizeMode='cover'>
              <View style={{paddingLeft: 10, paddingBottom: 10}}>
                <Text style={styles.mainTitle}>升级VIP 加速提升简历曝光</Text>
                <Text style={styles.subTitle}>搭配“竞争力分析”等9大权益，效果翻倍</Text>
              </View>

              <View style={{paddingRight: 10, paddingBottom: 10}}>
                <TouchableOpacity style={styles.levelupButton}>
                  <Text style={styles.levelupButtonText}>去升级</Text>
                </TouchableOpacity>
              </View>
        </ImageBackground>

    );
  }
  
  const renderRefreshLabel = () => {
    const styles = StyleSheet.create({
      root: {
        width: '98%',
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: 'white',
        borderRadius: 5,
        paddingHorizontal: 10,
        paddingVertical: 8,
        borderBottomColor: '#eee',
        marginTop: -20,
        height: 45,
        zIndex: 1
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
        color: CommonColor.deepGrey, 
        fontSize: 10,
        paddingTop: 2
      },

      fontCount: {
        fontWeight: 'bold',
        fontSize: 16,
        color: CommonColor.fontColor
      }
    });

    return (
      <View style={styles.root}>
        <View style={{flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', width: '50%'}}>
          <View style={{flexDirection: 'row', alignItems: 'center'}}>
            <Icon name='refresh' size={14} color={CommonColor.mainColor}/>
            <Text style={{fontSize: 11, fontWeight: 'bold', color: CommonColor.fontColor, paddingLeft: 2}}>简历刷新</Text>
          </View>

          <Text style={{fontSize: 10, color: CommonColor.normalGrey, paddingRight: 10}}>{'提升曝光 >'}</Text>
        </View>
        <Text style={{color: CommonColor.normalGrey}}>|</Text>
        <View style={{flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', width: '50%', paddingLeft: 10}}>
          <View style={{flexDirection: 'row', alignItems: 'center'}}>
            <Icon name='trending-up' size={14} color={CommonColor.mainColor}/>
            <Text style={{fontSize: 11, fontWeight: 'bold', color: CommonColor.fontColor, paddingLeft: 2}}>竞争力分析</Text>
          </View>

          <Text style={{fontSize: 10, color: CommonColor.normalGrey}}>{'洞悉竞争 >'}</Text>
        </View>
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
        borderRadius: 12,
        borderBottomColor: '#eee',
        marginTop: 6
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
        fontSize: 11,
        color: CommonColor.fontColor
      },

      subTitle: {
        fontSize: 10,
        color: CommonColor.normalGrey
      },

      functionIcon: {
        color: '#314486',
        fontSize: 22
      }
    });


    return (
      <View style={styles.root}>

        <View style={styles.countLayout}>
          <TouchableOpacity style={styles.countLabel} onPress={openModal} >
            <Text style={styles.countNumberText}>常用功能</Text>
          </TouchableOpacity>
        </View>


        <View style={styles.customMenuLayout}>
          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <TouchableOpacity onPress={() => { navigation.push("OnlineResumePage") }} style={{alignItems: 'center'}}>
              <Icon style={styles.functionIcon} name='newspaper'/>
              <Text style={styles.mainTitle}>在线简历</Text>
              <Text style={styles.subTitle}>立即修改</Text>
            </TouchableOpacity>
            
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <TouchableOpacity onPress={() => { navigation.push("AttachmentResumePage") }} style={{alignItems: 'center'}}>
              <Icon style={styles.functionIcon} name='folder-open-sharp'/>
              <Text style={styles.mainTitle}>附件简历</Text>
              <Text style={styles.subTitle}>制作/上传</Text>
            </TouchableOpacity>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.functionIcon} name='heart-circle'/>
            <Text style={styles.mainTitle}>求职意向</Text>
            <Text style={styles.subTitle}>选择你的意向</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.functionIcon} name='game-controller-sharp'/>
            <Text style={styles.mainTitle}>道具商城</Text>
            <Text style={styles.subTitle}>T币/其他</Text>
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
        borderRadius: 12,
        borderBottomColor: '#eee',
        marginTop: 6
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
            <Text style={styles.countNumberText}>其他功能</Text>
          </TouchableOpacity>
        </View>


        <View style={styles.customMenuLayout}>
          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='server-outline'/>
            <Text style={styles.otherTitle}>面试刷题</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='search-circle-outline'/>
            <Text style={styles.otherTitle}>薪酬查询</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='sunny-outline'/>
            <Text style={styles.otherTitle}>高薪机会</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='earth-outline'/>
            <Text style={styles.otherTitle}>规则中心</Text>
          </View>

        
        </View>

        <View style={styles.customMenuLayout}>
          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='nutrition-outline'/>
            <Text style={styles.otherTitle}>众裁庭</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='color-filter-outline'/>
            <Text style={styles.otherTitle}>我的客服</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='cog-outline'/>
            <Text style={styles.otherTitle}>防骗指南</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='newspaper-outline'/>
            <Text style={styles.otherTitle}>隐私规则</Text>
          </View>

        
        </View>

        <View style={styles.customMenuLayout}>
          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='planet-outline'/>
            <Text style={styles.otherTitle}>精选公司</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='pricetags-outline'/>
            <Text style={styles.otherTitle}>TT公益</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='rocket-outline'/>
            <Text style={styles.otherTitle}>关于</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon style={styles.otherFunctionIcon} name='person-outline'/>
            <Text style={styles.otherTitle}>个人主页</Text>
          </View>

        
        </View>


      </View>
    );
  }

  return (
    <View style={styles.root}>
      <StatusBar translucent backgroundColor={'transparent'} />

      <Image style={[styles.bgImg, { height: bgImgHeight + 128 }]} source={mine_bg} />

      {/** 头部个人信息 */}
      {renderHeadButton()}


      {/** 用户基本信息：头像，昵称，账号登 */}
      {renderInfo()}

      {/** 在线简历诊断栏目 */}
      {renderOnlineResumeReport()}


      {/** 在线简历诊断栏目 */}
      {renderCounterLabel()}

      {renderVIPLabel()}

      {/** 简历刷新，竞争力分析 */}
      {renderRefreshLabel()}

      {/** 常用功能 */}
      {renderNormalFunction()}

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
    </View>
  );
});

const styles = StyleSheet.create({
  root: {
    height: '100%',
    width: '100%',
    alignContent: 'center',
    alignItems: 'center'
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