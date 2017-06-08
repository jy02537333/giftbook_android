package com.zxw.giftbook.utils.contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zxw.giftbook.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pri.zxw.library.tool.ImgLoad.ImgLoadMipmapTool;
import pri.zxw.library.tool.ImgLoad.ImgUrlUtil;
import pri.zxw.library.tool.ImgLoad.MyImgLoadTool;

/**
 * @author 张相伟
 * @description 通讯录选择适配器
 * @date 2016-10-22
 */
public class ContactListSelectAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private HashMap<String, Integer> alphaIndexer; // 字母索引
    public TreeMap<Integer, Boolean> checkMap;
    private String[] sections; // 存储每个章节
    private HashMap<String, String> idMap = new HashMap<String, String>();
    private Context ctx; // 上下文
    private CharacterParser characterParser = new CharacterParser();

    List<Entry<String, AddressBooktBean>> list1 = null;

    public ContactListSelectAdapter(Context context, List<Entry<String, AddressBooktBean>> list,
                                    QuickAlphabeticBar alpha, String[] ids) {
        this.ctx = context;
        this.inflater = LayoutInflater.from(context);
        this.list1 = list;
        this.alphaIndexer = new HashMap<String, Integer>();
        this.sections = new String[list.size()];
        //通过ArrayList构造函数把map.entrySet()转换成list  
        for (int i = 0; i < list.size(); i++) {
            // 得到字母
            String name = ContactListAdapter.getAlpha(characterParser.getSelling(list.get(i).getValue().getSortKey()));
            if (!alphaIndexer.containsKey(name)) {
                alphaIndexer.put(name, i);
            }
        }
        Set<String> sectionLetters = alphaIndexer.keySet();
        ArrayList<String> sectionList = new ArrayList<String>(sectionLetters);
        Collections.sort(sectionList);
        sections = new String[sectionList.size()];
        sectionList.toArray(sections);

        alpha.setAlphaIndexer(alphaIndexer);
        checkMap = new TreeMap<Integer, Boolean>();
        for (int i = 0; ids != null && i < ids.length; i++) {
            idMap.put(ids[i], ids[i]);
        }

    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int position) {
        return list1.get(position).getValue();
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<Entry<String, AddressBooktBean>> list) {
        this.list1 = list;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void remove(int position) {
        list1.remove(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.plugin_item_list_contact_select, null);
            holder = new ViewHolder();
            holder.quickContactBadge = (ImageView) convertView
                    .findViewById(R.id.item_list_contact_select_head_qcb);
            holder.checkCbk = (CheckBox) convertView
                    .findViewById(R.id.item_list_contact_select_cbk);
            holder.alphaTv = (TextView) convertView.findViewById(R.id.item_list_contact_select_alpha_tv);
            holder.nameTv = (TextView) convertView.findViewById(R.id.item_list_contact_select_name_tv);
            holder.phoneTv = (TextView) convertView.findViewById(R.id.item_list_contact_select_phone_tv);
            holder.layout = (RelativeLayout) convertView.findViewById(R.id.item_list_contact_select_lay);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AddressBooktBean contact = list1.get(position).getValue();
        String name = contact.getTfName();
        String number = contact.getTfPhone();
        holder.nameTv.setText(name);
        holder.phoneTv.setText(number);
        if(contact.getTfPortrait()==null)
        {
            ImgLoadMipmapTool.load(R.mipmap.user_default, holder.quickContactBadge);
        }else {
            String imgUrl = ImgUrlUtil.getFullHeadImgUrl(contact.getTfPortrait());
            MyImgLoadTool.loadNetImg(ctx, imgUrl, R.mipmap.user_default, holder.quickContactBadge, 80, 80, null);
        }

        // 当前字母

        String currentStr = getAlpha(characterParser.getSelling(contact.getSortKey()));
        // 前面的字母
        String previewStr = (position - 1) >= 0 ? getAlpha(characterParser.getSelling(list1.get(position - 1).getValue().getSortKey())) : " ";

        if (!previewStr.equals(currentStr)) {
            holder.alphaTv.setVisibility(View.VISIBLE);
            holder.alphaTv.setText(currentStr);
        } else {
            holder.alphaTv.setVisibility(View.GONE);
        }
        if (position == 0) {
            Log.i("aa", checkMap.size() + "");
        }
        if (!checkMap.containsKey(position)) {
            if (idMap.containsKey(contact.getTfId())) {
                checkMap.put(position, true);
                holder.checkCbk.setChecked(true);
            } else {
                checkMap.put(position, false);
                holder.checkCbk.setChecked(false);
            }
        } else if (checkMap.containsKey(position) && checkMap.get(position)) {
            holder.checkCbk.setChecked(true);
        } else {

            holder.checkCbk.setChecked(false);
        }
        //holder.checkCbk.setOnCheckedChangeListener(new CheckClick(position));
        holder.checkCbk.setOnClickListener(new MyCheckClick(position));
        holder.layout.setOnClickListener(new LayoutClickListener(holder.checkCbk, position));

        return convertView;
    }

    class MyCheckClick implements OnClickListener {
        int mPosition = -1;

        public MyCheckClick(int position) {
            mPosition = position;
        }

        @Override
        public void onClick(View arg0) {
            CheckBox box = (CheckBox) arg0;
            boolean isCheck = false;
            if (checkMap.containsKey(mPosition)) {
                isCheck = checkMap.get(mPosition);
            }
            checkMap.put(mPosition, !isCheck);
            box.setChecked(!isCheck);
        }

    }

    class CheckClick implements OnCheckedChangeListener {
        int mPosition = -1;

        public CheckClick(int position) {
            mPosition = position;
        }

        @Override
        public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            checkMap.put(mPosition, arg1);
        }

    }

    public class LayoutClickListener implements OnClickListener {
        CheckBox mCheckBox;
        int mPostion;

        public LayoutClickListener(CheckBox checkBox, int position) {
            mCheckBox = checkBox;
            mPostion = position;
        }

        @Override
        public void onClick(View arg0) {
            boolean isCheck = false;
            if (checkMap.containsKey(mPostion)) {
                isCheck = checkMap.get(mPostion);
            }
            checkMap.put(mPostion, !isCheck);
            mCheckBox.setChecked(!isCheck);
        }

    }

    private static class ViewHolder {
        /**
         * 头像工具
         */
        ImageView quickContactBadge;
        /**
         * 首字母
         */
        TextView alphaTv;
        /**
         * 姓名
         */
        TextView nameTv;
        /**
         * 电话
         */
        TextView phoneTv;
        /**
         * 选择单选
         */
        CheckBox checkCbk;
        RelativeLayout layout;
    }

    /**
     * 获取首字母
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        if (str == null) {
            return "#";
        }
        if (str.trim().length() == 0) {
            return "#";
        }
        char c = str.trim().substring(0, 1).charAt(0);
        // 正则表达式匹配
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        if (pattern.matcher(c + "").matches()) {
            return (c + "").toUpperCase(); // 将小写字母转换为大写
        } else {
            return "#";
        }
    }
}
