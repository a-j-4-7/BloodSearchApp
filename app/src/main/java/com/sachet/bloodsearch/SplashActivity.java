package com.sachet.bloodsearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    TextView tv1,tv2;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        tv1 = findViewById(R.id.donate_blood);
        tv2 = findViewById(R.id.save_life);

      animation = AnimationUtils.loadAnimation(this,R.anim.donate_blood);
      tv1.setAnimation(animation);

      animation = AnimationUtils.loadAnimation(this,R.anim.save_life);
      tv2.setAnimation(animation);

      animation = AnimationUtils.loadAnimation(this,R.anim.logo_rotate);
      logo.setAnimation(animation);

      animation.setAnimationListener(new Animation.AnimationListener() {
          @Override
          public void onAnimationStart(Animation animation) {

          }

          @Override
          public void onAnimationEnd(Animation animation) {
              finish();

              Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
              startActivity(intent);

          }

          @Override
          public void onAnimationRepeat(Animation animation) {

          }
      });





    }
}
