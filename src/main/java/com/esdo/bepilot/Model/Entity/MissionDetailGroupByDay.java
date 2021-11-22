package com.esdo.bepilot.Model.Entity;


import java.math.BigDecimal;
import java.sql.Timestamp;

public interface MissionDetailGroupByDay {
    Timestamp getTime() ;
    int getTotalMission() ;
    BigDecimal getTotalMoney() ;

}
