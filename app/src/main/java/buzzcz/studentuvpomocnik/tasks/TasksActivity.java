package buzzcz.studentuvpomocnik.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import buzzcz.studentuvpomocnik.R;

/**
 * Activity for showing list of tasks
 * <p>
 * Created by Jaroslav Klaus
 */
public class TasksActivity extends AppCompatActivity {

	/**
	 * Student's personal number
	 */
	private String personalNumber;
	/**
	 * Name of the subject of which tasks should be shown
	 */
	private String subjectName;
	/**
	 * List of tasks
	 */
	private ArrayList<Task> tasks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tasks);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Float button creates new activity for creating tasks
		FloatingActionButton addTaskButton = (FloatingActionButton) findViewById(R.id
				.addTaskButton);
		addTaskButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent addTaskIntent = new Intent(view.getContext(), AddTaskActivity.class);
				addTaskIntent.putExtra("personalNumber", personalNumber);
				addTaskIntent.putExtra("subject", subjectName);
				startActivity(addTaskIntent);
			}
		});

		personalNumber = getIntent().getStringExtra("personalNumber");
		subjectName = getIntent().getStringExtra("subject");
		setTitle(getTitle() + " " + subjectName);

		// Task is opened for editing on item click
		ListView tasksListView = (ListView) findViewById(R.id.tasksListView);
		tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long
					id) {
				Intent addTaskIntent = new Intent(view.getContext(), AddTaskActivity.class);
				addTaskIntent.putExtra("personalNumber", personalNumber);
				addTaskIntent.putExtra("subject", subjectName);
				addTaskIntent.putExtra("task", tasks.get(position));
				startActivity(addTaskIntent);
			}
		});
		// Task is deleted on item long click (after confirming the dialog)
		tasksListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view, final int
					position,
			                               long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setTitle(R.string.delete_task)
						.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								SQLiteDatabase db = (new TasksDatabaseHelper(view.getContext()))
										.getWritableDatabase();
								db.delete("tasks", "_id = " + tasks.get(position).getId(), null);
								db.close();
								onResume();
							}
						})
						.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

							}
						});
				builder.create().show();
				return true;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		// Tasks are loaded from database and shown
		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string
				.loading_dialog_title), getString(R.string.loading_dialog_text));
		final Context context = this;
		new AsyncTask<Object, Object, Object>() {
			@Override
			protected Object doInBackground(Object[] params) {
				tasks = new ArrayList<>();
				SQLiteDatabase db = (new TasksDatabaseHelper(context)).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT * FROM tasks WHERE personalNumber = \"" +
								personalNumber + "\" AND subject = \"" + subjectName + "\" ORDER" +
								" " +
								"BY time",
						null);
				c.moveToFirst();
				try {
					while (!c.isAfterLast()) {
						String date = new SimpleDateFormat("dd.MM.yyyy").format(new
								SimpleDateFormat("yyyy-MM-dd HH:mm").parse(c.getString(3)));
						String time = new SimpleDateFormat("HH:mm").format(new
								SimpleDateFormat("yyyy-MM-dd HH:mm").parse(c.getString(3)));
						tasks.add(new Task(c.getInt(0), c.getString(1), c.getString(2), date,
								time, c.getString(4), c.getString(5)));
						c.moveToNext();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				} finally {
					c.close();
				}
				db.close();
				return null;
			}

			@Override
			protected void onPostExecute(Object o) {
				ListView tasksListView = (ListView) findViewById(R.id.tasksListView);
				tasksListView.setAdapter(new TaskAdapter(context, tasks));
				dialog.dismiss();
			}
		}.execute();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				onBackPressed();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
