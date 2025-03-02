<template>
  <a-modal v-model="show" title="生产流程信息" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back3" @click="apply" type="primary" v-if="processInfo != null && processInfo.reqNum == null && rurchaseData.currentStep != 5">
        申请
      </a-button>
      <a-button key="back1" @click="updateProcessStatus(1)" type="primary" v-if="processInfo != null && processInfo.reqNum != null && processInfo.passFlag == 1">
        提交
      </a-button>
      <a-button key="back2" @click="updateProcessStatus(2)" type="danger" v-if="rurchaseData.currentStep != 1 && processInfo != null && processInfo.reqNum == null">
        返工
      </a-button>
      <a-button key="back" @click="addWarehouse" type="primary" v-if="rurchaseData.currentStep == 5">
        入库
      </a-button>
      <a-button key="back10" @click="onClose" type="danger">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px" v-if="rurchaseData !== null">
      <div style="padding-left: 24px;padding-right: 24px;margin-bottom: 50px;margin-top: 50px">
        <a-steps :current="current" progress-dot size="small">
          <a-step title="清洗消毒" />
          <a-step title="原材料整理" />
          <a-step title="商品制作" />
          <a-step title="商品打包" />
          <a-step title="入库印刷" />
        </a-steps>
      </div>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>流程单号：</b>
          {{ rurchaseData.code }}
        </a-col>
        <a-col :span="8"><b>生产名称：</b>
          {{ rurchaseData.name }}
        </a-col>
        <a-col :span="8"><b>总流程数量：</b>
          {{ rurchaseData.stepNum }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>当前状态：</b>
          <span v-if="rurchaseData.status == 0">未提交</span>
          <span v-if="rurchaseData.status == 1">已提交</span>
          <span v-if="rurchaseData.status == 2">返工</span>
        </a-col>
        <a-col :span="8"><b>备注：</b>
          {{ rurchaseData.content }}
        </a-col>
        <a-col :span="8"><b>创建时间：</b>
          {{ rurchaseData.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="25" v-if="processInfo != null && processInfo.reqNum == null ">
        <a-col style="margin-bottom: 2px"><span style="font-size: 15px;font-weight: 650;color: #000c17">选择设备</span></a-col>
        <a-col :span="12">
          <a-select v-model="deviceId" style="width: 100%;margin-top: 10px">
            <a-select-option v-for="(item, index) in deviceList" :value="item.id" :key="index">{{ item.deviceName }}</a-select-option>
          </a-select>
        </a-col>
      </a-row>
      <a-row style="padding-left: 24px;padding-right: 24px;padding-top: 24px" :gutter="25">
        <a-col style="margin-bottom: 2px" v-if="processInfo != null && processInfo.reqNum == null"><span style="font-size: 15px;font-weight: 650;color: #000c17">选择流程材料</span></a-col>
        <a-col :span="12" v-if="processInfo != null && processInfo.reqNum == null">
          <a-select v-model="comboNum" @change="handleChange" style="width: 100%;margin-top: 10px">
            <a-select-option v-for="(item, index) in comboList" :value="item.code" :key="index">{{ item.name }}</a-select-option>
          </a-select>
        </a-col>
        <br/>
        <br/>
        <a-row :gutter="15" style="padding-top: 24px">
          <a-col :span="24" style="padding-left: 20px;padding-right: 20px;margin-bottom: 15px">
            <span style="font-size: 15px;font-weight: 650;color: #000c17;text-align: left">申请材料详情</span>
            <span style="font-size: 13px;font-weight: 500;color: #4a4a48;text-align: left;margin-left: 25px" v-if="processInfo != null && processInfo.passFlag != null">申请情况：{{ processInfo.passFlag == 1 ? '已通过' : '审核中' }}</span>
          </a-col>
          <a-col :span="24" style="padding-left: 20px;padding-right: 20px">
            <a-table :columns="columns" :data-source="goodsList">
            </a-table>
          </a-col>
        </a-row>
        <br/>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import {mapState} from 'vuex'
moment.locale('zh-cn')
export default {
  name: 'RurchaseView',
  props: {
    rurchaseShow: {
      type: Boolean,
      default: false
    },
    rurchaseData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.rurchaseShow
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '物品名称',
        dataIndex: 'name'
      }, {
        title: '型号',
        dataIndex: 'type'
      }, {
        title: '数量',
        dataIndex: 'amount'
      }, {
        title: '所属类型',
        dataIndex: 'consumableName'
      }, {
        title: '单位',
        dataIndex: 'unit'
      }, {
        title: '单价',
        dataIndex: 'price'
      }]
    }
  },
  data () {
    return {
      loading: false,
      goodsList: [],
      comboList: [],
      deviceList: [],
      processList: [],
      processInfo: null,
      current: 0,
      comboNum: '',
      deviceId: ''
    }
  },
  watch: {
    rurchaseShow: function (value) {
      if (value) {
        this.current = this.rurchaseData.currentStep - 1
        this.queryProcessDetail(this.rurchaseData.id)
        this.getGoodsByNum(this.rurchaseData.num)
      }
    }
  },
  mounted () {
    this.getComboData()
    this.getDeviceList()
  },
  methods: {
    addWarehouse () {
      this.$post('/cos/production-process-info/addWarehouse',  Object.assign(this.rurchaseData, { deviceId: this.deviceId, goods: JSON.stringify(this.goodsList), laseOperator: this.currentUser.userId})).then((r) => {
        this.goodsList = []
        this.deviceId = ''
        this.comboNum = ''
        this.$emit('success')
      })
    },
    updateProcessStatus (status) {
      this.rurchaseData.status = status
      this.$post('/cos/production-process-info/updateProcessStatus', this.rurchaseData).then((r) => {
        this.goodsList = []
        this.deviceId = ''
        this.comboNum = ''
        this.$emit('success')
      })
    },
    /**
     * 原材料申请
     */
    apply () {
      this.$post('/cos/production-process-info/requestProcessStatus', Object.assign(this.rurchaseData, { deviceId: this.deviceId, goods: JSON.stringify(this.goodsList), userId: this.currentUser.userId})).then((r) => {
        this.goodsList = []
        this.deviceId = ''
        this.comboNum = ''
        this.$emit('success')
      })
    },
    queryProcessDetail (id) {
      this.$get(`/cos/production-process-info/queryProcessDetail/${id}`).then((r) => {
        this.processList = r.data.data
        for (let i = 0; i < this.processList.length; i++) {
          if (this.processList[i].stepIndex == this.rurchaseData.currentStep) {
            this.processInfo = this.processList[i]
            this.goodsList = this.processInfo.itemList
            console.log(this.processInfo)
          }
        }
      })
    },
    handleChange (value) {
      if (value !== '') {
        this.$get('/cos/goods-belong/getGoodsByNum', { num: value }).then((r) => {
          this.goodsList = r.data.data
        })
      }
    },
    getComboData () {
      this.$get('/cos/combo-info/list').then((r) => {
        this.comboList = r.data.data
      })
    },
    getDeviceList () {
      this.$get('/cos/device-info/list').then((r) => {
        this.deviceList = r.data.data
      })
    },
    getGoodsByNum (num) {
      this.$get('/cos/goods-belong/getGoodsByNum', { num }).then((r) => {
        this.goodsList = r.data.data
        console.log(this.goodsList)
      })
    },
    onClose () {
      this.goodsList = []
      this.deviceId = ''
      this.comboNum = ''
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
