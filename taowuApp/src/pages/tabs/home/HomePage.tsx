import React, { useEffect, useState } from 'react';
import {
    View,
    Text,
    StyleSheet,
    Dimensions,
    Image,
    SafeAreaView,
    StatusBar,
} from 'react-native'
import { useLocalStore } from 'mobx-react';
import { observer } from 'mobx-react';
import FlowList from '../../../components/flowlist/FlowList.js';
import ResizeImage from '../../../components/ResizeImage';
import Heart from '../../../components/ThumbsButton';
import SearchBar from '../../../components/SearchBar';
import TabList from './components/TabList';
import HomeStore from '../../../stores/HomeStore';
import { CommonColor } from '../../../common/CommonColor';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

export default observer(() => {

    const store = useLocalStore(() => new HomeStore());

    const [currentTab, setCurrentTab] = useState('推荐');

    useEffect(() => {
        store.requestVideotTest();
        store.getCategoryList();
    }, []);
    
    const refreshNewData = () => {
        store.resetPage();
        store.requestVideotTest();
        store.getCategoryList();

    }

    const loadMoreData = () => {
      store.requestVideotTest();
      store.getCategoryList();

    }


    const renderItem = ({item, index}: {item: VideoEntity, index: number}) => {
        return (
            <View style={styles.item}>
                <ResizeImage uri={item.cover} />
                <Text style={styles.descText}>{item.descs}</Text>
                <View style={styles.memberNameLayout}>
                    <Image style={styles.avatarImage} source={{ uri: item.cover }} />
                    <Text style={styles.memberNameText}>{"测试名称"}</Text>
                    <Heart value={false}
                        onValueChanged={(value: boolean) => {

                        }}
                    />
                    <Text style={styles.likeCountText}>{666}</Text>
                </View>
            </View>
        );
    }

    const Footer = () => {
        return (
            <Text style={styles.footerTxt}>已经滑到底了...</Text>
        );
    }

    const renderFocus = () => {
        return (
            <Text style={styles.testPage}>关注页面</Text>
        );
    }

    const renderVideo = () => {
        return (
            <Text style={styles.testPage}>视频页面</Text>
        );
    }


    const categoryList = store.categoryList;
    return (
        <SafeAreaView>

        <View style={styles.root}>
            <View style={{height: StatusBar.currentHeight, width: '100%', backgroundColor: 'white'}}></View>

            <SearchBar titleLayoutStyle={{paddingTop: 5}}/>

            <TabList categoryList={categoryList} 
                onCategoryChange={(category: Category) => {
                    setCurrentTab(category.name);
                }}/>


            {
                (currentTab === '关注') ? renderFocus() : 
                (currentTab === '视频') ? renderVideo() :


                <>
                    <FlowList
                        style={styles.flatList}
                        data={store.jobList}
                        keyExtrator={(item: VideoEntity) => `${item.id}`}
                        extraData={[ store.refreshing ]}
                        contentContainerStyle={styles.container}
                        renderItem={renderItem}
                        numColumns={2}
                        refreshing={store.refreshing}
                        onRefresh={refreshNewData}
                        onEndReachedThreshold={0.1}
                        onEndReached={loadMoreData}
                        ListFooterComponent={<Footer />}/>
                
                </>
            }

            
        </View>
        </SafeAreaView>
    );
});

const styles = StyleSheet.create({
    root: {
        width: '100%',
        height: '100%',
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#f0f0f0'
    },
    flatList: {
        width: '100%',
        height: '100%',
    },
    container: {
        // paddingTop: 6,
    },
    item: {
        width: (SCREEN_WIDTH - 9) / 2,
        backgroundColor: 'white',
        marginLeft: 3,
        marginBottom: 6,
        borderRadius: 3,
        overflow: 'hidden',
    },
    descText: {
        fontSize: 12,
        color: CommonColor.fontColor,
        marginHorizontal: 10,
        marginVertical: 4,
    },
    memberNameLayout: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
        paddingHorizontal: 10,
        marginBottom: 10,
    },
    avatarImage: {
        width: 20,
        height: 20,
        resizeMode: 'cover',
        borderRadius: 30,
    },
    memberNameText: {
        fontSize: 10,
        color: CommonColor.fontColor,
        marginLeft: 6,
        flex: 1,
    },
    likeCountText: {
        fontSize: 12,
        color: CommonColor.normalGrey,
        marginLeft: 3,
    },
    footerTxt: {
        width: '100%',
        fontSize: 14,
        color: '#999',
        marginVertical: 16,
        textAlign: 'center',
        textAlignVertical: 'center',
    },

    testPage: {
        width: '100%',
        height: '100%',
    }
})