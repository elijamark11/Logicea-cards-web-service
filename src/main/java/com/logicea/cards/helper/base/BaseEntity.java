package com.logicea.cards.helper.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logicea.cards.entities.users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;


@MappedSuperclass
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "created_on")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdOn;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "updated_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @JoinColumn(name = "created_by", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User createdByLink;

    @JoinColumn(name = "updated_by", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User updatedByLink;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    @JsonIgnore
    public User getCreatedByLink() {
        return createdByLink;
    }

    public void setCreatedByLink(User createdByLink) {
        this.createdByLink = createdByLink;
    }


    @JsonIgnore
    public User getUpdatedByLink() {
        return updatedByLink;
    }

    public void setUpdatedByLink(User updatedByLink) {
        this.updatedByLink = updatedByLink;
    }

    public BaseEntity createdOn(Long userId) {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Africa/Nairobi"));
        var date = Date.from(dateTime
                .atZone(ZoneId.of("Africa/Nairobi"))
                .toInstant());
        this.updatedOn = date;
        this.createdOn = date;
        this.updatedBy = userId;
        this.createdBy = userId;
        return this;
    }

    public BaseEntity updatedOn(Long userId) {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Africa/Nairobi"));
        var date = Date.from(dateTime
                .atZone(ZoneId.of("Africa/Nairobi"))
                .toInstant());
        this.updatedOn = date;
        this.updatedBy = userId;
        return this;
    }
}