package com.cardviewer.client.navigation;

import android.content.Context;
import com.cardviewer.client.R;

/**
 * Created by Android on 06.12.14.
 */
public class LessonItem implements Comparable<LessonItem> {
    private String mAuthority;
    private String level;
    private String unit;
    private String lesson;

    final static String L = "L";
    final static String U = "U";

    LessonItem(String authority, String level, String unit, String lesson) {
        mAuthority = authority;
        this.level = level;
        this.unit = unit;
        this.lesson = lesson;
    }

    public String getLevel() {
        return level.replace(L, "");
    }

    public String getUnit() {
        return unit.replace(U, "");
    }

    public String getLesson() {
        return lesson.replace(L, "");
    }

    public String getAuthority() {
        return mAuthority;
    }

    @Override
    public int compareTo(LessonItem o) {
        if (Integer.parseInt(replace(level, "L")) > Integer.parseInt(replace(o.level, "L"))) {
            return 1;
        }
        if (Integer.parseInt(replace(level, "L")) < Integer.parseInt(replace(o.level, "L"))) {
            return -1;
        }
        if (Integer.parseInt(replace(unit, "U")) > Integer.parseInt(replace(o.unit, "U"))) {
            return 1;
        }
        if (Integer.parseInt(replace(unit, "U")) < Integer.parseInt(replace(o.unit, "U"))) {
            return -1;
        }
        if (Integer.parseInt(replace(lesson, "L")) > Integer.parseInt(replace(o.lesson, "L"))) {
            return 1;
        }
        if (Integer.parseInt(replace(lesson, "L")) < Integer.parseInt(replace(o.lesson, "L"))) {
            return -1;
        }
        return 0;
    }

    private String replace(String income, String symbols) {
        return income.replace(symbols, "");
    }
}
