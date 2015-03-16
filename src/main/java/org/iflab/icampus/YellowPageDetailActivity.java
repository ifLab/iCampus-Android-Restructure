package org.iflab.icampus;


import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.iflab.icampus.http.UrlStatic;
import org.iflab.icampus.model.YelloPageList;
import org.iflab.icampus.model.YellowPagePersonAdapter;
import org.iflab.icampus.http.AsyncHttpIc;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


public class YellowPageDetailActivity extends ActionBarActivity {

    private ListView listView;
    private List<YelloPageList> list;
    private View view;
    private Dialog aDialog;
    private int position;
    //private MyProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellowpage_detail);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String depart = intent.getStringExtra("depart");
        // ActionBar actionBar = getActionBar();
        //  actionBar.setDisplayHomeAsUpEnabled(true);
        // actionBar.show();
        //progressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
        this.setTitle(name);

        LayoutInflater inflater = LayoutInflater.from(this);
        view = inflater.inflate(R.layout.yellowpage_person_dialog, null);
        Button call = (Button) view.findViewById(R.id.button_Call);
        Button address = (Button) view.findViewById(R.id.button_addressList);
        Button cancel = (Button) view.findViewById(R.id.button_cancel);
        aDialog = new Dialog(YellowPageDetailActivity.this);
        aDialog.setCanceledOnTouchOutside(true);

        call.setOnClickListener(new Click());
        address.setOnClickListener(new Click());
        cancel.setOnClickListener(new Click());

        listView = (ListView) findViewById(R.id.contact);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view,
                                    int position,
                                    long id) {
                YellowPageDetailActivity.this.position = position;
                aDialog.setTitle(list.get(position).getName());
                aDialog.setContentView(YellowPageDetailActivity.this.view);
                aDialog.show();
            }
        });

        AsyncHttpIc.get(
                UrlStatic.GET_YELLOWPAGE_INFOMATION,
                getItemParams(depart),
                new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        String information = new String(responseBody);
                        list = getList(information);
                        YellowPagePersonAdapter adapter = new YellowPagePersonAdapter(
                                YellowPageDetailActivity.this, list);
                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        Toast.makeText(YellowPageDetailActivity.this, "请检查您的网络状况,稍后再试", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    class Click implements OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_Call:
                    Uri uri = Uri.parse("tel:" + list.get(position).getTelnum());
                    Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                    startActivity(intent);
                    aDialog.hide();
                    break;
                case R.id.button_addressList:
                    Intent intent1 = new Intent(Intent.ACTION_INSERT);
                    intent1.setType("vnd.android.cursor.dir/person");
                    intent1.setType("vnd.android.cursor.dir/contact");
                    intent1.setType("vnd.android.cursor.dir/raw_contact");
                    intent1.putExtra(ContactsContract.Intents.Insert.NAME, list.get(position).getName());
                    intent1.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
                    intent1.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, list.get(position).getTelnum());
                    startActivity(intent1);
                    aDialog.hide();
                    break;
                case R.id.button_cancel:
                    aDialog.hide();
                    break;
                default:
                    break;

            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                aDialog.cancel();
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static RequestParams getItemParams(String depart) {
        RequestParams params = new RequestParams();
        params.put("action", "tel");
        params.put("catid", depart);
        return params;
    }

    public List<YelloPageList> getList(String information) {
        try {
            List<YelloPageList> pagePersonals = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(information);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                YelloPageList pagePersonal = new YelloPageList();
                pagePersonal.setId(jsonObject.getString("id"));
                pagePersonal.setName(jsonObject.getString("name"));
                pagePersonal.setTelnum(jsonObject.getString("telnum"));
                pagePersonal.setDepart(jsonObject.getString("depart"));
                pagePersonals.add(pagePersonal);
            }
            return pagePersonals;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
