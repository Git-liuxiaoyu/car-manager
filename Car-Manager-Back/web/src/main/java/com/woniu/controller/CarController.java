package com.woniu.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.woniu.domain.Car;
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
@RequestMapping("/car")
public class CarController {
    @Autowired
    private CarService carService;

    @RequestMapping("/list")
    public ResponseResult<PageInfo<Car>> list(Integer p, String searchText, Integer size) {
        int pageIndex=1;
        int pageSize=5;
        if(p>=1){
            pageIndex=p;
        }
        if(pageSize>=5){
            pageSize=size;
        }
        Integer total=carService.count(searchText);
        List<Car> carList = carService.list(searchText,pageIndex,pageSize);
        PageInfo<Car> pageInfo = new PageInfo<>(carList);
        System.out.println(carList);
        pageInfo.setTotal(total);
        return new ResponseResult<>(pageInfo);
    }

    @RequestMapping("/add")
    public ResponseResult add(@RequestBody Car car){
        carService.add(car);
        return ResponseResult.SUCCESS;
    }

    @RequestMapping("/update")
    public ResponseResult updated(@RequestBody Car car){
        carService.update(car);
        return ResponseResult.SUCCESS;
    }
    @RequestMapping("/delete")
    public ResponseResult delete(Integer id){
        carService.delete(id);
        return ResponseResult.SUCCESS;
    }
    @RequestMapping("/getAll")
    public ResponseResult<List<Car>>getAll(){
        List<Car> allCars = carService.getAll();
        return new ResponseResult<>(allCars);

    }

}
