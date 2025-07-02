package main.java.com.streetfit.controllerfx;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.Queue;

import main.java.com.streetfit.beans.MessageBean;
import main.java.com.streetfit.beans.ParticipationBean;
import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.controller.JoinStageController;
import main.java.com.streetfit.daojdbc.ConnectionFactory;

import main.java.com.streetfit.model.Role;
import main.java.com.streetfit.model.TrainerNotification;

import main.java.com.streetfit.utils.NotificationQueue;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
		private AnchorPane membersForm;
	    @FXML
		private Button membersBtn;
	    @FXML
	    private AreaChart<String, Number> dashboardIncomeChart;
	    @FXML
	    private Button stagesBtn;
	    @FXML
	    private TextField stageTitle;
	    @FXML
	    private Label dashboardNM;
	    @FXML
	    private TextField stagePlace;
	    @FXML
	    private Label dashboardNS;
	    @FXML
	    private TextArea stageItinerary;
	    @FXML
	    private ComboBox<String> stageCategory;
	    @FXML
	    private DatePicker stageDate;
	    @FXML
	    private TextField maxParticipant;
	    @FXML
	    private TableView<TrainingStageBean> stageTable;
	    @FXML
	    private TableColumn<TrainingStageBean, String> nameColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, String> placeColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, String> itineraryColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, String> categoryColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, Date> dateColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, Integer> maxParticipantsColumn;
	    @FXML
	    private TableView<ParticipationBean> membersTableView; // Tabella per i membri
	    @FXML
	    private TableColumn<ParticipationBean, String> membersUsername; // Colonna per username
	    @FXML
	    private TableColumn<ParticipationBean, String> membersStage; // Colonna per stage
	    @FXML
	    private TableColumn<ParticipationBean, Integer> membersTickets; // Colonna per tickets
	    @FXML
	    private TableView<TrainingStageBean> infoTable;
	    @FXML
	    private TableColumn<TrainingStageBean, String> stageColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, Date> dataColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, Integer> remainingTicketsColumn;
	    @FXML
	    private Label notificationLabel;  // Label per la notifica
	    @FXML
	    private Button messageBtn;
	    @FXML
        private AnchorPane messageForm;
	    @FXML private TableView<MessageBean> messageTable;
	    @FXML private TableColumn<MessageBean, String> messageSenderColumn;
	    @FXML private TableColumn<MessageBean, String> messageContentColumn;
	    @FXML private TableColumn<MessageBean, Date> messageDateColumn;
	    @FXML private ComboBox<String> recipientComboBox;
	    @FXML private TextArea messageTextArea;
	    @FXML private Button sendMessageButton;
	    @FXML
	    private TableColumn<MessageBean, String> messageReplyColumn;
	    @FXML
	    private Label dashboardIncome;
	    @FXML
	    private TextArea replyTextArea;
	    @FXML
	    private Button sendReplyButton;
	    @FXML
	    private ListView<String> chatListView;
	    private List<MessageBean> messages;  // tiene traccia dei messaggi originali
	    
	    private NotificationQueue queue;
	    
	    String error = "Error"; //for sonar messages
	    String warning = "Warning";
	    private MessageBean selectedMessage;
	    private double x = 0;
	    private double y = 0;
	    private static final int NOTIFICATION_DISPLAY_TIME = 3; 
	    private  JoinStageController joinController = new JoinStageController();
	    private AddStageController stagecontroller = new AddStageController();
	    
	    public TrainerControllerFX() {
	    	ConnectionFactory.changeRole(Role.TRAINER);
	    }
	    
	    public TrainerControllerFX( NotificationQueue notificationQueue) {
			this.queue = notificationQueue;
		}



		public void initialize() {  //to initialize all FX components
	    	
	    	checkLoginNotificationsFX();
	    	stageCategory.getItems().addAll(
	    	        "HIIT",
	    	        "Yoga",
	    	        "CrossFit",
	    	        "Pilates",
	    	        "Functional",
	    	        "Stretching"
	    	    );
	    	  nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
	    	  placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
	    	  itineraryColumn.setCellValueFactory(new PropertyValueFactory<>("itinerary"));
	    	  categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
	    	  dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
	    	  maxParticipantsColumn.setCellValueFactory(new PropertyValueFactory<>("maxParticipants"));
	    	  printTable();
	    	  
	    	// Inizializzazione delle colonne
	    	    membersUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
	    	    membersStage.setCellValueFactory(cellData -> {
	    	        ParticipationBean participation = cellData.getValue();
	    	        String stageTitle = participation.getStage().getTitle();  // solo il titolo
	    	        return new SimpleStringProperty(stageTitle);
	    	    });
	    	    membersTickets.setCellValueFactory(new PropertyValueFactory<>("ticket"));

	    	    printMembers();
	    	    
	    	    printRemainingTickets();
	    	  
	    	    printCreatedStages();
	    	    
	    	    printTotalIncome();
	    	    
	    	    populateIncomeChart();

	            loadMessages();
	            chatListView.setOnMouseClicked(event -> {
	                int index = chatListView.getSelectionModel().getSelectedIndex();
	                if (index >= 0 && index < messages.size()) {
	                    selectedMessage = messages.get(index);
	                    replyTextArea.setText(selectedMessage.getReply() != null ? selectedMessage.getReply() : "");
	                }
	            });

	          

	            sendReplyButton.setOnAction(e -> sendReply());
	    }
	    
	    
	    
	    private void printTotalIncome() {
			List<ParticipationBean> members = joinController.retrieveMembers();
			double total = 0;
			
			for(ParticipationBean member: members) {
				total += member.getTotal();
			}
			
			dashboardIncome.setText("€" +total);
			
		}

		private void printCreatedStages() {
			
	    	int stages = stagecontroller.getAllStages().size();
	    	dashboardNS.setText(String.valueOf(stages));
			
		}
		
		private void populateIncomeChart() {
		    XYChart.Series<String, Number> series = new XYChart.Series<>();
		    series.setName("Income by Stage");

		    Map<String, Double> incomePerStage = new LinkedHashMap<>();
		    List<ParticipationBean> participations = joinController.retrieveMembers();

		    for (ParticipationBean p : participations) {
		        String title = p.getStage().getTitle(); // Usa direttamente il nome dello stage
		        incomePerStage.put(title,
		            incomePerStage.getOrDefault(title, 0.0) + p.getTotal());
		    }

		    for (Map.Entry<String, Double> entry : incomePerStage.entrySet()) {
		        series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		    }

		    dashboardIncomeChart.getData().clear();
		    dashboardIncomeChart.getData().add(series);
		}


		private void loadMessages() {
	        try {
	            messages = joinController.retrieveMessages(); // salva la lista originale
	            ObservableList<String> messageTexts = FXCollections.observableArrayList();

	            for (MessageBean msg : messages) {
	                StringBuilder display = new StringBuilder();
	                display.append("From " + msg.getFromUser() +": ").append(msg.getContent());

	                if (msg.hasReply()) {
	                    display.append("\n↳ Your reply: ").append(msg.getReply());
	                }

	                messageTexts.add(display.toString());
	            }

	            chatListView.setItems(messageTexts);

	        } catch (IllegalStateException e) {
	            showAlert(error, "Unable to load messages.", Alert.AlertType.ERROR);
	        }
	    }


	    private void sendReply() {
	        if (selectedMessage == null) {
	            showAlert(warning, "Select a message to reply.", Alert.AlertType.WARNING);
	            return;
	        }

	        String replyText = replyTextArea.getText().trim();
	        if (replyText.isEmpty()) {
	            showAlert(warning, "Reply cannot be empty.", Alert.AlertType.WARNING);
	            return;
	        }

	        try {
	            joinController.updateMessage(selectedMessage, replyText);
	            showAlert("Success", "Reply sent successfully.", Alert.AlertType.INFORMATION);
	            loadMessages();
	            replyTextArea.clear();
	        } catch (IllegalStateException e) {
	            showAlert(error, "Failed to send reply.", Alert.AlertType.ERROR);
	        }
	    }
	    private void showAlert(String title, String content, Alert.AlertType type) {
	        Alert alert = new Alert(type);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(content);
	        alert.showAndWait();
	    }
	    private void checkLoginNotificationsFX() {
	        Queue<TrainerNotification> notifications = queue.getAndClearNotifications();

	        if (notifications.isEmpty()) {
	            showTemporaryNotification("📭 No new notifications");
	        } else {
	            showTemporaryNotification("📬 There are new notifications:");
	            
	            // Mostra ogni notifica con una breve pausa
	            new Thread(() -> {
	                for (TrainerNotification n : notifications) {
	                    String msg = n.getMessage();
	                    javafx.application.Platform.runLater(() -> notificationLabel.setText(msg));
	                    try {
	                    	Thread.sleep(NOTIFICATION_DISPLAY_TIME * 1000L); // NOTIFICATION_DISPLAY_TIME è già definito a 3
	                    } catch (InterruptedException e) {
	                    	Thread.currentThread().interrupt();
	    	                return;
	                    }
	                }
	                javafx.application.Platform.runLater(() -> notificationLabel.setVisible(false));
	            }).start();
	        }
	    }
	    private void showTemporaryNotification(String msg) {
	        notificationLabel.setVisible(true);
	        notificationLabel.setText(msg);
	        new Thread(() -> {
	            try {
	            	Thread.sleep(NOTIFICATION_DISPLAY_TIME * 1000L);
	            } catch (InterruptedException e) {
	            	Thread.currentThread().interrupt();
	                return;
	            }
	            javafx.application.Platform.runLater(() -> notificationLabel.setVisible(false));
	        }).start();
	    }


	    public void addStage() {
	        String title = stageTitle.getText().trim();
	        String location = stagePlace.getText().trim();
	        String itinerary = stageItinerary.getText().trim();
	        String selectedCategory = stageCategory.getValue();
	        String selectedMaxParticipant = maxParticipant.getText().trim();
	        LocalDate localDate = stageDate.getValue();

	        if (isNotEmpty(title, location, itinerary, selectedCategory, selectedMaxParticipant, localDate)) {
	            try {
	                int maxPart = Integer.parseInt(selectedMaxParticipant);

	                if (maxPart <= 0) {
	                    showAlert(error, "Max participants must be greater than zero.", Alert.AlertType.ERROR);
	                    return;
	                }

	                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

	                TrainingStageBean stagebean = new TrainingStageBean(title, itinerary, selectedCategory, date, location, maxPart);

	              

	                    AddStageController controller = new AddStageController();
	                    controller.addstage(stagebean);

	                    Alert alert = new Alert(AlertType.INFORMATION);
	                    alert.setTitle("Information Message");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Successfully inserted");
	                    printRemainingTickets();
	                    alert.showAndWait();

	                    printTable();
	               
	            } catch (NumberFormatException e) {
	                showAlert("Invalid input", "Please enter a valid number for max participants.", Alert.AlertType.ERROR);
	            } catch (IllegalStateException e) {
	                showAlert(error, "Stage already exists.", Alert.AlertType.ERROR);
	            }

	        } else {
	            showAlert(error, "All fields must be filled.", Alert.AlertType.ERROR);
	        }
	    }


	    // Control function to verifiy if not empty fields
	    public boolean isNotEmpty(String title, String location, String itinerary, String selectedCategory, String maxPart, LocalDate localDate) {
	        return !title.isEmpty() && !location.isEmpty() && !itinerary.isEmpty() && 
	               selectedCategory != null && !selectedCategory.isEmpty() && 
	               !maxPart.isEmpty() && localDate != null;
	    }

	    public void printTable() {  //methods to print things into the table: it will use the general controller, that returns all the created stages, as it communicates with DAO
	        List<TrainingStageBean> stageList;

	        AddStageController controller = new AddStageController();
	        stageList = controller.getAllStages(); //

	        // Converti la List in ObservableList
	        ObservableList<TrainingStageBean> observableStageList = FXCollections.observableArrayList(stageList);

	        // Imposta la ObservableList nella TableView
	        stageTable.setItems(observableStageList); // Usa direttamente l'ObservableList
	    }
	    
	   
	    public void printRemainingTickets() {
	        // Recupera gli stage e i contatori dei partecipanti
	        AddStageController addStagecontroller = new AddStageController();
	       
	        
	        List<TrainingStageBean> stageList = addStagecontroller.getAllStages();
	     

	        // Converti la lista di TrainingStage in una lista di TrainingStageBean
	        List<TrainingStageBean> stageBeans = new ArrayList<>();
	        for (TrainingStageBean stage : stageList) {
	            TrainingStageBean bean = new TrainingStageBean(
	                stage.getTitle(),
	                stage.getItinerary(),
	                stage.getCategory(),
	                stage.getDate(),
	                stage.getPlace(),
	                stage.getMaxParticipants()
	            );
	            stageBeans.add(bean);
	        }
	        List<Integer> counters = joinController.getSubscribers(stageBeans);

	        
	        // Crea una lista di oggetti per la tabella
	        ObservableList<TrainingStageBean> stageDataList = FXCollections.observableArrayList(stageList);
	        
	        // Imposta i dati nella TableView
	        infoTable.setItems(stageDataList);

	        // Impostazione delle colonne
	        stageColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
	        dataColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDate()));
	        
	        // Calcola i biglietti rimanenti e mostra il valore nella colonna
	        remainingTicketsColumn.setCellValueFactory(cellData -> {
	            int stageIndex = stageDataList.indexOf(cellData.getValue());
	           
	            int remainingTickets = counters.get(stageIndex);
	            
	            return new SimpleIntegerProperty(remainingTickets).asObject();
	        });
	    }
	    
	    public void printMembers() {
	        List<ParticipationBean> members = new JoinStageController().retrieveMembers();
	        Map<String, ParticipationBean> aggregatedMap = new HashMap<>();
	        int soldTickets = 0;

	        for (ParticipationBean member : members) {
	            String key = member.getUsername() + "::" + member.getStage(); // chiave composta: username + stage
	            int ticket = member.getTicket();
	            double total = member.getTotal();

	            soldTickets += ticket;

	            if (aggregatedMap.containsKey(key)) {
	                ParticipationBean existing = aggregatedMap.get(key);
	                existing.setTicket(existing.getTicket() + ticket);
	                existing.setTotal(existing.getTotal() + total);
	            } else {
	                ParticipationBean copy = new ParticipationBean(member.getUsername(), member.getStage(), ticket, total);
	                aggregatedMap.put(key, copy);
	            }
	        }

	        dashboardNM.setText(String.valueOf(soldTickets));

	        ObservableList<ParticipationBean> observableMemberList =
	            FXCollections.observableArrayList(aggregatedMap.values());
	        membersTableView.setItems(observableMemberList);
	    }


	    
	    @FXML
	    private void removeSelectedMember() {
	        ParticipationBean selected = membersTableView.getSelectionModel().getSelectedItem();

	        if (selected == null) {
	            showAlert(warning, "Please select a member to remove.", Alert.AlertType.WARNING);
	            return;
	        }

	        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	        confirm.setTitle("Confirm Removal");
	        confirm.setHeaderText(null);
	        confirm.setContentText("Are you sure you want to remove member " + selected.getUsername() + " from stage " + selected.getStage() + "?");

	        Optional<ButtonType> result = confirm.showAndWait();
	        if (result.isPresent() && result.get() == ButtonType.OK) {
	            try {
	                joinController.removeParticipation(selected.getUsername(), selected.getStage().getTitle());
	                showAlert("Success", "Member removed successfully.", Alert.AlertType.INFORMATION);
	                printMembers();  // aggiorna la tabella
	                printRemainingTickets(); // aggiorna i ticket rimasti
	                printTotalIncome(); // aggiorna il totale incassato
	            } catch (Exception e) {
	                showAlert(error, "Failed to remove member. " + e.getMessage(), Alert.AlertType.ERROR);
	            }
	        }
	    }

	    
	    public void switchForm(ActionEvent event) {
	      	 if (event.getSource() == dashboardBtn) {

	               dashboardForm.setVisible(true);
	               stageForm.setVisible(false);
	               membersForm.setVisible(false); 
	               messageForm.setVisible(false);
	         

	           } else if (event.getSource() == stagesBtn) {

	          	dashboardForm.setVisible(false);
	            stageForm.setVisible(true);   
	            membersForm.setVisible(false);
	            messageForm.setVisible(false);
	           } 
	           else if(event.getSource()==membersBtn) {
	           	dashboardForm.setVisible(false);
	               stageForm.setVisible(false);
	               membersForm.setVisible(true); 
	               messageForm.setVisible(false);
	           }
	           else if(event.getSource()==messageBtn) {
	        	 	dashboardForm.setVisible(false);
		               stageForm.setVisible(false);
		               membersForm.setVisible(false);
		               messageForm.setVisible(true);
	           }
	      }
	    
	    public void dummy() {
	    	showAlert("Attention", "Not implemented yet",AlertType.WARNING );
	    }
	    
	    public void resetForm() {
	        stageTitle.clear();
	        stagePlace.clear();
	        stageItinerary.clear();
	        stageCategory.setValue(null);
	        maxParticipant.clear();
	        stageDate.setValue(null);
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
	                Parent root = FXMLLoader.load(getClass().getResource("/ViewFxml/Login.fxml"));

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
	        	throw new IllegalStateException(error);//just for now
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

