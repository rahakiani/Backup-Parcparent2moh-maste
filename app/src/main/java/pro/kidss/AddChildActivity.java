package pro.kidss;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import pro.kidss.wlcome.WelcomeActivity;

public class AddChildActivity extends AppCompatActivity {
    TextInputEditText etChildName;
    EditText etIMEI;
    String activity;
    private View parent_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_addchild );
        parent_view = findViewById( android.R.id.content );
        activity = "";
        Intent intent = getIntent();
        activity = intent.getStringExtra( "activity" );

        findView();

    }

    //onClick attribute for Button AddChild
    public void onClickAddChild(View view) {
        submitForm();
        add( AddChildActivity.this, "", etChildName.getText().toString() );

        // input here codes
    }

    private void submitForm() {
        if (!validatePhone()) {
            return;
        }

    }

    private boolean validatePhone() {
        if (etChildName.getText().toString().trim().isEmpty()) {
            snackBarIconError();
            return false;
        } else {
            snackBarIconSuccess();

        }
        return true;
    }

    private void snackBarIconSuccess() {
        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Success!" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_done );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.green_500 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
    }


    private void snackBarIconError() {
        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please check Child name" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.red_600 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
    }

    //findView method for input and populate all elements
    public void findView() {
        etChildName = (TextInputEditText) findViewById( R.id.edtChildName );


    }

    public void add(final Context context, final String imeichild, final String childName) {
        String url = "https://apisender.online/api/add-kid/";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonlogin = new JSONObject( response );
                            String status = jsonlogin.getString( "status" );

                            switch (status) {
                                case "ok":
                                    String code = jsonlogin.getString( "code" );
                                    Intent intent = new Intent( context, AddKidCodeActivity.class );
                                    intent.putExtra( "StringAddKidCode", code );
                                    startActivity( intent );

                                    break;
                                default:
                                    String message = jsonlogin.getString( "message" );
//                                    Toast.makeText( AddChildActivity.this, message, Toast.LENGTH_LONG ).show();
                                    SendEror.sender( AddChildActivity.this, message.toString() );
                                    break;

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender( AddChildActivity.this, e.toString() );
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                snackBarIconError();
//                Toast.makeText( context, "please check the connection", Toast.LENGTH_LONG ).show();
                SendEror.sender( AddChildActivity.this, error.toString() );
            }

        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "kidName", childName );
                params.put( "parentToken", getparentToken( context ) );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( context );
        requestQueue.add( stringRequest );

    }

    public String getparentToken(Context c) {
        OwnerDataBaseManager own = new OwnerDataBaseManager( c );
        return own.getowner();
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        if (intent.getStringExtra( "activity" ).isEmpty()) {
            snackBarIconError();
            super.onBackPressed();
        } else {
            if (intent.getStringExtra( "activity" ).equals( "child1" )) {
                allert();
            } else if (intent.getStringExtra( "activity" ).equals( "child2" )) {
                Intent intent1 = new Intent( AddChildActivity.this, WelcomeActivity.class );
                startActivity( intent1 );
            } else if (intent.getStringExtra( "activity" ).equals( "welcome" )) {
                Intent intent1 = new Intent( AddChildActivity.this, WelcomeActivity.class );
                startActivity( intent1 );
            } else {
                allert();

            }
        }
    }

    public void btndl(View view) {
        goToUrl( "https://play.google.com/store/apps/details?id=pro.kds.forkids.kidsguard" );
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse( url );
        Intent launchBrowser = new Intent( Intent.ACTION_VIEW, uriUrl );
        startActivity( launchBrowser );
    }

    public void allert() {
        AlertDialog.Builder alertClose = new AlertDialog.Builder( AddChildActivity.this );
        alertClose.setTitle( R.string.titleExitConfirm ).
                setMessage( R.string.bodyExitConfirm ).
                setPositiveButton( R.string.acceptExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent( AddChildActivity.this, VoroodActivity.class );
                        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        intent.putExtra( "EXIT", true );
                        startActivity( intent );
                    }
                } ).
                setNegativeButton( R.string.declineExitConfirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } ).show();
    }
}

