package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.healthcare.ml.HeartDiseaseTf;
import com.google.android.material.slider.Slider;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Heart_Disease extends AppCompatActivity {


    public static int x=0;

    private static float age_val_float=0;
    private static float height_val_float=0;
    private static float weight_val_float=0;
    private static float ap_hi_val_float=0;
    private static float ap_lo_val_float=0;
    private static float cholesterol_val_float=0;
    private static float gluc_val_float=0;

    Button predict;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_disease);

        predict = findViewById(R.id.predict);
        result=findViewById((R.id.result));


        Slider slider = findViewById(R.id.slider);

        RadioGroup gender = findViewById(R.id.gender);

        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                age_val_float=value*365;
                //result.setText(""+age_val_float);

            }
        });

        Slider slider_height = findViewById(R.id.slider_height);

        slider_height.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                height_val_float=value;
                //result.setText(""+height_val_float);

            }
        });

        Slider slider_weight = findViewById(R.id.slider_weight);

        slider_weight.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                weight_val_float=value;
                //result.setText(""+weight_val_float);

            }
        });

        Slider slider_ap_hi = findViewById(R.id.slider_ap_hi);

        slider_ap_hi.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                ap_hi_val_float=value;
                //result.setText(""+ap_hi_val_float);

            }
        });

        Slider slider_ap_lo = findViewById(R.id.slider_ap_lo);

        slider_ap_lo.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                ap_lo_val_float=value;
                //result.setText(""+ap_lo_val_float);

            }
        });

        Slider slider_cholesterol= findViewById(R.id.slider_cholesterol);

        slider_cholesterol.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                cholesterol_val_float=value;
                //result.setText(""+cholesterol_val_float);

            }
        });

        Slider slider_gluc= findViewById(R.id.slider_gluc);

        slider_gluc.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                gluc_val_float=value;
                //result.setText(""+gluc_val_float);

            }
        });

        RadioGroup smoke = findViewById(R.id.smoke);
        RadioGroup active = findViewById(R.id.active);
        RadioGroup alco = findViewById(R.id.alco);



        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText height =findViewById(R.id.height);
                //EditText weight=findViewById(R.id.weight);


                int selectedRadioButtonId = gender.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedValue = selectedRadioButton.getText().toString();


                float gender_val_float;

                //  if(selectedValue=="Male"){   this bitch damned me...


                if(selectedValue.equals("Female")){
                    gender_val_float=1;
                    //Toast.makeText(getApplicationContext(), "Selected value: " + selectedValue, Toast.LENGTH_SHORT).show();
                }
                else{
                    gender_val_float=2;
                    //Toast.makeText(getApplicationContext(), "Selected value: " + selectedValue, Toast.LENGTH_SHORT).show();
                }


                int smoke_id = smoke.getCheckedRadioButtonId();
                RadioButton does_smoke = findViewById(smoke_id);
                String smoke_value = does_smoke.getText().toString();


                float smoke_val_float;

                if(smoke_value.equals("No")){
                    smoke_val_float=0;

                }
                else{

                    smoke_val_float=1;

                }


                int alco_id = alco.getCheckedRadioButtonId();
                RadioButton does_alco = findViewById(alco_id);
                String alco_value = does_alco.getText().toString();


                float alco_val_float;

                if(alco_value.equals("No")){
                    alco_val_float=0;

                }
                else{

                    alco_val_float=1;

                }

                int active_id = active.getCheckedRadioButtonId();
                RadioButton does_active = findViewById(active_id);
                String active_value = does_active.getText().toString();


                float active_val_float;

                if(active_value.equals("Yes")){
                    active_val_float=1;

                }
                else{

                    active_val_float=0;

                }



                // String height_val_string = height.getText().toString();
                // float height_val_float=0;
                //try {
                // height_val_float = Float.parseFloat(height_val_string);
                // } catch (NumberFormatException e) {
                // Handle the exception (e.g., show an error message to the user)
                // e.printStackTrace();
                // }

                /*


                                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4*11);
                byteBuffer.putFloat(age_val_float);
                byteBuffer.putFloat(gender_val_float);
                byteBuffer.putFloat(height_val_float);
                byteBuffer.putFloat(weight_val_float);
                byteBuffer.putFloat(ap_hi_val_float);
                byteBuffer.putFloat(ap_lo_val_float);
                byteBuffer.putFloat(cholesterol_val_float);
                byteBuffer.putFloat(gluc_val_float);
                byteBuffer.putFloat(smoke_val_float);
                byteBuffer.putFloat(alco_val_float);
                byteBuffer.putFloat(active_val_float);




                try {
                    Tfmodel model = Tfmodel.newInstance(MainActivity.this); // this er poriborte mainactivity .this hobe arki

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 11}, DataType.FLOAT32);
                    inputFeature0.loadBuffer(byteBuffer);

                    // Runs model inference and gets result.
                    Tfmodel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Releases model resources if no longer used.
                    model.close();
                    //x++;
                    //result.setText(x+"");
                    result.setText(outputFeature0+"3");


                } catch (IOException e) {
                    // TODO Handle the exception
                }



                 */


                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 11);
                byteBuffer.order(ByteOrder.nativeOrder()); // Set the byte order to native

// Assuming these variables hold your input data

                byteBuffer.putFloat(age_val_float);
                byteBuffer.putFloat(gender_val_float);
                byteBuffer.putFloat(height_val_float);
                byteBuffer.putFloat(weight_val_float);
                byteBuffer.putFloat(ap_hi_val_float);
                byteBuffer.putFloat(ap_lo_val_float);
                byteBuffer.putFloat(cholesterol_val_float);
                byteBuffer.putFloat(gluc_val_float);
                byteBuffer.putFloat(smoke_val_float);
                byteBuffer.putFloat(alco_val_float);
                byteBuffer.putFloat(active_val_float);

                try {
                    HeartDiseaseTf model = HeartDiseaseTf.newInstance(getApplicationContext());

                    // Create inputs for the model
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 11}, DataType.FLOAT32);
                    inputFeature0.loadBuffer(byteBuffer);

                    // Run model inference
                    HeartDiseaseTf.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Release model resources
                    model.close();

                    // Access the output values
                    float[] outputValues = outputFeature0.getFloatArray();
                    float outputValue1 = outputValues[0];

                    ShowDialogBox(outputValue1);

                    // Update your UI or perform further operations with the output values
                    /*
                    if(outputValue1>0.50){
                        result.setText("Risk !!!"+"\nThe percentage is "+(outputValue1*100)+"%");
                    }
                    else{
                        result.setText("No Risk."+"\nThe percentage is "+(outputValue1*100)+"%");
                    }

                     */
                } catch (IOException e) {
                    // Handle the exception
                }


            }
        });




    }

    private void ShowDialogBox (float outputValue1){
        final AlertDialog.Builder alert = new AlertDialog.Builder(Heart_Disease.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_box_heart_d, null);
        alert.setView(mView);


        LottieAnimationView lottie_status;
        lottie_status = mView.findViewById(R.id.lottie_status);
        TextView disease_status;
        disease_status = mView.findViewById(R.id.disease_status);

        if(outputValue1>0.5) {
            lottie_status.setAnimation(R.raw.sad);
            disease_status.setText("Risk detected !!!"+"\nThe percentage is "+(outputValue1*100)+"%");
            disease_status.setTextColor(Color.parseColor("#FF0000"));

        }

        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);

        mView.findViewById(R.id.chancelBTN).setOnClickListener(v -> {
            //Toast.makeText(this, "Clicked OK BTN", Toast.LENGTH_SHORT).show();

            alertDialog.dismiss();
        });


        alertDialog.show();

    }
}