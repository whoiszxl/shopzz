import React from 'react'
import { StyleSheet } from 'react-native'
import ScrollBar from './ScrollBar'
import TabBar, { TabBarProps } from './TabBar'

export default function (props: TabBarProps) {
  return (
    <ScrollBar page={props.page} style={styles.scrollbar} contentContainerStyle={{ flexGrow: 1 }}>
      <TabBar style={styles.tabbar} tabStyle={styles.tab} {...props} />
    </ScrollBar>
  )
}

const styles = StyleSheet.create({
  scrollbar: {
    height: 48,
    flexGrow: 0,
  },
  tabbar: {
    height: '100%',
    borderBottomColor: '#eee',
    borderBottomWidth: 1,
    flex: 1,
    justifyContent: 'space-evenly',
  },
  tab: {
    paddingHorizontal: 16,
  },
})
