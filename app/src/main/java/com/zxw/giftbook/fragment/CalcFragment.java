package com.zxw.giftbook.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zxw.giftbook.R;

import pri.zxw.library.base.BaseFragment;

/**
 *  计算器，页面
 * Created by Administrator on 2017/3/3.
 */

public class CalcFragment extends BaseFragment {
    View view;
    TextView num0,num1,num2,num3,num4,num5,num6,num7,num8,num9,nump;
    Button confirmBtn;
    ImageView delBtn;
    EditText valEdit;
    LinearLayout backspaceLay;
    Button backspaceBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.include_calc, container, false);
        initView();
        initTool();
        initListener();
        return view;
    }
    void initView()
    {
        num0=(TextView)view.findViewById(R.id.i_calc_num_0);
        num1=(TextView)view.findViewById(R.id.i_calc_num_1);
        num2=(TextView)view.findViewById(R.id.i_calc_num_2);
        num3=(TextView)view.findViewById(R.id.i_calc_num_3);
        num4=(TextView)view.findViewById(R.id.i_calc_num_4);
        num5=(TextView)view.findViewById(R.id.i_calc_num_5);
        num6=(TextView)view.findViewById(R.id.i_calc_num_6);
        num7=(TextView)view.findViewById(R.id.i_calc_num_7);
        num8=(TextView)view.findViewById(R.id.i_calc_num_8);
        num9=(TextView)view.findViewById(R.id.i_calc_num_9);
        nump=(TextView)view.findViewById(R.id.i_calc_num_p);
        confirmBtn=(Button) view.findViewById(R.id.i_calc_confirm);
         delBtn=(ImageView)view.findViewById(R.id.i_calc_del_btn);
         valEdit=(EditText)view.findViewById(R.id.i_calc_value_edit);
         backspaceLay=(LinearLayout)view.findViewById(R.id.i_calc_backspace_lay);
        backspaceBtn=(Button)view.findViewById(R.id.i_calc_backspace_btn);
    }
    void initTool()
    {

    }
    void initListener()
    {
        num0.setOnClickListener(new CalcNumClick());num1.setOnClickListener(new CalcNumClick());
        num2.setOnClickListener(new CalcNumClick());num3.setOnClickListener(new CalcNumClick());
        num4.setOnClickListener(new CalcNumClick());num5.setOnClickListener(new CalcNumClick());
        num6.setOnClickListener(new CalcNumClick());num7.setOnClickListener(new CalcNumClick());
        num8.setOnClickListener(new CalcNumClick());num9.setOnClickListener(new CalcNumClick());
        nump.setOnClickListener(new CalcNumClick());
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valEdit.setText("0");
            }
        });
        backspaceLay.setOnClickListener(new BackspaceClick());
        backspaceBtn.setOnClickListener(new BackspaceClick());
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public String getFragmentName() {
        return null;
    }

    @Override
    public boolean getIsSpecial() {
        return false;
    }
    class BackspaceClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            int index = valEdit.getSelectionStart();
            Editable editable = valEdit.getEditableText();
            if(editable.length()<=1)
            {
                editable.clear();
                editable.append("0");
            }else {
                if(index<1)
                    index=editable.length()-1;
                editable = editable.delete(index , editable.length());
            }
            valEdit.setText(editable);
        }
    }

    class CalcNumClick implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            TextView tv=null;
            if(view instanceof TextView)
             tv=(TextView)view;
            else {
                return;
            }
            String inputVal=tv.getText().toString();
            String val=valEdit.getText().toString();
            if(val.indexOf(".")!=-1&&inputVal.equals("."))
                return;
            int index = valEdit.getSelectionStart();
            Editable editable = valEdit.getEditableText();
            if(index<=0)
            {
                if(editable.toString().equals("0"))
                {
                    editable.clear();
                    editable.append(inputVal);
                }else {
                    editable=  editable.insert(editable.length(), inputVal);
                }
            }else
            {
                editable=  editable.insert(index, inputVal);
            }
            valEdit.setText(editable);
        }
    }
}
