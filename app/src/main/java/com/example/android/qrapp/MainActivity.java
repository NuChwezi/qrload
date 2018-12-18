package com.example.android.qrapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity {

    //public static TextView textResult;
    private Button scanningButton;
    private Button creatingButton;
    private Button searchingButton;


    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.helpbutton) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Pomoc");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });

            builder.setMessage("Jeśli kody nie zapisują się w pamięci, wtedy:\nPrzytrzymaj palec na ikonie aplikacji," +
                    "wybierz opcję 'O aplikacji', następnie 'Uprawnienia', wyłącz i włącz opcję 'Pamięć wewnętrzna'." +
                    "Na koniec zresetuj aplikację. ");
            AlertDialog alert1 = builder.create();
            alert1.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //textResult = (TextView) findViewById(R.id.text_result);

        scanningButton = (Button) findViewById(R.id.qr_scan);

        creatingButton = (Button) findViewById(R.id.qr_create);

        searchingButton = (Button) findViewById(R.id.qr_search);

        scanningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

        creatingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intent);
            }
        });

        searchingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent intent = new Intent(MainActivity.this, SearchQRActivity.class);
                //startActivity(intent);
                //Snackbar snackbar = Snackbar.make(coordinatorLayout,"test",Snackbar.LENGTH_INDEFINITE);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Informacja");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.dismiss();
                    }
                });

                //builder.setMessage("Function not yet available. ");
                builder.setMessage("Funkcja obecnie niedostępna.");
                AlertDialog alert1 = builder.create();
                alert1.show();

            }
        });

    }
}