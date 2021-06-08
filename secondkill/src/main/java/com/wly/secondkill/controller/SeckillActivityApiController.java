package com.wly.secondkill.controller;

import com.wly.secondkill.db.dao.SeckillActivityDao;
import com.wly.secondkill.db.po.SeckillActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin("http://localhost:4200")
@Controller
@RequestMapping("/api")
public class SeckillActivityApiController {

    @Autowired
    SeckillActivityDao seckillActivityDao;

    @RequestMapping("/seckills")
    @ResponseBody
    List<SeckillActivity> getProducts() {
        return seckillActivityDao.querySeckillActivitysByStatus(1);
    }

}
