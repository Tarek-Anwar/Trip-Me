package com.example.tripmei;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class AddTripActivity extends AppCompatActivity {
    //Copmuntent UI
    EditText txt_StartPoint , txt_EndPoint, txt_Trip_Name;
    TextView txt_Time , txt_Date , text_view_time , txt_view_date;
    Spinner spin_Repeat , spin_ways ;
    Button btn_save;

    public String ACTION_PENDING = "action";
    String hoursAlarm , minutesAlarm;
    long epoch ;

    //final var which insart and update in Room
    String date , time , repeat , ways ;
    double latitudeStart, latitudeEnd ,longitudeStart,longitudeEnd ;
    String addressStart , addressEnd;

    // var date and time
    int tHour , tMinute,inMonth, inDay , inYear;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    final int hour = calendar.get(Calendar.HOUR);
    final int minute = calendar.get(Calendar.MINUTE);

    // var from intent Update
    int editCon, id;

    //items spianner
    public String[] Repeat= new String[]{"No Repeat","Repeat Daily","Repeat Weekly","Repeat Monthly"};
    public String[] Ways= new String[]{"one way Trip","Round Trip"};

    // other var
    boolean firstOpneTime = true;boolean firstOpneDate = true;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        intiCompountent();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        // update Trip
        editCon = getIntent().getIntExtra("edit",0);
        if(editCon==1){
            btn_save.setText("update");
            id = getIntent().getIntExtra("ID",-1);
            AppDatabase db = AppDatabase.getDbInstance(this);
            List<Trip> listHistory =  db.tripDao().getRow(id);
            time = listHistory.get(0).getTime();
            date = listHistory.get(0).getDate();
            addressStart = listHistory.get(0).getFrom();
            addressEnd = listHistory.get(0).getTo();
            text_view_time.setText(time);
            txt_view_date.setText(date);
            txt_StartPoint.setText(addressStart);
            txt_EndPoint.setText(addressEnd);
            longitudeEnd = listHistory.get(0).getLongitudeEnd();
            longitudeStart  = listHistory.get(0).getLongitudeStart();
            latitudeEnd = listHistory.get(0).getLatitudeEnd();
            latitudeStart  = listHistory.get(0).getLatitudeStart();
            repeat = listHistory.get(0).getTripRepeat();
            ways = listHistory.get(0).getTripType();

            txt_Trip_Name.setText(listHistory.get(0).getName());
        }

        // Edit Text Search for Address Google

        Places.initialize(getApplicationContext(),getString(R.string.key_map));
        txt_StartPoint.setFocusable(false);
        txt_EndPoint.setFocusable(false);
        txt_StartPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG , Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(AddTripActivity.this);
                startActivityForResult(intent,100);
            }
        });
        txt_EndPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                        Place.Field.LAT_LNG , Place.Field.NAME);
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY,fieldList).build(AddTripActivity.this);
                startActivityForResult(intent,200);
            }
        });

        // spinner choose state Trip
        spin_Repeat.setAdapter(new ArrayAdapter(AddTripActivity.this,android.R.layout.simple_spinner_dropdown_item,Repeat));
        spin_ways.setAdapter(new ArrayAdapter(AddTripActivity.this,android.R.layout.simple_spinner_dropdown_item,Ways));
        spin_Repeat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                repeat = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        spin_ways.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ways = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // Dialog choose time trip
        txt_Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddTripActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tHour = hourOfDay;
                        tMinute = minute;
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(0,0,0,tHour,tMinute);

                        time = (String) DateFormat.format("hh:mm aa",calendar);
                        hoursAlarm = (String) DateFormat.format("hh",calendar);
                        minutesAlarm = (String) DateFormat.format("mm",calendar);
                        epoch = calendar.getTimeInMillis();

                        text_view_time.setText(time);
                    }
                },12,0,false
                );
                if(firstOpneTime){
                    timePickerDialog.updateTime(hour,minute);
                    firstOpneTime=false;
                }else { timePickerDialog.updateTime(tHour,tMinute);}
                timePickerDialog.show();
            }
        });

        // Dialog choose date trip
        txt_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTripActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month++;
                        date = day + "/"+ month +"/"+ year;
                        txt_view_date.setText(date);
                        inDay=day;inMonth=month;inYear=year;
                    }
                },year,month,day);
                if(firstOpneDate){
                    datePickerDialog.updateDate(year,month,day);firstOpneDate=false; }
                else{ datePickerDialog.updateDate(inYear,inMonth,inDay); }
                datePickerDialog.show();
            }
        });

        //Validation input trip
      //  awesomeValidation.addValidation(this,R.id.txt_Trip_Name,RegexTemplate.NOT_EMPTY, R.string.invalidName);
