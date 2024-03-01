package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Temp extends AppCompatActivity {

    String sadat = "Start";
    String apiResponse = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.kristen_stewart);

        TextView textView = findViewById(R.id.text_sadat);


        // Load and send the image to the API in the background
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Load the image from resources
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kristen_stewart);

                    // Convert the Bitmap to a byte array
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] imageBytes = byteArrayOutputStream.toByteArray();

                    // Create an OkHttpClient instance
                    OkHttpClient client = new OkHttpClient();
                    // Create a multipart request body with the image file
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("image", "uploaded_image.jpg", RequestBody.create(MediaType.parse("image/jpeg"), imageBytes))
                            .build();
                    // Create the POST request
                    Request request = new Request.Builder()
                            .url("https://04ce-118-179-55-81.ngrok.io/skin_disease") // Replace with your API URL
                            .post(requestBody)
                            .build();
                    // Send the request and get the response
                    Response response = client.newCall(request).execute();

                    // Initialize a variable to store the API response


                    if (response.isSuccessful()) {
                        // Handle a successful response, e.g., parse JSON response
                        String responseBody = response.body().string();

                        // You can now parse the JSON response from the API
                        // and update the UI as needed.

                        sadat = responseBody;
                        try{
                            // Create a JSONArray from the string.
                            sadat= "why te fuck json objcet is not working ?";


                           // sadat= responseBody;

                            // Create a Gson object
                            Gson gson = new Gson();

                            // Parse the JSON string into a JsonArray
                            JsonArray jsonArray = gson.fromJson(responseBody, JsonArray.class);

                            // Assuming there's only one element in the array
                            JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

                            // Access the "class" value
                            String classValue = jsonObject.get("class").getAsString();

                            sadat = classValue+"\n";

                            // Access the "class_probability" array
                            JsonArray classProbabilityArray = jsonObject.getAsJsonArray("class_probability");

                            // Now you can work with the elements in the "class_probability" array
                            for (int i = 0; i < classProbabilityArray.size(); i++) {
                                double probability = classProbabilityArray.get(i).getAsDouble();
                                // Do something with the probability value, e.g., store it in a list
                                sadat = sadat + probability + "\n";
                            }



                        }
                        catch (Exception e){
                            //sadat = ""+e.getMessage();
                        }


                    } else {
                        // Handle an unsuccessful response
                        // You can show an error message or take appropriate action here.

                    }

                    // Update the textView with the new value of sadat
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //sadat = "Preityxcx";
                            textView.setText(sadat);
                            // Display the API response in a Toast message
                            //Toast.makeText(getApplicationContext(), apiResponse, Toast.LENGTH_SHORT).show();

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        textView.setText(sadat);

    }
}
