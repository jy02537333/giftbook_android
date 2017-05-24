package pri.zxw.mysetting;


import android.view.View;

/**
 *
 * Created by Administrator on 2016/4/28.
 */
public class MySettingInfo {
    /**
     * 项图标
     */
    private Integer itemIcon;
    private Boolean isShowIcon;
    private String itemName;
    private Integer nameWeight;
    private Float nameSize;
    private String itemDesc;
    private Integer descWeight;
    private Float descSize;
    /**
     * 是否显示箭头
     */
    private boolean isShowArrows=true;
    private String arrowsDesc;
    /**
     * 是否显示描述
     */
    private boolean isShowDesc=false;
    private Integer lineHeight=1;
    /**
     * 当前项触发的方法
     */
    private View.OnClickListener itemClickListener;

    public Boolean getShowIcon() {
        return isShowIcon;
    }

    public void setShowIcon(Boolean showIcon) {
        isShowIcon = showIcon;
    }

    public Integer getNameWeight() {
        return nameWeight;
    }

    public void setNameWeight(Integer nameWeight) {
        this.nameWeight = nameWeight;
    }

    public Integer getDescWeight() {
        return descWeight;
    }

    public void setDescWeight(Integer descWeight) {
        this.descWeight = descWeight;
    }

    public String getArrowsDesc() {
        return arrowsDesc;
    }

    public void setArrowsDesc(String arrowsDesc) {
        this.arrowsDesc = arrowsDesc;
    }

    public void setNameSize(Float nameSize) {
        this.nameSize = nameSize;
    }

    public void setDescSize(Float descSize) {
        this.descSize = descSize;
    }

    public Integer getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(Integer lineHeight) {
        this.lineHeight = lineHeight;
    }

    public float getNameSize() {
        return nameSize;
    }

    public void setNameSize(float nameSize) {
        this.nameSize = nameSize;
    }

    public float getDescSize() {
        return descSize;
    }

    public void setDescSize(float descSize) {
        this.descSize = descSize;
    }

    public boolean isShowDesc() {
        return isShowDesc;
    }

    public void setShowDesc(boolean showDesc) {
        isShowDesc = showDesc;
    }

    public View.OnClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public Integer getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(Integer itemIcon) {
        this.itemIcon = itemIcon;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public boolean isShowArrows() {
        return isShowArrows;
    }

    public void setShowArrows(boolean showArrows) {
        isShowArrows = showArrows;
    }
}
