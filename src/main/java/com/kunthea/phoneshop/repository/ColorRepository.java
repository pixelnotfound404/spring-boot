package com.kunthea.phoneshop.repository;

import com.kunthea.phoneshop.entity.Colors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;

@Repository
public interface ColorRepository extends JpaRepository<Colors,Integer> {

}
