package pro.kidss;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import pro.kidss.R;

public class Main2Activity extends AppCompatActivity {
    Dialog dialog;
    Button accept, no;
    TextView messageTv, titleTv;
    ImageView close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main2 );
        dialog = new Dialog( this );
        ShowQues();
//        AlertDialog.Builder alertClose = new AlertDialog.Builder( Main2Activity.this );
//        alertClose.setTitle( R.string.Privacy ).
//                setMessage( R.string.privacytwo ).
//                setPositiveButton( R.string.privacthree, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        goToUrl( "https://policy.kidsguard.ml/privacy" );
//
//                    }
//                } ).
//                setNegativeButton( R.string.privfour, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                } ).show();

    }

    private void ShowQues() {
        dialog.setContentView( R.layout.question_alert );
        titleTv = (TextView) dialog.findViewById( R.id.title_ques );

        accept = (Button) dialog.findViewById( R.id.bt_quest_yes );
        no = (Button) dialog.findViewById( R.id.bt_quest_no );
        messageTv = (TextView) dialog.findViewById( R.id.messaage_ques );
        titleTv.setText( R.string.Privacy );
        messageTv.setText( R.string.privacytwo );
        no.setText( R.string.privacthree );
        no.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl( "https://policy.kidsguard.ml/privacy" );
            }
        } );
        accept.setText( R.string.privfour );
        accept.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        } );


        dialog.getWindow().setBackgroundDrawable( new ColorDrawable( Color.TRANSPARENT ) );
        dialog.show();
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse( url );
        Intent launchBrowser = new Intent( Intent.ACTION_VIEW, uriUrl );
        startActivity( launchBrowser );
    }

    public void btparent(View view) {
        Intent intent = new Intent( Main2Activity.this, MainActivity.class );
        startActivity( intent );
    }

    public void btchild(View view) {
        AlertDialog.Builder alertClose = new AlertDialog.Builder( Main2Activity.this );
        alertClose.setTitle( R.string.Warnning ).
                setMessage( R.string.plsdown ).
                setPositiveButton( R.string.down, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToUrl( "https://play.google.com/store/apps/details?id=pro.kds.forkids.kidsguard" );

                    }
                } ).show();

    }

    public void bthelp(View view) {
        Intent intent = new Intent( Main2Activity.this, HelpingActivity.class );
        startActivity( intent );
    }
}
