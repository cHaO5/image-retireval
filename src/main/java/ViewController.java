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
        size.setValue("All");

        // Number of result
        number.getItems().addAll("All", "5", "10", "15");
        number.setValue("All");

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
        catalog.setValue("None");

    }

    @FXML
    private void search() {
        int advanceNum = 0;
        if (!number.getValue().equals("All")) {
            advanceNum = Integer.valueOf((String)size.getValue());
        }
        String advanceSize = (String)size.getValue();
        String advanceCatalog = (String)catalog.getValue();

        int resultNum = 0;
        count.setText("The number of result(s) is " + resultNum + ".");

        filter(advanceSize, advanceNum, advanceCatalog);

        display();
    }

    private void filter(String size, int num, String catalog) {}

    private void display() {}

}
