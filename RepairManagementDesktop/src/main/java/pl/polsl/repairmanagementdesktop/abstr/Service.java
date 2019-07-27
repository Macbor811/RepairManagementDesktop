package pl.polsl.repairmanagementdesktop.abstr;

import pl.polsl.repairmanagementdesktop.utils.search.SearchQuery;
import uk.co.blackpepper.bowman.Page;


public interface Service<E extends Entity> {

    public void save(E Employee);

    public E findById(String id);

    public Page<E> findAll(int page, int size);

    public Page<E> findAllMatching(SearchQuery query, int page, int size);
}