package vn.tdsoftware.hrm_backend.dao;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDAO {
    List<String> getPermissionByRole(String role);
    void deleteAllById(int id);
    void deleteCustomById(int id);
}
