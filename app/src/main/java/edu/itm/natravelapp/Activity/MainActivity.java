package edu.itm.natravelapp.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import edu.itm.natravelapp.R;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Typeface ty1;
    Context activity;

    protected int _splashTime = 3000; // time to display the splash screen in ms
    public boolean is_first = false;
    public boolean isInterent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        UIinit();

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while ((waited < _splashTime)) {
                        sleep(100);
//                        if (_active) {
                        waited += 100;
//                            if(waited>_splashTime-200)
//                            {
//
////                            }
//                        }
                    }
                } catch (Exception e) {

                } finally {                    {

                    if(isInterent){
                        startActivity(new Intent(MainActivity.this,
                                LoginActivity.class));
                    }else{

                        finish();
                    }
                }
                    finish();
                }
            };
        };

        splashTread.start();
    }

    public void UIinit(){

        textView = (TextView) findViewById(R.id.splashtext);
        ty1 = Typeface.createFromAsset(this.getAssets(),  "fonts/SinkinSans-700Bold.otf");
        textView.setTypeface(ty1);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
            // Hide status bar
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else{
            // do something for phones running an SDK before lollipop
        }

    }
}
