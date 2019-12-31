package com.pyhtag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.pyhtag.view.AddDialogViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class App extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AddDialogViewController addDialogViewController;

    public App() {
        
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Get it for me");
//        this.primaryStage.setResizable(false);
        Runtime r = Runtime.getRuntime();
        try {
            String s;
            Process p = r.exec("fc-list | grep \"fira\"");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
            p.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initRootLayout();
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/RrootView.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    public AddDialogViewController getAddDialogViewController(){
        return addDialogViewController;
    }

    public static void main(String[] args) {
        launch();
    }
}
