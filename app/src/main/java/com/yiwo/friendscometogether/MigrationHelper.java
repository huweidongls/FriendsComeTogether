package com.yiwo.friendscometogether;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yiwo.friendscometogether.greendao.gen.DaoMaster;
import com.yiwo.friendscometogether.greendao.gen.DuiZhangDeHuoDongDbModelDao;
import com.yiwo.friendscometogether.greendao.gen.DuiZhangFenZuDbModelDao;
import com.yiwo.friendscometogether.greendao.gen.LookHistoryDbModelDao;
import com.yiwo.friendscometogether.greendao.gen.MyGameCardDbModelDao;
import com.yiwo.friendscometogether.greendao.gen.UserGiveModelDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by ljc on 2019/9/18.
 * 升级数据库辅助类
 */
public class MigrationHelper extends DaoMaster.DevOpenHelper {
    private final String TAG="MigrationHelper";

    public MigrationHelper(Context context, String name) {
        super(context, name);
    }

    public MigrationHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i(TAG,"oldVersion="+oldVersion+",newVersion="+newVersion);
        if (newVersion > oldVersion) {
            new UpgradeHelper().migrate(db,
                    DuiZhangFenZuDbModelDao.class,
                    MyGameCardDbModelDao.class,
                    DuiZhangDeHuoDongDbModelDao.class,
                    LookHistoryDbModelDao.class,
                    UserGiveModelDao.class);
        }
    }
}
