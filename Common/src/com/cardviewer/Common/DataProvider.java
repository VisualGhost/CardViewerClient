package com.cardviewer.Common;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;
import com.cardviewer.Common.Card;
import com.cardviewer.Common.Lesson;
import com.cardviewer.Common.Parser;
import com.cardviewer.Common.Utils;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * DataProvider
 */
public abstract class DataProvider extends ContentProvider {

    private Lesson mLesson;
    private final String LOG = "DataProvider";

    @Override
    public boolean onCreate() {
        mLesson = parseFile(getContext(), "content.xml").getLesson();
        return true;
    }

    private Parser parseFile(Context context, String fileName) {
        InputStream inputStream;
        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            inputStream = context.getAssets().open(fileName);
            Parser parser = new Parser();
            SAXParser p = factory.newSAXParser();
            p.parse(new InputSource(inputStream), parser);
            return parser;
        } catch (Exception e) {
            Log.e(LOG, e.toString());
            return null;
        }
    }

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection, final String[] selectionArgs, final String sortOrder) {
        List<Card> cards = mLesson.getCards();
        if (cards == null) {
            return null;
        }
        MatrixCursor cursor = new MatrixCursor(projection);
        for (int i = 0; i < cards.size(); i++) {
            MatrixCursor.RowBuilder builder = cursor.newRow();
            for (String column : projection) {
                Card card = cards.get(i);
                if (column.equals("_id")) {
                    builder.add(i);
                } else if (column.equals(Utils.COLUMN_NAME)) {
                    builder.add(card.getText());
                } else if (column.equals(Utils.COLUMN_IMAGE)) {
                    builder.add(Uri.withAppendedPath(getAuthority(), card.getImagePath()));
                } else if (column.equals(Utils.COLUMN_AUDIO)) {
                    builder.add(Uri.withAppendedPath(getAuthority(), card.getAudioPath()));
                } else if (column.equals(Utils.COLUMN_LEVEL_NUMBER)) {
                    builder.add(mLesson.getLevelNumber());
                } else if (column.equals(Utils.COLUMN_UNIT_NUMBER)) {
                    builder.add(mLesson.getUnitNumber());
                } else if (column.equals(Utils.COLUMN_UNIT_NAME)) {
                    builder.add(mLesson.getUnitName());
                } else if (column.equals(Utils.COLUMN_LESSON_NUMBER)) {
                    builder.add(mLesson.getLessonNumber());
                }
            }
        }
        return cursor;
    }

    @Override
    public String getType(final Uri uri) {
        return null;
    }

    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        throw new UnsupportedOperationException(getDetailMessage());
    }

    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        throw new UnsupportedOperationException(getDetailMessage());
    }

    @Override
    public int update(final Uri uri, final ContentValues values, final String selection, final String[] selectionArgs) {
        throw new UnsupportedOperationException(getDetailMessage());
    }

    private String getDetailMessage() {
        return getContext() != null ? getContext().getString(com.cardviewer.Common.R.string.read_only) : "";
    }

    @Override
    public AssetFileDescriptor openAssetFile(final Uri uri, final String mode) throws FileNotFoundException {
        String encodedPath = uri.getEncodedPath();
        String file = encodedPath != null ? encodedPath.substring(1) : "";
        AssetFileDescriptor assetFileDescriptor;
        if (getContext() == null) return null;
        AssetManager assetManager = getContext().getAssets();
        try {
            assetFileDescriptor = assetManager.openFd(file);
        } catch (IOException e) {
            Log.e(LOG, e.toString());
            return null;
        }

        return assetFileDescriptor;
    }

    public abstract Uri getAuthority();
}
