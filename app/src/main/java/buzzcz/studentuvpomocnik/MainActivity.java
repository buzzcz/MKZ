package buzzcz.studentuvpomocnik;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		TODO if files doesn't exist "click" add timetable button, implement config file (last
// settings) and change it afterwards

		subjectsMo = new ArrayList<>();
		subjectsTu = new ArrayList<>();
		subjectsWe = new ArrayList<>();
		subjectsTh = new ArrayList<>();
		subjectsFr = new ArrayList<>();
		subjectsSa = new ArrayList<>();
		subjectsSu = new ArrayList<>();
		subjectsOther = new ArrayList<>();

		setContentView(R.layout.activity_main);

		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
		tabLayout.setupWithViewPager(mViewPager);
	}

	public void showSylabus(View v) {
		String name = ((TextView) ((View) v.getParent().getParent()).findViewById(R.id
				.subjectTextView)).getText().toString();
		Intent sylabusIntent = new Intent(this, SylabusActivity.class);
		sylabusIntent.putExtra("subject", name);
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
			return true;
		} else if (id == R.id.action_add_timetable) {
			Intent addTimetableIntent = new Intent(this, AddTimetableActivity.class);
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
