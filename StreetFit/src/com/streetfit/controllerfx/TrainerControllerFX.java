package com.streetfit.controllerfx;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.streetfit.beans.StageBean;
import com.streetfit.controller.AddStageController;
import com.streetfit.daojdbc.ConnectionFactory;
import com.streetfit.model.Role;
import com.streetfit.model.TrainingStage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.util.Date;
import java.util.List;

//Here, you declare all objects in FXML and declare methods that handle these object and implements the use cases. 

public class TrainerControllerFX {
	
	    @FXML
	    private AnchorPane mainForm;
	    @FXML
	    private Button logout;
	    @FXML
	    private Button dashboardBtn;
	    @FXML
	    private AnchorPane dashboardForm;
	    @FXML
	    private AnchorPane stageForm;
	    @FXML
	    private Button stagesBtn;
	    @FXML
	    private TextField stageTitle;
	    @FXML
	    private TextField stagePlace;
	    @FXML
	    private TextArea stageItinerary;
	    @FXML
	    private ComboBox<String> stageCategory;
	    @FXML
	    private DatePicker stageDate;
	    @FXML
	    private TextField maxParticipant;
	    @FXML
	    private TableView<TrainingStage> stageTable;
	    @FXML
	    private TableColumn<TrainingStage, String> nameColumn;
	    @FXML
	    private TableColumn<TrainingStage, String> placeColumn;
	    @FXML
	    private TableColumn<TrainingStage, String> itineraryColumn;
	    @FXML
	    private TableColumn<TrainingStage, String> categoryColumn;
	    @FXML
	    private TableColumn<TrainingStage, Date> dateColumn;
	    @FXML
	    private TableColumn<TrainingStage, Integer> maxParticipantsColumn;

	  
	    
	    private double x = 0;
	    private double y = 0;
	    
	    public TrainerControllerFX() {
	    	ConnectionFactory.changeRole(Role.TRAINER);
	    }
	    
	    public void initialize() {  //to initialize all FX components
	    	

	    	stageCategory.getItems().addAll(
	    	        "HIIT",
	    	        "Yoga",
	    	        "CrossFit",
	    	        "Pilates",
	    	        "Functional",
	    	        "Stretching"
	    	    );
	    	  nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
	    	  placeColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
	    	  itineraryColumn.setCellValueFactory(new PropertyValueFactory<>("itinerary"));
	    	  categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
	    	  dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
	    	  maxParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("maxParticipants"));
	    	    
