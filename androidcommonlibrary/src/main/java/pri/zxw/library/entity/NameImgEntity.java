package pri.zxw.library.entity;

/**
 * 设置图片与名称的实体
 * Createdy 张相伟
 * 2017/5/17.
 */

public class NameImgEntity {
    public NameImgEntity(){}
    public NameImgEntity(String name, int imgId,Class className) {
        this.name = name;
        this.imgId = imgId;
        this.className=className;
    }

    private  String name;
    private  int imgId;
    private Class className;

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
