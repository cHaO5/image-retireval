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
import java.util.*;

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
    private Text icon;

    private HashMap<String, Integer> filename = new HashMap<String, Integer>() {
        {
            put("airplane", 100);
            put("ant", 42);
            put("butterfly", 91);
            put("chair", 62);
            put("crab", 73);
            put("cup", 57);
            put("garfield", 34);
            put("lamp", 60);
            put("pizza", 53);
            put("rooster", 48);
            put("wild_cat", 34);
            put("yin_yang", 60);
        }
    };

    private Map<String, Double> imageSim = new HashMap<>();

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
            preview.setVisible(true);
            preview.setImage(new Image(image.toURI().toString()));
            icon.setVisible(false);
        } else {
            filePath.setText("No file chosen");
            preview.setVisible(false);
            icon.setVisible(true);
            return;
        }

//        BufferedImage sourceImage = null;
//        try {
//            sourceImage = ImageIO.read(new FileInputStream(image));
//            System.out.println(sourceImage.getWidth());
//            System.out.println(sourceImage.getHeight());
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }



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
        imageSim.clear();
        count.setVisible(false);

        // Filter and search
        String advanceSize = (String)size.getValue();
        String advanceCatalog = (String)catalog.getValue();
        for (HashMap.Entry<String, Integer> entry : filename.entrySet()) {
            for (int i = 1; i <= entry.getValue(); ++i) {
                String imagePath = "src/main/resources/img/" + entry.getKey() + "_" + String.format("%04d", i) + ".jpg";
//                if (filter(imagePath, advanceSize, advanceNum, advanceCatalog)) {
                if (checkSize(imagePath, advanceSize) && (advanceCatalog.equals("None") || entry.getKey().equals(advanceCatalog))) {
                    try {
                        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(filePath.getText())));
                        FingerPrint fp2 = new FingerPrint(ImageIO.read(new File(imagePath)));
                        //System.out.println(fp1.toString(true));

                        double temp = fp1.compare(fp2);

                        if (temp > 0.6) {
                            System.out.printf("sim=%f\n", temp);
                            imageSim.put(entry.getKey() + "_" + String.format("%04d", i) + ".jpg", temp);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        display();
    }

    private boolean checkSize(String filePath, String size) {
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
            if (size.equals("Small") && image.getWidth() * image.getHeight() > 300 * 200) return false;
            else if (size.equals("Medium") && image.getWidth() * image.getHeight() > 400 * 200) return false;
            else if (size.equals("Large") && image.getWidth() * image.getHeight() > 300 * 300) return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void display() {
        List<Map.Entry<String,Double>> imageList = new ArrayList<>(imageSim.entrySet());
        // TODO:lambda expression
        Collections.sort(imageList, new Comparator<Map.Entry<String, Double>>() {
            //降序排序
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // TODO:最终直接输出展示image即可， 暂时用一个list存起来
        if (!number.getValue().equals("All")) {
            int advanceNum = Integer.valueOf((String)size.getValue());
            for (int i = 0; i < advanceNum; ++i) {
                if (i < imageList.size()) System.out.println(imageList.get(i).getKey());
            }
            advanceNum = advanceNum > imageList.size() ? advanceNum : imageList.size();
            count.setText("The number of result(s) is " + advanceNum + ".");
        } else {
            for (Map.Entry<String, Double> m : imageList) System.out.println(m.getKey());
            count.setText("The number of result(s) is " + imageList.size() + ".");
        }
        count.setVisible(true);
    }

}
