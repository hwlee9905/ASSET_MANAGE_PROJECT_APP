package org.example.domain.history.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.example.types.Status;

import java.util.Date;

@Getter@Setter
public class Afterjsonhw {
    private Long assetidx;
    private String assettype;
    private String sn;
    private String dept;
    private String manufacturer;
    private String assetname;
    private Long hwidx;
    private String cpu;
    private String ssd;
    private String hdd;
    private String memory;
    private Status status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;
    private String usageduration;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date returndate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date assigneddate;
    private String currentuser;
    private String previoususer;
    private String location;
}
