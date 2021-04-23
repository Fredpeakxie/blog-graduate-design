package com.fred.repository;

import com.fred.entities.Manager;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther fred
 * @create 2021-04-19 15:52
 */
@Mapper
public interface ManagerDao {

    Integer createManager(Manager manager);

    Integer countManager(Manager manager);

    Integer login(@Param("username") String username,@Param("password") String password);

    List<Manager> getManagers();

    void deleteManager(Integer managerId);
}
