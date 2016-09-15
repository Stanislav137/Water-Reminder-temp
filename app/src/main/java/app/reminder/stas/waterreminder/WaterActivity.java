package app.reminder.stas.waterreminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WaterActivity extends AppCompatActivity{

    SharedPreferences sPref;

    SettingActivity sa = new SettingActivity();

    AlertDialog.Builder builder;

    private final int IDD_FINISH = 3, IDD_RESULT_ALL = 4;

    private AnimationDrawable mAnimationDrawable, mAnimationDrawableUpdate;

    ImageView batary, update, setting, glass;
    TextView bataryText,txt_500,txt_300,txt_200;

    int i = 0, all = sa.getAll2(), x = 0;  // test variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.water_main);

        batary = (ImageView) findViewById(R.id.batary);
        update = (ImageView) findViewById(R.id.update);
        setting = (ImageView) findViewById(R.id.settings);
      //  glass = (ImageView) findViewById(R.id.cuplemenu);

        batary.setBackgroundResource(R.drawable.batarynew);
        update.setBackgroundResource(R.drawable.updates);

      //  glass.setBackgroundResource(R.drawable.iconmenu);

        bataryText = (TextView) findViewById(R.id.bataryText);
        txt_500 = (TextView) findViewById(R.id.txt_500);
        txt_300 = (TextView) findViewById(R.id.txt_300);
        txt_200 = (TextView) findViewById(R.id.txt_200);

        update.setImageResource(R.drawable.updateanim);
        setting.setImageResource(R.drawable.settinganim);
     //   glass.setImageResource(R.drawable.glassanim);

        loadText();
    }

    public void check_txt() {
        batary.setBackgroundResource(R.drawable.batary0);
        mAnimationDrawable = (AnimationDrawable) batary.getBackground();
        mAnimationDrawable.start();
        x = 0;
        i = 0;
        bataryText.setText(i + getString(R.string.share) + all);
        saveText();
        if (all > 300) {
            i = 0;
            x = 0;
            txt_500.setText(R.string.ml500);
            txt_300.setText(R.string.ml300);
            txt_200.setText(R.string.ml200);
        }
        if(all <=300) {
            i = 0;
            x = 0;
            txt_500.setText(R.string.oz17);
            txt_300.setText(R.string.oz10);
            txt_200.setText(R.string.oz7);
        }
    }

    public void clickBottle500(View v) {
        if(all > 300) {
            i = i + 500;
            x = (i * 100) / all;
        }
        if(all<=300) {
            i = i + 17;
            x = (i * 100) / all;
        }
        bataryText.setText(i + getString(R.string.share) + all); // 17 унций
        bataryPlus();
        saveText();
    }

    public void clickBottle300(View v) {
        stopService(new Intent(this,MyService.class));
        if(all>200) {
            i = i + 300;
            x = (i * 100) / all;
        }
        if(all<=200) {
            i = i + 10;
            x = (i * 100) / all;
        }
        bataryText.setText(i + getString(R.string.share) + all); // 10 унций
        bataryPlus();
        saveText();
    }

    public void clickBottle200(View v) {
        if(all>200) {
            i = i + 200;
            x = (i * 100) / all;
        }
        if(all<=200) {
            i = i + 7;
            x = (i * 100) / all;
        }
        bataryText.setText(i + getString(R.string.share) + all); // 7 унций
        bataryPlus();
        saveText();
    }

    public void bataryPlus() {

        if(x>=25 && x<50) {
            batary.setBackgroundResource(R.drawable.bataryanim);
            mAnimationDrawable = (AnimationDrawable) batary.getBackground();
            mAnimationDrawable.start();
            saveText();
        }
        if(x>=50 && x<75) {
            batary.setBackgroundResource(R.drawable.bataryanim2);
            mAnimationDrawable = (AnimationDrawable) batary.getBackground();
            mAnimationDrawable.start();
            saveText();
        }
        if(x>=75 && x<100) {
            batary.setBackgroundResource(R.drawable.bataryanim3);
            mAnimationDrawable = (AnimationDrawable) batary.getBackground();
            mAnimationDrawable.start();
            saveText();
        }
        if(x>=100) {
            showDialog(IDD_FINISH);
            batary.setBackgroundResource(R.drawable.bataryanim4);
            mAnimationDrawable = (AnimationDrawable) batary.getBackground();
            mAnimationDrawable.start();
            saveText();
        }

    }

    public void clickUpdate(View v) {
        batary.setBackgroundResource(R.drawable.batary0);
        mAnimationDrawable = (AnimationDrawable) batary.getBackground();
        mAnimationDrawable.start();
        x = 0;
        i = 0;
        bataryText.setText(i + getString(R.string.share) + all);
        saveText();
    }

    protected Dialog onCreateDialog (int id) {
        switch (id) {
            case IDD_FINISH:
                final String[] mCont = {getString(R.string.cont)};

                builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.name_finish)); // заголовок для диалога

                builder.setItems(mCont, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        x = 0;
                        i = 0;
                        bataryText.setText(i + getString(R.string.share) + all);
                        batary.setBackgroundResource(R.drawable.batary0);
                        saveText();
                    }
                });
                builder.setCancelable(false);
                return builder.create();
            default:
                return null;
        }
    }

    public void onClickDrink(View v) {
        Intent intent = new Intent(this, WaterActivity.class);
    }

    public void onClickSettings(View v) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivityForResult(intent, IDD_RESULT_ALL);
    }

    // База памяти

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(getString(R.string.memoryX),x);
        ed.putInt(getString(R.string.memoryI), i);
        ed.putInt("memoryAll",all);
        ed.commit();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        i = sPref.getInt(getString(R.string.memoryI),i);
        x = sPref.getInt(getString(R.string.memoryX),x);
        all = sPref.getInt("memoryAll",all);
        bataryText.setText(i + getString(R.string.share) + all);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

    protected void onStart() {
        super.onStart();
        bataryPlus();
        check_txt();
        startService(new Intent(this, MyService.class));
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        String name = data.getStringExtra("name");
        all = Integer.valueOf(name);
        bataryText.setText(i+"/"+all);
    }
}


