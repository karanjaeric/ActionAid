package threemodernsystems.com.notes;

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
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences prefs = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String loanLImit = prefs.getString("loanLimit", null);
        String name = prefs.getString("name", null);
        String loanAdvisory = prefs.getString("loanAdvisory", "No Loan");
        TextView loanLimitTextView=findViewById(R.id.loanLimit);
        TextView loanadvisoryTextView=findViewById(R.id.loanadvisory);
        loanadvisoryTextView.setText(loanAdvisory);
        loanLimitTextView.setText(loanLImit);
        TextView appUserLBLView =findViewById(R.id.appUserLBL);
        appUserLBLView.setText("Account: "+name);
        Button borrowLoan = findViewById(R.id.borrowLoan);
        borrowLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, BorrowLoan.class);
                startActivity(intent);

            }

        });

        Button repayLoan = findViewById(R.id.repayLoan);
        repayLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, LoanRepayment.class);
                startActivity(intent);

            }

        });
    }
}
