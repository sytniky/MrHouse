package com.sytniky.mrhouse.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by yuriy on 15.10.16.
 */
public class DBContracts {
    public static final String DB_NAME = "MrHouse";
    public static final String AUTHORITY = "com.sytniky.mrhouse.database.MrHouse";
    public static final int DB_VERSION = 1;

    public static final class Cities implements BaseColumns {
        public static final String TABLE_NAME = "cities";
        private static final String SCHEME = "content://";
        private static final String PATH_CITIES = "/cities";
        private static final String PATH_CITIES_ID = "/cities/";
        public static final int CITIES_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_CITIES);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_CITIES_ID);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

        public static final String COLUMN_TITLE = "title";

        public static final String[] DEFAULT_PROJECTION = new String[]{
                _ID, COLUMN_TITLE
        };

        public static final String DEFAULT_SORT_ORDER = COLUMN_TITLE + " ASC";
    }

    public static final class Services implements BaseColumns {
        public static final String TABLE_NAME = "services";
        private static final String SCHEME = "content://";
        private static final String PATH_SERVICES = "/services";
        private static final String PATH_SERVICES_ID = "/services/";
        public static final int SERVICES_ID_PATH_POSITION = 1;
        public static final Uri CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + PATH_SERVICES);
        public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_SERVICES_ID);
        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TABLE_NAME;
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TABLE_NAME;

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_THUMB = "thumb";

        public static final String[] DEFAULT_PROJECTION = new String[]{
                _ID, COLUMN_TITLE, COLUMN_THUMB
        };

        public static final String DEFAULT_SORT_ORDER = COLUMN_TITLE + " ASC";
    }
}
