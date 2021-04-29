package com.woniu.controller;


import com.woniu.po.Car;
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
    public int add(Car car){
        return carService.add(car);

    }
    @RequestMapping("/car/delete")
    public int delete(Car car){
        return carService.delete(car);

    }
    @RequestMapping("/car/update")
    public int update(Car car){
        return carService.update(car);

    }
    @RequestMapping("/car/list")
    public List list(){
        List<Car> cars = carService.carList();
        return cars;
    }

}
