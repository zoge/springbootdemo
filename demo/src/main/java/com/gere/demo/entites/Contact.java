package com.gere.demo.entites;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Contact")
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c"),
    @NamedQuery(name = "Contact.findById", query = "SELECT c FROM Contact c WHERE c.id = :id"),
    @NamedQuery(name = "Contact.findByContactType", query = "SELECT c FROM Contact c WHERE c.contactType = :contactType"),
    @NamedQuery(name = "Contact.findByContactValue", query = "SELECT c FROM Contact c WHERE c.contactValue = :contactValue"),
    @NamedQuery(name = "Contact.findByCreatedAt", query = "SELECT c FROM Contact c WHERE c.createdAt = :createdAt")})
@Getter @Setter @Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Contact extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    @EqualsAndHashCode.Include
    private Integer id;
    @Column(name = "ContactType")
    private String contactType;
    @Basic(optional = false)
    @Column(name = "ContactValue")
    private String contactValue;
    @JoinColumn(name = "AddressId", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Address address;
}
