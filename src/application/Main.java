package application;

import java.io.File;
import java.util.Arrays;
import java.util.List;

//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main extends Application {
//	private static String path = "C:\\Users\\user\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\answers.dat";
//	private static BufferedInputStream originalFile;
//	private static int[] frequencies;
//	private static int numberOfChars;
//	private static TNode<Integer> root;
	private Button btnCompress1 = new Button("Compress");
	private Button btnDecompress1 = new Button("Decompress");
	private Stage primaryStage;
	private Stage compressStage;
	private Stage decompressStage;
	private Font btnFont = Font.font("Times New Roman", FontWeight.NORMAL, 20);
	private File fileToCompress;
	private File fileToDecompress;
	private HuffmanCompress compress;
	private HuffmanDecompress decompress;

	@Override
	public void start(Stage primaryStage) {
		try {
//			BorderPane root = new BorderPane();
//			Scene scene = new Scene(root, 400, 400);
			Scene scene = mainInterface();
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			this.primaryStage = primaryStage;
			this.primaryStage.setScene(scene);
			this.primaryStage.setFullScreen(true);
			this.primaryStage.setFullScreenExitHint("");
			this.primaryStage.show();

			compressStage = compressScreen();
			decompressStage = decompressScreen();

			// set on action
			btnCompress1.setOnAction(e -> {
				primaryStage.close();
				decompressStage.close();
				compressStage.show();
			});

			btnDecompress1.setOnAction(e -> {
				primaryStage.close();
				compressStage.close();
				decompressStage.show();
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		readFile();
//		printArray(frequencies);
//		buildHuffmanTree();

//		HuffmanCompress huff = new HuffmanCompress(
//				"C:\\Users\\user\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\answers.dat");
//
//		HuffmanDecompress huffDe = new HuffmanDecompress(
//				"C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\answers.huf");

//		HuffmanCompress huff4 = new HuffmanCompress(
//				"C:\\Users\\user\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\try.dat");
//
//		HuffmanDecompress huffDe4 = new HuffmanDecompress(
//				"C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\try.huf");
//		
//		boolean flag = true;
//		for(int i = 0; i < 8; i++) {
//			if(huff.getBuffer()[i] != huffDe.getBuffer()[i]) {
//				flag = false;
//				break;
//			}
//			System.out.println("flag = " + flag);
//		}

//		System.out.println("h");

//		for(int i = 0; i < 8; i++) {
//			System.out.print(huff.getBuffer()[i] + " ");
//		}
//		System.out.println();
//		
//		for(int i = 0; i < 8; i++) {
//			System.out.print(huffDe.getBuffer()[i] + " ");
//		}
		
//		HuffmanCompress huff6 = new HuffmanCompress("C:\\Users\\user\\Desktop\\HuffmanCompress.txt");
//		HuffmanDecompress huffDe6 = new HuffmanDecompress(
//				"C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\HuffmanCompress.huf");
				
//		HuffmanCompress huff2 = new HuffmanCompress("C:\\Users\\user\\Desktop\\myimg.png");
//		System.out.println("No. of chars: " + huff2.getNumberOfChars());
//		HuffmanDecompress huffDe2 = new HuffmanDecompress(
//				"C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\myimg.huf");

//		HuffmanCompress huff5 = new HuffmanCompress("C:\\Users\\user\\Desktop\\img.png");
//		HuffmanDecompress huffDe5 = new HuffmanDecompress("C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\img.huf");


//		for(int i = 0; i < 8; i++) {
//			System.out.print(huff6.getBuffer()[i] + " ");
//		}
//		System.out.println("\n");
//		
//		for(int i = 0; i < 8; i++) {
//			System.out.print(huffDe6.getBuffer()[i] + " ");
//		}

//		HuffmanCompress huff3 = new HuffmanCompress("C:\\Users\\user\\Desktop\\text.txt");
//		HuffmanDecompress huffDe3 = new HuffmanDecompress(
//				"C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\text.huf");

//		HuffmanCompressing huff = new HuffmanCompressing("C:\\Users\\user\\Downloads\\ds_salaries.csv", true);

//		HuffmanCompressing huff = new HuffmanCompressing("C:\\Users\\user\\Downloads\\archive.zip", true);

//		HuffmanCompress huff = new HuffmanCompress("C:\\Users\\user\\Downloads\\Untitled-2.py");

//		HuffmanCompressing huff = new HuffmanCompressing("C:\\Users\\user\\Downloads\\Assignemt No. 4.ipynb", true);

//		HuffmanCompress huff = new HuffmanCompress("C:\\Users\\user\\Documents\\Sound recordings\\Recording.m4a");
//		HuffmanDecompress huffDe = new HuffmanDecompress(
//				"C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\Recording.huf");

//		HuffmanCompressing huff = new HuffmanCompressing("C:\\Users\\user\\Desktop\\lina\\Java2\\phase1\\LibraryMember.java", true);

//		HuffmanCompress huff00 = new HuffmanCompress("C:\\Users\\user\\Videos\\Captures\\ 2024-05-01 18-02-20.mp4");
//		System.out.println("char at 506: " + huff00.CreateHuffmanCodingTree().charAt(506));
//		HuffmanDecompress huffDe00 = new HuffmanDecompress(
//				"C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\ 2024-05-01 18-02-20.huf");

//		HuffmanCompress huff7 = new HuffmanCompress(
//				"C:\\Users\\user\\Downloads\\android-studio-2023.2.1.23-windows.exe");
//		HuffmanDecompress huffDe7 = new HuffmanDecompress(
//				"C:\\Users\\user\\Desktop\\lina\\eclipse\\Algorithms_projII\\android-studio-2023.2.1.23-windows.huf");
		System.out.println("It worked!");
		launch(args);
	}

	private Scene mainInterface() {

		// root pane
		StackPane rootPane = new StackPane();

		// scene
		Scene scene = new Scene(rootPane);

		// image
		Image img = new Image("file icon.jpg");
		ImageView maniImgView = new ImageView(img);

		// fit height and width of image
		maniImgView.fitHeightProperty().bind(rootPane.heightProperty());
		maniImgView.fitWidthProperty().bind(rootPane.widthProperty());

		// add the image to the pane
		rootPane.getChildren().add(maniImgView);

		// create a border pane
		BorderPane pane = new BorderPane();

		// set the border pane items on top of the image
		rootPane.getChildren().add(pane);

		// add items to the border pane
		HBox buttonsBox = new HBox(20);
		buttonsBox.getChildren().addAll(btnCompress1, btnDecompress1);
		buttonsBox.setAlignment(Pos.CENTER);
		buttonsBox.setPadding(new Insets(30));

		// adjust buttons properties
		btnCompress1.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		btnDecompress1.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));

		Label lblTitle = new Label("Huffman Code: The Way Into Effective Data Compression");
		lblTitle.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 40));
