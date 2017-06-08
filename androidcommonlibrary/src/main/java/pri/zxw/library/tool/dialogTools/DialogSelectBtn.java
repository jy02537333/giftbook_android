package pri.zxw.library.tool.dialogTools;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import pri.zxw.library.R;

/**
 * 显示popwindows在中间
 * Created by Administrator on 2017/6/7.
 */

public class DialogSelectBtn {
    LinearLayout lay;
     PopupWindow popupWindow;
    public DialogSelectBtn(Context context,View rootView)
    {
        View popupView = LayoutInflater.from(context).inflate(R.layout.dialog_select_lay, null);
        popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = popupView.getMeasuredWidth();
        int popupHeight = popupView.getMeasuredHeight();
         lay = (LinearLayout) popupView.findViewById(R.id.dialog_select_lay);
    }
    public ViewGroup.LayoutParams getLP()
    {
        return lay.getLayoutParams();
    }


    public  void show(Context context, String name, List<View> views, View rootView) {

        if (views != null)
            for (View view : views)
            {
                view.setBackgroundColor(context.getResources().getColor(R.color.com_black_bg));
                lay.addView(view);

            }
        lay.setBackgroundColor(context.getResources().getColor(R.color.com_black_bg) );
        lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);

    }
}
