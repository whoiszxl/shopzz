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
import OrderConfirmStore from "./OrderConfirmStore";

import Ionicons from 'react-native-vector-icons/Ionicons';
import FontAwesome5 from 'react-native-vector-icons/FontAwesome5';

import MaterialCommunityIcons from 'react-native-vector-icons/MaterialCommunityIcons';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import Octicons from 'react-native-vector-icons/Octicons';


import { CommonLogo } from "../../common/CommonLogo";
import { CommonColor } from "../../common/CommonColor";
import PriceShowBar from "../../components/PriceShowBar";
import BouncyCheckbox from "react-native-bouncy-checkbox";
import { RadioButton } from 'react-native-paper';




type RouteParams = {
    OrderConfirm: {
        checkedSku: SKU;
        spuName: string;
    }
}

const { width: SCREEN_WIDTH } = Dimensions.get('window');


export default observer(() => {

    let wxBouncyCheckboxRef: BouncyCheckbox | null = null;
    let zfbBouncyCheckboxRef: BouncyCheckbox | null = null;
    let dcBouncyCheckboxRef: BouncyCheckbox | null = null;


    const store = useLocalStore(() => new OrderConfirmStore());
    const navigation = useNavigation<StackNavigationProp<any>>();


    const { params } = useRoute<RouteProp<RouteParams, 'OrderConfirm'>>();

    const [height, setHeight] = useState<number>(360);

    const [checkboxState, setCheckboxState] = React.useState(1);


    useEffect(() => {
        console.log(params.checkedSku);
        store.requestProductDetail();
    }, []);




    // 头部布局
    const renderTitle = () => {
        return (
            <View style={styles.titleLayout}>
                {/** 返回按钮 */}
                <TouchableOpacity style={styles.backButton} onPress={() => navigation.pop()}>
                    <Ionicons name={CommonLogo.Ionicons.arrow_back} size={22} color={CommonColor.fontColor}/>
                </TouchableOpacity>

                {/** 返回按钮 */}
                <Text style={styles.titleText}>确认订单</Text>

                {/** 返回按钮 */}
                <View/>
                
            </View>
        );
    }

    const renderProductImages = () => {


     
        return (
            <View style={{}} >
                
            </View>
        );
    }




    const renderGift = () => {
        return (
            <View></View>
        );
    }

    const renderRecentBuy = () => {
        const { addressList } = store;
        const defaultAddressInfo = addressList.length > 0 ? addressList[0] : null;

        const styles = StyleSheet.create({
            container: {
              flex: 1,
              padding: 10,
              backgroundColor: 'white',
              marginTop: 5,
              marginHorizontal: 4
            },

            noAddressLayout: {
                flexDirection: 'row',
                justifyContent: 'space-between',
                alignItems: 'center'
            },

            noAddressLeftText: {
                color: CommonColor.fontColor,
                fontSize: 14,
                fontWeight: 'bold'
            },

            noAddressRightText: {
                color: CommonColor.deepGrey,
                fontSize: 14,
                fontWeight: 'bold'
            },

            haveAddressLayout: {
                flexDirection: 'column',
            },

            haveAddressOneLine: {
                flexDirection: 'row',
                justifyContent: 'space-between',
                alignItems: 'center'
            },

            haveAddressTwoLine: {
                flexDirection: 'row',
                paddingTop: 6
            },

            haveAddressTextLayout: {
                flexDirection: 'row',
                justifyContent: 'center',
                alignItems: 'center',
            },

            defaultButton: {
                backgroundColor: CommonColor.normalGrey,
                borderRadius: 3,
                justifyContent: 'center',
                alignItems: 'center',
                height: 15
            },

            defaultButtonText: {
                fontSize: 9,
                fontWeight: 'bold',
                color: 'white',
                marginHorizontal: 3
            },
            defaultAddressText: {
                fontSize: 13,
                fontWeight: 'bold',
                color: CommonColor.fontColor,
                paddingLeft: 6
            },
            
            receiverNameText: {
                fontSize: 11,
                color: CommonColor.fontColor,
            },

            receiverPhoneText: {
                fontSize: 11,
                color: CommonColor.fontColor,
                paddingLeft: 7
            }
        });



        return (
            <View style={styles.container}>
                {
                    defaultAddressInfo ? 
                    
                    (
                        <TouchableOpacity activeOpacity={1} onPress={() => {}} style={styles.haveAddressLayout}>
                            
                            <View style={styles.haveAddressOneLine}>
                                <View style={styles.haveAddressTextLayout}>
                                    <View style={styles.defaultButton}>
                                        <Text style={styles.defaultButtonText}>默认</Text>
                                    </View>
                                    <Text style={styles.defaultAddressText}>{defaultAddressInfo.province + ' ' + defaultAddressInfo.city + ' ' + defaultAddressInfo.district + ' ' + defaultAddressInfo.detailAddress }</Text>
                                </View>

                                <Ionicons name={CommonLogo.Ionicons.chevron_forward_outline} size={16}/>
                            </View>

                            <View style={styles.haveAddressTwoLine}>
                                <Text style={styles.receiverNameText}>{addressList[0].receiverName}</Text>
                                <Text style={styles.receiverPhoneText}>{addressList[0].receiverPhone}</Text>
                            </View>
                        </TouchableOpacity>
                    ) 
                    
                    
                    : 
                    
                    
                    (
                        <TouchableOpacity activeOpacity={1} onPress={() => {}} style={styles.noAddressLayout}>
                            <Text style={styles.noAddressLeftText}>下单前需要填写收货地址</Text>
                            <Text style={styles.noAddressRightText}>{'>'}</Text>
                        </TouchableOpacity>
                    )
                }
            </View>
        );
    }

    const renderSkuInfo = () => {
        const { checkedSku } = params;
        const { spuName } = params;
        const styles = StyleSheet.create({
            container: {
                backgroundColor: CommonColor.whiteBg,
                borderRadius: 3,
                bottom: 1,
                padding: 16,
                marginTop: 5,
                marginHorizontal: 4
            },
            row: {  
                flexDirection: 'row',
                alignItems: 'center',
            },  
            image: {  
                width: 80,
                height: 80,
                marginRight: 16,
                borderWidth: 1,
                borderColor: CommonColor.greyButtonBg,
                borderRadius: 3
            },
            taowuIconImage: {  
                width: 16,
                height: 16,
                marginRight: 5,
                borderRadius: 2
            },
            textContainer: {  
                flex: 1,
                justifyContent: 'space-between',
                paddingVertical: 8,
            },
            priceLayout: {
                flexDirection: 'row',
                alignItems: 'center'
            },

            tag: {
                borderColor: CommonColor.transparentGreyBg,
                borderWidth: 1,
                marginRight: 4
            },
            
            tagText: {
                fontSize: 9,
                color: CommonColor.fontColor,
                paddingVertical: 0,
                paddingHorizontal: 3
            },

            text: {  
                fontSize: 11,
                color: CommonColor.fontColor,
                paddingTop: 1
            },
             
        });
        return (
            <View style={styles.container}>  
                <View style={styles.row}>  
                    <Image  
                    source={{uri: params.checkedSku?.skuImg}}
                    style={styles.image}  
                    />  
                    <View style={styles.textContainer}>  
                        
                        <Text style={styles.text} numberOfLines={2}>{spuName}</Text>

                        <Text style={[styles.text, {paddingTop: 4}]} numberOfLines={2}>{checkedSku.skuName + '   数量 x1'}</Text>

                        
                        <View style={[styles.priceLayout, {paddingTop: 4}]}>
                            <PriceShowBar symbolSize={13} priceSize={15} price={checkedSku?.salePrice + ""}/>
                        </View>

                    </View>  
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


    const [selected, setSelected] = useState('wx');

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
              fontSize: 11,
              color: CommonColor.fontColor
            },

            timeText: {
                fontSize: 9
            }
        });




        return (
            <View style={styles.container}>
            <View style={styles.firstRowContainer}>
              <Text style={styles.titleLeftText}>{'支付方式'}</Text>
            </View>
            <View style={styles.secondRowContainer}>

            <View style={styles.listItem}>
                <Text style={styles.text}>{'微信支付'}</Text>

                <RadioButton.Android
                    value="wx"
                    color={CommonColor.mainColorTwo}
                    status={selected === 'wx' ? 'checked' : 'unchecked'}
                    onPress={() => setSelected('wx')}
                />
            </View>

            <View style={styles.listItem}>
                <Text style={styles.text}>{'支付宝'}</Text>
                <RadioButton.Android
                    value="zfb"
                    color={CommonColor.mainColorTwo}
                    status={selected === 'zfb' ? 'checked' : 'unchecked'}
                    onPress={() => setSelected('zfb')}
                />
            </View>

            <View style={styles.listItem}>
                <Text style={styles.text}>{'数字货币'}</Text>
                <RadioButton.Android
                    value="dc"
                    color={CommonColor.mainColorTwo}
                    status={selected === 'dc' ? 'checked' : 'unchecked'}
                    onPress={() => setSelected('dc')}
                />
            </View>
            </View>
          </View>
        );
    }

    const renderAsk2 = () => {
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


        return (
            <View style={styles.container}>
            <View style={styles.firstRowContainer}>
              <Text style={styles.titleLeftText}>{'买家须知'}</Text>
            </View>
            <View style={styles.secondRowContainer}>
                <View style={styles.listItem}>
                    <Text style={styles.text}>{'查看商家资质'}</Text>
                    <Ionicons name={CommonLogo.Ionicons.chevron_forward_outline} size={12}/>
                </View>

                <View style={[styles.listItem, {marginTop: 6}]}>
                    <Text style={styles.text}>{'提交订单即表示同意  买家须知'}</Text>
                    <Ionicons name={CommonLogo.Ionicons.chevron_forward_outline} size={12}/>
                </View>

            </View>
            
          </View>
        );
    }

    const renderButtomButtonTab = () => {

        const { checkedSku } = params;

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
                flexDirection: 'row',
                alignItems: 'center',
                alignContent: 'center',
                paddingLeft: 10,
                flex: 6
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
                    <Text style={styles.buttonIconText}>合计：</Text>
                    <PriceShowBar titleLayoutStyle={{color: 'red'}} symbolSize={17} priceSize={18} price={checkedSku.salePrice + ''}/>
                </View>

                

                <TouchableOpacity activeOpacity={1} style={[styles.buyButton, {flex: 2}]} onPress={() => {
                    console.log("点击了");
                }}>
                <Text style={styles.buttonText}>立即支付</Text>
                </TouchableOpacity>
          </View>
        );
    }
    





    return (
        <View style={styles.container}>
            <View style={{height: (StatusBar.currentHeight || 0), width: '100%', backgroundColor: 'white'}}></View>

            {renderTitle()}

            <ScrollView style={{flex: 1}} showsVerticalScrollIndicator={false}>

                {renderRecentBuy()}

                {renderSkuInfo()}



                {renderAsk()}

                {renderAsk2()}



            </ScrollView>

            {renderButtomButtonTab()}

        </View>
    );


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
        justifyContent: 'space-between',
        backgroundColor: 'white'
    },
    backButton: {
        paddingLeft: 5,
        height: '100%',
        justifyContent: 'center',
    },

    titleText: {
        fontSize:16,
        fontWeight: 'bold',
        color: CommonColor.fontColor,
        paddingRight: 20
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