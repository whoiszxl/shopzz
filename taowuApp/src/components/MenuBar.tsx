import React from 'react';
import { View, ScrollView, TouchableOpacity, StyleSheet } from 'react-native';
import LinearGradient from 'react-native-linear-gradient';

const MenuBar = ({ menus, activeMenu, onMenuPress }:any) => {
  return (
    <ScrollView
      horizontal
      showsHorizontalScrollIndicator={false}
      contentContainerStyle={styles.menuContainer}
    >
      {menus.map((menu:any, index: number) => (
        <LinearGradient key={index}
        colors={['rgba(255, 165, 0, 0.01)', 'rgba(255, 165, 0, 0.005)', 'rgba(255, 165, 0, 0.002)', 'white']} // 渐变色数组
        style={[styles.menuItem, activeMenu === index && styles.activeMenuItem]}
      >
        <TouchableOpacity
          key={index}
          onPress={() => onMenuPress(index)}
        >
          {menu}   
        </TouchableOpacity>
        </LinearGradient>
      ))}
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  menuContainer: {
    flexDirection: 'row',
    alignItems: 'center',
  },
    linearGradient: {
    flex: 1,
  },
  menuItem: {
    paddingHorizontal: 5,
    paddingVertical: 8,
    marginRight: 10,
    borderRadius: 4,
    backgroundColor: 'rgba(255, 165, 0, 0.05)',
    width: 180,
  },
  activeMenuItem: {
    backgroundColor: 'blue',
  },
  menuText: {
    color: 'black',
  },
});

export default MenuBar;
