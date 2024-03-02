import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet } from 'react-native';

import Ionicons from 'react-native-vector-icons/Ionicons';
import { CommonColor } from "../../../../common/CommonColor";




export default () => {

    return (
        <View style={styles.titleLayout}>
             <View style={styles.searchBar}>
                <Ionicons name="search" size={14} color="black" style={styles.icon} />
                <View style={styles.placeholderTextLayout}>
                    <Text style={styles.searchText}>高级穿搭</Text>
                </View>
            </View>
            <Ionicons name="camera" size={26} color="black" style={styles.cameraIcon} />
        </View>
    );
}

const styles = StyleSheet.create({
    titleLayout: {
        width: '100%',
        height: 58,
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: 'white',
        paddingHorizontal: 0
    },
    searchBar: {
        flex: 1,
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: CommonColor.normalBg,
        borderRadius: 5,
        paddingHorizontal: 10,
        paddingVertical: 7,
        marginHorizontal: 10,
    },

    cameraIcon: {
        paddingRight: 5
    },
      icon: {
        marginRight: 4,
      },
      placeholderTextLayout: {
        flex: 1,
        alignContent: 'center',
      },

      searchText: {
        color: CommonColor.deepGrey,
        fontSize: 12,
        paddingLeft: 4,
      }
});