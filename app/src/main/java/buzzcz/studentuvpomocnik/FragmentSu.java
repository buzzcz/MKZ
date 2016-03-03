package buzzcz.studentuvpomocnik;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentSu extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	public FragmentSu() {
	}

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static FragmentSu newInstance(int sectionNumber) {
		FragmentSu fragment = new FragmentSu();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_su, container, false);
		ExpandableListView timetableListView = (ExpandableListView) rootView.findViewById(R.id
				.timetableSuExpandableListView);
		TimetableAdapter adapter = new TimetableAdapter(timetableListView.getContext(),
				MainActivity.subjectsSu);
		timetableListView.setAdapter(adapter);
		return rootView;
	}
}