package com.example.reservation.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "hospital_affiliations")
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToMany(mappedBy = "hospitalAffiliations")
    private Set<Doctor> doctors;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HospitalAffiliation)) return false;
        HospitalAffiliation that = (HospitalAffiliation) o;
        return Objects.equals(hospitalName, that.hospitalName) && Objects.equals(city, that.city) && Objects.equals(street, that.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hospitalName, city, street);
    }
}
