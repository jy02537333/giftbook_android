package pri.zxw.library.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pri.zxw.library.base.BaseApp;
import pri.zxw.library.entity.KVStringVString;

public class JsonStrHistoryDao {
    public static final String TABLE = "JsonStrTable";
    public static final String ID = "id";
    public static final String Url_key = "key";
    public static final String JSON_STR = "JsonStr";
    public SQLHelper helper = null;

    public JsonStrHistoryDao() {
        helper = new SQLHelper(BaseApp.getInstance());
    }

    public boolean addCache(String url, String jsonStr) {
        boolean flag = false;
        SQLiteDatabase database = null;
        long id = -1;
        try {
            database = helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Url_key, url);
            values.put(JSON_STR, jsonStr);
            id = database.insert(TABLE, null, values);
            flag = (id != -1 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }
    public boolean addCaches(List<ContentValues> values) {
        boolean flag = false;
        SQLiteDatabase database = null;
        long id = -1;
        try {
            database = helper.getWritableDatabase();
            for (int i = 0; i <values.size() ; i++) {
                id = database.insert(TABLE, null, values.get(i));
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    public boolean updateUserHistory(String url, String jsonStr) {
        String value = getCache(url);
        if (value == null || value.trim().length() == 0) {
            return addCache(url, jsonStr);
        } else {
            ContentValues values = new ContentValues();
            values.put(Url_key, url);
            values.put(JSON_STR, jsonStr);
            return updateCache(values,Url_key+ "=?", new String[]{url});
        }
    }

    public boolean deleteCache(String whereClause, String[] whereArgs) {
        // TODO Auto-generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            count = database.delete(TABLE, whereClause, whereArgs);
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    public boolean updateCache(ContentValues values, String whereClause,
                               String[] whereArgs) {
        // TODO Auto-generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            count = database.update(TABLE, values, whereClause, whereArgs);
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }
    public boolean updateCaches(List<KVStringVString> values) {
        // TODO Auto-generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            for (int i = 0; i <values.size() ; i++) {
                ContentValues value=new ContentValues();
                value.put(Url_key,values.get(i).key);
                value.put(JSON_STR,values.get(i).value);
                count = database.update(TABLE, value, Url_key+"=?", new String[]{values.get(i).key});
            }
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    public Map<String, String> viewCache(String selection,
                                         String[] selectionArgs) {
        // TODO Auto-generated method stub
        SQLiteDatabase database = null;
        Cursor cursor = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(true, TABLE, null, selection,
                    selectionArgs, null, null, null, null);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor
                            .getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return map;
    }

    public String getCache(String urlKey) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        String jsonString = "";
        try {
             database =helper.getReadableDatabase();
            cursor = database.query(TABLE, null, Url_key + "= ?",
                    new String[]{String.valueOf(urlKey)}, null, null, null);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                for (int i = 0; i < cols_len; i++) {
                    jsonString = cursor.getString(cursor
                            .getColumnIndex(JSON_STR));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return jsonString;
    }

    public List<Map<String, String>> listCache(String selection,
                                               String[] selectionArgs) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(false, TABLE, null, selection,
                    selectionArgs, null, null, null, null);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cols_len; i++) {

                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor
                            .getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
                list.add(map);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return list;
    }

    public void clearFeedTable() {
        String sql = "DELETE FROM " + TABLE + ";";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
        revertSeq();
    }

    private void revertSeq() {
        String sql = "update sqlite_sequence set seq=0 where name='" + TABLE
                + "'";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
    }
}
