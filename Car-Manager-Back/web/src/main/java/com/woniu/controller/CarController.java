package com.woniu.controller;

import com.woniu.po.CarPo;
import com.woniu.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarService carService;

    @RequestMapping("/car/add")
    public int add(CarPo carPo){
        return carService.add(carPo);

    }
    @RequestMapping("/car/delete")
    public int delete(CarPo carPo){
        return carService.delete(carPo);

    }
    @RequestMapping("/car/update")
    public int update(CarPo carPo){
        return carService.update(carPo);

    }
    @RequestMapping("/car/list")
    public List list(){
        List<CarPo> cars = carService.carList();
        return cars;
    }

}
