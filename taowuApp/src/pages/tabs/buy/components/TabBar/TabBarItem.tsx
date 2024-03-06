import React from 'react'
import {
  Animated,
  Pressable,
  StyleProp,
  StyleSheet,
  TextStyle,
  ViewProps,
  ViewStyle,
} from 'react-native'

interface TabBarItemProps {
  title: string
  onPress?: () => void
  onLayout: ViewProps['onLayout']
  style?: StyleProp<ViewStyle>
  labelStyle?:
    | Animated.WithAnimatedObject<TextStyle>
    | Animated.WithAnimatedArray<StyleProp<TextStyle>>
}

export default function TabBarItem({
  title,
  style,
  labelStyle,
  onPress,
  onLayout,
}: TabBarItemProps) {
  return (
    <Pressable style={[styles.tab, style]} onPress={onPress} onLayout={onLayout}>
      <Animated.Text style={[styles.label, labelStyle]}>{title}</Animated.Text>
    </Pressable>
  )
}

const styles = StyleSheet.create({
  tab: {
    height: '100%',
    minWidth: 24,
    justifyContent: 'center',
    alignItems: 'center',
  },
  label: {
    color: '#333333',
    fontSize: 16,
  },
})
