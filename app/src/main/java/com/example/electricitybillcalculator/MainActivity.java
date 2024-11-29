package com.example.electricitybillcalculator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    EditText etKwh, etRebate;
    Button btnCalc, btnClear;
    TextView tvFinal, tvTcharge, tvRreceive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView toolbarTitle = findViewById(R.id.toolbar_title);

        etKwh = findViewById(R.id.etKwh);
        etRebate = findViewById(R.id.etRebate);
        btnCalc = findViewById(R.id.btnCalc);
        btnClear = findViewById(R.id.btnClear);
        tvTcharge = findViewById(R.id.tvTcharge);
        tvRreceive = findViewById(R.id.tvRreceive);
        tvFinal = findViewById(R.id.tvFinal);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputKwh = etKwh.getText().toString();
                String inputRebate = etRebate.getText().toString();
                double kwh = 0.0, Rebate = 0.0;
                double totalC = 0.0;
                double finalC;

                // Try and catch for error handling
                try {
                    kwh = Double.parseDouble(inputKwh);
                    Rebate = Double.parseDouble(inputRebate);

                    if (Rebate > 5) {
                        Toast.makeText(getApplicationContext(), "Inputs cannot more than 5%!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (NumberFormatException nfe) {
                    Toast.makeText(getApplicationContext(), "Please enter numbers in the input field", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please enter numbers in the input field", Toast.LENGTH_SHORT).show();
                }

                if (kwh > 600) {
                    totalC += (kwh - 600) * 0.546;
                    kwh = 600;
                }
                if (kwh > 300) {
                    totalC += (kwh - 300) * 0.516;
                    kwh = 300;
                }
                if (kwh > 200) {
                    totalC += (kwh - 200) * 0.334;
                    kwh = 200;
                }
                if (kwh > 0) {
                    totalC += kwh * 0.218;
                }

                double rebateAmount = totalC * (Rebate / 100);
                finalC = totalC - rebateAmount;

                tvTcharge.setText(String.format("Total Charge:\t\t\t\t\t\tRM %.2f", totalC));
                tvRreceive.setText(String.format("Rebate Received:\t\tRM %.2f", rebateAmount));
                tvFinal.setText(String.format("Total Bill:\t\t\t\t\t\t\tRM %.2f", finalC));
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etKwh.setText("");
                etRebate.setText("");
                tvTcharge.setText(String.format("Total Charge:\t\t\t\t\t\tRM 0.00"));
                tvRreceive.setText(String.format("Rebate Received:\t\tRM 0.00"));
                tvFinal.setText(String.format("Total Bill:\t\t\t\t\t\t\tRM 0.00"));
                Toast.makeText(getApplicationContext(), "Clear is clicked", Toast.LENGTH_SHORT).show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.menuAbout) {
            Toast.makeText(this, "About is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, aboutActivity.class);
            startActivity(intent);
            return true;
        }
        if (selectedItemId == R.id.menuInstruction) {
            Toast.makeText(this, "Instruction is clicked", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, Instruction.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}







