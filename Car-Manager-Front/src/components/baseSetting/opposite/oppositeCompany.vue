<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>基础设置</el-breadcrumb-item>
      <el-breadcrumb-item>往来单位</el-breadcrumb-item>
    </el-breadcrumb>

    <br/>
    <br/>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input placeholder="输入车牌号查询" v-model="searchText" class="input-with-select">
          <el-button slot="append" icon="el-icon-search" @click="oppolist()"></el-button>
        </el-input>
      </el-col>
      <el-col :span="18">
        <el-button type="primary" @click="showEditoppo()">新增</el-button>
      </el-col>
    </el-row>
    <br/>
    <br/>
    <template>

      <el-table :data="oppos" border style="width: 100%" max-height="377"
                :header-cell-style="{background:'#eef1f6',color:'#606266'}">
        <el-table-column fixed prop="id" label="编号" min-width>

        </el-table-column>

        <el-table-column prop="name" label="单位名称" min-width>

        </el-table-column>

        <el-table-column prop="typename" label="单位类型" min-width>


        </el-table-column>

        <el-table-column prop="phone" label="单位电话" min-width>

        </el-table-column>

        <el-table-column prop="linkName" label="联系人" min-width>

        </el-table-column>

        <el-table-column prop="status" label="是否启用" min-width>
          <template slot-scope="scope">{{ scope.row.status == '1' ? '启用' : '禁用' }}</template>

        </el-table-column>

        <el-table-column prop="" label="操作" width="170">
          <template slot-scope="scope">
            <!-- element -ui  button   plain属性，鼠标放上去变蓝色  size="mini" -->
            <el-button icon="el-icon-search" circle plain @click="detail(scope.row.id,scope.row.type)">


            </el-button>

            <el-button type="primary" icon="el-icon-edit" circle plain
                       @click="goupdate(scope.row.id,scope.row.type)"
            ></el-button>
            <el-button type="danger" icon="el-icon-delete" circle plain
                       @click="del(scope.row.id)"
            ></el-button>
          </template>


        </el-table-column>

      </el-table>

    </template>

    <br/>
    <br/>
    <br/>

        <!--分页组件-->
    <el-row>
      <el-col :span="10" :push="6">
        <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="p"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="size"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
        </el-pagination>
      </el-col>
    </el-row>


    <!-- 添加往来单位信息 -->
    <el-dialog title="新增往来单位信息" :visible.sync="oppoVisible">
      <el-form :model="editoppo" label-width="80px" :rules="rules" ref="addForm">
        <el-form-item label="单位名称" prop="name">

          <el-input v-model="editoppo.name" @focus="addressdow()"></el-input>

        </el-form-item>

        <el-form-item label="单位类型" prop="type">
          <el-select v-model="editoppo.type" placeholder="请选择" @focus="addressdow()">
            <el-option :label="types.text" :value="types.id"
                       v-for="types in types" :key="types.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="地址" prop="address" >

          <el-col :span="10" >
              <el-input v-model="editoppo.address" @focus="addressshow()"  id='address' ></el-input>
          </el-col>

          <el-col :span="6" style="display:none; margin-left:40px"  id="addbutton">
           <el-button plain @click="luxian()" >规划驾车路线</el-button>
          </el-col>

          <el-col :span="6" style="display:none;"  id="addmapbutton">
            <el-button plain @click="addressdow()" >关闭地图</el-button>
          </el-col>
          
          
        </el-form-item>


          
          <div id="container" style="display:none;"></div>
        <div class="input-item" style="display:none;">
          <div class="input-item-prepend"><span class="input-item-text">经纬度</span></div>
          <input id='lnglat' type="text" >
        </div>






        <el-form-item label="电话" prop="phone">
          <el-input v-model="editoppo.phone" @focus="addressdow()"></el-input>
        </el-form-item>

        <el-form-item label="联系人" prop="linkName">
          <el-input v-model="editoppo.linkName" @focus="addressdow()"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remarks">
          <el-input v-model="editoppo.remarks" @focus="addressdow()"></el-input>
        </el-form-item>

        <el-form-item label="是否锁定" prop="status">
          <!-- element-ui 的 开关 switch -->
          <el-switch v-model="editoppo.status" @focus="addressdow()"></el-switch>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="outoppo('addForm')">取 消</el-button>
        <el-button type="primary" @click="addoppo('addForm')">确 定</el-button>
      </div>
    </el-dialog>





        <!-- 查看详细 -->
    <el-dialog title="往来单位详细信息" :visible.sync="oppodetail" center width="80%">
      <el-form :model="details" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="单位名：" prop="name">
              <el-input v-model="details.name" readonly="readonly" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">

            <el-form-item label="单位类型" prop="typee">
              <el-input  v-model="typee" readonly="readonly" disabled></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="8">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="details.phone" readonly="readonly" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="联系人" prop="linkName">
              <el-input v-model="details.linkName" readonly="readonly" disabled></el-input>
            </el-form-item>
          </el-col>


          <el-col :span="8">
            <el-form-item label="备注" prop="remarks">
              <el-input v-model="details.remarks" readonly="readonly" disabled></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="状态" prop="status">
              <el-input v-model="details.status" readonly="readonly" disabled>
              </el-input>

            </el-form-item>
          </el-col>


          <el-col :span="14">
            <el-form-item label="单位地址" prop="address">
              <el-input v-model="details.address" readonly="readonly" disabled>
              </el-input>

            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="oppodetail = false">关 闭</el-button>
      </div>
    </el-dialog>






    <!-- 修改往来单位信息 -->
    <el-dialog title="修改往来单位信息" :visible.sync="oppoupdate">
      <el-form :model="updates" label-width="80px" :rules="rulesupdate" ref="updateForm">
        <el-form-item label="单位名称" prop="name">

          <el-input v-model="updates.name" @focus="updateressdow()"></el-input>

        </el-form-item>

        <el-form-item label="单位类型" prop="type">
          <el-select v-model="updates.type" placeholder="请选择" :disabled="true" @focus="updateressdow()">

            <el-option :label="types.text" :value="types.id"
                       v-for="types in types" :key="types.id">
            </el-option>
          </el-select>
        </el-form-item>




        <el-form-item label="地址" prop="address">
         <el-col :span="10" >
               <el-input v-model="updates.address"  @focus="updateressshow()" id='address1'></el-input>
          </el-col>

           <el-col :span="6" style="display:none;  margin-left:20px" id="updatebutton">
           <el-button plain @click="updateluxian()">规划驾车路线</el-button>
          </el-col>

          <el-col :span="6" style="display:none;"  id="updatemapbutton">
           <el-button plain @click="updateressdow()">关闭地图</el-button>
          </el-col>
        </el-form-item>

        <div id="container1" style="display:none;"></div>

        <div class="input-item" style="display:none;">
        <div class="input-item-prepend"><span class="input-item-text">经纬度</span></div>
        <input id='lnglat1' type="text" >
        </div>






        <el-form-item label="电话" prop="phone">
          <el-input v-model="updates.phone" @focus="updateressdow()"></el-input>
        </el-form-item>

        <el-form-item label="联系人" prop="linkName">
          <el-input v-model="updates.linkName" @focus="updateressdow()"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remarks">
          <el-input v-model="updates.remarks" @focus="updateressdow()"></el-input>
        </el-form-item>

        <el-form-item label="是否锁定" prop="status">
          <!-- element-ui 的 开关 switch -->
          <el-switch v-model="updates.status" @focus="updateressdow()"></el-switch>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button    @click="outupdateoppo('updateForm')">取 消</el-button>
        <el-button type="primary" @click="update('updateForm')">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {

      map:null,
      mapObj:null,

     updatemap:null,
      //添加表单验证
      rules:{
        name: [
            { required: true, message: '请输入单位名称', trigger: 'blur' },
            { min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur' }
          ],
        type: [
            { required: true, message: '请选择单位类型', trigger: 'change' }
          ],
        // address: [
        //     { required: true, message: '请输入单位地址或点击地图', trigger: 'blur' },           
        //   ],
        phone: [
            { required: true, message: '请输入联系电话', trigger: 'blur' },
            { min: 5, max: 14, message: '长度在 5 到 14个字符', trigger: 'blur' }
          ],
        linkName: [
            { required: true, message: '请输入联系人', trigger: 'blur' },
            { min: 5, max: 10, message: '长度在 2 到 10个字符', trigger: 'blur' }
          ],

      },
      rulesupdate:{
        name: [
            { required: true, message: '请输入单位名称', trigger: 'blur' },
            { min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur' }
          ],
        type: [
            { required: true, message: '请选择单位类型', trigger: 'change' }
          ],
        // address: [
        //     { required: true, message: '请输入单位地址或点击地图', trigger: 'blur' },           
        //   ],
        phone: [
            { required: true, message: '请输入联系电话', trigger: 'blur' },
            { min: 5, max: 14, message: '长度在 5 到 14个字符', trigger: 'blur' }
          ],
        linkName: [
            { required: true, message: '请输入联系人', trigger: 'blur' },
            { min: 5, max: 10, message: '长度在 2 到 10个字符', trigger: 'blur' }
          ],

      },

      oppos: [
        {
          id: "",
          name: "",
          type: "",
          address: "",
          phone: "",
          linkName: "",
          remarks: "",
          status: "",
        }
      ],
       //分页
      searchText: '',
      p: 1,
      addDialogFormVisible: false,
      Employee: {},
      total: 0,
      size: 5,
      //控制添加对话框是否显示
      oppoVisible: false,
      //详细信息对话框
      oppodetail: false,
      //修改对话框
      oppoupdate: false,

      //新增往来单位
      editoppo: {
        name: "",
        type: "",
        address: "",
        phone: "",
        linkName: "",
        remarks: "",
        status: "",
      },

      types: [],
      typee:"",

      //详细信息
      details: {
        name: "",
        typename: "",
        address: "",
        phone: "",
        linkName: "",
        remarks: "",
        status: "",
      },
      //修改数据
      updates: {
        id: "",
        name: "",
        typename: "",
        address: "",
        phone: "",
        linkName: "",
        remarks: "",
        status: "",
      }


    }
  },
  methods: {
    oppolist() {
      this.$axios.get("opposite/list", {
        params: {
          p: this.p,
          searchText: this.searchText,
          size: this.size
        }
      }).then(r => {

        this.oppos = r.data.data.list
        this.total = r.data.data.total

      })

    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.oppolist();
    },
    handleSizeChange(val) {
      this.size = val;
      this.oppolist();
    },




    //显示新增对话框
    showEditoppo() {
      this.oppoVisible = true;//显示新增对话框

     //地图
      window.onLoad  = function(){
              var map = new AMap.Map('container', {
                  zoom:11,//级别
                  //center: [114.31, 30.52],//武汉坐标
                  viewMode:'3D'//使用3D视图
              });
              this.map = map;

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              map.addControl(toolbar);


              //var map = new AMap.Map('container');
              map.plugin('AMap.Geolocation', function () {
                var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              map.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container');//返回定位信息
             // AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
            })
            //this.mapObj = mapObj;

    var marker = new AMap.Marker();

    function regeoCode() {

        var lnglat  = document.getElementById('lnglat').value.split(',');
        //alert(lnglat)
        map.add(marker);
        marker.setPosition(lnglat);

       geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                var address = result.regeocode.formattedAddress;
                document.getElementById('address').value = address;

            }

        });
    }

 map.on('click',function(e){
     document.getElementById('lnglat').value = e.lnglat;
     regeoCode();
    
 })

 document.getElementById('lnglat').onkeydown = function(e) {
     if (e.keyCode === 13) {
         regeoCode();
         return false;
     }
     return true;
 }


  }

                var key = "ba9419462964ab1cb3aea92b4d1d12e6"
                var url = `https://webapi.amap.com/maps?v=1.4.15&key=${key}&callback=onLoad&plugin=AMap.ToolBar,AMap.Driving,AMap.Geolocation`;
                var jsapi = document.createElement('script');
                jsapi.charset = 'utf-8';
                jsapi.src = url;
                document.head.appendChild(jsapi);

                //点击地图回显地址
      var geocoder = new AMap.Geocoder({
        //city: "010", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
    });







      this.$axios.post("dictionary/menu").then(r => {

        this.types = r.data.data[3].children
      })
    },
    //地址失去焦点事件
     //地址
    luxian(){

            this.map = new AMap.Map('container', {
                  zoom:11,//级别
                  //center: [114.31, 30.52],//武汉坐标
                  viewMode:'3D'//使用3D视图
              });
              //this.map = new AMap.Map('container');
            var thismap = this.map;
             thismap.plugin('AMap.Geolocation', function () {
                 var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: false,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              thismap.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container');//返回定位信息
              //AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
              })

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              thismap.addControl(toolbar);




       //点击地图回显地址
      var geocoder = new AMap.Geocoder({
        //city: "010", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
    });

      var marker = new AMap.Marker();

    function regeoCode() {

        var lnglat  = document.getElementById('lnglat').value.split(',');
        //alert(lnglat)
        thismap.add(marker);
        marker.setPosition(lnglat);

       geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                var address = result.regeocode.formattedAddress;
                document.getElementById('address').value = address;

            }

        });
    }

                  //驾车路线规划
                  var driving = new AMap.Driving({
                  // 驾车路线规划策略，AMap.DrivingPolicy.LEAST_TIME是最快捷模式
                  policy: AMap.DrivingPolicy.LEAST_TIME,
                  map:this.map,
                })
                //alert(this.editoppo.address)
                var thisaddress = document.getElementById('address').value
                var points = [//路线规划起点终点
                   {keyword: '蜗牛学院',city:'武汉'},
                    {keyword: thisaddress,city:'武汉'}
                ]
                driving.search(points, function (status, result) {
                // 未出错时，result即是对应的路线规划方案

                })

 thismap.on('click',function(e){
     document.getElementById('lnglat').value = e.lnglat;
     regeoCode();
    
 })

 document.getElementById('lnglat').onkeydown = function(e) {
     if (e.keyCode === 13) {
         regeoCode();
         return false;
     }
     return true;
 }




    },



    //添加往来单位
    addoppo(addForm) {
      this.$refs[addForm].validate((valid) => {
          if (valid) {
           if (this.editoppo.status) {
        this.editoppo.status = '0'
      } else {
        this.editoppo.status = '1'
      }
      this.editoppo.address = document.getElementById('address').value;
      this.$axios.post("opposite/add", this.editoppo).then(r => {

        if (r.data.code == 200) {
          this.$message({type: 'success', message: "添加成功", duration: 800});
          this.oppoVisible = false;
          //获得焦点显示地图div
          document.getElementById('container').style.display="none";
          //循环清空editoppo集合中的值
          for (var i in this.editoppo) {
            this.editoppo[i] = "";
          }

          //重新加载页面
          this.oppolist();
        } else {
          this.$message({type: 'success', message: "添加失败", duration: 800});
        }
      })
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      
    },
    //取消添加框
    outoppo(addForm){
        this.oppoVisible = false;
        //获得焦点显示地图div
        document.getElementById('container').style.display="none";
        this.$refs[addForm].resetFields();

    },
    //取消修改框
    outupdateoppo(updateForm){
      this.oppoupdate = false;
      //获得焦点显示地图div
        document.getElementById('container1').style.display="none";

        this.$refs[updateForm].resetFields();
    },

    //往来单位详情
    detail(id, type) {

      this.oppodetail = true;
      this.$axios.post("opposite/findbyid?id=" + id).then(r => {

        this.details = r.data.data
        if (this.details.status == 1) {
          this.details.status = "启用";
        } else {
          this.details.status = "禁用";
        }
      })
       this.$axios.post("dictionary/menu").then(r => {

        this.types = r.data.data[3].children
            for (var i in this.types) {
            if(this.types[i].id==type){
              this.typee = this.types[i].text
            }
          }

      })
    },




    //打开修改对话框
    goupdate(id, type) {
      this.oppoupdate = true;


           //地图
      window.onLoad  = function(){
              var map = new AMap.Map('container1', {
                  zoom:11,//级别
                  //center: [114.31, 30.52],//武汉坐标
                  viewMode:'3D'//使用3D视图
              });
              this.mapupdate = map;

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              map.addControl(toolbar);


              //var map = new AMap.Map('container');
              map.plugin('AMap.Geolocation', function () {
                var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: true,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              map.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container1');//返回定位信息
             // AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
            })
            


  }

  

                var key = "ba9419462964ab1cb3aea92b4d1d12e6"
                var url = `https://webapi.amap.com/maps?v=1.4.15&key=${key}&callback=onLoad&plugin=AMap.ToolBar,AMap.Driving,AMap.Geolocation`;
                var jsapi = document.createElement('script');
                jsapi.charset = 'utf-8';
                jsapi.src = url;
                document.head.appendChild(jsapi);







      this.$axios.post("opposite/findbyid?id=" + id).then(r => {

        this.updates = r.data.data
        if (this.updates.status == 0) {
          this.updates.status = true;
        } else {
          this.updates.status = false
        }
      })
       this.$axios.post("dictionary/menu").then(r => {

        this.types = r.data.data[3].children

      })
    },
        //修改路线失去焦点触发
    updateluxian(){
         this.updatemap = new AMap.Map('container1', {
                  zoom:11,//级别
                  viewMode:'3D'//使用3D视图
              });
          //this.updates.address = document.getElementById('address1').value


            var thismap = this.updatemap;
             thismap.plugin('AMap.Geolocation', function () {
                 var geolocation = new AMap.Geolocation({
                  enableHighAccuracy: true,//是否使用高精度定位，默认:true
                  timeout: 10000000,          //超过10秒后停止定位，默认：无穷大
                  maximumAge: 0,           //定位结果缓存0毫秒，默认：0
                  convert: true,           //自动偏移坐标，偏移后的坐标为高德坐标，默认：true
                  showButton: true,        //显示定位按钮，默认：true
                  buttonPosition: 'RB',    //定位按钮停靠位置，默认：'LB'，左下角
                  buttonOffset: new AMap.Pixel(10, 20),//定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
                  showMarker: true,        //定位成功后在定位到的位置显示点标记，默认：true
                  showCircle: true,        //定位成功后用圆圈表示定位精度范围，默认：true
                  panToLocation: false,     //定位成功后将定位到的位置作为地图中心点，默认：true
                  zoomToAccuracy:true      //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
              });
              thismap.addControl(geolocation);
              geolocation.getCurrentPosition();
              AMap.event.addListener(geolocation, 'container1');//返回定位信息
              //AMap.event.addListener(geolocation, 'error', onError);      //返回定位出错信息
              })

              //初始化toolbar插件
              var toolbar = new AMap.ToolBar();
              //调用插件
              thismap.addControl(toolbar);


       //点击地图回显地址
      var geocoder = new AMap.Geocoder({
        //city: "010", //城市设为北京，默认：“全国”
        radius: 1000 //范围，默认：500
    });

      var marker = new AMap.Marker();

    function regeoCode() {

        var lnglat  = document.getElementById('lnglat1').value.split(',');
        //alert(lnglat)
        thismap.add(marker);
        marker.setPosition(lnglat);

       geocoder.getAddress(lnglat, function(status, result) {
            if (status === 'complete'&&result.regeocode) {
                var address = result.regeocode.formattedAddress;

                document.getElementById('address1').value = address;

            }

        });
    }

                  //驾车路线规划
                  var driving = new AMap.Driving({
                  // 驾车路线规划策略，AMap.DrivingPolicy.LEAST_TIME是最快捷模式
                  policy: AMap.DrivingPolicy.LEAST_TIME,
                  map:this.updatemap,
                })
                
                var thisaddress = document.getElementById('address1').value
                var points = [//路线规划起点终点
                   {keyword: '蜗牛学院',city:'武汉'},
                    {keyword: thisaddress,city:'武汉'}
                ]
                driving.search(points, function (status, result) {
                // 未出错时，result即是对应的路线规划方案

                })

      thismap.on('click',function(e){
        document.getElementById('lnglat1').value = e.lnglat;
        regeoCode();

 })

 document.getElementById('lnglat1').onkeydown = function(e) {
     if (e.keyCode === 13) {
         regeoCode();
         return false;
     }
     return true;
 }

    },

    //修改
    update(updateForm) {
      this.$refs[updateForm].validate((valid) => {
          if (valid) {
                //获得焦点关闭地图div
              document.getElementById('container1').style.display="none";
              //关闭规划路线按钮
              document.getElementById('updatebutton').style.display="none";

              document.getElementById('updatemapbutton').style.display="none";
            this.updates.address = document.getElementById('address1').value

            if (this.updates.status) {
              this.updates.status = '0'
            } else {
              this.updates.status = '1'
            }
            this.$axios.post("opposite/update", this.updates).then(r => {
              console.log(r)
              if (r.data.code == 200) {
                this.$message({type: 'success', message: "修改成功", duration: 800});
                this.oppoupdate = false;
                //循环清空editoppo集合中的值
                for (var i in this.updates) {
                  this.updates[i] = "";
                }
                //重新加载页面
                this.oppolist();
              } else {
                this.$message({type: 'success', message: "修改失败", duration: 800});
              }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });

      

    },

    //删除往来单位信息
    del(id) {
      this.$confirm("此操作将删除该账户,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.post("opposite/delete?id=" + id).then(r => {
          console.log(r)
          if (r.data.code == 200) {
            this.$message({type: 'success', message: "删除成功", duration: 800});
            //重新加载页面
            this.oppolist();
          } else {
            this.$message({type: 'success', message: "删除失败", duration: 800});
          }
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },
     //控制地图显示
    addressshow(){
        //获得焦点显示地图div
        document.getElementById('container').style.display="block";
        //显示规划路线按钮
        document.getElementById('addbutton').style.display="block";
        document.getElementById('addmapbutton').style.display="block";
    },
    //控制地图关闭
    addressdow(){
        //获得焦点关闭地图div
        document.getElementById('container').style.display="none";
         //关闭规划路线按钮
        document.getElementById('addbutton').style.display="none";
        //关闭关闭按钮
        document.getElementById('addmapbutton').style.display="none";
    },
    updateressshow(){
         //获得焦点显示地图div
        document.getElementById('container1').style.display="block";
        //显示规划路线按钮
        document.getElementById('updatebutton').style.display="block";

        document.getElementById('updatemapbutton').style.display="block";
    },
    updateressdow(){
      //获得焦点关闭地图div
        document.getElementById('container1').style.display="none";
         //关闭规划路线按钮
        document.getElementById('updatebutton').style.display="none";

        document.getElementById('updatemapbutton').style.display="none";
    },
  },
  created() {
    this.oppolist()
  }


}
</script>

<style>
#container{
    width: 600px;
    height: 300px;
    margin-left:78px;
    margin-bottom: 20px;
}
#container1{
    width: 600px;
    height: 300px;
    margin-left:78px;
    margin-bottom: 20px;
}


</style>