package ktu.bombit;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class HighScore extends AppCompatActivity {

    Context context = this;
    private ListView myList;
    private Button sortButton;
    ArrayList<Player> items = new ArrayList<Player>();
    ArrayList<User> users = new ArrayList<>();
    ArrayList<Player> filteredItems;
    private ListAdapter adapter;
    private TextView text;
    private EditText filter;
    private Random rand = new Random();

    DatabaseHelper mydb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        mydb = new DatabaseHelper(this);

        System.out.println("iki");
        getUsers();
        System.out.println("praejo");
        items.add(new Player("John", 0, "Description1", rand.nextInt(2000)));
        items.add(new Player("Jack", 0, "Description2", rand.nextInt(2000)));
        items.add(new Player("Veronica", 0, "Description3", rand.nextInt(2000)));
        items.add(new Player("Big bones", 0, "Description4", rand.nextInt(2000)));
        items.add(new Player("Melly", 0, "Description5", rand.nextInt(2000)));
        items.add(new Player("John", 0, "Description1", rand.nextInt(2000)));
        items.add(new Player("Jack", 0, "Description2", rand.nextInt(2000)));
        items.add(new Player("Veronica", 0, "Description3", rand.nextInt(2000)));
        items.add(new Player("Big bones", 0, "Description4", rand.nextInt(2000)));
        items.add(new Player("Melly", 0, "Description5", rand.nextInt(2000)));

        myList = findViewById(R.id.listView);
        filter = findViewById(R.id.name);

        adapter = new ListAdapter(context, items);
        myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context, playerDetailInfo.class);

                intent.putExtra("MyClass", items.get(position));
                startActivity(intent);
            }
        });


    }


        private void getUsers(){
            System.out.println("metode");
            Cursor res = mydb.getPlayers();
            //res.moveToFirst();
            //Toast.makeText(HighScore.this, res.getString(0) + res.getString(1) + res.getString(2), Toast.LENGTH_SHORT).show();
            Toast.makeText(HighScore.this, Integer.toString(res.getCount()), Toast.LENGTH_SHORT).show();

            StringBuffer buffer = new StringBuffer();
            while(res.moveToNext()){
                buffer.append("id: " + res.getString(0) + "\n");
                buffer.append("name: " + res.getString(1) + "\n");
                buffer.append("bestcore: " + res.getString(2) + "\n\n");
            }

            ShowMessage("Data", buffer.toString());
        }

    public void ShowMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(HighScore.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}
