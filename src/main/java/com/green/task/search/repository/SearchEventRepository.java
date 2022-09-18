package com.green.task.search.repository;


import com.green.task.search.entity.SearchEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchEventRepository extends JpaRepository<SearchEvent, Long>, SearchEventRepositoryWrapper {
}
