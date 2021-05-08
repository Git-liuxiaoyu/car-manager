        <template>
            <div>
              <el-breadcrumb separator="/">
                <el-breadcrumb-item :to="{ path: 'index' }">首页</el-breadcrumb-item>
                <el-breadcrumb-item>基础设置</el-breadcrumb-item>
                <el-breadcrumb-item>驾驶员档案</el-breadcrumb-item>
              </el-breadcrumb>

              <br/>

              <el-row :gutter="20">
                <el-col :span="6">
                  <el-input placeholder="请输入内容" v-model="searchText" class="input-with-select">
                    <el-button slot="append" icon="el-icon-search" @click="loadDriver"></el-button>
                  </el-input>
                </el-col>
                <el-col :span="18">
                  <!-- <el-button type="danger">批量删除</el-button> -->
                  <el-button type="primary" @click="addDriver">新增</el-button>
                </el-col>
              </el-row>
              <br/>

              <template>
                <el-table :data="tableData" :key="tableData.id" style="width: 100%" height="470">
                  <el-table-column fixed prop="employee.name" label="姓名" width="100"></el-table-column>
                  <el-table-column  prop="deptName" label="部门" width="100" >
                  </el-table-column>
                  <el-table-column  prop="employee.gender" label="性别" width="100" >
                      <template slot-scope="scope">{{ scope.row.employee.gender=='0'?'男':'女'}}</template>
                  </el-table-column>
                  <el-table-column  prop="employee.telephone" label="电话" width="100" ></el-table-column>
                  <el-table-column  prop="employee.entryDate" label="入职时间" width="180" >
                        <template slot-scope="scope">
                            <i class="el-icon-time"></i>
                            {{ scope.row.employee.entryDate | dateConvert() }}
                        </template>
                  </el-table-column>
                  <el-table-column  prop="driverNum" label="驾照号码" width="180" ></el-table-column>
                  <el-table-column  prop="driverType" label="驾照类型" width="100" ></el-table-column>
                  <el-table-column  prop="status" label="是否启用" width="100" >
                      <template slot-scope="scope">{{ scope.row.status=='1'?'启用':'禁用'}}</template>
                  </el-table-column>
                  <el-table-column   label="操作" width="200" >

                      <template slot-scope="scoped">
                          <!-- <el-button type="primary" circle plain
                          @click="showDriverDialog(scoped.row)"
                          >查看详细</el-button> -->
                        <el-tooltip content="查看详细" placement="bottom" effect="light">
                          <el-button icon="el-icon-search" circle  @click="showDriverDialog(scoped.row)"></el-button>
                        </el-tooltip>
            
                        <el-tooltip content="编辑" placement="bottom" effect="light">
                        <el-button type="primary" icon="el-icon-edit" circle @click="updateDriver(scoped.row)"></el-button>
                        </el-tooltip>

                        <el-tooltip content="删除" placement="bottom" effect="light">
                        <el-button type="danger" icon="el-icon-delete" circle @click="deleteDriver(scoped.row.id)"></el-button>
                        </el-tooltip>

                      </template>
                  </el-table-column>
                </el-table>
              </template>

              <br/>

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

              <!-- 新增 -->
              <el-dialog title="添加驾驶员" :visible.sync="addDialogFormVisible"
                :rules='rules' label-width="100px" ref = "toAddDriver">
                  <el-form :model="driver">
                    <el-row :gutter="20">
                        <el-col :span="8">
                        <el-form-item label="姓名" prop="employee.name" >
                            <el-input v-model="driver.employee.name" @blur="dname($event)"></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="部门" prop="deptId">
                        <el-input v-model="driver.employee.deptId" readonly="readonly"></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="生日" prop="birthday">
                        <el-input v-model="driver.employee.birthday" readonly="readonly"></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="身份证" prop="idCard">
                        <el-input v-model="driver.employee.idCard" readonly="readonly"></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="电话" prop="telephone">
                        <el-input v-model="driver.employee.telephone" readonly="readonly"></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="入职时间" prop="entryDate">
                        <el-input v-model="driver.employee.entryDate" readonly="readonly"></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="地址" prop="address">
                        <el-input v-model="driver.employee.address" readonly="readonly"></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="驾照号码" prop="driverNum">
                        <el-input v-model="driver.driverNum" ></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="驾照类型" prop="type">
                        <el-input v-model="driver.type" ></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="备注" prop="remarks">
                        <el-input v-model="driver.remarks" ></el-input>
                        </el-form-item>
                        </el-col>

                        <el-col :span="8">
                        <el-form-item label="状态" prop="status">
                        <el-switch v-model="driver.status"></el-switch>
                        </el-form-item>
                        </el-col>
                    </el-row>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                      <el-button @click="addDialogFormVisible = false">取 消</el-button>
                      <el-button type="primary" @click="toAddDriver()">确 定</el-button>
                  </div>


              </el-dialog>



              <!-- 查看详细 -->
               <el-dialog title="驾驶员详细信息" :visible.sync="dialogshowDriverVisible" center width="80%"> 
                  <el-form :model="showDriver" label-width="80px">
                    <el-row :gutter="20">
                    <el-col :span="8">    
                      <el-form-item label="姓名" prop="name">
                      <el-input v-model="showDriver.employee.name" readonly="readonly"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8"> 
                      <el-form-item label="部门" prop="deptId">
                      <el-input v-model="showDriver.employee.deptId" readonly="readonly"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8"> 
                      <!-- <el-form-item label="生日" prop="birthday">
                      <el-input v-model="showDriver.employee.birthday" readonly="readonly"></el-input>
                      </el-form-item> -->

                     <el-form-item label="生日">
                      <el-date-picker
                        v-model="showDriver.employee.birthday"
                        align="right"
                        type="date"
                        placeholder="选择日期">
                      </el-date-picker>
                    </el-form-item>
                    </el-col>

                    <el-col :span="8"> 
                     <el-form-item label="身份证" prop="idCard">
                      <el-input v-model="showDriver.employee.idCard" readonly="readonly"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8">
                      <el-form-item label="电话" prop="telephone">
                      <el-input v-model="showDriver.employee.telephone" readonly="readonly"></el-input>
                      </el-form-item>
                    </el-col>

                     <el-col :span="8">
                       <!-- <el-form-item label="入职时间" prop="entryDate">
                      <el-input v-model="showDriver.employee.entryDate" readonly="readonly"></el-input>
                      </el-form-item> -->
                      
                    <el-form-item label="入职时间">
                      <el-date-picker
                        v-model="showDriver.employee.entryDate"
                        align="right"
                        type="date"
                        placeholder="选择日期">
                      </el-date-picker>
                    </el-form-item>
                     </el-col>

                      <el-col :span="8">
                       <el-form-item label="地址" prop="address">
                      <el-input v-model="showDriver.employee.address" readonly="readonly"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8">
                      <el-form-item label="驾照号码" prop="driverNum">
                      <el-input v-model="showDriver.driverNum" readonly="readonly"></el-input>
                      </el-form-item>
                    </el-col>

                     <el-col :span="8">
                      <el-form-item label="驾照类型" prop="type">
                      <el-input v-model="showDriver.type" readonly="readonly"></el-input>
                      </el-form-item>
                     </el-col>

                    <el-col :span="8">
                      <el-form-item label="备注" prop="remarks">
                      <el-input v-model="showDriver.remarks" readonly="readonly"></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8">
                      <el-form-item label="状态" prop="status">
                       <el-input  v-model="showDriver.status" readonly="readonly">
                       </el-input>
                      <!-- {{showDriver.status}} -->
                    </el-form-item>
                    </el-col>

                   </el-row>
                  </el-form>
                  <div slot="footer" class="dialog-footer">
                  <el-button @click="dialogshowDriverVisible = false">取 消</el-button>
                  </div>
              </el-dialog>

                   <!-- 修改 -->
                    <el-dialog title="编辑用户" :visible.sync="dialogEdiDriverVisible" center width="80%">
                    <el-form :model="showDriver" label-width="80px">
                    <el-row :gutter="20">
                    <el-col :span="8">    
                      <el-form-item label="姓名" prop="name">
                      <el-input v-model="showDriver.employee.name" ></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8"> 
                      <el-form-item label="部门" prop="deptId">
                      <el-input v-model="showDriver.employee.deptId" ></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8"> 
                      <!-- <el-form-item label="生日" prop="birthday">
                      <el-input type="date" v-model="showDriver.employee.birthday" readonly="readonly"></el-input>
                      </el-form-item> -->
                     <el-form-item label="生日">
                      <el-date-picker
                        v-model="showDriver.employee.birthday"
                        align="right"
                        type="date"
                        placeholder="选择日期">
                      </el-date-picker>
                    </el-form-item>
                    </el-col>

                    <el-col :span="8"> 
                     <el-form-item label="身份证" prop="idCard">
                      <el-input v-model="showDriver.employee.idCard" ></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8">
                      <el-form-item label="电话" prop="telephone">
                      <el-input v-model="showDriver.employee.telephone" ></el-input>
                      </el-form-item>
                    </el-col>

                     <el-col :span="8">
                       <!-- <el-form-item label="入职时间" prop="entryDate">
                      <el-input type="date" v-model="showDriver.employee.entryDate" readonly="readonly"></el-input>
                      </el-form-item> -->
                     <el-form-item label="入职时间">
                      <el-date-picker
                        v-model="showDriver.employee.entryDate"
                        align="right"
                        type="date"
                        placeholder="选择日期">
                      </el-date-picker>
                    </el-form-item>

                     </el-col>

                      <el-col :span="8">
                       <el-form-item label="地址" prop="address">
                      <el-input v-model="showDriver.employee.address" ></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8">
                      <el-form-item label="驾照号码" prop="driverNum">
                      <el-input v-model="showDriver.driverNum" ></el-input>
                      </el-form-item>
                    </el-col>

                     <el-col :span="8">
                      <el-form-item label="驾照类型" prop="type">
                      <el-input v-model="showDriver.type" ></el-input>
                      </el-form-item>
                     </el-col>

                    <el-col :span="8">
                      <el-form-item label="备注" prop="remarks">
                      <el-input v-model="showDriver.remarks" ></el-input>
                      </el-form-item>
                    </el-col>

                    <el-col :span="8">
                      <el-form-item label="状态" prop="status">
                       <el-input  v-model="showDriver.status" >
                       </el-input>
                      <!-- {{showDriver.status}} -->
                    </el-form-item>
                    </el-col>

                   </el-row>
                    </el-form>
                    <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogEdiDriverVisible = false">取 消</el-button>
                    <el-button type="primary" @click="toUpdateDriver()">确 定</el-button>
                    </div>
                </el-dialog>


            </div>
          </template>


