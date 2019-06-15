package pl.polsl.repairmanagementdesktop.utils.search;

        import javafx.scene.control.DatePicker;
        import javafx.scene.control.TextField;
        import pl.polsl.repairmanagementdesktop.utils.search.ParamBinding;

public class DatePickerParamBinding implements ParamBinding {


    private final DatePicker datePicker;
    private final String queryParam;

    public DatePickerParamBinding(DatePicker datePicker, String queryParam) {
        this.datePicker = datePicker;
        this.queryParam = queryParam;
    }

    @Override
    public String toString() {
        return this.bind();
    }

    @Override
    public String bind() {
        if (!datePicker.getValue().toString().isEmpty()) {
            return "&" + queryParam + "=" + datePicker.getValue().toString();
        } else {
            return "";
        }
    }
}
