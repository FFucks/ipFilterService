package com.ffucks.ipfilter.models;

import jakarta.persistence.*;

@Entity
public class IpFilterModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "sourceIp")
    private String sourceIp;

    @Column(name = "destinationIp")
    private String destinationIp;
    @Column(name = "rule")
    private String rule;

    public IpFilterModel() {

    }

    public IpFilterModel(Long id, String name, String sourceIp, String destinationIp, String rule) {
        this.id = id;
        this.name = name;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.rule = rule;
    }

    public IpFilterModel(String name, String sourceIp, String destinationIp, String rule) {
        this.name = name;
        this.sourceIp = sourceIp;
        this.destinationIp = destinationIp;
        this.rule = rule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getDestinationIp() {
        return destinationIp;
    }

    public void setDestinationIp(String destinationIp) {
        this.destinationIp = destinationIp;
    }
}
