package com.fred.repository;

import com.fred.entities.Manager;
import org.apache.ibatis.annotations.Mapper;

/**
 * @auther fred
 * @create 2021-04-19 15:52
 */
@Mapper
public interface ManagerDao {

    Integer createManager(Manager manager);

    Integer countManager(Manager manager);
}
