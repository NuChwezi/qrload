package com.example.android.qrapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class SearchQRActivity extends AppCompatActivity
{
    private final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private final int GET_IMAGE = 1;
    private Button searchButton;
    private ImageView searchedImage;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchqr);

        if(!checkPermission())
            requestPermission();


        searchButton = (Button) findViewById(R.id.search_on_device);
        searchedImage = (ImageView) findViewById(R.id.searched_image);

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                String[] mimeTypes = {"image/jpeg", "image/png"};
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                startActivityForResult(intent, GET_IMAGE);


            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GET_IMAGE)
            if(resultCode == RESULT_OK)
            {
                Uri imageUri = data.getData();



                //String imagepath = imageUri.getPath();
                //File imageFile = new File(imagepath);
                //Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                //searchedImage.setVisibility(View.VISIBLE);
                //searchedImage.setImageBitmap(myBitmap);

                searchedImage.setVisibility(View.VISIBLE);
                searchedImage.setImageURI(imageUri);
            }
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
