package ktu.bombit;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class SecondLab extends AppCompatActivity implements RequestOperator.RequestOperatorListener{
    Button bButton;
    IndicatingView indicator;
    IndicatingView2 indicator2;
    IndicatorProggresBar pBar;

    ImageView ivImg;
    private AnimatorSet animatorSet;
    private AnimatorSet animatorSet2;
    private ArrayList<Player> players;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_lab);

        indicator = findViewById(R.id.indicator);
        indicator2 = findViewById(R.id.indicator2);

        bButton = findViewById(R.id.sendrequest);
        bButton.setOnClickListener(sendRequestListiner);

        ivImg = findViewById(R.id.img);
        ivImg.setVisibility(View.INVISIBLE);

        pBar = findViewById(R.id.customProgress);

        //Animation hyperspaceJump = AnimationUtils.loadAnimation(this, R.anim.custombar);
        //pBar.startAnimation(hyperspaceJump);

    }

    @Override
    public void success(ArrayList<Player> player) {
        this.players = player;
        updateField();
    }

    @Override
    public void failed(int responseCode) {
        this.players = null;
        updateField();
    }

    View.OnClickListener sendRequestListiner = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendRequest();
            setIndicatorStatus(IndicatingView.INPROGRESS);

            if(animatorSet == null){
                animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(SecondLab.this, R.animator.loading_animator);
                animatorSet.setTarget(ivImg);
            }

            if(animatorSet.isRunning()){
                animatorSet.cancel();
            }else {
                animatorSet.start();
                ivImg.setVisibility(View.VISIBLE);
            }

        }
    };

    public void setIndicatorStatus(final int status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                indicator.setState(status);
                indicator.invalidate();
            }
        });
    }

    public void setIndicatorStatus2(final int status){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                indicator2.setNumber(status);
                indicator2.invalidate();
            }
        });
    }

    private void sendRequest(){
        RequestOperator ro = new RequestOperator();
        ro.SetListener(this);
        ro.start();
    }


    public void updateField(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(players != null){
                    setIndicatorStatus2(players.size());
                    setIndicatorStatus(IndicatingView.SUCCESS);
                    Toast.makeText(SecondLab.this, Integer.toString(players.size()), Toast.LENGTH_SHORT).show();

                }else{
                    setIndicatorStatus(IndicatingView.FAILED);
                }
                animatorSet.cancel();
                ivImg.setVisibility(View.INVISIBLE);
            }
        });
    }

}
