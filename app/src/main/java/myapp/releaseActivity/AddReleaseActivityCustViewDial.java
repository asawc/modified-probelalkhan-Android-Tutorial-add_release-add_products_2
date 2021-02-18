package myapp.releaseActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import net.simplifiedcoding.simplifiedcoding.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import myapp.model.Product;
import myapp.api.ApiBuilder;
import myapp.api.CallbackImpl;
import myapp.api.MyApi;
import myapp.api.ResponseContainer;
import myapp.model.Employee;
import myapp.modelView.EmployeeView;
import myapp.modelView.ProductInputView;
import myapp.modelView.ProductView;
import retrofit2.Call;
import retrofit2.Response;

public class AddReleaseActivityCustViewDial extends AppCompatActivity {

    private Integer emplPosition;
    private boolean emplChecked;
    private List<EmployeeView> employeeViews;
    private List<Employee> employeeList;
    private final String GET_EMPLOYEES_TAG = "GET_EMPLOYEES";

    private final String GET_PRODUCTS_TAG = "GET_PRODUCTS";
    private List<ProductInputView> productViews;
    private List<Product> productList;
    private boolean[] checkedProducts;
    private CharSequence[] prodsInChars;

    private final MyApi apiService;

    public AddReleaseActivityCustViewDial() {
        apiService = new ApiBuilder().getApiService();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_add_activity);

        productViews = new ArrayList<ProductInputView>();
        productList = new ArrayList<Product>();

        emplChecked = false;
        emplPosition = null;
        employeeViews = new ArrayList<EmployeeView>();
        employeeList = new ArrayList<Employee>();

        final Button productsButton = (Button) findViewById(R.id.products_button);
        final Button employeeButton = (Button) findViewById(R.id.employees_button);
        final Button addReleaseButton = (Button) findViewById(R.id.butonAddRelease);

        final ListView prodsListView = findViewById(R.id.products_list);

//*******// PRACOWNICY ******************************************************************
        // Pobranie pracowników
        getEmployees();

        final EmployeeAdapter<EmployeeView> adapter = new EmployeeAdapter(this, R.layout.employe_spinner_dropdown_item, employeeViews);
        adapter.setDropDownViewResource(R.layout.employe_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(view.getContext(), "Button Clicked", Toast.LENGTH_LONG).show();
                //final View dialogHeaderView = findViewById(R.id.header_dialog_employees_textview);
                LayoutInflater inflater = getLayoutInflater();
                View dialogHeaderView = inflater.inflate(R.layout.release_add_activity_header_dialog, null);
                new AlertDialog.Builder(AddReleaseActivityCustViewDial.this)
                        .setCustomTitle(dialogHeaderView)
                        .setAdapter(adapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                emplChecked = true;
                                emplPosition = which;
                                String emplText = adapter.getItem(which).getSymbol()+"  "+
                                        adapter.getItem(which).getName()+"  "+
                                        adapter.getItem(which).getSurname();
                                Toast.makeText(adapter.getContext(), "Item Clicked: "+ which +"\n"+
                                        emplText, Toast.LENGTH_LONG).show();
                                // Wyświetlenie wybranego pracownika
                                /* LayoutInflater inflater = getLayoutInflater();
                                View viewActivity = inflater.inflate(R.layout.release_add_activity_dialog, null).;
                                viewActivity.findViewById(R.id.employeeChecked_textView); */
                                TextView checkedEmplTextView = (TextView) findViewById(R.id.employeeChecked_textView);
                                checkedEmplTextView.setText(emplText);
                                checkedEmplTextView.setVisibility(View.VISIBLE);

                                dialog.dismiss();
                            }
                        }).create().show();
            }
        });

