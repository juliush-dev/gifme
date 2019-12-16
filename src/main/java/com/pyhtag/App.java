package com.pyhtag;

import java.io.IOException;

import com.pyhtag.view.AddDialogViewController;
import com.pyhtag.view.RootViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
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
        this.primaryStage.setResizable(false);
        initRootLayout();
        // setUpLinksView();
        // showMediaFrame();
        // setMainApp();
    }

    private void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("view/RootView.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootViewController rootViewController = loader.getController();
            System.out.println(rootViewController);
            System.out.println(rootViewController.getLinksViewController());
            rootViewController.getLinksViewController().setApp(this);
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            System.out.println();
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
