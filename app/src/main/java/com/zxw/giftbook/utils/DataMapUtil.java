package com.zxw.giftbook.utils;

import android.content.Context;
import android.os.Message;

import com.zxw.giftbook.Activity.entitiy.GroupmemberEntity;
import com.zxw.giftbook.Activity.entitiy.SidekickergroupEntity;
import com.zxw.giftbook.FtpApplication;
import com.zxw.giftbook.config.NetworkConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import pri.zxw.library.myinterface.INotHandlerReturn;
import pri.zxw.library.tool.JsonParse;
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
    private static Map<String,SidekickergroupEntity> groupMember=new HashMap<>();

    public static Map<String, SidekickergroupEntity> getGroupMember() {
        return groupMember;
    }

    /**
     * 解析组成员信息
     * @param context
     * @param jsonStr
     * @return
     */
    public static Map<String,SidekickergroupEntity> handlerGroupMember(Context context,String jsonStr)
    {
        try {
            JSONArray jsonArray=new JSONArray(jsonStr);
            String oldId="";
            for (int i=0;i<jsonArray.length();i++) {
                JSONObject object= jsonArray.getJSONObject(i);
                SidekickergroupEntity sidekickergroupEntity=null;
                if(oldId.equals(object.getString("groupid")))
                {
                    sidekickergroupEntity=groupMember.get(oldId);
                }else
                {
                    sidekickergroupEntity=new SidekickergroupEntity();
                    sidekickergroupEntity.setId(object.getString("groupid"));
                    sidekickergroupEntity.setGroupmembersnum(object.getInt("groupmembersnum"));
                    sidekickergroupEntity.setGroupname(object.getString("groupname"));
                    sidekickergroupEntity.setCreateDate(object.getLong("createdate"));
                    oldId=object.getString("groupid");
                    groupMember.put(oldId,sidekickergroupEntity);
                }
                GroupmemberEntity groupmemberEntity=new GroupmemberEntity();
                groupmemberEntity.setId(object.getString("id"));
                groupmemberEntity.setAffiliatedperson(object.getString("affiliatedperson"));
                groupmemberEntity.setAffiliatedpersonid(object.getString("affiliatedperson"));
                groupmemberEntity.setGourpid(object.getString("groupid"));
                groupmemberEntity.setGourpName(object.getString("groupname"));
                groupmemberEntity.setMemberphone(object.getString("memberphone"));
                groupmemberEntity.setTotalmoney(object.getDouble("totalmoney"));
                List<GroupmemberEntity> list= sidekickergroupEntity.getGroupmemberList();
                list.add(groupmemberEntity);
            }
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
    public static  Map<String, SidekickergroupEntity> getGroupMemberData(final Context context)
    {
        if(groupMember.size()>0)
        {
            return groupMember;
        }else {
            AppServerTool servicesTool = new AppServerTool(NetworkConfig.api_url, context, null);
            Map<String, String> param = ComParamsAddTool.getParam();
            param.put("userid", FtpApplication.getInstance().getUser().getId());
            servicesTool.notHandlerReturn("apiGroupmemberCtrl.do?getFullMember", param, new INotHandlerReturn() {
                @Override
                public void onSuccess(Message ret, int id) {
                    Map<String, String> map = (Map<String, String>) ret.obj;
                    handlerGroupMember(context, map.get(JsonParse.CONTEXT));
                }

                @Override
                public void onFailure(Call call, Exception error, int requestCode) {

                }
            });
            return null;
        }
    }
}
