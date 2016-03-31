package buzzcz.studentuvpomocnik;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	public static ArrayList<Subject> subjectsMo, subjectsTu, subjectsWe, subjectsTh, subjectsFr,
			subjectsSa, subjectsSu, subjectsOther;
	private String personalNumber, semester;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		readConfig();

		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setLogo(R.drawable.logo);
		setSupportActionBar(toolbar);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);

		showCurrentDay();
	}

	private void readConfig() {
		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string
				.loading_dialog_title), getString(R.string.loading_dialog_text));
		new Thread(new Runnable() {
			@Override
			public void run() {
				loadSubjects(dialog);
			}
		}).start();
	}

	private void loadSubjects(ProgressDialog dialog) {
		try {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			if (!settings.getString("personalNumber", "null").equals(personalNumber) || !settings
					.getString("semester", "null").equals(semester)) {
				personalNumber = settings.getString("personalNumber", null);
				semester = settings.getString("semester", null);
				if (personalNumber != null && !personalNumber.equals("null")) {
					String dir = getFilesDir().getAbsolutePath() + File.separator + personalNumber;
					ArrayList<Subject> timetable = ParseXmls.parseTimetable(new FileInputStream
							(dir + File.separator + "timetable.xml"));
					Subject.sortTimetable(timetable, semester);

					ArrayList<String> ch = new ArrayList<>();
					ch.add("");
					for (Subject s : timetable) s.setItems(ch);

					setEmptySubjectArrayList();
					for (Subject s : timetable) {
						switch (s.getDay()) {
							case 0:
								subjectsMo.add(s);
								break;
							case 1:
								subjectsTu.add(s);
								break;
							case 2:
								subjectsWe.add(s);
								break;
							case 3:
								subjectsTh.add(s);
								break;
							case 4:
								subjectsFr.add(s);
								break;
							case 5:
								subjectsSa.add(s);
								break;
							case 6:
								subjectsSu.add(s);
								break;
							case 7:
								subjectsOther.add(s);
								break;
						}
					}

				} else {
					setEmptySubjectArrayList();
				}
			}
		} catch (XmlPullParserException | IOException e) {
			e.printStackTrace();
			setEmptySubjectArrayList();
		} finally {
			dialog.dismiss();
		}
	}

	private void setEmptySubjectArrayList() {
		subjectsMo = new ArrayList<>();
		subjectsTu = new ArrayList<>();
		subjectsWe = new ArrayList<>();
		subjectsTh = new ArrayList<>();
		subjectsFr = new ArrayList<>();
		subjectsSa = new ArrayList<>();
		subjectsSu = new ArrayList<>();
		subjectsOther = new ArrayList<>();
	}

	private void showCurrentDay() {
		int day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		switch (day) {
			case Calendar.MONDAY:
				mViewPager.setCurrentItem(0);
				break;
			case Calendar.TUESDAY:
				mViewPager.setCurrentItem(1);
				break;
			case Calendar.WEDNESDAY:
				mViewPager.setCurrentItem(2);
				break;
			case Calendar.THURSDAY:
				mViewPager.setCurrentItem(3);
				break;
			case Calendar.FRIDAY:
				mViewPager.setCurrentItem(4);
				break;
			case Calendar.SATURDAY:
				mViewPager.setCurrentItem(5);
				break;
			case Calendar.SUNDAY:
				mViewPager.setCurrentItem(6);
				break;
		}
	}

	public void showSylabus(View v) {
		String subjectName = ((TextView) ((View) v.getParent().getParent()).findViewById(R.id
				.subjectTextView)).getText().toString();
		Intent sylabusIntent = new Intent(this, SylabusActivity.class);
		sylabusIntent.putExtra("personalNumber", personalNumber);
		sylabusIntent.putExtra("subject", subjectName);
		startActivity(sylabusIntent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			Intent settingsIntent = new Intent(this, SettingsActivity.class);
			settingsIntent.putExtra("personalNumber", personalNumber);
			startActivityForResult(settingsIntent, 1);
			return true;
		} else if (id == R.id.action_add_timetable) {
			Intent addTimetableIntent = new Intent(this, AddTimetableActivity.class);
			addTimetableIntent.putExtra("semester", semester);
			startActivityForResult(addTimetableIntent, 0);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {
				subjectsMo = data.getParcelableArrayListExtra("subjects0");
				subjectsTu = data.getParcelableArrayListExtra("subjects1");
				subjectsWe = data.getParcelableArrayListExtra("subjects2");
				subjectsTh = data.getParcelableArrayListExtra("subjects3");
				subjectsFr = data.getParcelableArrayListExtra("subjects4");
				subjectsSa = data.getParcelableArrayListExtra("subjects5");
				subjectsSu = data.getParcelableArrayListExtra("subjects6");
				subjectsOther = data.getParcelableArrayListExtra("subjects7");
				personalNumber = data.getStringExtra("personalNumber");
			}
		} else if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
				subjectsMo = data.getParcelableArrayListExtra("subjects0");
				subjectsTu = data.getParcelableArrayListExtra("subjects1");
				subjectsWe = data.getParcelableArrayListExtra("subjects2");
				subjectsTh = data.getParcelableArrayListExtra("subjects3");
				subjectsFr = data.getParcelableArrayListExtra("subjects4");
				subjectsSa = data.getParcelableArrayListExtra("subjects5");
				subjectsSu = data.getParcelableArrayListExtra("subjects6");
				subjectsOther = data.getParcelableArrayListExtra("subjects7");
				personalNumber = data.getStringExtra("personalNumber");
				semester = data.getStringExtra("semester");
			}
		}
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			return DayFragment.newInstance(position);
		}

		@Override
		public int getCount() {
			// Show 8 total pages.
			return 8;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return getString(R.string.mo);
				case 1:
					return getString(R.string.tu);
				case 2:
					return getString(R.string.we);
				case 3:
					return getString(R.string.th);
				case 4:
					return getString(R.string.fr);
				case 5:
					return getString(R.string.sa);
				case 6:
					return getString(R.string.su);
				case 7:
					return getString(R.string.other);
			}
			return null;
		}
	}
}
