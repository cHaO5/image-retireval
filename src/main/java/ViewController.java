import com.jfoenix.controls.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    @FXML
    private JFXButton label;
    @FXML
    private ImageView image0;
    @FXML
    private ImageView image1;
    @FXML
    private ImageView image2;
    @FXML
    private ImageView image3;
    @FXML
    private ImageView image4;
    @FXML
    private ImageView image5;
    @FXML
    private JFXButton pre;
    @FXML
    private JFXButton next;

    private int currentPage = 0;

    private List<Map.Entry<String, Double>> imageList;

    // Kind of images and amount
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
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg"));
        File image = fileChooser.showOpenDialog(chooseImageButton.getScene().getWindow());

        // Show file path
        if (image != null) {
            filePath.setText(image.getAbsolutePath());
            filePath.setVisible(false);
            preview.setVisible(true);
            preview.setImage(new Image(image.toURI().toString()));
            icon.setVisible(false);
        } else {
            filePath.setVisible(true);
            filePath.setText("No file chosen");
            preview.setVisible(false);
            icon.setVisible(true);
        }
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
                "airplane",
                "ant",
                "butterfly",
                "chair",
                "crab",
                "cup",
                "garfield",
                "lamp",
                "pizza",
                "rooster",
                "wild_cat",
                "yin_yang");
        catalog.setValue("None");

        label.setVisible(false);
        pre.setDisable(true);
        next.setDisable(true);
    }

    @FXML
    private void search() {
        System.out.println("Start search.");
        imageSim.clear();
        count.setVisible(false);

        boolean found = false;

        // Filter and search
        String advanceSize = (String)size.getValue();
        String advanceCatalog = (String)catalog.getValue();
        for (HashMap.Entry<String, Integer> entry : filename.entrySet()) {
            for (int i = 1; i <= entry.getValue(); ++i) {
                String imagePath = "src/main/resources/img/" + entry.getKey() + "_" + String.format("%04d", i) + ".jpg";
                if (advanceCatalog.equals("None") || entry.getKey().equals(advanceCatalog)) {
                    try {
                        FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(filePath.getText())));
                        FingerPrint fp2 = new FingerPrint(ImageIO.read(new File(imagePath)));
                        if (fp1.compare(fp2) == 1.000000) { // Find same image
                            findImage(entry.getKey());
                            found = true;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //display();
        if (!found) {
            label.setVisible(false);
            count.setText("The number of result(s) is 0.");
            count.setVisible(true);
            image0.setVisible(false);
            image1.setVisible(false);
            image2.setVisible(false);
            image3.setVisible(false);
            image4.setVisible(false);
            image5.setVisible(false);
        }
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
        imageList.sort(new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        if (!number.getValue().equals("All")) {
            int advanceNum = Integer.valueOf((String)size.getValue());
            for (int i = 0; i < advanceNum; ++i) {
                if (i < imageList.size()) System.out.println(imageList.get(i).getKey());
            }
            advanceNum = advanceNum < imageList.size() ? advanceNum : imageList.size();
            count.setText("The number of result(s) is " + advanceNum + ".");
        } else {
            for (Map.Entry<String, Double> m : imageList) System.out.println(m.getKey());
            count.setText("The number of result(s) is " + imageList.size() + ".");
        }
        count.setVisible(true);
    }

    private void findImage(String name) {
        String advanceSize = (String)size.getValue();

        for (int i = 1; i <= filename.get(name); ++i) {
            String imagePath = "src/main/resources/img/" + name + "_" + String.format("%04d", i) + ".jpg";
            if (advanceSize.equals("All") || checkSize("src/main/resources/img/" + name + "_" + String.format("%04d", i) + ".jpg", advanceSize)) {
                try {
                    FingerPrint fp1 = new FingerPrint(ImageIO.read(new File(filePath.getText())));
                    FingerPrint fp2 = new FingerPrint(ImageIO.read(new File(imagePath)));
                    //System.out.println(fp1.toString(true));
                    double temp = fp1.compare(fp2);
                    imageSim.put("src/main/resources/img/" + name + "_" + String.format("%04d", i) + ".jpg", temp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Sort image by similarity
        imageList = new ArrayList<>(imageSim.entrySet());
        imageList.sort(new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        refreshView("none", imageList);

        label.setText(name);
        label.setVisible(true);

        if (!number.getValue().equals("All")) {
            int advanceNum = Integer.valueOf((String)number.getValue());
            advanceNum = advanceNum < imageList.size() ? advanceNum : imageList.size();
            count.setText("The number of result(s) is " + advanceNum + ".");
            count.setVisible(true);

            for (int i = 0; i < advanceNum; ++i) {
                System.out.println(imageList.get(i));
            }
        } else {
            count.setText("The number of result(s) is " + imageList.size() + ".");
            count.setVisible(true);
        }


    }

    // Display of results
    private void refreshView(String instruction, List<Map.Entry<String, Double>> imageList) {
        if (instruction.equals("next")) {
            currentPage++;
        } else if (instruction.equals("pre")) {
            currentPage--;
        }

        image0.setVisible(false);
        image1.setVisible(false);
        image2.setVisible(false);
        image3.setVisible(false);
        image4.setVisible(false);
        image5.setVisible(false);

        int advanceNum = 10000;
        if (!number.getValue().equals("All")) advanceNum = Integer.valueOf((String)number.getValue());
        if (currentPage == 0) {
            pre.setDisable(true);
        } else {
            pre.setDisable(false);
        }
        if ((currentPage + 1) * 6 >= imageList.size() || (currentPage + 1) * 6 > advanceNum) {
            next.setDisable(true);
        } else {
            next.setDisable(false);
        }

        switch (instruction) {
            case "none":
                refresh();
                pre.setDisable(true);
                break;
            case "pre":
                if (currentPage == 0) pre.setDisable(true);
                refresh();
                break;
            case "next":
                if (currentPage * 6 >= imageList.size() || currentPage * 6 > advanceNum) {
                    break;
                }
                refresh();
                break;
        }
    }

    @FXML
    private void prePage() { refreshView("pre", imageList); }
    @FXML
    private void nextPage() { refreshView("next", imageList); }

    // Display each image
    private void refresh() {
        int advanceNum = 0;
        if (!number.getValue().equals("All")) advanceNum = Integer.valueOf((String)number.getValue());
        if (imageList.size() - 6 * currentPage >= 1) {
            image0.setImage(new Image((new File((imageList.get(6 * currentPage)).getKey()).toURI().toString())));
            image0.setVisible(true);
        }
        if (imageList.size() - 6 * currentPage >= 2) {
            image1.setImage(new Image((new File((imageList.get(1 + 6 * currentPage)).getKey()).toURI().toString())));
            image1.setVisible(true);
        }
        if (imageList.size() - 6 * currentPage >= 3) {
            image2.setImage(new Image((new File((imageList.get(2 + 6 * currentPage)).getKey()).toURI().toString())));
            image2.setVisible(true);
        }
        if (imageList.size() - 6 * currentPage >= 4) {
            image3.setImage(new Image((new File((imageList.get(3 + 6 * currentPage)).getKey()).toURI().toString())));
            image3.setVisible(true);
            if (advanceNum == 15 && currentPage == 2) {
                image3.setVisible(false);
            }
        }
        if (imageList.size() - 6 * currentPage >= 5) {
            image4.setImage(new Image((new File((imageList.get(4 + 6 * currentPage)).getKey()).toURI().toString())));
            image4.setVisible(true);
            if ((advanceNum == 10 && currentPage == 1) || (advanceNum == 15 && currentPage == 2)) {
                image4.setVisible(false);
            }
        }
        if (imageList.size() % 6 == 0 || imageList.size() - 6 * currentPage > 5) {
            image5.setImage(new Image((new File((imageList.get(5 + 6 * currentPage)).getKey()).toURI().toString())));
            image5.setVisible(true);
            if ((advanceNum == 5 && currentPage == 0) ||
                    (advanceNum == 10 && currentPage == 1) ||
                    (advanceNum == 15 && currentPage == 2)) {
                image5.setVisible(false);
            }
        }
    }
}
