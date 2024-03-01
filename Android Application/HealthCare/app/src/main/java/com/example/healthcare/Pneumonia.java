package com.example.healthcare;

import android.Manifest;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthcare.ml.Model;
import com.example.healthcare.ml.Model2;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Pneumonia extends AppCompatActivity {

    Button camera, gallery;
    ImageView imageView;
    TextView result;
    int imageSize = 224;

    TextView confidence;

    TextView classified;
    TextView confidencesText;
    TextView inputchaitext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pneumonia);


        Bundle bundle = getIntent().getExtras();

        if(bundle!=null){

            String val = bundle.getString("name");
            // Create a Toast with a message and duration
            String message = val;
            int duration = Toast.LENGTH_SHORT; // or Toast.LENGTH_LONG
            //Toast toast = Toast.makeText(getApplicationContext(), message, duration);
            // Display the Toast
            //toast.show();

        }


        camera = findViewById(R.id.button);
        gallery = findViewById(R.id.button2);

        result = findViewById(R.id.result);
        imageView = findViewById(R.id.imageView);

        confidence = findViewById(R.id.confidence);

        classified = findViewById(R.id.classified);
        confidencesText = findViewById(R.id.confidencesText);
        inputchaitext = findViewById(R.id.inputchaitext);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });

    }
    public void classifyImage(Bitmap image){
        try {
            Model2 model = Model2.newInstance(getApplicationContext());


            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < imageSize; i ++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model2.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }


            String[] classes = {"NORMAL", "PNEUMONIA"};

            if(classes[maxPos].equals("NORMAL")){
                result.setTextColor(Color.parseColor("#008000"));
            }
            else{
                result.setTextColor(Color.parseColor("#c30000"));
            }

            classified.setVisibility(View.VISIBLE);
            confidencesText.setVisibility(View.VISIBLE);
            inputchaitext.setVisibility(View.GONE);


            result.setText(classes[maxPos]);

            String s = "";
            boolean isClass= false;
            for(int i = 0; i < classes.length; i++){
                s += String.format("%s: %.1f%%\n", classes[i], confidences[i] * 100);

                if(confidences[i] * 100>90)
                {
                    isClass = true;
                }
            }

            if(isClass==false){
                result.setText("Class Could Not Be Detected !!!");
            }

            confidence.setText(s);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 3){

                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);

                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);

                classifyImage(image);
            }else{
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);

                classifyImage(image);

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}