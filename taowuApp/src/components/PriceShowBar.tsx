import React, { useState, useEffect } from "react";
import { View, Text, StyleSheet, TextStyle } from 'react-native';

import Ionicons from 'react-native-vector-icons/Ionicons';
import { CommonColor } from "../common/CommonColor";
import { StyleProp, ViewStyle } from "react-native";


export interface PriceShowBarProps {
    price: string
    titleLayoutStyle?: StyleProp<TextStyle>
    priceSize?: number,
    symbolSize?: number,
  }

const PriceShowBar = ({ price, titleLayoutStyle, priceSize, symbolSize }: PriceShowBarProps) => {
    return (
        <View style={[styles.priceShowLayout]}>
            <Text style={[styles.cnyText, {fontSize: symbolSize}, titleLayoutStyle]}>¥</Text>
            <Text style={[styles.priceText, {fontSize: priceSize}, titleLayoutStyle]}>{price}</Text>
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
