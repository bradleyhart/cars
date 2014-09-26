package org.fazz.repository;


import org.fazz.search.CarSearch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarSearchRepository extends CrudRepository<CarSearch, Integer> {

}
