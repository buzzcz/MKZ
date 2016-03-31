package buzzcz.studentuvpomocnik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

	public boolean changed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		changed = false;
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment
				()).commit();
	}

	@Override
	public void onBackPressed() {
		if (changed) {
			loadSubjects(ProgressDialog.show(this, getString(R.string
					.loading_dialog_title), getString(R.string.loading_dialog_text)));
		} else {
			Intent result = new Intent();
			setResult(RESULT_CANCELED, result);
		}
		finish();
		super.onBackPressed();
	}

	private void loadSubjects(ProgressDialog dialog) {
		try {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			String personalNumber = settings.getString("personalNumber", null);
			String semester = settings.getString("semester", null);
			String dir = getFilesDir().getAbsolutePath() + File.separator + personalNumber;
			ArrayList<Subject> timetable = ParseXmls.parseTimetable(new FileInputStream
					(dir + File.separator + "timetable.xml"));
			Subject.sortTimetable(timetable, semester);

			ArrayList<String> ch = new ArrayList<>();
			ch.add("");
			for (Subject s : timetable) s.setItems(ch);

			Intent result = new Intent();
			result.putExtra("personalNumber", personalNumber);
			result.putExtra("semester", semester);
			ArrayList<Subject> subjects;
			for (int i = 0; i < 8; i++) {
				subjects = new ArrayList<>();
				for (Subject s : timetable) {
					if (s.getDay() == i) {
						subjects.add(s);
					}
				}
				result.putExtra("subjects" + i, subjects);
			}
			setResult(RESULT_OK, result);
			dialog.dismiss();
		} catch (XmlPullParserException | IOException e) {
			e.printStackTrace();
		}
	}

	public static class SettingsFragment extends PreferenceFragment implements SharedPreferences
			.OnSharedPreferenceChangeListener {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.settings);

			ListPreference p = (ListPreference) findPreference("personalNumber");
			String s = PreferenceManager.getDefaultSharedPreferences
					(getActivity().getApplicationContext()).getString("personalNumber", null);
			p.setDefaultValue(s);
			if (s != null && !s.equals("null")) {
				p.setSummary(s);
				SharedPreferences timetables = getActivity().getApplicationContext()
						.getSharedPreferences("timetables", MODE_PRIVATE);
				s = timetables.getString("timetables", null);
				String[] personalNumbers = s.split(",");
				ArrayList<String> personalNumbersEntries = new ArrayList<>();
				for (String personalNumber : personalNumbers) {
					personalNumbersEntries.add(personalNumber.split("/")[2]);
				}
				p.setEntries(personalNumbersEntries.toArray(new CharSequence[0]));
				p.setEntryValues(personalNumbers);
				p.setSummary(p.getEntry());
			}


			p = (ListPreference) findPreference("semester");
			s = PreferenceManager.getDefaultSharedPreferences
					(getActivity().getApplicationContext()).getString("semester", null);
			p.setDefaultValue(s);
			if (s != null && !s.equals("null")) p.setSummary(p.getEntry());
		}

		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			if (key.equals("personalNumber")) {
				ListPreference personalNumber = (ListPreference) findPreference(key);
				personalNumber.setSummary(personalNumber.getEntry());
			} else if (key.equals("semester")) {
				ListPreference semester = (ListPreference) findPreference(key);
				semester.setSummary(semester.getEntry());
			}
			((SettingsActivity) getActivity()).changed = true;
		}

		@Override
		public void onResume() {
			super.onResume();
			getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener
					(this);
		}

		@Override
		public void onPause() {
			super.onPause();
			getPreferenceScreen().getSharedPreferences()
					.unregisterOnSharedPreferenceChangeListener(this);
		}
	}
}
