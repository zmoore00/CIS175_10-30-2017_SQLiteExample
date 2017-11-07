package moore.zackary.cis175_10_30_2017_sqliteexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


//Joleen
public class MainActivity extends AppCompatActivity
{

    ArrayList<String> cv_listArray;
    ArrayAdapter<String> cv_adapter;
    DatabaseHelper cv_db;
    EditText input;
    Button addName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = (EditText) findViewById(R.id.v_txtBox);
        addName = (Button) findViewById(R.id.v_btnAdd);

        cv_db = new DatabaseHelper(this);
        cv_db.dbf_initRows();

        cv_listArray = cv_db.dbf_getAllRecords();

        cv_adapter = new ArrayAdapter<String>(
                this,
                R.layout.my_listcell,
                R.id.xv2_nameLbl,
                cv_listArray);

        ListView listView = (ListView) findViewById(R.id.v_myList);
        listView.setAdapter(cv_adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                cv_db.dbf_deletePart(cv_listArray.get(position));

                //This is safe, pull data directly from DB, but too costly
                //ArrayList<String> lv_items = cv_db.dbf_getAllRecords();
                //cv_listArray.clear();
                //cv_listArray.addAll(lv_items);

                cv_listArray.remove(position);
                cv_adapter.notifyDataSetChanged();
            }
        });

        addName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameToAdd = input.getText().toString();
                cv_db.dbf_appendPart(nameToAdd);

                //This is too costly, but will be certain of DB content
                //ArrayList<String> lv_items = cv_db.dbf_getAllRecords();
                //cv_listArray.clear();
                //cv_listArray.addAll(lv_items);
                cv_listArray.add(nameToAdd);
                cv_adapter.notifyDataSetChanged();
            }
        });

    }
}
