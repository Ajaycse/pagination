package com.xapp.jpa.pagination.entity;


import com.xapp.jpa.pagination.common.EmployeeType;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cms_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @Column(name = "employee_name")
    private String name;

    @NonNull
    private String dept;
    private Long salary;

    private EmployeeType type;

}
