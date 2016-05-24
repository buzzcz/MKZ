package buzzcz.studentuvpomocnik;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;

/**
 * Class for showing and editing absences of a subject
 * <p>
 * Created by Jaroslav Klaus
 */
public class AbsencesActivity extends AppCompatActivity {

	/**
	 * Student's personal number
	 */
	private String personalNumber;
	/**
	 * Name of the subject which absences are shown/edited
	 */
	private String subjectName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_absences);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		personalNumber = getIntent().getStringExtra("personalNumber");
		subjectName = getIntent().getStringExtra("subject");
		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		int absences = prefs.getInt(personalNumber + "/" + subjectName, 0);

		final NumberPicker absencesPicker = (NumberPicker) findViewById(R.id.absencesPicker);
		absencesPicker.setMinValue(0);
		absencesPicker.setMaxValue(Integer.MAX_VALUE);
		absencesPicker.setWrapSelectorWheel(false);
		absencesPicker.setValue(absences);

	}

	/**
	 * Method to save absences
	 *
	 * @param v view which called this method
	 */
	public void addAbsence(View v) {
		SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.remove(personalNumber + "/" + subjectName).putInt(personalNumber + "/" +
				subjectName, ((NumberPicker) findViewById(R.id.absencesPicker)).getValue());
		editor.apply();
		onBackPressed();
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
