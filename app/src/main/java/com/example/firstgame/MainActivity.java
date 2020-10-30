package com.example.firstgame;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pdialog;
    private ListView listView;

    // URL of the JSON
    private static String url = "https://api.androidhive.info/contacts/";

    ArrayList<HashMap<String, String>> contactlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactlist = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview);
        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // show loading dialog
            pdialog = new ProgressDialog(MainActivity.this);
            pdialog.setTitle("loading...");
            pdialog.setCancelable(false);
            pdialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            String jsonString = sh.makeServiceCall(url);
            Log.e(TAG, "tekshiramiz 1");
            Log.e(TAG, "Response from url: " + jsonString);
            if (jsonString != null) {
                Log.e(TAG, "tekshiramiz 2");
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // getting json array
                    JSONArray contacts = jsonObject.getJSONArray("contacts");

                    //looping through all contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");

                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        HashMap<String, String> contact = new HashMap<>();
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);
                        contactlist.add(contact);


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "tekshiramiz 3");
                    Log.e(TAG, "Response from url error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Error in json getting from server", Toast.LENGTH_SHORT).show();
                    }
                });


            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (pdialog.isShowing()) {
                pdialog.dismiss();

            }

            // updating json data to listview
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, contactlist, R.layout.list_item, new String[]{"name", "email", "mobile"},
                    new int[]{R.id.name, R.id.email, R.id.mobile});
            listView.setAdapter(adapter);


        }
    }


}
