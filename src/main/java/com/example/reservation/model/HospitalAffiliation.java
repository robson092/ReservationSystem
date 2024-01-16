package com.example.reservation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "hospital_affiliation")
@Getter @Setter
@RequiredArgsConstructor
public class HospitalAffiliation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "hospital_name")
    private String hospitalName;

    private String city;

    private String street;

    @Column(name = "time_slot_per_client_in_min")
    private int timeSlotPerClientInMinute;

    @ManyToOne(fetch = FetchType.LAZY)
    private Doctor doctor;
}
