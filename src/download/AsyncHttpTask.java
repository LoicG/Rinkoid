package download;

import fr.sport.rinkoid.R;
import android.os.AsyncTask;
import android.view.MenuItem;

public class AsyncHttpTask extends AsyncTask< String, Void, String > {
    private MenuItem menuItem;
    private HtmlExtractor extractor;

    public AsyncHttpTask(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.extractor = new HtmlExtractor();
    }

    @Override
    protected void onPreExecute() {
        menuItem.setActionView(R.layout.progress_bar);
        menuItem.expandActionView();
    }

    @Override
    protected String doInBackground(String... params) {
        return extractor.Extract(params[0], params[1]);
    }

    @Override
    protected void onPostExecute(String result) {
        menuItem.collapseActionView();
        menuItem.setActionView(null);
    }
}