	    		printTable();
	    
	    }
	    
	    public void addStage() {   //it calls, as every GUI controller, the general controller to implement the use case
	    	
	        String title = stageTitle.getText().trim(); // Rimuovi spazi bianchi all'inizio e alla fine
	        String location = stagePlace.getText().trim();
	        String itinerary = stageItinerary.getText().trim();
	        String selectedCategory = stageCategory.getValue();
	        String selectedMaxParticipant = maxParticipant.getText().trim();
	        LocalDate localDate = stageDate.getValue(); // esempio da DatePicker
	        
	      
	        if (isNotEmpty(title, location, itinerary, selectedCategory, selectedMaxParticipant, localDate)) {
	            try {
	            
	                int maxPart = Integer.parseInt(selectedMaxParticipant);

	                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	                StageBean stagebean = new StageBean(title, itinerary, selectedCategory, date, location, maxPart);

	                if (stagebean.isValid()) {
	                    TrainingStage stage = new TrainingStage(stagebean.getTitle(), stagebean.getItinerary(), 
	                            stagebean.getCategory(), stagebean.getDate(), stagebean.getPlace(), stagebean.getMaxParticipants());
	                    

	                    AddStageController controller = new AddStageController();
	                    controller.addstage(stage);
	                    
	                    Alert alert = new Alert(AlertType.INFORMATION);
	                    alert.setTitle("Information Message");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Successfully inserted");
	                    alert.showAndWait();

	                    printTable();  
	                } else {
	                  
	                    Alert alert = new Alert(AlertType.ERROR);
	                    alert.setTitle("Error on some fields");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Please check the validity of the fields.");
	                    alert.showAndWait();
	                }
	            } catch (NumberFormatException e) {
	              
	                Alert alert = new Alert(AlertType.ERROR);
	                alert.setTitle("Invalid input");
	                alert.setHeaderText(null);
	                alert.setContentText("Please enter a valid number for max participants.");
	                alert.showAndWait();
	            }
	            catch(IllegalStateException e) {
	            	 Alert alert = new Alert(AlertType.ERROR);
		                alert.setTitle("Input error");
		                alert.setHeaderText(null);
		                alert.setContentText("Stage already exists.");
		                alert.showAndWait();
	            }
	           
	        } else {
	          
	            Alert alert = new Alert(AlertType.ERROR);
	            alert.setTitle("Please compile all the required fields");
	            alert.setHeaderText(null);
	            alert.setContentText("All fields must be filled.");
	            alert.showAndWait();
	        }
	    }

	    // Control function to verifiy if not empty fields
	    public boolean isNotEmpty(String title, String location, String itinerary, String selectedCategory, String maxPart, LocalDate localDate) {
	        return !title.isEmpty() && !location.isEmpty() && !itinerary.isEmpty() && 
	               selectedCategory != null && !selectedCategory.isEmpty() && 
	               !maxPart.isEmpty() && localDate != null;
	    }

	    public void printTable() {  //methods to print things into the table: it will use the general controller, that returns all the created stages, as it communicates with DAO
	        List<TrainingStage> stageList;

	        AddStageController controller = new AddStageController();
	        stageList = controller.getAllStages(); //

	        // Converti la List in ObservableList
	        ObservableList<TrainingStage> observableStageList = FXCollections.observableArrayList(stageList);

	        // Imposta la ObservableList nella TableView
	        stageTable.setItems(observableStageList); // Usa direttamente l'ObservableList
	    }
	    
	    public void switchForm(ActionEvent event) {
	    	 if (event.getSource() == dashboardBtn) {

	             dashboardForm.setVisible(true);
	             stageForm.setVisible(false);
	          //   members_form.setVisible(false);  HANDLE LATER
	           //  payment_Form.setVisible(false);

	         } else if (event.getSource() == stagesBtn) {

	        	 dashboardForm.setVisible(false);
	             stageForm.setVisible(true);

	             // TO UPDATE WHEN YOU CLICK THE MENU BUTTON LIKE COACHES BUTTON
	            

	         } 
	         /*else if (event.getSource() == members_btn) {      OTHER USE CASES, HANDLE LATER

	             dashboard_form.setVisible(false);
	             coaches_form.setVisible(false);
	             members_form.setVisible(true);
	             payment_Form.setVisible(false);

	             membersShowData();
	             membersGender();
	             membersSchedule();
	             membersStatus();

	         } else if (event.getSource() == payment_btn) {

	             dashboard_form.setVisible(false);
	             coaches_form.setVisible(false);
	             members_form.setVisible(false);
	             payment_Form.setVisible(true);

	             paymentShowData();
	             paymentMemberId();
	             paymentName();

	         }
	         */
	    }
	    
	    public void logout() {
	    
	    	try {

	            Alert alert = new Alert(AlertType.CONFIRMATION);
	            alert.setTitle("Confirmation Message");
	            alert.setHeaderText(null);
	            alert.setContentText("Are you sure you want to logout?");
	            Optional<ButtonType> option = alert.showAndWait();
	            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
	                // TO HIDE YOUR DASHBOARD FORM
	                logout.getScene().getWindow().hide();

	                // LINK YOUR LOGIN FORM
	                Parent root = FXMLLoader.load(getClass().getResource("/com/streetfit/viewfx/Login.fxml"));

	                Stage stage = new Stage();
	                Scene scene = new Scene(root);

	                root.setOnMousePressed((MouseEvent event) -> {
	                    x = event.getSceneX();
	                    y = event.getSceneY();
	                });

	                root.setOnMouseDragged((MouseEvent event) -> {
	                    stage.setX(event.getScreenX() - x);
	                    stage.setY(event.getScreenY() - y);
	                    stage.setOpacity(0.8);
	                });

	                root.setOnMouseReleased(event -> stage.setOpacity(1));

	                stage.initStyle(StageStyle.TRANSPARENT);
	                stage.setScene(scene);
	                stage.show();
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    
	    public void minimize() {
	    	Stage stage = (Stage) mainForm.getScene().getWindow();
	        stage.setIconified(true);
	    }
	    
	    public void close() {
	        javafx.application.Platform.exit();
	    }
	}

