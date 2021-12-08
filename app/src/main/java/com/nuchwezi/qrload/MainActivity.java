package com.nuchwezi.qrload;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.android.qrapp.R;

public class MainActivity extends AppCompatActivity {

    //public static TextView textResult;
    private Button scanningButton;
    private Button creatingButton;
    private Button searchingButton;
    private ImageView scanningImage;


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
            builder.setTitle("HELP");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });

            String about = String.format("%s \n\n****************\n\n%s: Version %s (Build %s)",
                    getString(R.string.hint_startup_message),
                    getString(R.string.app_name),
                    Utility.getVersionName(this),
                    Utility.getVersionNumber(this));

            String help = "*** HELP ***\n\n"
            +"If the QRCodes aren\'t being saved on the device: "
            +"\n\n** Hold your finger on the application icon,"
                    +"\n** Select 'About App', then 'Permissions', " +
                    "\n** disable and enable 'Internal Storage'" +
                    "\n** Finally, reset the application, and then Try Again.";

            builder.setMessage(String.format("%s\n\n%s", about, help));
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

        scanningImage = (ImageView) findViewById(R.id.imgScanBtn);

        scanningButton = (Button) findViewById(R.id.qr_scan);

        creatingButton = (Button) findViewById(R.id.qr_create);

        searchingButton = (Button) findViewById(R.id.qr_search);

        scanningImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });

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
                Intent intent = new Intent(MainActivity.this, SearchQRActivity.class);
                startActivity(intent);
            }
        });

    }
}