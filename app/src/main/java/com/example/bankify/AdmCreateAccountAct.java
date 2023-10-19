package com.example.bankify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
public class AdmCreateAccountAct extends AppCompatActivity {

    EditText createFname,createLname,createAddress,createCity,createPostalC,createCountry,createPhone,createEmail;
    Spinner spnMOB,spnDOB,spnYOB, spnGender,spnProvince;
    Switch swtChecking, swtSaving, swtCredit;
    String cardNumber, provinceSelected,monthDoB,dayDoB,yearDoB,genderSelected;
    Button createAcc;
    FirebaseFirestore database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adm_create_account);

        createFname = findViewById(R.id.createFname);
        createLname = findViewById(R.id.createLname);
        createAddress = findViewById(R.id.createAddress);
        createCity = findViewById(R.id.createCity);
        createPostalC = findViewById(R.id.createPc);
        createCountry = findViewById(R.id.createCountry);
        createPhone = findViewById(R.id.createPhone);
        createEmail = findViewById(R.id.createEmail);

        spnMOB = findViewById(R.id.spnMonth);
        spnDOB = findViewById(R.id.spnDay);
        spnYOB = findViewById(R.id.spnYear);
        spnGender = findViewById(R.id.Gender);
        spnProvince = findViewById(R.id.spnProvince);

        createAcc = findViewById(R.id.btnCreateAccount);

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
                monthDoB = adapterView.getItemAtPosition(i).toString();

                Toast.makeText(AdmCreateAccountAct.this,monthDoB, Toast.LENGTH_SHORT).show();
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
                yearDoB = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdmCreateAccountAct.this,yearDoB, Toast.LENGTH_SHORT).show();
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
                dayDoB = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdmCreateAccountAct.this,dayDoB, Toast.LENGTH_SHORT).show();
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
                genderSelected = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(AdmCreateAccountAct.this,genderSelected, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AdmCreateAccountAct.this, "Select month", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> provinces = new ArrayList<String>();
        provinces.add("Alberta");
        provinces.add("British Columbia");
        provinces.add("Manitoba");
        provinces.add("New Brunswick");
        provinces.add("New Foundland and Labrador");
        provinces.add("Nova Scotia");
        provinces.add("Northwest Territories");
        provinces.add("Nunavut");
        provinces.add("Ontario");
        provinces.add("Prince Edward Island");
        provinces.add("Quebec");
        provinces.add("Saskatchewan");
        provinces.add("Yukon");

        ArrayAdapter<String> adpProvinces = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,provinces);

        spnProvince.setAdapter(adpProvinces);
        spnProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                provinceSelected = item;
                Toast.makeText(AdmCreateAccountAct.this,item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(AdmCreateAccountAct.this, "Select Province", Toast.LENGTH_SHORT).show();
            }
        });

        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database = FirebaseFirestore.getInstance();
                cardNumber = createCardNumber().toString();
                String cusName = createFname.getText().toString().trim() + " " + createLname.getText().toString().trim();
                String address = createAddress.getText().toString().trim() + " " + createCity.getText().toString().trim() + " " + provinceSelected +
                        " " + createCountry.getText().toString().trim();
                String postalCode = createPostalC.getText().toString().trim();
                String cusDoB = monthDoB + "/" + dayDoB + "/" + yearDoB;
                String cusPhone = createPhone.getText().toString().trim();
                String cusEmail = createEmail.getText().toString().trim();

                if(cardNumber.isEmpty() || cusName.isEmpty() || address.isEmpty() || postalCode.isEmpty()||cusDoB.isEmpty() || genderSelected.isEmpty() ||
                cusPhone.isEmpty() || cusEmail.isEmpty()){
                    Toast.makeText(AdmCreateAccountAct.this, "Please fill all", Toast.LENGTH_SHORT).show();
                    return;
                }else {

                    Model model = new Model(cardNumber,cusName,cusDoB,genderSelected,address,postalCode,cusPhone,cusEmail);
                    database.collection("accountDetails")
                            .add(model)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(AdmCreateAccountAct.this, "Account created", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AdmCreateAccountAct.this, home.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(AdmCreateAccountAct.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });

                    /*Model model = new Model(cardNumber,cusName,cusDoB,genderSelected,address,postalCode,cusPhone,cusEmail);
                    reference.child(cardNumber).setValue(model);
                    Toast.makeText(AdmCreateAccountAct.this, "Account created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AdmCreateAccountAct.this, home.class);
                    startActivity(intent);
                    finish();*/
                }
            }
        });

    }

    //function to create 16 random number for card number to be used onclick create account
    public String createCardNumber(){

            Random random = new Random();
            StringBuilder numberBuilder = new StringBuilder("53");

            for (int i = 2; i < 16; i++) {
                int digit = random.nextInt(10);
                numberBuilder.append(digit);
            }

            return numberBuilder.toString();
    }
}