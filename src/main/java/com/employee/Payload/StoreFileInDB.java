package com.employee.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreFileInDB {

    private String Filename;

    private String FileType;

    private String FileSize;

    private byte[] File;

}
