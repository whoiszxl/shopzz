'use strict';
import React, { Component } from 'react';
import {
    StyleSheet,
    AppRegistry,
    DeviceEventEmitter,
    View,
    Animated,
} from 'react-native';

let keyValue = 0;
const rrcRNLoadingKey = 'rrc-rn-loading-key';

export default class TopView extends Component<any, any> {

    state = {
        elements: [] as Array<any>,
        translateX: new Animated.Value(0) as Animated.Value,
        translateY: new Animated.Value(0) as Animated.Value,
        scaleX: new Animated.Value(1) as Animated.Value,
        scaleY: new Animated.Value(1) as Animated.Value,
        toastElements: [] as Array<any>,
    };

    static addAlert(element: any) {
        let key = ++keyValue;
        DeviceEventEmitter.emit('addOverlay-Alert', { key, element });
        return key;
    }

    static addLoading(element: any) {
        let key = rrcRNLoadingKey;
        DeviceEventEmitter.emit('addOverlay-Loading', { key, element });
        return key;
    }

    static addToast(element: any) {
        let key = ++keyValue;
        DeviceEventEmitter.emit('addOverlay-Toast', { key, element });
        return key;
    }

    static removeLoading() {
        DeviceEventEmitter.emit('removeOverlay-Loading');
    }

    static removeAlert(key: any) {
        DeviceEventEmitter.emit('removeOverlay-Alert', { key });
    }

    static removeToast(key: any) {
        DeviceEventEmitter.emit('removeOverlay-Toast', { key });
    }

    static removeAll() {
        DeviceEventEmitter.emit('removeAllOverlay-Alert', {});
    }

    static transform(transform: any, animated: boolean, animatesOnly: boolean = false) {
        DeviceEventEmitter.emit('transformRoot-Alert', {
            transform,
            animated,
            animatesOnly,
        });
    }

    static restore(animated: boolean, animatesOnly: boolean = false) {
        DeviceEventEmitter.emit('restoreRoot-Alert', {
            animated,
            animatesOnly,
        });
    }

    constructor(props: any) {
        super(props);
        this.state = {
            elements: [],
            translateX: new Animated.Value(0),
            translateY: new Animated.Value(0),
            scaleX: new Animated.Value(1),
            scaleY: new Animated.Value(1),
            toastElements: [],
        };
    }
    
    componentDidMount() {
        DeviceEventEmitter.addListener('addOverlay-Alert', (e) =>
            this.addAlertToTopView(e),
        );
        DeviceEventEmitter.addListener('removeOverlay-Alert', (e) =>
            this.removeAlertFromTopView(e),
        );
        DeviceEventEmitter.addListener('removeAllOverlay-Alert', () =>
            this.removeAll(),
        );
        DeviceEventEmitter.addListener('transformRoot-Alert', (e) =>
            this.transform(e),
        );
        DeviceEventEmitter.addListener('restoreRoot-Alert', (e) =>
            this.restore(e),
        );

        DeviceEventEmitter.addListener('addOverlay-Loading', (e) =>
            this.addLoadingToTopView(e),
        );
        DeviceEventEmitter.addListener('removeOverlay-Loading', () =>
            this.removeLoadingFromTopView(),
        );

        DeviceEventEmitter.addListener('addOverlay-Toast', (e) =>
            this.addToastToTopView(e),
        );
        DeviceEventEmitter.addListener('removeOverlay-Toast', (e) =>
            this.removeLToastFromTopView(e),
        );
    }

    componentWillUnmount() {
        DeviceEventEmitter.removeAllListeners('addOverlay-Alert');
        DeviceEventEmitter.removeAllListeners('removeOverlay-Alert');
        DeviceEventEmitter.removeAllListeners('removeAllOverlay-Alert');
        DeviceEventEmitter.removeAllListeners('transformRoot-Alert');
        DeviceEventEmitter.removeAllListeners('restoreRoot-Alert');

        DeviceEventEmitter.removeAllListeners('addOverlay-Loading');
        DeviceEventEmitter.removeAllListeners('removeOverlay-Loading');

        DeviceEventEmitter.removeAllListeners('addOverlay-Toast');
        DeviceEventEmitter.removeAllListeners('removeOverlay-Toast');
    }

