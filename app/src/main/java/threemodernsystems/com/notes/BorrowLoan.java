package threemodernsystems.com.notes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BorrowLoan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_loan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText amountToBorrow=findViewById(R.id.amountToBorrow);

        SharedPreferences prefs = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        String loanLImit = prefs.getString("loanLimit", null);
        TextView qualifiedAmount=findViewById(R.id.qualifiedBorrowAmount);
        qualifiedAmount.setText(loanLImit);

        Button proceedButton=findViewById(R.id.btn_proceedToLoanDetails);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountToBorrow= ((EditText) findViewById(R.id.amountToBorrow)).getText().toString();
                Bundle bundle=new Bundle();
                bundle.putString("amountToBorrow",amountToBorrow);
                Intent intent=new Intent(BorrowLoan.this,LoanDetails.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

}
