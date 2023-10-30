package com.xapp.jpa.pagination.dto;

import com.xapp.jpa.pagination.common.EmployeeType;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class EmployeeDTO {

    private long id;

    @NonNull
    private String name;

    @NonNull
    private String dept;
    private Long salary;

    private EmployeeType type;

}