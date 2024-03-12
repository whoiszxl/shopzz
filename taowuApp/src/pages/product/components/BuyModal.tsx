import React, { useEffect, useState, forwardRef, useImperativeHandle, useCallback } from 'react';
import {
    View,
    Text,
    StyleSheet,
    Image,
    LayoutAnimation,
    TouchableOpacity,
    StatusBar,
    Modal,
    Dimensions,
    ScrollView,
    FlatList,
} from 'react-native';
import Entypo from 'react-native-vector-icons/Entypo';
import { CommonColor } from '../../../common/CommonColor';
import PriceShowBar from '../../../components/PriceShowBar';


const { width: SCREEN_WIDTH } = Dimensions.get('window');

type Props = {
    spu: SPUVO;
};

export interface BuyModalRef {
    show: () => void;
    hide: () => void;
}

export default forwardRef((props: Props, ref) => {

    const { spu } = props;

    const [visible, setVisible] = useState<boolean>(false);

    const [edit, setEdit] = useState<boolean>(false);

    const [checkedItems, setCheckedItems] = useState(new Map());  

    const [currentSku, setCurrentSku] = useState<SKU>();

  
    // 替换Map中指定键的元素  
    const replaceItemAtKey = (key: string, newItem: string) => {  
        // 创建一个新的Map对象，并设置新的值  
        const newItems = new Map(checkedItems);  
        newItems.set(key, newItem);  
        setCheckedItems(newItems);
        return newItems;
    }; 

    const addOrUpdateItem = (key: string, item: string) => {  
        setCheckedItems(prevItems => {  
            const newItems = new Map(prevItems);  
            newItems.set(key, item);  
            return newItems;  
        });  
    }; 

    function convertMapToSkuCode(spuId:string, map:Map<string, string>) {  
        // 使用Map.prototype.entries()将Map转换为键值对迭代器  
        const iterator = map.entries();  
          
        // 将迭代器转换为数组，然后处理数组，方式与上面相同  
        const entries = Array.from(iterator);  
        const stringifiedEntries = entries.map(([key, value]) => `${key}-${value}`).join('&');  
        const skuCode = spuId + "#" + stringifiedEntries;
        return skuCode;  
    } 

    useEffect(() => {
        if (!spu) {
            return;
        }
        const tempMap = new Map<string, string>();
        spu.spuAttributeGroupVOList.map((e) => {
            console.log(e.keyId);
            addOrUpdateItem(e.keyId, e.spuAttrList[e.selected].valueId);
            tempMap.set(e.keyId, e.spuAttrList[e.selected].valueId);
        });

        console.log("初始化SKU选择信息", tempMap);
        
        // 获取默认选择的 skuCode
        const skuCode = convertMapToSkuCode(spu.spuVO.id, tempMap);
        console.log("SkuCode", skuCode);

        // 通过 skuCode 获取到对应的 sku 信息
        const sku = spu.skus.find(obj => obj.skuCode === skuCode);
        setCurrentSku(sku);
        console.log("选中的sku信息", sku);
        
    }, [spu]);

    const show = () => {
        setVisible(true);
    }

    const hide = () => {
        setVisible(false);
    }

    useImperativeHandle(ref, () => {
        return {
            show, hide,
        }
    });

    const renderHeader = () => {
        const styles = StyleSheet.create({
            container: {
                backgroundColor: CommonColor.whiteBg,
                borderRadius: 10,
                bottom: 1,
                padding: 16,
            },
            row: {  
                flexDirection: 'row',
                alignItems: 'center',
            },  
            image: {  
                width: 80,
                height: 80,
                marginRight: 16,
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
                color: CommonColor.deepGrey,
                paddingTop: 5
            },
             
        });
        return (
            <View style={styles.container}>  
                <View style={styles.row}>  
                    <Image  
                    source={{uri: currentSku?.skuImg}}
                    style={styles.image}  
                    />  
                    <View style={styles.textContainer}>  
                        <View style={styles.priceLayout}>
                            <Image  
                                source={require('../../../assets/images/ic_launcher_small.png')}
                                style={styles.taowuIconImage}  
                            />
                            <PriceShowBar symbolSize={22} priceSize={24} price={currentSku?.salePrice + ""}/>
                        </View>
                        
                        
                        <View style={{flexDirection: 'row'}}>
                            <View style={styles.tag}>
                                <Text style={styles.tagText}>包邮</Text>
                            </View>

                            <View style={styles.tag}>
                                <Text style={styles.tagText}>{Math.trunc((currentSku?.salePrice || 0) / 24) + '元/月起 >'}</Text>
                            </View>

                            <View style={styles.tag}>
                                <Text style={styles.tagText}>{'以旧换新 最高抵¥800'}</Text>
                            </View>
                        </View>


                        <Text style={styles.text}>{'已选 ' + currentSku?.skuName}</Text>  
                    </View>  
                </View>  
            </View>  
        );
    }


    const renderMyList = () => {
        return (
            <>
            <View style={styles.row}>
                <Text style={styles.titleText}>我的频道</Text>
                <Text style={styles.subTitleText}>点击进入频道</Text>
                <TouchableOpacity style={styles.closeButton} onPress={hide}>
                    <Entypo name='chevron-up' size={20}/>
                </TouchableOpacity>
            </View>
            <View style={styles.listContent}>
            <TouchableOpacity
                        
                            style={styles.itemLayoutDefault}
                            onPress={() => {
                                console.log("点击了");
                            }}
                        >
                            <Text style={styles.itemText}>{'aaaa'}</Text>
                            
                        </TouchableOpacity>

                        <TouchableOpacity
                        
                            style={styles.itemLayoutDefault}
                            onPress={() => {
                                console.log("点击了");
                            }}
                        >
                            <Text style={styles.itemText}>{'bbbb'}</Text>
                            
                        </TouchableOpacity>

                        
                        
            </View>
            </>
        );
    }


    const AttributeList = ({ title, items }: { title: any, items: SpuAttr[] }) => {
        const styles = StyleSheet.create({
            attrLabel: {
                backgroundColor: 'white',
                paddingHorizontal: 10,
                paddingVertical: 8,
                marginRight: 5,
                borderWidth: 2,
                borderColor: CommonColor.whiteBg,
                marginBottom: 3
            },

            attrLabelActive: {
                backgroundColor: 'white',
                paddingHorizontal: 10,
                paddingVertical: 8,
                marginRight: 5,
                borderWidth: 2,
                borderColor: CommonColor.dark,
                marginBottom: 3
            },
            
            attrLabelText: {
                fontSize: 10,
                fontWeight: 'bold',
                color: CommonColor.fontColor
            }
        });
        return (  
          <View style={{marginHorizontal: 7}}>  
            <Text style={{ fontWeight: 'bold', paddingTop: 10, fontSize: 11, paddingBottom: 7 }}>{title}</Text>  

            <View style={{flexDirection: 'row', flexWrap: 'wrap'}}>
            {
                items.map((spuAttr) => (
                    <TouchableOpacity activeOpacity={1} key={spuAttr.value} style={checkedItems.get(spuAttr.keyId) === spuAttr.valueId ? styles.attrLabelActive : styles.attrLabel} onPress={() => {
                        const newItems = replaceItemAtKey(spuAttr.keyId, spuAttr.valueId)
                        console.log(newItems);

                        const skuCode = convertMapToSkuCode(spu.spuVO.id, newItems);
                        console.log("SkuCode", skuCode);

                        const sku = spu.skus.find(obj => obj.skuCode === skuCode);
                        setCurrentSku(sku);
                        console.log("点击后选中的sku信息", sku);
                    }}>
                        <Text style={styles.attrLabelText} key={spuAttr.value}>{spuAttr.value}</Text>
                    </TouchableOpacity>
                ))
            } 
            </View>
          </View>  
        );  
    };  
    
    const renderAttributeGroup = () => {
        const styles = StyleSheet.create({
            container: {
                backgroundColor: '#FAF9FE',
                paddingBottom: 20
            }
        });
        return (
            <View style={styles.container}>
                
                <ScrollView>  
                    {spu.spuAttributeGroupVOList.map((attribute) => ( 
                        <View key={attribute.keyId}>
                            <AttributeList  
                                key={attribute.keyId}  
                                title={attribute.keyName}  
                                items={attribute.spuAttrList}  
                            />
                        </View>
                    ))}  
                </ScrollView>  

            </View>
        );
    }

    const renderBottomButtonList = () => {
        const styles = StyleSheet.create({
            container: {
                paddingTop: 10,
                backgroundColor: CommonColor.whiteBg,
                paddingHorizontal: 5,
                paddingBottom: 3
            },

            swipeItem: {  
                height: 56,
                backgroundColor: '#2A2A34',
                marginRight: 5,
                alignItems: 'center',
                justifyContent: 'space-between',
                flexDirection: 'row',
                paddingHorizontal: 10,
                borderRadius: 2 
            },

            firstSwipeItem: {
                backgroundColor: CommonColor.mainColorTwo
            },

            priceTextStyle: {
                color: 'white',
                fontSize: 12
            }
        });

        return (
            <View style={styles.container}>
                <ScrollView horizontal showsHorizontalScrollIndicator={false}>
                    <View style={[styles.swipeItem, styles.firstSwipeItem]}>  
                        <PriceShowBar titleLayoutStyle={styles.priceTextStyle} price={currentSku?.salePrice + ""}/>
                        <Text style={styles.priceTextStyle}>|</Text>
                        <Text style={styles.priceTextStyle}>约2-3天到</Text>
                    </View>

                    <View style={[styles.swipeItem]}>  
                        <PriceShowBar titleLayoutStyle={styles.priceTextStyle} price={currentSku?.salePrice + ""}/>
                        <Text style={styles.priceTextStyle}>|</Text>
                        <Text style={styles.priceTextStyle}>约2天到</Text>
                    </View>

                    <View style={[styles.swipeItem]}>  
                        <PriceShowBar titleLayoutStyle={styles.priceTextStyle} price={currentSku?.salePrice + ""}/>
                        <Text style={styles.priceTextStyle}>|</Text>
                        <Text style={styles.priceTextStyle}>约7-8天到</Text>
                    </View>

                    <View style={[styles.swipeItem]}>  
                        <PriceShowBar titleLayoutStyle={styles.priceTextStyle} price={currentSku?.salePrice + ""}/>
                        <Text style={styles.priceTextStyle}>|</Text>
                        <Text style={styles.priceTextStyle}>约2-3天到</Text>
                    </View>
                </ScrollView>
            </View>
        );

    }


    return (
        <Modal
            transparent={true}
            visible={visible}
            statusBarTranslucent={true}
            animationType='slide'
            onRequestClose={hide}
        >
            <View style={styles.root}>
                <View style={styles.content}>

                    {renderHeader()}

                    {renderAttributeGroup()}

                    {renderBottomButtonList()}

                </View>
                <TouchableOpacity style={styles.mask} onPress={hide}>
                    <View style={styles.mask}/>
                </TouchableOpacity>
            </View>
        </Modal>
    );
})

