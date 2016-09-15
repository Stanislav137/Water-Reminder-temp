package app.reminder.stas.waterreminder;

import android.app.AlertDialog;
import android.app.Application;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static app.reminder.stas.waterreminder.R.*;
import static app.reminder.stas.waterreminder.R.string.*;
import static app.reminder.stas.waterreminder.R.string.kg;
import static app.reminder.stas.waterreminder.R.string.weight;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences sPref;

    private final int IDD_LIST_SEX = 1, IDD_LIST_WEIGHT = 2;

    AlertDialog.Builder builder;

    CheckBox checkBox, checkBox2;
    ImageView glass;
    TextView tvStart,tvFinish;

    int lb_kg = 75;

    private final int IDD_DIALOG_TIME1 = 3, IDD_DIALOG_TIME2 = 4;
    int myHourStart = 14, myCurrentHour = 0, myHourFinish = 21;          // test
    int myMinuteStart = 35, myCurrentMinute = 0, myMinuteFinish = 10;

    int all2 = 1500, checkRadioBtn = 0;

    LinearLayout linearLayoutSex, linearLayoutWeight, linearLayoutStart, linearLayoutFinish;
    TextView chosenMaleFemale, weight_txt;
    private String m_Text = "";

    int weight = 1, lbVisual = 0;
    int sex = 1;
    Boolean checkLbKg = true;
    public int getAll2() {
        return all2;
    }

    public void setAll2(int all2) {
        this.all2 = all2;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_setting);

        tvStart = (TextView) findViewById(R.id.tvStart);
        tvFinish = (TextView) findViewById(R.id.tvFinish);

        checkBox2 = (CheckBox) findViewById(id.checkBox2);

        linearLayoutSex = (LinearLayout) findViewById(R.id.linearLayoutSex);
        linearLayoutWeight = (LinearLayout) findViewById(R.id.linearLayoutWeight);
        linearLayoutStart = (LinearLayout) findViewById(R.id.linearLayoutStart);
        linearLayoutFinish = (LinearLayout) findViewById(R.id.linearLayoutFinish);

        chosenMaleFemale = (TextView) findViewById(R.id.chosenMaleFemale);
        weight_txt = (TextView) findViewById(R.id.weight);

        tvStart.setText("Time is " + myHourStart + " hours " + myMinuteStart + " minutes");
        tvFinish.setText("Time is " + myHourFinish + " hours " + myMinuteFinish + " minutes");

        linearLayoutSex.setOnClickListener(this);
        linearLayoutWeight.setOnClickListener(this);
        linearLayoutStart.setOnClickListener(this);
        linearLayoutFinish.setOnClickListener(this);
        checkBox2.setOnClickListener(this);

        loadText();
    }

    public int func() {
        if(checkRadioBtn==0) {
            all2 = (int) (weight * 30);
        }
        if(checkRadioBtn==1) {
            all2 = (int) (weight * 14);
           all2 = (int) (all2 * 0.033814) - 2;
       }
        return all2;
    }

    public void onClickDrink(View v) {
        Intent intent = new Intent(this, WaterActivity.class);
        intent.putExtra("name", all2 + "");
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onClickSettings(View v) {
        Intent intent = new Intent(this, SettingActivity.class);
    }

    @Override
    public void onClick(View v) {
        time();
       // checkFunc();
        switch (v.getId()) {
            case id.linearLayoutSex:
                showDialog(IDD_LIST_SEX);
                break;
            case id.linearLayoutWeight:
                showDialog(IDD_LIST_WEIGHT);
                break;
             case id.linearLayoutStart:
                  showDialog(IDD_DIALOG_TIME1);
                break;
             case id.linearLayoutFinish:
                  showDialog(IDD_DIALOG_TIME2);
                 break;
            case id.checkBox2:
                if(checkBox2.isChecked()) {

                } else if(!checkBox2.isChecked()) {
              //      stopService(new Intent(this, MyService.class));
                }
                break;
                }
            }

    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHourStart = hourOfDay;
            myMinuteStart = minute;
            tvStart.setText("Time is " + myHourStart + " hours " + myMinuteStart + " minutes");
        }
    };

    TimePickerDialog.OnTimeSetListener myCallBack2 = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHourFinish = hourOfDay;
            myMinuteFinish = minute;
            tvFinish.setText("Time is " + myHourFinish + " hours " + myMinuteFinish + " minutes");
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case IDD_DIALOG_TIME1:
                if (id == IDD_DIALOG_TIME1) {
                    TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, myHourStart, myMinuteStart, true);
                    return tpd;
                }
                break;
            case IDD_DIALOG_TIME2:
                if(id == IDD_DIALOG_TIME2) {
                    TimePickerDialog tpd = new TimePickerDialog(this, myCallBack2, myHourFinish, myMinuteFinish, true);
                    return  tpd;
                }
                break;
            case IDD_LIST_SEX:

                final String[] mSexName = new String[]{getString(female), getString(male)};

                builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(gender)); // заголовок для диалога

                builder.setItems(mSexName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        Toast.makeText(getApplicationContext(),
                                getString(chosen) + mSexName[item],
                                Toast.LENGTH_SHORT).show();
                        sex = item;
                        chosenMaleFemale.setText(mSexName[item]);
                        saveText();
                    }
                });
                builder.setCancelable(false);
                return builder.create();

            case IDD_LIST_WEIGHT:

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(string.weight));

                final String[] mChooseWeight = {getString(kgWeight), getString(lbWeight)};

                // Set up the input
                final EditText input = new EditText(this);

                input.setText(weight + "");
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);
                builder.setSingleChoiceItems(mChooseWeight, checkRadioBtn,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int item) {

                                if (!TextUtils.isEmpty(input.getText().toString())) {

                                m_Text = input.getText().toString();
                                weight = Integer.parseInt(m_Text);

                                    if (mChooseWeight[item].equals(getString(kgWeight)) && checkRadioBtn!=0) {
                                        lb_kg = (int) Math.ceil(weight * 0.453592);
                                        checkRadioBtn = 0;
                                    }

                                    if (mChooseWeight[item].equals(getString(lbWeight)) && checkRadioBtn!=1) {
                                       lb_kg = (int) (weight * 2.20462);
                                        checkRadioBtn = 1;
                                    }
                                weight = lb_kg;

                                input.setText(weight + "");
                                Toast.makeText(
                                        getApplicationContext(),
                                        getString(weightin)
                                                + " " + mChooseWeight[item],
                                        Toast.LENGTH_SHORT).show();

                                saveText();
                            } else {
                                    if (mChooseWeight[item].equals(getString(kgWeight)) && checkRadioBtn!=0) {
                                        checkRadioBtn = 0;
                                    }
                                    if (mChooseWeight[item].equals(getString(lbWeight)) && checkRadioBtn!=1) {
                                        checkRadioBtn = 1;
                                    }
                                    return;
                                }
                            }
                        });
                builder.setPositiveButton(getString(ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(input.getText().toString())) {
                            m_Text = input.getText().toString();
                            weight = Integer.parseInt(m_Text);

                            func();
                            weight_txt.setText(weight + "");
                            saveText();
                        } else { return; }
                    }
                });
                builder.setNegativeButton(getString(cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        saveText();
                    }
                });
                builder.show();

            default:
                break;
        }
        return super.onCreateDialog(id);
    }

    void saveText() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt(getString(memorySex), sex);
        ed.putInt(getString(memoryWeight), (int) weight);
        ed.putInt("mTimeCurrentHour",myCurrentHour);
        ed.putInt("mTimeCurrentMinute",myCurrentMinute);
        ed.putInt("mTimeStartHour",myHourStart);
        ed.putInt("mTimeStartMinute",myMinuteStart);
        ed.putInt("mTimeFinishHour",myHourFinish);
        ed.putInt("mTimeFinishMinute",myMinuteFinish);
        ed.putInt("memoryCheckRB",checkRadioBtn);
        ed.putInt("memoryAll2", all2);
        ed.commit();
    }

    void loadText() {
        sPref = getPreferences(MODE_PRIVATE);
        weight = sPref.getInt(getString(memoryWeight),weight);
        sex = sPref.getInt(getString(memorySex), sex);
        all2 = sPref.getInt("memoryAll2", all2);
        myCurrentHour = sPref.getInt("mTimeCurrentHour",myCurrentHour);
        myCurrentMinute = sPref.getInt("mTimeCurrentMinute",myCurrentMinute);
        myHourStart = sPref.getInt("mTimeStartHour",myHourStart);
        myMinuteStart = sPref.getInt("mTimeStartMinute",myMinuteStart);
        myHourFinish = sPref.getInt("mTimeFinishHour",myHourFinish);
        myMinuteFinish = sPref.getInt("mTimeFinishMinute",myMinuteFinish);
        checkRadioBtn = sPref.getInt("memoryCheckRB",checkRadioBtn);
        weight_txt.setText(weight + "");
        tvStart.setText("Time is " + myHourStart + " hours " + myMinuteStart + " minutes");
        tvFinish.setText("Time is " + myHourFinish + " hours " + myMinuteFinish + " minutes");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveText();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sex == 0) {
            chosenMaleFemale.setText(getString(female));
        } else if (sex == 1) {
            chosenMaleFemale.setText(getString(male));
        }
    }

    public void time() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());

        Date date = cal.getTime();
        myCurrentMinute = date.getMinutes();
        myCurrentHour = date.getHours();
    }
