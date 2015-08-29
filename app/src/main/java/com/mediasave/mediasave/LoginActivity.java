package com.mediasave.mediasave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mediasave.InstagramAPIEndPoint.AuthenticatedUser;
import com.mediasave.InstagramAPIEndPoint.EndPoint;
import com.mediasave.sqltools.DatabaseManager;

import org.json.JSONException;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    public static final String client_side_authentication = "https://instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=token";

    private String access_token;
    private EndPoint endPoint;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        endPoint = EndPoint.getInstance();
        dbManager = new DatabaseManager(this);

        //making the Redirect URL ready to load
        String urlToLoad = client_side_authentication.replace("CLIENT-ID", getString(R.string.client_id));
        urlToLoad = urlToLoad.replace("REDIRECT-URI", getString(R.string.redirect_uri));

        //make the webview load the URL
        WebView view = (WebView) findViewById(R.id.webView);

        //and now trying to get the access token!
        view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("loading URL: " + url);
                if (url.contains("access_token")) {

                    //getting the access_token out of the url
                    String[] temp = url.split("[ = ]+");
                    access_token = temp[1];

                    System.out.println("access_token is : " + access_token);

                    //now let's load the authenticated user's feed
                    loadFeed(access_token);

                    return false;
                }
                else if (url.contains("error_reason")) {
                    Intent access = new Intent(LoginActivity.this, PermissionRequiredActivity.class);
                    startActivity(access);
                    LoginActivity.this
                            .finish();
                    return false;
                }
                else
                    return super.shouldOverrideUrlLoading(view, url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(LoginActivity.this, description + " " + errorCode, Toast.LENGTH_SHORT).show();
                System.out.println(description + " " + errorCode);
            }
        });
        view.loadUrl(urlToLoad);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFeed (String access_token) {
        try {
            //ok here let's first get the basic information of user
            String [] userData = endPoint.getBasicInformationaOfUser("self", access_token);

            //save the user who is authenticated
            dbManager.insertOrReplaceIntoTable(userData);

            //start the MainActivity
            Intent feedIntent = new Intent(this, MainActivity.class);
            feedIntent.putExtra(getString(R.string.authenticated_user_data), userData);
            startActivity(feedIntent);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
