import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class SeatingChartApp extends Application {
    private final int ROWS = 3;
    private final int COLUMNS = 3;
    private final ArrayList<Color> usedColors = new ArrayList<>();
    private final Random random = new Random();
    private final Rectangle[][] seatRectangles = new Rectangle[ROWS][COLUMNS];

    @Override
    public void start(Stage stage) {
        // Create form components
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label colorLabel = new Label("Color:");
        ComboBox<String> colorBox = new ComboBox<>();
        colorBox.getItems().addAll("Red", "Green", "Blue", "Yellow", "Orange", "Purple", "Pink");

        Button addButton = new Button("Add Student");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String colorString = colorBox.getValue();
            if (name.isEmpty()) {
                showAlert("Name field cannot be empty.");
                return;
            }
            Color color = Color.web(colorString);
            if (usedColors.contains(color)) {
                showAlert("This color has already been chosen.");
                return;
            }
            int[] indices = getSelectedSeatIndex();
            if (indices == null) {
                showAlert("There are no more empty seats.");
                return;
            }
            int row = indices[0];
            int col = indices[1];
            usedColors.add(color);
            seatRectangles[row][col].setFill(color);
            seatRectangles[row][col].setStroke(Color.BLACK);
            seatRectangles[row][col].setStrokeWidth(1);
        });

        // Create seating chart components
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Rectangle rectangle = new Rectangle(50, 50);
                seatRectangles[i][j] = rectangle;
                grid.add(rectangle, j, i);
            }
        }

        // Create form layout
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(25, 25, 25, 25));
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(colorLabel, 0, 1);
        formGrid.add(colorBox, 1, 1);
        formGrid.add(addButton, 0, 2, 2, 1);

        // Create root layout
        HBox root = new HBox();
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(grid, formGrid);

        // Create scene and show stage
        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.show();
    }

    private int[] getSelectedSeatIndex() {
        ArrayList<int[]> emptySeats = new ArrayList<>();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                if (seatRectangles[i][j].getFill() == null) {
                    emptySeats.add(new int[]{i, j});
                }
            }
        }
        if (emptySeats.isEmpty()) {
            return null;
        }
        int[] indices = emptySeats.get(random.nextInt(emptySeats.size()));
        return indices;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
