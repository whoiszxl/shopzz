import { observer, useLocalStore } from 'mobx-react';
import React, { useEffect } from 'react'
import { Dimensions, Image, StyleSheet, Text, TouchableOpacity, View } from 'react-native'
import BuyStore from './BuyStore';
import { CommonColor } from '../../../common/CommonColor';
import LinearGradient from 'react-native-linear-gradient';
import PriceShowBar from '../../../components/PriceShowBar';
import FlowList from '../../../components/flowlist/FlowList.js';
import ResizeImage from '../../../components/ResizeImage';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

export default observer(() => {

    const store = useLocalStore(() => new BuyStore());

    const navigation = useNavigation<StackNavigationProp<any>>();


    useEffect(() => {
      store.requestChildCategoryList('4');
      store.requestIndexSpuList();
    }, []);

    const refreshNewData = () => {
      store.resetPage();
    }

    
    const loadMoreData = () => {
      store.requestIndexSpuList();
    }

    const Footer = () => {
      return (
          <Text style={styles.footerTxt}>已经滑到底了...</Text>
      );
    }



    const renderItem = ({item, index}: {item: IndexSpuEntity, index: number}) => {
      return (


          <TouchableOpacity style={styles.item} onPress={() => { navigation.push('ProductDetailPage', {id: item.id}) }}>
              <ResizeImage uri={item.defaultPic} />
              <Text numberOfLines={2} style={styles.descText}>{item.name}</Text>
              <View style={styles.priceBottomContainer}>
                <Text style={styles.defaultPriceText}>{'¥' + item.defaultPrice}</Text>
                <Text style={styles.buyCountText}>2.41万+人付款</Text>
              </View>
          </TouchableOpacity>
      );
    }


    const renderHeader = () => {
      return (
        <View style={{width: '100%'}}>
            
        {/** 推荐分类栏目 */}
        <View style={styles.categoryContainer}>
          {store.childCategoryList.slice(0, 5).map((item : TwoLevelCategory) => (
            <View style={styles.categoryItemContainer} key={item.id}>
              <Image source={{uri: item.childList[0].icon}} style={styles.categoryImage} />
              <Text style={styles.categoryText}>{item.childList[0].name}</Text>
            </View>
          ))}
        </View>

        {/** 超值好价促销栏目 */}
        <View style={styles.goodPriceContainer}>

          <LinearGradient
            colors={['rgba(252,241,239,0.7)', 'rgba(252,241,239,0.2)', 'rgba(252,241,239,0.1)', 'rgba(252,241,239,0.0)']} // 渐变色数组
            style={{}}
          >
            <View style={styles.titleContainer}> 
              <Text style={styles.leftText}>超值好价促销栏目</Text>
              <Text style={styles.rightText}>20元起包邮到家  &gt;</Text>
            </View>

            <View style={styles.goodPriceProductInfo}>
              {store.childCategoryList.slice(0, 4).map((item : TwoLevelCategory) => (
              <View style={styles.goodPriceItemContainer} key={item.id}>
                <Image source={{uri: item.childList[0].icon}} style={styles.goodPriceImage} />
                
                <View style={{flexDirection: 'row', alignItems: 'center'}}>
                  <PriceShowBar price='18'/>
                  <Text style={{fontSize: 10, textDecorationLine: 'line-through'}}>199</Text>
                </View>
              </View>
            ))}
            </View>
          </LinearGradient>
            
        </View>


        {/** 活动栏目 */}
        <View style={styles.goodPriceContainer}>

          <LinearGradient 
            style={styles.menuItem} 
            colors={[CommonColor.mainColorTwoTrans, 'white']}
          >
            <View style={styles.leftContainer}>
              <Text style={styles.activityMainText}>618 钜惠来袭</Text>
              <Text style={styles.activitySubText}>好礼5折起 ›</Text>
            </View>

            <View style={styles.rightContainer}>
                <View style={styles.rightItemContainer}>
                  <Image source={{uri: 'http://qiniu.whoiszxl.com/test_pic/1.jpg'}} style={styles.rightItemImg} />
                  <View style={styles.daoshoujiaContainer}>
                    <Text style={styles.daoshoujiaText}>到手价</Text>
                    <Text style={styles.daoshoujiaPriceText}> ¥108</Text>
                  </View>
                </View>
                <View style={styles.rightItemContainer}>
                  <Image source={{uri: 'http://qiniu.whoiszxl.com/test_pic/2.jpg'}} style={styles.rightItemImg} />
                  <View style={styles.daoshoujiaContainer}>
                    <Text style={styles.daoshoujiaText}>到手价</Text>
                    <Text style={styles.daoshoujiaPriceText}> ¥99</Text>
                  </View>
                </View>
                <View style={styles.rightItemContainer}>
                  <Image source={{uri: 'http://qiniu.whoiszxl.com/test_pic/3.jpg'}} style={styles.rightItemImg} />
                  <View style={styles.daoshoujiaContainer}>
                    <Text style={styles.daoshoujiaText}>到手价</Text>
                    <Text style={styles.daoshoujiaPriceText}> ¥233</Text>
                  </View>
                </View>
            </View>

          </LinearGradient>

        </View>
      </View> 

      );
    }

    return (
        <View style={styles.container}> 

          {/** 首页推荐商品列表 */}
          <FlowList
            style={styles.flatList}
            data={store.jobList}
            keyExtrator={(item: IndexSpuEntity) => `${item.id}`}
            extraData={[ store.refreshing ]}
            renderItem={renderItem}
            ListHeaderComponent={renderHeader}
            numColumns={2}
            refreshing={store.refreshing}
            onRefresh={refreshNewData}
            onEndReachedThreshold={0.1}
            onEndReached={loadMoreData}
            ListFooterComponent={<Footer />}
          />

        </View>
      );

      

})


