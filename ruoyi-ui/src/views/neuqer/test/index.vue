<template>
  <div class="home" >
    <div class="div">

    <el-table :data="tableData" :default-expand-all="true" class="panrent-table"
              :span-method="arraySpanMethod"
              :cell-style="{'background-color':'#f8f8f9'}" border>
        <el-table-column type="expand">
          <template slot-scope="props" class="panrent-table-col-1-temp" >
                <el-table :data="props.row.OrderDetails" :default-expand-all="true" border :show-header="false"
                          class="childTable" :cell-style="{height:'30px'}">
                  <el-table-column prop="PartNo" align="center"
                                   label="产品型号"
                                   width="180">
                    <template slot-scope="scope">
                      <div class="name-b">{{scope.row.PartNo}}</div>
                    </template>
                  </el-table-column>
                  <el-table-column prop="Mfg"
                                   label="品牌" width="199">
                    <template slot-scope="scope">
                      <div class="name-b">{{scope.row.Mfg}}</div>
                    </template>
                  </el-table-column>
                  <el-table-column prop="Package"
                                   label="封装" width="114">
                    <template slot-scope="scope">
                      <div class="name-b">{{scope.row.Package}}</div>
                    </template>
                  </el-table-column>
                  <el-table-column prop="miaoshu"
                                   label="描述" width="300">
                    <template slot-scope="scope">
                      <div class="name-b" style="width:100%;">72MHZ 20KB 37 2V~3.6V-40°C~85°C（TA）</div>
                    </template>
                  </el-table-column>
                  <el-table-column prop="ProductNum" align="center" width="120"
                                   label="申请数量">
                    <template slot-scope="scope">
                      <div class="name-b">{{scope.row.ProductNum}}</div>
                    </template>
                  </el-table-column>
                  <el-table-column prop="maxNum"
                                   label="申请状态">
                    <template slot-scope="scope">
                      <div>
                        <el-popover trigger="hover" placement="right" v-if="scope.row.State==20||scope.row.State==40">
                          <p v-if="scope.row.State==20">失败原因: {{ scope.row.FailReason }}</p>
                          <p v-if="scope.row.State==40">物流信息: {{ scope.row.ExpressNo }}</p>
                          <div slot="reference" class="name-wrapper">
                            <span v-if="scope.row.State==40" style="margin-left:14px;color:#B77C20;">物流信息</span>
                            <span v-if="scope.row.State==20" style="margin-left:14px;color:#B77C20;">查看原因</span>
                          </div>
                        </el-popover>
                        <div>
                        </div>
                      </div>
                    </template>
                  </el-table-column>
                  <el-table-column label="操作" width="162">
                    <template slot-scope="scope">
                      <div style="display:flex;">
                        <el-button type="text" size="small" v-on:click="cancel(scope.row)"
                                   v-if="scope.row.State==10||scope.row.State==20">取消
                        </el-button>
                        <el-button type="text" size="small" v-on:click="sureHave(scope.row)" v-if="scope.row.State==40">确认收货
                        </el-button>
                      </div>
                    </template>
                  </el-table-column>
                </el-table>
          </template>
        </el-table-column>

      <el-table-column type="selection"></el-table-column>

      <el-table-column label="产品型号" align="center" width="100">
        <template slot-scope="scope">
          <div class="tes-div" ref="element">
            <span class="span">订单包号：{{scope.row.OrderNo}}</span>
            <text class="text">付款时间：{{scope.row.CreateTime}}</text>
            <span style="margin-left:42px;">付款时间：{{scope.row.CreateTime}}</span>
            <el-button type="text">确认收货
            </el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="品牌" width="199"></el-table-column>
      <el-table-column label="封装" width="114"></el-table-column>
      <el-table-column label="描述" width="300"></el-table-column>
      <el-table-column label="申请数量" align="center" width="120"></el-table-column>
      <el-table-column label="申请状态" width="auto"></el-table-column>
      <el-table-column label="操作" align="center" width="200"></el-table-column>
    </el-table>
    </div>
  </div>

</template>

