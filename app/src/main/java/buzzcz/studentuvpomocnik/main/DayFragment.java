package buzzcz.studentuvpomocnik.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import buzzcz.studentuvpomocnik.R;
import buzzcz.studentuvpomocnik.timetables.TimetableAdapter;

/**
 * A placeholder fragment containing expandable list view
 * <p>
 * Created by Jaroslav Klaus
 */
public class DayFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Constructor to create new fragment
	 */
	public DayFragment() {
	}

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static DayFragment newInstance(int sectionNumber) {
		DayFragment fragment = new DayFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Sets adapter with subjects to the expandable list view
	 */
	@Override
	public void onResume() {
		super.onResume();
		ExpandableListView timetableListView = (ExpandableListView) getView().findViewById(R.id
				.timetableExpandableListView);
		switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
			case 0:
				timetableListView.setAdapter(new TimetableAdapter(timetableListView.getContext(),
						MainActivity.subjectsMo));
				break;
			case 1:
				timetableListView.setAdapter(new TimetableAdapter(timetableListView.getContext(),
						MainActivity.subjectsTu));
				break;
			case 2:
				timetableListView.setAdapter(new TimetableAdapter(timetableListView.getContext(),
						MainActivity.subjectsWe));
				break;
			case 3:
				timetableListView.setAdapter(new TimetableAdapter(timetableListView.getContext(),
						MainActivity.subjectsTh));
				break;
			case 4:
				timetableListView.setAdapter(new TimetableAdapter(timetableListView.getContext(),
						MainActivity.subjectsFr));
				break;
			case 5:
				timetableListView.setAdapter(new TimetableAdapter(timetableListView.getContext(),
						MainActivity.subjectsSa));
				break;
			case 6:
				timetableListView.setAdapter(new TimetableAdapter(timetableListView.getContext(),
						MainActivity.subjectsSu));
				break;
			case 7:
				timetableListView.setAdapter(new TimetableAdapter(timetableListView.getContext(),
						MainActivity.subjectsOther));
				break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_main, container, false);
	}
}