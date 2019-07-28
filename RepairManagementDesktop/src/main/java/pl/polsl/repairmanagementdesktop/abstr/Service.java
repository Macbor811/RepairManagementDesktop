package pl.polsl.repairmanagementdesktop.abstr;

import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Page;


public interface Service<E extends Entity> {

    void save(E entity);

    E findById(String id);

    Page<E> findAll(int page, int size);

    Page<E> findAllMatching(SearchQuery query, int page, int size);
}