/*
    public void checkFunc() {
        if(myCurrentHour>myHourStart && myCurrentHour<myHourFinish) {
            startService(new Intent(this, MyService.class));
        } else if(myCurrentHour==myHourStart && myCurrentMinute>=myMinuteStart) {
            startService(new Intent(this, MyService.class));
        } else if(myCurrentHour==myHourStart && myCurrentMinute>=myMinuteStart) {
            startService(new Intent(this, MyService.class));
        } else if(myCurrentHour==myHourFinish && myCurrentMinute<=myMinuteFinish) {
            startService(new Intent(this, MyService.class));
        } else if(myCurrentHour==myHourStart && myCurrentHour==myHourFinish && myCurrentMinute>=myMinuteStart && myCurrentMinute<=myMinuteFinish) {
            startService(new Intent(this, MyService.class));
        } else if(myHourStart>myHourFinish) {
            stopService(new Intent(this,MyService.class));
        } else if(myHourStart==myHourFinish && myMinuteStart>=myMinuteFinish && myCurrentMinute<myMinuteStart) {
            stopService(new Intent(this,MyService.class));
        } else if(myHourStart==myHourFinish && myMinuteStart>=myMinuteFinish && myCurrentMinute>myMinuteFinish) {
            stopService(new Intent(this,MyService.class));
        }
        {                                // Все работает!
            stopService(new Intent(this,MyService.class));
        }

    }
    */
}