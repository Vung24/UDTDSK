package com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.R;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.dao.HeartHealthDAO;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.model.HeartHealth;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.main.adapter.HeartHealthAdapter;
import com.example.n15_20242it6029001_udtheodoisuckhoedientu.common.activity.MainActivity;
public class HeartHealthListActivity extends AppCompatActivity {
    ListView lvHH;
    Button btnHome, btnRemove;
    ArrayList<HeartHealth> arrayList = new ArrayList<>();
    //ArrayAdapter<HeartHealth> adapter = null;
    HeartHealthDAO heartHealthDAO = null;
    int selectedItemPosition = -1;
    String itemId;
    HeartHealthAdapter adapterHeartHealth = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ntv_activity_health_heart_list);
        getWidget();
        loadData();
        lvHH.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemId = String.valueOf(((HeartHealth) parent.getItemAtPosition(position)).getHeart_health_id());
                selectedItemPosition = position;
            }
        });
    }

    private void getWidget() {
        lvHH = findViewById(R.id.lvHH);
        btnHome = findViewById(R.id.btnHome);
        btnRemove = findViewById(R.id.btnRemove);
        btnHome.setOnClickListener(new doSomeTHing());
        btnRemove.setOnClickListener(new doSomeTHing());
        adapterHeartHealth = new HeartHealthAdapter(this,R.layout.ntv_custom_view_hearthealth,arrayList);
        lvHH.setAdapter(adapterHeartHealth);
    }

    protected class doSomeTHing implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.btnAdd) {
                Intent intent = new Intent(HeartHealthListActivity.this, HeartHealthActivity.class);
                startActivity(intent);
            }
            if (id == R.id.btnHome) {
                Intent intent = new Intent(HeartHealthListActivity.this, MainActivity.class);
                startActivity(intent);
            }
            if (id == R.id.btnRemove) {
                removeData();
            }
        }
    }

    private void loadData() {
        arrayList.clear();
        heartHealthDAO = new HeartHealthDAO(this);
        arrayList.addAll(heartHealthDAO.getAll());
        adapterHeartHealth.notifyDataSetChanged();
    }

    private void showDeleteConfirm(String id, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(HeartHealthListActivity.this);
        builder.setTitle("Xác nhận xóa!");
        builder.setMessage("Bạn có chắc muốn xóa mục này?");
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                heartHealthDAO.delete(id);
                arrayList.remove(position);
                adapterHeartHealth.notifyDataSetChanged();
                Toast.makeText(HeartHealthListActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                selectedItemPosition = -1;
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void removeData() {

        if (selectedItemPosition != AdapterView.INVALID_POSITION) {
            showDeleteConfirm(itemId, selectedItemPosition);
        } else {
            Toast.makeText(HeartHealthListActivity.this, "Please select an item to remove!", Toast.LENGTH_SHORT).show();
        }
    }
    public void back(View v){
        finish();
    }
}