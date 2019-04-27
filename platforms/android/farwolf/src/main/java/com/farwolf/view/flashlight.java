package com.farwolf.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.farwolf.base.ViewBase;
import com.farwolf.libary.R;
import com.farwolf.util.AppTool;

public class flashlight extends ViewBase {

    TextView sunline;

    public flashlight(Context context, AttributeSet attrs) {
        super(context, attrs);
        initial();

        // TODO Auto-generated constructor stub
    }

    public flashlight(Context context) {
        super(context);
        initial();
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getViewId() {
        // TODO Auto-generated method stub

        if(AppTool.OSVersion()>=19)
        {
            return R.layout.flash;
        }
        else
        {
            return R.layout.flash;
        }
//		return R.layout.api_titlebar;

    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

   public void initial(){

       sunline =(TextView)findViewById(R.id.sunline);
      // sunline.setOnClickListener(l);

   }
    public void setsunlineClick(OnClickListener l)
    {
        this.sunline.setOnClickListener(l);
    }

}
