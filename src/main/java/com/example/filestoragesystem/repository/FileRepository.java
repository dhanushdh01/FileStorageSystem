package com.example.filestoragesystem.repository;

import com.example.filestoragesystem.entity.File;
import com.example.filestoragesystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    List<File> findAllByOwner(User user);
}
