package com.example.sylvancombinedui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class DBImportExport {

    public class ExportImportDB extends Activity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            //creating a new folder for the database to be backuped to
            //Navigate to this folder to find backup databases
            //TO-DO: Build string that appends date to back-up database
            //And build option that allows the database to store as a zip file
            //Ask sylvan if he wants manual or auto backups
            File direct = new File(Environment.getExternalStorageDirectory() + "/Backup-Database");

            if(!direct.exists())
            {
                if(direct.mkdir())
                {
                    //directory is created;
                }

            }
            exportDB();
            importDB();

        }
        //importing database
        private void importDB() {
            // TODO Auto-generated method stub

            try {
                File sd = Environment.getExternalStorageDirectory();
                File data  = Environment.getDataDirectory();

                if (sd.canWrite()) {
                    String  currentDBPath= "//data//" + "SylvanCombinedUI"
                            + "//databases//" + "UserData.db";
                    String backupDBPath  = "/BackupFolder/UserData.db";
                    File  backupDB= new File(data, currentDBPath);
                    File currentDB  = new File(sd, backupDBPath);

                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getBaseContext(), backupDB.toString(),
                            Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {

                Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                        .show();

            }
        }
        //exporting database
        private void exportDB() {
            // TODO Auto-generated method stub

            try {
                File sd = Environment.getExternalStorageDirectory();
                File data = Environment.getDataDirectory();

                if (sd.canWrite()) {
                    String  currentDBPath= "//data//" + "SylvanCombinedUI"
                            + "//databases//" + "UserData.db";
                    String backupDBPath  = "/BackupFolder/UserData.db";
                    File currentDB = new File(data, currentDBPath);
                    File backupDB = new File(sd, backupDBPath);

                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                    Toast.makeText(getBaseContext(), backupDB.toString(),
                            Toast.LENGTH_LONG).show();

                }
            } catch (Exception e) {

                Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                        .show();

            }
        }

    }
}
