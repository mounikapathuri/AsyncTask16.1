package com.android.mounika.asynctask161;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    TextView tvContent;
    Button btnAddData,btnDeleteFile;
    EditText editText;
    String mFileData;
    File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/MyFile.txt");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.etEnterData);
        tvContent = (TextView) findViewById(R.id.tvContent);
        btnAddData = (Button) findViewById(R.id.btnAddData);
        btnDeleteFile = (Button) findViewById(R.id.btnDelete);

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new myAsyncTask().execute();
                Toast.makeText(MainActivity.this,"You Clicked Add",Toast.LENGTH_SHORT).show();
            }
        });

        btnDeleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile();
                Toast.makeText(MainActivity.this,"You Clicked Delete",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void writeIntoFile(){
        //writing data into file
        // calling this method when user cliked Add button
        try{
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            String mData = editText.getText().toString();
            outputStreamWriter.append(mData);
            outputStreamWriter.append("\n");
            outputStreamWriter.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showData(){
        //showing data on textview
        try{
            FileInputStream fileInputStream = new FileInputStream(file);
            DataInputStream dataInputStream = new DataInputStream(fileInputStream);
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataInputStream));
            String strLine;
            while ((strLine = reader.readLine())!=null){
                mFileData = mFileData+"\n"+strLine;
            }
            dataInputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteFile(){
        //deleting data in textview
        file.delete();
        mFileData="";
        tvContent.setText("No File Available");
    }

    private class myAsyncTask extends AsyncTask<String,Intent,String> {

        @Override
        protected String doInBackground(String... strings) {
            writeIntoFile();
            showData();
            return String.valueOf(true);
        }

        @Override
        protected void onProgressUpdate(Intent... values) {
            super.onProgressUpdate(values);
            btnAddData.setEnabled(false);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvContent.setText(mFileData);
            editText.setText("");
            btnAddData.setEnabled(true);
        }
    }
}
