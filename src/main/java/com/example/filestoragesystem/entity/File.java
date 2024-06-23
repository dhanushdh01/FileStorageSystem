package com.example.filestoragesystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Entity
@Table(name = "files")
@Data
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String fileType;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;

    public InputStream getDataAsStream() {
        return new ByteArrayInputStream(data);
    }
}
