import React, { useEffect, useState } from "react";
import { Text } from "react-native";
import { TouchableOpacity } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { formatNumber } from "../utils/StrUtil";

type Props = {
    value: number;
    onValueChanged?: (value: number) => void;
}

export default (props: Props) => {

    const { value, onValueChanged } = props;

    const [labelText, setLabelText]= useState<number>(0);


    useEffect(() => {
        setLabelText(value);
    }, [value]);


    return (
        <Text style={{color: 'white'}}>
            {formatNumber(labelText)}
          </Text>
    );
}