<script>
export default {

    data(){
        return{
            searchText:'',
            p: 1,
            total: 5,
            size: 5,
            tableData:[
              // {
                // driverNum:'',
                // type:'',
                // status:'',
                // remarks:'',
                // deptName:'',
                // employee:{name:'',deptId: '' ,gender:'',telephone:"",entryDate:"",address:'',birthday:'',idCard:''}
            // }
            ],
            // 字典信息
            dictionary:[],
            depts:[],
            driverTypes:[],
            driver:{
                driverNum:'',
                type:'',
                status:'',
                remarks:'',
                employee:{name:'',deptId: '' ,gender:'',telephone:"",entryDate:"",address:'',birthday:'',idCard:''}
            },
            employees:[],
            rules: {},
            //添加框
            addDialogFormVisible:false,
            // 详细框
            dialogshowDriverVisible:false,
            showDriver:{
                driverNum:'',
                type:'',
                status:'',
                remarks:'',
                employee:{name:'',deptId: '' ,gender:'',telephone:"",entryDate:"",address:'',birthday:'',idCard:''}
            },
            // 修改框
            dialogEdiDriverVisible:false,

        }
    },
    methods:{

        loadDriver(){
             this.$axios.get("driver/list", {params: {p: this.p, searchText: this.searchText, size: this.size}}).then(r=>{
                this.tableData = r.data.data.list
                this.total = r.data.data.total
                // console.log(r)
                // this.driverData=r.data;
                  // console.log(this.driverData)
                   this.tableData.forEach(e1 => {
                      this.depts.forEach(e2=>{
                        if(e1.employee.deptId===e2.id){
                          e1.deptName = e2.text;
                        }
                      })
                      // this.driverTypes.forEach(e3=>{
                      //   if(e1.type===e3.id){
                      //     e1.driverType = e3.text;
                      //   }
                      // })


                    });

                  
            //  console.log(this.depts);
            //   console.log(this.depts);
            //     for(let i=0 ; i<this.depts.length; i++){
            //       for(let j=0;j<this.driverData.length;j++){
            //           if(this.driverData[j].employee.deptId === this.depts[i].id){
            //             this.driverData[j].deptName = this.depts[i].text;
            //           }
            //       }
            //     }

            });

            //   this.$axios.get("dictionary/menu").then(r => {
            //   this.dictionary = r.data.data;
            //   //获取部门信息
            //    console.log(this.dictionary);
            //    console.log(this.dictionary[0]);
            //   for (let i = 0; i < this.dictionary.length; i++) {
            //     if (this.dictionary[i].id == 1) {
            //       this.depts = this.dictionary[i].children;
                  
            //     }
            //   }

            //  console.log(this.depts);
            //   console.log(this.depts[0]);


            //   console.log(this.driverData[2]);
            // // ]]

            // });


        },

        //分页方法
        handleCurrentChange(val) {
        this.p = val;
        this.loadDriver();
        },
        handleSizeChange(val) {
        this.size = val;
        this.loadDriver();
        },
        // 字典
        loadDictionary() {

            this.$axios.get("dictionary/menu").then(r => {
              this.dictionary = r.data.data;
               console.log(this.dictionary)
              //获取部门信息
              for (let i = 0; i < this.dictionary.length; i++) {
                if (this.dictionary[i].id === 1) {
                  this.depts = this.dictionary[i].children;
                  
                }
              }

              for (let i = 0; i < this.dictionary.length; i++) {
                if (this.dictionary[i].id === 51) {
                  this.driverTypes = this.dictionary[i].children;
                  
                }
              }

            })
          },
         addDriver(){
          this.addDialogFormVisible=true;
          // this.$axios.get("driver/getName" ).then(r=>{
          //       console.log(r)
          //       this.employees=r.data.data;

          //   })


         },
         //获取驾驶员的员工信息
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
          
         this.dialogshowDriverVisible=true;
        //   this.showDriver.employee.name=row.employee.name;
        //   this.showDriver.employee.deptId=row.employee.deptId;
        //   this.showDriver.employee.gender=row.employee.gender;
        //   this.showDriver.employee.telephone=row.employee.telephone;
        //   this.showDriver.employee.entryDate=row.employee.entryDate;
        //  this.showDriver.employee.address=row.employee.address;
        //  this.showDriver.employee.birthday=row.employee.birthday;
        //  this.showDriver.employee.idCard=row.employee.idCard;
        //  this.showDriver.driverNum=row.driverNum;
        //  this.showDriver.type=row.type;
        //  this.showDriver.remarks=row.remarks;
        //  this.showDriver.status=row.status;
        this.showDriver=row
        if(this.showDriver.status==1){
            this.showDriver.status = "启动";
        }else{
            this.showDriver.status = "禁止";
        }
        
        },

        updateDriver(row){
           this.dialogEdiDriverVisible=true;
            this.showDriver=row
        },

        toUpdateDriver(){

        },
        deleteDriver(id){

        },
    },
    created(){
      
      this.loadDictionary();
      this.loadDriver();
      // this.loadDept();
    }

}
</script>


<style>

</style>
