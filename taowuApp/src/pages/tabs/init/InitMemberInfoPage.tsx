import React, { useEffect, useState } from "react";
import { View, Text, TextInput, TouchableOpacity, StyleSheet, Image, LayoutAnimation, ScrollView } from "react-native";

import { useNavigation, useRoute } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { CommonColor } from "../../../common/CommonColor";
import Icon from 'react-native-vector-icons/Ionicons';

import DatePicker from 'react-native-date-picker'
import { dateFormat } from "../../../utils/StrUtil";
import MemberStore from "../../../stores/MemberStore";

import { launchImageLibrary, ImagePickerResponse } from "react-native-image-picker";
import apis from "../../../apis/apis";


//初始化用户信息页面
export default () => {

  const [initType, setInitType] = useState<'renderInitNameInfo'|'renderInitStatusInfo'|'renderInitQualification'|'renderInitAvatar'>('renderInitNameInfo');


  const navigation = useNavigation<StackNavigationProp<any>>();
  const {params} = useRoute<any>();

  const [name, setName] = useState('');

  const [avatarUrl, setAvatarUrl] = useState('');


  const [date, setDate] = useState(new Date())


  const [selectedButton, setSelectedButton] = useState(1);
  const handleButtonPress = (buttonIndex: number) => {
    setSelectedButton(buttonIndex === selectedButton ? 2 : buttonIndex);
  };

  const [selectedButtonIdentityStatus, setSelectedButtonIdentityStatus] = useState(1);
  const handleIdentityStatusButtonPress = (buttonIndex: number) => {
    setSelectedButtonIdentityStatus(buttonIndex === selectedButtonIdentityStatus ? 10 : buttonIndex);
  };


  const [workStatus, setWorkStatus] = useState(1);
  const handleWorkStatusButtonPress = (buttonIndex: any) => {
    setWorkStatus(buttonIndex === workStatus ? 10 : buttonIndex);
  };

  
  const [highestQualification, setHighestQualification] = useState(1);
  const handleHighestQualificationButtonPress = (buttonIndex: any) => {
    setHighestQualification(buttonIndex === highestQualification ? 100 : buttonIndex);
  };


  const [highestQualificationType, setHighestQualificationType] = useState(1);
  const handleHighestQualificationTypeButtonPress = (buttonIndex: any) => {
    setHighestQualificationType(buttonIndex === highestQualificationType ? 100 : buttonIndex);
  };


  const [selectedImageIndex, setSelectedImageIndex] = useState(-1);
  const handleImagePress = (index:number, image:string) => {
    setSelectedImageIndex(index);
    setAvatarUrl(image);
  };


  useEffect(() => {
    if(params.memberInfo.fullName === '' 
    || params.memberInfo.gender === '' 
    || params.memberInfo.birthday === '') {
        setInitType('renderInitNameInfo');
    }else if(params.memberInfo.identityStatus === '' 
    || params.memberInfo.workStatus === '') {
        setInitType('renderInitStatusInfo');
    }else if(params.memberInfo.highestQualification === '' || params.memberInfo.highestQualificationType === '') {
        setInitType('renderInitQualification');
    }else if(params.memberInfo.avatar === '') {
        setInitType('renderInitAvatar');
    }
  }, []);


  const renderInitNameInfo = () => {
    
    const styles = StyleSheet.create({
        root: {
          width: '100%',
          height: '100%',
          flexDirection: 'column',
          alignItems: 'flex-start',
          paddingHorizontal: 20,
        },
  
        title: {
          fontSize: 24,
          color: 'black',
          fontWeight: 'bold',
          marginTop: 54,
        },
  
        nameInputLayout: {
          width: '100%',
          height: 40,
          flexDirection: 'row',
          alignItems: 'center',
          borderWidth: 1,
          borderBottomColor: '#ddd',
          borderRadius: 5,
          marginTop: 15,
          borderColor: CommonColor.normalGrey
        },
  
        phoneInputPre: {
          fontSize: 16,
          color: 'black',
        },
  
        nameInput:{
          flex: 1,
          height: 50,
          backgroundColor: 'transparent',
          textAlign: 'left',
          textAlignVertical: 'center',
          fontSize: 16,
          marginLeft: 10,
          color: '#333'
  
        },

        nextButton: {
          width: '100%',
          height: 40,
          backgroundColor: CommonColor.mainColor,
          justifyContent: 'center',
          alignItems: 'center',
          borderRadius: 8,
          marginTop: 30
        },
  
        nextButtonText: {
          fontSize: 13,
          color: 'white',
          fontWeight: 'bold'
        },
  
        closeButtonLayout: {
          position: 'absolute',
          top: 5,
          left: 20,
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

        genderCheckLayout: {
            flexDirection: 'row',
            justifyContent: 'space-between',
            paddingHorizontal: 0,
            marginTop: 20,
          },
          button: {
            flex: 1,
            backgroundColor: CommonColor.tagBg,
            borderRadius: 5,
            paddingVertical: 10,
            alignItems: 'center',
          },
          selectedButton: {
            backgroundColor: CommonColor.transparentMainColor,
            borderWidth: 1,
            borderColor: CommonColor.mainColor,
            
          },
          buttonText: {
            color: 'black',
          },
          selectedText: {
            color: CommonColor.mainColor
          },
  
      });
    
      return (
        <View style={styles.root}>
            
            <Text style={styles.title}>你的姓名</Text>
  
            <View style={styles.nameInputLayout}>
              <TextInput style={styles.nameInput} placeholderTextColor='#999' placeholder="请输入你的姓名" autoFocus={false}
                maxLength={13} value={name} 
                 onChangeText={(text:string) => {
                  setName(text)
                }}/>
  
              <Icon size={18} onPress={() => {setName('')}} color={CommonColor.transparentGreyBg} style={{paddingRight: 10}} name="close-circle" />
  
            </View>

            <Text style={styles.title}>性别</Text>

            <View style={styles.genderCheckLayout}>

                <TouchableOpacity style={[styles.button, selectedButton === 1 ? styles.selectedButton : null]}
                    onPress={() => handleButtonPress(1)} >
                    <Text style={[styles.buttonText, selectedButton === 1 ? styles.selectedText : null]}>
                        男
                    </Text>
                </TouchableOpacity>

                <TouchableOpacity style={[styles.button, {marginLeft: 20}, selectedButton === 2 ? styles.selectedButton : null]}
                    onPress={() => handleButtonPress(2)}>
                    <Text style={[styles.buttonText, selectedButton === 2 ? styles.selectedText : null]}>
                        女
                    </Text>
                </TouchableOpacity>

            </View>

            <Text style={styles.title}>出生年月</Text>
            
            <DatePicker mode="date" date={date} onDateChange={setDate} />
            

  
            {/** 下一步按钮 */}
            <TouchableOpacity style={styles.nextButton} 
              activeOpacity={0.8} 
              onPress={() => {
                //保存用户的基本信息
                MemberStore.initBaseInfo(name, selectedButton, dateFormat(date), (success) => {
                    if(success) {
                        LayoutAnimation.easeInEaseOut();
                        setInitType('renderInitStatusInfo');
                    }
                });

              }}>
              <Text style={styles.nextButtonText}>下一步</Text>
            </TouchableOpacity>
  
  
            {/** 关闭页面 */}
            <TouchableOpacity onPress={() => {setInitType('renderInitNameInfo')}} style={styles.closeButtonLayout}>
              <Icon size={20} name="close"/>
            </TouchableOpacity>
  
            <View style={styles.bottomLoginMethods}>
              <View style={styles.bottomLoginMethodsRoot}>
                <Text style={styles.bottomLoginTitle}>根据《劳动法》《未成年人保护法》等相关法律规定申请注册TT直聘，请选择与你身份证一致的真实年龄并确保你已年满16周岁。</Text>
              </View>
            </View>
            
        </View>
      );
  }

  const renderInitStatusInfo = () => {
    const styles = StyleSheet.create({
        root: {
          width: '100%',
          height: '100%',
          flexDirection: 'column',
          alignItems: 'flex-start',
          paddingHorizontal: 20,
        },
  
        title: {
          fontSize: 24,
          color: 'black',
          fontWeight: 'bold',
          marginTop: 54,
        },
  
        nameInputLayout: {
          width: '100%',
          height: 40,
          flexDirection: 'row',
          alignItems: 'center',
          borderWidth: 1,
          borderBottomColor: '#ddd',
          borderRadius: 5,
          marginTop: 15,
          borderColor: CommonColor.normalGrey
        },
  
        phoneInputPre: {
          fontSize: 16,
          color: 'black',
        },
  
        nameInput:{
          flex: 1,
          height: 50,
          backgroundColor: 'transparent',
          textAlign: 'left',
          textAlignVertical: 'center',
          fontSize: 16,
          marginLeft: 10,
          color: '#333'
  
        },

        nextButton: {
          width: '100%',
          height: 40,
          backgroundColor: CommonColor.mainColor,
          justifyContent: 'center',
          alignItems: 'center',
          borderRadius: 8,
          marginTop: 170
        },
  
        nextButtonText: {
          fontSize: 13,
          color: 'white',
          fontWeight: 'bold'
        },
  
        closeButtonLayout: {
          position: 'absolute',
          top: 5,
          left: 20,
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

        genderCheckLayout: {
            flexDirection: 'row',
            justifyContent: 'space-between',
            paddingHorizontal: 0,
            marginTop: 20,
          },
          button: {
            flex: 1,
            borderWidth: 1,
            backgroundColor: CommonColor.tagBg,
            borderColor: CommonColor.tagBg,
            borderRadius: 5,
            paddingVertical: 10,
            alignItems: 'center',
          },
          selectedButton: {
            backgroundColor: CommonColor.transparentMainColor,
            borderWidth: 1,
            borderColor: CommonColor.mainColor,
            
          },
          buttonText: {
            color: 'black',
          },
          selectedText: {
            color: CommonColor.mainColor
          },
  
      });
    
      return (
        <View style={styles.root}>
            
            <Text style={styles.title}>你的身份</Text>
  
            <View style={styles.genderCheckLayout}>

                <TouchableOpacity style={[styles.button, selectedButtonIdentityStatus === 1 ? styles.selectedButton : null]}
                    onPress={() => handleIdentityStatusButtonPress(1)} >
                    <Text style={[styles.buttonText, selectedButtonIdentityStatus === 1 ? styles.selectedText : null]}>
                        职场人
                    </Text>
                </TouchableOpacity>

                <TouchableOpacity style={[styles.button, {marginLeft: 20}, selectedButtonIdentityStatus === 2 ? styles.selectedButton : null]}
                    onPress={() => handleIdentityStatusButtonPress(2)}>
                    <Text style={[styles.buttonText, selectedButton === 2 ? styles.selectedText : null]}>
                        学生
                    </Text>
                </TouchableOpacity>

            </View>

            <Text style={styles.title}>求职状态</Text>
            
            {
                selectedButtonIdentityStatus === 2 && (
                    <View style={styles.genderCheckLayout}>

                        <TouchableOpacity style={[styles.button, workStatus === 1 ? styles.selectedButton : null]}
                            onPress={() => handleWorkStatusButtonPress(1)} >
                            <Text style={[styles.buttonText, workStatus === 1 ? styles.selectedText : null]}>
                                离校-随时到岗
                            </Text>
                        </TouchableOpacity>

                        <TouchableOpacity style={[styles.button, {marginLeft: 20}, workStatus === 2 ? styles.selectedButton : null]}
                            onPress={() => handleWorkStatusButtonPress(2)}>
                            <Text style={[styles.buttonText, workStatus === 2 ? styles.selectedText : null]}>
                                在校-月内到岗
                            </Text>
                        </TouchableOpacity>

                    </View>
                )
            }

            {
                selectedButtonIdentityStatus === 2 && (
                    <View style={styles.genderCheckLayout}>

                        <TouchableOpacity style={[styles.button, workStatus === 3 ? styles.selectedButton : null]}
                            onPress={() => handleWorkStatusButtonPress(3)} >
                            <Text style={[styles.buttonText, workStatus === 3 ? styles.selectedText : null]}>
                                在校-考虑机会
                            </Text>
                        </TouchableOpacity>

                        <TouchableOpacity style={[styles.button, {marginLeft: 20}, workStatus === 4 ? styles.selectedButton : null]}
                            onPress={() => handleWorkStatusButtonPress(4)}>
                            <Text style={[styles.buttonText, workStatus === 4 ? styles.selectedText : null]}>
                                在校-暂不考虑
                            </Text>
                        </TouchableOpacity>

                    </View>
                )
            }

            

            {
                selectedButtonIdentityStatus === 1 && (
                    <View style={styles.genderCheckLayout}>

                    <TouchableOpacity style={[styles.button, workStatus === 5 ? styles.selectedButton : null]}
                        onPress={() => handleWorkStatusButtonPress(5)} >
                        <Text style={[styles.buttonText, workStatus === 5 ? styles.selectedText : null]}>
                            离职-随时到岗
                        </Text>
                    </TouchableOpacity>
    
                    <TouchableOpacity style={[styles.button, {marginLeft: 20}, workStatus === 6 ? styles.selectedButton : null]}
                        onPress={() => handleWorkStatusButtonPress(6)}>
                        <Text style={[styles.buttonText, workStatus === 6 ? styles.selectedText : null]}>
                            在职-月内到岗
                        </Text>
                    </TouchableOpacity>
    
                </View>
                )
            }

            {
                selectedButtonIdentityStatus === 1 && (
                    <View style={styles.genderCheckLayout}>

                        <TouchableOpacity style={[styles.button, workStatus === 7 ? styles.selectedButton : null]}
                            onPress={() => handleWorkStatusButtonPress(7)} >
                            <Text style={[styles.buttonText, workStatus === 7 ? styles.selectedText : null]}>
                                在职-考虑机会
                            </Text>
                        </TouchableOpacity>

                        <TouchableOpacity style={[styles.button, {marginLeft: 20}, workStatus === 8 ? styles.selectedButton : null]}
                            onPress={() => handleWorkStatusButtonPress(8)}>
                            <Text style={[styles.buttonText, workStatus === 8 ? styles.selectedText : null]}>
                                在职-暂不考虑
                            </Text>
                        </TouchableOpacity>

                    </View>
                )
            }
  
            {/** 下一步按钮 */}
            <TouchableOpacity style={styles.nextButton} 
              activeOpacity={0.8} 
              onPress={() => {
                console.log(selectedButtonIdentityStatus, workStatus);
                MemberStore.initIdentityInfo(selectedButtonIdentityStatus, workStatus, (success) => {
                    if(success) {
                        LayoutAnimation.easeInEaseOut();
                        setInitType('renderInitQualification');
                    }
                });

              }}>
              <Text style={styles.nextButtonText}>下一步</Text>
            </TouchableOpacity>
  
  
            {/** 关闭页面 */}
            <TouchableOpacity onPress={() => {setInitType('renderInitNameInfo')}} style={styles.closeButtonLayout}>
              <Icon size={20} name="close"/>
            </TouchableOpacity>
  
            <View style={styles.bottomLoginMethods}>
              <View style={styles.bottomLoginMethodsRoot}>
                <Text style={styles.bottomLoginTitle}>根据《劳动法》《未成年人保护法》等相关法律规定申请注册TT直聘，请选择与你身份证一致的真实年龄并确保你已年满16周岁。</Text>
              </View>
            </View>
            
        </View>
      );
  }

  const renderInitQualification = () => {
    const styles = StyleSheet.create({
        root: {
          width: '100%',
          height: '100%',
          flexDirection: 'column',
          alignItems: 'flex-start',
          paddingHorizontal: 20,
        },
  
        title: {
          fontSize: 24,
          color: 'black',
          fontWeight: 'bold',
          marginTop: 54,
        },
  
        nameInputLayout: {
          width: '100%',
          height: 40,
          flexDirection: 'row',
          alignItems: 'center',
          borderWidth: 1,
          borderBottomColor: '#ddd',
          borderRadius: 5,
          marginTop: 15,
          borderColor: CommonColor.normalGrey
        },
  
        phoneInputPre: {
          fontSize: 16,
          color: 'black',
        },
  
        nameInput:{
          flex: 1,
          height: 50,
          backgroundColor: 'transparent',
          textAlign: 'left',
          textAlignVertical: 'center',
          fontSize: 16,
          marginLeft: 10,
          color: '#333'
  
        },

        nextButton: {
          width: '100%',
          height: 40,
          backgroundColor: CommonColor.mainColor,
          justifyContent: 'center',
          alignItems: 'center',
          borderRadius: 8,
          marginTop: 170
        },
  
        nextButtonText: {
          fontSize: 13,
          color: 'white',
          fontWeight: 'bold'
        },
  
        closeButtonLayout: {
          position: 'absolute',
          top: 5,
          left: 20,
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

        genderCheckLayout: {
            flexDirection: 'row',
            justifyContent: 'space-between',
            paddingHorizontal: 0,
            marginTop: 20,
          },
          button: {
            flex: 1,
            borderWidth: 1,
            backgroundColor: CommonColor.tagBg,
            borderColor: CommonColor.tagBg,
            borderRadius: 5,
            paddingVertical: 10,
            alignItems: 'center',
          },
          selectedButton: {
            backgroundColor: CommonColor.transparentMainColor,
            borderWidth: 1,
            borderColor: CommonColor.mainColor,
            
          },
          buttonText: {
            color: 'black',
          },
          selectedText: {
            color: CommonColor.mainColor
          },
  
      });
    
      return (
        <View style={styles.root}>
            
            

            <Text style={styles.title}>最高学历</Text>
            
           
                <View style={styles.genderCheckLayout}>

                    <TouchableOpacity style={[styles.button, highestQualification === 1 ? styles.selectedButton : null]}
                        onPress={() => handleHighestQualificationButtonPress(1)} >
                        <Text style={[styles.buttonText, highestQualification === 1 ? styles.selectedText : null]}>
                            初中及以下
                        </Text>
                    </TouchableOpacity>

                    <TouchableOpacity style={[styles.button, {marginLeft: 20}, highestQualification === 2 ? styles.selectedButton : null]}
                        onPress={() => handleHighestQualificationButtonPress(2)}>
                        <Text style={[styles.buttonText, highestQualification === 2 ? styles.selectedText : null]}>
                            高中
                        </Text>
                    </TouchableOpacity>

                </View>

                <View style={styles.genderCheckLayout}>
                    <TouchableOpacity style={[styles.button, highestQualification === 3 ? styles.selectedButton : null]}
                        onPress={() => handleHighestQualificationButtonPress(3)} >
                        <Text style={[styles.buttonText, highestQualification === 3 ? styles.selectedText : null]}>
                            大专
                        </Text>
                    </TouchableOpacity>

                    <TouchableOpacity style={[styles.button, {marginLeft: 20}, highestQualification === 4 ? styles.selectedButton : null]}
                        onPress={() => handleHighestQualificationButtonPress(4)}>
                        <Text style={[styles.buttonText, highestQualification === 4 ? styles.selectedText : null]}>
                            本科
                        </Text>
                    </TouchableOpacity>
                </View>
  

        
                <View style={styles.genderCheckLayout}>
                    <TouchableOpacity style={[styles.button, highestQualification === 5 ? styles.selectedButton : null]}
                        onPress={() => handleHighestQualificationButtonPress(5)} >
                        <Text style={[styles.buttonText, highestQualification === 5 ? styles.selectedText : null]}>
                            硕士
                        </Text>
                    </TouchableOpacity>
    
                    <TouchableOpacity style={[styles.button, {marginLeft: 20}, highestQualification === 6 ? styles.selectedButton : null]}
                        onPress={() => handleHighestQualificationButtonPress(6)}>
                        <Text style={[styles.buttonText, highestQualification === 6 ? styles.selectedText : null]}>
                            博士    
                        </Text>
                    </TouchableOpacity>
                </View>


                <Text style={styles.title}>类型</Text>
  
                <View style={styles.genderCheckLayout}>

                    <TouchableOpacity style={[styles.button, highestQualificationType === 1 ? styles.selectedButton : null]}
                        onPress={() => handleHighestQualificationTypeButtonPress(1)} >
                        <Text style={[styles.buttonText, highestQualificationType === 1 ? styles.selectedText : null]}>
                            全日制
                        </Text>
                    </TouchableOpacity>

                    <TouchableOpacity style={[styles.button, {marginLeft: 20}, highestQualificationType === 2 ? styles.selectedButton : null]}
                        onPress={() => handleHighestQualificationTypeButtonPress(2)}>
                        <Text style={[styles.buttonText, highestQualificationType === 2 ? styles.selectedText : null]}>
                            非全日制
                        </Text>
                    </TouchableOpacity>

                </View>
    
            {/** 下一步按钮 */}
            <TouchableOpacity style={styles.nextButton} 
              activeOpacity={0.8} 
              onPress={() => {
                console.log(highestQualification, highestQualificationType);
                MemberStore.initQualificationInfo(highestQualification, highestQualificationType, (success) => {
                    if(success) {
                        LayoutAnimation.easeInEaseOut();
                        setInitType('renderInitAvatar');
                    }
                });

              }}>
              <Text style={styles.nextButtonText}>下一步</Text>
            </TouchableOpacity>
  
  
            {/** 关闭页面 */}
            <TouchableOpacity onPress={() => {setInitType('renderInitNameInfo')}} style={styles.closeButtonLayout}>
              <Icon size={20} name="close"/>
            </TouchableOpacity>
  
            <View style={styles.bottomLoginMethods}>
              <View style={styles.bottomLoginMethodsRoot}>
                <Text style={styles.bottomLoginTitle}>根据《劳动法》《未成年人保护法》等相关法律规定申请注册TT直聘，请选择与你身份证一致的真实年龄并确保你已年满16周岁。</Text>
              </View>
            </View>
            
        </View>
      );
  }


  
  const onUploadPress = () => {
    launchImageLibrary({
        mediaType: 'mixed',
        quality: 1,
        includeBase64: true
    }, async (res: ImagePickerResponse) => {
        const {assets} = res;
        if(!assets?.length) {
            console.log('选择图片失败');
            return;
        }

        const {uri, width, height, fileName, fileSize, type} = assets[0];

        await MemberStore.uploadAvatar(apis.fileUpload.url, uri!, fileName!, type!, (url:string) => {
            setAvatarUrl(url);
        });

    });
  }



  const renderInitAvatar = () => {
    const styles = StyleSheet.create({
        root: {
          width: '100%',
          height: '100%',
          flexDirection: 'column',
          alignItems: 'center',
          paddingHorizontal: 20,
        },
  
        title: {
          fontSize: 24,
          color: 'black',
          fontWeight: 'bold',
          marginTop: 54,
        },

        tips1: {
            fontSize: 12,
            color: CommonColor.deepGrey,
            fontWeight: 'bold',
            marginTop: 10,
        },

        tips2: {
            fontSize: 13,
            color: CommonColor.deepGrey,
            fontWeight: '400',
            marginTop: 120,
        },
  
        nameInputLayout: {
          width: '100%',
          height: 40,
          flexDirection: 'row',
          alignItems: 'center',
          borderWidth: 1,
          borderBottomColor: '#ddd',
          borderRadius: 5,
          marginTop: 15,
          borderColor: CommonColor.normalGrey
        },
  
        phoneInputPre: {
          fontSize: 16,
          color: 'black',
        },
  
        nameInput:{
          flex: 1,
          height: 50,
          backgroundColor: 'transparent',
          textAlign: 'left',
          textAlignVertical: 'center',
          fontSize: 16,
          marginLeft: 10,
          color: '#333'
  
        },

        nextButton: {
          width: '100%',
          height: 40,
          backgroundColor: CommonColor.mainColor,
          justifyContent: 'center',
          alignItems: 'center',
          borderRadius: 8,
          marginTop: 170
        },

        
        unNextButton: {
            width: '100%',
            height: 40,
            backgroundColor: CommonColor.mainColor,
            opacity: 0.4,
            justifyContent: 'center',
            alignItems: 'center',
            borderRadius: 8,
            marginTop: 170
        },
  
        nextButtonText: {
          fontSize: 15,
          color: 'white',
          fontWeight: 'bold'
        },
  
        closeButtonLayout: {
          position: 'absolute',
          top: 5,
          left: 20,
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

        genderCheckLayout: {
            flexDirection: 'row',
            justifyContent: 'space-between',
            paddingHorizontal: 0,
            marginTop: 20,
          },
          button: {
            flex: 1,
            borderWidth: 1,
            backgroundColor: CommonColor.tagBg,
            borderColor: CommonColor.tagBg,
            borderRadius: 5,
            paddingVertical: 10,
            alignItems: 'center',
          },
          selectedButton: {
            backgroundColor: CommonColor.transparentMainColor,
            borderWidth: 1,
            borderColor: CommonColor.mainColor,
            
          },
          buttonText: {
            color: 'black',
          },
          selectedText: {
            color: CommonColor.mainColor
          },


          uploadButton: {
            paddingTop: 100,
            justifyContent: 'center',
            alignItems: 'center',
          },
          circle: {
            width: 100,
            height: 100,
            borderRadius: 50,
            backgroundColor: CommonColor.mainColor,
            justifyContent: 'center',
            alignItems: 'center',
          },
          uploadText: {
            fontSize: 10,
            color: 'white',
          },




          avatarChooseContainer: {
            marginTop: 10,
            flexDirection: 'row',
            flexWrap: 'wrap',
            justifyContent: 'center',
          },
          avatarContainer: {
            width: '18%', // 每行显示四张图片
            aspectRatio: 1, // 保持图片宽高比为 1:1
            marginHorizontal: 10,
            borderRadius: 100, // 圆角框
            overflow: 'hidden', // 防止内容超出圆角框
            borderWidth: 2,
            borderColor: 'transparent',
          },
          avatarImage: {
            width: '100%',
            height: '100%',
          },

          selectedImageContainer: {
            borderColor: CommonColor.mainColor, // 选中时的边框颜色
          },
  
      });
      
      const avatarImages = [
        'https://tt-zhipin-oss.oss-cn-shenzhen.aliyuncs.com/image/default1.png',
        'https://tt-zhipin-oss.oss-cn-shenzhen.aliyuncs.com/image/default2.png',
        'https://tt-zhipin-oss.oss-cn-shenzhen.aliyuncs.com/image/default3.png',
        'https://tt-zhipin-oss.oss-cn-shenzhen.aliyuncs.com/image/default4.png',
      ];

      return (
        <View style={styles.root}>

            <View style={styles.uploadButton}>
                <TouchableOpacity style={styles.circle} onPress={onUploadPress}>
                    {
                    avatarUrl === '' ? (
                    <>
                        <Icon name="camera" size={40} color="white" />
                        <Text style={styles.uploadText}>点击上传</Text>
                    </>) : <Image style={{width: '100%', height: '100%', borderRadius: 100}} source={{uri: avatarUrl}} />
                    }
                </TouchableOpacity>
            </View>
            
            <Text style={styles.title}>最后一步，添加头像</Text>
            <Text style={styles.tips1}>上传头像，让头头们快速看到你</Text>
            <Text style={styles.tips2}>也可以选择下方虚拟头像</Text>

            <ScrollView contentContainerStyle={styles.avatarChooseContainer}>
                {avatarImages.map((image, index) => (
                    <TouchableOpacity
                        activeOpacity={1}
                        key={index}
                        style={[
                        styles.avatarContainer,
                        selectedImageIndex === index ? styles.selectedImageContainer : null,
                    ]}
                    onPress={() => handleImagePress(index, image)}
                    >
                    <Image source={{uri: image}} style={styles.avatarImage} />
                    </TouchableOpacity>
                ))}
            </ScrollView>
  
  
            {/** 关闭页面 */}
            <TouchableOpacity onPress={() => {setInitType('renderInitNameInfo')}} style={styles.closeButtonLayout}>
              <Icon size={20} name="close"/>
            </TouchableOpacity>
  
            <View style={styles.bottomLoginMethods}>
                {/** 下一步按钮 */}
                <TouchableOpacity style={avatarUrl === '' ? styles.unNextButton : styles.nextButton} 
                activeOpacity={0.8} 
                onPress={() => {
                    MemberStore.initAvatar(avatarUrl, (success) => {
                        if(success) {
                            LayoutAnimation.easeInEaseOut();
                            navigation.replace('TabPage');
                        }
                    });

                }}>
                <Text style={styles.nextButtonText}>开启打工之旅</Text>
                </TouchableOpacity>
            </View>
            
        </View>
      );
  }
  



  return (
    <View style={styles.root}>
        {

            (
                initType === 'renderInitNameInfo' ? renderInitNameInfo() : (
                    initType === 'renderInitStatusInfo' ? renderInitStatusInfo() : (
                        initType === 'renderInitQualification' ? renderInitQualification() : renderInitAvatar()
                    )
                )
            )
        }
    </View>
  )
}


const styles = StyleSheet.create({
    root: {
        width: '100%',
        height: '100%',
        backgroundColor: 'white',
        flexDirection: 'column',
        alignItems: 'center'
    },

})

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
      color: '#1020ff'
    },
})