import React from 'react'
import { Animated, StyleProp, StyleSheet, ViewStyle } from 'react-native'
import { CommonColor } from '../../../../../common/CommonColor'

interface TabBarIndicatorProps {
  style?: StyleProp<ViewStyle>
  scrollX: Animated.AnimatedInterpolation
}

export default function TabBarIndicator({ style, scrollX }: TabBarIndicatorProps) {
  return (
    <Animated.View
      key={'indicator'}
      style={[styles.indicator, style, { transform: [{ translateX: scrollX }] }]}
    />
  )
}

const styles = StyleSheet.create({
  indicator: {
    position: 'absolute',
    left: 0,
    bottom: 0,
    width: 30,
    height: 2,
    backgroundColor: CommonColor.mainColorTwo,
    borderRadius: 1,
  },
})
