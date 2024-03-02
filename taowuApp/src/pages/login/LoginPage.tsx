import React, { useState } from "react";
import { View, Image, StyleSheet, TouchableOpacity, Text, TextInput, LayoutAnimation } from "react-native";
import { Linking } from "react-native";

import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import Icon from 'react-native-vector-icons/Ionicons';
import AntDesign from 'react-native-vector-icons/AntDesign';

import FontAwesome from 'react-native-vector-icons/FontAwesome';

import icon_logo_main from '../../assets/images/logo_bg_transparent.png';
import icon_un_selection from '../../assets/icons/un_selection.png';
import icon_selection from '../../assets/icons/selection.png';

import MemberStore from "../../stores/MemberStore";
import { CommonColor } from "../../common/CommonColor";

export default () => {
  const navigation = useNavigation<StackNavigationProp<any>>();

  const [loginType, setLoginType] = useState<'quick'|'input'>('input');
  const [check, setCheck] = useState<boolean>(false);
  const [phone, setPhone] = useState<string>('11388889999');

  const onPressByLogin = async () => {
    const canLogin = phone?.length === 11 && check ? true : false;
    if(!canLogin) {
      return;
    }

    MemberStore.requestSendSmsCaptcha(phone, (success: boolean) => {
      if(success) {
        console.log("短信发送成功");
        console.log("MemberStore.loginToken: %s", MemberStore.loginToken);
        navigation.push('CheckSmsCaptchaPage', {loginToken: MemberStore.loginToken, phone: phone});
      }else {
        console.log("短信发送失败");
      }
    });
  }


  const renderInputLogin = () => {
    const styles = StyleSheet.create({
      root: {
        width: '100%',
        height: '100%',
        flexDirection: 'column',
        alignItems: 'flex-start',
        paddingHorizontal: 20,
      },

      phoneLoginTitle: {
        fontSize: 21,
        color: 'black',
        fontWeight: 'normal',
        marginTop: 54,
      },

      
      phoneLoginSubTitle: {
        fontSize: 12,
        color: CommonColor.deepGrey,
        marginTop: 10,
      },

      passwordTip: {
        fontSize: 12,
        color: '#999',
        marginTop: 4,
      },

      phoneInputLayout: {
        width: '100%',
        height: 40,
        flexDirection: 'row',
        alignItems: 'center',
        borderBottomWidth: 0.5,
        borderBottomColor: '#ddd',
        marginTop: 15
      },

      phoneInputPre: {
        fontSize: 16,
        color: 'black',
        fontWeight: 'bold'
      },
      phoneInputPreIcon:{
        width: 12,
        height: 6,
        marginLeft: 3,
      },

      phoneInput:{
        flex: 1,
        height: 50,
        backgroundColor: 'transparent',
        textAlign: 'left',
        textAlignVertical: 'center',
        fontSize: 19,
        fontWeight: 'bold',
        marginLeft: 10,
        color: '#333'

      },
      passwordInputLayout: {
        width: '100%',
        height: 40,
        flexDirection: 'row',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#ddd',
        marginTop: 4,
      },
      passwordInput: {
        flex: 1,
        height: 40,
        backgroundColor: 'transparent',
        textAlign: 'left',
        textAlignVertical: 'center',
        fontSize: 16,
        marginRight: 16,
        color: '#333'
      },

      passwordEye: {
        width: 20,
        height: 20,
      },

      changeLayout: {
        width: '100%',
        marginTop: 10,
        alignItems: 'center',
        flexDirection: 'row'
      },

      exchangeIcon: {
        width: 12,
        height: 12,
      },
      
      codeLoginText: {
        fontSize: 11,
        color: CommonColor.normalGrey,
        flex: 1,
        marginLeft: 4
      },

      forgetPasswordText: {
        fontSize: 12,
        color: '#303080',
      },

      loginButton: {
        width: '100%',
        height: 38,
        backgroundColor: CommonColor.mainColorTwo,
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 2,
        marginTop: 22
      },

      unloginButton: {
        width: '100%',
        height: 38,
        backgroundColor: 'red',
        opacity: 0.4,
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 2,
        marginTop: 22
      },
      

      loginText: {
        fontSize: 13,
        color: 'white',
        fontWeight: 'bold'
      },

      thridPartyLoginLayout: {
        width: '100%',
        flexDirection: 'row',
        marginTop: 50,
        justifyContent: 'center'
      },

      icon_qq: {
        width: 56,
        height: 56,
      },
      icon_wx: {
        width: 56,
        height: 56,
        marginRight: 60
      },

      closeButtonLayout: {
        position: 'absolute',
        top: 5,
        left: 20,
      },

      closeButtonIcon: {
        width: 24,
        height: 24
      },


      bottomLoginMethods: {
        position: 'absolute',
        bottom: 70,
        right: 20,
        width: '100%',
      },

      bottomLoginMethodsRoot: {
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: "center",
      },

      bottomLoginTitle: {
        fontSize: 10
      }

    });

    const canLogin = check ? true : false;

    return (
      <View style={styles.root}>
          
          {/** 密码登录提示 */}
          <Text style={styles.phoneLoginTitle}>手机号登录</Text>
          <Text style={styles.phoneLoginSubTitle}>未注册手机号将自动注册</Text>


          {/** 登录手机号表单 */}
          <View style={styles.phoneInputLayout}>
            <Text style={styles.phoneInputPre}>+86</Text>
            <AntDesign style={styles.phoneInputPreIcon} name="close"/>
            <TextInput style={styles.phoneInput} placeholderTextColor='#999' placeholder="请输入手机号" autoFocus={false}
               keyboardType="number-pad" maxLength={13} value={phone} 
               onChangeText={(text:string) => {
                setPhone(text)
              }}/>

            <Icon color={CommonColor.deepGrey} size={16} onPress={() => {setPhone('')}} name="close-circle" />

          </View>

          {/** 获取验证码按钮 */}
          <TouchableOpacity style={canLogin ? styles.loginButton : styles.loginButton} 
            activeOpacity={canLogin ? 0.7 : 1} 
            onPress={onPressByLogin}>
            <Text style={styles.loginText}>获取验证码</Text>
          </TouchableOpacity>

          {/** 注册登录协议 */}
          <View style={commonStyles.protocolLayout}>
            
            <TouchableOpacity onPress={() => { setCheck(!check) }}>
              <Image style={commonStyles.radioButton} source={check ? icon_selection : icon_un_selection}/>
            </TouchableOpacity>

            <Text style={commonStyles.labelText}>已阅读并同意</Text>
            <TouchableOpacity onPress={() => {
              Linking.openURL('https://www.github.com/whoiszxl/shopzz');
            }}>
              <Text style={commonStyles.protocolText}>《淘物用户协议》《隐私政策》《买家须知》</Text>
            </TouchableOpacity>

          </View>


          {/** 扩展功能 */}
          <View style={styles.changeLayout}>
            <Text style={styles.codeLoginText}>接收不到短信</Text>
          </View>

          {/** 关闭页面 */}
          <TouchableOpacity onPress={() => {setLoginType('quick')}} style={styles.closeButtonLayout}>
            <AntDesign style={styles.closeButtonIcon} name="close"/>
          </TouchableOpacity>


          {/** 登录方式 */}
          <View style={styles.bottomLoginMethods}>
            <View style={styles.bottomLoginMethodsRoot}>
              <Text style={styles.bottomLoginTitle}>或通过以下方式登录</Text>
              
              <View style={{flexDirection: "row", marginTop: 15}}>
                <TouchableOpacity activeOpacity={0.8}>
                  <AntDesign size={21} color={CommonColor.fontColor} name="apple1"/>
                </TouchableOpacity>

                <TouchableOpacity style={{marginLeft: 20}} activeOpacity={0.8}>
                  <AntDesign size={21} color={CommonColor.fontColor} name="wechat"/>
                </TouchableOpacity>

                <TouchableOpacity style={{marginLeft: 20}} activeOpacity={0.8}>
                  <AntDesign size={21} color={CommonColor.fontColor} name="weibo"/>
                </TouchableOpacity>

                <TouchableOpacity style={{marginLeft: 20}} activeOpacity={0.8}>
                  <AntDesign size={21} color={CommonColor.fontColor} name="QQ"/>
                </TouchableOpacity>

                <TouchableOpacity style={{marginLeft: 20}} activeOpacity={0.8}>
                  <AntDesign size={21} color={CommonColor.fontColor} name="google"/>
                </TouchableOpacity>
              </View>

            </View>
          </View>
          
      </View>
    );
  }

  return (
    <View style={commonStyles.root}>
        {
          renderInputLogin()
        }
    </View>
  )
}


const commonStyles = StyleSheet.create({
    root: {
        width: '100%',
        height: '100%',
        backgroundColor: 'white',
        flexDirection: 'column',
        alignItems: 'center'
    },

    protocolLayout: {
      width: '100%',
      flexDirection: 'row',
      alignItems: 'center',
      marginBottom: 10,
      marginTop: 15,
    },

    radioButton: {
      width: 20,
      height: 20,
    },

    labelText: {
      fontSize: 10,
      color: '#999',
    },

    protocolText: {
      fontSize: 10,
      color: CommonColor.fontColor
    },
})