const styles = StyleSheet.create({
    root: {
        width: '100%',
        height: '100%',
        backgroundColor: 'transparent',
    },
    content: {
        width: '100%',
        backgroundColor: 'white',
        paddingBottom: 15,
        position: 'absolute',
        borderRadius: 10,
        bottom: 1,
        zIndex: 2
    },
    mask: {
        width: '100%',
        flex: 1,
        backgroundColor: '#00000060',
        zIndex: 1
    },
    row: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
        borderRadius: 10
    },
    titleText: {
        fontSize: 13,
        color: CommonColor.fontColor,
        fontWeight: 'bold',
        marginLeft: 16,
    },
    subTitleText: {
        fontSize: 10,
        color: CommonColor.deepGrey,
        marginLeft: 12,
        flex: 1,
    },
    editButton: {
        paddingHorizontal: 10,
        height: 28,
        backgroundColor: CommonColor.transparentGreyBg,
        borderRadius: 2,
        justifyContent: 'center',
        alignItems: 'center',
    },
    editText: {
        fontSize: 13,
        color: '#3050ff',
    },
    closeButton: {
        paddingRight:10,
        paddingTop: 16
    },
    closeImg: {
        width: 16,
        height: 16,
        resizeMode: 'contain',
        transform: [{rotate: '90deg'}]
    },
    listContent: {
        width: '100%',
        flexDirection: 'row',
        flexWrap: 'wrap',
    },
    itemLayout: {
        width: SCREEN_WIDTH - 80 >> 2,
        height: 32,
        justifyContent: 'center',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#eee',
        borderRadius: 6,
        marginLeft: 16,
        marginTop: 12,
    },
    itemLayoutDefault: {
        width: SCREEN_WIDTH - 80 >> 2,
        height: 26,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#eee',
        borderRadius: 3,
        marginLeft: 16,
        marginTop: 10,
    },
    itemText: {
        fontSize: 11,
        fontWeight: 'bold',
        color: CommonColor.fontColor,
    },  
});

