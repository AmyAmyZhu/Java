package com.example.amyzhu.amya4;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amyzhu.amya4.dummy.DummyContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    //Json Step 4
    TextView tvData;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Json Step 1
        new JSONTask().execute("http://test.crowdcurio.com/api/project/?format=json",
                "http://test.crowdcurio.com/api/curio/",
                "http://test.crowdcurio.com/api/user/profile/?format=json");

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                //TO-DO
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            // Json Step 2
            tvData = ((TextView) rootView.findViewById(R.id.item_detail));
            //image = ((WebView) rootView.findViewById(R.id.webview)).loadUrl(mItem.details);
        }

        return rootView;
    }



    //Json Step 3
    public class JSONTask extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... urls) {

            //Description
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            //Question
            HttpURLConnection connection2 = null;
            BufferedReader reader2 = null;

            //Team
            HttpURLConnection connection3 = null;
            BufferedReader reader3 = null;

            try{

                //Description
                URL url = new URL(urls[0]);

                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();

                String line = "";
                while((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                ////

                //Question
                URL url2 = new URL(urls[1]);

                connection2 = (HttpURLConnection) url2.openConnection();
                connection2.connect();

                InputStream stream2 = connection2.getInputStream();
                reader2 = new BufferedReader(new InputStreamReader(stream2));
                StringBuffer buffer2 = new StringBuffer();

                String line2 = "";
                while((line2 = reader2.readLine()) != null){
                    buffer2.append(line2);
                }

                //Team
                URL url3 = new URL(urls[2]);

                connection3 = (HttpURLConnection) url3.openConnection();
                connection3.connect();

                InputStream stream3 = connection3.getInputStream();
                reader3 = new BufferedReader(new InputStreamReader(stream3));
                StringBuffer buffer3 = new StringBuffer();

                String line3 = "";
                while((line3 = reader3.readLine()) != null){
                    buffer3.append(line3);
                }

                ////

                //Description
                String finalJson = buffer.toString();
                JSONArray jsonArray = new JSONArray(finalJson);

                DummyContent.setCount(jsonArray.length());

                JSONObject jsonObject = jsonArray.getJSONObject(0);

                String description = jsonObject.getString("description");

                String name = jsonObject.getString("name");
                mItem.content = name;

                JSONArray teamArray = jsonObject.getJSONArray("team");

                //TO-DO
                //最多39个成员都参加
                //最好改成COUNT
                String[] toppings = new String[39];
                for(int i = 0; i < teamArray.length(); i++){
                    JSONObject teamObject = teamArray.getJSONObject(i);
                    String teamId = teamObject.getString("id");
                    toppings[i] = teamId;
                }

                for(int i=0; i < jsonArray.length();i++) {
                    if (mItem.id == String.valueOf(i)) {
                        jsonObject = jsonArray.getJSONObject(i-1);
                    }

                    description = jsonObject.getString("description");

                    name = jsonObject.getString("name");
                    mItem.content = name;

                    teamArray = jsonObject.getJSONArray("team");

                    for(int j = 0; j < teamArray.length(); j++){
                        JSONObject teamObject = teamArray.getJSONObject(j);
                        String teamId = teamObject.getString("id");
                        toppings[i] = teamId;
                    }
                }

                //Question
                String finalJson2 = buffer2.toString();
                JSONArray jsonArray2 = new JSONArray(finalJson2);

                JSONObject jsonObject2 = jsonArray2.getJSONObject(0);

                String question = jsonObject2.getString("question");

                for(int i=0; i < jsonArray2.length();i++) {
                    if (mItem.id == String.valueOf(i)) {
                        jsonObject2 = jsonArray2.getJSONObject(i-1);
                    }

                    question = jsonObject2.getString("question");
                }

                //Team
                String finalJson3 = buffer3.toString();
                JSONObject parentObject3 = new JSONObject(finalJson3);
                JSONArray parentArray3 = parentObject3.getJSONArray("results");

                StringBuffer finalBufferedData = new StringBuffer();

                for(int i = 0; i < parentArray3.length();i++) {
                    for(int j = 0; j<teamArray.length(); j++){
                        if(toppings[j] != null){
                            JSONObject finalObject = parentArray3.getJSONObject(j);
                            String nickname = finalObject.getString("nickname");
                            String avatar = finalObject.getString("avatar");
                            finalBufferedData.append(avatar + "\n\n" + nickname + "\n\n");
                            toppings[j] = null;

                        }
                    }

                }

                /*
                for(int i = 0; i < parentArray3.length();i++){
                    JSONObject finalObject3 = parentArray3.getJSONObject(i);
                    String nickname = finalObject3.getString("nickname");
                }*/
                ///

                return question + "\n\n" + description + "\n\n" + finalBufferedData.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                try{
                    if(reader != null){
                        reader.close();
                    }
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            tvData.setText(s);
        }
    }

}