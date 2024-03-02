import React, { useEffect, useState } from "react";
import { TouchableOpacity, StyleSheet } from 'react-native';
import MaterialIcons from 'react-native-vector-icons/MaterialIcons';
import { CommonColor } from "../common/CommonColor";


type Props = {
    value: boolean;
    onValueChanged?: (value: boolean) => void;
    size?: number;
}

export default (props: Props) => {

    const { value, onValueChanged, size = 13 } = props;

    const [showState, setShowState]= useState<boolean>(false);

    useEffect(() => {
        setShowState(value);
    }, [value]);

    const onPress = () => {
        const newState = !showState;
        setShowState(newState);
        onValueChanged?.(newState);
    }

    return (
        <TouchableOpacity onPress={onPress}>

            <MaterialIcons 
                size={size} 
                name={showState ? "thumb-up-alt" : "thumb-up-off-alt"}
                color={showState ? CommonColor.mainColorTwo : CommonColor.normalGrey}
            />
        </TouchableOpacity>
    );
}