        <template>
            <div>
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: 'homePage' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>基础设置</el-breadcrumb-item>
                <el-breadcrumb-item>驾驶员档案</el-breadcrumb-item>
              </el-breadcrumb>

              <br/>

              <el-row :gutter="20">
                <el-col :span="6">
                  <el-input placeholder="请输入内容" v-model="name" class="input-with-select">
                    <el-button slot="append" icon="el-icon-search"></el-button>
                  </el-input>
                </el-col>
                <el-col :span="18">
                  <el-button type="danger">批量删除</el-button>
                  <el-button type="primary" @click="addDriver">新增</el-button>
                </el-col>
              </el-row>
              <br/>

              <template>
                <el-table :data="driverData" style="width: 100%" height="470">
                  <el-table-column fixed prop="employee.name" label="姓名" width="100"></el-table-column>
                  <el-table-column  prop="employee.deptId" label="部门" width="100" ></el-table-column>
                  <el-table-column  prop="employee.gender" label="性别" width="100" >
                      <template slot-scope="scope">{{ scope.row.employee.gender=='0'?'男':'女'}}</template>
                  </el-table-column>
                  <el-table-column  prop="employee.telephone" label="电话" width="100" ></el-table-column>
                  <el-table-column  prop="employee.entryDate" label="入职时间" width="180" ></el-table-column>
                  <el-table-column  prop="driverNum" label="驾照号码" width="180" ></el-table-column>
                  <el-table-column  prop="type" label="驾照类型" width="100" ></el-table-column>  
                  <el-table-column  prop="status" label="是否启用" width="100" >
                      <template slot-scope="scope">{{ scope.row.status=='1'?'启用':'禁用'}}</template>    
                  </el-table-column> 
                  <el-table-column   label="操作" width="150" >
                    
                      <template slot-scope="scoped">
                          <el-button type="primary" circle plain
                          @click="showDriverDialog(scoped.row)"
                          >查看详细</el-button>
                      </template>
                  </el-table-column>               
                </el-table>
              </template>

              <br/>

              <el-row>
                <el-col :span="10" :push="6">
                  <el-pagination
                    background
                    layout="prev, pager, next"
                    :total="1000">
                  </el-pagination>
                </el-col>
              </el-row>
         
              <!-- 新增 -->
              <el-dialog title="添加驾驶员" :visible.sync="addDialogFormVisible" 
                :rules='rules' label-width="100px" ref = "toAddDriver">
                  <el-form :model="driver">
                    <el-form-item label="姓名" prop="employee.name" >
                         <el-input v-model="driver.employee.name" @blur="dname($event)"></el-input>
                    </el-form-item> 

                     <el-form-item label="部门" prop="deptId">
                      <el-input v-model="driver.employee.deptId" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="生日" prop="birthday">
                      <el-input v-model="driver.employee.birthday" readonly="true"></el-input>
                      </el-form-item>
                     <el-form-item label="身份证" prop="idCard">
                      <el-input v-model="driver.employee.idCard" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="电话" prop="telephone">
                      <el-input v-model="driver.employee.telephone" readonly="true"></el-input>
                      </el-form-item>
                       <el-form-item label="入职时间" prop="entryDate">
                      <el-input v-model="driver.employee.entryDate" readonly="true"></el-input>
                      </el-form-item>
                       <el-form-item label="地址" prop="address">
                      <el-input v-model="driver.employee.address" readonly="true"></el-input>
                      </el-form-item> 
                      <el-form-item label="驾照号码" prop="driverNum">
                      <el-input v-model="driver.driverNum" ></el-input>
                      </el-form-item>
                      <el-form-item label="驾照类型" prop="type">
                      <el-input v-model="driver.type" ></el-input>
                      </el-form-item>
                      <el-form-item label="备注" prop="remarks">
                      <el-input v-model="driver.remarks" ></el-input>
                      </el-form-item>
                      <el-form-item label="状态" prop="status">
                      <el-switch v-model="driver.status"></el-switch>
                      </el-form-item>   
                  </el-form> 
                  <div slot="footer" class="dialog-footer">
                      <el-button @click="addDialogFormVisible = false">取 消</el-button>
                      <el-button type="primary" @click="toAddDriver()">确 定</el-button>
                  </div>


              </el-dialog>



              <!-- 查看详细 -->
               <el-dialog title="驾驶员详细信息" :visible.sync="dialogshowDriverVisible">
                  <el-form :model="showDriver" label-width="80px">
                      <el-form-item label="姓名" prop="name">
                      <el-input v-model="showDriver.employee.name" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="部门" prop="deptId">
                      <el-input v-model="showDriver.employee.deptId" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="生日" prop="birthday">
                      <el-input v-model="showDriver.employee.birthday" readonly="true"></el-input>
                      </el-form-item>
                     <el-form-item label="身份证" prop="idCard">
                      <el-input v-model="showDriver.employee.idCard" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="电话" prop="telephone">
                      <el-input v-model="showDriver.employee.telephone" readonly="true"></el-input>
                      </el-form-item>
                       <el-form-item label="入职时间" prop="entryDate">
                      <el-input v-model="showDriver.employee.entryDate" readonly="true"></el-input>
                      </el-form-item>
                       <el-form-item label="地址" prop="address">
                      <el-input v-model="showDriver.employee.address" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="驾照号码" prop="driverNum">
                      <el-input v-model="showDriver.driverNum" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="驾照类型" prop="type">
                      <el-input v-model="showDriver.type" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="备注" prop="remarks">
                      <el-input v-model="showDriver.remarks" readonly="true"></el-input>
                      </el-form-item>
                      <el-form-item label="状态" prop="starus">
                      <el-switch v-model="showDriver.status"></el-switch>

                      </el-form-item>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogshowDriverVisible = false">取 消</el-button>
                  </div>
              </el-dialog>
            </div>
          </template>


