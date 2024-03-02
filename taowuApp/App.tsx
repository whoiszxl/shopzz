import React from 'react';
import { StatusBar } from 'react-native';

import { SafeAreaProvider } from 'react-native-safe-area-context';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import SplashPage from './src/pages/splash/SplashPage';
import LoginPage from './src/pages/login/LoginPage';
import CheckSmsCaptchaPage from './src/pages/login/CheckSmsCaptchaPage';
import TabPage from './src/pages/tabs/TabPage';

import JobPage from './src/pages/tabs/job/HomePage';
import DiscoveryPage from './src/pages/tabs/discovery/DiscoveryPage';
import BuyPage from './src/pages/tabs/message/BuyPage';
import MinePage from './src/pages/tabs/mine/MinePage';
import InitMemberInfoPage from './src/pages/tabs/init/InitMemberInfoPage';
import ChatPage from './src/pages/tabs/message/ChatPage';





const Stack = createStackNavigator();

function App(): JSX.Element {

  return (
    <SafeAreaProvider>
      

      <StatusBar
        barStyle={'dark-content'}
        translucent={false}
        backgroundColor={'black'}
      />


      <NavigationContainer>
        <Stack.Navigator initialRouteName='SplashPage'>
          <Stack.Screen options={{headerShown: false}} name='SplashPage' component={SplashPage}/>
          <Stack.Screen options={{headerShown: false}} name='LoginPage' component={LoginPage}/>
          <Stack.Screen options={{headerShown: false}} name='CheckSmsCaptchaPage' component={CheckSmsCaptchaPage}/>
          <Stack.Screen options={{headerShown: false}} name='TabPage' component={TabPage}/>
          <Stack.Screen options={{headerShown: false}} name='JobPage' component={JobPage}/>
          <Stack.Screen options={{headerShown: false}} name='InitMemberInfoPage' component={InitMemberInfoPage}/>

          <Stack.Screen options={{headerShown: false}} name='DiscoveryPage' component={DiscoveryPage}/>
          <Stack.Screen options={{headerShown: false}} name='BuyPage' component={BuyPage}/>
          <Stack.Screen options={{headerShown: false}} name='MinePage' component={MinePage}/>
          <Stack.Screen options={{headerShown: false}} name='ChatPage' component={ChatPage}/>



        </Stack.Navigator>
      </NavigationContainer>


    </SafeAreaProvider>
  );
}


export default App;
