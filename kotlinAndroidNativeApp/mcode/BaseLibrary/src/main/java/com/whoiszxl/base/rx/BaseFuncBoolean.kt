package com.whoiszxl.base.rx

import com.whoiszxl.base.common.ResultCode
import com.whoiszxl.base.data.protocol.BaseResp
import rx.Observable
import rx.functions.Func1

class BaseFuncBoolean<T>: Func1<BaseResp<T>, Observable<Boolean>> {
    override fun call(t: BaseResp<T>): Observable<Boolean> {
        if(t.status != ResultCode.SUCCESS){
            return Observable.error(BaseException(t.status, t.message))
        }
        return Observable.just(true)
    }
}