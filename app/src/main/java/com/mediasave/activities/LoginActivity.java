package com.mediasave.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mediasave.databasetools.DatabaseManager;
import com.mediasave.networking.InstagramEndPointClient;
import com.mediasave.networking.ResponseHandler;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    public static final String client_side_authentication = "https://instagram.com/oauth/authorize/?client_id=CLIENT-ID&redirect_uri=REDIRECT-URI&response_type=token";

    private String access_token;
    private DatabaseManager dbManager;
    private InstagramEndPointClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dbManager = DatabaseManager.getInstance();
        client = InstagramEndPointClient.getInstance();

        //making the Redirect URL ready to load
        String urlToLoad = client_side_authentication.replace("CLIENT-ID", getString(R.string.client_id));
        urlToLoad = urlToLoad.replace("REDIRECT-URI", getString(R.string.redirect_uri));

        WebView view = (WebView) findViewById(R.id.webView);

        //and now trying to get the access token!
        view.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                System.out.println("page finished : " + url);
                if (url.contains("access_token")) {

                    //getting the access_token out of the url
                    String[] temp = url.split("[ = ]+");
                    access_token = temp[1];

                    System.out.println("access_token is : " + access_token);

                    //ok, let's get the basic information of user
                    client.getUserBasicInfo(access_token, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            //and now let's save the data!
                            String [] userData = decodeDataAndSaveThisUserInDatabase(access_token, response);

                            //start the MainActivity
                            Intent feedIntent = new Intent(LoginActivity.this, MainActivity.class);
                            feedIntent.putExtra(WelcomeActivity.USER_DATA, userData);
                            startActivity(feedIntent);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                            System.out.println("failed: "+ errorResponse.toString());
                        }

                    });

                } else if (url.contains("error_reason")) {
                    Intent access = new Intent(LoginActivity.this, PermissionRequiredActivity.class);
                    startActivity(access);
                    LoginActivity.this.finish();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(LoginActivity.this, description + " " + errorCode, Toast.LENGTH_SHORT).show();
                System.out.println(description + " " + errorCode);
            }
        });

        //make the webview load the URL
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

    private String [] decodeDataAndSaveThisUserInDatabase(String access_token, JSONObject response) {
        String[] userData = new String[3];
        try {
            //dig the data out
            JSONObject data = response.getJSONObject("data");
            userData[DatabaseManager.ACCESS_TOKEN_INDEX] = access_token;
            userData[DatabaseManager.PROFILE_PIC_URL_INDEX] = data.getString("profile_picture");
            userData[DatabaseManager.USERNAME_INDEX] = data.getString("username");

            //save the authenticated user's data
            dbManager.insertOrReplaceIntoTable(userData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userData;
    }
}
