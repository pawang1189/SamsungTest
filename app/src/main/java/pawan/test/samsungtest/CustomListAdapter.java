package pawan.test.samsungtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    Context context;
    ArrayList<MovieData> movieDataArrayList;
    private LayoutInflater layoutInflater;
    private static String img_header = "https://image.tmdb.org/t/p/w500";

    public CustomListAdapter(Context context, ArrayList<MovieData> movieDataArrayList) {
        this.context = context;
        this.movieDataArrayList = movieDataArrayList;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return movieDataArrayList.size();
    }

    @Override
    public MovieData getItem(int position) {
        return movieDataArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_row, null);
        TextView title =  convertView.findViewById(R.id.title_textview);
        TextView popularity =  convertView.findViewById(R.id.poularity_textView);
        TextView genre =  convertView.findViewById(R.id.genre_textview);
        ImageView imageView = convertView.findViewById(R.id.imageView2);

        MovieData movieData = movieDataArrayList.get(position);

        title.setText(movieData.getTitle());
        popularity.setText("Popularity: "+movieData.getPopularity());
        genre.setText("Genre Type: "+ movieData.getGenre());

        String url = img_header+movieData.getImage_url();

        ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask(context, imageView);
        imageDownloaderTask.execute(url);
        return convertView;
    }
}
