package com.alryada.etqan.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alryada.etqan.R;

/**
 * Created by Abdallah Rashad A on 4/13/2018.
 */

public class SliderAdabter extends PagerAdapter {

    Context context ;
    LayoutInflater layoutInflater ;
    public SliderAdabter(Context context){
        this.context=context;
    }

    // Arrays
    public  int[] sLider_image = {
            // drwable icons
            R.drawable.etqanlogo1,
            R.drawable.cleaners,
            R.drawable.pricev
    };

    public  String[] sLide_Arabic ={
            // Arabic
          "تهدف مؤسسة اتقان الي تلبية احتياجاتكم من خدمة  تنظيف المنازل",
          "نتيح لكم حجز ثلاث عاملات تم تدريبهم لتلبية احتياجاتكم اليوميه " ,
            "الاسعار الخاصه بنا تنافسيه و تناسب كل فئات المجتمع "
    };
    public  String[] sLide_English ={
            // English
            "Etqan Foundation aims To meet your needs of home cleaning service" ,
            "We allow you to book three workers trained to meet your daily needs",
            "Our prices are competitive and suitable for all categories of society"
    };


    @Override
    public int getCount() {
        return sLide_Arabic.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view =  layoutInflater.inflate(R.layout.slide_layout,container,false);

        ImageView sLideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView sLide_ArabicView = (TextView) view.findViewById(R.id.slide_Arabic);
        TextView sLide_EnglishView = (TextView) view.findViewById(R.id.slide_English);
        sLideImageView.setImageResource(sLider_image[position]);
        sLide_ArabicView.setText(sLide_Arabic [position]);
        sLide_EnglishView.setText(sLide_English [position]);
        container.addView(view);
        return  view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);

    }
}
