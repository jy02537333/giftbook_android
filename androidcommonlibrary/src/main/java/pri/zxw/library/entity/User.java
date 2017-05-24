package pri.zxw.library.entity;

import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;

import pri.zxw.library.base.BaseApp;
import pri.zxw.library.base.BaseEntity;
import pri.zxw.library.db.JsonStrHistoryDao;
import pri.zxw.library.tool.DeviceTool;
import pri.zxw.library.tool.base64.Base64Tools;
import pri.zxw.library.tool.base64.Base64Utils;
import pri.zxw.library.tool.base64.MD5EncodeTool;
import pri.zxw.library.tool.base64.RSAEncrypt;

/**
 * @author 张相伟
 * @version 1
 * @className 用户
 * @function 类功能
 * @createDate 2015-1-13
 * @upadteMemter 2015-1-13
 * @ChangedBy 张相伟
 * @ChangedContent 修改内容
 */
public class User extends BaseEntity implements Cloneable {
    public Class<User> getMyClass() {
        return User.class;
    }

    /**
     * 自动编号
     */
    private String id;
    /**
     * 手机号码
     */
    private String userphone;
    private Integer sex;
    private String portraitThumbnail;
    /**
     * 用户头像
     */
    private String portrait;
    /**
     * 真实姓名
     */
    private String username;
    /**
     * 登录账号
     */
    private String loginname;
    /**
     * 登录密码
     */
    private String loginpassword;
    /**
     * 电子邮箱
     */
    private String useremail;
    /**
     * 所属省Id
     */
    private Integer provinceid;
    /**
     * 所属省
     */
    private String province;
    /**
     * 所属市Id
     */
    private Integer cityid;
    /**
     * 所属市
     */
    private String city;
    /**
     * 所属区/县Id
     */
    private Integer districtid;
    /**
     * 所属区/县
     */
    private String district;
    /**
     * 详细地址
     */
    private String detailaddress;
    /**
     * 使用状态(1启用2停用）
     */
    private Integer loginflag;
    /**
     * qqopenid
     */
    private String qqopenid;
    /**
     * wxopenid
     */
    private String wxopenid;
    /**
     * sinaopenid
     */
    private String sinaopenid;
    /**
     * 创建时间
     */
    private Long createDate;
    /**
     * 创建人编号
     */
    private String createBy;
    /**
     * 创建人姓名
     */
    private String createName;
    /**
     * 更新时间
     */
    private Long updateDate;
    /**
     * 更新人编号
     */
    private String updateBy;
    /**
     * 更信任姓名
     */
    private String updateName;
    private String timestamp;
    private String decvices;
    /**
     * 登录类型
     */
    private String loginType;
    private String token;
    private Bitmap localHeadImgBitmap;

    public Bitmap getLocalHeadImgBitmap() {
        return localHeadImgBitmap;
    }