//		lblTitle.setTextFill(Color.WHITE);
		lblTitle.setAlignment(Pos.CENTER);

		HBox lblBox = new HBox();
		lblBox.getChildren().add(lblTitle);
		lblBox.setAlignment(Pos.CENTER);
		lblBox.setPadding(new Insets(50));

		pane.setBottom(buttonsBox);
		pane.setTop(lblBox);

		return scene;
	}

	private Stage compressScreen() {

		// create rootPane
		StackPane rootPane = new StackPane();

		// create a scene
		Scene scene = new Scene(rootPane);

		// image
		Image img = new Image("filesss.jpg");
		ImageView maniImgView = new ImageView(img);

		// set the image in the pane
		rootPane.getChildren().add(maniImgView);

		// adjust image properties
		maniImgView.fitHeightProperty().bind(rootPane.heightProperty());
		maniImgView.fitWidthProperty().bind(rootPane.widthProperty());

		// border pane
		BorderPane pane = new BorderPane();

		// create a new Stage
		Stage stage = new Stage();
		stage.setScene(scene);

		// create back button
		Button btnBack = new Button("<< Go Back");
		// btnBack.setTextFill(Color.WHITE);
		adjustButtonBack(btnBack);

		// add pane to rootPane
		rootPane.getChildren().add(pane);

		// add File chooser
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		// Create a button called select to select a file from the fileChooser and a
		// label called selected
		Button btnSelect = new Button("Select a file");
		btnSelect.setFont(btnFont);
		Label lblSelected = new Label("No file has been selected");
		lblSelected.setFont(btnFont);
		gridPane.add(btnSelect, 0, 0);
		gridPane.add(lblSelected, 1, 0);

		// create a button called compress to compress file
		Button btnCompress = new Button("compress");
		btnCompress.setFont(btnFont);
		btnCompress.setDisable(true);
		Label lblCompressed = new Label("click the button to compress selected file");
		lblCompressed.setFont(btnFont);
		gridPane.add(btnCompress, 0, 1);
		gridPane.add(lblCompressed, 1, 1);

		VBox box = new VBox(100);
		box.setAlignment(Pos.CENTER);
//		box.getChildren().addAll(gridPane);

		HBox buttonsBox = new HBox(5);
		buttonsBox.setAlignment(Pos.CENTER);
		buttonsBox.setPadding(new Insets(20));

		Button btnStatistics = new Button("Show Statistics");
		btnStatistics.setFont(btnFont);
		Button btnHeader = new Button("Show Header");
		btnHeader.setFont(btnFont);

		buttonsBox.getChildren().addAll(btnStatistics, btnHeader);
		box.getChildren().add(buttonsBox);

//		TextArea txAreaHeader = new TextArea(); 
//		txAreaHeader.setEditable(false);
//		txAreaHeader.maxHeightProperty().bind(rootPane.heightProperty().divide(10));
//		txAreaHeader.maxWidthProperty().bind(rootPane.widthProperty().divide(1.5));
//		txAreaHeader.setPromptText("Click 'Show Header' button to view the header of the compressed file");
//		txAreaHeader.setDisable(true);

//		box.getChildren().add(txAreaHeader);

		btnStatistics.setDisable(true);
		btnHeader.setDisable(true);

		gridPane.setPadding(new Insets(50));
		pane.setTop(gridPane);
		pane.setLeft(box);

		HBox backBox = new HBox();
		backBox.getChildren().add(btnBack);

		VBox allButtons = new VBox();
		allButtons.setAlignment(Pos.CENTER);
		allButtons.getChildren().addAll(buttonsBox, backBox);
		pane.setBottom(allButtons);

		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");

		// Creating a FileChooser called chooser to select a file:
		FileChooser chooser = new FileChooser();

		// List for image files extension
		List<String> imageExtensions = Arrays.asList("*.png", "*.jpg", "*.jpeg");
		String[] imageExtensionsArr = imageExtensions.toArray(new String[0]);

		// List for audio files extension
		List<String> audioExtensions = Arrays.asList("*.mp3", "*.wav", "*.aac", "*.flac", "*.wma", "*.m4a");
		String[] audioExtensionsArr = audioExtensions.toArray(new String[0]);

		// List for video files extension
		List<String> videoExtensions = Arrays.asList("*.mp4", "*.mov", "*.avi", "*.wmv", "*.mkv");
		String[] videoExtensionsArr = videoExtensions.toArray(new String[0]);

		// Set extension filter for text files
		FileChooser.ExtensionFilter extFilter1 = new FileChooser.ExtensionFilter("DAT files (*.dat)", "*.dat");
		FileChooser.ExtensionFilter extFilter2 = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
		FileChooser.ExtensionFilter extFilter3 = new FileChooser.ExtensionFilter("EXE files (*.exe)", "*.exe");
		FileChooser.ExtensionFilter extFilter4 = new FileChooser.ExtensionFilter(
				"Image files (*.png), (*.jpg), (*.jpeg)", imageExtensionsArr);
		FileChooser.ExtensionFilter extFilter5 = new FileChooser.ExtensionFilter("Document files (*.docx)", "*.docx");
		FileChooser.ExtensionFilter extFilter6 = new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf");
		FileChooser.ExtensionFilter extFilter7 = new FileChooser.ExtensionFilter("Java files (*.java)", "*.java");
		FileChooser.ExtensionFilter extFilter8 = new FileChooser.ExtensionFilter("Python files (*.py)", "*.py");
		FileChooser.ExtensionFilter extFilter9 = new FileChooser.ExtensionFilter("PYNB files (*.pynb)", "*.pynb");
		FileChooser.ExtensionFilter extFilter10 = new FileChooser.ExtensionFilter(
				"Audio files (*.mp3), (*.wav), (*.aac), (*.flac), (*.wma), (*.m4a)", audioExtensionsArr);
		FileChooser.ExtensionFilter extFilter11 = new FileChooser.ExtensionFilter(
				"Video files (*.mp4), (*.mov), (*.avi), (*.wmv), (*.mkv)", videoExtensionsArr);
		FileChooser.ExtensionFilter extFilter12 = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
		FileChooser.ExtensionFilter extFilter13 = new FileChooser.ExtensionFilter("HTML files (*.html)", "*.html");
		FileChooser.ExtensionFilter extFilter14 = new FileChooser.ExtensionFilter("PRX files (*.prx)", "*.prx");
		chooser.getExtensionFilters().addAll(extFilter1, extFilter2, extFilter3, extFilter4, extFilter5, extFilter6,
				extFilter7, extFilter8, extFilter9, extFilter10, extFilter11, extFilter12, extFilter13, extFilter14);

		btnSelect.setOnAction(e -> {
			fileToCompress = chooser.showOpenDialog(stage);
			if (fileToCompress != null) {
				lblSelected.setText(fileToCompress.getAbsolutePath());
				btnCompress.setDisable(false);
				lblCompressed.setText("click the button to compress selected file");
				lblCompressed.setTextFill(Color.BLACK);
			}
		});

		btnCompress.setOnAction(e -> {
			try {
				compress = new HuffmanCompress(fileToCompress.getAbsolutePath());
				lblCompressed.setText("File has been compressed successfully!");
				lblCompressed.setTextFill(Color.GREEN);
				btnStatistics.setDisable(false);
				btnHeader.setDisable(false);
			} catch (Exception n) {
				lblCompressed.setText("Failed to compress the selected file!");
				lblCompressed.setTextFill(Color.RED);
			}
		});

		btnHeader.setOnAction(e -> {
			showHeader(stage, null, compress);
		});

		btnStatistics.setOnAction(e -> {
			getStatistics(stage, null, compress);
		});
		return stage;
	}

	private void showHeader(Window owner, Modality modality, HuffmanCompress compress) {
		Stage stage = new Stage();

		stage.initOwner(owner);
		stage.initModality(modality);

//		Pane rootPane = new Pane();
//		rootPane.setStyle("-fx-background-color: white;");

		String codingTree = compress.CreateHuffmanCodingTree();

		TextArea txtAreaHeader = new TextArea();
		txtAreaHeader.setEditable(false);
//		txtAreaHeader.maxHeightProperty().bind(rootPane.heightProperty().divide(15));
//		txtAreaHeader.maxWidthProperty().bind(rootPane.widthProperty().divide(5));

		String txtFileExtent = compress.getOriginalFileExtension() + "\n";
		String txtFileSize = compress.getOriginalFileSize() + "\n";
		String txtHeaderSize = compress.getHeaderSize() + "\n";
		String txtAll = "Original File Extension: " + txtFileExtent + "Original File Size: " + txtFileSize
				+ "Header Size: " + txtHeaderSize + "Huffman Coding Tree: " + codingTree;

		txtAreaHeader.setText(txtAll);

		VBox box = new VBox(10);
		box.setAlignment(Pos.CENTER);

		box.setStyle("-fx-background-color: white;");

		Label lblHeader = new Label("Header of compressed file: ");
		lblHeader.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 15));
		box.getChildren().addAll(lblHeader, txtAreaHeader);
		box.setPadding(new Insets(20));
