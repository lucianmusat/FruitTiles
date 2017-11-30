package at.lucianmus.fruittiles;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.app.Activity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private static int totalFlipped = 0;
    public List<Tile> tiles = new ArrayList<Tile>();

    // Generate values for tiles
    private List<Integer> values = new ArrayList<Integer>();

    ImageAdapter(Context c, int nrTiles){
        mContext = c;
        for(int i=0; i<nrTiles; i++){
            values.add(i);
            values.add(i);
        }
        values.subList(nrTiles, values.size()).clear();

        // Randomly scramble the values vector
        Collections.shuffle(values);

        // Set randomized values to the tiles
        for(int i=0; i<nrTiles; i++)
            tiles.add(new Tile((Activity)c, R.id.Tile, values.get(i)));
    }


    @Override
    public int getCount() {
        return tiles.size();
    }

    @Override
    public Object getItem(int position) {
        return tiles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        return tiles.get(position).img;
    }
}