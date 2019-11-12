package ktu.bombit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText tName;
    EditText tEmail;
    EditText tConfirmPassword;
    EditText tPassword;
    Button bConfirm;

    DatabaseHelper myDb;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tName = findViewById(R.id.name);
        tEmail = findViewById(R.id.email);
        tPassword = findViewById(R.id.password);
        tConfirmPassword = findViewById(R.id.confirmpasword);

        bConfirm = findViewById(R.id.button);
        bConfirm.setOnClickListener(confirmRegistration);

        myDb = new DatabaseHelper(this);
    }

    View.OnClickListener confirmRegistration = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if(correctAcc()){
                createAccount();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        }
    };

    private boolean correctAcc(){
        if( correctUsername() && correctPassword() ){
            return true;
        }else{
            return false;
        }

    }
    private boolean correctPassword(){

        if(tPassword.getText().length() == 0){
            Toast.makeText(Register.this, "Password is required!", Toast.LENGTH_SHORT).show();
            return false;
        }else if(tPassword.getText().toString().equals(tConfirmPassword.getText().toString())){

            return true;
        }else{
            Toast.makeText(Register.this, "Incorrect password", Toast.LENGTH_SHORT).show();
            return  false;
        }

    }

    private boolean correctUsername(){
        if(tName.getText().length() == 0){
            Toast.makeText(Register.this, "Username is required!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tName.getText().length()  < 3){
            Toast.makeText(Register.this, "Username min length is 3!", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void createAccount(){
        boolean isInserted = myDb.addPlayer(tName.getText().toString(), tEmail.getText().toString(), tPassword.getText().toString(), "");
        if(isInserted){
            Toast.makeText(Register.this, "Account created", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(Register.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }


}
