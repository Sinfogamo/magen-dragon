package com.example.reviv.security.help;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class RespImageView extends AppCompatImageView {
    View parent=(View)this.getParent();
    public RespImageView(Context context)
    {
        super(context);
    }
    public RespImageView(Context context, AttributeSet attrs)
    {
        super(context,attrs);
    }
    public RespImageView(Context context, AttributeSet attrs,int defStyle)
    {
        super(context,attrs,defStyle);
    }

    @Override
    public void onMeasure(int WidthMeasureSpec,int HeightMeasureSpec)
    {
        super.onMeasure(WidthMeasureSpec,HeightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredHeight());
        //setPadding(parent.getWidth()/2,parent.getHeight()/2,parent.getWidth()/2,parent.getHeight()/2);
    }

}
