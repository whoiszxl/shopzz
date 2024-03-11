import React, { useEffect, useRef, useState } from "react";
import {
    View,
    Text,
    StyleSheet,
    Dimensions,
    Image,
    TouchableOpacity,
    ScrollView,
    TextInput,
    StatusBar,
} from 'react-native';
import { useLocalStore } from 'mobx-react';
import { observer } from 'mobx-react';
import { RouteProp, useRoute } from "@react-navigation/native";
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';
import { ImageSlider } from "../../components/slidePager";
import ProductDetailStore from "./ProductDetailStore";

import Ionicons from 'react-native-vector-icons/Ionicons';
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5';

import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import Octicons from 'react-native-vector-icons/Octicons';


import { CommonLogo } from "../../common/CommonLogo";
import { CommonColor } from "../../common/CommonColor";
import PriceShowBar from "../../components/PriceShowBar";
import BuyModal, { BuyModalRef } from "./components/BuyModal";




type RouteParams = {
    ProductDetail: {
        id: number;
    }
}

const { width: SCREEN_WIDTH } = Dimensions.get('window');


export default observer(() => {

    const store = useLocalStore(() => new ProductDetailStore());
    const navigation = useNavigation<StackNavigationProp<any>>();


    const { params } = useRoute<RouteProp<RouteParams, 'ProductDetail'>>();

    const [height, setHeight] = useState<number>(360);

    const modalRef = useRef<BuyModalRef>(null);


    useEffect(() => {
        store.requestProductDetail(params.id);
    }, []);

    useEffect(() => {
        if (!store.productDetail?.images) {
            return;
        }
        const firstImg = store.productDetail?.images[0];
        Image.getSize(firstImg.imgUrl, (width: number, height: number) => {
            const showHeight = SCREEN_WIDTH * height / width;
            setHeight(showHeight);
        })
    }, [store.productDetail?.images]);


    // 头部布局
    const renderTitle = () => {
        return (
            <View style={styles.titleLayout}>
                {/** 返回按钮 */}
                <TouchableOpacity style={styles.backButton} onPress={() => navigation.pop()}>
                    <Ionicons name={CommonLogo.Ionicons.arrow_back} size={22} color={CommonColor.fontColor}/>
                </TouchableOpacity>

                {/** 分享按钮 */}
                <FontAwesome name={'weixin'} size={18} color={CommonColor.wxColor}/>
                {/** 菜单按钮 */}
                <MaterialCommunityIcons 
                    style={{paddingLeft: 10, paddingRight: 5, color: CommonColor.fontColor}} 
                    name={'dots-horizontal'} size={20}/>
            </View>
        );
    }

    const renderProductImages = () => {


     
        return (
            <View style={{}} >
                
            </View>
        );
    }



    const renderProductTitle = () => {

        const { spuVO } = store.productDetail;

        const styles = StyleSheet.create({

            mainContainer: {
                marginTop: 5,
                marginHorizontal: 4,
                backgroundColor: 'white',
                paddingTop: 8
            },

            priceContainer: {
              flexDirection: 'row', // 设置为横向排列
              alignItems: 'center', // 垂直居中对齐
              justifyContent: 'space-between', // 两个元素之间平均分配空间
              paddingHorizontal: 10, // 水平方向的内边距
            },

            leftText: {
              fontSize: 20, // 文字大小
              fontWeight: 'bold', // 文字粗细
              color: CommonColor.fontColor, // 文字颜色
            },
            rightText: {
              fontSize: 10,
              color: CommonColor.normalGrey,
            },

            tipsText: {
                fontSize: 9,
                color: CommonColor.fontColor,
            },

            tipsContainer: {
                paddingHorizontal: 10,
                paddingTop: 4
            },

            titleContainer: {
                paddingHorizontal: 10,
                paddingTop: 9
            },
            titleText: {
                fontSize: 15,
                color: CommonColor.fontColor
            },

            subTitleText: {
                paddingTop: 5,
                fontSize: 10,
                paddingBottom: 10
            }
        });



        return (
            <View style={styles.mainContainer}>
                <View style={styles.priceContainer}>
                    <PriceShowBar priceSize={22} symbolSize={18} price={spuVO.defaultPrice + ''}/>
                    <Text style={styles.rightText}>6.3万+人付款</Text>
                </View>

                <View style={styles.tipsContainer}>
                <Text style={styles.tipsText}>{'淘物包邮价 | 100.90元/月起，去开通 >'}</Text>
                </View>

                <View style={styles.titleContainer}>
                    <Text numberOfLines={2} style={styles.titleText}>{spuVO.name}</Text>

                    <Text style={styles.subTitleText}>{'推荐热销榜第3名 >'}</Text>
                </View>
            </View>

        );
    }

    const renderServiceAndParameter = () => {

        const { spuVO } = store.productDetail;

        const styles = StyleSheet.create({

            mainContainer: {
                marginTop: 5,
                marginHorizontal: 4,
                backgroundColor: 'white',
            },

            priceContainer: {
              flexDirection: 'row', // 设置为横向排列
              alignItems: 'center', // 垂直居中对齐
              justifyContent: 'space-between', // 两个元素之间平均分配空间
              paddingHorizontal: 10, // 水平方向的内边距
              paddingVertical: 12
            },

             leftText: {
              fontSize: 9, // 文字大小
              color: CommonColor.normalGrey, // 文字颜色
              paddingRight: 5,
            },
            rightText: {
              fontSize: 10,
              color: CommonColor.fontColor,
              width: '90%'
            },

            tipsText: {
                fontSize: 11,
                color: CommonColor.fontColor,
            },

            tipsContainer: {
                paddingHorizontal: 10,
                paddingTop: 4
            },

            titleContainer: {
                paddingHorizontal: 10,
                paddingTop: 9
            },
            titleText: {
                fontSize: 15,
                color: CommonColor.fontColor
            },

            subTitleText: {
                paddingTop: 5,
                fontSize: 11,
                paddingBottom: 12
            },

            line: {
                marginHorizontal: 16,
                height: StyleSheet.hairlineWidth,
                backgroundColor: '#eee',
            },


            container: {
                flexDirection: 'row',
                justifyContent: 'space-between',
                alignItems: 'center',
              },
              textContainer: {
                flex: 1,
              },
              text: {
                fontSize: 10,
              },

              subText: {
                fontSize: 10,
                color: CommonColor.fontColor
              },
              iconContainer: {
                flexDirection: 'row',
                alignItems: 'center',
                marginRight: 40
              },
              icon: {
                width: 20,
                height: 20
              },
        });



        return (
            <View style={styles.mainContainer}>
                <View style={styles.priceContainer}>
                    <View style={{flexDirection: 'row'}}>
                        <Text style={styles.leftText}>服务</Text>
                        <Text numberOfLines={1} style={styles.rightText}>假一赔三  ·  七天无理由退货  ·  全场包邮  ·  退换货减免运费  ·  晚到必赔  ·  防伪包装</Text>
                    </View>
                    <Text style={styles.leftText}>{'>'}</Text>
                </View>

                <View style={styles.line} />

                <View style={styles.priceContainer}>
                    <View style={{flexDirection: 'row'}}>
                        <Text style={styles.leftText}>参数</Text>

                        <View style={styles.container}>
                            {/* 第一个组件 */}
                            <View style={styles.textContainer}>
                                <Text style={styles.text}>电池容量</Text>
                                <Text style={styles.subText}>3095</Text>
                            </View>

                            {/* 第二个组件 */}
                            <View style={styles.textContainer}>
                                <Text style={styles.text}>机身厚度</Text>
                                <Text style={styles.subText}>7.4mm</Text>
                            </View>

                            {/* 第三个组件 */}
                            <View style={styles.textContainer}>
                                <Text style={styles.text}>网络类型</Text>
                                <Text style={styles.subText}>5G</Text>
                            </View>

                            {/* 第四个组件 */}
                            <View style={styles.textContainer}>
                                <Text style={styles.text}>CPU型号</Text>
                                <Text style={styles.subText}>Apple A15</Text>
                            </View>
                            
                        </View>
                    </View>
                    <Text style={styles.leftText}>{'>'}</Text>
                </View>
            </View>
        );
    }


    const renderGift = () => {
        return (
            <View></View>
        );
    }

    const renderRecentBuy = () => {

        const styles = StyleSheet.create({
            container: {
              flex: 1,
              padding: 10,
              backgroundColor: 'white', // 背景颜色
              marginTop: 5,
              marginHorizontal: 4
            },
            firstRowContainer: {
              flexDirection: 'row',
              justifyContent: 'space-between',
              marginBottom: 10,
            },
            titleLeftText: {
              fontSize: 14,
              fontWeight: 'bold',
              color: CommonColor.fontColor
            },

            titleRightText: {
                fontSize: 10,
                color: CommonColor.deepGrey
            },
            secondRowContainer: {
              flexDirection: 'column',
            },
            listItem: {
              flexDirection: 'row',
              marginBottom: 5,
              alignItems: 'center',
              justifyContent: 'space-between'
            },
            imageContainer: {
              flexDirection: 'row',
              width: 20,
              height: 20,
              borderRadius: 25,
              marginRight: 4,
              justifyContent: 'center',
              alignItems: 'center',
              backgroundColor: '#ccc', // 圆角图片背景颜色
            },
            image: {
              width: 20,
              height: 20,
              resizeMode: 'contain',
            },
            textContainer: {
              flex: 1,
              justifyContent: 'space-around',
              flexDirection: 'row'
            },
            text: {
              fontSize: 10,
              color: CommonColor.fontColor
            },

            timeText: {
                fontSize: 9
            }
        });


        const ListItem = ({ imageUrl, username, productName, price, time }: 
            { imageUrl: string, username: string, productName: string, price: string, time: string }) => (
            <View style={styles.listItem}>
              <View style={{flexDirection: 'row',alignItems: 'center' }}>
                <View style={styles.imageContainer}>
                    <Image source={{ uri: imageUrl }} style={styles.image} />
                </View>
                <Text style={styles.text}>{username}</Text>

              </View>
              <Text style={styles.text}>{productName}</Text>
                <Text style={styles.text}>{price}</Text>
                <Text style={styles.timeText}>{time}</Text>
            </View>
        );

        const data = [
            { id: 1, imageUrl: 'http://qiniu.whoiszxl.com/category/bd06e284-9bf2-4af2-886a-fbd5061a7aff.jpg', username: '王*', productName: '官方标配 256GB', price: '2799', time: '7小时前' },
            { id: 2, imageUrl: 'http://qiniu.whoiszxl.com/category/ed8ac4a8-d26a-4e0b-b919-94d3cc0b1acc.jpg', username: '钟*', productName: '官方标配 256GB', price: '2799', time: '7小时前' },
            { id: 3, imageUrl: 'http://qiniu.whoiszxl.com/category/9150fbd1-dfe3-4496-87c5-a1fffef3ba32.jpg', username: 'ab*', productName: '官方标配 256GB', price: '2799', time: '7小时前' },
            { id: 4, imageUrl: 'http://qiniu.whoiszxl.com/category/ca63d27c-498c-4a83-9b70-107f19bfcdec.jpg', username: '淘*', productName: '官方标配 256GB', price: '2799', time: '7小时前' },
            // 可以添加更多数据...
        ];

        return (
            <View style={styles.container}>
            <View style={styles.firstRowContainer}>
              <Text style={styles.titleLeftText}>{'最近购买(4000+)'}</Text>
              <Text style={styles.titleRightText}>{'全部 >'}</Text>
            </View>
            <View style={styles.secondRowContainer}>
              {data.slice(0, 4).map(item => (
                <ListItem
                    key={item.id}
                    imageUrl={item.imageUrl}
                    price={item.price}
                    username={item.username}
                    productName={item.productName}
                    time={item.time}
                />
              ))}
            </View>
          </View>
        );
    }

    const renderComment = () => {
        const styles = StyleSheet.create({
            container: {
              flex: 1,
              padding: 10,
              backgroundColor: 'white', // 背景颜色
              marginTop: 5,
              marginHorizontal: 4
            },
            firstRowContainer: {
              flexDirection: 'row',
              justifyContent: 'space-between',
              marginBottom: 10,
            },
            titleLeftText: {
              fontSize: 14,
              fontWeight: 'bold',
              color: CommonColor.fontColor
            },

            titleRightText: {
                fontSize: 10,
                color: CommonColor.deepGrey
            },
            secondRowContainer: {
              flexDirection: 'column',
            },
            listItem: {
              flexDirection: 'row',
              marginBottom: 5,
              alignItems: 'center',
              justifyContent: 'space-between'
            },
            imageContainer: {
              flexDirection: 'row',
              width: 20,
              height: 20,
              borderRadius: 25,
              marginRight: 4,
              justifyContent: 'center',
              alignItems: 'center',
              backgroundColor: '#ccc', // 圆角图片背景颜色
            },
            image: {
              width: 20,
              height: 20,
              resizeMode: 'contain',
            },
            textContainer: {
              flex: 1,
              justifyContent: 'space-around',
              flexDirection: 'row'
            },
            text: {
              fontSize: 10,
              color: CommonColor.fontColor
            },

            timeText: {
                fontSize: 9
            },

            scoreText: {
                fontSize: 24,
                fontWeight: 'bold',
                color: CommonColor.fontColor
            },

            starIcon: {
                paddingLeft: 2,
                color: CommonColor.deepGrey,
                fontSize: 8
            },

            commentTitleText: {
                fontSize: 10,
                color: CommonColor.fontColor
            },

            bottomScoreText: {
                fontSize: 12,
                fontWeight: 'bold',
                color: CommonColor.fontColor
            },

            commentTagContainer: {
                flexDirection: 'row',
                paddingTop: 5
            },

            commentListContainer: {
                paddingTop: 8
            },

            commentTagText: {
                fontSize: 10,
                color: CommonColor.fontColor,
                backgroundColor: CommonColor.greyButtonBg,
                paddingVertical: 3,
                paddingHorizontal: 5,
                marginLeft: 4,
            },

            commentLayout: {
                flexDirection: 'row',
                alignItems: 'center',
                paddingTop: 7
            },

            commentUsername: {
                color: CommonColor.deepGrey,
                fontWeight: 'bold',
                fontSize: 11,
                paddingLeft: 5
            },

            buyTag: {
                color: CommonColor.deepGrey,
                fontSize: 9,
                borderWidth: 0.6,
                borderColor: CommonColor.deepGrey,
                paddingHorizontal: 2,
                marginLeft: 5,
                borderRadius: 2
            },

            commentText: {
                paddingTop: 5,
                fontSize: 10,
                color: CommonColor.fontColor,
                paddingLeft: 2

            }
        });

        return (
            <View style={styles.container}>
            <View style={styles.firstRowContainer}>
              <Text style={styles.titleLeftText}>{'好物评价(1984)'}</Text>
              <Text style={styles.titleRightText}>{'评价 +'}</Text>
            </View>


            <View style={styles.secondRowContainer}>
                <View style={styles.listItem}>
                    <View style={{flexDirection: 'column', alignItems: 'center'}}>
                        <Text style={styles.scoreText}>9.3</Text>
                        <View style={{flexDirection: 'row'}}>
                            <FontAwesome style={styles.starIcon} name="star"/>
                            <FontAwesome style={styles.starIcon} name="star"/>
                            <FontAwesome style={styles.starIcon} name="star"/>
                            <FontAwesome style={styles.starIcon} name="star"/>
                            <FontAwesome style={styles.starIcon} name="star-half"/>
                        </View>
                    </View>

                    <View style={{flexDirection: 'column', alignItems: 'center'}}>
                        <Text style={styles.commentTitleText}>流畅性</Text>
                        <Text style={styles.bottomScoreText}>9.5</Text>
                    </View>


                    <View style={{flexDirection: 'column', alignItems: 'center'}}>
                        <Text style={styles.commentTitleText}>电池续航</Text>
                        <Text style={styles.bottomScoreText}>9.9</Text>
                    </View>

                    <View style={{flexDirection: 'column', alignItems: 'center'}}>
                        <Text style={styles.commentTitleText}>拍摄性能</Text>
                        <Text style={styles.bottomScoreText}>9.4</Text>
                    </View>
                    
                </View>
            </View>

            <View style={styles.commentTagContainer}>
                <Text style={styles.commentTagText}>续航能力好 168</Text>
                <Text style={styles.commentTagText}>拍照效果好 222</Text>
                <Text style={styles.commentTagText}>充电速度快 135</Text>
                <Text style={styles.commentTagText}>性价比高 666</Text>
            </View>

            <View style={styles.commentListContainer}>
                <View>
                    <View style={styles.commentLayout}>
                        <Image source={{ uri: 'http://qiniu.whoiszxl.com/category/bd06e284-9bf2-4af2-886a-fbd5061a7aff.jpg' }} style={styles.image} />
                        <Text style={styles.commentUsername}>热心市民</Text>
                        <Text style={styles.buyTag}>已购</Text>
                    </View>
                    <Text numberOfLines={2} style={styles.commentText}>商品收到了，孩子很喜欢，敏感肌也能用，还会回购。</Text>
                </View>

                <View>
                    <View style={styles.commentLayout}>
                        <Image source={{ uri: 'http://qiniu.whoiszxl.com/category/bd06e284-9bf2-4af2-886a-fbd5061a7aff.jpg' }} style={styles.image} />
                        <Text style={styles.commentUsername}>天选打工人</Text>
                        <Text style={styles.buyTag}>已购</Text>
                    </View>
                    <Text numberOfLines={2} style={styles.commentText}>
                        我最近购买了一部新款手机，真的非常满意！首先，它的外观设计非常时尚，简洁而又不失大气，手感也十分舒适。打开手机的那一刻，我就被它的高清屏幕所吸引，色彩鲜艳且清晰度高，让我在使用时感到非常愉悦。

                        此外，这部手机的性能也非常出色。无论是运行大型应用还是玩游戏，都能轻松应对，流畅度极高，从未出现卡顿现象。同时，它的电池续航也非常给力，即使长时间使用，也不必频繁充电，非常方便。

                        除了性能和外观，这部手机的拍照功能也让我印象深刻。无论是风景还是人像，都能轻松拍出高质量的照片，色彩还原度极高，让我对手机的拍照功能赞不绝口。

                        总的来说，这部手机无论是从外观、性能还是拍照功能方面都表现出色，让我非常满意。它的出现不仅提升了我的使用体验，还让我感受到了科技的魅力。我强烈推荐大家购买这款手机，相信你们一定会和我一样，爱上它的！
                    </Text>
                </View>

            </View>

            <View style={{alignItems: 'center', paddingTop: 15, paddingBottom: 2}}>
                <Text style={{fontSize: 10, color: CommonColor.normalGrey}}>{'查看全部  >'}</Text>
            </View>
            
          </View>
        );
    }

    const renderAsk = () => {
        const styles = StyleSheet.create({
            container: {
              flex: 1,
              padding: 10,
              backgroundColor: 'white', // 背景颜色
              marginTop: 5,
              marginHorizontal: 4
            },
            firstRowContainer: {
              flexDirection: 'row',
              justifyContent: 'space-between',
              marginBottom: 10,
            },
            titleLeftText: {
              fontSize: 14,
              fontWeight: 'bold',
              color: CommonColor.fontColor
            },

            titleRightText: {
                fontSize: 10,
                color: CommonColor.deepGrey
            },
            secondRowContainer: {
              flexDirection: 'column',
            },
            listItem: {
              flexDirection: 'row',
              marginBottom: 5,
              alignItems: 'center',
              justifyContent: 'space-between'
            },
            imageContainer: {
              flexDirection: 'row',
              width: 20,
              height: 20,
              borderRadius: 25,
              marginRight: 4,
              justifyContent: 'center',
              alignItems: 'center',
              backgroundColor: '#ccc', // 圆角图片背景颜色
            },
            image: {
              width: 20,
              height: 20,
              resizeMode: 'contain',
            },
            textContainer: {
              flex: 1,
              justifyContent: 'space-around',
              flexDirection: 'row'
            },
            text: {
              fontSize: 10,
              color: CommonColor.fontColor
            },

            timeText: {
                fontSize: 9
            }
        });


        const ListItem = ({ question, answerCount }: 
            { question: string, answerCount: string }) => (
            <View style={styles.listItem}>
                <Text style={styles.text}>{'问：' + question}</Text>
                <Text style={styles.timeText}>{answerCount + '个回答'}</Text>
            </View>
        );

        const data = [
            { id: 1, question: '续航怎么样？', answerCount: '4' },
            { id: 2, question: '是正品吗？有发票吗？', answerCount: '7' },
            // 可以添加更多数据...
        ];

        return (
            <View style={styles.container}>
            <View style={styles.firstRowContainer}>
              <Text style={styles.titleLeftText}>{'问问大家(66+)'}</Text>
              <Text style={styles.titleRightText}>{'全部 >'}</Text>
            </View>
            <View style={styles.secondRowContainer}>
              {data.slice(0, 4).map(item => (
                <ListItem
                    key={item.id}
                    question={item.question}
                    answerCount={item.answerCount}
                />
              ))}
            </View>
          </View>
        );
    }

    const renderButtomButtonTab = () => {

        const styles = StyleSheet.create({
            buttonContainer: {
                flexDirection: 'row',
                position: 'absolute',
                paddingHorizontal: 10,
                bottom: 0,
                left: 0,
                right: 0,
                alignItems: 'center',
                paddingBottom: 20,
                paddingTop: 5,
                borderTopColor: CommonColor.tagBg,
                borderTopWidth: 0.5,
                backgroundColor: 'white', // 背景色可以根据需要调整
              },

              reducePriceButton: {
                backgroundColor: 'white', // 按钮颜色
                paddingHorizontal: 15,
                paddingVertical: 8,
                borderRadius: 2,
                borderWidth: 0.6,
                borderColor: CommonColor.normalGrey,
                alignContent: 'center',
                justifyContent: 'center',
                alignItems: 'center'
              },

              buyButton: {
                backgroundColor: CommonColor.mainColorTwo, // 按钮颜色
                paddingHorizontal: 15,
                paddingVertical: 8,
                borderRadius: 2,
                alignContent: 'center',
                justifyContent: 'center',
                alignItems: 'center',
                marginLeft: 5,
                borderWidth: 0.6,
                borderColor: CommonColor.mainColorTwo,
            
              },
              buttonText: {
                color: 'white',
                fontSize: 14,
              },

              reducePriceButtonText: {
                color: CommonColor.deepGrey,
                fontSize: 14,
              },

              buttonIconLayout: {
                flexDirection: 'column',
                alignItems: 'center',
                flex: 1
              },

              buttonIcon: {
                fontSize: 16,
                fontWeight: 'bold',
                color: CommonColor.fontColor
              },

              buttonIconText: {
                fontWeight: 'bold',
                fontSize: 9,
                color: CommonColor.fontColor
              }
            
        });

        return (
            <View style={styles.buttonContainer}>

                <View style={styles.buttonIconLayout}>
                    <Octicons name="heart" style={styles.buttonIcon}/> 
                    <Text style={styles.buttonIconText}>想要</Text>
                </View>

                <View style={styles.buttonIconLayout}>
                    <Octicons name="check-circle" style={styles.buttonIcon}/>
                    <Text style={styles.buttonIconText}>我有</Text>
                </View>

                <View style={[styles.buttonIconLayout, {paddingRight: 10}]}>
                    <Octicons name="comment-discussion" style={styles.buttonIcon}/>
                    <Text style={styles.buttonIconText}>客服</Text>
                </View>

                <TouchableOpacity activeOpacity={1} style={[styles.reducePriceButton, {flex: 1}]} onPress={() => {}}>
                    <Text style={styles.reducePriceButtonText}>还价</Text>
                </TouchableOpacity>

                <TouchableOpacity activeOpacity={1} style={[styles.buyButton, {flex: 4}]} onPress={() => {
                    console.log("点击了");
                    modalRef.current?.show();
                }}>
                <Text style={styles.buttonText}>立即购买</Text>
                </TouchableOpacity>
          </View>
        );
    }
    

    const images = store.productDetail.images;
    if (!images?.length) {
        return null;
    }

    const imgData: any = images.map(item => ({ img: item.imgUrl }));


    return store.productDetail ? (
        <View style={styles.container}>
            <View style={{height: (StatusBar.currentHeight || 0), width: '100%', backgroundColor: 'white'}}></View>

            {renderTitle()}

            <ScrollView style={{flex: 1}} showsVerticalScrollIndicator={false}>

                <ImageSlider
                    data={imgData}
                    autoPlay={false}
                    closeIconColor={CommonColor.mainColor}
                    caroselImageStyle={{ height }}
                    indicatorContainerStyle={{}}
                    activeIndicatorStyle={styles.activeDot}
                    inActiveIndicatorStyle={styles.inActiveDot}
                />

                {renderProductTitle()}
                

                {renderServiceAndParameter()}

                {renderRecentBuy()}


                {renderComment()}

                {renderAsk()}



            </ScrollView>

            {renderButtomButtonTab()}

            <BuyModal ref={modalRef} spu={store.productDetail}/>

        </View>
    ) : null;


})


const styles = StyleSheet.create({
    container: {
        width: '100%',
        height: '100%',
        backgroundColor: CommonColor.normalBg,
    },

    titleLayout: {
        width: '100%',
        height: 40,
        flexDirection: 'row',
        alignItems: 'center',
        backgroundColor: 'white'
    },
    backButton: {
        paddingLeft: 5,
        height: '100%',
        flex: 1,
        justifyContent: 'center',
    },

    activeDot: {
        width: 6,
        height: 6,
        backgroundColor: CommonColor.mainColorTwo,
        borderRadius: 3,
    },
    inActiveDot: {
        width: 6,
        height: 6,
        backgroundColor: CommonColor.deepGrey,
        borderRadius: 3,
    },

    articleTitleTxt: {
        fontSize: 18,
        color: '#333',
        fontWeight: 'bold',
        paddingHorizontal: 16,
    },
    descTxt: {
        fontSize: 15,
        color: '#333',
        marginTop: 6,
        paddingHorizontal: 16,
    },
});