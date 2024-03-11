import React from 'react';
import {StyleSheet, View, ViewStyle} from 'react-native';
import {DataType} from '.';

export const Indicator = ({
    data = [],
    currenIndex = 0,
    activeIndicatorStyle = {},
    inActiveIndicatorStyle = {},
    indicatorContainerStyle = {},
}: {
    data: DataType[];
    currenIndex: number;
    activeIndicatorStyle: ViewStyle;
    inActiveIndicatorStyle: ViewStyle;
    indicatorContainerStyle: ViewStyle;
}) => {
    return (
        <View style={[styles.main, indicatorContainerStyle]}>
            {data.map((value, index) => {
                if (index == currenIndex) {
                    return (
                        <View
                            key={index}
                            style={[
                                styles.activeIndicatorStyle,
                                activeIndicatorStyle,
                            ]}
                        />
                    );
                } else {
                    return (
                        <View
                            key={index}
                            style={[
                                styles.inActiveIndicatorStyle,
                                inActiveIndicatorStyle,
                            ]}
                        />
                    );
                }
            })}
        </View>
    );
};

const styles = StyleSheet.create({
    main: {
        flexDirection: 'row',
        alignItems: 'center',
        alignSelf: 'center',
        justifyContent: 'center',
        position: 'absolute',
    },
    activeIndicatorStyle: {
        height: 5,
        width: 20,
        borderRadius: 100,
        backgroundColor: '#62b5f0',
        margin: 5,
    },
    inActiveIndicatorStyle: {
        height: 10,
        width: 10,
        borderRadius: 100,
        backgroundColor: 'rgb(223, 231, 245)',
        margin: 5,
    },
});
