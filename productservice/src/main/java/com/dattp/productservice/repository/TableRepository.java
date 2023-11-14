package com.dattp.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dattp.productservice.entity.TableE;

public interface TableRepository extends JpaRepository<TableE,Long>{
}