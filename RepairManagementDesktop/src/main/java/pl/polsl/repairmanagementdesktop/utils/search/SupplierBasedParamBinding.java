package pl.polsl.repairmanagementdesktop.utils.search;

import java.util.function.Supplier;

public class SupplierBasedParamBinding implements ParamBinding {

    private final Supplier<String> valueSupplier;
    private final String param;

    public SupplierBasedParamBinding( String param, Supplier<String> valueSupplier) {
        this.valueSupplier = valueSupplier;
        this.param = param;
    }


    @Override
    public String bind() {
        var value = valueSupplier.get();
        return "&" + param + "=" + (value != null ? value : "");
    }
}
