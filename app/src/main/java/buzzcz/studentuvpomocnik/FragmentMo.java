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
public class FragmentMo extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	public FragmentMo() {
	}

	/**
	 * Returns a new instance of this fragment for the given section
	 * number.
	 */
	public static FragmentMo newInstance(int sectionNumber) {
		FragmentMo fragment = new FragmentMo();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_mo, container, false);
		ExpandableListView timetableListView = (ExpandableListView) rootView.findViewById(R.id
				.timetableMoExpandableListView);
		TimetableAdapter adapter = new TimetableAdapter(timetableListView.getContext(),
				MainActivity.subjectsMo);
		timetableListView.setAdapter(adapter);
		return rootView;
	}
}