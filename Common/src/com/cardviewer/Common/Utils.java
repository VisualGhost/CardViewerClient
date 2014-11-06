package com.cardviewer.Common;

/**
 * Created by Android on 26.10.14.
 */
public class Utils {

    public final static String AUTHORITY = "authority";

    public static final String COLUMN_NAME = "text";
    public static final String COLUMN_IMAGE = "imageUri";
    public static final String COLUMN_AUDIO = "audioUri";
    public static final String COLUMN_LEVEL_NUMBER = "level_number";
    public static final String COLUMN_UNIT_NUMBER = "unit_number";
    public static final String COLUMN_UNIT_NAME = "unit_name";
    public static final String COLUMN_LESSON_NUMBER = "lesson_number";

    public static final String COM_CARDVIEWER_PROVIDER = "com.cardviewer.provider.";
    private Utils(){}

    public static boolean isCorrectProvider(String authority){
        return authority.contains(COM_CARDVIEWER_PROVIDER);
    }
}
