package pri.zxw.library.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import pri.zxw.library.db.DatabaseContext;
import pri.zxw.library.db.SQLHelper;
import pri.zxw.library.entity.AppPropertyInfo;
import pri.zxw.library.entity.User;
import pri.zxw.library.tool.ClearActivityTool;
import pri.zxw.library.tool.DeviceTool;
import pri.zxw.library.tool.ImgLoad.ImageLoadTool;
import pri.zxw.library.tool.SharedSaveUserTool;

/**
 * 功能
 * Createdy 张相伟
 * 2016/11/5.
 */

public class BaseApp extends AppPropertyInfo{
    private static BaseApp mAppApplication;
    public static  String um_device_token="";
    private SQLHelper sqlHelper;
    @Override
    public void onCreate() {
        // TODO onCreate
        super.onCreate();
//        CrashHandler catchHandler = CrashHandler.getInstance();
//        catchHandler.init(getApplicationContext());
        ImageLoadTool.imageLoadConfig(getApplicationContext());
        mAppApplication = this;
        if(user==null)
            user=new User();
            user.getUser();
    }

    /** 获取Application */
    public static BaseApp getInstance() {
        return mAppApplication;
    }

    /** 获取数据库Helper */
    public SQLHelper getSQLHelper() {
        if (sqlHelper == null) {
            DatabaseContext dbContext = new DatabaseContext(mAppApplication);
            sqlHelper = new SQLHelper(dbContext);
        }
        return sqlHelper;
    }
    public String  getUm_device_token()
    {
        if(um_device_token==null)
        {
            um_device_token=   DeviceTool.getDeviceId(this);
        }
        return um_device_token;
    }



    @Override
    public void onTerminate() {
        // TODO Auto-generated method stub
        if (sqlHelper != null)
            sqlHelper.close();
        super.onTerminate();
        // 整体摧毁的时候调用这个方法
    }

    public void exit() {
        try {
            clearActityNotServer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeUpdateService();
            // ActivityManager am = (ActivityManager)
            // this.getSystemService(ACTIVITY_SERVICE);
            // am.restartPackage(this.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

    }

    /**
     * 清空所有activity
     */
    public void clearActityNotServer() {
        ClearActivityTool.clearActityNotServer(activityList);
    }

    /**
     * 关闭其他activity，留下一个特定的activity
     *
     */
    public void ClearOtherActityLeavingAct(Class<? extends Activity> actClass) {
        ClearActivityTool.ClearOtherActityLeavingAct(activityList, actClass);
    }


    public void addActivity(Activity act) {
        activityList.add(act);
    }
    public List<Activity> getActivitys() {
        return activityList;
    }

    Intent updateIntent;

    public void saveUpdateService(Intent updateIntent) {
        this.updateIntent = updateIntent;
    }

    public void closeUpdateService() {
        if (updateIntent != null)
            mAppApplication.stopService(updateIntent);
    }

    public void saveSharedpreferences(Activity act, User user) {
        SharedSaveUserTool.saveSharedpreferences(act,user);
    }


    public void deleteSharedpreferences(Activity act) {
        SharedSaveUserTool.deleteSharedpreferences(act);
    }



    private boolean isAppOnForeground() {
        ActivityManager activityManager =(ActivityManager) getApplicationContext().getSystemService(
                Context.ACTIVITY_SERVICE);
        String packageName =getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo>appProcesses = activityManager.getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }
        return false;
    }

}
