package com.codepar.bussinesclientsv1.domains.client.infrastructure.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper=false)
@Table(name = "CLIENT")
public class ClientEntity extends Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CLIENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLIENT_GEN")
    @SequenceGenerator(name = "CLIENT_GEN", sequenceName = "CP_CLIENT_GEN_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "STATUS")
    private boolean status;
}
