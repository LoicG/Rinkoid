package application.rinkoid;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

public class PageTabFactory implements TabContentFactory {

    private final Context context;

    public PageTabFactory(Context ctxt) {
        context = ctxt;
    }

    public View createTabContent(String tag) {
        View v = new View(context);
        v.setMinimumWidth(0);
        v.setMinimumHeight(0);
        return v;
    }
}
