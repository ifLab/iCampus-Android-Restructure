package org.iflab.icampus.School;

/**
 * 显示详细学院信息的activity
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.apache.http.Header;
import org.iflab.icampus.R;
import org.iflab.icampus.http.AsyncHttpIc;
import org.iflab.icampus.model.MyProgressDialog;
import java.util.ArrayList;

public class SchoolDetailActivity extends Activity {
    private TextView school_text;
    private ArrayList<School> schools;
    private int position;
    private MyProgressDialog schoolprogressDialog;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_detail);//引用布局文件
        school_text = (TextView) findViewById(R.id.schoolDetail);

        Intent intent = getIntent();
        schools = (ArrayList<School>) intent.getSerializableExtra("schoolList");
        position = intent.getIntExtra("position", 0);

        schoolprogressDialog = new MyProgressDialog(this, "正在加载中", "请稍后...", false);
        show(position);
    }

    private void show(int position) {
        AsyncHttpIc.get(
                "http://api.bistu.edu.cn/api/api.php?table=collegeintro&action=detail&mod="
                        + schools.get(position).getMod() + "&id="
                        + schools.get(position).getId(),
                null,
                new AsyncHttpResponseHandler() {

                    @Override
                    public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                                          Throwable arg3) {
//                        super.onFailure(arg0, arg1, arg2, arg3);
                    }

                    @Override
                    public void onStart() {
                        schoolprogressDialog.show();
                        super.onStart();
                    }

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
//                        super.onSuccess(arg0, arg1, arg2);
                        String information = new String(arg2);
                        JsonSchoolDetail jsonSchoolDetail = new JsonSchoolDetail();
                        SchoolDetail schoolDetail = jsonSchoolDetail
                                .getList(information);
                        school_text.setText(schoolDetail.getIntroCont());
                        schoolprogressDialog.hide();
                    }
                });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                schoolprogressDialog.cancel();
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
