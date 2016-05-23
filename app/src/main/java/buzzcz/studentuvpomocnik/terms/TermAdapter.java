package buzzcz.studentuvpomocnik.terms;

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
 * Adapter for terms shown in list view
 * <p>
 * Created by Jaroslav Klaus
 */
class TermAdapter extends ArrayAdapter<Term> {

	/**
	 * Context passed to adapter
	 */
	private final Context context;
	/**
	 * List of terms
	 */
	private final ArrayList<Term> terms;

	/**
	 * Constructor for creating adapter
	 *
	 * @param context Context passed to adapter
	 * @param objects List of terms
	 */
	public TermAdapter(Context context, List<Term> objects) {
		super(context, -1, objects);
		this.context = context;
		terms = (ArrayList<Term>) objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Term term = terms.get(position);
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
					.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.term_item, null);
		}
		TextView textView = (TextView) convertView.findViewById(R.id.titleTextView);
		textView.setText(term.getTitle());

		textView = (TextView) convertView.findViewById(R.id.descriptionTextView);
		textView.setText(term.getDescription());

		return convertView;
	}
}
