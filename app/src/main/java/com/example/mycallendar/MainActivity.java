package com.example.mycallendar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
  private int dia,mes,año, hora1, minuto1;
                  //
    CheckBox duracion;              // Declaracion de Recursos
    ButtonBarLayout agregar;     //
    EditText titulo;            //
    EditText descripcion ;      //
    EditText localizacion ;
    EditText taño;
    EditText thora;
    Button bfecha,bhora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        thora = (EditText) findViewById(R.id.edhora);

        taño = (EditText) findViewById(R.id.edfecha);
                        //
                           //
        duracion = (CheckBox) findViewById(R.id.chkDuracion);            // Instanciacion de recursos
        bhora=(Button)findViewById(R.id.bhora);          //
        titulo = (EditText) findViewById(R.id.etTitulo);              //
        descripcion = (EditText) findViewById(R.id.etDescripcion);   //
        localizacion = (EditText) findViewById(R.id.etLocation);
        //
        bfecha=(Button)findViewById(R.id.baño);
        bhora.setOnClickListener(this);
        bfecha.setOnClickListener(this);



        //------------------------------------------------------------------------------------------------------------------------>

    }

    @Override
    public void onClick(View v) {
        if(v==bfecha){
            final Calendar c= Calendar.getInstance();
            dia=c.get(Calendar.DAY_OF_MONTH);
            mes=c.get(Calendar.MONTH);
            año=c.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    año=year;
                            dia=dayOfMonth;
                            mes=monthOfYear;
                    taño.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                }
            }
                    ,dia,mes,año);
            datePickerDialog.show();
        }
        if (v==bhora){
            final Calendar c= Calendar.getInstance();
            hora1=c.get(Calendar.HOUR_OF_DAY);
            minuto1=c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    minuto1=minute;
                    hora1=hourOfDay;
                    thora.setText(hourOfDay+":"+minute);


                }
            },hora1,minuto1,false);
            timePickerDialog.show();

        }
    }

    public void Agregar(View v) {


        Calendar cal = Calendar.getInstance();

        boolean val = false; //Controlador del ciclo while
        Intent intent = null;
        while (val == false) {


            try {
                cal.set(Calendar.YEAR, año);                 //
                cal.set(Calendar.MONTH, mes);   // Set de AÑO MES y Dia
                cal.set(Calendar.DAY_OF_MONTH, dia);       //


                cal.set(Calendar.HOUR_OF_DAY, hora1);// Set de HORA y MINUTO
                cal.set(Calendar.MINUTE, minuto1);            //

                intent = new Intent(Intent.ACTION_EDIT);
                intent.setType("vnd.android.cursor.item/event");

                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.getTimeInMillis() + 60 * 60 * 1000);

                intent.putExtra(CalendarContract.Events.ALL_DAY, duracion.isSelected());
                intent.putExtra(CalendarContract.Events.TITLE, titulo.getText().toString());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, descripcion.getText().toString());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, localizacion.getText().toString());

                startActivity(intent);
                val = true;
            } catch (Exception e) {


                taño.setText("");

                thora.setText("");

                Toast.makeText(getApplicationContext(), "Fecha Inválida", Toast.LENGTH_LONG).show();
            }
        }

    }


}
