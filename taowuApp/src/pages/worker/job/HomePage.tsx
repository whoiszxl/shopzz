import React, { useEffect } from 'react';
import {
    View,
    Text,
    StyleSheet,
    Dimensions,
    Image,
} from 'react-native'
import { useLocalStore } from 'mobx-react';
import { observer } from 'mobx-react';
import FlowList from '../../../components/flowlist/FlowList.js';
import ResizeImage from '../../../components/ResizeImage';
import Heart from '../../../components/ThumbsButton.js';
import TitleBar from './components/TitleBar';
import CategoryList from './components/CategoryList';
import HomeStore from '../../../stores/HomeStore';

const { width: SCREEN_WIDTH } = Dimensions.get('window');

export default observer(() => {

    const store = useLocalStore(() => new HomeStore());

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

    const categoryList = store.categoryList.filter(i => i.isAdd);
    return (
        <View style={styles.root}>
            <Text>Hello</Text>
        </View>
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
        width: SCREEN_WIDTH - 18 >> 1,
        backgroundColor: 'white',
        marginLeft: 6,
        marginBottom: 6,
        borderRadius: 8,
        overflow: 'hidden',
    },
    titleTxt: {
        fontSize: 14,
        color: '#333',
        marginHorizontal: 10,
        marginVertical: 4,
    },
    nameLayout: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
        paddingHorizontal: 10,
        marginBottom: 10,
    },
    avatarImg: {
        width: 20,
        height: 20,
        resizeMode: 'cover',
        borderRadius: 10,
    },
    nameTxt: {
        fontSize: 12,
        color: '#999',
        marginLeft: 6,
        flex: 1,
    },
    heart: {
        width: 20,
        height: 20,
        resizeMode: 'contain',
    },
    countTxt: {
        fontSize: 14,
        color: '#999',
        marginLeft: 4,
    },
    footerTxt: {
        width: '100%',
        fontSize: 14,
        color: '#999',
        marginVertical: 16,
        textAlign: 'center',
        textAlignVertical: 'center',
    },
})