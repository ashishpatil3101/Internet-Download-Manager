package org.example;

import org.example.models.FileInfo;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DownloadThread extends Thread{
    //for downloading we need

    private FileInfo file;
    //we need controller
    DownloadManager manager;

    //constructor
    public DownloadThread(FileInfo file,DownloadManager manager){
        this.file=file;
        this.manager=manager;
    }

    @Override
    public void run() {

        this.file.setStatus("DOWNLOADING");
        //AFTER staring downloading ui will change

        this.manager.updateUI(this.file);
        //download logic
        try {
            //accepting string convert into legitimate url then copying in local system
            Files.copy(new URL(this.file.getUrl()).openStream(), Paths.get(this.file.getPath()));
            //AFTER COMPLETING DOWNLAOD
            this.file.setStatus("DONE");
        }catch (IOException e){
            this.file.setStatus("FAILED");
            System.out.println("Downloading error");
            e.printStackTrace();

        }
        this.manager.updateUI(this.file);

    }
}
