package com.hyeonjs.gfriendmusicnumber;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(1);

        Pair<String[], String[]> data = createSongList();
        ListView list = new ListView(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data.first);
        list.setAdapter(adapter);
        layout.addView(list);

        setContentView(layout);
    }

    private Pair<String[], String[]> createSongList() {
        try {
            String[] data = readData(getAssets().open("songs.csv")).split("\n");
            String[] names = new String[data.length];
            String[] list = new String[data.length];
            for (int n = 0; n < data.length; n++) {
                String[] datum = data[n].split(",");
                names[n] = datum[0];
                list[n] = data[n];
            }
            return new Pair<>(names, list);
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            return new Pair<>(new String[]{}, new String[]{});
        }
    }

    private String readData(InputStream stream) {
        try {
            InputStreamReader isr = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(isr);
            String str = br.readLine();
            String line = "";
            while ((line = br.readLine()) != null) {
                str += "\n" + line;
            }
            isr.close();
            br.close();
            return str;
        } catch (Exception e) {
//            return e.toString();
        }
        return "";
    }


}