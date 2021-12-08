package com.nuchwezi.qrload;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.qrapp.R;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class SearchQRActivity extends AppCompatActivity
{
    private static final String TAG = "QRLOAD";
    private final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private final int GET_IMAGE = 1;
    private Button searchButton;
    private ImageView searchedImage;
    private TextView searchedResult;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchqr);

        if(!checkPermission())
            requestPermission();


        searchButton = (Button) findViewById(R.id.search_on_device);
        searchedImage = (ImageView) findViewById(R.id.searched_image);
        searchedResult = (TextView) findViewById(R.id.searchResult);

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

                String result = decodeImageFile(imageUri);
                searchedResult.setText(result);
                searchedResult.setTextIsSelectable(true);
            }
    }

    private String decodeImageFile(Uri uri) {
        try
        {
            InputStream inputStream = this.getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            if (bitmap == null)
            {
                Log.e(TAG, "uri is not a bitmap," + uri.toString());
                return null;
            }
            int width = bitmap.getWidth(), height = bitmap.getHeight();
            int[] pixels = new int[width * height];
            bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
            bitmap.recycle();
            bitmap = null;
            RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
            BinaryBitmap bBitmap = new BinaryBitmap(new HybridBinarizer(source));
            MultiFormatReader reader = new MultiFormatReader();
            try
            {
                Result result = reader.decode(bBitmap);
                return result.getText();
            }
            catch (NotFoundException e)
            {
                Log.e(TAG, "decode exception", e);
                return null;
            }
        }
        catch (FileNotFoundException e)
        {
            Log.e(TAG, "can not open file" + uri.toString(), e);
            return null;
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