//*******// PRODUKTY ******************************************************************* /
        //pobranie produktów
        getProducts();
        Log.d("ProductListSize", String.valueOf(productList.size()));


        final ProductAdapter<ProductView> adapter_prod = new ProductAdapter(this, R.layout.product_spinner_dropdown_item, productViews);
        adapter_prod.setDropDownViewResource(R.layout.product_spinner_dropdown_item);

        /*final ProductInputAdapter<ProductInputView> productsInputAdapter =
                new ProductInputAdapter<ProductInputView>(this,
                        R.layout.release_add_activity_prod_list_item, productViews);
        prodsListView.setAdapter(productsInputAdapter);*/
       // final ProductAdapter<ProductView> prodAdapter = new ProductAdapter<ProductView>(this, R.layout.product_spinner_dropdown_item, productViews);
      //  prodAdapter.setDropDownViewResource(R.layout.product_spinner_dropdown_item);
        productsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                TextView dialogHeaderView = (TextView) inflater.inflate(R.layout.release_add_activity_header_dialog, null);
                dialogHeaderView.setText("Select Products...");
                final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddReleaseActivityCustViewDial.this);
                alertBuilder.setCustomTitle(dialogHeaderView);
                alertBuilder.setMultiChoiceItems(prodsInChars, checkedProducts, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //Toast.makeText(alertBuilder.getContext(), "Item Clicked: "+ which +"\n"+
                         //      "IsChecked "+ String.valueOf(isChecked), Toast.LENGTH_LONG).show();
                        checkedProducts[which] = isChecked;
                    }
                });
                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Czyści listę produktów
                        if(!productViews.isEmpty()) {
                            productViews.clear();
                            adapter_prod.notifyDataSetChanged();
                            //productsInputAdapter.notifyDataSetChanged();
                        }

                        // Tworzy nową listę produktów zaznaczonych
                        for (int i=0; i < checkedProducts.length; i++ ) {
                            if(checkedProducts[i]) {
                                Product p = productList.get(i);
                                ProductInputView pView = new ProductInputView(p.getSymbol(), p.getName(), p.getQuantity(), null);
                                productViews.add(pView);
                            }
                        }
                        // zmiana w widoku listy (ListView)
                        if (!productViews.isEmpty())
                            adapter_prod.notifyDataSetChanged();
                            //productsInputAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0; i < checkedProducts.length; i++ ) {
                            checkedProducts[i] = false;
                            Product p = productList.get(i);
                            for(ProductView pView : productViews) {
                                if(pView.getSymbol().equals(p.getSymbol()))
                                    checkedProducts[i] = true;
                            }
                        }
                        dialog.dismiss();
                    }
                }).create().show();

            }
        });

        // jeżeli wybrany jest pracownik i przynajmniej jeden produkt
        // zrób główny przycisk clickable i nasłuchuj kliknięcia
        if(emplChecked && productViews.isEmpty() == false) {
            addReleaseButton.setClickable(true);
            addReleaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("addReleaseButton", "Main Button Clicked");
                }
            });
        }
    }


    public void getEmployees() {
        // wykonanie zapytania - pobranie pracowników
        Call<ResponseContainer<List<Employee>>> call = apiService.getEmployees();
        call.enqueue(new CallbackImpl<ResponseContainer<List<Employee>>>(GET_EMPLOYEES_TAG) {
            @Override
            public void onResponse(Call<ResponseContainer<List<Employee>>> call,
                                   Response<ResponseContainer<List<Employee>>> response) {
                super.onResponse(call, response);

                ResponseContainer<List<Employee>> resCon = response.body();
                Log.d(getTag(), resCon.getMessage());

                employeeList = resCon.getObject();
                for(Employee emp : employeeList) {
                    employeeViews.add(new EmployeeView(emp.getSymbol(), emp.getName(), emp.getSurname()));
                    Log.d(getTag(), emp.getSymbol()+ " " + emp.getName() + " " +
                            emp.getSurname());
                }
            }
        });
    }

    public void getProducts () {
        Call<ResponseContainer<List<Product>>> callProducts = apiService.getProducts();
        callProducts.enqueue(new CallbackImpl<ResponseContainer<List<Product>>>(GET_PRODUCTS_TAG){
            @Override
            public void onResponse(Call<ResponseContainer<List<Product>>> call,
                                   Response<ResponseContainer<List<Product>>> response) {
                super.onResponse(call, response);
                ResponseContainer<List<Product>> resCon = response.body();
                Log.d(getTag(), resCon.getMessage());

                productList = resCon.getObject();
                checkedProducts = new boolean[productList.size()];
                for(int i=0; i < productList.size(); i++){
                    checkedProducts[i] = false;
                }

                prodsInChars = new CharSequence[productList.size()];
                int i=0;
                for(Product prod : productList) {
                    String strProd = prod.getSymbol()+ "  " + prod.getName() + "  " +
                            String.valueOf(prod.getQuantity());
                    // productViews.add(new ProductView(prod.getSymbol(), prod.getName(), prod.getQuantity()));
                    //prodsInString.add(strProd);
                    prodsInChars[i]=strProd;
                    i++;
                    Log.d(getTag(), strProd);
                }
                Log.d("ProdsInCharsSize ", String.valueOf(prodsInChars.length));
            }
        });
    }

    public class ProductInputAdapter<T> extends ArrayAdapter<T> {

        private int mResource;

        public ProductInputAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
            mResource = resource;
        }

        public View getCustomView(final int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(mResource, parent, false);

            TextView symbol = (TextView) layout.findViewById(R.id.symbol);
            symbol.setText(productViews.get(position).getSymbol());

            TextView name = (TextView) layout.findViewById(R.id.name);
            name.setText(productViews.get(position).getName());

            TextView quantity = (TextView) layout.findViewById(R.id.quantity);
            quantity.setText(String.valueOf(productViews.get(position).getQuantity()));

            final EditText reqQuantity = (EditText) layout.findViewById(R.id.requested_quantity);
            reqQuantity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE) {
                        String text = v.getText().toString();
                        productViews.get(position).setRequestedQuantity(Integer.parseInt(text));
                        reqQuantity.setText(text);
                        return true;
                    }
                    return false;
                }
            });
            Integer intQuantity = productViews.get(position).getRequestedQuantity();
            String text="";
            if(intQuantity != null)
                text = String.valueOf(productViews.get(position).getRequestedQuantity());
            reqQuantity.setText(text);
            Log.d("EditText", text);
            return layout;
        }

        // It gets a View that displays the data at the specified position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        private class EditTextWatcher implements TextWatcher {
            public String mTemp="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                mTemp = mTemp + s.toString();
                // Log.d("EditTextChanged", s.toString());
            }
        }
    }

    public class EmployeeAdapter<T> extends ArrayAdapter<T> {

        public EmployeeAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.employe_spinner_dropdown_item, parent, false);

            TextView symbol = (TextView) layout.findViewById(R.id.symbol);
            symbol.setText(employeeViews.get(position).getSymbol());

            TextView name = (TextView) layout.findViewById(R.id.name);
            name.setText(employeeViews.get(position).getName());

            TextView surname = (TextView) layout.findViewById(R.id.surname);
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

    public class ProductAdapter<T> extends ArrayAdapter<T> {

        public ProductAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
            super(context, resource, objects);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.product_spinner_dropdown_item, parent, false);

            TextView symbol = (TextView) layout.findViewById(R.id.symbol);
            symbol.setText(productViews.get(position).getSymbol());

            TextView name = (TextView) layout.findViewById(R.id.name);
            name.setText(productViews.get(position).getName());

            TextView quantity = (TextView) layout.findViewById(R.id.product_quantity);
            quantity.setText(String.valueOf(productViews.get(position).getQuantity()));

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