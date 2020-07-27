package com.qiluhospital.chemotherapy.miniapp.controller;

import com.qiluhospital.chemotherapy.miniapp.servise.impl.PatientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component//使spring管理
@EnableScheduling//定时任务注解
@Slf4j//打印日志
public class Timer {

    @Autowired
    private PatientServiceImpl patientService;

    /**
     * 每天凌晨00:01:00将昨天就诊的病人的就诊时间自动重置
     */
    @Scheduled(cron = "0 1 0 * * ?")
    public void attendRecordSave(){
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        calendar.add(calendar.DATE,-1);//把日期往前后退一天
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年M月d日");
        boolean res = patientService.autoUpdatePatVisitDate(sdf1.format(calendar.getTime()));
        if(res){
            log.info(sdf2.format(calendar.getTime())+"就诊时间自动重置执行成功");
        }else {
            log.info(sdf2.format(calendar.getTime())+"就诊时间自动重置执行失败");
        }
    }





}