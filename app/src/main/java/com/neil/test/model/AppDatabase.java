package com.neil.test.model;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.neil.test.model.bean.User;
import com.neil.test.model.dao.UserDao;

/**
 * @author zhongnan
 * @time 2021/7/19 11:03
 *
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
}
