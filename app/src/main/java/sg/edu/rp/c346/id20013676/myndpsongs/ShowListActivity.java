package sg.edu.rp.c346.id20013676.myndpsongs;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ShowListActivity extends AppCompatActivity {

    ArrayList<Island> al;
    ListView lv;
    ArrayAdapter<Island> aa;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        lv = findViewById(R.id.lv);
        Button btnFilter = findViewById(R.id.btnUpdate);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        Intent i = getIntent();
        al = (ArrayList<Island>) i.getSerializableExtra("al");
        adapter = new CustomAdapter(this,
                R.layout.row, al);
        lv.setAdapter(adapter);

        final boolean[] isFiltered = {false};

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Island data = al.get(position);
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
                    al.addAll(dbh.getAllIslands("5"));
                    adapter.notifyDataSetChanged();
                    isFiltered[0] = true;
                } else {
                    al.clear();
                    al.addAll(dbh.getAllIslands());
                    adapter.notifyDataSetChanged();
                    isFiltered[0] = false;
                }
                spinner.setSelection(spinner.getCount()-1);
            }
        });
        DBHelper dbh = new DBHelper(ShowListActivity.this);
        ArrayList<String> areas = new ArrayList<String>();
        areas.addAll(dbh.getAllAreas());
        areas.add("All Areas");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, areas);
        spinner.setAdapter(adapter);
        spinner.setSelection(spinner.getCount()-1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ShowListActivity.this);
                if (spinner.getSelectedItem().toString().equalsIgnoreCase("All Areas")) {
                    al.clear();
                    al.addAll(dbh.getAllIslands());
                    adapter.notifyDataSetChanged();
                } else {
                    al.clear();
                    al.addAll(dbh.filterArea(spinner.getSelectedItem().toString()));
                    adapter.notifyDataSetChanged();
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
        al.addAll(dbh.getAllIslands());
        adapter.notifyDataSetChanged();
    }
}