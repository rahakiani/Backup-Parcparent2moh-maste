package pro.kidss.images;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zolad.zoominimageview.ZoomInImageView;

import java.util.ArrayList;

import pro.kidss.R;

public class imageActivity extends AppCompatActivity {
ArrayList<String>time;
ArrayList<String>date;
ArrayList<String>image;
    Bitmap bitmap;
    int position;
    String img2;
    ZoomInImageView imageView;
    ImageButton next,previous;
    TextView t_time,t_date;

    private View parent_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_image );
        t_date= findViewById( R.id.name );
        t_time= findViewById( R.id.time );
        next = findViewById( R.id.nextt );
        previous = findViewById( R.id.previous );
        parent_view = findViewById( R.id.parent_view );
        Intent intent = getIntent();
//        bitmap = Utils2.getBitmap( intent.getStringExtra( "image" ) );
        time =intent.getStringArrayListExtra( "time" );
        date =intent.getStringArrayListExtra( "date" );
        image =intent.getStringArrayListExtra( "image" );
        position = intent.getIntExtra( "position", 0 );
        Log.e("psition78", String.valueOf( position ) );

        imageView = findViewById( R.id.imgshow );
         img2=image.get(position).replace("\\n","\n");
         bitmap = Utils2.getBitmap(img2);
        imageView.setImageBitmap( bitmap );
        t_date.setText( date.get( position ) );
        t_time.setText( time.get( position ) );
        if (position +1  == date.size()){
            next.setVisibility( View.GONE );
            next.setEnabled( false );
        }

        Animation zoom = AnimationUtils.loadAnimation( imageActivity.this, R.anim.animzoomin );
        imageView.startAnimation( zoom );
    }

    public void controlClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.nextt:
                if (position <  date.size()){
                    Log.e("psition85", String.valueOf( position ) );
                    Log.e("psition855", String.valueOf( time.size() ) );
                    position++;
                    img2=image.get(position).replace("\\n","\n");
                    bitmap = Utils2.getBitmap(img2);
                    imageView.setImageBitmap( bitmap );
                    t_date.setText( date.get( position ) );
                    t_time.setText( time.get( position ) );
                    Log.e("psition", String.valueOf( position ) );
                    Log.e("psition2", String.valueOf( time.size() ) );





                }else {
                    Toast.makeText( this, "END", Toast.LENGTH_SHORT ).show();
                    next.setVisibility( View.GONE );
                    next.setEnabled( false );
                    Log.e("psition3", String.valueOf( position ) );
                    Log.e("psition4", String.valueOf( time.size() ) );


                }
                break;
            case R.id.previous:
                if (position == 0){



                        previous.setVisibility( View.GONE );
                        previous.setEnabled( false );
                        Toast.makeText( this, "gggggggggg", Toast.LENGTH_SHORT ).show();
//                    final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
//                    //inflate view
//                    View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );
//
//                    snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
//                    Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
//                    snackBarView.setPadding( 0, 0, 0, 0 );
//
//                    ((TextView) custom_view.findViewById( R.id.message )).setText( "There is no Photo" );
//                    ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
//                    (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.red_500 ) );
//                    snackBarView.addView( custom_view, 0 );
//                    snackbar.show();


                }else {
                    position--;
                    previous.setVisibility( View.VISIBLE );
                    previous.setEnabled( true );
                    img2=image.get(position).replace("\\n","\n");
                    bitmap = Utils2.getBitmap(img2);
                    imageView.setImageBitmap( bitmap );
                    t_date.setText( date.get( position ) );
                    t_time.setText( time.get( position ) );
                }
                break;


        }
    }
}
