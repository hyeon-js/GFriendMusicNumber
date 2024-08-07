package com.hyeonjs.gfriendmusicnumber;

import android.app.Activity;
import android.app.AlertDialog;
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

        final Pair<String[], String[]> data = createSongList();
        ListView list = new ListView(this);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data.first);
        list.setAdapter(adapter);
        list.setOnItemClickListener((parent, view, pos, id) -> {
            String[] datum = data.second[pos].split(",");
            showDialog(datum[0], ("TJ : " + datum[1].replace(" ", ", ") + "\n" +
                    "금영 : " + datum[2].replace(" ", ", ") + "\n" +
                    "JOYSOUND (한국어) : " + datum[3].replace(" ", ", ") + "\n" +
                    "JOYSOUND (일본어) : " + datum[4].replace(" ", ", "))
                    .replace("null", "정보 없음"));
        });
        layout.addView(list);

        int pad = dip2px(16);
        list.setPadding(pad, pad, pad, pad);

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

    private void showDialog(String title, String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setNegativeButton("닫기", null);
        dialog.show();
    }


    private int dip2px(int dips) {
        return (int) Math.ceil(dips * this.getResources().getDisplayMetrics().density);
    }

}