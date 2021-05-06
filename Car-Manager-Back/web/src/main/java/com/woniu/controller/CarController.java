package com.woniu.controller;

import com.woniu.domain.Car;
import com.woniu.po.CarPo;
import com.woniu.service.CarService;
import com.woniu.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class CarController {

    @Autowired
    private CarService carService;
    @RequestMapping("/car/add")
    public int add(@RequestBody CarPo carPo){
        return carService.add(carPo);

    }
    @RequestMapping("/car/delete")
    public int delete(CarPo carPo){
        return carService.delete(carPo);

    }
    @RequestMapping("/car/update")
    public int update(@RequestBody CarPo carPo){
        return carService.update(carPo);

    }
    @RequestMapping("/car/list")
    public ResponseResult<List<CarPo>> list(Integer pageIndex, String searchText){
        List<CarPo> cars = carService.carList();
        return new ResponseResult<List<CarPo>>(cars);
    }
}