    addAlertToTopView(e: any) {
        let { elements } = this.state;
        elements.push(e);
        this.setState({ elements });
    }

    removeAlertFromTopView(e: any) {
        let { elements } = this.state;
        for (let i = elements.length - 1; i >= 0; --i) {
            if (elements[i].key === e.key) {
                elements.splice(i, 1);
                break;
            }
        }
        this.setState({ elements });
    }

    removeLToastFromTopView(e: any) {
        let { toastElements } = this.state;
        for (let i = toastElements.length - 1; i >= 0; --i) {
            if (toastElements[i].key === e.key) {
                toastElements.splice(i, 1);
                break;
            }
        }
        this.setState({ toastElements });
    }

    addLoadingToTopView(e: any) {
        let { elements } = this.state;
        for (let i = elements.length - 1; i >= 0; --i) {
            if (elements[i].key === rrcRNLoadingKey) {
                elements.splice(i, 1);
            }
        }
        elements.push(e);
        this.setState({ elements });
    }

    removeLoadingFromTopView() {
        let { elements } = this.state;
        for (let i = elements.length - 1; i >= 0; --i) {
            if (elements[i].key === rrcRNLoadingKey) {
                elements.splice(i, 1);
                break;
            }
        }
        this.setState({ elements });
    }

    addToastToTopView(e: any) {
        let { toastElements } = this.state;
        toastElements.push(e);
        this.setState({ toastElements });
    }

    removeAll() {
        this.setState({ elements: [] });
    }

    transform(e: any) {
        let { translateX, translateY, scaleX, scaleY } = this.state;
        let { transform, animated, animatesOnly } = e;
        let tx = 0,
            ty = 0,
            sx = 1,
            sy = 1;
        transform.map((item: any) => {
            if (item && typeof item === 'object') {
                for (let name in item) {
                    let value = item[name];
                    switch (name) {
                        case 'translateX':
                            tx = value;
                            break;
                        case 'translateY':
                            ty = value;
                            break;
                        case 'scaleX':
                            sx = value;
                            break;
                        case 'scaleY':
                            sy = value;
                            break;
                    }
                }
            }
        });
        if (animated) {
            let animates = [
                // @ts-ignore
                Animated.spring(translateX, { toValue: tx, friction: 9 }),
                // @ts-ignore
                Animated.spring(translateY, { toValue: ty, friction: 9 }),
                // @ts-ignore
                Animated.spring(scaleX, { toValue: sx, friction: 9 }),
                // @ts-ignore
                Animated.spring(scaleY, { toValue: sy, friction: 9 }),
            ];
            animatesOnly
                ? animatesOnly(animates)
                : Animated.parallel(animates).start();
        } else {
            if (animatesOnly) {
                let animates = [
                    // @ts-ignore
                    Animated.timing(translateX, { toValue: tx, duration: 1 }),
                    // @ts-ignore
                    Animated.timing(translateY, { toValue: ty, duration: 1 }),
                    // @ts-ignore
                    Animated.timing(scaleX, { toValue: sx, duration: 1 }),
                    // @ts-ignore
                    Animated.timing(scaleY, { toValue: sy, duration: 1 }),
                ];
                animatesOnly(animates);
            } else {
                translateX.setValue(tx);
                translateY.setValue(ty);
                scaleX.setValue(sx);
                scaleY.setValue(sy);
            }
        }
    }

