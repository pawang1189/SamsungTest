package pawan.test.samsungtest;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class NowPlayingFragment extends Fragment {

    private static final String url_now_playing = "https://api.themoviedb.org/4/list/1?api_key=21e3c6c42909058f31c9860e55508ab4&page=1";
    private View view;
    private ProgressDialog progressDialog;
    private ListView listView;
    private ArrayList<MovieData> movieDataList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.now_playing_fragment, container, false);

        listView = view.findViewById(R.id.simpleListView);

        new GetMoviesAsyncTask().execute();
        return view;
    }

    private class GetMoviesAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler httpHandler = new HttpHandler();
            movieDataList = new ArrayList<>();

            // Making a request to url and getting response
            String jsonStr = httpHandler.makeServiceCall(url_now_playing);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray movies = jsonObj.getJSONArray("results");

                    // looping through All Contacts
                    for (int i = 0; i < movies.length(); i++) {
                        JSONObject jsonObject = movies.getJSONObject(i);

                        String title = jsonObject.getString("title");
                        String popularity = jsonObject.getString("popularity");
                        String img_url = jsonObject.getString("poster_path");
                        JSONArray genreCodeArray = jsonObject.getJSONArray("genre_ids");
                        String genreCodes = genreCodeArray.toString();

                        MovieData movieData = new MovieData(title, popularity, img_url, genreCodes);

                        // adding movie details to the list
                        movieDataList.add(movieData);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */


            CustomListAdapter customListAdapter = new CustomListAdapter(getActivity(), movieDataList);
            listView.setAdapter(customListAdapter);
            customListAdapter.notifyDataSetChanged();
        }

    }
}
