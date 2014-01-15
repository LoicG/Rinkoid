package fr.sport.rinkoid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.n1:
        	Toast.makeText(getApplicationContext(),
                    "N1", Toast.LENGTH_LONG).show();
            return true;
        case R.id.n2s:
            Toast.makeText(getApplicationContext(),
                    "N2 Sud", Toast.LENGTH_LONG).show();
            return true;
        case R.id.n2n:
            Toast.makeText(getApplicationContext(),
                    "N2 Nord", Toast.LENGTH_LONG).show();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
