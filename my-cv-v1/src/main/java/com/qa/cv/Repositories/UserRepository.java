package com.qa.cv.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qa.cv.Model.UsersDataModel;

@Repository
public interface UserRepository extends JpaRepository <UsersDataModel,Long>{

	Page<UsersDataModel> findByDepartmentId(Long departmentId, Pageable pageable);
	

}
