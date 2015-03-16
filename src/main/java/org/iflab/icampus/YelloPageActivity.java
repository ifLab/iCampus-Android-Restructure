package org.iflab.icampus;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.iflab.icampus.http.UrlStatic;
import org.iflab.icampus.model.YelloPageList;
import org.iflab.icampus.model.YellowPageSchoolAdapter;
import org.iflab.icampus.http.AsyncHttpIc;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class YelloPageActivity extends ActionBarActivity {
    private ListView yellowPage;
    private List<YelloPageList> list;
    //private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellowpage);

        setTitle("黄页");
        yellowPage = (ListView) findViewById(R.id.listview_school_list);


        AsyncHttpIc.get(
                UrlStatic.GET_YELLOWPAGE_INFOMATION,
                getItemParams(this),
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String information = new String(responseBody);
                        if (information.contains("<HTML>")) {
                            Toast.makeText(YelloPageActivity.this,
                                    "BistuWifi 请登录",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            list = getList(information);
                            YellowPageSchoolAdapter adapter = new YellowPageSchoolAdapter(
                                    YelloPageActivity.this, list);
                            yellowPage.setAdapter(adapter);
                        }
                        /*/
                        progressDialog.hideAndCancle();
                        //*/
                    }

                    @Override
                    public void onFailure(int statusCode,
                                          Header[] headers,
                                          byte[] responseBody,
                                          Throwable error) {
                        Toast.makeText(YelloPageActivity.this,
                                "请检查您的网络状况,稍后再试",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        yellowPage.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent,
                                            View view,
                                            int position,
                                            long id) {
                        Intent intent = new Intent();
                        intent.putExtra("depart", list.get(position).getDepart());
                        intent.putExtra("name", list.get(position).getName());
                        intent.setClass(YelloPageActivity.this,
                                YellowPageDetailActivity.class);
                        startActivity(intent);

                    }
                });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_yello_page_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        /*/
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:

                Intent intent = new Intent(this, ICampus.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                // finish();
                break;

            default:
                break;
        }
        //*/
        return super.onOptionsItemSelected(item);
    }


    public static RequestParams getItemParams(Context context) {
        RequestParams params = new RequestParams();
        params.put("action", "cat");
        return params;
    }

    public static List<YelloPageList> getList(String information) {
        try {
            List<YelloPageList> pages = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(information);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                YelloPageList page = new YelloPageList();
                page.setId(jsonObject.getString("id"));
                page.setName(jsonObject.getString("name"));
                page.setTelnum(jsonObject.getString("telnum"));
                page.setDepart(jsonObject.getString("depart"));
                pages.add(page);
            }
            return pages;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
