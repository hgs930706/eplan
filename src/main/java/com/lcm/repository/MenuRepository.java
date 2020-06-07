package com.lcm.repository;

import com.lcm.domain.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, String> {

    @EntityGraph(attributePaths = {"childMenu"})
    Menu findOneByFunctionId(String id);

    @EntityGraph(attributePaths = {"childMenu"})
    List<Menu> findByParentMenuIsNullOrderByDisplaySeq();

    @EntityGraph(attributePaths = {"childMenu"})
    List<Menu> findByLanguageAndActiveFlagAndParentMenuIsNullOrderByDisplaySeq(String language,String flag);


}
