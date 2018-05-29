import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    @FXML
    private JFXButton chooseImageButton;
    @FXML
    private JFXButton search;
    @FXML
    private Text filePath;
    @FXML
    private JFXComboBox size;
    @FXML
    private JFXComboBox number;
    @FXML
    private JFXComboBox catalog;
    @FXML
    private ImageView preview;
    @FXML
    private Text count;

    @FXML
    private void chooseImage(ActionEvent e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");

        // File filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
        File image = fileChooser.showOpenDialog(chooseImageButton.getScene().getWindow());

        // Show file path
        if (image != null) {
            filePath.setText(image.getAbsolutePath());
        } else {
            filePath.setText("No file chosen");
        }

        try {
            BufferedImage sourceImage = ImageIO.read(new FileInputStream(image));
            System.out.println(sourceImage.getWidth());
            System.out.println(sourceImage.getHeight());
        } catch (IOException e1) {
            e1.printStackTrace();
        }

//        preview.setGraphic(new ImageView(image.toURI().toString()));
//        preview.setContentDisplay(ContentDisplay.CENTER);
        preview.setImage(new Image(image.toURI().toString()));

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Image size
        size.getItems().addAll("All", "Small", "Medium", "Large");

        // Number of result
//        ToggleGroup group = new ToggleGroup();
//        result5.setToggleGroup(group);
//        result10.setToggleGroup(group);
//        result15.setToggleGroup(group);
        number.getItems().addAll("All", 5, 10, 15);

        catalog.getItems().addAll("None",
                "Airplane",
                "Ant",
                "Butterfly",
                "Chair",
                "Crab",
                "Cup",
                "Garfield",
                "Lamp",
                "Pizza",
                "Rooster",
                "Wild_cat",
                "Yin_yang");
//        FileChooser fileChooser = new FileChooser();
//
//        chooseFile.setOnAction(
//                (final ActionEvent e) -> {
//                    //fileChooser.showOpenDialog(this.stage);
//                });
//
//        AnchorPane root = new AnchorPane();
//
////        FileChooser fileChooser = new FileChooser();
////        fileChooser.setTitle("打开文件");
////
////
////        Label mLabel = new Label();
////        mLabel.setLayoutY(40);
//
////        Button btn = new Button("打开文件");
////        btn.setOnAction(event -> {
////            File file = fileChooser.showOpenDialog(primaryStage);
////            if(file != null){
////                mLabel.setText(file.getAbsolutePath());
////            }else {
////                mLabel.setText("没有打开任何文件");
////            }
////        });
//        chooseFile.setOnAction(event -> {
//            File file = fileChooser.showOpenDialog(primaryStage);
//            if(file != null){
//                mLabel.setText(file.getAbsolutePath());
//            }else {
//                mLabel.setText("没有打开任何文件");
//            }
//        });
//
//        root.getChildren().addAll(btn,mLabel);
//        //root.getChildren().add(btn);
//        primaryStage.setTitle("文件选择FileChooser");
//        primaryStage.setScene(new Scene(root, 300, 275));
//        primaryStage.show();
//
//        Stage stage = (Stage)search.get
    }

    @FXML
    private void search() {
        int num = 0;
        count.setText("The number of result(s) is " + num + ".");
    }

}
