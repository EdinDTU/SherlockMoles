package com.course22056.pythonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.graphics.Bitmap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {

    Button convertbtn;
    ImageView moleIMG,grayIMG;

    BitmapDrawable drawable;
    Bitmap bitmap;
    String imageString ="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        convertbtn = findViewById(R.id.convertBTN);
        moleIMG = findViewById(R.id.firstimage);
        grayIMG = findViewById(R.id.secondimage);





        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(getApplicationContext()));
        }

        final Python py = Python.getInstance();

        convertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawable = (BitmapDrawable)moleIMG.getDrawable();
                bitmap = drawable.getBitmap();
                imageString = getStringImage(bitmap);

                PyObject pyo = py.getModule("importmoletest");
                PyObject obj = pyo.callAttr("main",imageString);

                String str = obj.toString();

                byte data[] = android.util.Base64.decode(str,Base64.DEFAULT);

                Bitmap bmp = BitmapFactory.decodeByteArray(data,0,data.length);

                grayIMG.setImageBitmap(bmp);

            }
        });




    }

    private String getStringImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
}