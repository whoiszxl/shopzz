import React, { useEffect, useState } from "react";
import { Image, Dimensions, View } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { CommonColor } from "../../common/CommonColor";

type Props = {
    uri: string;
    resourceType: number;
};

const { width: SCREEN_WIDTH } = Dimensions.get('window');
const SHOW_WIDTH = SCREEN_WIDTH - 18 >> 1;

export default ({ uri, resourceType }: Props) => {

    const [height, setHeight] = useState<number>(200);

    useEffect(() => {
        Image.getSize(uri, (width: number, height: number) => {
            const showHeight = SHOW_WIDTH * height / width;
            setHeight(showHeight);
        })
    }, [uri]);

    return (
        <View>
            <Image
                style={{
                    width: SCREEN_WIDTH - 18 >> 1,
                    height: height,
                    resizeMode: 'cover',
                }}
                source={{ uri: uri }}
            />

            {
                resourceType === 1 ? 
                <View style={{width: 16, height: 16, borderRadius: 25, position: "absolute", top: 3,right: 2, backgroundColor: CommonColor.transparentGreyBg, alignContent: 'center',
                alignItems: 'center',
                justifyContent: 'center',}}>
                    <Ionicons style={{
                        
                    }} name="caret-forward" size={13} color={'white'} />
                </View>
                : null
            }
            

        </View>
    );
}