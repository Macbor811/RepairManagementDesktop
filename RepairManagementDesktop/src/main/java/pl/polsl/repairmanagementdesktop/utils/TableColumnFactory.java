package pl.polsl.repairmanagementdesktop.utils;

import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class TableColumnFactory {

    public static <S, T> TableColumn<S, T> createColumn(String text, String property){
        TableColumn<S, T> column = new TableColumn<>(text);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        //column.setPrefWidth(Control.USE_COMPUTED_SIZE);
        return column;
    }
}
