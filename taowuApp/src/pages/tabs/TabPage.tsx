import React, { useState } from "react";
import {View, Text, StyleSheet, TouchableOpacity, StatusBar} from "react-native";

import Ionicons from 'react-native-vector-icons/Ionicons';
import { launchImageLibrary, ImagePickerResponse } from "react-native-image-picker";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';

import HomePage from "./home/HomePage";
import BuyPage from "./buy/BuyPage";
import DiscoveryPage from "./discovery/DiscoveryPage";
import MinePage from "./mine/MinePage";

import { CommonColor } from "../../common/CommonColor";


const Tab = createBottomTabNavigator();

export default () => {

    const navigation = useNavigation<StackNavigationProp<any>>();

    const [process, setProcess] = useState(0);


    const MyTabBar = ({state, descriptors, navigation} : any) => {
        const {routes, index} = state;


        const onPublishPress = () => {
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
                console.log(`uri=${uri}, width=${width}, height=${height}`);
                console.log(`fileName=${fileName}, fileSize=${fileSize}, type=${type}`);

                // const { data } = await ApiService.upload(apis.fileUpload.url, uri!, fileName!, type!, (event:any) => {
                //     setProcess(Math.round((100 * event.loaded) / event.total));
                // });
                // console.log("上传结果%s", data);
                // console.log("进度%s", process);

                navigation.push('PublishPage', {assets: assets});

            });
        }


        return (
            <View style={styles.myTabBar}>
                {routes.map((route: any, i: number) => {
                    const {options} = descriptors[route.key];
                    const label = options.title;
                    const isFocused = index === i;

                    return (
            
                        <TouchableOpacity activeOpacity={1} key={label} style={styles.myTabItem} onPress={() => {
                            if(!isFocused) {
                                navigation.navigate(route.name);
                            }
                            
                        }}>

                            {i === 0 ? <Ionicons style={isFocused ? styles.focusedIcon : styles.unFocusedIcon} name={isFocused ? "cube" : "cube-outline"} size={20} color="black"/> : (
                                i === 1 ? <Ionicons style={isFocused ? styles.focusedIcon : styles.unFocusedIcon} name={isFocused ? "briefcase" : "briefcase-outline"} size={20} color="black"/> : (
                                    i === 2 ? <Ionicons style={isFocused ? styles.focusedIcon : styles.unFocusedIcon} name={isFocused ? "planet" : "planet-outline"} size={20} color="black"/> : 
                                    <Ionicons style={isFocused ? styles.focusedIcon : styles.unFocusedIcon} name={isFocused ? "rocket" : "rocket-outline"} size={20} color={CommonColor.mainColor}/>
                                )
                            )}

                            <Text style={isFocused ? styles.focusedText : styles.unFocusedText}>{label}</Text>
                        </TouchableOpacity>
                    );
                })}
            </View>
        );
    }

    return (
        
        <View style={styles.root}>
            <StatusBar translucent backgroundColor={'transparent'} />

            <Tab.Navigator tabBar={(props) => <MyTabBar {...props} />}>

                <Tab.Screen name="HomePage" component={HomePage} options={{
                    title: '淘物', headerShown: false, lazy: false
                }}/>

                <Tab.Screen name="BuyPage" component={BuyPage} options={{
                    title: '购买', headerShown: false, lazy: false
                }}/>

                <Tab.Screen name="DiscoveryPage" component={DiscoveryPage} options={{
                    title: '探索', headerShown: false, lazy: false
                }}/>

                <Tab.Screen name="MinePage" component={MinePage} options={{
                    title: '我', headerShown: false, lazy: false
                }}/>
            </Tab.Navigator>

        </View>

    );
}

const styles = StyleSheet.create({
    root: {
        width: '100%',
        height: '100%',
    },

    myTabBar: {
        width: '100%',
        height: 52,
        flexDirection: 'row',
        alignItems: "center",
        backgroundColor: "white",
        borderTopWidth: 0.5,
        borderTopColor: CommonColor.tagBg
    },

    myTabItem: {
        height: '100%',
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },

    choicenessTabBar: {
        width: '100%',
        height: 52,
        flexDirection: 'row',
        alignItems: "center",
        backgroundColor: "black"
    },

    otherTabBar: {
        width: '100%',
        height: 52,
        flexDirection: 'row',
        alignItems: "center",
        backgroundColor: "white"
    },

    otherTabItem: {
        height: '100%',
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    },


    focusedText: {
        fontSize: 10,
        color: CommonColor.mainColor,
        fontWeight: 'bold'
    },

    unFocusedText: {
        fontSize: 10,
        color: CommonColor.fontColor,
        fontWeight: 'normal'
    },

    focusedIcon: {
        color: CommonColor.mainColor,
        borderColor: 'yellow',
    },

    unFocusedIcon: {
        color: CommonColor.fontColor,
    },
    
});