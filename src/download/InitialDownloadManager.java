package download;

import java.util.ArrayList;
import java.util.LinkedList;

import jericho.JerichoParserFixture;
import jericho.JerichoParserRank;

import fr.sport.rinkoid.DatabaseHelper;
import fr.sport.rinkoid.PageAdapter;
import fr.sport.rinkoid.R;
import fr.sport.rinkoid.Tools;
import fr.sport.rinkoid.ranks.Rank;
import fr.sport.rinkoid.shedule.Match;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

public class InitialDownloadManager {
    private ProgressDialog progressDialog;
    private JerichoParserRank rankParser;
    private JerichoParserFixture fixtureParser;
    private LinkedList<Integer> championships;
    private int total;
    private int current;
    private int championship;
    private Context context;
    private PageAdapter pageAdapter;

    public InitialDownloadManager(Context context,PageAdapter pageAdapter) {
        this.context = context;
        this.rankParser = new JerichoParserRank();
        this.fixtureParser = new JerichoParserFixture();
        this.pageAdapter = pageAdapter;
        Resources ressources = context.getResources();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(ressources.getString(R.string.downloadText));
        progressDialog.setMessage(ressources.getString(R.string.download));
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setIcon(R.drawable.ic_action_download);
        progressDialog.setCancelable(false);
    }

    public void Initialize(LinkedList<Integer> championships) {
        progressDialog.show();
        this.championships = championships;
        for(Integer championship : championships) {
            total += Tools.GetDaysCount(championship) + 1;
        }
        current = 0;
        Download(new ArrayList<String>());
    }

    private void Download(ArrayList<String> results) {
        Parse(results);
        if(championships.isEmpty()) {
            progressDialog.dismiss();
            pageAdapter.onChampionshipChanged(Tools.N1);
        } else {
            championship = championships.removeFirst();
            Integer[] parameters = { championship };
            new AsyncHttpTask().execute( parameters );
        }
    }

    private void Parse(ArrayList<String> results) {
        if(!results.isEmpty()) {
            for(int i = 0; i < results.size() - 1; ++i) {
                ArrayList<Match> matchs = new ArrayList<Match>();
                fixtureParser.Parse(results.get(i), matchs);
                if(!matchs.isEmpty()) {
                    DatabaseHelper.getInstance(context).SaveMatchs(matchs, championship,i);
                }
            }
            ArrayList<Rank> ranks = new ArrayList<Rank>();
            rankParser.Parse(results.get(results.size() - 1), ranks);
            if(!ranks.isEmpty()) {
                DatabaseHelper.getInstance(context).SaveRanks(ranks, championship);
            }
        }
    }

    private class AsyncHttpTask extends AsyncTask<Integer, Integer, ArrayList<String>> {
        private HtmlExtractor extractor;

        public AsyncHttpTask() {
            this.extractor = new HtmlExtractor();
        }

        @Override
        protected void onPreExecute() {
            progressDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Integer... params) {
            ArrayList<String> result = new ArrayList<String>();
            String url = Tools.GetUrl(params[0], Tools.SCHEDULE_PAGE);
            for(int i = 0; i <= Tools.GetDaysCount(params[0]); ++i) {
                result.add(extractor.Extract(url, String.valueOf(i)));
                publishProgress(current++);
            }
            result.add(extractor.Extract(Tools.GetUrl(params[0],
                    Tools.RANKS_PAGE), ""));
            current++;
            publishProgress(current++);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            Download(result);
        }

        protected void onProgressUpdate(Integer... progress) {
            progressDialog.setProgress((progress[0] * 100) / total);
        }
    }
}
