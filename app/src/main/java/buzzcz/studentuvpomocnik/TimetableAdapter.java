package buzzcz.studentuvpomocnik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TimetableAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<Subject> groups;

	public TimetableAdapter(Context context, ArrayList<Subject> groups) {
		this.context = context;
		this.groups = groups;
	}

	@Override
	public int getGroupCount() {
		return groups.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return groups.get(groupPosition).getItems().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return groups.get(groupPosition).getItems().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup
			parent) {
		Subject subject = (Subject) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
					.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.subject_group, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.subjectTextView);
		textView.setText(subject.getStarts() + "-" + subject.getEnds() + " " + subject
				.getDepartment() + "/" + subject.getShortcut());

		textView = (TextView) convertView.findViewById(R.id.subjectNameTextView);
		textView.setText(subject.getName());

		textView = (TextView) convertView.findViewById(R.id.roomTextView);
		textView.setText(subject.getRoom());

		textView = (TextView) convertView.findViewById(R.id.teacherTextView);
		textView.setText(subject.getTeacher());

		textView = (TextView) convertView.findViewById(R.id.typeTextView);
		textView.setText(subject.getType());
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View
			convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
					.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.subject_child, null);
		}
		Button button = (Button) convertView.findViewById(R.id.subjectChildButton);
		button.setText(groups.get(groupPosition).getItems().get(childPosition));
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
}