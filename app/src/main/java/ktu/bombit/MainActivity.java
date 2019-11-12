package ktu.bombit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bLab2;


    DatabaseHelper myDb;
    Context context = this;
    private ScrollView svScroll;
    private TextView tvInstruction;
    private Button bPlay;
    private Button bSettings;
    private Button bHighScore;

    boolean isClicked = false;
    long targetTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svScroll = findViewById(R.id.scroll);
        tvInstruction = findViewById(R.id.instruction);
        bPlay = findViewById(R.id.play);
        bSettings = findViewById(R.id.settings);
        bHighScore = findViewById(R.id.highScore);

        myDb = new DatabaseHelper(this);
        tvInstruction.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                svScroll.setVisibility(View.GONE);
                SetInMiddle(bPlay);
                SetInMiddle(bSettings);
                SetInMiddle(bHighScore);
                return false;
            }
        });

        bPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Game.class);
                startActivity(intent);
            }
        });

        bSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Settings.class);
                startActivity(intent);
            }
        });

        bHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, HighScore.class);
                startActivity(intent);
            }
        });

        bLab2 = findViewById(R.id.lab2);

        bLab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SecondLab.class);
                startActivity(intent);
            }
        });

    }

    private void SetInMiddle(Button btn){
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) btn.getLayoutParams();
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        btn.setLayoutParams(lp);
    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (isClicked) {
            if (targetTime + 2000 > System.currentTimeMillis()) {
                this.finishAffinity();
                finishAndRemoveTask();
            } else {
                Toast.makeText(MainActivity.this, "Press again", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "Press again", Toast.LENGTH_SHORT).show();
        }
        isClicked = true;
        targetTime = System.currentTimeMillis();
    }
}
