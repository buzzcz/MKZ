package buzzcz.studentuvpomocnik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

	private Context context;
	private ArrayList<Task> tasks;

	public TaskAdapter(Context context, int resource, List<Task> objects) {
		super(context, -1, objects);
		this.context = context;
		tasks = (ArrayList<Task>) objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Task task = tasks.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
					.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.task_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.titleTextView);
		textView.setText(task.getTitle());

		textView = (TextView) convertView.findViewById(R.id.timeTextView);
		textView.setText(task.getTime());

		textView = (TextView) convertView.findViewById(R.id.descriptionTextView);
		textView.setText(task.getDescription());

		return convertView;
	}
}
