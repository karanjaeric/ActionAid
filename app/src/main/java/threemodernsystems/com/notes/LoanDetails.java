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
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LoanDetails extends AppCompatActivity {
    android.support.v7.app.AlertDialog.Builder builder1;
    String loanRequest_ur=SaccoUtil.getUrl()+"loanrequest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences prefs = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        final String interestRate = prefs.getString("interestRate", null);

        Bundle savedBundle = getIntent().getExtras();
        final String amountToBorrow = savedBundle.getString("amountToBorrow");

        TextView loanDetailsPricipalAmount = findViewById(R.id.loanDetailsPricipalAmount);
        loanDetailsPricipalAmount.setText(amountToBorrow);
        TextView loanDetailsInterestRate = findViewById(R.id.loanDetailsInterestRate);
        loanDetailsInterestRate.setText(interestRate);
        //Compute Interest For the Chosen Amount
        BigDecimal calculatorInterestRate =new BigDecimal(interestRate);
        BigDecimal calculatorPrinciplaAmount =new  BigDecimal(amountToBorrow);
        final BigDecimal interest = calculatorInterestRate.divide(BigDecimal.valueOf(100)).multiply(calculatorPrinciplaAmount).setScale(0, RoundingMode.HALF_UP);
        final BigDecimal totalAmountDue = calculatorPrinciplaAmount.add(interest);

        TextView loanDetailsInterest = findViewById(R.id.loanDetailsInterest);
        loanDetailsInterest.setText(interest.toString());
        TextView loanDetailsTotalDue = findViewById(R.id.loanDetailsTotalDue);
        loanDetailsTotalDue.setText(totalAmountDue.toString());


        Button requestLoanButton=findViewById(R.id.requestLoanButton);
        requestLoanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences("AppPreferences", MODE_PRIVATE);
                String saccoNumber = prefs.getString("saccoNumber", null);
                String name = prefs.getString("name", null);
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("saccoNumber", saccoNumber);
                    jsonObject.put("memberName", name);
                    jsonObject.put("requestedAmount", amountToBorrow);
                    jsonObject.put("interest", interest.toString());
                    jsonObject.put("interestRate", interestRate.toString());
                    jsonObject.put("totalRepaymentAmount", totalAmountDue.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                postData(loanRequest_ur,jsonObject);

            }
        });


    }
    public  void postData(String url,JSONObject postparams){
        builder1=new android.support.v7.app.AlertDialog.Builder(LoanDetails.this);
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
                                        .Builder(LoanDetails.this);

                                successBuilder.setTitle("Loan Status");
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
                                                        SharedPreferences.Editor editor = getSharedPreferences("AppPreferences", MODE_PRIVATE).edit();
                                                        editor.putString("loanAdvisory", "Loan Pending Approval");
                                                        editor.apply();

                                                        Intent intent=new Intent(LoanDetails.this,Dashboard.class);
                                                        startActivity(intent);

                                                    }
                                                });
                                android.support.v7.app.AlertDialog successAlertDialog = successBuilder.create();
                                successAlertDialog.show();

                            }else{
                                alertDialog1.hide();
                                final android.support.v7.app.AlertDialog.Builder failureBuilder
                                        = new android.support.v7.app.AlertDialog
                                        .Builder(LoanDetails.this);

                                failureBuilder.setTitle("Loan Status");
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
                                .Builder(LoanDetails.this);

                        failureBuilder.setTitle("Loan Status");
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
        VolleySingleton.getVolleySingletonInstance(LoanDetails.this).addToRequestque(jsonObjReq);

    }

}
