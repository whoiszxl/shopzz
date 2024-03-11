import React, {useEffect, useState, useRef, useCallback} from 'react';
import {
    View,
    Image,
    Dimensions,
    Modal,
    StyleSheet,
    Animated,
    Platform,
    TouchableOpacity,
    ImageSourcePropType,
    LayoutAnimation,
} from 'react-native';
import {PropsTypes} from './PropsTypes';

import {Indicator} from './indicator';
import {styles} from './style';

import Ionicons from 'react-native-vector-icons/Ionicons';


const { width } = Dimensions.get('screen');
const Os = Platform.OS;
export type DataType = {img: ImageSourcePropType};
export const ImageSlider = ({
    data = [],
    previewImageContainerStyle = {},
    previewImageStyle = {},
    caroselImageStyle = {},
    caroselImageContainerStyle = {},
    timer = 2000,
    autoPlay = false,
    showIndicator = true,
    activeIndicatorStyle = {},
    inActiveIndicatorStyle = {},
    indicatorContainerStyle = {},
    onItemChanged = itemData => {},
    localImg = false,
    onClick = (item: DataType, index: number) => {},
    preview = true,
    children,
    blurRadius = 50,
}: PropsTypes) => {
    const scrollX = React.useRef(new Animated.Value(0)).current;
    const imageW = width * 0.7;
    const [selectedIndex, setSelectedIndex] = useState(0);
    const [imageViewer, setImageViewer] = useState(false);
    const [currentIndex, setCurrentIndex] = useState(0);
    const slider = useRef(null);
    const timerRef = useRef<any>(null);
    const onViewRef = React.useRef(({viewableItems}: any) => {
        // Use viewable items in state or as intended
        if (viewableItems.length > 0) {
            let index = viewableItems[0].index;
            onItemChanged(viewableItems[0].item);
            setSelectedIndex(index);
        }
    });
    const viewConfigRef = React.useRef({viewAreaCoveragePercentThreshold: 50});

    useEffect(() => {
        if (autoPlay) {
            if (data.length > 0) startAutoPlay(imageViewer ? true : false);
        }
    }, []);

    useEffect(() => {
        if (!imageViewer) {
            if (autoPlay) {
                if (data.length > 0) startAutoPlay(imageViewer ? true : false);
            }
        } else {
            clearTimeout(timerRef?.current);
        }
    }, [currentIndex, imageViewer]);

    const switchViewer = useCallback(() => {
        LayoutAnimation.easeInEaseOut();
        setImageViewer(!imageViewer)
    }, [imageViewer]);

    const changeSliderListIndex = () => {
        if (slider.current) {
            if (currentIndex == data.length - 1) {
                setCurrentIndex(0);
                // @ts-ignore
                slider.current.scrollToIndex({
                    index: currentIndex,
                    animated: true,
                });
            } else {
                setCurrentIndex(currentIndex + 1);
                // @ts-ignore
                slider.current.scrollToIndex({
                    index: currentIndex,
                    animated: true,
                });
            }
        }
    };

    const startAutoPlay = (isViewer: boolean) => {
        if (!imageViewer) {
            (viewer => {
                let viewBool = viewer;
                timerRef.current = setTimeout(() => {
                    if (!viewBool) {
                        changeSliderListIndex();
                    }
                }, timer);
            })(isViewer);
        }
    };

    const previewImage = () => {
        return (
            <Modal
                visible={imageViewer}
                onDismiss={switchViewer}
                onRequestClose={switchViewer}>
                <View style={StyleSheet.absoluteFillObject}>
                    {data.map((val, ind) => {
                        const inputRange = [
                            (ind - 1) * width,
                            ind * width,
                            (ind + 1) * width,
                        ];
                        const opacity = scrollX.interpolate({
                            inputRange,
                            outputRange: [0, 1, 0],
                        });
                        return (
                            <Animated.Image
                                key={`image-${ind}`}
                                // @ts-ignore
                                source={localImg ? val.img : {uri: val.img}}
                                style={[
                                    StyleSheet.absoluteFillObject,
                                    {opacity},
                                ]}
                                blurRadius={blurRadius}
                            />
                        );
                    })}
                </View>

                <Animated.FlatList
                    data={data}
                    keyExtractor={(_, index) => index.toString()}
                    onScroll={Animated.event(
                        [{nativeEvent: {contentOffset: {x: scrollX}}}],
                        {useNativeDriver: true},
                    )}
                    horizontal
                    pagingEnabled
                    initialScrollIndex={selectedIndex}
                    pinchGestureEnabled={true}
                    onScrollToIndexFailed={info => {
                        const wait = new Promise(resolve =>
                            setTimeout(resolve, 500),
                        );
                        wait.then(() => {
                            // @ts-ignore
                            slider.current?.scrollToIndex({
                                index: info.index,
                                animated: true,
                            });
                        });
                    }}
                    showsHorizontalScrollIndicator={false}
                    renderItem={({item, index}) => {
                        return (
                            <View
                                style={[
                                    styles.previewImageContainerStyle,
                                    previewImageContainerStyle,
                                ]}>
                                <Image
                                    // @ts-ignore
                                    source={
                                        localImg ? item.img : {uri: item.img}
                                    }
                                    style={[
                                        styles.previewImageStyle,
                                        previewImageStyle,
                                    ]}
                                />
                                <TouchableOpacity
                                    onPress={() => {
                                        setSelectedIndex(index);
                                        switchViewer();
                                    }}
                                    style={{
                                        position: 'absolute',
                                        top: Os == 'ios' ? 30 : 12,
                                        left: 12,
                                    }}>

                                    <Ionicons name='close' size={24} color={'white'}/>
                                </TouchableOpacity>
                            </View>
                        );
                    }}
                />
            </Modal>
        );
    };
    return (
        <View>
            {imageViewer && previewImage()}
            <Animated.FlatList
                ref={slider}
                data={data}
                keyExtractor={(_, index) => index.toString()}
                onScroll={Animated.event(
                    [{nativeEvent: {contentOffset: {x: scrollX}}}],
                    {useNativeDriver: true},
                )}
                horizontal
                pagingEnabled
                snapToInterval={width}
                decelerationRate="fast"
                pinchGestureEnabled={true}
                showsHorizontalScrollIndicator={false}
                onViewableItemsChanged={onViewRef.current}
                viewabilityConfig={viewConfigRef.current}
                initialScrollIndex={selectedIndex}
                onScrollToIndexFailed={info => {
                    const wait = new Promise(resolve =>
                        setTimeout(resolve, 500),
                    );
                    wait.then(() => {
                        // flatList.current?.scrollToIndex({ index: info.index, animated: true });
                    });
                }}
                renderItem={({item, index}) => {
                    return (
                        <View style={[caroselImageContainerStyle]}>
                            <>
                                <TouchableOpacity
                                    activeOpacity={0.9}
                                    onPress={() => {
                                        if (!preview) {
                                            onClick(item, index);
                                        } else {
                                            setSelectedIndex(index);
                                            switchViewer();
                                        }
                                    }}>
                                    <Image
                                        // @ts-ignore
                                        source={
                                            localImg
                                                ? item.img
                                                : {uri: item.img}
                                        }
                                        style={[
                                            styles.caroselImageStyle,
                                            caroselImageStyle,
                                        ]}
                                    />
                                </TouchableOpacity>
                                {children}
                            </>
                        </View>
                    );
                }}
            />
            <View
                style={{
                    width: '100%',
                    position: 'absolute',
                    alignSelf: 'center',
                    backgroundColor: 'red',
                    bottom: 20,
                }}>
                {showIndicator && (
                    <Indicator
                        data={data}
                        currenIndex={selectedIndex}
                        indicatorContainerStyle={indicatorContainerStyle}
                        activeIndicatorStyle={activeIndicatorStyle}
                        inActiveIndicatorStyle={inActiveIndicatorStyle}
                    />
                )}
            </View>
        </View>
    );
};
