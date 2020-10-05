package com.edu.agh.easist.easistserver.repositories;

import com.edu.agh.easist.easistserver.models.Foo;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IFooRepository extends PagingAndSortingRepository<Foo, Long> {
}