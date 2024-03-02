import React, { useEffect, useState, useRef } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';

import TabModal, { TabModalRef } from './TabModal';

type Props = {
    categoryList: Category[];
    onCategoryChange: (category: Category) => void;
};

import { CommonColor } from '../../../../common/CommonColor';
import Entypo from 'react-native-vector-icons/Entypo';


export default ({categoryList, onCategoryChange}: Props) => {
    const modalRef = useRef<TabModalRef>(null);

    const [category, setCategory] = useState<Category>();

    useEffect(() => {
        setCategory(categoryList.find(i => i.name === '推荐'));
    }, []);

    const onCategoryPress = (category: Category) => {
        setCategory(category);
        onCategoryChange?.(category);
    }

    return (
        <View style={styles.container}>
            <ScrollView
                style={styles.scrollView}
                horizontal={true}
                showsHorizontalScrollIndicator={false}
            >
                {categoryList.map((item: Category, index: number) => {
                    const isSelected = item.name === category?.name;
                    return (
                        <TouchableOpacity
                            key={`${item.name}`}
                            style={styles.tabItem}
                            onPress={() => onCategoryPress(item)}
                        >
                            <Text style={isSelected ? styles.tabItemTextSelected : styles.tabItemText}>{item.name}</Text>
                        </TouchableOpacity>
                    );
                })}
            </ScrollView>
            <TouchableOpacity style={styles.openButton}
                onPress={() => {
                    modalRef.current?.show();
                }}
            >
                <Entypo name='chevron-down' size={20}/>
            </TouchableOpacity>

            <TabModal ref={modalRef} categoryList={categoryList}/>
        </View>
    );
}

const styles = StyleSheet.create({
    container: {
        width: '100%',
        height: 36,
        flexDirection: 'row',
        backgroundColor: 'white',
        marginBottom: 6,
    },
    scrollView: {
        flex: 1,
        height: '100%',
    },
    openButton: {
        width: 40,
        height: '100%',
        justifyContent: 'center',
        alignItems: 'center',
    },
    tabItem: {
        width: 40,
        height: '100%',
        justifyContent: 'center',
        alignItems: 'center',
    },
    tabItemText: {
        fontSize: 12,
        color: CommonColor.deepGrey,
    },
    tabItemTextSelected: {
        fontSize: 13,
        color: CommonColor.fontColor,
        fontWeight: 'bold',
        borderBottomWidth: 2,
        paddingBottom: 1,
        borderColor: CommonColor.mainColorTwo
    },
});