import { useCallback, useMemo, useRef, useState } from 'react'
import { Animated, Platform } from 'react-native'
import type {
  default as PagerView,
  PagerViewOnPageScrollEventData,
  PagerViewOnPageSelectedEvent,
  PageScrollState,
  PageScrollStateChangedNativeEvent,
} from 'react-native-pager-view'

export default function usePagerView(initialPage = 0) {
  const pagerRef = useRef<PagerView>(null)

  const [activePage, setActivePage] = useState(initialPage)
  const [isIdle, setIdle] = useState(true)

  const setPage = useCallback(
    (page: number, animated = true) => {
      if (animated) {
        pagerRef.current?.setPage(page)
      } else {
        pagerRef.current?.setPageWithoutAnimation(page)
      }
      console.log(time() + ' setPage', page)
      setActivePage(page)
      if (activePage !== page) {
        setIdle(false)
      }
    },
    [activePage],
  )

  const offset = useRef(new Animated.Value(0)).current
  const position = useRef(new Animated.Value(0)).current

  const onPageScroll = useMemo(
    () =>
      Animated.event<PagerViewOnPageScrollEventData>(
        [
          {
            nativeEvent: {
              offset: offset,
              position: position,
            },
          },
        ],
        {
          listener: ({ nativeEvent: { position, offset } }) => {
            console.log(time() + ' onPageScroll', 'position', position, 'offset', offset)
          },
          useNativeDriver: true,
        },
      ),
    [offset, position],
  )

  const onPageSelected = useCallback((e: PagerViewOnPageSelectedEvent) => {
    console.log(time() + ' onPageSelected', e.nativeEvent.position)
    setActivePage(e.nativeEvent.position)
    if (Platform.OS === 'ios') {
      setIdle(true)
    }
  }, [])

  const [scrollState, setScrollState] = useState<PageScrollState>('idle')

  const onPageScrollStateChanged = useCallback(
    ({ nativeEvent: { pageScrollState } }: PageScrollStateChangedNativeEvent) => {
      console.log(time() + ' onPageScrollStateChanged', pageScrollState)
      setScrollState(pageScrollState)
      setIdle(pageScrollState === 'idle')
    },
    [],
  )

  return {
    pagerRef,
    setPage,
    page: activePage,
    isIdle,
    scrollState,
    position,
    offset,
    onPageScroll,
    onPageSelected,
    onPageScrollStateChanged,
  }
}

function time() {
  const date = new Date()
  return (
    date.getHours() +
    '时' +
    date.getMinutes() +
    '分' +
    date.getSeconds() +
    '秒:' +
    date.getMilliseconds()
  )
}
