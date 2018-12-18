package com.example.android.qrapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;


public class CreateActivity extends AppCompatActivity
{
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private EditText editText;
    private Button createButton;
    private Button saveButton;
    private ImageView imageView;
    private Bitmap mainBitmap;
    private String mainText = "QR";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        if(!checkPermission())
            requestPermission();

        editText = (EditText) findViewById(R.id.edit_text);
        createButton = (Button) findViewById(R.id.create_button);
        imageView = (ImageView) findViewById(R.id.qr_code);
        saveButton = (Button) findViewById(R.id.qr_image);


        createButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String text = editText.getText().toString().trim();

                if (!text.equals(""))
                {
                    mainText = text;
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try
                    {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 1000, 1000);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        imageView.setImageBitmap(bitmap);

                        mainBitmap = bitmap;

                    }
                    catch (WriterException e)
                    {
                        e.printStackTrace();
                    }

                    saveButton.setVisibility(View.VISIBLE);


                }

                else Toast.makeText(CreateActivity.this, "Wprowadź tekst.", Toast.LENGTH_SHORT).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(!checkPermission())
                {
                    Toast.makeText(getApplicationContext(), "Nie przyznano wystarczających uprawnień.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateActivity.this, MainActivity.class);
                    startActivity(intent);
                }


                File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "Kody_QR");

                if (!folder.exists()) {
                    folder.mkdirs();
                }

                String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss",Locale.GERMANY).format(new Date());
                String fname = timeStamp+"-QR"+".jpg";
                File file = new File (folder, fname);
                if (file.exists ()) file.delete ();
                try {
                    FileOutputStream out = new FileOutputStream(file);
                    mainBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(CreateActivity.this, "Kod zapisano w: /Pamięć Wewnętrzna/Kody_QR", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean checkPermission()
    {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission()
    {
        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
    }

}
