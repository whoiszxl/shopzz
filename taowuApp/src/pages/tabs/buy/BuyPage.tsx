import React, { useEffect } from 'react'
import { Animated, Image, StatusBar, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import PagerView from 'react-native-pager-view'
import usePagerView from './components/TabBar/usePagerView'
import TabBar from './components/TabBar/TabBar'
import LangPage from './LangPage'
import ScrollBar from './components/TabBar/ScrollBar'
import SearchBar from '../../../components/SearchBar';
import { CommonColor } from '../../../common/CommonColor';
import LinearGradient from 'react-native-linear-gradient';
import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import Ionicons from 'react-native-vector-icons/Ionicons';
import BuyRecommendPage from './BuyRecommendPage';
import { observer, useLocalStore } from 'mobx-react'
import BuyStore from './BuyStore'



const AnimatedPagerView = Animated.createAnimatedComponent<typeof PagerView>(PagerView)

const tabNameList = ['推荐', '潮服', '数码', '鞋类', '手表', '配饰', '潮玩', '运动', '美妆']


export default observer(() => {
  const {
    pagerRef,
    setPage,
    page,
    position,
    offset,
    isIdle,
    scrollState,
    onPageScroll,
    onPageSelected,
    onPageScrollStateChanged,
  } = usePagerView()


  const store = useLocalStore(() => new BuyStore());

  useEffect(() => {
    store.requestOneCategoryList()
  }, []);

  return (
    <View style={styles.container}>
      
      <View style={{height: (StatusBar.currentHeight || 0) + 50, width: '100%', backgroundColor: 'white'}}>
        <LinearGradient 
          style={styles.menuItem} 
          colors={[CommonColor.mainColorTwoTrans, '#FBEBF8']}
          start={{x: 0, y: 0.5}}
          end={{x: 1, y: 0.5}}  
        >
          <View style={styles.titleAdLayout}>
          <View style={styles.leftContainer}>
            <Image source={require('../../../assets/images/ic_launcher_small.png')} style={styles.imageIcon} />
            <Text style={styles.leftText}>淘物 x 拼咚咚联名来了</Text>
            <Ionicons name="chevron-forward-outline" size={18} color={CommonColor.fontColor} />
          </View>

          <TouchableOpacity style={styles.rightContainer}>
            <MaterialCommunityIcons name="calendar-check-outline" size={22} color={CommonColor.mainColorTwoDeep} />
            <Text style={styles.rightText}>签到</Text>
          </TouchableOpacity>

          </View>
        </LinearGradient>
      </View>
      
      <SearchBar titleLayoutStyle={{paddingTop: 3}} />


      <ScrollBar style={styles.scrollbar} page={page}>
        <TabBar
          style={styles.tabbar}
          tabStyle={styles.tab}
          labelStyle={styles.labelStyle}
          tabs={tabNameList}
          onTabPress={setPage}
          position={position}
          offset={offset}
          page={page}
          isIdle={isIdle}
          scrollState={scrollState}
        />
      </ScrollBar>
      <AnimatedPagerView
        ref={pagerRef}
        style={styles.pager}
        overdrag={true}
        overScrollMode="always"
        onPageScroll={onPageScroll}
        onPageSelected={onPageSelected}
        onPageScrollStateChanged={onPageScrollStateChanged}>
        {tabNameList.map(tabName => (
          tabName === '推荐' ? 
          <BuyRecommendPage key={tabName} />
          :
          <LangPage key={tabName} type={tabName} />
        ))}
      </AnimatedPagerView>
    </View>
  )
  
})


const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  scrollbar: {
    height: 35,
    flexGrow: 0,
  },
  menuItem: {
    paddingHorizontal: 5,
    paddingVertical: 8,
    marginRight: 10,
    borderRadius: 4,
    backgroundColor: 'rgba(255, 165, 0, 0.05)',
    width: '100%',
    height: (StatusBar.currentHeight || 0) + 50
  },
  tabbar: {
    height: '100%',
    borderBottomColor: CommonColor.transparentGreyBg,
    borderBottomWidth: 0.4,
  },
  tab: {
    paddingHorizontal: 5,
  },

  labelStyle: {
    fontSize: 12,
    color: 'black',
  },
  pager: {
    flex: 1,
  },

  titleAdLayout: {
    paddingTop: StatusBar.currentHeight || 0,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    padding: 10,
  },

  leftContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  imageIcon: {
    width: 24,
    height: 24,
    marginRight: 5,
    borderRadius: 2
  },
  leftText: {
    fontSize: 15,
    fontWeight: 'bold',
    color: CommonColor.fontColor
  },
  rightContainer: {
    alignItems: 'center',
  },
  rightText: {
    marginTop: 0,
    fontSize: 10,
    fontWeight: 'bold',
    color: CommonColor.mainColorTwoDeep
  },
})