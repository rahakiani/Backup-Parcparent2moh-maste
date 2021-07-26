package pro.kidss;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

import pro.kidss.R;

public class VoiceAdapterRecycler extends RecyclerView.Adapter<VoiceAdapterRecycler.ViewHolder> {
    private ArrayList<String> voiceUrl;
    private ArrayList<String> voiceName;
    private Context context;
    boolean isPLAYING= false;
    ImageView imgcheck;
    MediaPlayer mp;
    Button btnvoice;
    int positionn=0;
    SeekBar mSeekBar;
    private Handler mHandler;
    private Runnable mRunnable;
    FloatingActionButton removefab,fab2;
    ArrayList<String> removeList=new ArrayList<String>();
    int i=0;

    public VoiceAdapterRecycler(ArrayList<String> voiceUrl, ArrayList<String> voiceName, Context context, FloatingActionButton removefab, FloatingActionButton fab2) {
        this.voiceUrl = voiceUrl;
        this.voiceName = voiceName;
        this.context = context;
        this.removefab=removefab;
        this.fab2=fab2;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardViieeww;
        public ViewHolder (CardView v){
            super(v);
            cardViieeww = v;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView cv = (CardView) LayoutInflater.from(context).inflate(R.layout.card_view_voi, parent , false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        mHandler = new Handler();
        final CardView cardView = holder.cardViieeww;
        mp=new MediaPlayer();
        mSeekBar = cardView.findViewById(R.id.seek_bar);
        mSeekBar.setProgress(0);
        imgcheck=(ImageView)cardView.findViewById(R.id.imgcheck);

        btnvoice=(Button)cardView.findViewById(R.id.btnvoice);
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN){
            btnvoice.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_play_arrow_24));
            btnvoice.setTag(12);

        }else {
            btnvoice.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_play_arrow_24));
            btnvoice.setTag(12);
        }
        Handler handler=new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mp.isPlaying()){

                }else {
                    mSeekBar = cardView.findViewById(R.id.seek_bar);
                    mSeekBar.setProgress(0);
                    btnvoice=(Button)cardView.findViewById(R.id.btnvoice);
                    if (Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN){
                        btnvoice.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_play_arrow_24));
                        btnvoice.setTag(12);
                    }else {
                        btnvoice.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_play_arrow_24));
                        btnvoice.setTag(12);
                    }
                }
                handler.postDelayed(this::run,100);
            }
        });

        TextView appNamee = (TextView)cardView.findViewById(R.id.txtVoiceName);
        appNamee.setText(voiceName.get(position));
        btnvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnvoice=(Button)cardView.findViewById(R.id.btnvoice);
                Log.e("onClick", voiceUrl.get(position) );
                if (mp.isPlaying()){
                    if (String.valueOf(view.getTag()).equals("11")){
                        mp.stop();
//                        mp.release();
//                        mp = null;
                        if(mHandler!=null){
                            mHandler.removeCallbacks(mRunnable);
                        }
                    }else {
                        mp.stop();
//                        mp.release();
//                        mp = null;
                        if(mHandler!=null){
                            mHandler.removeCallbacks(mRunnable);
                        }
                    Handler handler1=new Handler();
                    handler1.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnvoice=(Button)cardView.findViewById(R.id.btnvoice);
                            setmedia(view,voiceUrl.get(position),isplay());
                            mSeekBar = cardView.findViewById(R.id.seek_bar);
                            i=0;
                            initializeSeekBar1();
                        }
                    },1000);
                    Log.e("media", "isplaying");}
                }else {
                    btnvoice=(Button)cardView.findViewById(R.id.btnvoice);
                setmedia(cardView,voiceUrl.get(position),isplay());
                    mSeekBar = cardView.findViewById(R.id.seek_bar);
                    i=0;
                    initializeSeekBar1();}



            }
        });
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(mp!=null && b){mp.seekTo(i*1000);}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                imgcheck=(ImageView)cardView.findViewById(R.id.imgcheck);
                if (imgcheck.getVisibility()==View.GONE){
                    imgcheck.setVisibility(View.VISIBLE);
                if (removeList.size()==0){
                    removefab.setVisibility(View.VISIBLE);
                    fab2.setVisibility(View.VISIBLE);
                    removeList.add(voiceUrl.get(position));

                }else {
                    removeList.add(voiceUrl.get(position));
                }}else {
                    imgcheck.setVisibility(View.GONE);
                    removeList.remove(voiceUrl.get(position));
                    if (removeList.size()==0){
                        removefab.setVisibility(View.GONE);
                        fab2.setVisibility(View.GONE);
                    }
                }
                return true;
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgcheck=(ImageView)cardView.findViewById(R.id.imgcheck);
                if (removeList.size()>0){
                    if (imgcheck.getVisibility()==View.GONE){
                        imgcheck.setVisibility(View.VISIBLE);
                        if (removeList.size()==0){
                            removefab.setVisibility(View.VISIBLE);
                            fab2.setVisibility(View.VISIBLE);
                            removeList.add(voiceUrl.get(position));

                        }else {
                            removeList.add(voiceUrl.get(position));
                        }}else {
                        imgcheck.setVisibility(View.GONE);
                        removeList.remove(voiceUrl.get(position));
                        if (removeList.size()==0){
                            removefab.setVisibility(View.GONE);
                            fab2.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return voiceName.size();
    }

    public Boolean isplay(){
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.isMusicActive()){
            return true;
        }else {
            return false;
        }
    }

    public void setmedia(View v,String url,boolean isPLAYING) {




                    try {
                        mp.reset();
                        mp.setDataSource(url);
                        mp.prepare();
                        mp.start();

                                if (Build.VERSION.SDK_INT<Build.VERSION_CODES.JELLY_BEAN){
                                    btnvoice.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_pause_24));
                                    btnvoice.setTag(11);
                                }else {
                                    btnvoice.setBackground(ContextCompat.getDrawable(context, R.drawable.ic_baseline_pause_24));
                                    btnvoice.setTag(11);
                                }



                    } catch (IOException e) {

                    }




    }


    protected void initializeSeekBar1(){

        mSeekBar.setMax(mp.getDuration()/1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if(mp!=null){
                    int mCurrentPosition = mp.getCurrentPosition()/1000; // In milliseconds



                    mSeekBar.setProgress(mCurrentPosition);


                }
                mHandler.postDelayed(mRunnable,1000);
            }
        };
        mHandler.postDelayed(mRunnable,3000);
    }
    public ArrayList<String> getremovelist(){
        return removeList;
    }
}


