import React from 'react';
import { View, StyleSheet } from 'react-native';
import { CommonColor } from '../common/CommonColor';

const TtDivider = () => {
  return <View style={styles.divider} />;
};

const styles = StyleSheet.create({
  divider: {
    height: 0.5, // 设置分割线高度
    marginHorizontal: 7,
    backgroundColor: CommonColor.line, // 设置分割线颜色
  },
});

export default TtDivider;
