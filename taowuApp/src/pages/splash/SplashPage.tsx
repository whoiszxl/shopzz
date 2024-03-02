import React, { useEffect } from "react";
import { View, Image, StyleSheet, Text } from "react-native";

import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';

import icon_logo from '../../assets/images/logo_bg_transparent.png';
import MemberStore from "../../stores/MemberStore";


//闪屏页
export default () => {

  const navigation = useNavigation<StackNavigationProp<any>>();

  //延迟进入APP主页
  useEffect(() => {
    setTimeout( async () => {
      MemberStore.requestMemberInfo((data?:MemberInfoEntity) => {
        navigation.replace('TabPage');
      });
    }, 1000);
  }, []);


  return (
    <View style={styles.root}>
        {/** 闪屏页文字 */}
        <Text style={styles.title}>淘物</Text>
    </View>
  )
}


const styles = StyleSheet.create({
    root: {
        width: '100%',
        height: '100%',
        backgroundColor: 'black',
        flexDirection: 'column',
        alignItems: 'center'
    },

    icon_logo: {
        width: 300,
        height: 150,
        marginTop: 200,
        resizeMode: 'contain'
    },

    title: {
        marginTop: 200,
        fontSize: 80,
        fontWeight: "bold",
        color: "white"
    }
})