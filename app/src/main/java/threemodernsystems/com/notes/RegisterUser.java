package threemodernsystems.com.notes;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity {
    Button registerUser;
    String memberNo,phoneNumber,idNumber,password;

    String register_ur=SaccoUtil.getUrl()+"user";
    android.support.v7.app.AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        registerUser=findViewById(R.id.registerUser);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                memberNo=((EditText)findViewById(R.id.registrationMemberNo)).getText().toString();
                phoneNumber=((EditText)findViewById(R.id.et_phoneNumber)).getText().toString();
                idNumber=((EditText)findViewById(R.id.et_idNumber)).getText().toString();
                password=((EditText)findViewById(R.id.registerPassword)).getText().toString();

                if(phoneNumber.isEmpty() || password.isEmpty()){
                }else{

                    try{
                        JSONObject jsonObject=new JSONObject();
                        jsonObject.put("username",phoneNumber);
                        jsonObject.put("password",password);
                        jsonObject.put("phoneNumber",phoneNumber);
                        jsonObject.put("idNumber",idNumber);
                        jsonObject.put("saccoNumber",memberNo);
                        jsonObject.put("accountApproved","YES");
                        postData(register_ur,jsonObject);

                    }catch(Exception e){

                    }
                }

            }
        });

    }

public  void postData(String url,JSONObject postparams){
        builder1=new android.support.v7.app.AlertDialog.Builder(RegisterUser.this);
builder1.setMessage("Registering..");
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
                        if(success.equals("true")){
                            alertDialog1.hide();
                            android.support.v7.app.AlertDialog.Builder successBuilder
                                    = new android.support.v7.app.AlertDialog
                                    .Builder(RegisterUser.this);

                            successBuilder.setTitle("Registration Status");
                            successBuilder.setCancelable(false);
                            successBuilder.setMessage(message+" Click OK To Login");

                            successBuilder
                                    .setPositiveButton(
                                            "OK",
                                            new DialogInterface
                                                    .OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which) {

                                                    Intent intent=new Intent(RegisterUser.this,LoginActivity.class);
                                                    startActivity(intent);

                                                }
                                            });
                            android.support.v7.app.AlertDialog successAlertDialog = successBuilder.create();
                            successAlertDialog.show();

                        }else{
                            alertDialog1.hide();
                            final android.support.v7.app.AlertDialog.Builder failureBuilder
                                    = new android.support.v7.app.AlertDialog
                                    .Builder(RegisterUser.this);

                            failureBuilder.setTitle("Registration Status");
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
                            .Builder(RegisterUser.this);

                    failureBuilder.setTitle("Registration Status");
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

            }){
        @Override
        public Map getHeaders() throws AuthFailureError {
            HashMap headers = new HashMap();
            headers.put("Content-Type", "application/json");
            return headers;
        }
    };
    VolleySingleton.getVolleySingletonInstance(RegisterUser.this).addToRequestque(jsonObjReq);

}

}
