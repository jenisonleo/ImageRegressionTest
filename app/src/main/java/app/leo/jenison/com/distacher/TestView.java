package app.leo.jenison.com.distacher;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import static android.graphics.Paint.Style.FILL;

public class TestView extends View {
    private String url;
    private TalkWithTheMonster talkWithTheMonster;

    public TestView(Context context,Bitmap bitmap, String url,TalkWithTheMonster talkWithTheMonster) {
        super(context);
        this.url = url;
        this.talkWithTheMonster = talkWithTheMonster;
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        initPaint(bitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        Paint paint=new Paint();
//        paint.setStyle(FILL);
//        paint.setDither(true);
//        paint.setAntiAlias(true);
//        Matrix tranformMatrix=new Matrix();
//        Bitmap bitmao = talkWithTheMonster.getBitmap(url);
//        Bitmap bitmap=new Bitmap();
//        BitmapShader shader=new BitmapShader(bitmao, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
//        float scaleX=((float)bitmao.getWidth())/((float)bitmao.getWidth());
//        float scaleY=((float)bitmao.getHeight())/((float)bitmao.getHeight());
//        tranformMatrix.postScale(scaleX,scaleY);
//        shader.setLocalMatrix(tranformMatrix);
//        paint.setShader(shader);
//        Log.e("on draw"," "+url);
        canvas.drawPaint(paint);
    }

    private Paint paint;
    private void initPaint(Bitmap bitmao){
        paint=new Paint();
        paint.setStyle(FILL);
        paint.setDither(true);
        paint.setAntiAlias(true);
        Matrix tranformMatrix=new Matrix();
        BitmapShader shader=new BitmapShader(bitmao, Shader.TileMode.CLAMP,Shader.TileMode.CLAMP);
        float scaleX=((float)bitmao.getWidth())/((float)bitmao.getWidth());
        float scaleY=((float)bitmao.getHeight())/((float)bitmao.getHeight());
        tranformMatrix.postScale(scaleX,scaleY);
        shader.setLocalMatrix(tranformMatrix);
        paint.setShader(shader);
    }

}
