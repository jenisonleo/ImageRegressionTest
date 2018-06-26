package app.leo.jenison.com.distacher;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.ByteString;

public class MainActivity extends AppCompatActivity implements TalkWithTheMonster{
    RxTest rxTest;
    Picasso picasso;
    LruCache cache;
    ArrayList<String> strings;
    ArrayList<Bitmap> bitmaps;
    OkHttpClient okhttpclinet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        rxTest=new RxTest();
//        rxTest.addEvevnt();
//            ComputationTest computationTest=new ComputationTest();
        bitmaps=new ArrayList<>();
        strings=new ArrayList<>();
        strings.add("https://c2.staticflickr.com/8/7031/6643290037_7ac713ccf1_b.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/b/b8/Portrait_de_Picasso%2C_1908.jpg");
        strings.add("https://c1.staticflickr.com/3/2599/4045020694_f1d821da50_b.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/1/13/Pablo_Picasso%2C_1904%2C_Paris%2C_photograph_by_Ricard_Canals_i_Llamb%C3%AD.jpg");
        strings.add("http://www.publicdomainpictures.net/pictures/10000/velka/pablo-picasso-11282050953OjxQ.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/a/a9/Pablo_picasso_1_%28cuadrado%29.jpg");
        strings.add("https://c1.staticflickr.com/5/4097/4805137133_3cda2d6aae_b.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/f/ff/Ramon_Casas_-_Retrat_de_Pablo_Picasso.jpg");
        strings.add("https://c1.staticflickr.com/3/2345/2232748754_e2e788e0d7_b.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/7/77/Modigliani%2C_Picasso_and_Andr%C3%A9_Salmon.jpg");
        strings.add("https://c1.staticflickr.com/6/5815/23292067242_c4321e50c9_b.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/en/c/c6/Chicago_Picasso_Maquette.jpg");
        strings.add("https://c1.staticflickr.com/8/7096/27041812283_7f16f3fe50_b.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/7/72/Portrait_de_l%27artiste_%C3%A0_la_lampe_-_Henri_Rousseau_-_Mus%C3%A9e_Picasso_Paris.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/1/14/Pablo_Picasso_with_his_sister_Lola%2C_1889.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/5/51/Picasso_al_carrer_de_Montcada_de_Barcelona.JPG");
        strings.add("https://upload.wikimedia.org/wikipedia/en/7/7b/Parade_Picasso.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/2/2d/Picasso_und_Stiermaske%2C_Margret_Hofheinz-D%C3%B6ring%2C_%C3%96l%2C_1970_%28WV-Nr.5534%29.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/thumb/7/77/Modigliani%2C_Picasso_and_Andr%C3%A9_Salmon.jpg/608px-Modigliani%2C_Picasso_and_Andr%C3%A9_Salmon.jpg");
        strings.add("https://upload.wikimedia.org/wikipedia/commons/9/93/Citro%C3%ABn_C4_Grand_Picasso_front-1.jpg");


        final CacheControl.Builder builder2=new CacheControl.Builder();
        cache=new LruCache(1024*1024*50);
        builder2.maxAge(Integer.MAX_VALUE, TimeUnit.DAYS);
        builder2.minFresh(Integer.MAX_VALUE, TimeUnit.DAYS);
        builder2.maxStale(Integer.MAX_VALUE,TimeUnit.DAYS);
        final Picasso.Builder builder=new Picasso.Builder(this);
        OkHttpClient.Builder okhttpBuilder=new OkHttpClient.Builder();
        final Cache diskCache = new Cache(new File(getFilesDir(), "test"), 1024L * 1024L * 1024L);
        okhttpBuilder.cache(diskCache);
        okhttpBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response proceed = chain.proceed(chain.request());
                Response.Builder builder1 = proceed.newBuilder();
                Date date = new Date(System.currentTimeMillis()+(10L*365L*24L*60L*60L*1000L));//plus 10 years
                builder1.header("Expires",date.toString());
                builder1.header("Cache-Control", builder2.build().toString());
                return builder1.build();
            }
        });
        okhttpclinet = okhttpBuilder.build();
        builder.downloader(new OkHttp3Downloader(okhttpclinet));
        builder.memoryCache(cache);
        picasso=builder.build();
        setContentView(R.layout.activity_main);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    for(int j=0;j<Integer.MAX_VALUE;j++) {
                        FrameLayout layout=new  FrameLayout(MainActivity.this);
                        FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(1000, 1000);
                        layout.setLayoutParams(params);
                        for (int i = 0; i < strings.size(); i++) {
                            File file=new File(getFilesDir(),String.valueOf(strings.get(i).hashCode()));
                            if(file.exists()){
                                Log.e("does "," exists "+file.length());
                                byte bytes[] = new byte[(int) file.length()];
                                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                                DataInputStream dis = new DataInputStream(bis);
                                dis.readFully(bytes);
                                for(int x=0;x<20;x++) {
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                                    TestView imageView = new TestView(MainActivity.this,bitmap,strings.get(i),MainActivity.this);
                                    FrameLayout.LayoutParams p=new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
                                    imageView.setLayoutParams(p);
                                    layout.addView(imageView);
                                }
                            }else {
                                file.createNewFile();
                                Response execute = okhttpclinet.newCall(new Request.Builder().url(strings.get(i)).build()).execute();
                                byte[] bytes = execute.body().bytes();
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                                bos.write(bytes);
                                bos.flush();
                                bos.close();
                                Log.e("does ","not exists");
                            }
                            Log.e("image fetched"," "+strings.get(i));

                        }
                        Bitmap x=generateBitmap(layout);
                        Log.e("size"," "+x.getWidth()+" "+x.getHeight());
                    }
                    Log.e("bitmas"," "+bitmaps.size());


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void jenison(View view){

    }

    public void senhtur(View view){
        rxTest.send("2");
    }

    public void peru(View view){
        rxTest.send("3");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Bitmap getBitmap(String url) {
        Bitmap bitmap =null;
        try {
            File file=new File(getFilesDir(),String.valueOf(url.hashCode()));
            byte bytes[] = new byte[(int) file.length()];
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(bytes);
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
public Bitmap generateBitmap(View view) {
    view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
    Bitmap bitmap = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    view.layout(0, 0, 1000, 1000);
    view.draw(canvas);
    return bitmap;
}
}