    public void setLocalHeadImgBitmap(Bitmap localHeadImgBitmap) {
        this.localHeadImgBitmap = localHeadImgBitmap;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * 登录类型
     */
    public String getLoginType() {
        return loginType;
    }

    /**
     * 登录类型
     */
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDecvices() {
        return decvices;
    }

    public void setDecvices(String decvices) {
        this.decvices = decvices;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPortraitThumbnail() {
        return portraitThumbnail;
    }

    public void setPortraitThumbnail(String portraitThumbnail) {
        this.portraitThumbnail = portraitThumbnail;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  自动编号
     */
    public String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  自动编号
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  手机号码
     */
    public String getUserphone() {
        return this.userphone;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  手机号码
     */
    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  用户头像
     */
    public String getPortrait() {
        return this.portrait;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  用户头像
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  真实姓名
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  真实姓名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  登录账号
     */
    public String getLoginname() {
        return this.loginname;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  登录账号
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  登录密码
     */
    public String getLoginpassword() {
        return this.loginpassword;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  登录密码
     */
    public void setLoginpassword(String loginpassword) {
        this.loginpassword = loginpassword;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  电子邮箱
     */
    public String getUseremail() {
        return this.useremail;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  电子邮箱
     */
    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  所属省Id
     */
    public Integer getProvinceid() {
        return this.provinceid;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  所属省Id
     */
    public void setProvinceid(Integer provinceid) {
        this.provinceid = provinceid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  所属省
     */
    public String getProvince() {
        return this.province;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  所属省
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  所属市Id
     */
    public Integer getCityid() {
        return this.cityid;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  所属市Id
     */
    public void setCityid(Integer cityid) {
        this.cityid = cityid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  所属市
     */
    public String getCity() {
        return this.city;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  所属市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  所属区/县Id
     */
    public Integer getDistrictid() {
        return this.districtid;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  所属区/县Id
     */
    public void setDistrictid(Integer districtid) {
        this.districtid = districtid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  所属区/县
     */
    public String getDistrict() {
        return this.district;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  所属区/县
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  详细地址
     */
    public String getDetailaddress() {
        return this.detailaddress;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  详细地址
     */
    public void setDetailaddress(String detailaddress) {
        this.detailaddress = detailaddress;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  使用状态(1启用2停用）
     */
    public Integer getLoginflag() {
        return this.loginflag;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  使用状态(1启用2停用）
     */
    public void setLoginflag(Integer loginflag) {
        this.loginflag = loginflag;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  qqopenid
     */
    public String getQqopenid() {
        return this.qqopenid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  qqopenid
     */
    public void setQqopenid(String qqopenid) {
        this.qqopenid = qqopenid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  wxopenid
     */
    public String getWxopenid() {
        return this.wxopenid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  wxopenid
     */
    public void setWxopenid(String wxopenid) {
        this.wxopenid = wxopenid;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  sinaopenid
     */
    public String getSinaopenid() {
        return this.sinaopenid;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  sinaopenid
     */
    public void setSinaopenid(String sinaopenid) {
        this.sinaopenid = sinaopenid;
    }

    /**
     * 方法: 取得Long
     *
     * @return: Long  创建时间
     */
    public Long getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置Long
     *
     * @param: Long  创建时间
     */
    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人编号
     */
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人编号
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人姓名
     */
    public String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人姓名
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得Long
     *
     * @return: Long  更新时间
     */
    public Long getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置Long
     *
     * @param: Long  更新时间
     */
    public void setUpdateDate(Long updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人编号
     */
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人编号
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更信任姓名
     */
    public String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更信任姓名
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String toSignString(Context context) {
        MD5EncodeTool md5EncodeTool = new MD5EncodeTool();
        String signStr = null;
        try {
           // String pwdStr = md5EncodeTool.encryption(loginpassword, MD5EncodeTool.ENCRYPT_KEY);
            if(!isExists())
                return null;
            String pwdStr = Base64Tools.getBase64(loginpassword);
            StringBuilder sb = new StringBuilder("{\"loginname\":\"");
            sb.append(loginname);
            sb.append("\",\"loginpassword\":\"");
            sb.append(pwdStr);
            sb.append("\",\"timestamp\":\"");
            sb.append(System.currentTimeMillis());
            sb.append("\",\"decvices\":\"");
            sb.append(DeviceTool.getDeviceId(context) + "\"}");

             signStr=Base64Tools.getBase64(sb.toString());
         //   signStr = md5EncodeTool.encryption(signStr, MD5EncodeTool.LOGIN_ENCRYPT_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signStr;
    }

    /**
     * 判断是否登录
     *
     * @return
     */
    public boolean isLogin(Context context) {
        if (BaseApp.getInstance().getUser() == null||BaseApp.getInstance().getUser().getId()==null) {
            JsonStrHistoryDao dao = new JsonStrHistoryDao();
            String userJson = dao.getCache("user");
           if(userJson==null||userJson.trim().length()==0)
               return false;
            Gson gson = new Gson();
            User user = gson.fromJson(userJson, this.getMyClass());
            if (user != null) {
                BaseApp.getInstance().setUser(user);
                return true;
            }else
            {
                return false;
            }
        }else if(BaseApp.getInstance().getUser().getId().trim().length()>0)
        {
            return true;
        }

        return false;
    }

    /**
     * 判断用户是否存在
     * @return
     */
    public boolean isExists()
    {
        if(loginpassword==null||loginpassword.trim().length()==0)
            return false;
        return true;
    }
    public void saveUser() {
        try {
            Gson gson = new Gson();
            String userJson = gson.toJson(this);
            JsonStrHistoryDao dao = new JsonStrHistoryDao();
            dao.updateUserHistory("user", userJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() {
        try {
            if (this.getId() == null) {
                JsonStrHistoryDao dao = new JsonStrHistoryDao();
                String userJson = dao.getCache("user");
                Gson gson = new Gson();
                User user = gson.fromJson(userJson, this.getMyClass());
                if (user != null) {
                    BaseApp.getInstance().setUser(user);
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 清空用户信息
     */
    public User clearUser() {
        try {
            JsonStrHistoryDao dao = new JsonStrHistoryDao();
            dao.deleteCache(JsonStrHistoryDao.Url_key + "=?", new String[]{"user"});
            BaseApp.getInstance().setUser(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object clone() {
        User o = null;
        try {
            o = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
