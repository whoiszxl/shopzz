import React, { useEffect, useState } from "react";
import { TouchableOpacity } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';

type Props = {
    value: boolean;
    onValueChanged?: (value: boolean) => void;
    size?: number;
    likeIcon?: string;
    unlikeIcon?: string;
    likeColor?: string;
    unlikeColor?: string;
}

export default (props: Props) => {

    const { value, onValueChanged, size = 15, likeIcon = 'heart', unlikeIcon = 'heart-outline', likeColor = 'red', unlikeColor = 'grey' } = props;

    const [showState, setShowState]= useState<boolean>(false);
    useEffect(() => {
        setShowState(value);
    }, [value]);

    const onHeartPress = () => {
        const newState = !showState;
        setShowState(newState);
        onValueChanged?.(newState);
    }

    return (
        <TouchableOpacity onPress={onHeartPress} >
            <Ionicons size={size}  name={showState ? likeIcon : unlikeIcon}  color={showState ? likeColor : unlikeColor} />
        </TouchableOpacity>
    );
}