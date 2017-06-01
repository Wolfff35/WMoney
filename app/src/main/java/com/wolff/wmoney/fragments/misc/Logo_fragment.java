package com.wolff.wmoney.fragments.misc;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wolff.wmoney.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Logo_fragment extends Fragment {

     public Logo_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.logo_fragment, container, false);
  //      FrameLayout frameLayout = (FrameLayout)rootView.findViewById(R.id.logo_container);
  //      frameLayout.addView(new Rectangle(getActivity()));
        return rootView;
    }
/*    private class Rectangle extends View{

        public Rectangle(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
           Path circle = new Path();
            Paint cPaint = new Paint();
            circle.addCircle(500,550,450, Path.Direction.CW);
            canvas.drawPath(circle,cPaint);
            canvas.drawTextOnPath("WMoney is...",circle,0,20,cPaint);

        }
    }
*/
}
