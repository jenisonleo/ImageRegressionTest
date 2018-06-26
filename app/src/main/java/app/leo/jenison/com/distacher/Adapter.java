package app.leo.jenison.com.distacher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Adapter extends RecyclerView.Adapter<Adapter.Hold>{

    private final int width;
    private Context context;
    Random r=new Random();
    Executor execute;
    ThreadPoolExecutor executor=new ThreadPoolExecutor(10,10,0, TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>());


    public Adapter(Context context){
        execute=new Executor(){
            Handler handler=new Handler(Looper.getMainLooper());
            @Override
            public void execute(@NonNull Runnable command) {
                handler.post(command);
            }
        };
        this.context = context;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
    }
    @NonNull
    @Override
    public Hold onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView imageView=new ImageView(parent.getContext());
        ViewGroup.LayoutParams p=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,300);
        imageView.setLayoutParams(p);
        return new Hold(imageView);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(@NonNull final Hold holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"pos "+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();//NO I18N
            }
        });
        Log.e("onbind"," "+position);//NO I18N
    }


    @Override
    public void onViewRecycled(@NonNull final Hold holder) {
        Log.e("recycled"," ");
        ((FrameLayout)holder.itemView).removeAllViews();

    }

    @Override
    public int getItemCount() {
        return 100;
    }


    public static class Hold extends RecyclerView.ViewHolder{

        public Hold(View itemView) {
            super(itemView);
        }
    }
}
