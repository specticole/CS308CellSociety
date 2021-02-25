package cellsociety.view;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Creates the initial or splash screen. On the splash screen, users can choose the language of
 * the simulation.
 *
 * @author Bill Guo
 *
 */
public class SplashScreen {

  public static final int WIDTH = 1280;
  public static final int HEIGHT = 720;

  private Stage stage;
  private GridPane masterLayout;
  private ResourceBundle bundle;

  /**
   * Constructor of the SplashScreen. Needs the stage that the screen will be displayed upon. The
   * stage is created in the Main.
   * @param stage from the Main to be used as the display
   */
  public SplashScreen(Stage stage){
    this.stage = stage;
    masterLayout = new GridPane();
    masterLayout.getStyleClass().add("splash-gridpane");
    bundle = ResourceBundle.getBundle("cellsociety/resources/splashLabels");
  }

  /**
   * Creates the JavaFX UI elements of the splash screen. Users can choose between two languages,
   * pig latin and english, using the created buttons.
   * @return the GridPane that is displayed and created by the Main
   */
  public GridPane initialize(){
    Text title = new Text();
    title.setText(bundle.getString("Title"));
    title.getStyleClass().add("splash-button");
    masterLayout.add(title,0,0);

    Button englishButton = new Button(bundle.getString("EnglishButtonLabel"));
    englishButton.getStyleClass().add("splash-button");
    englishButton.setOnAction(e -> handleEnglishButton());
    masterLayout.add(englishButton,0,1);

    Button pigLatinButton = new Button(bundle.getString("PigLatinButtonLabel"));
    pigLatinButton.getStyleClass().add("splash-button");
    pigLatinButton.setOnAction(e -> handlePigLatinButton());
    masterLayout.add(pigLatinButton,0,2);

    return masterLayout;
  }

  private void handleEnglishButton(){
    Locale locale = new Locale("en", "US");
    ResourceBundle bundle = ResourceBundle.getBundle("cellsociety/resources/labels", locale);
    switchToSimulation(bundle);
  }

  private void handlePigLatinButton(){
    Locale locale = new Locale("pl", "US");
    ResourceBundle bundle = ResourceBundle.getBundle("cellsociety/resources/labels", locale);
    switchToSimulation(bundle);
  }

  private void switchToSimulation(ResourceBundle newBundle){
    Parent root = new CellularAutomatonView(new GridPane(), newBundle).initialize();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    scene.getStylesheets().add("cellsociety/resources/normalfont.css");
    stage.setScene(scene);

  }

}
