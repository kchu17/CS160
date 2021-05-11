package edu.sjsu.android.cs_160_project;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({OrderStatus.ORDERED, OrderStatus.IN_PROGRESS, OrderStatus.DONE})
@Retention(RetentionPolicy.SOURCE)
public @interface OrderStatus {
    int ORDERED = 10;
    int IN_PROGRESS=11;
    int DONE = 12;
}
