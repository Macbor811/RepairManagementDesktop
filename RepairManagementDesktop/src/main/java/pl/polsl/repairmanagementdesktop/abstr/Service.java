package pl.polsl.repairmanagementdesktop.abstr;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Page;

@ControllerAdvice
public interface Service<E extends Entity> {

    void save(E entity);

    E findById(String id);

    Page<E> findAll(int page, int size);

    Page<E> findAllMatching(SearchQuery query, int page, int size);

    @ExceptionHandler(ResourceAccessException.class)
    default void handleConnectionError(){
        System.out.println("handled");
    }
}