package pl.polsl.repairmanagementdesktop.utils.search;

public class ConstantParamBinding implements ParamBinding{

    private final String queryParam;
    private final String value;

    public ConstantParamBinding(String queryParam, String value) {
        this.queryParam = queryParam;
        this.value = value;
    }

    @Override
    public String bind() {
        return "&" + queryParam + "=" + (value != null ? value : "");
    }
}
