package download;

import java.util.ArrayList;

import jericho.JerichoParserFixture;
import jericho.JerichoParserKicker;
import jericho.JerichoParserRank;
import fr.sport.rinkoid.DatabaseHelper;
import fr.sport.rinkoid.IStateChanged;
import fr.sport.rinkoid.PageAdapter;
import fr.sport.rinkoid.Tools;
import fr.sport.rinkoid.kickers.Kicker;
import fr.sport.rinkoid.ranks.Rank;
import fr.sport.rinkoid.shedule.Match;
import android.view.MenuItem;

public class DownloadManager implements IStateChanged {
    private int championship;
    private int page;
    private DatabaseHelper database;
    private PageAdapter pageAdapter;
    private JerichoParserRank rankParser;
    private JerichoParserKicker kickerParser;
    private JerichoParserFixture fixtureParser;

    public DownloadManager(DatabaseHelper database,PageAdapter pageAdapter) {
        this.championship = Tools.N1;
        this.page = Tools.SCHEDULE_PAGE;
        this.database = database;
        this.pageAdapter = pageAdapter;
        this.rankParser = new JerichoParserRank();
        this.kickerParser = new JerichoParserKicker();
        this.fixtureParser = new JerichoParserFixture();
    }

    public void Update(MenuItem menuItem) {
        final int day = pageAdapter.getCurrentDay();
        String[] parameters = {Tools.GetUrl(championship, page),
                page == Tools.SCHEDULE_PAGE ? String.valueOf(day) : "" };
        new AsyncHttpTask(menuItem) {
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                resolve(result, page, day);
            }
        }.execute( parameters );
    }

    private void resolve(String result, int page, int day) {
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
        } else if(page == Tools.SCHEDULE_PAGE) {
            ArrayList<Match> matchs = new ArrayList<Match>();
            fixtureParser.Parse(result, matchs);
            if(!matchs.isEmpty()) {
                database.SaveMatchs(matchs, championship,day);
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
