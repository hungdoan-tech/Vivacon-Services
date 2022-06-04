package com.vivacon.entity;

import com.vivacon.entity.enum_type.SettingType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
public class Setting {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "setting_id_generator")
    @SequenceGenerator(name = "setting_id_generator", sequenceName = "setting_id_seq", allocationSize = 1)
    private String id;

    @ManyToOne(targetEntity = Account.class)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    private SettingType type;

    private String value;
}
