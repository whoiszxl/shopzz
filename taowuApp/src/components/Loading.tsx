import React from 'react';
import { View, Text, ActivityIndicator, Image, Dimensions } from 'react-native';
import TopView from './TopView';
const { width } = Dimensions.get('screen');

const LoadingOptions: any = {};

export default class Loading {

    static setLoadingOptions(options: any = {}) {
        if (typeof options.text === 'string') {
            LoadingOptions.text = options.text;
        }
        if (typeof options.loadingBackgroundColor === 'string') {
            LoadingOptions.loadingBackgroundColor =
                options.loadingBackgroundColor;
        }
        if (options.loadingImage != undefined) {
            LoadingOptions.loadingImage = options.loadingImage;
        }
        if (
            typeof options.loadingViewStyle === 'object' &&
            !Array.isArray(options.loadingViewStyle)
        ) {
            LoadingOptions.loadingViewStyle = options.loadingViewStyle;
        }
        if (
            typeof options.loadingTextStyle === 'object' &&
            !Array.isArray(options.loadingTextStyle)
        ) {
            LoadingOptions.loadingTextStyle = options.loadingTextStyle;
        }
    }

  static show(
    textContent = LoadingOptions.text && LoadingOptions.text.length > 0
      ? LoadingOptions.text
      : '加载中...'
  ) {
    const loadingBackgroundColor = LoadingOptions.loadingBackgroundColor
      ? LoadingOptions.loadingBackgroundColor
      : 'rgba(0,0,0,0.0)';
    const loadingViewBackgroundColor =
      LoadingOptions.loadingViewStyle &&
      LoadingOptions.loadingViewStyle.backgroundColor
        ? LoadingOptions.loadingViewStyle.backgroundColor
        : 'rgba(0,0,0,0.8)';
    const loadingView = (
      <View
        style={{
          position: 'absolute',
          left: 0,
          right: 0,
          top: 0,
          bottom: 0,
          backgroundColor: loadingBackgroundColor,
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        <View
          style={[
            {
              justifyContent: 'center',
              alignItems: 'center',
              backgroundColor: loadingViewBackgroundColor,
              padding: 10,
              borderRadius: 5,
              margin: width * 0.1,
            },
            { ...LoadingOptions.loadingViewStyle },
          ]}
        >
          {LoadingOptions.loadingImage ? (
            <Image
              source={LoadingOptions.loadingImage}
              style={{ marginTop: 10 }}
            />
          ) : (
            <ActivityIndicator
              color={'#fff'}
              animating={true}
              style={{ marginTop: 10 }}
              size="large"
            />
          )}
          <Text
            style={[
              {
                color: '#fff',
                margin: 10,
                fontSize: 16,
                lineHeight: 20,
                marginBottom: 5,
              },
              { ...LoadingOptions.loadingTextStyle },
            ]}
          >
            {textContent}
          </Text>
        </View>
      </View>
    );
    TopView.addLoading(loadingView);
  }

  static hide() {
    TopView.removeLoading();
  }

  static transformRoot(
    transform: any,
    animated: boolean,
    animatesOnly: boolean = false
  ) {
    TopView.transform(transform, animated, animatesOnly);
  }

  static restoreRoot(animated: boolean, animatesOnly: boolean = false) {
    TopView.restore(animated, animatesOnly);
  }
}
