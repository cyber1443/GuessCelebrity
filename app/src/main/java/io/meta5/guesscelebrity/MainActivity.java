package io.meta5.guesscelebrity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ImageView ivSlika;
    ArrayList<String> pics,names;
    //GetPic picture = new GetPic();


    int correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivSlika = (ImageView)findViewById(R.id.ivSlika);
        String html = null;
        GetData data = new GetData();
        try {
            html = data.execute("http://www.posh24.se/kandisar").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        pics = getTrazeno(html,"<img src=\"(.*?)\"");
        names = getTrazeno(html,"alt=\"(.*?)\"/>");
        start();
    }

    public ArrayList<String> getTrazeno(String html, String regex ){
        ArrayList<String> data = new ArrayList<>();
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(html);
        while(m.find()) {
            data.add(m.group(1));
        }
        return data;
    }

    public void guess (View view){
        view = (Button)view;
        if(((Button) view).getText()==names.get(correct))
            Toast.makeText(this,"Tacno!",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Netacno! " + names.get(correct),Toast.LENGTH_SHORT).show();
        start();

    }

    public void start(){
        Random random = new Random();
        int rand = random.nextInt((99)+1);
        int rand2 = random.nextInt((4-1)+1)+1;
        correct = rand;

        try {
            Bitmap slika = new GetPic().execute(pics.get(correct)).get();
            ivSlika.setImageBitmap(slika);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Button btn = null;
        for (int i=1;i<=4;i++){
            int id = getResources().getIdentifier("btn"+i,"id",getPackageName());
            btn = (Button)findViewById(id);
            if(i==rand2){ btn.setText(names.get(rand));continue;};
            int novi = random.nextInt((99)+1);
            while(rand==novi) novi=random.nextInt((99)+1);
            btn.setText(names.get(novi));

        }

    }

}
