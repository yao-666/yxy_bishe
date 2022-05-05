<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择订单状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.neuq_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="售后状态" prop="afterStatus">
        <el-select v-model="queryParams.afterStatus" placeholder="请选择售后状态" clearable size="small">
          <el-option
            v-for="dict in dict.type.neuq_after_state"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类名称" prop="className">
        <el-input
          v-model="queryParams.className"
          placeholder="请输入分类名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单次数量" prop="singleNumber">
        <el-input
          v-model="queryParams.singleNumber"
          placeholder="请输入单次数量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="姓名" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入姓名"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input
          v-model="queryParams.address"
          placeholder="请输入地址"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手机号码" prop="phone">
        <el-input
          v-model="queryParams.phone"
          placeholder="请输入手机号码"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="配送方式" prop="deliveryMode">
        <el-input
          v-model="queryParams.deliveryMode"
          placeholder="请输入配送方式"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="自提地点" prop="pickUpSite">
        <el-input
          v-model="queryParams.pickUpSite"
          placeholder="请输入自提地点"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['neuqer:orderItemBuffer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['neuqer:orderItemBuffer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['neuqer:orderItemBuffer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['neuqer:orderItemBuffer:import']"
        >导入(校验)</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['neuqer:orderItemBuffer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderItemBufferList" @selection-change="handleSelectionChange" stripe border>
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="产品名称" align="center" prop="productName" width="200px"/>
      <el-table-column label="分类名称" align="center" prop="className" />
      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="数量" align="center" prop="amount">
      </el-table-column>
      <el-table-column label="小计" align="center" prop="subtotal" />
      <el-table-column label="单次数量" align="center" prop="singleNumber" />
      <el-table-column label="总数量" align="center" prop="totalQuantity" />
      <el-table-column label="已取数量" align="center" prop="takeNumber" />
      <el-table-column label="订单备注" align="center" prop="note" width="150px" />
      <el-table-column label="姓名" align="center" prop="name" />
      <el-table-column label="地址" align="center" prop="address" />
      <el-table-column label="手机号码" align="center" prop="phone" />
      <el-table-column label="给卖家留言" align="center" prop="buyerMessage" width="150px"/>
      <el-table-column label="配送方式" align="center" prop="deliveryMode" />
      <el-table-column label="预计送达时间" align="center" prop="estimatedTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.estimatedTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="自提地点" align="center" prop="pickUpSite" width="150px" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['neuqer:orderItemBuffer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['neuqer:orderItemBuffer:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单明细导入缓冲区对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="form.orderStatus" placeholder="请选择订单状态">
            <el-option
              v-for="dict in dict.type.neuq_order_status"
              :key="dict.value"
              :label="dict.label"
:value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="产品名称" prop="productName">
          <el-input v-model="form.productName" placeholder="请输入产品名称" />
        </el-form-item>
        <el-form-item label="分类名称" prop="className">
          <el-input v-model="form.className" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="购物选项" prop="shoppingOptions">
          <el-input v-model="form.shoppingOptions" placeholder="请输入购物选项" />
        </el-form-item>
        <el-form-item label="单价" prop="unitPrice">
          <el-input v-model="form.unitPrice" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="数量" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="小计" prop="subtotal">
          <el-input v-model="form.subtotal" placeholder="请输入小计" />
        </el-form-item>
        <el-form-item label="单次数量" prop="singleNumber">
          <el-input v-model="form.singleNumber" placeholder="请输入单次数量" />
        </el-form-item>
        <el-form-item label="总数量" prop="totalQuantity">
          <el-input v-model="form.totalQuantity" placeholder="请输入总数量" />
        </el-form-item>
        <el-form-item label="已取数量" prop="takeNumber">
          <el-input v-model="form.takeNumber" placeholder="请输入已取数量" />
        </el-form-item>
        <el-form-item label="订单备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入订单备注" />
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="form.name" placeholder="请输入姓名" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入手机号码" />
        </el-form-item>
        <el-form-item label="给卖家留言" prop="buyerMessage">
          <el-input v-model="form.buyerMessage" placeholder="请输入给卖家留言" />
        </el-form-item>
        <el-form-item label="配送方式" prop="deliveryMode">
          <el-input v-model="form.deliveryMode" placeholder="请输入配送方式" />
        </el-form-item>
        <el-form-item label="取货地址" prop="pickUpAddress">
          <el-input v-model="form.pickUpAddress" placeholder="请输入取货地址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 用户导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listOrderItemBuffer, getOrderItemBuffer, delOrderItemBuffer, addOrderItemBuffer, updateOrderItemBuffer } from "@/api/neuqer/orderItemBuffer";
import { getToken } from '@/utils/auth'

export default {
  name: "OrderItemBuffer",
  dicts: ['neuq_after_state', 'neuq_order_status'],
  data() {
    return {
      // 按钮loading
      buttonLoading: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单明细导入缓冲区表格数据
      orderItemBufferList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,


      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/neuqer/orderItemBuffer/importData"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderSn: undefined,
        groupId: undefined,
        orderStatus: undefined,
        afterStatus: undefined,
        productName: undefined,
        className: undefined,
        shoppingOptions: undefined,
        unitPrice: undefined,
        amount: undefined,
        subtotal: undefined,
        singleNumber: undefined,
        totalQuantity: undefined,
        takeNumber: undefined,
        refundAmount: undefined,
        memberDiscount: undefined,
        note: undefined,
        memberAccount: undefined,
        memberName: undefined,
        additionForm: undefined,
        name: undefined,
        address: undefined,
        phone: undefined,
        buyerMessage: undefined,
        deliveryMode: undefined,
        deliveryPrice: undefined,
        deliveryNum: undefined,
        deliveryTime: undefined,
        estimatedTime: undefined,
        pickUpAddress: undefined,
        pickUpSite: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        id: [
          { required: true, message: "主键不能为空", trigger: "blur" }
        ],
        orderSn: [
          { required: true, message: "订单编号不能为空", trigger: "blur" }
        ],
        groupId: [
          { required: true, message: "拼单团单号不能为空", trigger: "blur" }
        ],
        orderStatus: [
          { required: true, message: "订单状态(0=待发货，1=待自提，2=已发货，3=已自提，4=售后中，5=交易完成)不能为空", trigger: "change" }
        ],
        afterStatus: [
          { required: true, message: "售后状态(0=申请退款，1=申请退货，2=退款完成，3=退货成功)不能为空", trigger: "change" }
        ],
        productName: [
          { required: true, message: "产品名称不能为空", trigger: "blur" }
        ],
        className: [
          { required: true, message: "分类名称不能为空", trigger: "blur" }
        ],
        shoppingOptions: [
          { required: true, message: "购物选项不能为空", trigger: "blur" }
        ],
        unitPrice: [
          { required: true, message: "单价不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ],
        subtotal: [
          { required: true, message: "小计不能为空", trigger: "blur" }
        ],
        singleNumber: [
          { required: true, message: "单次数量不能为空", trigger: "blur" }
        ],
        totalQuantity: [
          { required: true, message: "总数量不能为空", trigger: "blur" }
        ],
        takeNumber: [
          { required: true, message: "已取数量不能为空", trigger: "blur" }
        ],
        refundAmount: [
          { required: true, message: "退款金额不能为空", trigger: "blur" }
        ],
        memberDiscount: [
          { required: true, message: "会员折扣金额不能为空", trigger: "blur" }
        ],
        note: [
          { required: true, message: "订单备注不能为空", trigger: "blur" }
        ],
        memberAccount: [
          { required: true, message: "会员账号不能为空", trigger: "blur" }
        ],
        memberName: [
          { required: true, message: "会员姓名不能为空", trigger: "blur" }
        ],
        additionForm: [
          { required: true, message: "附加表单不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "姓名不能为空", trigger: "blur" }
        ],
        address: [
          { required: true, message: "地址不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "手机号码不能为空", trigger: "blur" }
        ],
        buyerMessage: [
          { required: true, message: "给卖家留言不能为空", trigger: "blur" }
        ],
        deliveryMode: [
          { required: true, message: "配送方式(0=自提，1=同城配送)不能为空", trigger: "blur" }
        ],
        deliveryPrice: [
          { required: true, message: "配送价格/运费不能为空", trigger: "blur" }
        ],
        deliveryNum: [
          { required: true, message: "运单编号不能为空", trigger: "blur" }
        ],
        deliveryTime: [
          { required: true, message: "发货时间不能为空", trigger: "blur" }
        ],
        estimatedTime: [
          { required: true, message: "预计送达时间不能为空", trigger: "blur" }
        ],
        pickUpAddress: [
          { required: true, message: "取货地址不能为空", trigger: "blur" }
        ],
        pickUpSite: [
          { required: true, message: "自提地点不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单明细导入缓冲区列表 */
    getList() {
      this.loading = true;
      listOrderItemBuffer(this.queryParams).then(response => {
        this.orderItemBufferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        orderSn: undefined,
        groupId: undefined,
        orderStatus: undefined,
        afterStatus: undefined,
        productName: undefined,
        className: undefined,
        shoppingOptions: undefined,
        unitPrice: undefined,
        amount: undefined,
        subtotal: undefined,
        singleNumber: undefined,
        totalQuantity: undefined,
        takeNumber: undefined,
        refundAmount: undefined,
        memberDiscount: undefined,
        note: undefined,
        memberAccount: undefined,
        memberName: undefined,
        additionForm: undefined,
        name: undefined,
        address: undefined,
        phone: undefined,
        buyerMessage: undefined,
        deliveryMode: undefined,
        deliveryPrice: undefined,
        deliveryNum: undefined,
        deliveryTime: undefined,
        estimatedTime: undefined,
        pickUpAddress: undefined,
        pickUpSite: undefined,
        delFlag: undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单明细导入缓冲区";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.loading = true;
      this.reset();
      console.log(row)
      const id = row.id || this.ids
      getOrderItemBuffer(id).then(response => {
        this.loading = false;
        this.form = response.data;
        this.open = true;
        this.title = "修改订单明细导入缓冲区";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.buttonLoading = true;
          if (this.form.id != null) {
            updateOrderItemBuffer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          } else {
            addOrderItemBuffer(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            }).finally(() => {
              this.buttonLoading = false;
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除订单明细导入缓冲区编号为"' + ids + '"的数据项？').then(() => {
        this.loading = true;
        return delOrderItemBuffer(ids);
      }).then(() => {
        this.loading = false;
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).finally(() => {
        this.loading = false;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('neuqer/orderItemBuffer/export', {
        ...this.queryParams
      }, `orderItemBuffer_${new Date().getTime()}.xlsx`)
    },


    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    }

  }
};
</script>
