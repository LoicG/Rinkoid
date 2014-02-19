package download;

import java.util.ArrayList;

import jericho.JerichoParserKicker;
import jericho.JerichoParserRank;
import fr.sport.rinkoid.DatabaseHelper;
import fr.sport.rinkoid.IStateChanged;
import fr.sport.rinkoid.PageAdapter;
import fr.sport.rinkoid.Tools;
import fr.sport.rinkoid.kickers.Kicker;
import fr.sport.rinkoid.ranks.Rank;
import android.view.MenuItem;

public class DownloadManager implements IStateChanged {
    private int championship;
    private int page;
    private DatabaseHelper database;
    private PageAdapter pageAdapter;
    private JerichoParserRank rankParser;
    private JerichoParserKicker kickerParser;

    public DownloadManager(DatabaseHelper database,PageAdapter pageAdapter) {
        this.championship = Tools.N1;
        this.page = Tools.SCHEDULE_PAGE;
        this.database = database;
        this.pageAdapter = pageAdapter;
        this.rankParser = new JerichoParserRank();
        this.kickerParser = new JerichoParserKicker();
    }

    public void Update(MenuItem menuItem) {
        String[] parameters = {Tools.GetUrl(championship, page)};
        new AsyncHttpTask(menuItem) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                resolve(result,page);
            }
        }.execute( parameters );
    }

    private void resolve(String result, int page) {
        boolean valid = false;
        if(page == Tools.RANKS_PAGE) {
            ArrayList<Rank> ranks = new ArrayList<Rank>();
            rankParser.Parse(result, ranks);
            if(!ranks.isEmpty()) {
                database.SaveRanks(ranks, championship);
                valid = true;
            }
        } else if(page == Tools.KICKERS_PAGE) {
            ArrayList<Kicker> kickers = new ArrayList<Kicker>();
            kickerParser.Parse(result, kickers);
            if(!kickers.isEmpty()) {
                database.SaveKicker(kickers, championship);
                valid = true;
            }
        }
        if( valid ) {
            pageAdapter.onChampionshipChanged(championship);
        }
    }
    @Override
    public void onChampionshipChanged(int championship) {
        this.championship = championship;
    }

    @Override
    public void onPageChanged(int page) {
        this.page = page;
    }
}
