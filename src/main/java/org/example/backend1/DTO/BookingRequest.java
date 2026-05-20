package org.example.backend1.DTO;

import lombok.Data;


@Data
public class BookingRequest {

    private String startDate;
    private String endDate;
    private boolean isDoubleRoom;
    private Long customer;
}