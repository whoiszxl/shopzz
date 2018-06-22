package com.whoiszxl.xl.ec.database;

import android.content.Context;

import org.greenrobot.greendao.AbstractDaoMaster;

/**
 * @author whoiszxl
 * 自定义数据库存储类
 */
public class ReleaseOpenHelper extends DaoMaster.OpenHelper{

    public ReleaseOpenHelper(Context context, String name) {
        super(context, name);
    }


}
