<template>
  <a-card :bordered="false" class="card-area">
    <div :class="advanced ? 'search' : null">
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row :gutter="15">
          <div :class="advanced ? null: 'fold'">
            <a-col :md="6" :sm="24">
              <a-form-item
                label="报废单号"
                :labelCol="{span: 5}"
                :wrapperCol="{span: 18, offset: 1}">
                <a-input v-model="queryParams.num"/>
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
        <a-button type="primary" ghost @click="add">报废添加</a-button>
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
        <template slot="numShow" slot-scope="text, record">
          <template>
            <a-badge v-if="record.step == 0" status="processing"/>
            <a-badge v-if="record.step == 1" status="success"/>
            {{ record.num }}
          </template>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon type="reconciliation" @click="view(record)" title="查 看" style="margin-right: 15px"></a-icon>
        </template>
      </a-table>
    </div>
    <request-add
      v-if="requestAdd.visiable"
      @close="handlerequestAddClose"
      @success="handlerequestAddSuccess"
      :requestAddVisiable="requestAdd.visiable">
    </request-add>
    <request-view
      @close="handlerequestViewClose"
      :requestShow="requestView.visiable"
      :requestData="requestView.data">
    </request-view>
  </a-card>
</template>

<script>
import RangeDate from '@/components/datetime/RangeDate'
import {mapState} from 'vuex'
import moment from 'moment'
import { newSpread, floatForm, floatReset, saveExcel } from '@/utils/spreadJS'
import RequestAdd from './ScrapAdd.vue'
import RequestView from './ScrapView.vue'
moment.locale('zh-cn')
export default {
  name: 'request',
  components: {RequestView, RequestAdd, RangeDate},
  data () {
    return {
      requestAdd: {
        visiable: false
      },
      requestEdit: {
        visiable: false
      },
      requestView: {
        visiable: false,
        data: null
      },
      advanced: false,
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
      userList: [],
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    columns () {
      return [{
        title: '报废单号',
        dataIndex: 'num',
        scopedSlots: {customRender: 'numShow'}
      }, {
        title: '报废申请人',
        dataIndex: 'name'
      }, {
        title: '员工图片',
        dataIndex: 'images',
        customRender: (text, record, index) => {
          if (!record.images) return <a-avatar shape="square" icon="user" />
          return <a-popover>
            <template slot="content">
              <a-avatar shape="square" size={132} icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
            </template>
            <a-avatar shape="square" icon="user" src={ 'http://127.0.0.1:9527/imagesWeb/' + record.images.split(',')[0] } />
          </a-popover>
        }
      }, {
        title: '报废主题',
        dataIndex: 'theme'
      }, {
        title: '申请说明',
        dataIndex: 'content'
      }, {
        title: '采购小组',
        dataIndex: 'team',
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
    downLoad (row) {
      this.$message.loading('正在生成', 0)
      this.$get('/cos/goods-belong/getGoodsByNum', { num: row.num }).then((r) => {
        let newData = []
        r.data.data.forEach((item, index) => {
          newData.push([item.name, item.type !== null ? item.type : '- -', item.amount, row.createDate, ''])
        })
        let spread = newSpread('claimForm')
        spread = floatForm(spread, 'claimForm', newData)
        saveExcel(spread, '申领单.xlsx')
        floatReset(spread, 'claimForm', newData.length)
        this.$message.destroy()
      })
    },
    add () {
      this.requestAdd.visiable = true
    },
    handlerequestAddClose () {
      this.requestAdd.visiable = false
    },
    handlerequestAddSuccess () {
      this.requestAdd.visiable = false
      this.$message.success('新增核销报废成功')
      this.search()
    },
    edit (record) {
      this.$refs.requestEdit.setFormValues(record)
      this.requestEdit.visiable = true
    },
    handlerequestEditClose () {
      this.requestEdit.visiable = false
    },
    handlerequestEditSuccess () {
      this.requestEdit.visiable = false
      this.$message.success('修改核销报废成功')
      this.search()
    },
    stop (row) {
      let that = this
      this.$confirm({
        title: '确定中止当前采购申请?',
        content: '当您点击确定按钮后，这些采购申请将会被彻底删除',
        centered: true,
        onOk () {
          that.$delete('/cos/goods-request/' + row.id).then(() => {
            that.$message.success('中止成功')
            that.search()
          })
        }
      })
    },
    view (row) {
      this.requestView.data = row
      this.requestView.visiable = true
    },
    handlerequestViewClose () {
      this.requestView.visiable = false
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    toggleAdvanced () {
      this.advanced = !this.advanced
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
          that.$delete('/cos/scrap-info/' + ids).then(() => {
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
      params.userId = this.currentUser.userId
      if (params.step === undefined) {
        delete params.step
      }
      this.$get('/cos/scrap-info/page', {
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
