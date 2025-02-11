<template>
  <a-modal v-model="show" title="生产流程信息" @cancel="onClose" :width="1000">
    <template slot="footer">
      <a-button key="back1" @click="onClose" type="primary">
        提交
      </a-button>
      <a-button key="back2" @click="onClose" type="danger">
        返工
      </a-button>
      <a-button key="back" @click="onClose" type="primary">
        入库
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
      <a-row style="padding-left: 24px;padding-right: 24px;" :gutter="25">
        <a-col :span="12" style="margin-bottom: 25px"><b>选择流程材料：</b>
          <a-select v-model="comboNum" @change="handleChange" style="width: 100%;margin-top: 10px">
            <a-select-option v-for="(item, index) in comboList" :value="item.code" :key="index">{{ item.name }}</a-select-option>
          </a-select>
        </a-col>
        <a-col :span="12"><b>选择设备：</b>
          <a-select v-model="deviceId" style="width: 100%;margin-top: 10px">
            <a-select-option v-for="(item, index) in deviceList" :value="item.id" :key="index">{{ item.deviceName }}</a-select-option>
          </a-select>
        </a-col>
        <br/>
        <br/>
        <a-row :gutter="15">
          <a-col :span="24" style="padding-left: 20px;padding-right: 20px;margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17;text-align: left">申请材料详情</span></a-col>
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
      current: 0,
      comboNum: '',
      deviceId: ''
    }
  },
  watch: {
    rurchaseShow: function (value) {
      if (value) {
        if (this.rurchaseData.step === 0) {
          this.current = 1
        }
        if (this.rurchaseData.step === 1) {
          this.current = 2
        }
        this.getGoodsByNum(this.rurchaseData.num)
      }
    }
  },
  mounted () {
    this.getComboData()
    this.getDeviceList()
  },
  methods: {
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
      this.$emit('close')
    }
  }
}
</script>

<style scoped>

</style>
