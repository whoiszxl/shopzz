package com.whoiszxl.base.data.protocol

class BaseResp<out T>(val status:Int, val message:String, val data:T)