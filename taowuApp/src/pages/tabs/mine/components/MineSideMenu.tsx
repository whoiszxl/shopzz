import React, { useState, forwardRef, useImperativeHandle, useCallback } from 'react';

import {
    View, Text, StyleSheet, TouchableOpacity,
    Modal, ScrollView, Dimensions, LayoutAnimation,
} from 'react-native';

import Ionicons from 'react-native-vector-icons/Ionicons';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { CommonConstant } from '../../../../common/CommonConstant';
import StorageUtil from '../../../../utils/StorageUtil';
import DatabaseHelper from '../../../../utils/DatabaseHelper';
import { Alert } from 'react-native';

export interface MineSideMenuRef {
    show: () => void;
    hide: () => void;
}

const {width: SCREEN_WIDTH} = Dimensions.get('window');
const ContentWidth = SCREEN_WIDTH * 0.60;

/**
 * 我的页面-侧边菜单栏
 * icon网址：https://oblador.github.io/react-native-vector-icons/
 */
export default forwardRef((props: any, ref) => {
    
    const menus = [
        [
            {icon: 'cart-outline', title: '我的订单', onPress: () => {}},
            {icon: 'wallet-outline', title: '我的钱包', onPress: () => {}},
        ],
    
        [
            {icon: 'qr-code-outline', title: '我的二维码', onPress: () => {}},
            {icon: 'ios-layers-outline', title: '观看历史', onPress: () => {}},
            {icon: 'md-person-outline', title: '使用管理助手', onPress: () => {}},
            {icon: 'md-rocket-outline', title: '创作者中心', onPress: () => {}},
        ],
        [
            {icon: 'albums-outline', title: '小程序', onPress: () => {}},
            {icon: 'md-call-outline', title: '我的客服', onPress: () => {}},
            {icon: 'md-settings-outline', title: '设置', onPress: () => {}},
            {icon: 'ellipsis-horizontal-outline', title: '更多功能', onPress: () => {}},
            {icon: 'nuclear-outline', title: '清除聊天记录', onPress: () => {
                DatabaseHelper.deleteMessage(CommonConstant.IM_PRIVATE_CHAT_TABLE);
                StorageUtil.removeItem(CommonConstant.OFFLINE_MESSAGE_SEQ);
            }},
            {icon: 'exit-outline', title: '退出登录', onPress: () => {
                StorageUtil.removeItem(CommonConstant.TOKEN);
                navigation.reset({
                    index: 0,
                    routes: [{name: 'LoginPage'}]
                });
            }},
        ],
    ];


    const [visible, setVisible] = useState<boolean>(false);

    const [open, setOpen] = useState<boolean>(false);

    const navigation = useNavigation<StackNavigationProp<any>>();

    const show = () => {
        setVisible(true);
        setTimeout(() => {
            LayoutAnimation.linear();
            setOpen(true);
        }, 100);
    }

    const hide = () => {
        LayoutAnimation.linear();
        setOpen(false);
        setTimeout(() => {
            setVisible(false);
        }, 300);
    }

    
    useImperativeHandle(ref, () => {
        return {
            show, hide,
        }
    });


    const renderContent = () => {
        return (
            <View style={[styles.content, {marginLeft: open ? 0 : -ContentWidth}]}>
                <ScrollView style={styles.scrollView} contentContainerStyle={styles.container} showsVerticalScrollIndicator={false}>
                    {
                        menus.map((item, index) => {
                            return (
                                <View key={`${index}`}>
                                    
                                    {
                                        item.map((subItem, subIndex) => {
                                            return (
                                                <TouchableOpacity key={`${index}-${subIndex}`} 
                                                    style={styles.subItem} onPress={subItem.onPress}>
                                                    
                                                    <Ionicons name={subItem.icon} size={20} color={'black'} />
                                                    <Text style={styles.menuItemTxt}>{subItem.title}</Text>
                                                </TouchableOpacity>
                                            );
                                        })
                                    }

                                    {index !== menus.length - 1 && <View style={styles.divideLine} />}
                                </View>
                            );
                        })
                    }

                </ScrollView>

            </View>
        );
    }


    return <Modal transparent={true} visible={visible} statusBarTranslucent={true} 
                animationType='fade' onRequestClose={hide} >
            <TouchableOpacity style={styles.root} onPress={hide} activeOpacity={1}>
                {renderContent()}
            </TouchableOpacity>
    </Modal>
});

const styles = StyleSheet.create({
    root: {
        width: '100%',
        height: '100%',
        backgroundColor: '#000000C0',
        flexDirection: 'row',
    },
    content: {
        height: '100%',
        width: ContentWidth,
        backgroundColor: 'white',

    },
    scrollView: {
        width: '100%',
        flex: 1,
    },
    container: {
        paddingTop: 40,
        paddingHorizontal: 28,
        paddingBottom: 12,
    },

    subItem: {
        width: '100%',
        height: 54,
        flexDirection: 'row',
        alignItems: 'center',
        alignContent: 'center',
    },

    menuItemTxt: {
        fontSize: 15,
        color: '#333',
        marginLeft: 14,
    },

    divideLine: {
        width: '100%',
        height: 1,
        backgroundColor: '#eee',
    },

});