package org.iflab.icampus.School;

/**
 * 显示学院列表的activity
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;
import org.iflab.icampus.R;
import org.iflab.icampus.http.AsyncHttpIc;
import org.iflab.icampus.http.UrlStatic;
import org.iflab.icampus.model.MyProgressDialog;
import java.util.ArrayList;

@SuppressLint("NewApi")
public class SchoolActivity extends Activity {
    private ListView schoolListView;//声明一个school界面的listview
    private ArrayList<School> list;//声明一个存放school对象的list
    private MyProgressDialog schoolprogressDialog;//声明一个进度框

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);

        schoolListView = (ListView) findViewById(R.id.school_list);//引用listview控件

        schoolprogressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);//显示获取进度框

        AsyncHttpIc.get(UrlStatic.GET_SCHOOL_INFORMATION,
                null,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onStart() {
                        // TODO Auto-generated method stub
                        schoolprogressDialog.show();
                        super.onStart();
                    }

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
                    }


                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
                        // TODO Auto-generated method stub
                        String information = new String(arg2);
                        if (information.contains("<HTML>")) {
                            Toast.makeText(SchoolActivity.this, "BistuWifi 请登录",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            JsonSchool school = new JsonSchool();
                            list = (ArrayList<School>) school
                                    .getList(information);
                            SchoolAdapter adapter = new SchoolAdapter(
                                    SchoolActivity.this, list);//建立适配器
                            schoolListView.setAdapter(adapter);//把适配器放进去
                        }
                        schoolprogressDialog.hideAndCancel();
                    }
                });


        schoolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Intent intent = new Intent();
                intent.putExtra("schoolList", list);
                intent.putExtra("position", arg2);
                intent.putExtra("introName", list.get(arg2).getIntroName());
                intent.setClass(SchoolActivity.this, SchoolDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
      /*  switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, ICampus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }*/
        return super.onOptionsItemSelected(item);
    }
}