const styles = StyleSheet.create({
    container: {
      width: '100%',
      height: '100%',
      alignItems: 'center',
      backgroundColor: CommonColor.whiteBg
    },
    text: {
      fontSize: 30,
      marginTop: 16,
      color: '#333333',
    },

    categoryContainer: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      paddingHorizontal: 10,
      backgroundColor: CommonColor.whiteBg,
      paddingVertical: 7
    },
    
    categoryItemContainer: {
      flex: 1,
      alignItems: 'center',
    },
    categoryImage: {
      width: 30,
      height: 30,
      marginBottom: 5,
    },
    categoryText: {
      fontSize: 10,
      fontWeight: '400',
      color: CommonColor.fontColor
    },


    goodPriceContainer: {
      flexDirection: 'column',
      justifyContent: 'space-between',
      alignItems: 'center',
      marginHorizontal: 5,
      backgroundColor: CommonColor.whiteBg,
      marginVertical: 7,
      borderColor: CommonColor.transparentGreyBg,
    },

    titleContainer: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      marginBottom: 10,
      width: '100%',
      paddingHorizontal: 8,
      paddingTop: 10,
    },

    leftText: {
      fontSize: 14,
      marginRight: 'auto',
      fontWeight: 'bold',
      color: CommonColor.fontColor,
    },
    rightText: {
      fontSize: 11,
      marginLeft: 'auto',
      color: CommonColor.normalGrey
    },



    goodPriceProductInfo: {
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',
      paddingHorizontal: 10,
      backgroundColor: CommonColor.whiteBg,
      paddingVertical: 7
    },
    
    goodPriceItemContainer: {
      flex: 1,
      alignItems: 'center',
    },
    goodPriceImage: {
      width: 45,
      height: 45,
      marginBottom: 5,
    },
    goodPriceText: {
      fontSize: 10,
      fontWeight: '400',
      color: CommonColor.fontColor
    },

    menuItem: {
      paddingHorizontal: 5,
      paddingVertical: 8,
      borderRadius: 4,
      backgroundColor: 'rgba(255, 165, 0, 0.05)',
      width: '100%',
      height: 100,
      flexDirection: 'row',
      justifyContent: 'space-between',
      alignItems: 'center',

    },

    leftContainer: {
      
    },

    activityMainText: {
      fontSize: 19,
      marginBottom: 2,
      fontWeight: 'bold',
      color: CommonColor.fontColor
    },

    activitySubText: {
      fontSize: 12,
      marginBottom: 10,
      color: CommonColor.fontColor
    },

    rightContainer: {
      alignItems: 'center',
      flexDirection: 'row'
    },

    rightItemContainer: {
      width: 68,
      height: 85,
      backgroundColor: CommonColor.whiteBg,
      marginLeft: 5,
      flexDirection: 'column',
      justifyContent: 'center',
      alignItems: 'center'
    },

    rightItemImg: {
      width: 68,
      height: 60
    },

    daoshoujiaContainer: {
      flexDirection: 'row',
      alignItems: 'center', 
    },
    daoshoujiaText: {
      fontSize: 9,
      color: CommonColor.fontColor
    },
    daoshoujiaPriceText: {
      fontSize: 11,
      fontWeight: 'bold',
      color: CommonColor.fontColor
    },
    flatList: {
      width: '100%',
      height: '100%',
    },

    footerTxt: {
      width: '100%',
      fontSize: 14,
      color: '#999',
      marginVertical: 16,
      textAlign: 'center',
      textAlignVertical: 'center',
    },


    item: {
      width: (SCREEN_WIDTH - 10) / 2,
      backgroundColor: 'white',
      marginLeft: 3,
      marginBottom: 6,
      borderRadius: 3,
      overflow: 'hidden',
    },

    descText: {
      fontSize: 11,
      color: CommonColor.deepGrey,
      marginHorizontal: 10,
      marginVertical: 4,
  },

  priceBottomContainer: {
    flexDirection: 'row',
    marginHorizontal: 10,
    justifyContent: 'space-between'
  },

  defaultPriceText: {
    fontSize: 13,
    fontWeight: 'bold',
    color: CommonColor.fontColor
  },

  buyCountText: {
    fontSize: 11,
    color: CommonColor.normalGrey
  }
});
  