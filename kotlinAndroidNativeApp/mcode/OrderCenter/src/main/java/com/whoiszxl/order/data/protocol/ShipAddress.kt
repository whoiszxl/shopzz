package com.whoiszxl.order.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShipAddress (
        val id: Int,
        var receiverName: String,
        var receiverPhone: String,
        var receiverProvince: String,
        var receiverCity: String,
        var receiverDistrict: String?,
        var receiverAddress: String,
        var receiverZip: String,
        var isDefault: Boolean
):Parcelable