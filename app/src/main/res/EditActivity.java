package sg.edu.rp.c346.id20013676.l09ps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {

    EditText etTitle, etSingers, etYear;
    RadioGroup rg;
    Button btnUpdate, btnDelete, btnCancel;
    Song data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        rg = findViewById(R.id.rg);
        etTitle = findViewById(R.id.tvEditTitle);
        etSingers = findViewById(R.id.tvEditSingers);
        etYear = findViewById(R.id.tvEditYear);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        RadioButton rb1 = findViewById(R.id.radioButton1);
        RadioButton rb2 = findViewById(R.id.radioButton2);
        RadioButton rb3 = findViewById(R.id.radioButton3);
        RadioButton rb4 = findViewById(R.id.radioButton4);
        RadioButton rb5 = findViewById(R.id.radioButton5);

        Intent i = getIntent();
        data = (Song) i.getSerializableExtra("data");
        etTitle.setText(data.getTitle());
        etSingers.setText(data.getSingers());
        if (data.getStars() == 1) {
            rb1.setChecked(true);
        }
        else if (data.getStars() == 2) {
            rb2.setChecked(true);
        }
        else if (data.getStars() == 3) {
            rb3.setChecked(true);
        }
        else if (data.getStars() == 4) {
            rb4.setChecked(true);
        }
        else if (data.getStars() == 5) {
            rb5.setChecked(true);
        }

        etYear.setText(data.getYear() + "");


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setTitle(etTitle.getText().toString());
                data.setSingers(etSingers.getText().toString());
                data.setYear(Integer.parseInt(etYear.getText().toString()));
                int checkedButton = rg.getCheckedRadioButtonId();
                RadioButton rb = findViewById(checkedButton);
                data.setStars(Integer.parseInt(rb.getText().toString()));
                dbh.updateSong(data);
                dbh.close();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                dbh.deleteSong(data.get_id());
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}