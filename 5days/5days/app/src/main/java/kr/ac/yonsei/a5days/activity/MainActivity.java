package kr.ac.yonsei.a5days.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import kr.ac.yonsei.a5days.R;
import kr.ac.yonsei.a5days.item.Goal;
import kr.ac.yonsei.a5days.util.DataBase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DataBase manager = new DataBase(getApplicationContext(),"Goal",null,1);
        List<String> list = manager.select();
        TextView text = findViewById(R.id.text2);
        String str = null;
        for(int i = 0; i < list.size();i++){
            str += list.get(i)+"\n";
        }
        text.setText(str);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fab){
            CustomDialog dialog = new CustomDialog(this);
            dialog.setDialogListener(new CustomDialog.CustomDialogListener() {
                @Override
                public void onPositiveClicked(Goal goal) {
                    TextView t = findViewById(R.id.text2);
                    DataBase manager = new DataBase(getApplicationContext(),"Goal",null,1);
                    manager.exequte("INSERT INTO Goal values('"+goal.getName()
                            +"', '"+goal.getDate()+"', "+goal.getLevel()+", "+goal.getPoint()+")");
                }
            });
            dialog.show();
        }
    }
}
