package net.brotzeller.topeer;

import android.app.Application;
import android.util.Log;

/**
 * Created by martin on 17.02.15.
 */
public class TopeerApplication extends Application {
    public void onCreate ()
  {
    // Setup handler for uncaught exceptions.
    Thread.setDefaultUncaughtExceptionHandler (new Thread.UncaughtExceptionHandler()
    {
      @Override
      public void uncaughtException (Thread thread, Throwable e)
      {
        handleUncaughtException (thread, e);
      }
    });
  }

  public void handleUncaughtException (Thread thread, Throwable e)
  {
    e.printStackTrace(); // not all Android versions will print the stack trace automatically

    Log.e("Topeer", "Uncaught " + e.getClass() + ": " + e.getMessage());

    System.exit(1); // kill off the crashed app
  }
}
