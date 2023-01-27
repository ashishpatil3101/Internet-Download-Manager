package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.config.AppConfig;
import org.example.models.FileInfo;

import java.io.File;

public class DownloadManager {
    @FXML
    private TableView<FileInfo> tableView;
    @FXML
    private TextField urlTextField;
    public int index=0;

    @FXML
    void DownloadButtonClicked(ActionEvent event) {

        String text=this.urlTextField.getText();
       // System.out.println("Field text="+text);

        String url=urlTextField.getText().trim();

        //https://facebiik.com//facebook.exe
        String filename=url.substring(url.lastIndexOf("/")+1);

        //status of download file

        String status="STARTING";
        String action="OPEN";
        String path= AppConfig.DOWNLOAD_PATH+ File.separator+filename;

        FileInfo file=new FileInfo((index+1)+"",filename,url,status,action,path);
        this.index++;

        //starting dowload by creating thread



        //https://www.python.org/ftp/python/3.11.1/python-3.11.1-amd64.exe
        DownloadThread thread=new DownloadThread(file,this);
        //upadating UI column before starting downlaoding
        //aftergetting table setting to files data
        this.tableView.getItems().add(Integer.parseInt(file.getIndex())-1,file);
        thread.start();

    }
    public void updateUI(FileInfo metaFile){
        System.out.println(metaFile);
        FileInfo fileInfo=this.tableView.getItems().get(Integer.parseInt(metaFile.getIndex())-1);
        fileInfo.setStatus(metaFile.getStatus());
        this.tableView.refresh();
        System.out.println("-----------------------------------");
    }

    @FXML
    public void initialize(){
        System.out.println("View initialized "+this.tableView.getColumns().get(0));
        //mappping to col-value will be done here
        TableColumn<FileInfo,String> sn=(TableColumn<FileInfo,String>) this.tableView.getColumns().get(0);
        sn.setCellValueFactory(p ->{
            return p.getValue().indexProperty();
        });
        TableColumn<FileInfo,String> filename=(TableColumn<FileInfo,String>) this.tableView.getColumns().get(1);
        filename.setCellValueFactory(p ->{
            return p.getValue().nameProperty();
        });

        TableColumn<FileInfo,String> url=(TableColumn<FileInfo,String>) this.tableView.getColumns().get(2);
        url.setCellValueFactory(p ->{
            return p.getValue().urlProperty();
        });

        TableColumn<FileInfo,String> status=(TableColumn<FileInfo,String>) this.tableView.getColumns().get(3);
        status.setCellValueFactory(p ->{
            return p.getValue().statusProperty();
        });

        TableColumn<FileInfo,String> action=(TableColumn<FileInfo,String>) this.tableView.getColumns().get(4);
        action.setCellValueFactory(p ->{
            return p.getValue().actionProperty();
        });


    }
}
