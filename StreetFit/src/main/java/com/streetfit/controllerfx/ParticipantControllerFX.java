package main.java.com.streetfit.controllerfx;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import main.java.com.streetfit.chainofresponsibility.FitnessCheckHandler;
import main.java.com.streetfit.chainofresponsibility.HealthCheckHandler;
import main.java.com.streetfit.chainofresponsibility.HeartCheckHandler;
import main.java.com.streetfit.chainofresponsibility.InjuriesCheckHandler;
import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.controller.JoinStageController;
import main.java.com.streetfit.decorator.BasicTicket;
import main.java.com.streetfit.decorator.SpecialTicket;
import main.java.com.streetfit.decorator.Ticket;
import main.java.com.streetfit.decorator.VipTicket;
import main.java.com.streetfit.model.Credentials;
import main.java.com.streetfit.model.HealthForm;
import main.java.com.streetfit.model.Message;
import main.java.com.streetfit.model.Participation;
import main.java.com.streetfit.model.TrainingStage;
import main.java.com.streetfit.strategy.PromotionalEvent;
import main.java.com.streetfit.strategy.StandardTicket;
import main.java.com.streetfit.strategy.TicketStrategy;
import main.java.com.streetfit.strategy.VipStrategy;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ParticipantControllerFX {
	private Credentials cred;
	
	@FXML
    private Button joinstagesBtn;
	 @FXML
	private Button dashboardBtn;
	 @FXML
	private AnchorPane dashboardForm;
	 @FXML
	private AnchorPane stageForm;
	 @FXML
	private AnchorPane membersForm;
	 @FXML
	private TableView<TrainingStage> stageTable;
	 @FXML
	private Button logout;
	 
	 @FXML
		private Button membersBtn;
	 @FXML
	  private AnchorPane mainForm;
	 @FXML
	 private TextField stageTitle;
	 @FXML
	 private CheckBox checkbox1;

	 @FXML
	 private CheckBox checkbox2;

	 @FXML
	 private CheckBox checkbox3;
	  @FXML
	    private TextField stagePlace;
	    @FXML
	    private TextArea stageItinerary;
	    @FXML
	    private ComboBox<String> stageCategory;
	    @FXML
	    private DatePicker stageDate;
	    @FXML
	    private TextField ticketsLeft;
	    @FXML
	    private ComboBox<String> ticketTypeComboBox;

	    @FXML
	    private Spinner<Integer> ticketQuantitySpinner;
	 @FXML
	    private TableColumn<TrainingStage, String> nameColumn;
	    @FXML
	    private TableColumn<TrainingStage, String> placeColumn;
	    @FXML
	    private Label dashboardNT;
	    @FXML
	    private Label dashboardOUT;
	    @FXML
	    private Label dashboardNS;
	    @FXML
	    private TableColumn<TrainingStage, String> itineraryColumn;
	    @FXML
	    private TableColumn<TrainingStage, String> categoryColumn;
	    @FXML
	    private TableColumn<TrainingStage, Date> dateColumn;
	    @FXML
	    private TableColumn<TrainingStage, Integer> ticketsLeftsColumn;
	    @FXML
	    private TextArea questionTextArea;
	    @FXML
	    private AnchorPane messageForm;
	    @FXML
	    private Button messageBtn;
	    @FXML
	    private ListView<String> messagesListView;
	    @FXML private AnchorPane subForm;
	    @FXML
	    private Button subBtn;
	    @FXML
	    private TableView<Participation> joinedStagesTable;

	    @FXML
	    private TableColumn<Participation, String> stageTitleColumn;

	    @FXML
	    private TableColumn<Participation, Integer> ticketColumn;

	    @FXML
	    private TableColumn<Participation, Double> totalColumn;

	    
	    
	    private double x = 0;
	    private double y = 0;
	
	    private JoinStageController joinStagecontroller = new JoinStageController();
	 
	

	    public void initialize() {
	        setupStageTableColumns();
	        setupDashboardBindings();
	        setupSpinner();
	        setupRowSelection();
	        printTable();
	    }

	    private void setupStageTableColumns() {
	        stageTitleColumn.setCellValueFactory(new PropertyValueFactory<>("stage"));
	        ticketColumn.setCellValueFactory(new PropertyValueFactory<>("ticket"));
	        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
	        
	        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
	        placeColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
	        itineraryColumn.setCellValueFactory(new PropertyValueFactory<>("itinerary"));
	        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
	        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
	    }

	    private void setupDashboardBindings() {
	        ticketsLeftsColumn.setCellValueFactory(cellData -> {
	            TrainingStage stage = cellData.getValue();

	            int remaining = stage.getMaxParticipants();
	            int stages = 0;

	            try {
	                List<Participation> all = joinStagecontroller.showMembers();
	                int booked = all.stream()
	                                .filter(p -> p.getStage().equals(stage.getTitle()))
	                                .mapToInt(Participation::getTicket)
	                                .sum();
	                remaining -= booked;

	                for (Participation member : all) {
	                    if (member.getUsername().equals(cred.getUsername())) {
	                        stages += 1;
	                    }
	                }

	                dashboardNS.setText(String.valueOf(stages));

	                List<Participation> members = joinStagecontroller.showMembers();
	                double total = 0;
	                int tickets = 0;

	                for (Participation member : members) {
	                    if (member.getUsername().equals(cred.getUsername())) {
	                        total += member.getTotal();
	                        tickets += member.getTicket();
	                    }
	                }

	                dashboardOUT.setText("€" + total);
	                dashboardNT.setText(String.valueOf(tickets));

	            } catch (Exception e) {
	                throw new IllegalStateException("Error");//just for now
	            }

	            return new javafx.beans.property.SimpleIntegerProperty(remaining).asObject();
	        });
	    }

	    private void setupSpinner() {
	        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
	            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
	        ticketQuantitySpinner.setValueFactory(valueFactory);
	    }

	    private void setupRowSelection() {
	        stageTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
	            if (newSelection != null) {
	                stageTitle.setText(newSelection.getTitle());
	                stagePlace.setText(newSelection.getLocation());
	                stageItinerary.setText(newSelection.getItinerary());
	                stageCategory.setValue(newSelection.getCategory());

	                if (newSelection.getDate() != null) {
	                    java.util.Date utilDate = newSelection.getDate();
	                    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
	                    LocalDate localDate = sqlDate.toLocalDate();
	                    stageDate.setValue(localDate);
	                } else {
	                    stageDate.setValue(null);
	                }

	                JoinStageController joinController = new JoinStageController();

	                try {
	                    List<Participation> all = joinController.showMembers();
	                    int booked = all.stream()
	                                    .filter(p -> p.getStage().equals(newSelection.getTitle()))
	                                    .mapToInt(Participation::getTicket)
	                                    .sum();

	                    int available = newSelection.getMaxParticipants() - booked;
	                    ticketsLeft.setText(String.valueOf(available));
	                } catch (Exception e) {
	                    ticketsLeft.setText("N/A");
	                }
	            }
	        });
	    }

	
	private void loadUserParticipations() {
	    try {
	        List<Participation> all = joinStagecontroller.showMembers();
	        List<Participation> userParticipations = all.stream()
	                .filter(p -> p.getUsername().equals(cred.getUsername()))
	                .collect(Collectors.toList());

	        ObservableList<Participation> observableList = FXCollections.observableArrayList(userParticipations);
	        joinedStagesTable.setItems(observableList);

	    } catch (Exception e) {
	    	throw new IllegalStateException("Error");//just for now
	    }
	}
	 
	private void updateDashboardInfo() {
	    try {
	        List<Participation> all = joinStagecontroller.showMembers();
	        

	        double total = all.stream()
	                .filter(p -> p.getUsername().equals(cred.getUsername()))
	                .mapToDouble(Participation::getTotal)
	                .sum();
	        int tickets = all.stream()
	        	    .filter(p -> p.getUsername().equals(cred.getUsername()))
	        	    .mapToInt(Participation::getTicket)
	        	    .sum();


	        dashboardOUT.setText("€" + total);
	        dashboardOUT.setText("€" + total);
	        dashboardNT.setText(String.valueOf(tickets));

	    } catch (Exception e) {
	    	throw new IllegalStateException("Error");//just for now
	    }
	}


	public void switchForm(ActionEvent event) {
   	 if (event.getSource() == dashboardBtn) {

            dashboardForm.setVisible(true);
            stageForm.setVisible(false);
             messageForm.setVisible(false);
           subForm.setVisible(false);

        } else if (event.getSource() == joinstagesBtn) {

       	 dashboardForm.setVisible(false);
         stageForm.setVisible(true);   
         messageForm.setVisible(false);
         subForm.setVisible(false);
        } 
        else if(event.getSource()==messageBtn) {
        	loadMessages(); // Carica i messaggi
        	dashboardForm.setVisible(false);
            stageForm.setVisible(false);
            messageForm.setVisible(true);
            subForm.setVisible(false);
        }
        else if(event.getSource()== subBtn) {
        	dashboardForm.setVisible(false);
            stageForm.setVisible(false);
            messageForm.setVisible(false);
            subForm.setVisible(true);
            loadUserParticipations(); // Carica i dati utente
        }
   	 
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
    

    private void loadMessages() {
        // Ottieni i messaggi dal controller per il partecipante specificato
        List<Message> messages = joinStagecontroller.retrieveMessages();
        
        List<Message> userMessage = new ArrayList<>();
   	 
   	 for(Message m : messages) {
   		 if(m.getFromUser().equals(cred.getUsername())) {
   		
   			 userMessage.add(m);
   		 }
   	 }

  // Prepara i testi per la ListView
     ObservableList<String> messageTexts = FXCollections.observableArrayList();
     for (Message msg : userMessage) {
         StringBuilder display = new StringBuilder();
         display.append("From you: ").append(msg.getContent());

         if (msg.hasReply()) {
             display.append("\n↳ Reply from trainer: ").append(msg.getReply());
         }

         messageTexts.add(display.toString());
     }

     // Mostra i messaggi nella ListView
     messagesListView.setItems(messageTexts);
    }
    
    public void joinStage() {//it calls, as every GUI controller, the general controller to implement the use case
    
    	    TrainingStage selectedStage = stageTable.getSelectionModel().getSelectedItem();

    	    if (selectedStage == null) {
    	        showAlert(Alert.AlertType.ERROR, "Selection Error", "No stage selected!");
    	        return;
    	    }

    	    // Crea e collega i tuoi Handler come nel CLI
    	    HealthForm form = new HealthForm(checkbox1.isSelected(),checkbox2.isSelected(), checkbox3.isSelected()); // O quello che usi per passare i dati di salute
    	    
    	    HealthCheckHandler heartCheck = new HeartCheckHandler();
	        HealthCheckHandler injuryCheck  = new InjuriesCheckHandler();
	        HealthCheckHandler isPhysicallyCheck = new FitnessCheckHandler();

    	    injuryCheck.setNext(heartCheck);
    	    heartCheck.setNext(isPhysicallyCheck );

    	    // Esegui la catena
    	    if (!injuryCheck.handle(form)) {
    	        showAlert(Alert.AlertType.ERROR, "Health Check Failed", "You must pass all health checks!");
    	        return;
    	    }

    	    // Se superato, procedi con ticket
    	    String ticketTypeText = ticketTypeComboBox.getSelectionModel().getSelectedItem();
    	    if (ticketTypeText == null) {
    	        showAlert(Alert.AlertType.ERROR, "Ticket Error", "You must select a ticket type!");
    	        return;
    	    }

    	    int quantity = ticketQuantitySpinner.getValue();

    	    Ticket ticket;
    	    TicketStrategy strategy;
    	   double total;

    	    if (ticketTypeText.contains("Basic")) {
    	        ticket = new BasicTicket(quantity);
    	        strategy = new StandardTicket();
    	    } else if (ticketTypeText.contains("Special")) {
    	        ticket = new SpecialTicket(new BasicTicket(quantity));
    	        strategy = new PromotionalEvent();
    	    } else if (ticketTypeText.contains("VIP")) {
    	        ticket = new VipTicket(new BasicTicket(quantity));
    	        strategy = new VipStrategy();
    	    } else {
    	        showAlert(Alert.AlertType.ERROR, "Ticket Error", "Invalid ticket type!");
    	        return;
    	    }

    	    total = strategy.applyEvents(ticket);

    	    AddStageController controller = new AddStageController();
    	    

    	    List<Integer> membersList = joinStagecontroller.getSubscribers(controller.getAllStages());
    	    int selectedIndex = stageTable.getSelectionModel().getSelectedIndex();

    	    if (selectedIndex >= 0 && selectedIndex < membersList.size()) {
    	        int availableSpots = membersList.get(selectedIndex);

    	        if (quantity > availableSpots) {
    	            showAlert(Alert.AlertType.ERROR, "Capacity Error", "Not enough spots available!");
    	            return;
    	        }
    	    } else {
    	        showAlert(Alert.AlertType.ERROR, "Selection Error", "Invalid stage selection!");
    	        return;
    	    }
    	   
    	    Participation participation = new Participation(cred.getUsername(), selectedStage.getTitle(), quantity,total);   //forse devo crearci una entita ticket proprio e non solo una quantità?
    	    JoinStageController joinstagecontroller = new JoinStageController();
    	    String question = questionTextArea.getText().trim();
    	    Message message = null;

    	    if (!question.isEmpty()) {
    	        message = new Message(cred.getUsername(), question);
    	    }

    	    joinstagecontroller.registrateMember(participation, message);
    	    questionTextArea.clear();
    	  
 
    	    showAlert(Alert.AlertType.INFORMATION, "Success", "Participation successful! Ticket Total: " + total + " €");
    	    updateDashboardInfo();
    	
    }

    	private void showAlert(Alert.AlertType type, String title, String message) {
    	    Alert alert = new Alert(type);
    	    alert.setTitle(title);
    	    alert.setHeaderText(null);
    	    alert.setContentText(message);
    	    alert.showAndWait();
    	}

    
	                
	public boolean isNotEmpty(String title, String location, String itinerary, String selectedCategory, String maxPart, LocalDate localDate) {
	return !title.isEmpty() && !location.isEmpty() && !itinerary.isEmpty() && selectedCategory != null && !selectedCategory.isEmpty() && 
	!maxPart.isEmpty() && localDate != null;
	 }   
	
    public void close() {
        javafx.application.Platform.exit();
    }
    public void minimize() {
    	Stage stage = (Stage) mainForm.getScene().getWindow();
        stage.setIconified(true);
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
        	
        	throw new IllegalStateException("Error");//just for now
        }
    }


	public void setCredentials(Credentials cred) {
		this.cred = cred;
		
	}
    
}
