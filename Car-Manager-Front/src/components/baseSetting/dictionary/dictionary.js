export default {
  name: "dictionary",
  data() {
    return {
      searchText: '',
      p: 1,
      tableData: [],
      addDialogFormVisible: false,
      updateDialogFormVisible: false,
      Dictionary: {},
      total: 0,
      size: 5,
      level: '一级',
      one: false,//二级菜单
      two: false,//三级菜单
      isDisable: true,//是否启用
      children: [],
      oneId: '',
      twoId: '',
      childrentow: [],
      parentId: '',
      updateDictionary: {
        id: '',
        text: '',
        parentId: '',
        level:'',
        isDisable: ''
      },
      updateisDisable: true,
    }
  },
  methods: {
    add() {
      this.addDialogFormVisible = true;
      this.$axios.get("dictionary/menu").then(r => {
        this.children = r.data.data
      })
    },
    onemenu(id) {
      for (let i = 0; i < this.children.length; i++)
        if (this.children[i].id == id) {
          this.childrentow = this.children[i].children;
        }
      this.twoId = this.childrentow[0].id
    },
    addRole() {
      if (this.two) {
        this.parentId = this.twoId;
      } else if (this.one) {
        this.parentId = this.oneId;
      } else {
        this.parentId = 0;
      }
      if (this.level == '一级') {
        this.Dictionary.level = 1;
      } else if (this.level == '二级') {
        this.Dictionary.level = 2;
      } else {
        this.Dictionary.level = 3;
      }
      if (this.isDisable) {
        this.Dictionary.isDisable = 1;
      } else {
        this.Dictionary.isDisable = 0;
      }
      this.Dictionary.parentId = this.parentId;
      this.$axios.get("dictionary/add", {params: this.Dictionary}).then(r => {
        console.log(r)
        if (r.data.code = 200) {
          this.$message.success("添加成功");
          this.addDialogFormVisible = false;
          this.findDictionary();
        }
      })
    },
    doUpdate(row) {
      this.updateDialogFormVisible = true;
      this.updateDictionary.id = row.id;
      this.updateDictionary.text = row.text;
      this.updateDictionary.parentId = row.parentId;
      this.updateDictionary.level = row.level;
      this.updateDictionary.isDisable = row.isDisable;
      if (this.updateDictionary.isDisable == 1) {
        this.updateisDisable = true;
      } else {
        this.updateisDisable = false;
      }
    },
    update() {
      if (this.updateisDisable == true) {
        this.updateDictionary.isDisable = 1;
      } else {
        this.updateDictionary.isDisable = 0;
      }
      this.$axios.get("dictionary/update", {params: this.updateDictionary}).then(r => {
        if (r.data.code == 200) {
          this.updateDialogFormVisible = false;
          this.$message.success("修改成功");
          this.findDictionary();
        }
      })
    },
    del(row) {
      this.$confirm("此操作将删除该字典,是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(res => {
        this.$axios.get("dictionary/delete", {params: {id: row.id}}).then(r => {
          if (r.data.code != 200) {
            this.$message.error(r.data.msg);
          } else {
            this.$message.success("删除成功");
            this.findDictionary();
          }
        })
      }).catch(res => {
        this.$message.info("取消删除");
      })
    },
    agreeChange(label) {
      if (label == '一级') {
        this.one = false;
        this.two = false;
      } else if (label == '二级') {
        this.one = true;
        this.two = false;
      } else if (label == '三级') {
        this.one = true;
        this.two = true;
      }
    },
    findDictionary() {
      this.$axios.get("dictionary/list", {
        params: {
          p: this.p,
          searchText: this.searchText,
          size: this.size
        }
      }).then(r => {
        this.tableData = r.data.data.list
        this.total = r.data.data.total
      })
    },
    //分页方法
    handleCurrentChange(val) {
      this.p = val;
      this.findDictionary();
    },
    handleSizeChange(val) {
      this.size = val;
      this.findDictionary();
    },
  },
  created() {
    this.findDictionary();
  }
}
