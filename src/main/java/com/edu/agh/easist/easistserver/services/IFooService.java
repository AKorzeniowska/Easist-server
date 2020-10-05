package com.edu.agh.easist.easistserver.services;

import com.edu.agh.easist.easistserver.models.Foo;

import java.util.Optional;

public interface IFooService {
    Optional<Foo> findById(Long id);

    Foo save(Foo foo);

    Iterable<Foo> findAll();

}