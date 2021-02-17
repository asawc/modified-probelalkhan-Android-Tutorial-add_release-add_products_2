package myapp.releaseActivity.archive;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import net.simplifiedcoding.simplifiedcoding.R;

import java.util.ArrayList;
import java.util.List;

import myapp.api.ApiBuilder;
import myapp.api.MyApi;
import myapp.api.ResponseContainer;
import myapp.model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReleaseActivityStringDialog extends AppCompatActivity {

    List<String> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_add_activity);
        employees = new ArrayList<String>();

        final Button button = (Button) findViewById(R.id.employees_button);

        // wykonanie zapytania - pobranie pracowników
        MyApi apiService = new ApiBuilder().getApiService();
        Call<ResponseContainer<List<Employee>>> call = apiService.getEmployees();
        call.enqueue(new Callback<ResponseContainer<List<Employee>>>() {
            @Override
            public void onResponse(Call<ResponseContainer<List<Employee>>> call,
                                   Response<ResponseContainer<List<Employee>>> response) {
                int statusCode = response.code();
                ResponseContainer<List<Employee>> resCon = response.body();
                Log.d("GET_EMPLOYEES", response.message());
                Log.d("GET_EMPLOYEES", response.toString());
                Log.d("GET_EMPLOYEES", resCon.getMessage());

                List<Employee> emplsList = resCon.getObject();
                for(Employee emp : emplsList) {
                   // employees.add(new EmployeeView(emp.getSymbol(), emp.getName(), emp.getSurname()));
                    employees.add(emp.getSymbol()+"  "+emp.getName()+"  "+emp.getSurname());
                    Log.d("GET_EMPLOYEES", emp.getSymbol()+ " " + emp.getName() + " " +
                            emp.getSurname());
                }
            }

            @Override
            public void onFailure(Call<ResponseContainer<List<Employee>>> call, Throwable t) {
                // Log error here since request failed
                Log.d("GET_EMPLOYEES", "FAIL");
                Log.d("GET_EMPLOYEES", t.getMessage());
                Log.d("GET_EMPLOYEES", t.getLocalizedMessage());
                t.printStackTrace();
            }
        });

        //R.layout.spinner_dropdown_item
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, employees);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(button.getContext())
                        .setTitle("Select employee")
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(adapter.getContext(), "Item Clicked: "+ which +"\n"+
                                        adapter.getItem(which), Toast.LENGTH_LONG).show();
                                // TODO: user specific action

                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });
    }


    public class EmployeeAdapter extends ArrayAdapter {

        public EmployeeAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.employe_spinner_dropdown_item, parent, false);
/*
            CheckedTextView symbol = (CheckedTextView) layout.findViewById(R.id.symbol);
            symbol.setText(employeeViews.get(position).getSymbol());

            CheckedTextView name = (CheckedTextView) layout.findViewById(R.id.name);
            name.setText(employeeViews.get(position).getName());

            CheckedTextView surname = (CheckedTextView) layout.findViewById(R.id.surname);
            surname.setText(employeeViews.get(position).getSurname());
            */
/*
            if(position == 0) {
                employeeViews.add(position, new EmployeeView("", "", ""));
                symbol.setVisibility(View.GONE);
                name.setVisibility(View.GONE);
                surname.setVisibility(View.GONE);

                TextView headerTextView = (TextView) layout.findViewById(R.id.header_textview);
                headerTextView.setVisibility(View.VISIBLE);
            }
            */
            return layout;
        }

        // It gets a View that displays in the drop down popup the data at the specified position
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }
    }

}