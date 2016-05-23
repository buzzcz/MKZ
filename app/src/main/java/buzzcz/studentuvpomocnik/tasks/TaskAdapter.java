package buzzcz.studentuvpomocnik.tasks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import buzzcz.studentuvpomocnik.R;

/**
 * Adapter for tasks shown in list view
 * <p>
 * Created by Jaroslav Klaus
 */
class TaskAdapter extends ArrayAdapter<Task> {

	/**
	 * Context passed to adapter
	 */
	private final Context context;
	/**
	 * List of tasks
	 */
	private final ArrayList<Task> tasks;

	/**
	 * Constructor for creating adapter
	 *
	 * @param context Context passed to adapter
	 * @param objects List of tasks
	 */
	public TaskAdapter(Context context, List<Task> objects) {
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
		textView.setText(task.getDate() + " " + task.getTime());

		textView = (TextView) convertView.findViewById(R.id.descriptionTextView);
		textView.setText(task.getDescription());

		return convertView;
	}
}
