package main.java.com.streetfit.controllerfx;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import main.java.com.streetfit.beans.HealthFormBean;
import main.java.com.streetfit.beans.MessageBean;
import main.java.com.streetfit.beans.ParticipationBean;
import main.java.com.streetfit.beans.TicketBean;
import main.java.com.streetfit.beans.TrainingStageBean;
import main.java.com.streetfit.controller.AddStageController;
import main.java.com.streetfit.controller.JoinStageController;
import main.java.com.streetfit.model.Credentials;

import main.java.com.streetfit.model.TrainingStage;

import main.java.com.streetfit.utils.NotificationQueue;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ParticipantControllerFX {
	
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
	private TableView<TrainingStageBean> stageTable;
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
	    private TableColumn<TrainingStageBean, String> nameColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, String> placeColumn;
	    @FXML
	    private Label dashboardNT;
	    @FXML
	    private Label dashboardOUT;
	    @FXML
	    private Label dashboardNS;
	    @FXML
	    private TableColumn<TrainingStageBean, String> itineraryColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, String> categoryColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, Date> dateColumn;
	    @FXML
	    private TableColumn<TrainingStageBean, Integer> ticketsLeftsColumn;
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
	    private TableView<ParticipationBean> joinedStagesTable;

	    @FXML
	    private TableColumn<ParticipationBean, String> stageTitleColumn;

	    @FXML
	    private TableColumn<ParticipationBean, Integer> ticketColumn;

	    @FXML
	    private TableColumn<ParticipationBean, Double> totalColumn;

	    private Credentials cred;
	    @FXML
	    private VBox ticketFormBox; 
	    @FXML private Button continueBtn;
	    @FXML private Button continueTicketBtn;
	    @FXML
	    private HBox ticketSelectionBox;
	    
	    private double x = 0;
	    private double y = 0;
	    private NotificationQueue queue;
	
	    private JoinStageController joinStagecontroller = new JoinStageController();
	 
	public ParticipantControllerFX(Credentials cred, NotificationQueue notificationQueue) {
		this.cred = cred;
		this.queue = notificationQueue;
		}

	

	    public void initialize() {
	    	ticketFormBox.setVisible(false);
	    	ticketFormBox.setOpacity(0);
	        setupStageTableColumns();
	        setupDashboardBindings();
	        setupSpinner();
	        setupRowSelection();
	        printTable();
	    }
	    
	    @FXML
	    private void handleProceed(ActionEvent event) {
	        if (stageTable.getSelectionModel().getSelectedItem() == null) {
	            showAlert(Alert.AlertType.ERROR, "Selection Required", "Please select a stage first.");
	            return;
	        }

	        // Mostra con effetto fade
	        ticketFormBox.setVisible(true);
	        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), ticketFormBox);
	        fadeIn.setFromValue(0.0);
	        fadeIn.setToValue(1.0);
	        fadeIn.play();
	    }
	    
	    @FXML
	    private void handleContinueTicket(ActionEvent event) {
	        if (stageTable.getSelectionModel().getSelectedItem() == null) {
	            showAlert(Alert.AlertType.WARNING, "No Stage Selected", "Please select a stage before continuing.");
	            return;
	        }

	        ticketSelectionBox.setVisible(true);

	        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), ticketSelectionBox);
	        fadeIn.setFromValue(0.0);
	        fadeIn.setToValue(1.0);
	        fadeIn.play();
	    }

	    private void setupStageTableColumns() {
	    	// Assicurati che la colonna del titolo dello stage prenda solo il titolo dello stage
	        stageTitleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStage().getTitle()));
	        
	        // Imposta altre colonne per ticket e totale
	        ticketColumn.setCellValueFactory(new PropertyValueFactory<>("ticket"));
	        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
	        
	        // Altre colonne
	        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
	        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
	        itineraryColumn.setCellValueFactory(new PropertyValueFactory<>("itinerary"));
	        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
	        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
	    }

	    private void setupDashboardBindings() {
	        ticketsLeftsColumn.setCellValueFactory(cellData -> {
	            TrainingStageBean stage = cellData.getValue();

	            int remaining = stage.getMaxParticipants();
	            int stages = 0;

	            try {
	                List<ParticipationBean> all = joinStagecontroller.retrieveMembers();
	                int booked = all.stream()
	                                .filter(p -> p.getStage().equals(stage.getTitle()))
	                                .mapToInt(ParticipationBean::getTicket)
	                                .sum();
	                remaining -= booked;

	                for (ParticipationBean member : all) {
	                    if (member.getUsername().equals(cred.getUsername())) {
	                        stages += 1;
	                    }
	                }

	                dashboardNS.setText(String.valueOf(stages));

	                List<ParticipationBean> members = joinStagecontroller.retrieveMembers();
	                double total = 0;
	                int tickets = 0;

	                for (ParticipationBean member : members) {
	                    if (member.getUsername().equals(cred.getUsername())) {
	                        total += member.getTotal();
	                        tickets += member.getTicket();
	                    }
	                }

	                dashboardOUT.setText("€" + total);
	                dashboardNT.setText(String.valueOf(tickets));

	            } catch (Exception e) {
	            	e.printStackTrace();
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
	                stagePlace.setText(newSelection.getPlace());
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
	                    List<ParticipationBean> all = joinController.retrieveMembers();
	                    int booked = all.stream()
	                                    .filter(p -> p.getStage().equals(newSelection.getTitle()))
	                                    .mapToInt(ParticipationBean::getTicket)
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
	            List<ParticipationBean> all = joinStagecontroller.retrieveMembers();

	            // Filtra solo quelli dell'utente corrente
	            List<ParticipationBean> userParticipations = all.stream()
	                .filter(p -> p.getUsername().equals(cred.getUsername()))
	                .collect(Collectors.toList());

	            // Raggruppa per stage e somma i ticket
	            Map<String, ParticipationBean> aggregated = new HashMap<>();

	            for (ParticipationBean p : userParticipations) {
	                String stageTitle = p.getStage().getTitle();
	                if (aggregated.containsKey(stageTitle)) {
	                    ParticipationBean existing = aggregated.get(stageTitle);
	                    int updatedTickets = existing.getTicket() + p.getTicket();
	                    double updatedTotal = existing.getTotal() + p.getTotal();
	                    aggregated.put(stageTitle, new ParticipationBean(p.getUsername(), p.getStage(), updatedTickets, updatedTotal));
	                } else {
	                    aggregated.put(stageTitle, new ParticipationBean(p.getUsername(), p.getStage(), p.getTicket(), p.getTotal()));
	                }
	            }

	            ObservableList<ParticipationBean> observableList = FXCollections.observableArrayList(aggregated.values());
	            joinedStagesTable.setItems(observableList);

	        } catch (Exception e) {
	            throw new IllegalStateException("Error"); // just for now
	        }
	      }

	 
	private void updateDashboardInfo() {
	    try {
	        List<ParticipationBean> all = joinStagecontroller.retrieveMembers();
	        

	        double total = all.stream()
	                .filter(p -> p.getUsername().equals(cred.getUsername()))
	                .mapToDouble(ParticipationBean::getTotal)
	                .sum();
	        int tickets = all.stream()
	        	    .filter(p -> p.getUsername().equals(cred.getUsername()))
	        	    .mapToInt(ParticipationBean::getTicket)
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
        List<TrainingStageBean> stageList;

        AddStageController controller = new AddStageController();
        stageList = controller.getAllStages(); //

        // Converti la List in ObservableList
        ObservableList<TrainingStageBean> observableStageList = FXCollections.observableArrayList(stageList);

        // Imposta la ObservableList nella TableView
        stageTable.setItems(observableStageList); // Usa direttamente l'ObservableList
    }
    

    private void loadMessages() {
        // Ottieni i messaggi dal controller per il partecipante specificato
        List<MessageBean> messages = joinStagecontroller.retrieveMessages();
        
        List<MessageBean> userMessage = new ArrayList<>();
   	 
   	 for(MessageBean m : messages) {
   		 if(m.getFromUser().equals(cred.getUsername())) {
   		
   			 userMessage.add(m);
   		 }
   	 }

  // Prepara i testi per la ListView
     ObservableList<String> messageTexts = FXCollections.observableArrayList();
     for (MessageBean msg : userMessage) {
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
    
    public void joinStage() {
        // Recupera lo stage selezionato dalla TableView
        TrainingStageBean selectedStage = stageTable.getSelectionModel().getSelectedItem();
        if (selectedStage == null) {
            showAlert(Alert.AlertType.ERROR, "Selection Error", "No stage selected!");
            return;
        }

        // Crea un oggetto HealthFormBean dal form
        HealthFormBean form = new HealthFormBean(checkbox1.isSelected(), checkbox2.isSelected(), checkbox3.isSelected());
        if (!joinStagecontroller.isHealthFormValid(form)) {
            showAlert(Alert.AlertType.ERROR, "Health Check Failed", "You must pass all health checks!");
            return;
        }

        // Recupera il tipo di ticket dalla ComboBox
        String ticketTypeText = ticketTypeComboBox.getSelectionModel().getSelectedItem();
        if (ticketTypeText == null) {
            showAlert(Alert.AlertType.ERROR, "Ticket Error", "You must select a ticket type!");
            return;
        }

        // Recupera la quantità del ticket dalla Spinner
        int quantity = ticketQuantitySpinner.getValue();

        // Crea il TicketBean in base alla selezione dell'utente
        TicketBean ticketBean = new TicketBean(ticketTypeText, quantity);

        ticketBean.handleInput(ticketTypeText);
        // Calcola il prezzo del ticket usando il controller
        double total = joinStagecontroller.calculateTicketPrice(ticketBean);

        // Ottieni la lista degli stage e i partecipanti
        AddStageController controller = new AddStageController();
        List<TrainingStageBean> stages = controller.getAllStages();
        List<TrainingStageBean> stageBeans = new ArrayList<>();
        
        for (TrainingStageBean stage : stages) {
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

        // Recupera la lista dei partecipanti per ogni stage
        List<Integer> membersList = joinStagecontroller.getSubscribers(stageBeans);
        int selectedIndex = stageTable.getSelectionModel().getSelectedIndex();

        // Controlla se ci sono abbastanza posti disponibili per il ticket
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
        
        TrainingStage stage = new TrainingStage(selectedStage.getTitle(),selectedStage.getItinerary(),selectedStage.getCategory(),selectedStage.getDate(),selectedStage.getPlace(),selectedStage.getMaxParticipants());

        // Crea il bean di partecipazione
        ParticipationBean participation = new ParticipationBean(cred.getUsername(), stage, quantity, total);
        
        // Se l'utente ha scritto una domanda nel campo del messaggio
        String question = questionTextArea.getText().trim();
        MessageBean message = question.isEmpty() ? null : new MessageBean(cred.getUsername(), question);

        // Mostra un alert di conferma prima di procedere con la registrazione
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Participation");
        confirmationAlert.setHeaderText("Please confirm your ticket purchase");
        confirmationAlert.setContentText("Stage: " + selectedStage.getTitle() + "\n" +
                "Ticket: " + ticketTypeText + "\n" +
                "Quantity: " + quantity + "\n" +
                "Total: " + total + " €\n\nProceed?");
        
        Optional<ButtonType> result = confirmationAlert.showAndWait();

        // Se l'utente conferma, procedi con la registrazione
        if (result.isPresent() && result.get() == ButtonType.OK) {
            joinStagecontroller.registrateMember(participation, message, queue);
            questionTextArea.clear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Participation successful! Ticket Total: " + total + " €");
            updateDashboardInfo();  // Aggiorna l'interfaccia utente
            setupDashboardBindings();  // Imposta il binding della dashboard
            stageForm.setVisible(false);  // Nascondi il form dello stage
            loadUserParticipations();  // Carica le partecipazioni dell'utente
            subForm.setVisible(true);  // Mostra il form delle sottoscrizioni
        }
    }


    public void dummy() {
    	showAlert(Alert.AlertType.WARNING,"Attention" ,"Not implemented yet" );
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
