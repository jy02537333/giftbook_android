package com.zxw.giftbook.utils;

import android.app.Activity;
import android.content.ContentValues;

import com.zxw.giftbook.FtpApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.entity.User;
import pri.zxw.library.tool.SharedpreferencesTool;

/**
 * Created by Administrator on 2016/3/30.
 */
public class SharedSaveUserTool {
    public static void saveSharedpreferences(Activity act, User user) {
    }
    public static void getUserJson(Activity act,User user,JsonStrHistoryDao dao)
    {
        String id=user.getId();
        FtpApplication.setCollectsStr(dao.getCache("USER_COLLECT_CODE" + id));
        FtpApplication.setPraisesStr(dao.getCache("USER_PRAISE_CODE" + id));
        FtpApplication.setNewsPraiseStr(dao.getCache("USER_NEWS_PRAISE_CODE" + id));
        FtpApplication.setCustomNewsCollects(dao.getCache("USER_DISCLOSE_NEWS_COLLECT_CODE" + id));
        FtpApplication.setCustomNewsPraises(dao.getCache("USER_DISCLOSE_NEWS_PRAISE_CODE" + id));
        FtpApplication.setCustomNewsCommentPraises(dao.getCache("USER_DISCLOSE_NEWS_COMMENT_PRAISE_CODE" + id));

    }
    public static void saveUserJson(Activity act,User user,JsonStrHistoryDao dao)
    {
        saveCollect(user, dao, FtpApplication.getInstance().getCollectsStr());
        saveCommentPraise(user, dao, FtpApplication.getNewsPraiseStr());
        saveNewsPraise(user, dao, FtpApplication.getPraisesStr());
       saveDiscloseNewsCollect(user, dao, FtpApplication.getCustomNewsCollects());
       saveDiscloseNewsPraise(user, dao, FtpApplication.getCustomNewsPraises());
       saveDiscloseNewsCommentPraise(user, dao, FtpApplication.getCustomNewsCommentPraises());
    }
    public static void saveUserJsonAll(Activity act,User user,JsonStrHistoryDao dao)
    {
        if(dao.getCache("USER_COLLECT_CODE"+user.getId())==null)
        {
            List<ContentValues> values=new ArrayList<>();
            ContentValues values1=new ContentValues( );
            values1.put(JsonStrHistoryDao.Url_key,"USER_COLLECT_CODE"+user.getId());
            values1.put(JsonStrHistoryDao.JSON_STR, FtpApplication.getInstance().getCollectsStr());
            ContentValues values2=new ContentValues( );
            values2.put(JsonStrHistoryDao.Url_key,"USER_PRAISE_CODE"+user.getId());
            values2.put(JsonStrHistoryDao.JSON_STR, FtpApplication.getPraisesStr());
            ContentValues values3=new ContentValues( );
            values3.put(JsonStrHistoryDao.Url_key,"USER_NEWS_PRAISE_CODE"+user.getId());
            values3.put(JsonStrHistoryDao.JSON_STR, FtpApplication.getInstance().getNewsPraiseStr());
            ContentValues values4=new ContentValues( );
            values4.put(JsonStrHistoryDao.Url_key,"USER_DISCLOSE_NEWS_COLLECT_CODE"+user.getId());
            values4.put(JsonStrHistoryDao.JSON_STR, FtpApplication.getInstance().getCustomNewsCollects());
            ContentValues values5=new ContentValues( );
            values5.put(JsonStrHistoryDao.Url_key,"USER_DISCLOSE_NEWS_PRAISE_CODE"+user.getId());
            values5.put(JsonStrHistoryDao.JSON_STR, FtpApplication.getInstance().getCustomNewsPraises());
            ContentValues values6=new ContentValues( );
            values6.put(JsonStrHistoryDao.Url_key,"USER_DISCLOSE_NEWS_COMMENT_PRAISE_CODE"+user.getId());
            values6.put(JsonStrHistoryDao.JSON_STR, FtpApplication.getInstance().getCustomNewsCommentPraises());
            dao.addCaches(values);
        }else
        {

        }
    }
    public static void saveCollect(User user, JsonStrHistoryDao dao, String collectJson)
    {
        dao.updateUserHistory("USER_COLLECT_CODE"+user.getId(),collectJson);
    }
    /**
     * 新闻评论点赞
     * @param user
     * @param dao
     * @param comentPraiseJson
     */
    public static void saveCommentPraise(User user,JsonStrHistoryDao dao,String comentPraiseJson)
    {
        dao.updateUserHistory("USER_PRAISE_CODE"+user.getId(),comentPraiseJson);
    }
    public static void saveNewsPraise(User user,JsonStrHistoryDao dao,String newsPraiseJson)
    {
        dao.updateUserHistory("USER_NEWS_PRAISE_CODE"+user.getId(),newsPraiseJson);
    }
    public static void saveDiscloseNewsCollect(User user,JsonStrHistoryDao dao,String discloseNewsCollectJson)
    {
        dao.updateUserHistory("USER_DISCLOSE_NEWS_COLLECT_CODE"+user.getId(),discloseNewsCollectJson);
    }
    public static void saveDiscloseNewsPraise(User user,JsonStrHistoryDao dao,String discloseNewsPraiseJson)
    {
        dao.updateUserHistory("USER_DISCLOSE_NEWS_PRAISE_CODE"+user.getId(),discloseNewsPraiseJson);
    }
    public static void saveDiscloseNewsCommentPraise(User user,JsonStrHistoryDao dao,String discloseNewsComentPraiseJson)
    {
        dao.updateUserHistory("USER_DISCLOSE_NEWS_COMMENT_PRAISE_CODE"+user.getId(),discloseNewsComentPraiseJson);
    }
    public static void deleteSharedpreferences(Activity act) {
        HashMap<String, String> userMap = new HashMap<String, String>();
        userMap.put(SharedpreferencesTool.USER_CONFIG_FILE_ACCOUNT, null);
        userMap.put(SharedpreferencesTool.USER_CONFIG_FILE_NICkNAME, null);
        userMap.put(SharedpreferencesTool.USER_CONFIG_FILE_GENDER,null);
        userMap.put(SharedpreferencesTool.USER_CONFIG_FILE_HEAD, null);
        userMap.put(SharedpreferencesTool.USER_CONFIG_FILE_ID, null);
        userMap.put(SharedpreferencesTool.USER_CONFIG_FILE_PASSWORD, null);
        userMap.put(SharedpreferencesTool.USER_CONFIG_FILE_OAUTH_TYPE, null);
        SharedpreferencesTool.addDataToPreferences(act, SharedpreferencesTool.USER_CONFIG_FILE, userMap);
//        SharedpreferencesTool.addDataToPreferences(act,
//                SharedpreferencesTool.USER_CONFIG_FILE,
//                SharedpreferencesTool.USER_CONFIG_FILE_ACCOUNT, "");
//        SharedpreferencesTool.addDataToPreferences(act,
//                SharedpreferencesTool.USER_CONFIG_FILE,
//                SharedpreferencesTool.USER_CONFIG_FILE_PASSWORD, "");
//        SharedpreferencesTool.addDataToPreferences(act,
//                SharedpreferencesTool.USER_CONFIG_FILE,
//                SharedpreferencesTool.USER_CONFIG_FILE_OPENID, "");
//        SharedpreferencesTool.addDataToPreferences(act,
//                SharedpreferencesTool.USER_CONFIG_FILE,
//                SharedpreferencesTool.USER_CONFIG_FILE_OAUTH_TYPE, "");
        FtpApplication.getInstance().setUser(null);
    }



}