//        awesomeValidation.addValidation(this,R.id.txt_StartPoint,RegexTemplate.NOT_EMPTY,R.string.invalidAddress);
   //     awesomeValidation.addValidation(this,R.id.txt_EndPoint,RegexTemplate.NOT_EMPTY,R.string.invalidAddress);

        //save Trip in Room and Validation for time and date
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* try {
                    if (Build.VERSION.SDK_INT >= 23) {
                        if (!Settings.canDrawOverlays(getApplicationContext())) {
                            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                    Uri.parse("package:" + getApplicationContext().getPackageName()));
                            startActivityForResult(intent, 1234);

                        } else startScheduling(AddTripActivity.this);

                    } else {
                        startScheduling(AddTripActivity.this);
                    } }/*/
                // catch (Exception e){Toast.makeText(getApplicationContext() , e.getMessage() , Toast.LENGTH_SHORT).show();}
              //  finally {
                if(awesomeValidation.validate() && !(time==null) && !(date==null)) {
                    if (btn_save.getText().toString().equals("Save")) {
                        saveTrip(txt_Trip_Name.getText().toString(), date, time, "UPCOMING", ways,
                                repeat, addressStart, addressEnd, latitudeStart, latitudeEnd, longitudeStart, longitudeEnd);
                        Toast.makeText(getApplicationContext(), "Save Trip", Toast.LENGTH_SHORT).show();
                    } else {
                        updateTrip(id, txt_Trip_Name.getText().toString(), date, time, "UPCOMING", ways,
                                repeat, addressStart, addressEnd, latitudeStart, latitudeEnd, longitudeStart, longitudeEnd);
                        Toast.makeText(getApplicationContext(), "Update Trip", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if(time == null){Toast.makeText(getApplicationContext(), "Please Enter Tiem Trip", Toast.LENGTH_SHORT).show(); }
                    else if(date == null){ Toast.makeText(getApplicationContext(), "Please Enter Date Trip", Toast.LENGTH_SHORT).show(); }
                    else{ Toast.makeText(getApplicationContext(), "Invalid Input Trip", Toast.LENGTH_SHORT).show(); }
                }
              //  }

            }
        });
    }

    // method insert new trip
    private void saveTrip(String nameTrip , String date , String time , String s, String t ,String r , String start
            , String end ,double latitudeStart, double latitudeEnd , double longitudeStart , double longitudeEnd )
    {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        Trip trip = new Trip(nameTrip,date,time,s,t,r,start,end,latitudeStart,latitudeEnd,longitudeStart,longitudeEnd);
        db.tripDao().insert(trip);
        finish();
    }
    //method update trip
    private  void  updateTrip(int id, String nameTrip, String date, String time, String s, String t, String r, String start
            , String end ,double latitudeStart, double latitudeEnd , double longitudeStart , double longitudeEnd)
    {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        Trip trip = new Trip(id,nameTrip,date,time,s,t,r,start,end,latitudeStart,latitudeEnd,longitudeStart,longitudeEnd);
        db.tripDao().update(trip);
        finish();
    }

    //get detiles address trip
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1234) {
            startScheduling(getApplicationContext());

        }

        if(requestCode==100 && resultCode == RESULT_OK){
            Place  place  = Autocomplete.getPlaceFromIntent(data);
            txt_StartPoint.setText(place.getAddress());
            latitudeStart =  place.getLatLng().latitude;
            longitudeStart = place.getLatLng().longitude;
            addressStart = place.getName();

        }
        else if(requestCode==200 && resultCode == RESULT_OK){
            Place  place  = Autocomplete.getPlaceFromIntent(data);
            txt_EndPoint.setText(place.getAddress());
            latitudeEnd = place.getLatLng().latitude;
            longitudeEnd = place.getLatLng().longitude;
            addressEnd = place.getName();
        }
        else if (resultCode == AutocompleteActivity.RESULT_ERROR){
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //find Copmpountent IU
    private void intiCompountent() {
        txt_StartPoint =findViewById(R.id.txt_StartPoint);
        txt_Time  = findViewById(R.id.txt_Time);
        txt_Date = findViewById(R.id.txt_Date);
        text_view_time = findViewById(R.id.txt_view_time);
        txt_view_date = findViewById(R.id.txt_view_date);
        spin_Repeat = findViewById(R.id.spin_Repeat);
        spin_ways  =findViewById(R.id.spin_ways);
        btn_save = findViewById(R.id.btn_Save);
        txt_EndPoint = findViewById(R.id.txt_EndPoint);
        txt_Trip_Name = findViewById(R.id.txt_Trip_Name);
    }

    public  void startScheduling(Context context) {

        long timemills = System.currentTimeMillis() - epoch;

        Intent intent = new Intent(context, AlarmService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                context.getApplicationContext(), 234, intent,0 );
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (timemills ), pendingIntent);
        Toast.makeText(context, time, Toast.LENGTH_LONG).show();

        Log.i("Test" , String.valueOf(epoch));
        Log.i("Test" , String.valueOf(System.currentTimeMillis()));

    }
}