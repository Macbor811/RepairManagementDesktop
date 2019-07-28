package pl.polsl.repairmanagementdesktop.abstr;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.utils.TextFormatterFactory;
import pl.polsl.repairmanagementdesktop.utils.search.ParamBinding;
import pl.polsl.repairmanagementdesktop.utils.search.UriSearchQuery;
import uk.co.blackpepper.bowman.Page;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class TabController<E extends Entity, T extends TableRow> {


    private static final Integer DEFAULT_ROWS_PER_PAGE = 20;
    protected final Service<E> service;
    protected final UriSearchQuery uriSearchQuery = new UriSearchQuery();

    @FXML
    protected TableView<T> tableView;

    private Vector<List<T>> currentResources = new Vector<>();

    @Autowired
    ThreadPoolExecutor executor;
    @FXML
    protected Pagination pagination;
    @FXML
    protected TextField rowsPerPageTextField;

    @FXML
    protected ProgressIndicator progressIndicator;

    private Integer rowsPerPage = DEFAULT_ROWS_PER_PAGE;
    private boolean isUpdating = false;
    private boolean isInitialized = false;
    private boolean shouldUpdate = false;

    protected TabController(Service<E> service, Function<E, T> tableRowSupplier) {
        this.service = service;
        this.tableRowSupplier = tableRowSupplier;
        currentResources.setSize(1);
    }

    protected abstract void initTableView();

    protected abstract void initQueryFields();

    public void addParamBindings(ParamBinding... bindings) {
        for (var binding : bindings) {
            uriSearchQuery.getBindings().add(binding);
        }
        uriSearchQuery.update();
    }

    private void initPagination() {
        rowsPerPageTextField.setText(DEFAULT_ROWS_PER_PAGE.toString());
        pagination.setMaxPageIndicatorCount(10);
        pagination.setPageCount(1);
        pagination.setPageFactory(this::createPage);
        rowsPerPageTextField.setTextFormatter(TextFormatterFactory.numericTextFormatter());
    }

    //The parameter and return value are required by pagination control, but not needed in this case.
    private Node createPage(int pageIndex) {
        updateTable();

        return new Pane();
    }


    /**
     * Updates search settings from text fields to show new results.
     */
    @FXML
    protected void showButtonClicked() {

        if (!isUpdating) {
            progressIndicator.setVisible(true);
            executor.submit(() -> {
                rowsPerPage = Integer.valueOf(rowsPerPageTextField.getText());
                uriSearchQuery.update();

                currentResources.clear();
                Page<E> firstPage = service.findAllMatching(uriSearchQuery, 0, rowsPerPage);
                currentResources.setSize(firstPage.getTotalPages() > 0 ? (int) firstPage.getTotalPages() : 1);

                currentResources.set(0, firstPage.getResources().stream().map(tableRowSupplier).collect(Collectors.toList()));
                Platform.runLater(() -> {
                    shouldUpdate = false;
                    pagination.setPageCount((int) firstPage.getTotalPages());
                    shouldUpdate = true;
                    updateTable();
                });
            });
        }
    }

    private final Function<E, T> tableRowSupplier;

    protected void updateTable() {
        if (!isUpdating && shouldUpdate) {
            shouldUpdate = false;
            System.out.println("Updating table");
            tableView.getItems().clear();
            isUpdating = true;
            int currentIndex = pagination.getCurrentPageIndex();
            var page = currentResources.get(currentIndex);
            if (page == null) {
                currentResources.set(currentIndex, service
                        .findAllMatching(uriSearchQuery, currentIndex, rowsPerPage)
                        .getResources().stream().map(tableRowSupplier)
                        .collect(Collectors.toList())
                );
                page = currentResources.get(currentIndex);
            }
            var resourcesIt = page.iterator();
            if (resourcesIt.hasNext()) {
                tableView.getItems().add(resourcesIt.next());
                executor.submit(() -> {
                    try {
                        resourcesIt.forEachRemaining(entity -> Platform.runLater(() -> {
                            tableView.getItems().add(entity);
                        }));
                    } catch (ResourceAccessException e) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Connection error");
                        errorAlert.setContentText(e.getMessage());
                        errorAlert.show();
                    } finally {
                        isUpdating = false;
                        shouldUpdate = true;
                        progressIndicator.setVisible(false);
                    }

                });
            }
        }
    }

    public void initView() {
        if (!isInitialized) {
            shouldUpdate = true;
            initTableView();
            initQueryFields();
            initPagination();
            isInitialized = true;
        }
    }
}
