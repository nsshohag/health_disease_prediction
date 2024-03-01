package com.example.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.healthcare.ml.DiabetesTf;
import com.example.healthcare.ml.HeartFailureTf;
import com.google.android.material.slider.Slider;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Heart_Failure extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_failure);
        Button predict = findViewById(R.id.predict);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Slider age = findViewById(R.id.slider_age);
                float age_val = age.getValue();

                RadioGroup gender = findViewById(R.id.gender);

                int selectedRadioButtonId = gender.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                String selectedValue = selectedRadioButton.getText().toString();

                float gender_int = 0;
                if(selectedValue.equals("Female")){
                    gender_int = 0;
                }
                else{
                    gender_int =1;

                }

                Slider slider_chest_pain_type = findViewById(R.id.slider_chest_pain_type);
                float slider_chest_pain_type_val = slider_chest_pain_type.getValue();

                Slider slider_resting_blood_pressure = findViewById(R.id.slider_resting_blood_pressure);
                float slider_resting_blood_pressure_val = slider_resting_blood_pressure.getValue();

                Slider slider_cholesterol = findViewById(R.id.slider_cholesterol);
                float slider_cholesterol_val = slider_cholesterol.getValue();

                RadioGroup fasting_bs = findViewById(R.id.fasting_bs);

                int selectedRadioButtonId2 = fasting_bs.getCheckedRadioButtonId();
                RadioButton selectedRadioButton2 = findViewById(selectedRadioButtonId2);
                String selectedValue2 = selectedRadioButton2.getText().toString();

                float fasting_bs_val = 0;//
                if(selectedValue2.equals("Yes")){
                    fasting_bs_val = 1;
                }
                else{
                    fasting_bs_val =0;
                }

                Slider slider_RestingECG = findViewById(R.id.slider_RestingECG);
                float slider_RestingECG_val = slider_RestingECG.getValue();

                Slider slider_mhr = findViewById(R.id.slider_mhr);
                float slider_mhr_val = slider_mhr.getValue();

                RadioGroup exercise_angina = findViewById(R.id.exercise_angina);

                int selectedRadioButtonId3 = exercise_angina.getCheckedRadioButtonId();
                RadioButton selectedRadioButton3 = findViewById(selectedRadioButtonId3);
                String selectedValue3 = selectedRadioButton3.getText().toString();

                float exercise_angina_val = 0;
                if(selectedValue3.equals("Yes")){
                    exercise_angina_val = 1;
                }
                else{
                    exercise_angina_val =0;

                }



                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 9);
                byteBuffer.order(ByteOrder.nativeOrder()); // Set the byte order to native

                byteBuffer.putFloat(age_val);
                byteBuffer.putFloat(gender_int);
                byteBuffer.putFloat(slider_chest_pain_type_val);
                byteBuffer.putFloat(slider_resting_blood_pressure_val);
                byteBuffer.putFloat(slider_cholesterol_val);
                byteBuffer.putFloat(fasting_bs_val);
                byteBuffer.putFloat(slider_RestingECG_val);
                byteBuffer.putFloat(slider_mhr_val);
                byteBuffer.putFloat(exercise_angina_val);

               // Toast.makeText(getApplicationContext(), ""+age_val+"\n"+gender_int+"\n"+gender_int+"\n"+slider_chest_pain_type_val+"\n"+slider_resting_blood_pressure_val+"\n"+slider_cholesterol_val+"\n"+fasting_bs_val+"\n"+slider_RestingECG_val+"\n"+slider_mhr_val+"\n"+exercise_angina_val, Toast.LENGTH_SHORT).show();

                try {
                    HeartFailureTf model = HeartFailureTf.newInstance(getApplicationContext());

                    // Create inputs for the model
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 9}, DataType.FLOAT32);
                    inputFeature0.loadBuffer(byteBuffer);

                    // Run model inference
                    HeartFailureTf.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                    // Release model resources
                    model.close();

                    // Access the output values
                    float[] outputValues = outputFeature0.getFloatArray();
                    float outputValue1 = outputValues[0];

                    ShowDialogBox(outputValue1);


                } catch (IOException e) {
                    // Handle the exception
                }




            }
        });

    }

    private void ShowDialogBox (float outputValue1){
        final AlertDialog.Builder alert = new AlertDialog.Builder(Heart_Failure.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_box_heart_d, null);
        alert.setView(mView);


        LottieAnimationView lottie_status;
        lottie_status = mView.findViewById(R.id.lottie_status);
        TextView disease_status;
        disease_status = mView.findViewById(R.id.disease_status);

        if(outputValue1>0.5) {
            lottie_status.setAnimation(R.raw.sad);
            disease_status.setText("Risk detected !!!");
            disease_status.setTextColor(Color.parseColor("#FF0000")); // eikha diabetes e color green ba red e problem hocch ena but pneumonia te jhamela hoy . karon maybe inflate kora hoiche dekhe kintu penumonia te parseColor diye color change korle sei color change abr parseColor diye chnage na kora porzonto persist kore.
        }


        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);

        mView.findViewById(R.id.chancelBTN).setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

}