<script>
export default {

    data(){
        return{
            driverData:[{
                driverNum:'',
                type:'',
                status:'',
                remarks:'',
                employee:{name:'',deptId:'',gender:'',telephone:"",entryDate:"",address:'',birthday:'',idCard:''}
            }],
            driver:{
                driverNum:'',
                type:'',
                status:'',
                remarks:'',
                employee:{name:'',deptId:'',gender:'',telephone:"",entryDate:"",address:'',birthday:'',idCard:''}
            },
            employees:[],
            rules: {},
            addDialogFormVisible:false,

            dialogshowDriverVisible:false,
            showDriver:{
                driverNum:'',
                type:'',
                status:'',
                employee:{name:'',deptId:'',gender:'',telephone:"",entryDate:"",address:'',birthday:'',idCard:''}
            }
        }
    },
    methods:{
        
        loadDriver(){
             this.$axios.get("driver/list").then(r=>{
                console.log(r)
                this.driverData=r.data;

            })
        },
         addDriver(){
          this.addDialogFormVisible=true;
          // this.$axios.get("driver/getName" ).then(r=>{
          //       console.log(r)
          //       this.employees=r.data.data;

          //   })


         },

         dname(event){
            let name = event.target.value;
            // alert(name);
          this.$axios.get("driver/dname?name="+name).then(r=>{
                console.log(r)
                if(r.data.code==200){
                this.driver.employee=r.data.data;
                }else{
                  alert(r.data.msg)
                }
            })


         },
         toAddDriver(){

            if(this.driver.status){
                this.driver.status = 1;
            }else{
                this.driver.status = 0;
            }
                this.$axios.post("driver/addDriver",this.driver).then(r=>{
                console.log(r)
                this.addDialogFormVisible=false;
                this.loadDriver();

            })
         },

        showDriverDialog(row){
          alert(1223)
         this.dialogshowDriverVisible=true;
          this.showDriver.employee.name=row.employee.name;
          this.showDriver.employee.deptId=row.employee.deptId;
          this.showDriver.employee.gender=row.employee.gender;
          this.showDriver.employee.telephone=row.employee.telephone;
          this.showDriver.employee.entryDate=row.employee.entryDate;
         this.showDriver.employee.address=row.employee.address;
         this.showDriver.employee.birthday=row.employee.birthday;
         this.showDriver.employee.idCard=row.employee.idCard;
         this.showDriver.driverNum=row.driverNum;
         this.showDriver.type=row.type;
         this.showDriver.remarks=row.remarks;
         this.showDriver.status=row.status;
        }
    },
    created(){
       this.loadDriver();

    }
    
}
</script>


<style>

</style>
