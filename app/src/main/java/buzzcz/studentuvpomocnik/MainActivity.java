package buzzcz.studentuvpomocnik;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import buzzcz.studentuvpomocnik.subjects.AddTimetableActivity;
import buzzcz.studentuvpomocnik.subjects.Subject;
import buzzcz.studentuvpomocnik.syllabuses.SyllabusActivity;
import buzzcz.studentuvpomocnik.tasks.TasksActivity;
import buzzcz.studentuvpomocnik.tasks.TasksDatabaseHelper;
import buzzcz.studentuvpomocnik.terms.TermsActivity;
import buzzcz.studentuvpomocnik.terms.TermsDatabaseHelper;

/**
 * Main class for showing subjects in expandable list views
 * <p>
 * Created by Jaroslav Klaus
 */
public class MainActivity extends AppCompatActivity {

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	/**
	 * Lists of subjects for each day and those without schedule
	 */
	public static ArrayList<Subject> subjectsMo, subjectsTu, subjectsWe, subjectsTh, subjectsFr,
			subjectsSa, subjectsSu, subjectsOther;
	/**
	 * Student's personal number
	 */
	private String personalNumber;
	/**
	 * Semester which subjects should be shown
	 */
	private String semester;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		readConfig();

		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		toolbar.setLogo(R.drawable.logo_transparent);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayShowTitleEnabled(false);

		setupSpinner();
		Spinner personalNumberSpinner = (Spinner) findViewById(R.id.personalNumberSpinner);
		// Set listener for changing personal number in spinner. It is then saved to config files
		// and subjects are loaded
		personalNumberSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String personalNumber = getApplicationContext().getSharedPreferences("timetables",
						MODE_PRIVATE).getString("timetables", null).split(",")[position];
				PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit()
						.remove("personalNumber").putString("personalNumber", personalNumber)
						.commit();
				readConfig();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		// The {@link android.support.v4.view.PagerAdapter} that will provide
		// fragments for each of the sections. We use a
		// {@link FragmentPagerAdapter} derivative, which will keep every
		// loaded fragment in memory. If this becomes too memory intensive, it
		// may be best to switch to a
		// {@link android.support.v4.app.FragmentStatePagerAdapter}.
		SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter
				(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);

