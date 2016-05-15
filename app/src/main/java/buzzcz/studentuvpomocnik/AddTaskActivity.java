package buzzcz.studentuvpomocnik;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {

	private String personalNumber;
	private String subjectName;
	private Task task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		personalNumber = getIntent().getStringExtra("personalNumber");
		subjectName = getIntent().getStringExtra("subject");
		task = getIntent().getParcelableExtra("task");
		setTitle(getTitle() + " " + subjectName);

		findViewById(R.id.dateEditText).setOnFocusChangeListener(new View.OnFocusChangeListener() {


			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});
		findViewById(R.id.timeEditText).setOnFocusChangeListener(new View.OnFocusChangeListener() {


			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {
					v.performClick();
				}
			}
		});

		if (task == null) {
			EditText dateEditText = (EditText) findViewById(R.id.dateEditText);
			Calendar c = Calendar.getInstance();
			String day = c.get(Calendar.DAY_OF_MONTH) + "";
			if (day.length() == 1) day = "0" + day;
			String month = (c.get(Calendar.MONTH) + 1) + "";
			if (month.length() == 1) month = "0" + month;
			String year = c.get(Calendar.YEAR) + "";
			if (year.length() == 1) year = "0" + year;
			dateEditText.setText(day + "." + month + "." + year);
			dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {
						selectDate(v);
					}
				}
			});
			EditText timeEditText = (EditText) findViewById(R.id.timeEditText);
			String hour = c.get(Calendar.HOUR_OF_DAY) + "";
			if (hour.length() == 1) hour = "0" + hour;
			String minute = c.get(Calendar.MINUTE) + "";
			if (minute.length() == 1) minute = "0" + minute;
			timeEditText.setText(hour + ":" + minute);
			timeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					if (hasFocus) {
						selectTime(v);
					}
				}
			});
		} else {
			((EditText) findViewById(R.id.titleEditText)).setText(task.getTitle());
			((EditText) findViewById(R.id.dateEditText)).setText(task.getDate());
			((EditText) findViewById(R.id.timeEditText)).setText(task.getTime());
			((EditText) findViewById(R.id.descriptionEditText)).setText(task.getDescription());
		}
	}

	public void selectDate(View v) {
		Calendar c = Calendar.getInstance();
		final EditText dateEditText = (EditText) findViewById(R.id.dateEditText);
		DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog
				.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				String y;
				if (year < 10) y = "0" + year;
				else y = year + "";
				String m;
				if ((monthOfYear + 1) < 10) m = "0" + (monthOfYear + 1);
				else m = (monthOfYear + 1) + "";
				String d;
				if (dayOfMonth < 10) d = "0" + dayOfMonth;
				else d = dayOfMonth + "";
				dateEditText.setText(d + "." + m + "." + y);
			}
		}, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
		datePicker.getDatePicker().setMinDate(c.getTime().getTime());
		datePicker.show();
	}

	public void selectTime(View v) {
		Calendar c = Calendar.getInstance();
		final EditText timeEditText = (EditText) findViewById(R.id.timeEditText);
		TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog
				.OnTimeSetListener() {

			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				String h;
				if (hourOfDay < 10) h = "0" + hourOfDay;
				else h = hourOfDay + "";
				String m;
				if (minute < 10) m = "0" + minute;
				else m = minute + "";
				timeEditText.setText(h + ":" + m);
			}
		}, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
		timePicker.show();
	}

	public void addTask(View v) throws ParseException {
		if (checkInput()) {
			if (task == null) {
				SQLiteDatabase db = (new TasksDatabaseHelper(this)).getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put("personalNumber", personalNumber);
				cv.put("subject", subjectName);
				cv.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new SimpleDateFormat
						("dd.MM.yyyy HH:mm").parse(((EditText) findViewById(R.id.dateEditText))
						.getText() + " " + ((EditText) findViewById(R.id.timeEditText)).getText
						())));
				cv.put("title", ((EditText) findViewById(R.id.titleEditText)).getText().toString
						());
				cv.put("description", ((EditText) findViewById(R.id.descriptionEditText)).getText
						().toString());
				db.insert("tasks", null, cv);
				db.close();
			} else {
				SQLiteDatabase db = (new TasksDatabaseHelper(this)).getWritableDatabase();
				ContentValues cv = new ContentValues();
				cv.put("time", new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new SimpleDateFormat
						("dd.MM.yyyy HH:mm").parse(((EditText) findViewById(R.id.dateEditText))
						.getText() + " " + ((EditText) findViewById(R.id.timeEditText)).getText
						())));
				cv.put("title", ((EditText) findViewById(R.id.titleEditText)).getText().toString
						());
				cv.put("description", ((EditText) findViewById(R.id.descriptionEditText)).getText
						().toString());
				db.update("tasks", cv, "_id = " + task.getId(), null);
				db.close();
			}
			onBackPressed();
		}
	}

	private boolean checkInput() {
		String s = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
		if (s.trim().isEmpty()) {
//			TODO open alert - need to input title
			return false;
		}

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
