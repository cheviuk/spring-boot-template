package com.template.repo;

import com.template.dto.Role;
import com.template.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    List<UserEntity> findAllByRole(Role role);
    List<UserEntity> findByIdInOrUsernameIn(List<Long> idList, List<String> usernameList);

    @Query(value = "SELECT CAST(deleteAllByIdsAndNames(?1, ?2) AS TEXT)", nativeQuery = true)
    String deleteByIdInOrUsernameIn(Long[] idList, String[] usernameList);

    List<UserEntity> findAllByRoleIn(List<Role> roleList);
}
