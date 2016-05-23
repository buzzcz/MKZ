package buzzcz.studentuvpomocnik.terms;

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

import java.util.ArrayList;

import buzzcz.studentuvpomocnik.R;

/**
 * Activity for showing list of terms
 * <p>
 * Created by Jaroslav Klaus
 */
public class TermsActivity extends AppCompatActivity {

	/**
	 * Student's personal number
	 */
	private String personalNumber;
	/**
	 * Name of the subject of which terms should be shown
	 */
	private String subjectName;
	/**
	 * List of terms
	 */
	private ArrayList<Term> terms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_terms);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		// Float button creates new activity for creating terms
		FloatingActionButton addTermButton = (FloatingActionButton) findViewById(R.id
				.addTermButton);
		addTermButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent addTermIntent = new Intent(view.getContext(), AddTermActivity.class);
				addTermIntent.putExtra("personalNumber", personalNumber);
				addTermIntent.putExtra("subject", subjectName);
				startActivity(addTermIntent);
			}
		});

		personalNumber = getIntent().getStringExtra("personalNumber");
		subjectName = getIntent().getStringExtra("subject");
		setTitle(getTitle() + " " + subjectName);

		// Term is opened for editing on item click
		ListView termsListView = (ListView) findViewById(R.id.termsListView);
		termsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long
					id) {
				Intent addTermIntent = new Intent(view.getContext(), AddTermActivity.class);
				addTermIntent.putExtra("personalNumber", personalNumber);
				addTermIntent.putExtra("subject", subjectName);
				addTermIntent.putExtra("term", terms.get(position));
				startActivity(addTermIntent);
			}
		});
		// Term is deleted on item long click (after confirming the dialog)
		termsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener
				() {


			@Override
			public boolean onItemLongClick(AdapterView<?> parent, final View view, final int
					position,
			                               long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
				builder.setTitle(R.string.delete_term)
						.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								SQLiteDatabase db = (new TermsDatabaseHelper(view.getContext()))
										.getWritableDatabase();
								db.delete("terms", "_id = " + terms.get(position).getId(), null);
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
		// Terms are loaded from database and shown
		final ProgressDialog dialog = ProgressDialog.show(this, getString(R.string
				.loading_dialog_title), getString(R.string.loading_dialog_text));
		final Context context = this;
		new AsyncTask<Object, Object, Object>() {
			@Override
			protected Object doInBackground(Object[] params) {
				terms = new ArrayList<>();
				SQLiteDatabase db = (new TermsDatabaseHelper(context)).getReadableDatabase();
				Cursor c = db.rawQuery("SELECT * FROM terms WHERE personalNumber = \"" +
						personalNumber + "\" AND subject = \"" + subjectName + "\"", null);
				c.moveToFirst();
				while (!c.isAfterLast()) {
					terms.add(new Term(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)
							, c.getString(4)));
					c.moveToNext();
				}
				c.close();
				db.close();
				return null;
			}

			@Override
			protected void onPostExecute(Object o) {
				ListView termsListView = (ListView) findViewById(R.id.termsListView);
				termsListView.setAdapter(new TermAdapter(context, terms));
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
