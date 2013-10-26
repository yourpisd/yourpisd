package app.sunstreak.yourpisd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class AppRater {
    private final static String APP_TITLE = "yourPISD";
    private final static String APP_PNAME = "app.sunstreak.yourpisd";
    
    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 7;
    
    public static void app_launched(Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("apprater", 0);
        if (prefs.getBoolean("dontshowagain", false)) { return ; }
        
        SharedPreferences.Editor editor = prefs.edit();
        
        // Increment launch counter
        long launch_count = prefs.getLong("launch_count", 0) + 1;
        editor.putLong("launch_count", launch_count);

        // Get date of first launch
        Long date_firstLaunch = prefs.getLong("date_firstlaunch", 0);
        if (date_firstLaunch == 0) {
            date_firstLaunch = System.currentTimeMillis();
            editor.putLong("date_firstlaunch", date_firstLaunch);
        }
        
        // Wait at least n days before opening
        if (launch_count >= LAUNCHES_UNTIL_PROMPT) {
            if (System.currentTimeMillis() >= date_firstLaunch + 
                    (DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000)) {
                showRateDialog(mContext, editor);
            }
        }
        
        editor.commit();
    }   
    
    public static void showRateDialog(final Context mContext, final SharedPreferences.Editor editor) {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Rate " + APP_TITLE);
        dialog.setMessage("Enjoying yourPISD? Please take a few seconds to rate us. Thank you for your support.");
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Rate yourPISD", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME)));
                dialog.dismiss();
          } }); 

          dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No, thanks", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            	if (editor != null) {
                  editor.putBoolean("dontshowagain", true);
                  editor.commit();
              }
              dialog.dismiss();
          }}); 

          dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Remind me later", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            	dialog.dismiss();
          }});

                
        dialog.show();        
    }
}