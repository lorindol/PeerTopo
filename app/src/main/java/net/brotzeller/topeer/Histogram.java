package net.brotzeller.topeer;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;

import static android.graphics.Bitmap.*;

/**
 * Created by martin on 06.02.15.
 */
public class Histogram {
    private Resources mRes;
    private int width = 100;
    private int height = 100;
    private Float[][] edges = {{2f,24f},{26f,49f},{51f,74f},{76f,99f}};
    private int[] colors = {0x607070c0, 0x6070c070 , 0x60c07070, 0x80c0c0c0};
    int textcolor = 0xFF333333;
    int textshadecolor = 0x88FFFFFF;

    public Histogram(Resources res) {
        mRes = res;
    }
    public BitmapDrawable hist(int[] ibins) {
        Bitmap img;
        img = createBitmap(mRes.getDisplayMetrics(), width, height, Config.ARGB_8888);
        img.eraseColor(Color.TRANSPARENT);

        float unit = height / binHeight(ibins, 5);
        Float[] bins = adjustHeight(ibins, unit);
        int textOffX = 8;
        int textOffY = 95;
        Canvas coo = new Canvas(img);
        RectF box = new RectF();
        Paint clr = new Paint();
        clr.setFakeBoldText(true);
        clr.setShadowLayer(1f, 1f, 1f, textshadecolor);

        for(int i = 0; i<4; i++) {
            box.set(edges[i][0],bins[i],edges[i][1],height);
            clr.setColor(colors[i]);
            coo.drawRect(box, clr);

            if (0 != ibins[i]) {
                clr.setColor(textcolor);
                coo.drawText(ibins[i] + "", edges[i][0]+textOffX, textOffY, clr);
            }
        }

        BitmapDrawable bmd = new BitmapDrawable(mRes, img);
        return bmd;
    }


    private int binHeight(int[] bins, int lub) {
        float max = (float) lub;
        for(float i : bins) {
            if(i>max) max = i;
        }
        max = (float)Math.ceil(max*1.2f);
        return (int)max;
    }
    private Float[] adjustHeight(int[] bins, float unit) {
        Float[] fbins = new Float[4];
        for(int i=0; i<4; i++) {
            fbins[i] = height - bins[i] * unit;
        }
        return fbins;
    }
}
