package sg.edu.rp.c346.id20013676.l09ps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {

    ArrayList<Song> al;
    ListView lv;
    ArrayAdapter<Song> aa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        lv = findViewById(R.id.lv);
        Button btnFilter = findViewById(R.id.btnUpdate);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        Intent i = getIntent();
        al = (ArrayList<Song>) i.getSerializableExtra("al");
        aa = new ArrayAdapter<Song>(this,
                android.R.layout.simple_list_item_1, al);
        lv.setAdapter(aa);

        final boolean[] isFiltered = {false};

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Song data = al.get(position);
                Intent i = new Intent(ShowListActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowListActivity.this);
                if (!isFiltered[0]) {
                    al.clear();
                    al.addAll(dbh.getAllSongs("5"));
                    aa.notifyDataSetChanged();
                    isFiltered[0] = true;
                } else {
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    aa.notifyDataSetChanged();
                    isFiltered[0] = false;
                }
                spinner.setSelection(0);
            }
        });
        DBHelper dbh = new DBHelper(ShowListActivity.this);
        ArrayList<String> years = new ArrayList<String>();
        years.addAll(dbh.getAllYears());
        years.add("All Years");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, years);
        spinner.setAdapter(adapter);
        spinner.setSelection(spinner.getCount()-1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ShowListActivity.this);
                if (spinner.getSelectedItem().toString().equalsIgnoreCase("All Years")) {
                    al.clear();
                    al.addAll(dbh.getAllSongs());
                    aa.notifyDataSetChanged();
                } else {
                    al.clear();
                    al.addAll(dbh.filterYear(spinner.getSelectedItem().toString()));
                    aa.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(ShowListActivity.this);
        al.clear();
        al.addAll(dbh.getAllSongs());
        aa.notifyDataSetChanged();
    }
}