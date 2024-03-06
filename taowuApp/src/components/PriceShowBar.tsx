import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet } from 'react-native';

import Ionicons from 'react-native-vector-icons/Ionicons';
import { CommonColor } from "../common/CommonColor";
import { StyleProp, ViewStyle } from "react-native";


export interface PriceShowBarProps {
    price: string
    titleLayoutStyle?: StyleProp<ViewStyle>
  }

const PriceShowBar = ({ price, titleLayoutStyle }: PriceShowBarProps) => {
    return (
        <View style={[styles.priceShowLayout, titleLayoutStyle]}>
            <Text style={styles.cnyText}>¥</Text>
            <Text style={styles.priceText}>{price}</Text>
        </View>
    );
}


const styles = StyleSheet.create({
    priceShowLayout: {
        flexDirection: 'row',
        alignItems: 'flex-end'
    },

    cnyText: {
        fontSize: 12,
        fontWeight: 'bold',
        color: CommonColor.fontColor
    },

    priceText: {
        fontSize: 14,
        fontWeight: 'bold',
        color: CommonColor.fontColor,
    }
});

export default PriceShowBar;
