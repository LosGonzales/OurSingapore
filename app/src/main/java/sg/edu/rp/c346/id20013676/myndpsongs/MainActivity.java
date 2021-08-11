package sg.edu.rp.c346.id20013676.myndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnShow;
    EditText etName, etDescription, etArea;
    RadioGroup rg;
    ArrayList<Island> al;
    int stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = findViewById(R.id.btnUpdate);
        btnShow = findViewById(R.id.btnDelete);
        etName = findViewById(R.id.tvEditName);
        etDescription = findViewById(R.id.tvEditDescription);
        etArea = findViewById(R.id.tvEditArea);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        al = new ArrayList<Island>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                int area = Integer.parseInt(etArea.getText().toString());
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertIsland(name, description, area, stars);
                if (inserted_id != -1) {
                    al.clear();
                    al.addAll(dbh.getAllIslands());
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        ShowListActivity.class);
                DBHelper dbh = new DBHelper(MainActivity.this);
                al.clear();
                al.addAll(dbh.getAllIslands());
                i.putExtra("al", al);
                startActivity(i);

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                stars = (int) rating;

            }
        });


    }
}