package kr.ac.yonsei.a5days.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.yonsei.a5days.R;
import kr.ac.yonsei.a5days.item.Goal;
import kr.ac.yonsei.a5days.util.DataBase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // 아이템 리스트
    private String[] myDataset;
   //private static ArrayList<? extends item> itemArrayList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DataBase manager = new DataBase(getApplicationContext(),"Goal",null,1);
        List<String> list = manager.select();

       // itemArrayList = new ArrayList<>();
        String str = "";


        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        List<Recycler_item> items=new ArrayList<>();

        for(int i = 0; i < list.size();i++) {
            str = list.get(i);
            String[] str2 = str.split("@");
            //str2[0]="name";//name
            //str2[2];//level
            //리스트 값을 여기에 넣어주기 (addItem)
            //itemArrayList.add(str2[0],str2[2]);
            Recycler_item item = new Recycler_item(R.drawable.star1,str2[0]);
            switch(Integer.valueOf(str2[2])) {
                case 1 :
                    item.setAll(R.drawable.star1,str2[0]);
                    break;
                case 2:
                    item.setAll(R.drawable.star2,str2[0]);
                    break;
                case 3:
                    item.setAll(R.drawable.star3,str2[0]);

            }
            items.add(item);
        }
            recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(),items,R.layout.activity_main));


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

                   // adapter.add(goal.getName(),Integer.toString(goal.getLevel()));
                }
            });
            dialog.show();


        }
    }
}
