import React, {  } from "react";
import { View, StyleSheet } from 'react-native';
import {useSafeAreaInsets} from 'react-native-safe-area-context';
import { CommonColor } from "../../../../common/CommonColor";

type Props = { tab: number; onTabChanged:(tabIndex: number) => void; onAddButtonPress:any};

export default () => {

    return (
        <View style={styles.root}>

            <View style={[styles.titleBarLayout]}>

            </View>
        </View>
    );
}

const styles = StyleSheet.create({
    root: {
        flexDirection: "row", 
        justifyContent: 'space-between', 
        width: '100%'
    },

    titleBarLayout: {
        width: '100%',
        flexDirection: 'row',
        backgroundColor: 'white',
        paddingHorizontal: 8,
        paddingVertical: 2,
        flex: 1
    },

    addButton: {
        justifyContent: 'center',
        alignItems: 'center',
        paddingRight: 12,
        marginRight: 42,
    },

    searchButton: {
        justifyContent: 'center',
    },

    line: {
        width: 0,
        height: 2,
        backgroundColor: '#ff2442',
        borderRadius: 1,
        position: 'absolute',
        bottom: 6,
    },

    tabTextButton: {
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        paddingLeft: 10
    },

    tabText: {
        fontSize: 12,
        color: CommonColor.fontColor,
    },

    tabTextSelected: {
        fontSize: 13,
        color: CommonColor.fontColor,
        fontWeight: 'bold',
        borderBottomWidth: 2,
        borderBottomColor: CommonColor.transparentMainColor2,
    },

    threeLineTag: {
        backgroundColor: CommonColor.tagBg,
        borderRadius: 3,
        paddingVertical: 4,
        paddingHorizontal: 10,
        marginRight: 5,
        flexDirection: "row",
        justifyContent: "center"
    },

    threeLineTagText: {
        color: CommonColor.deepGrey,
        fontSize: 10
    },

    clickIcon: {
        color: CommonColor.normalGrey,
        fontSize: 8,
        transform: [{ rotate: '90deg' }],
    }
});