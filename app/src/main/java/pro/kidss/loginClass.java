package pro.kidss;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class loginClass {
    CoordinatorLayout coordinatorLayout;

    public static void log(final Context context, final String phoneNumer, final String pass, CoordinatorLayout coordinatorLayout) {


        String url = "https://apisender.online/api/login/";
        StringRequest stringRequest = new StringRequest( Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonlogin = new JSONObject( response );
                            String status = jsonlogin.getString( "status" );
                            String message = jsonlogin.getString( "message" );
                            switch (status) {
                                case "ok":
                                    ColoredSnackbar.success( Snackbar.make( coordinatorLayout, " Login Successful ",
                                            Snackbar.LENGTH_SHORT ) )
                                            .show();
//                                    Toast.makeText( context, message, Toast.LENGTH_LONG ).show();
                                    final SharedPreferences shared = context.getSharedPreferences( "prefs", MODE_PRIVATE );
                                    final SharedPreferences.Editor editor = shared.edit();
                                    editor.putBoolean( "FIRSTRUN", false );
                                    editor.commit();
                                    String owner = jsonlogin.getString( "token" );
                                    OwnerDataBaseManager own = new OwnerDataBaseManager( context );
                                    own.Insertowner( owner );
                                    Intent intent = new Intent( context, getChildActivity.class );
                                    intent.putExtra( "activity", "first" );
                                    context.startActivity( intent );
                                    break;
                                default:
                                    ColoredSnackbar.error( Snackbar.make( coordinatorLayout, " Username or Password is Wrong! ",
                                            Snackbar.LENGTH_SHORT ) )
                                            .show();
//                                    Toast.makeText( context, "Username or Password is wrong!", Toast.LENGTH_LONG ).show();
                                    SendEror.sender( context, message );
                                    break;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            SendEror.sender( context, e.toString() );
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ColoredSnackbar.warning( Snackbar.make( coordinatorLayout, "Please Check The Connection",
                        Snackbar.LENGTH_SHORT ) )
                        .show();
//                Toast.makeText( context, "please check the connection", Toast.LENGTH_LONG ).show();
                SendEror.sender( context, error.toString() );
            }

        } ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put( "username", phoneNumer );
                params.put( "password", pass );
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue( context );
        requestQueue.add( stringRequest );
    }


}
