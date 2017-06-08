package com.zxw.giftbook.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zxw.giftbook.R;


/**
 * 显示删除还是编辑按钮操作
 * Created by Administrator on 2017/6/8.
 */

public class PopShowDelAndEditOperateTool {

    public static void show(Context context,final String id,final  String name,  View rootView,final DelAndEditCallback delAndEditCallback)
    {
        View popupView = LayoutInflater.from(context).inflate(R.layout.pop_select_operate, null);
       final  PopupWindow  popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = popupView.getMeasuredWidth();
        int popupHeight = popupView.getMeasuredHeight();
        LinearLayout lay = (LinearLayout) popupView.findViewById(R.id.pop_select_lay);
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        TextView delTv=(TextView) popupView.findViewById(R.id.pop_select_operate_del_tv);;
        delTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                delAndEditCallback.onDelComplate(id,name);
            }
        });
        TextView editTv=(TextView) popupView.findViewById(R.id.pop_select_operate_edit_tv);;
        editTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                delAndEditCallback.onEditComplate(id,name);
            }
        });

        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }
    public interface DelAndEditCallback{
        public  void onDelComplate(String id, String name);
        public  void onEditComplate(String id, String name);
    }
}
