package com.example.bankify;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class AdmCreateAccountAct extends AppCompatActivity {

    EditText createFname,createLname,createAddress,createCity,createPostalC,createCountry;
    Spinner spnMOB,spnDOB,spnYOB, spnGender,spnProvince;
    Switch swtChecking, swtSaving, swtCredit;

    Button createAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_create_account);

        spnMOB = findViewById(R.id.spnMonth);
        spnDOB = findViewById(R.id.spnDay);
        spnYOB = findViewById(R.id.spnYear);
        spnGender = findViewById(R.id.Gender);
        spnProvince = findViewById(R.id.spnProvince);

        ArrayList<String> months = new ArrayList<String>();
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("Mai");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("October");
        months.add("November");
        months.add("December");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,months);

        spnMOB.setAdapter(adapter);
        spnMOB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdmCreateAccountAct.this,item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AdmCreateAccountAct.this, "Select month", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = thisYear-18; i >= 1920; i--) {
            years.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, years);

        spnYOB.setAdapter(adapter1);

        spnYOB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdmCreateAccountAct.this,item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AdmCreateAccountAct.this, "Select year", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> days = new ArrayList<String>();
        for (int i = 1; i <= 31; i++) {
            days.add(Integer.toString(i));
        }
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, days);

        spnDOB.setAdapter(adapter2);

        spnDOB.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdmCreateAccountAct.this,item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AdmCreateAccountAct.this, "Select day", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> gender = new ArrayList<String>();
        gender.add("Female");
        gender.add("Male");
        gender.add("Prefer not to say");

        ArrayAdapter<String> adpGender = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,gender);

        spnGender.setAdapter(adpGender);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdmCreateAccountAct.this,item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AdmCreateAccountAct.this, "Select month", Toast.LENGTH_SHORT).show();
            }
        });
    }
}