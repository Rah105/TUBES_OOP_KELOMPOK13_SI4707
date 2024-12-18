import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.time.LocalDate;

public class ReservasiController extends DatabaseHotel {

    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private TextField namaPemesanField;
    @FXML
    private TextField noKamarField;
    @FXML
    private TableColumn<Hotel, Integer> noKamar;
    @FXML
    private TableColumn<Hotel, String> pemesan;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Hotel> table;
    @FXML
    private TextField tglKeluarField;
    @FXML
    private TextField tglMasukField;
    @FXML
    private TableColumn<Hotel, LocalDate> tglKeluar;
    @FXML
    private TableColumn<Hotel, LocalDate> tglMasuk;
    @FXML
    private TextField tipeKamarField;
    @FXML
    private TableColumn<Hotel, String> tipeKamar;

    private ObservableList<Hotel> hotelList;

    public ReservasiController() {
        super(); // Initialize the database connection
    }

    @FXML
    public void initialize() {
        hotelList = FXCollections.observableArrayList();

        noKamar.setCellValueFactory(new PropertyValueFactory<>("noKamar"));
        pemesan.setCellValueFactory(new PropertyValueFactory<>("pemesan"));
        tglMasuk.setCellValueFactory(new PropertyValueFactory<>("tglMasuk"));
        tglKeluar.setCellValueFactory(new PropertyValueFactory<>("tglKeluar"));
        tipeKamar.setCellValueFactory(new PropertyValueFactory<>("tipeKamar"));

        loadData();
        table.setItems(hotelList);

        // Add listener to populate fields when a row is selected
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Populate fields with selected row data
                namaPemesanField.setText(newValue.getPemesan());
                tglMasukField.setText(newValue.getTglMasuk().toString());
                tglKeluarField.setText(newValue.getTglKeluar().toString());
                tipeKamarField.setText(newValue.getTipeKamar());
                noKamarField.setText(String.valueOf(newValue.getNoKamar()));
            }
        });
    }

    private void loadData() {
        hotelList.clear();
        try (Connection connection = new DatabaseHotel().getConnection()) {
            String query = "SELECT * FROM reservation";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int noKamar = resultSet.getInt("noKamar");
                String pemesan = resultSet.getString("pemesan");
                LocalDate tglMasuk = resultSet.getDate("tglMasuk").toLocalDate();
                LocalDate tglKeluar = resultSet.getDate("tglKeluar").toLocalDate();
                String tipeKamar = resultSet.getString("tipeKamar");

                hotelList.add(new Hotel(pemesan, tglMasuk, tglKeluar, tipeKamar, noKamar));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addButton() {
        if (validateInput()) {
            try (Connection connection = new DatabaseHotel().getConnection()) {
                String query = "INSERT INTO reservation (noKamar, pemesan, tglMasuk, tglKeluar, tipeKamar) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, Integer.parseInt(noKamarField.getText()));
                preparedStatement.setString(2, namaPemesanField.getText());
                preparedStatement.setDate(3, Date.valueOf(tglMasukField.getText()));
                preparedStatement.setDate(4, Date.valueOf(tglKeluarField.getText()));
                preparedStatement.setString(5, tipeKamarField.getText());

                preparedStatement.executeUpdate();
                clearFields();
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorMessage("Error adding reservation: " + e.getMessage());
            }
        }
    }

    @FXML
    public void editButton() {
        Hotel selectedHotel = table.getSelectionModel().getSelectedItem();
        if (selectedHotel != null && validateInput()) {
            try (Connection connection = new DatabaseHotel().getConnection()) {
                String query = "UPDATE reservation SET pemesan = ?, tglMasuk = ?, tglKeluar = ?, tipeKamar = ? WHERE noKamar = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, namaPemesanField.getText());
                preparedStatement.setDate(2, Date.valueOf(tglMasukField.getText()));
                preparedStatement.setDate(3, Date.valueOf(tglKeluarField.getText()));
                preparedStatement.setString(4, tipeKamarField.getText());
                preparedStatement.setInt(5, Integer.parseInt(noKamarField.getText()));

                preparedStatement.executeUpdate();
                clearFields();
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorMessage("Error editing reservation: " + e.getMessage());
            }
        } else {
            showErrorMessage("Please select a reservation to edit.");
        }
    }

    @FXML
    public void deleteButton() {
        Hotel selectedHotel = table.getSelectionModel().getSelectedItem();
        if (selectedHotel != null) {
            try (Connection connection = new DatabaseHotel().getConnection()) {
                String query = "DELETE FROM reservation WHERE noKamar = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, selectedHotel.getNoKamar());

                preparedStatement.executeUpdate();
                loadData();
            } catch (SQLException e) {
                e.printStackTrace();
                showErrorMessage("Error deleting reservation: " + e.getMessage());
            }
        } else {
            showErrorMessage("Please select a reservation to delete.");
        }
    }

    private void clearFields() {
        noKamarField.clear();
        namaPemesanField.clear();
        tglMasukField.clear();
        tglKeluarField.clear();
        tipeKamarField.clear();
    }

    private boolean validateInput() {
        // Validate if any required fields are empty
        if (noKamarField.getText().isEmpty() || namaPemesanField.getText().isEmpty() ||
                tglMasukField.getText().isEmpty() || tglKeluarField.getText().isEmpty()
                || tipeKamarField.getText().isEmpty()) {
            showErrorMessage("All fields are required.");
            return false;
        }

        // Validate date format (yyyy-MM-dd)
        try {
            Date.valueOf(tglMasukField.getText());
            Date.valueOf(tglKeluarField.getText());
        } catch (IllegalArgumentException e) {
            showErrorMessage("Invalid date format. Please use yyyy-MM-dd.");
            return false;
        }

        return true;
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
