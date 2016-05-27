package buzzcz.studentuvpomocnik;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Activity with settings. It allows users to change timetables and semesters to show
 * <p>
 * Created by Jaroslav Klaus
 */
public class SettingsActivity extends AppCompatActivity {

	/**
	 * Indicates if any changes were made
	 */
	private boolean changed;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		changed = false;
		getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment
				()).commit();
	}

	@Override
	public void onBackPressed() {
		Intent result = new Intent();
		if (changed) {
			setResult(RESULT_OK, result);
		} else {
			setResult(RESULT_CANCELED, result);
		}
		finish();
		super.onBackPressed();
	}

	/**
	 * Fragment for showing settings and listening for changes
	 */
	public static class SettingsFragment extends PreferenceFragment implements SharedPreferences
			.OnSharedPreferenceChangeListener {

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.settings);

			// Set personal numbers to the list preference
			ListPreference p = (ListPreference) findPreference("personalNumber");
			String s = PreferenceManager.getDefaultSharedPreferences
					(getActivity().getApplicationContext()).getString("personalNumber", null);
			p.setDefaultValue(s);
			if (s != null && !s.equals("null")) {
				SharedPreferences timetables = getActivity().getApplicationContext()
						.getSharedPreferences("timetables", MODE_PRIVATE);
				s = timetables.getString("timetables", null);
				String[] personalNumbers = s.split(",");
				ArrayList<String> personalNumbersEntries = new ArrayList<>();
				for (String personalNumber : personalNumbers) {
					personalNumbersEntries.add(personalNumber.split("/")[2]);
				}
				p.setEntries(personalNumbersEntries.toArray(new
						CharSequence[personalNumbersEntries.size()]));
				p.setEntryValues(personalNumbers);
				p.setSummary(p.getEntry());
			} else {
				p.setEntries(new CharSequence[0]);
				p.setEntryValues(new CharSequence[0]);
			}

			// Set semesters to the list preference
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
