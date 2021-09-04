package pro.kidss;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText etPhoneRegister, etPassRegister;
    EditText etEmailRegister;
    Button btnRegister;
    TextView signin;
    private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_register );
        parent_view = findViewById( android.R.id.content );
        etPhoneRegister = (TextInputEditText) findViewById( R.id.edtPhoneRegister );
        //etEmailRegister = (EditText)view.findViewById(pro.kidss.R.id.edtEmailRegister);
        etPassRegister = (TextInputEditText) findViewById( R.id.edtPassRegister );
        btnRegister = (Button) findViewById( R.id.btnRegister );
        signin = findViewById( R.id.sign_in );
        signin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RegisterActivity.this, LoginActivity.class );
                startActivity( intent );
            }
        } );
        btnRegister.setOnClickListener( new View.OnClickListener() {
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

        RegisterClass.regis( this, etPhoneRegister.getText().toString(), etPassRegister.getText().toString(), "" );


    }


    private boolean validatePhone() {
        String phone = etPhoneRegister.getText().toString().trim();
        if (phone.isEmpty()) {
            snackBarIconError();
            etPhoneRegister.requestFocus();
            return false;
        } else {
            snackBarIconSuccess();
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

    private boolean validateEmail() {
        String email = etEmailRegister.getText().toString().trim();
        if (email.isEmpty() || !isvalidateEmail( email )) {

//            inputLayoutEmail.setError( getString( R.string.err_email ) );
            etEmailRegister.requestFocus();
            return false;
        } else {
//            inputLayoutEmail.setErrorEnabled( false );
        }
        return true;
    }

    private boolean validatePassword() {
        if (etPassRegister.getText().toString().trim().isEmpty()) {
            snackBarIconError();

            etPassRegister.requestFocus();
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


    private static boolean isvalidatePhone(String phone) {
        return !TextUtils.isEmpty( phone ) && Patterns.PHONE.matcher( phone ).matches();
    }

    private static boolean isvalidateEmail(String email) {
        return !TextUtils.isEmpty( email ) && Patterns.EMAIL_ADDRESS.matcher( email ).matches();
    }
}

