package ktu.bombit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class playerDetailInfo extends AppCompatActivity {

    private ImageView image;
    private TextView tvName;
    private TextView tvDescription;
   // String idk;
    Player item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail_info);

        image = findViewById(R.id.imageView);
        tvName = findViewById(R.id.name);
        tvDescription = findViewById(R.id.description);

        Intent intent = getIntent();
        item = intent.getExtras().getParcelable("MyClass");

        image.setImageResource(R.drawable.ic_account_circle_black_24dp);
        tvName.setText(item.getTitle());
        tvDescription.setText(item.getDescription());

    }
}
