package pro.kidss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

import pro.kidss.R;

public class RecyclerviewImage extends RecyclerView.Adapter<RecyclerviewImage.ViewHolder> {
    ArrayList<String> imageUrls;
    Context context;
    String typee;
    ArrayList<String> removeList=new ArrayList<String>();
    FloatingActionButton removefab;
    ArrayList<String> ids,dating;


    public RecyclerviewImage(ArrayList<String> imgurl, Context context, String typee, FloatingActionButton removefab,ArrayList<String> ids,ArrayList<String> dating) {
        imageUrls = imgurl;
        this.context = context;
        this.typee=typee;
        this.removefab=removefab;
        this.ids=ids;
        this.dating=dating;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.image_card_view, viewGroup, false);
        return new ViewHolder(view);
    }

    /**
     * gets the image url from adapter and passes to Glide API to load the image
     *
     * @param viewHolder
     * @param i
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.txtdate.setText(dating.get(i));
        if (typee.equals("vid")){
        long thumb = i*1000;
        RequestOptions options = new RequestOptions().frame(thumb);
        Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
            if (removeList.contains(imageUrls.get(i))){
                viewHolder.imgcheck.setVisibility(View.VISIBLE);
            }else {
                viewHolder.imgcheck.setVisibility(View.GONE);
            }
        viewHolder.img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


              //  viewHolder.img.setBackground(ContextCompat.getDrawable(context,R.drawable.backgradiant));
                if (!removeList.contains(imageUrls.get(i))){
                    removeList.add(imageUrls.get(i));
                    //viewHolder.img.setImageResource(R.drawable.backgradiant);
                    viewHolder.imgcheck.setVisibility(View.VISIBLE);
                }else {removeList.remove(imageUrls.get(i));
//                    long thumb = i*1000;
//                    RequestOptions options = new RequestOptions().frame(thumb);
//                    Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
                    viewHolder.imgcheck.setVisibility(View.GONE);

                }
                if (removeList.size()==0){
                    removefab.setVisibility(View.GONE);
                }
                if (removeList.size()==1){
                    removefab.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (removeList.size()==0){
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
                    Intent intent=new Intent(context, ShowVidActivity.class);
                    intent.putExtra("path",imageUrls.get(i));
                    context.startActivity(intent);
//                    RxPermissions rxPermissions=new RxPermissions((FragmentActivity) context);
//                    rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                            .subscribe(granted ->{
//                                if (granted){
//
//                                }else {
//                                    Toast.makeText(context, "Sorry, we need permission to do this", Toast.LENGTH_LONG).show();
//                                }
//                            });
                }else {
                    goToUrl(imageUrls.get(i),context);}}else {
                    if (!removeList.contains(imageUrls.get(i))){
                        removeList.add(imageUrls.get(i));
                        //viewHolder.img.setImageResource(R.drawable.backgradiant);
                        viewHolder.imgcheck.setVisibility(View.VISIBLE);
                    }else {
                        removeList.remove(imageUrls.get(i));
//                    long thumb = i*1000;
//                    RequestOptions options = new RequestOptions().frame(thumb);
//                    Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
                        viewHolder.imgcheck.setVisibility(View.GONE);

                    }
                    if (removeList.size()==0){
                        removefab.setVisibility(View.GONE);
                    }
                    if (removeList.size()==1){
                        removefab.setVisibility(View.VISIBLE);
                    }

                }
            }

        });}else if (typee.equals("vidcate")){
            long thumb = i*1000;
            RequestOptions options = new RequestOptions().frame(thumb);
           // Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
            switch (dating.get(i)){
                case "camera":
                    viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.cam));
                    viewHolder.txtdate.setText("Camera");
                    break;
                case "com.whatsapp":
                    viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.whatsapp));
                    viewHolder.txtdate.setText("Whatsapp");
                    break;
                case "com.instagram.android":
                    viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.instagram));
                    viewHolder.txtdate.setText("instagram");
                    break;
                case "org.telegram.messenger":
                    viewHolder.img.setBackground(ContextCompat.getDrawable(context, R.drawable.telegram));
                    viewHolder.txtdate.setText("Telegram");
                    break;
                default:
                    Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
                    break;

            }

            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, VideoDateActivity.class);
                    intent.putExtra("Type",dating.get(i));
                    context.startActivity(intent);

                }
            });

        }else if (typee.equals("viddate")){
            viewHolder.txtdate.setText(dating.get(i).split(",,::")[1]);
            long thumb = i*1000;
            RequestOptions options = new RequestOptions().frame(thumb);
            Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, vidGaleryActivity.class);
                    intent.putExtra("Type",dating.get(i));
                    context.startActivity(intent);

                }
            });
        }else {
            viewHolder.img.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {


                    //  viewHolder.img.setBackground(ContextCompat.getDrawable(context,R.drawable.backgradiant));

//                    if (removeList.contains(ids.get(i))){
//                        removeList.remove(ids.get(i));
//                        String img2=imageUrls.get(i).replace("\\n","\n");
//                        Bitmap bitmap = Utils2.getBitmap(img2);
//                        viewHolder.img.setImageBitmap(bitmap);
//                    }else {
//                        removeList.add(ids.get(i));
//                        viewHolder.img.setImageResource(R.drawable.backgradiant);
//                    }
//                    if (removeList.size()==0){
//                        removefab.setVisibility(View.GONE);
//                    }
//                    if (removeList.size()==1){
//                        removefab.setVisibility(View.VISIBLE);
//                    }
//                    return true;
                    //  viewHolder.img.setBackground(ContextCompat.getDrawable(context,R.drawable.backgradiant));
                    if (!removeList.contains(ids.get(i))){
                        removeList.add(ids.get(i));
                        //viewHolder.img.setImageResource(R.drawable.backgradiant);
                        viewHolder.imgcheck.setVisibility(View.VISIBLE);
                    }else {removeList.remove(ids.get(i));
//                    long thumb = i*1000;
//                    RequestOptions options = new RequestOptions().frame(thumb);
//                    Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
                        viewHolder.imgcheck.setVisibility(View.GONE);

                    }
                    if (removeList.size()==0){
                        removefab.setVisibility(View.GONE);
                    }
                    if (removeList.size()==1){
                        removefab.setVisibility(View.VISIBLE);
                    }
                    return true;
                }
            });
            String img2=imageUrls.get(i).replace("\\n","\n");
            Bitmap bitmap = Utils2.getBitmap(img2);
            viewHolder.img.setImageBitmap(bitmap);

            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (removeList.size()==0){
                        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD){
                            Intent intent=new Intent(context, imageActivity.class);
                            intent.putExtra("image",img2);
                            context.startActivity(intent);
                        }else {
                            goToUrl(ids.get(i),context);}}else {
                        if (!removeList.contains(ids.get(i))){
                            removeList.add(ids.get(i));
                            //viewHolder.img.setImageResource(R.drawable.backgradiant);
                            viewHolder.imgcheck.setVisibility(View.VISIBLE);
                        }else {
                            removeList.remove(ids.get(i));
//                    long thumb = i*1000;
//                    RequestOptions options = new RequestOptions().frame(thumb);
//                    Glide.with(context).load(imageUrls.get(i)).apply(options).into(viewHolder.img);
                            viewHolder.imgcheck.setVisibility(View.GONE);

                        }
                        if (removeList.size()==0){
                            removefab.setVisibility(View.GONE);
                        }
                        if (removeList.size()==1){
                            removefab.setVisibility(View.VISIBLE);
                        }

                    }

                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView img,imgcheck;
        TextView txtdate;

        public ViewHolder(View view) {
            super(view);
            img = view.findViewById(R.id.imageView);
            imgcheck=view.findViewById(R.id.imgcheck);
            txtdate=view.findViewById(R.id.txtdate);
        }

    }
    public static Bitmap retriveVideoFrameFromVideo(String videoPath)throws Throwable
    {
        Bitmap bitmap = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            if (Build.VERSION.SDK_INT >= 14)
                mediaMetadataRetriever.setDataSource(videoPath, new HashMap<String, String>());
            else
                mediaMetadataRetriever.setDataSource(videoPath);
            //   mediaMetadataRetriever.setDataSource(videoPath);
            bitmap = mediaMetadataRetriever.getFrameAtTime(1, MediaMetadataRetriever.OPTION_CLOSEST);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Throwable("Exception in retriveVideoFrameFromVideo(String videoPath)"+ e.getMessage());
        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }
        return bitmap;
    }
    private void goToUrl (String url,Context co) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        co.startActivity(launchBrowser);
    }
    public ArrayList<String> getremovelist(){
        return removeList;
    }
}
