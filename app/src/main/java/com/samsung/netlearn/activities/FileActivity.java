package com.samsung.netlearn.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.samsung.netlearn.fragments.wb.datatype.InputImageFragment;
import com.samsung.netlearn.services.FileUtil;

import java.io.File;
import java.io.IOException;


public class FileActivity extends Activity {
    private static final int ACTIVITY_CHOOSE_FILE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBrowse();
    }

    public void onBrowse() {
        Intent chooseFile;
        Intent intent;
        chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
        chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
        chooseFile.setType("image/*");
        intent = Intent.createChooser(chooseFile, "Choose a file");
        startActivityForResult(intent, ACTIVITY_CHOOSE_FILE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode)
        {
            case 1:
            {
                if (resultCode == RESULT_OK)
                {
                    Uri fileUri = data.getData();
                    try {
                        File file = FileUtil.from(this, fileUri);

                        Log.i("file", "File name: " + file.getName());
                        InputImageFragment.bundleFile = file;
                        onBackPressed();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
        }
    }
}
