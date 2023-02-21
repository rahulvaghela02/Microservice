package com.employee.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "employeeInformation")
public class Employee {

    @Id
    private long id;
    private String firstName;
    private String lastName;
    private String email;


}
