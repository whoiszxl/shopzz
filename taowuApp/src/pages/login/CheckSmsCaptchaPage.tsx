import React, { useState, useRef, useEffect } from "react";
import { View, Image, StyleSheet, TouchableOpacity, Text, TextInput, LayoutAnimation, StatusBar } from "react-native";
import { RouteProp, useNavigation, useRoute } from '@react-navigation/native'

import { StackNavigationProp } from '@react-navigation/stack';

import MemberStore from "../../stores/MemberStore";
import { CommonColor } from "../../common/CommonColor";
import {useSafeAreaInsets} from 'react-native-safe-area-context';
import StorageUtil from "../../utils/StorageUtil";
import { CommonConstant } from "../../common/CommonConstant";
import AntDesign from 'react-native-vector-icons/AntDesign';




//校验短信验证码页面
export default () => {
  
  const insets = useSafeAreaInsets();

  const {params} = useRoute<any>();

  const navigation = useNavigation<StackNavigationProp<any>>();


  const [check, setCheck] = useState<boolean>(false);
  const [phone, setPhone] = useState<string>('11388889999');



  const canLogin = check ? true : false;

  const input1Ref = useRef<TextInput | null>(null);
  const input2Ref = useRef<TextInput | null>(null);
  const input3Ref = useRef<TextInput | null>(null);
  const input4Ref = useRef<TextInput | null>(null);

  const [inputValues, setInputValues] = useState<string[]>(['', '', '', '']);

  useEffect(() => {
  
  }, [])



  const handleTextChange = (text: string, inputIndex: number, nextInputRef: React.RefObject<TextInput | null>) => {
    const newInputValues = [...inputValues];
    newInputValues[inputIndex] = text.length === 1 ? text : '';
    setInputValues(newInputValues);

    if (text.length === 1 && nextInputRef.current) {
      nextInputRef.current.focus();
    }
  };
  
  const onPressByLogin =async () => {
    console.log(inputValues);
    MemberStore.requestSmsLogin(params.phone, inputValues.join(''), params.loginToken, (success: boolean) => {
      if(success) {
        MemberStore.requestMemberInfo((data?:MemberInfoEntity) => {
          console.log(data);
          if(data) {
            if(data.identityStatus === '' || data.workStatus === '' 
              || data.highestQualification === '' || data.highestQualificationType === '' 
              || data.fullName === '' || data.gender === '' || data.birthday === ''
              || data.avatar === '') {
                navigation.replace('InitMemberInfoPage', {memberInfo: data});
              }else {
                //将当前的用户信息保存到本地
                StorageUtil.setItem(CommonConstant.MEMBER_INFO, JSON.stringify(data));
                navigation.replace('TabPage');
              }
          }else if(data === undefined) {
            navigation.replace('LoginPage');
          }
        });
      }else {
        console.log("登录失败");
      }
    });
  }

  return (
    <>
        {/** 隐藏状态栏 */}
    <StatusBar translucent backgroundColor={'transparent'} />
    
    <View style={[styles.root, {top: insets.top + 10}]}>
                        

        <View style={styles.root}>
  
          
          {/** 密码登录提示 */}
          <Text style={styles.phoneLoginTitle}>输入短信验证码</Text>
          <Text style={styles.phoneLoginSubTitle}>已经向您的手机 xxxx 发送验证码</Text>

          <View style={styles.smsRoot}>
            <TextInput ref={input1Ref} style={styles.smsInput} keyboardType="numeric" maxLength={1}
                onChangeText={(text) => handleTextChange(text, 0, input2Ref)}
            />
            <TextInput ref={input2Ref} style={styles.smsInput} keyboardType="numeric" maxLength={1}
                onChangeText={(text) => handleTextChange(text, 1, input3Ref)}
            />
            <TextInput ref={input3Ref} style={styles.smsInput} keyboardType="numeric" maxLength={1}
                onChangeText={(text) => handleTextChange(text, 2, input4Ref)}
            />
            <TextInput ref={input4Ref} style={styles.smsInput} keyboardType="numeric" maxLength={1}
                onChangeText={(text) => handleTextChange(text, 3, input1Ref)}
            />
            </View>

          {/** 重新发送按钮 */}
          <View style={commonStyles.protocolLayout}>

            
            <TouchableOpacity onPress={() => { }}>
              <Text style={commonStyles.labelText}>重新发送</Text>
            </TouchableOpacity>

          </View>

          {/** 下一步按钮 */}
          <TouchableOpacity style={canLogin ? styles.loginButton : styles.loginButton} 
            activeOpacity={canLogin ? 0.7 : 1} 
            onPress={onPressByLogin}>
            <Text style={styles.loginText}>下一步</Text>
          </TouchableOpacity>


          {/** 扩展功能 */}
          <View style={styles.changeLayout}>
            <Text style={styles.codeLoginText}>接收不到短信</Text>
          </View>

          {/** 关闭页面 */}
          <TouchableOpacity onPress={() => {  }} style={styles.closeButtonLayout}>
            <AntDesign style={styles.phoneInputPreIcon} name="close"/>
          </TouchableOpacity>
          
      </View>
    </View>
    
    </>
  )
}


const styles = StyleSheet.create({
      root: {
        width: '100%',
        height: '100%',
        flexDirection: 'column',
        alignItems: 'flex-start',
        paddingHorizontal: 20,
      },

      phoneLoginTitle: {
        fontSize: 24,
        color: 'black',
        fontWeight: 'bold',
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
        fontSize: 16,
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
        justifyContent: 'space-between',
        flexDirection: 'row',
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
        height: 40,
        backgroundColor: CommonColor.mainColor,
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 8,
        marginTop: 10
      },

      unloginButton: {
        width: '100%',
        height: 40,
        backgroundColor: 'red',
        opacity: 0.4,
        justifyContent: 'center',
        alignItems: 'center',
        borderRadius: 8,
        marginTop: 10
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
        bottom: 50,
        right: 20,
        width: '100%',
      },

      bottomLoginMethodsRoot: {
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: "center",
      },

      bottomLoginTitle: {
        fontSize: 12
      },

      smsRoot: {
        paddingTop: 20,
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
      },

      smsInput: {
        width: 60,
        height: 60,
        borderWidth: 0.5,
        borderColor: CommonColor.normalGrey,
        textAlign: 'center',
        fontSize: 25,
        fontWeight: "bold",
        marginHorizontal: 5,
      },

});

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
      fontSize: 11,
      color: CommonColor.fontColor,
    },

    protocolText: {
      fontSize: 10,
      color: '#1020ff'
    },
})