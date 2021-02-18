package myapp;

import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.simplifiedcoding.simplifiedcoding.R;
import net.simplifiedcoding.simplifiedcoding.SharedPrefManager;

import myapp.model.Product;

//import androidx.appcompat.app.AppCompatActivity;

public class EmployeeInfoActivity extends AppCompatActivity {

    TextView textViewEmployeeSurname, textViewEmployeeName, textViewEmployeeSymbol;
    private Button buttonScan5 /*, buttonAddProductActivity*/;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_info);

        //buttonScan2 = (Button) findViewById(R.id.buttonScan2);
        //buttonAddProductActivity = (Button) findViewById(R.id.buttonAddProductActivity);
        //if the user is not logged in
        //starting the login activity
        /*if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/


        textViewEmployeeSurname = (TextView) findViewById(R.id.textViewEmployeeSurname);
        textViewEmployeeName = (TextView) findViewById(R.id.textViewEmployeeName);
        textViewEmployeeSymbol = (TextView) findViewById(R.id.textViewEmployeeSymbol);


        //getting the current employee
        Employee employee = SharedPrefManager.getInstance(this).getEmployee();

        //setting the values to the textviews
        textViewEmployeeSurname.setText(employee.getEmployeeSurname());
        textViewEmployeeName.setText(employee.getEmployeeName());
        textViewEmployeeSymbol.setText(employee.getEmployeeSymbol());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });

        //when the user presses scan button
        //calling the ScanActivity
        findViewById(R.id.buttonScan5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openScanActivity();
            }
        });

        /*findViewById(R.id.buttonAddProductActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                openAddProductActivity();
            }
        });*/
    }

    public void openScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        startActivity(intent);
    }

    /*public void openAddProductActivity() {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivity(intent);
    }*/
}
