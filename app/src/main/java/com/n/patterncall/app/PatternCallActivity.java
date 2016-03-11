package com.n.patterncall.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.n.patterncall.R;
import com.n.patterncall.adapter.PatternDirectoryAdapter;
import com.n.patterncall.model.PatternDirectory;
import com.n.patterncall.util.PatternCallUtils;
import com.n.patterncall.util.PatternLockUtils;

import java.util.ArrayList;

public class PatternCallActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_SELECT_PHONE_NUMBER = 2;
    private static final int REQUEST_CODE_SET_PATTERN_FOR_CALL = 3;
    private static final String KEY_CONFIRM_STARTED = "confirm_started";

    public static final String INTENT_KEY_PHONE_DATA_URI = "intent_key_number_uri";
    public static final String INTENT_KEY_PATTERN = "intent_key_pattern";

    private boolean isConfirmStarted = false;

    private ListView listView;
    private Button button;

    private PatternDirectoryAdapter adapter;
    private PatternDirectory newPatternDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pattern_call);

        listView = (ListView) findViewById(R.id.pattern_call_list);
        button = (Button) findViewById(R.id.pattern_call_btn_add);

        if (savedInstanceState != null) {
            isConfirmStarted = savedInstanceState.getBoolean(KEY_CONFIRM_STARTED);
        }
        if (!isConfirmStarted) {
            PatternLockUtils.confirmPatternIfHas(this);
            isConfirmStarted = true;
            initList();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PatternDirectoryAdapter parentAdapter =
                        (PatternDirectoryAdapter) (parent.getAdapter());
                createDialog(parentAdapter, position);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_SELECT_PHONE_NUMBER);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_CONFIRM_STARTED, isConfirmStarted);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (PatternLockUtils.checkConfirmPatternResult(this, requestCode, resultCode)) {
            isConfirmStarted = false;
        } else if (requestCode == REQUEST_CODE_SELECT_PHONE_NUMBER && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            newPatternDirectory = new PatternDirectory(null,
                    PatternCallUtils.getDataFromUri(uri, this));

            Intent i = new Intent(this, SetPatternActivity.class);
            i.putExtra(INTENT_KEY_PHONE_DATA_URI, uri.toString());
            startActivityForResult(i, REQUEST_CODE_SET_PATTERN_FOR_CALL);
        } else if (requestCode == REQUEST_CODE_SET_PATTERN_FOR_CALL && resultCode == RESULT_OK) {
            String keyPattern = data.getStringExtra(INTENT_KEY_PATTERN);
            newPatternDirectory.setPattern(keyPattern);
            adapter.add(newPatternDirectory);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initList() {
        ArrayList<PatternDirectory> hasPatternDirectories;
        if (PatternCallUtils.hasKeyPatternSet(this)) {
            hasPatternDirectories = PatternCallUtils.createPatternDirectories(this);
        } else {
            hasPatternDirectories = new ArrayList<>();
        }
        adapter = new PatternDirectoryAdapter(this, R.layout.item_pattern_call_list, hasPatternDirectories);

        listView.setAdapter(adapter);
    }

    private void createDialog(final PatternDirectoryAdapter directoryAdapter, final int position) {
        final CharSequence[] items = new CharSequence[] {"Delete", "Cancel"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete Item");
        dialog.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Delete")) {
                    String key = directoryAdapter.getItem(position).getPattern();
                    PatternCallUtils.clearPattern(key, getApplicationContext());
                    directoryAdapter.remove(directoryAdapter.getItem(position));
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
