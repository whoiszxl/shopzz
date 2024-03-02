import {StyleSheet, Dimensions} from 'react-native';

const width = Dimensions.get('screen').width;
export const styles = StyleSheet.create({
  caroselImageStyle: {
    width: width,
    resizeMode: 'contain',
    height: 300,
  },
  previewImageContainerStyle: {
    width,
    justifyContent: 'center',
    alignItems: 'center',
  },
  previewImageStyle: {
    width: width,
    resizeMode: 'contain',
    height: 400,
  },
});
