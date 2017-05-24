package pri.zxw.mysetting;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/4/28.
 */
public class MysettingAdapter extends BaseAdapter {

    Activity _act;
    List<MySettingInfo> _infos;
    protected LayoutInflater m_inflater = null;
    MysettingViewHolder  dataViewHolder;
    public  MysettingAdapter(Activity act, List<MySettingInfo> infos)
    {
        _act=act;
        _infos=infos;
        m_inflater = (LayoutInflater) act
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _infos.size();
    }

    @Override
    public Object getItem(int position) {
        return _infos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = m_inflater.inflate(R.layout.item_list_mysetting, parent,false);

            dataViewHolder = new MysettingViewHolder();
            dataViewHolder.item_list_mysetting_lay = (LinearLayout) convertView
                    .findViewById(R.id.item_list_mysetting_lay);
            dataViewHolder.tem_list_mysetting_img = (ImageView) convertView
                    .findViewById(R.id.item_list_mysetting_img);
            dataViewHolder.item_list_mysetting_name = (TextView) convertView
                    .findViewById(R.id.item_list_mysetting_name);
            dataViewHolder.item_list_mysetting_desc = (TextView) convertView
                    .findViewById(R.id.item_list_mysetting_desc);
            dataViewHolder.item_list_mysetting_arrows = (TextView) convertView
                    .findViewById(R.id.item_list_mysetting_arrows);
            dataViewHolder.item_list_mysetting_line = (TextView) convertView
                    .findViewById(R.id.item_list_mysetting_line);
            convertView.setTag(dataViewHolder);
        } else {
            dataViewHolder = (MysettingViewHolder) convertView.getTag();
        }
        setViewValue(position, dataViewHolder);
        return convertView;
    }
    void   setViewValue(int position,MysettingViewHolder dataViewHolder)
    {
        MySettingInfo info=_infos.get(position);
        if(info.getItemIcon()!=null||info.getShowIcon()!=null&&info.getShowIcon())
            dataViewHolder.tem_list_mysetting_img.setImageResource(info.getItemIcon());
        else
            dataViewHolder.tem_list_mysetting_img.setVisibility(View.GONE);
        dataViewHolder.item_list_mysetting_name.setText(info.getItemName());
        if(info.getNameWeight()!=null)
        {
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams)
                    dataViewHolder.item_list_mysetting_name.getLayoutParams();
            lp.weight=info.getNameWeight();
            dataViewHolder.item_list_mysetting_name.setLayoutParams(lp);
        }
        if(info.getItemDesc()!=null)
        {
            dataViewHolder.item_list_mysetting_desc.setText(info.getItemDesc());
        }else
            dataViewHolder.item_list_mysetting_desc.setText(" ");
        if(info.getDescWeight()!=null)
        {
            LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams)
                    dataViewHolder.item_list_mysetting_desc.getLayoutParams();
            lp.weight=info.getDescWeight();
            dataViewHolder.item_list_mysetting_desc.setLayoutParams(lp);
        }
        if(!info.isShowArrows()){
            dataViewHolder.item_list_mysetting_arrows.setVisibility(View.GONE);
        }else if(info.getArrowsDesc()!=null)
        {
            dataViewHolder.item_list_mysetting_arrows.setText(info.getArrowsDesc());
            dataViewHolder.item_list_mysetting_arrows.setVisibility(View.VISIBLE);
            dataViewHolder.item_list_mysetting_arrows.setCompoundDrawables(null,null,null,null);
        }
        LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams)
                dataViewHolder.item_list_mysetting_line.getLayoutParams();
        lp.height=info.getLineHeight();
        dataViewHolder.item_list_mysetting_line.setLayoutParams(lp);
        dataViewHolder.item_list_mysetting_lay.setOnClickListener(info.getItemClickListener());
    }




    class  MysettingViewHolder{
        public LinearLayout item_list_mysetting_lay;
        public ImageView tem_list_mysetting_img;
        public TextView item_list_mysetting_name;
        public TextView  item_list_mysetting_desc;
        public TextView  item_list_mysetting_arrows;
        public  TextView item_list_mysetting_line;

    }

}
