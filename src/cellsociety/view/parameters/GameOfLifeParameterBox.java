package cellsociety.view.parameters;

import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class GameOfLifeParameterBox  extends ParameterBox{
  
  public GameOfLifeParameterBox(VBox newBox, ResourceBundle currentBundle){
    super(newBox, currentBundle);
  }

  @Override
  public void createFields() {
    HBox bornNumsBox = new HBox();
    bornNumsBox.getStyleClass().add("parameter-box");
    Text bornNums = new Text();
    bornNums.setText(bundle.getString("TODO"));
    TextField bornNumsField = new TextField();
    bornNumsBox.getChildren().addAll(bornNums, bornNumsField);

    HBox surviveNumsBox = new HBox();
    surviveNumsBox.getStyleClass().add("parameter-box");
    Text surviveNums = new Text();
    bornNums.setText(bundle.getString("TODO"));
    TextField surviveNumsField = new TextField();
    bornNumsBox.getChildren().addAll(surviveNums, surviveNumsField);

    Button bornNumsButton = new Button(bundle.getString("ApplyButtonLabel"));
    box.getChildren().addAll(bornNumsBox, surviveNumsBox, bornNumsButton);
  }
}
