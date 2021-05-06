package edu.sjsu.android.cs_160_project;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef ({UserType.REGULAR_USER, UserType.ADMIN_USER})
@Retention(RetentionPolicy.SOURCE)
@interface UserType{
    int REGULAR_USER = 5;
    int ADMIN_USER = 6;
}