package com.devprosony.core.controllers.modal.controller.main;

/**
 * Created by ${Prosony} on 4/14/2017.
 */
import com.devprosony.core.DataBaseManager;
import com.devprosony.core.controllers.model.TableSearchResult;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.devprosony.Main.stdOut;

public class ScreenSearchPanel extends DataBaseManager{

    @FXML private TextField textFieldTitleSearch;
    @FXML private TextField textFieldGenreSearch;
    @FXML private TextField textFieldAuthorSearch;

    @FXML private TableView<TableSearchResult> tableViewSearch;
    @FXML private TableColumn<TableSearchResult, String> tableColomnTitle;
    @FXML private TableColumn<TableSearchResult, String> tableColomnGenre;
    @FXML private TableColumn<TableSearchResult, String> tableColomnAuthor;

    private Stage stageSceneSearch;
    private ObservableList<TableSearchResult> chestSearchResult = FXCollections.observableArrayList();

    @FXML private void initialize() {
    tableColomnTitle.setCellValueFactory(new PropertyValueFactory<TableSearchResult, String>("Title"));
    tableColomnGenre.setCellValueFactory(new PropertyValueFactory<TableSearchResult, String>("Genre"));
    tableColomnAuthor.setCellValueFactory(new PropertyValueFactory<TableSearchResult, String>("Author"));
    }
    public ScreenSearchPanel() throws SQLException {}

    public void clickButtonSearch() {
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

    public void clickButtonCancel() {
        stageSceneSearch.close();
    }
/********************************************************************************
*                              Other Metods                                     *
* ******************************************************************************/
    public void setStageAndOther(Stage stageSceneSearch){
        this.stageSceneSearch = stageSceneSearch;
    }
}
