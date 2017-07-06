package com.zxw.giftbook.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxw.giftbook.Activity.entitiy.GifttypeEntity;
import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.config.NetworkConfig;
import com.zxw.giftbook.myinterface.IDataMapUtilCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.Call;
import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.myinterface.INotHandlerReturn;
import pri.zxw.library.tool.JsonParse;
import pri.zxw.library.tool.ProgressDialogTool;
import pri.zxw.library.tool.ServicesTool;

/**
 * 功能  存储 部分类型信息
 * Createdy 张相伟
 * 2017/6/18.
 */

public class DataMapUtil {
    /**
     * 组成员信息
     */
    private static Map<String,SidekickergroupEntity> groupMember=new TreeMap<>();
    /**
     * 组成员信息
     */
    private static Map<String,GifttypeEntity> giftTypeMap=new TreeMap<>();
    public static Map<String, SidekickergroupEntity> getGroupMember() {
        return groupMember;
    }

    /**
     * 礼金类型
     * @return
     */
    public static Map<String, GifttypeEntity> getGiftTypeMap() {
        return giftTypeMap;
    }

    /**
     * 解析组成员信息
     * @param context
     * @param jsonStr
     * @return
     */
    public static Map<String,SidekickergroupEntity> handlerJson(Context context,String jsonStr)
    {
        try {
            if(jsonStr==null||jsonStr.trim().length()==0)
                return null;
            JsonStrHistoryDao dao=new JsonStrHistoryDao();
            dao.addCache("allTypeJson",jsonStr);
            JSONObject jsonObject=new JSONObject(jsonStr);
            JSONArray jsonArray=jsonObject.getJSONArray("sidekickerGroups");
            String oldId="";
            int num=0;
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject object= jsonArray.getJSONObject(i);
                SidekickergroupEntity sidekickergroupEntity=null;
                if(oldId.equals(object.getString("groupid")))
                {
                    sidekickergroupEntity=groupMember.get(oldId);
                }else
                {
                    num=0;
                    sidekickergroupEntity=new SidekickergroupEntity();
                    sidekickergroupEntity.setId(object.getString("groupid"));
                    sidekickergroupEntity.setGroupmembersnum(object.getInt("groupmembersnum"));
                    sidekickergroupEntity.setGroupname(object.getString("groupname"));
                    sidekickergroupEntity.setCreateDate(object.getLong("createdate"));
                    oldId=object.getString("groupid");
                    groupMember.put(oldId,sidekickergroupEntity);
                }
                GroupmemberEntity groupmemberEntity=new GroupmemberEntity();
                if(object.get("id")==null||object.getString("id").equals("null"))//id为空说明该组下没有成员
                    continue;
                num++;
                 groupmemberEntity.setId(object.getString("id"));
                groupmemberEntity.setGroupmember(object.getString("groupmember"));
                if(object.get("affiliatedperson")!=null&&!object.getString("affiliatedperson").equals("null"))
                      groupmemberEntity.setAffiliatedperson(object.getString("affiliatedperson"));
                if(object.get("affiliatedpersonid")!=null&&!object.getString("affiliatedpersonid").equals("null"))
                        groupmemberEntity.setAffiliatedpersonid(object.getString("affiliatedpersonid"));
                groupmemberEntity.setGourpid(object.getString("groupid"));
                groupmemberEntity.setGourpName(object.getString("groupname"));
                if(object.get("memberphone")!=null&&!object.getString("memberphone").equals("null"))
                       groupmemberEntity.setMemberphone(object.getString("memberphone"));
                if(object.get("totalmoney")!=null&&!object.getString("totalmoney").equals("null"))
                     groupmemberEntity.setTotalmoney(object.getDouble("totalmoney"));
                else
                    groupmemberEntity.setTotalmoney(0d);
                if(sidekickergroupEntity.getGroupmemberList()==null)
                    sidekickergroupEntity.setGroupmemberList(new ArrayList<GroupmemberEntity>());
                sidekickergroupEntity.setGroupmembersnum(num);
                List<GroupmemberEntity> list= sidekickergroupEntity.getGroupmemberList();
                list.add(groupmemberEntity);
            }

            Type giftType=new TypeToken<List<GifttypeEntity>>(){}.getType();
            Gson gson=new Gson();
            List<GifttypeEntity> list=  gson.fromJson(jsonObject.getString("gifttypes"),giftType);
            for(GifttypeEntity gifttypeEntity:list)
            {
                giftTypeMap.put(gifttypeEntity.getId(),gifttypeEntity);
            }
            //相关的请帖1=婚礼，2=百日宴，3=寿宴，4乔迁
            GifttypeEntity gifttypeEntity=new GifttypeEntity();
            gifttypeEntity.setId("1");
            gifttypeEntity.setTypename("婚礼");
            giftTypeMap.put("1",gifttypeEntity);

            GifttypeEntity gifttypeEntity2=new GifttypeEntity();
            gifttypeEntity2.setId("2");
            gifttypeEntity2.setTypename("百日宴");
            giftTypeMap.put("2",gifttypeEntity2);

            GifttypeEntity gifttypeEntity3=new GifttypeEntity();
            gifttypeEntity3.setId("3");
            gifttypeEntity3.setTypename("寿宴");
            giftTypeMap.put("3",gifttypeEntity3);

            GifttypeEntity gifttypeEntity4=new GifttypeEntity();
            gifttypeEntity4.setId("4");
            gifttypeEntity4.setTypename("乔迁");
            giftTypeMap.put("4",gifttypeEntity4);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return groupMember;
    }
    /**
     * 获取组成员信息
     * @param context
     * @return
     */
    public static  Map<String, SidekickergroupEntity> getAllTypeData( Activity context)
    {
        return getAllTypeData(context,true,false,null);
    }
    /**
     * 获取组成员信息
     * @param context
     * @return
     */
    public static  Map<String, SidekickergroupEntity> getAllTypeData( Activity context,IDataMapUtilCallback callback)
    {
        return getAllTypeData(context,true,false,callback);
    }