//		rootPane.getChildren().add(box); 

//		txtAreaHeader.setDisable(true);

//		rootPane.getChildren().add(txtAreaHeader);
//		
//		rootPane.setPrefSize(500, 500);
		stage.setScene(new Scene(box));
		stage.show();
	}

	private void getStatistics(Window owner, Modality modality, HuffmanCompress compress) {
		Stage stage = new Stage();

		stage.initOwner(owner);
		stage.initModality(modality);

		BorderPane rootPane = new BorderPane();
		rootPane.setStyle("-fx-background-color: white;");

		TableView<Data> table = new TableView<>();

		TableColumn<Data, Character> characterClm = new TableColumn<>("ASCII Character");
		TableColumn<Data, Integer> freqClm = new TableColumn<>("Frequency");
		TableColumn<Data, Byte> codeClm = new TableColumn<>("Huffman Code");
		TableColumn<Data, Integer> lengthClm = new TableColumn<>("Length");

		characterClm.setCellValueFactory(new PropertyValueFactory<>("character"));
		freqClm.setCellValueFactory(new PropertyValueFactory<>("frequency"));
		codeClm.setCellValueFactory(new PropertyValueFactory<>("code"));
		lengthClm.setCellValueFactory(new PropertyValueFactory<>("length"));

		characterClm.setSortable(false);
		freqClm.setSortable(false);
		codeClm.setSortable(false);
		lengthClm.setSortable(false);

		characterClm.setStyle("-fx-alignment: center; -fx-posture: bold;");
		freqClm.setStyle("-fx-alignment: center; -fx-posture: bold;");
		codeClm.setStyle("-fx-alignment: center; -fx-posture: bold;");
		lengthClm.setStyle("-fx-alignment: center; -fx-posture: bold;");

		characterClm.setMinWidth(375);
		freqClm.setMinWidth(375);
		codeClm.setMinWidth(375);
		lengthClm.setMinWidth(375);

		table.setMaxWidth(1515);
		table.setMaxHeight(2200);

		table.setRowFactory(i -> {
			TableRow<Data> row = new TableRow<>();
			row.setMinHeight(30);
			row.setDisable(true);
			return row;
		});

		table.getColumns().addAll(characterClm, freqClm, codeClm, lengthClm);

		ObservableList<Data> data = FXCollections.observableArrayList();
		for (int i = 0; i < 256; i++) {
			Data dataItem = new Data((char) i, compress.getFrequencies()[i], compress.getHuff()[i],
					compress.getLengths()[i]);
			data.add(dataItem);
		}

		table.setItems(data);

		rootPane.setCenter(table);

//		stage.setFullScreenExitHint("");
//		stage.setFullScreen(true);
//		rootPane.setPrefSize(stage.getWidth()-10, stage.getHeight()-10);
		Scene scene = new Scene(rootPane);
		stage.setScene(scene);
		stage.show();
	}

	private Stage decompressScreen() {

		// create rootPane
		StackPane rootPane = new StackPane();

		// create a scene
		Scene scene = new Scene(rootPane);

		// image
		Image img = new Image("filesss.jpg");
		ImageView maniImgView = new ImageView(img);

		// set the image in the pane
		rootPane.getChildren().add(maniImgView);

		// adjust image properties
		maniImgView.fitHeightProperty().bind(rootPane.heightProperty());
		maniImgView.fitWidthProperty().bind(rootPane.widthProperty());

		// border pane
		BorderPane pane = new BorderPane();

		// create a new Stage
		Stage stage = new Stage();
		stage.setScene(scene);

		// create back button
		Button btnBack = new Button("<< Go Back");
		// btnBack.setTextFill(Color.WHITE);
		adjustButtonBack(btnBack);

		// add pane to rootPane
		rootPane.getChildren().add(pane);

		// add File chooser
		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(20);

		// Create a button called select to select a file from the fileChooser and a
		// label called selected
		Button btnSelect = new Button("Select a file");
		btnSelect.setFont(btnFont);
		Label lblSelected = new Label("No file has been selected");
		lblSelected.setFont(btnFont);
		gridPane.add(btnSelect, 0, 0);
		gridPane.add(lblSelected, 1, 0);

		// create a button called compress to compress file
		Button btnDecompress = new Button("Decompress");
		btnDecompress.setFont(btnFont);
		btnDecompress.setDisable(true);
		Label lblDecompreesed = new Label("click the button to decompress selected file");
		lblDecompreesed.setFont(btnFont);
		gridPane.add(btnDecompress, 0, 1);
		gridPane.add(lblDecompreesed, 1, 1);

		pane.setTop(gridPane);
		gridPane.setPadding(new Insets(50));

		pane.setBottom(btnBack);

		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");

		// Creating a FileChooser called chooser to select a file:
		FileChooser chooser = new FileChooser();

		// Set extension filter for text files
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("HUF files (*.huf)", "*.huf");
		chooser.getExtensionFilters().add(extFilter);

		btnSelect.setOnAction(e -> {
			fileToDecompress = chooser.showOpenDialog(stage);
			if (fileToDecompress != null) {
				lblSelected.setText(fileToDecompress.getAbsolutePath());
				btnDecompress.setDisable(false);
				lblDecompreesed.setText("click the button to decompress selected file");
				lblDecompreesed.setTextFill(Color.BLACK);
			}
		});

		btnDecompress.setOnAction(e -> {
			try {
				decompress = new HuffmanDecompress(fileToDecompress.getAbsolutePath());
				lblDecompreesed.setText("File has been decompressed successfully!");
				lblDecompreesed.setTextFill(Color.GREEN);
			} catch (Exception n) {
				lblDecompreesed.setText("Failed to decompress the selected file!");
				n.printStackTrace();
				lblDecompreesed.setTextFill(Color.RED);
			}
		});
		return stage;
	}

	private void adjustButtonBack(Button btnBack) {
		btnBack.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		btnBack.setPrefSize(200, 50);
		btnBack.setStyle("-fx-background-color: transparent;");

		btnBack.setOnAction(e -> {
			compressStage.close();
			decompressStage.close();
			primaryStage.show();
		});

		btnBack.setOnMouseMoved(e -> {
			btnBack.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
		});

		btnBack.setOnMouseExited(e -> {
			btnBack.setFont(Font.font("Times New Roman", FontWeight.NORMAL, 20));
		});
	}
}
