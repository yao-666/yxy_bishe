<template>
  <div class="app-container orderHome">
    <el-form class="headerForm" :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch"
             abel-width="68px">
      <el-form-item prop="orderSn">
        <div class="seachInput">
          <el-select v-model="headerSelect" placeholder="请选择">
            <el-option label="手机号码" value="1"></el-option>
            <el-option label="姓名" value="2"></el-option>
            <el-option label="产品名称" value="3"></el-option>
            <el-option label="订单编号" value="4"></el-option>
          </el-select>
          <el-input
            v-model="queryParams.orderSn"
            placeholder="请输入订单编号"
            size="small"
            clearable
            @keyup.enter.native="handleQuery"
          >
            <el-button class="seachButton" slot="append" icon="el-icon-search"></el-button>
          </el-input>


        </div>
      </el-form-item>
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
      <el-form-item label="订单类型">
        <el-select v-model="queryParams.orderType" placeholder="请选择订单类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.neuq_order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="配送方式" prop="shippingMethod">
        <el-select v-model="queryParams.shippingMethod" placeholder="请选择配送方式" clearable size="small">
          <el-option
            v-for="dict in dict.type.neuq_shipping_mode"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
      <el-form-item label="总数量" prop="totalQuantity">
        <el-input
          v-model="queryParams.totalQuantity"
          placeholder="请输入总数量"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="已取数量" prop="takeNumber">
        <el-input
          v-model="queryParams.takeNumber"
          placeholder="请输入已取数量"
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

    <div class="mid">
      <el-tabs v-model="activeName">
        <el-tab-pane label="所有订单" name="first"></el-tab-pane>
        <el-tab-pane label="待发货" name="second"></el-tab-pane>
        <el-tab-pane label="已发货" name="third"></el-tab-pane>
        <el-tab-pane label="交易完成" name="fourth"></el-tab-pane>
        <el-tab-pane label="售后中" name="five"></el-tab-pane>
        <el-tab-pane label="待付款" name="six"></el-tab-pane>
        <el-tab-pane label="交易关闭" name="seven"></el-tab-pane>
      </el-tabs>
      <el-row :gutter="8" class="mb8 tabsRow">
        <el-col :span="1.5">
          <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                     v-hasPermi="['neuqer:order:add']">新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-edit"
            size="mini"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['neuqer:order:edit']"
          >修改
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['neuqer:order:remove']"
          >删除
          </el-button>
        </el-col>
        <!--      导入功能-->
        <el-col :span="1.5">
          <el-button
            type="info"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            v-hasPermi="['neuqer:order:import']"
          >导入(校验)
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            v-hasPermi="['neuqer:order:export']"
          >导出
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
    </div>
    <div class="midBottom"></div>

    <div style="overflow:hidden;">

    </div>
    <el-table class="table" v-loading="loading" :data="orderList" @selection-change="handleSelectionChange" border
              :cell-style="{'padding':'0px'}" ref="refTable">
      <el-table-column type="selection" width="30" align="center"/>
      <el-table-column label="配送方式" align="center" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.neuq_shipping_mode" :value="scope.row.shippingMethod"/>
        </template>
      </el-table-column>
      <el-table-column label="实付金额" align="center" width="100">
        <template slot-scope="scope">
          <span style="font-size: 13px">￥{{scope.row.orderAmount}}</span>
        </template>
      </el-table-column>

      <el-table-column label="收货人信息" align="center" width="150">
        <template slot-scope="scope">
          <div>
            <div>
              <i style="font-size: 16px;padding-right: 8px" class="el-icon-postcard"></i>
              <span style="font-size: 13px;">{{scope.row.receiverName}}</span>
            </div>
            <div>
              <i style="font-size: 16px;padding-right: 8px" class="el-icon-mobile-phone"></i>
              <span style="font-size: 13px;">{{scope.row.receiverPhone}}</span>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="收货人地址" align="left" width="300">
        <template slot-scope="scope">
          <div class="tableNote">
            {{scope.row.receiverDetailAddress}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="订单状态" align="center" prop="orderStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.neuq_order_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="给卖家留言" align="center" width="250">
        <template slot-scope="scope">
          <!-- tips悬浮提示 -->
          <el-tooltip
            placement="top"
            v-model="scope.row.showTooltip"
            :open-delay="500"
            effect="light"
            :disabled="!scope.row.showTooltip">
            <div slot="content">{{ scope.row.buyerMessage }}</div>
            <div @mouseenter="showTips($event, scope.row)" class="tableNote">{{ scope.row.buyerMessage }}</div>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-tickets"
            @click="handleDetail(scope.row)"
            v-hasPermi="['neuqer:order:edit']"
          >详情
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['neuqer:order:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['neuqer:order:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>

      <el-table-column type="expand" align="left" width="1">
        <template slot-scope="props">
          <div class="expandDiv">
            <div class="expandDiv1">订单编号：{{props.row.orderSn}}</div>
            <div class="expandDiv2">下单时间：{{ parseTime(props.row.paymentTime, '{y}-{m}-{d} {h}:{m}:{s}') }}</div>
            <div class="expandDiv3">实付金额：{{props.row.orderAmount}}</div>
          </div>
          <el-table :data="props.row.itemVos" :default-expand-all="true" border :show-header="false"
                    class="childTable" :cell-style="{height:'30px'}">
            <el-table-column label="商品名称">
              <template slot-scope="scope">
                <div class="orderName">
                  <span style="font-size: 13px">产品名称：{{scope.row.productName}}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="产品规格" prop="shoppingOptions">
              <template slot-scope="scope">
                <div class="orderName">
                  <span style="font-size: 13px"
                        v-if="scope.row.shoppingOptions.length !== 0 && scope.row.shoppingOptions !== undefined ">规格：</span>
                  <span style="font-size: 13px">{{scope.row.shoppingOptions}}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="单价/数量" width="120" align="center">
              <template slot-scope="scope">
                <div class="orderName">
                  <span style="font-size: 13px">￥{{scope.row.unitPrice}}</span>
                  <span style="padding-left: 6px;font-size: 13px">×{{scope.row.amount}}</span>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="数量" width="200" align="center">
              <template slot-scope="scope">
                <div class="sigleNumber">
                  <span style="font-size: 13px">单次数量：{{scope.row.sigleNumber}}</span>
                  <i class="el-icon-edit" style="padding-left: 5px;font-size: 13px"></i>
                  <el-button type="text" style="font-size: 13px">修改</el-button>
                </div>
                <div class="sigleNumber">
                  <span style="padding-left: 12px;font-size: 13px">总数量：{{scope.row.totalQuantity}}</span>
                  <i class="el-icon-edit" style="padding-left: 5px;font-size: 13px"></i>
                  <el-button type="text" style="font-size: 13px">修改</el-button>
                </div>
                <div class="sigleNumber">
                  <span style="font-size: 13px">剩余数量：{{scope.row.takeNumber}}</span>
                  <i class="el-icon-edit" style="padding-left: 5px;font-size: 13px"></i>
                  <el-button type="text" style="font-size: 13px">修改</el-button>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="售后" width="100" align="center">
              <template slot-scope="scope">
                <div class="orderName">
                  <span v-if="scope.row.afterStatus.length === 0 || scope.row.afterStatus === undefined">-</span>
                  <dict-tag :options="dict.type.neuq_order_status" :value="scope.row.afterStatus"
                            style="font-size: 13px"/>
                </div>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <div class="tableLast">
                  <el-button type="text">文字按钮</el-button>
                  <el-button type="text" disabled>文字按钮</el-button>
                  <el-button type="text">文字按钮</el-button>
                  <el-button type="text" disabled>文字按钮</el-button>
                </div>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </el-table-column>
    </el-table>
    <pagination class="tab-pagination"
                v-show="total>0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList"
    />

    <!-- 添加或修改订单列表对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="1000px" append-to-body>
      <el-form label-position="left" :inline="true" ref="form" :model="form" :rules="rules" label-width="200px">
        <el-form-item label="订单编号" prop="orderSn">
          <el-input v-model="form.orderSn" placeholder="请输入订单编号"/>
        </el-form-item>
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
        <el-form-item label="结算时间" prop="paymentTime">
          <el-date-picker clearable size="small"
                          v-model="form.paymentTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择结算时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="交易完成时间" prop="accomplishTime">
          <el-date-picker clearable size="small"
                          v-model="form.accomplishTime"
                          type="datetime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          placeholder="请选择交易完成时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单总金额" prop="totalAmount">
          <el-input v-model="form.totalAmount" placeholder="请输入订单总金额"/>
        </el-form-item>
        <el-form-item label="订单类型" prop="orderType">
          <el-select v-model="form.orderType" placeholder="请选择订单类型">
            <el-option
              v-for="dict in dict.type.neuq_order_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="配送方式" prop="shippingMethod">
          <el-select v-model="form.shippingMethod" placeholder="请选择配送方式">
            <el-option
              v-for="dict in dict.type.neuq_shipping_mode"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单次数量" prop="singleNumber">
          <el-input v-model="form.singleNumber" placeholder="请输入单次数量"/>
        </el-form-item>
        <el-form-item label="总数量" prop="totalQuantity">
          <el-input v-model="form.totalQuantity" placeholder="请输入总数量"/>
        </el-form-item>
        <el-form-item label="已取数量" prop="takeNumber">
          <el-input v-model="form.takeNumber" placeholder="请输入已取数量"/>
        </el-form-item>
        <el-form-item label="收货人姓名" prop="receiverName">
          <el-input v-model="form.receiverName" placeholder="请输入收货人姓名"/>
        </el-form-item>
        <el-form-item label="收货人电话" prop="receiverPhone">
          <el-input v-model="form.receiverPhone" placeholder="请输入收货人电话"/>
        </el-form-item>
        <el-form-item label="收货人地址" prop="receiverDetailAddress">
          <el-input v-model="form.receiverDetailAddress" placeholder="请输入收货人地址"/>
        </el-form-item>
        <el-form-item label="给卖家留言" prop="messageForSeller">
          <el-input v-model="form.messageForSeller" placeholder="请输入给卖家留言"/>
        </el-form-item>
        <el-form-item label="订单备注" prop="note">
          <el-input v-model="form.note" placeholder="请输入订单备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="buttonLoading" type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 订单导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        :file-list="upload.fileList"
        :on-change="getUploadChange"
        :http-request="submitFileForm"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport + '&?isImport=' + upload.isImport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag

      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" @change="isUpdate"/>
            是否更新并覆盖已经存在的订单数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                   @click="importTemplate">下载模板
          </el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="checkFileForm">校 验</el-button>
        <el-tooltip class="item" effect="dark" content="请校验上传文件没有错误后再导入数据！" placement="top" :disabled="upload.isImport">
          <el-button type="primary" @click="submitFileForm" :disabled="!upload.isImport">导 入</el-button>
        </el-tooltip>

        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!--    显示导入校验结构-->
    <el-dialog :title="importDialog.title" :visible.sync="importDialog.dialogTableVisible" width="850px">
      <el-table :data="importMsgList" height="500">
        <el-table-column property="sheetName" label="表格名称" width="150px"></el-table-column>
        <el-table-column property="errLocation" label="位置" width="150px"></el-table-column>
        <el-table-column property="orderSn" label="订单编号" width="150px"></el-table-column>
        <el-table-column property="errMessage" label="详细信息" width="350px"></el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
  import { listOrder, getOrder, delOrder, addOrder, updateOrder, uploadOrder } from '@/api/neuqer/order'
  import { getToken } from '@/utils/auth'
  // import { ElMessage, ElMessageBox } from 'element-plus'

  export default {
    name: 'Order',
    dicts: ['neuq_shipping_mode', 'neuq_order_status', 'neuq_order_type'],
    data() {
      return {
        //头部下拉框选项
        headerSelect: '手机号码',
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
        // 订单列表表格数据
        orderList: [],
        // 弹出层标题
        title: '',
        // 是否显示弹出层
        open: false,

        // 用户导入参数
        upload: {
          // 是否显示弹出层（用户导入）
          open: false,
          // 弹出层标题（用户导入）
          title: '',
          //是否将订单保存到数据库，用户区别当前用户点击的是校验按钮还是导入按钮
          isImport: false,
          // 是否禁用上传
          isUploading: false,
          //导入对话框文件列表
          fileList: [],
          // 是否更新已经存在的用户数据
          updateSupport: 0,
          // 设置上传的请求头部
          headers: { Authorization: 'Bearer ' + getToken() },
          // 上传的地址
          url: process.env.VUE_APP_BASE_API + '/neuqer/order/importData'
        },

        //订单导入页面相关
        importMsgList: [],
        //导入对话框
        importDialog: {
          title: '',
          dialogTableVisible: false
        },

        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          memberId: undefined,
          orderSn: undefined,
          orderStatus: undefined,
          paymentTime: undefined,
          accomplishTime: undefined,
          totalAmount: undefined,
          orderType: undefined,
          shippingMethod: undefined,
          singleNumber: undefined,
          totalQuantity: undefined,
          takeNumber: undefined,
          receiverName: undefined,
          receiverPhone: undefined,
          receiverDetailAddress: undefined,
          messageForSeller: undefined,
          shippingPrice: undefined,
          shippingManId: undefined,
          pickUpAddress: undefined,
          note: undefined,
          deleteStatus: undefined
        },
        // 表单参数
        form: {},

        // 表单校验
        rules: {
          id: [
            { required: true, message: '订单id不能为空', trigger: 'blur' }
          ],
          orderSn: [
            { required: true, message: '订单编号不能为空', trigger: 'blur' }
          ],
          orderStatus: [
            { required: true, message: '订单状态：0->待发货；1->已发货；2->已完成；3->申请退款；4->已退款；5->拒绝退款；不能为空', trigger: 'change' }
          ],
          paymentTime: [
            { required: true, message: '结算时间不能为空', trigger: 'blur' }
          ],
          accomplishTime: [
            { required: true, message: '交易完成时间不能为空', trigger: 'blur' }
          ],
          totalAmount: [
            { required: true, message: '订单总金额不能为空', trigger: 'blur' }
          ],
          orderType: [
            { required: true, message: '订单类型：0->月订订单；1->周订订单不能为空', trigger: 'change' }
          ],
          shippingMethod: [
            { required: true, message: '配送方式：0->自提；1->同校送货不能为空', trigger: 'change' }
          ],
          singleNumber: [
            { required: true, message: '单次数量不能为空', trigger: 'blur' }
          ],
          totalQuantity: [
            { required: true, message: '总数量不能为空', trigger: 'blur' }
          ],
          takeNumber: [
            { required: true, message: '已取数量不能为空', trigger: 'blur' }
          ],
          receiverName: [
            { required: true, message: '收货人姓名不能为空', trigger: 'blur' }
          ],
          receiverPhone: [
            { required: true, message: '收货人电话不能为空', trigger: 'blur' }
          ],
          receiverDetailAddress: [
            { required: true, message: '收货人地址不能为空', trigger: 'blur' }
          ],
          messageForSeller: [
            { required: true, message: '给卖家留言不能为空', trigger: 'blur' }
          ],
          shippingPrice: [
            { required: true, message: '配送价格/运费不能为空', trigger: 'blur' }
          ],
          shippingManId: [
            { required: true, message: '配送人id不能为空', trigger: 'blur' }
          ],
          pickUpAddress: [
            { required: true, message: '取货地址不能为空', trigger: 'blur' }
          ],
          note: [
            { required: true, message: '订单备注不能为空', trigger: 'blur' }
          ],
          deleteStatus: [
            { required: true, message: '删除状态：0->未删除；1->已删除不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    created() {
      this.getList()
    },
    methods: {
      /** 查询订单列表列表 */
      getList() {
        this.loading = true
        listOrder(this.queryParams).then(response => {
          this.orderList = response.rows
          this.total = response.total
          this.loading = false
        })
      },
      // 取消按钮
      cancel() {
        this.open = false
        this.reset()
      },
      // 表单重置
      reset() {
        this.form = {
          id: undefined,
          memberId: undefined,
          orderSn: undefined,
          orderStatus: undefined,
          paymentTime: undefined,
          accomplishTime: undefined,
          totalAmount: undefined,
          orderType: undefined,
          shippingMethod: undefined,
          singleNumber: undefined,
          totalQuantity: undefined,
          takeNumber: undefined,
          receiverName: undefined,
          receiverPhone: undefined,
          receiverDetailAddress: undefined,
          messageForSeller: undefined,
          shippingPrice: undefined,
          shippingManId: undefined,
          pickUpAddress: undefined,
          note: undefined,
          deleteStatus: 0,
          createBy: undefined,
          createTime: undefined,
          updateBy: undefined,
          updateTime: undefined
        }
        this.resetForm('form')
      },
      /** 搜索按钮操作 */
      handleQuery() {
        console.log(this.headerSelect)
        this.queryParams.pageNum = 1
        this.getList()
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.resetForm('queryForm')
        this.handleQuery()
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.id)
        this.single = selection.length !== 1
        this.multiple = !selection.length
      },

      //表格下拉详情
      handleDetail(row) {
        this.$refs.refTable.toggleRowExpansion(row) //只需要这一句就好了
      },

      /** 新增按钮操作 */
      handleAdd() {
        this.reset()
        this.open = true
        this.title = '添加订单列表'
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.loading = true
        this.reset()
        const id = row.id || this.ids
        getOrder(id).then(response => {
          this.loading = false
          this.form = response.data
          this.open = true
          this.title = '修改订单列表'
        })
      },
      /** 提交按钮 */
      submitForm() {
        this.$refs['form'].validate(valid => {
          if (valid) {
            this.buttonLoading = true
            if (this.form.id != null) {
              updateOrder(this.form).then(response => {
                this.$modal.msgSuccess('修改成功')
                this.open = false
                this.getList()
              }).finally(() => {
                this.buttonLoading = false
              })
            } else {
              addOrder(this.form).then(response => {
                this.$modal.msgSuccess('新增成功')
                this.open = false
                this.getList()
              }).finally(() => {
                this.buttonLoading = false
              })
            }
          }
        })
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const ids = row.id || this.ids
        this.$modal.confirm('是否确认删除订单列表编号为"' + row.orderSn + '"的数据项？').then(() => {
          this.loading = true
          return delOrder(ids)
        }).then(() => {
          this.loading = false
          this.getList()
          this.$modal.msgSuccess('删除成功')
        }).finally(() => {
          this.loading = false
        })
      },
      /** 导出按钮操作 */
      handleExport() {
        this.download('neuqer/order/export', {
          ...this.queryParams
        }, `order_${new Date().getTime()}.xlsx`)
      },

      /** 导入按钮操作 */
      handleImport() {
        this.upload.title = '订单导入'
        this.upload.open = true
        this.upload.isImport = false
      },
      /** 下载模板操作 */
      importTemplate() {
        this.download('neuqer/order/importTemplate', {}, `order_template_${new Date().getTime()}.xlsx`)
      },
      // 文件上传中处理
      handleFileUploadProgress(event, file, fileList) {
        this.upload.isUploading = true
      },
      // 文件上传成功处理
      handleFileSuccess(response, file, fileList) {

        this.upload.isUploading = false
        let myFileList = fileList
        console.log(' myFileLis')
        console.log(myFileList)
        this.$refs.upload.clearFiles()
        console.log(' handleFileSuccess:this.fileList')
        console.log(this.upload.fileList)
        if (response.msg === '错误信息') {
          this.upload.isImport = false
          this.importDialog.dialogTableVisible = true
          this.importMsgList = response.data
          this.importDialog.title = '校验结果：共有' + response.data.length + '条错误，错误信息如下：'
        } else if (response.msg === '成功') {
          if (this.upload.isImport) {
            this.importMsgList = response.data
            this.importDialog.title = '成功信息如下:'
            this.importDialog.dialogTableVisible = true
          } else {
            this.upload.isImport = true
            this.$alert('<div style=\'overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;\'>' + '<br>校验成功</br>' + '</div>', '校验结果', { dangerouslyUseHTMLString: true })
          }
        } else {
          this.$alert('<div style=\'overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;\'>' + response.msg + '</div>', '导入结果', { dangerouslyUseHTMLString: true })
        }
        this.getList()
      },
      // 文件值改变时触发 change事件
      getUploadChange(file, fileList) {
        this.myFileList = this.upload.fileList
        if (fileList.length > 0) {
          this.upload.fileList = [fileList[fileList.length - 1]]
        } else {
          this.upload.fileList = fileList[0]
        }
      },
      // 提交上传文件
      submitFileForm() {
        console.log('执行这里了')
        console.log(this.upload.isImport)
        let formData = new FormData()
        formData.append('file', this.upload.fileList[0].raw)
        uploadOrder(formData, this.upload.updateSupport, this.upload.isImport).then(response => {
          console.log(response)
          if (response.msg === '错误信息') {
            this.upload.isImport = false
            this.importDialog.dialogTableVisible = true
            this.importMsgList = response.data
            this.importDialog.title = '校验结果：共有' + response.data.length + '条错误，错误信息如下：'
          } else if (response.msg === '成功') {
            if (this.upload.isImport) {
              this.importMsgList = response.data
              this.importDialog.title = '成功信息如下:'
              this.importDialog.dialogTableVisible = true
            } else {
              this.upload.isImport = true
              this.$alert('<div style=\'overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;\'>' + '<br>校验成功</br>' + '</div>', '校验结果', { dangerouslyUseHTMLString: true })
            }
          } else {
            this.$alert('<div style=\'overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;\'>' + response.msg + '</div>', '导入结果', { dangerouslyUseHTMLString: true })
          }
          this.getList()
        })
      },
      //校验按钮
      checkFileForm() {
        this.importDialog.dialogTableVisible = false
        this.upload.isImport = false
        this.$refs.upload.submit()
      },

      isUpdate() {
        if (this.upload.updateSupport) {
        }
      },
      //表格展示tooltip使用
      showTips(obj, row) {
        console.log(row)

        /*obj为鼠标移入时的事件对象*/
        /*currentWidth 为文本在页面中所占的宽度，创建标签，加入到页面，获取currentWidth ,最后在移除*/
        let TemporaryTag = document.createElement('span')
        TemporaryTag.innerText = row.buyerMessage
        TemporaryTag.className = 'getTextWidth'
        document.querySelector('body').appendChild(TemporaryTag)
        let currentWidth = document.querySelector('.getTextWidth').offsetWidth
        document.querySelector('.getTextWidth').remove()

        /*cellWidth为表格容器的宽度*/
        const cellWidth = obj.target.offsetWidth

        /*当文本宽度小于||等于容器宽度两倍时，代表文本显示未超过两行*/
        currentWidth <= (2 * cellWidth) ? row.showTooltip = false : row.showTooltip = true
      }
    }
  }
</script>
<style lang="scss" scoped>

  .orderHome {
    .headerForm {
      .seachInput {
        display: flex;
        align-items: center;
        width: 250px;
        height: 32px;
        border: 1px solid #e8e8e8;
        border-radius: 4px;
        .el-select {
          height: 30px;
          line-height: 30px;
          ::v-deep .el-input--medium {
            .el-input__inner {
              border: 0px;
              width: 105px;
              padding-right: 20px;
              border-right: 1px solid #e8e8e8;
              border-radius: 0px;
              height: 30px;
              line-height: 30px;
            }
            .el-input__icon {
              line-height: 30px;
            }

          }

        }
          .el-input {
            height: 30px;
            ::v-deep .el-input__inner {
              width: 120px;
              border: 0px;
              height: 30px;
              padding: 0px 10px 0px 10px;
              margin-right: 0px;
            }

            ::v-deep .el-input--small {
              width: 120px;
            }

            ::v-deep .el-input-group__append {
              padding: 0px;
              width: 20px;
              background-color: #ffffff;
              border: 0px;

              .seachButton {
                padding: 0px;
                margin: 0px;
              }
            }

          }
        }
      }

      .mid {
        display: flex;
        align-content: center;
        justify-content: space-between;

        .el-tabs {
          width: 60%;

          ::v-deep .el-tabs__item {
            padding-bottom: 45px;
            text-align: center !important;;
            font-size: 16px !important;
          }

          ::v-deep .el-tabs__item.is-active {
            padding-bottom: 10px;
          }

          ::v-deep .el-tabs__active-bar {
            height: 3px;
          }

          ::v-deep .el-tabs__header {
            margin-bottom: 0px;
            padding-left: 40px;
          }
        }

        .tabsRow {
          width: 50%;
          margin: 0px;
          display: flex;
          align-items: center;
          justify-content: space-around;
          padding-bottom: 10px;
          padding-right: 50px;
        }
      }

      .midBottom {
        margin-top: -1px;
        height: 2px;
        width: auto;
        background-color: #dddddd;
        margin-bottom: 10px;
      }

      .table {
        ::v-deep .el-table__expand-column {
          display: none;
        }

        ::v-deep .el-table-column--selection .cell {
          padding-left: 7px;
          padding-right: 7px;
        }

        .expandDiv {
          display: flex;
          align-items: center;
          background-color: #f2f6ff;
          height: 40px;

          .expandDiv1 {
            padding: 0px 10px;
          }

          .expandDiv2 {
            padding: 0px 10px;
          }

          .expandDiv3 {
            padding: 0px 10px;
          }
        }

        .tableNote {
          font-size: 13px;
          display: -webkit-box;
          text-overflow: ellipsis;
          overflow: hidden;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
        }

        .recevicerAddress {

          .span {
            font-size: 13px;
            display: -webkit-box;
            text-overflow: ellipsis;
            overflow: hidden;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
          }
        }

        .childTable {
          ::v-deep .el-table__cell {
            padding: 0px;
            background-color: #f2f6ff;
          }

          .sigleNumber {
            padding: 0px;
            height: 28px;
            display: flex;
            align-items: center;
            margin-left: 30px;
          }
        }
      }
    }

</style>
