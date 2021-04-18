package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Posrednik;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Posrednik test = new Posrednik();
        test.calculate_profit_jednostkowy();
        test.max_matrix();
        test.calculate_alpha_beta();
        test.calculate_tabela_wskaznikow();
        test.calculate_if_optimal();


        Label label = new Label();
//        label.setText("KEKW");
        test.toString();
        StackPane lo = new StackPane();
        lo.getChildren().addAll(label);
        //primaryStage.setTitle("Zag_pośr");
        //Scene scene = new Scene(lo,600,400);
        //primaryStage.setScene(scene);
        //primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
