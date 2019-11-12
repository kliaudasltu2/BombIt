package ktu.bombit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Context context = this;
    Button bLogin;
    TextView tvClickHere;
    EditText tUsername;
    EditText tPassword;
    User user;

    boolean isClicked = false;
    long targetTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        bLogin = findViewById(R.id.login);
        bLogin.setOnClickListener(loadGame);

        tvClickHere = findViewById(R.id.clickhere);
        tvClickHere.setOnClickListener(clickText);

        tUsername = findViewById(R.id.username);
        tPassword = findViewById(R.id.password);

        myDb = new DatabaseHelper(this);
        user = new User();
    }

    View.OnClickListener loadGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(getPlayer()) {
                if (PasswordCorrect(user)) {
                    Constants.user = user;
                    Intent intent = new Intent(context, MainActivity.class);
                    startActivity(intent);
                }
            }
           // Toast.makeText(FirstActivity.this, Integer.toString(res.getCount()), Toast.LENGTH_SHORT).show();

        }
    };

    View.OnClickListener clickText = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            getPlayer();

            Intent intent = new Intent(context, Register.class);
            startActivity(intent);
        }
    };

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (isClicked) {
            if (targetTime + 2000 > System.currentTimeMillis()) {
                this.finishAffinity();
                finishAndRemoveTask();
            } else {
                Toast.makeText(FirstActivity.this, "Press again", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(FirstActivity.this, "Press again", Toast.LENGTH_SHORT).show();
        }
        isClicked = true;
        targetTime = System.currentTimeMillis();
    }

    private boolean getPlayer(){
        Cursor res = myDb.getPlayer(tUsername.getText().toString());
        if(res.getCount() != 0){
            res.moveToNext();
            user.setId(Integer.parseInt(res.getString(0)));
            user.setUsername(res.getString(1));
            user.setPassword(res.getString(2));
            user.setEmail(res.getString(3));
            user.setDescription(res.getString(4));
            return true;
        }
        else{
            Toast.makeText(FirstActivity.this, "Username not exist!", Toast.LENGTH_SHORT).show();
            return false;

        }
    }

    private boolean PasswordCorrect(User user){

        if(user.getPassword().equals(tPassword.getText().toString())){
            return true;
        }else{
            Toast.makeText(FirstActivity.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
