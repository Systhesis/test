package com.neil.test.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.neil.test.model.bean.User;

import java.util.List;

/**
 * @author zhongnan
 * @time 2021/7/19 10:55
 *
 */
@Dao
public interface UserDao {
    @Query("select * from user")
    List<User> getAllUser();

    @Query("select * from user where uid = :uid ")
    List<User> getUserByUid(Long uid);

    @Query("select * from user where first_name like :firstName " +
            " and last_name like :lastName")
    List<User> getUserByName(String firstName, String lastName);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Delete
    void delete(User user);
}
