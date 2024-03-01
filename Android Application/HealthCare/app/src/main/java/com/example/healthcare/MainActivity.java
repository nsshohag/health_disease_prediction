package com.example.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layoutSkin = findViewById(R.id.layoutSkin);
        LinearLayout layoutSkin2 = findViewById(R.id.layoutSkin2);
        LinearLayout layoutSkin3 = findViewById(R.id.layoutSkin3);
        LinearLayout layoutSkin4 = findViewById(R.id.layoutSkin4);
        LinearLayout layoutSkin5 = findViewById(R.id.layoutSkin5);

        EditText input_x = findViewById(R.id.input_x);
        ImageView search_buttonx = findViewById(R.id.search_buttonx);



        TextView textTrending = findViewById(R.id.textTrending);

        layoutSkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,SkinDisease.class);
                intent.putExtra("name","Sadat");
                startActivity(intent);
            }
        });

        layoutSkin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Pneumonia.class);
                intent.putExtra("name","Sadat");
                startActivity(intent);
            }
        });

        layoutSkin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Heart_Disease.class);
                startActivity(intent);
            }
        });

        layoutSkin4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ShowDialogBox ();
                Intent intent = new Intent(MainActivity.this,Diabetes.class);
                startActivity(intent);
            }
        });

        layoutSkin5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Heart_Failure.class);
                startActivity(intent);
            }
        });




        textTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,Acne.class);
                startActivity(intent);

            }
        });

        search_buttonx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String searchText = input_x.getText().toString();
                // Toast.makeText(MainActivity.this, "Search Text: " + searchText, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Search_Google.class);
                intent.putExtra("key1",searchText);
                startActivity(intent);


            }
        });


    }


    private void ShowDialogBox (){
        final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.dialog_box, null);
        alert.setView(mView);


        final AlertDialog alertDialog = alert.create();
        alertDialog.setCancelable(false);

        mView.findViewById(R.id.chancelBTN).setOnClickListener(v -> {
            alertDialog.dismiss();
            Toast.makeText(this, "Clicked OK BTN", Toast.LENGTH_SHORT).show();
        });

        mView.findViewById(R.id.okBTN).setOnClickListener(v -> {
            Toast.makeText(this, "Clicked OK BTN", Toast.LENGTH_SHORT).show();
            alertDialog.dismiss();
        });

        alertDialog.show();
    }
}