    /**
     * 获取组成员信息
     * @param context
     * @param isShowDialog 是否显示加载状态
     * @param isClearUp 是否清理map
     * @param callback
     * @return
     */
    public static  Map<String, SidekickergroupEntity> getAllTypeData(final Activity context,boolean isShowDialog,final boolean isClearUp, final IDataMapUtilCallback callback)
    {
        if(groupMember.size()>0&&!isClearUp)
        {
            return groupMember;
        }else {
            if(callback!=null&&isShowDialog)
                ProgressDialogTool.getInstance(context).showDialog("加载中....");
            AppServerTool servicesTool = new AppServerTool(NetworkConfig.api_url, context, null);
            Map<String, String> param = ComParamsAddTool.getParam();
            param.put("userid", FtpApplication.getInstance().getUser().getId());
            servicesTool.notHandlerReturn("apiAllTypeCtrl.do?getAll", param, new INotHandlerReturn() {
                @Override
                public void onSuccess(Message ret, int id) {
                    Map<String, String> map = (Map<String, String>) ret.obj;
                    if(map!=null&&isClearUp)
                    {
                        groupMember.clear();
                        giftTypeMap.clear();
                    }
                    handlerJson(context, map.get(JsonParse.CONTEXT));
                    if(callback!=null)
                    {
                        if(ret.arg1==1)
                            callback.onSuccess(true);
                        else
                            callback.onSuccess(false);
                    }
                    ProgressDialogTool.getInstance(context).dismissDialog();
                }

                @Override
                public void onFailure(Call call, Exception error, int requestCode) {
                    if(callback!=null)
                        callback.onFailure(true);
                    ProgressDialogTool.getInstance(context).dismissDialog();
                }
            });
            return null;
        }
    }
    public  static void editItem(SidekickergroupEntity sidekickergroupEntity)
    {
            groupMember.put(sidekickergroupEntity.getId(),sidekickergroupEntity);
    }
    public  static void editChildItem(String parentId,GroupmemberEntity groupmemberEntity)
    {
        SidekickergroupEntity sidekickergroupEntity=  groupMember.get(parentId);
        if(sidekickergroupEntity!=null)
        {
            sidekickergroupEntity.getGroupmemberList().add(groupmemberEntity);
        }
    }
    public  static void delItem(SidekickergroupEntity sidekickergroupEntity)
    {
        groupMember.remove(sidekickergroupEntity.getId());
    }
    public  static void delChildItem(String parentId,GroupmemberEntity groupmemberEntity)
    {
        SidekickergroupEntity sidekickergroupEntity=  groupMember.get(parentId);
        if(sidekickergroupEntity!=null)
        {
            for (GroupmemberEntity entity:sidekickergroupEntity.getGroupmemberList())
            {
                if(entity.getId().equals(groupmemberEntity.getId()))
                {
                    sidekickergroupEntity.getGroupmemberList().remove(entity);
                    break;
                }
            }

        }
    }
}
