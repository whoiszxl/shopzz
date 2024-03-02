import React, { useEffect, useState, forwardRef, useImperativeHandle, useCallback } from 'react';
import {
    View,
    Text,
    StyleSheet,
    Image,
    LayoutAnimation,
    TouchableOpacity,
    StatusBar,
    Modal,
    Dimensions,
} from 'react-native';
import Entypo from 'react-native-vector-icons/Entypo';
import { CommonColor } from '../../../../common/CommonColor';


const { width: SCREEN_WIDTH } = Dimensions.get('window');

type Props = {
    categoryList: Category[];
};

export interface TabModalRef {
    show: () => void;
    hide: () => void;
}

export default forwardRef((props: Props, ref) => {

    const { categoryList } = props;

    const [visible, setVisible] = useState<boolean>(false);

    const [edit, setEdit] = useState<boolean>(false);

    useEffect(() => {
        if (!categoryList) {
            return;
        }
    }, [categoryList]);

    const show = () => {
        setVisible(true);
    }

    const hide = () => {
        setVisible(false);
    }

    useImperativeHandle(ref, () => {
        return {
            show, hide,
        }
    });


    const renderMyList = () => {
        return (
            <>
            <View style={styles.row}>
                <Text style={styles.titleText}>我的频道</Text>
                <Text style={styles.subTitleText}>点击进入频道</Text>
                <TouchableOpacity style={styles.closeButton} onPress={hide}>
                    <Entypo name='chevron-up' size={20}/>
                </TouchableOpacity>
            </View>
            <View style={styles.listContent}>
                {categoryList.map((item: Category, index: number) => {
                    return (
                        <TouchableOpacity
                            key={`${item.name}`}
                            style={styles.itemLayoutDefault}
                            onPress={() => {
                                console.log("点击了");

                                hide()
                            }}
                        >
                            <Text style={styles.itemText}>{item.name}</Text>
                            
                        </TouchableOpacity>
                    );
                })}
            </View>
            </>
        );
    }


    return (
        <Modal
            transparent={true}
            visible={visible}
            statusBarTranslucent={true}
            animationType='fade'
            onRequestClose={hide}
        >
            <View style={styles.root}>
                <View style={styles.content}>
                    {renderMyList()}
                </View>
                <View style={styles.mask}/>
            </View>
        </Modal>
    );
})

const styles = StyleSheet.create({
    root: {
        width: '100%',
        height: '100%',
        backgroundColor: 'transparent',
    },
    content: {
        width: '100%',
        backgroundColor: 'white',
        marginTop: 58 + (StatusBar.currentHeight || 0),
        paddingBottom: 15,
    },
    mask: {
        width: '100%',
        flex: 1,
        backgroundColor: '#00000060',
    },
    row: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
    },
    titleText: {
        fontSize: 13,
        color: CommonColor.fontColor,
        fontWeight: 'bold',
        marginLeft: 16,
    },
    subTitleText: {
        fontSize: 10,
        color: CommonColor.deepGrey,
        marginLeft: 12,
        flex: 1,
    },
    editButton: {
        paddingHorizontal: 10,
        height: 28,
        backgroundColor: CommonColor.transparentGreyBg,
        borderRadius: 2,
        justifyContent: 'center',
        alignItems: 'center',
    },
    editText: {
        fontSize: 13,
        color: '#3050ff',
    },
    closeButton: {
        paddingRight:10,
        paddingTop: 16
    },
    closeImg: {
        width: 16,
        height: 16,
        resizeMode: 'contain',
        transform: [{rotate: '90deg'}]
    },
    listContent: {
        width: '100%',
        flexDirection: 'row',
        flexWrap: 'wrap',
    },
    itemLayout: {
        width: SCREEN_WIDTH - 80 >> 2,
        height: 32,
        justifyContent: 'center',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#eee',
        borderRadius: 6,
        marginLeft: 16,
        marginTop: 12,
    },
    itemLayoutDefault: {
        width: SCREEN_WIDTH - 80 >> 2,
        height: 26,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#eee',
        borderRadius: 3,
        marginLeft: 16,
        marginTop: 10,
    },
    itemText: {
        fontSize: 11,
        fontWeight: 'bold',
        color: CommonColor.fontColor,
    },
});

