package application.rinkoid.download;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class HtmlExtractor
{
    private DefaultHttpClient httpClient_;

    public HtmlExtractor() {
        this.httpClient_ = new DefaultHttpClient();
    }

    public String Extract( String adress, String option ) {
        try {
            HttpResponse response;
            HttpPost methodpost = new HttpPost( adress );
            methodpost.addHeader( "pragma", "no-cache" );
            List< NameValuePair > nvps = new ArrayList< NameValuePair >();
            if( option != "" )
                nvps.add( new BasicNameValuePair( "numero", option ) );
            methodpost.setEntity( new UrlEncodedFormEntity( nvps, HTTP.UTF_8 ) );
            response = httpClient_.execute( methodpost );
            InputStream data = response.getEntity().getContent();
            return Convert( data );
        } catch( ClientProtocolException exception ) {
            Log.d("HtmlExtractor", exception.getLocalizedMessage());
        }
        catch( IOException exception ) {
            Log.d("HtmlExtractor", exception.getLocalizedMessage());
        }
        return "";
    }

    private String Convert( InputStream stream ) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        stream.close();
        return result;
    }
}
