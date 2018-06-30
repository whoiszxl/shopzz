package com.whoiszxl.base.widgets

import android.content.Context
import android.widget.ImageView
import com.whoiszxl.base.utils.GlideUtils
import com.youth.banner.loader.ImageLoader

class BannerImageLoader:ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        GlideUtils.loadUrlImage(context, path.toString(), imageView)
    }
}