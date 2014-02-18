package download;

import java.util.ArrayList;

import jericho.JerichoParserRank;
import fr.sport.rinkoid.DatabaseHelper;
import fr.sport.rinkoid.PageAdapter;
import fr.sport.rinkoid.Tools;
import fr.sport.rinkoid.ranks.Rank;
import android.view.MenuItem;

public class DownloadManager {
    private int championship;
    private DatabaseHelper database;
    private PageAdapter pageAdapter;

    public DownloadManager(DatabaseHelper database,PageAdapter pageAdapter) {
        this.championship = Tools.N1;
        this.database = database;
        this.pageAdapter = pageAdapter;
    }

    public void Update(MenuItem menuItem) {
        Integer[] parameters = {-1, championship,-1};
        new AsyncHttpTask(menuItem) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                resolve(result);
            }
        }.execute( parameters );
    }

    public void onChampionshipChanged(int championship) {
        this.championship = championship;
    }

    private void resolve(String result) {
        ArrayList<Rank> ranks = new ArrayList<Rank>();
        new JerichoParserRank().Parse(result,ranks);
        if(!ranks.isEmpty()) {
            database.SaveRanks(ranks, championship);
            pageAdapter.onChampionshipChanged(championship);
        }
    }
}
