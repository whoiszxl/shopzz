import React from 'react';
import { View, Text, StyleSheet, SafeAreaView } from 'react-native';
import { useSafeAreaInsets } from 'react-native-safe-area-context';
import Icon from 'react-native-vector-icons/Ionicons';
import { CommonColor } from '../../../../common/CommonColor';
import { TouchableOpacity } from 'react-native-gesture-handler';
import AntDesign from 'react-native-vector-icons/AntDesign';
import { useNavigation } from '@react-navigation/native';
import { StackNavigationProp } from '@react-navigation/stack';

interface TopMenuBarProps {
    name: string;
    jobTitle: string;
}

const ChatTopMenu: React.FC<TopMenuBarProps> = ({ name, jobTitle }) => {

    const navigation = useNavigation<StackNavigationProp<any>>();


    const insets = useSafeAreaInsets();

    return (
        <SafeAreaView style={[styles.container, { paddingTop: insets.top - 20 }]}>

            <View style={styles.topBar}>
                <View style={styles.oneLine}>
                    <Icon style={styles.leftText} size={20} color={CommonColor.fontColor} name='chevron-back-sharp' onPress={() => { navigation.goBack() }}/>
                    <Text style={styles.centerText}>{name + " · " + jobTitle}</Text>
                    <Icon style={styles.rightText} size={20} color={CommonColor.fontColor} name='menu'/>
                </View>

                {renderNormalFunction()}
            </View>
        </SafeAreaView>
    );
};



const renderNormalFunction = () => {
    const styles = StyleSheet.create({
      root: {
        width: '100%',
        flexDirection: 'column',
        alignItems: 'center',
        backgroundColor: 'white',
        borderRadius: 12,
        borderBottomColor: '#eee',
        marginTop: 10
      },

      customMenuLayout: {
        width: '100%',
        flexDirection: 'row',
        paddingTop: 2,
        alignItems: 'center',
      },

      menuItemLayout: {
        width: '100%',
        flexDirection: 'row',
        alignItems: 'center',
      },


      mainTitle: {
        fontSize: 11,
        color: CommonColor.fontColor
      },

      subTitle: {
        fontSize: 10,
        color: CommonColor.normalGrey
      }
    });


    return (
      <View style={styles.root}>
        <View style={styles.customMenuLayout}>
          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <Icon size={20} color={'black'} name='logo-whatsapp'/>
            <Text style={styles.subTitle}>换电话</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <AntDesign size={20} color={'black'} name='smileo'/>
            <Text style={styles.subTitle}>换微信</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <AntDesign size={20} color={'black'} name='filetext1'/>
            <Text style={styles.subTitle}>发简历</Text>
          </View>

          <View style={{flexDirection: 'column', flex:1, alignItems: 'center'}}>
            <AntDesign size={20} color={'black'} name='closecircleo'/>
            <Text style={styles.subTitle}>不感兴趣</Text>
          </View>
        </View>


      </View>
    );
  }


const styles = StyleSheet.create({
    oneLine: {
        flexDirection: 'row', // 横向排列
        justifyContent: 'space-between', // 左中右分散对齐
        alignItems: 'center', // 垂直居中对齐
        padding: 2,
    },

    leftText: {
        flex: 1,
        textAlign: 'left',
      },
      centerText: {
        flex: 1,
        textAlign: 'center',
        color: CommonColor.fontColor
      },
      rightText: {
        flex: 1,
        textAlign: 'right',
      },

    container: {
        backgroundColor: '#ffffff',
    },
    topBar: {
        padding: 16,
        justifyContent: 'center',
        alignItems: 'center',
    },
    title: {
        fontSize: 18,
        fontWeight: 'bold',
        color: 'black',
    },
});

export default ChatTopMenu;
