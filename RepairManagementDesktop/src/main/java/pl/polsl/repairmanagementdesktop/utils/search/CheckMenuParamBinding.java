package pl.polsl.repairmanagementdesktop.utils.search;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;

import java.util.StringJoiner;

public class CheckMenuParamBinding implements ParamBinding{

    private final MenuButton menuButton;
    private final String param;

    public CheckMenuParamBinding(MenuButton menuButton, String param){
        this.menuButton = menuButton;
        this.param = param;
    }


    @Override
    public String bind() {
        var joiner = new StringJoiner( "&" + param + "=", "&" + param + "=", "");
        joiner.setEmptyValue("");
        for (var item : menuButton.getItems().filtered(it -> it instanceof CheckMenuItem)){
            var checkItem = (CheckMenuItem) item;
            if (checkItem.isSelected()){
                joiner.add(checkItem.getText());
            }
        }
        return joiner.toString();
    }
}