		showCurrentDay();
	}

	/**
	 * Fills spinner with available personal numbers read from config files
	 */
	private void setupSpinner() {
		Spinner personalNumberSpinner = (Spinner) findViewById(R.id.personalNumberSpinner);
		// Available personal numbers
		String timetables = getApplicationContext().getSharedPreferences("timetables",
				MODE_PRIVATE).getString("timetables", null);
		// Short personal numbers to be shown in the spinner
		ArrayList<String> personalNumbersEntries = new ArrayList<>();
		if (timetables != null && !timetables.equals("null")) {
			String[] personalNumbers = timetables.split(",");
			// Personal number of currently shown timetable
			String perNum = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
					.getString("personalNumber", "null");
			int pos = 0, i = 0;
			// Fill list with personal numbers and get position of the current one
			for (String personalNumber : personalNumbers) {
				personalNumbersEntries.add(personalNumber.split("/")[2]);
				if (personalNumber.equals(perNum)) pos = i;
				i++;
			}
			// Create and set adapter to spinner and select the current personal number
			ArrayAdapter<String> adapter = new ArrayAdapter<>(getSupportActionBar()
					.getThemedContext(), android.R.layout.simple_spinner_item,
					personalNumbersEntries);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			personalNumberSpinner.setAdapter(adapter);
			personalNumberSpinner.setSelection(pos);
		} else {
			// Create and set adapter to spinner in case there are no available personal numbers
			ArrayAdapter<String> adapter = new ArrayAdapter<>(getSupportActionBar()
					.getThemedContext(), android.R.layout.simple_spinner_item,
					personalNumbersEntries);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			personalNumberSpinner.setAdapter(adapter);
		}
	}

	/**
	 * Shows wait dialog and calls method to read config files and load subjects in an AsyncTask
	 */
	private void readConfig() {
		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string
				.loading_dialog_title), getString(R.string.loading_dialog_text));
		new AsyncTask<Object, Object, Object>() {

			@Override
			protected Object doInBackground(Object... params) {
				loadSubjects();
				return null;
			}

			@Override
			protected void onPostExecute(Object o) {
				if (mViewPager != null) mViewPager.getAdapter().notifyDataSetChanged();
				dialog.dismiss();
			}
		}.execute();
	}

	/**
	 * Reads config files and loads subjects from XML files
	 */
	private void loadSubjects() {
		try {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
			// Currently selected personal number and semester
			personalNumber = settings.getString("personalNumber", null);
			semester = settings.getString("semester", null);
			// Check if semester if set and set it in case it is not
			if (semester == null || semester.equals("null")) {
				Calendar now = Calendar.getInstance();
				Calendar unor = Calendar.getInstance();
				unor.set(now.get(Calendar.YEAR), 1, 1);
				if (now.compareTo(unor) == -1) semester = "ZS";
				else semester = "LS";
				settings.edit().remove("semester").putString("semester", semester).apply();
			}
			if (personalNumber != null && !personalNumber.equals("null")) {
				// Get folder with XML files of selected personal number and parse them
				String dir = getFilesDir().getAbsolutePath() + File.separator + personalNumber;
				ArrayList<Subject> timetable = ParseXmls.parseTimetable(new FileInputStream
						(dir + File.separator + "timetable.xml"));
				Subject.sortTimetable(timetable, semester);

				// Expandable list view needs to have one item to invoke showing the buttons after
				// clicking it's items
				ArrayList<String> ch = new ArrayList<>();
				ch.add("");
				for (Subject s : timetable) s.setItems(ch);

				// Fill lists of subjects from parsed timetable
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
		} catch (XmlPullParserException | IOException e) {
			e.printStackTrace();
			setEmptySubjectArrayList();
		}
	}

	/**
	 * Sets empty lists for subjects
	 */
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

	/**
	 * Selects tab with current day to show today's subjects
	 */
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

	/**
	 * Starts new activity to show syllabus
	 *
	 * @param v view that called this method
	 */
	public void showSyllabus(View v) {
		String subjectName = (String) ((View) v.getParent()).getTag();
		Intent syllabusIntent = new Intent(this, SyllabusActivity.class);
		syllabusIntent.putExtra("personalNumber", personalNumber);
		syllabusIntent.putExtra("subject", subjectName);
		startActivity(syllabusIntent);
	}

	/**
	 * Starts new activity to show tasks
	 *
	 * @param v view that called this method
	 */
	public void showTasks(View v) {
		String subjectName = (String) ((View) v.getParent()).getTag();
		Intent tasksIntent = new Intent(this, TasksActivity.class);
		tasksIntent.putExtra("personalNumber", personalNumber);
		tasksIntent.putExtra("subject", subjectName);
		startActivity(tasksIntent);
	}

	/**
	 * Starts new activity to show terms
	 *
	 * @param v view that called this method
	 */
	public void showTerms(View v) {
		String subjectName = (String) ((View) v.getParent()).getTag();
		Intent termsIntent = new Intent(this, TermsActivity.class);
		termsIntent.putExtra("personalNumber", personalNumber);
		termsIntent.putExtra("subject", subjectName);
		startActivity(termsIntent);
	}

	/**
	 * Starts new activity to show absences
	 *
	 * @param v view that called this method
	 */
	public void showAbsences(View v) {
		String subjectName = (String) ((View) v.getParent()).getTag();
		Intent absencesIntent = new Intent(this, AbsencesActivity.class);
		absencesIntent.putExtra("personalNumber", personalNumber);
		absencesIntent.putExtra("subject", subjectName);
		startActivity(absencesIntent);
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
		} else if (id == R.id.action_delete_timetable) {
			deleteTimetablesDialog();
			return true;
		} else if (id == R.id.action_about) {
			Intent aboutIntent = new Intent(this, AboutActivity.class);
			startActivity(aboutIntent);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * Creates dialog to select timetables to delete and removes their files. They are also
	 * removed from config files. Deleting is performed in AsyncTask
	 */
	private void deleteTimetablesDialog() {
		// Available personal numbers
		String timetables = getApplicationContext().getSharedPreferences
				("timetables", MODE_PRIVATE).getString("timetables", null);
		if (timetables != null && !timetables.equals("null")) {
			final String[] personalNumbers = timetables.split(",");
			// Short personal numbers to be shown for deleting
			final ArrayList<String> perNums = new ArrayList<>();
			for (String s : personalNumbers) {
				perNums.add(s.split("/")[2]);
			}
			boolean checked[] = new boolean[perNums.size()];
			Arrays.fill(checked, false);

			// List containing indexes of selected personal numbers
			final ArrayList<Integer> selected = new ArrayList<>();
			final Context context = getApplicationContext();
			AlertDialog.Builder builder = new AlertDialog.Builder(findViewById(R.id
					.action_delete_timetable).getContext());
			builder.setTitle(R.string.delete_timetable).setMultiChoiceItems(perNums.toArray(new
					CharSequence[perNums.size()]), checked, new DialogInterface
					.OnMultiChoiceClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which, boolean isChecked) {
					if (isChecked) {
						selected.add(which);
					} else if (selected.contains(which)) {
						selected.remove(Integer.valueOf(which));
					}
				}
			}).setNegativeButton(android.R.string.cancel, null).setPositiveButton(android.R.string
					.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					final ProgressDialog waitDialog = ProgressDialog.show(findViewById(R.id
							.action_delete_timetable).getContext(), getString(R.string
							.loading_dialog_title), getString(R.string.loading_dialog_text));
					new AsyncTask<Object, Object, Object>() {

						/**
						 * Indicates if the currently shown personal number was selected to be
						 * deleted
						 */
						private boolean change = false;

						@Override
						protected Object doInBackground(Object... params) {
							perNums.clear();
							for (Integer i : selected) {
								perNums.add(personalNumbers[i]);
							}

							SharedPreferences settings = getSharedPreferences("timetables",
									MODE_PRIVATE);
							String timetables = settings.getString("timetables", null);
							for (String s : perNums) {
								File f = new File(getFilesDir().getAbsolutePath(), s);
								deleteRecursive(f);
								SQLiteDatabase db = (new TermsDatabaseHelper(context))
										.getWritableDatabase();
								db.delete("terms", "personalNumber = \"" + s + "\"", null);
								db.close();

								db = (new TasksDatabaseHelper(context))
										.getWritableDatabase();
								db.delete("tasks", "personalNumber = \"" + s + "\"", null);
								db.close();

								if (timetables.contains(s + ",")) {
									timetables = timetables.replace(s + ",", "");
								} else if (timetables.contains("," + s)) {
									timetables = timetables.replace("," + s, "");
								} else if (timetables.contains(s)) {
									timetables = timetables.replace(s, "");
								}

								if (s.equals(personalNumber)) change = true;
							}
							if (!timetables.trim().isEmpty())
								settings.edit().remove("timetables").putString("timetables",
										timetables).commit();
							else {
								settings.edit().remove("timetables").putString("timetables",
										null).commit();
								timetables = null;
							}
							if (change) {
								if (timetables != null) {
									personalNumber = timetables.split(",")[0];
									PreferenceManager.getDefaultSharedPreferences
											(getApplicationContext()).edit().remove
											("personalNumber").putString("personalNumber",
											personalNumber).commit();
								} else {
									personalNumber = null;
									PreferenceManager.getDefaultSharedPreferences
											(getApplicationContext()).edit().remove
											("personalNumber").putString("personalNumber",
											personalNumber).commit();
								}
							}
							return null;
						}

						@Override
						protected void onPostExecute(Object o) {
							waitDialog.dismiss();
							if (change) readConfig();
							setupSpinner();
						}
					}.execute();

				}
			});
			builder.create().show();
		} else {
			Toast.makeText(getApplicationContext(), R.string.nothing_to_delete, Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * Recursively deletes files and folders
	 *
	 * @param f file or folder to be deleted
	 */
	private void deleteRecursive(File f) {
		if (f.isDirectory())
			for (File child : f.listFiles())
				deleteRecursive(child);

		f.delete();
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
				setupSpinner();
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
				setupSpinner();
			}
		}
	}

	/**
	 * A {@link FragmentStatePagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		/**
		 * Constructor of adapter
		 *
		 * @param fm fragment manager
		 */
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			return DayFragment.newInstance(position);
		}

		@Override
		public int getItemPosition(Object object) {
			((DayFragment) object).onResume();
			return POSITION_NONE;
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