    restore(e: any) {
        let { translateX, translateY, scaleX, scaleY } = this.state;
        let { animated, animatesOnly } = e;
        if (animated) {
            let animates = [
                // @ts-ignore
                Animated.spring(translateX, { toValue: 0, friction: 9 }),
                // @ts-ignore
                Animated.spring(translateY, { toValue: 0, friction: 9 }),
                // @ts-ignore
                Animated.spring(scaleX, { toValue: 1, friction: 9 }),
                // @ts-ignore
                Animated.spring(scaleY, { toValue: 1, friction: 9 }),
            ];
            animatesOnly
                ? animatesOnly(animates)
                : Animated.parallel(animates).start();
        } else {
            if (animatesOnly) {
                let animates = [
                    // @ts-ignore
                    Animated.timing(translateX, { toValue: 0, duration: 1 }),
                    // @ts-ignore
                    Animated.timing(translateY, { toValue: 0, duration: 1 }),
                    // @ts-ignore
                    Animated.timing(scaleX, { toValue: 1, duration: 1 }),
                    // @ts-ignore
                    Animated.timing(scaleY, { toValue: 1, duration: 1 }),
                ];
                animatesOnly(animates);
            } else {
                translateX.setValue(0);
                translateY.setValue(0);
                scaleX.setValue(1);
                scaleY.setValue(1);
            }
        }
    }

    render() {
        let {
            elements,
            toastElements,
            translateX,
            translateY,
            scaleX,
            scaleY,
        } = this.state;
        let transform = [
            { translateX },
            { translateY },
            { scaleX },
            { scaleY },
        ];
        // 如果存在loading  只加载loading，loading结束后加载其他element
        let laodingItem = null;
        for (let i = elements.length - 1; i >= 0; --i) {
            if (elements[i].key === rrcRNLoadingKey) {
                laodingItem = elements[i].element;
                break;
            }
        }
        if (laodingItem) {
            return (
                <View style={{ flex: 1 }}>
                    <Animated.View style={{ flex: 1, transform: transform }}>
                        {this.props.children}
                    </Animated.View>
                    <View style={styles.overlayContainer}>{laodingItem}</View>
                </View>
            );
        }
        return (
            <View style={{ flex: 1 }}>
                <Animated.View style={{ flex: 1, transform: transform }}>
                    {this.props.children}
                </Animated.View>
                {elements.length > 0 || toastElements.length > 0 ? (
                    <View
                        style={styles.overlayContainer}
                        pointerEvents={'box-none'}>
                        {
                            // 如果最后一项是alert，应该查找并清除前几项中的actionSheet（如果有的话）
                            // 目前alert和actionSheet是相容的，应该改为互斥的，即最后出现的可以打断之前出现的
                            elements.map((item, index) => {
                                // 同一时刻只加载elements中最后一个element
                                if (index === elements.length - 1) {
                                    return (
                                        <View
                                            key={'TopView' + item.key}
                                            style={styles.overlay}>
                                            {item.element}
                                        </View>
                                    );
                                } else {
                                    return null;
                                }
                            })
                        }
                        {toastElements.map((item, index) => {
                            // 同一时刻只加载elements中最后一个element
                            if (index === toastElements.length - 1) {
                                return (
                                    <View
                                        key={'TopView_Toast' + item.key}
                                        style={styles.overlay}
                                        pointerEvents={'box-none'}>
                                        {item.element}
                                    </View>
                                );
                            } else {
                                return null;
                            }
                        })}
                    </View>
                ) : null}
            </View>
        );
    }
}

var styles = StyleSheet.create({
    overlayContainer: {
        backgroundColor: 'rgba(0, 0, 0, 0)',
        position: 'absolute',
        top: 0,
        left: 0,
        right: 0,
        bottom: 0,
        justifyContent: 'center',
        alignItems: 'center',
    },
    overlay: {
        position: 'absolute',
    },
});

// @ts-ignore
if (!AppRegistry.registerComponentOld) {
    // @ts-ignore
    AppRegistry.registerComponentOld = AppRegistry.registerComponent;
}

AppRegistry.registerComponent = function (appKey, componentProvider) {
    class RootElement extends Component {
        render() {
            let Component = componentProvider();
            return (
                <TopView>
                    <Component {...this.props} />
                </TopView>
            );
        }
    }

    // @ts-ignore
    return AppRegistry.registerComponentOld(appKey, () => RootElement);
};
