package com.example.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.healthcare.ml.DiabetesTf;
import com.google.android.material.slider.Slider;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class Diabetes extends AppCompatActivity {

   /* private static float age = 0;
    private static float pregnant = 0;
    private static float glucose = 0;
    private static float bp = 0;
    private static float tn =0;
    private static float insulin = 0;
    private static float p_function =0;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diabetes);

        Button predict = findViewById(R.id.predict);
        predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Slider age = findViewById(R.id.slider_age);
                float age_val = age.getValue();

                Slider pregnant = findViewById(R.id.slider_pregnant);
                float pregnant_val = pregnant.getValue();

                Slider glucose = findViewById(R.id.slider_glucose);
                float glucose_val = glucose.getValue();

                Slider bp = findViewById(R.id.slider_b_pressure);
                float bp_val = bp.getValue();

                Slider th = findViewById(R.id.slider_s_thickness);
                float th_val = th.getValue();

                Slider insulin = findViewById(R.id.slider_insulin);
                float insulin_val = insulin.getValue();

                Slider bmi = findViewById(R.id.slider_bmi);
                float bmi_val = bmi.getValue();

                Slider p_f = findViewById(R.id.slider_function);
                float p_f_val = p_f.getValue();



                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * 8);
                byteBuffer.order(ByteOrder.nativeOrder()); // Set the byte order to native

                byteBuffer.putFloat(pregnant_val);
                byteBuffer.putFloat(glucose_val);
                byteBuffer.putFloat(bp_val);
                byteBuffer.putFloat(th_val);
                byteBuffer.putFloat(insulin_val);
                byteBuffer.putFloat(bmi_val);
                byteBuffer.putFloat(p_f_val);
                byteBuffer.putFloat(age_val);

                // Toast.makeText(getApplicationContext(),"\n"+pregnant_val+"\n"+glucose_val+"\n"+bp_val+"\n"+th_val+"\n"+insulin_val+"\n"+bmi_val+"\n"+p_f_val+"\n"+age_val, Toast.LENGTH_SHORT).show();

                try {
                    DiabetesTf model = DiabetesTf.newInstance(getApplicationContext());

                    // Create inputs for the model
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 8}, DataType.FLOAT32);
                    inputFeature0.loadBuffer(byteBuffer);

                    // Run model inference
                    DiabetesTf.Outputs outputs = model.process(inputFeature0);
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
        final AlertDialog.Builder alert = new AlertDialog.Builder(Diabetes.this);
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
            //Toast.makeText(this, "Clicked OK BTN", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        alertDialog.show();
    }


}