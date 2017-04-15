package com.devprosony.core.controllers.modal.controller.main;

import com.devprosony.core.DataBaseManager;
import com.devprosony.core.controllers.model.TableSearchResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.stdOut;

/**
 * Created by proso on 4/14/2017.
 */
public class ScreenSearchPanel extends DataBaseManager{

    @FXML TextField textFieldTitleSearch;
    @FXML TextField textFieldGenreSearch;
    @FXML TextField textFieldAuthorSearch;

    @FXML TableView<TableSearchResult> tableViewSearch;
    @FXML TableColumn<TableSearchResult, String> tableColomnTitle;
    @FXML TableColumn<TableSearchResult, String> tableColomnGenre;
    @FXML TableColumn<TableSearchResult, String> tableColomnAuthor;

    Stage stageSceneSearch;
    private ObservableList<TableSearchResult> chestSearchResult = FXCollections.observableArrayList();

    @FXML private void initialize() {
    tableColomnTitle.setCellValueFactory(new PropertyValueFactory<TableSearchResult, String>("title"));
    tableColomnGenre.setCellValueFactory(new PropertyValueFactory<TableSearchResult, String>("genre"));
    tableColomnAuthor.setCellValueFactory(new PropertyValueFactory<TableSearchResult, String>("author"));
    }
    public ScreenSearchPanel() throws SQLException {}

    public void clickButtonSearch(ActionEvent actionEvent) {
        chestSearchResult.clear();
         String title = textFieldTitleSearch.getText();
        String genre = textFieldGenreSearch.getText();
        String author = textFieldAuthorSearch.getText();
        boolean titleTriger = false;
        boolean genreTriger = false;
        boolean authorTriger = false;
        if(title != null && !title.isEmpty()) titleTriger = true;
        if(genre != null && !genre.isEmpty()) genreTriger = true;
        if(author != null && !author.isEmpty()) authorTriger = true;
        connectionBD();
        ResultSet resultSet = getFullDataBookForSearchTable(titleTriger, genreTriger, authorTriger, title, genre, author);

        try {
            while(resultSet.next()){
                String titleForTable = resultSet.getString("book_title");
                String genreForTalbe = resultSet.getString("genre");
                String authorForTable = resultSet.getString("full_name");
                stdOut.println(titleForTable +" "+ genreForTalbe +" "+ authorForTable);
                chestSearchResult.add(new TableSearchResult(titleForTable, genreForTalbe, authorForTable));
                tableViewSearch.setItems(chestSearchResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        connectionClose();
    }

    public void clickButtonCansel(ActionEvent actionEvent) {
        stageSceneSearch.close();
    }
/********************************************************************************
*                              Other Metods                                     *
* ******************************************************************************/
    public void setStageAndOther(Stage stageSceneSearch){
        this.stageSceneSearch = stageSceneSearch;
    }
}
