package application.rinkoid;

import android.app.Application;
import org.acra.*;
import org.acra.annotation.*;

@ReportsCrashes( formKey = "", // will not be used
mailTo = "geffroy.loic@gmail.com" )
public class ACRALogger extends Application
{
    @Override
    public void onCreate()
    {
        ACRA.init( this );
        super.onCreate();
    }
}
