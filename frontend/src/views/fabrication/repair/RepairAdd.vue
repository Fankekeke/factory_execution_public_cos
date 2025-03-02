<template>
  <a-drawer
    title="新增维保记录"
    :maskClosable="false"
    placement="right"
    :closable="false"
    :visible="show"
    :width="1200"
    @close="onClose"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form" layout="vertical">
      <a-row :gutter="20">
        <a-col :span="12">
          <a-form-item label='选择入库记录' v-bind="formItemLayout">
            <a-select v-model="comboNum" @change="handleChange" style="width: 100%">
              <a-select-option v-for="(item, index) in comboList" :value="item.num" :key="index">{{ item.num }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='维保标题' v-bind="formItemLayout">
            <a-input v-decorator="[
            'theme',
            { rules: [{ required: true, message: '请输入维保标题!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='备注消息' v-bind="formItemLayout">
            <a-textarea :rows="4" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入备注消息!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-table :columns="columns" :data-source="dataList">
            <template slot="nameShow" slot-scope="text, record">
              <a-input v-model="record.name" disabled></a-input>
            </template>
            <template slot="typeShow" slot-scope="text, record">
              <a-input v-model="record.type" disabled></a-input>
            </template>
            <template slot="typeIdShow" slot-scope="text, record">
              <a-select v-model="record.typeId" disabled style="width: 100%">
                <a-select-option v-for="(item, index) in consumableType" :value="item.id" :key="index">{{ item.name }}</a-select-option>
              </a-select>
            </template>
            <template slot="unitShow" slot-scope="text, record">
              <a-input v-model="record.unit" disabled></a-input>
            </template>
            <template slot="amountShow" slot-scope="text, record">
              <a-input-number v-model="record.amount" :min="1" :step="1" disabled/>
            </template>
            <template slot="priceShow" slot-scope="text, record">
              <a-input-number v-model="record.price" :min="1" disabled/>
            </template>
          </a-table>
        </a-col>
      </a-row>
    </a-form>
    <div class="drawer-bootom-button">
      <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
    </div>
  </a-drawer>
</template>

<script>
import {mapState} from 'vuex'
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'requestAdd',
  props: {
    requestAddVisiable: {
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.requestAddVisiable
      },
      set: function () {
      }
    },
    columns () {
      return [{
        title: '物品名称',
        dataIndex: 'name',
        scopedSlots: {customRender: 'nameShow'}
      }, {
        title: '型号',
        dataIndex: 'type',
        scopedSlots: {customRender: 'typeShow'}
      }, {
        title: '数量',
        dataIndex: 'amount',
        scopedSlots: {customRender: 'amountShow'}
      }, {
        title: '所属类型',
        dataIndex: 'typeId',
        width: 200,
        scopedSlots: {customRender: 'typeIdShow'}
      }, {
        title: '单位',
        dataIndex: 'unit',
        scopedSlots: {customRender: 'unitShow'}
      }, {
        title: '单价',
        dataIndex: 'price',
        scopedSlots: {customRender: 'priceShow'}
      }]
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      dataList: [],
      consumableType: [],
      comboList: [],
      comboNum: ''
    }
  },
  mounted () {
    this.getConsumableType()
    this.getComboData()
  },
  methods: {
    handleChange (value) {
      if (value !== '') {
        this.$get('/cos/goods-belong/getGoodsByNum', { num: value }).then((r) => {
          this.dataList = r.data.data
        })
      }
    },
    getComboData () {
      this.$get('/cos/stock-goods-put/list').then((r) => {
        this.comboList = r.data.data
      })
    },
    getConsumableType () {
      this.$get('/cos/consumable-type/list').then((r) => {
        this.consumableType = r.data.data
      })
    },
    dataAdd () {
      this.dataList.push({name: '', type: '', typeId: '', unit: '', amount: '', price: ''})
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      if (this.dataList.length !== 0) {
        this.form.validateFields((err, values) => {
          if (!err) {
            values.staffId = this.currentUser.userId
            values.num = this.comboNum
            this.loading = true
            this.$post('/cos/repair-info', {
              ...values
            }).then((r) => {
              this.reset()
              this.$emit('success')
            }).catch(() => {
              this.loading = false
            })
          }
        })
      } else {
        this.$message.warning('请添加记录！')
      }
    }
  }
}
</script>

<style scoped>

</style>
