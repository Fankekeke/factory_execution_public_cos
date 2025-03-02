<template>
  <a-card :bordered="false" class="card-area" style="padding: 25px">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="流程单号"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.code"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="生产流程"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.name"/>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="24">
              <a-form-item
                label="状态"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-select v-model="queryParams.status" allowClear>
                  <a-select-option value="0">未提交</a-select-option>
                  <a-select-option value="1">已提交</a-select-option>
                  <a-select-option value="2">返工</a-select-option>
                </a-select>
              </a-form-item>
            </a-col>
          </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="add">新增</a-button>
        <a-button @click="batchDelete">删除</a-button>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
               :columns="columns"
               :rowKey="record => record.id"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="titleShow" slot-scope="text, record">
          <template>
            <a-badge status="processing"/>
            <a-tooltip>
              <template slot="title">
                {{ record.title }}
              </template>
              {{ record.title.slice(0, 8) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="contentShow" slot-scope="text, record">
          <template>
            <a-tooltip>
              <template slot="title">
                {{ record.content }}
              </template>
              {{ record.content.slice(0, 30) }} ...
            </a-tooltip>
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon type="folder-add" @click="handleProductionView(record)" title="查 看"></a-icon>
          <a-icon type="tool" @click="view(record)" title="查 看" style="margin-left: 15px" v-if="record.currentStep != 6"></a-icon>
<!--          <a-icon type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修 改"></a-icon>-->
        </template>
      </a-table>
    </div>
    <unit-add
      v-if="unitAdd.visiable"
      @close="handleunitAddClose"
      @success="handleunitAddSuccess"
      :unitAddVisiable="unitAdd.visiable">
    </unit-add>
    <unit-edit
      ref="unitEdit"
      @close="handleunitEditClose"
      @success="handleunitEditSuccess"
      :unitEditVisiable="unitEdit.visiable">
    </unit-edit>
    <rurchase-view
      @close="handleRurchaseViewClose"
      @success="handleRurchaseViewSuccess"
      :rurchaseShow="rurchaseView.visiable"
      :rurchaseData="rurchaseView.data">
    </rurchase-view>
    <production-view
      @close="handleProductionViewClose"
      :rurchaseShow="productionView.visiable"
      :rurchaseData="productionView.data">
    </production-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import unitAdd from './ProductionAdd.vue'
import unitEdit from './ProductionEdit.vue'
import RurchaseView from './RurchaseView'
import ProductionView from './ProductionView'
import {mapState} from 'vuex'
import moment from 'moment'
moment.locale('zh-cn')

export default {
  name: 'unit',
  components: {unitAdd, unitEdit, RurchaseView, ProductionView, RangeDate},
  data () {
    return {
      advanced: false,
      unitAdd: {
        visiable: false
      },
      unitEdit: {
        visiable: false
      },
      rurchaseView: {
        visiable: false,
        data: null
      },
      productionView: {
        visiable: false,
        data: null
      },
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,
      paginationInfo: null,
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      pagination: {
        pageSizeOptions: ['10', '20', '30', '40', '100'],
        defaultCurrent: 1,
        defaultPageSize: 10,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      },
      userList: []
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '流程单号',
        dataIndex: 'code'
      }, {
        title: '生产名称',
        dataIndex: 'name'
      }, {
        title: '总流程数量',
        dataIndex: 'stepNum',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '当前流程',
        dataIndex: 'currentStep',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag>清洗消毒</a-tag>
            case 2:
              return <a-tag>原材料整理</a-tag>
            case 3:
              return <a-tag>商品制作</a-tag>
            case 4:
              return <a-tag>商品打包</a-tag>
            case 5:
              return <a-tag>入库印刷</a-tag>
            case 6:
              return <a-tag color="#87d068">完成</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '当前状态',
        dataIndex: 'status',
        customRender: (text, row, index) => {
          switch (text) {
            case '0':
              return <a-tag color="red">未提交</a-tag>
            case '1':
              return <a-tag>已提交</a-tag>
            case '2':
              return <a-tag>返工</a-tag>
            default:
              return '- -'
          }
        }
      }, {
        title: '生产备注',
        dataIndex: 'content',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '创建时间',
        dataIndex: 'createDate',
        customRender: (text, row, index) => {
          if (text !== null) {
            return text
          } else {
            return '- -'
          }
        }
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: {customRender: 'operation'}
      }]
    }
  },
  mounted () {
    this.fetch()
  },
  methods: {
    view (row) {
      this.rurchaseView.data = row
      this.rurchaseView.visiable = true
    },
    handleRurchaseViewClose () {
      this.rurchaseView.visiable = false
    },
    handleProductionView (row) {
      this.productionView.data = row
      this.productionView.visiable = true
    },
    handleProductionViewClose () {
      this.productionView.visiable = false
    },
    handleRurchaseViewSuccess () {
      this.rurchaseView.visiable = false
      this.$message.success('提交成功')
      this.search()
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
    },
    add () {
      this.unitAdd.visiable = true
    },
    handleunitAddClose () {
      this.unitAdd.visiable = false
    },
    handleunitAddSuccess () {
      this.unitAdd.visiable = false
      this.$message.success('新增生产流程成功')
      this.search()
    },
    edit (record) {
      this.$refs.unitEdit.setFormValues(record)
      this.unitEdit.visiable = true
    },
    handleunitEditClose () {
      this.unitEdit.visiable = false
    },
    handleunitEditSuccess () {
      this.unitEdit.visiable = false
      this.$message.success('修改生产流程成功')
      this.search()
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let ids = that.selectedRowKeys.join(',')
          that.$delete('/cos/production-process-info/' + ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    search () {
      let {sortedInfo, filteredInfo} = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetch({
        sortField: sortField,
        sortOrder: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      this.fetch()
    },
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter

      this.fetch({
        sortField: sorter.field,
        sortOrder: sorter.order,
        ...this.queryParams,
        ...filters
      })
    },
    fetch (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.size = this.paginationInfo.pageSize
        params.current = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.size = this.pagination.defaultPageSize
        params.current = this.pagination.defaultCurrent
      }
      if (params.status === undefined) {
        delete params.status
      }
      this.$get('/cos/production-process-info/page', {
        ...params
      }).then((r) => {
        let data = r.data.data
        const pagination = {...this.pagination}
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
      })
    }
  },
  watch: {}
}
</script>
<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
