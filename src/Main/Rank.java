package Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class Rank implements Initializable {

    @FXML
    private TableView<?> personalEasyTable;

    @FXML
    private TableView<?> personalMediumTable;

    @FXML
    private TableView<?> personalHardTable;

    @FXML
    private TableView<?> allEasyTable;

    @FXML
    private TableView<?> allMediumTable;

    @FXML
    private TableView<?> allHardTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableColumn[][][] tableColumn = new TableColumn[][][]{
                {{column1_1, column1_2, column1_3}, {column1_4, column1_5, column1_6}, {column1_7, column1_8, column1_9}},
                {{column2_1, column2_2, column2_3}, {column2_4, column2_5, column2_6}, {column2_7, column2_8, column2_9}}
        };

        TableView[][] tableViews = new TableView[][]{
                {personalEasyTable, personalMediumTable, personalHardTable},
                {allEasyTable, allMediumTable, allHardTable}
        };

        ObservableList<RankItem>[][] datas = new ObservableList[2][3];

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                datas[i][j] = FXCollections.observableArrayList();
                tableViews[i][j].setItems(datas[i][j]);
                for (int k = 0; k < 3; k++) {
                    tableColumn[i][j][k].setCellValueFactory(
                            new PropertyValueFactory<RankItem, String>(
                                    RankItem.rankItemName[k]
                            ));
                }
            }
        }

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                RankItem[] rankItems = null;
                if (i == 0) {
                   rankItems = new Utils().getTop(EasyTransFromWindow.name, EasyTransFromWindow.GAMETYPES[j]);
                   datas[i][j].addAll(rankItems);
                }
                if (i == 1) {
                    rankItems = new Utils().getTop(EasyTransFromWindow.GAMETYPES[j]);
                    datas[i][j].addAll(rankItems);
                }
            }
        }
    }

    @FXML
    private TableColumn<?, ?> column1_1;

    @FXML
    private TableColumn<?, ?> column1_2;

    @FXML
    private TableColumn<?, ?> column1_3;

    @FXML
    private TableColumn<?, ?> column1_4;

    @FXML
    private TableColumn<?, ?> column1_5;

    @FXML
    private TableColumn<?, ?> column1_6;

    @FXML
    private TableColumn<?, ?> column1_7;

    @FXML
    private TableColumn<?, ?> column1_8;

    @FXML
    private TableColumn<?, ?> column1_9;

    @FXML
    private TableColumn<?, ?> column2_1;

    @FXML
    private TableColumn<?, ?> column2_2;

    @FXML
    private TableColumn<?, ?> column2_3;

    @FXML
    private TableColumn<?, ?> column2_4;

    @FXML
    private TableColumn<?, ?> column2_5;

    @FXML
    private TableColumn<?, ?> column2_6;

    @FXML
    private TableColumn<?, ?> column2_7;

    @FXML
    private TableColumn<?, ?> column2_8;

    @FXML
    private TableColumn<?, ?> column2_9;
}
