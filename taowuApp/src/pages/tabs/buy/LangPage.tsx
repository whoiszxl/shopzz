import { observer, useLocalStore } from 'mobx-react';
import React, { useEffect } from 'react'
import { Dimensions, Image, StyleSheet, Text, View } from 'react-native'
import BuyStore from './BuyStore';
import { CommonColor } from '../../../common/CommonColor';
import FlowList from '../../../components/flowlist/FlowList.js';
import ResizeImage from '../../../components/ResizeImage';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

export default observer(({type} : {type: string}) => {

    const store = useLocalStore(() => new BuyStore());

    useEffect(() => {
      store.requestChildCategoryList('2');
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
          <View style={styles.item}>
              <ResizeImage uri={item.defaultPic} />
              <Text numberOfLines={2} style={styles.descText}>{item.name}</Text>
              <View style={styles.priceBottomContainer}>
                <Text style={styles.defaultPriceText}>{'¥' + item.defaultPrice}</Text>
                <Text style={styles.buyCountText}>2.41万+人付款</Text>
              </View>

          </View>
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

        {/** 推荐分类栏目 */}
        <View style={styles.categoryContainer}>
          {store.childCategoryList.slice(0, 5).map((item : TwoLevelCategory) => (
            <View style={styles.categoryItemContainer} key={item.id}>
              <Image source={{uri: item.childList[0].icon}} style={styles.categoryImage} />
              <Text style={styles.categoryText}>{item.childList[0].name}</Text>
            </View>
          ))}
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
  