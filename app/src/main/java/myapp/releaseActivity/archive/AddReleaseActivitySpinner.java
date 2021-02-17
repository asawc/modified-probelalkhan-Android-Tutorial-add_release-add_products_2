package myapp.releaseActivity.archive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.Toast;

import net.simplifiedcoding.simplifiedcoding.R;

import java.util.ArrayList;
import java.util.List;

import myapp.api.ApiBuilder;
import myapp.api.MyApi;
import myapp.api.ResponseContainer;
import myapp.model.Employee;
import myapp.modelView.EmployeeView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddReleaseActivitySpinner extends AppCompatActivity {

    List<EmployeeView> employeeViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_add_activity_spinner);
        employeeViews = new ArrayList<EmployeeView>();

        final Spinner spin = (Spinner) findViewById(R.id.employees_spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("Item_Selected", String.valueOf(position) + " " + view.getId() +
                        " " + parent.getId());
                EmployeeView emplView = (EmployeeView) spin.getItemAtPosition(position);
                employeeViews.remove(position);
                Toast.makeText(AddReleaseActivitySpinner.this, emplView.getSymbol() + " " +
                        emplView.getName() + " " + emplView.getSurname(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddReleaseActivitySpinner.this, "Błąd, AdapterViewId="+parent.getId(), Toast.LENGTH_LONG).show();
            }

        } );
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
                    employeeViews.add(new EmployeeView(emp.getSymbol(), emp.getName(), emp.getSurname()));
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
        ArrayAdapter adapter = new EmployeeAdapter(this, R.layout.employe_spinner_dropdown_item, employeeViews);
        adapter.setDropDownViewResource(R.layout.employe_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(adapter);
        // spin.setPromptId(R.id.header_textview);
    }

    public class EmployeeAdapter extends ArrayAdapter {

        public EmployeeAdapter(@NonNull Context context, int resource, @NonNull List<EmployeeView> objects) {
            super(context, resource, objects);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.employe_spinner_dropdown_item, parent, false);

            CheckedTextView symbol = (CheckedTextView) layout.findViewById(R.id.symbol);
            symbol.setText(employeeViews.get(position).getSymbol());

            CheckedTextView name = (CheckedTextView) layout.findViewById(R.id.name);
            name.setText(employeeViews.get(position).getName());

            CheckedTextView surname = (CheckedTextView) layout.findViewById(R.id.surname);
            surname.setText(employeeViews.get(position).getSurname());
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