### 基于SpringBoot + Vue的食品工厂制造系统 工厂货物系统.

食品系统、生产批次溯源、数字化车间、仓储管理、生产排程

###### 管理员：
流程管理（初级产品加工、二次加工、食品制造、食品分装）、食品制造流程，员工管理、设备管理、原材料管理、工艺路线管理、客户管理、库房管理、库房统计、出库管理、出库管理、订单管理、订单支付记录、客户收货地址管理、原料审批出库、数据统计。

###### 员工：
员工信息、修改密码、订单管理、设备维修、库房管理，食品制造流程、流程原料申请。

###### 用户：
用户注册、修改密码、用户信息、商品选购、订单支付、订单管理、订单评价、收货地址管理。

##### 制造加工与工艺管理
###### 流程管理/制造： 涵盖初加、精加到分装的全链路管控，确保食品生产每道工序符合工艺标准。

###### 工艺路线管理： 科学定义产品加工路径与技术参数，标准化作业流程，保障食品品质的高度统一。

###### 制造流程/原料申请： 员工根据产线进度在线申领物料，实现生产需求与库房供应的精准对接。

##### 物资供应与仓储物流
###### 原材料/库房管理： 全面监管原料入库、存储与保质期，通过数字化盘点确保库存状态实时透明。

###### 原料审批/出库： 管理员严格审核生产领料请求，记录物料流向，实现食品生产的正向与反向溯源。

###### 客户/收货地址： 维护完善的客户档案与多地收货信息，为成品精准配送提供核心基础数据支持。

##### 设备运维与资源配置
###### 设备管理/维修： 建立设备电子档案并跟进维修进度，预防性维护保障产线平稳运行，降低停工风险。

###### 员工管理/信息： 统一管控工厂人员权限与岗位信息，支持密码自主修改，确保生产操作责任到人。

##### 商业交易与订单执行
###### 商品选购/支付： 为用户提供直观的食品选购界面与安全支付通道，缩短从车间到餐桌的交易链路。

###### 订单管理/记录： 记录从下单、支付到评价的全生命周期，协助管理人员监控销售动态与服务质量。

##### 数据统计与辅助决策
###### 库房统计/数据： 聚合生产成本、库存损耗及订单营收等多维指标，通过可视化报表辅助工厂经营决策。

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok


#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 默认后台账户密码
[管理员]
admin
1234qwer

#### 项目截图
暂无

|  |  |
|---------------------|---------------------|
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/10e53904-f0ac-42af-91a2-80af13b94365.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/64212323-3d67-48d3-a433-6eb126355fcf.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/28a75739-f367-46ff-b925-a9a26b548fe5.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/af6f5c1e-cce1-438d-8e0d-a9d72356c11c.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/81e871e2-21bf-49b7-aca2-0d31b49d6ed8.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/b39a5f63-5e02-4cea-b547-5b3a3d3b8a74.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/96c0f440-0a1b-4a2d-8ff6-5933eca13f64.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/b760f9ba-b646-4324-b92a-c2c9271a7431.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/224cc8cd-6b63-4be7-81a8-05a36aa62cbd.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/c0d65b77-b0fa-42bc-9451-45be269f39c2.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/447afaed-e088-4935-8437-78cf41e3bf4f.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/c92d9253-453a-4659-a606-173f7d57824d.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/746cae70-b51f-4d2b-b40d-2f29197182ae.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/c240736f-c448-4874-bcf6-028bcd6a046a.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/2774fd30-1548-4b51-9935-9c482dde32c0.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/cbd36fdc-4b06-4276-b005-205959bca8aa.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/9349c471-7b1f-42b7-b4d8-a67780ad9085.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/efc0d2fc-7c1b-4813-897e-5cea29e66c1f.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/43604d17-de0a-4226-82d7-4e9e3257d38b.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/4a0ce975-9fc8-42d4-9e5b-e5ac0236b930.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/50124b35-be44-4eba-a955-da7d8e3d27cd.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/07c72027-7a74-42d8-b794-f4d38fd02d9e.png) |
| ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/610008af-6136-4089-a99e-e844a9e9b365.png) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/8ebb5e2d-f6b8-48f6-8f54-4ab8d61d06f2.png) |

![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png)

#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.**黑奴价格**

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)

#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)

<p><img align="center" src="https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/%E5%90%88%E4%BD%9C%E7%89%A9%E6%96%99%E6%A0%B7%E5%BC%8F%20(3).png" alt="fankekeke" /></p>
