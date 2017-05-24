package pri.zxw.library.tool;

import android.app.Activity;
import android.content.ContentValues;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pri.zxw.library.base.BaseApp;
import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.entity.User;
import pri.zxw.library.tool.SharedpreferencesTool;

/**
 * Created by Administrator on 2016/3/30.
 */
public class SharedSaveUserTool {
    public static void saveSharedpreferences(Activity act, User user) {
        String oauthType = user.getLoginType();
        String openid = null;
        if (oauthType == null)
            oauthType = "0";
        if (oauthType.equals("1"))
            openid = user.getQqopenid();
        else if (oauthType.equals("2"))
            openid = user.getSinaopenid();
        else if (oauthType.equals("3"))
            openid = user.getWxopenid();
        SharedpreferencesTool.addDataToPreferences(act,
                SharedpreferencesTool.USER_CONFIG_FILE,
                SharedpreferencesTool.USER_CONFIG_FILE_OPENID, openid);
        BaseApp.getInstance().setUser(user);
    }
    public static void saveUserJsonAll(Activity act,User user,JsonStrHistoryDao dao)
    {
        if(dao.getCache("USER_COLLECT_CODE"+user.getId())==null)
        {

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

    }



}
