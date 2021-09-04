package pro.kidss;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText etPhoneLogin, etPassLogin;
    private View parent_view;
    Button btnLogin;
    TextView signup;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        coordinatorLayout = (CoordinatorLayout) findViewById( R.id.coordinator );

        parent_view = findViewById( android.R.id.content );
        btnLogin = (Button) findViewById( R.id.btnLogin );
        etPhoneLogin = (TextInputEditText) findViewById( R.id.edtPhoneLogin );
        etPassLogin = (TextInputEditText) findViewById( R.id.edtPassLogin );
        signup = findViewById( R.id.sign_up );
        signup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( LoginActivity.this, RegisterActivity.class );
                startActivity( intent );
            }
        } );
        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        } );
    }


    private void submitForm() {
        if (!validatePhone()) {
            return;
        }
        if (!validatePassword()) {
            return;
        }

        loginClass.log( this, etPhoneLogin.getText().toString(), etPassLogin.getText().toString(), coordinatorLayout );

    }


    private boolean validatePhone() {
        if (etPhoneLogin.getText().toString().trim().isEmpty()) {
            snackBarIconError();
            etPhoneLogin.requestFocus();
            return false;
        } else {
//            snackBarIconSuccess();

        }
        return true;
    }

    private void snackBarIconError() {
        final Snackbar snackbar = Snackbar.make( parent_view, "", Snackbar.LENGTH_SHORT );
        //inflate view
        View custom_view = getLayoutInflater().inflate( R.layout.snackbar_icon_text, null );

        snackbar.getView().setBackgroundColor( Color.TRANSPARENT );
        Snackbar.SnackbarLayout snackBarView = (Snackbar.SnackbarLayout) snackbar.getView();
        snackBarView.setPadding( 0, 0, 0, 0 );

        ((TextView) custom_view.findViewById( R.id.message )).setText( "Please check username or password" );
        ((ImageView) custom_view.findViewById( R.id.icon )).setImageResource( R.drawable.ic_close );
        (custom_view.findViewById( R.id.parent_view )).setBackgroundColor( getResources().getColor( R.color.red_600 ) );
        snackBarView.addView( custom_view, 0 );
        snackbar.show();
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

    private boolean validatePassword() {
        if (etPassLogin.getText().toString().trim().isEmpty()) {
            snackBarIconError();
            etPassLogin.requestFocus();
            return false;
        } else {
//            snackBarIconSuccess();
        }
        return true;
    }

    private static boolean isvalidatePhone(String phone) {
        return !TextUtils.isEmpty( phone ) && Patterns.PHONE.matcher( phone ).matches();
    }
}

