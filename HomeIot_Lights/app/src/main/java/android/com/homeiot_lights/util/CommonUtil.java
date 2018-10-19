package android.com.homeiot_lights.util;

import android.com.homeiot_lights.R;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CommonUtil {

    private static Drawable[] drawables = new Drawable[3];

    public static Drawable makeLightsType(Resources resources, int index){
        if(drawables[index]==null){
            TypedArray images = resources.obtainTypedArray(R.array.lights_resource);
            Drawable drawable = images.getDrawable(index);
            drawables[index]=drawable;
            Log.d("makeLightsType",  drawables[index]+"");
        }
        return drawables[index];
    }
}
