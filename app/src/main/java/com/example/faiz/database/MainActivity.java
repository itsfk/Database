package com.example.faiz.database;



        import android.database.Cursor;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    // For all our buttons and edit text
    Button btnInsert;
    Button btnDelete;
    Button btnSelect;
    Button btnSearch;
    TextView btnText;
    EditText editName;
    EditText editAge;
    EditText editDelete;
    EditText editSearch;

    // This is our DataManager instance
    DataManager dm;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DataManager(this);

        // get a reference to the UI item
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnText=(TextView)findViewById(R.id.textView);
        editName = (EditText) findViewById(R.id.editName);
        editAge = (EditText) findViewById(R.id.editAge);
        editDelete = (EditText) findViewById(R.id.editDelete);
        editSearch = (EditText) findViewById(R.id.editSearch);

        // Register MainActivity as a listener
        btnSelect.setOnClickListener(this);
        btnInsert.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.btnInsert:
                dm.insert(editName.getText().toString(),
                        editAge.getText().toString());
                Toast.makeText(MainActivity.this, "The data has been inserted", Toast.LENGTH_LONG).show();

                break;

            case R.id.btnSelect:
                List<String> person = listOfData(dm.selectAll());

                for (String p: person)
                {
                    btnText.append(p);
                }
                break;

            case R.id.btnSearch:
                showData(dm.searchName(editSearch.getText().toString()));
                btnText.setText(searchedData());
                Toast.makeText(MainActivity.this, "The following data has been searched", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnDelete:
                Toast.makeText(MainActivity.this, "The Following data has been deleted", Toast.LENGTH_LONG).show();
                dm.delete(editDelete.getText().toString());
                break;

        }

    }

    // Output the cursor contents to the log
    public void showData(Cursor c){
        while (c.moveToNext()){
            Log.i(c.getString(1), c.getString(2));
        }
    }
    public List listOfData(Cursor c){
        List <String>data=new ArrayList<>();
        c.moveToFirst();
        while(c.moveToNext()){
            data.add(c.getString(1)+c.getString(2));

        }
        return data;
    }
    public String showText(Cursor c)
    {
        int column=1;
        c.moveToFirst();
        String data="";
        while(column<=2){
            data+=" "+c.getString(column);
            column++;
        }

        return data;
    }

    public String searchedData(){
        Cursor c= dm.searchName(editSearch.getText().toString());
        c.moveToFirst();
        return(c.getString(1)+" "+c.getString(2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
