package com.neil.test.model.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author zhongnan
 * @time 2021/7/19 10:53
 *
 */
@Entity
public class User {
    @PrimaryKey
    private Long uid;
    @ColumnInfo(name = "first_name")
    private String firstName;
    @ColumnInfo(name = "last_name")
    private String lastName;
}
