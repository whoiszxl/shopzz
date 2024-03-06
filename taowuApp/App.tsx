import React from 'react';
import { StatusBar } from 'react-native';

import { SafeAreaProvider } from 'react-native-safe-area-context';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';

import SplashPage from './src/pages/splash/SplashPage';
import LoginPage from './src/pages/login/LoginPage';
import CheckSmsCaptchaPage from './src/pages/login/CheckSmsCaptchaPage';
import TabPage from './src/pages/tabs/TabPage';

import HomePage from './src/pages/tabs/home/HomePage';
import BuyPage from './src/pages/tabs/buy/BuyPage';
import DiscoveryPage from './src/pages/tabs/discovery/DiscoveryPage';
import MinePage from './src/pages/tabs/mine/MinePage';
import ChatPage from './src/pages/tabs/discovery/ChatPage';





const Stack = createStackNavigator();

function App(): JSX.Element {

  return (
    <SafeAreaProvider>
      <StatusBar translucent backgroundColor={'transparent'} barStyle={'dark-content'} />

      <NavigationContainer>
        <Stack.Navigator initialRouteName='SplashPage'>
          <Stack.Screen options={{headerShown: false}} name='SplashPage' component={SplashPage}/>
          <Stack.Screen options={{headerShown: false}} name='LoginPage' component={LoginPage}/>
          <Stack.Screen options={{headerShown: false}} name='CheckSmsCaptchaPage' component={CheckSmsCaptchaPage}/>
          <Stack.Screen options={{headerShown: false}} name='TabPage' component={TabPage}/>
          <Stack.Screen options={{headerShown: false}} name='HomePage' component={HomePage}/>

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
