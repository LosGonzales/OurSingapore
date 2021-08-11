package sg.edu.rp.c346.id20013676.l09ps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnShow;
    EditText etTitle, etSingers, etYear;
    RadioGroup rg;
    ArrayList<Song> al;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rg = findViewById(R.id.rg);
        btnAdd = findViewById(R.id.btnUpdate);
        btnShow = findViewById(R.id.btnDelete);
        etTitle = findViewById(R.id.tvEditTitle);
        etSingers = findViewById(R.id.tvEditSingers);
        etYear = findViewById(R.id.tvEditYear);

        al = new ArrayList<Song>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String singers = etSingers.getText().toString();
                int year = Integer.parseInt(etYear.getText().toString());
                int checkedButton = rg.getCheckedRadioButtonId();
                RadioButton rb = findViewById(checkedButton);
                int stars = Integer.parseInt(rb.getText().toString());
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertSong(title, singers, year, stars);

                if (inserted_id != -1) {
                    al.clear();
                    al.addAll(dbh.getAllSongs());
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
                al.addAll(dbh.getAllSongs());
                i.putExtra("al", al);
                startActivity(i);

            }
        });
        

    }
}