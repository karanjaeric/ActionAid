package threemodernsystems.com.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    String username, password;
    String login_ur = SaccoUtil.getUrl() + "login";
    android.support.v7.app.AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //open borrow loan activity
        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
//                startActivity(intent);
                password = ((EditText) findViewById(R.id.et_password)).getText().toString();
                username = ((EditText) findViewById(R.id.phoneNumber)).getText().toString();
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("username", username);
                    jsonObject.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                postData(login_ur, jsonObject);


            }
        });

        Button registerButton = findViewById(R.id.btn_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterUser.class);
                startActivity(intent);
            }
        });

    }

    public void postData(String url, final JSONObject postparams) {
        builder1 = new android.support.v7.app.AlertDialog.Builder(LoginActivity.this);
        builder1.setMessage("Login..");
        builder1.setCancelable(false);
        final android.support.v7.app.AlertDialog alertDialog1 = builder1.create();
        alertDialog1.show();


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, postparams,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        try {
                            JSONObject responseObject=new JSONObject(response.toString());
                            String success=responseObject.getString("success");
                            String message=responseObject.getString("message");
                            String loanLimit=responseObject.getString("loanLimit");
                            String interestRate=responseObject.getString("interestRate");
                            String saccoNumber=responseObject.getString("saccoNumber");
                            String name=responseObject.getString("name");
                            String phoneNumber=responseObject.getString("phoneNumber");
                            if(success.equals("true")){
                                SharedPreferences.Editor editor = getSharedPreferences("AppPreferences", MODE_PRIVATE).edit();
                                editor.putString("interestRate", interestRate);
                                editor.putString("loanLimit", loanLimit);
                                editor.putString("name", name);
                                editor.putString("saccoNumber", saccoNumber);
                                editor.putString("phoneNumber", phoneNumber);
                                editor.apply();
                                alertDialog1.hide();
                                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                startActivity(intent);

                            }else{
                                alertDialog1.hide();
                                final android.support.v7.app.AlertDialog.Builder failureBuilder
                                        = new android.support.v7.app.AlertDialog
                                        .Builder(LoginActivity.this);

                                failureBuilder.setTitle("Login Status");
                                failureBuilder.setCancelable(false);
                                failureBuilder.setMessage(message);

                                failureBuilder
                                        .setPositiveButton(
                                                "Try Again",
                                                new DialogInterface
                                                        .OnClickListener() {

                                                    @Override
                                                    public void onClick(DialogInterface dialog,
                                                                        int which) {


                                                    }
                                                });
                                final android.support.v7.app.AlertDialog failureAlertDialog = failureBuilder.create();

                                failureAlertDialog.show();


                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        alertDialog1.hide();
                        final android.support.v7.app.AlertDialog.Builder failureBuilder
                                = new android.support.v7.app.AlertDialog
                                .Builder(LoginActivity.this);

                        failureBuilder.setTitle("Login Status");
                        failureBuilder.setCancelable(false);
                        failureBuilder.setMessage(error.toString());

                        failureBuilder
                                .setPositiveButton(
                                        "Try Again",
                                        new DialogInterface
                                                .OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which) {


                                            }
                                        });
                        final android.support.v7.app.AlertDialog failureAlertDialog = failureBuilder.create();

                        failureAlertDialog.show();
                    }

                }) {
            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleySingleton.getVolleySingletonInstance(LoginActivity.this).addToRequestque(jsonObjReq);

    }

    @Override
    public void onBackPressed() {
        // Create the object of
        // AlertDialog Builder class
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(LoginActivity.this);
        // Set the message show for the Alert time
        builder.setMessage("Sure Want To Exit?");
        // Set Alert Title
        builder.setTitle("APP EXIT");
        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);
        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // When the user click yes button
                                // then app will close
                                finish();
                            }
                        });
        // Set the Negative button with No name
        // OnClickListener method is use
        // of DialogInterface interface.
        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });
// Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
    }
}
