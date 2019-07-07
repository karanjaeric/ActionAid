package threemodernsystems.com.notes;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoanRepayment extends AppCompatActivity {
    android.support.v7.app.AlertDialog.Builder builder1;
    String loanRepayment_ur=SaccoUtil.getUrl()+"saccoloanrepayment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_repayment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button repayNowButton=findViewById(R.id.repayNowButton);
        EditText repaymentAmountET=findViewById(R.id.amountToRepay);
        final String amountToPay=repaymentAmountET.getText().toString();
        repayNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject=new JSONObject();
                SharedPreferences prefs = getSharedPreferences("AppPreferences", MODE_PRIVATE);
                String phoneNumber = prefs.getString("phoneNumber", null);
                EditText repaymentAmountET=findViewById(R.id.amountToRepay);
                final String amountToPay=repaymentAmountET.getText().toString();

                try{

                    jsonObject.put("phoneNumber",phoneNumber);
                    jsonObject.put("amount",amountToPay);
                    postData(loanRepayment_ur,jsonObject);

                }catch(Exception e){

                }


            }
        });
    }
    public  void postData(String url,JSONObject postparams){
        builder1=new android.support.v7.app.AlertDialog.Builder(LoanRepayment.this);
        builder1.setMessage("Repaying Loan..");
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
                                        .Builder(LoanRepayment.this);

                                successBuilder.setTitle("Repaymnet Status");
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

//                                                        Intent intent=new Intent(LoanRepayment.this,LoginActivity.class);
//                                                        startActivity(intent);

                                                    }
                                                });
                                android.support.v7.app.AlertDialog successAlertDialog = successBuilder.create();
                                successAlertDialog.show();

                            }else{
                                alertDialog1.hide();
                                final android.support.v7.app.AlertDialog.Builder failureBuilder
                                        = new android.support.v7.app.AlertDialog
                                        .Builder(LoanRepayment.this);

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
                                .Builder(LoanRepayment.this);

                        failureBuilder.setTitle("Repayment Status");
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
        VolleySingleton.getVolleySingletonInstance(LoanRepayment.this).addToRequestque(jsonObjReq);

    }

}
