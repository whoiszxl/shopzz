import React, { useCallback, useEffect, useMemo, useRef, useState } from 'react'
import { Animated, Platform, StyleProp, StyleSheet, TextStyle, View, ViewStyle } from 'react-native'
import TabBarIndicator from './TabBarIndicator'
import TabBarItem from './TabBarItem'

export interface TabBarProps {
  tabs: string[]
  onTabPress: (index: number) => void
  onTabsLayout?: (layouts: Layout[]) => void
  position: Animated.Value
  offset: Animated.Value
  page: number
  isIdle: boolean
  scrollState: 'idle' | 'dragging' | 'settling'
  spacing?: number
  style?: StyleProp<ViewStyle>
  tabStyle?: StyleProp<ViewStyle>
  labelStyle?: StyleProp<TextStyle>
  indicatorStyle?: StyleProp<ViewStyle>
}

interface Layout {
  x: number
  y: number
  width: number
  height: number
}

export default function TabBar({
  tabs,
  onTabPress,
  onTabsLayout,
  position,
  offset,
  page,
  isIdle,
  scrollState,
  spacing = 8,
  style,
  tabStyle,
  labelStyle,
  indicatorStyle,
}: TabBarProps) {
  const inputRange = useMemo(() => tabs.map((_, index) => index), [tabs])
  const [outputRange, setOutputRange] = useState(inputRange.map(() => 0))
  const indicatorWidth = getIndicatorWidth(indicatorStyle)
  const layouts = useRef<Layout[]>([]).current

  const handleTabLayout = useCallback(
    (index: number, layout: Layout) => {
      layouts[index] = layout

      const length = layouts.filter(layout => layout.width > 0).length
      if (length !== tabs.length) {
        return
      }

      const range: number[] = []
      for (let index = 0; index < length; index++) {
        const { x, width } = layouts[index]
        // 我们希望指示器和所选 Tab 垂直居中对齐
        // 那么指示器的 x 轴偏移量就是 Tab 的 center.x - 指示器的 center.x
        const tabCenterX = x + width / 2
        const indicatorCenterX = indicatorWidth / 2
        range.push(tabCenterX - indicatorCenterX)
      }

      console.log('---------------onTabLayout-------------------')
      setOutputRange(range)
      onTabsLayout?.(layouts)
    },
    [onTabsLayout, tabs, layouts, indicatorWidth],
  )

  const scrollX = Animated.add(offset, position).interpolate({
    inputRange,
    outputRange,
  })

  const lastPage = useLastPage(page, isIdle)
  const interactive = useInteractive(scrollState)

  console.log('lastPage', lastPage, 'currentPage', page)

  const handleTabPress = useCallback(
    (index: number) => {
      if (isIdle) {
        onTabPress?.(index)
      } else {
        console.log('不空闲，无法点击')
      }
    },
    [onTabPress, isIdle],
  )

  return (
    <View style={[styles.tabbar, style]}>
      {tabs.map((tab: string, index: number) => {
        const enhanced = interactive || index === page || index === lastPage

        let scale = Animated.add(offset, position).interpolate({
          inputRange: [index - 1, index, index + 1],
          outputRange: [1, enhanced ? 1.2 : 1, 1],
          extrapolate: 'clamp',
        })

        let opacity = Animated.add(offset, position).interpolate({
          inputRange: [index - 1, index, index + 1],
          outputRange: [0.8, enhanced ? 1 : 0.8, 0.8],
          extrapolate: 'clamp',
        })

        if (Platform.OS === 'ios' && Math.abs(page - lastPage) > 1 && index === lastPage) {
          scale = Animated.add(offset, position).interpolate({
            inputRange: [page - 1, page, page + 1],
            outputRange: [1.2, 1, 1.2],
            extrapolate: 'clamp',
          })

          opacity = Animated.add(offset, position).interpolate({
            inputRange: [page - 1, page, page + 1],
            outputRange: [1, 0.8, 1],
            extrapolate: 'clamp',
          })
        }

        return (
          <TabBarItem
            key={tab}
            title={tab}
            onPress={() => handleTabPress(index)}
            onLayout={event => handleTabLayout(index, event.nativeEvent.layout)}
            style={[tabStyle, { marginLeft: index === 0 ? 0 : spacing }]}
            labelStyle={[labelStyle, { opacity, transform: [{ scale }] }]}
          />
        )
      })}
      <TabBarIndicator style={[styles.indicator, indicatorStyle]} scrollX={scrollX} />
    </View>
  )
}

function useInteractive(scrollState: 'idle' | 'dragging' | 'settling') {
  const interactiveRef = useRef(false)
  const scrollStateRef = useRef<'idle' | 'dragging' | 'settling'>(scrollState)

  useEffect(() => {
    scrollStateRef.current = scrollState
  }, [scrollState])

  if (scrollState === 'dragging') {
    interactiveRef.current = true
  }

  if (
    scrollState === 'idle' &&
    // 防止 overdrag 时，读到过期的 idle 回调信息
    (Platform.OS === 'android' || scrollStateRef.current === 'settling')
  ) {
    interactiveRef.current = false
  }

  return interactiveRef.current
}

function useLastPage(page: number, isIdle: boolean) {
  const lastPageRef = useRef(0)

  useEffect(() => {
    if (isIdle) {
      lastPageRef.current = page
    }
  }, [isIdle, page])

  return lastPageRef.current
}

function getIndicatorWidth(indicatorStyle: StyleProp<ViewStyle>) {
  const { width } = StyleSheet.flatten([styles.indicator, indicatorStyle])
  if (typeof width === 'number') {
    return width
  }
  return styles.indicator.width
}

const styles = StyleSheet.create({
  tabbar: {
    flexDirection: 'row',
    height: 48,
    alignItems: 'center',
    backgroundColor: '#FFFFFF',
  },
  indicator: {
    width: 24,
  },
})