<script>
  // import * as echarts from "echarts";

  import elementResizeDetectorMaker from 'element-resize-detector';
  export default {
    data() {
      return {
        elcol:[],
        elrow: {
          'background-color': '#fef6e9 !important',
          'border-top': 'none !important'
        },
        tableData: [
          {
            'OrderNo': 'ICS-10390-1',
            'ProductIds': '[646,309118,331385,331393,331394]',
            'UserId': 10390,
            'Applicant': '(￣▽￣*)b',
            'ApplicantMobile': '18458192430',
            'Receiver': '123',
            'CompanyName': '卡卡卡卡卡12 32  26',
            'Post': '高管',
            'Industry': '工业设计',
            'Purpose': '332',
            'Province': '广东省',
            'City': '广州市',
            'Address': '123',
            'ContactMobile': '18458192430',
            'CreateTime': '2021/9/7 8:51',
            'OrderDetails': [
              {
                'Id': 1309,
                'OrderNo': 'ICS-10390-1',
                'SupplierId': 2,
                'ProductId': 331393,
                'ProductNum': 1,
                'ExpressNo': null,
                'SendTime': '',
                'ReceiveTime': '',
                'JpSkuNo': 'JPC47B1332N331393',
                'PartNo': 'cs-454',
                'Package': '21',
                'Mfg': 'Samsung(三星)',
                'ProPics': 'https://test-jpfile1.oss-cn-shenzhen.aliyuncs.com//IcMall/icmall/2021/4/30/2021043014452714515931.JPG',
                'CreateTime': '2021/9/7 8:51',
                'UpdateTime': '2021/9/7 8:51',
                'IsDeleted': false,
                'State': 10,
                'CheckTime': null,
                'FailReason': null,
                'SupplierName': '深圳前海宝利士科技有限公司',
                'ExpressCompany': null,
                'Ship': 1
              },
              {
                'Id': 1309,
                'OrderNo': 'ICS-10390-1',
                'SupplierId': 2,
                'ProductId': 331393,
                'ProductNum': 1,
                'ExpressNo': null,
                'SendTime': '',
                'ReceiveTime': '',
                'JpSkuNo': 'JPC47B1332N331393',
                'PartNo': 'cs-454',
                'Package': '21',
                'Mfg': 'Samsung(三星)',
                'ProPics': 'https://test-jpfile1.oss-cn-shenzhen.aliyuncs.com//IcMall/icmall/2021/4/30/2021043014452714515931.JPG',
                'CreateTime': '2021/9/7 8:51',
                'UpdateTime': '2021/9/7 8:51',
                'IsDeleted': false,
                'State': 10,
                'CheckTime': null,
                'FailReason': null,
                'SupplierName': '深圳前海宝利士科技有限公司',
                'ExpressCompany': null,
                'Ship': 1
              },
              {
                'Id': 1309,
                'OrderNo': 'ICS-10390-1',
                'SupplierId': 2,
                'ProductId': 331393,
                'ProductNum': 1,
                'ExpressNo': null,
                'SendTime': '',
                'ReceiveTime': '',
                'JpSkuNo': 'JPC47B1332N331393',
                'PartNo': 'cs-454',
                'Package': '21',
                'Mfg': 'Samsung(三星)',
                'ProPics': 'https://test-jpfile1.oss-cn-shenzhen.aliyuncs.com//IcMall/icmall/2021/4/30/2021043014452714515931.JPG',
                'CreateTime': '2021/9/7 8:51',
                'UpdateTime': '2021/9/7 8:51',
                'IsDeleted': false,
                'State': 10,
                'CheckTime': null,
                'FailReason': null,
                'SupplierName': '深圳前海宝利士科技有限公司',
                'ExpressCompany': null,
                'Ship': 1
              }
            ]
          },
          {
            'OrderNo': 'ICS-10390-1',
            'ProductIds': '[646,309118,331385,331393,331394]',
            'UserId': 10390,
            'Applicant': '(￣▽￣*)b',
            'ApplicantMobile': '18458192430',
            'Receiver': '123',
            'CompanyName': '卡卡卡卡卡12 32  26',
            'Post': '高管',
            'Industry': '工业设计',
            'Purpose': '332',
            'Province': '广东省',
            'City': '广州市',
            'Address': '123',
            'ContactMobile': '18458192430',
            'CreateTime': '2021/9/7 8:51',
            'OrderDetails': [
              {
                'Id': 1309,
                'OrderNo': 'ICS-10390-1',
                'SupplierId': 2,
                'ProductId': 331393,
                'ProductNum': 1,
                'ExpressNo': null,
                'SendTime': '',
                'ReceiveTime': '',
                'JpSkuNo': 'JPC47B1332N331393',
                'PartNo': 'cs-454',
                'Package': '21',
                'Mfg': 'Samsung(三星)',
                'ProPics': 'https://test-jpfile1.oss-cn-shenzhen.aliyuncs.com//IcMall/icmall/2021/4/30/2021043014452714515931.JPG',
                'CreateTime': '2021/9/7 8:51',
                'UpdateTime': '2021/9/7 8:51',
                'IsDeleted': false,
                'State': 10,
                'CheckTime': null,
                'FailReason': null,
                'SupplierName': '深圳前海宝利士科技有限公司',
                'ExpressCompany': null,
                'Ship': 1
              },
              {
                'Id': 1309,
                'OrderNo': 'ICS-10390-1',
                'SupplierId': 2,
                'ProductId': 331393,
                'ProductNum': 1,
                'ExpressNo': null,
                'SendTime': '',
                'ReceiveTime': '',
                'JpSkuNo': 'JPC47B1332N331393',
                'PartNo': 'cs-454',
                'Package': '21',
                'Mfg': 'Samsung(三星)',
                'ProPics': 'https://test-jpfile1.oss-cn-shenzhen.aliyuncs.com//IcMall/icmall/2021/4/30/2021043014452714515931.JPG',
                'CreateTime': '2021/9/7 8:51',
                'UpdateTime': '2021/9/7 8:51',
                'IsDeleted': false,
                'State': 10,
                'CheckTime': null,
                'FailReason': null,
                'SupplierName': '深圳前海宝利士科技有限公司',
                'ExpressCompany': null,
                'Ship': 1
              }
            ]
          }
        ],
        'Queryable': null,
        'TotalCount': 1,
        'Msg': null
      }

    },
    methods: {
      arraySpanMethod({ row, column, rowIndex, columnIndex }) {
        if(columnIndex == 2){
          return[1,9];
        }


      },
    },
    changerow(row){
      console.log(row);
    },
  }
</script>


<style lang="scss" scoped>

  .home {
    .panrent-table {

      .testdiv{
        display: flex;
        align-items: center;
        height: 500px;
        background-color: #1ab394;
      }
      ::v-deep .el-table__expand-column {
        display: none;
      }
      ::v-deep .el-descriptions-item__label{
        display: none;
      }

      ::v-deep .el-table__expanded-cell{
        padding-top: 0px;
      }
    div {
          background-color:#f8f8f9;
          display: flex;
          justify-content: left;
          align-items: center;
          .span{
          }
          .text{
            font-size: 20px;
            color: #1ab394;
          }
        }
      ::v-deep .el-table__header{
        padding-bottom: 10px;
        border-bottom: 1px solid #e8e8e9 !important;
      }
    }
  }

</style>
